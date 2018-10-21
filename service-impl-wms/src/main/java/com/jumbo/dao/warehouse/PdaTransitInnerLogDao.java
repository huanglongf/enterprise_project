package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PdaTransitInnerLog;

@Transactional
public interface PdaTransitInnerLogDao extends GenericEntityDao<PdaTransitInnerLog, Long> {

}
