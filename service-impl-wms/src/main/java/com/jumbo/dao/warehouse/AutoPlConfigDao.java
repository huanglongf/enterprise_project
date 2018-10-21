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
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.AutoPlConfig;
import com.jumbo.wms.model.warehouse.AutoPlConfigCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

@Transactional
public interface AutoPlConfigDao extends GenericEntityDao<AutoPlConfig, Long> {

    @NamedQuery
    AutoPlConfig getAotoPlConfig(@QueryParam(value = "whOuId") Long whOuId);

    @NativeQuery
    AutoPlConfigCommand getAotoPlConfigCommand(@QueryParam(value = "ouid") Long ouid, RowMapper<AutoPlConfigCommand> r);

    
    @NativeQuery
    StockTransApplicationCommand getAllStaAutoLpCode(@QueryParam(value = "owner") List<String> owner, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "pickType") Integer pickType, @QueryParam(value = "skuCid") Long skuCid,
            RowMapper<StockTransApplicationCommand> r);


    @NativeQuery
    List<StockTransApplicationCommand> getAllAutoStaId(@QueryParam(value = "owner") List<String> owner, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "pickType") Integer pickType, @QueryParam(value = "skuCid") Long skuCid,
            @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "skuSize") Long skuSize, RowMapper<StockTransApplicationCommand> r);

    @NativeQuery
    List<StockTransApplicationCommand> getAllAutoStaIdByCount(@QueryParam("chanelList") List<String> channelCode, @QueryParam("isSn") Integer isSn, @QueryParam("ouId") Long hwouid, @QueryParam("pickingType") Integer type,
            @QueryParam("skuCategoryId") Long skuCategoryId, @QueryParam("lpList") List<String> lpList, @QueryParam("sizeList") List<SkuSizeConfig> sizeList, @QueryParam("sendCity") String sendCity, @QueryParam("staCount") String staCount,
            BeanPropertyRowMapper<StockTransApplicationCommand> beanPropertyRowMapper);
}
