package com.jumbo.dao.pms.parcel;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.ParcelLine;

@Transactional
public interface ParcelLineDao extends GenericEntityDao<ParcelLine, Long> {
	
}