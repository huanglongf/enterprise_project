package com.jumbo.wms.manager.task.edw;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.util.MailUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.EdwTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.model.system.ChooseOption;

/**
 * 
 * 每天同步对应信息给EDW 格式为.txt文件 bin.hu
 * 
 */
public class EdwTaskImpl extends BaseManagerImpl implements EdwTask {

    private static final long serialVersionUID = 1253531063135951333L;

    private static final String categoryCode = "SYS_EMAIL";
    private static final String HK_URL = "APP13";
    private static final String DL_URL = "APP02";
    private static final String DL_USER_NAME = "Group_WMS";
    private static final String HK_USER_NAME = "HK_WMS";
    private static final String DL = "DL";
    private static final String HK = "HK";

    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private EdwTaskManagerProxy edwManagerProxy;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadEdwFile() {
        log.info("======================EdwTask uploadEwdFile begin!");
        // 上传文件
        String dateTimeString = getFormateDate("yyyyMMdd");
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.EDW_GROUP);
        String locationUploadPath = config.get(Constants.EDW_FTP_UP_LOCALPATH);
        // 上传文件
        String remoteUpPath = config.get(Constants.EDW_FTP_UPPATH);
        String locationUploadBackupPath = config.get(Constants.EDW_FTP_UP_LOCALPATH_BK);
        String remoteUpPathReceving = remoteUpPath;// FTP服务器上的上传相对路径
        String locationUploadPathReceiving = locationUploadPath;// 上传后保存到本地的路径信息文件
        String locationUploadPathReceivingOk = locationUploadPath + "/ok";// 上传后保存到本地的路径OK文件
        String locationUploadBackupPathReceving = locationUploadBackupPath + "/backup";// 上传后保存到本地的备份路径
        String result = edwManagerProxy.uploadEdwFile(locationUploadPathReceiving, locationUploadPathReceivingOk, dateTimeString);
        if (result.equals("SUCCESS")) {
            // 全部成功上传SFTP服务器
            // 先上传信息文件
            try {
                SFTPUtil.sendFile(config.get(Constants.EDW_FTP_URL), config.get(Constants.EDW_FTP_PORT), config.get(Constants.EDW_FTP_USERNAME), config.get(Constants.EDW_FTP_PASSWORD), remoteUpPathReceving, locationUploadPathReceiving,
                        locationUploadBackupPathReceving);
            } catch (Exception e) {
                EdwErrorEmailInform("UERROR", DL_USER_NAME, DL_URL, dateTimeString, DL);
                log.error("", e);
                return;
            }
            // 信息文件上传成功后上传OK文件
            try {
                SFTPUtil.sendFile(config.get(Constants.EDW_FTP_URL), config.get(Constants.EDW_FTP_PORT), config.get(Constants.EDW_FTP_USERNAME), config.get(Constants.EDW_FTP_PASSWORD), remoteUpPathReceving, locationUploadPathReceivingOk,
                        locationUploadBackupPathReceving);
                EdwErrorEmailInform("SUCCESS", DL_USER_NAME, DL_URL, dateTimeString, DL);
            } catch (Exception e) {
                EdwErrorEmailInform("UERROR", DL_USER_NAME, DL_URL, dateTimeString, DL);
                log.error("", e);
            }
        } else {
            EdwErrorEmailInform("FERROR", DL_USER_NAME, DL_URL, dateTimeString, DL);
            // 有失败删除本地保存所有文件，不上传SFTP服务器
            deleteErrorFile(locationUploadPathReceiving);
            deleteErrorFile(locationUploadPathReceivingOk);
        }
        // config.get(Constants.EDW_FTP_URL)
        log.info("======================EdwTask uploadEwdFile end!");
    }

    /**
     * 香港数据同步数据给EDW
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadEdwFileHk() {
        log.info("======================EdwTask uploadEdwFileHk begin!");
        // 上传文件
        String dateTimeString = getFormateDate("yyyyMMdd");
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.EDW_GROUP);
        String locationUploadPath = config.get(Constants.EDW_FTP_UP_LOCALPATH);
        // 上传文件
        String remoteUpPath = config.get(Constants.EDW_FTP_UPPATH);
        String locationUploadBackupPath = config.get(Constants.EDW_FTP_UP_LOCALPATH_BK);
        String remoteUpPathReceving = remoteUpPath;// FTP服务器上的上传相对路径
        String locationUploadPathReceiving = locationUploadPath;// 上传后保存到本地的路径信息文件
        String locationUploadPathReceivingOk = locationUploadPath + "/ok";// 上传后保存到本地的路径OK文件
        String locationUploadBackupPathReceving = locationUploadBackupPath + "/backup";// 上传后保存到本地的备份路径
        String result = edwManagerProxy.uploadEdwFileHk(locationUploadPathReceiving, locationUploadPathReceivingOk, dateTimeString);
        if (result.equals("SUCCESS")) {
            // 全部成功上传SFTP服务器
            // 先上传信息文件
            try {
                SFTPUtil.sendFile(config.get(Constants.EDW_FTP_URL), config.get(Constants.EDW_FTP_PORT), config.get(Constants.EDW_FTP_USERNAME), config.get(Constants.EDW_FTP_PASSWORD), remoteUpPathReceving, locationUploadPathReceiving,
                        locationUploadBackupPathReceving);
            } catch (Exception e) {
                EdwErrorEmailInform("UERROR", HK_USER_NAME, HK_URL, dateTimeString, HK);
                log.error("", e);
                return;
            }
            // 信息文件上传成功后上传OK文件
            try {
                SFTPUtil.sendFile(config.get(Constants.EDW_FTP_URL), config.get(Constants.EDW_FTP_PORT), config.get(Constants.EDW_FTP_USERNAME), config.get(Constants.EDW_FTP_PASSWORD), remoteUpPathReceving, locationUploadPathReceivingOk,
                        locationUploadBackupPathReceving);
                EdwErrorEmailInform("SUCCESS", HK_USER_NAME, HK_URL, dateTimeString, HK);
            } catch (Exception e) {
                EdwErrorEmailInform("UERROR", HK_USER_NAME, HK_URL, dateTimeString, HK);
                log.error("", e);
            }
        } else {
            EdwErrorEmailInform("FERROR", HK_USER_NAME, HK_URL, dateTimeString, HK);
            // 有失败删除本地保存所有文件，不上传SFTP服务器
            deleteErrorFile(locationUploadPathReceiving);
            deleteErrorFile(locationUploadPathReceivingOk);
        }
        // config.get(Constants.EDW_FTP_URL)
        log.info("======================EdwTask uploadEdwFileHk end!");
    }


    /**
     * 删除文件夹内所有文件
     */
    private void deleteErrorFile(String locationUploadPathReceiving) {
        File f = new File(locationUploadPathReceiving);
        for (File toFile : f.listFiles()) {
            if (!toFile.isDirectory()) {
                toFile.delete();
            }
        }
    }

    /**
     * 发送edw邮件
     */
    private void EdwErrorEmailInform(String subjectType, String userName, String sftpType, String dateTimeString, String type) {
        String dateTime = dateTimeString + "001";
        ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, "EDW_ERROR");
        List<String> sn = getTableName(type);
        if (!StringUtil.isEmpty(c.getOptionValue())) {
            String subject = "";
            // 传人邮件模板的CODE -- 查询String类型可用的模板
            StringBuffer sb = new StringBuffer();
            if (subjectType.equals("FERROR")) {
                // 生成文件出错
                subject = "ERROR：生成 " + dateTime + "  批次文件 [/" + sftpType + "] 失败";
                sb.append("文件生成失败列表：" + " \n");
            }
            if (subjectType.equals("UERROR")) {
                // 上传文件出错
                subject = "ERROR：上传 " + dateTime + " 批次文件到SFTP [ 10.11.12.11 : " + userName + "] 目录 [/" + sftpType + "] 失败";
                sb.append("上传失败文件列表：" + " \n");
            }
            if (subjectType.equals("SUCCESS")) {
                // 上传文件成功
                subject = "Successful：上传 " + dateTime + "  批次文件到SFTP [ 10.11.12.11 : " + userName + "] 目录 [/" + sftpType + "] 成功";
                sb.append("上传成功文件列表：" + " \n");
            }
            sb.append(dateTimeString + "001" + Constants.FILE_EXTENSION_OK + "\n");
            for (String s : sn) {
                sb.append(s + dateTimeString + "001" + Constants.FILE_EXTENSION_TXT + "\n");
            }
            boolean bool = false;
            bool = MailUtil.sendMail(subject, c.getOptionValue(), "", sb.toString(), false, null);
            if (bool) {
                log.debug("邮件通知成功！");
            } else {
                log.debug("邮件通知失败,请联系系统管理员！");
            }
        } else {
            log.debug("邮件模板不存在或被禁用！");
        }
    }

    public String getFormateDate(String s) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date dateTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(dateTime);
        return date;
    }

    private List<String> getTableName(String type) {
        List<String> s = new ArrayList<String>();
        if (type.equals(DL)) {
            s.add("t_bi_channel_");
            s.add("t_au_user_");
            s.add("t_au_operation_unit_");
            s.add("t_bi_brand_");
            s.add("t_sf_confirm_order_queue_log_");
            s.add("t_bi_inv_sku_");
            s.add("t_bi_warehouse_");
            s.add("t_wh_sta_");
            s.add("t_wh_sta_delivery_info_");
            s.add("t_wh_sta_line_");
            s.add("t_wh_stv_");
            s.add("t_wh_stv_line_");
            s.add("t_wh_location_");
            s.add("t_wh_package_info_");
            s.add("t_wh_sta_ho_list_");
            s.add("t_wh_sta_ho_list_line_");
            s.add("t_wh_info_time_ref_");
            s.add("t_wh_st_log_");
            s.add("t_wh_sta_add_line_");
            s.add("t_wh_sku_inventory_");
            s.add("t_bi_inv_sku_barcode_");
            s.add("t_wh_sta_picking_list_");
        }
        if (type.equals(HK)) {
            s.add("t_wh_sku_inventory_");
        }
        return s;
    }

}
