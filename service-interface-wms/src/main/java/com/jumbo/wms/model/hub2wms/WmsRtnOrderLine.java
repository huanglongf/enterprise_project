package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;

import com.jumbo.wms.model.BaseModel;

/**
 * 退入订单明细
 * 
 * @author cheng.su
 * 
 */
public class WmsRtnOrderLine extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1798893641551068273L;
    /**
     * 行号
     */
    private String lineNo;
    /**
     * 商品
     */
    private String sku;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 销售商品名称
     */
    private String skuName;
    /**
     * 吊牌价
     */
    private BigDecimal listPrice;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 行总金额
     */
    private BigDecimal lineAmt;
    /**
     * 订单货主
     */
    private String owner;
    /**
     * 商品状态
     */
    private String invStatus;
    /**
     * 扩展字段
     */
    private String ext_code;

    /**
     * 颜色打印值
     */
    private String color;

    /**
     * SIZE打印值
     */
    private String size;

    private String lpCode; // 运输公司

    private String transNo; // 单号

    private Long orderQty;// 平台订单计划量


    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Long getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Long orderQty) {
        this.orderQty = orderQty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public BigDecimal getLineAmt() {
        return lineAmt;
    }

    public void setLineAmt(BigDecimal lineAmt) {
        this.lineAmt = lineAmt;
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

    public String getExt_code() {
        return ext_code;
    }

    public void setExt_code(String ext_code) {
        this.ext_code = ext_code;
    }



}
