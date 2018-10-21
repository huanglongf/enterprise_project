package com.jumbo.wms.model.warehouse;

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

import com.jumbo.wms.model.BaseModel;

/**
 * WX 获取运单日志
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
@Table(name = "T_WX_GET_MAILNO_LOG")
public class WxGetMailnoLog extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = 652249593658960256L;
    /**
     * PK
     */
    private Long id;
    /**
     * 运单号
     */
    private String mailno;
    /**
     * 揽货商（分拨中心）编码
     */
    private String allocatorCode;
    /**
     * 揽货商（分拨中心）名称
     */
    private String allocatorName;
    /**
     * 二级配送公司编码
     */
    private String tmsCode;
    /**
     * 二级配送公司名称
     */
    private String tmsName;
    /**
     * 一级流向信息，大头笔
     */
    private String shortAddress;
    /**
     * 二级流向信息
     */
    private String secDistribution;
    /**
     * 物流商公司编码
     */
    private String logisticsCode;
    /**
     * 物流商名称
     */
    private String logisticsName;
    /**
     * 物流信息
     */
    private StaDeliveryInfo staDeliveryInfo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 备用字段
     */
    private String filter1;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WX_GET_MAILNO", sequenceName = "S_T_WX_GET_MAILNO_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WX_GET_MAILNO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "mailno")
    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    @Column(name = "allocator_code")
    public String getAllocatorCode() {
        return allocatorCode;
    }

    public void setAllocatorCode(String allocatorCode) {
        this.allocatorCode = allocatorCode;
    }

    @Column(name = "allocator_name")
    public String getAllocatorName() {
        return allocatorName;
    }

    public void setAllocatorName(String allocatorName) {
        this.allocatorName = allocatorName;
    }

    @Column(name = "tms_code")
    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    @Column(name = "tms_name")
    public String getTmsName() {
        return tmsName;
    }

    public void setTmsName(String tmsName) {
        this.tmsName = tmsName;
    }

    @Column(name = "short_address")
    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    @Column(name = "sec_distribution")
    public String getSecDistribution() {
        return secDistribution;
    }

    public void setSecDistribution(String secDistribution) {
        this.secDistribution = secDistribution;
    }

    @Column(name = "logistics_code")
    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    @Column(name = "logistics_name")
    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_DELIVERY_INFO_ID")
    public StaDeliveryInfo getStaDeliveryInfo() {
        return staDeliveryInfo;
    }

    public void setStaDeliveryInfo(StaDeliveryInfo staDeliveryInfo) {
        this.staDeliveryInfo = staDeliveryInfo;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "filter1")
    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }


}
