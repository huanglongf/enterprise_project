package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

public interface TotalInvTask extends BaseManager {
	/**
	 * 全量库存同步定时任务 每天凌晨1点开始计算
	 */
	void totalInv();

	/**
	 * 重新上传zip定时任务 凌晨2点到9点间每5分钟检查是否存在（t_wh_task_log）重新上传，如果存在则执行上传任务，记录日志
	 */
	void totalInvZipUploadTask();
}
