package com.jumbo.wms.task;


/**
 * 定时任务接口
 * 
 * @author jingkai
 * 
 */
public interface WmsTaskInterface {


    /**
     * 按仓库占用库存
     * 
     * @author jingkai
     * @param whCode
     */
    void newOcpInvByWhId(String whCode);

    /**
     * 非MQ获取运单号
     * 
     * @author jingkai
     */
    void exeSetAllTransNo();

    /**
     * MQ获取运单号
     * 
     * @author jingkai
     */
    void exeSetAllTransNoByMq();

    /**
     * 外包仓解锁
     */
    void msgUnLocked();

    /**
     * 区域计算库存
     * 
     * @author jingkai
     */
    void queryStaToOcpAeraInv(String ouId);
    
    /**
     * 区域计算库存ALL
     * 
     * @author lzb
     */
    void queryStaToOcpAeraInvAll();

    /**
     * 直连MQ开关关闭定时任务创单
     * 
     * @author jingkai
     */
    void createStaAfterSetFlag();

    /**
     * 非直连MQ开关关闭定时任务创单
     * 
     * @author jingkai
     */
    void createStaAfterSetFlagPac();

    /**
     * 直连不校验库存MQ创单
     * 
     * @author jingkai
     */
    void createStaNotCheckInvToMQ();
    
    /**
     * 非直连不校验库存MQ创单
     * 
     * @author jingkai
     */
    void createStaNotCheckInvToMQByPac();

    /**
     * 直连&非直连过仓重推MQ
     */
    void sendMqByPacAndToms();

    /**
     * 设置是否可以创单标识位
     */
    void setFlagForOrderToCreate();

    /**
     * 设置是否可以创单标识位 1、根据仓库查询出固定条数，设置开始标识，计算默认发货仓库; 2、按照默认发货仓库进行分线程设置标识位，标识为true该单结束；false汇总执行
     */
    void setFlagForOrderToCreatePac();

    /**
     * pac直连转toms创单
     */
    void wmsConfirmOrderServiceDaemon();

    /**
     * 新建状态占用 未执行丢MQ
     */
    void wmsZhanYongDaemon();

    /**
     * 根据source 执行出库消息反馈
     */
    void executeMsgRtnOutbound(String source);

    /**
     * EMS 出库通知
     */
    void exeEmsConfirmOrderQueue();

    /**
     * EMS出库通知多线程
     */
    void exeEmsConfirmOrderQueueSeo();

    /**
     * 自动化补偿定时任务
     */
    void msgToMcsTask();

    /**
     * SF库存占用(新)
     */
    void newExeSfConfirmOrderQueue();

    /**
     * 直连出库通知OMS (和退货入库)(非adidas pacs)
     */
    void outboundNoticeOms();

    /**
     * 出库通知PAC
     */
    void outboundNoticePac();

    /**
     * AEO 出库订单 同步到AEORMS
     */
    void sendAEOSalesOrderToRMSTask();

    /**
     * 根据source出库订单同步
     */
    void sendSalesOrderToLFTask(String source);

    // ////////////////////////新增01

    /**
     * pac直连转toms出库
     */
    void outboudNoticePacSystenKeyDaemon();

    /**
     * 通用补偿 执行失败或丢mq 失败 补偿
     */
    void wmsCommonMessageProducerErrorMq();



    /**
     * 外包仓出库反馈 反馈执行失败或丢mq 失败 补偿
     */
    void wmsRtnOutBountMq();


    /**
     * 直连订单创单反馈 (非adidas)
     */
    void wmsOrderFinishOms();


    /**
     * 非直连创建反馈补偿
     */
    void orderCreateMsgToPac();

    /**
     * CNP 出库通知
     */
    void exeCnpConfirmOrderQueue();
    /**
     * 顺丰出库反馈
     */
    void exeSfConfirmOrderQueue();

    /**
     * 库内移动exl执行
     */
    void exeExlFileTask();

    /**
     * 删除执行成功的文件。 删除执行失败一个星期前的文件。 定时任务每两小时执行一次
     */
    void deleteExlFileTask();


    /**
     * 播种反馈执行
     */
    void msgToMwsTask();

