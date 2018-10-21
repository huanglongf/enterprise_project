package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sku")
public class SkuSyncCommand implements Serializable {

    private static final long serialVersionUID = -9158541469503524893L;
    private String skucode = "";
    private String name = "";
    private String desc = "";
    private String brand = "";
    private String ALTERNATESKU1;
    private String ALTERNATESKU2;

    public String getSkucode() {
        return skucode;
    }

    public void setSkucode(String skucode) {
        this.skucode = skucode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getALTERNATESKU1() {
        return ALTERNATESKU1;
    }

    public void setALTERNATESKU1(String aLTERNATESKU1) {
        ALTERNATESKU1 = aLTERNATESKU1;
    }

    public String getALTERNATESKU2() {
        return ALTERNATESKU2;
    }

    public void setALTERNATESKU2(String aLTERNATESKU2) {
        ALTERNATESKU2 = aLTERNATESKU2;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
