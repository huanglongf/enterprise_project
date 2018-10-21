package com.jumbo.wms.model.boc;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


/**
 * 接收品牌调整库存的数据
 * @author jieqing.wang
 *
 */

@Entity
@Table(name="T_VMI_INV_MOVEMENT_DATA")
@org.hibernate.annotations.Entity(optimisticLock=OptimisticLockType.VERSION)
public class VmiInventoryMovementData extends BaseModel {

	
	private static final long serialVersionUID = -6790141151352947550L;

	/*
	 * PK
	 */
	private Long id;
	
	/*
	 * 创建时间
	 */
	private Date createTime;
	
	/*
	 * 单据号
	 */
	private String billNo;
	
	/*
	 * 对应盘点单号
	 */
	private String icCode;
	
	/*
	 * 仓库
	 */
	private String warehouse;
	
	/*
	 * 店铺号
	 */
	private String storeCode;
	
	/*
	 * 操作日期
	 */
	private String moveDate;
	
	/*
	 * 操作类型
	 * 0.入库   1.出库
	 */
	private String moveType;
	
	/*
	 * 商品唯一编码
	 */
	private String upc;
	
	/*
	 * 数量
	 */
	private BigDecimal quantity;
	
	/*
	 * 货品状态
	 * 良品、残次品
	 */
	private String status;
	
	/*
	 * 来源
	 */
	private String source;
	
	/*
	 * 文件名字
	 */
	private String fileName;
	
	/*
	 * 执行时间
	 */
	private Date executeTime;
	
	/*
	 * 执行情况
	 * 1为新建,10为成功
	 */
	private String executeStatus;
	
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="SEQ_T_VMI_INV_MOVEMENT_DATA",sequenceName="S_T_VMI_INV_MOVEMENT_DATA",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_T_VMI_INV_MOVEMENT_DATA")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "BILL_NO",length=30)
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@Column(name = "IC_CODE",length=30)
	public String getIcCode() {
		return icCode;
	}

	public void setIcCode(String icCode) {
		this.icCode = icCode;
	}

	@Column(name = "WAREHOUSE",length=50)
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "STORE_CODE",length=20)
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@Column(name = "MOVEDATE",length=8)
	public String getMoveDate() {
		return moveDate;
	}

	public void setMoveDate(String moveDate) {
		this.moveDate = moveDate;
	}

	@Column(name = "MOVETYPE",length=4)
	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	@Column(name = "UPC",length=30)
	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	@Column(name = "QUANTITY",precision = 22, scale = 2)
	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Column(name = "STATUS",length=30)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "SOURCE",length=100)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "FILE_NAME",length=100)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "EXECUTE_TIME")
	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	@Column(name = "EXECUTE_STATUS",length=4)
	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
	
	
}
