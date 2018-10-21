package com.jumbo.dao.warehouse;


import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PdaHandOverLog;

@Transactional
public interface PdaHandOverLogDao extends GenericEntityDao<PdaHandOverLog, Long> {

    @NativeQuery
    public PdaHandOverLog findPdaHandOverLogByTransNo(@QueryParam("transNo") String transNo, RowMapper<PdaHandOverLog> rowMapper);
}
