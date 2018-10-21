package com.jumbo.dao.expressRadar;


import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.expressRadar.RadarTimeRuleLog;


@Transactional
public interface RadarTimeRuleLogDao extends GenericEntityDao<RadarTimeRuleLog, Long> {


}
