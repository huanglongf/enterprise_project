package com.jumbo.dao.vmi.adidas;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.adidasData.AdidasAmiCheck;

@Transactional
public interface AdidasAmiCheckDao extends GenericEntityDao<AdidasAmiCheck, Long>{

}
