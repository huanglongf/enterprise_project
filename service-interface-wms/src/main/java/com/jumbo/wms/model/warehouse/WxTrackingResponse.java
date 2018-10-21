package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_WX_TRACKING_RESPONSE")
public class WxTrackingResponse extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1975146019576744140L;

    /*
     * PK
     */
    private Long id;
    /*
     * 面单号
     */
    private String staCode;
    /*
     * 是否成功
     */
    private Integer success;
    /*
     * 错误编码
     */
    private String errorCode;
    /*
     * 错误消息
     */
    private String errorMsg;
    /*
     * 运单号
     */
    private String mailNo;
    /*
     * 一级流向信息
     */
    private String shortAddress;
    /*
     * 二级流向信息
     */
    private String secDistribution;
    /*
     * 揽货商(分拨中心)信息
     */
    private String allocatorCode;
    /*
     * 揽货商(分拨中心)名称
     */
    private String allocatorName;
    /*
     * 配送商信息
     */
    private String tmsCode;
    /*
     * 配送商名称
     */
    private String tmsName;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "T_WH_WX_TRACKING_RESPONSE_ID_GENERATOR", sequenceName = "S_T_WH_WX_TRACKING_RESPONSE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_WH_WX_TRACKING_RESPONSE_ID_GENERATOR")
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

    @Column(name = "SUCCESS")
    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    @Column(name = "ERROR_CODE", length = 50)
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Column(name = "ERROR_MSG", length = 500)
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Column(name = "MAIL_NO", length = 50)
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    @Column(name = "SHORT_ADDRESS", length = 500)
    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    @Column(name = "SEC_DISTRIBUTION", length = 500)
    public String getSecDistribution() {
        return secDistribution;
    }

    public void setSecDistribution(String secDistribution) {
        this.secDistribution = secDistribution;
    }

    @Column(name = "ALLOCATOR_CODE", length = 50)
    public String getAllocatorCode() {
        return allocatorCode;
    }

    public void setAllocatorCode(String allocatorCode) {
        this.allocatorCode = allocatorCode;
    }

    @Column(name = "ALLOCATOR_NAME", length = 500)
    public String getAllocatorName() {
        return allocatorName;
    }

    public void setAllocatorName(String allocatorName) {
        this.allocatorName = allocatorName;
    }

    @Column(name = "TMS_CODE", length = 50)
    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    @Column(name = "TMS_NAME", length = 100)
    public String getTmsName() {
        return tmsName;
    }

    public void setTmsName(String tmsName) {
        this.tmsName = tmsName;
    }

}
