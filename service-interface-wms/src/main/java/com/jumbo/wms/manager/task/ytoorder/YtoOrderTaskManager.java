package com.jumbo.wms.manager.task.ytoorder;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;

/**
 * 
 * @author sj
 * 
 */
public interface YtoOrderTaskManager extends BaseManager {

    void ytoInterfaceAllWarehouse();

    void ytoIntefaceByWarehouse(Long whId);

    void ytoOrder(String strWhId);

    List<Long> getAllYtoWarehouse();

}
