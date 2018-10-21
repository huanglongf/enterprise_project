package com.jumbo.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.automaticEquipment.MsgToWcsManager;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.manager.task.ems.EmsTaskManager;
import com.jumbo.wms.manager.task.sforder.SfOrderTaskManager;
import com.jumbo.wms.manager.warehouse.AutoOutboundTurnboxManager;
import com.jumbo.wms.manager.warehouse.QstaManager;
import com.jumbo.wms.manager.warehouse.QueueStaManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.automaticEquipment.MsgToWms;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.automaticEquipment.MsgToWcsCommand;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;
import com.jumbo.wms.model.msg.MessageProducerError;
import com.jumbo.wms.model.pickZone.OcpInvConstants;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.AdPackageLineDealDto;
import com.jumbo.wms.model.warehouse.ImportFileLog;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOms;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundDto;
import com.jumbo.wms.newInventoryOccupy.FileExecuteThread;
import com.jumbo.wms.newInventoryOccupy.MsgToWcsThread;
import com.jumbo.wms.newInventoryOccupy.OutboundNoticeOmsThread;
import com.jumbo.wms.newInventoryOccupy.OutboundNoticePacThread;
import com.jumbo.wms.newInventoryOccupy.TransConfrimThread;
import com.jumbo.wms.newInventoryOccupy.TransGetNoThread;
import com.jumbo.wms.newInventoryOccupy.WmsCommonMessageProducerErrorMqThread;
import com.jumbo.wms.newInventoryOccupy.WmsOrderFinishOmsThread;
import com.jumbo.wms.newInventoryOccupy.WmsRtnOutBountMqThread;


/**
 * 非直连过仓
 * 
 * @author Administrator
 * 
 */
@Service("order2WmsTask")
public class Order2WmsTask {
    protected static final Logger log = LoggerFactory.getLogger(Order2WmsTask.class);

    @Autowired
    private QueueStaManagerProxy queueStaManagerProxy;
    @Autowired
    private QstaManager qstaManager;
    @Autowired
    private SfOrderTaskManager sfOrderTaskManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private TaskOmsOutManager taskOmsOutManager;
    @Autowired
    private EmsTaskManager emsTaskManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private MsgToWcsManager msgToWcsManager;
    @Autowired
    private AutoOutboundTurnboxManager autoOutboundTurnboxManager;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private ExcelWriterManager excelWriterManager;
    @Autowired
    private ThreadPoolService threadPoolService;
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    
    
    /**
     * 推送ad异常包裹（2.3异常退货订单更新）//orders_status_update
     */
    public void adPackageUpdate() {
        log.error("adPackageUpdate.....");
        if (log.isInfoEnabled()) {
            log.info("adPackageUpdate start...");
        }
        List<AdPackageLineDealDto> list = null;
        do {
            // 待处理订单列表
            list = taskOmsOutManager.adPackageUpdate();
            if (log.isInfoEnabled()) {
                log.info("adPackageUpdate thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (AdPackageLineDealDto c : list) {
                try {
                    Thread t = new AdPackageLineDealThread(c);
                    threadPoolService.executeThread("adPackageUpdate", t);
                } catch (Exception e) {
                    log.error("adPackageUpdate", e);
                }

            }

            threadPoolService.waitToFinish("adPackageUpdate");
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("adPackageUpdate end...");
        }
    }

    


    /***
     * 新建状态占用 未执行丢MQ 已迁移
     */

    public void wmsZhanYongDaemon() {
        if (log.isInfoEnabled()) {
            log.info("wmsZhanYongDaemon start...");
        }
        List<StockTransApplication> list = null;
        do {
            list = taskOmsOutManager.getListWmsZhanYong();
            if (log.isInfoEnabled()) {
                log.info("wmsCreatePickingDaemon thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (StockTransApplication c : list) {
                try {
                    Thread t = new ZhanYongThread(c);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_ZHAN_YONG, t);
                } catch (Exception e) {
                    log.error("wmsZhanYongDaemon", e);
                }

            }

            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_ZHAN_YONG);
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("wmsZhanYongDaemon end...");
        }
    }

