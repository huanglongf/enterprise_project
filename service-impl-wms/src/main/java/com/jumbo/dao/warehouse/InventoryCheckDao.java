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

package com.jumbo.dao.warehouse;

import java.math.BigDecimal;
import java.util.Date;
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

import com.jumbo.wms.model.pda.InboundOnShelvesDetailCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;

@Transactional
public interface InventoryCheckDao extends GenericEntityDao<InventoryCheck, Long> {

    @NativeQuery
    InventoryCheckCommand findInventoryCheck(@QueryParam("invcheckid") Long invcheckid, RowMapper<InventoryCheckCommand> rowMapper);

    @NativeQuery
    InventoryCheckCommand findICById(@QueryParam("invcheckid") Long invcheckid, RowMapper<InventoryCheckCommand> rowMapper);

    @NativeQuery
    InventoryCheckCommand findIMaxTransactionTime(@QueryParam("invcheckid") Long invcheckid, RowMapper<InventoryCheckCommand> rowMapper);

    /**
     * 查询状态为"新建"和"取消未处理"的盘点批信息
     * 
     * @param ouid
     * @param sorts
     * @return
     */
    @NamedQuery
    List<InventoryCheck> findInventoryCheckList(@QueryParam("ouid") Long ouid, Sort[] sorts);

    @NamedQuery
    List<InventoryCheck> findInventoryCheckListByType(@QueryParam("ouid") Long ouid, @QueryParam("type") InventoryCheckType type, Sort[] sorts);

    @NamedQuery
    List<InventoryCheck> findInventoryCheckListByTypes(@QueryParam("ouid") Long ouid, Sort[] sorts);

    @NamedQuery
    List<InventoryCheck> findInventoryCheckListManager(@QueryParam("ouid") Long ouid, @QueryParam("status") InventoryCheckStatus status, Sort[] sorts);

    @NamedQuery
    InventoryCheck findByCode(@QueryParam("code") String code);

    /**
     * 根据条件查询盘点批信息
     * 
     * @param start
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param intStatus
     * @param code
     * @param creatorName
     * @param confirmUser
     * @param ouid
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<InventoryCheckCommand> findAllinventoryCheckByPage(int start, int pageSize, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, @QueryParam("intStatus") Integer intStatus, @QueryParam("code") String code,
            @QueryParam("creatorName") String creatorName, @QueryParam("confirmUser") String confirmUser, @QueryParam("ouid") Long ouid, Sort[] sorts, BeanPropertyRowMapper<InventoryCheckCommand> beanPropertyRowMapper);


    @NativeQuery(pagable = true)
    Pagination<InventoryCheckCommand> findAllVMIinventoryCheckByPage(int start, int pageSize, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, @QueryParam("intStatus") Integer intStatus, @QueryParam("code") String code,
            @QueryParam("slipCode") String slipCode, @QueryParam("ouid") Long ouid,@QueryParam("owner") Long owner, Sort[] sorts, BeanPropertyRowMapper<InventoryCheckCommand> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<InventoryCheckCommand> findSkuGroupICheckListPage(int start, int pageSize, @QueryParam("startCreateTime") Date startCreateTime, @QueryParam("endCreateTime") Date endCreateTime, @QueryParam("startFinishTime") Date startFinishTime,
            @QueryParam("endFinishTime") Date endFinishTime, @QueryParam("intStatus") Integer intStatus, @QueryParam("code") String code, @QueryParam("owner") String owner, @QueryParam("icheckTypeList") List<Integer> icheckTypeList,
            @QueryParam("user") String user, @QueryParam("ouid") Long ouid, Sort[] sorts, BeanPropertyRowMapperExt<InventoryCheckCommand> beanPropertyRowMapperExt);


    @NativeQuery
    BigDecimal findSequence(SingleColumnRowMapper<BigDecimal> singleColumnRowMapper);

    @NamedQuery
    InventoryCheck findinvCheckBySlipCode(@QueryParam("slipCode") String slipCode);

    @NativeUpdate
    void operatorCheck(@QueryParam("code") String code, @QueryParam("status") Integer status, @QueryParam("operatorId") Long operatorid);

    // 仓库经理确认检验（是否已导入）
    @NativeQuery
    Long managerchecknumber(@QueryParam("code") String code, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Long occupationcheck(@QueryParam("code") String code, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NamedQuery
    InventoryCheck finInventoryCheckBySlipCodeandShop(@QueryParam("slipCode") String slipCode, @QueryParam("shopouId") Long shopouId);

    @NativeQuery
    List<InboundOnShelvesDetailCommand> getInventoryCheck(@QueryParam("code") String code, @QueryParam("uniqCode") String uniqCode, BeanPropertyRowMapper<InboundOnShelvesDetailCommand> beanPropertyRowMapper);

    /**
     * 根据盘点单号修改单据状态
     * 
     * @param code
     */
    @NativeUpdate
    void updateStatusByCode(@QueryParam("code") String code);

    /**
     * 根据盘点单号修改单据状态
     * 
     * @param code
     */
    @NativeUpdate
    void updateStatusByCodeAndStatus(@QueryParam("code") String code, @QueryParam("status") int status);

    /**
     * 根据前置单据查询库存盘点单据
     * 
     * @param code
     * @return
     */
    @NamedQuery
    InventoryCheck getBySlipCode(@QueryParam("slipCode") String code);


    @NamedQuery
    List<InventoryCheck> getInventoryCheckStatusList(@QueryParam("ouid") Long ouid);

    @NamedQuery
    List<InventoryCheck> getInventoryCheckStatus(@QueryParam("ouid") Long ouid);

    @NativeQuery
    List<InventoryCheck> getInventoryCheckByStatus(@QueryParam("status") Long status, @QueryParam(value = "sourceList") List<String> sourceList, BeanPropertyRowMapper<InventoryCheck> beanPropertyRowMapper);

    @NativeQuery
    List<InventoryCheckCommand> findInventoryCheckCommandListByType(@QueryParam("ouid") Long ouid, @QueryParam("type") Integer type, BeanPropertyRowMapper<InventoryCheckCommand> beanPropertyRowMapper);

}
