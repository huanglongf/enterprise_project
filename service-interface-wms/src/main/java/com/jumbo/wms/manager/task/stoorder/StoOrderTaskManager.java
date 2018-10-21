package com.jumbo.wms.manager.task.stoorder;


import com.jumbo.wms.manager.BaseManager;
/**
 * 
 * @author jinlong.ke
 *
 */
public interface StoOrderTaskManager extends BaseManager{

    void stoInterfaceAllWarehouse();

    void stoIntefaceByWarehouse(Long whId);
    
    void stoOrder(String strWhId);
    
    public Long getStoTranNoNum();
    /**
     *根据物流商获取随机一条数据
     */
    public Long getTranNoByLpcodeList();
}
