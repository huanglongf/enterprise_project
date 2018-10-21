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



public class SysLoginLogCommand extends SysLoginLog {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1152288322589217907L;

    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 用户帐号是否可用
     */
    private String isEnabled;
    /**
     * 用户对应分组
     */
    private Long groupId;

    /**
     * 登录时间起始
     */
    private Date loginTimeFrom;


    /**
     * 登录时间结束
     */
    private Date loginTimeTo;
    private String loginTimeFrom1;// 得到包含时分秒的时间
    private String loginTimeTo1;

    private String remoteAddr;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Date getLoginTimeFrom() {
        return loginTimeFrom;
    }

    public void setLoginTimeFrom(Date loginTimeFrom) {
        this.loginTimeFrom = loginTimeFrom;
    }

    public Date getLoginTimeTo() {
        return loginTimeTo;
    }

    public void setLoginTimeTo(Date loginTimeTo) {
        this.loginTimeTo = loginTimeTo;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getLoginTimeFrom1() {
        return loginTimeFrom1;
    }

    public void setLoginTimeFrom1(String loginTimeFrom1) {
        this.loginTimeFrom1 = loginTimeFrom1;
    }

    public String getLoginTimeTo1() {
        return loginTimeTo1;
    }

    public void setLoginTimeTo1(String loginTimeTo1) {
        this.loginTimeTo1 = loginTimeTo1;
    }

}
