package com.jumbo.wms.manager.expressRadar;

import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.expressRadar.RadarRouteStatusRefCommand;

public interface ExpressStatusCodeManager extends BaseManager {
	/**
	 * 根据参数查询快递状态代码，并分页
	 * @param start
	 * @param pageSize
	 * @param params
	 * @return
	 */
	Pagination<RadarRouteStatusRefCommand> findExpressStatusCodeByParams(int start, int pageSize,Map<String,Object> params);
	
	/**
	 * 保存快递运单路由状态关联
	 * @param radarRouteStatusRef
	 * @return
	 */
	String saveRRSR(RadarRouteStatusRefCommand rrsrCommand);
	
	/**
	 * 修改快递运单路由状态关联
	 * @param radarRouteStatusRef
	 */
	void updateRRSR(Map<String, Object> params,List<Long> ids);
}
