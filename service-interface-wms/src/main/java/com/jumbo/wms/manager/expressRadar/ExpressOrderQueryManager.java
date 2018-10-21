package com.jumbo.wms.manager.expressRadar;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.expressRadar.ExpressDetailCommand;
import com.jumbo.wms.model.command.expressRadar.ExpressOrderQueryCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonLineCommand;
import com.jumbo.wms.model.expressRadar.RadarTransNo;
import com.jumbo.wms.model.expressRadar.TransRoute;

public interface ExpressOrderQueryManager extends BaseManager{
    
    /**
     * 查询所有物流
     * 
     * @return Pagination<ExpressOrderQueryCommand>
     */
    Pagination<ExpressOrderQueryCommand> findExpressInfoList(int start, int pageSize, ExpressOrderQueryCommand expressOrderQueryCommand, Sort[] sorts);
    
    /**
     * 根据id查询运单明细
     * 
     * @param id
     * @return RadarTransNo
     */
    RadarTransNo findRadarTransNoDetail(Long id); 
    
    /**
     * 查询快递明细列表
     * 
     * @param expressCode
     * @return List<ExpressDetailCommand>
     */
    List<ExpressDetailCommand> findExpressDetail(String expressCode);

    /**
     * 订单明细信息查看列表
     * 
     * @param pCode
     * @return List<ExpressDetailCommand>
     */
    List<ExpressDetailCommand> findOrderDetail(String orderCode);
    
    /**
     * 物流明细信息查看列表
     * 
     * @param expressCode
     * @return List<TransRoute>
     */
    List<TransRoute> findLogisticsDetail(String expressCode);
    
    /**
     * 获取预警天数
     * 
     * @param expressOrderQuery
     * @param flag
     * @return Long
     */
    Long getWarningDate(ExpressOrderQueryCommand expressOrderQuery, int flag);
    
    /**
     * 更新预警类型
     * 
     * @param expressCode
     * @param warningTypeId
     * @param remark
     * @param userId
     * @return String
     */
    String updateWarningType(String expressCode, Long warningTypeId, String remark, Long userId, Long lvId);
    
    /**
     * 根据预警类型code查找预警级别
     * 
     * @param errorCode
     * @return List<SysRadarErrorCode>
     */
    List<RadarWarningReasonLineCommand> findOrderWarningLv(String errorCode);

}
