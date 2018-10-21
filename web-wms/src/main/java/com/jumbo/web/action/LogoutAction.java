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

package com.jumbo.web.action;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.system.SystemManager;
import com.jumbo.wms.model.system.LoginFrom;
import com.jumbo.wms.model.system.LoginStatus;
import com.jumbo.wms.model.system.SysLoginLog;
import com.jumbo.web.WebBzAuthenticationDetails;

public class LogoutAction extends BaseProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 545620897952892021L;

    @Autowired
    private AuthorizationManager authManager;
    @Autowired
    private SystemManager systemManager;
    private Long roleId;
    private Long ouId;

    public String logout() {
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setLoginName(userDetails.getUsername());
        loginLog.setLoginTime(new Date());
        Object authenticationDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (authenticationDetails instanceof WebBzAuthenticationDetails) {
            WebBzAuthenticationDetails wad = (WebBzAuthenticationDetails) authenticationDetails;
            loginLog.setLoginFrom(LoginFrom.WEB);
            loginLog.setRemoteAddress(wad.getRemoteAddress());
            loginLog.setUserAgent(wad.getUserAgent());
        }
        loginLog.setLoginStatus(LoginStatus.LOGOUT_SUCCESS);
        systemManager.saveSysLoginLog(loginLog);
        authManager.updateUserAccessTimeSql(loginLog.getLoginTime(), loginLog.getLoginName());
        SecurityContextHolder.getContext().setAuthentication(null);
        if (roleId == null) {
            roleId = getCurrentUserRole().getRole().getId();
        }
        if (ouId == null) {
            ouId = getUser().getOu().getId();
        }
        authManager.updateUserRoleToDefault(roleId, ouId, userDetails.getUser().getId());
        return SUCCESS;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }
}
