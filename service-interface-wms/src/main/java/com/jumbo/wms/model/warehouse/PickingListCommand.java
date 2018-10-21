/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jumbo.util.StringUtils;

/**
 * 
 * @author jumbo
 * 
 */
public class PickingListCommand extends PickingList {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2724556516803505854L;

    private Long excelIndex;

    /**
     * sta id
     */
    private Long staId;
    /**
     * stvline
     */
    private Integer quantity;
    /**
     * sku id
     */
    private Long skuId;

    /**
     * SKucode
     */
    private String skuCode;


    private String color;

    private String skuSize;

    private String unitPrice;

    /**
     * sku code
     */
    private String jmskucode;
    /**
     * 所属店铺
     */
    private String owner;
    /**
     * 库位
     */
    private String locationcode;
    /**
     * 商品条码
     */
    private String barcode;
    /**
     * jmCode
     */
    private String jmcode;
    /**
     * 供应商编码
     */
    private String supplierCode;

    private String orderDate;
    /**
     * SKU 名称
     */
    private String skuname;
    /**
     * SKU 扩展属性
     */
    private String keyproperties;

    /**
     * 物流快递公司名称
     */
    private String deliveryCompany;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 联系人
     */
    private String contact;

    private String telephone;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 收件人地址
     */
    private String receiveAddress;

    /**
     * 省份
     */
    private String province;

    /**
     * 省份
     */
    private String city;

    /**
     * 要求收获时间
     */
    private String receiveTime;

    /**
     * 退换货仓库地址名称
     */
    private String returnWarehouseName;
    /**
     * 订单货款
     */
    private BigDecimal totalActual;
    /**
     * 物流运费
     */
    private BigDecimal transferFee;
    /**
     * 订单金额(订单货款+物流运费)
     */
    private BigDecimal orderSum;

    /**
     * 已发货作业单数
     */
    private BigDecimal sendStaQty;

    /**
     * 已发货商品数
     */
    private BigDecimal sendSkuQty;

    /**
     * 最后发货时间
     */
    private Date lastSendTime;
    /**
     * 创建人
     */
    private String crtUserName;
    /**
     * 修改人
     */
    private String operUserName;

    /**
     * 配货模式
     */
    private Integer pkModeInt;

    private String country; // 国家
    private String district;// 区
    private String address;// 送货地址
    private String payType;// 支付方式

    /**
     * 作业单
     */
    private String staCode;

    /**
     * 状态
     */
    private Integer statusInt;
    /**
     * 配货清单状态
     */
    private String statusc;
    /**
     * 拣货批状态
     */
    private String nodeType;

    /**
     * 是否EMS电子面单
     */
    private Boolean isEmsOlOrder;

    private Boolean isSpecialPackaging;

    private String strExpireDate;

    private String jobNumber; // 工号
    /**
     * 批次包裹id
     */
    private Long pickingListPackageId;

    private Integer transTypeInt;

    private Integer transTimeTypeInt;

    private Integer isPPP;

    private Integer isPEB;

    private Integer isInvoiceInt;

    private Integer specialTypeInt;

    private Integer isBkCheckInteger;

    private Integer packageCount;

    /**
     * 拣货区域code
     */
    private String pickZoneCode;
    private Integer pickZoneId;

    private String whZone;

    private String whZone1;

    /**
     * 拣货开始时间
     */
    private Date startTime;



    /**
     * 自动化二级批次号
     */
    private String batchIndex;
    /**
     * 多条码
     */
    private String otherBarcode;

    private Date expireDate;


    private String agv1;
    private String agv2;
    private String agv3;
    private String agv4;
    private String agv5;
    private String agv6;
    private String agv7;
    private String agv8;
    private String agv9;
    private String agv10;
    private String agv11;


    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getAgv1() {
        return agv1;
    }

    public void setAgv1(String agv1) {
        this.agv1 = agv1;
    }

    public String getAgv2() {
        return agv2;
    }

