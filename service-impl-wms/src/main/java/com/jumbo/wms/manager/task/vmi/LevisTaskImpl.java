package com.jumbo.wms.manager.task.vmi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.ConstantsFtpConifg;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.LevisTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.ftp.FtpUtilManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.VmiFileManager;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.levisData.LevisChildrenManager;
import com.jumbo.wms.manager.vmi.levisData.LevisManager;
import com.jumbo.wms.manager.warehouse.vmi.VmiStaCreateManagerProxy;
import com.jumbo.wms.model.baseinfo.BiChannel;

@Service("levisTask")
public class LevisTaskImpl extends BaseManagerImpl implements LevisTask {

    private static final long serialVersionUID = -2040110696767184435L;
    // 收货文件名前缀
    public static final String LEVIS_STOCKIN_FILE_NAME_START = "LSCN_PRTRDX";
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private FtpUtilManager ftpUtilManager;
    @Autowired
    private LevisManager levisManager;
    @Resource(name = "vmiLevis")
    private VmiInterface vmiLevis;
    @Autowired
    private VmiFileManager vmiFileManager;
    @Autowired
    private VmiStaCreateManagerProxy vmiStaCreateManagerProxy;
    @Autowired
    private LevisChildrenManager levisChildrenManager;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void downloadFile() {
        log.info("downloadFile Debug===============");
        ftpUtilManager.downloadFile(ConstantsFtpConifg.FTP_CONFIG_GROUP_LEVIS, null, null);
        readFile();
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createSkmrData() {
        // 获取前一天的skmr数据
        Date date = new Date();// 取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        System.out.println(dateString);
        levisManager.createSkmrData(dateString);
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createStkrData() {
        levisManager.createStkrData();
    }

    private void readFile() {
        Map<String, String> config = configManager.getCommonFTPConfig(ConstantsFtpConifg.FTP_CONFIG_GROUP_LEVIS);
        String localPath = config.get(ConstantsFtpConifg.FTP_KEY_LOCAL_DOWNLOAD_PATH);
        String localBackupPath = config.get(ConstantsFtpConifg.FTP_KEY_LOCAL_DOWNLOAD_BACKUP_PATH);
        if (!StringUtil.isEmpty(localPath)) {
            File f = new File(localPath);
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        if (file.getName().startsWith(LEVIS_STOCKIN_FILE_NAME_START)) {
                            levisManager.readStockInFile(file, localBackupPath);
                        }
                    }
                }
            }
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateInboundSta() {
        vmiLevis.generateInboundSta();
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void downloadFtpDataAndCreateStaForLevisShoes() {
        Map<String, String> cfg = configManager.getCommonFTPConfig(ConstantsFtpConifg.FTP_CONFIG_GROUP_LEVIS_CHILDREN);
        String localFileDir = cfg.get(ConstantsFtpConifg.FTP_KEY_LOCAL_DOWNLOAD_PATH);
        String bakFileDir = cfg.get(ConstantsFtpConifg.FTP_KEY_LOCAL_UPLOAD_BACKUP_PATH);
        // 下载FTP文件到本地
        try {
            SFTPUtil.readFile(cfg.get(ConstantsFtpConifg.FTP_KEY_URL), cfg.get(ConstantsFtpConifg.FTP_KEY_PORT), cfg.get(ConstantsFtpConifg.FTP_KEY_USERNAME), cfg.get(ConstantsFtpConifg.FTP_KEY_PASSWORD),
                    cfg.get(ConstantsFtpConifg.FTP_KEY_REMOTE_DOWNLOAD_PATH), localFileDir, null, true, null);
        } catch (Exception e) {
            log.error("", e);
        }
        // 解析本地文件保存到数据库并备份
        try {
            vmiFileManager.inBoundreadFileIntoDB(localFileDir, bakFileDir, cfg.get(Constants.NIKE_FTP_DOWNLOAD_FILE_START_STR), BiChannel.CHANNEL_VMICODE_LEVIS);
        } catch (Exception e1) {
            log.error("", e1);
        }
        // 创建入库单据
        try {
            vmiStaCreateManagerProxy.generateVmiInboundStaByVmiCode(BiChannel.CHANNEL_VMICODE_LEVIS);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadLeivsData() {
        Map<String, String> cfg = configManager.getCommonFTPConfig(ConstantsFtpConifg.FTP_CONFIG_GROUP_LEVIS_CHILDREN);
        String localFileDir = cfg.get(ConstantsFtpConifg.FTP_KEY_LOCAL_UPLOAD_PATH);
        try {
            // 入库反馈写文件
            try {
                levisChildrenManager.inBoundTransferInBoundWriteToFile(localFileDir);
            } catch (Exception e) {
                log.error("", e);
            }
            
            //退仓反馈写文件
            try {
                levisChildrenManager.vmiReturnReceiveWriteToFile(localFileDir);
            } catch (Exception e) {
                log.error("levisxy 退仓反馈异常",e);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        try {
            SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_UPPATH),
                    cfg.get(ConstantsFtpConifg.FTP_KEY_LOCAL_UPLOAD_PATH), cfg.get(Constants.NIKE_FTP_UP_LOCALPATH_BACKUP));
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
