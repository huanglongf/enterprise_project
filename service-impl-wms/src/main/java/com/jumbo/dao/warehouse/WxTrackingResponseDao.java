package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WxTrackingResponse;

@Transactional
public interface WxTrackingResponseDao extends GenericEntityDao<WxTrackingResponse, Long>{

}
