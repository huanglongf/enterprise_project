package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.PackageListLog;

@Transactional
public interface PackageListLogDao extends GenericEntityDao<PackageListLog, Long> {

}
