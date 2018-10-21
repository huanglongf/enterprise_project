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

package com.jumbo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loxia.support.json.JSONObject;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@SuppressWarnings("deprecation")
public class AjaxLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (((HttpServletRequest) request).getHeader("X-Requested-With") != null) {
            Map<String, Object> exceptionMap = new HashMap<String, Object>();
            exceptionMap.put("exception", authException);
            exceptionMap.put("errorMessages", authException.getMessage());
            exceptionMap.put("stackTrace", getExceptionStackTrace(authException));
            response.setContentType("application/json");
            response.setHeader("Content-Disposition", "inline");
            PrintWriter writer = response.getWriter();
            if (writer != null) {
                writer.write(new JSONObject(exceptionMap).toString());
                writer.flush();
                writer.close();
            }
            response.flushBuffer();
        } else
            super.commence(request, response, authException);
    }

    private String getExceptionStackTrace(Exception exception) {
        String exceptionStack = null;
        if (exception == null) return null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            exception.printStackTrace(pw);
            exceptionStack = sw.toString();
        } finally {
            try {
                sw.close();
                pw.close();
            } catch (IOException e) {
                // ignore
            }
        }
        return exceptionStack;
    }
}
