package com.jumbo.dao.vmi.itData;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.itData.ITBrand;

@Transactional
public interface ITBrandDao extends GenericEntityDao<ITBrand, Long> {

}
