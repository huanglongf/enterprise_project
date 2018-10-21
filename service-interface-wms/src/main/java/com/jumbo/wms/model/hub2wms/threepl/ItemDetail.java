package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 */
public class ItemDetail implements Serializable {

    private static final long serialVersionUID = -5463706263233604557L;

    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 规格型号
     */
    private String specificModel;
    /**
     * 数量单位
     */
    private String unit;
    /**
     * 单价,单位分(增值税发票可以为空)
     */
    private Double price;
    /**
     * 数量
     */
    private Long quantity;
    /**
     * 发票明细总金额,单位分
     */
    private Double amount;
    /**
     * 税率(增值发票必填)
     */
    private Double taxRate;
    /**
     */
    private Double taxAmount;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpecificModel() {
        return specificModel;
    }

    public void setSpecificModel(String specificModel) {
        this.specificModel = specificModel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

}
