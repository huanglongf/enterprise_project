package com.jumbo.wms.manager.task.vmi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.util.FileUtil;
import com.jumbo.util.FtpUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.CKTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.ckData.CKParseDataManager;
import com.jumbo.wms.manager.vmi.ckData.CKParseDataManagerProxy;

@Service("ckTask")
public class CKTaskImpl extends BaseManagerImpl implements CKTask {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4307275679609461558L;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private CKParseDataManager ckParseManager;
    @Autowired
    private CKParseDataManagerProxy ckParseDataManagerProxy;

    /**
     * 退货入库通知、出库通知
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inOutBoundNotify() {
        log.debug("------task-----CK inOutBoundNotify---------start------------");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CKFTP_CONFIG_GROUP);
        String localUpFileDir = cfg.get(Constants.CK_FTP_UP_LOCALPATH);

        // 1.入库通知
        try {
            ckParseManager.writeInbound(localUpFileDir);
        } catch (Exception e) {
            log.error("", e);
        }

        // 2.出库通知
        try {
            ckParseManager.writeOutbound(localUpFileDir);
        } catch (Exception e) {
            log.error("", e);
        }
        // 上传出库通知文件到ftp
        uploadFile();

        log.debug("------task-----CK inOutBoundNotify---------end------------");
    }

    /**
     * 执行出入库
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void executeOutAndInbound() {
        downloadAndImput();
        // 执行出库操作
        try {
            ckParseDataManagerProxy.executeOBFeedBack();
        } catch (Exception e) {
            log.error("", e);
        }

        // 执行入库操作
        try {
            ckParseDataManagerProxy.executeIBFeedBack();
        } catch (Exception e) {
            log.error("", e);
        }

        log.debug("------task-----CK import Data---------end------------");
    }

    public void downloadAndImput() {
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CKFTP_CONFIG_GROUP);
        String dirPath = cfg.get(Constants.CK_FTP_DOWN_LOCALPATH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String bakPath = dirPath + File.separator + Constants.CK_BAK_DIR + File.separator + sdf.format(new Date());
        // 下载
        try {
            downloadFile(cfg);
        } catch (Exception e) {
            log.error("", e);
        }

        // 导入
        try {
            importData(dirPath, bakPath);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void downloadInvDataAndImput() {
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CK_INV_FTP_CONFIG_GROUP);
        String dirPath = cfg.get(Constants.CK_FTP_DOWN_LOCALPATH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String bakPath = dirPath + File.separator + Constants.CK_BAK_DIR + File.separator + sdf.format(new Date());
        // 下载
        try {
            String localDownFileDir = cfg.get(Constants.CK_FTP_DOWN_LOCALPATH);
            int result =
                    FtpUtil.ckReadFile(cfg.get(Constants.CK_FTP_URL), cfg.get(Constants.CK_FTP_PORT), cfg.get(Constants.CK_FTP_USERNAME), cfg.get(Constants.CK_FTP_PASSWORD), "", cfg.get(Constants.CK_FTP_DOWNPATH), cfg.get(Constants.CK_FTP_DOWNPATH)
                            + "/bak", localDownFileDir, true);
            if (result == 0) {
                log.debug("{}… ………download file success", new Object[] {new Date()});
            }
        } catch (Exception e) {
            log.error("", e);
        }
        // 导入
        if (dirPath == null || dirPath.equals("")) {
            log.debug("Import CK task File Dir is not exist!");
            return;
        }
        File dir = new File(dirPath);
        List<File> fileList = FileUtil.traveFile(dir.getAbsolutePath());
        if (null != fileList && fileList.size() > 0) {
            for (File file : fileList) {
                try {
                    String fileName = file.getName();
                    if (fileName.startsWith("INV")) {
                        ckParseManager.parseInventoryDataForInvStart(file, bakPath);
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        } else {
            com.jumbo.util.FileUtil.deleteFile(dir.getAbsolutePath());
        }
    }

    // 初始化库存
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void synchronizeInventory() {
        // 下载导入。
        downloadInvDataAndImput();
        // 初始化SKU
        generateCKSku();
        // 初始化库存
        generateInventoryCheck();
    }

    private void downloadFile(Map<String, String> cfg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String bakFile = cfg.get(Constants.CK_FTP_DOWNPATH) + File.separator + Constants.CK_BAK_DIR + File.separator + date;
        String localDownFileDir = cfg.get(Constants.CK_FTP_DOWN_LOCALPATH);
        int result = FtpUtil.ckReadFile(cfg.get(Constants.CK_FTP_URL), cfg.get(Constants.CK_FTP_PORT), cfg.get(Constants.CK_FTP_USERNAME), cfg.get(Constants.CK_FTP_PASSWORD), "", cfg.get(Constants.CK_FTP_DOWNPATH), bakFile, localDownFileDir, true);
        if (result == 0) {
            log.debug("{}… ………download file success", new Object[] {new Date()});
        }
    }

    private void uploadFile() {
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CKFTP_CONFIG_GROUP);
        String localUpFileDir = cfg.get(Constants.CK_FTP_UP_LOCALPATH);
        File dir = new File(localUpFileDir);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String bakDir = dir + File.separator + "bak" + File.separator + sdf.format(new Date()) + File.separator;
        if (dir.listFiles() != null && dir.listFiles().length != 0) {
            for (File f : dir.listFiles()) {
                boolean flag = false;
                String upfileDir = cfg.get(Constants.CK_FTP_UPPATH);
                if (f.isDirectory()) {
                    continue;
                }
                flag = FtpUtil.sendFile(cfg.get(Constants.CK_FTP_URL), cfg.get(Constants.CK_FTP_PORT), cfg.get(Constants.CK_FTP_USERNAME), cfg.get(Constants.CK_FTP_PASSWORD), upfileDir, f.getAbsolutePath());
                if (flag) {
                    com.jumbo.util.FileUtil.copyFile(f.getAbsolutePath(), bakDir);
                    com.jumbo.util.FileUtil.deleteFile(f.getAbsolutePath());
                    log.debug("{}.......upload success", new Object[] {new Date()});
                }
            }
        }
    }

    private void importData(String dirPath, String bakPath) {
        if (dirPath == null || dirPath.equals("")) {
            log.debug("Import CK task File Dir is not exist!");
            return;
        }
        File dir = new File(dirPath);
        List<File> fileList = FileUtil.traveFile(dir.getAbsolutePath());
        if (null != fileList && fileList.size() > 0) {
            for (File file : fileList) {
                try {
                    String fileName = file.getName();
                    String temp[] = fileName.split("_");
                    String type = temp[0];
                    if (type != null) {
                        if (type.equals("FBR")) {
                            ckParseManager.parseInBoundData(file, bakPath);
                        } else if (type.equals("FBS")) {
                            ckParseManager.parseOutBoundData(file, bakPath);
                        } else if (type.equals("Item")) {
                            ckParseManager.parseProductData(file, bakPath);
                        }
                    }
                } catch (Exception e) {
                    log.error("", e);
                }

            }
        } else {
            com.jumbo.util.FileUtil.deleteFile(dir.getAbsolutePath());
        }
    }

    public void generateCKSku() {
        // List<SkuCommand> skuList = skuDao.findSKUCommandFromCKData(new
        // BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
        // for (SkuCommand sku : skuList) {
        // // ckParseManager.generateCKSKU(sku, Constants.SKU_TEMPLATE_ID, Constants.CK_BS_SHOP_ID);
        // }
    }

    /**
     * 初始化库存
     */
    public void generateInventoryCheck() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        ckParseManager.generateInventoryCheck(sdf.format(date));
    }
}
