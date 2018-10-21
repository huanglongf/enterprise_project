package com.jumbo.wms.newInventoryOccupy;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.task.ThreadPoolService;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.manager.warehouse.AreaOcpStaManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.MongoDBInventoryOcp;
import com.jumbo.wms.model.pickZone.OcpInvConstants;


/**
 * 作业单区域占用库存
 * 
 * @author xiaolong.fei
 *
 */
public class AreaOcpStaThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(AreaOcpStaThread.class);
    private Long staId;
    private String areaCode;
    private Long ouId;
    private String flag;
    private ThreadPoolService threadPoolService;
    private AreaOcpStaManager areaOcpStaManager;
    private WareHouseManagerProxy wareHouseManagerProxy;
    private NewInventoryOccupyManager newInventoryOccupyManager;


    public AreaOcpStaThread(Long staId, Long ouId, String flag, String areaCode, AreaOcpStaManager areaOcpStaManager, ThreadPoolService threadPoolService, WareHouseManagerProxy wareHouseManagerProxy, NewInventoryOccupyManager newInventoryOccupyManager) {
        this.areaOcpStaManager = areaOcpStaManager;
        this.staId = staId;
        this.ouId = ouId;
        this.flag = flag;
        this.areaCode = areaCode;
        this.threadPoolService = threadPoolService;
        this.wareHouseManagerProxy = wareHouseManagerProxy;
        this.newInventoryOccupyManager = newInventoryOccupyManager;

    }


    public void run() {
        try {
        
        if ("intMongDb".equals(flag)) {
            // 初始化MongDb
            initmongDbInvByAreaCode(areaCode, ouId, areaOcpStaManager);
        } else if ("to_mq".equals(flag)) {
            try {
                newInventoryOccupyManager.occupiedInventoryByStaToMq2(staId);// 打标
                newInventoryOccupyManager.occupiedInventoryByStaToMq(staId);// 占用
            } catch (Exception e) {
                log.info("AreaOcpStaThread_to_mq error staid:" + staId, e);
            }
        } else if ("ocpStaById".equals(flag)) {
            try {
                wareHouseManagerProxy.newOccupiedInventoryBySta(staId, null, null);
            } catch (Exception e) {
                log.error("AreaOcpStaThread error staid:" + staId, e);
            }
        } else {
            // 计算作业单
            areaOcpStaManager.ocpInvByStaId(staId);
        }
        } catch (Exception e) {
            log.error("AreaOcpStaThread_NEW_staid:" + staId, e);
        }
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public AreaOcpStaManager getAreaOcpStaManager() {
        return areaOcpStaManager;
    }

    public void setAreaOcpStaManager(AreaOcpStaManager areaOcpStaManager) {
        this.areaOcpStaManager = areaOcpStaManager;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }


    public ThreadPoolService getThreadPoolService() {
        return threadPoolService;
    }


    public void setThreadPoolService(ThreadPoolService threadPoolService) {
        this.threadPoolService = threadPoolService;
    }

    /**
     * 初始化mongDb库存
     * 
     * @param areaCode
     */
    private void initmongDbInvByAreaCode(String areaCode, Long ouId, AreaOcpStaManager areaOcpStaManager) {
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
            threadPoolService.executeThread(OcpInvConstants.AREA_OCP_THREAD_MONGDB_INV, d);
        }
        threadPoolService.waitToFinish2(OcpInvConstants.AREA_OCP_THREAD_MONGDB_INV);// 停止往线程池里放线程
        log.info("initmongDbInvByAreaCode is end " + areaCode);
    }
}
