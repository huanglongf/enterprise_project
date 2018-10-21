package com.jumbo.wms.model.authorization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.jumbo.wms.model.BaseModel;

/**
 * 密码规则
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "syspwdpolicy")
public class SysPwdPolicy extends BaseModel {


    private static final long serialVersionUID = -2759250380965388125L;
    /**
     * PK
     */
    private Long id;
    /**
     * VERSION
     */
    private Integer version;
    /**
     * 密码有效期
     */
    private Integer pwdlegaldays;
    /**
     * 首次登录改密码
     */
    private Boolean changePwdWhenFirstLogin;
    /**
     * 密码最小长度
     */
    private Integer pwdMinLength;
    /**
     * 密码规则
     */
    private String policy;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SPP", sequenceName = "S_PWDPOLICYID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SPP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "PWDLEGALDAYS")
    public Integer getPwdlegaldays() {
        return pwdlegaldays;
    }

    public void setPwdlegaldays(Integer pwdlegaldays) {
        this.pwdlegaldays = pwdlegaldays;
    }

    @Column(name = "CHANGEPWDWHENFIRSTLOGIN")
    public Boolean getChangePwdWhenFirstLogin() {
        return changePwdWhenFirstLogin;
    }

    public void setChangePwdWhenFirstLogin(Boolean changePwdWhenFirstLogin) {
        this.changePwdWhenFirstLogin = changePwdWhenFirstLogin;
    }

    @Column(name = "PWDMINLENGTH")
    public Integer getPwdMinLength() {
        return pwdMinLength;
    }

    public void setPwdMinLength(Integer pwdMinLength) {
        this.pwdMinLength = pwdMinLength;
    }

    @Column(name = "POLICY", length = 80)
    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

}
