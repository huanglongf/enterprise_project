package com.jumbo.webservice.sfOrder.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 顺风订单确认
 * 
 * @author sjk
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "updateOrderRequest")
public class SfComfirmOrder implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5376910680174809325L;
    /**
     * 订单号
     */
    @XmlElement(required = true, name = "orderid")
    private String orderId;
    /**
     * 重量
     */
    @XmlElement(required = true, name = "weight")
    private String weight;
    /**
     * 运单号
     */
    @XmlElement(required = true, name = "mailno")
    private String mailno;
    /**
     * 验证码
     */
    @XmlElement(required = true, name = "checkword")
    private String checkword;
    /**
     * 长
     */
    @XmlElement(required = true, name = "filter2")
    private String filter2;
    /**
     * 宽
     */
    @XmlElement(required = true, name = "filter3")
    private String filter3;
    /**
     * 高
     */
    @XmlElement(required = true, name = "filter4")
    private String filter4;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    public String getFilter4() {
        return filter4;
    }

    public void setFilter4(String filter4) {
        this.filter4 = filter4;
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    public String getFilter3() {
        return filter3;
    }

    public void setFilter3(String filter3) {
        this.filter3 = filter3;
    }
}
