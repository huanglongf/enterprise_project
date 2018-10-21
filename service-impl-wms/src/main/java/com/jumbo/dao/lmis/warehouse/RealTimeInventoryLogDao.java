package com.jumbo.dao.lmis.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.RealTimeInventoryLog;

@Transactional
public interface RealTimeInventoryLogDao extends GenericEntityDao<RealTimeInventoryLog, Long> {

}
