package com.jumbo.pms.model.command;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 包裹主信息    
 */
public class ShipmentCommand implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 业务店铺
	 */
	private String channelCode;

	/**
     * 订单系统来源
            GOMS
            TmallOMS
            etc.
     */
    private String sourceSys;
    
    /**
     * TransTimeType
     * 运单时限类型(快递附加服务)
     *     ORDINARY(1), // 普通
            TIMELY(3), // 及时达
            SAME_DAY(5), // 当日
            THE_NEXT_DAY(6); // 次日
     */
    private Integer transTimeType;
    
    /**
     * 出发点编码
     */
    private String originCode;
    
    /**
     * 目的地编码
     */
    private String destinationCode;

    /**
     * oms订单号
     */
    private String omsOrderCode;
    
    /**
     * 外部平台订单号
     */
    private String platformOrderCode;
    
    //暂时冗余退单号
    /**
     * 外部平台退单号
     */
    private String platformRaCode;
    
    /**
     * 宝尊退单号
     */
    private String omsRaCode;

    /**
     * 寄件人
     */
    private String sender;

    /**
     * 寄件人电话
     */
    private String senderMobile;
    
    /**
     * 收件人
     */
    private String receiver;

    /**
     * 收件人电话
     */
    private String receiverMobile;
    
    /**
     * 地址(如果是自提点,非必填)
     */
    private String address;

    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    
    /**
     * 邮编
     */
    private String zipcode;


    /**
     * 是否COD
     */
    private Boolean isCod;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 类型
     * 1. 销售出
     * 2. 退货入
     */
    private Integer type;
    
    /**
     * 包裹费用总计
     */
    private BigDecimal totalCharges;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

    public String getSourceSys() {
		return sourceSys;
	}

	public void setSourceSys(String sourceSys) {
		this.sourceSys = sourceSys;
	}

	public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }

    public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public Boolean getIsCod() {
		return isCod;
	}

	public void setIsCod(Boolean isCod) {
		this.isCod = isCod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOmsOrderCode() {
		return omsOrderCode;
	}

	public void setOmsOrderCode(String omsOrderCode) {
		this.omsOrderCode = omsOrderCode;
	}

    public String getPlatformOrderCode() {
        return platformOrderCode;
    }

    public void setPlatformOrderCode(String platformOrderCode) {
        this.platformOrderCode = platformOrderCode;
    }

    public String getPlatformRaCode() {
        return platformRaCode;
    }

    public void setPlatformRaCode(String platformRaCode) {
        this.platformRaCode = platformRaCode;
    }

    public String getOmsRaCode() {
        return omsRaCode;
    }

    public void setOmsRaCode(String omsRaCode) {
        this.omsRaCode = omsRaCode;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public BigDecimal getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(BigDecimal totalCharges) {
        this.totalCharges = totalCharges;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
