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

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.OccupationInfoCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * @author yushanshan
 * 
 */
public class ShopInventoryQueryAction extends BaseJQGridProfileAction {

    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private BaseinfoManager baseinfoManager;

    private static final long serialVersionUID = -6557588123114629891L;
    private InventoryCommand inventoryCommand;
    private OccupationInfoCommand occupationInfoCommand;
//    private List<CompanyShopCommand> companyShopList;
    private List<InventoryStatus> inventoryStatusList;

    public String findShopInventoryDetial() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManagerQuery.findInventoryBySku(inventoryCommand.getSkuId(), inventoryCommand.getInvOwner(), null, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 库存明细查寻
     * 
     * @return
     */
    public String inventoryDetailsQueryEntry() {
        return SUCCESS;
    }

    public String inventoryDetailsQuery() {
        Long companyid = baseinfoManager.findCompanyByWarehouse(userDetails.getCurrentOu().getId()).getId();
        setTableConfig();
        request.put(JSON, toJson(wareHouseManagerQuery.findDetailsInventoryByPage(tableConfig.getStart(), tableConfig.getPageSize(), inventoryCommand, userDetails.getCurrentOu().getId(), companyid, tableConfig.getSorts())));
        return JSON;
    }


    // GETTER && SETTER
    public InventoryCommand getInventoryCommand() {
        return inventoryCommand;
    }

    public void setInventoryCommand(InventoryCommand inventoryCommand) {
        this.inventoryCommand = inventoryCommand;
    }

    public List<InventoryStatus> getInventoryStatusList() {
        return inventoryStatusList;
    }

    public void setInventoryStatusList(List<InventoryStatus> inventoryStatusList) {
        this.inventoryStatusList = inventoryStatusList;
    }

    public OccupationInfoCommand getOccupationInfoCommand() {
        return occupationInfoCommand;
    }

    public void setOccupationInfoCommand(OccupationInfoCommand occupationInfoCommand) {
        this.occupationInfoCommand = occupationInfoCommand;
    }

//    public List<CompanyShopCommand> getCompanyShopList() {
//        return companyShopList;
//    }
//
//    public void setCompanyShopList(List<CompanyShopCommand> companyShopList) {
//        this.companyShopList = companyShopList;
//    }
}
