package com.jumbo.wms.manager.listener;



import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface StvListenerManager extends BaseManager {
    /**
     * STV完成状态监听处理方法
     * 
     * @param stv
     */
    void stvFinished(StockTransVoucher stv);

    /**
     * 库存事务同步OMS公用方法
     * 
     * @param stv
     */
    void inventoryTransactionToOms(StockTransVoucher stv, Boolean b);

}
