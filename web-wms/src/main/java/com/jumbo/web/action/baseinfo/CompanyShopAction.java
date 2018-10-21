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
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class CompanyShopAction extends BaseJQGridProfileAction /* BaseProfileAction */{

    private static final long serialVersionUID = -4271727572338359892L;

    @Autowired
    private BaseinfoManager baseinfoManager;
    private OperationUnit ou;
    private BiChannel companyShop;


    /**
     * 根据登录用户查找相应组织店铺信息
     * 
     * @return
     */
    @Override
    public String execute() {
        /**
         * 取得当前登录用户的组织
         */
        // ou = getCurrentOu();
        ou = userDetails.getCurrentOu();
        /**
         * 根据组织id查找 相应的公司附加信息
         */
        companyShop = null;//baseinfoManager.findCompanyShopByOuId(ou.getId());
        return SUCCESS;
    }

    public String findLogistics() {
        setTableConfig();
        request.put(JSON, toJson(baseinfoManager.findTransportatorByShop(this.userDetails.getCurrentOu().getId())));
        return JSON;
    }

    public String findWarehouse() {
        setTableConfig();
        request.put(JSON, toJson(baseinfoManager.findWarehouseByShop(this.userDetails.getCurrentOu().getId())));
        return JSON;
    }



    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public BiChannel getCompanyShop() {
        return companyShop;
    }

    public void setCompanyShop(BiChannel companyShop) {
        this.companyShop = companyShop;
    }

}
