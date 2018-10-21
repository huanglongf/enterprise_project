package com.jumbo.wms.model.vmi.ids;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

@Entity
@Table(name = "T_IDS_INV_SYNCHRONOUS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class IdsInventorySynchronous extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2939465574043618325L;

    private Long id;

    private int version;

    private String batchID;

    /**
     * 商户编号
     */
    private String storerKey;

    /**
     * 商户编号
     */
    private String facility;

    /**
     * 到达仓库编码（仅用于转仓）
     */
    private String toFacility;

    /**
     * 同步类型 REC-收货 ADJ-调整 IQC-状态转移 TRF-ECOM->大仓或大仓->ECOM
     */
    private String iTRType;

    /**
     * 仓库作业单号
     */
    private String wMSDocKey;

    /**
     * 创建时间
     */
    private Date createDate;


    private DefaultStatus status;

    /**
     * 客户参考号
     */
    private String customerRefNo;

    /**
     * 其它参考号
     */
    private String otherRefNo;

    /**
     * 出入库时间
     */
    private String effectiveDate;

    /**
     * 原因编码
     */
    private String reasonCode;

    /**
     * 原因编码
     */
    private String remark;

    /**
     * 作业单号
     */
    private String staCode;

    /**
     * 来源
     */
    private String source = "";

    /**
     * 是否邮件通知 1：是
     */
    private Long isSend;

    /**
     * 下次执行时间
     */
    private Date nextExecuteTime;

    /**
     * 错误次数
     */
    private Integer errorCount;



    @Column(name = "NEXT_EXECUTE_TIME")
    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "IS_SEND")
    public Long getIsSend() {
        return isSend;
    }

    public void setIsSend(Long isSend) {
        this.isSend = isSend;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_IDS_INV_SYN", sequenceName = "S_T_IDS_INV_SYN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_IDS_INV_SYN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "BATCH_ID", length = 20)
    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    @Column(name = "STORER_KEY", length = 20)
    public String getStorerKey() {
        return storerKey;
    }

    public void setStorerKey(String storerKey) {
        this.storerKey = storerKey;
    }

    @Column(name = "FACILITY", length = 20)
    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    @Column(name = "TO_FACILITY", length = 20)
    public String getToFacility() {
        return toFacility;
    }

    public void setToFacility(String toFacility) {
        this.toFacility = toFacility;
    }

    @Column(name = "IT_RTYPE", length = 10)
    public String getiTRType() {
        return iTRType;
    }

    public void setiTRType(String iTRType) {
        this.iTRType = iTRType;
    }

    @Column(name = "WMS_DOCKEY", length = 20)
    public String getwMSDocKey() {
        return wMSDocKey;
    }

    public void setwMSDocKey(String wMSDocKey) {
        this.wMSDocKey = wMSDocKey;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "CUSTOMERREF_NO", length = 20)
    public String getCustomerRefNo() {
        return customerRefNo;
    }

    public void setCustomerRefNo(String customerRefNo) {
        this.customerRefNo = customerRefNo;
    }

    @Column(name = "OTHERREF_NO", length = 20)
    public String getOtherRefNo() {
        return otherRefNo;
    }

    public void setOtherRefNo(String otherRefNo) {
        this.otherRefNo = otherRefNo;
    }

    @Column(name = "EFFECTIVE_DATE", length = 20)
    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Column(name = "REASON_CODE", length = 20)
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Column(name = "REMARK", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
