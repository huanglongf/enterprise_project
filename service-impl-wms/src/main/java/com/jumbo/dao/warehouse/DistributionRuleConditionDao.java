package com.jumbo.dao.warehouse;


import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.DistributionRuleCondition;
import com.jumbo.wms.model.warehouse.DistributionRuleConditionCommand;


/**
 * @author NCGZ-DZ-SJ
 */
@Transactional
public interface DistributionRuleConditionDao extends GenericEntityDao<DistributionRuleCondition, Long> {

	@NativeQuery(model = DistributionRuleCondition.class)
	List<DistributionRuleCondition> getDistributionRuleConditionList();
	
	@NativeQuery
	List<DistributionRuleConditionCommand> getDistributionRuleConditionDetail(@QueryParam("groupCode") String groupCode,BeanPropertyRowMapperExt<DistributionRuleConditionCommand> r);
	
}
