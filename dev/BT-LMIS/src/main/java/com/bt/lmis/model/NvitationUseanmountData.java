package com.bt.lmis.model;

/**
* @ClassName: NvitationUseanmountData
* @Description: TODO(NvitationUseanmountData)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class NvitationUseanmountData {
	
		private Integer id;			//主键	private java.util.Date create_time;			//创建时间	private String create_user;			//创建人	private java.util.Date update_time;			//更新时间	private String update_user;			//更新人	private java.util.Date ib_time;			//入库时间	private Integer store_id;			//店铺编码	private String store_name;			//店铺名称	private String vendor;			//供货商	private String inbound_no;			//PO单号	private String barcode;			//条形码	private String bz_number;			//耗材编码	private String item_no;			//	private Integer inbound_qty;			//实际入库数量	private java.math.BigDecimal purchase_price;			//采购单价	private java.math.BigDecimal inbound_money;			//实际到货入库金额	private java.util.Date end_time;			//到期时间	private String paymentdays_type;			//账期类型	private Integer settle_flag;			//结算标识(0:未结算；1：已结算)
	private String sku_name;
	private String starttime;
	private String endtime;
	
	private String park_code;
	private String park_name;
	private String park_cost_center;
	
	private Integer firstResult;
	private Integer maxResult;
		public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public java.util.Date getCreate_time() {	    return this.create_time;	}	public void setCreate_time(java.util.Date create_time) {	    this.create_time=create_time;	}	public String getCreate_user() {	    return this.create_user;	}	public void setCreate_user(String create_user) {	    this.create_user=create_user;	}	public java.util.Date getUpdate_time() {	    return this.update_time;	}	public void setUpdate_time(java.util.Date update_time) {	    this.update_time=update_time;	}	public String getUpdate_user() {	    return this.update_user;	}	public void setUpdate_user(String update_user) {	    this.update_user=update_user;	}	public java.util.Date getIb_time() {	    return this.ib_time;	}	public void setIb_time(java.util.Date ib_time) {	    this.ib_time=ib_time;	}	public Integer getStore_id() {	    return this.store_id;	}	public void setStore_id(Integer store_id) {	    this.store_id=store_id;	}	public String getStore_name() {	    return this.store_name;	}	public void setStore_name(String store_name) {	    this.store_name=store_name;	}	public String getVendor() {	    return this.vendor;	}	public void setVendor(String vendor) {	    this.vendor=vendor;	}	public String getInbound_no() {	    return this.inbound_no;	}	public void setInbound_no(String inbound_no) {	    this.inbound_no=inbound_no;	}	public String getBarcode() {	    return this.barcode;	}	public void setBarcode(String barcode) {	    this.barcode=barcode;	}	public String getBz_number() {	    return this.bz_number;	}	public void setBz_number(String bz_number) {	    this.bz_number=bz_number;	}	public String getItem_no() {	    return this.item_no;	}	public void setItem_no(String item_no) {	    this.item_no=item_no;	}	public Integer getInbound_qty() {	    return this.inbound_qty;	}	public void setInbound_qty(Integer inbound_qty) {	    this.inbound_qty=inbound_qty;	}	public java.math.BigDecimal getPurchase_price() {	    return this.purchase_price;	}	public void setPurchase_price(java.math.BigDecimal purchase_price) {	    this.purchase_price=purchase_price;	}	public java.math.BigDecimal getInbound_money() {	    return this.inbound_money;	}	public void setInbound_money(java.math.BigDecimal inbound_money) {	    this.inbound_money=inbound_money;	}	public java.util.Date getEnd_time() {	    return this.end_time;	}	public void setEnd_time(java.util.Date end_time) {	    this.end_time=end_time;	}	public String getPaymentdays_type() {	    return this.paymentdays_type;	}	public void setPaymentdays_type(String paymentdays_type) {	    this.paymentdays_type=paymentdays_type;	}	public Integer getSettle_flag() {	    return this.settle_flag;	}	public void setSettle_flag(Integer settle_flag) {	    this.settle_flag=settle_flag;	}
	public Integer getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	public Integer getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}
	public String getPark_code() {
		return park_code;
	}
	public void setPark_code(String park_code) {
		this.park_code = park_code;
	}
	public String getPark_name() {
		return park_name;
	}
	public void setPark_name(String park_name) {
		this.park_name = park_name;
	}
	public String getPark_cost_center() {
		return park_cost_center;
	}
	public void setPark_cost_center(String park_cost_center) {
		this.park_cost_center = park_cost_center;
	}
}
