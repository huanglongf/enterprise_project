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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.model.system.LoginFrom;
import com.jumbo.wms.model.system.LoginStatus;
import com.jumbo.wms.model.system.SysLoginLog;
import com.jumbo.web.WebBzAuthenticationDetails;

public class SysLoginListener implements ApplicationListener<AbstractAuthenticationEvent> {

	private static final Logger logger = LoggerFactory.getLogger(SysLoginListener.class);

	@Autowired
	private AuthorizationManager authorizationManager;

	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		if (event instanceof InteractiveAuthenticationSuccessEvent) {
			logger.debug("InteractiveAuthenticationSuccessEvent is ignored");
			return;
		}
		SysLoginLog loginLog = constructSysLoginLog(event);
		if (loginLog != null) {
			logger.debug("save SysLoginLog");
			authorizationManager.saveLoginLog(loginLog);
			if (loginLog.getLoginStatus() == LoginStatus.LOGIN_SUCCESS) {
				authorizationManager.updateUserAccessTime(loginLog.getLoginTime(), loginLog.getLoginName());
			}
		} else logger.debug("AuthenticationEvent is ignored: {}", event);
	}

	private SysLoginLog constructSysLoginLog(AbstractAuthenticationEvent event) {
		if (event.getAuthentication() == null) return null;
		logger.debug("construct SysLoginLog via AuthenticationEvent");
		SysLoginLog loginLog = new SysLoginLog();
		loginLog.setLoginName(event.getAuthentication().getName());
		loginLog.setLoginTime(new Date());
		Object authenticationDetails = event.getAuthentication().getDetails();
		if (authenticationDetails instanceof WebBzAuthenticationDetails) {
			WebBzAuthenticationDetails wad = (WebBzAuthenticationDetails) authenticationDetails;
			loginLog.setLoginFrom(LoginFrom.WEB);
			loginLog.setRemoteAddress(wad.getRemoteAddress());
			loginLog.setUserAgent(wad.getUserAgent());
		}
		if (event instanceof AuthenticationSuccessEvent) {
			loginLog.setLoginStatus(LoginStatus.LOGIN_SUCCESS);
		} else if (event instanceof AbstractAuthenticationFailureEvent) loginLog.setLoginStatus(determineLoginStatus((AbstractAuthenticationFailureEvent) event));

		return loginLog;
	}

	private LoginStatus determineLoginStatus(AbstractAuthenticationFailureEvent event) {
		if (event instanceof AuthenticationFailureBadCredentialsEvent)
			return LoginStatus.LOGIN_FAILURE_BAD_CREDENTIAL;
		else if (event instanceof AuthenticationFailureCredentialsExpiredEvent)
			return LoginStatus.LOGIN_FAILURE_CREDENTIAL_EXPIRED;
		else if (event instanceof AuthenticationFailureDisabledEvent)
			return LoginStatus.LOGIN_FAILURE_USER_DISABLED;
		else if (event instanceof AuthenticationFailureExpiredEvent)
			return LoginStatus.LOGIN_FAILURE_USER_EXPIRED;
		else if (event instanceof AuthenticationFailureLockedEvent)
			return LoginStatus.LOGIN_FAILURE_USER_LOCKED;
		else return LoginStatus.LOGIN_FAILURE_OTHERS;
	}
}
