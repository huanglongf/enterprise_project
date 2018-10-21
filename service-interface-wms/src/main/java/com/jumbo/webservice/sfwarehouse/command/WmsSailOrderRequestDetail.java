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
public class WmsSailOrderRequestDetail implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3490997871028456352L;
    @XmlElement
    private Integer erp_order_line_num; // <erp_order_line_num>行号</erp_order_line_num>
    @XmlElement
    private String item;// <item>商品编号</item>
    @XmlElement
    private String item_name;// <item_name>商品名称</item_name>
    @XmlElement
    private String uom;// <uom>单位</uom>
    @XmlElement
    private String lot;// <lot>批号</lot>
    @XmlElement
    private BigDecimal qty;// <qty>数量</qty>
    @XmlElement
    private BigDecimal item_price;// <item_price>价格</item_price>
    @XmlElement
    private BigDecimal item_discount;// <item_discount>优惠金额</item_discount>
    @XmlElement
    private String bom_action;// <bom_action>是否为组合商品</bom_action>
    @XmlElement
    private String user_def1;// <user_def1>预留字段1</user_def1>
    @XmlElement
    private String user_def2;// <user_def2>预留字段2</user_def2>
    @XmlElement
    private String user_def3;// <user_def3>预留字段3</user_def3>
    @XmlElement
    private String user_def4;// <user_def4>预留字段4</user_def4>
    @XmlElement
    private String user_def5;// <user_def5>预留字段5</user_def5>
    @XmlElement
    private String user_def6;// <user_def6>预留字段6</user_def6>
    @XmlElement
    private String user_def7;// <user_def7>预留字段7</user_def7>
    @XmlElement
    private String user_def8;// <user_def8>预留字段8</user_def8>

    public Integer getErp_order_line_num() {
        return erp_order_line_num;
    }

    public void setErp_order_line_num(Integer erpOrderLineNum) {
        erp_order_line_num = erpOrderLineNum;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String itemName) {
        item_name = itemName;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getItem_price() {
        return item_price;
    }

    public void setItem_price(BigDecimal itemPrice) {
        item_price = itemPrice;
    }

    public BigDecimal getItem_discount() {
        return item_discount;
    }

    public void setItem_discount(BigDecimal itemDiscount) {
        item_discount = itemDiscount;
    }

    public String getBom_action() {
        return bom_action;
    }

    public void setBom_action(String bomAction) {
        bom_action = bomAction;
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
