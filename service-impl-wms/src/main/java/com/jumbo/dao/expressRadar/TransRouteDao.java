package com.jumbo.dao.expressRadar;


import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.expressRadar.TransRoute;


@Transactional
public interface TransRouteDao extends GenericEntityDao<TransRoute, Long> {

    @NamedQuery
    Long getTransRouteByParam(@QueryParam(value = "expressCode") String expressCode, @QueryParam(value = "operateTime") Date operateTime);

    @NativeQuery
    List<TransRoute> findTransRouteByExpressCode(@QueryParam(value = "expressCode") String expressCode, RowMapper<TransRoute> rowMapper);
}
