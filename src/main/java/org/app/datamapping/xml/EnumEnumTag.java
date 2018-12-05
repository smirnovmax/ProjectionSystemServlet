package org.app.datamapping.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EnumEnum")
public class EnumEnumTag {
    @XmlElement(name = "IObject")
    private IObject iObject;
    @XmlElement(name = "IEnumEnum")
    private IEnumEnum iEnumEnum;
    @XmlElement(name = "ISchemaObj")
    private ISchemaObj iSchemaObj;

    public IObject getiObject() {
        return iObject;
    }

    public void setiObject(IObject iObject) {
        this.iObject = iObject;
    }

    public IEnumEnum getiEnumEnum() {
        return iEnumEnum;
    }

    public void setiEnumEnum(IEnumEnum iEnumEnum) {
        this.iEnumEnum = iEnumEnum;
    }

    public ISchemaObj getiSchemaObj() {
        return iSchemaObj;
    }

    public void setiSchemaObj(ISchemaObj iSchemaObj) {
        this.iSchemaObj = iSchemaObj;
    }
}
