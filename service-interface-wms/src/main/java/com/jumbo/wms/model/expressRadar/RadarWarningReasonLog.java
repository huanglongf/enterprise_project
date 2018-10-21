package com.jumbo.wms.model.expressRadar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;


/**
 * @author lihui
 * 
 *         2015年5月25日 下午4:44:59
 */
@Entity
@Table(name = "T_OOC_RD_WARNING_REASON_LOG")
public class RadarWarningReasonLog extends BaseModel {

    private static final long serialVersionUID = -6655984487940564801L;

    private Long id;

    private String code;

    private Date creareTime;

    private Long createUserId;

    private String errorCode;

    private String errorName;

    private String name;

    private String remark;

    private Integer warningLv;

    private String warningName;


    @Id
    @SequenceGenerator(name = "T_OOC_RD_WARNING_REASON_LOG_ID_GENERATOR", sequenceName = "S_T_OOC_RD_WARNING_REASON_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_OOC_RD_WARNING_REASON_LOG_ID_GENERATOR")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "CREARE_TIME")
    public Date getCreareTime() {
        return this.creareTime;
    }

    public void setCreareTime(Date creareTime) {
        this.creareTime = creareTime;
    }


    @Column(name = "CREATE_USER_ID")
    public Long getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }


    @Column(name = "ERROR_CODE")
    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


    @Column(name = "ERROR_NAME")
    public String getErrorName() {
        return this.errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    @Column(name = "NAME")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Column(name = "WARNING_LV")
    public Integer getWarningLv() {
        return this.warningLv;
    }

    public void setWarningLv(Integer warningLv) {
        this.warningLv = warningLv;
    }


    @Column(name = "WARNING_NAME")
    public String getWarningName() {
        return this.warningName;
    }

    public void setWarningName(String warningName) {
        this.warningName = warningName;
    }

}
