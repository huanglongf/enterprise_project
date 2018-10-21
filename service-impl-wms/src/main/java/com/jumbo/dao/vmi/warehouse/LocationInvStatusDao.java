package com.jumbo.dao.vmi.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.warehouse.LocationInvStatus;

@Transactional
public interface LocationInvStatusDao extends GenericEntityDao<LocationInvStatus, Long> {

    @NamedQuery
    LocationInvStatus findLocBystatus(@QueryParam(value = "status") Long status, @QueryParam(value = "vmiSource") String source);

    @NamedQuery
    LocationInvStatus findLocBySource(@QueryParam(value = "isForSales") Boolean isForSales, @QueryParam(value = "vmiSource") String source);

}
