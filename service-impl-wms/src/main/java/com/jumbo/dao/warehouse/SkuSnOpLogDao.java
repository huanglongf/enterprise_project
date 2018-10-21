package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SkuSnOpLog;

@Transactional
public interface SkuSnOpLogDao extends GenericEntityDao<SkuSnOpLog, Long> {

}
