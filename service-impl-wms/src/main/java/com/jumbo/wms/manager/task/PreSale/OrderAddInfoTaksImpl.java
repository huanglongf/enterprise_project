package com.jumbo.wms.manager.task.PreSale;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.OrderRecieverInfoDao;
import com.jumbo.wms.daemon.OrderAddInfoTaks;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.trans.TransSuggestManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.RecieverInfo;

@Service("orderAddInfoTaks")
public class OrderAddInfoTaksImpl extends BaseManagerImpl implements OrderAddInfoTaks {

    /**
     * 
     */
    private static final long serialVersionUID = 1032213277866936046L;


    @Autowired
    private OrderRecieverInfoDao recieverInfoDao;
    @Autowired
    TransSuggestManager transSuggestManager;
    @Autowired
    TransOlManager transOlManager;
    @Autowired
    WareHouseManager wareHouseManager;

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void trasnInfo() {
        List<RecieverInfo> recieverInfos = recieverInfoDao.findByTransInfo(new BeanPropertyRowMapperExt<RecieverInfo>(RecieverInfo.class));
        for (RecieverInfo recieverInfo : recieverInfos) {
            transSuggestManager.suggestTransServicePreSale(recieverInfo.getId());
        }

    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void matchingTransNo() {
        List<RecieverInfo> recieverInfos = recieverInfoDao.findByTrackingNumber(new BeanPropertyRowMapperExt<RecieverInfo>(RecieverInfo.class));
        for (RecieverInfo recieverInfo : recieverInfos) {
            try {
                wareHouseManager.updateDeliveryInfo(recieverInfo.getId());
            } catch (Exception e) {

            }

        }
    }

}
