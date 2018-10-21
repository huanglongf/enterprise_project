package com.jumbo.wms.manager.task.lmis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jcraft.jsch.ChannelSftp;
import com.jumbo.dao.lmis.warehouse.WarehouseChargesDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TotalZeroInvTask;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.model.lmis.WarehouseCharges;

/**
 * （此定时任务不再使用）
 * 
 */
public class TotalZeroInvTaskImpl implements BaseManager, TotalZeroInvTask {

    private static final long serialVersionUID = -3689028848625520570L;

    protected static final Logger log = LoggerFactory.getLogger(TotalZeroInvTaskImpl.class);

    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private WarehouseChargesDao warehouseChargesDao;

    public String temFileName = null;// 临时文件路径
    public String backupsFileName = null;// 备份文件路径

    /**
     * 零点库存统计 00:00执行
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void totalInv() {
        log.error("totalZeroInv start.........!");
        /* 创建数据文件 */
        // 定时任务默认生成昨天的库存文件
        if (!saveFile(DateUtils.addDays(new Date(), -1), new Date(), null, null, null, null)) {
            return;
        }
        /* 上传至sftp */
        if (temFileName != null) {

            if (!uploadZeroInv()) {
                log.debug("0点库存文件上传失败");
            } else {
                log.debug("0点库存文件上传成功");

                if (backupsFile()) {// 备份文件并删除临时文件
                    log.debug("0点库存文件备份成功");
                } else {
                    log.debug("0点库存文件备份失败");
                }
            }
        }
        temFileName = null;
        backupsFileName = null;
        log.error("totalZeroInv end.........!");
    }

    /**
     * 将需要上传的数据临时保存在服务器上
     */
    public boolean saveFile(Date historyDate, Date nextDate, Integer warehouseType, String storeCode, String warehouseCode, String areaCode) {
        Map<String, String> cfg = configManager.getLmisFTPConfig(); // 查询配置信息
        String localDir = cfg.get(Constants.LMIS_LOCAL_PATH);
        File dir = new File(localDir);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs(); // 创建备份目录
        }
        String parentDir = dir.getParent();
        File tempDir = new File(parentDir + "/temporaryFile");
        if (!tempDir.exists() && !tempDir.isDirectory()) {
            tempDir.mkdirs(); // 创建临时目录
        }
        // 文件名
        String fileName = "/ZERO_DATA_INVENTORY_" + String.valueOf(FormatUtil.formatDate(historyDate, "yyyyMMdd")) + ".txt";
        temFileName = tempDir + fileName;// 临时文件路径
        backupsFileName = localDir;// 备份文件路径
        File file = new File(temFileName);
        if (file.exists()) {
            file.delete();
        }
        FileWriter fileWriter = null;
        String start_time = new SimpleDateFormat("yyyy-MM-dd").format(historyDate);
        try {
            file.createNewFile();
            int pageSize = 5000;
            Pagination<WarehouseCharges> pagination =
                    warehouseChargesDao.findWarehouseChargesByTime(0, pageSize, null, nextDate, warehouseType, storeCode, warehouseCode, areaCode, new BeanPropertyRowMapper<WarehouseCharges>(WarehouseCharges.class), null);
            int totalPages = pagination.getTotalPages();
            fileWriter = new FileWriter(file, true);
            for (int i = 0; i < totalPages; i++) {
                // 不重复查询第一页
                if (i == 0) {
                    List<WarehouseCharges> list = pagination.getItems();
                    if (list.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < list.size(); j++) {
                            list.get(j).setStart_time(start_time);
                            sb.append(list.get(j).toString() + "\r\n");
                            // fileWriter.write(list.get(j).toString() + "\r\n");
                        }
                        fileWriter.write(sb.toString());
                        fileWriter.flush();
                    }
                    continue;
                }
                // 剩余页数据
                Pagination<WarehouseCharges> temp =
                        warehouseChargesDao.findWarehouseChargesByTime(i * pageSize, pageSize, null, nextDate, warehouseType, storeCode, warehouseCode, areaCode, new BeanPropertyRowMapper<WarehouseCharges>(WarehouseCharges.class), null);
                List<WarehouseCharges> list = temp.getItems();
                if (list.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).setStart_time(start_time);
                        sb.append(list.get(j).toString() + "\r\n");
                        // fileWriter.write(list.get(j).toString() + "\r\n");
                    }
                    fileWriter.write(sb.toString());
                    fileWriter.flush();
                }
            }
            return true;
        } catch (Exception e) {
            temFileName = null;
            log.error("零点库存文件生成失败", e);
            return false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("totalZeroInvTask saveFile IOException", e);
                    }
                    return false;
                }
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("totalZeroInvTask fileWriter.close IOException", e);
                    }
                    return false;
                }
            }
        }
    }

    /**
     * 上传零点库存统计文件
     * 
     * @return
     */
    public boolean uploadZeroInv() {
        Map<String, String> cfg = configManager.getLmisFTPConfig();
        boolean flag = false;
        flag = sendFile(cfg.get(Constants.LMIS_FTP_URL), cfg.get(Constants.LMIS_FTP_PORT), cfg.get(Constants.LMIS_FTP_USERNAME), cfg.get(Constants.LMIS_FTP_PASSWORD), cfg.get(Constants.LMIS_FTP_UPPATH), temFileName, "GBK");
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
        // 连接SFTP
        ChannelSftp sftp = SFTPUtil.connect(serverName, Integer.parseInt(port), username, password);
        try {
            log.debug("ready to send file!");
            if (sftp != null && sftp.isConnected()) {
                // 上传文件
                boolean flag = SFTPUtil.sendFile(path, localFileName, sftp);
                log.debug("send file :" + flag);
                success = flag;
            }
        } catch (Exception e) {
            log.error("", e);
            success = false;
        } finally {
            SFTPUtil.disconnect(sftp);
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

    /**
     * 清空缓存目录
     * 
     * @return
     */
    public void clear() {
        temFileName = null;
        backupsFileName = null;
    }
}
