package com.jumbo.dao.mq;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.mq.MqMsgBatch;

@Transactional
public interface MqMsgBatchDao extends GenericEntityDao<MqMsgBatch, Long> {

}
