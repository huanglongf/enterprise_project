package com.jumbo.dao.expressRadar;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.expressRadar.SysRadarErrorCode;

@Transactional
public interface SysRadarErrorCodeDao extends GenericEntityDao<SysRadarErrorCode, Long> {

    @NativeQuery
    List<SysRadarErrorCode> findRdErrorCode(RowMapper<SysRadarErrorCode> rowMapper);
    
    @NativeQuery
    List<SysRadarErrorCode> findOrderErrorCode(RowMapper<SysRadarErrorCode> rowMapper);

}
