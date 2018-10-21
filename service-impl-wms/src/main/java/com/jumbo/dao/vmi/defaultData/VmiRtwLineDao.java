package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiRtwLineDefault;

@Transactional
public interface VmiRtwLineDao extends GenericEntityDao<VmiRtwLineDefault, Long> {

    @NativeQuery
    List<VmiRtwLineDefault> findVmiRtwLineByRtwId(@QueryParam("rtwid") Long rtwid, RowMapper<VmiRtwLineDefault> r);
}
