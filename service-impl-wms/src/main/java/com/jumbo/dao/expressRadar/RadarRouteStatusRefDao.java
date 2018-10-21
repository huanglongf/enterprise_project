package com.jumbo.dao.expressRadar;


import java.util.List;
import java.util.Map;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.expressRadar.RadarRouteStatusRefCommand;
import com.jumbo.wms.model.expressRadar.RadarRouteStatusRef;


@Transactional
public interface RadarRouteStatusRefDao extends GenericEntityDao<RadarRouteStatusRef, Long>{

	
	@NativeQuery(pagable = true)
	Pagination<RadarRouteStatusRefCommand> findRadarStatusRefByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<RadarRouteStatusRefCommand> rowMapper);
	
	@NativeUpdate
    void updateRadarStatusRefStatus(@QueryParam(value ="status") Integer status,@QueryParam(value="ids") List<Long> ids);
	
	@NativeUpdate
    void updateRadarStatusRefRemark(@QueryParam(value ="remark") String remark,@QueryParam(value="ids") List<Long> ids);

    @NativeQuery
    RadarRouteStatusRefCommand findRadarRouteStatusRefByExpressStatus(@QueryParam(value = "expressStatus") String expressStatus, @QueryParam(value = "logisticsCode") String logisticsCode, RowMapper<RadarRouteStatusRefCommand> rowMapper);

    @NativeQuery
    RadarRouteStatusRefCommand findRadarRouteStatusRefByCode(@QueryParam(value = "code") String code, @QueryParam(value = "logisticsCode") String logisticsCode, RowMapper<RadarRouteStatusRefCommand> rowMapper);

    @NativeQuery
    RadarRouteStatusRefCommand findRouteStatusRoleByExpressStatus(@QueryParam(value = "expressStatus") String expressStatus, @QueryParam(value = "logisticsCode") String logisticsCode, RowMapper<RadarRouteStatusRefCommand> rowMapper);
}
