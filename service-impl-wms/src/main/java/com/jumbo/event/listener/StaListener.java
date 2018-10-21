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

package com.jumbo.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.wms.manager.listener.StaListenerManager;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public class StaListener implements TransactionalEventListener<TransactionalEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StaListener.class);

    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaListenerManager staListenerManager;

    public void onEvent(TransactionalEvent event) {
        logger.debug(event.getSource() + "'s has been changed!");
        if (event.getSource() instanceof StockTransApplication) {
            StockTransApplication sta = (StockTransApplication) event.getSource();
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            logger.info("sta create onEvent start,  sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            switch (sta.getStatus()) {
                case CREATED:
                    // 新建
                    staListenerManager.staCreaded(sta, wh);
                    logger.info("sta create onEvent, sta created end,  sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                    break;
                case OCCUPIED:
                    // 库存占用
                    staListenerManager.staOccupied(sta, wh);
                    break;
                case CANCELED:
                    // 取消完成
                    staListenerManager.staCanceled(sta, wh);
                    break;
                case PARTLY_RETURNED:
                    // 部分转入
                    staListenerManager.staPartlyReturned(sta, wh);
                    break;
                case CANCEL_UNDO:
                    // 取消未处理
                    staListenerManager.staCancelUndo(sta, wh);
                    break;
                case CHECKED:
                    // 核对
                    staListenerManager.staChecked(sta, wh);
                    break;
                case INTRANSIT:
                    // 已转出
                    staListenerManager.staIntransit(sta, wh);
                    break;
                case FINISHED:
                    // 完成
                    staListenerManager.staFinished(sta, wh);
                    break;
                default:
                    break;
            }
        }
    }
}
