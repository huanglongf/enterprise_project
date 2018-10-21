package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsShipping;
import com.jumbo.wms.model.hub2wms.WmsShippingQueue;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsShippingQueueDao extends GenericEntityDao<WmsShippingQueue, Long> {
    @NativeQuery
    List<WmsShipping> findAllShippingById(@QueryParam("id") Long id, BeanPropertyRowMapper<WmsShipping> beanPropertyRowMapper);

    @NativeQuery
    List<WmsShippingQueue> findAllShippingQueueById(@QueryParam("orderId") Long orderId, BeanPropertyRowMapper<WmsShippingQueue> beanPropertyRowMapper);

}
