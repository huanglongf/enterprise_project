package com.jumbo.dao.hub2wms;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnSenderInfo;

@Transactional
public interface CnSenderInfoDao extends GenericEntityDao<CnSenderInfo, Long> {

}
