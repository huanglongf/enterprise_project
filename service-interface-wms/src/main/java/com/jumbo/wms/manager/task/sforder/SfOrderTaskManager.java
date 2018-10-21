package com.jumbo.wms.manager.task.sforder;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
/**
 * 
 * @author jinlong.ke
 *
 */
public interface SfOrderTaskManager extends BaseManager{

    void sfInterfaceAllWarehouse();

    void sfIntefaceByWarehouse(Long whId);
    
    void sfOrder(String strWhId);

    List<Long> findGetTransNoSta(Long whId,List<String> lpList);
    
    void matchingTransNo(String lpcode,Long whid,Long staid);
}
