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
 * wms库内取消单据
 * 
 * @author CSH5574
 * 
 */
@Entity
@Table(name = "T_WH_WMS_CANCEL_ORDER")
public class WmsCancelOrder extends BaseModel {
    private static final long serialVersionUID = -2506850240187507483L;

    private Long id;// 主键ID

    private String staCode;// 作业单code

    private String slipCode;// 相关单据号

    private String slipCode1;// 相关单据号
    
    private String owner;// 店铺

    private String whCode;// 仓库编码

    private int status;// 状态:1.新建,10.同步成功,5.失败

    private Date createTime;// 创建时间
    
    private Long planQty;//计划数量
    
    private Long staStatus;//作业单状态

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CANCEL_ORDER", sequenceName = "S_WH_WMS_CANCEL_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CANCEL_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }
    
    @Column(name="SLIP_CODE")
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }
    
    @Column(name="SLIP_CODE1")
    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    @Column(name="OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    @Column(name="WH_CODE")
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    @Column(name="status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    @Column(name="CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="PLAN_QTY")
    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    @Column(name="STA_STATUS")
    public Long getStaStatus() {
        return staStatus;
    }

    public void setStaStatus(Long staStatus) {
        this.staStatus = staStatus;
    }

    
}