    /**
     * 库存变更通知PACS/原库存共享逻辑
     */
    void inventoryChangeToOms();

    /* 同步PACS库存事务数据，并记录相关信息 */
    void inventoryTransationToOmsTask();

    /* 发送邮件及保存WMS出入库事务通知PACS */
    void sendEmailForITTO();
    
    
    /**
     * 异常定时任务 邮件通知
     */
    void errorTaskMailNotify();

    // ///////////////service 切换S

    /* NIKE库存 */
    void insertNikeInventory();

    /**
     * 根据中间表插入转店数据
     */
    void insertNikeStockReceive();

    /* NIKE 库存反馈 */
    void idsNikeFeedbackTask();

    /**
     * NIKE 发送入库订单
     */
    void sendNikeOrderToLFTask();

    /**
     * 推送出库信息
     */
    void sendNikeCartonNo();

    void startConverseTasks();

    /**
     * 1、下载FTP文件到本地<br/>
     * 2、 解析本地文件保存到数据库并备份<br/>
     * 3、创单
     */
    void downloadFtpDataAndCreateStaForConverseYx();

    /**
     * Converse永兴根据反馈中间表写数据到文件并上传FTP
     */
    void uploadConverseYxData();

    /* 同步JD运单号 */
    void jdReceiveOrder();

    /* 获取JD运单号 */
    void toHubGetJdTransNo();

    /**
     * 解锁
     */
    void deblocking();

    /**
     * 邮件通知解锁失败的订单
     */
    void errorEmail();

    /**
     * 创建退换货单据
     */
    void createRtnOrder();

    /**
     * 每30分钟汇总一次增量库存错误批次，发送邮件通知OMS
     */
    void salesInventoryOmsEmail();

    /**
     * 每30分钟汇总一次过仓失败的单数，发送邮件通知OMS
     */
    void emailToOmsForIncrementInvFailure();

    /**
     * 全量库存补充文件
     */
    void replenishForSalesInventory();

    /**
     * EBS库存同步
     */
    void ebsInventory();

    /**
     * 实时增量库存同步
     */
    void salesInventoryOms();

    /* NIKE 根据中间表创建入库单据 */

    void downloadNikeData();

    /* 通过收货指令创建STA */

    void createStaForVmiDefault();

    /* 每天早上10点邮件通知异常数据 */
    void vmiErrorEmailInform();

    /*
     * 上传NIKE入库反馈文件
     */
    void uploadNikeData();

    /**
     * 定时任务邮件发送
     */
    void mailIds();

    /**
     * NIKE库存广州
     */
    void insertNikeInventoryGZ();

    /**
     * NIKE库存
     */
    void insertNikeInventoryTM();

    /**
     * NIKE库存广州
     */
    void insertNikeInventoryGZTM();

    /* check是否存在重复订单并邮件通知 */
    void checkStaRepetitive();


    /**
     * 每30分钟汇总一次过仓失败的单数，发送邮件通知OMS
     */
    void emailToOmsForFailure();

    /**
     * 全量库存同步定时任务 每天凌晨1点开始计算
     */
    void totalInv();

    /**
     * 邮件通知定时任务-- 每小时一次 汇总错误次数超过10次仍旧没有成功的单据且没有进行邮件通知
     */
    void emailNotice();

    /**
     * 邮件通知定时任务-- 每小时一次 所有超过6小时的通知OMS创建SKU而OMS仍未创建的邮件通知（同一商品不重复发送邮件通知）
     */
    public void emailNoticeOms();

    /**
     * OMS外包仓销售出库定时任务入口
     */
    void vmiOMSOutbound();

    /* 创建作业单队列 HK */

    void createSta();

    /**
     * 缺失体积或重量的商品是否已经维护好了
     */
    void verifyThreeDimensionalOver();
    // ///////////////service 切换E

    /**
     * 同步纸箱数据给LMIS
     */
    void sendStaCartonInfoToLMIS();
    
    
    /**
     * MQ定时任务补偿
     */
    /**
     * 消费者补偿
     * 
     */
    public void consumerMsgDataCompensate(String topics);

    /* *//**
          * 同步纸箱数据给LMIS
          *//*
             * void sendStaCartonInfoToLMIS();
             */
}
