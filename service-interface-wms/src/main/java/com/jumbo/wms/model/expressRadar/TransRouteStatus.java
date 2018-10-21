package com.jumbo.wms.model.expressRadar;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;

/**
 * @author lihui
 *
 *         2015年5月25日 下午4:45:35
 */
@Entity
@Table(name = "T_OOC_TRANS_ROUTE_STATUS")
public class TransRouteStatus extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7648071651728837691L;
    private Long id;
    /**
     * 当前预警编辑ID
     */
    private User modifyUser;
    /**
     * 当前预警创建人ID
     */
    private User warningCreaterUser;
    /**
     * 创建时间
     */
    private Date creareTime;
    /**
     * 修改时间
     */
    private Date lastModifyTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预警原因
     */
    private RadarWarningReason radarWarningReason;
    /**
     * 快递使用路由状态
     */
    private RadarRouteStatusRef RadarRouteStatusRef;
    /**
     * 路由
     */
    private TransRoute TransRoute;

    public TransRouteStatus() {}


    @Id
    @SequenceGenerator(name = "T_OOC_TRANS_ROUTE_STATUS_ID_GENERATOR", sequenceName = "S_T_OOC_TRANS_ROUTE_STATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_OOC_TRANS_ROUTE_STATUS_ID_GENERATOR")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_MODIFY_USER_ID")
    public User getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(User modifyUser) {
        this.modifyUser = modifyUser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_WARNING_CREATER_ID")
    public User getWarningCreaterUser() {
        return warningCreaterUser;
    }

    public void setWarningCreaterUser(User warningCreaterUser) {
        this.warningCreaterUser = warningCreaterUser;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREARE_TIME")
    public Date getCreareTime() {
        return this.creareTime;
    }

    public void setCreareTime(Date creareTime) {
        this.creareTime = creareTime;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WR_ID")
    public RadarWarningReason getRadarWarningReason() {
        return radarWarningReason;
    }

    public void setRadarWarningReason(RadarWarningReason radarWarningReason) {
        this.radarWarningReason = radarWarningReason;
    }

    // uni-directional many-to-one association to RadarRouteStatusRef
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REF_ID")
    public RadarRouteStatusRef getRadarRouteStatusRef() {
        return this.RadarRouteStatusRef;
    }

    public void setRadarRouteStatusRef(RadarRouteStatusRef RadarRouteStatusRef) {
        this.RadarRouteStatusRef = RadarRouteStatusRef;
    }


    // uni-directional many-to-one association to TransRoute
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TR_ID")
    public TransRoute getTransRoute() {
        return this.TransRoute;
    }

    public void setTransRoute(TransRoute TransRoute) {
        this.TransRoute = TransRoute;
    }

}
