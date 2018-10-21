package com.jumbo.dao.pms.parcel;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.BranchLibrary;

@Transactional
public interface BranchLibraryDao extends GenericEntityDao<BranchLibrary, Long> {
	
    @NamedQuery
    BranchLibrary findBySlipCode(@QueryParam("slipCode") String slipCode);
    
}