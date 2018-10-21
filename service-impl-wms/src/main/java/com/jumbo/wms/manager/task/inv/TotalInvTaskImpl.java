package com.jumbo.wms.manager.task.inv;



import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.WholeTaskLogDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TotalInvTask;
import com.jumbo.wms.model.warehouse.WholeTaskLog;
import com.jumbo.wms.model.warehouse.WholeTaskLogStatus;

public class TotalInvTaskImpl implements TotalInvTask {

    /**
     * 
     */
    private static final long serialVersionUID = 8630456017288712926L;

    protected static final Logger log = LoggerFactory.getLogger(TotalInvTaskImpl.class);

    @Autowired
    private TotalInvManager totalInvManager;
    @Autowired
    private WholeTaskLogDao wholeTaskLogDao;

    /**
     * 全量库存同步定时任务 每天凌晨1点开始计算
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void totalInv() {
        log.info("totalInv Execute.........!");
        /* 1.备份历史所有ZIP文件、库存文件 */
        totalInvManager.copyFile(true);

        /* 2.通过SQL计算全量库存待同步数据至表T_WH_WHOLE_INV_SYNCHRO */
        if (!totalInvManager.salesInventory()) {
            return;
        }

        /* 3 生成库存文件，备份库存日志 */
        if (!totalInvManager.createInvFile()) {
            return;
        }

        /* 4.库存文件压缩ZIP包及校验：读取压缩文件，校验压缩文件中是否存在原文件文件名且可以读取状态 */// TODO校验文件待测试
        if (!totalInvManager.createZipAndCheck()) {
            return;
        }

        /* 5.备份库存文件 */
        totalInvManager.copyFile(false);

        /* 6.删除ftp上同名文件 */
        if (!totalInvManager.deleteSameFile()) {
            return;
        }

        /* 7.上传ftp文件 */
        if (!totalInvManager.uploadFileToFtp()) {
            return;
        }
        /* 8.上传ftp文件 cheng.su */
        if (!totalInvManager.fileToFtp()) {
            return;
        }

        log.info("totalInv end.........!");
    }

    /**
     * 重新上传zip定时任务 凌晨2点到9点间每5分钟检查是否存在（t_wh_task_log）重新上传，如果存在则执行上传任务，记录日志
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void totalInvZipUploadTask() {
        WholeTaskLog task = wholeTaskLogDao.getByDate(); // 检查是否存在当天新建的 上传任务
        if (task != null) {
            /* 删除ftp上同名文件 */
            if (!totalInvManager.deleteSameFile()) {
                task.setUpdateTime(new Date());
                task.setStatus(WholeTaskLogStatus.ERROR); // 更新状态
                wholeTaskLogDao.save(task);
                return;
            }
            /* 上传ftp文件 */
            if (!totalInvManager.uploadFileToFtp()) {
                task.setUpdateTime(new Date());
                task.setStatus(WholeTaskLogStatus.ERROR); // 更新状态
                wholeTaskLogDao.save(task);
                return;
            }
            task.setUpdateTime(new Date());
            task.setStatus(WholeTaskLogStatus.FINISH); // 更新状态
            wholeTaskLogDao.save(task);
            log.info("task upload success.........!");
        }
    }

}
