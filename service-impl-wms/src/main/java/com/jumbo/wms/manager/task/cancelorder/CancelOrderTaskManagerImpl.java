package com.jumbo.wms.manager.task.cancelorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("cancelOrderTaskManager")
public class CancelOrderTaskManagerImpl extends BaseManagerImpl implements CancelOrderTaskManager {

    /**
     * 
     */
    private static final long serialVersionUID = -7108515109046046984L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private InventoryDao invDao;

    @Override
    public void cancelOrderById(Long id) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        if (!sta.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
            log.error("CancelOrderById Exception:status error.Sta Id:" + id);
            throw new BusinessException("");
        } else {
            invDao.releaseInventoryByOpcOcde(sta.getCode());
            sta.setStatus(StockTransApplicationStatus.CANCELED);
        }
    }
}
