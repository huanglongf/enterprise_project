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

package com.jumbo.wms.manager.trans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.manager.BaseManagerImpl;

/**
 * @author lingyun.dai
 * 
 */
@Service("transSuggestManagerProxy")
public class TransSuggestManagerProxyImpl extends BaseManagerImpl implements TransSuggestManagerProxy {

    /**
     * 
     */
    private static final long serialVersionUID = -3691586133582019287L;

    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransSuggestManager transSuggestManager;

    /**
     * 基于仓库作业单物流推荐,By单据处理
     * 
     * @param whOuId
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void suggestTransService(Long whOuId) {
        List<Long> staIds = staDao.findTrasnNullStaByWH(whOuId, new SingleColumnRowMapper<Long>(Long.class));
        log.debug("debug ***********************************----------suggestTransService warehouseID:" + whOuId + " staSize:" + staIds.size());
        for (Long id : staIds) {
            try {
                transSuggestManager.suggestTransService(id);
            } catch (Exception e) {
                log.error("STA suggest trans error!  staID:" + id);
                log.error("", e);
            }
        }
    }

    /**
     * 定时任务物流推荐方法，By仓库处理
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void suggestTransService() {
        List<Long> whIds = warehouseDao.getTransRefWarehouse(new SingleColumnRowMapper<Long>(Long.class));
        log.debug("debug Task----***********************************----------suggestTransService WarehouseSize:" + whIds.size());
        for (Long id : whIds) {
            suggestTransService(id);
        }
    }
}
