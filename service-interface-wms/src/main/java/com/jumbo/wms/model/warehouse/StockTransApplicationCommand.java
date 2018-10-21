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
 * 仓库作业申请单，所有仓库作业最终都表现为此作业单
 * 
 * @author wanghua
 * 
 */
public class StockTransApplicationCommand extends StockTransApplication {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2724556516803505854L;


    private String orderCode;

    private String statusName;

    private Long nikeQty;

    private Long varianceQty;

    private String nfsStoreCode;

    private String divisionCode;

    private String planLastOutboundTime1;

    private String packSlipNo;

    private Long qty;

    private String vmiCode;

    private String outerOrderCode;

    private String soCode;

    private String strType;

    private BigDecimal skuTotalActual;

    private String skuName;

    private String optionValue;

    private String inboundInfo;

    private Long quantity;

    private Long completeQuantity;

    private Long pickingListId;

    private Long staLineId;

    private String isCod;

    private Integer isNeedOccupiedInt;
    private Integer refSlipTypeInt;

    private Integer deliveryTypeInt;
    private Integer pickingTypeInt;
    private Integer vmiRCStatusInt;
    /**
     * 执行情况:1=未执行,2=库存占用,5=部分执行,7=待上架
     */
    private Integer processStatus;

    private Long qty2;
    /**
     * 未收货入库的数量
     */
    private Long remnant;
    /**
     * 已经收货未上架的作业明细单
     */
    private Long stvId;
    /**
     * 1.采购：已经收货未上架的作业明细单的总量 2.销售：计划执行总数量
     */
    private Long stvTotal;
    /**
     * 已经收货未上架的作业明细单的上架模式
     */
    private Long stvMode;
    /**
     * 店铺名
     */
    private String shopId;
    /**
     * 物流商简称
     */
    private String lpcode;
    /**
     * 商品总数
     */
    private Long totalQty;
    /**
     * 是否需要发票
     */
    private Boolean isNeedInvoice;
    /**
     * 是否需要发票（中文）
     */
    private String isNeedInvoicecn;

    /**
     * 配货批次号
     */
    private String pickingCode;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 快递单号
     */
    private String trackingNo;

    private String cartonCode;

    /**
     * 品牌
     */
    private String brand;
    /**
     * 事物方向
     */
    private Integer transaction;
    /**
     * 操作人
     */
    private String operator;
    /**
     * sta状态
     */
    private Integer intStaStatus;
    /**
     * 确认人
     */
    private String receiptor;
    /**
     * 确认数量
     */
    private Long receiptNumber;
    /**
     * 作业类型
     */
    private Integer intStaType;
    /**
     * 作业单商品总数量
     */
    private Long totalSkuQty;
    /**
     * 源仓库
     */
    private String mainName;
    /**
     * 源仓库
     */
    private Long wcenid;
    /**
     * sn
     */
    private String sn;
    /**
     * 特殊处理类型
     */
    private Integer intSpecialType;

    private Long skuid;

    /**
     * 合并订单code
     */
    private String groupStaCode;

    private String startTime;

    /**
     * 物流商平台订单号
     */
    private String extTransOrderId;
    /**
     * 退货原因
     */
    private String returnReasonMemo;

    private String returnReasonType;
    private Integer mainWhId;
    private Integer isSnInt;

    /**
     * 是否QS
     */
    private String isQs;

    /**
     * 物流宝外包仓
     */
    private String whSource;

    /**
     * 物流宝外包仓编码
     */
    private String whSourceCode;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 外包仓库存 调整ID
     */
    private Long adjustMentId;


    private String cardStatus;

    private Long snid;

    private Long customerid;

    private Long staid;

    private String plCode;

    private String pickStatus;

    private String isPickingList;

    /**
     * 货物运送方式
     */
    private Integer freightType;

    /**
     * 店铺名称
     */
    private String chName;
    /**
     * 店铺联系方式
     */
    private String chTel;


