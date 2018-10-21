package com.jumbo.wms.manager.warehouse;

import com.jumbo.wms.manager.BaseManager;

public interface StaPerfectManager extends BaseManager{
	/**
     * 过仓后修改作业单信息
     * @param staId
     */
    void omsTOwmsUpdateSta(Long staId);

}
