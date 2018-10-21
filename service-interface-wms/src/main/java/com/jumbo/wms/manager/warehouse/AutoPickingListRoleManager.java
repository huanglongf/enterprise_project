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
 */
package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRole;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleDetailCommand;

/**
 * @author lichuan
 * 
 */
public interface AutoPickingListRoleManager extends BaseManager {
    /**
     * 分页查找仓库配货清单自动创建规则列表
     * 
     * @param role
     * @param status
     * @param start
     * @param pageSize
     * @param sorts
     * @return Pagination<AutoPickingListRoleCommand>
     * @throws
     */
    Pagination<AutoPickingListRoleCommand> findAutoPLRoleByPagination(AutoPickingListRole role, Integer status, int start, int pageSize, Sort[] sorts);

    /**
     * 根据编码查询规则是否存在
     * 
     * @param code
     * @return boolean
     * @throws
     */
    boolean findAutoPLRoleExistByCode(String code);

    /**
     * 保存规则
     * 
     * @param role
     * @param status void
     * @throws
     */
    void saveAutoPLRole(AutoPickingListRole role, Integer status);

    /**
     * 根据编码查询规则
     * 
     * @param code
     * @return AutoPickingListRole
     * @throws
     */
    AutoPickingListRole findAutoPLRoleByCode(String code);

    /**
     * 根据规则id查询所有关联规则明细
     * 
     * @param roleId
     * @param start
     * @param pageSize
     * @param sorts
     * @return Pagination<AutoPickingListRoleDetailCommand>
     * @throws
     */
    Pagination<AutoPickingListRoleDetailCommand> findAllPLRoleDetailByRoleId(Long roleId, int start, int pageSize, Sort[] sorts);

    /**
     * 保存规则及明细
     * 
     * @param role
     * @param status
     * @param roleDetailList void
     * @throws
     */
    void saveAutoPLRoleDetail(AutoPickingListRole role, Integer status, List<AutoPickingListRoleDetailCommand> roleDetailList);


    List<Transportator> findTransportatorList();

    List<Transportator> findTransportatorListAll();

    List<TransportatorCommand> findTransportatorListByAll(String lpCode, String lpName, Integer status, Integer isCod);


}
