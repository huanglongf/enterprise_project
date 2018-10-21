package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsSalesOrderPaymentQueue;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsSalesOrderPaymentQueueDao extends GenericEntityDao<WmsSalesOrderPaymentQueue, Long> {
    @NativeQuery
    List<WmsSalesOrderPaymentQueue> getAllPaymentByOrderId(@QueryParam("id") Long id, BeanPropertyRowMapper<WmsSalesOrderPaymentQueue> beanPropertyRowMapper);

    @NativeUpdate
    void cleanDataByOrderId(@QueryParam("id") Long id);

}
