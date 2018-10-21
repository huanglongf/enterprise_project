package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.StaCheckLog;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface StaCheckLogDao extends GenericEntityDao<StaCheckLog, Long> {

    /**
     * 插入sn
     * @param orderCode
     * @param staCode
     */
    @NativeUpdate
    void insertStaCheckLogByStvId(@QueryParam("orderCode") String orderCode, @QueryParam("staCode") String staCode, @QueryParam("stvid") Long stvid);

    @NativeQuery
    List<String> findSnBySkuIdAndSlipCode(@QueryParam("skuId") Long skuId, @QueryParam("orderCode") String orderCode, RowMapper<String> row);

    @NativeQuery
    List<String> findRfidBySkuIdAndSlipCode(@QueryParam("skuId") Long skuId, @QueryParam("orderCode") String orderCode, RowMapper<String> row);

    @NativeQuery
    List<StaCheckLog> findExpDateBySkuIdAndSlipCode(@QueryParam("skuId") Long skuId, @QueryParam("orderCode") String orderCode, RowMapper<StaCheckLog> row);

}
