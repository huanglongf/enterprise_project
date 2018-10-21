package com.jumbo.wms.manager.task.inv;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TotalInvToPACTask;

public class TotalInvToPACTaskImpl implements TotalInvToPACTask {

	private static final long serialVersionUID = -8887847545196102105L;
    protected static final Logger log = LoggerFactory.getLogger(TotalInvToPACTaskImpl.class);

    @Autowired
    private TotalInvToPACManager totalInvToPACManager;

    /**
     * 全量库存同步定时任务 每天凌晨1点开始计算
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void totalInvToPac() {
        log.debug("totalInvToPac Execute.........!");
        /* 1.备份历史所有ZIP文件、库存文件 */
        totalInvToPACManager.copyFile(true);

        /* 2.通过SQL计算全量库存待同步数据至表T_WH_WHOLE_INV_SYNC_PAC */
        if (!totalInvToPACManager.totalInventory()) {
            return;
        }

        /* 3 生成库存文件，备份库存日志 */
        if (!totalInvToPACManager.createInvFile()) {
            return;
        }

        /* 4.库存文件压缩ZIP包及校验：读取压缩文件，校验压缩文件中是否存在原文件文件名且可以读取状态 */// TODO校验文件待测试
        if (!totalInvToPACManager.createZipAndCheck()) {
            return;
        }

        /* 5.备份库存文件 */
        totalInvToPACManager.copyFile(false);

        /* 6.删除ftp上同名文件 */
        if (!totalInvToPACManager.deleteSameFile()) {
            return;
        }

        /* 7.上传ftp文件 */
        if (!totalInvToPACManager.uploadFileToFtp()) {
            return;
        }

        log.debug("totalInv end.........!");
    }

}
