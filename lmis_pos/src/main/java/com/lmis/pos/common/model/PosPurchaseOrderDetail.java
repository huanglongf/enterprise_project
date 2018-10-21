package com.lmis.pos.common.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosPurchaseOrderDetail
 * @Description: TODO(NIKE-PO单明细表)
 * @author codeGenerator
 * @date 2018-05-31 20:13:13
 * 
 */
public class PosPurchaseOrderDetail extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "批次号")
	private String batId;
	public String getBatId() {
		return batId;
	}
	public void setBatId(String batId) {
		this.batId = batId;
	}

    @ApiModelProperty(value = "文件编号")
	private String fileNo;
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

    @ApiModelProperty(value = "文件名")
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
    @ApiModelProperty(value = "")
	private String soldTo;
	public String getSoldTo() {
		return soldTo;
	}
	public void setSoldTo(String soldTo) {
		this.soldTo = soldTo;
	}
	
    @ApiModelProperty(value = "")
	private String door;
	public String getDoor() {
		return door;
	}
	public void setDoor(String door) {
		this.door = door;
	}
	
    @ApiModelProperty(value = "")
	private String shipTo;
	public String getShipTo() {
		return shipTo;
	}
	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}
	
    @ApiModelProperty(value = "单据类型")
	private String orderType;
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
    @ApiModelProperty(value = "最晚到货时间")
	private String crdTime;
	public String getCrdTime() {
		return crdTime;
	}
	public void setCrdTime(String crdTime) {
		this.crdTime = crdTime;
	}
	
    @ApiModelProperty(value = "")
	private String cancelTime;
	public String getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
	
    @ApiModelProperty(value = "")
	private String launchDate;
	public String getLaunchDate() {
		return launchDate;
	}
	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}
	
    @ApiModelProperty(value = "商品类型 10-服饰、20-鞋、30-配件")
	private String itemType;
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
    @ApiModelProperty(value = "")
	private String salesOrg;
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	
    @ApiModelProperty(value = "")
	private String plant;
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	
    @ApiModelProperty(value = "PO单号")
	private String poNumber;
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	@ApiModelProperty(value = "PO单号（未处理过的）")
	private String poNumber1;
	public String getPoNumber1() {
		return poNumber1;
	}
	public void setPoNumber1(String poNumber1) {
		this.poNumber1 = poNumber1;
	}
	
    @ApiModelProperty(value = "")
	private String skuCode;
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

    @ApiModelProperty(value = "商品名称(pos_sku_base表name(1)+name)")
	private String productName;
    public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

    @ApiModelProperty(value = "商品规格(pos_sku_base表KEY_PROPERTIES)")
	private String productSpec;
	public String getProductSpec() {
		return productSpec;
	}
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}
	
    @ApiModelProperty(value = "")
	private String contract;
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	
    @ApiModelProperty(value = "")
	private String prodCode;
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	
    @ApiModelProperty(value = "")
	private String prodSize;
	public String getProdSize() {
		return prodSize;
	}
	public void setProdSize(String prodSize) {
		this.prodSize = prodSize;
	}
	
    @ApiModelProperty(value = "")
	private Integer prodQty;
	public Integer getProdQty() {
		return prodQty;
	}
	public void setProdQty(Integer prodQty) {
		this.prodQty = prodQty;
	}
	
    @ApiModelProperty(value = "")
	private String vNumber;
	public String getVNumber() {
		return vNumber;
	}
	public void setVNumber(String vNumber) {
		this.vNumber = vNumber;
	}
	
    @ApiModelProperty(value = "po类型")
	private String poType;
	public String getPoType() {
		return poType;
	}
	public void setPoType(String poType) {
		this.poType = poType;
	}
	
    @ApiModelProperty(value = "商品分类")
	private String category;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
