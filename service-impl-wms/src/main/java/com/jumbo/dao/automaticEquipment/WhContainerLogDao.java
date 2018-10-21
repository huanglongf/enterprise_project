package com.jumbo.dao.automaticEquipment;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.WhContainerLog;

/**
 * 出库周转箱日志DAO
 * 
 * @author jinlong.ke
 * @date 2016年2月26日下午5:18:36
 */
@Transactional
public interface WhContainerLogDao extends GenericEntityDao<WhContainerLog, Long> {


}
