package com.jumbo.dao.archive;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.archive.WmsTransInfoOmsArchive;

@Transactional
public interface wmsTransInfoOmsArchiveDao extends GenericEntityDao<WmsTransInfoOmsArchive, Long> {

}
