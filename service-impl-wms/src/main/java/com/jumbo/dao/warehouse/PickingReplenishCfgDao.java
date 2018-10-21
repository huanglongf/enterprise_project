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
import java.util.List;
import java.util.Map;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PickingReplenishCfg;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;

@Transactional
public interface PickingReplenishCfgDao extends GenericEntityDao<PickingReplenishCfg, Long> {

    @NativeQuery
    List<SkuReplenishmentCommand> findReplenishSummary(@QueryParam(value = "ouId") Long ouId, @QueryParam Map<String, Object> skuInfoMap, RowMapper<SkuReplenishmentCommand> rowMapper);

    @NativeQuery
    List<SkuReplenishmentCommand> findReplenishSummaryForPickingFailed(@QueryParam(value = "ouid") Long ouid, @QueryParam Map<String, Object> skuInfoMap, RowMapper<SkuReplenishmentCommand> rowMapper);

    @NativeQuery
    List<SkuReplenishmentCommand> findReplenishInv(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "skuId") Long skuId, @QueryParam Map<String, Object> skuInfoMap, RowMapper<SkuReplenishmentCommand> rowMapper);

    @NativeQuery
    List<SkuReplenishmentCommand> findReplenishInvDetaile(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "skuId") Long skuId, @QueryParam Map<String, Object> skuInfoMap, RowMapper<SkuReplenishmentCommand> rowMapper);

    /*
     * @NativeQuery PickingReplenishCfg
     * findBySkuBarCodeAndDistrictAndOu(@QueryParam(value="skuBarCode") String skuCode,
     * 
     * @QueryParam(value="districtCode") String districtCode,
     * 
     * @QueryParam(value="ouid") Long ouid, RowMapper<PickingReplenishCfg> rowMapper);
     * 
     * @NativeQuery PickingReplenishCfg findBySkuCodeAndDistrictAndOu(@QueryParam(value="skuCode")
     * String skuCode, @QueryParam(value="districtCode") String districtCode,
     * 
     * @QueryParam(value="ouid") Long ouid, RowMapper<PickingReplenishCfg> rowMapper);
     */
    @NativeQuery
    PickingReplenishCfg findBySkuDistrictAndOu(@QueryParam(value = "skuBarCode") String skuBarCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "districtCode") String districtCode, @QueryParam(value = "ouid") Long ouid,
            @QueryParam("shopOwner") String shopOwner, RowMapper<PickingReplenishCfg> rowMapper);

    @NativeUpdate
    void updateBySkuAndDistrict(@QueryParam(value = "skuid") Long skuid, @QueryParam(value = "districtid") Long districtid, @QueryParam(value = "quantity") Long quantity, @QueryParam(value = "warningPre") BigDecimal warningPre,
            @QueryParam(value = "ouid") Long ouid, @QueryParam("shopOwner") String shopOwner);

    /**
     * 根据仓库和商品查询拣货区补货配置
     * 
     * @param ouId
     * @param skuId
     * @param innerShopCode 
     * @return
     */
    @DynamicQuery
    PickingReplenishCfg findCfgBySkuAndOu(@QueryParam("ouId")Long ouId, @QueryParam("skuId")Long skuId, @QueryParam("shop")String innerShopCode);

}
