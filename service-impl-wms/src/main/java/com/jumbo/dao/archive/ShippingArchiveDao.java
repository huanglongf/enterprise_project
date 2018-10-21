package com.jumbo.dao.archive;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.archive.ShippingArchive;

@Transactional
public interface ShippingArchiveDao extends GenericEntityDao<ShippingArchive, Long> {

}
