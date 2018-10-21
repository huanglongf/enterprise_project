package com.jumbo.dao.archive;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.archive.WmsOrderStatusOmsArchive;

@Transactional
public interface WmsOrderStatusOmsArchiveDao extends GenericEntityDao<WmsOrderStatusOmsArchive, Long> {

}
