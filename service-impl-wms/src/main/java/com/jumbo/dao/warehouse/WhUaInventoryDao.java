package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.warehouse.WhUaInventory;


@Transactional
public interface WhUaInventoryDao extends GenericEntityDao<WhUaInventory, Long>{

}
