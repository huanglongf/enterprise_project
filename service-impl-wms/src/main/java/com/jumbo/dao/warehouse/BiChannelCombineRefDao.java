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

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.BiChannelCombineRef;
import com.jumbo.wms.model.warehouse.BiChannelCombineRefCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Transactional
public interface BiChannelCombineRefDao extends GenericEntityDao<BiChannelCombineRef, Long> {

    /**
     * 查询合并发货渠道信息
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannelCombineRefCommand> findBiChannelCombineRef(int start, int pagesize, RowMapper<BiChannelCombineRefCommand> r, Sort[] sorts,@QueryParam(value = "ouId") Long ouId);
    
    /**
     * 查询合并发货渠道信息
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery
    List<BiChannelCombineRefCommand> findBiChannelCombineChildrenRef(@QueryParam(value = "id") Long id,@QueryParam(value = "whId") Long whId,RowMapper<BiChannelCombineRefCommand> r, Sort[] sorts,@QueryParam(value = "ouId") Long ouId);

    @NamedQuery
    BiChannelCombineRef getByWhIdAndChId (@QueryParam("whId") Long whId,@QueryParam("chId") Long chId);
    
    /**
     * 查询为过期的合并发货渠道信息
     */
    @NativeQuery
    List<BiChannelCombineRefCommand> findBiChannelCombineRefToExpTime(@QueryParam("whId") Long whId,RowMapper<BiChannelCombineRefCommand> r);

    /**
     * 查询所有能合并的STAID信息
     */
    @NativeQuery
    List<BiChannelCombineRefCommand> findMergerStaId(@QueryParam("whId") Long whId,@QueryParam(value = "channelCode") List<String> channelCode, RowMapper<BiChannelCombineRefCommand> r);

    @NativeQuery
    List<StockTransApplication> findOldStaList(@QueryParam(value = "staId") List<Long> staId, RowMapper<StockTransApplication> r);

    /**
     * 商品体积单边最长值（用于区分大小件）
     */
    @NativeQuery
    BigDecimal getSkuMaxLength(@QueryParam(value = "staId") List<Long> staId, SingleColumnRowMapper<BigDecimal> singleColumnRowMapper);

    /**
     * 删除主渠道合并信息
     * 
     * @param staid
     */
    @NativeUpdate
    void deleteBiChannelCombineRefByChanId(@QueryParam(value = "chanId") Long chanId);
    
    /**
     * 根据ID修改合并信息
     * 
     * @param staid
     */
    @NativeUpdate
    void updateBiChannelCombineRefByChanId(@QueryParam(value = "whId") Long whId,@QueryParam(value = "expTime") Date expTime,@QueryParam(value = "id") Long id);
    
    /**
     * 根据ID修改合并信息
     * 
     * @param staid
     */
    @NativeUpdate
    void updateBiChannelRefByChanId(@QueryParam(value = "whId") Long whId,@QueryParam(value = "expTime") Date expTime,@QueryParam(value = "chId") Long chId);
    
    /**
     * 根据主渠道查询所有渠道合并信息
     * @param chanId
     * @return
     */
    @NativeQuery
    List<BiChannelCombineRefCommand> getBiChannelCombineRefByChanId(@QueryParam(value = "chanId") Long chanId,RowMapper<BiChannelCombineRefCommand> r);
    
}
