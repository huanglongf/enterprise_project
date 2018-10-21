package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.StaPayment;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface StaPaymentDao extends GenericEntityDao<StaPayment, Long> {

}
