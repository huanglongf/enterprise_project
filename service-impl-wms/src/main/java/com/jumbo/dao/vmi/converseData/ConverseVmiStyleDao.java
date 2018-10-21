package com.jumbo.dao.vmi.converseData;


import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseVmiStyle;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ConverseVmiStyleDao extends GenericEntityDao<ConverseVmiStyle, Long> {

    @NativeUpdate
    void updateRetailPriceByListPriceChange(@QueryParam("status") int status);
}
