package com.jumbo.dao.pms.parcel;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.parcel.ParcelInfoLine;

@Transactional
public interface ParcelInfoLineDao extends GenericEntityDao<ParcelInfoLine, Long> {
	
}