package org.app.datamapping.xml;

import javax.xml.bind.annotation.XmlElement;

public class IObject {

    @XmlElement(name = "UID")
    private String uid;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Description")
    private String description;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
