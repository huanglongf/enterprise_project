package com.lmis.jbasis.expenses.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: TbOperationfeeData
 * @Description: TODO()
 * @author codeGenerator
 * @date 2018-04-23 10:25:38
 * 
 */
public class TbOperationfeeData implements Serializable{

    /** 
     * @Fields serialVersionUID : TODO(序列号) 
     */
    private static final long serialVersionUID = 1L;
    
    public static long getSerialversionuid() {
        return serialVersionUID;    
    }
    @ApiModelProperty(value = "id")
    private Integer id;
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    
    
    
    public Date getCreateTime(){
        return createTime;
    }

    
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    
    public Date getUpdateTime(){
        return updateTime;
    }

    
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
    @ApiModelProperty(value = "")
    private Integer primaryId;
    public Integer getPrimaryId() {
        return primaryId;
    }
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }
    
    @ApiModelProperty(value = "")
    private Integer batId;
    public Integer getBatId() {
        return batId;
    }
    public void setBatId(Integer batId) {
        this.batId = batId;
    }
    
    @ApiModelProperty(value = "创建人")
    private String createUser;
    public String getCreateUser() {
        return createUser;
    }
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    @ApiModelProperty(value = "更新人")
    private String updateUser;
    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    
    @ApiModelProperty(value = "订单类型")
    private String orderType;
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间戳")
    private Date operationTime;
    public Date getOperationTime() {
        return operationTime;
    }
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }
    
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    @ApiModelProperty(value = "作业单号")
    private String jobOrderno;
    public String getJobOrderno() {
        return jobOrderno;
    }
    public void setJobOrderno(String jobOrderno) {
        this.jobOrderno = jobOrderno;
    }
    
    @ApiModelProperty(value = "相关单号")
    private String relatedOrderno;
    public String getRelatedOrderno() {
        return relatedOrderno;
    }
    public void setRelatedOrderno(String relatedOrderno) {
        this.relatedOrderno = relatedOrderno;
    }
    
    @ApiModelProperty(value = "")
    private String platformOrder;
    public String getPlatformOrder() {
        return platformOrder;
    }
    public void setPlatformOrder(String platformOrder) {
        this.platformOrder = platformOrder;
    }
    
    @ApiModelProperty(value = "作业类型名称")
    private String jobType;
    public String getJobType() {
        return jobType;
    }
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    
    @ApiModelProperty(value = "库位编码")
    private String storaggelocationCode;
    public String getStoraggelocationCode() {
        return storaggelocationCode;
    }
    public void setStoraggelocationCode(String storaggelocationCode) {
        this.storaggelocationCode = storaggelocationCode;
    }
    
    @ApiModelProperty(value = "入库数量")
    private String inNum;
    public String getInNum() {
        return inNum;
    }
    public void setInNum(String inNum) {
        this.inNum = inNum;
    }
    
    @ApiModelProperty(value = "出库数量")
    private String outNum;
    public String getOutNum() {
        return outNum;
    }
    public void setOutNum(String outNum) {
        this.outNum = outNum;
    }
    
    @ApiModelProperty(value = "商品编码")
    private String itemNumber;
    public String getItemNumber() {
        return itemNumber;
    }
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }
    
    @ApiModelProperty(value = "sku编码")
    private String skuNumber;
    public String getSkuNumber() {
        return skuNumber;
    }
    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }
    
    @ApiModelProperty(value = "货号")
    private String artNo;
    public String getArtNo() {
        return artNo;
    }
    public void setArtNo(String artNo) {
        this.artNo = artNo;
    }
    
    @ApiModelProperty(value = "商品名称")
    private String itemName;
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    @ApiModelProperty(value = "商品大小")
    private String itemSize;
    public String getItemSize() {
        return itemSize;
    }
    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }
    
    @ApiModelProperty(value = "库存状态")
    private String inventoryStatus;
    public String getInventoryStatus() {
        return inventoryStatus;
    }
    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }
    
    @ApiModelProperty(value = "操作员")
    private String operator;
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    @ApiModelProperty(value = "")
    private String warehouseType;
    public String getWarehouseType() {
        return warehouseType;
    }
    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }
    
    @ApiModelProperty(value = "")
    private String warehouseName;
    public String getWarehouseName() {
        return warehouseName;
    }
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    @ApiModelProperty(value = "")
    private String warehouseCode;
    public String getWarehouseCode() {
        return warehouseCode;
    }
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
    
    @ApiModelProperty(value = "所属店铺id")
    private Integer storeId;
    public Integer getStoreId() {
        return storeId;
    }
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    
    @ApiModelProperty(value = "结算标识")
    private Integer settleFlag;
    public Integer getSettleFlag() {
        return settleFlag;
    }
    public void setSettleFlag(Integer settleFlag) {
        this.settleFlag = settleFlag;
    }
    
    @ApiModelProperty(value = "品牌对接编码（AD没有）")
    private String brandDockingCode;
    public String getBrandDockingCode() {
        return brandDockingCode;
    }
    public void setBrandDockingCode(String brandDockingCode) {
        this.brandDockingCode = brandDockingCode;
    }
    
    @ApiModelProperty(value = "条形码")
    private String barcode;
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    @ApiModelProperty(value = "前置单据号")
    private String epistaticOrder;
    public String getEpistaticOrder() {
        return epistaticOrder;
    }
    public void setEpistaticOrder(String epistaticOrder) {
        this.epistaticOrder = epistaticOrder;
    }
    
}
