package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.QueueStaLineOwnerLog;

@Transactional
public interface QueueStaLineOwnerLogDao extends GenericEntityDao<QueueStaLineOwnerLog, Long> {

}
