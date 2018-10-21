package com.jumbo.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;


public class IDSInterfaceDaemonTask {
    protected static final Logger log = LoggerFactory.getLogger(IDSInterfaceDaemonTask.class);
    @Autowired
    private IdsManagerProxy idsManagerProxy;
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    private ThreadPoolService threadPoolService;

    /**
     * 预售订单 发货单释放 同步立峰
     * 
     * @param source
     */

    public void sendPreSalesOrderToLFTask(String source) {

        if (log.isInfoEnabled()) {
            log.info("sendPreSalesOrderToLFTask " + source + " start");
        }
        IdsServerInformation idsServerInformation = idsManagerProxy.findServerInformationBySource(source);
        Integer pNum = null;
        try {
            pNum = createStaTaskManager.getLFPiCiValueByKey(source + "Pre");
        } catch (Exception e) {
            pNum = 10;
        }
        if (log.isInfoEnabled()) {
            log.info("sendPreSalesOrderToLFTask " + source + " batch set start");
        }
        while (true) {
            Long batchNo = idsManagerProxy.findBatchNoPre();
            Integer updateCount = idsManagerProxy.updatePreOrderBatchNo(batchNo, source, pNum);
            if (updateCount == null || updateCount < pNum) {
                break;
            }
        }

        if (log.isInfoEnabled()) {
            log.info("IDSInterfaceDaemonTask sendPreSalesOrderToLFTask " + source + " batch set end");
        }
        List<Long> batchNoList = idsManagerProxy.findPreOrderBatchNoBySource(source);

        Integer num = null;
        try {
            num = createStaTaskManager.getSystemThreadValueByKeyAndDes(source + "Pre", true);// 创建线程池
        } catch (Exception e) {
            num = 1;// 如果没有配置默认为1
        }
        if (log.isInfoEnabled()) {
            log.info("IDSInterfaceDaemonTask sendPreSalesOrderToLFTask " + source + " , pool : {},size :{}", num, batchNoList.size());
        }
        ExecutorService pool = Executors.newFixedThreadPool(num);
        ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
        for (Long batchNo : batchNoList) {
            try {
                PreIdsThread t = new PreIdsThread(source, batchNo, idsServerInformation);
                tx.execute(t);
            } catch (Exception e) {
                log.error("sendPreSalesOrderToLFTask is error:", e);
            }

            while (true) {
                long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                if (todoTotal >= 1000) {
                    try {
                        Thread.sleep(500L);
                        if (log.isDebugEnabled()) {
                            log.debug("IDSInterfaceDaemonTask sendPreSalesOrderToLFTask " + source + " batch set end");
                        }
                    } catch (InterruptedException e) {
                        log.error("sendPreSalesOrderToLFTask sleep error");
                    }
                } else {
                    break;
                }
            }

        }
        tx.shutdown();
        boolean isFinish = false;
        while (!isFinish) {
            isFinish = pool.isTerminated();
            try {
                Thread.sleep(5 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (log.isInfoEnabled()) {
            log.info("IDSInterfaceDaemonTask sendPreSalesOrderToLFTask " + source + " end");
        }
    }


    /*
     * 出库订单 同步到立峰
     */
    public void sendSalesOrderToLFTask(String source) {
        if (log.isInfoEnabled()) {
            log.info("IDSInterfaceDaemonTask sendSalesOrderToLFTask " + source + " start");
        }
        IdsServerInformation idsServerInformation = idsManagerProxy.findServerInformationBySource(source);
        
        List<Long> batchNoList = idsManagerProxy.findMsgOutboundOrderBatchNoBySource(source);
//        Integer num = null;
//        try {
//            num = createStaTaskManager.getSystemThreadValueByKeyAndDes(source, true);// 创建线程池
//        } catch (Exception e) {
//            num = 1;// 如果没有配置默认为1
//        }
        if (log.isInfoEnabled()) {
//            log.info("IDSInterfaceDaemonTask sendSalesOrderToLFTask " + source + " , pool : {},size :{}", num, batchNoList.size());
            log.info("IDSInterfaceDaemonTask sendSalesOrderToLFTask " + source + " , pool :size :{}", batchNoList.size());
        }
//        ExecutorService pool = Executors.newFixedThreadPool(num);
//        ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
        for (Long batchNo : batchNoList) {
            try {
                Thread thread = new IdsThread(source, batchNo, idsServerInformation);
//                tx.execute(thread);
                threadPoolService.executeThread("SendSalesOrderToLFTask_" + source, thread);
            } catch (Exception e) {
                log.error("sendSalesOrderToLFTask is error:", e);
            }

//            while (true) {
//                long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
//                if (todoTotal >= 1000) {
//                    try {
//                        Thread.sleep(500L);
//                        if (log.isDebugEnabled()) {
//                            log.debug("IDSInterfaceDaemonTask sendSalesOrderToLFTask " + source + " batch set end");
//                        }
//                    } catch (InterruptedException e) {
//                        log.error("sendSalesOrderToLFTask sleep error");
//                    }
//                } else {
//                    break;
//                }
//            }

        }
//        tx.shutdown();
//        boolean isFinish = false;
//        while (!isFinish) {
//            isFinish = pool.isTerminated();
//            try {
//                Thread.sleep(5 * 1000L);
//            } catch (InterruptedException e) {
//                // e.printStackTrace();
//                if (log.isErrorEnabled()) {
//                    log.error("IdsThread", e);
//                }
//            }
//        }
        threadPoolService.waitToFinish("SendSalesOrderToLFTask_" + source);
        if (log.isInfoEnabled()) {
            log.info("IDSInterfaceDaemonTask sendSalesOrderToLFTask " + source + " end");
        }
    }
    
    //设置批次
    public void setSalesOrderBatchTask(String source) {
        if (log.isInfoEnabled()) {
            log.info("IDSInterfaceDaemonTask setSalesOrderBatchTask " + source + " batch set start");
        }
        Integer pNum = null;
        try {
            pNum = createStaTaskManager.getLFPiCiValueByKey(source);
        } catch (Exception e) {
            pNum = 10;
        }

        while (true) {
            Long batchNo = idsManagerProxy.findBatchNo();
            Integer updateCount = idsManagerProxy.updateMsgOutboundOrderBatchNo2(batchNo, source, pNum);
            if (updateCount == null || updateCount < pNum) {
                break;
            }
        }

        if (log.isInfoEnabled()) {
            log.info("IDSInterfaceDaemonTask setSalesOrderBatchTask " + source + " batch set end");
        }
    }

    
    
    //设置退货入库批次
    public void setCreateRtnOrderBatchTask() {
        log.info("IDSInterfaceDaemonTask setCreateRtnOrderBatchTask batch set start");
        Integer pNum = null;
        try {
            pNum = createStaTaskManager.getChooseOptionByCodeKey("num","100");
        } catch (Exception e) {
            pNum = 10;
        }
        while (true) {
            String batchNo = idsManagerProxy.queryBatchcodeByOrderQueue();
            Integer updateCount = idsManagerProxy.updateQstaBatchCodeByOuid(batchNo, pNum);
            if (updateCount == null || updateCount < pNum) {
                break;
            }
        }
            log.info("IDSInterfaceDaemonTask setCreateRtnOrderBatchTask  batch set end");
    }


    
    /*
     * 创建退货入库单
     */
    public void createRtnOrderDaemon() {
         log.info("IDSInterfaceDaemonTask createRtnOrderDaemon start");
         List<String> batchNoList = idsManagerProxy.findBatchCodeByDetial();
         log.info("IDSInterfaceDaemonTask createRtnOrderDaemon "+ " , pool :size :{}", batchNoList.size());
        for (String batchNo : batchNoList) {
            try {
                Thread thread = new IdsThread2(batchNo);
                threadPoolService.executeThread("createRtnOrderDaemon", thread);
            } catch (Exception e) {
                log.error("sendSalesOrderToLFTask is error:", e);
            }


        }
        threadPoolService.waitToFinish("createRtnOrderDaemon");
        if (log.isInfoEnabled()) {
            log.info("IDSInterfaceDaemonTask createRtnOrderDaemon end");
        }
    }
    
    
    
    
    
    

    /**
     * 预售订单 发货单释放 同步立峰2
     * 
     * @param source
     */
//    public void sendPreSalesOrderToLFTask2(String source) {
//
//        if (log.isInfoEnabled()) {
//            log.info("sendPreSalesOrderToLFTask start..." + source);
//        }
//        IdsServerInformation idsServerInformation = idsManagerProxy.findServerInformationBySource(source);
//        List<AdvanceOrderAddInfo> list;
//        Integer threadPoolQty = null;// 线程数
//        do {
//            try {
//                threadPoolQty = createStaTaskManager.getSystemThreadValueByKeyAndDes(source + "Pre", true);// 创建线程池
//            } catch (Exception e) {
//                threadPoolQty = 1;// 如果没有配置默认为1
//            }
//            ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
//            ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
//            // 待处理订单列表 预售
//            list = createStaTaskManager.findPreSalesOrder(source);
//            if (log.isInfoEnabled()) {
//                log.info("sendPreSalesOrderToLFTask thread pool size : {},todo list size :{}" + source, threadPoolQty, list.size());
//            }
//            // 分线程通知
//            for (AdvanceOrderAddInfo order : list) {
//                try {
//                    PreIdsThread t = new PreIdsThread(source, order.getId(), idsServerInformation);
//                    tx.execute(t);
//                } catch (Exception e) {
//                    log.error("", e);
//                }
//                while (true) {
//                    long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
//                    if (todoTotal >= 1000) {
//                        try {
//                            Thread.sleep(500L);
//                            log.debug("sendPreSalesOrderToLFTask, thread todoTotal is " + todoTotal + "_" + source);
//                        } catch (InterruptedException e) {
//                            log.error("sendPreSalesOrderToLFTask sleep error" + "_" + source);
//                        }
//                    } else {
//                        break;
//                    }
//                }
//            }
//            tx.shutdown();
//            boolean isFinish = false;
//            while (!isFinish) {
//                isFinish = pool.isTerminated();
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    log.error("sendPreSalesOrderToLFTask majorThread error" + "_" + source);
//                }
//            }
//        } while (list.size() >= 500);
//        if (log.isInfoEnabled()) {
//            log.info("sendPreSalesOrderToLFTask end..." + "_" + source);
//        }
//    }

    
}
