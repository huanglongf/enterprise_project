package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.QueueLogStaLine;
@Transactional
public interface QueueLogStaLineDao extends GenericEntityDao<QueueLogStaLine, Long> {

}
