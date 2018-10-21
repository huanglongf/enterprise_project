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

import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ognl.NullHandler;
import ognl.OgnlRuntime;
import java.util.Arrays;
import loxia.utils.InstantiatingNullHandler;
import loxia.utils.OgnlStack;
import ognl.OgnlException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class OgnlNullHandlerInterceptor extends AbstractInterceptor implements StrutsStatics {

    private static final long serialVersionUID = -649869122684113508L;
    protected static final Logger log = LoggerFactory.getLogger(OgnlNullHandlerInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        NullHandler nullHandler = null;
        synchronized (OgnlStack.class) {
            try {
                nullHandler = OgnlRuntime.getNullHandler(Object.class);
                if (nullHandler == null || !(nullHandler instanceof InstantiatingNullHandler)) {
                    log.debug("set null handler");
                    OgnlRuntime.setNullHandler(Object.class, new InstantiatingNullHandler(nullHandler, Arrays.asList("java")));
                }
            } catch (OgnlException e) {
                log.error("",e);
                log.debug(e.getMessage());
                OgnlRuntime.setNullHandler(Object.class, new InstantiatingNullHandler(null, Arrays.asList("java")));
            }
        }
        return invocation.invoke();
    }
}
