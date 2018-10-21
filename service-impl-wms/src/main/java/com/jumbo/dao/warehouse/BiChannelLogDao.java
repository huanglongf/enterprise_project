package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.BiChannelLog;

@Transactional
public interface BiChannelLogDao extends GenericEntityDao<BiChannelLog, Long>{

}
