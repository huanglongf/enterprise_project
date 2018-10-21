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

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.GroupBuyingSku;

/**
 * 团购商品表Dao
 * 
 * @author fanht
 * 
 */
public interface GroupBuyingSkuDao extends GenericEntityDao<GroupBuyingSku, Long> {

    /**
     * 确定该订单是否属于团购
     * 
     * @param whou
     * @param sku
     * @param type
     * @return
     */
    @NativeQuery
    Boolean isGroupBuyingSku(@QueryParam(value = "whou") Long whou, @QueryParam(value = "sku") Long sku, @QueryParam(value = "type") Integer type, RowMapper<Boolean> r);

    /**
     * 插入团购表
     * 
     * @param whou
     * @param sku
     * @param type
     * @return
     */
    @NativeUpdate
    void insertGroupBuyingSku(@QueryParam(value = "whou") Long whou, @QueryParam(value = "sku") Long sku, @QueryParam(value = "type") Integer type);

    /**
     * 根据作业单判断并删除团购商品 KJL
     * 
     * @param staIdList
     * @param warehouseId
     */
    @NativeUpdate
    @Deprecated
    void deleteGroupBuyingSkuByStaId(@QueryParam(value = "idList") List<Long> staIdList, @QueryParam(value = "ouId") Long warehouseId, @QueryParam(value = "singType") Integer singleType);

}
