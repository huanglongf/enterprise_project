package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiInBoundLineDefault;

@Transactional
public interface VmiInBoundLineDao extends GenericEntityDao<VmiInBoundLineDefault, Long> {



    @NativeQuery
    List<VmiInBoundLineDefault> findVmiInBoundLineByIbid(@QueryParam("id") Long id, RowMapper<VmiInBoundLineDefault> rowMapper);

}
