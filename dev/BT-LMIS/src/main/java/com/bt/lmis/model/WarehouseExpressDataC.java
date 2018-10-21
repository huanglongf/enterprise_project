package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.bt.lmis.page.QueryParameter;

public class WarehouseExpressDataC extends QueryParameter{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4106367682507450022L;

	private Integer id;

    private String batId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String costCenter;

    private String storeCode;

    private String storeName;

    private String warehouseCode;

    private String warehouse;

    private String transportCode;

    private String transportName;

    private String deliveryOrder;

    private String epistaticOrder;

    private String orderType;

    private String transportDirection;

    private String itemtypeCode;

    private String itemtypeName;

    private Date transportTime;

    private BigDecimal collectionOnDelivery;

    private BigDecimal orderAmount;

    private String skuNumber;

    private BigDecimal weight;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal higth;

    private BigDecimal volumn;

    private String deliveryAddress;

    private String province;

    private String city;

    private String state;

    private String street;

    private String detailAddress;

    private String shiptoname;

    private String phone;

    private Boolean insuranceFlag;

    private Boolean codFlag;

    private String balanceSubject;

    private Integer settleFlag;

    private Integer settleLogisticsFlag;

    private Integer settleClientFlag;

    private Integer settleStoreFlag;

    private Integer packingChargeFlag;

    private Integer dflag;

    private Integer operatingFlag;

    private Integer qty;

    private String hcfSettleFlag;

    private Integer offerId;

    private String expressNumber;
    
    private String errorType;
    private String startTime;
    private String endTime;
    
    
    
    

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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatId() {
        return batId;
    }

