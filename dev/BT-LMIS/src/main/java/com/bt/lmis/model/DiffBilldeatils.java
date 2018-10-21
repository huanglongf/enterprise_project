package com.bt.lmis.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @ClassName: DiffBilldeatils
* @Description: TODO(DiffBilldeatils)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class DiffBilldeatils  implements Serializable   {
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -6253319187032533139L;
	private Integer id;			//	private java.util.Date create_time;			//	private String create_user;			//	private java.util.Date update_time;			//	private String update_user;			//	private String billingCycle;			//核销周期	private String month_account;			//月结账号	private java.util.Date transport_time;			//发货时间（供应商）	private String waybill;			//	private java.math.BigDecimal transport_weight;			//发货重量（kg）（供应商）	private java.math.BigDecimal transport_volumn;			//体积(长*宽*高）（供应商）	private String origin_province;			//始发地省（供应商）	private String origin_city;			//始发地市（供应商）	private String origin_state;			//始发地区（供应商）	private String dest_province;			//目的地省（供应商）	private String dest_city;			//目的地市（供应商）	private String dest_state;			//目的地区（供应商）	private java.math.BigDecimal charged_weight;			//计费重量（kg）（供应商）	private String express_code;			//vendor_code,contract_owner	private String producttype_code;			//产品类型代码（供应商）Itemtype_code	private java.math.BigDecimal insurance;			//保值（供应商）	private java.math.BigDecimal freight;			//运费（供应商）	private java.math.BigDecimal insurance_fee;			//保价费（供应商）	private java.math.BigDecimal other_value_added_service_charges;			//其他增值服务费（供应商）	private java.math.BigDecimal total_charge;			//合计费用（供应商）
	private java.math.BigDecimal insurance1;			//保价货值（订单金额）
	private java.math.BigDecimal volumn_charged_weight;			//体积计费重量（mm3）
	private String cal_batid;
	private String express_name;
	private String producttype_name;
		private String transport_warehouse;			//发货仓
	private String store;			//店铺	private String cost_center;			//成本中心	private String epistatic_order;			//前置单据号	private String platform_no;			//平台订单号	private String sku_number;			//耗材sku编码	private java.math.BigDecimal length;			//长（mm）	private java.math.BigDecimal width;			//宽（mm）	private java.math.BigDecimal height;			//高（mm）	private java.math.BigDecimal volumn;			//体积（mm3）	private java.util.Date transport_time1;			//发货时间	private String origin_province1;			//	private String origin_city1;			//	private String dest_province1;			//	private String dest_city1;			//	private java.math.BigDecimal transport_weight1;			//	private String express_code1;			//	private String producttype_code1;			//产品类型
	private String account_id;
	private String master_id;
	
	// 计抛基数（报价）	private BigDecimal jpNum;
	// 体积重量（计算得）	private BigDecimal volumnWeight;
	// 体积核算重量（计算得）	private BigDecimal volumnAccountWeight;
	// 计费重量（报价）	private BigDecimal jfWeight;
	// 计费重量（计算得）	private java.math.BigDecimal charged_weight1;
	// 首重（报价）	private java.math.BigDecimal firstWeight;
	// 首重价格（报价）	private BigDecimal firstWeightPrice;
	// 续重（计算得）	private java.math.BigDecimal addedWeight;
	// 续重价格（查询续重报价区间得）	private BigDecimal addedWeightPrice;
	// 标准运费	private java.math.BigDecimal standard_freight;
	// 单运单折扣（报价，顺丰不需赋值）
	private java.math.BigDecimal discount;
	// 折后运费（计算得）		private java.math.BigDecimal afterdiscount_freight;
	// 保价费	private java.math.BigDecimal insurance_fee1;
	//	private java.math.BigDecimal additional_fee;	private java.math.BigDecimal last_fee;
	
	// 0-未结算 1-结算成功 2-结算失败
	private int settleFlag;
	// 结算异常日志
	private String settleErrorReason;
		private String is_verification;//是否核销	private String reason;	private String remarks;
	private java.math.BigDecimal total_discount;  //总运费折扣
		public String getMaster_id() {
		return master_id;
	}
	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public String getBillingCycle() {	    return this.billingCycle;	}	public void setBillingCycle(String billingCycle) {	    this.billingCycle=billingCycle;	}		public java.util.Date getTransport_time() {	    return this.transport_time;	}	public void setTransport_time(java.util.Date transport_time) {	    this.transport_time=transport_time;	}	public String getWaybill() {	    return this.waybill;	}	public void setWaybill(String waybill) {	    this.waybill=waybill;	}	public java.math.BigDecimal getTransport_weight() {	    return this.transport_weight;	}	public void setTransport_weight(java.math.BigDecimal transport_weight) {	    this.transport_weight=transport_weight;	}	public java.math.BigDecimal getTransport_volumn() {	    return this.transport_volumn;	}	public void setTransport_volumn(java.math.BigDecimal transport_volumn) {	    this.transport_volumn=transport_volumn;	}	public String getOrigin_province() {	    return this.origin_province;	}	public void setOrigin_province(String origin_province) {	    this.origin_province=origin_province;	}	public String getOrigin_city() {	    return this.origin_city;	}	public void setOrigin_city(String origin_city) {	    this.origin_city=origin_city;	}	public String getOrigin_state() {	    return this.origin_state;	}	public void setOrigin_state(String origin_state) {	    this.origin_state=origin_state;	}	public String getDest_province() {	    return this.dest_province;	}	public void setDest_province(String dest_province) {	    this.dest_province=dest_province;	}	public String getDest_city() {	    return this.dest_city;	}	public void setDest_city(String dest_city) {	    this.dest_city=dest_city;	}	public String getDest_state() {	    return this.dest_state;	}	public void setDest_state(String dest_state) {	    this.dest_state=dest_state;	}	public java.math.BigDecimal getCharged_weight() {	    return this.charged_weight;	}	public void setCharged_weight(java.math.BigDecimal charged_weight) {	    this.charged_weight=charged_weight;	}	public String getExpress_code() {	    return this.express_code;	}	public void setExpress_code(String express_code) {	    this.express_code=express_code;	}	public String getProducttype_code() {	    return this.producttype_code;	}	public void setProducttype_code(String producttype_code) {	    this.producttype_code=producttype_code;	}	public java.math.BigDecimal getInsurance() {	    return this.insurance;	}	public void setInsurance(java.math.BigDecimal insurance) {	    this.insurance=insurance;	}	public java.math.BigDecimal getFreight() {	    return this.freight;	}	public void setFreight(java.math.BigDecimal freight) {	    this.freight=freight;	}	public java.math.BigDecimal getInsurance_fee() {	    return this.insurance_fee;	}	public void setInsurance_fee(java.math.BigDecimal insurance_fee) {	    this.insurance_fee=insurance_fee;	}	public java.math.BigDecimal getOther_value_added_service_charges() {	    return this.other_value_added_service_charges;	}	public void setOther_value_added_service_charges(java.math.BigDecimal other_value_added_service_charges) {	    this.other_value_added_service_charges=other_value_added_service_charges;	}	public java.math.BigDecimal getTotal_charge() {	    return this.total_charge;	}	public void setTotal_charge(java.math.BigDecimal total_charge) {	    this.total_charge=total_charge;	}	public String getTransport_warehouse() {	    return this.transport_warehouse;	}	public void setTransport_warehouse(String transport_warehouse) {	    this.transport_warehouse=transport_warehouse;	}	public String getStore() {	    return this.store;	}	public void setStore(String store) {	    this.store=store;	}	public String getCost_center() {	    return this.cost_center;	}	public void setCost_center(String cost_center) {	    this.cost_center=cost_center;	}	public String getEpistatic_order() {	    return this.epistatic_order;	}	public void setEpistatic_order(String epistatic_order) {	    this.epistatic_order=epistatic_order;	}	public String getPlatform_no() {	    return this.platform_no;	}	public void setPlatform_no(String platform_no) {	    this.platform_no=platform_no;	}	public String getSku_number() {	    return this.sku_number;	}	public void setSku_number(String sku_number) {	    this.sku_number=sku_number;	}	public java.math.BigDecimal getLength() {	    return this.length;	}	public void setLength(java.math.BigDecimal length) {	    this.length=length;	}	public java.math.BigDecimal getWidth() {	    return this.width;	}	public void setWidth(java.math.BigDecimal width) {	    this.width=width;	}	public java.math.BigDecimal getHeight() {	    return this.height;	}	public void setHeight(java.math.BigDecimal height) {	    this.height=height;	}	public java.math.BigDecimal getVolumn() {	    return this.volumn;	}	public void setVolumn(java.math.BigDecimal volumn) {	    this.volumn=volumn;	}	public java.util.Date getTransport_time1() {	    return this.transport_time1;	}	public void setTransport_time1(java.util.Date transport_time1) {	    this.transport_time1=transport_time1;	}	public String getOrigin_province1() {	    return this.origin_province1;	}	public void setOrigin_province1(String origin_province1) {	    this.origin_province1=origin_province1;	}	public String getOrigin_city1() {	    return this.origin_city1;	}	public void setOrigin_city1(String origin_city1) {	    this.origin_city1=origin_city1;	}	public String getDest_province1() {	    return this.dest_province1;	}	public void setDest_province1(String dest_province1) {	    this.dest_province1=dest_province1;	}	public String getDest_city1() {	    return this.dest_city1;	}	public void setDest_city1(String dest_city1) {	    this.dest_city1=dest_city1;	}	public java.math.BigDecimal getTransport_weight1() {	    return this.transport_weight1;	}	public void setTransport_weight1(java.math.BigDecimal transport_weight1) {	    this.transport_weight1=transport_weight1;	}	public String getExpress_code1() {	    return this.express_code1;	}	public void setExpress_code1(String express_code1) {	    this.express_code1=express_code1;	}	public String getProducttype_code1() {	    return this.producttype_code1;	}	public void setProducttype_code1(String producttype_code1) {	    this.producttype_code1=producttype_code1;	}	public java.math.BigDecimal getInsurance1() {	    return this.insurance1;	}	public void setInsurance1(java.math.BigDecimal insurance1) {	    this.insurance1=insurance1;	}	public java.math.BigDecimal getVolumn_charged_weight() {	    return this.volumn_charged_weight;	}	public void setVolumn_charged_weight(java.math.BigDecimal volumn_charged_weight) {	    this.volumn_charged_weight=volumn_charged_weight;	}	public java.math.BigDecimal getCharged_weight1() {	    return this.charged_weight1;	}	public void setCharged_weight1(java.math.BigDecimal charged_weight1) {	    this.charged_weight1=charged_weight1;	}	public java.math.BigDecimal getFirstWeight() {	    return this.firstWeight;	}	public void setFirstWeight(java.math.BigDecimal firstWeight) {	    this.firstWeight=firstWeight;	}	public java.math.BigDecimal getAddedWeight() {	    return this.addedWeight;	}	public void setAddedWeight(java.math.BigDecimal addedWeight) {	    this.addedWeight=addedWeight;	}	public java.math.BigDecimal getDiscount() {	    return this.discount;	}	public void setDiscount(java.math.BigDecimal discount) {	    this.discount=discount;	}	public java.math.BigDecimal getStandard_freight() {	    return this.standard_freight;	}	public void setStandard_freight(java.math.BigDecimal standard_freight) {	    this.standard_freight=standard_freight;	}	public java.math.BigDecimal getAfterdiscount_freight() {	    return this.afterdiscount_freight;	}	public void setAfterdiscount_freight(java.math.BigDecimal afterdiscount_freight) {	    this.afterdiscount_freight=afterdiscount_freight;	}	public java.math.BigDecimal getInsurance_fee1() {	    return this.insurance_fee1;	}	public void setInsurance_fee1(java.math.BigDecimal insurance_fee1) {	    this.insurance_fee1=insurance_fee1;	}	public java.math.BigDecimal getAdditional_fee() {	    return this.additional_fee;	}	public void setAdditional_fee(java.math.BigDecimal additional_fee) {	    this.additional_fee=additional_fee;	}	public java.math.BigDecimal getLast_fee() {	    return this.last_fee;	}	public void setLast_fee(java.math.BigDecimal last_fee) {	    this.last_fee=last_fee;	}	public String getIs_verification() {	    return this.is_verification;	}	public void setIs_verification(String is_verification) {	    this.is_verification=is_verification;	}	public String getReason() {	    return this.reason;	}	public void setReason(String reason) {	    this.reason=reason;	}	public String getRemarks() {	    return this.remarks;	}	public void setRemarks(String remarks) {	    this.remarks=remarks;	}
	public int getSettleFlag() {
		return settleFlag;
	}
	public void setSettleFlag(int settleFlag) {
		this.settleFlag = settleFlag;
	}
	public String getSettleErrorReason() {
		return settleErrorReason;
	}
	public void setSettleErrorReason(String settleErrorReason) {
		this.settleErrorReason = settleErrorReason;
	}
	public BigDecimal getJpNum() {
		return jpNum;
	}
	public void setJpNum(BigDecimal jpNum) {
		this.jpNum = jpNum;
	}
	public BigDecimal getVolumnWeight() {
		return volumnWeight;
	}
	public void setVolumnWeight(BigDecimal volumnWeight) {
		this.volumnWeight = volumnWeight;
	}
	public BigDecimal getVolumnAccountWeight() {
		return volumnAccountWeight;
	}
	public void setVolumnAccountWeight(BigDecimal volumnAccountWeight) {
		this.volumnAccountWeight = volumnAccountWeight;
	}
	public BigDecimal getJfWeight() {
		return jfWeight;
	}
	public void setJfWeight(BigDecimal jfWeight) {
		this.jfWeight = jfWeight;
	}
	public BigDecimal getFirstWeightPrice() {
		return firstWeightPrice;
	}
	public void setFirstWeightPrice(BigDecimal firstWeightPrice) {
		this.firstWeightPrice = firstWeightPrice;
	}
	public BigDecimal getAddedWeightPrice() {
		return addedWeightPrice;
	}
	public void setAddedWeightPrice(BigDecimal addedWeightPrice) {
		this.addedWeightPrice = addedWeightPrice;
	}
	public java.math.BigDecimal getTotal_discount() {
		return total_discount;
	}
	public void setTotal_discount(java.math.BigDecimal total_discount) {
		this.total_discount = total_discount;
	}
	public String getMonth_account() {
		return month_account;
	}
	public void setMonth_account(String month_account) {
		this.month_account = month_account;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getCal_batid() {
		return cal_batid;
	}
	public void setCal_batid(String cal_batid) {
		this.cal_batid = cal_batid;
	}
	public String getExpress_name() {
		return express_name;
	}
	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}
	public String getProducttype_name() {
		return producttype_name;
	}
	public void setProducttype_name(String producttype_name) {
		this.producttype_name = producttype_name;
	}
	@Override
	public String toString() {
		return "DiffBilldeatils [id=" + id + ", create_time=" + create_time + ", create_user=" + create_user
				+ ", update_time=" + update_time + ", update_user=" + update_user + ", billingCycle=" + billingCycle
				+ ", month_account=" + month_account + ", transport_time=" + transport_time + ", waybill=" + waybill
				+ ", transport_weight=" + transport_weight + ", transport_volumn=" + transport_volumn
				+ ", origin_province=" + origin_province + ", origin_city=" + origin_city + ", origin_state="
				+ origin_state + ", dest_province=" + dest_province + ", dest_city=" + dest_city + ", dest_state="
				+ dest_state + ", charged_weight=" + charged_weight + ", express_code=" + express_code
				+ ", producttype_code=" + producttype_code + ", insurance=" + insurance + ", freight=" + freight
				+ ", insurance_fee=" + insurance_fee + ", other_value_added_service_charges="
				+ other_value_added_service_charges + ", total_charge=" + total_charge + ", insurance1=" + insurance1
				+ ", volumn_charged_weight=" + volumn_charged_weight + ", transport_warehouse=" + transport_warehouse
				+ ", store=" + store + ", cost_center=" + cost_center + ", epistatic_order=" + epistatic_order
				+ ", platform_no=" + platform_no + ", sku_number=" + sku_number + ", length=" + length + ", width="
				+ width + ", height=" + height + ", volumn=" + volumn + ", transport_time1=" + transport_time1
				+ ", origin_province1=" + origin_province1 + ", origin_city1=" + origin_city1 + ", dest_province1="
				+ dest_province1 + ", dest_city1=" + dest_city1 + ", transport_weight1=" + transport_weight1
				+ ", express_code1=" + express_code1 + ", producttype_code1=" + producttype_code1 + ", account_id="
				+ account_id + ", master_id=" + master_id + ", jpNum=" + jpNum + ", volumnWeight=" + volumnWeight
				+ ", volumnAccountWeight=" + volumnAccountWeight + ", jfWeight=" + jfWeight + ", charged_weight1="
				+ charged_weight1 + ", firstWeight=" + firstWeight + ", firstWeightPrice=" + firstWeightPrice
				+ ", addedWeight=" + addedWeight + ", addedWeightPrice=" + addedWeightPrice + ", standard_freight="
				+ standard_freight + ", discount=" + discount + ", afterdiscount_freight=" + afterdiscount_freight
				+ ", insurance_fee1=" + insurance_fee1 + ", additional_fee=" + additional_fee + ", last_fee=" + last_fee
				+ ", settleFlag=" + settleFlag + ", settleErrorReason=" + settleErrorReason + ", is_verification="
				+ is_verification + ", reason=" + reason + ", remarks=" + remarks + ", total_discount=" + total_discount
				+ "]";
	}
	
}
