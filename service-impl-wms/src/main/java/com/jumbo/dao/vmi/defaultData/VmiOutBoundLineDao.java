package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiOutBoundLineDefault;

@Transactional
public interface VmiOutBoundLineDao extends GenericEntityDao<VmiOutBoundLineDefault, Long> {


    @NativeQuery
    List<VmiOutBoundLineDefault> findVmiOutBoundLineByObid(@QueryParam("id") Long id, RowMapper<VmiOutBoundLineDefault> rowMapper);
}
