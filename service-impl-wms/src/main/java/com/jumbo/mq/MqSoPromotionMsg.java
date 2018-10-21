package com.jumbo.mq;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 订单行促销信息
 * 
 * @author dzz
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqsopromotionmsg")
public class MqSoPromotionMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6149310021680638843L;

    /**
     * 平台订单号
     */
    private String platformOrderCode;

    /**
     * 平台订单行ID
     */
    private Long platformLineId;

    /**
     * 促销编码 该字段目前在oms中无需备案,oms中仅记录该信息
     */
    private String code;

    /**
     * 促销类型 值域由商城前端预定义(商城共用一套) ,维护进oms ChooseOption
     */
    private String type;

    /**
     * 优惠券代码
     */
    private String couponCode;

    /**
     * 优惠金额
     */
    private BigDecimal discountFee;

    /**
     * 活动描述
     */
    private String description;

    public String getPlatformOrderCode() {
        return platformOrderCode;
    }

    public void setPlatformOrderCode(String platformOrderCode) {
        this.platformOrderCode = platformOrderCode;
    }

    public Long getPlatformLineId() {
        return platformLineId;
    }

    public void setPlatformLineId(Long platformLineId) {
        this.platformLineId = platformLineId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
