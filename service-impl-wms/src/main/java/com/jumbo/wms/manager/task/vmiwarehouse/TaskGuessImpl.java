package com.jumbo.wms.manager.task.vmiwarehouse;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.util.SFTPUtil;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskGuess;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.guess.GuessManager;
import com.jumbo.wms.manager.vmi.guess.GuessManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;

public class TaskGuessImpl extends BaseManagerImpl implements TaskGuess {

    /**
     * 
     */
    private static final long serialVersionUID = 3428350108540108532L;

    @Autowired
    private CommonConfigManager configManager;

    @Autowired
    private GuessManager guessManager;

    @Autowired
    private GuessManagerProxy guessManagerProxy;
    @Autowired
    InventoryCheckDao inventoryCheckDao;
    @Autowired
    OperationUnitDao operationUnitDao;
    @Autowired
    WareHouseManager wareHouseManager;
    @Autowired
    IdsManager idsManager;
    @Autowired
    InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createGuessSales() {
        Map<String, String> cfg = configManager.getGuessFTPConfig();
        // String localDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH);
        // guessManager.createSalesFile(localDir);
        // 上传文件
        try {
            SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_UPPATH), cfg.get(Constants.VMI_FTP_UP_LOCALPATH),
                    cfg.get(Constants.GUESS_FTP_UP_LOCALPATH_BACKUP));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void insertGuessInventoryLog() {
        log.debug("******************* Guess inventory start ********************");
        try {
            guessManager.insertGuessInventoryLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("TaskGuess insertGuessInventoryLog errors", e);
            }
        }
        log.debug("******************* Guess inventory end ********************");
    }

    @Override
    public void sendSalesOrderToHubTask() {
        log.error("sendSalesOrderToHubTask_1");
        guessManager.sendSalesOrderToHub(Constants.VIM_WH_SOURCE_GUESS);
        guessManager.sendSalesOrderToHub(Constants.VIM_WH_SOURCE_GUESS_RETAIL);
        log.error("sendSalesOrderToHubTask_2");

    }

    @Override
    public void customerReturnRequestToHubTask() {
        guessManager.customerReturnRequestToHub(Constants.VIM_WH_SOURCE_GUESS);
        guessManager.customerReturnRequestToHub(Constants.VIM_WH_SOURCE_GUESS_RETAIL);
    }

    @Override
    public void receiveEcomAdjTask() {
        guessManagerProxy.receiveEcomAdj();
    }

    @Override
    public void customerReturnCancelRequestTask() {

        guessManager.customerReturnCancelRequestToHub();

    }

    @Override
    public void createInventoryRetail() {
        log.info("createInventoryRetail>>>>>>>>>>>>>>>>>>>>>-begin");
        try {
            guessManager.createInventoryRetail();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("TaskGuess createInventoryRetail errors", e);
            }
        }

    }


    public void createInventoryByAll() {
        log.info("createInventoryByAll>>>>>>>>>>>>>>>>>>>>>-begin");
        try {
            guessManager.createInventoryByAll();
        } catch (Exception e) {
                log.error("TaskGuess createInventoryByAll errors", e);
        }
    }

    @Override
    public void insertGuessInventoryRetailLog() {
        log.info("insertGuessInventoryRetailLog>>>>>>>>>>>>>>>>>>>>>-begin");
        try {
            guessManager.insertGuessInventoryRetailLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("TaskGuess insertGuessInventoryRetailLog errors", e);
            }
        }

    }

    @Override
    public void createInventory() {
        log.info("createInventory>>>>>>>>>>>>>>>>>>>>>>");
        queryOuId(Constants.VIM_WH_SOURCE_GUESS);
        queryOuId(Constants.VIM_WH_SOURCE_GUESS_RETAIL);

    }

    public void queryOuId(String vmiCode) {
        OperationUnit operationUnit = operationUnitDao.getByCode(vmiCode);
        List<InventoryCheck> checks = inventoryCheckDao.getInventoryCheckStatus(operationUnit.getId());
        for (InventoryCheck inventoryCheck : checks) {
            if (InventoryCheckStatus.CREATED.equals(inventoryCheck.getStatus())) {
                try {
                    idsManager.executionVmiAdjustment(inventoryCheck);
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("TaskGuess executionVmiAdjustment error:" + vmiCode, e);
                    }
                    if (inventoryCheck.getErrorCount() == null) {
                        inventoryCheck.setErrorCount(1);
                        inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
                    } else {
                        inventoryCheck.setErrorCount(inventoryCheck.getErrorCount() + 1);
                        inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
                    }
                }
                inventoryCheckDao.save(inventoryCheck);
            } else if (InventoryCheckStatus.UNEXECUTE.equals(inventoryCheck.getStatus())) {
                try {
                    List<InventoryCheckDifferenceLine> checkDifferenceLines = inventoryCheckDifferenceLineDao.findByInventoryCheck(inventoryCheck.getId());
                    if (checkDifferenceLines.size() == 0) {
                        log.error("IDS->WMS Error! InventoryCheckDifferenceLine execution ,Status is error!");
                        throw new BusinessException();
                    } else {
                        wareHouseManager.confirmVMIInvCKAdjustment(inventoryCheck);
                    }
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("TaskGuess confirmVMIInvCKAdjustment error:" + vmiCode, e);
                    }
                    if (inventoryCheck.getErrorCount() == null) {
                        inventoryCheck.setErrorCount(1);
                        // inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
                    } else {
                        inventoryCheck.setErrorCount(inventoryCheck.getErrorCount() + 1);
                        // inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
                    }
                }
                inventoryCheckDao.save(inventoryCheck);
            } else {
                throw new BusinessException(ErrorCode.STA_TYPE_ERROR);
            }
        }
    }
}
