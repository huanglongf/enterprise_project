package com.jumbo.dao.vmi.philipsData;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.philipsData.PhilipsProvice;

@Transactional
public interface PhilipsProviceDao extends GenericEntityDao<PhilipsProvice, Long> {

    @NamedQuery
    public PhilipsProvice getProviecByCode(@QueryParam("code") String code);
}
