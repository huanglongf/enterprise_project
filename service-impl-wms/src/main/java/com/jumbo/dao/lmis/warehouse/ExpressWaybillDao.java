package com.jumbo.dao.lmis.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.ExpressWaybill;

@Transactional
public interface ExpressWaybillDao extends GenericEntityDao<ExpressWaybill, Long> {

    @NativeQuery(model = ExpressWaybill.class)
    List<ExpressWaybill> findExpressWaybillByTime(@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime, @QueryParam("timeType") String timeType);

    @NativeQuery(pagable = true)
    Pagination<ExpressWaybill> findExpressWaybillByTime(int start, int pageSize, @QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime, @QueryParam("timeType") String timeType, @QueryParam("trackingNo") String trackingNo,
        @QueryParam("ownCode") String ownCode,@QueryParam("whCode") String whCode,BeanPropertyRowMapper<ExpressWaybill> r, Sort[] sorts);
}
