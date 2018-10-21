package com.jumbo.wms.model.lmis;

import java.io.Serializable;

/**
 * 耗材费-实际使用量接口数据
 */
public class MaterialFeeActualUsage implements Serializable {

    private static final long serialVersionUID = -8183086160548188860L;

    /**
     * 唯一标识
     */
    private Long upper_id;

    /**
     * 入库时间
     */
    private String ib_time;

    /**
     * 出库时间
     */
    private String ob_time;

    /**
     * 店铺编码
     */
    private String store_code;
    /**
     * 店铺名称
     */
    private String store_name;
    /**
     * 耗材编码
     */
    private String sku_code;
    /**
     * 耗材名称
     */
    private String sku_name;
    /**
     * 使用量
     */
    private String use_amount;
    /**
     * 使用量单位
     */
    private String use_amountunit;
    /**
     * 仓库编码
     */
    private String warehosue_code;
    /**
     * 仓库名称
     */
    private String warehosue_name;
    /**
     * 上位系统订单号/前置单据号/相关单据号
     */
    private String epistatic_order;
    /**
     * 运单号
     */
    private String express_number;

    public String getIb_time() {
        return ib_time;
    }

    public void setIb_time(String ib_time) {
        this.ib_time = ib_time;
    }

    public String getOb_time() {
        return ob_time;
    }

    public void setOb_time(String ob_time) {
        this.ob_time = ob_time;
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

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public String getSku_name() {
        return sku_name;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
    }

    public String getUse_amount() {
        return use_amount;
    }

    public void setUse_amount(String use_amount) {
        this.use_amount = use_amount;
    }

    public String getUse_amountunit() {
        return use_amountunit;
    }

    public void setUse_amountunit(String use_amountunit) {
        this.use_amountunit = use_amountunit;
    }

    public Long getUpper_id() {
        return upper_id;
    }

    public void setUpper_id(Long upper_id) {
        this.upper_id = upper_id;
    }

    public String getWarehosue_code() {
        return warehosue_code;
    }

    public void setWarehosue_code(String warehosue_code) {
        this.warehosue_code = warehosue_code;
    }

    public String getWarehosue_name() {
        return warehosue_name;
    }

    public void setWarehosue_name(String warehosue_name) {
        this.warehosue_name = warehosue_name;
    }

    public String getEpistatic_order() {
        return epistatic_order;
    }

    public void setEpistatic_order(String epistatic_order) {
        this.epistatic_order = epistatic_order;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    @Override
    public String toString() {
        return "MaterialFeeActualUsage [upper_id=" + upper_id + ", ib_time=" + ib_time + ", ob_time=" + ob_time + ", store_code=" + store_code + ", store_name=" + store_name + ", sku_code=" + sku_code + ", sku_name=" + sku_name + ", use_amount="
                + use_amount + ", use_amountunit=" + use_amountunit + ", warehosue_code=" + warehosue_code + ", warehosue_name=" + warehosue_name + ", epistatic_order=" + epistatic_order + ", express_number=" + express_number + "]";
    }
}
