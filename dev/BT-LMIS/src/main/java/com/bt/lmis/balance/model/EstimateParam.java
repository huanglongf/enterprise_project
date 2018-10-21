package com.bt.lmis.balance.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * @ClassName: SettleParam
 * @Description: TODO(结算传参)
 * @author Ian.Huang
 * @date 2017年5月4日 下午2:18:20 
 * 
 */
public class EstimateParam implements Serializable {
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 239462476817983453L;
	// 批次号
	private String batchNumber;
	// 结算周期
	private String fromDate;
	private String toDate;
	// 合同实体
	private Contract contract;
	// 其他参数
	private Map<String, Object> param;
	public EstimateParam() {}
	public EstimateParam(String batchNumber, String fromDate, String toDate) {
		super();
		this.batchNumber = batchNumber;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.param = new LinkedHashMap<String, Object>();
	}
	public EstimateParam(String batchNumber, String fromDate, String toDate, Contract contract) {
		super();
		this.batchNumber = batchNumber;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.contract = contract;
		this.param = new LinkedHashMap<String, Object>();
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}