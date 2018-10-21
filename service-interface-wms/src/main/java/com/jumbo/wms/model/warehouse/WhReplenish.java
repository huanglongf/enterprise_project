package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 库内补货报表
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_REPLENISH")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhReplenish extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7149848253739747377L;
    /**
     * PK
     */
    private Long id;
    /**
     * 最后补货时间
     */
    private Date lastReplenishTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 状态
     */
    private WhReplenishStatus status;
    /**
     * 单据编码
     */
    private String code;
    /**
     * 类型
     */
    private WhReplenishType type;
    /**
     * 安全警戒线
     */
    private BigDecimal warningPre;
    /**
     * 仓库组织
     */
    private OperationUnit ou;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_REPLENISH", sequenceName = "S_T_WH_REPLENISH", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_REPLENISH")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LAST_REPLENISH_TIME")
    public Date getLastReplenishTime() {
        return lastReplenishTime;
    }

    public void setLastReplenishTime(Date lastReplenishTime) {
        this.lastReplenishTime = lastReplenishTime;
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

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhReplenishStatus")})
    public WhReplenishStatus getStatus() {
        return status;
    }

    public void setStatus(WhReplenishStatus status) {
        this.status = status;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhReplenishType")})
    public WhReplenishType getType() {
        return type;
    }

    public void setType(WhReplenishType type) {
        this.type = type;
    }

    @Column(name = "WARNING_PRE")
    public BigDecimal getWarningPre() {
        return warningPre;
    }

    public void setWarningPre(BigDecimal warningPre) {
        this.warningPre = warningPre;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_REPLENISH_OUID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }
}
