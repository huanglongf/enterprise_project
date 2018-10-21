package com.jumbo.wms.manager.task.sforder;

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
import com.jumbo.wms.manager.expressDelivery.logistics.TransAliWaybill;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOnLineFactory;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Service("sfOrderTaskManager")
public class SfOrderTaskManagerImpl extends BaseManagerImpl implements SfOrderTaskManager {

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
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private TransAliWaybill transAliWaybill;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sfOrder(String strWhId) {
        log.debug("sfOrder=============WH:" + strWhId);
        Long whid = Long.valueOf(strWhId);
        sfIntefaceByWarehouse(whid);
    }

    /**
     * 查询需要获取运单号作业单
     * 
     * @param whId
     * @param lpList
     * @return
     */
    public List<Long> findGetTransNoSta(Long whId, List<String> lpList) {
        return staDao.findStaByOuIdAndStatus(whId, lpList, new SingleColumnRowMapper<Long>(Long.class));
    }

    public void matchingTransNo(String lpcode, Long whid, Long staid) {
        // SF下单
        try {
            // 设置顺丰单据号
            TransOlInterface trans = transOnLineFactory.getTransOnLine(lpcode, whid);
            if (trans != null) {
                // StaDeliveryInfo sd = null;
                /*
                 * ChooseOption option =
                 * chooseOptionManager.findChooseOptionByCategoryCodeAndKey(Constants.ALI_WAYBILL,
                 * lpcode); if (option != null &&
                 * Constants.ALI_WAYBILL_SWITCH_ON.equals(option.getOptionValue())) { sd =
                 * transAliWaybill.getTransNoByStaId(staid); if (sd == null ||
                 * StringUtil.isEmpty(sd.getTrackingNo())) { sd = null; } } if (sd == null) {
                 */
                    trans.matchingTransNo(staid);
                // }

            } else {
                // 不是电子运单, 修改Next_Get_Trans_Time
                wareHouseManagerExecute.updateNextGetTransTime(staid);
            }
        } catch (BusinessException e) {
            // 记录获取运单失败的单据
            wareHouseManagerExecute.failedToGetTransno(staid);
            log.error(lpcode + " IntefaceByWarehouse error code is : " + e.getErrorCode());
        } catch (Exception e) {
            wareHouseManagerExecute.failedToGetTransno(staid);
            log.error(lpcode + ":" + staid + " matchingTransNo :" + e);
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sfIntefaceByWarehouse(Long whId) {
        log.debug("==============" + whId + "==================");
        Warehouse wh = warehouseDao.getWarehouseByOuId(whId, new BeanPropertyRowMapper<Warehouse>(Warehouse.class));
        if (wh == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }
        Boolean flag = true;
        while (flag) {
            List<String> lpList = new ArrayList<String>();
            lpList.add("SF");
            lpList.add("SFCOD");
            lpList.add("SFDSTH");
            List<Long> staList = staDao.findStaByOuIdAndStatus(whId, lpList, new SingleColumnRowMapper<Long>(Long.class));
            if (staList.size() < 100) {
                flag = false;
            }
            for (Long id : staList) {
                // SF下单
                try {
                    // 设置顺丰单据号
                    TransOlInterface trans = transOnLineFactory.getTransOnLine(Transportator.SF, whId);
                    trans.matchingTransNo(id);
                } catch (BusinessException e) {
                    // 记录获取运单失败的单据
                    wareHouseManagerExecute.failedToGetTransno(id);
                    log.error("sfIntefaceByWarehouse error code is : " + e.getErrorCode());
                } catch (Exception e) {
                    wareHouseManagerExecute.failedToGetTransno(id);
                    log.error("", e);
                }
            }
        }
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sfInterfaceAllWarehouse() {
        List<Long> idList = warehouseDao.getAllSfWarehouse(new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : idList) {
            try {
                sfIntefaceByWarehouse(id);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

}
