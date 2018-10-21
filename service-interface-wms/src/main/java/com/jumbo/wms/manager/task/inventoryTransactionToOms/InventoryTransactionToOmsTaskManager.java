package com.jumbo.wms.manager.task.inventoryTransactionToOms;

import java.util.List;

import com.jumbo.wms.model.warehouse.InventoryTransactionToOms;

/**
 * 库存事务同步PACS逻辑
 * 
 * @author jinlong.ke
 * 
 */
public interface InventoryTransactionToOmsTaskManager {
    /**
     * 查询需要同步PACS的库存事务数据
     * 
     * @return
     */
    List<InventoryTransactionToOms> getNeedExcuteData();

    /**
     * 同步PACS库存事务数据，并记录相关信息
     * 
     * @param id
     * @param stvId
     */
    void noticePacsData(Long id, Long stvId);

    /**
     * 查询需要邮件通知的库存事务数据
     * 
     * @return
     */
    List<InventoryTransactionToOms> getNeedSendData();

    void sendEmailForITTO(List<InventoryTransactionToOms> ilist);

}
