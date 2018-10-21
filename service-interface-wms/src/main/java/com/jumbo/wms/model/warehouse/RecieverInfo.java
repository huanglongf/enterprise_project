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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "t_wh_Reciever_Info")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class RecieverInfo extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 4972177546155922609L;
    private Long id;
    /**
     * 运输服务商
     */
    private String lpcode;
    /**
     * 快递单号
     */
    private String trackingNumber;
    /**
     * 收件人姓名
     */
    private String recieverName;
    /**
     * 收件人手机
     */
    private String recieverMobilePhone;
    /**
     * 收件人固定电话
     */
    private String recieverTelephone;
    /**
     * 收件人国家
     */
    private String recieverCountry;
    /**
     * 收件人省
     */
    private String recieverProvince;
    /**
     * 收件人市
     */
    private String recieverCity;
    /**
     * 收件人区
     */
    private String recieverDistrict;
    /**
     * 收件人乡镇/街道
     */
    private String recieverVillagesTowns;
    /**
     * 收件人详细地址
     */
    private String recieverAddress;
    /**
     * 收件人邮箱
     */
    private String recieverEmail;
    /**
     * 收件人邮编
     */
    private String recieverZipCode;
    /**
     * 状态
     */
    private Integer status;


    private AdvanceOrderAddInfo addInfo;

    @Column(name = "lpcode")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "trackingNumber")
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Column(name = "recieverName")
    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    @Column(name = "recieverMobilePhone")
    public String getRecieverMobilePhone() {
        return recieverMobilePhone;
    }

    public void setRecieverMobilePhone(String recieverMobilePhone) {
        this.recieverMobilePhone = recieverMobilePhone;
    }

    @Column(name = "recieverTelephone")
    public String getRecieverTelephone() {
        return recieverTelephone;
    }

    public void setRecieverTelephone(String recieverTelephone) {
        this.recieverTelephone = recieverTelephone;
    }

    @Column(name = "recieverCountry")
    public String getRecieverCountry() {
        return recieverCountry;
    }

    public void setRecieverCountry(String recieverCountry) {
        this.recieverCountry = recieverCountry;
    }

    @Column(name = "recieverProvince")
    public String getRecieverProvince() {
        return recieverProvince;
    }

    public void setRecieverProvince(String recieverProvince) {
        this.recieverProvince = recieverProvince;
    }

    @Column(name = "recieverCity")
    public String getRecieverCity() {
        return recieverCity;
    }

    public void setRecieverCity(String recieverCity) {
        this.recieverCity = recieverCity;
    }

    @Column(name = "recieverDistrict")
    public String getRecieverDistrict() {
        return recieverDistrict;
    }

    public void setRecieverDistrict(String recieverDistrict) {
        this.recieverDistrict = recieverDistrict;
    }

    @Column(name = "recieverVillagesTowns")
    public String getRecieverVillagesTowns() {
        return recieverVillagesTowns;
    }

    public void setRecieverVillagesTowns(String recieverVillagesTowns) {
        this.recieverVillagesTowns = recieverVillagesTowns;
    }

    @Column(name = "recieverAddress")
    public String getRecieverAddress() {
        return recieverAddress;
    }

    public void setRecieverAddress(String recieverAddress) {
        this.recieverAddress = recieverAddress;
    }

    @Column(name = "recieverEmail")
    public String getRecieverEmail() {
        return recieverEmail;
    }

    public void setRecieverEmail(String recieverEmail) {
        this.recieverEmail = recieverEmail;
    }

    @Column(name = "recieverZipCode")
    public String getRecieverZipCode() {
        return recieverZipCode;
    }

    public void setRecieverZipCode(String recieverZipCode) {
        this.recieverZipCode = recieverZipCode;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_Reciever_Info", sequenceName = "S_t_wh_Reciever_Info", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Reciever_Info")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addInfo_id")
    @Index(name = "IDX_addInfo_id")
    public AdvanceOrderAddInfo getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(AdvanceOrderAddInfo addInfo) {
        this.addInfo = addInfo;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
