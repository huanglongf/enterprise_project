package com.jumbo.dao.warehouse;


import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhTransOlConfig;

@Transactional
public interface WhTransOlConfigDao extends GenericEntityDao<WhTransOlConfig, Long> {

    @NativeQuery
    List<WhTransOlConfig> findTransOlConfig(@QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "departure") String departure, RowMapper<WhTransOlConfig> rowMapper);
}
