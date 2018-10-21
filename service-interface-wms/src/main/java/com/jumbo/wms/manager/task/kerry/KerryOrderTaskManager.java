package com.jumbo.wms.manager.task.kerry;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.KerryConfirmOrderQueue;

public interface KerryOrderTaskManager extends BaseManager {
    
    /**
     * 执行订单信息回传，出库通知Kerry
     */
    void exeKerryConfirmOrder(KerryConfirmOrderQueue q);
    
    /**
     * 执行订单取消,通知Kerry
     */
    void exeKerryCancelOrder(KerryConfirmOrderQueue q);
    
    /**
     * 保存要取消的订单
     * @param staId
     */
    void saveKerryCancelOrder(List<Long> staId);
}
