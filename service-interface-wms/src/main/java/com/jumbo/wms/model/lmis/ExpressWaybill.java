package com.jumbo.wms.model.lmis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 快递运单接口数据
 */
public class ExpressWaybill implements Serializable {

    private static final long serialVersionUID = 5964538729525152959L;

    /**
     * 唯一标识
     */
    private Long upper_id;

    /**
     * 店铺代码
     */
    private String store_code;
    /**
     * 店铺名称
     */
    private String store_name;
    /**
     * 所属仓库编码 逻辑仓库
     */
    private String warehouse_code;
    /**
     * 所属仓库名称 逻辑仓库
     */
    private String warehouse_name;
    /**
     * 快递代码（如SF YTO 等）
     */
    private String transport_code;
    /**
     * 快递名称
     */
    private String transport_name;
    /**
     * 平台订单号
     */
    private String delivery_order;
    /**
     * 上位系统订单号
     */
    private String epistatic_order;
    /**
     * 订单类型［作业单类型］
     */
    private String order_type;
    /**
     * 运单号
     */
    private String express_number;
    /**
     * 运输方向(0:正向（发货）；1：逆向（退货）)
     */
    private String transport_direction;
    /**
     * 产品类型代码
     */
    private String itemtype_code;
    /**
     * 产品类型名称
     */
    private String itemtype_name;
    /**
     * 运单出库时间
     */
    private String transport_time;
    /**
     * 代收货款金额 可以为空
     */
    private Double collection_on_delivery;
    /**
     * 订单金额
     */
    private Double order_amount;
    /**
     * 耗材SKU编码 可以为空
     */
    private String sku_number;
    /**
     * 实际重量
     */
    private Double weight;
    /**
     * 长
     */
    private Double length;
    /**
     * 宽
     */
    private Double width;
    /**
     * 高
     */
    private Double higth;
    /**
     * 体积
     */
    private Double volumn;
    /**
     * 始发地
     */
    private String delivery_address;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县 可以为空
     */
    private String state;
    /**
     * 是否保价（0：否；1：是）
     */
    private Integer insurance_flag;
    /**
     * 是否COD（0：否；1：是）
     */
    private Integer cod_flag;
    /**
     * 收件人
     */
    private String shiptoname;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 快递交接时间
     */
    private String express_time;
    /**
     * 复核时间
     */
    private String check_time;
    /**
     * 称重时间
     */
    private String weight_time;
    /**
     * 平台订单时间
     */
    private String platform_order_time;
    /**
     * 平台付款时间
     */
    private String platform_pay_time;
    /**
     * 子包裹标识（0:主包裹/1：子包裹）
     */
    private String k_flag;
    /**
     * 订单明细
     */
    private List<OrderDetail> order_details = new ArrayList<OrderDetail>();
    /**
     * sta的skus
     */
    private String skus;
    /**
     * sta的id
     */
    private String staId;
    /**
     * 包裹类型：（0 线上包裹；1线下包裹）
     */
    private String package_type;

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

    public String getDelivery_order() {
        return delivery_order;
    }

    public void setDelivery_order(String delivery_order) {
        this.delivery_order = delivery_order;
    }

    public String getEpistatic_order() {
        return epistatic_order;
    }

    public void setEpistatic_order(String epistatic_order) {
        this.epistatic_order = epistatic_order;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public String getTransport_direction() {
        return transport_direction;
    }

    public void setTransport_direction(String transport_direction) {
        this.transport_direction = transport_direction;
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

    public String getTransport_time() {
        return transport_time;
    }

    public void setTransport_time(String transport_time) {
        this.transport_time = transport_time;
    }

    public Double getCollection_on_delivery() {
        return collection_on_delivery;
    }

    public void setCollection_on_delivery(Double collection_on_delivery) {
        this.collection_on_delivery = collection_on_delivery;
    }

    public Double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(Double order_amount) {
        this.order_amount = order_amount;
    }

    public String getSku_number() {
        return sku_number;
    }

    public void setSku_number(String sku_number) {
        this.sku_number = sku_number;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHigth() {
        return higth;
    }

    public void setHigth(Double higth) {
        this.higth = higth;
    }

    public Double getVolumn() {
        return volumn;
    }

    public void setVolumn(Double volumn) {
        this.volumn = volumn;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getInsurance_flag() {
        return insurance_flag;
    }

    public void setInsurance_flag(Integer insurance_flag) {
        this.insurance_flag = insurance_flag;
    }

    public Integer getCod_flag() {
        return cod_flag;
    }

    public void setCod_flag(Integer cod_flag) {
        this.cod_flag = cod_flag;
    }

    public String getShiptoname() {
        return shiptoname;
    }

    public void setShiptoname(String shiptoname) {
        this.shiptoname = shiptoname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpress_time() {
        return express_time;
    }

    public void setExpress_time(String express_time) {
        this.express_time = express_time;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getWeight_time() {
        return weight_time;
    }

    public void setWeight_time(String weight_time) {
        this.weight_time = weight_time;
    }

    public List<OrderDetail> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(List<OrderDetail> order_details) {
        this.order_details = order_details;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public String getStaId() {
        return staId;
    }

    public void setStaId(String staId) {
        this.staId = staId;
    }

    public Long getUpper_id() {
        return upper_id;
    }

    public void setUpper_id(Long upper_id) {
        this.upper_id = upper_id;
    }

    public String getPlatform_order_time() {
        return platform_order_time;
    }

    public void setPlatform_order_time(String platform_order_time) {
        this.platform_order_time = platform_order_time;
    }

    public String getPlatform_pay_time() {
        return platform_pay_time;
    }

    public void setPlatform_pay_time(String platform_pay_time) {
        this.platform_pay_time = platform_pay_time;
    }

    public String getK_flag() {
        return k_flag;
    }

    public void setK_flag(String k_flag) {
        this.k_flag = k_flag;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

}
