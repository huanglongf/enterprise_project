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

import com.jumbo.wms.model.BaseModel;

/**
 * 物流变更配置信息
 * 
 * @author jinggang.chen
 *
 */
@Entity
@Table(name = "T_WH_DELIVERY_CHANGE_CONFIGURE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class DeliveryChangeConfigure extends BaseModel {

    private static final long serialVersionUID = 6650079293849064496L;

    /**
     * pk
     */
    private Long id;

    /**
     * 原始物流商
     */
    private String lpcode;

    /**
     * 目标物流商
     */
    private String newLpcode;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 保留字段
     */
    private String extCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_DELIVERY_CHANGE_CONFIGURE", sequenceName = "S_T_DELIVERY_CHANGE_CONFIGURE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DELIVERY_CHANGE_CONFIGURE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LPCODE")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "NEWLPCODE")
    public String getNewLpcode() {
        return newLpcode;
    }

    public void setNewLpcode(String newLpcode) {
        this.newLpcode = newLpcode;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "EXTCODE")
    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

}
