package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.ZkRootConstants;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.manager.outbound.AdCheckManager;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.system.ChooseOption;

/**
 * 
 * @author jinlong.ke
 * @date 2016年3月7日下午6:35:43<br/>
 *       Edit 2016-03-07 <br/>
 *       系统加标志位，用以区分两套mongoDB逻辑，mongoType:1 原始逻辑 2 单批初始化库存，之后再删除
 * 
 */
public class CreateStaTask {
    protected static final Logger log = LoggerFactory.getLogger(CreateStaTask.class);
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    private AdCheckManager adCheckManager;
    @Autowired
    private ThreadPoolService threadPoolService;

    /**
     * 设置是否可以创单标识位<br/>
     * 1、查询固定条目单据数，设置开始标识，计算默认发货仓库 <br/>
     * 2、按照默认发货仓库进行分线程设置标识位，标识为true该单结束；false汇总执行3<br/>
     * 3、重新计算标识位为false的单据默认仓，转2;<br/>
     */
    public void setFlagForOrderToCreate() {
        if (log.isInfoEnabled()) {
            log.info("CreateStaTask setFlagForOrderToCreate ...Enter");
        }
        // 1、取固定条目数据设置开始标志，计算默认发货仓。
        // a.不管什么逻辑，首先删除特殊逻辑的初始库存，根据订单重新初始化
        createStaTaskManager.deleteInventoryForOnceUse();
        if (log.isInfoEnabled()) {
            log.info("CreateStaTask setFlagForOrderToCreate ...del inv end,set flag start.");
        }
        createStaTaskManager.setBeginFlagForOrder();
        if (log.isInfoEnabled()) {
            log.info("CreateStaTask setFlagForOrderToCreate ...set flag end,init inv start.");
        }
        // 根据店铺分线程初始化库存
        initInventoryByOwner();
        if (log.isInfoEnabled()) {
            log.info("CreateStaTask setFlagForOrderToCreate ...init inv end,set def wh start.");
        }
        // 2、查询所有设了标志位，但是没有设置默认发货仓的单子，进行设置默认发货仓
        List<WmsSalesOrderQueue> wl = createStaTaskManager.getAllOrderHaveFlag();
        if (wl != null && wl.size() > 0) {
            setDefaultWarehouse(wl);
        }
        if (log.isInfoEnabled()) {
            log.info("CreateStaTask setFlagForOrderToCreate ...set def wh end,exe order start.");
        }
        // 判断直连开关 是否新逻辑还是老逻辑
        if (ZkRootConstants.createRoot) {
            // 新逻辑 bin.hu
            setFlagForOrderNew();
            // System.out.println("OK");
        } else {
            // 老逻辑
            Boolean flag = true;
            Integer limit = createStaTaskManager.getSystemThreadValueByKey(Constants.DO_WHILE_NUM);
            int num = 0;
            do {// 当没有单子需要设置标识，或者线程抛出异常，但是循环达到一定次数（最大深度）
                setFlagForOder();// 计算是否可以创单逻辑，一次分仓不够则进行多次分仓
                flag = createStaTaskManager.getAllNeedSetFlagOrder();// 获取是否还有未设置标识的单据
                num++;
                if (log.isInfoEnabled()) {
                    log.info("CreateStaTask setFlagForOrderToCreate ...exe order count :{}", num);
                }
            } while (flag && num <= limit);
            if (log.isInfoEnabled()) {
                log.info("CreateStaTask setFlagForOrderToCreate ...End");
            }
        }
        // c.不管什么逻辑，首先删除特殊逻辑的初始库存，根据订单重新初始化
        createStaTaskManager.deleteInventoryForOnceUse();
    }


    /**
     * 直连计算库存新逻辑
     */
    private void setFlagForOrderNew() {
        // 获取所有本次定时内需要处理的单据
        List<WmsSalesOrderQueue> soList = createStaTaskManager.getWmsSalesOrderQueueByBeginFlag(1);
        // for (WmsSalesOrderQueue oo : soList) {
        // createStaTaskManager.setFlagToOrderNew(oo.getId());
        // createStaTaskManager.createTomsOrdersendToMq(oo.getId());
        // }
        for (WmsSalesOrderQueue oo : soList) {
            String threadName = "To_warehouse:" + oo.getId();
            SetFlagForOrderThread t = new SetFlagForOrderThread();
            t.setIds(oo.getId());
            Thread t1 = new Thread(t, threadName);
            threadPoolService.executeThread(Constants.FLAGFORORDER, t1);
        }
        threadPoolService.waitToFinish(Constants.FLAGFORORDER);
    }

