package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.ExpressConfirmOrderQueueLog;
@Transactional
public interface ExpressConfirmOrderQueueLogDao extends GenericEntityDao<ExpressConfirmOrderQueueLog, Long> {
    
}
