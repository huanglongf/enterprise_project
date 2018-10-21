package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 采购入库单接口实体Header
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "header")
public class WmsPurchaseOrderRequestHeader implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -335060478782594940L;
    @XmlElement
    private String company;
    @XmlElement
    private String warehouse;
    @XmlElement
    private String erp_order_num;
    @XmlElement
    private String erp_order_type;
    @XmlElement
    private String order_date;
    @XmlElement
    private String buyer;
    @XmlElement
    private String buyer_phone;
    @XmlElement
    private String scheduled_receipt_date;
    @XmlElement
    private String source_id;
    @XmlElement
    private String transfer_warehouse;
    @XmlElement
    private String original_order_no;
    @XmlElement
    private String other_inbound_note;
    @XmlElement
    private String note_to_receiver;
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
    private String user_def50;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getErp_order_num() {
        return erp_order_num;
    }

    public void setErp_order_num(String erpOrderNum) {
        erp_order_num = erpOrderNum;
    }

    public String getErp_order_type() {
        return erp_order_type;
    }

    public void setErp_order_type(String erpOrderType) {
        erp_order_type = erpOrderType;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String orderDate) {
        order_date = orderDate;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyer_phone() {
        return buyer_phone;
    }

    public void setBuyer_phone(String buyerPhone) {
        buyer_phone = buyerPhone;
    }

    public String getScheduled_receipt_date() {
        return scheduled_receipt_date;
    }

    public void setScheduled_receipt_date(String scheduledReceiptDate) {
        scheduled_receipt_date = scheduledReceiptDate;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String sourceId) {
        source_id = sourceId;
    }

    public String getTransfer_warehouse() {
        return transfer_warehouse;
    }

    public void setTransfer_warehouse(String transferWarehouse) {
        transfer_warehouse = transferWarehouse;
    }

    public String getOriginal_order_no() {
        return original_order_no;
    }

    public void setOriginal_order_no(String originalOrderNo) {
        original_order_no = originalOrderNo;
    }

    public String getOther_inbound_note() {
        return other_inbound_note;
    }

    public void setOther_inbound_note(String otherInboundNote) {
        other_inbound_note = otherInboundNote;
    }

    public String getNote_to_receiver() {
        return note_to_receiver;
    }

    public void setNote_to_receiver(String noteToReceiver) {
        note_to_receiver = noteToReceiver;
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

    public String getUser_def50() {
        return user_def50;
    }

    public void setUser_def50(String userDef50) {
        user_def50 = userDef50;
    }

}
