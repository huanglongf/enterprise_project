package com.jumbo.dao.vmi.itochuData;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.ItochuRtnInv;

@Transactional
public interface ItochuRtnInvDao extends GenericEntityDao<ItochuRtnInv, Long> {

}
