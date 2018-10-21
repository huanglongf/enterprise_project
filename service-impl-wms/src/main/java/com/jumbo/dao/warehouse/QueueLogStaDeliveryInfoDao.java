package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.QueueLogStaDeliveryInfo;
@Transactional
public interface QueueLogStaDeliveryInfoDao extends GenericEntityDao<QueueLogStaDeliveryInfo, Long> {

}
