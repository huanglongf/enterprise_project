package com.jumbo.wms.model.warehouse;

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

/**
 * 配货清单操作日志 bin.hu
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "T_WH_STA_PICKING_LIST_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhStaPickingListLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4416713892263165058L;

    /**
     * PK
     */
    private Long id;

    /**
     * 配货清单
     */
    private PickingList pickList;

    /**
     * 日志时间
     */
    private Date logTime;

    /**
     * 附加状态
     */
    private WhAddStatusMode addStatus;

    /**
     * 状态
     */
    private WhAddStatusMode status;

    /**
     * 操作人
     */
    private Long userId;

    /**
     * 版本
     */
    private int version;

    /**
     * 备注
     */
    private String memo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PKLTLOG", sequenceName = "S_T_WH_STA_PICKING_LIST_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PKLTLOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICK_LIST_ID")
    @Index(name = "IDX_WH_STA_PICKING_LOG")
    public PickingList getPickList() {
        return pickList;
    }

    public void setPickList(PickingList pickList) {
        this.pickList = pickList;
    }

    @Column(name = "LOG_TIME")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    @Column(name = "ADD_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhAddStatusMode")})
    public WhAddStatusMode getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(WhAddStatusMode addStatus) {
        this.addStatus = addStatus;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhAddStatusMode")})
    public WhAddStatusMode getStatus() {
        return status;
    }

    public void setStatus(WhAddStatusMode status) {
        this.status = status;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
