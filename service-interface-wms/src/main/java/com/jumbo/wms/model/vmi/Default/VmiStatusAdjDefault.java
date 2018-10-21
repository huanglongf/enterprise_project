package com.jumbo.wms.model.vmi.Default;

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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * Vmi 调整
 */
@Entity
@Table(name = "T_STA_ADJ")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VmiStatusAdjDefault extends BaseModel {

    private static final long serialVersionUID = -9217658618631041473L;

    public static final String vmiadj = "vmiadj";

    /**
     * id
     */
    private Long id;

    /**
     * 品牌店铺编码t_bi_channel.vmi_code
     */
    private String storeCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 品牌来源，渠道vmi_source
     */
    private String vmiSource;

    /**
     * 转出单据编码
     */
    private String orderCode;

    /**
     * 调整时间
     */
    private String adjDate;

    /**
     * 扩展字段信息
     */
    private String extMemo;

    /**
     * 品牌仓库编码
     */
    private String whCode;

    /**
     * 状态
     */
    private VmiGeneralStatus status;

    /**
     * 调整原因
     */
    private String adjReason;

    /**
     * 作业单ID
     */
    private StockTransApplication staId;

    /**
     * 调整ID
     */
    private InventoryCheck invId;

    /**
     * 异常次数
     */
    private int errorCount;

    /**
     * 版本
     */
    private int version;

    /**
     * 品牌通用接口标识是否品牌定制
     */
    private Boolean isVmiExt;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_ADJ", sequenceName = "S_T_STA_ADJ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_ADJ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "VMI_SOURCE")
    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "WH_CODE")
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.Default.VmiGeneralStatus")})
    public VmiGeneralStatus getStatus() {
        return status;
    }

    public void setStatus(VmiGeneralStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "INDEX_T_STA_ADJ_STA")
    public StockTransApplication getStaId() {
        return staId;
    }

    public void setStaId(StockTransApplication staId) {
        this.staId = staId;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "ERROR_COUNT")
    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "ADJ_DATE")
    public String getAdjDate() {
        return adjDate;
    }

    public void setAdjDate(String adjDate) {
        this.adjDate = adjDate;
    }

    @Column(name = "ADJ_REASON")
    public String getAdjReason() {
        return adjReason;
    }

    public void setAdjReason(String adjReason) {
        this.adjReason = adjReason;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_CK_ID")
    @Index(name = "INDEX_T_STA_ADJ_INV")
    public InventoryCheck getInvId() {
        return invId;
    }

    public void setInvId(InventoryCheck invId) {
        this.invId = invId;
    }

    @Column(name = "IS_VMI_EXT")
    public Boolean getIsVmiExt() {
        return isVmiExt;
    }

    public void setIsVmiExt(Boolean isVmiExt) {
        this.isVmiExt = isVmiExt;
    }

}
