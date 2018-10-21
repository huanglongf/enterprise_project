package com.jumbo.wms.newInventoryOccupy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.manager.warehouse.AreaOcpStaManager;
import com.jumbo.wms.model.MongoDBInventoryOcp;


/**
 * mongDb新增库存
 * 
 * @author xiaolong.fei
 *
 */
public class MongDbAddInvThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(MongDbAddInvThread.class);
    private MongoDBInventoryOcp ocp;
    private Long ouId;
    private AreaOcpStaManager areaOcpStaManager;

    public MongDbAddInvThread(Long ouId, MongoDBInventoryOcp ocp, AreaOcpStaManager areaOcpStaManager) {
        this.ouId = ouId;
        this.ocp = ocp;
        this.areaOcpStaManager = areaOcpStaManager;
    }

    public void run() {
        // 初始化MongDB库存
        areaOcpStaManager.initInventoryForOcpAreaByOwner(ocp, ouId);
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public MongoDBInventoryOcp getOcp() {
        return ocp;
    }

    public void setOcp(MongoDBInventoryOcp ocp) {
        this.ocp = ocp;
    }

    public AreaOcpStaManager getAreaOcpStaManager() {
        return areaOcpStaManager;
    }

    public void setAreaOcpStaManager(AreaOcpStaManager areaOcpStaManager) {
        this.areaOcpStaManager = areaOcpStaManager;
    }

}
