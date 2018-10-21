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

package com.jumbo.event.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import loxia.dao.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;

import com.jumbo.web.WebBzAuthenticationDetails;
import com.jumbo.web.security.UserDetails;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.model.system.LoginFrom;
import com.jumbo.wms.model.system.LoginStatus;
import com.jumbo.wms.model.system.SysLoginLog;
import com.jumbo.wms.model.system.SysLoginLogCommand;

public class HttpSessionEventListener implements ApplicationListener<SessionDestroyedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(HttpSessionEventListener.class);
    @Autowired
    private AuthorizationManager authManager;

    public void onApplicationEvent(SessionDestroyedEvent event) {
        if (event.getSecurityContexts() == null) return;
        List<SecurityContext> list = event.getSecurityContexts();
        Authentication authentication = null;
        // UAC size为0
        if(0<list.size()){
            for(int i=0; i<list.size(); i++){
                authentication = event.getSecurityContexts().get(i).getAuthentication();
                if (authentication != null) {
                    log(event, authentication);
                }
            }
        }
        /*if (authentication != null) {
            log(event, authentication);
        }*/
    }

    private void log(SessionDestroyedEvent event, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails == null) return;
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setLoginName(userDetails.getUsername());
        loginLog.setLoginTime(new Date());
        Object authenticationDetails = authentication.getDetails();
        if (authenticationDetails instanceof WebBzAuthenticationDetails) {
            WebBzAuthenticationDetails wad = (WebBzAuthenticationDetails) authenticationDetails;
            loginLog.setLoginFrom(LoginFrom.WEB);
            loginLog.setRemoteAddress(wad.getRemoteAddress());
            loginLog.setUserAgent(wad.getUserAgent());
        }
        loginLog.setLoginStatus(LoginStatus.SESSION_TIMEOUT);
        authManager.saveLoginLog(loginLog);
        HttpSession session = (HttpSession) event.getSource();
        Map<String, Object> cmd = new HashMap<String, Object>();
        cmd.put("loginTimeFrom", new Date(session.getLastAccessedTime()));
        cmd.put("loginStatus", LoginStatus.LOGIN_SUCCESS.name());
        Pagination<SysLoginLogCommand> logs = authManager.findSystemLoginLogPagByCommandSql(0, -1, cmd, null);

        if (logs != null && logs.getCount() > 0) {
            logger.debug("{} session timeout from {},but relogin during the passed time", loginLog.getLoginName(), loginLog.getRemoteAddress());
        } else {
            authManager.updateUserAccessTime(loginLog.getLoginTime(), loginLog.getLoginName());
            authManager.updateUserRoleToDefault(userDetails.getCurrentRole().getId(), userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            logger.debug("{} session timeout from {}", loginLog.getLoginName(), loginLog.getRemoteAddress());
        }
    }
}
