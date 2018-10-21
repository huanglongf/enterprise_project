package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * HUB2WMS过仓 销售订单明细
 * 
 * @author jinlong.ke
 * 
 */
public class WmsSalesOrderLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2664853085648855912L;
    /*
     * 平台行号
     */
    private String lineNo;
    /*
     * 商品
     */
    private String sku;
    /*
     * 数量
     */
    private Long qty;
    /*
     * 增值服务列表
     */
    private List<ValueAddedService> vasList;
    /*
     * 销售平台商品名称
     */
    private String skuName;
    /*
     * 吊牌价
     */
    private BigDecimal listPrice;
    /*
     * 单价
     */
    private BigDecimal unitPrice;
    /*
     * 行折扣
     */
    private BigDecimal lineDiscount;
    /*
     * 行总金额
     */
    private BigDecimal lineAmt;
    /*
     * 活动编码
     */
    private String activeCode;
    /*
     * 订单货主
     */
    private String owner;
    /*
     * 指定仓库
     */
    private String warehouseCode;
    /*
     * 商品状态 
     */
    private String invStatus;
    /*
     * 是否是赠品 
     */
    private boolean isGift;
    /*
     * 扩展字段
     */
    private String ext_code;
    /*
     * sn号
     */
    private List<String> sns;
    /*
     * 产地
     */
    private String origin;
    /*
     * 批次号 
     */
    private String batchNo;
    /*
     * 起始过期时间
     */
    private Date sExpDate;
    /*
     * 结束过期时间
     */
    private Date eExpDate;

    private String lpCode;

    private String color;// 商品颜色

    private String skuSize;// 商品尺码

    private String orderLineNo;// 平台订单行号

    private Long orderQty;// 平台订单计划量

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
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

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public Long getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Long orderQty) {
        this.orderQty = orderQty;
    }

    public void setGift(boolean isGift) {
        this.isGift = isGift;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public List<ValueAddedService> getVasList() {
        return vasList;
    }

    public void setVasList(List<ValueAddedService> vasList) {
        this.vasList = vasList;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLineDiscount() {
        return lineDiscount;
    }

    public void setLineDiscount(BigDecimal lineDiscount) {
        this.lineDiscount = lineDiscount;
    }

    public BigDecimal getLineAmt() {
        return lineAmt;
    }

    public void setLineAmt(BigDecimal lineAmt) {
        this.lineAmt = lineAmt;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(boolean isGift) {
        this.isGift = isGift;
    }

    public String getExt_code() {
        return ext_code;
    }

    public void setExt_code(String ext_code) {
        this.ext_code = ext_code;
    }

    public List<String> getSns() {
        return sns;
    }

    public void setSns(List<String> sns) {
        this.sns = sns;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getsExpDate() {
        return sExpDate;
    }

    public void setsExpDate(Date sExpDate) {
        this.sExpDate = sExpDate;
    }

    public Date geteExpDate() {
        return eExpDate;
    }

    public void seteExpDate(Date eExpDate) {
        this.eExpDate = eExpDate;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }


}