    /**
     * 根据需要初始化的库存分店铺初始化
     */
    private void initInventoryByOwner() {
        // Integer num = createStaTaskManager.getSystemThreadValueByKey(Constants.OWNER_THREAD);
        // ExecutorService pool = Executors.newFixedThreadPool(num);
        // 此处查询店铺列表，并创建mongoDB中对应的库存表和索引
        List<String> ownerList = createStaTaskManager.findInitInventoryOwnerList();
        if (ownerList != null && ownerList.size() > 0) {
            for (String owner : ownerList) {
                String threadName = "InitInv_by Owner:" + owner;
                SetFlagThread t = new SetFlagThread(threadName);
                t.setOwner(owner);
                t.setName("initInv");
                Thread t1 = new Thread(t, threadName);
                // pool.execute(t1);
                threadPoolService.executeThread(Constants.OWNER_THREAD, t1);
            }
        }
        // pool.shutdown();
        // boolean isFinish = false;
        // while (!isFinish) {
        // isFinish = pool.isTerminated();
        // try {
        // Thread.sleep(5 * 1000L);
        // } catch (InterruptedException e) {
        // // e.printStackTrace();
        // if (log.isErrorEnabled()) {
        // log.error("SetFlagThread", e);
        // }
        // }
        // }
        threadPoolService.waitToFinish(Constants.OWNER_THREAD);
    }

    private void setDefaultWarehouse(List<WmsSalesOrderQueue> wl) {
        if (log.isInfoEnabled()) {
            log.info("CreateStaTask setDefaultWarehouse ...Enter");
        }
        // Integer num = createStaTaskManager.getSystemThreadValueByKey(Constants.ORDER_THREAD);
        // ExecutorService pool = Executors.newFixedThreadPool(num);
        for (WmsSalesOrderQueue w : wl) {
            String threadName = "Set_warehouse:" + w.getId();
            SetFlagThread t = new SetFlagThread(threadName);
            t.setId(w.getId());
            t.setName("setWarehouse");
            Thread t1 = new Thread(t, threadName);
            // pool.execute(t1);
            threadPoolService.executeThread(Constants.ORDER_THREAD, t1);
        }
        // pool.shutdown();
        // boolean isFinish = false;
        // while (!isFinish) {
        // isFinish = pool.isTerminated();
        // try {
        // Thread.sleep(5 * 1000L);
        // } catch (InterruptedException e) {
        // // e.printStackTrace();
        // if (log.isErrorEnabled()) {
        // log.error("SetFlagThread", e);
        // }
        // }
        // }
        threadPoolService.waitToFinish(Constants.ORDER_THREAD);
        if (log.isInfoEnabled()) {
            log.info("CreateStaTask setDefaultWarehouse ...End");
        }
    }



    private void setFlagForOder() {
        // Integer num = createStaTaskManager.getSystemThreadValueByKey(Constants.WAREHOUSE_THREAD);
        // ExecutorService pool = Executors.newFixedThreadPool(num);
        List<Long> idList = createStaTaskManager.getNeedExecuteWarehouse();
        if (idList != null && idList.size() > 0) {
            // 根据仓库分线程实现标志位设定
            for (Long id : idList) {
                String threadName = "To_warehouse:" + id;
                SetFlagThread t = new SetFlagThread(threadName);
                t.setId(id);
                t.setName("ou");
                Thread t1 = new Thread(t, threadName);
                // pool.execute(t1);
                threadPoolService.executeThread(Constants.WAREHOUSE_THREAD, t1);
            }
        } else {
            // 所有最后需要分仓的单据启动一个新的线程
            String threadName = "To_warehouse:devideWarehouse";
            SetFlagThread t = new SetFlagThread(threadName);
            t.setName("ou");
            Thread t1 = new Thread(t, threadName);
            // pool.execute(t1);
            threadPoolService.executeThread(Constants.WAREHOUSE_THREAD, t1);
        }
        // pool.shutdown();
        // boolean isFinish = false;
        // while (!isFinish) {
        // isFinish = pool.isTerminated();
        // try {
        // Thread.sleep(5 * 1000L);
        // } catch (InterruptedException e) {
        // // e.printStackTrace();
        // if (log.isErrorEnabled()) {
        // log.error("SetFlagThread", e);
        // }
        // }
        // }
        threadPoolService.waitToFinish(Constants.WAREHOUSE_THREAD);
    }

