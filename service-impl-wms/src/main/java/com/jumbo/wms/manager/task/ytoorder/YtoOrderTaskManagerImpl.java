package com.jumbo.wms.manager.task.ytoorder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOnLineFactory;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;

/**
 * 
 * @author sj
 * 
 */
@Service("ytoOrderTaskManager")
public class YtoOrderTaskManagerImpl extends BaseManagerImpl implements YtoOrderTaskManager {

    /**
     * 
     */
    private static final long serialVersionUID = -1779257941840801046L;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransOnLineFactory transOnLineFactory;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void ytoInterfaceAllWarehouse() {
        List<Long> idList = warehouseDao.getAllYtoWarehouse(new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : idList) {
            try {
                ytoIntefaceByWarehouse(id);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void ytoOrder(String strWhId) {
        log.debug("ytoOrder=============WH:" + strWhId);
        Long whid = Long.valueOf(strWhId);
        ytoIntefaceByWarehouse(whid);
    }

    public void ytoIntefaceByWarehouse(Long whId) {
        log.debug("==============" + whId + "==================");
        Warehouse wh = warehouseDao.getYtoWarehouseByOuId(whId, new BeanPropertyRowMapper<Warehouse>(Warehouse.class));
        if (wh == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }
        Boolean flag = true;
        while (flag) {
            List<String> lpList = new ArrayList<String>();
            lpList.add("YTO");
            lpList.add("YTOCOD");
            List<Long> staList = staDao.findStaByOuIdAndStatus(whId, lpList, new SingleColumnRowMapper<Long>(Long.class));
            if (staList.size() < 100) {
                flag = false;
            }
            for (Long id : staList) {
                // Yto下单
                try {
                    // 设置顺丰单据号
                    TransOlInterface trans = transOnLineFactory.getTransOnLine(Transportator.YTO, whId);
                    trans.matchingTransNo(id);
                } catch (BusinessException e) {
                    // 记录获取运单号失败的单据
                    wareHouseManagerExecute.failedToGetTransno(id);
                    log.error("ytoIntefaceByWarehouse error code is : " + e.getErrorCode());
                } catch (Exception e) {
                    // 记录获取运单号失败的单据
                    wareHouseManagerExecute.failedToGetTransno(id);
                    log.error("", e);
                }
            }
        }
    }

    @Override
    public List<Long> getAllYtoWarehouse() {
        return warehouseDao.getAllYtoWarehouse(new SingleColumnRowMapper<Long>(Long.class));
    }
}
