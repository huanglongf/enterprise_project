package com.jumbo.wms.model.expressRadar;

// Generated 2015-5-25 15:27:48 by Hibernate Tools 3.4.0.CR1

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

import com.jumbo.wms.model.BaseModel;


/**
 * @author gianni.zhang
 * 
 *         2015年5月25日 下午5:06:49
 */
@Entity
@Table(name = "T_OOC_RD_WARNING_REASON_LINE")
public class RadarWarningReasonLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7416493486620163034L;

    /**
     * id
     */
    private Long id;

    /**
     * 预警级别
     */
    private SysRadarWarningLv sw;

    /**
     * 预警原因
     */
    private RadarWarningReason wr;

    /**
     * 预警时效
     */
    private Integer warningHour;

    /**
     * 备注
     */
    private String memo;


    @Id
    @SequenceGenerator(name = "T_OOC_RD_WARNING_REASON_LINE_ID_GENERATOR", sequenceName = "S_T_OOC_RD_WARNING_REASON_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_OOC_RD_WARNING_REASON_LINE_ID_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SW_ID")
    public SysRadarWarningLv getSw() {
        return this.sw;
    }

    public void setSw(SysRadarWarningLv sw) {
        this.sw = sw;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WR_ID")
    public RadarWarningReason getWr() {
        return this.wr;
    }

    public void setWr(RadarWarningReason wr) {
        this.wr = wr;
    }

    @Column(name = "WARNING_HOUR")
    public Integer getWarningHour() {
        return this.warningHour;
    }

    public void setWarningHour(Integer warningHour) {
        this.warningHour = warningHour;
    }

    @Column(name = "MEMO", length = 100)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
