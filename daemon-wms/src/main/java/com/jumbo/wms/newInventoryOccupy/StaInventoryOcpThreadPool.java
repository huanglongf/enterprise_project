package com.jumbo.wms.newInventoryOccupy;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.task.ThreadPoolService;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.thread.ThreadUtilManager;
import com.jumbo.wms.manager.warehouse.AreaOcpStaManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.MongoDBInventoryOcp;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.config.ThreadConfig;
import com.jumbo.wms.model.pickZone.OcpInvConstants;



/**
 * @author lihui
 * 
 * @createDate 2015年7月14日 下午6:00:55
 */
public class StaInventoryOcpThreadPool {
    protected static final Logger log = LoggerFactory.getLogger(StaInventoryOcpThreadPool.class);
    public static final long TASK_LOCK_TIMEOUT = Constants.TASK_LOCK_TIMEOUT;
    public static final String TASK_LOCK_VALUE = Constants.TASK_LOCK_VALUE;
    
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private NewInventoryOccupyManager newInventoryOccupyManager;
    @Autowired
    private ThreadPoolService threadPoolService;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private AreaOcpStaManager areaOcpStaManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private ThreadUtilManager threadUtilManager;

    public void staHandle() {
        if (log.isInfoEnabled()) {
            log.info("staHandle start..........");
        }
        // 获取批次相关的常量配置
        // ChooseOption co =
        // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH,
        // OcpInvConstants.OCP_THREAD_COUNT);
        // Integer amount = Integer.parseInt(co.getOptionValue());

        List<Long> wooIdList = newInventoryOccupyManager.findOcpOrderIdsByStatus(null, OcpInvConstants.OCP_ORDER_STATUS_OCPOVER);
        if (wooIdList != null && wooIdList.size() > 0) {
            // ExecutorService pool = Executors.newFixedThreadPool(amount);
            // 根据参数创建线程池
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            for (Long wooId : wooIdList) {
                // 每个占用批一个线程
                StaOcpBatchThread t = new StaOcpBatchThread(wooId, newInventoryOccupyManager, wareHouseManagerProxy);
                Thread d = new Thread(t);
                threadPoolService.executeThread(OcpInvConstants.OCP_THREAD_COUNT, d);
                // ThreadPoolUtil.executeThread(tx, d, 25);
            }
            // ThreadPoolUtil.closeThreadPool(tx);// 停止往线程池里放线程
            threadPoolService.waitToFinish(OcpInvConstants.OCP_THREAD_COUNT);
        }
        if (log.isInfoEnabled()) {
            log.info("staHandle end..........");
        }
    }

    /**
     * 占用库存补偿机制 半小时一次
     */
    public void staHandleMarkUp() {
        if (log.isInfoEnabled()) {
            log.info("staHandleMarkUp start..........");
        }
        newInventoryOccupyManager.releaseAllOcpCode();
        if (log.isInfoEnabled()) {
            log.info("staHandleMarkUp end..........");
        }
    }


