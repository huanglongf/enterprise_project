package com.jumbo.dao.archive;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.archive.ShippingLineArchive;

@Transactional
public interface ShippingLineArchiveDao extends GenericEntityDao<ShippingLineArchive, Long> {

}
