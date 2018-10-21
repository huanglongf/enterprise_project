package com.jumbo.wms.manager.expressRadar;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.expressRadar.SysRouteStatusCode;

public interface SysRouteStatusCodeManager extends BaseManager {
	/**
	 * 根据参数获取状态编码列表
	 * @param params
	 * @return
	 */
	List<SysRouteStatusCode> findRouteStatusCodeByParam(Map<String, Object> params);
}
