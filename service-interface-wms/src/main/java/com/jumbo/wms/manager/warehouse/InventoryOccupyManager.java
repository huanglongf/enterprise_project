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

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StvLine;

/**
 * @author lichuan
 * 
 */
public interface InventoryOccupyManager extends BaseManager {
    /**
     * 库存锁定：创库存锁定作业单，占用库存并生产增量库存
     * 
     * @param slipCode
     * @param memo
     * @param userId
     * @param ouid
     * @param list
     * @return StockTransApplication
     * @throws
     */
    StockTransApplication inventoryLock(String slipCode, Integer lockType, String memo, List<StvLine> list, Long userId, Long ouid);

    /**
     * 查询库存锁定作业单列表
     * 
     * @param start
     * @param pageSize
     * @param staCom
     * @param ouId
     * @param isExpIn
     * @param sort
     * @return Pagination<StockTransApplicationCommand>
     * @throws
     */
    Pagination<StockTransApplicationCommand> findInventoryLockStaByPage(int start, int pageSize, StockTransApplicationCommand staCmd, Integer lockType, Long ouId, Sort[] sort);

    /**
     * 根据staId查询作业单占用明细
     * 
     * @param staId
     * @return List<StaLineCommand>
     * @throws
     */
    List<StaLineCommand> findOccupiedStaLineByStaId(Long staId);

    /**
     * 库存解锁
     * 
     * @param staCmd
     * @param userId void
     * @throws
     */
    void inventoryUnlock(StockTransApplicationCommand staCmd, Long userId);

    /**
     * 通用库存占用逻辑
     * 
     * @param sta
     * @param transactionCode void
     * @throws
     */
    void inventoryOccupyCommon(StockTransApplication sta, String transactionCode, Boolean isStv);
    
    /**
     * 通用库存占用逻辑
     *@param sta
     *@param transactionCode
     *@param stvId void 
     *@throws
     */
    void inventoryOccupyCommon(StockTransApplication sta, String transactionCode, Long stvId);
    
    /**
     * 作业单库存占用逻辑(非销售单)
     * 
     * @param staId 作业单Id
     * @param userId 操作人
     * @param transactionCode 事务类型编码
     * @throws
     */
    void inventoryOccupyBySta(Long staId, Long userId, String transactionCode);
    
    /**
     * 通用作业单库存占用逻辑
     * @param staId 作业单Id
     * @param userId 操作人
     * @param transactionCode 事务类型编码
     * @param isStv 是否有执行单明细
     * @throws
     */
    void inventoryOccupyBySta(Long staId, Long userId, String transactionCode, Boolean isStv);
    
    /**
     * 通用作业单库存占用逻辑
     *@param staId
     *@param userId
     *@param transactionCode
     *@param stvId
     *@param isStv void 
     *@throws
     */
    void inventoryOccupyBySta(Long staId, Long userId, String transactionCode,Long stvId, Boolean isStv);
    
    /**
     * 通用作业单库存占用逻辑
     *@param staId
     *@param userId
     *@param transactionCode
     *@param stvId void 
     *@throws
     */
    void inventoryOccupyBySta(Long staId, Long userId, String transactionCode, Long stvId);

    /**
     * 通用作业单库存占用逻辑
     * 
     * @param staId 作业单Id
     * @param userId 操作人
     * @param transactionCode 事务类型编码
     * @param isSale 是否销售单
     * @throws
     */
    void inventoryOccupyBySta(Long staId, Long userId, String transactionCode, Boolean isSale, Boolean isStv);

}
