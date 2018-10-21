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

/**
 * 物流面单信息
 * 
 * @author jumbo
 * 
 */
public class StaDeliveryInfoCommand extends StaDeliveryInfo {
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        StaDeliveryInfoCommand other = (StaDeliveryInfoCommand) obj;
        if (this.getId() == null) {
            if (other.getId() != null) {return false;}
        } else if (!this.getId().equals(other.getId())) {return false;}
        return true;
    }

    /**
     * 箱号
     */
    private String pgindex;
    /**
     * 批次中包含的sta数
     */
    private Integer count;
    /**
	 * 
	 */
    private static final long serialVersionUID = 8776071616029522720L;
    /**
     * 物流供应商名称
     */
    private String expName;

    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 订单号
     */
    private String refSlipCode;
    /**
     * 商品数量
     */
    private String quantity;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 公司
     */
    private String company;

    private String ownerproduct;

    private String nullValue;

    private String isSupportCod;
    private Integer transTypeInt;
    private Integer transTimeTypeInt;
    private Integer storeComIsNeedInvoiceInt;
    private Integer isCodInt;
    private Integer isMorePackageInt;
    private Integer isCodPosInt;
    /**
     * 代收货款金额-BigDecimal(SFCOD)
     */
    private BigDecimal amount;
    /**
     * 代收货款金额-String(EMSCOD)
     */
    private String strAmount;
    /**
     * 创建时间
     */
    private Date staCreateTime;
    private String contacts;
    private String ext1; // NIKE指定字段
    private String slipCode2;
    /**
     * 寄件人
     */
    private String sender;
    /**
     * 。。。
     */
    private String senderUnit;
    /**
     * 寄件人地址
     */
    private String senderAddress;
    /**
     * 寄件人电话
     */
    private String senderTel;
    /**
     * 收件人电话
     */
    private String receverTel;
    /**
     * 寄件人手机
     */
    private String senderMobile;
    /**
     * 寄件人邮编
     */
    private String sendZipCode;

    /**
     * 装箱清单-价格是否打印
     */
    private Boolean isNotDisplaySum;
    /**
     * SF固定电话
     */
    private String sfMobile = "4008 111 111";
    /**
     * SF仓库编码
     */
    private String sfWhCode;
    /**
     * 快递LOGO图片路径
     */
    private String logoImg;
    /**
     * SF帐号
     */
    private String jcustid;
    /**
     * SF帐号
     */
    private String payMethod;
    /**
     * 详细地址
     */
    private String disAddr;
    /**
     * 配货清单编码
     */
    private String pickingListCode;
    /**
     * 打印时间
     */
    private String printTime;
    /**
     * 发运地
     */
    private String departure = "上海";
    /**
     * 是否陆运
     */
    private Boolean isRailway;
    /**
     * 大字
     */
    private String bigAddress;
    /**
     * 公司名称
     */
    private String cmpName;
    /**
     * 总单据数
     */
    private Long totalBillQty;
    /**
     * 保价费，统一改用字段insuranceAmount
     */
    private Long totalactual;
    /**
     * 快递类型
     */
    private String transTypeB;
    /**
     * 快递类型名称
     */
    private String transTypeName;

    /**
     * 快递时限类型
     */
    private String transTimeTypeB;
    /**
     * 快递时限类型名称
     */
    private String transTimeTypeName;
    /**
     * 快递时限类型名称
     */
    private String transmemo;

    /**
     * 保价金额,统一改用字段insuranceAmount
     */
    private BigDecimal ordertotalactual;
    /**
     * 订单运费
     */
    private BigDecimal orderTransferFree;
    /**
     * 标识OTO订单
     */
    private String toLocation;
    /**
     * 渠道编码
     */
    private String channelCode;
    private String code;

    private String pic; // 发货人
    private String provinceF;// 发货省
    private String cityF; // 发货市
    private String districtF;// 发货区
    private String warehouseCode; // 仓库编码
    private Long warehouseId;// 仓库ID
    private Long packageId; // 包裹Id

    private String sfOrder;
    private String sfSum;
    private String sfTrackingNo;

    // SFGJ
    private String categoryName;
    private String categoryTotal;
    private String categoryName1;
    private String categoryName2;
    private String categoryName3;

    private String categoryQty1;
    private String categoryQty2;
    private String categoryQty3;

    private String proCode;

    private Boolean isNikePick;

    private String skuCategories;

    private String totalCategories;

    private BigDecimal orderPrice;//订单总金额
    public String getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(String totalCategories) {
        this.totalCategories = totalCategories;
    }

    public String getSkuCategories() {
        return skuCategories;
    }

    public void setSkuCategories(String skuCategories) {
        this.skuCategories = skuCategories;
    }

    public Boolean getIsNikePick() {
        return isNikePick;
    }

    public void setIsNikePick(Boolean isNikePick) {
        this.isNikePick = isNikePick;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * 店铺Id
     */
    private Long channelId;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public BigDecimal getOrderTransferFree() {
        return orderTransferFree;
    }

    public void setOrderTransferFree(BigDecimal orderTransferFree) {
        this.orderTransferFree = orderTransferFree;
    }

    private String barCode;

    private String staQuantity;

    private String skuList;

    private String areaNumber;// 省份编码

    /**
     * 是否POS机刷卡
     */
    private String isTransCodPos;

    private String shipmentCode;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Long getTotalactual() {
        return totalactual;
    }

    public void setTotalactual(Long totalactual) {
        this.totalactual = totalactual;
    }

    public String getPickingListCode() {
        return pickingListCode;
    }

    public void setPickingListCode(String pickingListCode) {
        this.pickingListCode = pickingListCode;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStrAmount() {
        return strAmount;
    }

    public void setStrAmount(String strAmount) {
        this.strAmount = strAmount;
    }

    public String getOwnerproduct() {
        return ownerproduct;
    }

    public void setOwnerproduct(String ownerproduct) {
        this.ownerproduct = ownerproduct;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPgindex() {
        return pgindex;
    }

    public void setPgindex(String pgindex) {
        this.pgindex = pgindex;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getStaCreateTime() {
        return staCreateTime;
    }

    public void setStaCreateTime(Date staCreateTime) {
        this.staCreateTime = staCreateTime;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public String getIsSupportCod() {
        return isSupportCod;
    }

    public void setIsSupportCod(String isSupportCod) {
        this.isSupportCod = isSupportCod;
    }

    public String getJcustid() {
        return jcustid;
    }

    public void setJcustid(String jcustid) {
        this.jcustid = jcustid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderUnit() {
        return senderUnit;
    }

    public void setSenderUnit(String senderUnit) {
        this.senderUnit = senderUnit;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getSendZipCode() {
        return sendZipCode;
    }

    public void setSendZipCode(String sendZipCode) {
        this.sendZipCode = sendZipCode;
    }

    public Boolean getIsNotDisplaySum() {
        return isNotDisplaySum;
    }

    public void setIsNotDisplaySum(Boolean isNotDisplaySum) {
        this.isNotDisplaySum = isNotDisplaySum;
    }

    public String getSfMobile() {
        return sfMobile;
    }

    public void setSfMobile(String sfMobile) {
        this.sfMobile = sfMobile;
    }

    public String getSfWhCode() {
        return sfWhCode;
    }

    public void setSfWhCode(String sfWhCode) {
        this.sfWhCode = sfWhCode;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getDisAddr() {
        return disAddr;
    }

    public void setDisAddr(String disAddr) {
        this.disAddr = disAddr;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean getIsRailway() {
        return isRailway;
    }

    public void setIsRailway(Boolean isRailway) {
        this.isRailway = isRailway;
    }

    public String getBigAddress() {
        return bigAddress;
    }

    public void setBigAddress(String bigAddress) {
        this.bigAddress = bigAddress;
    }

    public String getReceverTel() {
        return receverTel;
    }

    public void setReceverTel(String receverTel) {
        this.receverTel = receverTel;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public Long getTotalBillQty() {
        return totalBillQty;
    }

    public void setTotalBillQty(Long totalBillQty) {
        this.totalBillQty = totalBillQty;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public String getTransTypeB() {
        return transTypeB;
    }

    public void setTransTypeB(String transTypeB) {
        this.transTypeB = transTypeB;
    }

    public String getTransTimeTypeB() {
        return transTimeTypeB;
    }

    public void setTransTimeTypeB(String transTimeTypeB) {
        this.transTimeTypeB = transTimeTypeB;
    }

    public String getTransTimeTypeName() {
        return transTimeTypeName;
    }

    public void setTransTimeTypeName(String transTimeTypeName) {
        this.transTimeTypeName = transTimeTypeName;
    }

    public String getTransmemo() {
        return transmemo;
    }

    public void setTransmemo(String transmemo) {
        this.transmemo = transmemo;
    }

    public BigDecimal getOrdertotalactual() {
        return ordertotalactual;
    }

    public void setOrdertotalactual(BigDecimal ordertotalactual) {
        this.ordertotalactual = ordertotalactual;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getStaQuantity() {
        return staQuantity;
    }

    public void setStaQuantity(String staQuantity) {
        this.staQuantity = staQuantity;
    }

    public String getSkuList() {
        return skuList;
    }

    public void setSkuList(String skuList) {
        this.skuList = skuList;
    }

    public String getIsTransCodPos() {
        return isTransCodPos;
    }

    public void setIsTransCodPos(String isTransCodPos) {
        this.isTransCodPos = isTransCodPos;
    }

    public String getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public Integer getTransTypeInt() {
        return transTypeInt;
    }

    public void setTransTypeInt(Integer transTypeInt) {
        this.transTypeInt = transTypeInt;
    }

    public Integer getTransTimeTypeInt() {
        return transTimeTypeInt;
    }

    public void setTransTimeTypeInt(Integer transTimeTypeInt) {
        this.transTimeTypeInt = transTimeTypeInt;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public Integer getStoreComIsNeedInvoiceInt() {
        return storeComIsNeedInvoiceInt;
    }

    public void setStoreComIsNeedInvoiceInt(Integer storeComIsNeedInvoiceInt) {
        this.storeComIsNeedInvoiceInt = storeComIsNeedInvoiceInt;
    }

    public Integer getIsCodInt() {
        return isCodInt;
    }

    public void setIsCodInt(Integer isCodInt) {
        this.isCodInt = isCodInt;
    }

    public Integer getIsMorePackageInt() {
        return isMorePackageInt;
    }

    public void setIsMorePackageInt(Integer isMorePackageInt) {
        this.isMorePackageInt = isMorePackageInt;
    }

    public Integer getIsCodPosInt() {
        return isCodPosInt;
    }

    public void setIsCodPosInt(Integer isCodPosInt) {
        this.isCodPosInt = isCodPosInt;
    }

    public String getProvinceF() {
        return provinceF;
    }

    public void setProvinceF(String provinceF) {
        this.provinceF = provinceF;
    }

    public String getCityF() {
        return cityF;
    }

    public void setCityF(String cityF) {
        this.cityF = cityF;
    }

    public String getDistrictF() {
        return districtF;
    }

    public void setDistrictF(String districtF) {
        this.districtF = districtF;
    }



    public String getSfTrackingNo() {
        return sfTrackingNo;
    }

    public void setSfTrackingNo(String sfTrackingNo) {
        this.sfTrackingNo = sfTrackingNo;
    }

    public String getSfOrder() {
        return sfOrder;
    }

    public void setSfOrder(String sfOrder) {
        this.sfOrder = sfOrder;
    }

    public String getSfSum() {
        return sfSum;
    }

    public void setSfSum(String sfSum) {
        this.sfSum = sfSum;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getCategoryName1() {
        return categoryName1;
    }

    public void setCategoryName1(String categoryName1) {
        this.categoryName1 = categoryName1;
    }

    public String getCategoryName2() {
        return categoryName2;
    }

    public void setCategoryName2(String categoryName2) {
        this.categoryName2 = categoryName2;
    }

    public String getCategoryName3() {
        return categoryName3;
    }

    public void setCategoryName3(String categoryName3) {
        this.categoryName3 = categoryName3;
    }

    public String getCategoryQty1() {
        return categoryQty1;
    }

    public void setCategoryQty1(String categoryQty1) {
        this.categoryQty1 = categoryQty1;
    }

    public String getCategoryQty2() {
        return categoryQty2;
    }

    public void setCategoryQty2(String categoryQty2) {
        this.categoryQty2 = categoryQty2;
    }

    public String getCategoryQty3() {
        return categoryQty3;
    }

    public void setCategoryQty3(String categoryQty3) {
        this.categoryQty3 = categoryQty3;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryTotal() {
        return categoryTotal;
    }

    public void setCategoryTotal(String categoryTotal) {
        this.categoryTotal = categoryTotal;
    }

    public String getShipmentCode() {
        return shipmentCode;
    }

    public void setShipmentCode(String shipmentCode) {
        this.shipmentCode = shipmentCode;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

}
