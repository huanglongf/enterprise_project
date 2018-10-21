package com.jumbo.dao.lmis.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.OperatingCost;

@Transactional
public interface OperatingCostDao extends GenericEntityDao<OperatingCost, Long> {
    @NativeQuery(model = OperatingCost.class)
    List<OperatingCost> findOperatingCostByTime(@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime, @QueryParam("orderType") String orderType, @QueryParam("storecode") String storecode, @QueryParam("type") String type);

    @NativeQuery(pagable = true)
    Pagination<OperatingCost> findOperatingCostByTime(int start, int pageSize, @QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime, @QueryParam("orderType") String orderType, @QueryParam("storecode") String storecode,
            @QueryParam("type") String type, BeanPropertyRowMapper<OperatingCost> r, Sort[] sorts);
}
