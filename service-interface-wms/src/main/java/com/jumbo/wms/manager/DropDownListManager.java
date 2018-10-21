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
package com.jumbo.wms.manager;

import java.util.List;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.authorization.UserGroup;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.InventoryStatus;

import loxia.dao.Sort;

/**
 * 构造下拉列表
 * 
 * @author wanghua
 * 
 */
public interface DropDownListManager extends BaseManager {
    /**
     * 查找系统中的可用组织类型列表
     * 
     * @param containsRoot 组织类型是否包括集团,包括true,不包括null/false
     * @return
     */
    List<OperationUnitType> findOperationUnitTypeList(Boolean containsRoot);



    Boolean nikeRFIDByCode(Long staId, String barCode, String nikeRFIDCode);



    /**
     * UserGroup下拉菜单
     */
    List<UserGroup> findUserGroupList();

    /**
     * 使用中=true,已禁用=false下拉列表
     * 
     * @return
     */
    List<ChooseOption> findStatusChooseOptionList();

    /**
     * PACKING(2), // 配货中 PARTLY_RETURNED(8), // 部分完成
     * 
     * @return
     */
    List<ChooseOption> findPickingListStatusForVerify();

    /**
     * 库存状态列表
     * 
     * @return
     */
    List<InventoryStatus> findInvStatusListByCompany(Long whId, Sort[] sorts);

    List<OperationUnit> findWarehouseOuListByComOuId(Long comOuId, Sort[] sorts);

    /**
     * sta状态
     * 
     * @return
     */
    List<ChooseOption> findStaStatusChooseOptionList();

    /**
     * sta上架模式
     * 
     * @return
     */
    List<ChooseOption> findStaInboundStoreModeChooseOptionList();

    /**
     * 配货清单状态
     * 
     * @return
     */
    public List<ChooseOption> findPickingListStatusOptionList();


    /**
     * 根据组织类型父节点查找子节点
     * 
     * @param parentId 父节点ID
     * @return
     */
    List<OperationUnitType> findChildOUPList(Long parentId);

    /**
     * 销售出库过仓状态
     * 
     * @return
     */
    public List<ChooseOption> findMsgOutboundOrderStatusOptionList();

    /**
     * 销售入库过仓状态
     * 
     * @return
     */
    public List<ChooseOption> findMsgInboundOrderStatusOptionList();
}
