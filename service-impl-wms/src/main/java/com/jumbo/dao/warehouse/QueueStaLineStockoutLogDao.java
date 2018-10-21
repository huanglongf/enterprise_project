package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueStaLineStockoutLog;

@Transactional
public interface QueueStaLineStockoutLogDao extends GenericEntityDao<QueueStaLineStockoutLog, Long> {

}
