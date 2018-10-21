package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.WhGuessInventoryRetail;

@Transactional
public interface WhGuessInventoryRetailDao extends GenericEntityDao<WhGuessInventoryRetail, Long>{

}
