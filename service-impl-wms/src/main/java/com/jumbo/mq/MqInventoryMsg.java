package com.jumbo.mq;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MqInventoryMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4415691108569936424L;

    /**
     * sku jmskucode
     */
    @Deprecated
    private String jmskucode;

    /**
     * sku 条码
     */
    @Deprecated
    private String barcode;

    /**
     * sku 款号
     */
    @Deprecated
    private String productCode;

    /**
     * sku唯一标识(到尺码,颜色)
     */
    private String extentionCode;

    /**
     * 供应商商品编码(到款)
     */
    private String supplierSkuCode;

    /**
     * sku 扩展属性
     */
    private String keyProperties;

    /**
     * 类型:1全量 2 增量
     */
    private int type;

    /**
     * 方向：1 入库,2 出库
     */
    private int direction;

    /**
     * 数量
     */
    private Long qty;

    /**
     * 库存日志生成时间
     */
    private Date opTime;

    /**
     * 颜色编码
     */
    private String color;

    /**
     * 尺码
     */
    private String skuSize;

    public String getJmskucode() {
        return jmskucode;
    }

    public void setJmskucode(String jmskucode) {
        this.jmskucode = jmskucode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getExtentionCode() {
        return extentionCode;
    }

    public void setExtentionCode(String extentionCode) {
        this.extentionCode = extentionCode;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
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
}
