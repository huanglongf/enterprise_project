package com.jumbo.wms.newInventoryOccupy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.task.ThreadPoolService;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.mq.MessageConfigManager;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.thread.ThreadUtilManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.pickZone.WhOcpOrderCommand;
import com.jumbo.wms.model.config.ThreadConfig;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.pickZone.OcpInvConstants;
import com.jumbo.wms.model.system.ChooseOption;


public class CreateOcpOrderThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(CreateOcpOrderThread.class);
    private ChooseOptionManager chooseOptionManager;
    private NewInventoryOccupyManager newInventoryOccupyManager;
    private WareHouseManager wareHouseManager;
    private SequenceManager sequenceManager;
    private MessageConfigManager messageConfigManager;
    // private Warehouse wh;

    // private String staOcpBatchCode;
    private Long ouId;
    private String flag;
    private WareHouseManagerProxy wareHouseManagerProxy;
    private Integer amount;
    private String threadName;
    private ThreadPoolService threadPoolService;
    private ThreadUtilManager threadUtilManager;

    public CreateOcpOrderThread(Long ouId, String staOcpBatchCode, String flag, String threadName, ChooseOptionManager chooseOptionManager, NewInventoryOccupyManager newInventoryOccupyManager, WareHouseManager wareHouseManager,
            SequenceManager sequenceManager, WareHouseManagerProxy wareHouseManagerProxy, ThreadPoolService threadPoolService, MessageConfigManager messageConfigManager, ThreadUtilManager threadUtilManager) {
        this.chooseOptionManager = chooseOptionManager;
        this.newInventoryOccupyManager = newInventoryOccupyManager;
        this.wareHouseManager = wareHouseManager;
        this.sequenceManager = sequenceManager;
        this.wareHouseManagerProxy = wareHouseManagerProxy;
        this.threadPoolService = threadPoolService;
        this.messageConfigManager = messageConfigManager;
        this.threadUtilManager = threadUtilManager;
        ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH, OcpInvConstants.OCP_CHOOSE_SYS_WH_OCP_BARCH_LIMIT);
        amount = Integer.parseInt(co.getOptionValue());
        this.ouId = ouId;
        this.flag = flag;
        this.threadName = threadName;
    }


    public void run() {
        try {
        if (flag != null && flag.equals("new")) {
            occupationInventoryByOu(ouId);
        } else {
            if (log.isDebugEnabled()) {
                log.debug(getLogThread() + ":Begin");
            }
            // 异常批次处理(新建、处理中、异常)
            List<Integer> status = new ArrayList<Integer>();
            status.add(OcpInvConstants.OCP_ORDER_STATUS_CREATED);
            status.add(OcpInvConstants.OCP_ORDER_STATUS_PROCESS);
            status.add(OcpInvConstants.OCP_ORDER_STATUS_EXCEPTION);
            List<WhOcpOrderCommand> woocList = newInventoryOccupyManager.findOcpOrderByParams(ouId, status);
            for (WhOcpOrderCommand co : woocList) {
                try {
                    if (log.isDebugEnabled()) {
                        log.debug(getLogThread() + " exceptionWooc..." + co.getCode());
                    }
                    newInventoryOccupyManager.releaseInventoryByOcpCode(co.getCode()); // 释放该批次的库存
                    newInventoryOccupyManager.ocpBatchCreateExceptionHandleAgain(co.getCode()); // 清空作业单占用信息，修改批次为已处理
                } catch (BusinessException e) {
                    if (log.isInfoEnabled()) {
                        log.info(getLogThread() + " exceptionWooc...business error " + co.getCode());
                    }
                } catch (Exception e) {
                    log.error(getLogThread() + " exceptionWooc...sys error " + co.getCode(), e);
                }
            }
            // 批次占用库存
            ocpOrderCreate(ouId, amount);
            // if (Constants.WAREHOUSE_OCP_TIME_MAP.containsKey(ouId)) {
            // Constants.WAREHOUSE_OCP_TIME_MAP.remove(ouId);
            // }
            // 将占有结束的仓库放入 缓存中，记录完成时间
            // Constants.WAREHOUSE_OCP_TIME_MAP.put(ouId, new Date());
            if (log.isDebugEnabled()) {
                log.debug(getLogThread() + " End");
            }
        }
        } catch (Exception e) {
            log.error("CreateOcpOrderThread_NEW"+ ouId, e);
        }
    }

    private void occupationInventoryByOu(Long ouId2) {
        if (log.isDebugEnabled()) {
            log.debug(getLogThread() + "ocp ou :" + ouId2 + "...Enter");
        }
        List<Long> staList = newInventoryOccupyManager.findNeedOcpOrderByOuId(ouId2);
        if (log.isDebugEnabled()) {
            log.debug(getLogThread() + "occupationInventoryByOu staList size:" + staList.size());
        }
        for (Long id : staList) {
            try {
                wareHouseManagerProxy.occupiedInventoryBySta(id, null);
            } catch (BusinessException e) {
                if (log.isInfoEnabled()) {
                    log.info("occupationInventoryByOu error ou:" + ouId2 + ";order id" + id, e);
                }
            } catch (Exception e) {
                log.error("occupationInventoryByOu error ou:" + ouId2 + ";order id" + id, e);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("CreateOcpOrderThrad.occupationInventoryByOu:" + ouId2 + "...end");
        }
    }

    /**
     * 根据占用批SKU占用库存
     * 
     * @throws Exception
     */
    public void skuOccupyByOcpOrder(Long wooId, List<Long> woolIdList, String ocpCode) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug(getLogThread() + wooId + " skuOccupyByOcpOrder start.........." + ocpCode);
        }
        try {
            // ChooseOption co =
            // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH,
            // OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_SKU_THREAD_LIMIT);
            // Integer amount = Integer.parseInt(co.getOptionValue());// 获取SKU占用库存最大线程数
            newInventoryOccupyManager.updateWhOcpOrderStatus(wooId, OcpInvConstants.OCP_ORDER_STATUS_PROCESS);// 更新此批次为处理中
            // ExecutorService pool = Executors.newFixedThreadPool(amount);// 根据参数创建线程池
            // ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            if (log.isDebugEnabled()) {
                log.info(getLogThread() + "sku ocp,pool size : {},todo size is :{}", amount, woolIdList.size());
            }
            for (Long woolId : woolIdList) {
                SkuOcpThread t = new SkuOcpThread(woolId, newInventoryOccupyManager);
                Thread dd = new Thread(t);
                threadPoolService.executeThread(OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_SKU_THREAD_LIMIT, dd);
                // ThreadPoolUtil.executeThread(tx, dd, 20);
            }
            // ThreadPoolUtil.closeThreadPool(tx); // 关闭线程池
            threadPoolService.waitToFinish(OcpInvConstants.OCP_CHOOSE_SYS_WH_DEFAULT_OCP_SKU_THREAD_LIMIT);
            newInventoryOccupyManager.updateWhOcpOrderStatus(wooId, OcpInvConstants.OCP_ORDER_STATUS_OCPOVER); // 更新批次状态为占用结束
            newInventoryOccupyManager.updateStaIsNeedOcpByOcpId(0l, wooId); // 作业单打标不需要占用库存
        } catch (BusinessException e) {
            log.error(getLogThread() + wooId + " skuOccupyByOcpOrder error...{},error code : ", ocpCode, e.getErrorCode());
            throw e;
        } catch (Exception e) {
            // newInventoryOccupyManager.releaseInventoryByOcpCode(ocpCode);
            // newInventoryOccupyManager.ocpBatchCreateExceptionHandle(ocpCode);
            log.error(getLogThread() + wooId + " skuOccupyByOcpOrder error.........." + ocpCode, e);
            throw e;// 抛出异常，在外层方法处理异常数据
        }
        if (log.isDebugEnabled()) {
            log.debug(getLogThread() + wooId + " skuOccupyByOcpOrder start.........." + ocpCode);
        }
    }


    private void ocpOrderList(List<Long> idList, Boolean isNew) {
        String ocpCode = "";
        try {
            ocpCode = sequenceManager.getOcpBatchCode();// 获取占用批编码
            newInventoryOccupyManager.updateStaOcpByIdList(ocpCode, idList);
            WhOcpOrderCommand wooc = newInventoryOccupyManager.createOcpOrder(ocpCode, ouId, isNew); // 创建占用批次
            skuOccupyByOcpOrder(wooc.getId(), wooc.getWoolIds(), wooc.getCode());// 根据占用批占用库存
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                log.warn(getLogThread() + ouId + " ocpOrderCreate is error,{},error code is {}", ocpCode, be.getErrorCode());// 异常处理
            } else {
                log.error(getLogThread() + ouId + " ocpOrderCreate is error:" + ocpCode, e);// 异常处理
            }
            newInventoryOccupyManager.releaseInventoryByOcpCode(ocpCode); // 释放该批次的库存
            newInventoryOccupyManager.ocpBatchCreateExceptionHandle(ocpCode); // 清空作业单占用信息、占用批刷成失败、超过3次修改作业单状态为20
            newInventoryOccupyManager.updateStaIsNeedOcpByOcpCode(1l, ocpCode); // 作业单打标需要占用库存
        }
    }

    /**
     * 批次占用库存
     * 
     * @param ouId
     * @param amount
     */
    public void ocpOrderCreate(Long ouId, Integer amount) {
        if (log.isDebugEnabled()) {
            log.debug(getLogThread() + ouId + " ocpOrderCreate start........");
        }

        List<Long> idList = new ArrayList<Long>(); // 作业单列表
        Warehouse se = wareHouseManager.getByOuId(ouId);
        // MQ开关
        boolean b = false;
        List<MessageConfig> configs = messageConfigManager.findMessageConfigByParameter(null, "MQ_wms3_occupy_inventory", null);
        if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
            b = true;
        }
        // mq或多线程开关控制
        ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH, OcpInvConstants.OCP_MQ_OR_THREAD);
        Boolean isMq = false;
        String threadCode = "";
     // 仓库线程池配置
        threadCode = OcpInvConstants.WH_OCP_THREAD_STA_COUNT + ouId;
        ThreadConfig config = threadUtilManager.getByCodeAndSysKey(OcpInvConstants.WH_OCP_THREAD_STA_COUNT + ouId, "DEAMON");
        if (config == null) {
            threadCode = OcpInvConstants.WH_OCP_THREAD_STA_COUNT; // 如为空，就走默认配置
        }
        if (co != null && "1".equals(co.getOptionValue())) {
            isMq = true;
        } else {
            isMq = false;
        }
        if (se.getIsAreaOcpInv() == null || !se.getIsAreaOcpInv()) {
            while (true) {
                try {
                    List<Long> staList = newInventoryOccupyManager.setOcpCodeForSta(null, null, ouId, 10000, null, null, null);// 查询1W单
                    if (log.isDebugEnabled()) {
                        log.info(getLogThread() + ouId + " ocpOrderCreate..........10000-" + staList.size());
                    }
                    if (b) {
                        // 丢MQ逻辑
                        log.debug(getLogThread() + ouId + " ocpOrderCreate_tomq_1");
                        for (Long staid : staList) {
                            // 是否MQ开关控制
                            if (!isMq) {
                                // 多线程调用
                                AreaOcpStaThread t = new AreaOcpStaThread(staid, ouId, "ocpStaById", null, null, threadPoolService, wareHouseManagerProxy, newInventoryOccupyManager);
                                Thread d = new Thread(t);
                                threadPoolService.executeThread(threadCode, d);
                            } else {
                                // 多线程调用 丢MQ
                                AreaOcpStaThread t = new AreaOcpStaThread(staid, ouId, "to_mq", null, null, threadPoolService, wareHouseManagerProxy, newInventoryOccupyManager);
                                Thread d = new Thread(t);
                                threadPoolService.executeThread(threadCode, d);
                                // newInventoryOccupyManager.occupiedInventoryByStaToMq(staid);
                            }
                        }
                        log.debug(getLogThread() + ouId + " ocpOrderCreate_tomq_2");
                       if (!isMq) {
                            threadPoolService.waitToFinish(threadCode);// 停止往线程池里放线程
                        }
                       // threadPoolService.waitToFinish(threadCode);// 停止往线程池里放线程
                    } else {
                        for (int i = 0, j = 1; i < staList.size(); i++, j++) {
                            idList.add(staList.get(i));
                            if (j == amount || i == staList.size() - 1) {
                                ocpOrderList(idList, false);
                                j = 0;// 重置计数
                                idList.clear(); // 重置ID
                            }
                        }
                    }
                    // 如果 订单池为空，或 订单总数小于1W单，则跳出
                    if (staList == null || staList.size() < 10000) {
                        break;
                    }
                } catch (Exception e) {
                    log.error(getLogThread() + ouId + " ocpOrderCreate is error:", e);// 异常日志打印
                    break;
                }
                log.info(getLogThread() + ouId + " ocpOrderCreate.end");
            }
        } else {
            // 区域占用库存新逻辑
            List<String> areaList = newInventoryOccupyManager.findAllZoon(ouId);
            String moreArea = "跨区域";
            areaList.add(moreArea);
            for (String areaCode : areaList) {
                List<Long> staList = newInventoryOccupyManager.setOcpCodeForSta(null, null, ouId, 40000, null, null, areaCode);// 查询1W单
                if (log.isDebugEnabled()) {
                    log.info(getLogThread() + ouId + " ocpOrderCreate..........10000-" + staList.size());
                }
                if (b) {
                    // 丢MQ逻辑
                    for (Long staid : staList) {
                        // 是否MQ开关控制
                        if (!isMq) {
                            // 多线程调用
                            AreaOcpStaThread t = new AreaOcpStaThread(staid, ouId, "ocpStaById", null, null, threadPoolService, wareHouseManagerProxy, newInventoryOccupyManager);
                            Thread d = new Thread(t);
                            threadPoolService.executeThread(threadCode, d);
                        } else {
                            // 多线程调用 丢MQ
                            AreaOcpStaThread t = new AreaOcpStaThread(staid, ouId, "to_mq", null, null, threadPoolService, wareHouseManagerProxy, newInventoryOccupyManager);
                            Thread d = new Thread(t);
                            threadPoolService.executeThread(threadCode, d);
                            // newInventoryOccupyManager.occupiedInventoryByStaToMq(staid);
                        }
                    }
                   if (!isMq) {
                        threadPoolService.waitToFinish2(threadCode);// 停止往线程池里放线程
                    }
                   // threadPoolService.waitToFinish(threadCode);// 停止往线程池里放线程
                } else {
                    for (int i = 0, j = 1; i < staList.size(); i++, j++) {
                        idList.add(staList.get(i));
                        if (j == amount || i == staList.size() - 1) {
                            ocpOrderList(idList, true);
                            j = 0;// 重置计数
                            idList.clear(); // 重置ID
                        }
                    }
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug(getLogThread() + ouId + " ocpOrderCreate end..........");
        }

    }

    private String getLogThread() {
        return "thread : " + threadName + "";
    }

}
