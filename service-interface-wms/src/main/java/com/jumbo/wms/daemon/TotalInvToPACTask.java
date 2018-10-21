package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

public interface TotalInvToPACTask extends BaseManager {
	/**
	 * 全量库存同步定时任务 每天凌晨1点开始计算
	 */
	void totalInvToPac();

}
