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

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.checklist.CheckListManager;

// import org.springframework.beans.factory.annotation.Autowired;

// import com.jumbo.wms.manager.baseinfo.AccountSetManager;
// import com.jumbo.util.JsonUtil;

public class LoginAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 545620897952892021L;
    @Autowired
    private CheckListManager checkListManager;

    // @Autowired
    // private AccountSetManager accountManager;

    // public String getAccountSets() throws Exception {
    // request.put(JSON, JsonUtil.collection2json(accountManager.getAccountSetList()));
    // return JSON;
    // }

    public void checkService() throws IOException {
        boolean isSuccessSer = checkListManager.checkDubboService(new Date());
        boolean isSuccessDb = checkListManager.checkDd(new Date());
        ServletActionContext.getResponse().setContentType("text/plain;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write("wms dubbo service check : " + isSuccessSer + "\n");
        ServletActionContext.getResponse().getWriter().write("wms dubbo service check : " + isSuccessDb);
    }
}
