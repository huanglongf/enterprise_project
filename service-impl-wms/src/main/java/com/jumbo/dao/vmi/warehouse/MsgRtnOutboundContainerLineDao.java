package com.jumbo.dao.vmi.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundContainerLine;

/**
 * 顺丰外包仓出库反馈装箱明细
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface MsgRtnOutboundContainerLineDao extends GenericEntityDao<MsgRtnOutboundContainerLine, Long> {

}
