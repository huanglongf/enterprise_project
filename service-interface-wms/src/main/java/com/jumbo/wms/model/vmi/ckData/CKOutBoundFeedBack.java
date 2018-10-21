package com.jumbo.wms.model.vmi.ckData;

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
@Table(name = "T_CK_OUTBOUND_FEEDBACK")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CKOutBoundFeedBack extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2516993468977108185L;
    private Long id;

    private String staCode;

    private String lpCode;

    private String trackingNO;

    private Double weight;

    private Date outboundTime;

    private Date createTime;

    private Integer status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CK_OUTBOUND_FEEDBACK", sequenceName = "s_T_CK_OUTBOUND_FEEDBACK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CK_OUTBOUND_FEEDBACK")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "LP_CODE", length = 50)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "TRACKING_NO", length = 50)
    public String getTrackingNO() {
        return trackingNO;
    }

    public void setTrackingNO(String trackingNO) {
        this.trackingNO = trackingNO;
    }

    @Column(name = "WEIGHT")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "OUTBOUND_DATE")
    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
