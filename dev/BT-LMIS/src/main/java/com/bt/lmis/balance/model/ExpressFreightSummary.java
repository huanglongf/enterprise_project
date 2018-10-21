package com.bt.lmis.balance.model;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
 * @ClassName: ExpressFreightSummary
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月17日 上午10:24:42 
 * 
 */
public class ExpressFreightSummary implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -7113015089167319065L;
	private String express;
	private Integer orderNum;
	private BigDecimal totalFreight;
	private BigDecimal totalInsurance;
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public BigDecimal getTotalFreight() {
		return totalFreight;
	}
	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}
	public BigDecimal getTotalInsurance() {
		return totalInsurance;
	}
	public void setTotalInsurance(BigDecimal totalInsurance) {
		this.totalInsurance = totalInsurance;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
