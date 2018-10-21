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

package com.jumbo.wms.model.command;

import java.util.HashMap;
import java.util.Map;

import com.jumbo.util.StringUtils;

import com.jumbo.wms.model.BaseModel;

public class UserGroupCommand extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1080643394182581784L;
    /**
     * 登录名
     */
    private String loginName;
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

    public String getLoginName() {
        return loginName;
    }


    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.hasLength(loginName)) {
            map.put("loginName", "%" + loginName + "%");
        }
        if (StringUtils.hasLength(userName)) {
            map.put("userName", userName);
        }
        if (StringUtils.hasLength(jobNumber)) {
            map.put("jobNumber", "%" + jobNumber);
        }
        if (StringUtils.hasLength(isEnabled)) {
            map.put("isEnabled", Boolean.valueOf(isEnabled) ? 1 : 0);
        }
        if (null != groupId) {
            map.put("groupId", groupId);
        }
        return map;
    }
}
