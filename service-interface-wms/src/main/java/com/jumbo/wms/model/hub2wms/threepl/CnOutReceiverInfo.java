package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

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

@Entity
@Table(name = "T_WH_CN_RECEIVER_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnOutReceiverInfo implements Serializable {

    private static final long serialVersionUID = -1576866063134721416L;
    
    private Long id;
    /**
     * 收件方邮编
     */
    private String receiverZipCode;
    /**
     * 收件方国家
     */
    private String receiverCountry;
    /**
     * 收件方省份 国际地址可能没有
     */
    private String receiverProvince;
    /**
     * 收件方城市，市与区不会同时为空
     */
    private String receiverCity;
    /**
     * 收件方区县，市与区不会同时为空
     */
    private String receiverArea;
    /**
     * 收件方镇
     */
    private String receiveTown;
    /**
     * 收件方最小区划ID，四级地址ID
     */
    private String receiverDivisionId;
    /**
     * 收件方详情地址，不包含省、市、区、街道信息
     */
    private String receiverAddress;
    /**
     * 收件人名称
     */
    private String receiverName;

    /**
     * 收件人手机，手机和电话不会同时为空
     */
    private String receiverMobile;
    /**
     * 收件人电话，手机和电话不会同时为空
     */
    private String receiverPhone;

    private CnOutOrderNotify cnOutOrderNotify;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CRI", sequenceName = "S_T_WH_CN_RECEIVER_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CRI")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "RECEIVER_ZIPCODE")
    public String getReceiverZipCode() {
        return receiverZipCode;
    }

    public void setReceiverZipCode(String receiverZipCode) {
        this.receiverZipCode = receiverZipCode;
    }

    @Column(name = "RECEIVER_COUNTRY")
    public String getReceiverCountry() {
        return receiverCountry;
    }

    public void setReceiverCountry(String receiverCountry) {
        this.receiverCountry = receiverCountry;
    }

    @Column(name = "RECEIVER_PROVINCE")
    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    @Column(name = "RECEIVER_CITY")
    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    @Column(name = "RECEIVER_AREA")
    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    @Column(name = "RECEIVE_TOWN")
    public String getReceiveTown() {
        return receiveTown;
    }

    public void setReceiveTown(String receiveTown) {
        this.receiveTown = receiveTown;
    }

    @Column(name = "RECEIVER_DIVISIONID")
    public String getReceiverDivisionId() {
        return receiverDivisionId;
    }

    public void setReceiverDivisionId(String receiverDivisionId) {
        this.receiverDivisionId = receiverDivisionId;
    }

    @Column(name = "RECEIVER_ADDRESS")
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Column(name = "RECEIVER_NAME")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }


    @Column(name = "RECEIVER_MOBILE")
    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    @Column(name = "RECEIVER_PHONE")
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUT_ORDER_NOTIFY_ID")
    public CnOutOrderNotify getCnOutOrderNotify() {
        return cnOutOrderNotify;
    }

    public void setCnOutOrderNotify(CnOutOrderNotify cnOutOrderNotify) {
        this.cnOutOrderNotify = cnOutOrderNotify;
    }
}
