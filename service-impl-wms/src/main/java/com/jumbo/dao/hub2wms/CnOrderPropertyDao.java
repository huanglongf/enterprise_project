package com.jumbo.dao.hub2wms;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnOrderProperty;

@Transactional
public interface CnOrderPropertyDao extends GenericEntityDao<CnOrderProperty, Long> {

    @NamedQuery
    CnOrderProperty getCnOrderPropertyByOrderCode(@QueryParam("orderCode") String orderCode);

    @NamedQuery
    CnOrderProperty getCnOrderPropertyByOrderCodeAndType(@QueryParam("orderCode") String orderCode, @QueryParam("orderType") String orderType);
}
