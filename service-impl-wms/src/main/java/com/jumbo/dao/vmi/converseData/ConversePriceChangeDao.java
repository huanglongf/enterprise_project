package com.jumbo.dao.vmi.converseData;


import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConversePriceChange;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ConversePriceChangeDao extends GenericEntityDao<ConversePriceChange, Long> {

    @NativeUpdate
    public void updateOldPriceById(@QueryParam("style") String style, @QueryParam("color") String color, @QueryParam("oldPrice") BigDecimal oldPrice);

}
