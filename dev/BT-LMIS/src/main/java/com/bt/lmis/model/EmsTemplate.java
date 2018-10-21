package com.bt.lmis.model;

/**
* @ClassName: EmsTemplate
* @Description: TODO(EmsTemplate)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class EmsTemplate {
	
		private Integer id;			//
	private String bat_id;			//批次号	private String delivery_time;			//收寄日期	private String express_number;			//邮件号	private String reach;			//寄达地	private String type;			//类	private String weight;			//重量	private String postage;			//邮资	private String total_fee;			//合计费用	private String balance_postage;			//结算邮资	private String standard_postage;			//标准邮资	private String other_fee;			//其他费	private String product;			//产品	private String num;			//件数	private String num_in;			//内件数	private String payment_amount;			//货款金额	private String big_client;			//大客户	private String receiver;			//揽收员	private String staff_name;			//员工姓名	private String pay_status;			//收款情况	private String package_fee;			//包装费	private String premium;			//保险费	private String insurance;			//保价费	private String declare_fee;			//报关费	private String additional_fee;			//附加费	private String single_fee;			//单式费	private String airport_fee;			//机场费	private String return_fee;			//回执费	private String information_fee;			//信息费	private String receive_fee;			//揽收费	private String other_postage;			//其他资费	private String insurance_procedures;			//保价手续	private String length;			//长	private String width;			//宽	private String higth;			//高	private String volumn_weight;			//体积重	private String organization_number;			//机构编号	private String system_discount;			//系统折扣	private String adjustment_discount;			//调整折扣	private String real_weight;			//实重	private String real_standard_postage;			//实重标准资费	private String balance_freight;			//结算运费	private String weight_different;			//重量差额	private String freight_different;			//运费差额
	
		public String getBat_id() {
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getDelivery_time() {	    return this.delivery_time;	}	public void setDelivery_time(String delivery_time) {	    this.delivery_time=delivery_time;	}	public String getExpress_number() {	    return this.express_number;	}	public void setExpress_number(String express_number) {	    this.express_number=express_number;	}	public String getReach() {	    return this.reach;	}	public void setReach(String reach) {	    this.reach=reach;	}	public String getType() {	    return this.type;	}	public void setType(String type) {	    this.type=type;	}	public String getWeight() {	    return this.weight;	}	public void setWeight(String weight) {	    this.weight=weight;	}	public String getPostage() {	    return this.postage;	}	public void setPostage(String postage) {	    this.postage=postage;	}	public String getTotal_fee() {	    return this.total_fee;	}	public void setTotal_fee(String total_fee) {	    this.total_fee=total_fee;	}	public String getBalance_postage() {	    return this.balance_postage;	}	public void setBalance_postage(String balance_postage) {	    this.balance_postage=balance_postage;	}	public String getStandard_postage() {	    return this.standard_postage;	}	public void setStandard_postage(String standard_postage) {	    this.standard_postage=standard_postage;	}	public String getOther_fee() {	    return this.other_fee;	}	public void setOther_fee(String other_fee) {	    this.other_fee=other_fee;	}	public String getProduct() {	    return this.product;	}	public void setProduct(String product) {	    this.product=product;	}	public String getNum() {	    return this.num;	}	public void setNum(String num) {	    this.num=num;	}	public String getNum_in() {	    return this.num_in;	}	public void setNum_in(String num_in) {	    this.num_in=num_in;	}	public String getPayment_amount() {	    return this.payment_amount;	}	public void setPayment_amount(String payment_amount) {	    this.payment_amount=payment_amount;	}	public String getBig_client() {	    return this.big_client;	}	public void setBig_client(String big_client) {	    this.big_client=big_client;	}	public String getReceiver() {	    return this.receiver;	}	public void setReceiver(String receiver) {	    this.receiver=receiver;	}	public String getStaff_name() {	    return this.staff_name;	}	public void setStaff_name(String staff_name) {	    this.staff_name=staff_name;	}	public String getPay_status() {	    return this.pay_status;	}	public void setPay_status(String pay_status) {	    this.pay_status=pay_status;	}	public String getPackage_fee() {	    return this.package_fee;	}	public void setPackage_fee(String package_fee) {	    this.package_fee=package_fee;	}	public String getPremium() {	    return this.premium;	}	public void setPremium(String premium) {	    this.premium=premium;	}	public String getInsurance() {	    return this.insurance;	}	public void setInsurance(String insurance) {	    this.insurance=insurance;	}	public String getDeclare_fee() {	    return this.declare_fee;	}	public void setDeclare_fee(String declare_fee) {	    this.declare_fee=declare_fee;	}	public String getAdditional_fee() {	    return this.additional_fee;	}	public void setAdditional_fee(String additional_fee) {	    this.additional_fee=additional_fee;	}	public String getSingle_fee() {	    return this.single_fee;	}	public void setSingle_fee(String single_fee) {	    this.single_fee=single_fee;	}	public String getAirport_fee() {	    return this.airport_fee;	}	public void setAirport_fee(String airport_fee) {	    this.airport_fee=airport_fee;	}	public String getReturn_fee() {	    return this.return_fee;	}	public void setReturn_fee(String return_fee) {	    this.return_fee=return_fee;	}	public String getInformation_fee() {	    return this.information_fee;	}	public void setInformation_fee(String information_fee) {	    this.information_fee=information_fee;	}	public String getReceive_fee() {	    return this.receive_fee;	}	public void setReceive_fee(String receive_fee) {	    this.receive_fee=receive_fee;	}	public String getOther_postage() {	    return this.other_postage;	}	public void setOther_postage(String other_postage) {	    this.other_postage=other_postage;	}	public String getInsurance_procedures() {	    return this.insurance_procedures;	}	public void setInsurance_procedures(String insurance_procedures) {	    this.insurance_procedures=insurance_procedures;	}	public String getLength() {	    return this.length;	}	public void setLength(String length) {	    this.length=length;	}	public String getWidth() {	    return this.width;	}	public void setWidth(String width) {	    this.width=width;	}	public String getHigth() {	    return this.higth;	}	public void setHigth(String higth) {	    this.higth=higth;	}	public String getVolumn_weight() {	    return this.volumn_weight;	}	public void setVolumn_weight(String volumn_weight) {	    this.volumn_weight=volumn_weight;	}	public String getOrganization_number() {	    return this.organization_number;	}	public void setOrganization_number(String organization_number) {	    this.organization_number=organization_number;	}	public String getSystem_discount() {	    return this.system_discount;	}	public void setSystem_discount(String system_discount) {	    this.system_discount=system_discount;	}	public String getAdjustment_discount() {	    return this.adjustment_discount;	}	public void setAdjustment_discount(String adjustment_discount) {	    this.adjustment_discount=adjustment_discount;	}	public String getReal_weight() {	    return this.real_weight;	}	public void setReal_weight(String real_weight) {	    this.real_weight=real_weight;	}	public String getReal_standard_postage() {	    return this.real_standard_postage;	}	public void setReal_standard_postage(String real_standard_postage) {	    this.real_standard_postage=real_standard_postage;	}	public String getBalance_freight() {	    return this.balance_freight;	}	public void setBalance_freight(String balance_freight) {	    this.balance_freight=balance_freight;	}	public String getWeight_different() {	    return this.weight_different;	}	public void setWeight_different(String weight_different) {	    this.weight_different=weight_different;	}	public String getFreight_different() {	    return this.freight_different;	}	public void setFreight_different(String freight_different) {	    this.freight_different=freight_different;	}
	public EmsTemplate(String[] str,String bath_id) {
		this.bat_id = bath_id;
		this.delivery_time = str[0];
		this.express_number = str[1];
		this.reach = str[2];
		this.type = str[3];
		this.weight = str[4];
		this.postage = str[5];
		this.total_fee = str[6];
		this.balance_postage = str[7];
		this.standard_postage = str[8];
		this.other_fee = str[9];
		this.product = str[10];
		this.num = str[11];
		this.num_in = str[12];
		this.payment_amount = str[13];
		this.big_client = str[14];
		this.receiver = str[15];
		this.staff_name = str[16];
		this.pay_status = str[17];
		this.package_fee = str[18];
		this.premium = str[19];
		this.insurance = str[20];
		this.declare_fee = str[21];
		this.additional_fee = str[22];
		this.single_fee = str[23];
		this.airport_fee = str[24];
		this.return_fee = str[25];
		this.information_fee = str[26];
		this.receive_fee = str[27];
		this.other_postage = str[28];
		this.insurance_procedures = str[29];
		this.length = str[30];
		this.width = str[31];
		this.higth = str[32];
		this.volumn_weight = str[33];
		this.organization_number = str[34];
		this.system_discount = str[35];
		this.adjustment_discount = str[36];
		this.real_weight = str[37];
		this.real_standard_postage = str[38];
		this.balance_freight = str[39];
		this.weight_different = str[40];
		this.freight_different = str[41];
	}
	
	
}
