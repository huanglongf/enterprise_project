package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.QueueLogSta;
@Transactional
public interface QueueLogStaDao extends GenericEntityDao<QueueLogSta, Long> {
	

}
