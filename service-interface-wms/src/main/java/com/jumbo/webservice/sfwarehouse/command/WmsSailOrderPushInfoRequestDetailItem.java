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
public class WmsSailOrderPushInfoRequestDetailItem implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4359420491658069238L;
    @XmlElement
    private String item;
    @XmlElement
    private BigDecimal quantity;
    @XmlElement
    private String quantity_um;
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

}
