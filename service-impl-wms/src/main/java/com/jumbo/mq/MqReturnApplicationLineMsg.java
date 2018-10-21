package com.jumbo.mq;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqralinemsg")
public class MqReturnApplicationLineMsg implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 803669157865727147L;

    /**
     * 退回商品SKU编码
     */
    private String rtnJmskuCode;
    
    /**
     * 换货商品SKU编码
     */
    private String chgJmskuCode;
    
    /**
     * 退回商品外部编码
     */
    private String rtnExtentionCode;

    /**
     * 换货商品外部编码
     */
    private String chgExtentionCode;

    /**
     * 退换货商品数量
     */
    private Integer qty;

    /**
     * 平台退货订单行ID
     */
    private Long platformSoLineId;

    public String getRtnExtentionCode() {
        return rtnExtentionCode;
    }

    public void setRtnExtentionCode(String rtnExtentionCode) {
        this.rtnExtentionCode = rtnExtentionCode;
    }

    public String getChgExtentionCode() {
        return chgExtentionCode;
    }

    public void setChgExtentionCode(String chgExtentionCode) {
        this.chgExtentionCode = chgExtentionCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Long getPlatformSoLineId() {
        return platformSoLineId;
    }

    public void setPlatformSoLineId(Long platformSoLineId) {
        this.platformSoLineId = platformSoLineId;
    }

	public String getRtnJmskuCode() {
		return rtnJmskuCode;
	}

	public void setRtnJmskuCode(String rtnJmskuCode) {
		this.rtnJmskuCode = rtnJmskuCode;
	}

	public String getChgJmskuCode() {
		return chgJmskuCode;
	}

	public void setChgJmskuCode(String chgJmskuCode) {
		this.chgJmskuCode = chgJmskuCode;
	}

}