    /**
     * 收货地址
     */
    private String reAddress;

    /**
     * 收货国家
     */
    private String reCountry;
    /**
     * 收货省
     */
    private String reProvince;
    /**
     * 收货市
     */
    private String reCity;

    /**
     * 收货区
     */
    private String reDistrict;
    /**
     * 收货人
     */
    private String reReceiver;

    /**
     * 收货人手机
     */
    private String reTelephone;

    /**
     * 收货人手机2
     */
    private String reTelephone2;
    /**
     * 打印装箱清单
     */
    private Boolean isPackingList;

    private String extCode2;

    private String seqno;
    

    private Integer baoShuiType;//保税仓 出库类型

    
    
    /**
     * 入库作业单纸箱数量
     */
    private Integer cartonNum;


    private String licensePlateNumber;//保税仓 车牌号
    
    private String prestowageNo;//保税仓 配载单号
    
    private String wmsType;//wms 单据类型
    
    private Integer baoShuiStatus;//保税仓 报关状态
    
    private String wmsStatus;//wms 单据状态



    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getPrestowageNo() {
        return prestowageNo;
    }

    public void setPrestowageNo(String prestowageNo) {
        this.prestowageNo = prestowageNo;
    }

    public String getWmsType() {
        return wmsType;
    }

    public void setWmsType(String wmsType) {
        this.wmsType = wmsType;
    }

    public Integer getBaoShuiStatus() {
        return baoShuiStatus;
    }

    public void setBaoShuiStatus(Integer baoShuiStatus) {
        this.baoShuiStatus = baoShuiStatus;
    }

    public String getWmsStatus() {
        return wmsStatus;
    }

    public void setWmsStatus(String wmsStatus) {
        this.wmsStatus = wmsStatus;
    }

    public Integer getBaoShuiType() {
        return baoShuiType;
    }

