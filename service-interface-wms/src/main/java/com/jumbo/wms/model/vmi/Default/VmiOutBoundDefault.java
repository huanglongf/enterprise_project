package com.jumbo.wms.model.vmi.Default;

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
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * Vmi 出库反馈信息
 */
@Entity
@Table(name = "T_VMI_OUTBOUND")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VmiOutBoundDefault extends BaseModel {


    private static final long serialVersionUID = -8813365542956001481L;

    /**
     * id
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户引用
     */
    private String customerRef;

    /**
     * 订单时间
     */
    private Date orderTime;

    /**
     * 品牌对接吗
     */
    private String storeCode;


    private Date createTime;


    private Date finishTime;

    /**
     * 出库时间
     */
    private Date outBoundTime;


    private String extMemo;

    /**
     * 状态
     */
    private VmiGeneralStatus status;

    /**
     * 作业单ID
     */
    private StockTransApplication staId;

    /**
     * 版本
     */
    private int version;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_VMI_OUTBOUND", sequenceName = "S_T_VMI_OUTBOUND", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VMI_OUTBOUND")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.Default.VmiGeneralStatus")})
    public VmiGeneralStatus getStatus() {
        return status;
    }

    public void setStatus(VmiGeneralStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_VMI_OUTBOUND_STA")
    public StockTransApplication getStaId() {
        return staId;
    }

    public void setStaId(StockTransApplication staId) {
        this.staId = staId;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "CUSTOMER_CODE")
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Column(name = "CUSTOMER_REF")
    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    @Column(name = "ORDER_TIME")
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "OUTBOUND_TIME")
    public Date getOutBoundTime() {
        return outBoundTime;
    }

    public void setOutBoundTime(Date outBoundTime) {
        this.outBoundTime = outBoundTime;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }


}
