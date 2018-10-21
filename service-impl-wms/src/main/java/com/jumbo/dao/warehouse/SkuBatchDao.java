package com.jumbo.dao.warehouse;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SkuBatch;

@Transactional
public interface SkuBatchDao extends GenericEntityDao<SkuBatch, Long> {

    @NativeQuery
    SkuBatch findSkuBatchByInvBatchCodeAndSkuId(@QueryParam(value = "batchCode") String batchCode, @QueryParam(value = "skuid") Long skuid, RowMapper<SkuBatch> r);

    @NativeQuery
    SkuBatch findSkuBatchByInvBatchCodeAndSkuIdAndBatchNo(@QueryParam(value = "batchCode") String batchCode, @QueryParam(value = "skuid") Long skuid, @QueryParam(value = "batchno") String batchno, RowMapper<SkuBatch> r);
}
