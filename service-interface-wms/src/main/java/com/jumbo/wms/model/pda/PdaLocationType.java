package com.jumbo.wms.model.pda;

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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;


/**
 * 
 * @author lizaibiao
 * 
 */
@Entity
@Table(name = "T_WH_LOCATION_TYPE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PdaLocationType extends BaseModel {
    private static final long serialVersionUID = 4555953896487440573L;
    /**
     * PK
     */
    private Long id;
    /*
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private User creatorUser;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    /**
     * 最后修改人
     */
    private User modifyUser;

    /**
     * 仓库
     * 
     * @return
     */
    private OperationUnit op;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_FK_OU_ID")
    public OperationUnit getOp() {
        return op;
    }

    public void setOp(OperationUnit op) {
        this.op = op;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_LOCATION_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Index(name = "IDX_FK_OP_USER_ID")
    public User getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFY_USER_ID")
    @Index(name = "IDX_FK_OP_USER_ID")
    public User getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(User modifyUser) {
        this.modifyUser = modifyUser;
    }

}
