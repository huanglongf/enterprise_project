package com.jumbo.wms.manager.task.vmi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.defaultData.VmiStatusAdjDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.wms.daemon.VmiInventoryTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.Default.VmiDefaultManager;
import com.jumbo.wms.manager.vmi.vmiInventory.VmiInventoryManager;
import com.jumbo.wms.model.vmi.Default.VmiAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiStatusAdjCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;

public class VmiInventoryTaskImpl extends BaseManagerImpl implements VmiInventoryTask {

    private static final long serialVersionUID = 4883262168478943801L;

    @Autowired
    private VmiInventoryManager vmiInventoryManager;
    @Autowired
    private BiChannelDao biChannelDao;

    @Autowired
    private VmiDefaultManager vmiDefaultManager;


    @Autowired
    private VmiStatusAdjDao vmiStatusAdjDao;

    /**
     * MK库存快照同步
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInventoryMK() {
        log.debug("==============================VmiInventoryTask vmiInventoryMichaelKors start");
        BiChannelCommand b = biChannelDao.getBiChannelByDefaultCode("MichaelKors", new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        try {
            // 获取库存数据
            List<String> biChannelCodeList = vmiInventoryManager.getBiChannelByVmiCode(b.getVmiCode());
            vmiInventoryManager.insertVmiInventory(b, biChannelCodeList);
        } catch (Exception e) {
            log.error("", e);
            log.error("==============================VmiInventoryTask vmiInventoryMichaelKors insertvmiInventory error");
            return;
        }
        // 同步HUB
        vmiInventoryManager.uploadVmiInventoryToHub(b.getVmiCode());
        log.debug("==============================VmiInventoryTask vmiInventoryMichaelKors end");

    }


    /**
     * Ralph库存快照同步
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInventoryRalph() {
        log.debug("==============================VmiInventoryTask vmiInventoryRalph start");
        BiChannelCommand b = biChannelDao.getBiChannelByDefaultCode("07300", new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        try {
            // 获取库存数据
            List<String> biChannelCodeList = vmiInventoryManager.getBiChannelByVmiCode(b.getVmiCode());
            vmiInventoryManager.insertVmiInventory(b, biChannelCodeList);
        } catch (Exception e) {
            log.error("", e);
            log.error("==============================VmiInventoryTask vmiInventoryMichaelKors insertvmiInventory error");
            return;
        }
        // 同步HUB
        vmiInventoryManager.uploadVmiInventoryToHub(b.getVmiCode());
        log.debug("==============================VmiInventoryTask vmiInventoryRalph end");

    }

    /**
     * CK库存快照同步
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInventoryCK() {
        log.debug("==============================VmiInventoryTask vmiInventoryMichaelKors start");
        List<String> vmiCodeList = biChannelDao.findBiChannelByDefaultCode("CK", new SingleColumnRowMapper<String>(String.class));
        try {
            // 获取库存数据
            vmiInventoryManager.insertCKVmiInventory("CK", vmiCodeList);
        } catch (Exception e) {
            log.error("", e);
            log.error("==============================VmiInventoryTask vmiInventoryMichaelKors insertvmiInventory error");
            return;
        }
        // 同步HUB
        for (String code : vmiCodeList) {
            vmiInventoryManager.uploadVmiInventoryToHub(code);
        }
        log.debug("==============================VmiInventoryTask vmiInventoryMichaelKors end");

    }

    /**
     * 强生库存快照同步
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInventoryJNJ() {
        log.debug("==============================VmiInventoryTask vmiInventoryJNJ start");
        BiChannelCommand b = biChannelDao.getBiChannelByDefaultCode("johnson", new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        try {
            // 获取库存数据
            List<String> biChannelCodeList = vmiInventoryManager.getBiChannelByVmiCode(b.getVmiCode());
            vmiInventoryManager.insertVmiInventory(b, biChannelCodeList);
        } catch (Exception e) {
            log.error("", e);
            log.error("==============================VmiInventoryTask vmiInventoryJNJ insertvmiInventory error");
            return;
        }
        // 同步HUB
        vmiInventoryManager.uploadVmiInventoryToHub(b.getVmiCode());
        log.debug("==============================VmiInventoryTask vmiInventoryJNJ end");
    }

    /**
     * 星巴克库存快照同步
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInventoryStarbucks() {
        log.debug("==============================VmiInventoryTask vmiInventoryStarbucks start");
        BiChannelCommand b = biChannelDao.getBiChannelByDefaultCode("starbucks", new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        try {
            // 获取库存数据
            List<String> biChannelCodeList = vmiInventoryManager.getBiChannelByVmiCode(b.getVmiCode());
            vmiInventoryManager.insertVmiInventory(b, biChannelCodeList);
        } catch (Exception e) {
            log.error("", e);
            log.error("==============================VmiInventoryTask vmiInventoryStarbucks insertvmiInventory error");
            return;
        }
        // 同步HUB
        vmiInventoryManager.uploadVmiInventoryToHub(b.getVmiCode());
        log.debug("==============================VmiInventoryTask vmiInventoryStarbucks end");

    }


    /**
     * PaulFrank库存快照同步
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInventoryPaulFrank() {
        log.debug("==============================VmiInventoryTask vmiInventorySpeedo start");
        BiChannelCommand b = biChannelDao.getBiChannelByDefaultCode("paulFrank", new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        try {
            // 获取库存数据
            List<String> biChannelCodeList = vmiInventoryManager.getBiChannelByVmiCode(b.getVmiCode());
            vmiInventoryManager.insertVmiInventory(b, biChannelCodeList);
        } catch (Exception e) {
            log.error("", e);
            log.error("==============================VmiInventoryTask vmiInventoryStarbucks insertvmiInventory error");
            return;
        }
        // 同步HUB
        vmiInventoryManager.uploadVmiInventoryToHub(b.getVmiCode());
        log.debug("==============================VmiInventoryTask PaulFrank end");

    }

    /**
     * speedo库存快照同步
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInventorySpeedo() {
        log.debug("==============================VmiInventoryTask vmiInventorySpeedo start");
        BiChannelCommand b = biChannelDao.getBiChannelByDefaultCode("speedo", new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        try {
            // 获取库存数据
            List<String> biChannelCodeList = vmiInventoryManager.getBiChannelByVmiCode(b.getVmiCode());
            vmiInventoryManager.insertVmiInventory(b, biChannelCodeList);
        } catch (Exception e) {
            log.error("", e);
            log.error("==============================VmiInventoryTask vmiInventoryStarbucks insertvmiInventory error");
            return;
        }
        // 同步HUB
        vmiInventoryManager.uploadVmiInventoryToHub(b.getVmiCode());
        log.debug("==============================VmiInventoryTask vmiInventoryStarbucks end");

    }

    /**
     * speedo库存调整反馈
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInventorySpeedoExt() {
        // 查询speedo库存调整数据
        List<VmiAdjCommand> adjList = vmiDefaultManager.findVmiAdjAllExt("SPEEDO");
        // 同步调整数据给HUB
        for (VmiAdjCommand vmiAdjCommand : adjList) {
            try {
                vmiDefaultManager.uploadVmiAdjToHubSpeedoExt(vmiAdjCommand);
            } catch (Exception e) {
                log.error("", e);
                vmiDefaultManager.updateVmiErrorCount(VmiAdjDefault.vmiadj, vmiAdjCommand.getId());
            }
        }
    }

    /**
     * speedo库存状态调整反馈
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void StatusInventorySpeedoExt() {
        List<VmiStatusAdjCommand> adjList = vmiStatusAdjDao.findVmiStatusAdjAllExt("SPEEDO", new BeanPropertyRowMapper<VmiStatusAdjCommand>(VmiStatusAdjCommand.class));
        for (VmiStatusAdjCommand vmiAdjStatusCommand : adjList) {
            try {
                // 同步调整数据给HUB
                vmiDefaultManager.uploadStatusAdjToHubSpeedoExt(vmiAdjStatusCommand);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}
