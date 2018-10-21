package com.jumbo.dao.pms.parcel;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.ParcelTransDetail;

@Transactional
public interface ParcelTransDetailDao extends GenericEntityDao<ParcelTransDetail, Long> {
	
}