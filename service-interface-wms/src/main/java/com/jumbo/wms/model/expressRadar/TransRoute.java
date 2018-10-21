package com.jumbo.wms.model.expressRadar;

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

import com.jumbo.wms.model.BaseModel;


/**
 * @author lihui
 *
 * 2015年5月25日 下午4:45:27
 */
@Entity
@Table(name="T_OOC_TRANS_ROUTE")
public class TransRoute extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3569666815524833675L;
	private Long id;
	private String address;
	private String expressCode;
	private Date lastModifyTime;
	private String message;
	private String opcode;
	private Date operateTime;
	private String remark;
	private RadarTransNo RadarTransNo;

	public TransRoute() {
	}


	@Id
	@SequenceGenerator(name="T_OOC_TRANS_ROUTE_ID_GENERATOR", sequenceName="S_T_OOC_TRANS_ROUTE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_OOC_TRANS_ROUTE_ID_GENERATOR")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Column(name="EXPRESS_CODE")
	public String getExpressCode() {
		return this.expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}


	@Column(name="LAST_MODIFY_TIME")
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}


	@Column(name="\"MESSAGE\"")
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getOpcode() {
		return this.opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}


	@Column(name="OPERATE_TIME")
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	//uni-directional many-to-one association to RadarTransNo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RTN_ID")
	public RadarTransNo getRadarTransNo() {
		return this.RadarTransNo;
	}

	public void setRadarTransNo(RadarTransNo RadarTransNo) {
		this.RadarTransNo = RadarTransNo;
	}


	@Override
	public String toString() {
		return "TransRoute [id=" + id + ", address=" + address
				+ ", expressCode=" + expressCode + ", lastModifyTime="
				+ lastModifyTime + ", message=" + message + ", opcode="
				+ opcode + ", operateTime=" + operateTime + ", remark="
				+ remark + ", RadarTransNo=" + RadarTransNo + "]";
	}

	
}