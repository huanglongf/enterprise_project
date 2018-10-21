package com.jumbo.wms.manager.task.vmi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import com.jumbo.util.FtpUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.ItTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.itData.ITVMIInstructionManager;
import com.jumbo.wms.manager.vmi.itData.ITVMIReceiveInfoManager;

@Service("itTask")
public class ItTaskImpl extends BaseManagerImpl implements ItTask {

    private static final long serialVersionUID = 4964791893917446683L;

    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private ITVMIReceiveInfoManager receiveManager;
    @Autowired
    private ITVMIInstructionManager instructionManager;
    @Autowired
    private VmiFactory f;

    private final String upBakDir = "bak";

    /**
     * 抓取文件
     */
    public void download() {
        Map<String, String> config = configManager.getVMIFTPConfig();

        if (config == null || config.size() == 0 || config.get(Constants.VMI_FTP_URL) == null) {
            log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();// 取系统日期
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        // 获取前一天时间
        String date = sdf.format(c.getTime());
        String ftpDir = config.get(Constants.VMI_FTP_DOWNPATH);
        if (ftpDir == null || ftpDir.equals("")) {
            log.debug("ftp:" + config.get(Constants.VMI_FTP_URL) + " has no download path");
            return;
        }
        String remotePath = ftpDir + "/" + date + "/";
        String downDir = config.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        if (downDir == null || downDir.equals("")) {
            log.debug("the download path is not exist");
            return;
        }
        String localPath = downDir + "/" + date + "/";
        // flag = FtpUtil.CreateDirecroty(remotePath, ftpClient);
        ChannelSftp sftp = SFTPUtil.connect(config.get(Constants.VMI_FTP_URL), Integer.parseInt(config.get(Constants.VMI_FTP_PORT)), config.get(Constants.VMI_FTP_USERNAME), config.get(Constants.VMI_FTP_PASSWORD));

        try {
            try {
                sftp.cd(remotePath);
            } catch (SftpException e) {
                log.error("the download folder is not exist");
                SFTPUtil.disconnect(sftp);
                return;
            }
            @SuppressWarnings("unchecked")
            Vector<LsEntry> vector = sftp.ls("*.del");

            if (vector == null || vector.size() == 0) {
                Vector<LsEntry> directorys = SFTPUtil.listFiles(remotePath, sftp);
                if (directorys.size() > 0) {
                    for (int k = 0; k < directorys.size(); k++) {
                        LsEntry lsEntry = directorys.get(k);
                        if (null != lsEntry) {
                            boolean isRead = true;
                            // 先判断文件名称的合法性 如果不合法就不继续下面的工作
                            isRead = judgeFilesName(lsEntry.getFilename());

                            // 如果条件匹配就进行文件的下载
                            if (isRead) {

                                String filename = lsEntry.getFilename();
                                String downRemotePath = remotePath + "/" + filename;
                                String downLocalPath = localPath + "/" + filename;

                                File dir = new File(localPath);
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }

                                // int result = SFTPUtil.readFile(config.get(Constants.VMI_FTP_URL),
                                // config.get(Constants.VMI_FTP_PORT),
                                // config.get(Constants.VMI_FTP_USERNAME),
                                // config.get(Constants.VMI_FTP_PASSWORD), downRemotePath,
                                // downLocalPath,"", false);
                                int result = SFTPUtil.downloadForIT(downRemotePath, downLocalPath, sftp);
                                if (result == 0) {
                                    log.error("{} it ………download file success", new Object[] {new Date()});
                                } else {
                                    log.error("i.t file get fail,resultCode:" + result);
                                }

                            } else {
                                log.error("i.t file judgeFilesName fail:" + lsEntry.getFilename());
                            }
                        }
                    }
                }
            }


        } catch (SftpException e) {
            log.error("SFTP Exception:", e);
        } catch (Exception e) {
            log.error("I.T Download Exception:", e);
        } finally {
            SFTPUtil.disconnect(sftp);
        }
    }

