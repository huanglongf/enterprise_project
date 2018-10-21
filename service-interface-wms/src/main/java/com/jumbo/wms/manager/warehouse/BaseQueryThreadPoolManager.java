package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.InventoryCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;

public interface BaseQueryThreadPoolManager extends BaseManager {

    /**
     * 库存数量查询
     * 
     * @param start
     * @param pageSize
     * @param inv
     * @param whOuId
     * @param companyid
     * @param sorts
     * @return
     */
    public Pagination<InventoryCommand> findCurrentInventoryByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts) throws Exception;
    
    public Pagination<InventoryCommand> findCurrentInventoryZeroByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts) throws Exception;

    public List<InventoryCommand> findCurrentInventory(List<InventoryCommand> list) throws Exception;
}
