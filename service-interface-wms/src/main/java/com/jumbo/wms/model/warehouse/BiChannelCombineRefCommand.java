package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;

import com.jumbo.wms.model.baseinfo.BiChannelCombineRef;

public class BiChannelCombineRefCommand extends BiChannelCombineRef {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private String warehName;// 仓库名称
	private String channName;// 渠道名称
	private String channCode;// 渠道唯一对接编码
	private String name;//子渠道名称
	private String expDate;//过期时间
	private Long hbChId;// 合并qudao
	private String childChannelIdList;// 子渠道List
	private String staIdList;// 所有能合并的STAIDLIST
	private Long cId;// 主渠道ID
	private Long whId;// 仓库ID
	private BigDecimal skuMaxLength;// 最大SKU体积
	private Long chType;// 渠道类型
	private Long isCombine;//是否合并  0 否 ,1是
	private String zxShopName;//装箱店铺名称  20
	private String ydShopName;//运单店铺名称  20
	
	public String getWarehName() {
		return warehName;
	}

	public void setWarehName(String warehName) {
		this.warehName = warehName;
	}

	public String getChannName() {
		return channName;
	}

	public void setChannName(String channName) {
		this.channName = channName;
	}

	public String getChannCode() {
		return channCode;
	}

	public void setChannCode(String channCode) {
		this.channCode = channCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChildChannelIdList() {
		return childChannelIdList;
	}

	public void setChildChannelIdList(String childChannelIdList) {
		this.childChannelIdList = childChannelIdList;
	}

	public String getStaIdList() {
		return staIdList;
	}

	public void setStaIdList(String staIdList) {
		this.staIdList = staIdList;
	}

	public Long getHbChId() {
		return hbChId;
	}

	public void setHbChId(Long hbChId) {
		this.hbChId = hbChId;
	}

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public Long getWhId() {
		return whId;
	}

	public void setWhId(Long whId) {
		this.whId = whId;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public BigDecimal getSkuMaxLength() {
		return skuMaxLength;
	}

	public void setSkuMaxLength(BigDecimal skuMaxLength) {
		this.skuMaxLength = skuMaxLength;
	}

	public Long getChType() {
		return chType;
	}

	public void setChType(Long chType) {
		this.chType = chType;
	}

	public Long getIsCombine() {
		return isCombine;
	}

	public void setIsCombine(Long isCombine) {
		this.isCombine = isCombine;
	}

	public String getZxShopName() {
		return zxShopName;
	}

	public void setZxShopName(String zxShopName) {
		this.zxShopName = zxShopName;
	}

	public String getYdShopName() {
		return ydShopName;
	}

	public void setYdShopName(String ydShopName) {
		this.ydShopName = ydShopName;
	}

	
}
