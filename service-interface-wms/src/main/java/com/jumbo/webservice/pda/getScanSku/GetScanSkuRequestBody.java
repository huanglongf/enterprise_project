package com.jumbo.webservice.pda.getScanSku;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSkuRequestBody", propOrder = {"skuBarcode","qty","locationCode","batchCode"})
public class GetScanSkuRequestBody  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4228359409633488979L;
	/**
	 * 商品Id
	 */
	@XmlElement(required = true)
	private String skuBarcode;
	/**
	 * 数量
	 */
	@XmlElement(required = true)
	private Long qty;
	/**
	 * 库位
	 */
	@XmlElement(required = true)
	private String locationCode;
	/**
	 * 批次号
	 */
	@XmlElement(required = true)
	private String batchCode;
	
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getSkuBarcode() {
		return skuBarcode;
	}
	public void setSkuBarcode(String skuBarcode) {
		this.skuBarcode = skuBarcode;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

}
