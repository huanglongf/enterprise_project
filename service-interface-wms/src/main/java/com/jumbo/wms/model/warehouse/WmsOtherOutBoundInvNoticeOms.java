package com.jumbo.wms.model.warehouse;

import java.util.Date;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * WMS其他出库通知OMS中间表
 * 
 * @author PUCK SHEN
 * 
 */
@Entity
@Table(name = "T_WH_OTHEROUTINV_TO_OMS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WmsOtherOutBoundInvNoticeOms extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8254445564069030456L;
	/**
	 * PK
	 */
	private Long id;
	/**
	 * 出库作业单号
	 */
	private String staCode;
	/**
	 * 作业单ID
	 */
	private Long staId;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	/**
	 * 仓库组织ID
	 */
	private OperationUnit whOuId;

	/**
	 * 渠道code
	 */
	private String owner;
	
	/**
	 * 占用错误次数
	 */
	private Long occupationErrorCount;
	/**
	 * oms占用反馈错误信息
	 */
	private String occupationReturnMsg;
	/**
	 * 占用通知是否发送 0：否，1：是
	 */
	private Long occupationIsSend;
	
	/**
	 * 完成发送错误次数
	 */
	private Long finishErrorCount;
	/**
	 * wms其他出库完成通知oms反馈错误信息
	 */
	private String finishReturnMsg;
	
	/**
	 * 取消发送错误次数
	 */
	private Long cancelErrorCount;
	/**
	 * wms其他出库完成通知oms反馈错误信息
	 */
	private String cancelReturnMsg;
	
	/**
	 * 中间表作业单类型
	 */
	private Integer finishIsSend;
	
	/**
	 * 批次号（定时任务用） 生成规则：当前时间转String
	 */
	private String batchCode;
	
	/**
	 * 作业单状态
	 */
	private Long status;
	
	/**
	 * qty
	 */
	private Long pushForsaleQty;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_OUT_NOTICE_OMS", sequenceName = "S_T_WH_OTHEROUTINV_TO_OMS", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OUT_NOTICE_OMS")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "STA_CODE")
	public String getStaCode() {
		return staCode;
	}

	public void setStaCode(String staCode) {
		this.staCode = staCode;
	}

	@Column(name = "STA_ID")
	public Long getStaId() {
		return staId;
	}

	public void setStaId(Long staId) {
		this.staId = staId;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "LAST_MODIFY_TIME")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WH_OU_ID")
	public OperationUnit getWhOuId() {
		return whOuId;
	}

	public void setWhOuId(OperationUnit whOuId) {
		this.whOuId = whOuId;
	}

	@Column(name = "OWNER")
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "OCCUPATION_ERROR_COUNT")
	public Long getOccupationErrorCount() {
		return occupationErrorCount;
	}

	public void setOccupationErrorCount(Long occupationErrorCount) {
		this.occupationErrorCount = occupationErrorCount;
	}

	@Column(name = "OCCUPATION_RETURN_MSG")
	public String getOccupationReturnMsg() {
		return occupationReturnMsg;
	}

	public void setOccupationReturnMsg(String occupationReturnMsg) {
		this.occupationReturnMsg = occupationReturnMsg;
	}

	@Column(name = "OCCUPATION_IS_SEND")
	public Long getOccupationIsSend() {
		return occupationIsSend;
	}

	public void setOccupationIsSend(Long occupationIsSend) {
		this.occupationIsSend = occupationIsSend;
	}

	@Column(name = "FINISH_ERROR_COUNT")
	public Long getFinishErrorCount() {
		return finishErrorCount;
	}

	public void setFinishErrorCount(Long finishErrorCount) {
		this.finishErrorCount = finishErrorCount;
	}

	@Column(name = "FINISH_RETURN_MSG")
	public String getFinishReturnMsg() {
		return finishReturnMsg;
	}

	public void setFinishReturnMsg(String finishReturnMsg) {
		this.finishReturnMsg = finishReturnMsg;
	}

	@Column(name = "CANCEL_ERROR_COUNT")
	public Long getCancelErrorCount() {
		return cancelErrorCount;
	}

	public void setCancelErrorCount(Long cancelErrorCount) {
		this.cancelErrorCount = cancelErrorCount;
	}

	@Column(name = "CANCEL_RETURN_MSG")
	public String getCancelReturnMsg() {
		return cancelReturnMsg;
	}

	public void setCancelReturnMsg(String cancelReturnMsg) {
		this.cancelReturnMsg = cancelReturnMsg;
	}

	@Column(name = "FINISH_IS_SEND")
	public Integer getFinishIsSend() {
		return finishIsSend;
	}

	public void setFinishIsSend(Integer finishIsSend) {
		this.finishIsSend = finishIsSend;
	}

	@Column(name = "BATCH_CODE")
	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "PUSH_FORSALE_QTY")
	public Long getPushForsaleQty() {
		return pushForsaleQty;
	}

	public void setPushForsaleQty(Long pushForsaleQty) {
		this.pushForsaleQty = pushForsaleQty;
	}

	
}
