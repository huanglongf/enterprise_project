package com.jumbo.wms.newInventoryOccupy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.ems.EmsTaskManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;

/**
 * SF反馈
 * 
 * @author sjk
 * 
 */
public class TransConfrimThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(TransConfrimThread.class);

    private Long id;
    private String falg;
    private WareHouseManager wareHouseManager;
    private EmsTaskManager emsTaskManager;

    public TransConfrimThread(Long id, String falg, WareHouseManager wareHouseManager, EmsTaskManager emsTaskManager) {
        this.id = id;
        this.falg = falg;
        this.wareHouseManager = wareHouseManager;
        this.emsTaskManager = emsTaskManager;
    }

    public void run() {
        // SF反馈
        try {
            if ("SF".equals(falg)) {
                wareHouseManager.exeSfConfirmOrder(id);
            } else if ("EMSOLD".equals(falg)) {
                emsTaskManager.exeEmsConfirmOrder(id);
            } else if ("EMSNEW".equals(falg)) {
                emsTaskManager.exeEmsConfirmOrderNew(id);
            }
        } catch (BusinessException e) {
            log.error(id + falg + "sfIntefaceByWarehouse error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error(id + falg + "TransConfrimThread error: ", e);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFalg() {
        return falg;
    }

    public void setFalg(String falg) {
        this.falg = falg;
    }


}
