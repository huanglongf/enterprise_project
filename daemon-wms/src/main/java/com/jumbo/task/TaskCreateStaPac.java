package com.jumbo.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.ThreadPoolUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;

/**
 * cheng.su
 * 
 * @author Administrator
 * 
 */
public class TaskCreateStaPac {
    protected static final Logger log = LoggerFactory.getLogger(TaskCreateStaPac.class);
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    private ThreadPoolService threadPoolService;

    /**
     * 设置是否可以创单标识位<br/>
     * 1、根据仓库查询出固定条数，设置开始标识，计算默认发货仓库 <br/>
     * 2、按照默认发货仓库进行分线程设置标识位，标识为true该单结束；false汇总执行3<br/>
     */
    public void setFlagForOrderToCreatePac() {
        if (log.isInfoEnabled()) {
            log.info("TaskCreateSta setFlagForOrderToCreatePac ...Enter");
        }

        // a.不管什么逻辑，首先删除各个仓库初始库存，根据订单重新初始化
        createStaTaskManager.deleteInventoryForOuId();
        // 根据仓库查询出固定条数，计算默认发货仓。
        setBeginFlagForOrderPac();
        // 根据仓库初始化库存
        initInventoryByOuId();
        Boolean flag = true;
        Integer limit = createStaTaskManager.getSystemThreadValueByKey(Constants.DO_WHILE_NUM);
        int num = 0;
        do {// 当没有单子需要设置标识，或者线程抛出异常，但是循环达到一定次数（最大深度）
            setFlagForOderPac();// 计算是否可以创单逻辑
            flag = createStaTaskManager.getAllNeedSetFlagOrderPac();// 获取是否还有未设置标识的单据
            num++;
        } while (flag && num <= limit);
        if (log.isInfoEnabled()) {
            log.info("CreateStaTask setFlagForOrderToCreate ...End");
        }
        // c.不管什么逻辑，删除各个仓库初始库存，根据订单重新初始化
        createStaTaskManager.deleteInventoryForOuId();
    }

    private void setFlagForOderPac() {
//        Integer num = createStaTaskManager.getSystemThreadValueByKey(Constants.WAREHOUSE_THREAD_PAC);
//        ExecutorService pool = Executors.newFixedThreadPool(num);
        List<Long> idList = createStaTaskManager.getNeedExecuteWarehousePac();
        if (idList != null && idList.size() > 0) {
            // 根据仓库分线程实现标志位设定
            for (Long id : idList) {
                SetFlagThreadPac t = new SetFlagThreadPac("canFlag:" + id);
                t.setId(id);
                t.setName("canFlag");
                Thread t1 = new Thread(t);
//                pool.execute(t1);
                threadPoolService.executeThread(Constants.WAREHOUSE_THREAD_PAC, t1);
            }
        }
//        pool.shutdown();
//        boolean isFinish = false;
//        while (!isFinish) {
//            isFinish = pool.isTerminated();
//            try {
//                Thread.sleep(5 * 1000L);
//            } catch (InterruptedException e) {
//                // e.printStackTrace();
//                if (log.isErrorEnabled()) {
//                    log.error("SetFlagThreadPac", e);
//                }
//            }
//        }
        threadPoolService.waitToFinish(Constants.WAREHOUSE_THREAD_PAC);
    }

    private void setBeginFlagForOrderPac() {
//        Integer num = createStaTaskManager.getSystemThreadValueByKey(Constants.WAREHOUSE_THREAD_PAC);
//        ExecutorService pool = Executors.newFixedThreadPool(num);
        List<Long> idList = createStaTaskManager.getBeginFlagForOrderPac();
        if (idList != null && idList.size() > 0) {
            // 根据仓库分线程实现标志位设定
            for (Long id : idList) {
                SetFlagThreadPac t = new SetFlagThreadPac("To_beginFlag:" + id);
                t.setId(id);
                t.setName("beginFlag");
                Thread t1 = new Thread(t);
//                ThreadPoolUtil.executeThread((ThreadPoolExecutor) pool, t1);
                threadPoolService.executeThread(Constants.WAREHOUSE_THREAD_PAC, t1);
            }
        }
//        ThreadPoolUtil.closeThreadPool((ThreadPoolExecutor) pool);
        threadPoolService.waitToFinish(Constants.WAREHOUSE_THREAD_PAC);
    }

    /**
     * 创单定时任务
     */
    public void createStaAfterSetFlagPac() {
        log.info("Daemon Taskcreate sta Task begin.......");
//        Integer num = createStaTaskManager.getSystemThreadValueByKey(Constants.CREATE_STA_THREAD_PAC);
//        ExecutorService pool = Executors.newFixedThreadPool(num);
        List<Long> idList = createStaTaskManager.getAllOrderToCreatePac();
        for (Long id : idList) {
            SetFlagThreadPac t = new SetFlagThreadPac("createSta:" + id);
            t.setId(id);
            t.setName("createSta");
            Thread t1 = new Thread(t);
//            ThreadPoolUtil.executeThread((ThreadPoolExecutor) pool, t1);
            threadPoolService.executeThread(Constants.CREATE_STA_THREAD_PAC, t1);
        }
//        ThreadPoolUtil.closeThreadPool((ThreadPoolExecutor) pool);
        threadPoolService.waitToFinish(Constants.CREATE_STA_THREAD_PAC);
    }

    /**
     * 根据需要初始化的库存分店铺初始化
     */
    private void initInventoryByOuId() {
//        Integer num = createStaTaskManager.getSystemThreadValueByKey(Constants.OUID_THREAD);
//        ExecutorService pool = Executors.newFixedThreadPool(num);
        // 此处查询店铺列表，并创建mongoDB中对应的库存表和索引
        List<String> warehouseCodelist = createStaTaskManager.findInitInventoryWarehouseCodeList();
        if (warehouseCodelist != null && warehouseCodelist.size() > 0) {
            for (String warehouseCode : warehouseCodelist) {
                SetFlagThreadPac t = new SetFlagThreadPac("InitInv_by warehouseCode:" + warehouseCode);
                t.setWarehouseCode(warehouseCode);
                t.setName("InitInv_by warehouseCode");
                Thread t1 = new Thread(t);
//                pool.execute(t1);
                threadPoolService.executeThread(Constants.OUID_THREAD, t1);
            }
        }
//        pool.shutdown();
//        boolean isFinish = false;
//        while (!isFinish) {
//            isFinish = pool.isTerminated();
//            try {
//                Thread.sleep(5 * 1000L);
//            } catch (InterruptedException e) {
//                // e.printStackTrace();
//                if (log.isErrorEnabled()) {
//                    log.error("SetFlagThreadPac", e);
//                }
//            }
//        }
        threadPoolService.waitToFinish(Constants.OUID_THREAD);
    }
}
