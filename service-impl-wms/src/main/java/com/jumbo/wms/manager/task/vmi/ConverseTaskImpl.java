package com.jumbo.wms.manager.task.vmi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jcraft.jsch.ChannelSftp;
import com.jumbo.util.FileUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.ConverseTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.converseData.ConverseVmiReceiveManager;
import com.jumbo.wms.manager.vmi.converseData.ConverseVmiStockInManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.vmi.itData.ConverseTaskType;
import com.jumbo.wms.model.vmi.itData.VMIReceiveInfoType;

@Service("converseTask")
public class ConverseTaskImpl extends BaseManagerImpl implements ConverseTask {

    private static final long serialVersionUID = 3582504427477959864L;
    @Autowired
    private VmiFactory f;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private ConverseVmiReceiveManager receiveManager;
    @Autowired
    private ConverseVmiStockInManager stockInManager;

    public void conversSendFile() {
        // Map<String, String> config =
        // configManager.getCommonFTPConfig(Constants.CONVERSE_RP_GROUP);
        // SFTPUtil.uploadLocalPath(config.get(Constants.CONVERSE_RP_FTP_URL),
        // config.get(Constants.CONVERSE_RP_FTP_PORT),
        // config.get(Constants.CONVERSE_RP_FTP_USERNAME),
        // config.get(Constants.CONVERSE_RP_FTP_PASSWORD), config
        // .get(Constants.CONVERSE_RP_FTP_UP_LOCALPATH),
        // config.get(Constants.CONVERSE_RP_FTP_UPPATH),
        // config.get(Constants.CONVERSE_RP_FTP_UP_LOCALPATH_BK));
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void startConverseTasks() {
        log.info("startConverseTasks1");
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
        SimpleDateFormat sdf2 = new SimpleDateFormat("mm");
        String hour = sdf1.format(date);
        String min = sdf2.format(date);
        // 30分钟一次
        if (min.equals("30")) {
            outputTransferOutFile();// 半小时一次
            if (hour.equals("00") || hour.equals("11")) {
                uploadSalesFile();
            }
        } else {
            inputASN();
            outputTransferOutFile();
            outputAdjustmentFile();
            outputInvStatusChangeFile();
            outputReceiveFile();
            if (hour.equals("10")) {
                inputMastData();
                inputProductInfo();
                inputEverGreenData();
                inputPriceChangeData();
            }
            if (hour.equals("11") || hour.equals("21")) {
                outputRTV();
            }
            if (hour.equals("00")) {
                outputInvSnapshot();
                // inputListPriceChange();
            }
        }
        log.info("startConverseTasks2");
    }

    /**
     * 创入库单
     */
    public void generateSta() {
        // resetAccountSet();
        VmiInterface vmiConverse = f.getBrandVmi(BiChannel.CHANNEL_VMICODE_CONVERSE_STORE);
        vmiConverse.generateInboundSta();
    }

    /**
     * 导出收货确认文件
     */
    public void outputReceiveFile() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String filePath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_ASN_RECEIVE;
            if (filePath == null || filePath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            String localBakPath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_ASN_RECEIVE;
            receiveManager.generateVMIReceiveFile(filePath, sdf.format(date), VMIReceiveInfoType.RECEIVE);

            String upPath = config.get(Constants.CONVERSE_FTP_UPPATH) + "/" + Constants.CONVERSE_FTP_ASN_RECEIVE;
            if (upPath == null || upPath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            // 上传文件
            upload(upPath, filePath, localBakPath, config);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导出转店退仓反馈
     */
    public void outputTransferOutFile() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String filePath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_TRANSFER;
            if (filePath == null || filePath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            String localBakPath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_TRANSFER;
            receiveManager.generateVMIReceiveFile(filePath, sdf.format(date), VMIReceiveInfoType.TRANSFEROUT);

            String upPath = config.get(Constants.CONVERSE_FTP_UPPATH) + "/" + Constants.CONVERSE_FTP_TRANSFER;
            if (upPath == null || upPath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            // 上传文件
            upload(upPath, filePath, localBakPath, config);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导出退仓反馈
     */
    public void outputRTV() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String filePath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_RA;
            if (filePath == null || filePath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            String localBakPath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "//" + Constants.CONVERSE_FTP_RA;
            receiveManager.generateVMIReceiveFile(filePath, sdf.format(date), VMIReceiveInfoType.RTV);

            String upPath = config.get(Constants.CONVERSE_FTP_UPPATH) + "/" + Constants.CONVERSE_FTP_RA;
            if (upPath == null || upPath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            // 上传文件
            upload(upPath, filePath, localBakPath, config);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导出调整数据
     */
    public void outputAdjustmentFile() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String filePath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_IA;
            if (filePath == null || filePath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            String localBakPath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_IA;
            // 生成文件
            receiveManager.generateVMIAdjustmentFile(filePath, sdf.format(date));

            String upPath = config.get(Constants.CONVERSE_FTP_UPPATH) + "/" + Constants.CONVERSE_FTP_IA;
            if (upPath == null || upPath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            // 上传文件
            upload(upPath, filePath, localBakPath, config);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导出库存状态调整文档
     */
    public void outputInvStatusChangeFile() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String filePath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_BINTOBIN;
            if (filePath == null || filePath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            String localBakPath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_BINTOBIN;
            // 生成文件
            receiveManager.generateInvStatusChangeFile(filePath, sdf.format(date));

            String upPath = config.get(Constants.CONVERSE_FTP_UPPATH) + "/" + Constants.CONVERSE_FTP_BINTOBIN;
            if (upPath == null || upPath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            // 上传文件
            log.debug("=============filePath:  " + filePath + "====================");
            upload(upPath, filePath, localBakPath, config);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导出库存快照
     */
    public void outputInvSnapshot() {
        try {
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String filePath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_RIB;
            if (filePath == null || filePath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            String localBakPath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_RIB;
            // 生成文件
            receiveManager.exportInventorySnapShot(BiChannel.CHANNEL_VMICODE_CONVERSE_STORE, filePath);
            Thread.sleep(6000);
            receiveManager.exportInventorySnapShot(BiChannel.CHANNEL_VMICODE_CONVERSE_TMALL, filePath);

            String upPath = config.get(Constants.CONVERSE_FTP_UPPATH) + "/" + Constants.CONVERSE_FTP_RIB;;
            if (upPath == null || upPath.equals("")) {
                log.debug("the filePath path is not exist");
                return;
            }
            // 上传文件
            upload(upPath, filePath, localBakPath, config);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void uploadSalesFile() {
        try {
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String remotePath = config.get(Constants.CONVERSE_FTP_UPPATH);
            if (remotePath == null || remotePath.equals("")) {
                log.debug("the remotePath path is not exist");
                return;
            }
            remotePath = remotePath + "/" + Constants.CONVERSE_FTP_SALES;
            String localPath = config.get(Constants.CONVERSE_FTP_UP_LOCALPATH);
            if (localPath == null || localPath.equals("")) {
                log.debug("the remotePath path is not exist");
                return;
            }
            String localBakPath = localPath + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_SALES;
            localPath = localPath + "/" + Constants.CONVERSE_FTP_SALES;
            upload(remotePath, localPath, localBakPath, config);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导入入库指令文件
     */
    public void inputASN() {
        try {
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String remotePath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" + Constants.CONVERSE_FTP_ASN;
            if (remotePath == null || remotePath.equals("")) {
                log.debug("the remotePath path is not exist");
                return;
            }
            String localPath = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" + Constants.CONVERSE_FTP_ASN;
            if (localPath == null || localPath.equals("")) {
                log.debug("the remotePath path is not exist");
                return;
            }
            String remoteBakPath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_ASN;
            if (remoteBakPath == null || remoteBakPath.equals("")) {
                log.debug("the remoteBakPath path is not exist");
                return;
            }
            download(remotePath, localPath, remoteBakPath, config);
            String bakDir = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/";
            String filePath = localPath + "/";
            importConverseTaskFile(filePath, bakDir, ConverseTaskType.ASN);
            generateSta();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导入入库指令文件
     */
    public void inputProductInfo() {
        try {
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String remotePath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" + Constants.CONVERSE_FTP_PRODUCTINFO;
            if (remotePath == null || remotePath.equals("")) {
                log.debug("the remotePath path is not exist");
                return;
            }
            String localPath = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" + Constants.CONVERSE_FTP_PRODUCTINFO;
            if (localPath == null || localPath.equals("")) {
                log.debug("the remotePath path is not exist");
                return;
            }
            String remoteBakPath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_PRODUCTINFO;
            if (remoteBakPath == null || remoteBakPath.equals("")) {
                log.debug("the remoteBakPath path is not exist");
                return;
            }
            download(remotePath, localPath, remoteBakPath, config);
            String bakDir = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/";
            String filePath = localPath + "/";
            importConverseTaskFile(filePath, bakDir, ConverseTaskType.PRODUCTINFO);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导入基础信息
     */
    public void inputMastData() {
        // try {
        // Map<String, String> config = configManager.getConverseFTPConfig();
        // if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) ==
        // null) {
        // log.error("## no task available for task batch [accountSet={}] ", new Object[]
        // {"default"});
        // return;
        // }
        // String remotePath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" +
        // Constants.CONVERSE_FTP_STYLE;
        // if (remotePath == null || remotePath.equals("")) {
        // log.error("the remotePath path is not exist");
        // return;
        // }
        // String localPath = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" +
        // Constants.CONVERSE_FTP_STYLE;
        // if (localPath == null || localPath.equals("")) {
        // log.error("the remotePath path is not exist");
        // return;
        // }
        // String remoteBakPath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" +
        // Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_STYLE;
        // if (remoteBakPath == null || remoteBakPath.equals("")) {
        // log.error("the remoteBakPath path is not exist");
        // return;
        // }
        // download(remotePath, localPath, remoteBakPath, config);
        // String bakDir = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" +
        // Constants.CONVERSE_FTP_FINISH_DIR + "/";
        // String filePath = localPath + "/";
        // importConverseTaskFile(filePath, bakDir, ConverseTaskType.MASTER);
        // } catch (Exception e) {
        // log.error("", e);
        // }
    }

    /**
     * 导入价格调整
     */
    public void inputPriceChangeData() {
        // try {
        // Map<String, String> config = configManager.getConverseFTPConfig();
        // if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) ==
        // null) {
        // log.error("## no task available for task batch [accountSet={}] ", new Object[]
        // {"default"});
        // return;
        // }
        // String remotePath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" +
        // Constants.CONVERSE_FTP_PRICE;
        // if (remotePath == null || remotePath.equals("")) {
        // log.error("the remotePath path is not exist");
        // return;
        // }
        // String localPath = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" +
        // Constants.CONVERSE_FTP_PRICE;
        // if (localPath == null || localPath.equals("")) {
        // log.error("the remotePath path is not exist");
        // return;
        // }
        // String remoteBakPath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" +
        // Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_PRICE;
        // if (remoteBakPath == null || remoteBakPath.equals("")) {
        // log.error("the remoteBakPath path is not exist");
        // return;
        // }
        // download(remotePath, localPath, remoteBakPath, config);
        // String bakDir = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" +
        // Constants.CONVERSE_FTP_FINISH_DIR + "/";
        // String filePath = localPath + "/";
        // importConverseTaskFile(filePath, bakDir, ConverseTaskType.PRICECHANGE);
        // } catch (Exception e) {
        // log.error("", e);
        // }
    }

    /**
     * 吊牌价格修改
     */
    public void inputListPriceChange() {
        try {
            Map<String, String> config = configManager.getConverseFTPConfig();
            if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
                log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
                return;
            }
            String remotePath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" + Constants.CONVERSE_FTP_LISTPRCE;
            if (remotePath == null || remotePath.equals("")) {
                log.debug("the remotePath path is not exist");
                return;
            }
            String localPath = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" + Constants.CONVERSE_FTP_LISTPRCE;
            if (localPath == null || localPath.equals("")) {
                log.debug("the remotePath path is not exist");
                return;
            }
            String remoteBakPath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_LISTPRCE;
            if (remoteBakPath == null || remoteBakPath.equals("")) {
                log.debug("the remoteBakPath path is not exist");
                return;
            }
            download(remotePath, localPath, remoteBakPath, config);
            String bakDir = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" + Constants.CONVERSE_FTP_FINISH_DIR + "/";
            String filePath = localPath + "/";
            importConverseTaskFile(filePath, bakDir, ConverseTaskType.LISTPRICECHANGE);
            stockInManager.changeStyleListPrice();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void changeListPrice() {

    }

    /**
     * 导入货运单据
     */
    public void inputEverGreenData() {
        // try {
        // Map<String, String> config = configManager.getConverseFTPConfig();
        // if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) ==
        // null) {
        // log.error("## no task available for task batch [accountSet={}] ", new Object[]
        // {"default"});
        // return;
        // }
        // String remotePath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" +
        // Constants.CONVERSE_FTP_EAN;
        // if (remotePath == null || remotePath.equals("")) {
        // log.error("the remotePath path is not exist");
        // return;
        // }
        // String localPath = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" +
        // Constants.CONVERSE_FTP_EAN;
        // if (localPath == null || localPath.equals("")) {
        // log.error("the remotePath path is not exist");
        // return;
        // }
        // String remoteBakPath = config.get(Constants.CONVERSE_FTP_DOWNPATH) + "/" +
        // Constants.CONVERSE_FTP_FINISH_DIR + "/" + Constants.CONVERSE_FTP_EAN;
        // if (remoteBakPath == null || remoteBakPath.equals("")) {
        // log.error("the remoteBakPath path is not exist");
        // return;
        // }
        // download(remotePath, localPath, remoteBakPath, config);
        // String bakDir = config.get(Constants.CONVERSE_FTP_DOWN_LOCALPATH) + "/" +
        // Constants.CONVERSE_FTP_FINISH_DIR + "/";
        // String filePath = localPath + "/";
        // importConverseTaskFile(filePath, bakDir, ConverseTaskType.EVERGREEN);
        // } catch (Exception e) {
        // log.error("", e);
        // }
    }

    /**
     * 抓取文件
     */
    public void download(String remotePath, String localPath, String remoteBakPath, Map<String, String> config) {

        if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
            log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
            return;
        }

        File dir = new File(localPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int result = SFTPUtil.readFile(config.get(Constants.CONVERSE_FTP_URL), config.get(Constants.CONVERSE_FTP_PORT), config.get(Constants.CONVERSE_FTP_USERNAME), config.get(Constants.CONVERSE_FTP_PASSWORD), remotePath, localPath, remoteBakPath, true);
        if (result == 0) {
            log.debug("{}… ………download file success", new Object[] {new Date()});
        }
    }

    /**
     * 上传
     */
    public void upload(String remotePath, String localPath, String localBakPath, Map<String, String> config) {
        log.debug("=============localPath:  " + localPath + "====================");
        if (config == null || config.size() == 0 || config.get(Constants.CONVERSE_FTP_URL) == null) {
            log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
            return;
        }
        File bakdir = new File(localBakPath);
        if (!bakdir.exists()) {
            bakdir.mkdirs();
        }
        List<File> fileList = traveFile(localPath);
        if (fileList.size() == 0) {
            log.debug("there is not file need to upload!");
            return;
        }
        boolean flag = false;
        ChannelSftp sftp = SFTPUtil.connect(config.get(Constants.CONVERSE_FTP_URL), Integer.parseInt(config.get(Constants.CONVERSE_FTP_PORT)), config.get(Constants.CONVERSE_FTP_USERNAME), config.get(Constants.CONVERSE_FTP_PASSWORD));
        try {
            for (File file : fileList) {
                flag = SFTPUtil.sendFile(remotePath, file.getAbsolutePath(), sftp);
                if (flag) {
                    boolean flg = com.jumbo.util.FileUtil.copyFile(file.getAbsolutePath(), localBakPath);
                    if (flg) {
                        com.jumbo.util.FileUtil.deleteFile(file.getAbsolutePath());
                        log.debug("{}.......upload success", new Object[] {new Date()});
                    }
                }
            }
        } finally {
            SFTPUtil.disconnect(sftp);
        }
    }

    private List<File> traveFile(String dirPath) {
        LinkedList<File> fileList = new LinkedList<File>();
        File dir = new File(dirPath);
        File files[] = dir.listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {
                fileList.add(f);
            }
        }
        return fileList;
    }

    /**
     * 导入指令文件到数据库
     */
    public void importConverseTaskFile(String dirpath, String bakDir, ConverseTaskType taskType) {
        if (dirpath == null || dirpath.equals("")) {
            log.debug("Import converse task File Dir is not exist!");
            return;
        }
        File dir = new File(dirpath);
        List<File> fileList = FileUtil.traveFile(dir.getAbsolutePath());
        if (null != fileList && fileList.size() > 0) {
            for (File file : fileList) {
                try {
                    importTaskFile(file, bakDir, taskType);
                } catch (IOException ioe) {
                    log.error("", ioe);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 判断文件名称是否正确
     */
    public boolean importTaskFile(File file, String bakDir, ConverseTaskType taskType) throws Exception {
        String fileName = file.getName();
        switch (taskType) {
            case MASTER:
                if (fileName.length() >= 5) {
                    if (file.getName().substring(0, 5).toUpperCase().equals("STYLE")) {
                        // 解析文件存入数据库
                        stockInManager.parseStyleData(file, bakDir);
                        return true;
                    }
                }
                break;
            case PRICECHANGE:
                if (fileName.length() >= 11) {
                    if (file.getName().substring(0, 11).toUpperCase().equals("PRICECHANGE")) {
                        // 解析文件存入数据库
                        stockInManager.parsePriceChangeData(file, bakDir);
                        return true;
                    }
                }
                break;
            case EVERGREEN:
                if (fileName.length() >= 3) {
                    if (file.getName().substring(0, 3).toUpperCase().equals("EAN")) {
                        // 解析文件存入数据库
                        stockInManager.parseEverGreenData(file, bakDir);
                        return true;
                    }
                }
                break;
            case ASN:
                if (fileName.length() >= 5) {
                    if (file.getName().substring(0, 5).toUpperCase().equals("TFXOB")) {
                        // 解析文件存入数据库
                        stockInManager.parseStockInData(file, bakDir);
                        return true;
                    }
                }
                break;

            case PRODUCTINFO:
                if (fileName.length() >= 11) {
                    if (file.getName().substring(0, 11).toUpperCase().equals("PRODUCTINFO")) {
                        // 解析文件存入数据库
                        stockInManager.parseProductInfoData(file, bakDir);
                        return true;
                    }
                }
                break;

            case LISTPRICECHANGE:
                if (fileName.length() >= 8) {
                    if (file.getName().substring(0, 8).toUpperCase().equals("DUPSTYLE")) {
                        // 解析文件存入数据库
                        stockInManager.parseListpriceData(file, bakDir);
                        return true;
                    }
                }
                break;
        }

        return false;
    }
}
