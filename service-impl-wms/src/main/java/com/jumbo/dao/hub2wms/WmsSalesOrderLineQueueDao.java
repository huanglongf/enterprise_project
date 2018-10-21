package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsSalesOrderLineQueue;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsSalesOrderLineQueueDao extends GenericEntityDao<WmsSalesOrderLineQueue, Long> {
    /**
     * 根据订单头查询订单明细
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WmsSalesOrderLineQueue> getOrderLineQueueById(@QueryParam("orderId") Long id, BeanPropertyRowMapper<WmsSalesOrderLineQueue> beanPropertyRowMapper);

    @NativeQuery
    List<Long> findOuByOrderId(@QueryParam("orderId") Long id, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<WmsSalesOrderLineQueue> findLineByOuAndOrderId(@QueryParam("ouId") Long ouId, @QueryParam("id") Long id, BeanPropertyRowMapper<WmsSalesOrderLineQueue> beanPropertyRowMapper);

    @NativeUpdate
    void cleanDataByOrderId(@QueryParam("id") Long id);

    @NativeQuery
    Boolean findOrderIfExists(@QueryParam("orderCode") String orderCode, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    @NativeQuery
    StockTransApplication staStatusCancleByOrderCode(@QueryParam("orderCode") String orderCode, BeanPropertyRowMapper<StockTransApplication> beanPropertyRowMapper);

}
