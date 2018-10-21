package com.jumbo.wms.model.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 * 物流商扩展信息
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_TRANS_EMS")
public class TransEmsInfo extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3420168464680213059L;
    /**
     * PK
     */
    private Long id;

    private String account;

    private String password;

    /**
     * appKey
     */
    private String appKey;
    /**
     * 是否支持COD
     */
    private Boolean isCod;

    /**
     * 是否是默认账号
     */
    private Boolean isDefaultAccount;

    private String accountName;// 大客户号

    private String authorization;// 授权码

    private Date expireTime;// 失效日期

    private String flashToken;// 刷新码

    private Date flashTokenExpireTime;// 刷新码失效日期

    private String key;// 秘钥key

    private String secret;// 秘钥密码

    private Integer type;// 1:新

    @Column(name = "ACCOUNT_NAME")
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Column(name = "AUTHORIZATION")
    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Column(name = "EXPIRE_TIME")
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Column(name = "FLASH_TOKEN")
    public String getFlashToken() {
        return flashToken;
    }

    public void setFlashToken(String flashToken) {
        this.flashToken = flashToken;
    }

    @Column(name = "FLASHTOKEN_EXPIRETIME")
    public Date getFlashTokenExpireTime() {
        return flashTokenExpireTime;
    }

    public void setFlashTokenExpireTime(Date flashTokenExpireTime) {
        this.flashTokenExpireTime = flashTokenExpireTime;
    }

    @Column(name = "KEY")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "SECRET")
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_twts", sequenceName = "s_T_WH_TRANS_EMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_twts")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "is_default_account")
    public Boolean getIsDefaultAccount() {
        return isDefaultAccount;
    }

    public void setIsDefaultAccount(Boolean isDefaultAccount) {
        this.isDefaultAccount = isDefaultAccount;
    }

    @Column(name = "ACCOUNT", length = 100)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "PASSWORD", length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "IS_COD")
    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    @Column(name = "APPKEY")
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

}
