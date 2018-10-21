package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsShippingLine;
import com.jumbo.wms.model.hub2wms.WmsShippingLineQueue;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsShippingLineQueueDao extends GenericEntityDao<WmsShippingLineQueue, Long> {
    @NativeQuery
    List<WmsShippingLine> findAllShippingLineById(@QueryParam("id") Long id, BeanPropertyRowMapper<WmsShippingLine> beanPropertyRowMapper);

    @NativeQuery
    List<WmsShippingLineQueue> findAllShippingLineQueueById(@QueryParam("orderId") Long orderId, BeanPropertyRowMapper<WmsShippingLineQueue> beanPropertyRowMapper);

}
