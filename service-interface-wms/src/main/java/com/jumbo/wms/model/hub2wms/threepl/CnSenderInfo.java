package com.jumbo.wms.model.hub2wms.threepl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 菜鸟入库单发件方信息
 */
@Entity
@Table(name = "T_WH_CN_SENDER_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnSenderInfo extends BaseModel {

    private static final long serialVersionUID = 6228341390984858864L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 菜鸟入库通知单
     */
    private CnWmsStockInOrderNotify cnWmsStockInOrderNotify;
    /**
     * 发件方邮编
     */
    private String senderZipCode;
    /**
     * 发件方国家
     */
    private String senderCountry;
    /**
     * 发件方省份 国际地址可能没有
     */
    private String senderProvince;
    /**
     * 发件方城市
     */
    private String senderCity;
    /**
     * 发件方区县
     */
    private String senderArea;
    /**
     * 发件方镇
     */
    private String senderTown;
    /**
     * 发件方地址
     */
    private String senderAddress;
    /**
     * 发件方最小区划ID
     */
    private String senderDivisionId;
    /**
     * 发件方名称（采购入库放供应商名称）， （销退入库填买家名称）， （调拨入库填写调拨出库仓库联系人名称）
     */
    private String senderName;
    /**
     * 发件方手机,手机和电话不能同时为空
     */
    private String senderMobile;
    /**
     * 发件方电话，手机和电话不能同时为空
     */
    private String senderPhone;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "cnSenderInfo")
    public CnWmsStockInOrderNotify getCnWmsStockInOrderNotify() {
        return cnWmsStockInOrderNotify;
    }

    public void setCnWmsStockInOrderNotify(CnWmsStockInOrderNotify cnWmsStockInOrderNotify) {
        this.cnWmsStockInOrderNotify = cnWmsStockInOrderNotify;
    }

    @Column(name = "SENDER_ZIP_CODE")
    public String getSenderZipCode() {
        return senderZipCode;
    }

    public void setSenderZipCode(String senderZipCode) {
        this.senderZipCode = senderZipCode;
    }

    @Column(name = "SENDER_COUNTRY")
    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    @Column(name = "SENDER_PROVINCE")
    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    @Column(name = "SENDER_CITY")
    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    @Column(name = "SENDER_AREA")
    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    @Column(name = "SENDER_TOWN")
    public String getSenderTown() {
        return senderTown;
    }

    public void setSenderTown(String senderTown) {
        this.senderTown = senderTown;
    }

    @Column(name = "SENDER_ADDRESS")
    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Column(name = "SENDER_DIVISION_ID")
    public String getSenderDivisionId() {
        return senderDivisionId;
    }

    public void setSenderDivisionId(String senderDivisionId) {
        this.senderDivisionId = senderDivisionId;
    }

    @Column(name = "SENDER_NAME")
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Column(name = "SENDER_MOBILE")
    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    @Column(name = "SENDER_PHONE")
    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

}
