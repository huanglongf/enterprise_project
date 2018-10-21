package com.bt.lmis.balance.controller.form;

import java.util.List;
import java.util.Map;

import com.bt.lmis.page.QueryParameter;
import com.bt.utils.CommonUtils;

public class EstimateQueryParam extends QueryParameter {
	
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -3039532179069472431L;
	private String pageName;
	private List<String> estimate_id;
	private String batch_number;
	private String batch_status;
	private String date_domain;
	private String domain_from;
	private String domain_to;
	private String estimate_type;
	private String contract_in_estimate;
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public List<String> getEstimate_id() {
		return estimate_id;
	}
	public void setEstimate_id(List<String> estimate_id) {
		this.estimate_id = estimate_id;
	}
	public String getBatch_number() {
		return batch_number;
	}
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}
	public String getBatch_status() {
		return batch_status;
	}
	public void setBatch_status(String batch_status) {
		this.batch_status = batch_status;
	}
	public String getDate_domain() {
		return date_domain;
	}
	public void setDate_domain(String date_domain) {
		this.date_domain = date_domain;
		if(CommonUtils.checkExistOrNot(date_domain)) {
			Map<String, String> range=CommonUtils.spiltDateString(date_domain);
			this.domain_from=range.get("startDate");
			this.domain_to=range.get("endDate");
			
		}
		
	}
	public String getDomain_from() {
		return domain_from;
	}
	public void setDomain_from(String domain_from) {
		this.domain_from = domain_from;
	}
	public String getDomain_to() {
		return domain_to;
	}
	public void setDomain_to(String domain_to) {
		this.domain_to = domain_to;
	}
	public String getEstimate_type() {
		return estimate_type;
	}
	public void setEstimate_type(String estimate_type) {
		this.estimate_type = estimate_type;
	}
	public String getContract_in_estimate() {
		return contract_in_estimate;
	}
	public void setContract_in_estimate(String contract_in_estimate) {
		this.contract_in_estimate = contract_in_estimate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}