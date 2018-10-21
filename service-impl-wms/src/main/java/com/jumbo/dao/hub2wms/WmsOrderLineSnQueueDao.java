package com.jumbo.dao.hub2wms;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsOrderLineSnQueue;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsOrderLineSnQueueDao extends GenericEntityDao<WmsOrderLineSnQueue, Long> {

    @NativeQuery
    List<WmsOrderLineSnQueue> findOrderSnLineQueueById(@QueryParam("orderId") Long id, BeanPropertyRowMapper<WmsOrderLineSnQueue> beanPropertyRowMapper);
}
