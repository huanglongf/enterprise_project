package com.jumbo.webservice.rfd;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 2.3	订单出库
 */
public class RfdOutStoreRequest {
	
	@JsonProperty(value = "FormCode")
	private String formCode;		// 订单编号
	@JsonProperty(value = "WaybillNo")
	private Long waybillNo;			// 运单号
	@JsonProperty(value = "PackageCount")
	private Integer packageCount;	// 打包数量
	@JsonProperty(value = "Weight")
	private BigDecimal weight;		// 重量
	@JsonProperty(value = "ExtendData")
	private String extendData;		// 扩展数据
	@JsonProperty(value = "SubOrders")
	private List<RfdOutStoreDetail> subOrders; //子单信息
	
	public static class RfdOutStoreDetail {
		
		@JsonProperty(value = "WaybillNo")
		private Long waybillNo;			// 子运单号
		@JsonProperty(value = "DeliverCode")
		private String deliverCode;		// 子订单号
		@JsonProperty(value = "SubWeight")
		private BigDecimal subWeight;	// 子单重量
		@JsonProperty(value = "ExtendData")
		private String extendData;		// 扩展数据
		
		public Long getWaybillNo() {
			return waybillNo;
		}
		public void setWaybillNo(Long waybillNo) {
			this.waybillNo = waybillNo;
		}
		public String getDeliverCode() {
			return deliverCode;
		}
		public void setDeliverCode(String deliverCode) {
			this.deliverCode = deliverCode;
		}
		public BigDecimal getSubWeight() {
			return subWeight;
		}
		public void setSubWeight(BigDecimal subWeight) {
			this.subWeight = subWeight;
		}
		public String getExtendData() {
			return extendData;
		}
		public void setExtendData(String extendData) {
			this.extendData = extendData;
		}
		
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public Long getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(Long waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Integer getPackageCount() {
		return packageCount;
	}

	public void setPackageCount(Integer packageCount) {
		this.packageCount = packageCount;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getExtendData() {
		return extendData;
	}

	public void setExtendData(String extendData) {
		this.extendData = extendData;
	}

	public List<RfdOutStoreDetail> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<RfdOutStoreDetail> subOrders) {
		this.subOrders = subOrders;
	}
}
