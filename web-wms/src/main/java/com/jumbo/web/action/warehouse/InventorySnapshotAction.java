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

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 时段库存快照查询
 * 
 * @author sjk
 * 
 */
public class InventorySnapshotAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1536570041154340491L;

    private String startDate;
    private String endDate;
    private InventoryCommand inventory;
    private Long skuId;
    private String owner;

    @Autowired
    private WareHouseManager manager;
    @Autowired
    private AuthorizationManager authorizationManager;

    public String inventorysnapshotEntry() {
        return SUCCESS;
    }

    /**
     * 组织ouid,获取组织仓库信息
     * 
     * @return
     * @throws Exception
     */
    public String getWarehouseData() throws Exception {
        request.put(JSON, JsonUtil.collection2json(manager.getWarehouseData(userDetails.getCurrentOu().getId())));
        return JSON;
    }

    /**
     * 库存快照查询
     * 
     * @return
     * @throws ParseException
     */
    public String findInventorySnapshot() throws ParseException {
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
        Pagination<InventoryCommand> page =
                manager.findSnapShotPageByDate(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.stringToDate(startDate), FormatUtil.stringToDate(endDate), inventory, userDetails.getCurrentOu().getId(), companyid, tableConfig.getSorts());
        request.put(JSON, toJson(page));
        return JSON;
    }

    /**
     * 库存日志查询
     * 
     * @return
     * @throws ParseException
     */
    public String findStTxLogPageBySku() throws ParseException {
        setTableConfig();
        Pagination<StockTransTxLogCommand> page =
                manager.findStTxLogPageBySku(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.stringToDate(startDate), FormatUtil.stringToDate(endDate), skuId, owner, inventory, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(page));
        return JSON;
    }

    public InventoryCommand getInventory() {
        return inventory;
    }

    public void setInventory(InventoryCommand inventory) {
        this.inventory = inventory;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
