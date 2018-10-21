package com.jumbo.dao.expressRadar;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonCommand;
import com.jumbo.wms.model.expressRadar.RadarWarningReason;

@Transactional
public interface RadarWarningReasonDao extends GenericEntityDao<RadarWarningReason, Long> {

    @NativeQuery(pagable = true)
    Pagination<RadarWarningReasonCommand> findRadarWarningReason(int start, int pagesize, @QueryParam(value = "eid") Long eid, @QueryParam(value = "lvid") Long lvid, RowMapper<RadarWarningReasonCommand> r, Sort[] sorts);
    
    @NativeQuery
    RadarWarningReason findRadarWarningReasonByName(@QueryParam(value = "warningName") String warningName, RowMapper<RadarWarningReason> r);
}
