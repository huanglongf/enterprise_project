package com.jumbo.task.test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.task.ThreadPoolService;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.system.ChooseOption;

/**
 * 压力测试定时任务
 * 
 * @author sjk
 * 
 */
@Service("whOutboundTask")
public class WhOutboundTask {
    private static final Logger logger = LoggerFactory.getLogger(WhOutboundTask.class);

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private ThreadPoolService threadPoolService;

    /**
     * 仓库自动出库
     */
    public void whOutbound() {
        logger.info("wh outbound start");
        while (true) {
//            ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD, ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_TEST_OUTBOUND);
//            Integer threadPoolQty = 15;
//            if (co != null) {
//                threadPoolQty = Integer.parseInt(co.getOptionValue());
//            }
            List<BigDecimal> staIds = wareHouseManager.testFindOcpStaIds();
//            ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
//            ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            for (BigDecimal id : staIds) {
                try {
                    // 放入线程
                    Thread t = new WhOutboundThread(id.longValue());
//                    tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_TEST_OUTBOUND, t);
                } catch (Exception e) {
                    logger.info("outbound error,id:{}", id, e);
                }
//                while (true) {
//                    // 判断线程池中待执行数，如多余1000，则休眠不再增加，否则直接将线程加入线程池
//                    long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
//                    if (todoTotal >= 1000) {
//                        try {
//                            Thread.sleep(500L);
//                            logger.error("outbound thread pool input sleep,thread todoTotal:" + todoTotal);
//                        } catch (InterruptedException e) {
//                            logger.error("outbound thread pool input error", e);
//                        }
//                    } else {
//                        break;
//                    }
//                }
            }
            // 关闭线程池
//            tx.shutdown();
//            boolean isFinish = false;
//            while (!isFinish) {
//                isFinish = pool.isTerminated();
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    logger.error("InterruptedException majorThread error");
//                }
//            }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_TEST_OUTBOUND);
            if (staIds.size() <= 30000) {
                break;
            }
        }
        logger.info("wh outbound end");
    }

}
