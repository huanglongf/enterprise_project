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

package com.jumbo.wms.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 系统登录日志
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_SYS_LOGIN_LOG")
public class SysLoginLog extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1152288322589217907L;

    /**
     * PK
     */
    private Long id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录方式
     */
    private LoginFrom loginFrom;

    /**
     * 远程IP（Web登录时有效）
     */
    private String remoteAddress;

    /**
     * 登录相关信息，如使用浏览器，对方操作系统等（Web登录时有效）
     */
    private String userAgent;

    /**
     * 登录结果
     */
    private LoginStatus loginStatus;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SLL", sequenceName = "S_T_SYS_LOGIN_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SLL")
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

    @Column(name = "LOGIN_TIME")
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "LOGIN_FROM", length = 50)
    public LoginFrom getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(LoginFrom loginFrom) {
        this.loginFrom = loginFrom;
    }

    @Column(name = "REMOTE_ADDR", length = 50)
    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Column(name = "USER_AGENT", length = 500)
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "LOGIN_STATUS", length = 50)
    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }
}
