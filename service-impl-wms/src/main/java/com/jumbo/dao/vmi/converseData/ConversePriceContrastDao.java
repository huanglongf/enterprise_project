package com.jumbo.dao.vmi.converseData;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.converseData.ConversePriceContrast;

@Transactional
public interface ConversePriceContrastDao extends GenericEntityDao<ConversePriceContrast, Long> {

}
