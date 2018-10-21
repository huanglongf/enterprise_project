/**
 * \ * Copyright (c) 2013 Jumbomart All Rights Reserved.
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

import java.util.List;
import java.util.Map;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;
import com.jumbo.wms.model.warehouse.StaErrorLine;
import com.jumbo.wms.model.warehouse.StaErrorLineCommand;

/**
 * 配货失败操作的表
 * 
 * @author fanht
 * 
 */
public interface StaErrorLineDao extends GenericEntityDao<StaErrorLine, Long> {

    /**
     * 删除明细配货失败信息
     * 
     * @param staId
     * @param skuId
     */
    @NativeUpdate
    void deleteByStaErrorLine(@QueryParam("staId") Long staId, @QueryParam("skuId") Long skuId);

    /**
     * 查询配货失败作业单的数据
     * 
     * @param ouid
     * @param skuInfoMap
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<SkuReplenishmentCommand> findReplenishSummaryForPickingFailed(@QueryParam(value = "ouId") Long ouId, @QueryParam Map<String, Object> skuInfoMap, @QueryParam(value = "terms") Long terms, RowMapper<SkuReplenishmentCommand> rowMapper);

    /**
     * 根据 配货失败作业单 ID查找库存数量
     * 
     * @param ouId
     * @param skuId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    SkuReplenishmentCommand findstockBySkuId(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "locationCode") String locationCode, RowMapper<SkuReplenishmentCommand> rowMapper);

    /**
     * 根据 配货失败作业单 ID查找库存数量
     * 
     * @param ouId
     * @param skuId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    SkuReplenishmentCommand findCodeBySkuId(@QueryParam(value = "skuId") Long skuId, RowMapper<SkuReplenishmentCommand> rowMapper);

    /**
     * 导出配货失败 缺货的sku信息
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(model = StaErrorLineCommand.class)
    List<StaErrorLineCommand> findStaFailure(@QueryParam(value = "ouid") Long ouid);

    /**
     * 删除明细配货失败信息staid
     * 
     * @param staId
     * @param skuId
     */
    @NativeUpdate
    void deleteByStaErrorLineByStaId(@QueryParam("staId") Long staId);
    
    /**
     * 查询配货失败作业单的数据
     * 
     * @param ouid
     * @param staCode
     * @return
     */
    @NativeQuery
    List<SkuReplenishmentCommand> findReplenishSummarySuggest(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "staCode") String staCode, RowMapper<SkuReplenishmentCommand> rowMapper);


}
