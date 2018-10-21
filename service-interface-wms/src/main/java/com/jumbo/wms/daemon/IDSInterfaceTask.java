package com.jumbo.wms.daemon;

public interface IDSInterfaceTask {

    void sendSalesOrderToLFTask();



    /**
     * NBAUA 出库订单 同步到立峰
     */
    void sendNBAUASalesOrderToLFTask();

    /**
     * AF 出库订单 同步到立峰
     */
    void sendAFSalesOrderToLFTask();

    /**
     * newLook 出库订单 同步到立峰
     */
    void sendNewLookSalesOrderToLFTask();

    /**
     * godiva出库订单 同步到立峰(HAVI外包仓)
     */
    void sendGodivaHaviSalesOrderToLFTask();



    /**
     * NBJ01 出库订单 同步到立峰
     */
    void sendNBJ01SalesOrderToLFTask();

    void deleteSoLogByTime();

    /**
     * NBJ01出库单反馈
     */
    void exeNBJ01SalesOrderToLFTask();

    /**
     * AEO 出库订单 同步到立峰
     */
    void sendAEOSalesOrderToLFTask();

    /**
     * NIKE 出库订单 同步到立峰
     */
    void sendNIKESalesOrderToLFTask();

    /**
     * newLook 发送退换货入库同步到立峰
     */
    void sendNewLookReturnOrderToLFTask();

    /**
     * Godiva 发送退换货入库同步到立峰(Havi外包仓)
     */
    void sendGodivaHaviReturnOrderToLFTask();

    /**
     * AF 出库订单 反馈执行
     */
    void exeAFSalesOrderToLFTask();

    /**
     * AEO 出库订单 反馈执行
     */
    void exeAEOSalesOrderToLFTask();

    /**
     * NIKE 出库订单反馈执行
     */
    void exeNIKESalesOrderToLFTask();

    /**
     * NIKE 出库订单反馈执行TM
     */
    void exeNIKESalesOrderToLFTaskTm();

    /**
     * AF 发送入库订单
     */
    void sendAFReturnOrderToLFTask();

    /**
     * AEO 发送入库订单
     */
    void sendAEOReturnOrderToLFTask();

    /**
     * NBAUA 发送入库订单
     */
    void sendNBAUAReturnOrderToLFTask();

    /**
     * NIKE 发送入库订单
     */
    void sendNikeOrderToLFTask();

    void sendIdsVsToLFTask();

    /**
     * IDS 发送退换货入库同步到立峰
     */
    void sendIDSReturnOrderToLFTask();

    void idsNikeFeedbackTask();


    /**
     * 立峰 发送取消订单
     */
    void sendCaneleOrderToLFTask();

    void executeAFIdsInventorySyn();

    void executeAEOIdsInventorySyn();





    void executeLevisIdsInventorySyn();

    void executeUAIdsInventorySyn();

    void executeIdsVsInventorySyn();

    /**
     * newlook 库存同步
     */
    void executeNewLookIdsInventorySyn();

    /**
     * Godiva 库存同步(Havi外包仓)
     */
    void executeGodivaHaviIdsInventorySyn();

    /**
     * 执行失败重复执行
     */
    void inventoryCheckAEO();

    /**
     * 执行失败重复执行
     */
    void inventoryCheckAF();

    /**
     * UA 发送入库订单
     */
    void sendUAReturnOrderToLFTask();

    /**
     * UA 出库订单 同步到立峰
     */
    void sendUASalesOrderToLFTask();

    /**
     * UA 出库订单 反馈执行
     */
    void exeUASalesOrderToLFTask();

    /**
     * guess 任务补发
     */
    void inventoryCheckGUESS();

    /**
     * IDS-SH-UA 任务补发
     */
    void inventoryCheckUA();

    /**
     * newLook 任务补发
     */
    void inventoryCheckNewLook();

    /**
     * NBA UA 库存调整定时任务
     */
    void executeNBAUAIdsInventorySyn();

    /**
     * godiva-havi出库反馈执行
     */
    void exeGodivaHaviSalesOrderToLFTask();

    /**
     * newLookJD出库订单 同步到立峰
     */
    void sendNewLookJDSalesOrderToLFTask();

    /**
     * newLookJD发送入库订单
     */
    void sendNewLookJDReturnOrderToLFTask();

    /**
     * newLookJD取消订单
     */
    void sendNewLookJDCaneleOrderToLFTask();

    /**
     * newLookJD库存同步
     */
    void executeNewLookJDIdsInventorySyn();

    /**
     * AEO 出库订单 同步到AEORMS
     */
    void sendAEOSalesOrderToRMSTask();

    /**
     * NewLookJD 任务补发
     */
    void inventoryCheckNewLookJD();


    void inventoryCheckVS();

    /**
     * GodivaHavi 任务补发
     */
    void inventoryCheckGodivaHavi();

    void inventoryStatusChangeToOms();
}
