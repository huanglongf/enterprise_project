/**
 * 
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 库存共享管理
 * 
 * @author jumbo
 * 
 */
public class InventoryShareMaintainAction extends BaseJQGridProfileAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = -3814719696717454393L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private AuthorizationManager authorizationManager;

    private List<OperationUnit> operationUnitList;
    private List<Warehouse> warehouseList;
    private Warehouse warehouse;

    /**
	 * 
	 */
    private String flag;

    public String execute() {
        operationUnitList = authorizationManager.findOperationUnitList(this.userDetails.getCurrentOu().getId());
        if (operationUnitList != null && !operationUnitList.isEmpty()) {
            Map<String, List<Object>> tempMap = wareHouseManager.findOperationUnitOrWarehouseMap(operationUnitList);
            if (tempMap != null && !tempMap.isEmpty()) {
                if (operationUnitList.size() == tempMap.get("warehouseList").size()) {
                    // 仓库信息
                    flag = "1";
                    request.put("warehouseList", tempMap.get("warehouseList"));
                } else {
                    // 组织信息
                    flag = "0";
                    request.put("operationUnitList", tempMap.get("operationUnitList"));
                }
            }
        }
        return ERROR;
    }

    /**
     * 运营中心下所有的仓库组织对应的仓库附加信息都填写完整之后，输出本表格——仓库附加信息
     * 
     * @return
     */
    public String warehouseDetailList() {
        if (operationUnitList == null) operationUnitList = authorizationManager.findOperationUnitList(this.userDetails.getCurrentOu().getId());
        setTableConfig();
        Pagination<Warehouse> warehouseDetailList = wareHouseManager.findWarehouseDetailList(tableConfig.getStart(), tableConfig.getPageSize(), operationUnitList, tableConfig.getSorts());
        request.put(JSON, toJson(warehouseDetailList));
        return JSON;
    }


    /**
     * 运营中心下所有的仓库组织对应的仓库附加信息未填写完整，输出本表格——仓库组织信息
     * 
     * @return
     */
    public String findOuWarehouseList() {
        operationUnitList = authorizationManager.findOperationUnitList(this.userDetails.getCurrentOu().getId());
        if (operationUnitList != null && !operationUnitList.isEmpty()) {
            Map<String, List<Object>> tempMap = wareHouseManager.findOperationUnitOrWarehouseMap(operationUnitList);
            if (tempMap != null && !tempMap.isEmpty()) {
                setTableConfig();
                Pagination<OperationUnit> operationUnitList = authorizationManager.findOuWarehouseList(tableConfig.getStart(), tableConfig.getPageSize(), tempMap.get("operationUnitList"), tableConfig.getSorts());
                request.put(JSON, toJson(operationUnitList));
            }
        }
        return JSON;
    }

    /**
     * 添加库存共享
     * 
     * @return
     */
    public String addInventoryShare() {
        List<Long> ids = new ArrayList<Long>();
        for (Warehouse w : warehouseList) {
            ids.add(w.getId());
        }
        wareHouseManager.updateInventoryShare(ids, Integer.parseInt(Constants.STATUS_TRUE));
        this.asynReturnTrueValue();
        return JSON;
    }

    /**
     * 取消库存共享
     * 
     * @return
     */
    public String deleteInventoryShare() {
        List<Long> ids = new ArrayList<Long>();
        for (Warehouse w : warehouseList) {
            ids.add(w.getId());
        }
        wareHouseManager.updateInventoryShare(ids, Integer.parseInt(Constants.STATUS_FALSE));
        this.asynReturnTrueValue();
        return JSON;
    }


    // GETTER && SETTER
    public List<Warehouse> getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(List<Warehouse> warehouseList) {
        this.warehouseList = warehouseList;
    }

    public List<OperationUnit> getOperationUnitList() {
        return operationUnitList;
    }

    public void setOperationUnitList(List<OperationUnit> operationUnitList) {
        this.operationUnitList = operationUnitList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
