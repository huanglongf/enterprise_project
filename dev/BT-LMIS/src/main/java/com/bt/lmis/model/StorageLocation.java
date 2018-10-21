package com.bt.lmis.model;

/**
* @ClassName: StorageLocation
* @Description: TODO(StorageLocation)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class StorageLocation {
	
	
	private Integer id;			//
	private java.util.Date create_time;			//
	private String create_user;			//
	private java.util.Date update_time;			//
	private String update_user;			//
	private String warehouse_code;			//
	private String warehouse_name;			//
	private String location_type;			//货位类型
	private String reservoir_code;			//库区编码
	private String passageway_code;			//通道
	private String group_code;			//
	private String line_code;			//行
	private String cell_code;			//格
	private String del_flag;			//1:有效数据
                                         //0 ：无效数据
	private java.math.BigDecimal single_square;			//面积
	private java.math.BigDecimal single_volumn;			//
	private java.math.BigDecimal all_square;			//
	private java.math.BigDecimal all_volumn;			//
	private Integer storage_number;
	private Integer  row_no;
	public Integer getRow_no() {
		return row_no;
	}
	public void setRow_no(Integer row_no) {
		this.row_no = row_no;
	}
	public Integer getStorage_number() {
		return storage_number;
	}
	public void setStorage_number(Integer storage_number) {
		this.storage_number = storage_number;
	}
	public Integer getId() {
	    return this.id;
	}
	public void setId(Integer id) {
	    this.id=id;
	}
	public java.util.Date getCreate_time() {
	    return this.create_time;
	}
	public void setCreate_time(java.util.Date create_time) {
	    this.create_time=create_time;
	}
	public String getCreate_user() {
	    return this.create_user;
	}
	public void setCreate_user(String create_user) {
	    this.create_user=create_user;
	}
	public java.util.Date getUpdate_time() {
	    return this.update_time;
	}
	public void setUpdate_time(java.util.Date update_time) {
	    this.update_time=update_time;
	}
	public String getUpdate_user() {
	    return this.update_user;
	}
	public void setUpdate_user(String update_user) {
	    this.update_user=update_user;
	}
	public String getWarehouse_code() {
	    return this.warehouse_code;
	}
	public void setWarehouse_code(String warehouse_code) {
	    this.warehouse_code=warehouse_code;
	}
	public String getWarehouse_name() {
	    return this.warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
	    this.warehouse_name=warehouse_name;
	}
	public String getLocation_type() {
	    return this.location_type;
	}
	public void setLocation_type(String location_type) {
	    this.location_type=location_type;
	}
	public String getReservoir_code() {
	    return this.reservoir_code;
	}
	public void setReservoir_code(String reservoir_code) {
	    this.reservoir_code=reservoir_code;
	}
	public String getPassageway_code() {
	    return this.passageway_code;
	}
	public void setPassageway_code(String passageway_code) {
	    this.passageway_code=passageway_code;
	}
	public String getGroup_code() {
	    return this.group_code;
	}
	public void setGroup_code(String group_code) {
	    this.group_code=group_code;
	}
	public String getLine_code() {
	    return this.line_code;
	}
	public void setLine_code(String line_code) {
	    this.line_code=line_code;
	}
	public String getCell_code() {
	    return this.cell_code;
	}
	public void setCell_code(String cell_code) {
	    this.cell_code=cell_code;
	}
	public String getDel_flag() {
	    return this.del_flag;
	}
	public void setDel_flag(String del_flag) {
	    this.del_flag=del_flag;
	}
	public java.math.BigDecimal getSingle_square() {
	    return this.single_square;
	}
	public void setSingle_square(java.math.BigDecimal single_square) {
	    this.single_square=single_square;
	}
	public java.math.BigDecimal getSingle_volumn() {
	    return this.single_volumn;
	}
	public void setSingle_volumn(java.math.BigDecimal single_volumn) {
	    this.single_volumn=single_volumn;
	}
	public java.math.BigDecimal getAll_square() {
	    return this.all_square;
	}
	public void setAll_square(java.math.BigDecimal all_square) {
	    this.all_square=all_square;
	}
	public java.math.BigDecimal getAll_volumn() {
	    return this.all_volumn;
	}
	public void setAll_volumn(java.math.BigDecimal all_volumn) {
	    this.all_volumn=all_volumn;
	}
}
