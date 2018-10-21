package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
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
public class WmsPurchaseOrderPushInfoRequestHeader implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3824181959446265865L;
    @XmlElement
    private String erp_order_num;
    @XmlElement
    private String receipt_id;
    @XmlElement
    private Date close_date;

    public String getErp_order_num() {
        return erp_order_num;
    }

    public void setErp_order_num(String erpOrderNum) {
        erp_order_num = erpOrderNum;
    }

    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receiptId) {
        receipt_id = receiptId;
    }

    public Date getClose_date() {
        return close_date;
    }

    public void setClose_date(Date closeDate) {
        close_date = closeDate;
    }
}
