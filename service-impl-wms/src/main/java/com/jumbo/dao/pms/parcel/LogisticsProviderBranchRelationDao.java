package com.jumbo.dao.pms.parcel;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.LogisticsProviderBranchRelation;

@Transactional
public interface LogisticsProviderBranchRelationDao extends GenericEntityDao<LogisticsProviderBranchRelation, Long> {
	
    @NamedQuery
    LogisticsProviderBranchRelation findByBlIdAndPriority(@QueryParam("blId") Long blId, @QueryParam("priority") Integer priority);
    
    @NamedQuery
    List<LogisticsProviderBranchRelation> findByBlId(@QueryParam("blId") Long blId);
    
}