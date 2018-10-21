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

public class MsgUnLockedTask {

    protected static final Logger log = LoggerFactory.getLogger(MsgUnLockedTask.class);


    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private ThreadPoolService threadPoolService;

    public void msgUnLocked() {
        Boolean flag = true;
//        Integer num = transOlManager.getSystemThreadValueByKey(Constants.MSG_UNLOCKED);// 创建线程池
        Integer rowNum = transOlManager.getSystemThreadValueByKey(Constants.MSG_UNLOCKED_NUM);
        while (flag) {
//            ExecutorService pool = Executors.newFixedThreadPool(num);
//            ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            List<Long> idList = transOlManager.findLockedOrder(rowNum);
            if (idList.size() < rowNum) {
                flag = false;
            }
            for (Long id : idList) {
                try {
                    Thread t = new MsgUnLockedTaskThread(id);
//                    tx.execute(t);
                    threadPoolService.executeThread(Constants.MSG_UNLOCKED, t);
                } catch (Exception e) {
                    log.error("msgUnLocked is error:" + id, e);
                }
//                while (true) {
//                    long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
//                    if (todoTotal >= 1000) {
//                        try {
//                            Thread.sleep(1000L);
//                        } catch (InterruptedException e) {
//                            log.error("msgUnLocked" + e);
//                        }
//                    } else {
//                        break;
//                    }
//                }
            }
//            tx.shutdown();
//            boolean isFinish = false;
//            while (!isFinish) {
//                isFinish = pool.isTerminated();
//                try {
//                    Thread.sleep(5 * 1000L);
//                } catch (InterruptedException e) {
//                    // e.printStackTrace();
//                    if (log.isErrorEnabled()) {
//                        log.error("MsgUnLockedTaskThread", e);
//                    }
//                }
//            }
            threadPoolService.waitToFinish(Constants.MSG_UNLOCKED);
            if (log.isInfoEnabled()) {
                log.info("msgUnLocked ...End");
            }
        }
    }

}
