package com.jumbo.wms.model.hub2wms.threepl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 菜鸟订单原始属性
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_WH_CN_CANCEL_ORDER_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnCancelOrderLog extends BaseModel {

    private static final long serialVersionUID = 6472571430719038992L;

    private Long id;// 主键

    /**
     * 单据编码
     */
    private String orderCode;


    /**
     * 系统编码
     */
    private String systemKey;

    /**
     * 取消状态
     */
    private Integer status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CN_COL", sequenceName = "S_T_WH_CN_CANCEL_ORDER_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CN_COL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }



    @Column(name = "SYSTEM_KEY")
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



}
