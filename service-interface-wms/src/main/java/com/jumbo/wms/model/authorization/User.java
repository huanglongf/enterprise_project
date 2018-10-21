/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.authorization;

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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 用户
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_AU_USER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class User extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2375857495487182428L;

    /**
     * PK
     */
    private Long id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否系统用户，系统用户只能初始化，不能通过界面功能维护和删除
     */
    private Boolean isSystem = false;

    /**
     * 用户帐号是否未过期，过期帐号无法登录系统
     */
    private Boolean isAccNonExpired = true;

    /**
     * 用户密码是否未失效，过期的密码将无法认证
     */
    private Boolean isPwdNonExpired = true;

    /**
     * 用户帐号是否未被锁定，被锁定的用户无法使用系统
     */
    private Boolean isAccNonLocked = true;

    /**
     * 用户帐号是否可用
     */
    private Boolean isEnabled = true;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date latestUpdateTime;

    /**
     * 最后登录时间
     */
    private Date latestAccessTime;

    /**
     * VERSION
     */
    private int version;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 备注
     */
    private String memo;

    /**
     * 用户对应组织
     */
    private OperationUnit ou;

    /**
     * 交接上限
     */

    private Long maxNum;

    /**
     * 是否拥有核对权限
     */
    private Boolean isCheck;
    /**
     * 是否拥有称重权限
     */
    private Boolean isWeight;
    /**
     * 是否拥有交接权限
     */
    private Boolean isHo;
    /**
     * 是否可以操作缺货
     */
    private Boolean isHaveShortButton;
    /**
     * 是否可以操作短配核对
     */
    private Boolean isShortCheck;



    @Column(name = "MAX_NUM")
    public Long getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Long maxNum) {
        this.maxNum = maxNum;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_USR", sequenceName = "S_T_AU_USER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USR")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LOGIN_NAME", length = 50)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "USER_NAME", length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "PASSWORD", length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "IS_SYSTEM")
    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    @Column(name = "IS_ACC_NON_EXPIRED")
    public Boolean getIsAccNonExpired() {
        return isAccNonExpired;
    }

    public void setIsAccNonExpired(Boolean isAccNonExpired) {
        this.isAccNonExpired = isAccNonExpired;
    }

    @Column(name = "IS_PWD_NON_EXPIRED")
    public Boolean getIsPwdNonExpired() {
        return isPwdNonExpired;
    }

    public void setIsPwdNonExpired(Boolean isPwdNonExpired) {
        this.isPwdNonExpired = isPwdNonExpired;
    }

    @Column(name = "IS_ACC_NON_LOCKED")
    public Boolean getIsAccNonLocked() {
        return isAccNonLocked;
    }

    public void setIsAccNonLocked(Boolean isAccNonLocked) {
        this.isAccNonLocked = isAccNonLocked;
    }

    @Column(name = "IS_ENABLED")
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setIntIsEnabled(String isEnabled) {
        if (isEnabled == null || "".equals(isEnabled) || "null".equals(isEnabled)) {
            this.isEnabled = null;
        } else if ("true".equals(isEnabled)) {
            this.isEnabled = true;
        } else if ("false".equals(isEnabled)) {
            this.isEnabled = false;
        }
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "LATEST_UPDATE_TIME")
    public Date getLatestUpdateTime() {
        return latestUpdateTime;
    }

    public void setLatestUpdateTime(Date latestUpdateTime) {
        this.latestUpdateTime = latestUpdateTime;
    }

    @Column(name = "LATEST_ACCESS_TIME")
    public Date getLatestAccessTime() {
        return latestAccessTime;
    }

    public void setLatestAccessTime(Date latestAccessTime) {
        this.latestAccessTime = latestAccessTime;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "EMAIL", length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "PHONE", length = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "JOB_NUMBER", length = 50)
    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    @Column(name = "MEMO", length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_USR_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "IS_CHECK")
    public Boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }

    @Column(name = "IS_WEIGHT")
    public Boolean getIsWeight() {
        return isWeight;
    }

    public void setIsWeight(Boolean isWeight) {
        this.isWeight = isWeight;
    }

    @Column(name = "IS_HO")
    public Boolean getIsHo() {
        return isHo;
    }

    public void setIsHo(Boolean isHo) {
        this.isHo = isHo;
    }

    @Column(name = "IS_HAVE_SHORT_BUTTON")
    public Boolean getIsHaveShortButton() {
        return isHaveShortButton;
    }

    public void setIsHaveShortButton(Boolean isHaveShortButton) {
        this.isHaveShortButton = isHaveShortButton;
    }

    @Column(name = "IS_SHORT_CHECK")
    public Boolean getIsShortCheck() {
        return isShortCheck;
    }

    public void setIsShortCheck(Boolean isShortCheck) {
        this.isShortCheck = isShortCheck;
    }
}
