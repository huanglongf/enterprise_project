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

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.rmi.warehouse.WarehousePriority;
import com.jumbo.wms.model.command.WarehouseCoverageAreaRefCommand;
import com.jumbo.wms.model.warehouse.WarehouseCoverageAreaRef;

/**
 * @author lichuan
 * 
 */
@Transactional
public interface WarehouseCoverageAreaRefDao extends GenericEntityDao<WarehouseCoverageAreaRef, Long> {

    @NativeQuery(pagable = true)
    Pagination<WarehouseCoverageAreaRefCommand> findCoverageAreaByOuId(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapper<WarehouseCoverageAreaRefCommand> BeanPropertyRowMapper);

    /**
     * 根据ouId删除关联覆盖区域
     * 
     * @param ouId void
     * @throws
     */
    @NativeUpdate
    void deleteCoverageAreaByOuId(@QueryParam(value = "ouId") Long ouId);

    /**
     * 根据 渠道和配送范围查询配送仓库编码
     * 
     * @param owner 渠道
     * @param province
     * @param city
     * @param r
     * @return
     */
    @NativeQuery
    List<WarehousePriority> findWarehouseByCoverageArea(@QueryParam("province") String province, @QueryParam("city") String city, @QueryParam("cusId") Long cusId, BeanPropertyRowMapper<WarehousePriority> beanPropertyRowMapper);

    /**
     * 根据渠道ID和配送范围查询配送仓库编码
     * 
     * @param province
     * @param city
     * @param cusId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WarehousePriority> findWarehouseByCoverageAreaChannleId(@QueryParam("province") String province, @QueryParam("city") String city, @QueryParam("cusId") Long cusId, @QueryParam("channelId") Long channelId,
            BeanPropertyRowMapper<WarehousePriority> beanPropertyRowMapper);
}
