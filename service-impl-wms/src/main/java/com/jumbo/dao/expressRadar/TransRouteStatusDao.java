package com.jumbo.dao.expressRadar;


import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.expressRadar.ExpressDetailCommand;
import com.jumbo.wms.model.expressRadar.TransRouteStatus;


@Transactional
public interface TransRouteStatusDao extends GenericEntityDao<TransRouteStatus, Long> {

    @NativeQuery
    List<ExpressDetailCommand> findExpressDetailByExpressCode(@QueryParam(value = "expressCode") String expressCode, RowMapper<ExpressDetailCommand> rowMapper);
    
    @NativeQuery
    List<ExpressDetailCommand> findOrderDetailByOrderCode(@QueryParam(value = "orderCode") String orderCode, RowMapper<ExpressDetailCommand> rowMapper);
    
//    @NativeQuery
//    List<ExpressDetailCommand> findLogisticsDetailByExpressCode(@QueryParam(value = "expressCode") String expressCode, RowMapper<ExpressDetailCommand> rowMapper);
    
    @NativeUpdate
    void updateRouteStatusWarningReason(@QueryParam(value = "expressCode") String expressCode, @QueryParam(value = "wrId") Long wrId, @QueryParam(value = "remark") String remark, @QueryParam(value = "userId") Long userId);
    
    @NativeQuery
    ExpressDetailCommand findExpressWarningByExpressCode(@QueryParam(value = "expressCode") String expressCode, RowMapper<ExpressDetailCommand> rowMapper);

    @NamedQuery
    List<TransRouteStatus> getTransRouteStatusByExpressCode(@QueryParam(value = "expressCode") String expressCode);

}
