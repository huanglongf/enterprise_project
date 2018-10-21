package com.jumbo.wms.task.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.task.BocDaemonTask;
import com.jumbo.task.CreateStaTask;
import com.jumbo.task.DaemonTask;
import com.jumbo.task.IDSInterfaceDaemonTask;
import com.jumbo.task.InventoryTransactionToOmsTask;
import com.jumbo.task.MsgUnLockedTask;
import com.jumbo.task.Order2WmsTask;
import com.jumbo.task.SetTransNoTask;
import com.jumbo.task.StaCartonInfoTask;
import com.jumbo.task.TaskCreateStaPac;
import com.jumbo.task.ThreadPoolService;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.*;
import com.jumbo.wms.manager.task.SendMqByPacAndTomsTask;
import com.jumbo.wms.manager.task.NikeCartonNo.NikeCartonNoTask;
import com.jumbo.wms.manager.task.SendMqByPacAndTomsTask;
import com.jumbo.wms.manager.task.NikeCartonNo.NikeCartonNoTask;
import com.jumbo.wms.manager.warehouse.AutoOutboundTurnboxManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.newInventoryOccupy.AreaOcpStaAllThread;
import com.jumbo.wms.newInventoryOccupy.InventoryOccupyThreadPool;
import com.jumbo.wms.newInventoryOccupy.StaInventoryOcpThreadPool;
import com.jumbo.wms.task.WmsTaskInterface;

/**
 * 定时任务实现
 * 
 * @author jingkai
 * 
 */
public class WmsTaskImpl implements WmsTaskInterface {

    protected static final Logger log = LoggerFactory.getLogger(WmsTaskImpl.class);
    public static final long TASK_LOCK_TIMEOUT = Constants.TASK_LOCK_TIMEOUT;
    public static final String TASK_LOCK_VALUE = Constants.TASK_LOCK_VALUE;

    @Autowired
    private InventoryOccupyThreadPool inventoryOccupyThreadPool;
    @Autowired
    private SetTransNoTask setTransNoTask;
    @Autowired
    private MsgUnLockedTask msgUnLockedTask;
    @Autowired
    private StaInventoryOcpThreadPool staInventoryOcpThreadPool;
    @Autowired
    private CreateStaTask createStaTask;
    @Autowired
    private TaskCreateStaPac taskCreateStaPac;
    @Autowired
    private SendMqByPacAndTomsTask sendMqByPacAndTomsTask;
    @Autowired
    private Order2WmsTask order2WmsTask;
    @Autowired
    private BocDaemonTask bocDaemonTask;
    @Autowired
    private IDSInterfaceTask idsInterfaceTask;
    @Autowired
    private IDSInterfaceDaemonTask iDSInterfaceDaemonTask;
    @Autowired
    private InventoryTransactionToOmsTask inventoryTransactionToOmsTask;
    @Autowired
    private DaemonTask daemonTask;
    @Autowired
    private TaskItochuIDS taskItochuIDS;
    @Autowired
    private NikeCartonNoTask nikeCartonNoTask;
    @Autowired
    private NikeTask nikeTask;
    @Autowired
    private IDSInterfaceTask iDSInterfaceTask;
    @Autowired
    private ConverseTask converseTask;
    @Autowired
    private ConverseYxTask converseYxTask;
    @Autowired
    private JdOrderTask jdOrderTask;
    @Autowired
    private ArriveTimeTask arriveTimeTask;
    @Autowired
    private RtnOrderTask rtnOrderTask;
    @Autowired
    private InvTask invTask;
    @Autowired
    private VmiDefaultTask vmiDefaultTask;
    @Autowired
    private IdsTask idsTask;
    @Autowired
    private CheckStaRepetitiveTask checkStaRepetitiveTask;
    @Autowired
    private TaskCreateSta taskCreateSta;
    @Autowired
    private TotalInvTask totalInvTask;
    @Autowired
    private TotalRealtimeInvTask totalRealtimeInvTask;
    @Autowired
    private TaskOms taskOms;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ThreadPoolService threadPoolService;
    @Autowired
    private AutoOutboundTurnboxManager autoOutboundTurnboxManager;

