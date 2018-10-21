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

package com.jumbo.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.jumbo.web.action.UserDetailsAware;
import com.jumbo.web.security.Acl;
import com.jumbo.web.security.UserDetails;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorizationInterceptor extends AbstractInterceptor implements StrutsStatics {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3704650109877365290L;

    protected static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        logger.debug("Start AuthenticationInterceptor");
        final Object action = invocation.getAction();

        String strMethod = invocation.getProxy().getMethod();
        Method m = getActionMethod(action.getClass(), strMethod);
        Acl acl = m.getAnnotation(Acl.class);
        if (acl == null) acl = action.getClass().getAnnotation(Acl.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean needCheck = (acl != null);

        if (needCheck) {
            Assert.notNull(authentication, "authentication object should not be null after security authentication.");
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.debug("Current Principal:" + userDetails);

            if (!userDetails.check(acl.value())) {
                logger.debug("No sufficicent privilege.");
                // throw new BusinessException(ErrorCode.NO_SUFFICICENT_PRIVILEGE);
                final ActionContext context = invocation.getInvocationContext();
                HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);
                response.sendRedirect("/accessdenied.jsp");
            }
        }

        if (action instanceof UserDetailsAware) {
            Assert.notNull(authentication, "authentication object should not be null for profileaction.");
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            UserDetailsAware uda = (UserDetailsAware) action;
            uda.setUserDetails(userDetails);
        }

        return invocation.invoke();
    }

    protected Method getActionMethod(Class<?> actionClass, String methodName) throws NoSuchMethodException {
        Method method;
        try {
            method = actionClass.getMethod(methodName, new Class[0]);
        } catch (NoSuchMethodException e) {
            // hmm -- OK, try doXxx instead
            try {
                String altMethodName = "do" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
                method = actionClass.getMethod(altMethodName, new Class[0]);
            } catch (NoSuchMethodException e1) {
                // throw the original one
                throw e;
            }
        }
        return method;
    }
}
