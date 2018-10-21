package com.jumbo.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;

public class AutoOutboundTask {

    protected static final Logger log = LoggerFactory.getLogger(AutoOutboundTask.class);

    @Autowired
    private CreateStaTaskManager createStaTaskManager;

    @Autowired
    private TransOlManager transOlManager;
    
    @Autowired
    private ThreadPoolService threadPoolService;

    public void AutoOutbound() {
        if (log.isInfoEnabled()) {
            log.info("AutoOutbound start");
        }
        Integer rowNum = transOlManager.getSystemThreadValueByKey(Constants.TEST_NUM);
        // List<StockTransApplication> list = transOlManager.findStaByStatus(rowNum);
//        Integer num = createStaTaskManager.getSystemThreadValueByKeyAndDes("AutoOutbound", false);// 创建线程池
//        ExecutorService pool = Executors.newFixedThreadPool(num);
//        ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
        try {
            AutoOutboundTaskThread t = new AutoOutboundTaskThread(rowNum);
//            tx.execute(t);
            threadPoolService.executeThread("AutoOutbound", t);
        } catch (Exception e) {
            log.error("AutoOutbound is error:", e);
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
//                    log.error("AutoOutboundTaskThread", e);
//                }
//            }
//        }
        threadPoolService.waitToFinish("AutoOutbound");
        if (log.isInfoEnabled()) {
            log.info("AutoOutbound end");
        }
    }
}
