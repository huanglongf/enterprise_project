package com.jumbo.dao.archive;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.archive.ConfirmOrderArchive;

@Transactional
public interface ConfirmOrderArchiveDao extends GenericEntityDao<ConfirmOrderArchive, Long> {

}
