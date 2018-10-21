package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TotalFreightDiscount {
	private int id;
	private int con_id;
	private String belong_to;
	private String product_type;
	private int ladder_type;
	private BigDecimal discount;
	private int discount_uom;
	private Integer compare_1;
	private BigDecimal num_1;
	private Integer uom_1;
	private Integer rel;
	private Integer compare_2;
	private BigDecimal num_2;
	private Integer uom_2;
	private Byte insurance_contain;
	private String create_by;
	private Date create_time;
	private String update_by;
	private Date update_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCon_id() {
		return con_id;
	}
	public void setCon_id(int con_id) {
		this.con_id = con_id;
	}
	public String getBelong_to() {
		return belong_to;
	}
	public void setBelong_to(String belong_to) {
		this.belong_to = belong_to;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public int getLadder_type() {
		return ladder_type;
	}
	public void setLadder_type(int ladder_type) {
		this.ladder_type = ladder_type;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public int getDiscount_uom() {
		return discount_uom;
	}
	public void setDiscount_uom(int discount_uom) {
		this.discount_uom = discount_uom;
	}
	public Integer getCompare_1() {
		return compare_1;
	}
	public void setCompare_1(Integer compare_1) {
		this.compare_1 = compare_1;
	}
	public BigDecimal getNum_1() {
		return num_1;
	}
	public void setNum_1(BigDecimal num_1) {
		this.num_1 = num_1;
	}
	public Integer getUom_1() {
		return uom_1;
	}
	public void setUom_1(Integer uom_1) {
		this.uom_1 = uom_1;
	}
	public Integer getRel() {
		return rel;
	}
	public void setRel(Integer rel) {
		this.rel = rel;
	}
	public Integer getCompare_2() {
		return compare_2;
	}
	public void setCompare_2(Integer compare_2) {
		this.compare_2 = compare_2;
	}
	public BigDecimal getNum_2() {
		return num_2;
	}
	public void setNum_2(BigDecimal num_2) {
		this.num_2 = num_2;
	}
	public Integer getUom_2() {
		return uom_2;
	}
	public void setUom_2(Integer uom_2) {
		this.uom_2 = uom_2;
	}
	public Byte getInsurance_contain() {
		return insurance_contain;
	}
	public void setInsurance_contain(Byte insurance_contain) {
		this.insurance_contain = insurance_contain;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
