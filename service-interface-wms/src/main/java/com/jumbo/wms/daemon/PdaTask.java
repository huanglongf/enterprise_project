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

package com.jumbo.wms.daemon;

import java.util.List;

import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author laodai
 * 
 */

public interface PdaTask {

    public void pdaOrderTask();

    /**
     * PDA定时任务入库执行
     */
    public void taskPdaInbound();

    public void pdaReturnTask();

    /**
     * PDA库内移动
     */
    public void pdaInnerMove();

    public void exeInnerMove(PdaOrder po);

    /**
     * PDA入库
     */
    public void pdaInbound(List<PdaOrder> pdaList);

    /**
     * 执行单据
     * 
     * @param sta
     * @param pdaOrder
     * @return
     */
    public void execute(StockTransApplication sta, PdaOrder pdaOrder);

    /**
     * PDA主动移库入库数据
     */
    public void executeInitiativeMoveInbound();

    /**
     * 定时任务执行PDA退仓单数据入口方法
     */
    public void returnOrderExecute();

    /**
     * 定时任务删除PDA收货缓存数据
     */
    public void deletePDAReceiveMongoDb();

    /**
     * 按单执行PDA退仓单 创箱 执行出库
     * 
     * @param po
     */

    /**
     * 定时任务执行PDA盘点数据入口方法
     */
    public void inventoryCheckOrderExecute();

}
