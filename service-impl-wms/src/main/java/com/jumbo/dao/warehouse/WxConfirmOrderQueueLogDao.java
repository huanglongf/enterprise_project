package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WxConfirmOrderQueueLog;

@Transactional
public interface WxConfirmOrderQueueLogDao extends GenericEntityDao<WxConfirmOrderQueueLog, Long> {



}
