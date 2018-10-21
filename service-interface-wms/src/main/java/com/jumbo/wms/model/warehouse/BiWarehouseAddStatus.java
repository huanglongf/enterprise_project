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
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 仓库流程可配状态列表 bin.hu
 */
@Entity
@Table(name = "T_BI_WAREHOUSE_ADD_STATUS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class BiWarehouseAddStatus extends BaseModel implements Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -4665998421802054920L;

    /**
     * PK
     */
    private Long id;

    /**
     *类型 1、配货清单类
     */
    private WhAddTypeMode type;

    /**
     * 状态 21、待打印 24、待拣货 26、待分拣 29、待核对
     */
    private WhAddStatusMode status;

    /**
     * 仓库信息外键
     */
    private OperationUnit operationUnit;

    /**
     * 版本
     */
    private int version;

    /**
     * 顺序
     */
    private int sort;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_BI_WAREHOUSE_ADD_STATUS", sequenceName = "S_T_BI_WAREHOUSE_ADD_STATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BI_WAREHOUSE_ADD_STATUS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhAddTypeMode")})
    public WhAddTypeMode getType() {
        return type;
    }

    public void setType(WhAddTypeMode type) {
        this.type = type;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhAddStatusMode")})
    public WhAddStatusMode getStatus() {
        return status;
    }

    public void setStatus(WhAddStatusMode status) {
        this.status = status;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_AU_OPERATION_UNIT_ID")
    public OperationUnit getOperationUnit() {
        return operationUnit;
    }

    public void setOperationUnit(OperationUnit operationUnit) {
        this.operationUnit = operationUnit;
    }

    @Column(name = "SORT")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
