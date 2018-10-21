package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiAdjLineDefault;

@Transactional
public interface VmiAdjLineDao extends GenericEntityDao<VmiAdjLineDefault, Long> {

    @NativeQuery
    List<VmiAdjLineDefault> findVmiAdjLineByAdjId(@QueryParam("adjid") Long adjid, RowMapper<VmiAdjLineDefault> r);
}
