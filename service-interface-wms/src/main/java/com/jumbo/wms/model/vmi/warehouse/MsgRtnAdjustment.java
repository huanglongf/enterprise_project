package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

@Entity
@Table(name = "T_WH_MSG_RTN_ADJUSTMENT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRtnAdjustment extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 9106816894038248908L;
    /**
     * PK
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态
     */
    private DefaultStatus status;
    /**
     * 来源
     */
    private String source;
    /**
     * 来源仓库
     */
    private String sourceWh;

    /**
     * 处理单据号
     */
    private Long entityId;

    /**
     * ids 单据号
     */
    private String idsKey;

    /**
     * ids 调整时间
     */
    private Date effectiveDate;

    private int version;

    private String ownerSource;

    /**
     * uuid
     */
    private Long uuid;

    /**
     * 单据唯一对接编码
     */
    private String orderCode;
    /**
     * 备注
     */
    private String memo;
    /**
     * 扩展字段
     */
    private String extMemo;

    /**
     * 类型 50
     */
    private Long type;


    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MSGO", sequenceName = "S_T_WH_MSG_RTN_ADJUSTMENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MSGO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "SOURCE_WH", length = 30)
    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }

    @Column(name = "R_ENTITY_ID", length = 100)
    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Column(name = "IDS_kEY", length = 10)
    public String getIdsKey() {
        return idsKey;
    }

    public void setIdsKey(String idsKey) {
        this.idsKey = idsKey;
    }

    @Column(name = "EFFECTIVE_DATE", length = 30)
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "OWNER_WH_SOURCE")
    public String getOwnerSource() {
        return ownerSource;
    }

    public void setOwnerSource(String ownerSource) {
        this.ownerSource = ownerSource;
    }

    @Column(name = "UUID")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "TYPE")
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
