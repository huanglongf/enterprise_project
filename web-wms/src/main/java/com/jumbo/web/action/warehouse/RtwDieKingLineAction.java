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
package com.jumbo.web.action.warehouse;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.RtwDieKingLineManager;
import com.jumbo.wms.model.command.RtwDiekingCommend;
import com.jumbo.wms.model.warehouse.RtwDieking;

/**
 * 
 * @author jumbo
 * 
 */
public class RtwDieKingLineAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 7204734504759119078L;
    @Autowired
    private RtwDieKingLineManager rtwDieKingLineManager;

    public RtwDieking getRtwDieking() {
        return rtwDieking;
    }

    public void setRtwDieking(RtwDieking rtwDieking) {
        this.rtwDieking = rtwDieking;
    }

    public RtwDiekingCommend getRtwDiekingCommend() {
        return rtwDiekingCommend;
    }

    public void setRtwDiekingCommend(RtwDiekingCommend rtwDiekingCommend) {
        this.rtwDiekingCommend = rtwDiekingCommend;
    }



    public String getShoplist() {
        return shoplist;
    }

    public void setShoplist(String shoplist) {
        this.shoplist = shoplist;
    }



    private RtwDieking rtwDieking;

    private RtwDiekingCommend rtwDiekingCommend;
    /**
     * 店铺列表
     */
    private String shoplist;



    public String showRtwDieKingLine() {
        return SUCCESS;
    }


    public String getRtwDiekingList() {
        setTableConfig();
        /*
         * if (rtwDieking != null) {
         * rtwDieking.setStartCreateTime1(FormatUtil.getDate(rtwDieking.getStartCreateTime1()));
         * rtwDieking.setEndCreateTime1(FormatUtil.getDate(rtwDieking.getEndCreateTime1())); }
         */
        Sort[] ss = tableConfig.getSorts();
        for (int i = 0; i < ss.length; i++) {
            Sort s = ss[i];
            s.setField("d.batch_code");
            s.setType("desc");
        }
        Pagination<RtwDiekingCommend> outboundPackageStaList = rtwDieKingLineManager.getRtwDiekingList(tableConfig.getStart(), tableConfig.getPageSize(), rtwDiekingCommend, shoplist, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(outboundPackageStaList));
        return JSON;
    }



}
