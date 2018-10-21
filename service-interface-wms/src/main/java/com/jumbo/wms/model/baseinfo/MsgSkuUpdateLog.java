package com.jumbo.wms.model.baseinfo;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.TimeTypeMode;

/**
 * 
 * 
 * 项目名称：Wms 类名称：MsgSkuUpdate 类描述：保质期商品修改发送OMS对列LOG表 创建人：bin.hu 创建时间：2014-7-17 上午10:01:52
 * 
 * @version
 * 
 */
@Entity
@Table(name = "T_MSG_SKU_UPDATE_QUEUE_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgSkuUpdateLog extends BaseModel {

    private static final long serialVersionUID = 2312625345625090597L;


    private Long id;

    /**
     * 外键 商品ID
     */
    private Sku sku;

    /**
     * 外键 客户ID
     */
    private Customer customer;

    /**
     * 保质期天数
     */
    private Integer validDate;

    /**
     * 时间维护类型
     */
    private TimeTypeMode timeType;

    /**
     * 记录LOG时间
     */
    private Date tranTime;

    /**
     * 原记录创建时间
     */
    private Date originalTime;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CMPSHOP", sequenceName = "S_T_MSG_SKU_UPDATE_QUEUE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CMPSHOP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Column(name = "VALID_DATE")
    public Integer getValidDate() {
        return validDate;
    }

    public void setValidDate(Integer validDate) {
        this.validDate = validDate;
    }

    @Column(name = "TIME_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TimeTypeMode")})
    public TimeTypeMode getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeTypeMode timeType) {
        this.timeType = timeType;
    }

    @Column(name = "TRAN_TIME")
    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    @Column(name = "ORIGINAL_CREAT_TIME")
    public Date getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(Date originalTime) {
        this.originalTime = originalTime;
    }
}
