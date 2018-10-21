package com.jumbo.wms.model.vmi.warehouse;

import java.math.BigDecimal;
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
@Table(name = "t_wh_msg_omstmall_order")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgOmstmallOrder extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3589427474440545624L;


    /**
     * PK
     */
    private Long id;


    private String source;


    private String staCode;


    private String slipCode;

    private String lpCode;

    private String trackingNo;

    private BigDecimal weight = new BigDecimal(0);

    private Date createTime = new Date();

    private Boolean isOk = false;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "seq_msg_omstmall_order", sequenceName = "seq_msg_omstmall_order", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_msg_omstmall_order")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SOURCE", length = 10)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "STA_CODE", length = 100)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "SLIP_CODE", length = 100)
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "LP_CODE", length = 50)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "TRACKING_NO", length = 100)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "IS_OK")
    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

}
