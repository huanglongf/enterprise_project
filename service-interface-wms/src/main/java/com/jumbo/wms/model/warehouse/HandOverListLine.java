package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultLifeCycleStatus;
import com.jumbo.wms.model.authorization.User;

/**
 * 交接清单明细
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_STA_HO_LIST_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class HandOverListLine extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7563870572475412263L;
    /**
     * PK
     */
    private Long id;

    private int version;
    /**
     * 货物重量
     */
    private BigDecimal weight;
    /**
     * 快递单号
     */
    private String trackingNo;
    /**
     * 状态
     */
    private DefaultLifeCycleStatus status;
    /**
     * 相关作业单
     */
    private StockTransApplication sta;

    /**
     * 所属交接清单
     */
    private HandOverList hoList;
    /**
     * 取消原因
     */
    private String cancelReason;
    /**
     * 取消时间
     */
    private Date cancelTime;
    /**
     * 取消操作员
     */
    private User cancelUser;

    /**
     * 是否是预售订单
     * 
     * @return
     */
    private Boolean isPreSale = false;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA", sequenceName = "S_T_WH_STA_HO_LIST_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "TRACKING_NO", length = 20)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultLifeCycleStatus")})
    public DefaultLifeCycleStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultLifeCycleStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_FK_STA_ID")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HO_LIST_ID")
    @Index(name = "IDX_FK_STA_HO_LIST_ID")
    public HandOverList getHoList() {
        return hoList;
    }

    public void setHoList(HandOverList hoList) {
        this.hoList = hoList;
    }

    @Column(name = "CANCEL_REASON", length = 500)
    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Column(name = "CANCEL_TIME")
    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANCEL_USER_ID")
    @Index(name = "IDX_FK_CANCEL_USER_ID")
    public User getCancelUser() {
        return cancelUser;
    }

    public void setCancelUser(User cancelUser) {
        this.cancelUser = cancelUser;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Transient
    public int getIntStatus() {
        return status == null ? -1 : status.getValue();
    }

    @Column(name = "is_Pre_Sale")
    public Boolean getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(Boolean isPreSale) {
        this.isPreSale = isPreSale;
    }


}