    /**
     * AGV出库推送
     */

    public void agvOutBoundDaemon() {
        log.error("agvOutBoundDaemon.....");
        if (log.isInfoEnabled()) {
            log.info("agvOutBoundDaemon start...");
        }
        List<AgvOutBoundDto> list = null;
        do {
            // 待处理订单列表
            list = taskOmsOutManager.agvOutBoundDaemon();
            if (log.isInfoEnabled()) {
                log.info("agvOutBoundDaemon thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (AgvOutBoundDto c : list) {
                try {
                    Thread t = new AgvOutBoundThread(c);
                    threadPoolService.executeThread("agvOutBoundDaemon", t);
                } catch (Exception e) {
                    log.error("agvOutBoundDaemon", e);
                }

            }

            threadPoolService.waitToFinish("agvOutBoundDaemon");
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("agvOutBoundDaemon end...");
        }
    }



    /**
     * pac直连转toms创单 已迁移
     */
    public void wmsConfirmOrderServiceDaemon() {
        if (log.isInfoEnabled()) {
            log.info("wmsConfirmOrderServiceDaemon start...");
        }
        List<WmsConfirmOrderQueue> list = null;
        do {
            // 待处理订单列表
            list = taskOmsOutManager.getListByQueryPac2();
            if (log.isInfoEnabled()) {
                log.info("wmsConfirmOrderServiceDaemon thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (WmsConfirmOrderQueue c : list) {
                try {
                    Thread t = new WmsConfirmThread(c);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_PACS_TOMS_OUTBOUND, t);
                } catch (Exception e) {
                    log.error("wmsConfirmOrderServiceDaemon", e);
                }

            }

            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_PACS_TOMS_OUTBOUND);
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("wmsConfirmOrderServiceDaemon end...");
        }

    }