    @Autowired
    private StaCartonInfoTask staCartonInfoTask;
  
    @Autowired
    private MqTask mqTask;
    

    /*
     * @Autowired private StaCartonInfoTask staCartonInfoTask;
     */
    /**
     * 按仓库占用库存
     * 
     * @author jingkai
     * @param whCode
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void newOcpInvByWhId(String whCode) {
        //7579:adidas 7580:吴江永鼎NIKE仓
         if("7579".equals(whCode) || "7580".equals(whCode)){
             log.error("newOcpInvByWhId start,whcode:{}", whCode);
         }
        log.info("newOcpInvByWhId start,whcode:{}", whCode);
        try {
            inventoryOccupyThreadPool.newOcpInvByWhId(whCode);
        } catch (Exception e) {
            log.error("newOcpInvByWhId_2,whcode:"+whCode,e);
        }
        log.info("newOcpInvByWhId end,whcode:{}", whCode);
        if("7579".equals(whCode) || "7580".equals(whCode)){
            log.error("newOcpInvByWhId end,whcode:{}", whCode);
        }
    }

    /**
     * 非MQ获取运单号
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeSetAllTransNo() {
        log.info("exeSetAllTransNo start");
        setTransNoTask.exeSetAllTransNo();
        log.info("exeSetAllTransNo end");
    }

    /**
     * MQ获取运单号
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeSetAllTransNoByMq() {
        log.info("exeSetAllTransNoByMq start");
        setTransNoTask.exeSetAllTransNoByMq();
        log.info("exeSetAllTransNoByMq end");
    }

    /**
     * 外包仓解锁
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void msgUnLocked() {
        log.info("out vmi warehouse un lock order start");
        msgUnLockedTask.msgUnLocked();
        log.info("out vmi warehouse un lock order end");
    }

    /**
     * 区域计算库存
     * 
     * @author jingkai
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void queryStaToOcpAeraInv(String ouId) {
        //7579:adidas 7580:吴江永鼎NIKE仓
        if("7579".equals(ouId) || "7580".equals(ouId)){
            log.error("queryStaToOcpAeraInv start,ouId:{}", ouId);
        }
        log.info("warehouse area ocp inv start,ouid : {}", ouId);
        try {
            staInventoryOcpThreadPool.queryStaToOcpAeraInv(Long.parseLong(ouId));
        } catch (Exception e) {
            log.error("queryStaToOcpAeraInv_2,ouId:"+ouId, e);
        }
        log.info("warehouse area ocp inv end,ouid : {}", ouId);
        if("7579".equals(ouId) || "7580".equals(ouId)){
            log.error("queryStaToOcpAeraInv end,ouId:{}", ouId);
        }
    }

    
    /**
     * 区域计算库存All
     * 
     * @author lzb
     */
    //@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void queryStaToOcpAeraInvAll() {
        log.error("queryStaToOcpAeraInvAll start");
        List <Warehouse> wh = wareHouseManager.getIsAreaOcpInvAll();
        log.error("queryStaToOcpAeraInvAll size:"+wh.size());
        for (Warehouse warehouse : wh) {
            Thread t = new AreaOcpStaAllThread(warehouse.getOu().getId());
            threadPoolService.executeThread("queryStaToOcpAeraInvAll_ALL", t);
        }
   //     threadPoolService.waitToFinish("queryStaToOcpAeraInvAll_ALL");
        log.error("queryStaToOcpAeraInvAll end");
    }
    
    /**
     * 直连MQ开关关闭定时任务创单
     * 
     * @author jingkai
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createStaAfterSetFlag() {
        log.info("create sta for oms start");
        createStaTask.createStaAfterSetFlag();
        log.info("create sta for oms end");
    }

    /**
     * 非直连MQ开关关闭定时任务创单
     * 
     * @author jingkai
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createStaAfterSetFlagPac() {
        log.info("create sta for pac start");
        taskCreateStaPac.createStaAfterSetFlagPac();
        log.info("create sta for pac end");
    }

    /**
     * 直连不校验库存MQ创单
     * 
     * @author jingkai
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createStaNotCheckInvToMQ() {
        log.info("create sta for oms,no check inv start");
        createStaTask.createStaNotCheckInvToMQ();
        log.info("create sta for oms,no check inv end");
    }


    /**
     * 非直连不校验库存MQ创单
     * 
     * @author jingkai
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createStaNotCheckInvToMQByPac() {
        log.info("create sta for pacs,no check inv start");
        createStaTask.createStaNotCheckInvToMQByPac();
        log.info("create sta for pacs,no check inv end");

    }

    /**
     * 直连&非直连过仓重推MQ
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendMqByPacAndToms() {
        log.info(" sendMqByPacAndToms start");
        sendMqByPacAndTomsTask.sendMqByPacAndToms();
        log.info("sendMqByPacAndToms end");
    }

    /**
     * 设置是否可以创单标识位
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void setFlagForOrderToCreate() {
        log.info("setFlagForOrderToCreate start");
        createStaTask.setFlagForOrderToCreate();
        log.info("setFlagForOrderToCreate end");
    }

    /**
     * 设置是否可以创单标识位 1、根据仓库查询出固定条数，设置开始标识，计算默认发货仓库; 2、按照默认发货仓库进行分线程设置标识位，标识为true该单结束；false汇总执行
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void setFlagForOrderToCreatePac() {
        log.info("setFlagForOrderToCreatePac start");
        taskCreateStaPac.setFlagForOrderToCreatePac();
        log.info("setFlagForOrderToCreatePac end");
    }

    /**
     * pac直连转toms创单
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void wmsConfirmOrderServiceDaemon() {
        log.info("wmsConfirmOrderServiceDaemon start");
        order2WmsTask.wmsConfirmOrderServiceDaemon();
        log.info("wmsConfirmOrderServiceDaemon end");
    }

    /**
     * 新建状态占用 未执行丢MQ
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void wmsZhanYongDaemon() {
        log.info("wmsZhanYongDaemon start");
        order2WmsTask.wmsZhanYongDaemon();
        log.info("wmsZhanYongDaemon end");
    }

    /**
     * 根据source 执行出库消息反馈
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void executeMsgRtnOutbound(String source) {
        log.info("executeMsgRtnOutbound start ,source{}", source);
        bocDaemonTask.executeMsgRtnOutbound(source);
        log.info("executeMsgRtnOutbound start ,source{}", source);
    }

    /**
     * EMS 出库通知
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeEmsConfirmOrderQueue() {
        log.info("exeEmsConfirmOrderQueue start");
        order2WmsTask.exeEmsConfirmOrderQueue();
        log.info("exeEmsConfirmOrderQueue end");
    }

    /**
     * EMS出库通知多线程
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeEmsConfirmOrderQueueSeo() {
        log.error("exeEmsConfirmOrderQueueSeo start");
        order2WmsTask.exeEmsConfirmOrderQueueSeo();
        log.error("exeEmsConfirmOrderQueueSeo end ");
    }

    /**
     * 自动化补偿定时任务
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void msgToMcsTask() {
        log.info("msgToMcsTask start");
        order2WmsTask.msgToMcsTask();
        log.info("msgToMcsTask end");
    }

    /**
     * SF库存占用(新)
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void newExeSfConfirmOrderQueue() {
        log.info("newExeSfConfirmOrderQueue start");
        order2WmsTask.newExeSfConfirmOrderQueue();
        log.info("newExeSfConfirmOrderQueue end ");
    }

    /**
     * * 直连出库通知OMS (和退货入库)(非adidas pacs)
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void outboundNoticeOms() {
        log.info("outboundNoticeOms start");
        order2WmsTask.outboundNoticeOms();
        log.info("outboundNoticeOms end");
    }

    /**
     * 出库通知PAC
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void outboundNoticePac() {
        log.info("outboundNoticePac start");
        order2WmsTask.outboundNoticePac();
        log.info("outboundNoticePac end");
    }

    /**
     * AEO 出库订单 同步到AEORMS
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendAEOSalesOrderToRMSTask() {
        log.info("sendAEOSalesOrderToRMSTask start");
        idsInterfaceTask.sendAEOSalesOrderToRMSTask();
        log.info("sendAEOSalesOrderToRMSTask end");
    }

    /**
     * 根据source出库订单同步
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendSalesOrderToLFTask(String source) {
        log.info("sendSalesOrderToLFTask ,source{}", source);
        iDSInterfaceDaemonTask.sendSalesOrderToLFTask(source);
        log.info("sendSalesOrderToLFTask,source{}", source);
    }

    /**
     * pac直连转toms出库
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void outboudNoticePacSystenKeyDaemon() {
        log.error("outboudNoticePacSystenKeyDaemon start");
        order2WmsTask.outboudNoticePacSystenKeyDaemon();
        log.error("outboudNoticePacSystenKeyDaemon end");
    }

    /**
     * 通用补偿 执行失败或丢mq 失败 补偿
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void wmsCommonMessageProducerErrorMq() {
        log.error("wmsCommonMessageProducerErrorMq start");
        order2WmsTask.wmsCommonMessageProducerErrorMq();
        log.error("wmsCommonMessageProducerErrorMq end");
    }

    /**
     * 外包仓出库反馈 反馈执行失败或丢mq 失败 补偿
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void wmsRtnOutBountMq() {
        log.error("wmsRtnOutBountMq start");
        order2WmsTask.wmsRtnOutBountMq();
        log.error("wmsRtnOutBountMq end");
    }

    /**
     * 直连订单创单反馈 (非adidas)
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void wmsOrderFinishOms() {
        log.error("wmsOrderFinishOms start");
        order2WmsTask.wmsOrderFinishOms();
        log.error("wmsOrderFinishOms end");
    }


    /**
     * 非直连创建反馈补偿
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void orderCreateMsgToPac() {
        log.error("orderCreateMsgToPac start");
        order2WmsTask.orderCreateMsgToPac();
        log.error("orderCreateMsgToPac end");
    }

    /**
     * CNP 出库通知
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeCnpConfirmOrderQueue() {
        log.error("orderCreateMsgToPac start");
        order2WmsTask.exeCnpConfirmOrderQueue();
        log.error("orderCreateMsgToPac end");
    }

    /**
     * 顺丰出库反馈
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeSfConfirmOrderQueue() {
        log.error("exeSfConfirmOrderQueue start");
        order2WmsTask.exeSfConfirmOrderQueue();
        log.error("exeSfConfirmOrderQueue end");
    }

    /**
     * 库内移动exl执行
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeExlFileTask() {
        log.error("exeExlFileTask start");
        order2WmsTask.exeExlFileTask();
        log.error("exeExlFileTask end");
    }

    /**
     * 删除执行成功的文件。 删除执行失败一个星期前的文件。 定时任务每两小时执行一次
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void deleteExlFileTask() {
        log.error("deleteExlFileTask start");
        order2WmsTask.deleteExlFileTask();
        log.error("deleteExlFileTask end");
    }

    /**
     * 播种反馈执行
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void msgToMwsTask() {
        log.error("msgToMwsTask start");
        order2WmsTask.msgToMwsTask();
        log.error("msgToMwsTask end");
    }

    // /////////////////////////////////////////
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inventoryChangeToOms() {
        log.error("inventoryChangeToOms1");
        inventoryTransactionToOmsTask.inventoryChangeToOms();
        log.error("inventoryChangeToOms2");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inventoryTransationToOmsTask() {
        log.error("inventoryTransationToOmsTask1");
        inventoryTransactionToOmsTask.inventoryTransationToOmsTask();
        log.error("inventoryTransationToOmsTask2");

    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendEmailForITTO() {
        log.error("sendEmailForITTO1");
        inventoryTransactionToOmsTask.sendEmailForITTO();
        log.error("sendEmailForITTO2");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void errorTaskMailNotify() {
        log.error("errorTaskMailNotify1");
        daemonTask.errorTaskMailNotify();
        log.error("errorTaskMailNotify2");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void insertNikeInventory() {
        log.error("insertNikeInventory1");
        taskItochuIDS.insertNikeInventory();
        log.error("insertNikeInventory2");
    }

    @Override
    public void sendNikeCartonNo() {
        log.error("sendNikeCartonNo1");
        nikeCartonNoTask.sendNikeCartonNo();
        log.error("sendNikeCartonNo2");

    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void insertNikeStockReceive() {
        log.error("insertNikeStockReceive1");
        nikeTask.insertNikeStockReceive();
        log.error("insertNikeStockReceive2");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void idsNikeFeedbackTask() {
        log.error("idsNikeFeedbackTask1");
        iDSInterfaceTask.idsNikeFeedbackTask();
        log.error("idsNikeFeedbackTask2");
    }

    @Override
    public void sendNikeOrderToLFTask() {
        log.error("sendNikeOrderToLFTask1");
        iDSInterfaceTask.sendNikeOrderToLFTask();
        log.error("sendNikeOrderToLFTask2");
    }

    @Override
    public void startConverseTasks() {
        log.error("startConverseTasks1");
        converseTask.startConverseTasks();
        log.error("startConverseTasks2");
    }

    @Override
    public void downloadFtpDataAndCreateStaForConverseYx() {
        log.error("downloadFtpDataAndCreateStaForConverseYx1");
        converseYxTask.downloadFtpDataAndCreateStaForConverseYx();
        log.error("downloadFtpDataAndCreateStaForConverseYx2");
    }

    @Override
    public void uploadConverseYxData() {
        log.error("uploadConverseYxData1");
        converseYxTask.uploadConverseYxData();
        log.error("uploadConverseYxData2");

    }

    @Override
    public void jdReceiveOrder() {
        log.error("jdReceiveOrder1");
        jdOrderTask.jdReceiveOrder();
        log.error("jdReceiveOrder2");
    }

    @Override
    public void toHubGetJdTransNo() {
        log.error("toHubGetJdTransNo1");
        jdOrderTask.toHubGetJdTransNo();
        log.error("toHubGetJdTransNo2");

    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void deblocking() {
        log.error("deblocking1");
        arriveTimeTask.deblocking();
        log.error("deblocking2");

    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void errorEmail() {
        log.error("errorEmail1");
        arriveTimeTask.errorEmail();
        log.error("errorEmail2");

    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createRtnOrder() {
        log.error("createRtnOrder1");
        rtnOrderTask.createRtnOrder();
        log.error("createRtnOrder2");
    }

    @Override
    public void salesInventoryOmsEmail() {
        log.error("salesInventoryOmsEmail1");
        invTask.salesInventoryOmsEmail();
        log.error("salesInventoryOmsEmail2");
    }

    @Override
    public void emailToOmsForIncrementInvFailure() {
        log.error("emailToOmsForIncrementInvFailure1");
        invTask.emailToOmsForIncrementInvFailure();
        log.error("emailToOmsForIncrementInvFailure2");
    }

    @Override
    public void replenishForSalesInventory() {
        log.error("replenishForSalesInventory1");
        invTask.replenishForSalesInventory();
        log.error("replenishForSalesInventory2");
    }

    @Override
    public void ebsInventory() {
        log.error("ebsInventory1");
        invTask.ebsInventory();
        log.error("ebsInventory2");
    }

    @Override
    public void salesInventoryOms() {
        log.error("salesInventoryOms1");
        invTask.salesInventoryOms();
        log.error("salesInventoryOms2");

    }

    @Override
    public void downloadNikeData() {
        log.error("downloadNikeData1");
        nikeTask.downloadNikeData();
        log.error("downloadNikeData2");

    }

    @Override
    public void createStaForVmiDefault() {
        log.error("createStaForVmiDefault1");
        vmiDefaultTask.createStaForVmiDefault();
        log.error("createStaForVmiDefault2");
    }

    @Override
    public void vmiErrorEmailInform() {
        log.error("vmiErrorEmailInform1");
        vmiDefaultTask.vmiErrorEmailInform();
        log.error("vmiErrorEmailInform2");

    }

    @Override
    public void uploadNikeData() {
        log.error("uploadNikeData1");
        nikeTask.uploadNikeData();
        log.error("uploadNikeData2");
    }

    @Override
    public void mailIds() {
        log.error("mailIds1");
        idsTask.mailIds();
        log.error("mailIds2");
    }

    @Override
    public void insertNikeInventoryGZ() {
        log.error("insertNikeInventoryGZ1");
        taskItochuIDS.insertNikeInventoryGZ();
        log.error("insertNikeInventoryGZ2");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void insertNikeInventoryTM() {
        log.error("insertNikeInventoryTM1");
        taskItochuIDS.insertNikeInventoryTM();
        log.error("insertNikeInventoryTM2");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void insertNikeInventoryGZTM() {
        log.error("insertNikeInventoryGZTM1");
        taskItochuIDS.insertNikeInventoryGZTM();
        log.error("insertNikeInventoryGZTM2");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void checkStaRepetitive() {
        log.error("checkStaRepetitive1");
        checkStaRepetitiveTask.checkStaRepetitive();
        log.error("checkStaRepetitive2");

    }

    @Override
    public void emailToOmsForFailure() {
        log.error("emailToOmsForFailure1");
        taskCreateSta.emailToOmsForFailure();
        log.error("emailToOmsForFailure2");

    }

    @Override
    public void totalInv() {
        log.error("totalInv1");
        totalInvTask.totalInv();
        log.error("totalInv2");
    }

    public void totalRealtimeInv() {
        log.error("totalRealtimeInv1");
        totalRealtimeInvTask.totalRealtimeInv();
        log.error("totalRealtimeInv2");
    }

    @Override
    public void emailNotice() {
        log.error("emailNotice1");
        taskOms.emailNotice();
        log.error("emailNotice2");
    }

    @Override
    public void emailNoticeOms() {
        log.error("emailNoticeOms1");
        taskOms.emailNoticeOms();
        log.error("emailNoticeOms2");
    }


    @Override
    public void vmiOMSOutbound() {
        log.error("vmiOMSOutbound1");
        taskOms.vmiOMSOutbound();
        log.error("vmiOMSOutbound2");
    }

    @Override
    public void createSta() {
        log.error("createSta1");
        taskCreateSta.createSta();
        log.error("createSta2");
    }

    /**
     * 缺失体积或重量的商品是否已经维护好了
     */
    @Override
    public void verifyThreeDimensionalOver() {
        log.error("verifyThreeDimensionalOver1");
        autoOutboundTurnboxManager.verifyThreeDimensionalOver();
        log.error("verifyThreeDimensionalOver2");
    }

    @Override
    public void sendStaCartonInfoToLMIS() {
        log.error("sendStaCartonInfoToLMIS1");
        staCartonInfoTask.sendToLMIS();
        log.error("sendStaCartonInfoToLMIS2");
    }
    
 public void consumerMsgDataCompensate(String topics) {//定时任务补偿
        mqTask.consumerMsgDataCompensate(topics);
    }
    /*
     * @Override public void sendStaCartonInfoToLMIS() { log.error("sendStaCartonInfoToLMIS1");
     * staCartonInfoTask.sendToLMIS(); log.error("sendStaCartonInfoToLMIS2"); }
     */

}
