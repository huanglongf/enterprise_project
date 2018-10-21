package com.jumbo.wms.manager.task.inventoryTransactionToOms;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.WmsInvChangeToOms;

/**
 * WMS所有库存变更通知PAC接口
 * 
 * @author jinlong.ke
 * 
 */
public interface WmsInvChangeToOmsManager extends BaseManager {
    /**
     * 查询所有需要反馈的数据
     * 
     * @return
     */
    List<WmsInvChangeToOms> getAllNeedData();

    /**
     * 通知PACS相关库存变更
     * 
     * @param id
     */
    void noticeOmsThisInvChange(Long id);

}
