package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ImperfectStvLog;

@Transactional
public interface ImperfectStvLogDao extends GenericEntityDao<ImperfectStvLog, Long> {

}
