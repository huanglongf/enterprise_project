package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WxGetMailnoLog;

@Transactional
public interface WxGetMailnoLogDao extends GenericEntityDao<WxGetMailnoLog, Long> {


}
