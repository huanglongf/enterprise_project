package com.jumbo.web.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class ExceptionInterceptor extends ExceptionMappingInterceptor {

    /**
	 *
	 */
    private static final long serialVersionUID = -108722874114862093L;
    private static final Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);

    private boolean debug = false;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        final ActionContext context = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
        boolean isXhr = (request.getHeader("X-Requested-With") != null);
        String result = super.intercept(invocation);
        if (isXhr) {
            logger.debug("This is one xhr equest.");
            return "json";
        } else {
            return result;
        }
    }

    private List<String> getErrMessage(ActionInvocation invocation, ExceptionHolder exceptionHolder) {
        List<String> errMessage = new ArrayList<String>();
        if (exceptionHolder.getException() instanceof BusinessException) {
            errMessage = getBusiExceptionMessage(invocation, (BusinessException) exceptionHolder.getException());
        } else {
            errMessage.add(getMessage(invocation, "system_error", null));
        }
        return errMessage;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishException(ActionInvocation invocation, ExceptionHolder exceptionHolder) {
        final ActionContext context = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
        boolean isXhr = (request.getHeader("X-Requested-With") != null);

        @SuppressWarnings("rawtypes")
        Map req = (Map) invocation.getInvocationContext().get("request");
        Map<String, Object> exceptionMap = new HashMap<String, Object>();
        exceptionMap.put("exception", exceptionHolder.getException());
        exceptionMap.put("errorMessages", getErrMessage(invocation, exceptionHolder));
        exceptionMap.put("stackTrace", exceptionHolder.getExceptionStack());
        if (isXhr) {
            req.put("json", exceptionMap);
        } else {
            req.put("exception", exceptionMap);
        }
        if (!(exceptionHolder.getException() instanceof BusinessException)) {
            if (debug) {
                exceptionHolder.getException().printStackTrace();
            }
        }
    }

    private List<String> getBusiExceptionMessage(ActionInvocation invocation, BusinessException e) {
        List<String> errors = new ArrayList<String>();
        BusinessException be = e;
        while (be != null) {
            String msgKey = "business_exception_" + be.getErrorCode();
            errors.add(getMessage(invocation, msgKey, be.getArgs()));
            be = be.getLinkedException();
        }

        return errors;
    }

    private String getMessage(ActionInvocation invocation, String msgKey, Object[] args) {
        Locale locale = invocation.getInvocationContext().getLocale();
        return LocalizedTextUtil.findText(invocation.getAction().getClass(), msgKey, locale, msgKey, args);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
