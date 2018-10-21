package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WmsInventoryAdjustPushInfoAdjust implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4894363938941187449L;
    @XmlElement
    private String item;
    @XmlElement
    private String direction;
    @XmlElement
    private BigDecimal quantity;
    @XmlElement
    private String inventory_sts;
    @XmlElement
    private String lot;
    @XmlElement
    private String transaction_type;
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
    private BigDecimal user_def7;
    @XmlElement
    private BigDecimal user_def8;
    @XmlElement
    private Date activity_date_time;
    @XmlElement
    private BigDecimal before_on_hand_qty;
    @XmlElement
    private BigDecimal after_on_hand_qty;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getInventory_sts() {
        return inventory_sts;
    }

    public void setInventory_sts(String inventorySts) {
        inventory_sts = inventorySts;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transactionType) {
        transaction_type = transactionType;
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

    public BigDecimal getUser_def7() {
        return user_def7;
    }

    public void setUser_def7(BigDecimal userDef7) {
        user_def7 = userDef7;
    }

    public BigDecimal getUser_def8() {
        return user_def8;
    }

    public void setUser_def8(BigDecimal userDef8) {
        user_def8 = userDef8;
    }

    public Date getActivity_date_time() {
        return activity_date_time;
    }

    public void setActivity_date_time(Date activityDateTime) {
        activity_date_time = activityDateTime;
    }

    public BigDecimal getBefore_on_hand_qty() {
        return before_on_hand_qty;
    }

    public void setBefore_on_hand_qty(BigDecimal beforeOnHandQty) {
        before_on_hand_qty = beforeOnHandQty;
    }

    public BigDecimal getAfter_on_hand_qty() {
        return after_on_hand_qty;
    }

    public void setAfter_on_hand_qty(BigDecimal afterOnHandQty) {
        after_on_hand_qty = afterOnHandQty;
    }

}
