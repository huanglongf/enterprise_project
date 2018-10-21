package com.jumbo.wms.manager.expressRadar;

import java.util.List;

import loxia.dao.Pagination;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;
import com.jumbo.wms.model.expressRadar.TransRoute;

public interface ExpressRadarManager extends BaseManager {
	

	/**
	 * 根据订单号更新快递
	 * @param erList
	 */
    void expressDispose(RadarTransNoCommand rtncCommand);
	
	
	/**
	 * 其他物流信息获取
	 * @param expressCode
	 * @return
	 */
    List<TransRoute> expressInfoAcquire(String expressCode, String lpcode, Long noId);
	
	/**
	 * 根据不同的物流商的快递明细状态判断是否需要修改快递的状态
	 * 未完成则返回null
	 * 完成则返回状态值
	 * @param status
	 * @return
	 */
	Integer expressStatus(String status,String lpcode);
	
    /**
     * 批量修改快递状态
     * 
     * @param:快递单号
     * @param:状态值
     */
    void updateExpressRadarBatch(String expressCode, Integer status);

    /**
     * 添加快递运单路由状态
     * 
     * @param transRouteList
     */
    void saveTransRouteStatus(List<TransRoute> transRouteList);

    /**
     * 修改快递为出库状态
     * 
     * @param transRouteList
     */
    void updateOutBound(List<TransRoute> transRouteList);

    /**
     * 分页获取可能揽件超时的快递
     * 
     * @param start
     * @param pageSize
     * @return
     */
    Pagination<RadarTransNoCommand> findTakesTimeoutExpress(int start, int pageSize);

    /**
     * 分页获取可能配送超时的快递
     * 
     * @param start
     * @param pageSize
     * @return
     */
    Pagination<RadarTransNoCommand> findDeliveryTimeoutExpress(int start, int pageSize);
}
