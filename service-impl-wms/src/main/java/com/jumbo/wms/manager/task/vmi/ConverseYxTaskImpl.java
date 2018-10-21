package com.jumbo.wms.manager.task.vmi;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.ConverseYxTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.VmiFileManager;
import com.jumbo.wms.manager.vmi.converseYxData.ConverseYxManager;
import com.jumbo.wms.manager.warehouse.vmi.VmiStaCreateManagerProxy;
import com.jumbo.wms.model.baseinfo.BiChannel;

/**
 * Converse永兴定时任务入库
 * 
 * @author jinlong.ke
 * 
 */
public class ConverseYxTaskImpl extends BaseManagerImpl implements ConverseYxTask {
    /**
     * 
     */
    private static final long serialVersionUID = -8247562910599798426L;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private ConverseYxManager converseYxManager;
    @Autowired
    private VmiFileManager vmiFileManager;
    @Autowired
    private VmiStaCreateManagerProxy vmiStaCreateManagerProxy;

    /**
     *1、下载FTP文件到本地<br/>
     *2、 解析本地文件保存到数据库并备份<br/>
     *3、创单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void downloadFtpDataAndCreateStaForConverseYx() {
        log.info("downloadFtpDataAndCreateStaForConverseYx1");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_CONVERSE_YX_FTP);
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.NIKE_FTP_DOWN_LOCALPATH_BACKUP);
        // 下载FTP文件到本地
        try {
            SFTPUtil.readFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_DOWNPATH), localFileDir, null, true, cfg
                    .get(Constants.NIKE_FTP_DOWNLOAD_FILE_START_STR));
        } catch (Exception e) {
            log.error("", e);
        }
        // 解析本地文件保存到数据库并备份
        try {
            vmiFileManager.inBoundreadFileIntoDB(localFileDir, bakFileDir, cfg.get(Constants.NIKE_FTP_DOWNLOAD_FILE_START_STR), BiChannel.CHANNEL_VMICODE_CONVERSE_YX);
        } catch (Exception e1) {
            log.error("", e1);
        }
        // 创建入库单据
        try {
            vmiStaCreateManagerProxy.generateVmiInboundStaByVmiCode(BiChannel.CHANNEL_VMICODE_CONVERSE_YX);
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("downloadFtpDataAndCreateStaForConverseYx2");
    }

    /**
     * Converse永兴根据反馈中间表写数据到文件并上传FTP
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadConverseYxData() {
        log.info("uploadConverseYxData1");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_CONVERSE_YX_FTP);
        String localDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH);
        try {
            // 入库反馈写文件
            try {
                converseYxManager.inBoundTransferInBoundWriteToFile(localDir);
            } catch (Exception e) {
                log.error("", e);
            }
            // 退大库反馈写文件
            try {
                converseYxManager.vmiReturnReceiveWriteToFile(localDir);
            } catch (Exception e) {
                log.error("", e);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        try {
            SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_UPPATH), cfg.get(Constants.VMI_FTP_UP_LOCALPATH), cfg
                    .get(Constants.NIKE_FTP_UP_LOCALPATH_BACKUP));
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("uploadConverseYxData2");
    }
}
