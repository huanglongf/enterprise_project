package com.jumbo.mq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 订单支付信息
 * 
 * @author dzz
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqsopaymentinfomsg")
public class MqSoPaymentInfoMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1706845583411235425L;

    /**
     * oms店铺ID
     */
    private Long omsShopId;

    /**
     * 平台订单号
     */
    private String platformOrderCode;

    /**
     * 付款时间
     */
    private Date paymentCompleteTime;

    /**
     * 付款总金额
     */
    private BigDecimal total;

    /**
     * 付款方式详细信息
     */
    @XmlElements({@XmlElement(name = "mqsotendermsg", type = MqSoTenderMsg.class)})
    private List<MqSoTenderMsg> tenderList;

    public Long getOmsShopId() {
        return omsShopId;
    }

    public void setOmsShopId(Long omsShopId) {
        this.omsShopId = omsShopId;
    }

    public String getPlatformOrderCode() {
        return platformOrderCode;
    }

    public void setPlatformOrderCode(String platformOrderCode) {
        this.platformOrderCode = platformOrderCode;
    }

    public Date getPaymentCompleteTime() {
        return paymentCompleteTime;
    }

    public void setPaymentCompleteTime(Date paymentCompleteTime) {
        this.paymentCompleteTime = paymentCompleteTime;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<MqSoTenderMsg> getTenderList() {
        return tenderList;
    }

    public void setTenderList(List<MqSoTenderMsg> tenderList) {
        this.tenderList = tenderList;
    }

}
