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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;

/**
 * @author lichuan
 * 
 */
public interface PickingListManager extends BaseManager {

    /**
     * 获取配货清单的核对模式
     * 
     * @param pickId
     * @return Long
     * @throws
     */
    Long findPickingListByPickId(Long pickId);

    /**
     * 配货清单id，是否特定
     * 
     * @param pickId
     * @return Long
     * @throws
     */
    Long findPickingListByPickIdS(Long pickId);

    /**
     * 区域ID下拉框
     * 
     * @param pickingId
     * @param ouId
     * @return
     */
    List<Zoon> findPickingDistrictByPickingId(Long pickingId, Long ouId);

    /**
     * 区域ID下拉框
     * 
     * @param pickingId
     * @param ouId
     * @return
     */
    List<Zoon> findPickingDistrictByPickingListId(List<Long> pickingIdList, Long ouId);

    /**
     * 
     * 方法说明：通过配货批ID查找ZOON ID
     * 
     * @author LuYingMing
     * @date 2016年9月6日 下午9:18:08
     * @param pickingIdList
     * @param ouId
     * @return
     */
    List<Long> findZoonIds(Long pickingIdList,Long ouId);

    /**
     * 获取打印次数
     * 
     * @param plId
     * @return BigDecimal
     * @throws
     */
    BigDecimal findOutputCount(Long plId);

    /**
     * 根据主键获取配货清单
     * 
     * @param plId
     * @return PickingListCommand
     * @throws
     */
    public PickingListCommand getByPrimaryKey(Long plId);

    List<PickingListCommand> findDetailInfoById(Long plId);

    List<SkuReplenishmentCommand> findReplenishSummary(Long ouId, Map<String, Object> skuInfoMap);

    List<SkuReplenishmentCommand> findReplenishSummaryForPickingFailed(Long ouid, Map<String, Object> skuInfoMap);

    List<SkuReplenishmentCommand> findReplenishInv(Long ouId, Long skuId, Map<String, Object> skuInfoMap);

    List<SkuReplenishmentCommand> findReplenishInvDetaile(Long ouId, Long skuId, Map<String, Object> skuInfoMap);

    List<PickingListCommand> findPickingListByPickingId(Long pickingListId, Integer pickZoneId, Long ouid);
    
    PickingListCommand findPickListByPickCode(String pickingCode,Long ouId);

}
