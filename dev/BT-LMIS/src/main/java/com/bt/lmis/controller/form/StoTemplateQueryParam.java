package com.bt.lmis.controller.form;

import com.bt.lmis.page.QueryParameter;

public class StoTemplateQueryParam extends QueryParameter{
	
	
	private Integer id;			//
	private String express_bill;			//快递账单
	private String transport_product_type;			//运输产品类别
	private String transport_direction;			//运输方向（正向运输/逆向退货）
	private String deliver_date;			//发货日期
	private String transport_time;			//运输时间
	private String express_number;			//运单号
	private String store;			//店铺/品牌
	private String order_number;			//订单号/指令号
	private String orign;			//始发地
	private String province_dest;			//目的省
	private String city_dest;			//市区
	private String weight;			//实际重量
	private String sku_number;			//SKU编码
	private String length;			//长
	private String width;			//宽
	private String higth;			//高
	private String volumn;			//体积
	private String order_amount;			//订单金额
	private String firstWeigthPrice;			//首重报价
	private String addedWeightPrice;			//续重报价
	private String discount;			//折扣
	private String standard_freight;			//标准运费
	private String afterDiscount_freight;			//折后运费
	private String insurance;			//保价费
	private String other;			//其他
	private String total_fee;			//合计费用
	private String remark;			//备注
	private String bat_id;			//批次号
	
	public String getBat_id() {
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}
	public Integer getId() {
	    return this.id;
	}
	public void setId(Integer id) {
	    this.id=id;
	}
	public String getExpress_bill() {
	    return this.express_bill;
	}
	public void setExpress_bill(String express_bill) {
	    this.express_bill=express_bill;
	}
	public String getTransport_product_type() {
	    return this.transport_product_type;
	}
	public void setTransport_product_type(String transport_product_type) {
	    this.transport_product_type=transport_product_type;
	}
	public String getTransport_direction() {
	    return this.transport_direction;
	}
	public void setTransport_direction(String transport_direction) {
	    this.transport_direction=transport_direction;
	}
	public String getDeliver_date() {
	    return this.deliver_date;
	}
	public void setDeliver_date(String deliver_date) {
	    this.deliver_date=deliver_date;
	}
	public String getTransport_time() {
	    return this.transport_time;
	}
	public void setTransport_time(String transport_time) {
	    this.transport_time=transport_time;
	}
	public String getExpress_number() {
	    return this.express_number;
	}
	public void setExpress_number(String express_number) {
	    this.express_number=express_number;
	}
	public String getStore() {
	    return this.store;
	}
	public void setStore(String store) {
	    this.store=store;
	}
	public String getOrder_number() {
	    return this.order_number;
	}
	public void setOrder_number(String order_number) {
	    this.order_number=order_number;
	}
	public String getOrign() {
	    return this.orign;
	}
	public void setOrign(String orign) {
	    this.orign=orign;
	}
	public String getProvince_dest() {
	    return this.province_dest;
	}
	public void setProvince_dest(String province_dest) {
	    this.province_dest=province_dest;
	}
	public String getCity_dest() {
	    return this.city_dest;
	}
	public void setCity_dest(String city_dest) {
	    this.city_dest=city_dest;
	}
	public String getWeight() {
	    return this.weight;
	}
	public void setWeight(String weight) {
	    this.weight=weight;
	}
	public String getSku_number() {
	    return this.sku_number;
	}
	public void setSku_number(String sku_number) {
	    this.sku_number=sku_number;
	}
	public String getLength() {
	    return this.length;
	}
	public void setLength(String length) {
	    this.length=length;
	}
	public String getWidth() {
	    return this.width;
	}
	public void setWidth(String width) {
	    this.width=width;
	}
	public String getHigth() {
	    return this.higth;
	}
	public void setHigth(String higth) {
	    this.higth=higth;
	}
	public String getVolumn() {
	    return this.volumn;
	}
	public void setVolumn(String volumn) {
	    this.volumn=volumn;
	}
	public String getOrder_amount() {
	    return this.order_amount;
	}
	public void setOrder_amount(String order_amount) {
	    this.order_amount=order_amount;
	}
	public String getFirstWeigthPrice() {
	    return this.firstWeigthPrice;
	}
	public void setFirstWeigthPrice(String firstWeigthPrice) {
	    this.firstWeigthPrice=firstWeigthPrice;
	}
	public String getAddedWeightPrice() {
	    return this.addedWeightPrice;
	}
	public void setAddedWeightPrice(String addedWeightPrice) {
	    this.addedWeightPrice=addedWeightPrice;
	}
	public String getDiscount() {
	    return this.discount;
	}
	public void setDiscount(String discount) {
	    this.discount=discount;
	}
	public String getStandard_freight() {
	    return this.standard_freight;
	}
	public void setStandard_freight(String standard_freight) {
	    this.standard_freight=standard_freight;
	}
	public String getAfterDiscount_freight() {
	    return this.afterDiscount_freight;
	}
	public void setAfterDiscount_freight(String afterDiscount_freight) {
	    this.afterDiscount_freight=afterDiscount_freight;
	}
	public String getInsurance() {
	    return this.insurance;
	}
	public void setInsurance(String insurance) {
	    this.insurance=insurance;
	}
	public String getOther() {
	    return this.other;
	}
	public void setOther(String other) {
	    this.other=other;
	}
	public String getTotal_fee() {
	    return this.total_fee;
	}
	public void setTotal_fee(String total_fee) {
	    this.total_fee=total_fee;
	}
	public String getRemark() {
	    return this.remark;
	}
	public void setRemark(String remark) {
	    this.remark=remark;
	}
}
