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

import com.jumbo.web.action.BaseJQGridAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 处理excel通用导出限制功能，限制原先默认全表格导出数据为2W条
 * 
 * @author sjk
 *
 */
public class ExcelExportLimitInterceptor extends AbstractInterceptor implements StrutsStatics {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8597951549058697157L;
	protected static final Logger logger = LoggerFactory.getLogger(ExcelExportLimitInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.debug("intercept start");
		Object obj = invocation.getAction();
		if (obj instanceof BaseJQGridAction) {
			BaseJQGridAction action = (BaseJQGridAction) obj;
			if (action.getIsExcel() != null && action.getIsExcel()) {
				logger.debug("action is excel export");
				if (action.getPage() == 1 && action.getRows() == -1) {
					action.setRows(20000);
					logger.debug("action pager is 1,and rows is -1,set rows = 2w");
				}else if(action.getRows() > 20000){
					action.setRows(20000);
					logger.debug("action rows > 2W,set 2W");
				}
			}
		}
		logger.debug("intercept end");
		return invocation.invoke();
	}

}
