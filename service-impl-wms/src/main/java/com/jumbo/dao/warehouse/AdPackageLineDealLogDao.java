package com.jumbo.dao.warehouse;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.AdPackageLineDealLog;
import com.jumbo.wms.model.warehouse.AdPackageLineDealLogDto;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

public interface AdPackageLineDealLogDao extends GenericEntityDao<AdPackageLineDealLog, Long> {
    @NativeQuery(pagable = true)
    Pagination<AdPackageLineDealLogDto> adPackageLog(int start, int pageSize, RowMapper<AdPackageLineDealLogDto> rowMapper, Sort[] sorts, @QueryParam(value = "adOrderId") String adOrderId);
}
