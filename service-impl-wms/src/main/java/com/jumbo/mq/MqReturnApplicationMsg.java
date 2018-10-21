package com.jumbo.mq;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqramsg")
public class MqReturnApplicationMsg implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 437615224993945567L;

    /**
     * 退换货申请编号
     */
    private String raCode;
    
    /**
     * OMS订单编码
     */
    private String omsCode;
    
    /**
     * OMS退货单编号
     */
    private String omsRoCode;
    
    /**
     * OMS换货-新销售单编号
     */
    private String newOmsCode;
    
    /**
     * 平台申请编码
     */
    private String platformCode;

    /**
     * 宝尊OMS店铺ID
     */
    private Long omsShopId;

    /**
     * 平台订单编码
     */
    private String platformSoCode;

    /**
     * 平台创建时间
     */
    private Date platformCreateTime;

    /**
     * 退换货申请类型
     */
    private int type;

    /**
     * 退款方式
     */
    private String refundType;

    /**
     * 申请原因
     */
    private String returnReason;

    /**
     * 退款账户
     */
    private String refundAccount;
    
    /**
     * 退款收款人
     */
    private String refundPayee;

    /**
     * 退款银行
     */
    private String refundBank;

    /**
     * 退货仓库编码
     */
    private String rtnWarehouseCode;

    /**
     * 是否需要退回发票
     */
    private String isNeededReturnInvoice;

    /**
     * 备注
     */
    private String memo;

    /**
     * 退换货申请明细行信息
     */
    @XmlElements({@XmlElement(name = "mqralinemsg", type = MqReturnApplicationLineMsg.class)})
    private List<MqReturnApplicationLineMsg> mqReAppLineMsgList;

    /**
     * 换货申请配送信
     */
    private MqRaDeliveryInfoMsg mqRaDeliveryInfoMsg;

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public Long getOmsShopId() {
        return omsShopId;
    }

    public void setOmsShopId(Long omsShopId) {
        this.omsShopId = omsShopId;
    }

    public String getPlatformSoCode() {
        return platformSoCode;
    }

    public void setPlatformSoCode(String platformSoCode) {
        this.platformSoCode = platformSoCode;
    }

    public Date getPlatformCreateTime() {
        return platformCreateTime;
    }

    public void setPlatformCreateTime(Date platformCreateTime) {
        this.platformCreateTime = platformCreateTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }

    public String getRefundBank() {
        return refundBank;
    }

    public void setRefundBank(String refundBank) {
        this.refundBank = refundBank;
    }

    public String getRtnWarehouseCode() {
        return rtnWarehouseCode;
    }

    public void setRtnWarehouseCode(String rtnWarehouseCode) {
        this.rtnWarehouseCode = rtnWarehouseCode;
    }

    public String getIsNeededReturnInvoice() {
        return isNeededReturnInvoice;
    }

    public void setIsNeededReturnInvoice(String isNeededReturnInvoice) {
        this.isNeededReturnInvoice = isNeededReturnInvoice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<MqReturnApplicationLineMsg> getMqReAppLineMsgList() {
        return mqReAppLineMsgList;
    }

    public void setMqReAppLineMsgList(List<MqReturnApplicationLineMsg> mqReAppLineMsgList) {
        this.mqReAppLineMsgList = mqReAppLineMsgList;
    }

    public MqRaDeliveryInfoMsg getMqRaDeliveryInfoMsg() {
        return mqRaDeliveryInfoMsg;
    }

    public void setMqRaDeliveryInfoMsg(MqRaDeliveryInfoMsg mqRaDeliveryInfoMsg) {
        this.mqRaDeliveryInfoMsg = mqRaDeliveryInfoMsg;
    }

	public String getRefundPayee() {
		return refundPayee;
	}

	public void setRefundPayee(String refundPayee) {
		this.refundPayee = refundPayee;
	}
	
	public String getOmsCode() {
		return omsCode;
	}

	public void setOmsCode(String omsCode) {
		this.omsCode = omsCode;
	}

	public String getRaCode() {
		return raCode;
	}

	public void setRaCode(String raCode) {
		this.raCode = raCode;
	}

	public String getOmsRoCode() {
		return omsRoCode;
	}

	public void setOmsRoCode(String omsRoCode) {
		this.omsRoCode = omsRoCode;
	}

	public String getNewOmsCode() {
		return newOmsCode;
	}

	public void setNewOmsCode(String newOmsCode) {
		this.newOmsCode = newOmsCode;
	}

}
