/**
 * F * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.web.action.warehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.warehouse.DistributionRule;
import com.jumbo.wms.model.warehouse.DistributionRuleCommand;
import com.jumbo.wms.model.warehouse.DistributionRuleCondition;
import com.jumbo.wms.model.warehouse.DistributionRuleConditionCommand;
import com.jumbo.wms.model.warehouse.DistributionRuleDetailCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author SJ
 * 
 */
public class DistributionRuleAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4012186321121611460L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    
	private DistributionRule distributionRule;
	
	private String groupCode;
	
	private String idList;
	
	private String remark;
	
	private Long ruleId;
	
    /**
     * 查寻所有的配货规则
     * 
     * @return
     */
	public String findAllDistributionRule() {
		setTableConfig();
		Pagination<DistributionRuleCommand> distributionRuleList = wareHouseManagerQuery.findAllDistributionRule(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), distributionRule.getName(), tableConfig.getSorts());
		request.put(JSON, toJson(distributionRuleList));
		return JSON;
	}
	
	/**
	 * 根据规则Id禁用对应的配合规则(只做状态修改，不做物理删除)
	 * @return
	 */
	public String deleteDistributionRuleById(){
		JSONObject result = new JSONObject();
		try {
			wareHouseManager.deleteDistributionRuleById(distributionRule.getId());
			result.put("status", "success");
		} catch (JSONException e) {
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}
	
	/**
	 * 条件描述下拉框加载
	 * @return
	 */
	public String getDistributionRuleCondition(){
		JSONObject result = new JSONObject();
		try {
			List<DistributionRuleCondition>  drcList = wareHouseManager.getDistributionRuleConditionList();
			result.put("drcList", drcList);
		} catch (JSONException e) {
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}
	
	
	/**
	 * 根据条件组编码进行查询筛选
	 * @return
	 */
	public String getDistributionRuleConditionDetail(){
		setTableConfig();
		if(!StringUtils.hasText(groupCode)){
			groupCode = null;
		}
		List<DistributionRuleConditionCommand> distributionRuleConditionList = wareHouseManager.getDistributionRuleConditionDetail(groupCode);
		request.put(JSON, toJson(distributionRuleConditionList));
		return JSON;
	}
	
	//新建保存配货规则校验名称是否存在
	public String checkRuleNameIsExist(){
		JSONObject result = new JSONObject();
		try {
			Integer dr = wareHouseManager.checkRuleNameIsExist(distributionRule.getName());
			if(dr > 0){
				result.put("success", "success");
			}else{
				result.put("error", "error");
			}
		} catch (JSONException e) {
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}
	
	//新建配货规则
	public String newDistributionRuleAndDetail(){
		JSONObject result = new JSONObject();
		List<Long> list = new ArrayList<Long>();
		String idListStr = idList;
		if(StringUtils.hasText(idListStr)){
			String[] ids = idListStr.split("-");
			List<String> ss = Arrays.asList(ids);
			for (String str : ss) {
				Long l = Long.valueOf(str);
				list.add(l);
			}
		}
 		try {
			wareHouseManager.newDistributionRuleAndDetail(distributionRule.getName(),userDetails.getCurrentOu().getId(),userDetails.getUser().getId(),list,remark);
			result.put("success", "success");
		} catch (JSONException e) {
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}
	
	//编辑页面当前规则详情
	public String getDistributionRuleConditionCurrentDetail(){
		JSONObject result = new JSONObject();
		try {
			List<DistributionRuleDetailCommand> distributionRuleDetailList = wareHouseManager.getDistributionRuleConditionCurrentDetail( userDetails.getCurrentOu().getId(), distributionRule.getId());
			result.put("dbrdList", distributionRuleDetailList);
		} catch (JSONException e) {
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}
	
	
	//配合规则条件明细分页
	public String getDistributionRuleConditionCurrentDetailPagination(){
		setTableConfig();
		Pagination<DistributionRuleDetailCommand> distributionRuleList = wareHouseManager.getDistributionRuleConditionCurrentDetail(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), distributionRule.getId(), tableConfig.getSorts());
		request.put(JSON, toJson(distributionRuleList));
		return JSON;
	}
	
	// 修改保存配货规则
	public String updateDistributionRuleAndDetail() {
		JSONObject result = new JSONObject();
		List<Long> list = new ArrayList<Long>();
		String idListStr = idList;
		if (StringUtils.hasText(idListStr)) {
			String[] ids = idListStr.split("-");
			List<String> ss = Arrays.asList(ids);
			for (String str : ss) {
				Long l = Long.valueOf(str);
				list.add(l);
			}
		}
		try {
			wareHouseManager.updateDistributionRuleAndDetail(distributionRule.getName(), userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), list, remark);
			result.put("success", "success");
		} catch (JSONException e) {
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}
	
	public DistributionRule getDistributionRule() {
		return distributionRule;
	}

	public void setDistributionRule(DistributionRule distributionRule) {
		this.distributionRule = distributionRule;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
}
