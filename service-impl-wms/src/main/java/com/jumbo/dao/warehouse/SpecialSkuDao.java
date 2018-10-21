package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SpecialSku;
@Transactional
public interface SpecialSkuDao  extends GenericEntityDao<SpecialSku,Long>{

}
