package com.bt.lmis.model;

/**
* @ClassName: StorageDataGroup
* @Description: TODO(StorageDataGroup)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class StorageDataGroup {
	
		private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private Integer contract_id;			//合同id	private String billing_cycle;			//账单周期	private java.math.BigDecimal fixed_qty;			//固定费用结算面积	private String fixed_unit;			//固定费用结算面积单位	private java.math.BigDecimal fixed_cost;			//固定费用合计	private String fixed_comment;			//固定费用备注	private java.math.BigDecimal tray_qty;			//货物托盘费数量	private String tray_qtyunit;			//货物托盘费数量单位	private java.math.BigDecimal tray_cost;			//货物托盘结算合计	private String tray_comment;			//货物托盘备注	private java.math.BigDecimal area_qty;			//面积大小	private String area_costunit;			//面积单位	private java.math.BigDecimal area_cost;			//面积汇总金额	private String area_comment;			//面积备注	private java.math.BigDecimal piece_qty;			//	private String piece_unit;			//	private java.math.BigDecimal piece_cost;			//	private String piece_comment;			//
	private String batch;
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public Integer getContract_id() {	    return this.contract_id;	}	public void setContract_id(Integer contract_id) {	    this.contract_id=contract_id;	}	public String getBilling_cycle() {	    return this.billing_cycle;	}	public void setBilling_cycle(String billing_cycle) {	    this.billing_cycle=billing_cycle;	}	public String getFixed_unit() {	    return this.fixed_unit;	}	public void setFixed_unit(String fixed_unit) {	    this.fixed_unit=fixed_unit;	}	public java.math.BigDecimal getFixed_cost() {	    return this.fixed_cost;	}	public void setFixed_cost(java.math.BigDecimal fixed_cost) {	    this.fixed_cost=fixed_cost;	}	public String getFixed_comment() {	    return this.fixed_comment;	}	public void setFixed_comment(String fixed_comment) {	    this.fixed_comment=fixed_comment;	}	public String getTray_qtyunit() {	    return this.tray_qtyunit;	}	public void setTray_qtyunit(String tray_qtyunit) {	    this.tray_qtyunit=tray_qtyunit;	}	public java.math.BigDecimal getTray_cost() {	    return this.tray_cost;	}	public void setTray_cost(java.math.BigDecimal tray_cost) {	    this.tray_cost=tray_cost;	}	public String getTray_comment() {	    return this.tray_comment;	}	public void setTray_comment(String tray_comment) {	    this.tray_comment=tray_comment;	}	public java.math.BigDecimal getArea_qty() {	    return this.area_qty;	}	public void setArea_qty(java.math.BigDecimal area_qty) {	    this.area_qty=area_qty;	}	public String getArea_costunit() {	    return this.area_costunit;	}	public void setArea_costunit(String area_costunit) {	    this.area_costunit=area_costunit;	}	public java.math.BigDecimal getArea_cost() {	    return this.area_cost;	}	public void setArea_cost(java.math.BigDecimal area_cost) {	    this.area_cost=area_cost;	}	public String getArea_comment() {	    return this.area_comment;	}	public void setArea_comment(String area_comment) {	    this.area_comment=area_comment;	}	public java.math.BigDecimal getPiece_qty() {	    return this.piece_qty;	}	public void setPiece_qty(java.math.BigDecimal piece_qty) {	    this.piece_qty=piece_qty;	}	public String getPiece_unit() {	    return this.piece_unit;	}	public void setPiece_unit(String piece_unit) {	    this.piece_unit=piece_unit;	}	public java.math.BigDecimal getPiece_cost() {	    return this.piece_cost;	}	public void setPiece_cost(java.math.BigDecimal piece_cost) {	    this.piece_cost=piece_cost;	}	public String getPiece_comment() {	    return this.piece_comment;	}	public void setPiece_comment(String piece_comment) {	    this.piece_comment=piece_comment;	}
	public java.math.BigDecimal getFixed_qty() {
		return fixed_qty;
	}
	public void setFixed_qty(java.math.BigDecimal fixed_qty) {
		this.fixed_qty = fixed_qty;
	}
	public java.math.BigDecimal getTray_qty() {
		return tray_qty;
	}
	public void setTray_qty(java.math.BigDecimal tray_qty) {
		this.tray_qty = tray_qty;
	}
	
}
