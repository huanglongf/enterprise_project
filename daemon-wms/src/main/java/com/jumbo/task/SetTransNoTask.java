package com.jumbo.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.daemon.EmsTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.task.sforder.SfOrderTaskManager;
import com.jumbo.wms.manager.task.ytoorder.YtoOrderTaskManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.newInventoryOccupy.TransGetNoMqThread;
import com.jumbo.wms.newInventoryOccupy.TransGetNoThread;

public class SetTransNoTask {
    protected static final Logger log = LoggerFactory.getLogger(SetTransNoTask.class);
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private EmsTask emsTask;
    @Autowired
    private YtoOrderTaskManager ytoOrderTaskManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private SfOrderTaskManager sfOrderTaskManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private ThreadPoolService threadPoolService;

    public void setEmsStaTransNo() {
        Long count = emsTask.getTransNoNumber();
        // 快递单号如果没有了 就不执行此代码
        if (count > 0) {
            List<Long> idList = emsTask.getAllEmsWarehouse();
            for (Long id : idList) {
                try {
                    List<String> lpList = new ArrayList<String>();
                    lpList.add(Transportator.EMS);
                    // lpList.add(Transportator.EMS_COD);
                    List<Long> staList = emsTask.findStaByOuIdAndStatus(id, lpList);
                    for (Long staId : staList) {
                        // EMS下单
                        try {
                            // 设置EMS单据号
                            transOlManager.matchingTransNo(staId);
                        } catch (Exception e) {
                            log.error("", e);
                        }
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    public void ytoInterfaceAllWarehouse() {
        List<Long> idList = ytoOrderTaskManager.getAllYtoWarehouse();
        for (Long id : idList) {
            try {
                ytoIntefaceByWarehouse(id);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    private void ytoIntefaceByWarehouse(Long whId) {
    	if(log.isInfoEnabled()){
    		log.info("==============" + whId + "==================");    		
    	}
        Warehouse wh = wareHouseManager.getWarehouseByOuId(whId);
        if (wh == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }
        Boolean flag = true;
        while (flag) {
            List<String> lpList = new ArrayList<String>();
            lpList.add("YTO");
            lpList.add("YTOCOD");
            List<Long> staList = wareHouseManager.findStaByOuIdAndStatus(whId, lpList);
            if (staList.size() < 100) {
                flag = false;
            }
            for (Long id : staList) {
                // Yto下单
                try {
                    // 设置顺丰单据号
                    sfOrderTaskManager.matchingTransNo(Transportator.YTO, whId, id);
                } catch (BusinessException e) {
                    log.error("ytoIntefaceByWarehouse error code is : " + e.getErrorCode());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }
    
    /**
     * 通用设置运单号方法
     */
    public void exeSetAllTransNo() {
    	if(log.isInfoEnabled()){
    		log.info("exeSetAllTransNo start====================");	
    	}
    	Boolean flag = true;
//        ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("threadOrLimitNum", "THREAD_NUM");
//        Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
        ChooseOption co2 = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("threadOrLimitNum", "ORDER_NUM");
        Long orderCount = Long.parseLong(co2.getOptionValue());
        if(log.isInfoEnabled()){
        	//log.info("exeSetAllTransNo cfg : threadPoolQty : {} , orderCount : {}",threadPoolQty,orderCount);
        	log.info("exeSetAllTransNo cfg : orderCount : {}",orderCount);
        }
        // 根据参数创建线程池
        while (flag) {
//        	ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
//            ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            // 查询所有没有运单号的工作单
        	List<StockTransApplicationCommand> staList = wareHouseManager.getNoTransSta(orderCount);
            if (staList.size() < orderCount) {
                flag = false;
            }
            for (StockTransApplicationCommand stac : staList) {
            	try {
                    Thread t = new TransGetNoThread(stac.getOuId(), stac.getId(), stac.getLpcode());
//                    tx.execute(t);
                    threadPoolService.executeThread("THREAD_NUM", t);
                } catch (Exception e) {
                    log.error("", e);
                }
//            	while (true) {
//                    long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
//                    if (todoTotal >= 2000) {
//                        try {
//                            Thread.sleep(500L);
//                            if(log.isDebugEnabled()){
//                            	log.debug("get trans no thread todoTotal is " + todoTotal);	
//                            }
//                        } catch (InterruptedException e) {
//                            log.error("exeSetAllTransNo sleep error",e);
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
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    log.error("exeSetAllTransNo InterruptedException majorThread error",e);
//                }
//            }
            threadPoolService.waitToFinish("THREAD_NUM");
        }
        if(log.isInfoEnabled()){
        	log.info("exeSetAllTransNo end====================");	
        }
    }

    /**
     * 获取运单号 MQ
     */
    public void exeSetAllTransNoByMq() {
        if (log.isInfoEnabled()) {
            log.info("exeSetAllTransNoByMq start====================");
        }
        Boolean flag = true;
        ChooseOption co2 = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("threadOrLimitNum", "MQ_ORDER_NUM");
        Long orderCount = Long.parseLong(co2.getOptionValue());
        if (log.isInfoEnabled()) {
            log.info("exeSetAllTransNoByMq cfg : orderCount : {}", orderCount);
        }
        // 根据参数创建线程池
        while (flag) {
            // 查询所有没有运单号的工作单
            List<Long> staList = wareHouseManager.findNoTransStaByMq(orderCount);
            if (staList.size() < orderCount) {
                flag = false;
            }
            for (Long staId : staList) {
                try {
                    Thread t = new TransGetNoMqThread(staId);
                    threadPoolService.executeThread("getTransNoSendMq", t);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            threadPoolService.waitToFinish("getTransNoSendMq");
            if (log.isInfoEnabled()) {
                log.info("exeSetAllTransNo end====================");
            }
        }
    }
}
