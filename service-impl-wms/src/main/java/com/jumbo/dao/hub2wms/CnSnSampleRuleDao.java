package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnSnSampleRule;

@Transactional
public interface CnSnSampleRuleDao extends GenericEntityDao<CnSnSampleRule, Long> {
    @NamedQuery
    List<CnSnSampleRule> getCnSnSampleRuleByCnSnSample(@QueryParam(value = "snSampleId") Long snSampleId);
}