    private void initOcpMgDbInv(Long ouId) {
        log.info("isInitMongDBInv is start:" + ouId);
        areaOcpStaManager.deleteInventoryForAreaOcpInv(ouId); // MongDb删库跑路
        areaOcpStaManager.createMongDbTableForOcp(ouId); // MongDb建库
        List<String> areaList = areaOcpStaManager.findAllAreaCode(ouId);
        for (String area : areaList) {
            // 每区域一个线程
          /*  AreaOcpStaThread t = new AreaOcpStaThread(null, ouId, "intMongDb", area, areaOcpStaManager, threadPoolService, null, null);
            Thread d = new Thread(t);
            threadPoolService.executeThread(OcpInvConstants.AREA_OCP_THREAD_MONGDB, d);*/
            initmongDbInvByAreaCode(area, ouId, areaOcpStaManager);
        }
      //  threadPoolService.waitToFinish(OcpInvConstants.AREA_OCP_THREAD_MONGDB);// 停止往线程池里放线程
        threadPoolService.waitToFinish2(OcpInvConstants.AREA_OCP_THREAD_MONGDB_INV+"_"+ouId);// 停止往线程池里放线程
        log.info("isInitMongDBInv is end:" + ouId);
    }

    
    /**
     * 初始化mongDb库存
     * 
     * @param areaCode
     */
    private void initmongDbInvByAreaCode(String areaCode, Long ouId, AreaOcpStaManager areaOcpStaManager) {//run
        log.info("initmongDbInvByAreaCode is start " + areaCode);
        List<MongoDBInventoryOcp> mongDbList = areaOcpStaManager.findMongDbInvList(areaCode, ouId);
        // 查询 扣减SKU
        try {
            List<MongoDBInventoryOcp> mongDbListMinus = areaOcpStaManager.findMongDbInvListMinus(areaCode, ouId);
            if (mongDbListMinus != null && mongDbListMinus.size() > 0) {
                for (MongoDBInventoryOcp minus : mongDbListMinus) {
                    for (MongoDBInventoryOcp ocp : mongDbList) {
                        if (StringUtils.equals(minus.getOuId() + "", ocp.getOuId() + "") && StringUtils.equals(minus.getOwner(), ocp.getOwner()) && StringUtils.equals(minus.getSkuId() + "", ocp.getSkuId() + "") && minus.getAvailQty() > 0) {
                            ocp.setAvailQty(ocp.getAvailQty() - minus.getAvailQty());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("minus mongoinv failed!---" + areaCode + "---" + ouId);
        }
        for (MongoDBInventoryOcp ocp : mongDbList) {
            MongDbAddInvThread t = new MongDbAddInvThread(ouId, ocp, areaOcpStaManager);
            Thread d = new Thread(t);
            threadPoolService.executeThread(OcpInvConstants.AREA_OCP_THREAD_MONGDB_INV+"_"+ouId, d);
        }
        log.info("initmongDbInvByAreaCode is end " + areaCode);
    }
    
    
    /**
     * 查询队列数据，计算区域库存All
     */
   // @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void queryStaToOcpAeraInvAll(Long ouId) {
        log.error("queryStaToOcpAeraInvAll1-1:"+ouId);
        queryStaToOcpAeraInv(ouId);
        log.error("queryStaToOcpAeraInvAll1-2:"+ouId);
    }

    /**
     * 查询队列数据，计算区域库存
     * 
     * @param ouId
     */
    public void queryStaToOcpAeraInv(Long ouId) {
        log.info("queryStaToOcpAeraInv is start.........." + ouId);
        Warehouse wh = wareHouseManager.getByOuId(ouId);
        // 判断是否需要计算
        if (wh.getIsAreaOcpInv() != null) {
            if (!wh.getIsAreaOcpInv()) {
                // 不需要计算直接返回
                return;
            }
        } else {
            // 为空直接返回
            return;
        }
        // 仓库线程数配置
        String threadCode = OcpInvConstants.AREA_OCP_THREAD_STA_COUNT + ouId;
        ThreadConfig config = threadUtilManager.getByCodeAndSysKey(OcpInvConstants.AREA_OCP_THREAD_STA_COUNT + ouId, "DEAMON");
        if (config == null) {
            threadCode = OcpInvConstants.AREA_OCP_THREAD_STA_COUNT; // 如为空，就走默认配置
        }
        Boolean isInitMongDBInv = true; // 是否初始化Mongdb库存
        List<String> typeList = newInventoryOccupyManager.findOcpAreaByOuId(ouId);

        for (String pickType : typeList) {
            while (true) {
                List<Long> staList = newInventoryOccupyManager.setOcpCodeForSta(null, null, ouId, 20000, pickType, null, null);// 查询1W单
                if (staList.size() > 0) {
                    /**
                     * 初始化MongDb库存， by 区域多线程， 再by库存行多线程
                     */
                    if (isInitMongDBInv) {
                        initOcpMgDbInv(ouId);
                        isInitMongDBInv = false; // 每次任务只初始化一次
                    }
                    /**
                     * 作业单计算仓库区域
                     */
                    for (Long staId : staList) {
                        log.info("AreaOcpStaThread start:" + staId);
                        // 每单一个线程
                        AreaOcpStaThread t = new AreaOcpStaThread(staId, ouId, "ocpSta", null, areaOcpStaManager, threadPoolService, null, null);
                        Thread d = new Thread(t);
                        threadPoolService.executeThread(threadCode, d);
                    }
                }
                threadPoolService.waitToFinish2(threadCode);// 停止往线程池里放线程
                if (staList.size() < 20000) {
                    break;
                }
            }

        }
        log.info(" is end.........." + ouId);
    }
}
