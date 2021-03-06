package org.app.datamapping.json.translation;


import org.app.datamapping.json.outgoing.ProjectionSystemResponseBody;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects certain data from xml files and allows to fill specific entity
 */
public class DataManager {

    private static final Logger log = LoggerFactory.getLogger(DataManager.class);
    //environment variable name
    private static final String XML_PATH_ENVIRONMENT_VARIABLE_NAME = "SPF_SCHEMA";
    //extension
    private static final String XML_EXTENSION = ".xml";
    //xml tags
    private static final String XML_TAG_ENUM_ENUM = "EnumEnum";
    private static final String XML_TAG_NAME = "Name";
    private static final String XML_TAG_UID = "UID";
    private static final String XML_TAG_IOBJECT = "IObject";

    /**
     * Method get path from environment variable
     * @return - all data from all subdirectories
     */
    public List<ProjectionSystemResponseBody> extractData() {
        try {
            String environmentVar = System.getenv(XML_PATH_ENVIRONMENT_VARIABLE_NAME);
            return readAllData(environmentVar);
        } catch (Exception exc) {
            log.error("environment variable not found {}", exc.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Search files in subdirectories
     * @param path - folder path
     * @return - all data with xml tag IObject
     */
    private List<ProjectionSystemResponseBody> readAllData(String path) {
        List<ProjectionSystemResponseBody> allData = new ArrayList<>();
        FilesPathsResearcher filesResearcher = new FilesPathsResearcher(XML_EXTENSION, XML_TAG_ENUM_ENUM);
        try {
            Files.walkFileTree(Paths.get(path), filesResearcher);
            List<Path> xmlFilePaths = filesResearcher.getFoundFiles();
            for (Path xmlPath : xmlFilePaths) {
                allData.addAll(readDataFromXml(xmlPath.toString()));
            }
        } catch (IOException exc) {
            log.error("Can't read data from xml file: {}", exc.getMessage());
        }
        return allData;
    }

    /**
     * Method allows to collect data for attributes from a specific xml tag
     * @param path - file path
     * @return - data with xml tag IObject
     */
    private List<ProjectionSystemResponseBody> readDataFromXml(String path) {
        List<ProjectionSystemResponseBody> resultData = new ArrayList<>();
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
                    Element eElement = (Element) nNode;
                    ProjectionSystemResponseBody dataItem = new ProjectionSystemResponseBody(eElement.getAttribute(XML_TAG_UID),
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
