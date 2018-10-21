package com.jumbo.wms.model.pickZone;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.Zoon;


/**
 * The persistent class for the T_WH_PICK_ZOON database table.
 * 
 */
@Entity
@Table(name = "T_WH_PICK_ZOON")
public class WhPickZoon extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4590269635106272595L;
    private Long id;
    /**
     * 编码
     */
    private String code;
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
    private User lastModifyUser;
    /**
     * 名称
     */
    private String name;

    /**
     * 状态: 1.可用 0. 禁用
     */
    private Integer status;

    /**
     * 相关仓库
     */
    private OperationUnit ou;

    /**
     * 区域(自动化)
     */
    private Zoon zoon;

    @Id
    @SequenceGenerator(name = "T_WH_PICK_ZOON_ID_GENERATOR", sequenceName = "S_T_WH_PICK_ZOON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_WH_PICK_ZOON_ID_GENERATOR")
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


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    public User getCreatorUser() {
        return creatorUser;
    }


    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }



    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_MODIFY_USER_ID")
    public User getLastModifyUser() {
        return lastModifyUser;
    }


    public void setLastModifyUser(User lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    @Column(name = "NAME")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_OU_ID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wh_zoon_id")
    public Zoon getZoon() {
        return zoon;
    }

    public void setZoon(Zoon zoon) {
        this.zoon = zoon;
    }



}
