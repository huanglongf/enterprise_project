package com.jumbo.webservice.sfNew.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风订单发货确认 反馈数据
 * 
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OrderConfirmResponse")
public class SfOrderConfirmResponse extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -773638422238298283L;
    
    public final static int RES_STATUS_ERROR = 1; //订单号与运单号不匹配
    public final static int RES_STATUS_SUCCESS = 2; //成功
    /**
     * 订单号
     */
    @XmlAttribute(name = "orderid")
    private String orderId;
    /**
     * 运单号
     */
    @XmlAttribute(name = "mailno")
    private String mailno;
    /**
     * 备注 1 订单号与运单号不匹配 2 成功
     */
    @XmlAttribute(name = "res_status")
    private Integer resStatus;

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

    public Integer getResStatus() {
        return resStatus;
    }

    public void setResStatus(Integer resStatus) {
        this.resStatus = resStatus;
    }
}
