package com.bt.lmis.balance.model;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
 * @ClassName: ExpressExpenditureEstimateProductTypeSummary
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月10日 下午7:44:47 
 * 
 */
public class ExpressExpenditureEstimateProductTypeSummary implements Serializable {
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 1667755820525068084L;
	private String productType;
	private String productTypeName;
	private int orderNum;
	private BigDecimal afterDiscount;
	private BigDecimal insurance;
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public BigDecimal getAfterDiscount() {
		return afterDiscount;
	}
	public void setAfterDiscount(BigDecimal afterDiscount) {
		this.afterDiscount = afterDiscount;
	}
	public BigDecimal getInsurance() {
		return insurance;
	}
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
