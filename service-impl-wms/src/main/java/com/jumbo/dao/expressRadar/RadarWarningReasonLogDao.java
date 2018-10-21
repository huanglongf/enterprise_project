package com.jumbo.dao.expressRadar;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.expressRadar.RadarWarningReasonLog;

@Transactional
public interface RadarWarningReasonLogDao extends GenericEntityDao<RadarWarningReasonLog, Long> {


}
