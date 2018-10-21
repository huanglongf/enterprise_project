package com.jumbo.webservice.sfNew.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风订单发货确认
 * 
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OrderConfirm")
public class SfOrderConfirm extends BaseModel {
    static final long serialVersionUID = -812366078695607187L;
    
    public final static int DEAL_TYPE_DETERMINE = 1; //订单确认
    public final static int DEAL_TYPE_CANCEL = 2; //取消
    
    public static final String SERVICE_NAME = "OrderConfirmService";
    
    /**
     * 订单号
     */
    @XmlAttribute(required = true, name = "orderid")
    private String orderId;
    /**
     * 运单号
     */
    @XmlAttribute(required = true, name = "mailno")
    private String mailno;
    /**
     * 订单操作标识 :1 -订单确认 2-取消
     */
    @XmlAttribute(required = true, name = "dealtype")
    private Integer dealtype;
    
    /**
     * 可选信息
     */
    @XmlElement(name = "OrderConfirmOption")
    private SfOrderConfirmOption orderConfirmOption;

    public SfOrderConfirmOption getOrderConfirmOption() {
        return orderConfirmOption;
    }

    public void setOrderConfirmOption(SfOrderConfirmOption orderConfirmOption) {
        this.orderConfirmOption = orderConfirmOption;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public Integer getDealtype() {
        return dealtype;
    }

    public void setDealtype(Integer dealtype) {
        this.dealtype = dealtype;
    }
}
