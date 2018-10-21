package com.jumbo.dao.vmi.espData;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPGiftItem;

import loxia.dao.GenericEntityDao;

@Transactional
public interface ESPGiftItemDao extends GenericEntityDao<ESPGiftItem, Long> {


}
