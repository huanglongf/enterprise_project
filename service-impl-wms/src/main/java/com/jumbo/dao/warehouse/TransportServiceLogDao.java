package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.TransportServiceLog;

/**
 * 物流服务
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface TransportServiceLogDao extends GenericEntityDao<TransportServiceLog, Long> {

}
