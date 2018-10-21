package com.jumbo.dao.expressRadar;


import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.expressRadar.TransRouteWarningLog;


@Transactional
public interface TransRouteWarningLogDao extends GenericEntityDao<TransRouteWarningLog, Long> {


}
