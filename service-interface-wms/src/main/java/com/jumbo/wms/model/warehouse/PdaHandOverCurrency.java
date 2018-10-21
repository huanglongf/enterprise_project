package com.jumbo.wms.model.warehouse;

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
@Table(name = "t_wh_pda_hand_Over_currency")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class PdaHandOverCurrency extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -8776442532418221155L;


    private Long id;
    /**
     * 运输服务商
     */
    private String lpcode;
    /**
     * 快递单号
     */
    private String trackingNo;


    private Long ouId;
    /**
     * 
     */
    private Long userId;
    /**
     * 
     */
    private Long packageInfoId;
    /**
     * 
     */
    private Date createTime;

    private Integer status;


    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "lpcode")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PDA_HAND_OVER_CURRENCY", sequenceName = "s_t_wh_pda_hand_over_currency", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDA_HAND_OVER_CURRENCY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRACKING_NO")
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "PACKAGE_INFO_ID")
    public Long getPackageInfoId() {
        return packageInfoId;
    }

    public void setPackageInfoId(Long packageInfoId) {
        this.packageInfoId = packageInfoId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
