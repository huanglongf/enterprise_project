package com.jumbo.dao.vmi.order;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.order.VMIOrderCancel;

import loxia.dao.GenericEntityDao;

@Transactional
public interface VMIOrderCancelDao extends GenericEntityDao<VMIOrderCancel, Long> {

}
