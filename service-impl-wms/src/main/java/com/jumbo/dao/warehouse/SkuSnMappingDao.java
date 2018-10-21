package com.jumbo.dao.warehouse;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.SkuSnMapping;

@Transactional
public interface SkuSnMappingDao extends GenericEntityDao<SkuSnMapping, Long> {

    @NativeQuery
    SkuSnMapping getSkuSnMappingBySn(@QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "skuid") Long skuid, RowMapper<SkuSnMapping> r);

    @NativeQuery
    SkuSnMapping findMappingSkuSnBySn(@QueryParam(value = "sn") String sn, RowMapper<SkuSnMapping> r);

    @NativeUpdate
    void deleteSkuSnMappingByStaIdAndStvId(@QueryParam(value = "staId") Long staId, @QueryParam(value = "stvId") Long stvId);

    @NativeQuery
    SkuSnMapping findByStaIdAndSn(@QueryParam(value = "staId") Long staId, @QueryParam(value = "sn") String sn, RowMapper<SkuSnMapping> r);
}