    /**
     * 判断文件名称是否符合匹配字符
     * 
     * @param fileName 文件名称
     * @return
     */
    private static boolean judgeFilesName(String fileName) {
        boolean flag = true;
        if ("".equals(fileName) && fileName == null) {
            flag = false;
            return flag;
        }

        String[] name = fileName.split("\\.");
        if (name.length == 0) {
            flag = false;
            return flag;
        }
        for (int i = 0; i < name.length; i++) {
            if (name[i] == null || name[i].equals("")) {
                flag = false;
                return flag;
            }
        }

        return flag;
    }

    /**
     * 上传
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void upload() {
        Map<String, String> config = configManager.getVMIFTPConfig();

        if (config == null || config.size() == 0 || config.get(Constants.VMI_FTP_URL) == null) {
            log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());

        String upDir = config.get(Constants.VMI_FTP_UPPATH);
        if (upDir == null || upDir.equals("")) {
            log.debug("ftp:" + config.get(Constants.VMI_FTP_URL) + " the upload path is not exist");
            return;
        }
        String remotePath = upDir + "/" + date + "/";
        String locationDir = config.get(Constants.VMI_FTP_UP_LOCALPATH);
        if (locationDir == null || locationDir.equals("")) {
            log.debug("the upload path is null");
            return;
        }
        String localPath = locationDir + "/" + date + "/";
        File dir = new File(localPath);
        if (!dir.exists()) {
            log.debug("the upload path is not exist");
            return;
        }
        String localBakPath = locationDir + "/" + upBakDir + "/" + date + "/";
        File bakdir = new File(localBakPath);
        if (!bakdir.exists()) {
            bakdir.mkdirs();
        }
        List<File> fileList = traveFile(localPath);
        if (fileList.size() == 0) {
            log.debug("there is not file need to upload!");
            return;
        }
        log.debug("file count is :" + fileList.size());
        boolean flag = false;
        for (File file : fileList) {
            flag = FtpUtil.sendFile(config.get(Constants.VMI_FTP_URL), config.get(Constants.VMI_FTP_PORT), config.get(Constants.VMI_FTP_USERNAME), config.get(Constants.VMI_FTP_PASSWORD), remotePath, file.getAbsolutePath());
            if (flag) {
                com.jumbo.util.FileUtil.copyFile(file.getAbsolutePath(), localBakPath);
                com.jumbo.util.FileUtil.deleteFile(file.getAbsolutePath());
                List<File> list = com.jumbo.util.FileUtil.traveFile(localPath);
                if (list.size() == 0) {
                    com.jumbo.util.FileUtil.deleteFile(localPath);
                }
                log.debug("{}.......upload success", new Object[] {new Date()});
            }
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

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateReceiveFile() {
        Map<String, String> config = configManager.getVMIFTPConfig();
        if (config == null || config.size() == 0 || config.get(Constants.VMI_FTP_UP_LOCALPATH) == null) {
            log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String fileDir = config.get(Constants.VMI_FTP_UP_LOCALPATH) + "/" + date;
        // 创建DN接受确认文件
        receiveManager.generateReceiveConfirmationFile(fileDir, date);
    }

    /**
     * 生成STA
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateSta() {
        Map<String, String> config = configManager.getVMIFTPConfig();
        if (config == null || config.size() == 0 || config.get(Constants.VMI_FTP_DOWN_LOCALPATH) == null) {
            log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();// 取系统日期
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        // 获取前一天时间
        String date = sdf.format(c.getTime());
        String dir = config.get(Constants.VMI_FTP_DOWN_LOCALPATH) + "/" + date;
        String bakDir = config.get(Constants.VMI_FTP_DOWN_LOCALPATH) + "/" + Constants.IT_FTP_FINISH_DIR + "/" + date + "/";// +
        // file.getParentFile().getName();
        // date = "20120410";
        // dir = "E:/home/appuser/itdata/in/20120410";
        // bakDir = "E:/home/appuser/itdata/in/BAK/20120410";

        // iTVMIParseDataManager.importProductInfo(dir, bakDir, date);
        // 导入指令文件到数据库
        instructionManager.importITStockFile(dir, bakDir);
        // 执行IT的入库和调整指令
        // instructionManager.executeITInstruction(Constants.IT_VENDER);
        VmiInterface vmiit = f.getBrandVmi("EITCN");
        vmiit.generateInboundSta();

    }

}
