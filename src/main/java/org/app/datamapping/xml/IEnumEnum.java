package org.app.datamapping.xml;

import javax.xml.bind.annotation.XmlElement;

public class IEnumEnum {
    @XmlElement(name = "EnumNumber")
    private String enumNumber;

    public String getEnumNumber() {
        return enumNumber;
    }

    public void setEnumNumber(String enumNumber) {
        this.enumNumber = enumNumber;
    }
}
