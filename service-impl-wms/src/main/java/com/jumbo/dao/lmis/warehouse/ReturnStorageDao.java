package com.jumbo.dao.lmis.warehouse;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.ReturnStorage;

@Transactional
public interface ReturnStorageDao extends GenericEntityDao<ReturnStorage, Long> {

    @NativeQuery(pagable = true)
    Pagination<ReturnStorage> findreturnStorageDataByTime(int start, int pageSize, @QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime, @QueryParam("related_no") String related_no, BeanPropertyRowMapper<ReturnStorage> r,
            Sort[] sorts);
}
