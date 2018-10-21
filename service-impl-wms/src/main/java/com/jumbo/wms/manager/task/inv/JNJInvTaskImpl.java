package com.jumbo.wms.manager.task.inv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.util.FileUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.FtpUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.JNJInvTask;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;

public class JNJInvTaskImpl implements BaseManager, JNJInvTask {

    /**
     * 
     */
    private static final long serialVersionUID = 2527648388126036547L;

    protected static final Logger log = LoggerFactory.getLogger(JNJInvTaskImpl.class);

    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private ExcelWriterManager writerManager;

    public String temFileName = null;// 临时文件路径
    public String backupsFileName = null;// 备份文件路径

    /**
     * 强生库存统计 23:00执行
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void totalInv() {

        log.info("totalJNJInv start.........!");
        /* 创建excel文件 */
        if (!saveFile()) {
            return;
        }

        /* 上传至sftp */
        if (temFileName != null) {

            if (!uploadJNJInv()) {
                log.debug("强生excel库存文件上传失败");
            } else {
                log.debug("强生excel库存文件上传成功");

                if (backupsFile()) {// 备份文件并删除临时文件
                    log.debug("强生excel库存文件备份成功");
                } else {
                    log.debug("强生excel库存文件备份失败");
                }
            }
        }
        temFileName = null;
        backupsFileName = null;

        log.info("totalJNJInv end.........!");
    }

    /**
     * 将excel临时保存在服务器上
     */
    public boolean saveFile() {
        Map<String, String> cfg = configManager.getJNJFTPConfig(); // 查询配置信息
        String localDir = cfg.get(Constants.JNJ_LOCAL_PATH);
        File dir = new File(localDir);

        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs(); // 创建备份目录

        }
        String parentDir = dir.getParent();

        File tempDir = new File(parentDir + "/temporaryFile");

        if (!tempDir.exists() && !tempDir.isDirectory()) {
            tempDir.mkdirs(); // 创建临时目录
        }
        String fileName = "/DMS_DATA_INVENTORY_" + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd")) + ".xls";
        temFileName = tempDir + fileName;// 临时文件路径
        backupsFileName = localDir;// 备份文件路径
        File file = new File(temFileName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream outs = null;
        try {
            file.createNewFile();

            outs = new FileOutputStream(file);
            writerManager.johnsonInvExport(outs);
            return true;
        } catch (IOException e) {
            temFileName = null;
            log.error("强生excel库存文件生成失败", e);
            return false;
        } finally {
            if (outs != null) {
                try {
                    outs.flush();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("强生 saveFile IOException", e);
                    }
                    return false;
                }
                try {
                    outs.close();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("强生 outs.close IOException", e);
                    }
                    return false;
                }
            }
        }
    }

    /**
     * 上传强生库存统计文件
     * 
     * @return
     */
    public boolean uploadJNJInv() {
        Map<String, String> cfg = configManager.getJNJFTPConfig();

        boolean flag = false;
        flag = sendFile(cfg.get(Constants.JNJ_FTP_URL), cfg.get(Constants.JNJ_FTP_PORT), cfg.get(Constants.JNJ_FTP_USERNAME), cfg.get(Constants.JNJ_FTP_PASSWORD), cfg.get(Constants.JNJ_FTP_UPPATH), temFileName, "GBK");
        return flag;

    }

    /**
     * Description: 向FTP服务器上传文件
     * 
     * @param path FTP服务器保存目录
     * @param localFileName 上传到FTP服务器上的文件名
     * @return 成功返回true，否则返回false
     * @throws IOException
     */
    public static boolean sendFile(String serverName, String port, String username, String password, String path, String localFileName, String ENcoding) {
        boolean success = false;
        // 得到文件名称
        if (localFileName == null || "".equals(localFileName)) {
            return success;
        }
        // 如果路径是\\转化成/
        if (localFileName.indexOf("/") == -1) {
            localFileName = localFileName.replace("\\\\", "\\");
            localFileName = localFileName.replace("\\", "/");
        }
        log.debug("remote path :" + path + ":localFileName:" + localFileName);
        String toFileName = localFileName.substring(localFileName.lastIndexOf("/") == -1 ? 0 : localFileName.lastIndexOf("/") + 1, localFileName.length());
        log.debug("remote path :" + path + ":toFileName:" + toFileName);
        FTPClient ftpClient = FtpUtil.connectServer(serverName, Integer.parseInt(port), username, password, ENcoding, Boolean.TRUE);
        FileInputStream input = null;
        try {
            // 设置 本机被动模式
            ftpClient.enterLocalPassiveMode();
            // 设置以二进制流的方式传输,图片保真,要在login以后执行. 因为这个方法要向服务器发送"TYPE I"命令
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean r1 = ftpClient.changeWorkingDirectory(path);
            if (!r1) {
                FtpUtil.createDir(path, ftpClient);
                boolean t = ftpClient.changeWorkingDirectory(path);
                log.debug("change Working Directory :" + t);
            }
            log.debug("change Working Directory :" + r1);

            input = new FileInputStream(new File(localFileName));
            log.debug("ready to send file!");
            if (ftpClient != null && ftpClient.isConnected()) {
                boolean tmp = ftpClient.storeFile(toFileName, input);
                log.debug("send file :" + tmp);
                success = tmp;
            }

        } catch (IOException e) {
            log.error("", e);
            log.error("upload error:IOException,cause:{},msg:{}", new Object[] {e.getCause(), e.getMessage()});
            success = false;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                log.error("", e);
                log.error("close iostream IOException,cause:{},msg:{}", new Object[] {e.getCause(), e.getMessage()});
            }
            FtpUtil.disconnectServer(ftpClient);
        }
        return success;
    }

    /**
     * 备份文件
     * 
     * @return
     */
    public boolean backupsFile() {
        if (temFileName != null && backupsFileName != null) {

            boolean b = FileUtil.copyFile(temFileName, backupsFileName);
            FileUtil.deleteFile(temFileName);
            return b;
        }
        return false;
    }
}
