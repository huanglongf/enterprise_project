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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import loxia.support.LoxiaSupportConstants;
import loxia.support.LoxiaSupportSettings;
import loxia.utils.DateUtil;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.baozun.scm.baseservice.account.SdkConstants;
import com.baozun.scm.baseservice.account.SdkContext;


public class InitServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
/*        DateUtil.applyPattern(LoxiaSupportSettings.getInstance().get(LoxiaSupportConstants.DATE_PATTERN));
        SdkConstants sdkConstants = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean("sdkConstants", SdkConstants.class);
        SdkContext.init(sdkConstants);*/
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

}
