package com.jumbo.dao.vmi.espData;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.EspSeason;

import loxia.dao.GenericEntityDao;

@Transactional
public interface ESPSeasonDao extends GenericEntityDao<EspSeason, Long> {


}
