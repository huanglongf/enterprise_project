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
@Entity
@Table(name = "T_JD_CONFIRM_ORDER_QUEUE_LOG")
public class JdConfirmOrderQueueLog  extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9220178200348245139L;
	private Long id;
    private Date createTime;
    private String transno;    
    private String staCode;
    private String weight;
    private String whight;
    private String height;
    private String length;
    private Date logCreateTime;
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_JDCQQ_LOG", sequenceName = "S_T_JD_CONFIRM_ORDER_QUEUE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JDCQQ_LOG")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="create_Time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="trans_no")
	public String getTransno() {
		return transno;
	}
	public void setTransno(String transno) {
		this.transno = transno;
	}
	@Column(name="sta_Code")
	public String getStaCode() {
		return staCode;
	}
	public void setStaCode(String staCode) {
		this.staCode = staCode;
	}
	@Column(name="weight")
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	@Column(name="whight")
	public String getWhight() {
		return whight;
	}
	public void setWhight(String whight) {
		this.whight = whight;
	}
	@Column(name="height")
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	@Column(name="length")
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@Column(name="log_create_time")
	public Date getLogCreateTime() {
		return logCreateTime;
	}
	public void setLogCreateTime(Date logCreateTime) {
		this.logCreateTime = logCreateTime;
	}
	

}
