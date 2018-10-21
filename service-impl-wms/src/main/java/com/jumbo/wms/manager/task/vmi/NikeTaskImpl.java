package com.jumbo.wms.manager.task.vmi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.nikeDate.NikeStockInDao;
import com.jumbo.dao.warehouse.StaCreateQueueDao;
import com.jumbo.util.SFTPUtil;
import com.jumbo.webservice.nike.manager.NikeManager;
import com.jumbo.webservice.nike.manager.NikeOrderManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.NikeTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.warehouse.CustomsDeclarationManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.zk.ZkDataChangeManagerImpl;

@Service("nikeTask")
public class NikeTaskImpl extends BaseManagerImpl implements NikeTask {

    private static final long serialVersionUID = 4741423912493393416L;

    @Autowired
    private NikeManager nikeManager;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private StaCreateQueueDao staCreateQueueDao;
    @Autowired
    private NikeStockInDao nikeStockInDao;
    @Autowired
    private NikeOrderManager nikeOrderManager;
    @Autowired
    private CustomsDeclarationManager customsDeclarationManager;

    /**
     * 创建NIKE作业单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateStaByQueue() {
        log.debug("==================nike create sta trigger start==============");
        // 创建SO STA
        List<String> codeList = staCreateQueueDao.findToCreateCode(Constants.NIKE_RESOURCE, StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES.getValue(), new SingleColumnRowMapper<String>(String.class));
        for (String slipCode : codeList) {
            try {
                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES);
                sta.setRefSlipCode(slipCode);
                sta.setRefSlipType(SlipType.SALES_ORDER);
                nikeOrderManager.createNikeSalesSta(sta);
                staCreateQueueDao.updateStatusBySlipCode(DefaultStatus.FINISHED.getValue(), null, slipCode, StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES.getValue());
            } catch (Exception e) {
                String errorInfo = "";
                if (e instanceof BusinessException) {
                    BusinessException error = (BusinessException) e;
                    StringBuffer sb = new StringBuffer();
                    if (error.getLinkedException() == null) {
                        sb.append(error.getMessage());
                    } else {
                        BusinessException be = error;
                        while ((be = be.getLinkedException()) != null) {
                            sb.append(be.getMessage());
                        }
                    }
                    if (sb.length() > 500) {
                        errorInfo = sb.substring(0, 490) + "......";
                    } else {
                        errorInfo = sb.toString();
                    }
                } else {
                    log.error("", e);
                    errorInfo = "System error!";
                }
                staCreateQueueDao.updateStatusBySlipCode(DefaultStatus.CANCELED.getValue(), errorInfo, slipCode, StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES.getValue());
                log.error("Create NIKE so(CODE:" + slipCode + " ) error!");
            }
        }
        // 创建退换货出库STA
        List<String> code1List = staCreateQueueDao.findToCreateCode(Constants.NIKE_RESOURCE, StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue(), new SingleColumnRowMapper<String>(String.class));
        for (String slipCode : code1List) {
            try {
                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.OUTBOUND_RETURN_REQUEST);
                sta.setRefSlipCode(slipCode);
                sta.setRefSlipType(SlipType.OUT_RETURN_REQUEST);
                nikeOrderManager.createNikeSalesSta(sta);
                staCreateQueueDao.updateStatusBySlipCode(DefaultStatus.FINISHED.getValue(), null, slipCode, StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue());
            } catch (Exception e) {
                String errorInfo = "";
                if (e instanceof BusinessException) {
                    BusinessException error = (BusinessException) e;
                    StringBuffer sb = new StringBuffer();
                    if (error.getLinkedException() == null) {
                        sb.append(error.getMessage());
                    } else {
                        BusinessException be = error;
                        while ((be = be.getLinkedException()) != null) {
                            sb.append(be.getMessage());
                        }
                    }
                    if (sb.length() > 500) {
                        errorInfo = sb.substring(0, 490) + "......";
                    } else {
                        errorInfo = sb.toString();
                    }
                } else {
                    errorInfo = "System error!";
                }
                staCreateQueueDao.updateStatusBySlipCode(DefaultStatus.CANCELED.getValue(), errorInfo, slipCode, StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue());
                log.error("Create NIKE return Out(CODE:" + slipCode + " ) error!");
            }
        }
        // 创建退换货入库STA
        List<String> code2List = staCreateQueueDao.findToCreateCode(Constants.NIKE_RESOURCE, StockTransApplicationType.INBOUND_RETURN_REQUEST.getValue(), new SingleColumnRowMapper<String>(String.class));
        for (String slipCode : code2List) {
            try {
                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.INBOUND_RETURN_REQUEST);
                sta.setRefSlipCode(slipCode);
                sta.setRefSlipType(SlipType.OUT_RETURN_REQUEST);
                nikeOrderManager.createNikeSalesSta(sta);
                staCreateQueueDao.updateStatusBySlipCode(DefaultStatus.FINISHED.getValue(), null, slipCode, StockTransApplicationType.INBOUND_RETURN_REQUEST.getValue());
            } catch (Exception e) {
                String errorInfo = "";
                if (e instanceof BusinessException) {
                    BusinessException error = (BusinessException) e;
                    StringBuffer sb = new StringBuffer();
                    if (error.getLinkedException() == null) {
                        sb.append(error.getMessage());
                    } else {
                        BusinessException be = error;
                        while ((be = be.getLinkedException()) != null) {
                            sb.append(be.getMessage());
                        }
                    }
                    if (sb.length() > 500) {
                        errorInfo = sb.substring(0, 490) + "......";
                    } else {
                        errorInfo = sb.toString();
                    }
                } else {
                    errorInfo = "System error!";
                }
                staCreateQueueDao.updateStatusBySlipCode(DefaultStatus.CANCELED.getValue(), errorInfo, slipCode, StockTransApplicationType.INBOUND_RETURN_REQUEST.getValue());
                log.error("Create NIKE return In(CODE:" + slipCode + " ) error!");
            }
        }
        nikeOrderManager.deleteCreateFinishQueue();
    }

    protected void generateRsnDate() {
        try {
            nikeManager.generateRsnData();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadNikeData() {
        Map<String, String> cfg = configManager.getNikeFTPConfig();
        String localDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH);
        try {
            // 生成文件
            // 入库反馈写文件
            try {
                // FIXME NIKE收货调整
                // start-----------------
                // generateRsnDate();
                // end------------------
                nikeManager.inBoundTransferInBoundWriteToFile(localDir);
            } catch (Exception e) {
                log.error("", e);
            }

            // 转店退仓反馈写文件
            try {
                nikeManager.transferOutReceiveWriteToFile(localDir);
            } catch (Exception e) {
                log.error("", e);
            }
            // 退大库反馈写文件
            try {
                nikeManager.vmiReturnReceiveWriteToFile(localDir);
            } catch (Exception e) {
                log.error("", e);
            }
            // 库存调整 反馈写文件
            try {
                nikeManager.invCheckReceiveWriteToFile(localDir);
            } catch (Exception e) {
                log.error("", e);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        // upload file to ftp
        try {
            SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_UPPATH), cfg.get(Constants.VMI_FTP_UP_LOCALPATH),
                    cfg.get(Constants.NIKE_FTP_UP_LOCALPATH_BACKUP));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void downloadNikeData() {
        log.debug("down load nike Data........Begin");
        Map<String, String> cfg = configManager.getNikeFTPConfig();
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.NIKE_FTP_DOWN_LOCALPATH_BACKUP);
        VmiInterface vmiNikeInter = vmiFactory.getBrandVmi(BiChannel.CHANNEL_VMICODE_NIKE_BZ_STORE);
        // download file
        try {
            SFTPUtil.readFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_DOWNPATH), localFileDir, null, true,
                    cfg.get(Constants.NIKE_FTP_DOWNLOAD_FILE_START_STR));
        } catch (Exception e) {
            log.error("", e);
        }
        // sava data
        try {
            nikeManager.inBoundreadFileIntoDB(localFileDir, bakFileDir, cfg.get(Constants.NIKE_FTP_DOWNLOAD_FILE_START_STR));
        } catch (Exception e1) {
            if (log.isErrorEnabled()) {
                log.error("nikeManager.inBoundreadFileIntoDB Exception:", e1);
            }
        }
        // create sta
        try {
            vmiNikeInter.generateInboundSta();
        } catch (Exception e) {
            log.error("", e);
        }
        List<NikeVmiStockInCommand> list = customsDeclarationManager.findNikeVmiStockInByDeclaration(ZkDataChangeManagerImpl.nikeVmiCode);
        for (NikeVmiStockInCommand nikeVmiStockInCommand : list) {
            StockTransApplication stockTransApplication = customsDeclarationManager.findStaById(nikeVmiStockInCommand.getStaId());
            try {
                customsDeclarationManager.changeIsCustomsDeclaration(stockTransApplication, nikeVmiStockInCommand);
            } catch (Exception e) {
                log.error("", e);
            }

        }
        log.debug("down load nike Data........End");
    }

    @Override
    public void insertNikeStockReceive() {
        log.info("insertNikeStockReceive start");
        nikeOrderManager.insertNikeStockReceivePac();
        log.info("insertNikeStockReceive end");

    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadInboundNikeDataToHub() {
        /*
         * try { nikeManager.uploadInboundNikeDataToHub(); } catch (Exception e) {
         * log.error("uploadInboundNikeDataToHub", e); }
         */
        try {
            nikeManager.uploadInboundNikeDataToHub2();
        } catch (Exception e) {
            log.error("uploadInboundNikeDataToHub", e);
        }
    }
}
