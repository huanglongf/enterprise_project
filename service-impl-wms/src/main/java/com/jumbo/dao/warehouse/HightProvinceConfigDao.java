package com.jumbo.dao.warehouse;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.HightProvinceConfig;

@Transactional
public interface HightProvinceConfigDao extends GenericEntityDao<HightProvinceConfig, Long> {

    @NativeQuery(pagable = true)
    Pagination<HightProvinceConfig> queryHightProvinceConfig(int start, int pagesize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "ouTypeId") Long ouTypeId, @QueryParam(value = "name") String name, RowMapper<HightProvinceConfig> r);
}
