package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 待创建队列日志-订单明细行
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "t_wh_qlg_sta_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class QueueLogStaLine extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2164567796629614127L;
	/**
	 * PK
	 */
	private long id;
	/**
	 * 商品唯一编码
	 */
	private String skucode;
	/**
	 * 商品名称
	 */
	private String skuName;
	/**
	 * 数量
	 */
	private long qty;
	/**
	 * 行总金额
	 */
	private BigDecimal totalactual;
	/**
	 * 行总金额
	 */
	private BigDecimal ordertotalactual;
	/**
	 * 吊牌价
	 */
	private BigDecimal listprice;
	/**
	 * 单价
	 */
	private BigDecimal unitprice;
	/**
	 * 方向
	 */
	private int direction;
	/**
	 * 店铺
	 */
	private String owner;
	/**
	 * 库存状态ID
	 */
	private long invstatusid;
	/**
	 * 队列日志作业单
	 */
	private QueueLogSta queueLogSta;
	/**
	 * 活动
	 */
	private String activitysource;
	/**
	 * 订单折前金额
	 */
	private BigDecimal ordertotalbfdiscount;
	/**
	 * 商品类型
	 */
	private QueueStaLineType lineType;
	/**
	 * 行状态
	 */
	private QueueStaLineStatus lineStatus;

	@Column(name = "skuName")
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	@Column(name = "order_total_bf_discount", precision = 22, scale = 4)
	public BigDecimal getOrdertotalbfdiscount() {
		return ordertotalbfdiscount;
	}

	public void setOrdertotalbfdiscount(BigDecimal ordertotalbfdiscount) {
		this.ordertotalbfdiscount = ordertotalbfdiscount;
	}

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_T_WH_QLG_STA_LINE", sequenceName = "S_t_wh_qlg_sta_line", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_QLG_STA_LINE")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "sku_code", length = 100)
	public String getSkucode() {
		return skucode;
	}

	public void setSkucode(String skucode) {
		this.skucode = skucode;
	}

	@Column(name = "qty")
	public long getQty() {
		return qty;
	}

	public void setQty(long qty) {
		this.qty = qty;
	}

	@Column(name = "total_actual", precision = 22, scale = 4)
	public BigDecimal getTotalactual() {
		return totalactual;
	}

	public void setTotalactual(BigDecimal totalactual) {
		this.totalactual = totalactual;
	}

	@Column(name = "order_total_actual", precision = 22, scale = 4)
	public BigDecimal getOrdertotalactual() {
		return ordertotalactual;
	}

	public void setOrdertotalactual(BigDecimal ordertotalactual) {
		this.ordertotalactual = ordertotalactual;
	}

	@Column(name = "list_price", precision = 22, scale = 4)
	public BigDecimal getListprice() {
		return listprice;
	}

	public void setListprice(BigDecimal listprice) {
		this.listprice = listprice;
	}

	@Column(name = "unit_price", precision = 22, scale = 4)
	public BigDecimal getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

	@Column(name = "direction")
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Column(name = "owner", length = 100)
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "inv_status_id")
	public long getInvstatusid() {
		return invstatusid;
	}

	public void setInvstatusid(long invstatusid) {
		this.invstatusid = invstatusid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qlg_sta_id")
	@Index(name = "IDX_QLG_STA_LINE")
	public QueueLogSta getQueueLogSta() {
		return queueLogSta;
	}

	public void setQueueLogSta(QueueLogSta queueLogSta) {
		this.queueLogSta = queueLogSta;
	}

	@Column(name = "activity_source", length = 100)
	public String getActivitysource() {
		return activitysource;
	}

	public void setActivitysource(String activitysource) {
		this.activitysource = activitysource;
	}

	@Column(name = "line_type")
	@Type(type = "loxia.dao.support.GenericEnumUserType", parameters = { @Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.QueueStaLineType") })
	public QueueStaLineType getLineType() {
		return lineType;
	}

	public void setLineType(QueueStaLineType lineType) {
		this.lineType = lineType;
	}

	@Column(name = "line_status")
	@Type(type = "loxia.dao.support.GenericEnumUserType", parameters = { @Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.QueueStaLineStatus") })
	public QueueStaLineStatus getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(QueueStaLineStatus lineStatus) {
		this.lineStatus = lineStatus;
	}

}
