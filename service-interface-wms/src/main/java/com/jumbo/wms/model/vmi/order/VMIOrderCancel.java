package com.jumbo.wms.model.vmi.order;

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

@Entity
@Table(name = "T_VMI_ORDER_CANCEL")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VMIOrderCancel extends BaseModel {

    private static final long serialVersionUID = 6665752406809117994L;

    private Long id;

    private Date createTime;

    private String orderCode;

    /**
     * 是否接受删除指令（Y/N）
     */
    private String isAcceptCancel;

    private Integer status;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_VMI_ORDER_CANCEL", sequenceName = "S_T_VMI_ORDER_CANCEL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VMI_ORDER_CANCEL")
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

    @Column(name = "ORDER_CODE", length = 20)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "IS_ACCEPT_CANCEL", length = 20)
    public String getIsAcceptCancel() {
        return isAcceptCancel;
    }

    public void setIsAcceptCancel(String isAcceptCancel) {
        this.isAcceptCancel = isAcceptCancel;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



}
