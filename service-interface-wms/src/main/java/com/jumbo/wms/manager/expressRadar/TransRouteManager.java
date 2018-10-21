package com.jumbo.wms.manager.expressRadar;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.expressRadar.TransRoute;

public interface TransRouteManager extends BaseManager {
	/**
	 * 批量保存快递明细
	 * @param erdList
	 */
	void saveTransRouteList(List<TransRoute> erdList);


}
