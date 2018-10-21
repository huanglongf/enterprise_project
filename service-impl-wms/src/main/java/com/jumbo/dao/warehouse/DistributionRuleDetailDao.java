package com.jumbo.dao.warehouse;


import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.DistributionRuleDetail;
import com.jumbo.wms.model.warehouse.DistributionRuleDetailCommand;


/**
 * @author NCGZ-DZ-SJ
 */
@Transactional
public interface DistributionRuleDetailDao extends GenericEntityDao<DistributionRuleDetail, Long> {
	
	
	@NativeQuery
	List<DistributionRuleDetailCommand> getDistributionRuleConditionCurrentDetail( @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "ruleId") Long ruleId, RowMapper<DistributionRuleDetailCommand> r);

	@NativeQuery(pagable = true)
	Pagination<DistributionRuleDetailCommand> getDistributionRuleConditionCurrentDetail(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "ruleId") Long ruleId, RowMapper<DistributionRuleDetailCommand> r, Sort[] sorts);
	
	/*@NativeQuery
	List<DistributionRule> checkRuleNameIsExist(@QueryParam("ruleName") String ruleName, RowMapper<DistributionRule> beanPropertyRowMapper);*/
	
	/*@NativeQuery(model = DistributionRuleCommand.class)
	List<DistributionRuleCommand> findAllDistributionRule(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "ruleName") String ruleName);*/
}
