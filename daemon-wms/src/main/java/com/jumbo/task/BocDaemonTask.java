package com.jumbo.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;

public class BocDaemonTask {

    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    private ThreadPoolService threadPoolService;
    protected static final Logger log = LoggerFactory.getLogger(BocDaemonTask.class);


    public void executeMsgRtnOutbound(String source) {
        if (log.isInfoEnabled()) {
            log.info("executeMsgRtnOutbound start, {}", source);
        }
        Integer rowNum = transOlManager.getSystemThreadValueByKey(Constants.RTN_MSG_NUM);
        List<MsgRtnOutbound> list = transOlManager.findAllVmiMsgOutboundByRowNum(source, rowNum);
//        Integer num = createStaTaskManager.getSystemThreadValueByKeyAndDes(source, false);// 创建线程池
//        ExecutorService pool = Executors.newFixedThreadPool(num);
//        ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
        try {
            Thread t = new BocDaemonTaskThread(list);
//            tx.execute(t);
            threadPoolService.executeThread("ExecuteMsgRtnOutbound_" + source, t);
        } catch (Exception e) {
            log.error("msgUnLocked is error:" + source, e);
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
//                    log.error("BocDaemonTaskThread", e);
//                }
//            }
//        }
        threadPoolService.waitToFinish("ExecuteMsgRtnOutbound_" + source);
        if (log.isInfoEnabled()) {
            log.info("executeMsgRtnOutbound end, {}", source);
        }
    }
}
