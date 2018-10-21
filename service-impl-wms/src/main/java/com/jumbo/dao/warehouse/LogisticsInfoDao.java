package com.jumbo.dao.warehouse;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.LogisticsInfo;

@Transactional
public interface LogisticsInfoDao extends GenericEntityDao<LogisticsInfo, Long> {
    @NamedQuery
    public LogisticsInfo findBySystemKeyDefault(@QueryParam("systemKey") String systemKey, @QueryParam("isDefault") Boolean isDefault);

}
