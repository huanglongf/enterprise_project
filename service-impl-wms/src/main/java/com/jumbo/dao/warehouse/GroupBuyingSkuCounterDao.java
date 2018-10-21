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

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.GroupBuyingSkuCounter;

/**
 * 团购计数器表Dao
 * 
 * @author fanht
 * 
 */
public interface GroupBuyingSkuCounterDao extends GenericEntityDao<GroupBuyingSkuCounter, Long> {

    /**
     * 插入团购计数器表
     * 
     * @param whou
     * @param sku
     * @param singelOrderQty
     * @param singelGoodsQty
     */
    @NativeUpdate
    void insertGroupBuyingSkuCounter(@QueryParam(value = "whou") Long whou, @QueryParam(value = "sku") Long sku, @QueryParam(value = "singelOrderQty") Integer singelOrderQty, @QueryParam(value = "singelGoodsQty") Integer singelGoodsQty,
            @QueryParam(value = "staId") Long staId);


    /**
     * 检查是否达到团购下线
     * 
     * @param whou
     * @param sku
     */
    @NativeQuery
    GroupBuyingSkuCounter checkToGroupBuying(@QueryParam(value = "whou") Long whou, @QueryParam(value = "sku") Long sku, RowMapper<GroupBuyingSkuCounter> r);

    /**
     * 更新STA状态为团购单
     * 
     * @param whou
     * @param sku
     * @param singelOrderQty
     * @param singelGoodsQty
     */
    @NativeUpdate
    void updateSta(@QueryParam(value = "whou") Long whou, @QueryParam(value = "sku") Long sku, @QueryParam(value = "singelOrderQty") Integer singelOrderQty, @QueryParam(value = "singelGoodsQty") Integer singelGoodsQty);


    /**
     * 删除团购计数器记录
     * 
     * @param whou
     * @param sku
     * @param singelOrderQty
     * @param singelGoodsQty
     */
    @NativeUpdate
    void deleteGrpCnt(@QueryParam(value = "whou") Long whou, @QueryParam(value = "sku") Long sku, @QueryParam(value = "singelOrderQty") Integer singelOrderQty, @QueryParam(value = "singelGoodsQty") Integer singelGoodsQty);

    /**
     * 重复插入计数器表判断
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    List<GroupBuyingSkuCounter> findByStaId(@QueryParam(value = "staId") Long staId);


    /**
     * 根据作业单编号删除团购计数器信息
     * 
     * @param staIdList
     */
    @NativeUpdate
    @Deprecated
    void deleteCounterByStaId(@QueryParam(value = "idList") List<Long> staIdList);

}
