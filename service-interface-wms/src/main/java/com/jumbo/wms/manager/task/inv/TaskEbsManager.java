package com.jumbo.wms.manager.task.inv;



public interface TaskEbsManager {
    /**
     * EBS库存同步
     */
    public void ebsInventory();

    /**
     * 每日可销售库存同步
     */
    public void salesInventory();
    /**
     * 实时增量库存同步
     */
    public void salesInventoryOms(String batchID);
    /**
     * 全量库存补充文件
     */
    void replenishForSalesInventory();
    
    /**
     * 增量错误邮件通知
     */
    void salesInventoryOmsEmail();

    /**
     * 将结果集备份至日志表，删除结果集
     */
    void copyDataToLog();
    
    /**
     * 将结果集备份到PAC全量库存日志表，删除结果集
     */
    void copyInvDataToPACLog();

    /**
     * 未升单邮件通知
     */
    void upgradeEmail();
}
