package com.jumbo.wms.model.automaticEquipment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 通用WCS到WMS通讯实体
 * 
 * @author xiaolong.fei
 * @date 2016年6月18日下午8:39:36
 * 
 */
@Entity
@Table(name = "T_WH_MSG_TO_WMS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class MsgToWms extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 8056669618784894256L;
    private Long id;
    /**
     * 接口信息内容，JSON格式
     */
    private String context;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 接口类型
     */
    private WcsInterfaceType type;

    /**
     * 配货批ID
     */
    private Long pkId;
    /**
     * 状态： 1新建，10 完成， 0 失败
     */
    private Integer status;

    /**
     * 错误次数
     */
    private Integer errorCount;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_MSG_TO_WMS", sequenceName = "S_T_WH_MSG_TO_WMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_MSG_TO_WMS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Lob
    @Column(name = "CONTEXT", columnDefinition = "CLOB")
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "type", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.automaticEquipment.WcsInterfaceType")})
    public WcsInterfaceType getType() {
        return type;
    }

    public void setType(WcsInterfaceType type) {
        this.type = type;
    }

    @Column(name = "pkId")
    public Long getPkId() {
        return pkId;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "error_count")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

}
