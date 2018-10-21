package com.bt.lmis.balance.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: ConsumerExpress
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月16日 下午1:31:10 
 * 
 */
public class ConsumerCarrier implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -4775426442949645055L;
	private int id;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;
	private int dFlag;
	private int contractId;
	private int carrierType;
	private String carrierCode;
	private String carrierName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public int getdFlag() {
		return dFlag;
	}
	public void setdFlag(int dFlag) {
		this.dFlag = dFlag;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public int getCarrierType() {
		return carrierType;
	}
	public void setCarrierType(int carrierType) {
		this.carrierType = carrierType;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
