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
package com.jumbo.web.action.baseinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Company;
import com.jumbo.web.action.BaseProfileAction;

public class CompanyAction extends BaseProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2204980356252296470L;

    @Autowired
    private BaseinfoManager baseinfoManager;

    private OperationUnit ou;
    private Company company;

    /**
     * 根据登录用户查找相应组织(公司)信息
     * 
     * @return
     */
    @Override
    public String execute() {
        /**
         * 取得当前登录用户的组织
         */
        ou = getCurrentOu();
        /**
         * 根据组织id查找 相应的公司附加信息
         */
        company = baseinfoManager.findCompanyByOuId(ou.getId());
        return SUCCESS;
    }

    /**
     * 编辑修改登录用户的组织(公司)信息
     * 
     * @return
     */
    public String updateCompanyInfo() {
        ou.setId(this.getCurrentOu().getId());
        baseinfoManager.updateCompany(company, ou);
        return JSON;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
