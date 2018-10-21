package com.jumbo.wms.model.lmis;

import java.io.Serializable;

/**
 * 操作费接口数据
 */
public class OperatingCost implements Serializable {

    private static final long serialVersionUID = -1129747145919259263L;

    /**
     * 唯一标识
     */
    private Long upper_id;

    /**
     * 订单类型
     */
    private String order_type;
    /**
     * 操作时间戳
     */
    private String operation;
    /**
     * 所属店铺编号
     */
    private String store_code;
    /**
     * 店铺名称
     */
    private String store_name;
    /**
     * 作业单号
     */
    private String job_orderno;
    /**
     * 相关单号
     */
    private String related_orderno;
    /**
     * 作业类型名称
     */
    private String job_type;
    /**
     * 库位编码
     */
    private String storaggelocation_code;
    /**
     * 入库数量 可为空
     */
    private Long in_num;
    /**
     * 出库数量 可为空
     */
    private Long out_num;
    /**
     * 商品编码
     */
    private String item_number;
    /**
     * sku编码
     */
    private String sku_number;
    /**
     * 货号
     */
    private String art_no;
    /**
     * 商品名称
     */
    private String item_name;
    /**
     * 商品大小
     */
    private String item_size;
    /**
     * 库存状态
     */
    private String inventory_status;
    /**
     * 是否耗材(1:是 0：否)
     */
    private String is_consumable;
    /**
     * 平台订单号
     */
    private String platform_order;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 仓库类别(0:自营仓；1：外包仓)
     */
    private Integer warehouse_type;
    /**
     * 仓库编码
     */
    private String warehouse_code;
    /**
     * 仓库名称
     */
    private String warehouse_name;
    /**
     * 前置单据号[销售单、换货出库等有前置单据号]
     */
    private String epistatic_order;
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 品牌对接编码
     */
    private String brand_docking_code;
    /**
     * QS标识（Y/N）
     */
    private String qs_flag;

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public String getJob_orderno() {
        return job_orderno;
    }

    public void setJob_orderno(String job_orderno) {
        this.job_orderno = job_orderno;
    }

    public String getRelated_orderno() {
        return related_orderno;
    }

    public void setRelated_orderno(String related_orderno) {
        this.related_orderno = related_orderno;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getStoraggelocation_code() {
        return storaggelocation_code;
    }

    public void setStoraggelocation_code(String storaggelocation_code) {
        this.storaggelocation_code = storaggelocation_code;
    }

    public Long getIn_num() {
        return in_num;
    }

    public void setIn_num(Long in_num) {
        this.in_num = in_num;
    }

    public Long getOut_num() {
        return out_num;
    }

    public void setOut_num(Long out_num) {
        this.out_num = out_num;
    }

    public String getItem_number() {
        return item_number;
    }

    public void setItem_number(String item_number) {
        this.item_number = item_number;
    }

    public String getSku_number() {
        return sku_number;
    }

    public void setSku_number(String sku_number) {
        this.sku_number = sku_number;
    }

    public String getArt_no() {
        return art_no;
    }

    public void setArt_no(String art_no) {
        this.art_no = art_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getInventory_status() {
        return inventory_status;
    }

    public void setInventory_status(String inventory_status) {
        this.inventory_status = inventory_status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIs_consumable() {
        return is_consumable;
    }

    public void setIs_consumable(String is_consumable) {
        this.is_consumable = is_consumable;
    }

    public String getPlatform_order() {
        return platform_order;
    }

    public void setPlatform_order(String platform_order) {
        this.platform_order = platform_order;
    }

    public Long getUpper_id() {
        return upper_id;
    }

    public void setUpper_id(Long upper_id) {
        this.upper_id = upper_id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getWarehouse_type() {
        return warehouse_type;
    }

    public void setWarehouse_type(Integer warehouse_type) {
        this.warehouse_type = warehouse_type;
    }

    public String getWarehouse_code() {
        return warehouse_code;
    }

    public void setWarehouse_code(String warehouse_code) {
        this.warehouse_code = warehouse_code;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public String getEpistatic_order() {
        return epistatic_order;
    }

    public void setEpistatic_order(String epistatic_order) {
        this.epistatic_order = epistatic_order;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBrand_docking_code() {
        return brand_docking_code;
    }

    public void setBrand_docking_code(String brand_docking_code) {
        this.brand_docking_code = brand_docking_code;
    }

    public String getQs_flag() {
        return qs_flag;
    }

    public void setQs_flag(String qs_flag) {
        this.qs_flag = qs_flag;
    }

}
