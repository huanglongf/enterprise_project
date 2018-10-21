package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueLogStaPayment;

@Transactional
public interface QueueLogStaPaymentDao extends GenericEntityDao<QueueLogStaPayment, Long> {

    /**
     * 根据qId获得费用列表
     */
    // @NativeQuery
    // public List<QueueLogStaPayment> findByStaPaymentId(@QueryParam(value = "qId") long qId,
    // RowMapper<QueueLogStaPayment> beanPropertyRowMapper);


}
