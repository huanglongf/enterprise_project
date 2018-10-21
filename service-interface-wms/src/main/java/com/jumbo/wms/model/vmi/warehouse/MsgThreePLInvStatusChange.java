package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 外包仓库存状态调整反馈
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "T_WH_THREEPL_INVSTATUS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgThreePLInvStatusChange extends BaseModel {

    private static final long serialVersionUID = -6833351949249668767L;

    private Long id;

    /**
     * 来源(标识第三方系统编码)
     */
    private String customer;

    /**
     * 仓库编码（WMS分配给第三方仓储编码）
     */
    private String whCode;
    /**
     * 单据唯一对接编码
     */
    private String orderCode;
    /**
     * 实际调整时间
     */
    private Date orderDate;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 版本
     */
    private Long version;
    /**
     * 调整原因
     */
    private String memo;
    /**
     * 扩展字段，json格式
     */
    private String extMemo;
    /**
     * 类型
     */
    private String type;

    /**
     * 明细
     */
    private List<MsgThreePLInvStatusChangeLine> changeLine;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_S_THREEPL_INVSTATUS", sequenceName = "S_THREEPL_INVSTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_THREEPL_INVSTATUS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "customer")
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "wh_code")
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    @Column(name = "order_code")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "order_date")
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "ext_memo")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "msgThreePLInv", orphanRemoval = true)
    public List<MsgThreePLInvStatusChangeLine> getChangeLine() {
        return changeLine;
    }

    public void setChangeLine(List<MsgThreePLInvStatusChangeLine> changeLine) {
        this.changeLine = changeLine;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "VERSION")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
