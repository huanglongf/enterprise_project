package com.jumbo.wms.model.lmis;

import java.io.Serializable;

/**
 * 耗材费-采购明细接口数据
 */
public class MaterialFeePurchaseDetails implements Serializable {

    private static final long serialVersionUID = -5108878767552973153L;

    /**
     * 唯一标识
     */
    private Long upper_id;

    /**
     * 入库时间
     */
    private String ib_time;
    /**
     * 店铺编码
     */
    private String store_code;
    /**
     * 店铺名称
     */
    private String store_name;
    /**
     * 供货商
     */
    private String vendor;
    /**
     * PO单号
     */
    private String inbound_no;
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 宝尊编码 可为空
     */
    private String bz_number;
    /**
     * 商品编码
     */
    private String item_no;
    /**
     * 商品名称
     */
    private String item_name;
    /**
     * 实际入库数量
     */
    private Long inbound_qty;
    /**
     * 采购单价
     */
    private Double purchase_price;
    /**
     * 实际到货入库金额
     */
    private Double inbound_money;
    /**
     * 到期时间 可为空
     */
    private String end_time;

    public String getIb_time() {
        return ib_time;
    }

    public void setIb_time(String ib_time) {
        this.ib_time = ib_time;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getInbound_no() {
        return inbound_no;
    }

    public void setInbound_no(String inbound_no) {
        this.inbound_no = inbound_no;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBz_number() {
        return bz_number;
    }

    public void setBz_number(String bz_number) {
        this.bz_number = bz_number;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public Long getInbound_qty() {
        return inbound_qty;
    }

    public void setInbound_qty(Long inbound_qty) {
        this.inbound_qty = inbound_qty;
    }

    public Double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(Double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public Double getInbound_money() {
        return inbound_money;
    }

    public void setInbound_money(Double inbound_money) {
        this.inbound_money = inbound_money;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Long getUpper_id() {
        return upper_id;
    }

    public void setUpper_id(Long upper_id) {
        this.upper_id = upper_id;
    }

    @Override
    public String toString() {
        return "MaterialFeePurchaseDetails [upper_id=" + upper_id + ", ib_time=" + ib_time + ", store_code=" + store_code + ", store_name=" + store_name + ", vendor=" + vendor + ", inbound_no=" + inbound_no + ", barcode=" + barcode + ", bz_number="
                + bz_number + ", item_no=" + item_no + ", item_name=" + item_name + ", inbound_qty=" + inbound_qty + ", purchase_price=" + purchase_price + ", inbound_money=" + inbound_money + ", end_time=" + end_time + "]";
    }

}
