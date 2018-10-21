package com.bt.lmis.model;

/**
* @ClassName: PerationfeeDataDailySettlement
* @Description: TODO(PerationfeeDataDailySettlement)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class PerationfeeDataDailySettlement {
	
		private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private Integer contract_id;			//	private String data_date;			//	private java.math.BigDecimal btc_qty;			//B2C订单数量	private String btc_qtyunit;			//B2C订单数量单位	private java.math.BigDecimal btc_fee;			//B2C订单费用	private String btc_remark;			//B2C备注	private java.math.BigDecimal btb_qty;			//B2B订单数量	private String btb_qtyunit;			//B2B订单数量单位	private java.math.BigDecimal btb_fee;			//B2B订单费用	private String btb_remark;			//B2B备注	private java.math.BigDecimal return_qty;			//退货入库订单数量	private String return_qtyunit;			//退货入库数量单位	private java.math.BigDecimal return_fee;			//退货入库订单费用	private String return_remark;			//退货入库备注	private java.math.BigDecimal ib_qty;			//收货上架数量	private String ib_qtyunit;			//收货上架数量单位	private java.math.BigDecimal ib_fee;			//收货上架费用	private String ib_remark;			//收货上架备注

	private java.math.BigDecimal xse_qty;
	private String	xse_qtyunit;
	private java.math.BigDecimal xse_fee;
	private String xse_remark;
	
	private java.math.BigDecimal gd_qty;
	private String	gd_qtyunit;
	private java.math.BigDecimal gd_fee;
	private String gd_remark;
	
		public java.math.BigDecimal getXse_qty() {
		return xse_qty;
	}
	public void setXse_qty(java.math.BigDecimal xse_qty) {
		this.xse_qty = xse_qty;
	}
	public String getXse_qtyunit() {
		return xse_qtyunit;
	}
	public void setXse_qtyunit(String xse_qtyunit) {
		this.xse_qtyunit = xse_qtyunit;
	}
	public java.math.BigDecimal getXse_fee() {
		return xse_fee;
	}
	public void setXse_fee(java.math.BigDecimal xse_fee) {
		this.xse_fee = xse_fee;
	}
	public String getXse_remark() {
		return xse_remark;
	}
	public void setXse_remark(String xse_remark) {
		this.xse_remark = xse_remark;
	}
	public java.math.BigDecimal getGd_qty() {
		return gd_qty;
	}
	public void setGd_qty(java.math.BigDecimal gd_qty) {
		this.gd_qty = gd_qty;
	}
	public String getGd_qtyunit() {
		return gd_qtyunit;
	}
	public void setGd_qtyunit(String gd_qtyunit) {
		this.gd_qtyunit = gd_qtyunit;
	}
	public java.math.BigDecimal getGd_fee() {
		return gd_fee;
	}
	public void setGd_fee(java.math.BigDecimal gd_fee) {
		this.gd_fee = gd_fee;
	}
	public String getGd_remark() {
		return gd_remark;
	}
	public void setGd_remark(String gd_remark) {
		this.gd_remark = gd_remark;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public Integer getContract_id() {	    return this.contract_id;	}	public void setContract_id(Integer contract_id) {	    this.contract_id=contract_id;	}	public String getData_date() {	    return this.data_date;	}	public void setData_date(String data_date) {	    this.data_date=data_date;	}	public java.math.BigDecimal getBtc_qty() {	    return this.btc_qty;	}	public void setBtc_qty(java.math.BigDecimal btc_qty) {	    this.btc_qty=btc_qty;	}	public String getBtc_qtyunit() {	    return this.btc_qtyunit;	}	public void setBtc_qtyunit(String btc_qtyunit) {	    this.btc_qtyunit=btc_qtyunit;	}	public java.math.BigDecimal getBtc_fee() {	    return this.btc_fee;	}	public void setBtc_fee(java.math.BigDecimal btc_fee) {	    this.btc_fee=btc_fee;	}	public String getBtc_remark() {	    return this.btc_remark;	}	public void setBtc_remark(String btc_remark) {	    this.btc_remark=btc_remark;	}	public java.math.BigDecimal getBtb_qty() {	    return this.btb_qty;	}	public void setBtb_qty(java.math.BigDecimal btb_qty) {	    this.btb_qty=btb_qty;	}	public String getBtb_qtyunit() {	    return this.btb_qtyunit;	}	public void setBtb_qtyunit(String btb_qtyunit) {	    this.btb_qtyunit=btb_qtyunit;	}	public java.math.BigDecimal getBtb_fee() {	    return this.btb_fee;	}	public void setBtb_fee(java.math.BigDecimal btb_fee) {	    this.btb_fee=btb_fee;	}	public String getBtb_remark() {	    return this.btb_remark;	}	public void setBtb_remark(String btb_remark) {	    this.btb_remark=btb_remark;	}	public java.math.BigDecimal getReturn_qty() {	    return this.return_qty;	}	public void setReturn_qty(java.math.BigDecimal return_qty) {	    this.return_qty=return_qty;	}	public String getReturn_qtyunit() {	    return this.return_qtyunit;	}	public void setReturn_qtyunit(String return_qtyunit) {	    this.return_qtyunit=return_qtyunit;	}	public java.math.BigDecimal getReturn_fee() {	    return this.return_fee;	}	public void setReturn_fee(java.math.BigDecimal return_fee) {	    this.return_fee=return_fee;	}	public String getReturn_remark() {	    return this.return_remark;	}	public void setReturn_remark(String return_remark) {	    this.return_remark=return_remark;	}	public java.math.BigDecimal getIb_qty() {	    return this.ib_qty;	}	public void setIb_qty(java.math.BigDecimal ib_qty) {	    this.ib_qty=ib_qty;	}	public String getIb_qtyunit() {	    return this.ib_qtyunit;	}	public void setIb_qtyunit(String ib_qtyunit) {	    this.ib_qtyunit=ib_qtyunit;	}	public java.math.BigDecimal getIb_fee() {	    return this.ib_fee;	}	public void setIb_fee(java.math.BigDecimal ib_fee) {	    this.ib_fee=ib_fee;	}	public String getIb_remark() {	    return this.ib_remark;	}	public void setIb_remark(String ib_remark) {	    this.ib_remark=ib_remark;	}
}
