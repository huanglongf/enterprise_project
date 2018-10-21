package com.jumbo.dao.lmis.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.MaterialFeeActualUsage;

@Transactional
public interface MaterialFeeActualUsageDao extends GenericEntityDao<MaterialFeeActualUsage, Long> {
    @NativeQuery(model = MaterialFeeActualUsage.class)
    List<MaterialFeeActualUsage> findMaterialFeeActualUsageByTime(@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime, @QueryParam("store_code") String store_code);

    @NativeQuery(pagable = true)
    Pagination<MaterialFeeActualUsage> findMaterialFeeActualUsageByTime(int start, int pageSize, @QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime, @QueryParam("store_code") String store_code,
            BeanPropertyRowMapper<MaterialFeeActualUsage> r, Sort[] sorts);
}