    public void setBatId(String batId) {
        this.batId = batId == null ? null : batId.trim();
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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    public String getShiptoname() {
        return shiptoname;
    }

    public void setShiptoname(String shiptoname) {
        this.shiptoname = shiptoname == null ? null : shiptoname.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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

    public Integer getSettleFlag() {
        return settleFlag;
    }

    public void setSettleFlag(Integer settleFlag) {
        this.settleFlag = settleFlag;
    }

    public Integer getSettleLogisticsFlag() {
        return settleLogisticsFlag;
    }

    public void setSettleLogisticsFlag(Integer settleLogisticsFlag) {
        this.settleLogisticsFlag = settleLogisticsFlag;
    }

    public Integer getSettleClientFlag() {
        return settleClientFlag;
    }

    public void setSettleClientFlag(Integer settleClientFlag) {
        this.settleClientFlag = settleClientFlag;
    }

    public Integer getSettleStoreFlag() {
        return settleStoreFlag;
    }

    public void setSettleStoreFlag(Integer settleStoreFlag) {
        this.settleStoreFlag = settleStoreFlag;
    }

    public Integer getPackingChargeFlag() {
        return packingChargeFlag;
    }

    public void setPackingChargeFlag(Integer packingChargeFlag) {
        this.packingChargeFlag = packingChargeFlag;
    }

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }

    public Integer getOperatingFlag() {
        return operatingFlag;
    }

    public void setOperatingFlag(Integer operatingFlag) {
        this.operatingFlag = operatingFlag;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getHcfSettleFlag() {
        return hcfSettleFlag;
    }

    public void setHcfSettleFlag(String hcfSettleFlag) {
        this.hcfSettleFlag = hcfSettleFlag == null ? null : hcfSettleFlag.trim();
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

	public WarehouseExpressDataC(Integer id, String batId, Date createTime, String createUser, Date updateTime,
			String updateUser, String costCenter, String storeCode, String storeName, String warehouseCode,
			String warehouse, String transportCode, String transportName, String deliveryOrder, String epistaticOrder,
			String orderType, String transportDirection, String itemtypeCode, String itemtypeName, Date transportTime,
			BigDecimal collectionOnDelivery, BigDecimal orderAmount, String skuNumber, BigDecimal weight,
			BigDecimal length, BigDecimal width, BigDecimal higth, BigDecimal volumn, String deliveryAddress,
			String province, String city, String state, String street, String detailAddress, String shiptoname,
			String phone, Boolean insuranceFlag, Boolean codFlag, String balanceSubject, Integer settleFlag,
			Integer settleLogisticsFlag, Integer settleClientFlag, Integer settleStoreFlag, Integer packingChargeFlag,
			Integer dflag, Integer operatingFlag, Integer qty, String hcfSettleFlag, Integer offerId,
			String expressNumber, String errorType, String startTime, String endTime) {
		super();
		this.id = id;
		this.batId = batId;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.costCenter = costCenter;
		this.storeCode = storeCode;
		this.storeName = storeName;
		this.warehouseCode = warehouseCode;
		this.warehouse = warehouse;
		this.transportCode = transportCode;
		this.transportName = transportName;
		this.deliveryOrder = deliveryOrder;
		this.epistaticOrder = epistaticOrder;
		this.orderType = orderType;
		this.transportDirection = transportDirection;
		this.itemtypeCode = itemtypeCode;
		this.itemtypeName = itemtypeName;
		this.transportTime = transportTime;
		this.collectionOnDelivery = collectionOnDelivery;
		this.orderAmount = orderAmount;
		this.skuNumber = skuNumber;
		this.weight = weight;
		this.length = length;
		this.width = width;
		this.higth = higth;
		this.volumn = volumn;
		this.deliveryAddress = deliveryAddress;
		this.province = province;
		this.city = city;
		this.state = state;
		this.street = street;
		this.detailAddress = detailAddress;
		this.shiptoname = shiptoname;
		this.phone = phone;
		this.insuranceFlag = insuranceFlag;
		this.codFlag = codFlag;
		this.balanceSubject = balanceSubject;
		this.settleFlag = settleFlag;
		this.settleLogisticsFlag = settleLogisticsFlag;
		this.settleClientFlag = settleClientFlag;
		this.settleStoreFlag = settleStoreFlag;
		this.packingChargeFlag = packingChargeFlag;
		this.dflag = dflag;
		this.operatingFlag = operatingFlag;
		this.qty = qty;
		this.hcfSettleFlag = hcfSettleFlag;
		this.offerId = offerId;
		this.expressNumber = expressNumber;
		this.errorType = errorType;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public WarehouseExpressDataC() {
		super();
	}

	@Override
	public String toString() {
		return "WarehouseExpressDataC [id=" + id + ", batId=" + batId + ", createTime=" + createTime + ", createUser="
				+ createUser + ", updateTime=" + updateTime + ", updateUser=" + updateUser + ", costCenter="
				+ costCenter + ", storeCode=" + storeCode + ", storeName=" + storeName + ", warehouseCode="
				+ warehouseCode + ", warehouse=" + warehouse + ", transportCode=" + transportCode + ", transportName="
				+ transportName + ", deliveryOrder=" + deliveryOrder + ", epistaticOrder=" + epistaticOrder
				+ ", orderType=" + orderType + ", transportDirection=" + transportDirection + ", itemtypeCode="
				+ itemtypeCode + ", itemtypeName=" + itemtypeName + ", transportTime=" + transportTime
				+ ", collectionOnDelivery=" + collectionOnDelivery + ", orderAmount=" + orderAmount + ", skuNumber="
				+ skuNumber + ", weight=" + weight + ", length=" + length + ", width=" + width + ", higth=" + higth
				+ ", volumn=" + volumn + ", deliveryAddress=" + deliveryAddress + ", province=" + province + ", city="
				+ city + ", state=" + state + ", street=" + street + ", detailAddress=" + detailAddress
				+ ", shiptoname=" + shiptoname + ", phone=" + phone + ", insuranceFlag=" + insuranceFlag + ", codFlag="
				+ codFlag + ", balanceSubject=" + balanceSubject + ", settleFlag=" + settleFlag
				+ ", settleLogisticsFlag=" + settleLogisticsFlag + ", settleClientFlag=" + settleClientFlag
				+ ", settleStoreFlag=" + settleStoreFlag + ", packingChargeFlag=" + packingChargeFlag + ", dflag="
				+ dflag + ", operatingFlag=" + operatingFlag + ", qty=" + qty + ", hcfSettleFlag=" + hcfSettleFlag
				+ ", offerId=" + offerId + ", expressNumber=" + expressNumber + ", errorType="
				+ errorType + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
    
    
}