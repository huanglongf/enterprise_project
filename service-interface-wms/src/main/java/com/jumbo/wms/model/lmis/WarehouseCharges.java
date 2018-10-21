package com.jumbo.wms.model.lmis;

import java.io.Serializable;

/**
 * 仓储接口数据
 */
public class WarehouseCharges implements Serializable {

    private static final long serialVersionUID = 5970029544588758521L;

    /**
     * 大类(0:自营仓；1：外包仓)
     */
    private Integer warehouse_type;

    /**
     * 日期(这条数据是对应哪一天)
     */
    private String start_time;

    /**
     * 店铺编号
     */
    private String store_code;
    /**
     * 店铺名称
     */
    private String store_name;
    /**
     * 是否关店(0:开店；1：关店)
     */
    private Integer is_closed;
    /**
     * 仓库编号
     */
    private String warehouse_code;
    /**
     * 仓库名称
     */
    private String warehouse_name;
    /**
     * 系统仓 逻辑仓
     */
    private String system_warehouse;
    /**
     * 区域代码
     */
    private String area_code;
    /**
     * 区域名称
     */
    private String area_name;
    /**
     * 商品类型编码
     */
    private String sku_code;
    /**
     * 商品类型
     */
    private String item_type;
    /**
     * 货位代码
     */
    private String goods_area_code;
    /**
     * 面积
     */
    private String forests;
    /**
     * 体积
     */
    private String volume;
    /**
     * 件数
     */
    private Long qty;
    /**
     * 库区
     */
    private String l_code;
    /**
     * 小库区
     */
    private String x_code;
    /**
     * 通道+组
     */
    private String y_code;
    /**
     * 层
     */
    private String z_code;
    /**
     * 格
     */
    private String c_code;

    public Integer getWarehouse_type() {
        return warehouse_type;
    }

    public void setWarehouse_type(Integer warehouse_type) {
        this.warehouse_type = warehouse_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
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

    public Integer getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(Integer is_closed) {
        this.is_closed = is_closed;
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

    public String getSystem_warehouse() {
        return system_warehouse;
    }

    public void setSystem_warehouse(String system_warehouse) {
        this.system_warehouse = system_warehouse;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getGoods_area_code() {
        return goods_area_code;
    }

    public void setGoods_area_code(String goods_area_code) {
        this.goods_area_code = goods_area_code;
    }

    public String getForests() {
        return forests;
    }

    public void setForests(String forests) {
        this.forests = forests;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public String getL_code() {
        return l_code;
    }

    public void setL_code(String l_code) {
        this.l_code = l_code;
    }

    public String getX_code() {
        return x_code;
    }

    public void setX_code(String x_code) {
        this.x_code = x_code;
    }

    public String getY_code() {
        return y_code;
    }

    public void setY_code(String y_code) {
        this.y_code = y_code;
    }

    public String getZ_code() {
        return z_code;
    }

    public void setZ_code(String z_code) {
        this.z_code = z_code;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

}
