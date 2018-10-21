package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.vmi.Default.VmiStatusAdjLineDefault;

public interface VmiStatusAdjLineDao extends GenericEntityDao<VmiStatusAdjLineDefault, Long> {
    @NativeQuery
    List<VmiStatusAdjLineDefault> findVmiStatusAdjLineByAdjId(@QueryParam("adjid") Long adjid, RowMapper<VmiStatusAdjLineDefault> r);

}
