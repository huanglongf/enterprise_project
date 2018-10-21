package com.jumbo.dao.mq;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.mq.MqInvoicePrintMsgLog;

@Transactional
public interface MqInvoicePrintMsgLogDao extends GenericEntityDao<MqInvoicePrintMsgLog, Long> {

}
