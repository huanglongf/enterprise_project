package com.jumbo.dao.expressRadar;


import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonLineCommand;
import com.jumbo.wms.model.expressRadar.RadarWarningReasonLine;


@Transactional
public interface RadarWarningReasonLineDao extends GenericEntityDao<RadarWarningReasonLine, Long> {

    @NativeQuery
    List<RadarWarningReasonLineCommand> findWarningReasonLineByErrorCode(@QueryParam(value = "errorCode") String errorCode, RowMapper<RadarWarningReasonLineCommand> rowMapper);
    @NativeQuery
    List<RadarWarningReasonLineCommand> findRadarWarningReasonLine(@QueryParam(value = "id") Long id, RowMapper<RadarWarningReasonLineCommand> rowMapper);
}
