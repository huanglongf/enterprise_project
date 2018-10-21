package com.jumbo.dao.warehouse;


import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;

@Transactional
public interface AdvanceOrderAddInfoDao extends GenericEntityDao<AdvanceOrderAddInfo, Long> {

    @NativeQuery
    public AdvanceOrderAddInfo findOrderInfoByOrderCode(@QueryParam("slipCode") String slipCode, RowMapper<AdvanceOrderAddInfo> rowMapper);


    @NativeUpdate
    void updateAdvanceOrderAddInfoById(@QueryParam(value = "orderCode") String orderCode);

    @NativeUpdate
    Integer updatePreOrderBatchNo(@QueryParam(value = "batchNo") Long batchNo, @QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "num") Integer num);

    @NativeQuery
    public List<AdvanceOrderAddInfo> transInfo(RowMapper<AdvanceOrderAddInfo> rowMapper);

    @NativeQuery
    public List<AdvanceOrderAddInfo> findPreSalesOrder(@QueryParam("source") String source, RowMapper<AdvanceOrderAddInfo> rowMapper);

    @NativeQuery
    List<Long> findPreOrderBatchNoBySource(@QueryParam(value = "source") String source, RowMapper<Long> batchNo);


    @NativeQuery
    List<AdvanceOrderAddInfo> getPreOrderListByBatchNo(@QueryParam(value = "batchNo") Long batchNo, RowMapper<AdvanceOrderAddInfo> rowMapper);



    @NativeUpdate
    void updatePreStatusByBatchId(@QueryParam(value = "status") int status, @QueryParam(value = "batchNo") Long batchNo, @QueryParam(value = "staCode") String staCode);



}
