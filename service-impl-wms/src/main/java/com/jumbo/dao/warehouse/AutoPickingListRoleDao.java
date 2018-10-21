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
package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.AutoPickingListRole;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleDetailCommand;

/**
 * 仓库配货清单自动创建规则
 * 
 * @author lichuan
 * 
 */
@Transactional
public interface AutoPickingListRoleDao extends GenericEntityDao<AutoPickingListRole, Long> {
    /**
     * 分页查找仓库配货清单自动创建规则列表
     * 
     * @param start
     * @param size
     * @param code
     * @param name
     * @param status
     * @param sorts
     * @param tag
     * @return Pagination<AutoPickingListRoleCommand>
     * @throws
     */
    @NativeQuery(pagable = true)
    Pagination<AutoPickingListRoleCommand> findAutoPLRoleByPagination(int start, int size, @QueryParam(value = "code") String code, @QueryParam(value = "name") String name, @QueryParam(value = "status") List<Integer> status, Sort[] sorts,
            BeanPropertyRowMapper<AutoPickingListRoleCommand> role);

    /**
     * 根据编码查询配货清单自动创建规则数量
     * 
     * @param code
     * @param r
     * @return Long
     * @throws
     */
    @NativeQuery
    Long findAutoPLRoleCountByCode(@QueryParam("code") String code, SingleColumnRowMapper<Long> r);

    /**
     * 根据编码查询规则
     * 
     * @param code
     * @return AutoPickingListRole
     * @throws
     */
    @NamedQuery
    AutoPickingListRole findAutoPLRoleByCode(@QueryParam("code") String code);

    /**
     * 根据规则id查询所有关联规则明细
     * 
     * @param start
     * @param size
     * @param roleId
     * @param sorts
     * @param roleDetail
     * @return Pagination<AutoPickingListRoleDetailCommand>
     * @throws
     */
    @NativeQuery(pagable = true)
    Pagination<AutoPickingListRoleDetailCommand> findAllPLRoleDetailByRoleId(int start, int size, @QueryParam(value = "roleId") Long roleId, Sort[] sorts, BeanPropertyRowMapper<AutoPickingListRoleDetailCommand> roleDetail);

    /**
     * @param isTransAfter
     * @param isSn 插入规则明细
     * 
     * @param roleId
     * @param skuSizeId
     * @param skuCategoryId
     * @param pickingType
     * @param sort
     * @param maxOrder
     * @param minOrder void
     * @throws
     */
    @NativeUpdate
    void insertPLRoleDetail(@QueryParam(value = "roleId") Long roleId, @QueryParam(value = "skuSizeId") Long skuSizeId, @QueryParam(value = "skuCategoryId") Long skuCategoryId, @QueryParam(value = "pickingType") Integer pickingType,
            @QueryParam(value = "sort") Integer sort, @QueryParam(value = "maxOrder") Integer maxOrder, @QueryParam(value = "minOrder") Integer minOrder, @QueryParam("sendCity") String sendCity, @QueryParam("isSn") Integer isSn,
            @QueryParam("isTransAfter") Integer isTransAfter);

    /**
     * 删除规则明细
     * 
     * @param roleId void
     * @throws
     */
    @NativeUpdate
    void deletePLRoleDetail(@QueryParam(value = "roleId") Long roleId);

    @NativeQuery
    List<AutoPickingListRoleCommand> findAutoPickingListRoleList(BeanPropertyRowMapperExt<AutoPickingListRoleCommand> r);

    @NativeQuery
    List<AutoPickingListRoleDetailCommand> getAutoPickingListRoleDetial(@QueryParam(value = "roleId") Long roleId, RowMapper<AutoPickingListRoleDetailCommand> r);

    @NativeUpdate
    void insertRoleTran(@QueryParam(value = "roleId") Long roleId, @QueryParam(value = "tranId") Long tranId);

    /**
     * 删除规则明细
     */
    @NativeUpdate
    void deletePLRoleDetailById(@QueryParam(value = "id") Long id);

    /**
     *删除规则明细对应物流商
     */
    @NativeUpdate
    void deletePLRoleDetailTranById(@QueryParam(value = "id") Long id);
}
