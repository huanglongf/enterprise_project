package com.jumbo.dao.vmi.etamData;


import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.etamData.EtamSku;

@Transactional
public interface EtamSkuDao extends GenericEntityDao<EtamSku, Long> {

    @NamedQuery
    EtamSku findSkuBySku(@QueryParam("sku0") String sku0);

}
