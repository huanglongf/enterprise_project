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

import java.text.ParseException;
import java.util.Date;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.util.DateUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 历史库存查询
 * 
 * @author sjk
 * 
 */
public class InventoryHistoricalQueryAction extends BaseJQGridProfileAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = -8361757185145675819L;
    @Autowired
    private WareHouseManager manager;
    @Autowired
    private AuthorizationManager authorizationManager;

    private String date;
    private InventoryCommand inventory;

    public String historicalQueryEntry() {
        return SUCCESS;
    }

    /**
     * 查询某时间点库存
     * 
     * @return
     * @throws ParseException
     */
    public String historicalInventoryQurey() throws ParseException {
        Long companyid = null;
        if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_COMPANY)) {
            companyid = userDetails.getCurrentOu().getId();
        } else if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_OPERATION_CENTER)) {
            OperationUnit ou = authorizationManager.getOUByPrimaryKey(userDetails.getCurrentOu().getId());
            companyid = ou.getParentUnit().getId();
        } else if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_WAREHOUSE)) {
            OperationUnit ou = authorizationManager.getOUByPrimaryKey(userDetails.getCurrentOu().getId());
            companyid = ou.getParentUnit().getParentUnit().getId();
        }
        setTableConfig();
        Date dat = FormatUtil.stringToDate(date);
        boolean b = DateUtil.isYearDate(date);
        if (b) // 日期为"yyy-mm-dd"默认补加一天
        {
            dat = DateUtil.addDays(dat, 1);
        }

        Pagination<InventoryCommand> page = manager.findSnapShotPageByPreciseTime(tableConfig.getStart(), tableConfig.getPageSize(), dat, inventory, userDetails.getCurrentOu().getId(), companyid, tableConfig.getSorts());
        request.put(JSON, toJson(page));
        return JSON;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public InventoryCommand getInventory() {
        return inventory;
    }

    public void setInventory(InventoryCommand inventory) {
        this.inventory = inventory;
    }

}
