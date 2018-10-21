package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.Date;

import com.bt.lmis.page.QueryParameter;

public class WarehouseExpressDataSettlementC extends QueryParameter {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1726800840062829076L;

	private String id;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String costCenter;

    private String storeCode;

    private String storeName;

    private String warehouse;

    private String transportCode;

    private String transportName;

    private String deliveryOrder;

    private String epistaticOrder;

    private String orderType;

    private String expressNumber;

    private String transportDirection;

    private String itemtypeCode;

    private String itemtypeName;

    private Date transportTime;

    private BigDecimal collectionOnDelivery;

    private BigDecimal orderAmount;

    private String skuNumber;

    private BigDecimal weight;

    private BigDecimal accountWeight;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal higth;

    private BigDecimal volumn;

    private String deliveryAddress;

    private String province;

    private String city;

    private String state;

    private String street;

    private Boolean insuranceFlag;

    private Boolean codFlag;

    private String balanceSubject;

    private BigDecimal jpNum;

    private BigDecimal volumnWeight;

    private BigDecimal volumnAccountWeight;

    private BigDecimal jfWeight;

    private BigDecimal chargedWeight;

    private BigDecimal firstWeight;

    private BigDecimal firstWeightPrice;

    private BigDecimal addedWeight;

    private BigDecimal addedWeightPrice;

    private BigDecimal standardFreight;

    private BigDecimal discount;

    private BigDecimal afterdiscountFreight;

    private BigDecimal insuranceFee;

    private BigDecimal cod;

    private BigDecimal delegatedPickup;

    private BigDecimal extendProp1;

    private BigDecimal extendProp2;

    private BigDecimal totalFee;

    private String remark;
    private String startTime;
    private String endTime;
    private String contractName;

    private Integer dataId;

    private Integer contractId;

    private Integer dflag;
    
    private String errorType;