    /**
     * 创单定时任务
     */
    public void createStaAfterSetFlag() {
        log.error("Daemon create sta Task begin.......");
        // Integer num =
        // createStaTaskManager.getSystemThreadValueByKey(Constants.CREATE_STA_THREAD);
        // ExecutorService pool = Executors.newFixedThreadPool(num);
        List<Long> idList = createStaTaskManager.getAllOrderToCreate();
        for (Long id : idList) {
            String threadName = "Create_sta:" + id;
            SetFlagThread t = new SetFlagThread(threadName);
            t.setId(id);
            t.setName("createSta");
            Thread t1 = new Thread(t, threadName);
            // pool.execute(t1);
            threadPoolService.executeThread(Constants.CREATE_STA_THREAD, t1);
        }
        // pool.shutdown();
        // boolean isFinish = false;
        // while (!isFinish) {
        // isFinish = pool.isTerminated();
        // try {
        // Thread.sleep(5 * 1000L);
        // } catch (InterruptedException e) {
        // // e.printStackTrace();
        // if (log.isErrorEnabled()) {
        // log.error("SetFlagThread", e);
        // }
        // }
        // }
        threadPoolService.waitToFinish(Constants.CREATE_STA_THREAD);
    }

    public void adCheckSendTrigger() {
        // Integer num =
        // createStaTaskManager.getSystemThreadValueByKey(Constants.SEND_AD_CHECK_THREAD);
        // ExecutorService pool = Executors.newFixedThreadPool(num);
        List<Long> idList = adCheckManager.getAllNeedConfirmCheckOrder();
        for (Long id : idList) {
            String threadName = "adCheck:" + id;
            SetFlagThread t = new SetFlagThread(threadName);
            t.setId(id);
            t.setName("adCheck");
            Thread t1 = new Thread(t, threadName);
            // pool.execute(t1);
            threadPoolService.executeThread(Constants.SEND_AD_CHECK_THREAD, t1);
        }
        // pool.shutdown();
        // boolean isFinish = false;
        // while (!isFinish) {
        // isFinish = pool.isTerminated();
        // try {
        // Thread.sleep(5 * 1000L);
        // } catch (InterruptedException e) {
        // // e.printStackTrace();
        // if (log.isErrorEnabled()) {
        // log.error("SetFlagThread", e);
        // }
        // }
        // }
        threadPoolService.waitToFinish(Constants.SEND_AD_CHECK_THREAD);
    }

    /**
     * 不校验库存丢MQ
     */
    // public void createStaNotCheckInvToMQ2() {
    // log.error("Daemon create createStaIsNotCheckInvToMQ Task begin.......");
    // List<Long> idList = createStaTaskManager.getAllOrderInvCheckCreate();
    // for (Long id : idList) {
    // String threadName = "IsNot_CheckInv:" + id;
    // SetFlagThread t = new SetFlagThread(threadName);
    // t.setId(id);
    // t.setName("IsNotCheckInv");
    // Thread t1 = new Thread(t, threadName);
    // threadPoolService.executeThread(Constants.NO_CHECK_INV_THREAD, t1);
    // }
    // threadPoolService.waitToFinish(Constants.NO_CHECK_INV_THREAD);
    // }

    /**
     * 不校验库存丢MQ(直连)
     */
    public void createStaNotCheckInvToMQ() {
        if (log.isInfoEnabled()) {
            log.info("outboudNoticePacSystenKeyDaemon start...");
        }
        List<Long> idList = null;
        do {
            idList = createStaTaskManager.getAllOrderInvCheckCreate();
            if (log.isInfoEnabled()) {
                log.info("createStaNotCheckInvToMQ thread pool size : todo list size :{}", idList.size());
            }
            // 分线程通知
            for (Long id : idList) {
                String threadName = "IsNot_CheckInv:" + id;
                SetFlagThread t = new SetFlagThread(threadName);
                t.setId(id);
                t.setName("IsNotCheckInv");
                Thread t1 = new Thread(t, threadName);
                threadPoolService.executeThread(Constants.NO_CHECK_INV_THREAD, t1);
            }
            threadPoolService.waitToFinish(Constants.NO_CHECK_INV_THREAD);
        } while (idList.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("createStaNotCheckInvToMQ end...");
        }
    }



    /**
     * 不校验库存丢MQ(非直连)
     */
    public void createStaNotCheckInvToMQByPac() {
        if (log.isInfoEnabled()) {
            log.info("createStaNotCheckInvToMQByPac start...");
        }
        List<Long> idList = null;
        do {
            idList = createStaTaskManager.getAllOrderInvCheckCreateByPac();
            if (log.isInfoEnabled()) {
                log.info("createStaNotCheckInvToMQByPac thread pool size : todo list size :{}", idList.size());
            }
            // 分线程通知
            for (Long id : idList) {
                try {
                    Thread t = new StaNotCheckInvToMQByPacThread(id);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_PACS_MQ_5, t);
                } catch (Exception e) {
                    log.error("createStaNotCheckInvToMQByPac", e);
                }
            }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_PACS_MQ_5);
        } while (idList.size() >= 500);
        if (log.isInfoEnabled()) {
            log.info("createStaNotCheckInvToMQByPac end...");
        }
    }

}
