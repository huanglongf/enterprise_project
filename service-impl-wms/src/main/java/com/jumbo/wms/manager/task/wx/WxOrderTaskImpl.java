package com.jumbo.wms.manager.task.wx;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.daemon.WxOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.model.baseinfo.Transportator;

public class WxOrderTaskImpl extends BaseManagerImpl implements WxOrderTask {


    private static final long serialVersionUID = -3035096110478837361L;

    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransOlManager transOlManager;

    /**
     * 设置WX快递单号
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void setWxWarehouseTranckingNo() {
        Long count = transProvideNoDao.getTranNoNumberByLpCode(Transportator.WX, null, new SingleColumnRowMapper<Long>(Long.class));
        // 快递单号如果没有了 就不执行此代码
        if (count > 0) {
            List<Long> idList = warehouseDao.getAllWXWarehouse(new SingleColumnRowMapper<Long>(Long.class));
            for (Long id : idList) {
                try {
                    List<String> lpList = new ArrayList<String>();
                    lpList.add(Transportator.WX);
                    // lpList.add(Transportator.WX_COD);
                    List<Long> staList = staDao.findStaByOuIdAndStatus(id, lpList, new SingleColumnRowMapper<Long>(Long.class));
                    for (Long staId : staList) {
                        // WX下单8001
                        try {
                            // 设置WX单据号
                            transOlManager.matchingTransNo(staId);
                        } catch (Exception e) {
                            log.error("", e);
                        }
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }



}
