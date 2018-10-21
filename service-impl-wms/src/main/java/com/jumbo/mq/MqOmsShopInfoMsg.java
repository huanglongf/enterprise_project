package com.jumbo.mq;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 订单信息
 * 
 * @author fanyunlong
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqomsshopinfomsg")
public class MqOmsShopInfoMsg implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5482888608109901489L;

	/**
     * oms店铺id
     */
    private Long omsShopId;

    /**
     * oms店铺Name
     */
    private String omsShopName;
    
    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 备注(相关明细信息拼接成的JSON字符串)
     */
    private String description;

	public Long getOmsShopId() {
		return omsShopId;
	}

	public void setOmsShopId(Long omsShopId) {
		this.omsShopId = omsShopId;
	}

	public String getOmsShopName() {
		return omsShopName;
	}

	public void setOmsShopName(String omsShopName) {
		this.omsShopName = omsShopName;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
