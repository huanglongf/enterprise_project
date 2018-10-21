package com.jumbo.dao.expressRadar;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.expressRadar.SysRadarWarningLv;

@Transactional
public interface SysRadarWarningLvDao extends GenericEntityDao<SysRadarWarningLv, Long> {

    @NativeQuery
    List<SysRadarWarningLv> findRdWarningLv(RowMapper<SysRadarWarningLv> rowMapper);

    @NativeQuery
    List<SysRadarWarningLv> findRdWarningLvByLv(@QueryParam(value = "id") Long id, RowMapper<SysRadarWarningLv> rowMapper);

}
