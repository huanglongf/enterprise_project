package com.jumbo.wms.model.lmis;

import java.io.Serializable;

/**
 * 退换货接口数据
 */
public class ReturnStorage implements Serializable {

    private static final long serialVersionUID = 4927113033121838508L;
    /**
     * 仓库编码
     */
    private String warehouse_code;
    /**
     * 仓库名称
     */
    private String warehouse_name;
    /**
     * 作业单号
     */
    private String job_no;
    /**
     * 作业类型
     */
    private String job_type;
    /**
     * 店铺编码
     */
    private String store_code;
    /**
     * 店铺名称
     */
    private String store_name;
    /**
     * PACS退货相关单号
     */
    private String related_no;
    /**
     * 快递单号
     */
    private String waybill;
    /**
     * PACS销售单号
     */
    private String re_epistatic_order;
    /**
     * 平台订单号
     */
    private String platform_no;
    /**
     * 作业单创建时间
     */
    private String create_time;
    /**
     * 作业单完成时间
     */
    private String finish_time;
    /**
     * 计划执行量
     */
    private Long plan_qty;
    /**
     * 实际执行量
     */
    private Long actual_qty;
    /**
     * 物流商代码
     */
    private String transport_code;
    /**
     * 物流商名称
     */
    private String transport_name;
    /**
     * 产品类型代码
     */
    private String itemtype_code;
    /**
     * 产品类型名称
     */
    private String itemtype_name;
    /**
     * 登记物理仓名称
     */
    private String physical_warehouse;
    /**
     * 创建人
     */
    private String create_user;
    /**
     * 解锁人
     */
    private String deblock_user;
    /**
     * 备注
     */
    private String remark;
    /**
     * 退货入库订单金额
     */
    private String return_price;

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

    public String getJob_no() {
        return job_no;
    }

    public void setJob_no(String job_no) {
        this.job_no = job_no;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
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

    public String getRelated_no() {
        return related_no;
    }

    public void setRelated_no(String related_no) {
        this.related_no = related_no;
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public String getRe_epistatic_order() {
        return re_epistatic_order;
    }

    public void setRe_epistatic_order(String re_epistatic_order) {
        this.re_epistatic_order = re_epistatic_order;
    }

    public String getPlatform_no() {
        return platform_no;
    }

    public void setPlatform_no(String platform_no) {
        this.platform_no = platform_no;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public Long getPlan_qty() {
        return plan_qty;
    }

    public void setPlan_qty(Long plan_qty) {
        this.plan_qty = plan_qty;
    }

    public Long getActual_qty() {
        return actual_qty;
    }

    public void setActual_qty(Long actual_qty) {
        this.actual_qty = actual_qty;
    }

    public String getTransport_code() {
        return transport_code;
    }

    public void setTransport_code(String transport_code) {
        this.transport_code = transport_code;
    }

    public String getTransport_name() {
        return transport_name;
    }

    public void setTransport_name(String transport_name) {
        this.transport_name = transport_name;
    }

    public String getItemtype_code() {
        return itemtype_code;
    }

    public void setItemtype_code(String itemtype_code) {
        this.itemtype_code = itemtype_code;
    }

    public String getItemtype_name() {
        return itemtype_name;
    }

    public void setItemtype_name(String itemtype_name) {
        this.itemtype_name = itemtype_name;
    }

    public String getPhysical_warehouse() {
        return physical_warehouse;
    }

    public void setPhysical_warehouse(String physical_warehouse) {
        this.physical_warehouse = physical_warehouse;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getDeblock_user() {
        return deblock_user;
    }

    public void setDeblock_user(String deblock_user) {
        this.deblock_user = deblock_user;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReturn_price() {
        return return_price;
    }

    public void setReturn_price(String return_price) {
        this.return_price = return_price;
    }
}
