package com.jumbo.wms.manager.expressRadar;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;
import com.jumbo.wms.model.expressRadar.TransRouteStatus;

public interface TransRouteStatusManager extends BaseManager {
    /**
     * 批量保存快递运单路由状态
     * 
     * @param trsList
     */
    void saveTransRouteStatusList(List<TransRouteStatus> trsList);



    /**
     * 揽件超时
     */
    List<RadarTransNoCommand> takesTimeout(List<RadarTransNoCommand> rtncCommands);

    /**
     * 配送超时
     */
    List<RadarTransNoCommand> deliveryTimeout(List<RadarTransNoCommand> rtncCommands);

    /**
     * 其它预警原因
     */
    List<RadarTransNoCommand> otherWarning();

    /**
     * 
     * @param expressCode
     * @param userId
     * @param wrId
     * @param lvId
     */
    void addTransRouteWarningLog(String expressCode, Long userId, Long wrId, Long lvId);

    /**
     * 更新快递预警信息
     * 
     * @param warningList
     */
    void updateWarningStatus(RadarTransNoCommand rtnc);
}
