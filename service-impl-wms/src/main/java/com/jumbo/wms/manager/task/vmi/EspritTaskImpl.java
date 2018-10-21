package com.jumbo.wms.manager.task.vmi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import loxia.utils.DateUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.task.annotation.SingleTaskLock;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLineQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderQueueDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.EspritTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.espData.ESPOrderManager;
import com.jumbo.wms.manager.vmi.espData.ESPReceiveDeliveryManager;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch.EspDispatch;
import com.jumbo.wms.model.command.vmi.esprit.xml.order.EspritOrderRootXml;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLineQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryReceiveCommand;
import com.jumbo.wms.model.warehouse.QueueSta;

@Service("espTask")
public class EspritTaskImpl extends BaseManagerImpl implements EspritTask {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7441021130414452366L;
    @Autowired
    private ESPOrderManager espOrderManager;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private VmiFactory f;
    @Autowired
    private ESPReceiveDeliveryManager espRDeliveryManager;
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private RocketMQProducerServer producerServer;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private WmsSalesOrderQueueDao wmsSalesOrderQueueDao;
    @Autowired
    private WmsSalesOrderLineQueueDao wmsSalesOrderLineQueueDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    List<File> filePathsList = new ArrayList<File>();



    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void downloadFile() {
        // 下载ORDER下所有文件
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.ESPRIT_GROUP);
        String localDownloadPath = config.get(Constants.ESPRIT_FTP_DOWN_LOCALPATH);
        String localDownloadBackupPath = config.get(Constants.ESPRIT_FTP_DOWN_LOCALPATH_BK);
        String remoteDownloadOrderPath = config.get(Constants.ESPRIT_FTP_DOWNPATH) + "/Order/";
        try {
            SFTPUtil.readFile(config.get(Constants.ESPRIT_FTP_URL), config.get(Constants.ESPRIT_FTP_PORT), config.get(Constants.ESPRIT_FTP_USERNAME), config.get(Constants.ESPRIT_FTP_PASSWORD), remoteDownloadOrderPath, localDownloadPath, null, true);
        } catch (Exception e) {
            log.error("", e);
        }
        // 读取所有order至数据库
        saveOrderToTable(localDownloadPath, localDownloadBackupPath);
    }


    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void downloadTestFile() {
        // 下载ORDER下所有文件
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.TESTFILE_GROUP);
        String localFileDir = cfg.get(Constants.TESTFILE_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.TESTFILE_FTP_DOWN_LOCALPATH_BK);

        // 下载FTP文件到本地
        try {
            SFTPUtil.readFile(cfg.get(Constants.TESTFILE_FTP_URL), cfg.get(Constants.TESTFILE_FTP_PORT), cfg.get(Constants.TESTFILE_FTP_USERNAME), cfg.get(Constants.TESTFILE_FTP_PASSWORD), cfg.get(Constants.TESTFILE_RD_FTP_DOWNPATH), localFileDir,
                    bakFileDir, true, cfg.get(Constants.TESTFILE_FTP_DOWNLOAD_FILE_START_STR));
        } catch (Exception e) {
            log.error("", e);
        }
        File f = new File(localFileDir);
        getFileList(f);
        Integer num = null;
        try {
            num = chooseOptionManager.getSystemThreadValueByKey("analyzeInvData");
        } catch (Exception e) {
            num = 5;
        }
        ExecutorService pool = Executors.newFixedThreadPool(num);
        for (File file : filePathsList) {
            ReadInvDataFile readInvDataFile = new ReadInvDataFile(file, localFileDir, bakFileDir, cfg.get(Constants.TESTFILE_FTP_DOWNLOAD_FILE_START_STR));
            Thread t1 = new Thread(readInvDataFile);
            pool.execute(t1);
        }
        pool.shutdown();
        boolean isFinish = false;
        while (!isFinish) {
            isFinish = pool.isTerminated();
            try {
                Thread.sleep(5 * 1000L);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("downloadTestFile", e);
                }
            }
        }
        // saveToTable
        // analyzeInvData(localFileDir, bakFileDir,
        // cfg.get(Constants.TESTFILE_FTP_DOWNLOAD_FILE_START_STR));
    }

    public void getFileList(File f) {
        File[] filePaths = f.listFiles();
        for (File s : filePaths) {
            if (s.isDirectory()) {
                continue;
            } else {
                if (s.getName().contains(".csv")) {
                    filePathsList.add(s);
                }
            }
        }
    }

    /*
     * public void analyzeInvData(String localPath, String localBackupPath, String fileStart) { File
     * fileDir = new File(localPath); File[] localInFileList = null; localInFileList =
     * fileDir.listFiles(); if (localInFileList == null || localInFileList.length == 0) {
     * log.debug("saveToTable ", localPath); } if (localInFileList == null) { return; } for (File
     * file : localInFileList) { if (file.isDirectory()) { continue; } if (fileStart != null) { if
     * (!file.getName().contains(fileStart)) { continue; } } try { if (file.length() > 0) {
     * espOrderManager.saveToTable(file); } FileUtils.copyFile(new File(localPath + File.separator +
     * file.getName()), new File(localBackupPath + File.separator + file.getName()), true); if (new
     * File(localPath + File.separator + file.getName()).getAbsoluteFile().exists()) {
     * FileUtils.forceDelete(new File(localPath + File.separator +
     * file.getName()).getAbsoluteFile()); } } catch (IOException e) { log.error("analyzeInvData",
     * e); }
     * 
     * }
     * 
     * 
     * }
     */

    public void analyzeInvData(File file, String localPath, String localBackupPath, String fileStart) {
        try {
            if (file.length() > 0) {
                if (fileStart != null) {
                    if (file.getName().contains(fileStart)) {
                        espOrderManager.saveToTable(file);
                    }
                }
                FileUtils.copyFile(new File(localPath + File.separator + file.getName()), new File(localBackupPath + File.separator + file.getName()), true);
                if (new File(localPath + File.separator + file.getName()).getAbsoluteFile().exists()) {
                    FileUtils.forceDelete(new File(localPath + File.separator + file.getName()).getAbsoluteFile());
                }
            }

        } catch (IOException e) {
            log.error("analyzeInvData", e);
        }
    }


    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadFile() {
        if (log.isInfoEnabled()) {
            log.info("esprit upload file start");
        }
        // 上传文件
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.ESPRIT_GROUP);
        String locationUploadPath = config.get(Constants.ESPRIT_FTP_UP_LOCALPATH);
        // 上传文件
        String remoteUpPath = config.get(Constants.ESPRIT_FTP_UPPATH);
        String locationUploadBackupPath = config.get(Constants.ESPRIT_FTP_UP_LOCALPATH_BK);
        String remoteUpPathReceving = remoteUpPath + "/Receiving";
        String locationUploadPathReceiving = locationUploadPath + "/Receiving";
        String locationUploadBackupPathReceving = locationUploadBackupPath + "/Receiving";
        if (log.isDebugEnabled()) {
            log.debug("esprit upload file : write file");
        }
        saveReceivingFile(locationUploadPathReceiving);
        if (log.isDebugEnabled()) {
            log.debug("esprit upload file : write file success");
        }
        // 创建文件
        // saveDeliveryFile(locationUploadPath);
        // String remoteUpPathDelivery = remoteUpPath + "/Delivery";
        // String locationUploadPathDelivery = locationUploadPath + "/Delivery";
        // String locationUploadBackupPathDelivery = locationUploadBackupPath + "/Delivery";
        try {
            if (log.isDebugEnabled()) {
                log.debug("esprit upload file : send file to sftp");
            }
            SFTPUtil.sendFile(config.get(Constants.ESPRIT_FTP_URL), config.get(Constants.ESPRIT_FTP_PORT), config.get(Constants.ESPRIT_FTP_USERNAME), config.get(Constants.ESPRIT_FTP_PASSWORD), remoteUpPathReceving, locationUploadPathReceiving,
                    locationUploadBackupPathReceving);
            if (log.isDebugEnabled()) {
                log.debug("esprit upload file : send file to sftp success");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        // try {
        // SFTPUtil.sendFile(config.get(Constants.ESPRIT_FTP_URL),
        // config.get(Constants.ESPRIT_FTP_PORT), config.get(Constants.ESPRIT_FTP_USERNAME),
        // config.get(Constants.ESPRIT_FTP_PASSWORD), remoteUpPathDelivery,
        // locationUploadPathDelivery,
        // locationUploadBackupPathDelivery);
        // } catch (Exception e) {
        // log.error("", e);
        // }
        if (log.isInfoEnabled()) {
            log.info("esprit upload file end");
        }
    }

    @Override
    public void saveReceivingFile(String locationUploadPath) {
        try {
            espOrderManager.writeReceivingDataToFile(locationUploadPath);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Override
    public void saveDeliveryFile(String locationUploadPath) {
        try {
            espOrderManager.writeDeliveryDataToFile(locationUploadPath + "/Delivery");
        } catch (Exception e) {
            log.error("", e);
        }
    }


    private void saveOrderToTable(String localPath, String localBackupPath) {
        File[] localInFileList = FileUtil.getDirFile(localPath);
        if (localInFileList == null) return;
        for (File file : localInFileList) {
            if (file.length() > 0) {
                try {
                    EspritOrderRootXml root = unmarshallerEspOrder(file);
                    espOrderManager.saveEspritOrderData(root, file, localBackupPath);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }



    private EspritOrderRootXml unmarshallerEspOrder(File file) {
        try {
            JAXBContext cxt = JAXBContext.newInstance(EspritOrderRootXml.class);
            Unmarshaller unmarshaller = cxt.createUnmarshaller();
            return (EspritOrderRootXml) unmarshaller.unmarshal(file);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    // 大仓收货未反馈邮件通知
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void receivedNotUploadEmailInform() {
        espOrderManager.receivedNotUploadEmailInform();
    }

    // 创入库单
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateSta() {
        VmiInterface vmiit = f.getBrandVmi(Constants.ESPRIT_VIM_CODE);
        vmiit.generateInboundSta();
    }

    // 创转仓（调拨）单
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateTransferSta() {
        VmiInterface vmiit = f.getBrandVmi(Constants.ESPRIT_VIM_CODE);
        vmiit.generateVMITranscationWH();
    }

    // 转入指令
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void downloadEspritDispatchInfo() {
        // 下载下所有文件
        if (log.isInfoEnabled()) {
            log.info("====>Esprit download dispatch info task begin");
        }
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.ESPRIT_RD_GROUP);
        String localDownloadPath = config.get(Constants.ESPRIT_RD_FTP_DOWN_LOCALPATH);
        String localDownloadBackupPath = config.get(Constants.ESPRIT_RD_FTP_DOWN_LOCALPATH_BK);
        String remoteDownloadOrderPath = config.get(Constants.ESPRIT_RD_FTP_DOWNPATH);
        if (log.isDebugEnabled()) {
            log.debug("====>Esprit download sftp config:" + (null != config ? config.toString() : ""));
        }
        try {
            SFTPUtil.readFile(config.get(Constants.ESPRIT_RD_FTP_URL), config.get(Constants.ESPRIT_RD_FTP_PORT), config.get(Constants.ESPRIT_RD_FTP_USERNAME), config.get(Constants.ESPRIT_RD_FTP_PASSWORD), remoteDownloadOrderPath, localDownloadPath, null,
                    true, config.get(Constants.ESPRIT_RD_FTP_DOWNLOAD_FILE_START_STR));
        } catch (Exception e) {
            log.error("", e);
        }
        // String localDownloadPath = "D:/baozun/work/esprit/locPath";
        // String localDownloadBackupPath = "D:/baozun/work/esprit/locBackPath";
        saveEspritDispatchDate(localDownloadPath, localDownloadBackupPath);
        if (log.isInfoEnabled()) {
            log.info("====>Esprit download dispatch info task end");
        }
    }

    private void saveEspritDispatchDate(String localDownloadPath, String localDownloadBackupPath) {
        File[] localInFileList = FileUtil.getDirFile(localDownloadPath);
        if (localInFileList == null) return;
        Pattern p = Pattern.compile("^\\S+\\.(xml|XML)$");
        for (File file : localInFileList) {
            if (file.length() > 0) {
                String fName = StringUtils.trimWhitespace(file.getName());
                Matcher m = p.matcher(fName);
                if (m.matches()) {
                    try {
                        EspDispatch root = unmarshallerEspritDispatchDate(file);
                        espRDeliveryManager.saveEspritDispatchDate(root, file, localDownloadBackupPath);
                    } catch (Exception e) {
                        String fileName = (null != file ? file.getName() : "");
                        log.error("====>Esprit dispatch file [" + fileName + "] handler exception:", e);
                    }
                }
            }
        }
    }

    private EspDispatch unmarshallerEspritDispatchDate(File file) {
        try {
            JAXBContext cxt = JAXBContext.newInstance(EspDispatch.class);
            Unmarshaller unmarshaller = cxt.createUnmarshaller();
            return (EspDispatch) unmarshaller.unmarshal(file);
        } catch (Exception e) {
            String fileName = (null != file ? file.getName() : "");
            log.error("====>Esprit dispatch xml source file [" + fileName + "] transform to java bean error:", e);
            return null;
        }
    }

    // 创转入单
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateEspritDispatchSta() {
        // VmiInterface vmiit = f.getBrandVmi("4046655000664");
        // vmiit.generateInboundStaGetAllOrderCode();
        // espRDeliveryManager.generateInboundSta();
        // 转入数据，创入库单
        List<ESPDeliveryReceiveCommand> esList = espRDeliveryManager.getReceiveDatasGroupByBatchNoAndPN(null);
        for (ESPDeliveryReceiveCommand espComd : esList) {
            try {
                espRDeliveryManager.generateNewInboundSta(espComd, Constants.ESPRIT_VIM_CODE);
            } catch (Exception e) {}
        }
    }

    // 转入转出反馈,关闭反馈
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inOutboundEspritDeliveryRtn() {
        // espRDeliveryManager.inOutBoundRtn();
        // 收货单关闭反馈
        // espRDeliveryManager.inBoundCloseRtn();

        // 差异收货推送
        // espRDeliveryManager.inOutBoundRtn2();
        // 差异收货关闭反馈
        // espRDeliveryManager.inBoundCloseRtn2();

        if (log.isInfoEnabled()) {
            log.info("====>Esprit inOutBound data feedback task begin");
        }
        // 转出反馈Pacs
        outBoundRtn();
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.ESPRIT_RD_GROUP);
        String localUpPath = config.get(Constants.ESPRIT_RD_FTP_UP_LOCALPATH);
        String localBackupPath = config.get(Constants.ESPRIT_RD_FTP_UP_LOCALPATH_BK);
        String remoteUpPath = config.get(Constants.ESPRIT_RD_FTP_UPPATH);
        if (log.isDebugEnabled()) {
            log.debug("====>Esprit download sftp config:" + (null != config ? config.toString() : ""));
        }
        // 转入生产文件反馈
        inBoundFeedback(localUpPath);
        // 收货单关闭生成文件反馈
        inBoundCloseFeedback(localUpPath);
        try {
            if (log.isDebugEnabled()) {
                log.debug("esprit upload file : send file to sftp");
            }
            SFTPUtil.sendFile(config.get(Constants.ESPRIT_RD_FTP_URL), config.get(Constants.ESPRIT_RD_FTP_PORT), config.get(Constants.ESPRIT_RD_FTP_USERNAME), config.get(Constants.ESPRIT_RD_FTP_PASSWORD), remoteUpPath, localUpPath, localBackupPath);
            if (log.isDebugEnabled()) {
                log.debug("esprit upload file : send file to sftp success");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        if (log.isInfoEnabled()) {
            log.info("====>Esprit inOutBound data feedback task end");
        }
    }

    private void outBoundRtn() {
        if (log.isDebugEnabled()) {
            log.debug("====>Esprit send delivery info to pacs start");
        }
        espRDeliveryManager.outBoundRtn();
        if (log.isDebugEnabled()) {
            log.debug("====>Esprit send delivery info to pacs end");
        }
    }

    private void inBoundFeedback(String localUpPath) {
        if (log.isDebugEnabled()) {
            log.debug("====>Esprit upload dispatch file to localUpPath start");
        }
        espRDeliveryManager.inBoundFeedback(localUpPath);
        if (log.isDebugEnabled()) {
            log.debug("====>Esprit upload dispatch file to localUpPath end");
        }
    }

    private void inBoundCloseFeedback(String localUpPath) {
        if (log.isDebugEnabled()) {
            log.debug("====>Esprit upload dispatch close file to localUpPath start");
        }
        espRDeliveryManager.inBoundCloseFeedback(localUpPath);
        if (log.isDebugEnabled()) {
            log.debug("====>Esprit upload dispatch close file to localUpPath end");
        }
    }


    public void sendMqByPac() {
        List<QueueSta> qStaList = queueStaDao.queryByErrorCount();
        if (null != qStaList && qStaList.size() > 0) {
            List<Long> idList = new ArrayList<Long>();
            for (QueueSta list : qStaList) {
                // 发送MQ
                MessageCommond mc = new MessageCommond();
                mc.setMsgId(list.getId() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                mc.setTags(list.getMainwhouid().toString());
                mc.setMsgType("com.jumbo.wms.manager.hub2wms.BaseManagerImpl.sendMqByPac");
                mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                mc.setMsgBody(JsonUtil.writeValue(idList.add(list.getId())));
                mc.setIsMsgBodySend(false);
                try {
                    producerServer.sendDataMsgConcurrently(MqStaticEntity.WMS3_MQ_CREATE_STA_PAC, mc);
                    list.setErrorMqCount(10);
                } catch (Exception e) {
                    list.setErrorMqCount(5);
                    log.debug("sendMqByPac:" + list.getId(), e.toString());
                }
                MongoDBMessage mdbm = new MongoDBMessage();
                BeanUtils.copyProperties(mc, mdbm);
                mdbm.setStaCode(list.getId().toString());
                mdbm.setOtherUniqueKey(list.getOrdercode());
                mongoOperation.save(mdbm);
                idList.clear();
            }
        }
    }

    public void sendMqByOms() {
        List<WmsSalesOrderQueue> wmsSalesOrderQueueList = wmsSalesOrderQueueDao.queryErrorCount();
        if (null != wmsSalesOrderQueueList && wmsSalesOrderQueueList.size() > 0) {
            List<Long> idList = new ArrayList<Long>();
            for (WmsSalesOrderQueue list : wmsSalesOrderQueueList) {
                MessageCommond mc = new MessageCommond();
                mc.setMsgId(list.getId() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                if (list.isAllowDS()) {
                    List<WmsSalesOrderLineQueue> lists = wmsSalesOrderLineQueueDao.getOrderLineQueueById(list.getId(), new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
                    if (null != lists) {
                        mc.setTags(lists.get(0).getOuId().toString());
                    }
                } else {
                    mc.setTags(ouDao.getByCode(list.getWarehouseCode()).getId().toString());
                }
                mc.setMsgType("com.jumbo.wms.manager.hub2wms.BaseManagerImpl.sendMqByOms");
                mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                mc.setMsgBody(JsonUtil.writeValue(idList.add(list.getId())));
                mc.setIsMsgBodySend(false);
                try {
                    producerServer.sendDataMsgConcurrently(MqStaticEntity.WMS3_MQ_CREATE_STA_OMS, mc);
                    list.setErrorCount(10);
                } catch (Exception e) {
                    log.debug("sendMqByOms:" + list.getId(), e.toString());
                    list.setErrorCount(5);
                    // 失败处理
                }
                MongoDBMessage mdbm = new MongoDBMessage();
                BeanUtils.copyProperties(mc, mdbm);
                mdbm.setStaCode(list.getId().toString());
                mdbm.setOtherUniqueKey(list.getOrderCode());
                mongoOperation.save(mdbm);
                idList.clear();
            }
        }
    }
}