    public void setBaoShuiType(Integer baoShuiType) {
        this.baoShuiType = baoShuiType;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getExtCode2() {
        return extCode2;
    }

    public void setExtCode2(String extCode2) {
        this.extCode2 = extCode2;
    }

    public String getReTelephone2() {
        return reTelephone2;
    }

    public void setReTelephone2(String reTelephone2) {
        this.reTelephone2 = reTelephone2;
    }

    /**
     * 阿里包裹号
     */
    private String aliPackageNo;
    private Boolean isHandover;
    private Long tansPgId;
    private Boolean isHaveOccupation;

    /**
     * 是否显示合并订单主订单
     * 
     * @return
     */
    private String isShowMerge;

    private String fileName;

    private String fileStatus;

    private Date exeTime;

    private String remork;


    public String getIsShowMerge() {
        return isShowMerge;
    }

    public void setIsShowMerge(String isShowMerge) {
        this.isShowMerge = isShowMerge;
    }

    public String getReReceiver() {
        return reReceiver;
    }

    public void setReReceiver(String reReceiver) {
        this.reReceiver = reReceiver;
    }

    public String getReTelephone() {
        return reTelephone;
    }

    public void setReTelephone(String reTelephone) {
        this.reTelephone = reTelephone;
    }

    private String toId;// 线下包裹order

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public String getReCountry() {
        return reCountry;
    }

    public void setReCountry(String reCountry) {
        this.reCountry = reCountry;
    }

    public String getReProvince() {
        return reProvince;
    }

    public void setReProvince(String reProvince) {
        this.reProvince = reProvince;
    }

    public String getReCity() {
        return reCity;
    }

    public void setReCity(String reCity) {
        this.reCity = reCity;
    }

    public String getReDistrict() {
        return reDistrict;
    }

    public void setReDistrict(String reDistrict) {
        this.reDistrict = reDistrict;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getChTel() {
        return chTel;
    }

    public void setChTel(String chTel) {
        this.chTel = chTel;
    }

    public String getChAddress() {
        return chAddress;
    }

    public void setChAddress(String chAddress) {
        this.chAddress = chAddress;
    }

    /**
     * 店铺地址详情
     */
    private String chAddress;



    // 包装类型
    private String packTypeStr;

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public Long getSnid() {
        return snid;
    }

    public void setSnid(Long snid) {
        this.snid = snid;
    }

    private Long ocpId;

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 物流宝入库查询执行时间
     */
    private Date exeQueryDate;

    public String getReturnReasonType() {
        return returnReasonType;
    }

    public void setReturnReasonType(String returnReasonType) {
        this.returnReasonType = returnReasonType;
    }

    public String getReturnReasonMemo() {
        return returnReasonMemo;
    }

    public void setReturnReasonMemo(String returnReasonMemo) {
        this.returnReasonMemo = returnReasonMemo;
    }

    public Long getWcenid() {
        return wcenid;
    }

    public void setWcenid(Long wcenid) {
        this.wcenid = wcenid;
    }

    /**
     * 目标仓库
     */
    private String addiName;
    /**
     * 创建人
     */
    private String creater;

    private String store;
    /**
     * 作业类型名称
     */
    private String transactionTypeName;

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
     * 送货地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 收取运费
     */
    private BigDecimal transferFee;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * Sku条码，唯一标示Sku的编码
     */
    private String barCode;
    /**
     * Product中JMCode
     */
    private String jmCode;

    /**
     * 编码，唯一标示Sku的编码
     */
    private String skuCode;

    /**
     * 供应商商品编码
     */
    private String supplierCode;

    /**
     * 核对人
     */
    private String affirmor;
    /**
     * 合计
     */
    private Long count;

    private String ownerOuName;

    private String addiOwnerOuName;

    private String invStrutsName;

    private Integer ptType;

    private Boolean isManualWeighing;
    private Integer outboundOperatorId;
    private Integer isSedKillInt;
    /**
     * 是否需要包材
     */
    private Boolean isNeedWrapstuff;

    private Integer isInboundInvoice;

    private Date startCreateTime;

    private String transNo;

    private Integer stvStatus;


    /**
     * 截止创建时间
     */
    private Date endCreateTime;

    /**
     * 存在取消未处理订单
     */
    private Boolean hasCancelUndoSta;
    /**
     * 箱号
     */
    private Integer pgIndex;

    private Integer intCheckMode;
    /*
     * 新增String格式时间字段辅助完成查询时间精确到时分秒
     */
    private String createTime1;
    private String endCreateTime1;
    private String finishTime1;
    private String startCreateTime1;

    /**
     * 是否分包
     */
    private String isMorePackage;


    /**
     * 商品大小 大件、中件、小件
     */
    private String productSize;
    /**
     * 店铺名称
     */
    private String channelName;
    /**
     * 是否后置装箱清单
     */
    private Integer isPostpositionPackingPage;

    private Long skuSize;
    private Integer skuCategoriesInt;
    private Integer isSpecialPackagingInt;
    private Integer isMergeInt;
    private Integer orderTransferFreeInt;

    /**
     * 是否运单后置
     */
    private Integer isPostpositionExpressBill;
    /**
     * 扩展信息
     */
    private String extInfo;
    /**
     * 扩展信息
     */
    private String extInfo2;

    /**
     * 创建人ID
     */
    private Integer creatorId;
    /**
     * 交接清单ID
     */
    private Integer hoListId;
    /**
     * 入库操作人ID
     */
    private Integer InboundOperatorId;

    private String transTimeType;
    private String o2oShop;
    private String skuCategoriesName;
    private String pickingTypeString;

    private String ouName;
    private String totalCompleteQty;

    private Long ouId;

    private Long quantity2;// 已经上架数量

    private Long quantity3;// 收货总数量

    private Long comleteQuantity;// 已完成数量

    private Long quantity4;// 剩余上架

    private Long skuTypeNum;// SKU种类数
    private Long pickAreasNum;// 拣货区域数量
    private Long warehouseAreasNum;// 仓库区域数量

    private String isHaoCai;// 耗材


    public String getIsHaoCai() {
        return isHaoCai;
    }

    public void setIsHaoCai(String isHaoCai) {
        this.isHaoCai = isHaoCai;
    }

    public Long getQuantity4() {
        return quantity4;
    }

    public void setQuantity4(Long quantity4) {
        this.quantity4 = quantity4;
    }

    public Long getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(Long quantity2) {
        this.quantity2 = quantity2;
    }

    public Long getQuantity3() {
        return quantity3;
    }

    public void setQuantity3(Long quantity3) {
        this.quantity3 = quantity3;
    }

    public Long getComleteQuantity() {
        return comleteQuantity;
    }

    public void setComleteQuantity(Long comleteQuantity) {
        this.comleteQuantity = comleteQuantity;
    }

    public Integer getIsPostpositionPackingPage() {
        return isPostpositionPackingPage;
    }

    public void setIsPostpositionPackingPage(Integer isPostpositionPackingPage) {
        this.isPostpositionPackingPage = isPostpositionPackingPage;
    }

    public Integer getIsPostpositionExpressBill() {
        return isPostpositionExpressBill;
    }

    public void setIsPostpositionExpressBill(Integer isPostpositionExpressBill) {
        this.isPostpositionExpressBill = isPostpositionExpressBill;
    }

    public String getIsMorePackage() {
        return isMorePackage;
    }

    public void setIsMorePackage(String isMorePackage) {
        this.isMorePackage = isMorePackage;
    }

    public Integer getIntCheckMode() {
        return intCheckMode;
    }

    public void setIntCheckMode(Integer intCheckMode) {
        this.intCheckMode = intCheckMode;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public Integer getPtType() {
        return ptType;
    }

    public void setPtType(Integer ptType) {
        this.ptType = ptType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Long getRemnant() {
        return remnant;
    }

    public void setRemnant(Long remnant) {
        this.remnant = remnant;
    }

    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    public Long getStvTotal() {
        return stvTotal;
    }

    public void setStvTotal(Long stvTotal) {
        this.stvTotal = stvTotal;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public Boolean getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setIsNeedInvoice(Boolean isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getPickingCode() {
        return pickingCode;
    }

    public void setPickingCode(String pickingCode) {
        this.pickingCode = pickingCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getSlipCode() {
        return getRefSlipCode();
    }

    public void setSlipCode(String rslipCode) {
        setRefSlipCode(rslipCode);
    }

    public Long getStvMode() {
        return stvMode;
    }

    public void setStvMode(Long stvMode) {
        this.stvMode = stvMode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Integer getStatus1() {
        if (getStatus() == null) {
            return null;
        }
        return getStatus().getValue();
    }

    public void setStatus1(Integer status1) {
        if (status1 != null) {
            setStatus(StockTransApplicationStatus.valueOf(status1));
        }
    }

    public Integer getTransaction() {
        return transaction;
    }

    public void setTransaction(Integer transaction) {
        this.transaction = transaction;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Integer getIntStaStatus() {
        return intStaStatus;
    }

    public void setIntStaStatus(Integer intStaStatus) {
        this.intStaStatus = intStaStatus;
    }

    public Integer getIntStaType() {
        return intStaType;
    }

    public void setIntStaType(Integer intStaType) {
        this.intStaType = intStaType;
    }

    public Long getTotalSkuQty() {
        return totalSkuQty;
    }

    public void setTotalSkuQty(Long totalSkuQty) {
        this.totalSkuQty = totalSkuQty;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getAddiName() {
        return addiName;
    }

    public void setAddiName(String addiName) {
        this.addiName = addiName;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public Map<String, Object> setQueryLikeParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.hasText(this.getCode())) {
            this.setCode(this.getCode() + "%");
            params.put("code", this.getCode());
        } else {
            this.setCode(null);
        }
        if (StringUtils.hasText(this.getSlipCode())) {
            this.setSlipCode(this.getSlipCode() + "%");
            params.put("slipCode", this.getSlipCode());
        } else if (StringUtils.hasText(this.getRefSlipCode())) {
            this.setRefSlipCode(this.getRefSlipCode() + "%");
            params.put("refSlipCode", this.getRefSlipCode());
        } else {
            this.setRefSlipCode(null);
        }
        if (StringUtils.hasText(this.getSlipCode1())) {
            this.setSlipCode1(this.getSlipCode1() + "%");
            params.put("slipCode1", this.getSlipCode1());
        } else {
            this.setSlipCode1(null);
        }
        if (StringUtils.hasText(this.getOwner())) {
            this.setOwner(this.getOwner() + "%");
            params.put("owner", this.getOwner());
        } else {
            this.setOwner(null);
        }
        if (StringUtils.hasText(this.getLpcode())) {
            this.setLpcode(this.getLpcode() + "%");
            params.put("lpcode", this.getLpcode());
        } else {
            this.setLpcode(null);
        }
        if (StringUtils.hasText(this.getOperator())) {
            this.setOperator("%" + this.getOperator() + "%");
            params.put("operator", this.getOperator());
        } else {
            this.setOperator(null);
        }
        if (StringUtils.hasText(this.getTrackingNo())) {
            this.setTrackingNo(this.getTrackingNo() + "%");
            params.put("trackingNo", this.getTrackingNo());
        } else {
            this.setTrackingNo(null);
        }
        if (this.getStatus1() != null) {
            params.put("status", this.getStatus1());
        }
        if (this.getType() != null) {
            params.put("type", this.getIntType());
        } else if (this.getIntStaType() != null) {
            params.put("type", this.getIntStaType());
        }
        if (this.getCreateTime() != null) {
            params.put("createTime", this.getCreateTime());
        }
        if (this.getEndCreateTime() != null) {
            params.put("endCreateTime", this.getEndCreateTime());
        }
        if (this.getIntStaStatus() != null) {
            params.put("intStatus", this.getIntStaStatus());
        } else {
            params.put("intStatus", null);
        }
        return params;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getOwnerOuName() {
        return ownerOuName;
    }

    public void setOwnerOuName(String ownerOuName) {
        this.ownerOuName = ownerOuName;
    }

    public String getAddiOwnerOuName() {
        return addiOwnerOuName;
    }

    public void setAddiOwnerOuName(String addiOwnerOuName) {
        this.addiOwnerOuName = addiOwnerOuName;
    }

    public String getInvStrutsName() {
        return invStrutsName;
    }

    public void setInvStrutsName(String invStrutsName) {
        this.invStrutsName = invStrutsName;
    }

    public Boolean getIsManualWeighing() {
        return isManualWeighing;
    }

    public void setIsManualWeighing(Boolean isManualWeighing) {
        this.isManualWeighing = isManualWeighing;
    }

    public Boolean getHasCancelUndoSta() {
        return hasCancelUndoSta;
    }

    public void setHasCancelUndoSta(Boolean hasCancelUndoSta) {
        this.hasCancelUndoSta = hasCancelUndoSta;
    }

    public Integer getIsInboundInvoice() {
        return isInboundInvoice;
    }

    public void setIsInboundInvoice(Integer isInboundInvoice) {
        this.isInboundInvoice = isInboundInvoice;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getReceiptor() {
        return receiptor;
    }

    public void setReceiptor(String receiptor) {
        this.receiptor = receiptor;
    }

    public Long getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(Long receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getAffirmor() {
        return affirmor;
    }

    public void setAffirmor(String affirmor) {
        this.affirmor = affirmor;
    }

    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    @Override
    public String getCreateTime1() {
        return createTime1;
    }

    @Override
    public void setCreateTime1(String createTime1) {
        this.createTime1 = createTime1;
    }

    public String getEndCreateTime1() {
        return endCreateTime1;
    }

    public void setEndCreateTime1(String endCreateTime1) {
        this.endCreateTime1 = endCreateTime1;
    }

    @Override
    public String getFinishTime1() {
        return finishTime1;
    }

    @Override
    public void setFinishTime1(String finishTime1) {
        this.finishTime1 = finishTime1;
    }

    public String getStartCreateTime1() {
        return startCreateTime1;
    }

    public void setStartCreateTime1(String startCreateTime1) {
        this.startCreateTime1 = startCreateTime1;
    }

    public Integer getStvStatus() {
        return stvStatus;
    }

    public void setStvStatus(Integer stvStatus) {
        this.stvStatus = stvStatus;
    }


    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }



    public Boolean getIsNeedWrapstuff() {
        return isNeedWrapstuff;
    }

    public void setIsNeedWrapstuff(Boolean isNeedWrapstuff) {
        this.isNeedWrapstuff = isNeedWrapstuff;
    }

    public Integer getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(Integer pgIndex) {
        this.pgIndex = pgIndex;
    }

    public String getIsNeedInvoicecn() {
        return isNeedInvoicecn;
    }

    public void setIsNeedInvoicecn(String isNeedInvoicecn) {
        this.isNeedInvoicecn = isNeedInvoicecn;
    }

    public String getOuterOrderCode() {
        return outerOrderCode;
    }

    public void setOuterOrderCode(String outerOrderCode) {
        this.outerOrderCode = outerOrderCode;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public BigDecimal getSkuTotalActual() {
        return skuTotalActual;
    }

    public void setSkuTotalActual(BigDecimal skuTotalActual) {
        this.skuTotalActual = skuTotalActual;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getCompleteQuantity() {
        return completeQuantity;
    }

    public void setCompleteQuantity(Long completeQuantity) {
        this.completeQuantity = completeQuantity;
    }

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    public String getInboundInfo() {
        return inboundInfo;
    }

    public void setInboundInfo(String inboundInfo) {
        this.inboundInfo = inboundInfo;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public Integer getIntSpecialType() {
        return intSpecialType;
    }

    public void setIntSpecialType(Integer intSpecialType) {
        this.intSpecialType = intSpecialType;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getGroupStaCode() {
        return groupStaCode;
    }

    public void setGroupStaCode(String groupStaCode) {
        this.groupStaCode = groupStaCode;
    }

    public Long getSkuid() {
        return skuid;
    }

    public void setSkuid(Long skuid) {
        this.skuid = skuid;
    }

    public Long getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(Long skuSize) {
        this.skuSize = skuSize;
    }

    public String getIsCod() {
        return isCod;
    }

    public void setIsCod(String isCod) {
        this.isCod = isCod;
    }

    public String getExtTransOrderId() {
        return extTransOrderId;
    }

    public void setExtTransOrderId(String extTransOrderId) {
        this.extTransOrderId = extTransOrderId;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getExtInfo2() {
        return extInfo2;
    }

    public void setExtInfo2(String extInfo2) {
        this.extInfo2 = extInfo2;
    }

    public String getIsQs() {
        return isQs;
    }

    public void setIsQs(String isQs) {
        this.isQs = isQs;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getHoListId() {
        return hoListId;
    }

    public void setHoListId(Integer hoListId) {
        this.hoListId = hoListId;
    }

    public Integer getInboundOperatorId() {
        return InboundOperatorId;
    }

    public void setInboundOperatorId(Integer inboundOperatorId) {
        InboundOperatorId = inboundOperatorId;
    }

    public Integer getRefSlipTypeInt() {
        return refSlipTypeInt;
    }

    public void setRefSlipTypeInt(Integer refSlipTypeInt) {
        this.refSlipTypeInt = refSlipTypeInt;
    }

    public Integer getDeliveryTypeInt() {
        return deliveryTypeInt;
    }

    public void setDeliveryTypeInt(Integer deliveryTypeInt) {
        this.deliveryTypeInt = deliveryTypeInt;
    }

    public Integer getPickingTypeInt() {
        return pickingTypeInt;
    }

    public void setPickingTypeInt(Integer pickingTypeInt) {
        this.pickingTypeInt = pickingTypeInt;
    }

    public Integer getMainWhId() {
        return mainWhId;
    }

    public void setMainWhId(Integer mainWhId) {
        this.mainWhId = mainWhId;
    }

    public Integer getOutboundOperatorId() {
        return outboundOperatorId;
    }

    public void setOutboundOperatorId(Integer outboundOperatorId) {
        this.outboundOperatorId = outboundOperatorId;
    }

    public Integer getSkuCategoriesInt() {
        return skuCategoriesInt;
    }

    public void setSkuCategoriesInt(Integer skuCategoriesInt) {
        this.skuCategoriesInt = skuCategoriesInt;
    }

    public Integer getIsNeedOccupiedInt() {
        return isNeedOccupiedInt;
    }

    public void setIsNeedOccupiedInt(Integer isNeedOccupiedInt) {
        this.isNeedOccupiedInt = isNeedOccupiedInt;
    }

    public Integer getIsSnInt() {
        return isSnInt;
    }

    public void setIsSnInt(Integer isSnInt) {
        this.isSnInt = isSnInt;
    }

    public Integer getIsSpecialPackagingInt() {
        return isSpecialPackagingInt;
    }

    public void setIsSpecialPackagingInt(Integer isSpecialPackagingInt) {
        this.isSpecialPackagingInt = isSpecialPackagingInt;
    }

    public Integer getIsMergeInt() {
        return isMergeInt;
    }

    public void setIsMergeInt(Integer isMergeInt) {
        this.isMergeInt = isMergeInt;
    }

    public Integer getOrderTransferFreeInt() {
        return orderTransferFreeInt;
    }

    public void setOrderTransferFreeInt(Integer orderTransferFreeInt) {
        this.orderTransferFreeInt = orderTransferFreeInt;
    }

    public Integer getVmiRCStatusInt() {
        return vmiRCStatusInt;
    }

    public void setVmiRCStatusInt(Integer vmiRCStatusInt) {
        this.vmiRCStatusInt = vmiRCStatusInt;
    }

    public Integer getIsSedKillInt() {
        return isSedKillInt;
    }

    public void setIsSedKillInt(Integer isSedKillInt) {
        this.isSedKillInt = isSedKillInt;
    }

    public String getWhSource() {
        return whSource;
    }

    public void setWhSource(String whSource) {
        this.whSource = whSource;
    }

    public String getTransTimeType() {
        return transTimeType;
    }

    public Date getExeQueryDate() {
        return exeQueryDate;
    }

    public void setExeQueryDate(Date exeQueryDate) {
        this.exeQueryDate = exeQueryDate;
    }

    public void setTransTimeType(String transTimeType) {
        this.transTimeType = transTimeType;
    }

    public String getO2oShop() {
        return o2oShop;
    }

    public void setO2oShop(String o2oShop) {
        this.o2oShop = o2oShop;
    }

    public String getSkuCategoriesName() {
        return skuCategoriesName;
    }

    public void setSkuCategoriesName(String skuCategoriesName) {
        this.skuCategoriesName = skuCategoriesName;
    }

    public String getPickingTypeString() {
        return pickingTypeString;
    }

    public void setPickingTypeString(String pickingTypeString) {
        this.pickingTypeString = pickingTypeString;
    }

    public String getWhSourceCode() {
        return whSourceCode;
    }

    public void setWhSourceCode(String whSourceCode) {
        this.whSourceCode = whSourceCode;
    }

    public Long getAdjustMentId() {
        return adjustMentId;
    }

    public void setAdjustMentId(Long adjustMentId) {
        this.adjustMentId = adjustMentId;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }


    public Long getOcpId() {
        return ocpId;
    }

    public void setOcpId(Long ocpId) {
        this.ocpId = ocpId;
    }

    public String getPickStatus() {
        return pickStatus;
    }

    public void setPickStatus(String pickStatus) {
        this.pickStatus = pickStatus;
    }

    public Integer getFreightType() {
        return freightType;
    }

    public void setFreightType(Integer freightType) {
        this.freightType = freightType;
    }


    public String getIsPickingList() {
        return isPickingList;
    }

    public void setIsPickingList(String isPickingList) {
        this.isPickingList = isPickingList;
    }

    public String getTotalCompleteQty() {
        return totalCompleteQty;
    }

    public void setTotalCompleteQty(String totalCompleteQty) {
        this.totalCompleteQty = totalCompleteQty;
    }

    public String getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(String vmiCode) {
        this.vmiCode = vmiCode;
    }

    public String getPackTypeStr() {
        return packTypeStr;
    }

    public void setPackTypeStr(String packTypeStr) {
        this.packTypeStr = packTypeStr;
    }

    public String getAliPackageNo() {
        return aliPackageNo;
    }

    public void setAliPackageNo(String aliPackageNo) {
        this.aliPackageNo = aliPackageNo;
    }

    public Long getTansPgId() {
        return tansPgId;
    }

    public void setTansPgId(Long tansPgId) {
        this.tansPgId = tansPgId;
    }

    public Boolean getIsHandover() {
        return isHandover;
    }

    public void setIsHandover(Boolean isHandover) {
        this.isHandover = isHandover;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Boolean getIsHaveOccupation() {
        return isHaveOccupation;
    }

    public void setIsHaveOccupation(Boolean isHaveOccupation) {
        this.isHaveOccupation = isHaveOccupation;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getQty2() {
        return qty2;
    }

    public void setQty2(Long qty2) {
        this.qty2 = qty2;
    }

    public Boolean getIsPackingList() {
        return isPackingList;
    }

    public void setIsPackingList(Boolean isPackingList) {
        this.isPackingList = isPackingList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Date getExeTime() {
        return exeTime;
    }

    public void setExeTime(Date exeTime) {
        this.exeTime = exeTime;
    }

    public String getRemork() {
        return remork;
    }

    public void setRemork(String remork) {
        this.remork = remork;
    }

    public Long getSkuTypeNum() {
        return skuTypeNum;
    }

    public void setSkuTypeNum(Long skuTypeNum) {
        this.skuTypeNum = skuTypeNum;
    }

    public Long getPickAreasNum() {
        return pickAreasNum;
    }

    public void setPickAreasNum(Long pickAreasNum) {
        this.pickAreasNum = pickAreasNum;
    }

    public Long getWarehouseAreasNum() {
        return warehouseAreasNum;
    }

    public void setWarehouseAreasNum(Long warehouseAreasNum) {
        this.warehouseAreasNum = warehouseAreasNum;
    }

    public String getCartonCode() {
        return cartonCode;
    }

    public void setCartonCode(String cartonCode) {
        this.cartonCode = cartonCode;
    }

    public String getPlanLastOutboundTime1() {
        return planLastOutboundTime1;
    }

    public void setPlanLastOutboundTime1(String planLastOutboundTime1) {
        this.planLastOutboundTime1 = planLastOutboundTime1;
    }

    public String getPackSlipNo() {
        return packSlipNo;
    }

    public void setPackSlipNo(String packSlipNo) {
        this.packSlipNo = packSlipNo;
    }

    public String getNfsStoreCode() {
        return nfsStoreCode;
    }

    public void setNfsStoreCode(String nfsStoreCode) {
        this.nfsStoreCode = nfsStoreCode;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public Long getNikeQty() {
        return nikeQty;
    }

    public void setNikeQty(Long nikeQty) {
        this.nikeQty = nikeQty;
    }

    public Long getVarianceQty() {
        return varianceQty;
    }

    public void setVarianceQty(Long varianceQty) {
        this.varianceQty = varianceQty;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    
}
