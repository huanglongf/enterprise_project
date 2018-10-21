package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SkuChildSnLog;

@Transactional
public interface SkuChildSnLogDao extends GenericEntityDao<SkuChildSnLog, Long> {
    @NamedQuery
    public List<SkuChildSnLog> getbyStaIdSn(@QueryParam(value = "sn") String sn, @QueryParam(value = "staId") Long staId);

    @NamedQuery
    public List<SkuChildSnLog> getbyChildSn(@QueryParam(value = "sn") String sn);

}
