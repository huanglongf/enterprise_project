package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "t_wh_Order_AddInfo")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class AdvanceOrderAddInfo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 9131976318517589211L;
    private Long id;
    /**
     * 订单来源(OMS/PAC)
     */
    private String orderSource;
    /**
     * 订单号(数据唯一标识)
     */
    private String orderCode;
    /**
     * 店铺编码
     */
    private String owner;
    /**
     * 是否允许发货标识
     */
    private Boolean IsAllowDeliver;
    /**
     * 是否修改收货人相关信息
     */
    private Boolean IsChangeRecieverInfo;
    /**
     * 付款状态(已全额收款/部分收款)
     */
    private String paymentStatus;
    /**
     * 付尾款时间
     */
    private Date paymentTime;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态
     */
    private Integer status;

    private int version;

    private Integer lfStatus;// LF 状态 1：新建 2：跟新 5：发送OK null 非外包仓

    private String lfMemo;// 备注

    private Long batchId;// 批次ID

    private String vmiSource;// source

    private Integer errorNum;// 错误次数


    @Column(name = "ERROR_NUM")
    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    @Column(name = "VMI_SOURCE")
    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    @Column(name = "LF_STATUS")
    public Integer getLfStatus() {
        return lfStatus;
    }

    public void setLfStatus(Integer lfStatus) {
        this.lfStatus = lfStatus;
    }

    @Column(name = "LF_MEMO")
    public String getLfMemo() {
        return lfMemo;
    }

    public void setLfMemo(String lfMemo) {
        this.lfMemo = lfMemo;
    }

    @Column(name = "BATCH_ID")
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }


    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }


    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_Order_AddInfo", sequenceName = "S_t_wh_Order_AddInfo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Order_AddInfo")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "orderSource")
    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    @Column(name = "orderCode")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "IsAllowDeliver")
    public Boolean getIsAllowDeliver() {
        return IsAllowDeliver;
    }

    public void setIsAllowDeliver(Boolean isAllowDeliver) {
        IsAllowDeliver = isAllowDeliver;
    }

    @Column(name = "IsChangeRecieverInfo")
    public Boolean getIsChangeRecieverInfo() {
        return IsChangeRecieverInfo;
    }

    public void setIsChangeRecieverInfo(Boolean isChangeRecieverInfo) {
        IsChangeRecieverInfo = isChangeRecieverInfo;
    }

    @Column(name = "paymentStatus")
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Column(name = "paymentTime")
    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



}
