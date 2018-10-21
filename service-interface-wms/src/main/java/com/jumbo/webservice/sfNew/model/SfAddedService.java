package com.jumbo.webservice.sfNew.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 *  附加属性
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AddedService")
public class SfAddedService extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 4994136869858794860L;
    /**
     * 增值服务名称
     */
    @XmlAttribute(required = true, name = "name")
    private String name;
    /**
     * 增值服务值 1，2，3，4，5
     */
    @XmlAttribute(required = true, name = "value")
    private String value;
    @XmlAttribute(required = true, name = "value1")
    private String value1;
    @XmlAttribute(required = true, name = "value2")
    private String value2;
    @XmlAttribute(required = true, name = "value3")
    private String value3;
    @XmlAttribute(required = true, name = "value4")
    private String value4;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

}
