package com.jumbo.dao.warehouse;


import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.DistributionRule;
import com.jumbo.wms.model.warehouse.DistributionRuleCommand;


/**
 * @author NCGZ-DZ-SJ
 */
@Transactional
public interface DistributionRuleDao extends GenericEntityDao<DistributionRule, Long> {

	@NativeQuery(pagable = true)
	Pagination<DistributionRuleCommand> findAllDistributionRule(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "ruleName") String ruleName, RowMapper<DistributionRuleCommand> r, Sort[] sorts);

	@NativeQuery
	List<DistributionRule> checkRuleNameIsExist(@QueryParam("ruleName") String ruleName, RowMapper<DistributionRule> beanPropertyRowMapper);
	
	@NativeQuery
	DistributionRule getIdByRuleName(@QueryParam("ruleName") String ruleName, RowMapper<DistributionRule> beanPropertyRowMapper);
	/*@NativeQuery(model = DistributionRuleCommand.class)
	List<DistributionRuleCommand> findAllDistributionRule(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "ruleName") String ruleName);*/
}
