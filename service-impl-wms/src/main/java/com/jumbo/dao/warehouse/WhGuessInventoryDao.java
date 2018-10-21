package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.WhGuessInventory;

@Transactional
public interface WhGuessInventoryDao extends GenericEntityDao<WhGuessInventory, Long>{

}
