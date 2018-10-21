package com.jumbo.wms.model.nikeLogistics;

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

/**
 * 推送NIKE订单信息
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_nike_CartonNo")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class NikeCartonNo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -3935240171216696685L;

    private Long Id;
    /**
     * 平台订单号
     */
    private String aoId;
    /**
     * 作业单号
     */
    private String CNID;
    /**
     * 箱号
     */
    private String cartonNo;
    /**
     * 服务类型
     */
    private String serviceLevel;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 送货地址
     */
    private String ConsumerAddress;
    /**
     * 联系电话
     */
    private String consumerTel;
    /**
     * 收货人
     */
    private String consumer;
    /**
     * 快递单号
     */
    private String trackingNo;
    /**
     * 正反向类型
     */
    private String trackingType;
    /**
     * 是否CPP
     */
    private Boolean isCPP;
    /**
     * CPP类型
     */
    private String CPPtype;
    /**
     * CPPcode
     */
    private String CPPcode;

    private Integer status;
    /**
     * 是否COD订单
     */
    private Boolean isCod;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_NIKE_CARTONNO", sequenceName = "S_T_NIKE_CARTONNO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NIKE_CARTONNO")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Column(name = "aoId")
    public String getAoId() {
        return aoId;
    }

    public void setAoId(String aoId) {
        this.aoId = aoId;
    }

    @Column(name = "CNID")
    public String getCNID() {
        return CNID;
    }

    public void setCNID(String cNID) {
        CNID = cNID;
    }

    @Column(name = "cartonNo")
    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    @Column(name = "ServiceLevel")
    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "consumerTel")
    public String getConsumerTel() {
        return consumerTel;
    }

    @Column(name = "ConsumerAddress")
    public String getConsumerAddress() {
        return ConsumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        ConsumerAddress = consumerAddress;
    }

    public void setConsumerTel(String consumerTel) {
        this.consumerTel = consumerTel;
    }

    @Column(name = "consumer")
    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    @Column(name = "trackingNo")
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "trackingType")
    public String getTrackingType() {
        return trackingType;
    }

    public void setTrackingType(String trackingType) {
        this.trackingType = trackingType;
    }

    @Column(name = "isCPP")
    public Boolean getIsCPP() {
        return isCPP;
    }

    public void setIsCPP(Boolean isCPP) {
        this.isCPP = isCPP;
    }

    @Column(name = "CPPtype")
    public String getCPPtype() {
        return CPPtype;
    }

    public void setCPPtype(String cPPtype) {
        CPPtype = cPPtype;
    }

    @Column(name = "CPPcode")
    public String getCPPcode() {
        return CPPcode;
    }

    public void setCPPcode(String cPPcode) {
        CPPcode = cPPcode;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "isCod")
    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }
}
