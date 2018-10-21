package com.jumbo.webservice.sfNew.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 顺风下单响应实体
 * 
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OrderResponse")
public class SfOrderResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2186600594935181281L;
    /**
     * 订单号
     */
    @XmlAttribute(name = "orderid")
    private String orderid;
    /**
     * 运单号 ，可多个单号如子母件以逗分隔
     */
    @XmlAttribute(name = "mailno")
    private String mailno;
    /**
     * 原寄地代码
     */
    @XmlAttribute(name = "origincode")
    private String originCode;
    /**
     * 目的地代码
     */
    @XmlAttribute(name = "destcode")
    private String destCode;
    /**
     * 筛单结果： 1-人工确认， 2-可收派， 3-不可以派送
     */
    @XmlAttribute(name = "filter_result")
    private Integer filterResult;
    /**
     * 1-收方超范围， 收方超范围， 收方超范围， 收方超范围， 2-派方超范围， 派方超范围， 派方超范围， 3-其他原因 其
     */
    @XmlAttribute(name = "remark")
    private String remark;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public Integer getFilterResult() {
        return filterResult;
    }

    public void setFilterResult(Integer filterResult) {
        this.filterResult = filterResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
