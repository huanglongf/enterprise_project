package com.jumbo.pms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 包裹路由信息
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_WH_PARCEL_TRANS_DETAIL")
public class ParcelTransDetail extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1967334664116017675L;

	/**
     * PK
     */
    private Long id;
    
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    
	/**
	 * 操作码
	 */
	private String opCode;
    
    /**
     * 路由产生时间
     */
    private Date acceptTime;
    
    /**
     * 路由发成城市
     * 如果是门店已签收/顾客已签收 此字段允许为空
     */
    private String acceptAddress;
    
    /**
     * 路由说明,记录当前路由状态
     */
    private String remark;

    /**
     * 不用与包裹主表做外键关联,此字段加索引
     */
    private String parcelCode;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PARCEL_TRANS_DETAIL", sequenceName = "S_T_WH_PARCEL_TRANS_DETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARCEL_TRANS_DETAIL")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

    @Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    @Column(name = "OP_CODE")
    public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	@Column(name = "ACCEPT_TIME")
	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

    @Column(name = "ACCEPT_ADDRESS")
	public String getAcceptAddress() {
		return acceptAddress;
	}

	public void setAcceptAddress(String acceptAddress) {
		this.acceptAddress = acceptAddress;
	}

    @Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    @Column(name = "PARCEL_CODE")
    @Index(name = "IDX_D_PARCEL_CODE")
	public String getParcelCode() {
		return parcelCode;
	}

	public void setParcelCode(String parcelCode) {
		this.parcelCode = parcelCode;
	}

}