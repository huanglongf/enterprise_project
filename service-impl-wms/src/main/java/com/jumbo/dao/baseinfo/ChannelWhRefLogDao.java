package com.jumbo.dao.baseinfo;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.baseinfo.ChannelWhRefLog;

@Transactional
public interface ChannelWhRefLogDao extends GenericEntityDao<ChannelWhRefLog, Long>{

}
