package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.EbsInventory;

@Transactional
public interface EbsInventoryDao  extends GenericEntityDao<EbsInventory, Long> {
	@NamedQuery
	public List<EbsInventory> queryEbsInventory();

}
