package com.jumbo.web.action.pda.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.web.action.pda.base.PdaBaseAction;
import com.jumbo.web.security.UserDetails;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PdaAuthInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4115832657528644541L;
	protected static final Logger logger = LoggerFactory.getLogger(PdaAuthInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.info("pda auth start");
		final Object action = invocation.getAction();
		HttpServletRequest request = ServletActionContext.getRequest();
		if (action instanceof PdaBaseAction) {
			HttpSession session = request.getSession();
			UserDetails userDetails = (UserDetails) session.getAttribute("userDetials");
			if (userDetails == null) {
				logger.info("pda auth failed,to login");
				HttpServletResponse response = ServletActionContext.getResponse();
				response.sendRedirect(request.getContextPath() + "/pda/login.do");
			} else {
				PdaBaseAction uda = (PdaBaseAction) action;
				uda.setUserDetails(userDetails);
			}
		}
		return invocation.invoke();
	}
}
