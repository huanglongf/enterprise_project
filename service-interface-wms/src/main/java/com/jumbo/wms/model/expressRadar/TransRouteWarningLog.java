package com.jumbo.wms.model.expressRadar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jumbo.wms.model.BaseModel;


/**
 * @author lihui
 *
 * 2015年5月25日 下午4:45:44
 */
@Entity
@Table(name="T_OOC_TRANS_ROUTE_WARNING_LOG")
public class TransRouteWarningLog extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1653364371052925725L;
	private Long id;
	private Date creareTime;
	private String errorCode;
	private String errorName;
	private Date lastModifyTime;
	private Long modifyUserId;
	private String remark;
	private Long rscId;
	private Long trId;
	private Long warningCreaterId;
    private Long warningLv;
	private String warningName;

	public TransRouteWarningLog() {
	}


	@Id
	@SequenceGenerator(name="T_OOC_TRANS_ROUTE_WARNING_LOG_ID_GENERATOR", sequenceName="S_OOC_TRANS_ROUTE_WARNING_LOG", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_OOC_TRANS_ROUTE_WARNING_LOG_ID_GENERATOR")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREARE_TIME")
	public Date getCreareTime() {
		return this.creareTime;
	}

	public void setCreareTime(Date creareTime) {
		this.creareTime = creareTime;
	}


	@Column(name="ERROR_CODE")
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	@Column(name="ERROR_NAME")
	public String getErrorName() {
		return this.errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_MODIFY_TIME")
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}


	@Column(name="MODIFY_USER_ID")
	public Long getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Column(name="RSC_ID")
	public Long getRscId() {
		return this.rscId;
	}

	public void setRscId(Long rscId) {
		this.rscId = rscId;
	}


	@Column(name="TR_ID")
	public Long getTrId() {
		return this.trId;
	}

	public void setTrId(Long trId) {
		this.trId = trId;
	}


	@Column(name="WARNING_CREATER_ID")
	public Long getWarningCreaterId() {
		return this.warningCreaterId;
	}

	public void setWarningCreaterId(Long warningCreaterId) {
		this.warningCreaterId = warningCreaterId;
	}


	@Column(name="WARNING_LV")
    public Long getWarningLv() {
		return this.warningLv;
	}

    public void setWarningLv(Long warningLv) {
		this.warningLv = warningLv;
	}


	@Column(name="WARNING_NAME")
	public String getWarningName() {
		return this.warningName;
	}

	public void setWarningName(String warningName) {
		this.warningName = warningName;
	}

}