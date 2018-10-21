package com.jumbo.dao.vmi.converseData;



import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseEverGreen;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ConverseEverGreenDao extends GenericEntityDao<ConverseEverGreen, Long> {

    @NamedQuery
    ConverseEverGreen findEANbyUpc(@QueryParam("upc") String upc);
}