    public void setAgv2(String agv2) {
        this.agv2 = agv2;
    }

    public String getAgv3() {
        return agv3;
    }

    public void setAgv3(String agv3) {
        this.agv3 = agv3;
    }

    public String getAgv4() {
        return agv4;
    }

    public void setAgv4(String agv4) {
        this.agv4 = agv4;
    }

    public String getAgv5() {
        return agv5;
    }

    public void setAgv5(String agv5) {
        this.agv5 = agv5;
    }

    public String getAgv6() {
        return agv6;
    }

    public void setAgv6(String agv6) {
        this.agv6 = agv6;
    }

    public String getAgv7() {
        return agv7;
    }

    public void setAgv7(String agv7) {
        this.agv7 = agv7;
    }

    public String getAgv8() {
        return agv8;
    }

    public void setAgv8(String agv8) {
        this.agv8 = agv8;
    }

    public String getAgv9() {
        return agv9;
    }

    public void setAgv9(String agv9) {
        this.agv9 = agv9;
    }

    public String getAgv10() {
        return agv10;
    }

    public void setAgv10(String agv10) {
        this.agv10 = agv10;
    }

    public String getAgv11() {
        return agv11;
    }

    public void setAgv11(String agv11) {
        this.agv11 = agv11;
    }

    public Integer getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Integer packageCount) {
        this.packageCount = packageCount;
    }

    public Integer getSpecialTypeInt() {
        return specialTypeInt;
    }

    public void setSpecialTypeInt(Integer specialTypeInt) {
        this.specialTypeInt = specialTypeInt;
    }

    public Integer getIsInvoiceInt() {
        return isInvoiceInt;
    }

    public void setIsInvoiceInt(Integer isInvoiceInt) {
        this.isInvoiceInt = isInvoiceInt;
    }

    public Integer getIsPEB() {
        return isPEB;
    }

    public void setIsPEB(Integer isPEB) {
        this.isPEB = isPEB;
    }

    public Integer getIsPPP() {
        return isPPP;
    }

    public void setIsPPP(Integer isPPP) {
        this.isPPP = isPPP;
    }

    public Integer getTransTimeTypeInt() {
        return transTimeTypeInt;
    }

    public void setTransTimeTypeInt(Integer transTimeTypeInt) {
        this.transTimeTypeInt = transTimeTypeInt;
    }

    public Integer getTransTypeInt() {
        return transTypeInt;
    }

    public void setTransTypeInt(Integer transTypeInt) {
        this.transTypeInt = transTypeInt;
    }

    public String getStatusc() {
        return statusc;
    }

    public void setStatusc(String statusc) {
        this.statusc = statusc;
    }

    public Integer getPickZoneId() {
        return pickZoneId;
    }

    public void setPickZoneId(Integer pickZoneId) {
        this.pickZoneId = pickZoneId;
    }

    /**
     * 配货模式
     */
    private Integer intCheckMode;

    /**
     * 前置单据号
     */
    private String slipCode;

    /**
     * 物流
     */
    private String lpcode;

    /**
     * 备注
     */
    private String memo;

    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 快递单号
     */
    private String tbCode;

    /**
     * 临时数据
     */
    private String temp;

    /**
     * 付款时间
     */
    private String paymentTime;
    /**
     * 仓库名称
     */
    private String wname;

    /**
     * 自动化1级批次
     */
    private String idx1;

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    private Long whid;

    private Integer whStatusInt;

    private Integer whTypeInt;

    public Integer getWhTypeInt() {
        return whTypeInt;
    }

    public void setWhTypeInt(Integer whTypeInt) {
        this.whTypeInt = whTypeInt;
    }

    public Integer getWhStatusInt() {
        return whStatusInt;
    }

    public void setWhStatusInt(Integer whStatusInt) {
        this.whStatusInt = whStatusInt;
    }

    public Long getWhid() {
        return whid;
    }

    public void setWhid(Long whid) {
        this.whid = whid;
    }

    /**
     * 仓库ID
     */
    private String wid;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    /**
     * 状态名字
     */
    private String statusName;
    /*
     * 新增String类型日期辅助实现时间精确到时分秒
     */
    private String pickingTime1;
    private String executedTime1;
    private String createTime1;

    /**
     * 拣货模式 查询用
     */
    private Integer sortingModeInt;
    /**
     * 物流交接单号
     */
    private Long handId;
    private Integer intStatus;
    /**
     * 是否存在取消的作业单
     */
    private Boolean isHaveCancel;
    /**
     * sn号字符串 多个中间","隔开 仓库批量出库用
     */
    private String sns;

    /**
     * 核对量
     */
    private Integer checkQuantity;

    /**
     * 是否SN商品
     */
    private Integer isSnSku;

    private Date executionTime; // 配货时间

    private String staType;

    public String getStaType() {
        return staType;
    }

    public void setStaType(String staType) {
        this.staType = staType;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getSortingModeInt() {
        return sortingModeInt;
    }

    public void setSortingModeInt(Integer sortingModeInt) {
        this.sortingModeInt = sortingModeInt;
    }

    public Integer getIntCheckMode() {
        return intCheckMode;
    }

    public void setIntCheckMode(Integer intCheckMode) {
        this.intCheckMode = intCheckMode;
    }

    public Integer getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(Integer statusInt) {
        this.statusInt = statusInt;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getCrtUserName() {
        return crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getJmcode() {
        return jmcode;
    }

    public void setJmcode(String jmcode) {
        this.jmcode = jmcode;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public String getKeyproperties() {
        return keyproperties;
    }

    public void setKeyproperties(String keyproperties) {
        this.keyproperties = keyproperties;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReturnWarehouseName() {
        return returnWarehouseName;
    }

    public void setReturnWarehouseName(String returnWarehouseName) {
        this.returnWarehouseName = returnWarehouseName;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public void setOrderSum(BigDecimal orderSum) {
        this.orderSum = orderSum;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public BigDecimal getOrderSum() {
        return orderSum;
    }

    // 核对配货清单字段
    /**
     * 已发货单据数
     */
    private Long shipStaCount;
    /**
     * 已发货商品数
     */
    private Long shipSkuQty;

    public Long getShipStaCount() {
        return shipStaCount;
    }

    public void setShipStaCount(Long shipStaCount) {
        this.shipStaCount = shipStaCount;
    }

    public Long getShipSkuQty() {
        return shipSkuQty;
    }

    public void setShipSkuQty(Long shipSkuQty) {
        this.shipSkuQty = shipSkuQty;
    }

    public Map<String, Object> toVerifyMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (getWarehouse() != null) {
            map.put("ouId", getWarehouse().getId());
        }
        if (getCode() != null && StringUtils.hasLength(getCode())) {
            map.put("code", getCode() + "%");
        }
        if (getIntStatus() > 0) {
            map.put("status", getIntStatus());
        }
        if (getPickingTime() != null) {
            map.put("fromTime", getPickingTime());
        }
        if (getExecutedTime() != null) {
            map.put("toTime", getExecutedTime());
        }
        if (getPickingMode() != null) {
            map.put("pickingMode", getPickingMode().getValue());
        }
        if (getIntCheckMode() != null) {
            map.put("checkMode", getIntCheckMode());
        }
        // 拣货模式
        if (getSortingModeInt() != null) {
            map.put("sortingMode", getSortingModeInt());
        }
        if (getIsSpecialPackaging() != null && getIsSpecialPackaging()) {
            map.put("isSpecialPackaging", getIsSpecialPackaging());
        }
        return map;
    }

    public BigDecimal getSendStaQty() {
        return sendStaQty;
    }

    public void setSendStaQty(BigDecimal sendStaQty) {
        this.sendStaQty = sendStaQty;
    }

    public BigDecimal getSendSkuQty() {
        return sendSkuQty;
    }

    public void setSendSkuQty(BigDecimal sendSkuQty) {
        this.sendSkuQty = sendSkuQty;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getJmskucode() {
        return jmskucode;
    }

    public void setJmskucode(String jmskucode) {
        this.jmskucode = jmskucode;
    }

    public Integer getPkModeInt() {
        return pkModeInt;
    }

    public void setPkModeInt(Integer pkModeInt) {
        this.pkModeInt = pkModeInt;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    @Override
    public String getCity() {
        return city;
    }
    @Override
    public void setCity(String city) {
        this.city = city;
    }
    @Override
    public String getLpcode() {
        return lpcode;
    }
    @Override
    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Long getExcelIndex() {
        return excelIndex;
    }

    public void setExcelIndex(Long excelIndex) {
        this.excelIndex = excelIndex;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPickingTime1() {
        return pickingTime1;
    }

    public void setPickingTime1(String pickingTime1) {
        this.pickingTime1 = pickingTime1;
    }

    public String getExecutedTime1() {
        return executedTime1;
    }

    public void setExecutedTime1(String executedTime1) {
        this.executedTime1 = executedTime1;
    }

    public String getCreateTime1() {
        return createTime1;
    }

    public void setCreateTime1(String createTime1) {
        this.createTime1 = createTime1;
    }

    public Long getHandId() {
        return handId;
    }

    public void setHandId(Long handId) {
        this.handId = handId;
    }

    @Override
    public Integer getIntStatus() {
        return intStatus == null ? -1 : intStatus;
    }

    @Override
    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public Boolean getIsHaveCancel() {
        return isHaveCancel;
    }

    public void setIsHaveCancel(Boolean isHaveCancel) {
        this.isHaveCancel = isHaveCancel;
    }

    public Boolean getIsEmsOlOrder() {
        return isEmsOlOrder;
    }

    public void setIsEmsOlOrder(Boolean isEmsOlOrder) {
        this.isEmsOlOrder = isEmsOlOrder;
    }

    public String getSns() {
        return sns;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }
    @Override
    public Boolean getIsSpecialPackaging() {
        return isSpecialPackaging;
    }
    @Override
    public void setIsSpecialPackaging(Boolean isSpecialPackaging) {
        this.isSpecialPackaging = isSpecialPackaging;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStrExpireDate() {
        return strExpireDate;
    }

    public void setStrExpireDate(String strExpireDate) {
        this.strExpireDate = strExpireDate;
    }

    public Integer getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(Integer checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public Integer getIsSnSku() {
        return isSnSku;
    }

    public void setIsSnSku(Integer isSnSku) {
        this.isSnSku = isSnSku;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Long getPickingListPackageId() {
        return pickingListPackageId;
    }

    public void setPickingListPackageId(Long pickingListPackageId) {
        this.pickingListPackageId = pickingListPackageId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getIsBkCheckInteger() {
        return isBkCheckInteger;
    }

    public void setIsBkCheckInteger(Integer isBkCheckInteger) {
        this.isBkCheckInteger = isBkCheckInteger;
    }

    public String getPickZoneCode() {
        return pickZoneCode;
    }

    public void setPickZoneCode(String pickZoneCode) {
        this.pickZoneCode = pickZoneCode;
    }

    public String getWhZone() {
        return whZone;
    }

    public void setWhZone(String whZone) {
        this.whZone = whZone;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getBatchIndex() {
        return batchIndex;
    }

    public void setBatchIndex(String batchIndex) {
        this.batchIndex = batchIndex;
    }

    public String getIdx1() {
        return idx1;
    }

    public void setIdx1(String idx1) {
        this.idx1 = idx1;
    }

	public String getOtherBarcode() {
		return otherBarcode;
	}

	public void setOtherBarcode(String otherBarcode) {
		this.otherBarcode = otherBarcode;
	}

    public String getWhZone1() {
        return whZone1;
    }

    public void setWhZone1(String whZone1) {
        this.whZone1 = whZone1;
    }



}
