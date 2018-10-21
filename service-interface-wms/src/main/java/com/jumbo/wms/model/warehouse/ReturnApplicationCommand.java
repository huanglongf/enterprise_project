package com.jumbo.wms.model.warehouse;

public class ReturnApplicationCommand extends ReturnApplication {

    /**
	 * 
	 */
    private static final long serialVersionUID = 663485377494382480L;
    /**
     * 状态
     */
    private String statusName;
    /**
     * 操作人
     */
    private String creataUserName;
    /**
     * 扫描商品列表集合。 格式为： 商品ID,数量;状态ID/......
     */
    private String skuString1;

    /**
     * 手动添加商品集合。格式： 商品信息描述,数量;状态ID/......
     */
    private String skuString2;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 商品状态ID
     */
    private Long invStatusId;

    /**
     * 商品信息描述
     */
    private String skuInfo;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 商品条码
     */
    private String skuBarCode;

    /**
     * 商品编码
     */
    private String skuCode;

    /**
     * 商品数量
     */
    private Long qty;

    /**
     * 操作节点 1：创建并提交 2：作废
     */
    private String returnStatus;

    /**
     * 店铺Code
     */
    private String ownerCode;

    /**
     * 作业单号
     */
    private String staCode;

    /**
     * OMS状态
     */
    private String omsStatuss;

    /**
     * 是否有附件
     */
    private String isUpload;

    /**
     * oms状态名称
     */
    private String omsStatusName;
    
    /**
     * 退货指令状态
     */
    private String status;
    
    
    private String wmsOrderType;
    
    //////////////////////////ad///////////////////////////////
    private String receiveDate;//收货日期 
    //////////////////////////////////////////////////////////
   
    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getWmsOrderType() {
        return wmsOrderType;
    }

    public void setWmsOrderType(String wmsOrderType) {
        this.wmsOrderType = wmsOrderType;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCreataUserName() {
        return creataUserName;
    }

    public void setCreataUserName(String creataUserName) {
        this.creataUserName = creataUserName;
    }

    public String getSkuString1() {
        return skuString1;
    }

    public void setSkuString1(String skuString1) {
        this.skuString1 = skuString1;
    }

    public String getSkuString2() {
        return skuString2;
    }

    public void setSkuString2(String skuString2) {
        this.skuString2 = skuString2;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(String skuInfo) {
        this.skuInfo = skuInfo;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getOmsStatuss() {
        return omsStatuss;
    }

    public void setOmsStatuss(String omsStatuss) {
        this.omsStatuss = omsStatuss;
    }

    public String getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(String isUpload) {
        this.isUpload = isUpload;
    }

    public String getOmsStatusName() {
        return omsStatusName;
    }

    public void setOmsStatusName(String omsStatusName) {
        this.omsStatusName = omsStatusName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
