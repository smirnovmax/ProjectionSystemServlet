package org.app.datamapping.json.translation;


import org.app.datamapping.json.outgoing.ProjectionSystemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final Logger log = LoggerFactory.getLogger(DataManager.class);
    //environment variable name
    private static final String XML_PATH_ENVIRONMENT_VARIABLE_NAME = "SPF_SCHEMA";
    //extensions
    private static final String LOWER_XML_EXTENSION = ".xml";
    private static final String UPPER_XML_EXTENSION = ".XML";
    //xml tags
    private static final String XML_TAG_ENUM_ENUM = "EnumEnum";
    private static final String XML_TAG_NAME = "Name";
    private static final String XML_TAG_UID = "UID";
    private static final String XML_TAG_IOBJECT = "IObject";

    /**
     * method get path from environment variable
     * @return - all data from all subdirectories
     */
    public List<ProjectionSystemData> extractData() {
        try {
            String environmentVar = System.getenv(XML_PATH_ENVIRONMENT_VARIABLE_NAME);
            return readAllData(environmentVar);
        } catch (Exception exc) {
            log.error("environment variable not found {}", exc.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * search files in subdirectories
     * @param path - folder path
     * @return - all data with xml tag IObject
     */
    private List<ProjectionSystemData> readAllData(String path) {
        List<ProjectionSystemData> allData = new ArrayList<>();
        try {
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                String filename = file.getName();

                if (file.isDirectory()) {
                    allData.addAll(readAllData(file.getCanonicalPath()));
                } else if (filename.endsWith(LOWER_XML_EXTENSION) || filename.endsWith(UPPER_XML_EXTENSION)) {
                    allData.addAll(readDataFromXml(path + "\\" + filename));
                }
            }
        } catch (IOException exc) {
            log.error("Can't read data from xml file: {}", exc.getMessage());
        }
        return allData;
    }

    /**
     *
     * @param path - file path
     * @return - data with xml tag IObject
     */
    private List<ProjectionSystemData> readDataFromXml(String path) {
        List<ProjectionSystemData> resultData = new ArrayList<>();
        try {
            File file = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName(XML_TAG_IOBJECT);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE &&
                        nNode.getParentNode().getNodeName().equals(XML_TAG_ENUM_ENUM)) {
                    log.warn("Wrong node type or Parent name not equals {} ", XML_TAG_ENUM_ENUM);
                    Element eElement = (Element) nNode;
                    ProjectionSystemData dataItem = new ProjectionSystemData(eElement.getAttribute(XML_TAG_UID),
                            eElement.getAttribute(XML_TAG_NAME));
                    resultData.add(dataItem);
                }
            }
        } catch (Exception exc) {
            log.error("Can't read data from xml file: {}", exc.getMessage());
        }
        if (resultData.isEmpty()) {
            log.warn("Tags {} are not found in {} file ", XML_TAG_IOBJECT, path);
        }
        return resultData;
    }
}