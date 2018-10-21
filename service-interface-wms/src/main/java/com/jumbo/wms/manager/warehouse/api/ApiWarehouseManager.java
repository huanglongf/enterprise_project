package com.jumbo.wms.manager.warehouse.api;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.cond.WarehouseQueryCond;

/**
 * 仓库对外接口
 * 
 * @author ShengGe
 * 
 */
public interface ApiWarehouseManager extends BaseManager {

    /**
     * 根据组织节点编码查询仓库
     * @param channelCode 店铺编码
     * @param ouCode 组织节点编码
     * @return
     */
    WarehouseQueryCond findWhByOuCode(String channelCode, String ouCode);
    
}
