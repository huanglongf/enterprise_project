package com.jumbo.dao.vmi.levis;

import loxia.dao.GenericEntityDao;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.levisData.LevisSkmr;

@Transactional
public interface LevisSkmrDao extends GenericEntityDao<LevisSkmr, Long> {

}
