package com.jumbo.dao.wmsdataimport;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.dataimport.SnAndValidDateSkuFlow;

@Transactional
public interface SnAndValidDateSkuFlowDao extends GenericEntityDao<SnAndValidDateSkuFlow, Long> {

    @NativeQuery(pagable = true)
    Pagination<SnAndValidDateSkuFlow> getSnAndValidDateSkuFlowDate(int start, int pageSize, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("owner") String owner, @QueryParam("slipCode1s") List<String> slipCode1,
            BeanPropertyRowMapper<SnAndValidDateSkuFlow> r, Sort[] sorts);
}