    public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter == null ? null : costCenter.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse == null ? null : warehouse.trim();
    }

    public String getTransportCode() {
        return transportCode;
    }

    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode == null ? null : transportCode.trim();
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName == null ? null : transportName.trim();
    }

    public String getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(String deliveryOrder) {
        this.deliveryOrder = deliveryOrder == null ? null : deliveryOrder.trim();
    }

    public String getEpistaticOrder() {
        return epistaticOrder;
    }

    public void setEpistaticOrder(String epistaticOrder) {
        this.epistaticOrder = epistaticOrder == null ? null : epistaticOrder.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber == null ? null : expressNumber.trim();
    }

    public String getTransportDirection() {
        return transportDirection;
    }

    public void setTransportDirection(String transportDirection) {
        this.transportDirection = transportDirection == null ? null : transportDirection.trim();
    }

    public String getItemtypeCode() {
        return itemtypeCode;
    }

    public void setItemtypeCode(String itemtypeCode) {
        this.itemtypeCode = itemtypeCode == null ? null : itemtypeCode.trim();
    }

    public String getItemtypeName() {
        return itemtypeName;
    }

    public void setItemtypeName(String itemtypeName) {
        this.itemtypeName = itemtypeName == null ? null : itemtypeName.trim();
    }

    public Date getTransportTime() {
        return transportTime;
    }

    public void setTransportTime(Date transportTime) {
        this.transportTime = transportTime;
    }

    public BigDecimal getCollectionOnDelivery() {
        return collectionOnDelivery;
    }

    public void setCollectionOnDelivery(BigDecimal collectionOnDelivery) {
        this.collectionOnDelivery = collectionOnDelivery;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber == null ? null : skuNumber.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getAccountWeight() {
        return accountWeight;
    }

    public void setAccountWeight(BigDecimal accountWeight) {
        this.accountWeight = accountWeight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHigth() {
        return higth;
    }

    public void setHigth(BigDecimal higth) {
        this.higth = higth;
    }

    public BigDecimal getVolumn() {
        return volumn;
    }

    public void setVolumn(BigDecimal volumn) {
        this.volumn = volumn;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public Boolean getInsuranceFlag() {
        return insuranceFlag;
    }

    public void setInsuranceFlag(Boolean insuranceFlag) {
        this.insuranceFlag = insuranceFlag;
    }

    public Boolean getCodFlag() {
        return codFlag;
    }

    public void setCodFlag(Boolean codFlag) {
        this.codFlag = codFlag;
    }

    public String getBalanceSubject() {
        return balanceSubject;
    }

    public void setBalanceSubject(String balanceSubject) {
        this.balanceSubject = balanceSubject == null ? null : balanceSubject.trim();
    }

    public BigDecimal getJpNum() {
        return jpNum;
    }

    public void setJpNum(BigDecimal jpNum) {
        this.jpNum = jpNum;
    }

    public BigDecimal getVolumnWeight() {
        return volumnWeight;
    }

    public void setVolumnWeight(BigDecimal volumnWeight) {
        this.volumnWeight = volumnWeight;
    }

    public BigDecimal getVolumnAccountWeight() {
        return volumnAccountWeight;
    }

    public void setVolumnAccountWeight(BigDecimal volumnAccountWeight) {
        this.volumnAccountWeight = volumnAccountWeight;
    }

    public BigDecimal getJfWeight() {
        return jfWeight;
    }

    public void setJfWeight(BigDecimal jfWeight) {
        this.jfWeight = jfWeight;
    }

    public BigDecimal getChargedWeight() {
        return chargedWeight;
    }

    public void setChargedWeight(BigDecimal chargedWeight) {
        this.chargedWeight = chargedWeight;
    }

    public BigDecimal getFirstWeight() {
        return firstWeight;
    }

    public void setFirstWeight(BigDecimal firstWeight) {
        this.firstWeight = firstWeight;
    }

    public BigDecimal getFirstWeightPrice() {
        return firstWeightPrice;
    }

    public void setFirstWeightPrice(BigDecimal firstWeightPrice) {
        this.firstWeightPrice = firstWeightPrice;
    }

    public BigDecimal getAddedWeight() {
        return addedWeight;
    }

    public void setAddedWeight(BigDecimal addedWeight) {
        this.addedWeight = addedWeight;
    }

    public BigDecimal getAddedWeightPrice() {
        return addedWeightPrice;
    }

    public void setAddedWeightPrice(BigDecimal addedWeightPrice) {
        this.addedWeightPrice = addedWeightPrice;
    }

    public BigDecimal getStandardFreight() {
        return standardFreight;
    }

    public void setStandardFreight(BigDecimal standardFreight) {
        this.standardFreight = standardFreight;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAfterdiscountFreight() {
        return afterdiscountFreight;
    }

    public void setAfterdiscountFreight(BigDecimal afterdiscountFreight) {
        this.afterdiscountFreight = afterdiscountFreight;
    }

    public BigDecimal getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(BigDecimal insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public BigDecimal getCod() {
        return cod;
    }

    public void setCod(BigDecimal cod) {
        this.cod = cod;
    }

    public BigDecimal getDelegatedPickup() {
        return delegatedPickup;
    }

    public void setDelegatedPickup(BigDecimal delegatedPickup) {
        this.delegatedPickup = delegatedPickup;
    }

    public BigDecimal getExtendProp1() {
        return extendProp1;
    }

    public void setExtendProp1(BigDecimal extendProp1) {
        this.extendProp1 = extendProp1;
    }

    public BigDecimal getExtendProp2() {
        return extendProp2;
    }

    public void setExtendProp2(BigDecimal extendProp2) {
        this.extendProp2 = extendProp2;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }


	public WarehouseExpressDataSettlementC(String id, Date createTime, String createUser, Date updateTime,
			String updateUser, String costCenter, String storeCode, String storeName, String warehouse,
			String transportCode, String transportName, String deliveryOrder, String epistaticOrder, String orderType,
			String expressNumber, String transportDirection, String itemtypeCode, String itemtypeName,
			Date transportTime, BigDecimal collectionOnDelivery, BigDecimal orderAmount, String skuNumber,
			BigDecimal weight, BigDecimal accountWeight, BigDecimal length, BigDecimal width, BigDecimal higth,
			BigDecimal volumn, String deliveryAddress, String province, String city, String state, String street,
			Boolean insuranceFlag, Boolean codFlag, String balanceSubject, BigDecimal jpNum, BigDecimal volumnWeight,
			BigDecimal volumnAccountWeight, BigDecimal jfWeight, BigDecimal chargedWeight, BigDecimal firstWeight,
			BigDecimal firstWeightPrice, BigDecimal addedWeight, BigDecimal addedWeightPrice,
			BigDecimal standardFreight, BigDecimal discount, BigDecimal afterdiscountFreight, BigDecimal insuranceFee,
			BigDecimal cod, BigDecimal delegatedPickup, BigDecimal extendProp1, BigDecimal extendProp2,
			BigDecimal totalFee, String remark, String startTime, String endTime, Integer dataId, Integer contractId,
			Integer dflag, String errorType) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.costCenter = costCenter;
		this.storeCode = storeCode;
		this.storeName = storeName;
		this.warehouse = warehouse;
		this.transportCode = transportCode;
		this.transportName = transportName;
		this.deliveryOrder = deliveryOrder;
		this.epistaticOrder = epistaticOrder;
		this.orderType = orderType;
		this.expressNumber = expressNumber;
		this.transportDirection = transportDirection;
		this.itemtypeCode = itemtypeCode;
		this.itemtypeName = itemtypeName;
		this.transportTime = transportTime;
		this.collectionOnDelivery = collectionOnDelivery;
		this.orderAmount = orderAmount;
		this.skuNumber = skuNumber;
		this.weight = weight;
		this.accountWeight = accountWeight;
		this.length = length;
		this.width = width;
		this.higth = higth;
		this.volumn = volumn;
		this.deliveryAddress = deliveryAddress;
		this.province = province;
		this.city = city;
		this.state = state;
		this.street = street;
		this.insuranceFlag = insuranceFlag;
		this.codFlag = codFlag;
		this.balanceSubject = balanceSubject;
		this.jpNum = jpNum;
		this.volumnWeight = volumnWeight;
		this.volumnAccountWeight = volumnAccountWeight;
		this.jfWeight = jfWeight;
		this.chargedWeight = chargedWeight;
		this.firstWeight = firstWeight;
		this.firstWeightPrice = firstWeightPrice;
		this.addedWeight = addedWeight;
		this.addedWeightPrice = addedWeightPrice;
		this.standardFreight = standardFreight;
		this.discount = discount;
		this.afterdiscountFreight = afterdiscountFreight;
		this.insuranceFee = insuranceFee;
		this.cod = cod;
		this.delegatedPickup = delegatedPickup;
		this.extendProp1 = extendProp1;
		this.extendProp2 = extendProp2;
		this.totalFee = totalFee;
		this.remark = remark;
		this.startTime = startTime;
		this.endTime = endTime;
		this.dataId = dataId;
		this.contractId = contractId;
		this.dflag = dflag;
		this.errorType = errorType;
	}

	public WarehouseExpressDataSettlementC() {
		super();
	}

	@Override
	public String toString() {
		return "WarehouseExpressDataSettlementC [id=" + id + ", createTime=" + createTime + ", createUser=" + createUser
				+ ", updateTime=" + updateTime + ", updateUser=" + updateUser + ", costCenter=" + costCenter
				+ ", storeCode=" + storeCode + ", storeName=" + storeName + ", warehouse=" + warehouse
				+ ", transportCode=" + transportCode + ", transportName=" + transportName + ", deliveryOrder="
				+ deliveryOrder + ", epistaticOrder=" + epistaticOrder + ", orderType=" + orderType + ", expressNumber="
				+ expressNumber + ", transportDirection=" + transportDirection + ", itemtypeCode=" + itemtypeCode
				+ ", itemtypeName=" + itemtypeName + ", transportTime=" + transportTime + ", collectionOnDelivery="
				+ collectionOnDelivery + ", orderAmount=" + orderAmount + ", skuNumber=" + skuNumber + ", weight="
				+ weight + ", accountWeight=" + accountWeight + ", length=" + length + ", width=" + width + ", higth="
				+ higth + ", volumn=" + volumn + ", deliveryAddress=" + deliveryAddress + ", province=" + province
				+ ", city=" + city + ", state=" + state + ", street=" + street + ", insuranceFlag=" + insuranceFlag
				+ ", codFlag=" + codFlag + ", balanceSubject=" + balanceSubject + ", jpNum=" + jpNum + ", volumnWeight="
				+ volumnWeight + ", volumnAccountWeight=" + volumnAccountWeight + ", jfWeight=" + jfWeight
				+ ", chargedWeight=" + chargedWeight + ", firstWeight=" + firstWeight + ", firstWeightPrice="
				+ firstWeightPrice + ", addedWeight=" + addedWeight + ", addedWeightPrice=" + addedWeightPrice
				+ ", standardFreight=" + standardFreight + ", discount=" + discount + ", afterdiscountFreight="
				+ afterdiscountFreight + ", insuranceFee=" + insuranceFee + ", cod=" + cod + ", delegatedPickup="
				+ delegatedPickup + ", extendProp1=" + extendProp1 + ", extendProp2=" + extendProp2 + ", totalFee="
				+ totalFee + ", remark=" + remark + ", startTime=" + startTime + ", endTime=" + endTime + ", dataId="
				+ dataId + ", contractId=" + contractId + ", dflag=" + dflag + ", errorType=" + errorType + "]";
	}

	
    
}