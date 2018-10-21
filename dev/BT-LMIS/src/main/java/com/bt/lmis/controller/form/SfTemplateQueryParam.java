package com.bt.lmis.controller.form;

import com.bt.lmis.page.QueryParameter;

public class SfTemplateQueryParam extends QueryParameter {
	
	
	private Integer id;			//
	private String bat_id;			//批次号
	private String date;			//日期
	private String express_number;			//运单号码
	private String other_area;			//对方地区
	private String other_company_name;			//对方公司名称
	private String charge_weight;			//计费重量
	private String product_type;			//产品类型
	private String pay_method;			//付款方式
	private String fee;			//费用(元)
	private String insurance;			//保费
	private String delegated_picked;			//委托取件/逆向物流
	private String sumsung_project;			//三星项目
	private String fragile_pieces;			//易碎件
	private String amount_payable;			//应付金额
	private String operator;			//经手人
	private String source;			//原寄地
	private String deliver_company_name;			//寄件公司名称
	private String deliver_company_phone;			//寄件公司电话
	private String reach;			//到件地区
	private String reach_client_name;			//到方客户名称
	private String reach_client_phone;			//到方客户电话
	private String sender;			//寄件人
	private String send_time;			//寄件时间
	private String origin_province;			//始发地(省名)
	private String send_company_address;			//寄件公司地址
	private String recipient_no;			//收件人工号
	private String deliver_content;			//托寄物内容
	private String deliver_num;			//托寄物数量
	private String value;			//声明价值
	private String num;			//件数
	private String volumn;			//体积
	private String dest_province;			//目的地(省名)
	private String recipient;			//收件人
	private String recipient_address;			//收件地址
	private String courier_no;			//派件员工号
	private String weight;			//实际重量
	private String return_order_no;			//回单单号
	private String receiver;			//签收人
	private String recipient_time;			//签收时间
	private String return_related_express_number;			//退回件关联运单号
	private String default1;			//附加字段1
	private String default2;			//附加字段2
	private String default3;			//附加字段3
	private String balance_date;			//结算日期
	private String pay_net_node;			//付款网点
	private String subcompany_card_number;			//子公司卡号
	private String remark;			//备注
	private String express_content;			//快件内容
	private String reserve_column3;			//预留字段3
	private String reserve_column4;			//预留字段4
	private String reserve_column5;			//预留字段5
	private String delegated_picked_remark;			//委托费备注说明
	private String firstWeight;			//首重
	private String addedWeight;			//续重
	private String remark2;			//备注
	public Integer getId() {
	    return this.id;
	}
	public void setId(Integer id) {
	    this.id=id;
	}
	public String getBat_id() {
	    return this.bat_id;
	}
	public void setBat_id(String bat_id) {
	    this.bat_id=bat_id;
	}
	public String getDate() {
	    return this.date;
	}
	public void setDate(String date) {
	    this.date=date;
	}
	public String getExpress_number() {
	    return this.express_number;
	}
	public void setExpress_number(String express_number) {
	    this.express_number=express_number;
	}
	public String getOther_area() {
	    return this.other_area;
	}
	public void setOther_area(String other_area) {
	    this.other_area=other_area;
	}
	public String getOther_company_name() {
	    return this.other_company_name;
	}
	public void setOther_company_name(String other_company_name) {
	    this.other_company_name=other_company_name;
	}
	public String getCharge_weight() {
	    return this.charge_weight;
	}
	public void setCharge_weight(String charge_weight) {
	    this.charge_weight=charge_weight;
	}
	public String getProduct_type() {
	    return this.product_type;
	}
	public void setProduct_type(String product_type) {
	    this.product_type=product_type;
	}
	public String getPay_method() {
	    return this.pay_method;
	}
	public void setPay_method(String pay_method) {
	    this.pay_method=pay_method;
	}
	public String getFee() {
	    return this.fee;
	}
	public void setFee(String fee) {
	    this.fee=fee;
	}
	public String getInsurance() {
	    return this.insurance;
	}
	public void setInsurance(String insurance) {
	    this.insurance=insurance;
	}
	public String getDelegated_picked() {
	    return this.delegated_picked;
	}
	public void setDelegated_picked(String delegated_picked) {
	    this.delegated_picked=delegated_picked;
	}
	public String getSumsung_project() {
	    return this.sumsung_project;
	}
	public void setSumsung_project(String sumsung_project) {
	    this.sumsung_project=sumsung_project;
	}
	public String getFragile_pieces() {
	    return this.fragile_pieces;
	}
	public void setFragile_pieces(String fragile_pieces) {
	    this.fragile_pieces=fragile_pieces;
	}
	public String getAmount_payable() {
	    return this.amount_payable;
	}
	public void setAmount_payable(String amount_payable) {
	    this.amount_payable=amount_payable;
	}
	public String getOperator() {
	    return this.operator;
	}
	public void setOperator(String operator) {
	    this.operator=operator;
	}
	public String getSource() {
	    return this.source;
	}
	public void setSource(String source) {
	    this.source=source;
	}
	public String getDeliver_company_name() {
	    return this.deliver_company_name;
	}
	public void setDeliver_company_name(String deliver_company_name) {
	    this.deliver_company_name=deliver_company_name;
	}
	public String getDeliver_company_phone() {
	    return this.deliver_company_phone;
	}
	public void setDeliver_company_phone(String deliver_company_phone) {
	    this.deliver_company_phone=deliver_company_phone;
	}
	public String getReach() {
	    return this.reach;
	}
	public void setReach(String reach) {
	    this.reach=reach;
	}
	public String getReach_client_name() {
	    return this.reach_client_name;
	}
	public void setReach_client_name(String reach_client_name) {
	    this.reach_client_name=reach_client_name;
	}
	public String getReach_client_phone() {
	    return this.reach_client_phone;
	}
	public void setReach_client_phone(String reach_client_phone) {
	    this.reach_client_phone=reach_client_phone;
	}
	public String getSender() {
	    return this.sender;
	}
	public void setSender(String sender) {
	    this.sender=sender;
	}
	public String getSend_time() {
	    return this.send_time;
	}
	public void setSend_time(String send_time) {
	    this.send_time=send_time;
	}
	public String getOrigin_province() {
	    return this.origin_province;
	}
	public void setOrigin_province(String origin_province) {
	    this.origin_province=origin_province;
	}
	public String getSend_company_address() {
	    return this.send_company_address;
	}
	public void setSend_company_address(String send_company_address) {
	    this.send_company_address=send_company_address;
	}
	public String getRecipient_no() {
	    return this.recipient_no;
	}
	public void setRecipient_no(String recipient_no) {
	    this.recipient_no=recipient_no;
	}
	public String getDeliver_content() {
	    return this.deliver_content;
	}
	public void setDeliver_content(String deliver_content) {
	    this.deliver_content=deliver_content;
	}
	public String getDeliver_num() {
	    return this.deliver_num;
	}
	public void setDeliver_num(String deliver_num) {
	    this.deliver_num=deliver_num;
	}
	public String getValue() {
	    return this.value;
	}
	public void setValue(String value) {
	    this.value=value;
	}
	public String getNum() {
	    return this.num;
	}
	public void setNum(String num) {
	    this.num=num;
	}
	public String getVolumn() {
	    return this.volumn;
	}
	public void setVolumn(String volumn) {
	    this.volumn=volumn;
	}
	public String getDest_province() {
	    return this.dest_province;
	}
	public void setDest_province(String dest_province) {
	    this.dest_province=dest_province;
	}
	public String getRecipient() {
	    return this.recipient;
	}
	public void setRecipient(String recipient) {
	    this.recipient=recipient;
	}
	public String getRecipient_address() {
	    return this.recipient_address;
	}
	public void setRecipient_address(String recipient_address) {
	    this.recipient_address=recipient_address;
	}
	public String getCourier_no() {
	    return this.courier_no;
	}
	public void setCourier_no(String courier_no) {
	    this.courier_no=courier_no;
	}
	public String getWeight() {
	    return this.weight;
	}
	public void setWeight(String weight) {
	    this.weight=weight;
	}
	public String getReturn_order_no() {
	    return this.return_order_no;
	}
	public void setReturn_order_no(String return_order_no) {
	    this.return_order_no=return_order_no;
	}
	public String getReceiver() {
	    return this.receiver;
	}
	public void setReceiver(String receiver) {
	    this.receiver=receiver;
	}
	public String getRecipient_time() {
	    return this.recipient_time;
	}
	public void setRecipient_time(String recipient_time) {
	    this.recipient_time=recipient_time;
	}
	public String getReturn_related_express_number() {
	    return this.return_related_express_number;
	}
	public void setReturn_related_express_number(String return_related_express_number) {
	    this.return_related_express_number=return_related_express_number;
	}
	public String getDefault1() {
	    return this.default1;
	}
	public void setDefault1(String default1) {
	    this.default1=default1;
	}
	public String getDefault2() {
	    return this.default2;
	}
	public void setDefault2(String default2) {
	    this.default2=default2;
	}
	public String getDefault3() {
	    return this.default3;
	}
	public void setDefault3(String default3) {
	    this.default3=default3;
	}
	public String getBalance_date() {
	    return this.balance_date;
	}
	public void setBalance_date(String balance_date) {
	    this.balance_date=balance_date;
	}
	public String getPay_net_node() {
	    return this.pay_net_node;
	}
	public void setPay_net_node(String pay_net_node) {
	    this.pay_net_node=pay_net_node;
	}
	public String getSubcompany_card_number() {
	    return this.subcompany_card_number;
	}
	public void setSubcompany_card_number(String subcompany_card_number) {
	    this.subcompany_card_number=subcompany_card_number;
	}
	public String getRemark() {
	    return this.remark;
	}
	public void setRemark(String remark) {
	    this.remark=remark;
	}
	public String getExpress_content() {
	    return this.express_content;
	}
	public void setExpress_content(String express_content) {
	    this.express_content=express_content;
	}
	public String getReserve_column3() {
	    return this.reserve_column3;
	}
	public void setReserve_column3(String reserve_column3) {
	    this.reserve_column3=reserve_column3;
	}
	public String getReserve_column4() {
	    return this.reserve_column4;
	}
	public void setReserve_column4(String reserve_column4) {
	    this.reserve_column4=reserve_column4;
	}
	public String getReserve_column5() {
	    return this.reserve_column5;
	}
	public void setReserve_column5(String reserve_column5) {
	    this.reserve_column5=reserve_column5;
	}
	public String getDelegated_picked_remark() {
	    return this.delegated_picked_remark;
	}
	public void setDelegated_picked_remark(String delegated_picked_remark) {
	    this.delegated_picked_remark=delegated_picked_remark;
	}
	public String getFirstWeight() {
	    return this.firstWeight;
	}
	public void setFirstWeight(String firstWeight) {
	    this.firstWeight=firstWeight;
	}
	public String getAddedWeight() {
	    return this.addedWeight;
	}
	public void setAddedWeight(String addedWeight) {
	    this.addedWeight=addedWeight;
	}
	public String getRemark2() {
	    return this.remark2;
	}
	public void setRemark2(String remark2) {
	    this.remark2=remark2;
	}

}
