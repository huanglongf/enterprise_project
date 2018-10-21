package com.jumbo.dao.pda;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.StaCheckRecordLog;

import loxia.dao.GenericEntityDao;


/**
 * 
 * @author lizaibiao
 * 
 */
@Transactional
public interface StaCheckRecordLogDao extends GenericEntityDao<StaCheckRecordLog, Long> {

}
