package com.jumbo.dao.expressRadar;


import java.util.List;
import java.util.Map;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.expressRadar.SysRouteStatusCode;


@Transactional
public interface SysRouteStatusCodeDao extends GenericEntityDao<SysRouteStatusCode, Long>{
	
	@NativeQuery
	List<SysRouteStatusCode> findRouteStatusCodeByParam(@QueryParam Map<String, Object> m,RowMapper<SysRouteStatusCode> rowMapper);
}