    /**
     * pac直连转toms出库 已经迁移
     */
    public void outboudNoticePacSystenKeyDaemon() {
        if (log.isInfoEnabled()) {
            log.info("outboudNoticePacSystenKeyDaemon start...");
        }
        List<WmsOrderStatusOms> list = null;
        do {
            // 待处理订单列表
            list = taskOmsOutManager.wmsOrderConfirmPac2();
            if (log.isInfoEnabled()) {
                log.info("outboudNoticePacSystenKeyDaemon thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (WmsOrderStatusOms c : list) {
                try {
                    Thread t = new WmsOrderStatusOmsThread(c);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_PACS_TOMS_OUTBOUND, t);
                } catch (Exception e) {
                    log.error("", e);
                }

            }

            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_PACS_TOMS_OUTBOUND);
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("outboudNoticePacSystenKeyDaemon end...");
        }

    }


    /**
     * 通用补偿 执行失败或丢mq 失败 补偿 已经迁移
     */

    public void wmsCommonMessageProducerErrorMq() {
        if (log.isInfoEnabled()) {
            log.info("wmsCommonMessageProducerErrorMq start...");
        }
        List<MessageProducerError> list = null;
        do {
            // ChooseOption co =
            // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD,
            // ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_COMMON_ERROR_MQ);
            // Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
            // ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            list = taskOmsOutManager.wmsCommonMessageProducerErrorMq();
            if (log.isInfoEnabled()) {
                // log.info("wmsCommonMessageProducerErrorMq thread pool size : {},todo list size :{}",
                // threadPoolQty, list.size());
                log.info("wmsCommonMessageProducerErrorMq thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (MessageProducerError c : list) {
                try {
                    Thread t = new WmsCommonMessageProducerErrorMqThread(c.getId());
                    // tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_COMMON_ERROR_MQ, t);
                } catch (Exception e) {
                    log.error("", e);
                }
                // while (true) {
                // long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                // if (todoTotal >= 1000) {
                // try {
                // Thread.sleep(500L);
                // log.debug("wmsCommonMessageProducerErrorMq, thread todoTotal is " + todoTotal);
                // } catch (InterruptedException e) {
                // log.error("sleep error");
                // }
                // } else {
                // break;
                // }
                // }
            }
            // tx.shutdown();
            // boolean isFinish = false;
            // while (!isFinish) {
            // isFinish = pool.isTerminated();
            // try {
            // Thread.sleep(1000L);
            // } catch (InterruptedException e) {
            // log.error("InterruptedException majorThread error");
            // }
            // }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_COMMON_ERROR_MQ);
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("wmsCommonMessageProducerErrorMq end...");
        }

    }



    /**
     * 外包仓出库反馈 反馈执行失败或丢mq 失败 补偿 已经迁移
     */
    public void wmsRtnOutBountMq() {
        if (log.isInfoEnabled()) {
            log.info("wmsRtnOutBountMq start...");
        }
        List<MsgRtnOutbound> list = null;
        do {
            // ChooseOption co =
            // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD,
            // ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_RTN_MQ);
            // Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
            // ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            // 待处理订单列表
            list = taskOmsOutManager.wmsRtnOutBountMq();
            if (log.isInfoEnabled()) {
                // log.info("wmsOrderFinishOms thread pool size : {},todo list size :{}",
                // threadPoolQty, list.size());
                log.info("wmsOrderFinishOms thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (MsgRtnOutbound c : list) {
                try {
                    Thread t = new WmsRtnOutBountMqThread(c.getId());
                    // tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_RTN_MQ, t);
                } catch (Exception e) {
                    log.error("", e);
                }
                // while (true) {
                // long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                // if (todoTotal >= 1000) {
                // try {
                // Thread.sleep(500L);
                // log.debug("wmsRtnOutBountMq, thread todoTotal is " + todoTotal);
                // } catch (InterruptedException e) {
                // log.error("sleep error");
                // }
                // } else {
                // break;
                // }
                // }
            }
            // tx.shutdown();
            // boolean isFinish = false;
            // while (!isFinish) {
            // isFinish = pool.isTerminated();
            // try {
            // Thread.sleep(1000L);
            // } catch (InterruptedException e) {
            // log.error("InterruptedException majorThread error");
            // }
            // }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_RTN_MQ);
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("wmsRtnOutBountMq end...");
        }

    }


    /**
     * 直连订单创单反馈 (非adidas) 已经迁移
     */
    public void wmsOrderFinishOms() {
        if (log.isInfoEnabled()) {
            log.info("wmsOrderFinishOms start...");
        }
        List<WmsConfirmOrderQueue> list = null;
        do {
            // ChooseOption co =
            // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD,
            // ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_FINISH_OMS);
            // Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
            // ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            // 待处理订单列表
            list = taskOmsOutManager.findWmsOrderFinishList();
            if (log.isInfoEnabled()) {
                // log.info("wmsOrderFinishOms thread pool size : {},todo list size :{}",
                // threadPoolQty, list.size());
                log.info("wmsOrderFinishOms thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (WmsConfirmOrderQueue c : list) {
                try {
                    Thread t = new WmsOrderFinishOmsThread(c.getId());
                    // tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_FINISH_OMS, t);
                } catch (Exception e) {
                    log.error("", e);
                }
                // while (true) {
                // long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                // if (todoTotal >= 1000) {
                // try {
                // Thread.sleep(500L);
                // log.debug("wmsOrderFinishOms, thread todoTotal is " + todoTotal);
                // } catch (InterruptedException e) {
                // log.error("sleep error");
                // }
                // } else {
                // break;
                // }
                // }
            }
            // tx.shutdown();
            // boolean isFinish = false;
            // while (!isFinish) {
            // isFinish = pool.isTerminated();
            // try {
            // Thread.sleep(1000L);
            // } catch (InterruptedException e) {
            // log.error("InterruptedException majorThread error");
            // }
            // }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_FINISH_OMS);
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("wmsOrderFinishOms end...");
        }
    }

    /**
     * 非直连创建反馈补偿 已经迁移
     */
    public void orderCreateMsgToPac() {
        List<Long> idList = createStaTaskManager.findOrderCreateOrderToPac();
        if (idList != null && idList.size() > 0) {
            for (Long id : idList) {
                try {
                    createStaTaskManager.sendCreateOrderMQToPacById(id);
                } catch (Exception e) {
                    log.error("orderCreateMsgToPac " + id, e);
                }
            }
        }
    }


    /**
     * 直连出库通知OMS (和退货入库)(非adidas pacs) 已经迁移
     */
    public void outboundNoticeOms() {
        if (log.isInfoEnabled()) {
            log.info("outboundNoticeOms start...");
        }
        List<WmsOrderStatusOms> list;
        do {
            // ChooseOption co =
            // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD,
            // ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_OMS);
            // Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
            // ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            // 待处理订单列表
            list = taskOmsOutManager.findToNoticeOrderOms();
            if (log.isInfoEnabled()) {
                // log.info("outboundNoticeOms thread pool size : {},todo list size :{}",
                // threadPoolQty, list.size());
                log.info("outboundNoticeOms thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (WmsOrderStatusOms order : list) {
                try {
                    Thread t = new OutboundNoticeOmsThread(order.getId());
                    // tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_OMS, t);
                } catch (Exception e) {
                    log.error("", e);
                }
                // while (true) {
                // long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                // if (todoTotal >= 1000) {
                // try {
                // Thread.sleep(500L);
                // log.debug("outbound notice oms, thread todoTotal is " + todoTotal);
                // } catch (InterruptedException e) {
                // log.error("sleep error");
                // }
                // } else {
                // break;
                // }
                // }
            }
            // tx.shutdown();
            // boolean isFinish = false;
            // while (!isFinish) {
            // isFinish = pool.isTerminated();
            // try {
            // Thread.sleep(1000L);
            // } catch (InterruptedException e) {
            // log.error("InterruptedException majorThread error");
            // }
            // }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_OMS);
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("outboundNoticeOms end...");
        }
    }


    /**
     * 出库通知PAC 已经迁移
     */
    public void outboundNoticePac() {
        if (log.isInfoEnabled()) {
            log.info("outboundNoticePac start...");
        }
        List<WmsIntransitNoticeOms> list;
        do {
            // ChooseOption co =
            // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD,
            // ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_PAC);
            // Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
            // ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            // 待处理订单列表
            list = taskOmsOutManager.findToNoticeOrder();
            if (log.isInfoEnabled()) {
                // log.info("outboundNoticePac thread pool size : {},todo list size :{}",
                // threadPoolQty, list.size());
                log.info("outboundNoticePac thread pool size : todo list size :{}", list.size());
            }
            // 分线程通知
            for (WmsIntransitNoticeOms order : list) {
                try {
                    Thread t = new OutboundNoticePacThread(order.getStaId(), order.getId());
                    // tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_PAC, t);
                } catch (Exception e) {
                    log.error("", e);
                }
                // while (true) {
                // long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                // if (todoTotal >= 1000) {
                // try {
                // Thread.sleep(500L);
                // log.debug("outbound notice pac, thread todoTotal is " + todoTotal);
                // } catch (InterruptedException e) {
                // log.error("sleep error");
                // }
                // } else {
                // break;
                // }
                // }
            }
            // tx.shutdown();
            // boolean isFinish = false;
            // while (!isFinish) {
            // isFinish = pool.isTerminated();
            // try {
            // Thread.sleep(1000L);
            // } catch (InterruptedException e) {
            // log.error("InterruptedException majorThread error");
            // }
            // }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_OUTBOUND_NOTICE_PAC);
        } while (list.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("outboundNoticePac end...");
        }
    }

    public void sfGetTransNoByWh(String strWhId) {
        if (log.isInfoEnabled()) {
            log.info("get sf trans no start-------------------:" + strWhId);
        }
        Long whouid = Long.valueOf(strWhId);
        Boolean flag = true;
        // ChooseOption co =
        // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD,
        // ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_SF_TRANS_NO_WH);
        // Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
        // 根据参数创建线程池
        while (flag) {
            // ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            List<String> lpList = new ArrayList<String>();
            lpList.add(Transportator.SF);
            lpList.add(Transportator.SFCOD);
            lpList.add(Transportator.SFDSTH);
            List<Long> staList = sfOrderTaskManager.findGetTransNoSta(whouid, lpList);
            if (staList.size() < 1000) {
                flag = false;
            }
            for (Long id : staList) {
                try {
                    Thread t = new TransGetNoThread(whouid, id, Transportator.SF);
                    // tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_SF_TRANS_NO_WH, t);
                } catch (Exception e) {
                    log.error("", e);
                }
                // while (true) {
                // long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                // if (todoTotal >= 100) {
                // try {
                // Thread.sleep(500L);
                // if (log.isDebugEnabled()) {
                // log.debug("get sf trans no thread todoTotal is " + todoTotal);
                // }
                // } catch (InterruptedException e) {
                // log.error("sleep error");
                // }
                // } else {
                // break;
                // }
                // }
            }
            // tx.shutdown();
            // boolean isFinish = false;
            // while (!isFinish) {
            // isFinish = pool.isTerminated();
            // try {
            // Thread.sleep(1000L);
            // } catch (InterruptedException e) {
            // log.error("InterruptedException majorThread error");
            // }
            // }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_SF_TRANS_NO_WH);
        }
        if (log.isInfoEnabled()) {
            log.info("get sf trans no end-------------------:" + strWhId);
        }
    }

    public void createStaByWh(String strWhId) {
        if (log.isInfoEnabled()) {
            log.info("createStaByWh start-------------------:" + strWhId);
        }
        Long whouid = Long.valueOf(strWhId);
        // 生成批次号
        queueStaManagerProxy.generateQstaBatchCode(whouid);
        // 查询所有批次号
        List<String> salesBatchs = queueStaManagerProxy.findBatchCodeByWhouid(whouid, QueueStaManagerProxy.Q_STA_ORDER_TYPE_SALES);
        // 销售订单计算
        for (String batchCode : salesBatchs) {
            try {
                qstaManager.createStabyBatchCode(whouid, batchCode);
            } catch (Exception e) {
                log.error("sta create error, bartch is : " + batchCode);
            }

        }
        List<String> spBatches = queueStaManagerProxy.findBatchCodeByWhouid(whouid, QueueStaManagerProxy.Q_STA_ORDER_TYPE_SP);
        // 特殊逻辑订单计算
        for (String batchCode : spBatches) {
            try {
                queueStaManagerProxy.createStabyBatchCode(whouid, batchCode);
            } catch (Exception e) {
                log.error("sta create error, bartch is : " + batchCode);
            }
        }
        List<String> returnBatches = queueStaManagerProxy.findBatchCodeByWhouid(whouid, QueueStaManagerProxy.Q_STA_ORDER_TYPE_RETURN);
        // 退换货订单计算
        for (String batchCode : returnBatches) {
            try {
                queueStaManagerProxy.createStabyBatchCode(whouid, batchCode);
            } catch (Exception e) {
                log.error("sta create error, bartch is : " + batchCode);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("createStaByWh end-------------------:" + strWhId);
        }
    }

    /**
     * EMS 出库通知 已经迁移
     */
    public void exeEmsConfirmOrderQueue() {
        Boolean b = emsTaskManager.getChooseEmsHttps();
        if (log.isInfoEnabled()) {
            log.info("exeEmsConfirmOrderQueue start-------------------");
        }
        Boolean flag = true;
        while (flag) {
            // 一次查询5000条，如果大于1000条，继续查询
            List<Long> qsId = emsTaskManager.findExtOrderIdSeo();
            if (qsId.size() < 1000) {
                flag = false;
            }
            for (Long qId : qsId) {
                try {
                    if (b) {// 老的
                        emsTaskManager.exeEmsConfirmOrder(qId);
                    } else {// 新的
                        emsTaskManager.exeEmsConfirmOrderNew(qId);
                    }
                } catch (BusinessException e) {
                    log.debug("exeEmsConfirmOrderQueue be : " + e.getErrorCode());
                } catch (Exception e) {
                    log.error("exeEmsConfirmOrderQueue error " + e.toString());
                }
            }
        }
    }

    /**
     * EMS发货确认优化
     */
    public void exeEmsConfirmOrderQueueSeo() {
        log.error("exeEmsConfirmOrderQueueSeo start2");
        if (log.isInfoEnabled()) {
            log.info("exeEmsConfirmOrderQueueSeo start-------------------");
        }
        Boolean b = emsTaskManager.getChooseEmsHttps();
        String flag = "";
        if (b) {// 老的
            flag = "EMSOLD";
        } else {// 新的
            flag = "EMSNEW";
        }
        List<Long> qsId = emsTaskManager.findExtOrderIdSeo();
        if (qsId != null && qsId.size() > 0) {
            for (Long id : qsId) {
                // 每个占用批一个线程
                TransConfrimThread t = new TransConfrimThread(id, flag, wareHouseManager, emsTaskManager);
                Thread d = new Thread(t);
                threadPoolService.executeThread(OcpInvConstants.OCP_THREAD_COUNT, d);
            }
            threadPoolService.waitToFinish(OcpInvConstants.OCP_THREAD_COUNT);
        }
        if (log.isInfoEnabled()) {
            log.info("exeEmsConfirmOrderQueueSeo end-------------------");
        }
    }


    /**
     * EMS 出库通知 NEW
     */
    // public void exeEmsConfirmOrderQueueNew() {
    // if (log.isInfoEnabled()) {
    // log.info("exeEmsConfirmOrderQueue2 start-------------------");
    // }
    // Boolean flag = true;
    // while (flag) {
    // // 一次查询5000条，如果大于1000条，继续查询
    // List<Long> qsId = emsTaskManager.findExtOrderIdSeo();
    // if (qsId.size() < 1000) {
    // flag = false;
    // }
    // for (Long qId : qsId) {
    // try {
    // emsTaskManager.exeEmsConfirmOrderNew(qId);
    // } catch (Exception e) {
    // log.error("exeEmsConfirmOrderQueue2 error " + e.toString());
    // }
    // }
    // }
    // }



    /**
     * CNP 出库通知 已经迁移
     */
    public void exeCnpConfirmOrderQueue() {
        if (log.isInfoEnabled()) {
            log.info("exeCnpConfirmOrderQueue start-------------------");
        }
        Boolean flag = true;
        while (flag) {
            // 一次查询5000条，如果大于1000条，继续查询
            List<Long> qsId = emsTaskManager.findCnpOrderIdSeo();
            if (qsId.size() < 1000) {
                flag = false;
            }
            for (Long qId : qsId) {
                try {
                    emsTaskManager.exeCnpConfirmOrder(qId);
                } catch (BusinessException e) {
                    log.error(qId + " exeCnpConfirmOrderQueue be " + e.getErrorCode());
                } catch (Exception e) {
                    log.error(qId + " exeCnpConfirmOrderQueue error " + e.toString());
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info("exeCnpConfirmOrderQueue end-------------------");
        }
    }

    /**
     * 顺丰出库反馈 已经迁移
     */
    public void exeSfConfirmOrderQueue() {
        if (log.isInfoEnabled()) {
            log.info("exeSfConfirmOrderQueue start-------------------");
        }
        Boolean flag = true;
        while (flag) {
            List<Long> qsId = wareHouseManager.findExtOrderIdSeo(Constants.SF_QUEUE_TYP_COUNT);
            if (qsId.size() < 1000) {
                flag = false;
            }
            for (Long qId : qsId) {
                try {
                    wareHouseManager.exeSfConfirmOrder(qId);
                } catch (BusinessException e) {
                    log.debug("exeEmsConfirmOrderQueue be " + e.getErrorCode());
                } catch (Exception e) {
                    log.error("exeEmsConfirmOrderQueue error " + e.toString());
                }
            }
        }

    }

    // 已经迁移
    public void newExeSfConfirmOrderQueue() {
        if (log.isInfoEnabled()) {
            log.info("newExeSfConfirmOrderQueue start-------------------");
        }
        List<Long> qsId = wareHouseManager.findExtOrderIdSeo(Constants.SF_QUEUE_TYP_COUNT);
        if (qsId != null && qsId.size() > 0) {
            for (Long id : qsId) {
                // 每个占用批一个线程
                TransConfrimThread t = new TransConfrimThread(id, "SF", wareHouseManager, null);
                Thread d = new Thread(t);
                threadPoolService.executeThread(OcpInvConstants.OCP_THREAD_COUNT, d);
            }
            threadPoolService.waitToFinish(OcpInvConstants.OCP_THREAD_COUNT);
        }
        if (log.isInfoEnabled()) {
            log.info("newExeSfConfirmOrderQueue end-------------------");
        }
    }

    /**
     * 库内移动exl执行 已经迁移
     */
    public void exeExlFileTask() {
        log.error("exeExlFileTask start2");

        if (log.isInfoEnabled()) {
            log.info("exeExlFileTask start-------------------");
        }
        // 获取批次相关的常量配置
        // ChooseOption co =
        // chooseOptionManager.findChooseOptionByCategoryCodeAndKey("system_thread",
        // "exl_file_exe");
        // Integer amount = Integer.parseInt(co.getOptionValue());
        List<Long> whList = excelReadManager.findAllTodowhId(1, 1);
        if (whList != null && whList.size() > 0) {
            // ExecutorService pool = Executors.newFixedThreadPool(amount);
            // 根据参数创建线程池
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            for (Long whId : whList) {
                // String fileUrl = url + file.getFileName() + "/" + file.getColum2();
                // 每个占用批一个线程
                FileExecuteThread t = new FileExecuteThread(whId, excelWriterManager);
                Thread d = new Thread(t);
                threadPoolService.executeThread("exl_file_exe", d);
                // ThreadPoolUtil.executeThread(tx, d, 10);
            }
            // ThreadPoolUtil.closeThreadPool(tx);// 停止往线程池里放线程
            threadPoolService.waitToFinish("exl_file_exe");
        }
        if (log.isInfoEnabled()) {
            log.info("exeExlFileTask end-------------------");
        }
    }

    /**
     * 删除执行成功的文件。 删除执行失败一个星期前的文件。 定时任务每两小时执行一次 已经迁移
     */
    public void deleteExlFileTask() {
        if (log.isInfoEnabled()) {
            log.info("deleteExlFileTask start-------------------");
        }
        // 查状态为10，sta_code 不为空的文件，作业单状态完成的数据。 或sta_code 为空的文件，一个星期前的数据
        List<ImportFileLog> fileList = excelReadManager.findAllToDeleteFile();
        for (ImportFileLog file : fileList) {
            try {
                excelReadManager.exeDeleteFileTask(file);
            } catch (Exception e) {
                log.error("deleteExlFileTask is error,file is:" + file.getId(), e);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("deleteExlFileTask end-------------------");
        }
    }

    /**
     * STO匹配运单号
     */
    public void stoInterfaceAllWarehouse() {
        Long count = wareHouseManager.getTranNoNumberByLpCode();
        if (count > 0) {
            List<Long> idList = wareHouseManager.getAllSTOWarehouse();
            for (Long id : idList) {
                try {
                    stoIntefaceByWarehouse(id);
                } catch (Exception e) {
                    log.error("stoInterfaceAllWarehouse : " + e.toString());
                }
            }
        }
    }

    public void stoIntefaceByWarehouse(Long whId) {
        if (log.isInfoEnabled()) {
            log.info("==============" + whId + "==================");
        }
        Warehouse wh = wareHouseManager.getWarehouseByOuId(whId);
        if (wh == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }
        Boolean flag = true;
        while (flag) {
            List<String> lpList = new ArrayList<String>();
            lpList.add(Transportator.STO);
            List<Long> staList = wareHouseManager.findStaByOuIdAndStatus(whId, lpList);
            if (staList.size() < 100) {
                flag = false;
            }
            for (Long id : staList) {
                // STO下单
                try {
                    // 设置STO单据号
                    transOlManager.matchingTransNo(id);
                } catch (BusinessException e) {
                    log.error("stoIntefaceByWarehouse  error: " + e.getErrorCode());
                } catch (Exception e) {
                    log.error("stoIntefaceByWarehouse error : " + e.toString());
                }
            }
        }
    }

    /**
     * SF匹配运单号
     */
    public void sfInterfaceAllWarehouse() {
        List<Long> idList = wareHouseManager.getAllSFWarehouse();
        for (Long id : idList) {
            try {
                sfGetTransNoByWh(String.valueOf(id));
            } catch (Exception e) {
                log.error("sfInterfaceAllWarehouse error : " + e.toString());
            }
        }
    }



    /**
     * 自动化消息推送补偿定时任务 已经迁移
     */
    public void msgToMcsTask() {
        List<MsgToWcsCommand> list;
        list = msgToWcsManager.findWcsList();
        log.info("msgToMcsTask is start......");
        if (list.size() > 0) {
            // ChooseOption co =
            // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD,
            // ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_WCS);
            // Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
            // ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            // 待处理订单列表
            // 分线程通知
            for (MsgToWcsCommand order : list) {
                try {
                    MsgToWcsThread t = new MsgToWcsThread(order.getMsgType(), null, order.getId(), null);
                    Thread d = new Thread(t);
                    // tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_WCS, d);
                } catch (Exception e) {
                    log.error("msgToMcsTask is error:", e);
                }
                // while (true) {
                // long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                // if (todoTotal >= 1000) {
                // try {
                // Thread.sleep(500L);
                // if (log.isDebugEnabled()) {
                // log.debug("msgToMcsTask, thread todoTotal is " + todoTotal);
                // }
                // } catch (InterruptedException e) {
                // log.error("msgToMcsTask sleep error");
                // }
                // } else {
                // break;
                // }
                // }
            }
            // tx.shutdown();
            // boolean isFinish = false;
            // while (!isFinish) {
            // isFinish = pool.isTerminated();
            // try {
            // Thread.sleep(1000L);
            // } catch (InterruptedException e) {
            // log.error("msgToMcsTask InterruptedException majorThread error");
            // }
            // }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_WCS);
        }
        if (log.isInfoEnabled()) {
            log.info("msgToMcsTask is end......");
        }

    }

    /**
     * 播种反馈执行 已经迁移
     */
    public void msgToMwsTask() {
        List<MsgToWms> tt = msgToWcsManager.findWmsBzList();
        for (MsgToWms t : tt) {
            try {
                autoOutboundTurnboxManager.resetTurnoverBoxStatusByPickingList(t.getPkId(), null);
                msgToWcsManager.updateBzStatusById(t.getId());
            } catch (Exception e) {
                log.error(t.getId() + "msgToMwsTask is error:", e);
            }
        }
    }
}
