package com.bt.lmis.model;

import java.math.BigDecimal;

/**
* @ClassName: OperationSettlementRule
* @Description: TODO(OperationSettlementRule)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class OperationSettlementRule {
	
		private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private Integer contract_id;			//关联合同主表	private Integer osr_setttle_method;			//结算方式[1固定费用结算2按销售额百分比结算3.按实际发生费用结算]	private Integer osr_fixed_type;			//固定费用类型 1无阶梯 2超过部分	private String osr_fixed_price;			//固定费用单价	private Integer osr_fixed_price_unit;			//固定费用单价单位	private String osr_fixed_exceed_tableid;			//操作费-固定费用超过部分阶梯－表格(tb_operation_settlement_fixed)	private String osr_sales_percent;			//按销售额百分比结算(百分比)	private Integer osr_ibop_fee;			//入库操作费	private java.math.BigDecimal osr_ibop_skuprice;			//入库操作费SKU单价	private String osr_ibop_skuprice_unit;			//入库操作费SKU单价单位	private java.math.BigDecimal osr_ibop_itemprice;			//入库操作费商品单价	private String osr_ibop_itemprice_unit;			//入库操作费商品单价单位	private java.math.BigDecimal osr_ibop_qtyprice;			//入库操作费数量单价	private String osr_ibop_qtyprice_unit;			//入库操作费数量单价单位	private Integer osr_btcobop_fee;			//B2C出库操作费(tb_btcobop_num_table)	private Integer osr_btcobop_numfee;			//B2C出库按件收费	private java.math.BigDecimal osr_btcobop_numprice;			//B2C出库按件收费件数单价	private java.math.BigDecimal osr_btcobop_numdc;			//B2C出库按件收费折扣	private String osr_btcobopnum_tableid;			//B2C出库操作费按件表格ID	private Integer osr_tobtb;			//是否转B2B	private String osr_btcobopbill_tobtb_tableid;			//B2C出库操作费按单出库转B2B表格	private String osr_btcobopbill_tableid;			//B2C出库操作费按单出库表格id	private String osr_btcobopbill_discount_tableid;			//B2C出库操作费按单出库阶梯折扣	private Integer osr_btbobop_fee;			//B2B出库操作费	private java.math.BigDecimal osr_btbobop_billprice;			//B2B出库操作费件数单价	private String osr_btbobop_billprice_unit;			//B2B出库操作费件数单价单位	private java.math.BigDecimal osr_btbobop_skuprice;			//B2B出库操作费SKU单价	private String osr_btbobop_skuprice_unit;			//B2B出库操作费SKU单价单位	private java.math.BigDecimal osr_btbobop_itemprice;			//B2B出库操作费商品单价	private String osr_btbobop_itemprice_unit;			//B2B出库操作费商品单价单位	private Integer osr_btbrtop_fee;			//B2B退仓操作费	private java.math.BigDecimal osr_btbrtop_sku_skuprice;			//B2B退仓操作费按SKU计算SKU类型单价	private String osr_btbrtop_sku_skuprice_unit;			//B2B退仓操作费按SKU计算SKU类型单价单位	private java.math.BigDecimal osr_btbrtop_sku_billprice;			//B2B退仓操作费按SKU计算件数单价	private String osr_btbrtop_sku_billprice_unit;			//B2B退仓操作费按SKU计算件数单价单位	private java.math.BigDecimal osr_btbrtop_bill_billprice;			//B2B退仓操作费按件数计算件数单价	private String osr_btbrtop_bill_billprice_unit;			//B2B退仓操作费按件数计算件数单价单位	private Integer osr_btcrtib_fee;			//B2C退换货入库费	private java.math.BigDecimal osr_btcrtib_bill_billprice;			//B2C退换货入库费按单计算单量单价	private String osr_btcrtib_bill_billprice_unit;			//B2C退换货入库费按单计算单量单价单位	private Integer osr_btcrtib_piece;			//B2C退换货入库费按件计算阶梯模式	private java.math.BigDecimal osr_btcrtib_piece_pieceprice;			//B2C退换货入库费按件计算无阶梯件数单价	private String osr_btcrtib_piece_pieceprice_unit;			//B2C退换货入库费按件计算无阶梯件数单价单位	private String osr_btcrti_tableid;			//B2C退换货按件阶梯表格id	private Integer osr_addservice_fee;			//增值服务费	private Integer osr_qc_fee;			//QC费用	private java.math.BigDecimal osr_qc_pieceprice;			//QC费用件数单价	private String osr_qc_pieceprice_unit;			//	private Integer osr_gift_fee;			//放赠品费用	private java.math.BigDecimal osr_gift_billprice;			//放赠品费用单数单价	private String osr_gift_billprice_unit;			//放赠品费用单数单价单位	private Integer osr_mesurement;			//测量尺寸	private java.math.BigDecimal osr_mesurement_price;			//测量尺寸件数单价	private String osr_mesurement_price_unit;			//	private Integer osr_security_code;			//代贴防伪码	private java.math.BigDecimal osr_security_code_price;			//代贴防伪码件数单价	private String osr_security_code_price_unit;			//代贴防伪码件数单价单位	private Integer osr_itemnum;			//代贴条码	private java.math.BigDecimal osr_itemnum_price;			//	private String osr_itemnum_price_unit;			//代贴条码单价单位	private Integer osr_drop_photo;			//吊牌拍照	private java.math.BigDecimal osr_drop_photo_price;			//吊牌单价件数单价	private String osr_drop_photo_price_unit;			//吊牌单价件数单价单位	private Integer osr_message;			//短信服务费	private java.math.BigDecimal osr_message_price;			//短信服务费单价	private String osr_message_price_unit;			//	private Integer osr_extra_fee;			//	private java.math.BigDecimal osr_extra_fee_price;			//	private String osr_extra_fee_price_unit;			//额外税费以及管理税费单价单位	private Integer osr_change_package;			//更改包装	private java.math.BigDecimal osr_change_package_price;			//更改包装单价	private String osr_change_package_price_unit;			//更改包装单价单位	private Integer osr_notworkingtime;			//非工作时间作业	private java.math.BigDecimal osr_notworkingtime_price;			//非工作时间作业单价	private String osr_notworkingtime_price_unit;			//非工作时间作业单价单位	private Integer osr_waybillpeer;			//货票同行	private java.math.BigDecimal osr_waybillpeer_price;			//货票同行单价	private String osr_waybillpeer_price_unit;			//货票同行单价单位	private Integer osr_emergency_receipt;			//紧急收货	private java.math.BigDecimal osr_emergency_receipt_price;			//紧急收货单价	private String osr_emergency_receipt_price_unit;			//紧急收货单价单位	private Integer osr_cancelorder;			//取消订单拦截	private java.math.BigDecimal osr_cancelorder_price;			//取消订单拦截单价	private String osr_cancelorder_price_unit;			//取消订单拦截单价单位	private Integer osr_makebarfee;			//条码制作人工费	private java.math.BigDecimal osr_makebarfee_price;			//条码制作人工费单价	private String osr_makebarfee_price_unit;			//条码制作人工费单价单位	private Integer osr_landfee;			//卸货费	private java.math.BigDecimal osr_landfee_price;			//卸货费单价	private String osr_landfee_price_util;			//卸货费单价单位
	private String osr_btcobopbill_tobtb_js;
	private String osr_btcobopbill_discount_type;
	private String osr_btcobopbill_discount_tableid2;
	private String osr_zzfwf_status;
	private String osr_zzfwf_detail;
	private String osr_tax_point; 				//税点
	private String allzk;
	private String osr_btcobop_numfees;		//osr_btcobop_numfees,osr_btcobop_numprices,osr_btcobop_numdcs
	private BigDecimal osr_btcobop_numprices;
	private BigDecimal osr_btcobop_numdcs;
	private String osr_btcobopnum_tableids;		//tb_btcobop_num_tables
	

	private BigDecimal osr_btcobop_numpricess;
	private BigDecimal osr_btcobop_numdcss;
	private String osr_btcobopnum_tableidss;		//tb_btcobop_num_tables
	
		public BigDecimal getOsr_btcobop_numpricess() {
		return osr_btcobop_numpricess;
	}
	public void setOsr_btcobop_numpricess(BigDecimal osr_btcobop_numpricess) {
		this.osr_btcobop_numpricess = osr_btcobop_numpricess;
	}
	public BigDecimal getOsr_btcobop_numdcss() {
		return osr_btcobop_numdcss;
	}
	public void setOsr_btcobop_numdcss(BigDecimal osr_btcobop_numdcss) {
		this.osr_btcobop_numdcss = osr_btcobop_numdcss;
	}
	public String getOsr_btcobopnum_tableidss() {
		return osr_btcobopnum_tableidss;
	}
	public void setOsr_btcobopnum_tableidss(String osr_btcobopnum_tableidss) {
		this.osr_btcobopnum_tableidss = osr_btcobopnum_tableidss;
	}
	public String getOsr_btcobopnum_tableids() {
		return osr_btcobopnum_tableids;
	}
	public void setOsr_btcobopnum_tableids(String osr_btcobopnum_tableids) {
		this.osr_btcobopnum_tableids = osr_btcobopnum_tableids;
	}
	public String getOsr_btcobop_numfees() {
		return osr_btcobop_numfees;
	}
	public void setOsr_btcobop_numfees(String osr_btcobop_numfees) {
		this.osr_btcobop_numfees = osr_btcobop_numfees;
	}
	public BigDecimal getOsr_btcobop_numprices() {
		return osr_btcobop_numprices;
	}
	public void setOsr_btcobop_numprices(BigDecimal osr_btcobop_numprices) {
		this.osr_btcobop_numprices = osr_btcobop_numprices;
	}
	public BigDecimal getOsr_btcobop_numdcs() {
		return osr_btcobop_numdcs;
	}
	public void setOsr_btcobop_numdcs(BigDecimal osr_btcobop_numdcs) {
		this.osr_btcobop_numdcs = osr_btcobop_numdcs;
	}
	public String getAllzk() {
		return allzk;
	}
	public void setAllzk(String allzk) {
		this.allzk = allzk;
	}
	public String getOsr_tax_point() {
		return osr_tax_point;
	}
	public void setOsr_tax_point(String osr_tax_point) {
		this.osr_tax_point = osr_tax_point;
	}
	public String getOsr_zzfwf_status() {
		return osr_zzfwf_status;
	}
	public void setOsr_zzfwf_status(String osr_zzfwf_status) {
		this.osr_zzfwf_status = osr_zzfwf_status;
	}
	public String getOsr_zzfwf_detail() {
		return osr_zzfwf_detail;
	}
	public void setOsr_zzfwf_detail(String osr_zzfwf_detail) {
		this.osr_zzfwf_detail = osr_zzfwf_detail;
	}
	public String getOsr_btcobopbill_discount_type() {
		return osr_btcobopbill_discount_type;
	}
	public void setOsr_btcobopbill_discount_type(String osr_btcobopbill_discount_type) {
		this.osr_btcobopbill_discount_type = osr_btcobopbill_discount_type;
	}
	public String getOsr_btcobopbill_discount_tableid2() {
		return osr_btcobopbill_discount_tableid2;
	}
	public void setOsr_btcobopbill_discount_tableid2(String osr_btcobopbill_discount_tableid2) {
		this.osr_btcobopbill_discount_tableid2 = osr_btcobopbill_discount_tableid2;
	}
	public String getOsr_btcobopbill_tobtb_js() {
		return osr_btcobopbill_tobtb_js;
	}
	public void setOsr_btcobopbill_tobtb_js(String osr_btcobopbill_tobtb_js) {
		this.osr_btcobopbill_tobtb_js = osr_btcobopbill_tobtb_js;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public Integer getContract_id() {	    return this.contract_id;	}	public void setContract_id(Integer contract_id) {	    this.contract_id=contract_id;	}	public Integer getOsr_setttle_method() {	    return this.osr_setttle_method;	}	public void setOsr_setttle_method(Integer osr_setttle_method) {	    this.osr_setttle_method=osr_setttle_method;	}	public Integer getOsr_fixed_type() {	    return this.osr_fixed_type;	}	public void setOsr_fixed_type(Integer osr_fixed_type) {	    this.osr_fixed_type=osr_fixed_type;	}	public String getOsr_fixed_price() {	    return this.osr_fixed_price;	}	public void setOsr_fixed_price(String osr_fixed_price) {	    this.osr_fixed_price=osr_fixed_price;	}	public Integer getOsr_fixed_price_unit() {	    return this.osr_fixed_price_unit;	}	public void setOsr_fixed_price_unit(Integer osr_fixed_price_unit) {	    this.osr_fixed_price_unit=osr_fixed_price_unit;	}	public String getOsr_fixed_exceed_tableid() {	    return this.osr_fixed_exceed_tableid;	}	public void setOsr_fixed_exceed_tableid(String osr_fixed_exceed_tableid) {	    this.osr_fixed_exceed_tableid=osr_fixed_exceed_tableid;	}	public String getOsr_sales_percent() {	    return this.osr_sales_percent;	}	public void setOsr_sales_percent(String osr_sales_percent) {	    this.osr_sales_percent=osr_sales_percent;	}	public Integer getOsr_ibop_fee() {	    return this.osr_ibop_fee;	}	public void setOsr_ibop_fee(Integer osr_ibop_fee) {	    this.osr_ibop_fee=osr_ibop_fee;	}	public java.math.BigDecimal getOsr_ibop_skuprice() {	    return this.osr_ibop_skuprice;	}	public void setOsr_ibop_skuprice(java.math.BigDecimal osr_ibop_skuprice) {	    this.osr_ibop_skuprice=osr_ibop_skuprice;	}	public String getOsr_ibop_skuprice_unit() {	    return this.osr_ibop_skuprice_unit;	}	public void setOsr_ibop_skuprice_unit(String osr_ibop_skuprice_unit) {	    this.osr_ibop_skuprice_unit=osr_ibop_skuprice_unit;	}	public java.math.BigDecimal getOsr_ibop_itemprice() {	    return this.osr_ibop_itemprice;	}	public void setOsr_ibop_itemprice(java.math.BigDecimal osr_ibop_itemprice) {	    this.osr_ibop_itemprice=osr_ibop_itemprice;	}	public String getOsr_ibop_itemprice_unit() {	    return this.osr_ibop_itemprice_unit;	}	public void setOsr_ibop_itemprice_unit(String osr_ibop_itemprice_unit) {	    this.osr_ibop_itemprice_unit=osr_ibop_itemprice_unit;	}	public java.math.BigDecimal getOsr_ibop_qtyprice() {	    return this.osr_ibop_qtyprice;	}	public void setOsr_ibop_qtyprice(java.math.BigDecimal osr_ibop_qtyprice) {	    this.osr_ibop_qtyprice=osr_ibop_qtyprice;	}	public String getOsr_ibop_qtyprice_unit() {	    return this.osr_ibop_qtyprice_unit;	}	public void setOsr_ibop_qtyprice_unit(String osr_ibop_qtyprice_unit) {	    this.osr_ibop_qtyprice_unit=osr_ibop_qtyprice_unit;	}	public Integer getOsr_btcobop_fee() {	    return this.osr_btcobop_fee;	}	public void setOsr_btcobop_fee(Integer osr_btcobop_fee) {	    this.osr_btcobop_fee=osr_btcobop_fee;	}	public Integer getOsr_btcobop_numfee() {	    return this.osr_btcobop_numfee;	}	public void setOsr_btcobop_numfee(Integer osr_btcobop_numfee) {	    this.osr_btcobop_numfee=osr_btcobop_numfee;	}	public java.math.BigDecimal getOsr_btcobop_numprice() {	    return this.osr_btcobop_numprice;	}	public void setOsr_btcobop_numprice(java.math.BigDecimal osr_btcobop_numprice) {	    this.osr_btcobop_numprice=osr_btcobop_numprice;	}	public java.math.BigDecimal getOsr_btcobop_numdc() {	    return this.osr_btcobop_numdc;	}	public void setOsr_btcobop_numdc(java.math.BigDecimal osr_btcobop_numdc) {	    this.osr_btcobop_numdc=osr_btcobop_numdc;	}	public Integer getOsr_tobtb() {	    return this.osr_tobtb;	}	public void setOsr_tobtb(Integer osr_tobtb) {	    this.osr_tobtb=osr_tobtb;	}	public Integer getOsr_btbobop_fee() {	    return this.osr_btbobop_fee;	}	public void setOsr_btbobop_fee(Integer osr_btbobop_fee) {	    this.osr_btbobop_fee=osr_btbobop_fee;	}	public java.math.BigDecimal getOsr_btbobop_billprice() {	    return this.osr_btbobop_billprice;	}	public void setOsr_btbobop_billprice(java.math.BigDecimal osr_btbobop_billprice) {	    this.osr_btbobop_billprice=osr_btbobop_billprice;	}	public String getOsr_btbobop_billprice_unit() {	    return this.osr_btbobop_billprice_unit;	}	public void setOsr_btbobop_billprice_unit(String osr_btbobop_billprice_unit) {	    this.osr_btbobop_billprice_unit=osr_btbobop_billprice_unit;	}	public java.math.BigDecimal getOsr_btbobop_skuprice() {	    return this.osr_btbobop_skuprice;	}	public void setOsr_btbobop_skuprice(java.math.BigDecimal osr_btbobop_skuprice) {	    this.osr_btbobop_skuprice=osr_btbobop_skuprice;	}	public String getOsr_btbobop_skuprice_unit() {	    return this.osr_btbobop_skuprice_unit;	}	public void setOsr_btbobop_skuprice_unit(String osr_btbobop_skuprice_unit) {	    this.osr_btbobop_skuprice_unit=osr_btbobop_skuprice_unit;	}	public java.math.BigDecimal getOsr_btbobop_itemprice() {	    return this.osr_btbobop_itemprice;	}	public void setOsr_btbobop_itemprice(java.math.BigDecimal osr_btbobop_itemprice) {	    this.osr_btbobop_itemprice=osr_btbobop_itemprice;	}	public String getOsr_btbobop_itemprice_unit() {	    return this.osr_btbobop_itemprice_unit;	}	public void setOsr_btbobop_itemprice_unit(String osr_btbobop_itemprice_unit) {	    this.osr_btbobop_itemprice_unit=osr_btbobop_itemprice_unit;	}	public Integer getOsr_btbrtop_fee() {	    return this.osr_btbrtop_fee;	}	public void setOsr_btbrtop_fee(Integer osr_btbrtop_fee) {	    this.osr_btbrtop_fee=osr_btbrtop_fee;	}	public java.math.BigDecimal getOsr_btbrtop_sku_skuprice() {	    return this.osr_btbrtop_sku_skuprice;	}	public void setOsr_btbrtop_sku_skuprice(java.math.BigDecimal osr_btbrtop_sku_skuprice) {	    this.osr_btbrtop_sku_skuprice=osr_btbrtop_sku_skuprice;	}	public String getOsr_btbrtop_sku_skuprice_unit() {	    return this.osr_btbrtop_sku_skuprice_unit;	}	public void setOsr_btbrtop_sku_skuprice_unit(String osr_btbrtop_sku_skuprice_unit) {	    this.osr_btbrtop_sku_skuprice_unit=osr_btbrtop_sku_skuprice_unit;	}	public java.math.BigDecimal getOsr_btbrtop_sku_billprice() {	    return this.osr_btbrtop_sku_billprice;	}	public void setOsr_btbrtop_sku_billprice(java.math.BigDecimal osr_btbrtop_sku_billprice) {	    this.osr_btbrtop_sku_billprice=osr_btbrtop_sku_billprice;	}	public String getOsr_btbrtop_sku_billprice_unit() {	    return this.osr_btbrtop_sku_billprice_unit;	}	public void setOsr_btbrtop_sku_billprice_unit(String osr_btbrtop_sku_billprice_unit) {	    this.osr_btbrtop_sku_billprice_unit=osr_btbrtop_sku_billprice_unit;	}	public java.math.BigDecimal getOsr_btbrtop_bill_billprice() {	    return this.osr_btbrtop_bill_billprice;	}	public void setOsr_btbrtop_bill_billprice(java.math.BigDecimal osr_btbrtop_bill_billprice) {	    this.osr_btbrtop_bill_billprice=osr_btbrtop_bill_billprice;	}	public String getOsr_btbrtop_bill_billprice_unit() {	    return this.osr_btbrtop_bill_billprice_unit;	}	public void setOsr_btbrtop_bill_billprice_unit(String osr_btbrtop_bill_billprice_unit) {	    this.osr_btbrtop_bill_billprice_unit=osr_btbrtop_bill_billprice_unit;	}	public Integer getOsr_btcrtib_fee() {	    return this.osr_btcrtib_fee;	}	public void setOsr_btcrtib_fee(Integer osr_btcrtib_fee) {	    this.osr_btcrtib_fee=osr_btcrtib_fee;	}	public java.math.BigDecimal getOsr_btcrtib_bill_billprice() {	    return this.osr_btcrtib_bill_billprice;	}	public void setOsr_btcrtib_bill_billprice(java.math.BigDecimal osr_btcrtib_bill_billprice) {	    this.osr_btcrtib_bill_billprice=osr_btcrtib_bill_billprice;	}	public String getOsr_btcrtib_bill_billprice_unit() {	    return this.osr_btcrtib_bill_billprice_unit;	}	public void setOsr_btcrtib_bill_billprice_unit(String osr_btcrtib_bill_billprice_unit) {	    this.osr_btcrtib_bill_billprice_unit=osr_btcrtib_bill_billprice_unit;	}	public Integer getOsr_btcrtib_piece() {	    return this.osr_btcrtib_piece;	}	public void setOsr_btcrtib_piece(Integer osr_btcrtib_piece) {	    this.osr_btcrtib_piece=osr_btcrtib_piece;	}	public java.math.BigDecimal getOsr_btcrtib_piece_pieceprice() {	    return this.osr_btcrtib_piece_pieceprice;	}	public void setOsr_btcrtib_piece_pieceprice(java.math.BigDecimal osr_btcrtib_piece_pieceprice) {	    this.osr_btcrtib_piece_pieceprice=osr_btcrtib_piece_pieceprice;	}	public String getOsr_btcrtib_piece_pieceprice_unit() {	    return this.osr_btcrtib_piece_pieceprice_unit;	}	public void setOsr_btcrtib_piece_pieceprice_unit(String osr_btcrtib_piece_pieceprice_unit) {	    this.osr_btcrtib_piece_pieceprice_unit=osr_btcrtib_piece_pieceprice_unit;	}	public Integer getOsr_addservice_fee() {	    return this.osr_addservice_fee;	}	public void setOsr_addservice_fee(Integer osr_addservice_fee) {	    this.osr_addservice_fee=osr_addservice_fee;	}	public Integer getOsr_qc_fee() {	    return this.osr_qc_fee;	}	public void setOsr_qc_fee(Integer osr_qc_fee) {	    this.osr_qc_fee=osr_qc_fee;	}	public java.math.BigDecimal getOsr_qc_pieceprice() {	    return this.osr_qc_pieceprice;	}	public void setOsr_qc_pieceprice(java.math.BigDecimal osr_qc_pieceprice) {	    this.osr_qc_pieceprice=osr_qc_pieceprice;	}	public String getOsr_qc_pieceprice_unit() {	    return this.osr_qc_pieceprice_unit;	}	public void setOsr_qc_pieceprice_unit(String osr_qc_pieceprice_unit) {	    this.osr_qc_pieceprice_unit=osr_qc_pieceprice_unit;	}	public Integer getOsr_gift_fee() {	    return this.osr_gift_fee;	}	public void setOsr_gift_fee(Integer osr_gift_fee) {	    this.osr_gift_fee=osr_gift_fee;	}	public java.math.BigDecimal getOsr_gift_billprice() {	    return this.osr_gift_billprice;	}	public void setOsr_gift_billprice(java.math.BigDecimal osr_gift_billprice) {	    this.osr_gift_billprice=osr_gift_billprice;	}	public String getOsr_gift_billprice_unit() {	    return this.osr_gift_billprice_unit;	}	public void setOsr_gift_billprice_unit(String osr_gift_billprice_unit) {	    this.osr_gift_billprice_unit=osr_gift_billprice_unit;	}	public Integer getOsr_mesurement() {	    return this.osr_mesurement;	}	public void setOsr_mesurement(Integer osr_mesurement) {	    this.osr_mesurement=osr_mesurement;	}	public java.math.BigDecimal getOsr_mesurement_price() {	    return this.osr_mesurement_price;	}	public void setOsr_mesurement_price(java.math.BigDecimal osr_mesurement_price) {	    this.osr_mesurement_price=osr_mesurement_price;	}	public String getOsr_mesurement_price_unit() {	    return this.osr_mesurement_price_unit;	}	public void setOsr_mesurement_price_unit(String osr_mesurement_price_unit) {	    this.osr_mesurement_price_unit=osr_mesurement_price_unit;	}	public Integer getOsr_security_code() {	    return this.osr_security_code;	}	public void setOsr_security_code(Integer osr_security_code) {	    this.osr_security_code=osr_security_code;	}	public java.math.BigDecimal getOsr_security_code_price() {	    return this.osr_security_code_price;	}	public void setOsr_security_code_price(java.math.BigDecimal osr_security_code_price) {	    this.osr_security_code_price=osr_security_code_price;	}	public String getOsr_security_code_price_unit() {	    return this.osr_security_code_price_unit;	}	public void setOsr_security_code_price_unit(String osr_security_code_price_unit) {	    this.osr_security_code_price_unit=osr_security_code_price_unit;	}	public Integer getOsr_itemnum() {	    return this.osr_itemnum;	}	public void setOsr_itemnum(Integer osr_itemnum) {	    this.osr_itemnum=osr_itemnum;	}	public java.math.BigDecimal getOsr_itemnum_price() {	    return this.osr_itemnum_price;	}	public void setOsr_itemnum_price(java.math.BigDecimal osr_itemnum_price) {	    this.osr_itemnum_price=osr_itemnum_price;	}	public String getOsr_itemnum_price_unit() {	    return this.osr_itemnum_price_unit;	}	public void setOsr_itemnum_price_unit(String osr_itemnum_price_unit) {	    this.osr_itemnum_price_unit=osr_itemnum_price_unit;	}	public Integer getOsr_drop_photo() {	    return this.osr_drop_photo;	}	public void setOsr_drop_photo(Integer osr_drop_photo) {	    this.osr_drop_photo=osr_drop_photo;	}	public java.math.BigDecimal getOsr_drop_photo_price() {	    return this.osr_drop_photo_price;	}	public void setOsr_drop_photo_price(java.math.BigDecimal osr_drop_photo_price) {	    this.osr_drop_photo_price=osr_drop_photo_price;	}	public String getOsr_drop_photo_price_unit() {	    return this.osr_drop_photo_price_unit;	}	public void setOsr_drop_photo_price_unit(String osr_drop_photo_price_unit) {	    this.osr_drop_photo_price_unit=osr_drop_photo_price_unit;	}	public Integer getOsr_message() {	    return this.osr_message;	}	public void setOsr_message(Integer osr_message) {	    this.osr_message=osr_message;	}	public java.math.BigDecimal getOsr_message_price() {	    return this.osr_message_price;	}	public void setOsr_message_price(java.math.BigDecimal osr_message_price) {	    this.osr_message_price=osr_message_price;	}	public String getOsr_message_price_unit() {	    return this.osr_message_price_unit;	}	public void setOsr_message_price_unit(String osr_message_price_unit) {	    this.osr_message_price_unit=osr_message_price_unit;	}	public Integer getOsr_extra_fee() {	    return this.osr_extra_fee;	}	public void setOsr_extra_fee(Integer osr_extra_fee) {	    this.osr_extra_fee=osr_extra_fee;	}	public java.math.BigDecimal getOsr_extra_fee_price() {	    return this.osr_extra_fee_price;	}	public void setOsr_extra_fee_price(java.math.BigDecimal osr_extra_fee_price) {	    this.osr_extra_fee_price=osr_extra_fee_price;	}	public String getOsr_extra_fee_price_unit() {	    return this.osr_extra_fee_price_unit;	}	public void setOsr_extra_fee_price_unit(String osr_extra_fee_price_unit) {	    this.osr_extra_fee_price_unit=osr_extra_fee_price_unit;	}	public Integer getOsr_change_package() {	    return this.osr_change_package;	}	public void setOsr_change_package(Integer osr_change_package) {	    this.osr_change_package=osr_change_package;	}	public java.math.BigDecimal getOsr_change_package_price() {	    return this.osr_change_package_price;	}	public void setOsr_change_package_price(java.math.BigDecimal osr_change_package_price) {	    this.osr_change_package_price=osr_change_package_price;	}	public String getOsr_change_package_price_unit() {	    return this.osr_change_package_price_unit;	}	public void setOsr_change_package_price_unit(String osr_change_package_price_unit) {	    this.osr_change_package_price_unit=osr_change_package_price_unit;	}	public Integer getOsr_notworkingtime() {	    return this.osr_notworkingtime;	}	public void setOsr_notworkingtime(Integer osr_notworkingtime) {	    this.osr_notworkingtime=osr_notworkingtime;	}	public java.math.BigDecimal getOsr_notworkingtime_price() {	    return this.osr_notworkingtime_price;	}	public void setOsr_notworkingtime_price(java.math.BigDecimal osr_notworkingtime_price) {	    this.osr_notworkingtime_price=osr_notworkingtime_price;	}	public String getOsr_notworkingtime_price_unit() {	    return this.osr_notworkingtime_price_unit;	}	public void setOsr_notworkingtime_price_unit(String osr_notworkingtime_price_unit) {	    this.osr_notworkingtime_price_unit=osr_notworkingtime_price_unit;	}	public Integer getOsr_waybillpeer() {	    return this.osr_waybillpeer;	}	public void setOsr_waybillpeer(Integer osr_waybillpeer) {	    this.osr_waybillpeer=osr_waybillpeer;	}	public java.math.BigDecimal getOsr_waybillpeer_price() {	    return this.osr_waybillpeer_price;	}	public void setOsr_waybillpeer_price(java.math.BigDecimal osr_waybillpeer_price) {	    this.osr_waybillpeer_price=osr_waybillpeer_price;	}	public String getOsr_waybillpeer_price_unit() {	    return this.osr_waybillpeer_price_unit;	}	public void setOsr_waybillpeer_price_unit(String osr_waybillpeer_price_unit) {	    this.osr_waybillpeer_price_unit=osr_waybillpeer_price_unit;	}	public Integer 紧急收货() {	    return this.osr_emergency_receipt;	}	public void setOsr_emergency_receipt(Integer osr_emergency_receipt) {	    this.osr_emergency_receipt=osr_emergency_receipt;	}	public java.math.BigDecimal getOsr_emergency_receipt_price() {	    return this.osr_emergency_receipt_price;	}	public void setOsr_emergency_receipt_price(java.math.BigDecimal osr_emergency_receipt_price) {	    this.osr_emergency_receipt_price=osr_emergency_receipt_price;	}	public String getOsr_emergency_receipt_price_unit() {	    return this.osr_emergency_receipt_price_unit;	}	public void setOsr_emergency_receipt_price_unit(String osr_emergency_receipt_price_unit) {	    this.osr_emergency_receipt_price_unit=osr_emergency_receipt_price_unit;	}	public Integer getOsr_cancelorder() {	    return this.osr_cancelorder;	}	public void setOsr_cancelorder(Integer osr_cancelorder) {	    this.osr_cancelorder=osr_cancelorder;	}	public java.math.BigDecimal getOsr_cancelorder_price() {	    return this.osr_cancelorder_price;	}	public void setOsr_cancelorder_price(java.math.BigDecimal osr_cancelorder_price) {	    this.osr_cancelorder_price=osr_cancelorder_price;	}	public String getOsr_cancelorder_price_unit() {	    return this.osr_cancelorder_price_unit;	}	public void setOsr_cancelorder_price_unit(String osr_cancelorder_price_unit) {	    this.osr_cancelorder_price_unit=osr_cancelorder_price_unit;	}	public Integer getOsr_makebarfee() {	    return this.osr_makebarfee;	}	public void setOsr_makebarfee(Integer osr_makebarfee) {	    this.osr_makebarfee=osr_makebarfee;	}	public java.math.BigDecimal getOsr_makebarfee_price() {	    return this.osr_makebarfee_price;	}	public void setOsr_makebarfee_price(java.math.BigDecimal osr_makebarfee_price) {	    this.osr_makebarfee_price=osr_makebarfee_price;	}	public String getOsr_makebarfee_price_unit() {	    return this.osr_makebarfee_price_unit;	}	public void setOsr_makebarfee_price_unit(String osr_makebarfee_price_unit) {	    this.osr_makebarfee_price_unit=osr_makebarfee_price_unit;	}	public Integer getOsr_landfee() {	    return this.osr_landfee;	}	public void setOsr_landfee(Integer osr_landfee) {	    this.osr_landfee=osr_landfee;	}	public java.math.BigDecimal getOsr_landfee_price() {	    return this.osr_landfee_price;	}	public void setOsr_landfee_price(java.math.BigDecimal osr_landfee_price) {	    this.osr_landfee_price=osr_landfee_price;	}	public String getOsr_btcobopnum_tableid() {
		return osr_btcobopnum_tableid;
	}
	public void setOsr_btcobopnum_tableid(String osr_btcobopnum_tableid) {
		this.osr_btcobopnum_tableid = osr_btcobopnum_tableid;
	}
	public String getOsr_btcobopbill_tobtb_tableid() {
		return osr_btcobopbill_tobtb_tableid;
	}
	public void setOsr_btcobopbill_tobtb_tableid(String osr_btcobopbill_tobtb_tableid) {
		this.osr_btcobopbill_tobtb_tableid = osr_btcobopbill_tobtb_tableid;
	}
	public String getOsr_btcobopbill_tableid() {
		return osr_btcobopbill_tableid;
	}
	public void setOsr_btcobopbill_tableid(String osr_btcobopbill_tableid) {
		this.osr_btcobopbill_tableid = osr_btcobopbill_tableid;
	}
	public String getOsr_btcobopbill_discount_tableid() {
		return osr_btcobopbill_discount_tableid;
	}
	public void setOsr_btcobopbill_discount_tableid(String osr_btcobopbill_discount_tableid) {
		this.osr_btcobopbill_discount_tableid = osr_btcobopbill_discount_tableid;
	}
	public String getOsr_btcrti_tableid() {
		return osr_btcrti_tableid;
	}
	public void setOsr_btcrti_tableid(String osr_btcrti_tableid) {
		this.osr_btcrti_tableid = osr_btcrti_tableid;
	}
	public String getOsr_landfee_price_util() {
		return osr_landfee_price_util;
	}
	public void setOsr_landfee_price_util(String osr_landfee_price_util) {
		this.osr_landfee_price_util = osr_landfee_price_util;
	}
	public Integer getOsr_emergency_receipt() {
		return osr_emergency_receipt;
	}
	
}
