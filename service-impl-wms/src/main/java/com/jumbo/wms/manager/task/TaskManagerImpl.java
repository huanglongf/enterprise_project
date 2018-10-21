package com.jumbo.wms.manager.task;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.task.annotation.SingleTaskLock;
import com.jcraft.jsch.ChannelSftp;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;

/**
 * @author wanghua
 * 
 */
@Transactional
@Service("k3Executor")
public class TaskManagerImpl extends BaseManagerImpl implements TaskManager {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7948401585616320908L;
    @Autowired
    private ExcelWriterManager excelWriterManager;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;



    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void taskStatisticsMainland() {
        taskStatistics(MAINLAND);
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void taskStatisticsOverseas() {
        taskStatistics(OVERSEAS);
    }

    /**
     * 报表数据上传FTP
     */
    public void taskStatistics(String district) {
        try {
            msSnReport(district);
        } catch (Exception e) {
            log.error("", e);
        }

        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.MICROSOFTTEST_FTP_GROUP);
        // 获取日期时间
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        String fileName = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        // 本地备份目录
        String localPath = cfg.get(Constants.MICROSOFTTEST_FTP_LOCAL_UPPATH);
        if (localPath == null || localPath.equals("")) {
            log.debug("the download path is not exist");
            return;
        }
        File path = new File(localPath);
        // 判断目录是否存在 不存在 创建
        if (!path.exists()) {
            path.mkdirs();
        }
        // 库存文件
        File inv = null;
        if (MAINLAND.equals(district)) {
            inv = new File(localPath + "/RRCN00I_INV_" + fileName + Constants.EXCEL_XLS);
        } else {
            inv = new File(localPath + "/HK00I_INV_" + fileName + Constants.EXCEL_XLS);
        }
        File msSn = new File(localPath + "/Microsoft Store_UpRunning_" + fileName + Constants.EXCEL_XLS);
        try {
            // 判断是否存在
            if (!inv.exists()) {
                inv.createNewFile();
            }
            // 读取库存信息
            excelWriterManager.getInvStatisticsData(new FileOutputStream(inv), district);
        } catch (Exception e) {
            log.error("", e);
        }
        try {
            // Sn号报表
            if (!msSn.exists()) {
                msSn.createNewFile();
            }
            excelWriterManager.msSnReportExport(new FileOutputStream(msSn));
        } catch (Exception e) {
            log.error("", e);
        }
        // 上传成功后 做备份
        String backupPath = cfg.get(Constants.MICROSOFTTEST_FTP_UPPATH_BACKUP);
        // 获取SFTP连接
        ChannelSftp sftp = SFTPUtil.connect(cfg.get(Constants.MICROSOFTTEST_FTP_URL), Integer.parseInt(cfg.get(Constants.MICROSOFTTEST_FTP_PORT)), cfg.get(Constants.MICROSOFTTEST_FTP_USERNAME), cfg.get(Constants.MICROSOFTTEST_FTP_PASSWORD));
        // 上传到SFTP
        try {
            Boolean bool = SFTPUtil.sendFile(cfg.get(Constants.MICROSOFTTEST_FTP_UPPATH), inv.getPath(), sftp);
            if (bool) {
                FileUtil.copyFile(inv.getPath(), backupPath);
            }

            bool = SFTPUtil.sendFile(cfg.get(Constants.MICROSOFTTEST_FTP_UPPATH), msSn.getPath(), sftp);
            if (bool) {
                FileUtil.copyFile(msSn.getPath(), backupPath);
            }
        } finally {
            SFTPUtil.disconnect(sftp);
        }

    }

    /**
     * 报表数据上传FTP
     */
    public void msSnReport(String district) {
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.MICROSOFTTEST_SN_FTP_GROUP);
        // 获取日期时间
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        String fileName = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        // 本地备份目录
        String localPath = cfg.get(Constants.MICROSOFTTEST_SN_FTP_LOCAL_UPPATH);
        if (localPath == null || localPath.equals("")) {
            log.debug("the download path is not exist");
            return;
        }
        File path = new File(localPath);
        // 判断目录是否存在 不存在 创建
        if (!path.exists()) {
            path.mkdirs();
        }
        // 库存日志
        File msSn = new File(localPath + "/Microsoft Store_UpRunning_" + fileName + Constants.EXCEL_XLS);
        try {
            // Sn号报表
            if (!msSn.exists()) {
                msSn.createNewFile();
            }
            excelWriterManager.msSnReportExport(new FileOutputStream(msSn));
        } catch (Exception e) {
            log.error("", e);
        }
        // 上传成功后 做备份
        String backupPath = cfg.get(Constants.MICROSOFTTEST_SN_FTP_UPPATH_BACKUP);
        // 获取SFTP连接
        ChannelSftp sftp = SFTPUtil.connect(cfg.get(Constants.MICROSOFTTEST_SN_FTP_URL), Integer.parseInt(cfg.get(Constants.MICROSOFTTEST_SN_FTP_PORT)), cfg.get(Constants.MICROSOFTTEST_SN_FTP_USERNAME), cfg.get(Constants.MICROSOFTTEST_SN_FTP_PASSWORD));
        // 上传到SFTP
        try {
            Boolean bool = SFTPUtil.sendFile(cfg.get(Constants.MICROSOFTTEST_SN_FTP_UPPATH), msSn.getPath(), sftp);
            if (bool) {
                FileUtil.copyFile(msSn.getPath(), backupPath);
            }
        } finally {
            SFTPUtil.disconnect(sftp);
        }

    }

    @Override
    public void updateUpdateCancleOrder(Long msgId, MsgOutboundOrderCancelStatus status) {
        if (msgId != null && status != null) {
            msgOutboundOrderCancelDao.updateOuOrderStatus(msgId, status.getValue());
        }

    }

}
