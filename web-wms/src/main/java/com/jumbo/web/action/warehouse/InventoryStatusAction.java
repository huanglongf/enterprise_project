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

import java.util.Date;
import java.util.List;

import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author wanghua
 * 
 */
public class InventoryStatusAction extends BaseJQGridProfileAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1232454134351323729L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private DropDownListManager dropDownListManager;
    /**
     * 系统内置库存状态
     */
    private List<InventoryStatus> sysList;
    /**
     * 非系统内置库存状态
     */
    private List<InventoryStatus> notSysList;
    /**
     * 状态下拉列表
     */
    private List<ChooseOption> statusOptionList;
    /**
     * 创建/修改的model
     */
    private InventoryStatus inventoryStatus;

    public String execute() throws Exception {
        statusOptionList = dropDownListManager.findStatusChooseOptionList();
        return SUCCESS;
    }

    /**
     * 系统内置库存状态,该功能是在公司角色下,所以当前的ou=公司
     * 
     * @return
     */
    public String inventorySysList() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findInventoryStatusList(true, null, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 系统非内置库存状态、自定义库存状态,该功能是在公司角色下,所以当前的ou=公司
     * 
     * @return
     */
    public String inventoryNotSysList() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findInventoryStatusList(false, userDetails.getCurrentOu(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 创建
     * 
     * @return
     */
    public String create() {
        if (inventoryStatus != null) {
            inventoryStatus.setOu(userDetails.getCurrentOu());
            inventoryStatus.setLastModifyTime(new Date());
            wareHouseManager.createInventoryStatus(inventoryStatus);
        }
        return JSON;
    }

    /**
     * 修改
     * 
     * @return
     */
    public String update() {
        if (inventoryStatus != null) {
            wareHouseManager.updaetInventoryStatus(inventoryStatus);
        }
        return JSON;
    }

    public String checkForDisableInvstatus() throws Exception {
        JSONObject result = new JSONObject();
        result.put("enabled", wareHouseManager.findInventoryCountByStatusId(inventoryStatus.getId()) == 0);
        request.put(JSON, result);
        return JSON;
    }

    public List<InventoryStatus> getSysList() {
        return sysList;
    }

    public void setSysList(List<InventoryStatus> sysList) {
        this.sysList = sysList;
    }

    public List<InventoryStatus> getNotSysList() {
        return notSysList;
    }

    public void setNotSysList(List<InventoryStatus> notSysList) {
        this.notSysList = notSysList;
    }

    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public List<ChooseOption> getStatusOptionList() {
        return statusOptionList;
    }

    public void setStatusOptionList(List<ChooseOption> statusOptionList) {
        this.statusOptionList = statusOptionList;
    }


}
