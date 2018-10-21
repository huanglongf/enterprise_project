package com.jumbo.dao.vmi.espData;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.EspColor;

import loxia.dao.GenericEntityDao;

@Transactional
public interface ESPColorDao extends GenericEntityDao<EspColor, Long> {


}
