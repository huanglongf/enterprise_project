package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WmsSailOrderPushInfoRequestContainerItem implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1745055616369211322L;
    @XmlElement
    private String container_id;
    @XmlElement
    private String item;
    @XmlElement
    private BigDecimal quantity;
    @XmlElement
    private String quantity_um;
    @XmlElement
    private String lot;
    @XmlElement
    private BigDecimal weight;
    @XmlElement
    private String weight_um;
    @XmlElement
    private String user_stamp;
    @XmlElement
    private String user_def1;
    @XmlElement
    private String user_def2;
    @XmlElement
    private String user_def3;
    @XmlElement
    private String user_def4;
    @XmlElement
    private String user_def5;
    @XmlElement
    private String user_def6;
    @XmlElement
    private String user_def7;
    @XmlElement
    private String user_def8;
    @XmlElement
    private SerialNumber serialNumberList;

    public String getContainer_id() {
        return container_id;
    }

    public void setContainer_id(String containerId) {
        container_id = containerId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getQuantity_um() {
        return quantity_um;
    }

    public void setQuantity_um(String quantityUm) {
        quantity_um = quantityUm;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getWeight_um() {
        return weight_um;
    }

    public void setWeight_um(String weightUm) {
        weight_um = weightUm;
    }

    public String getUser_stamp() {
        return user_stamp;
    }

    public void setUser_stamp(String userStamp) {
        user_stamp = userStamp;
    }

    public String getUser_def1() {
        return user_def1;
    }

    public void setUser_def1(String userDef1) {
        user_def1 = userDef1;
    }

    public String getUser_def2() {
        return user_def2;
    }

    public void setUser_def2(String userDef2) {
        user_def2 = userDef2;
    }

    public String getUser_def3() {
        return user_def3;
    }

    public void setUser_def3(String userDef3) {
        user_def3 = userDef3;
    }

    public String getUser_def4() {
        return user_def4;
    }

    public void setUser_def4(String userDef4) {
        user_def4 = userDef4;
    }

    public String getUser_def5() {
        return user_def5;
    }

    public void setUser_def5(String userDef5) {
        user_def5 = userDef5;
    }

    public String getUser_def6() {
        return user_def6;
    }

    public void setUser_def6(String userDef6) {
        user_def6 = userDef6;
    }

    public String getUser_def7() {
        return user_def7;
    }

    public void setUser_def7(String userDef7) {
        user_def7 = userDef7;
    }

    public String getUser_def8() {
        return user_def8;
    }

    public void setUser_def8(String userDef8) {
        user_def8 = userDef8;
    }

    public SerialNumber getSerialNumberList() {
        return serialNumberList;
    }

    public void setSerialNumberList(SerialNumber serialNumberList) {
        this.serialNumberList = serialNumberList;
    }
}
