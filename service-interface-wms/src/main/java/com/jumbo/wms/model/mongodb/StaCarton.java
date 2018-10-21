package com.jumbo.wms.model.mongodb;


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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 
 * 收货箱
 */
@Entity
@Table(name = "t_wh_sta_carton")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaCarton extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4424046102663674296L;

    private Long id;

    private StockTransApplication sta;

    /*
     * 箱号
     */
    private String code;

    private Date createTime;

    /*
     * 周转箱目前状态 1:可用 5:占用 2:已发送信息给WCS 10:此箱子上架完成
     */
    private DefaultStatus status;

    /*
     * 库存状态
     */
    private Long invStatusId;

    /*
     * 仓库组织ID
     */
    private Long whOuId;

    private Long userId;

    /**
     * 类型 1：必须手动上架
     * 
     * @return
     */
    private Integer type;

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_StaCarton", sequenceName = "S_T_STACARTON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_StaCarton")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_STA_CARTON_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }


    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "inv_status_id")
    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    @Column(name = "wh_ou_id")
    public Long getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(Long whOuId) {
        this.whOuId = whOuId;
    }


}
