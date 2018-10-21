package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueStaPayment;

@Transactional
public interface QueueStaPaymentDao extends GenericEntityDao<QueueStaPayment, Long> {

    /**
     * 根据qId获得费用列表
     */
    @NativeQuery
    public List<QueueStaPayment> findByStaPaymentId(@QueryParam(value = "qId") long qId, RowMapper<QueueStaPayment> beanPropertyRowMapper);

    /**
     * 根据ID删除队列信息
     * 
     * @param id
     * @return
     */
    @NativeUpdate
    public int deleteStaPayment(@QueryParam("id") Long id);


}
