package com.jumbo.wms.newInventoryOccupy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.task.ThreadPoolService;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.mq.MessageConfigManager;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.thread.ThreadUtilManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.config.ThreadConfig;
import com.jumbo.wms.model.pickZone.OcpInvConstants;
import com.jumbo.wms.model.system.ChooseOption;



public class InventoryOccupyThreadPool {
    protected static final Logger log = LoggerFactory.getLogger(InventoryOccupyThreadPool.class);
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private NewInventoryOccupyManager newInventoryOccupyManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private ThreadPoolService threadPoolService;
    @Autowired
    private MessageConfigManager messageConfigManager;
    @Autowired
    private ThreadUtilManager threadUtilManager;

    public void staBatchHandle() {
        if (log.isInfoEnabled()) {
            log.info("InventoryOccupyThreadPool.staBatchHandle....Enter");
        }
        // 获取查询相关的常量配置
        ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH, OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_STA_BATCH_LIMIT);
        Long amount = Long.parseLong(co.getOptionValue());
        // 修改一批订单为占用批占用中
        String sbc = getStaBatchCode();
        Integer count = wareHouseManager.updateStaOcpBatchCode(sbc, amount);
        if (count != null && count != 0) {
            majorThread(sbc);
        }
        if (log.isInfoEnabled()) {
            log.info("InventoryOccupyThreadPool.staBatchHandle....end");
        }
    }

    public void majorThread(String StaOcpBatchCode) {
        if (log.isInfoEnabled()) {
            log.info("InventoryOccupyThreadPool.majorThread....Enter");
        }
        // 统计此批次订单中有多少仓库
        List<Long> ouList = wareHouseManager.findOuIdByOcpBatchCode(StaOcpBatchCode);
        // 获取批次相关的常量配置
        // ChooseOption co =
        // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH,
        // OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT);
        // Integer amount = Integer.parseInt(co.getOptionValue());

        // 根据参数创建线程池
        // ExecutorService pool = Executors.newFixedThreadPool(amount);
        // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
        for (Long ouId : ouList) {
            // 每个仓库一个线程
            CreateOcpOrderThread t =
                    new CreateOcpOrderThread(ouId, StaOcpBatchCode, "new", "thread ocp inv,ou :" + ouId, chooseOptionManager, newInventoryOccupyManager, wareHouseManager, sequenceManager, wareHouseManagerProxy, threadPoolService, messageConfigManager,
                            threadUtilManager);
            Thread tt = new Thread(t);
            // tx.execute(t);
            threadPoolService.executeThread(OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT, tt);
        }
        // tx.shutdown();
        // boolean isFinish = false;
        // while (!isFinish) {
        // isFinish = pool.isTerminated();
        // try {
        // Thread.sleep(5 * 1000L);
        //
        // } catch (InterruptedException e) {
        // // e.printStackTrace();
        // if (log.isErrorEnabled()) {
        // log.error("CreateOcpOrderThread-InterruptedException", e);
        // }
        // }
        // }
        threadPoolService.waitToFinish(OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT);
        if (log.isInfoEnabled()) {
            log.info("InventoryOccupyThreadPool.majorThread....end");
        }
    }

    /**
     * 获取此批订单的批次号
     * 
     * @return
     */
    public String getStaBatchCode() {
        Date date = new Date();
        Long time = date.getTime();
        return "OBC" + time.toString();
    }


    /** ........................新占用库存........................ **/

    /**
     * 占用库存定时任务
     */
    public void newOcpInv() {
        if (log.isInfoEnabled()) {
            log.info("newOcpInv start..........");
        }
        // 获取占用库存系统配置
        ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH, OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT);
        // Integer amount = Integer.parseInt(co.getOptionValue()); // 仓库线程数
        Integer mine = co.getSortNo(); // 判断仓库是否执行过的 分钟数
        // ExecutorService pool = Executors.newFixedThreadPool(amount);// 根据参数创建线程池
        // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
        Constants.WAREHOUSE_OCP_TIME_MAP.clear(); // 初始化执行时间列表
        // 查询所有非外包仓仓库
        List<Long> idList = wareHouseManager.findAllWarehouseByExcludeVmi();
        for (Long ouId : idList) {
            CreateOcpOrderThread t =
                    new CreateOcpOrderThread(ouId, null, null, "thread ocp inv,ou :" + ouId, chooseOptionManager, newInventoryOccupyManager, wareHouseManager, sequenceManager, wareHouseManagerProxy, threadPoolService, messageConfigManager, null);// 线程启动
            // tx.execute(t);
            Thread d = new Thread(t);
            threadPoolService.executeThread(OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT, d);
        }

        // 继续遍历执行成功的仓库
        Map<Long, Date> tempMap = Constants.WAREHOUSE_OCP_TIME_MAP;
        for (Long id : tempMap.keySet()) {
            // 判断当前时间减2分钟，是否在执行时间之前。 是：则跳出等待， 否则继续执行
            Boolean isContinue = wareHouseManager.ocpIsWaitByOcpStatus(mine, tempMap.get(id));
            if (!isContinue) {
                CreateOcpOrderThread t =
                        new CreateOcpOrderThread(id, null, null, "thread ocp inv,no ou", chooseOptionManager, newInventoryOccupyManager, wareHouseManager, sequenceManager, wareHouseManagerProxy, threadPoolService, messageConfigManager, threadUtilManager); // 线程启动
                // tx.execute(t);
                Thread d = new Thread(t);
                threadPoolService.executeThread(OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT, d);
            } else {
                if (log.isInfoEnabled()) {
                    log.info(id + " break loop again..........");
                    continue;
                }
            }
        }

        // while (true) {
        // try {
        // Thread.sleep(10000L); // 主线程休眠10秒
        // if (log.isInfoEnabled()) {
        // log.info("thread sleep 10 second..........");
        // }
        // long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();// 判断线程是否都结束
        // if (todoTotal > 0) {
        // // 继续遍历执行成功的仓库
        // Map<Long, Date> tempMap = Constants.WAREHOUSE_OCP_TIME_MAP;
        // for (Long id : tempMap.keySet()) {
        // // 判断当前时间减2分钟，是否在执行时间之前。 是：则跳出等待， 否则继续执行
        // Boolean isContinue = wareHouseManager.ocpIsWaitByOcpStatus(mine, tempMap.get(id));
        // if (!isContinue) {
        // CreateOcpOrderThread t = new CreateOcpOrderThread(id, null, null,
        // "thread ocp inv,no ou"); // 线程启动
        // tx.execute(t);
        // } else {
        // if (log.isInfoEnabled()) {
        // log.info(id + " break loop again..........");
        // continue;
        // }
        // }
        // }
        // } else {
        // break;
        // }
        // } catch (Exception e) {
        // log.error("newOcpInv is error:", e);
        // break;
        // }
        // }
        // ThreadPoolUtil.closeThreadPool(tx); // 关闭线程池
        threadPoolService.waitToFinish(OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT);
        if (log.isInfoEnabled()) {
            log.info("newOcpInv end..........");
        }
    }

    /**
     * 按仓占用库存
     * 
     * @param whId
     */
    public void newOcpInvByWhId(String whCode) {
        // 按仓占用库存配置
        String threadCode = OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT + whCode;
        ThreadConfig config = threadUtilManager.getByCodeAndSysKey(threadCode, "DEAMON");
        if (config == null) {
            threadCode = OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT; // 如为空，就走默认配置
        }
        
        List<Long> idList = new ArrayList<Long>();
        if (whCode.equals("DEFAULT")) {
            idList = wareHouseManager.findAllWarehouseByExcludeVmiNotTask(); // 通用定时任务的仓库列表
        } else {
            Long ouId = Long.parseLong(whCode); // 单个定时任务
            idList.add(ouId);
        }
        log.info("newOcpInvByWhId is start:" + whCode);
        // 获取占用库存系统配置
        for (Long ouId : idList) {
            CreateOcpOrderThread t =
                    new CreateOcpOrderThread(ouId, null, null, "thread ocp inv,ou :" + ouId, chooseOptionManager, newInventoryOccupyManager, wareHouseManager, sequenceManager, wareHouseManagerProxy, threadPoolService, messageConfigManager,
                            threadUtilManager);// 线程启动
            Thread tt = new Thread(t);
            threadPoolService.executeThread(threadCode, tt);
        }
        threadPoolService.waitToFinish2(threadCode);
        log.info("newOcpInvByWhId is end:" + whCode);
    }
}
