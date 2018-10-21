package com.jumbo.wms.model.baseinfo;

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
 * 接口安全认证信息
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_BI_INTERFACE_SECURITY_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class InterfaceSecurityInfo extends BaseModel {
    private static final long serialVersionUID = 8724253602224654284L;

    /**
     * PK
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 合作方
     */
    private String source;
    /**
     * 过期时间
     */
    private Date expDate;
    /**
     * 状态
     */
    private InterfaceSecurityInfoStatus status;
    /**
     * 帐套ID
     */
    private Long accountSetId;
    
    /**
     * 加密机制
     */
    private String encryptionType;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_IS", sequenceName = "S_T_BI_INTERFACE_SECURITY_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USERNAME", length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "EXP_DATE")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.InterfaceSecurityInfoStatus")})
    public InterfaceSecurityInfoStatus getStatus() {
        return status;
    }

    public void setStatus(InterfaceSecurityInfoStatus status) {
        this.status = status;
    }

    @Column(name = "ACCOUNTSET_ID")
    public Long getAccountSetId() {
        return accountSetId;
    }

    public void setAccountSetId(Long accountSetId) {
        this.accountSetId = accountSetId;
    }

    @Column(name = "SOURCE", length = 50)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "ENCRYPTION_TYPE", length = 50)
    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

}
