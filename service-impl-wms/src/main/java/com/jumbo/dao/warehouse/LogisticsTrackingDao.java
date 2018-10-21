package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.LogisticsTracking;
/**
 * 
 * @author jinlong.ke
 *
 */
@Transactional
public interface LogisticsTrackingDao extends GenericEntityDao<LogisticsTracking,Long>{

}
