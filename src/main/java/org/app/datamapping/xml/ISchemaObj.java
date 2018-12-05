package org.app.datamapping.xml;

import javax.xml.bind.annotation.XmlElement;

public class ISchemaObj {
    @XmlElement(name = "SchemaRevVer")
    private String schemaRevVer;

    public String getSchemaRevVer() {
        return schemaRevVer;
    }

    public void setSchemaRevVer(String schemaRevVer) {
        this.schemaRevVer = schemaRevVer;
    }
}
