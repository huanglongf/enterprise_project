package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
/**
 * 仓库全量库存TOPAC同步表
 * 
 * @author PUCK SHEN
 * 
 */
@Entity
@Table(name = "T_WH_WHOLE_INV_SYNC_PAC")
public class WholeInventorySyncToPAC extends BaseModel {
	
	private static final long serialVersionUID = 7588769663285431434L;
	private Long ID;
	private Date createTime;
	private Long skuId;
	private String skuCode;
	private String channelCode;
	private Long qty;
	private Long whouId;
	private Integer manageMode;
	private Integer marketAbility;
	private Integer status;
	
    @Id
    @Column(name="id")
    @SequenceGenerator(name = "SEQ_TOTAL_INVENTORY", sequenceName = "S_T_WH_WHOLE_INV_SYNC_PAC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TOTAL_INVENTORY")
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	
	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="SKU_ID")
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	@Column(name="SKU_CODE")
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
	@Column(name="CHANNEL_CODE")
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Column(name="QTY")
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	
	@Column(name="wh_ou_id")
	public Long getWhouId() {
		return whouId;
	}
	public void setWhouId(Long whouId) {
		this.whouId = whouId;
	}
	
	@Column(name="MANAGE_MODE")
	public Integer getManageMode() {
		return manageMode;
	}
	public void setManageMode(Integer manageMode) {
		this.manageMode = manageMode;
	}
	
	@Column(name="MARKET_ABILITY")
	public Integer getMarketAbility() {
		return marketAbility;
	}
	public void setMarketAbility(Integer marketAbility) {
		this.marketAbility = marketAbility;
	}
	
	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
