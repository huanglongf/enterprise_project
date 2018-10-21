package com.jumbo.wms.model.warehouse;

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

/**
 * 系统仓库可配状态列表 bin.hu
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "T_SYS_WH_ADD_STATUS_SOURCE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhAddStatusSource extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -287237981504361447L;

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
     * 是否需要状态 0、不必要 1、需要
     */
    private int isNecessary;

    /**
     * 版本
     */
    private int version;

    /**
     * 状态描述
     */
    private String statusName;

    private int sort;

    private String typeName;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_ADD_STATUS_SOURCE", sequenceName = "S_T_SYS_WH_ADD_STATUS_SOURCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_ADD_STATUS_SOURCE")
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

    @Column(name = "IS_NECESSARY")
    public int getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(int isNecessary) {
        this.isNecessary = isNecessary;
    }


    @Column(name = "SORT")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Column(name = "STATUS_NAME")
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Column(name = "TYPE_NAME")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }



}
