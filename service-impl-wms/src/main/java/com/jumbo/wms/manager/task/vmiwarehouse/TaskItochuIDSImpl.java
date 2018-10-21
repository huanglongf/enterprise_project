package com.jumbo.wms.manager.task.vmiwarehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.daemon.TaskItochuIDS;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.itochuData.ItochuUAManager;

public class TaskItochuIDSImpl extends BaseManagerImpl implements TaskItochuIDS {

    /**
     * 
     */
    private static final long serialVersionUID = 8470137586914948679L;
    @Autowired
    private ItochuUAManager itochuUAManager;

    protected static final Logger log = LoggerFactory.getLogger(TaskItochuIDSImpl.class);


    @Override
    public void insertAeoInventory() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertAeoInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");

    }

    @Override
    public void insertAeoJDInventory() {
        log.debug("******************* insertAeoJDInventory ********************");
        try {
            itochuUAManager.insertAeoJDInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* insertAeoJDInventory end ********************");

    }

    @Override
    public void insertAFInventory() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertAfInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");

    }

    @Override
    public void insertNikeNewInventory() {
        log.error("******************* nike new inventory start ********************");
        try {
            itochuUAManager.insertNikeNewInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* nike new  inventory end ********************");

    }
    
    @Override
    public void insertNikeNewInventory2() {
        log.error("******************* nike2 new inventory start ********************");
        try {
            itochuUAManager.insertNikeNewInventoryLog2();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* nike2 new  inventory end ********************");

    }

    @Override
    public void insertNikeCrwInventory() {
        log.error("******************* insertNikeCrwInventory start********************");
        try {
            itochuUAManager.insertNikeCrwInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* insertNikeCrwInventory end ********************");

    }

    @Override
    public void insertConverseInventoryLog() {
        log.error("******************* insertConverseInventoryLog start********************");
        try {
            itochuUAManager.insertConverseInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* insertConverseInventoryLog end ********************");
    }

    @Override
    public void insertNewLookInventory() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertNewLookInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");

    }

    @Override
    public void insertUaNbaInventory() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertUaNbaInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");

    }

    @Override
    public void insertNikeInventory() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertNikeInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");

    }

    @Override
    public void insertNikeInventoryGZ() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertNikeInventoryLogGZ();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");

    }

    @Override
    public void insertNikeInventoryTM() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertNikeInventoryLogTM();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");

    }

    @Override
    public void insertNikeInventoryGZTM() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertNikeInventoryLogGZTM();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");

    }


    @Override
    public void insertIDSVSInventoryLog() {
        try {
            itochuUAManager.insertIDSVSInventoryLog();
        } catch (Exception e) {
            log.error("VS" + e.getMessage());
        }
    }

    @Override
    public void insertNewLookJDInventory() {
        log.debug("******************* NewLookJD inventory start ********************");
        try {
            itochuUAManager.insertNewLookJDInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* NewLookJD inventory end ********************");

    }

}
