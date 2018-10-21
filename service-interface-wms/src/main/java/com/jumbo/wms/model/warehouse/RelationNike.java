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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * NIKE收货-导入箱号关系表
 * 
 * @author wq
 * 
 */

@Entity
@Table(name = "T_WH_RELATION_NIKE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class RelationNike extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -4811455706373823223L;

    /**
     * PK
     */
    private Long id;

    /**
     * 系统箱号
     */
    private String sysPid;

    /**
     * 实物箱号
     */
    private String enPid;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * Version
     */
    private int version;

    /**
     * 仓库
     */
    private OperationUnit whOu;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_RELATION_NIKE", sequenceName = "s_t_wh_relation_nike", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RELATION_NIKE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SYSPID")
    public String getSysPid() {
        return sysPid;
    }

    public void setSysPid(String sysPid) {
        this.sysPid = sysPid;
    }

    @Column(name = "ENPID")
    public String getEnPid() {
        return enPid;
    }

    public void setEnPid(String enPid) {
        this.enPid = enPid;
    }

    @Column(name = "CREATEPERSON")
    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    @Column(name = "CREATETIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    @JoinColumn(name = "WH_OU_ID")
    public OperationUnit getWhOu() {
        return whOu;
    }

    public void setWhOu(OperationUnit whOu) {
        this.whOu = whOu;
    }



}
