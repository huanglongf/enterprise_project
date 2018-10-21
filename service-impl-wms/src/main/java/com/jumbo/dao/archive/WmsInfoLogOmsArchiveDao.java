package com.jumbo.dao.archive;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.archive.wmsInfoLogOmsArchive;

@Transactional
public interface WmsInfoLogOmsArchiveDao extends GenericEntityDao<wmsInfoLogOmsArchive, Long> {

}
