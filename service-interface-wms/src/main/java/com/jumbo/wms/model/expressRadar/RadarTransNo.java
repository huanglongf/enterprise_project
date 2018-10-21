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
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;

/**
 * @author lihui
 *
 *         2015年5月25日 下午4:44:25
 */
@Entity
@Table(name = "T_OOC_RD_TRANS_NO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class RadarTransNo extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1782580670353938227L;
    private Long id;
    /**
     * 收件地址
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 当前预警原因
     */
    private RadarWarningReason radarWarningReason;
    /**
     * 当前预警级别
     */
    private SysRadarWarningLv sysRadarWarningLv;
    /**
     * 目的地市
     */
    private String destinationCity;
    /**
     * 目的地省
     */
    private String destinationProvince;
    /**
     * 快递单号
     */
    private String expressCode;
    /**
     * 修改时间
     */
    private Date lastModifyTime;
    /**
     * 物流商
     */
    private String lpcode;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 单据号
     */
    private String orderCode;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 出库时间
     */
    private Date outboundTime;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 平台订单号
     */
    private String pCode;
    /**
     * 物理仓ID
     */
    private PhysicalWarehouse physicalWarehouse;
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 快递状态
     */
    private Integer status;
    /**
     * 揽收时间
     */
    private Date takingTime;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 时效类型
     */
    private Integer transTimeType;
    /**
     * 物流类型
     */
    private Integer transType;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 当前路由状态
     */
    private TransRouteStatus TransRouteStatus;

    public RadarTransNo() {}


    @Id
    @SequenceGenerator(name = "T_OOC_RD_TRANS_NO_ID_GENERATOR", sequenceName = "S_T_OOC_RD_TRANS_NO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_OOC_RD_TRANS_NO_ID_GENERATOR")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENT_WR_ID")
    public RadarWarningReason getRadarWarningReason() {
        return this.radarWarningReason;
    }

    public void setRadarWarningReason(RadarWarningReason radarWarningReason) {
        this.radarWarningReason = radarWarningReason;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENT_WRL_ID")
    public SysRadarWarningLv getSysRadarWarningLv() {
        return sysRadarWarningLv;
    }

    public void setSysRadarWarningLv(SysRadarWarningLv sysRadarWarningLv) {
        this.sysRadarWarningLv = sysRadarWarningLv;
    }


    @Column(name = "DESTINATION_CITY")
    public String getDestinationCity() {
        return this.destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }


    @Column(name = "DESTINATION_PROVINCE")
    public String getDestinationProvince() {
        return this.destinationProvince;
    }

    public void setDestinationProvince(String destinationProvince) {
        this.destinationProvince = destinationProvince;
    }


    @Column(name = "EXPRESS_CODE")
    public String getExpressCode() {
        return this.expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }


    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


    public String getLpcode() {
        return this.lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }


    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return this.orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }


    @Column(name = "ORDER_TYPE")
    public Integer getOrderType() {
        return this.orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }


    @Column(name = "OUTBOUND_TIME")
    public Date getOutboundTime() {
        return this.outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }


    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    @Column(name = "P_CODE")
    public String getPCode() {
        return this.pCode;
    }

    public void setPCode(String pCode) {
        this.pCode = pCode;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PWH_ID")
    public PhysicalWarehouse getPhysicalWarehouse() {
        return this.physicalWarehouse;
    }

    public void setPhysicalWarehouse(PhysicalWarehouse physicalWarehouse) {
        this.physicalWarehouse = physicalWarehouse;
    }


    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Column(name = "TAKING_TIME")
    public Date getTakingTime() {
        return this.takingTime;
    }

    public void setTakingTime(Date takingTime) {
        this.takingTime = takingTime;
    }


    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    @Column(name = "TRANS_TIME_TYPE")
    public Integer getTransTimeType() {
        return this.transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }


    @Column(name = "TRANS_TYPE")
    public Integer getTransType() {
        return this.transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }


    @Version
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    // uni-directional many-to-one association to TransRouteStatus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENT_ROUTE_STATUS")
    public TransRouteStatus getTransRouteStatus() {
        return this.TransRouteStatus;
    }

    public void setTransRouteStatus(TransRouteStatus TransRouteStatus) {
        this.TransRouteStatus = TransRouteStatus;
    }


    @Override
    public String toString() {
        return "RadarTransNo [id=" + id + ", address=" + address + ", createTime=" + createTime + ", radarWarningReason=" + radarWarningReason + ", sysRadarWarningLv=" + sysRadarWarningLv + ", destinationCity=" + destinationCity
                + ", destinationProvince=" + destinationProvince + ", expressCode=" + expressCode + ", lastModifyTime=" + lastModifyTime + ", lpcode=" + lpcode + ", mobile=" + mobile + ", orderCode=" + orderCode + ", orderType=" + orderType
                + ", outboundTime=" + outboundTime + ", owner=" + owner + ", pCode=" + pCode + ", physicalWarehouse=" + physicalWarehouse + ", receiver=" + receiver + ", status=" + status + ", takingTime=" + takingTime + ", telephone=" + telephone
                + ", transTimeType=" + transTimeType + ", transType=" + transType + ", version=" + version + ", TransRouteStatus=" + TransRouteStatus + "]";
    }


}
