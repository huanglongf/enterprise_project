package com.jumbo.wms.manager.task.vmiwarehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import com.jumbo.dao.baseinfo.MsgOmsSkuLogDao;
import com.jumbo.dao.warehouse.WmsIntransitNoticeOmsDao;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskOms;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.listener.InventoryCheckListenerManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.baseinfo.MsgOmsSkuLog;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOms;

public class TaskOmsImpl extends BaseManagerImpl implements TaskOms {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7028596272368132064L;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private TaskOmsOutManager taskOmsOutManager;
    @Autowired
    private WmsIntransitNoticeOmsDao wmsIntransitNoticeOmsDao;
    @Autowired
    private MsgOmsSkuLogDao msgOmsSkuLogDao;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private InventoryCheckListenerManager inventoryCheckListenerManager;

    /**
     * OMS外包仓销售出库定时任务入口
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiOMSOutbound() {
        log.debug("-- OMS vmi sales outBound  -- start");
        List<MsgRtnOutbound> rtns = wareHouseManagerQuery.findAllMsgRtnOutbound(Constants.VIM_WH_SOURCE_OMS);
        for (MsgRtnOutbound rtn : rtns) {
            try {
                // 1.执行出库流程
                wareHouseManagerProxy.callVmiSalesStaOutBound(rtn.getId());
            } catch (BusinessException e) {
                log.error("vmiOMSOutbound error ! OUT STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * oms出库接口优化定时任务入口
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void runThreadOutBound() {
        log.debug("-- OMS outBound- start");
        try {
            taskOmsOutManager.threadOutBound();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 邮件通知定时任务-- 每小时一次 汇总错误次数超过10次仍旧没有成功的单据且没有进行邮件通知
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void emailNotice() {
        // 查询错误次数超过10次仍旧没有成功且没有邮件通知的单据
        List<WmsIntransitNoticeOms> list = wmsIntransitNoticeOmsDao.findPartByErCount(new BeanPropertyRowMapperExt<WmsIntransitNoticeOms>(WmsIntransitNoticeOms.class));
        if (list.size() > 0) {
            try {
                taskOmsOutManager.sendEmail(list);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 邮件通知定时任务-- 每小时一次 所有超过6小时的通知OMS创建SKU而OMS仍未创建的邮件通知（同一商品不重复发送邮件通知）
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void emailNoticeOms() {
        // 超过6小时的通知OMS创建SKU而OMS未创建的数据
        List<MsgOmsSkuLog> list = msgOmsSkuLogDao.findMsgOmsSkuLogByTime(new BeanPropertyRowMapperExt<MsgOmsSkuLog>(MsgOmsSkuLog.class));
        if (list.size() > 0) {
            try {
                taskOmsOutManager.emailNoticeOms(list);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 上传退货入文档到FTP
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadRtnInDocToFtp() {
        // 读取库存同步配置信息
        Map<String, String> cfg = configManager.getEbsFTPConfig();
        // WMS 本地目录文件生成目录
        String localDir = cfg.get(Constants.LOC_DOC_PATH);
        // 读取生成目录下所有文件，剪切到备份目录。
        File f = new File(localDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
            File[] files = f.listFiles();
            for (File file : files) {
                // 删除FTP同名文件并上传FTP
                deleteAndUploadFile(file);
                file.delete();
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("uploadRtnInDocToFtp Exception:", e);
            }
        }
    }

    private void deleteAndUploadFile(File file) {
        // 读取库存同步配置信息
        Map<String, String> cfg = configManager.getEbsFTPConfig();
        String ftpLocal = cfg.get(Constants.RTN_IN_DOC_PATH); // ftp目录
        // 连接sftp服务器
        ChannelSftp sftp = SFTPUtil.connect(cfg.get(Constants.VMI_FTP_URL), Integer.parseInt(cfg.get(Constants.VMI_FTP_PORT)), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD));
        if (sftp == null) {
            throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
        }
        try {
            // 删除同名文件
            Vector<LsEntry> LsEntry = SFTPUtil.listFiles(ftpLocal, sftp); // 获取ftp目录
            for (int i = 0; i < LsEntry.size(); i++) { // 遍历目录文件
                log.info(LsEntry.get(i).getFilename());
                if (LsEntry.get(i).getFilename().equals(file.getName())) { // 如果存在同名文件，则删除
                    if (!deleteFtpFile(ftpLocal, file.getName(), sftp)) {
                        throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
                    }
                }
            }
            // 上传文件
            boolean isSucess = SFTPUtil.sendFile(cfg.get(Constants.RTN_IN_DOC_PATH), file.getPath(), sftp);
            if (!isSucess) {
                throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
            }
        } catch (SftpException e) {
            log.error("", e);
        } finally {
            SFTPUtil.disconnect(sftp);
        }
    }

    private boolean deleteFtpFile(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * pacs库存同步优化 (每半个小时执行一次)
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void insertOmsOutBound() {
        inventoryCheckListenerManager.recordFinishDataToOms();
    }

    /**
     * 更新t_wh_order_status_oms表createTime是空的数据
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void modifyWmsOrderStatusOmsDate() {
        log.error("======modifyWmsOrderStatusOmsDate start========");
        List<Long> list = taskOmsOutManager.getWmsOrderCreateTimeIsNullIds();
        // 把list集合按200的size分割
        List<List<Long>> listArray = new ArrayList<List<Long>>();
        int listSize = list.size();
        int pageSize = 200;
        int page = (listSize + (pageSize - 1)) / pageSize;
        for (int i = 0; i < page; i++) {
            List<Long> subList = new ArrayList<Long>();
            for (int j = 0; j < listSize; j++) {
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                if (pageIndex == (i + 1)) {
                    subList.add(list.get(j));
                }
                if ((j + 1) == ((j + 1) * pageSize)) {
                    break;
                }
            }
            listArray.add(subList);
        }
        // 每200条数据一次更新
        for (List<Long> ids : listArray) {
            try {
                taskOmsOutManager.updateWmsOrderStatusOmsCreateTime(ids);
            } catch (Exception e) {
                log.error("modifyWmsOrderStatusOmsDate error : " + e);
            }
        }
        log.error("======modifyWmsOrderStatusOmsDate end========");
    }
}
