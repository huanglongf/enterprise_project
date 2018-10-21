package com.bt.workOrder.model;

import java.math.BigDecimal;
import java.util.Date;

public class ClaimDetail {
	private String claimDetailId;
	private String create_by;
	private Date create_time;
	private String update_by;
	private Date update_time;
	private String wo_id;
	private String related_number;
	private String sku_number;
	private String bar_code;
	private String sku_name;
	private String extended_name;
	private BigDecimal sku_num;
	private BigDecimal platform_price;
	private BigDecimal platform_price_all;
	private BigDecimal claim_num_applied;
	private BigDecimal claim_price_applied;
	private BigDecimal claim_value_applied;
	private BigDecimal claim_num_agreed;
	private BigDecimal claim_value_ld;
	private BigDecimal claim_value_tv;
	private BigDecimal compensate_value_ld;
	private BigDecimal compensate_value_tv;
	private BigDecimal total_claim_value;
	private String remark;
	private Boolean dFlag;
	public String getClaimDetailId() {
		return claimDetailId;
	}
	public void setClaimDetailId(String claimDetailId) {
		this.claimDetailId = claimDetailId;
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
	public String getWo_id() {
		return wo_id;
	}
	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}
	public String getRelated_number() {
		return related_number;
	}
	public void setRelated_number(String related_number) {
		this.related_number = related_number;
	}
	public String getSku_number() {
		return sku_number;
	}
	public void setSku_number(String sku_number) {
		this.sku_number = sku_number;
	}
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public String getExtended_name() {
		return extended_name;
	}
	public void setExtended_name(String extended_name) {
		this.extended_name = extended_name;
	}
	public BigDecimal getSku_num() {
		return sku_num;
	}
	public void setSku_num(BigDecimal sku_num) {
		this.sku_num = sku_num;
	}
	public BigDecimal getPlatform_price() {
		return platform_price;
	}
	public void setPlatform_price(BigDecimal platform_price) {
		this.platform_price = platform_price;
	}
	public BigDecimal getPlatform_price_all() {
		return platform_price_all;
	}
	public void setPlatform_price_all(BigDecimal platform_price_all) {
		this.platform_price_all = platform_price_all;
	}
	public BigDecimal getClaim_num_applied() {
		return claim_num_applied;
	}
	public void setClaim_num_applied(BigDecimal claim_num_applied) {
		this.claim_num_applied = claim_num_applied;
	}
	public BigDecimal getClaim_price_applied() {
		return claim_price_applied;
	}
	public void setClaim_price_applied(BigDecimal claim_price_applied) {
		this.claim_price_applied = claim_price_applied;
	}
	public BigDecimal getClaim_value_applied() {
		return claim_value_applied;
	}
	public void setClaim_value_applied(BigDecimal claim_value_applied) {
		this.claim_value_applied = claim_value_applied;
	}
	public BigDecimal getClaim_num_agreed() {
		return claim_num_agreed;
	}
	public void setClaim_num_agreed(BigDecimal claim_num_agreed) {
		this.claim_num_agreed = claim_num_agreed;
	}
	public BigDecimal getClaim_value_ld() {
		return claim_value_ld;
	}
	public void setClaim_value_ld(BigDecimal claim_value_ld) {
		this.claim_value_ld = claim_value_ld;
	}
	public BigDecimal getClaim_value_tv() {
		return claim_value_tv;
	}
	public void setClaim_value_tv(BigDecimal claim_value_tv) {
		this.claim_value_tv = claim_value_tv;
	}
	public BigDecimal getCompensate_value_ld() {
		return compensate_value_ld;
	}
	public void setCompensate_value_ld(BigDecimal compensate_value_ld) {
		this.compensate_value_ld = compensate_value_ld;
	}
	public BigDecimal getCompensate_value_tv() {
		return compensate_value_tv;
	}
	public void setCompensate_value_tv(BigDecimal compensate_value_tv) {
		this.compensate_value_tv = compensate_value_tv;
	}
	public BigDecimal getTotal_claim_value() {
		return total_claim_value;
	}
	public void setTotal_claim_value(BigDecimal total_claim_value) {
		this.total_claim_value = total_claim_value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getdFlag() {
		return dFlag;
	}
	public void setdFlag(Boolean dFlag) {
		this.dFlag = dFlag;
	}
	
}
