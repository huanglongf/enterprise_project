package com.jumbo.webservice.sfOrder.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 顺风订单确认反馈
 * 
 * @author sjk
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "updatedOrderResponse")
public class SfComfirmOrderResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6631973511876656648L;

    public final static String RESULT_SUEECSS = "1";

    public final static String RESULT_FAILED = "2";

    public final static String REMARK_COMMITED = "1201-3";
    /**
     * 订单号
     */
    @XmlElement(required = true, name = "orderid")
    private String orderId;
    /**
     * 结果
     */
    @XmlElement(name = "result")
    private String result;
    /**
     * 结果
     */
    @XmlElement(name = "remark")
    private String remark;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
