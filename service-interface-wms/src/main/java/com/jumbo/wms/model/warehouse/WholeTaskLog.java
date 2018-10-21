package com.jumbo.wms.model.warehouse;

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

/**
 * 上传任务记录表
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_WH_TASK_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WholeTaskLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5612739806479622485L;
    /**
     * ID
     */
    private Long ID;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 类型
     */
    private WholeTaskLogType type;

    /**
     * 节点
     */
    private WholeTaskLogStatus status;

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "SEQ_TASK_LOG", sequenceName = "S_T_WH_TASK_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TASK_LOG")
    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WholeTaskLogType")})
    public WholeTaskLogType getType() {
        return type;
    }

    public void setType(WholeTaskLogType type) {
        this.type = type;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WholeTaskLogStatus")})
    public WholeTaskLogStatus getStatus() {
        return status;
    }

    public void setStatus(WholeTaskLogStatus status) {
        this.status = status;
    }

}
