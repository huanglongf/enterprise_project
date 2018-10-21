package com.jumbo.wms.model.lmis;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 */
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -4759555205471828033L;

    /**
     * SKU条码
     */
    private String sku_number; //
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 商品名称
     */
    private String item_name;
    /**
     * 扩展属性
     */
    private String extend_pro;
    /**
     * 商品数量
     */
    private Integer qty;

    // 添加主信息字段
    /**
     * 运单号
     */
    private String express_number;
    /**
     * 购买单价
     */
    private Long unit_price;
    /**
     * 购买金额
     */
    private Long total_price;

    public String getSku_number() {
        return sku_number;
    }

    public void setSku_number(String sku_number) {
        this.sku_number = sku_number;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getExtend_pro() {
        return extend_pro;
    }

    public void setExtend_pro(String extend_pro) {
        this.extend_pro = extend_pro;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }


    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public Long getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Long unit_price) {
        this.unit_price = unit_price;
    }

    public Long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Long total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "OrderDetail [sku_number=" + sku_number + ", barcode=" + barcode + ", item_name=" + item_name + ", extend_pro=" + extend_pro + ", qty=" + qty + ", express_number=" + express_number + ", unit_price=" + unit_price + ", total_price="
                + total_price + "]";
    }
}
