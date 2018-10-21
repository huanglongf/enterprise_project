package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.ValueAddedServiceQueue;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface ValueAddedServiceQueueDao extends GenericEntityDao<ValueAddedServiceQueue, Long> {
    @NativeQuery
    List<ValueAddedServiceQueue> getAllAddedServiceByOrderId(@QueryParam("orderId") Long id, BeanPropertyRowMapper<ValueAddedServiceQueue> beanPropertyRowMapper);

    @NativeUpdate
    void cleanDataByOrderId(@QueryParam("id") Long id);

    @NativeUpdate
    void cleanDataByOrderLineId(@QueryParam("id") Long id);

    @NativeQuery
    List<ValueAddedServiceQueue> getAllAddedServiceByOrderLineId(@QueryParam("orderLineId") Long id, BeanPropertyRowMapper<ValueAddedServiceQueue> beanPropertyRowMapper);

}
