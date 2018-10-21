package com.lmis.sys.codeRule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.codeRule.model.SysCoderuleRule;
import com.lmis.sys.codeRule.model.ViewSysCoderuleRule;
import com.lmis.sys.codeRule.vo.RuleDataTypeVo;

/** 
 * @ClassName: SysCoderuleRuleMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-22 10:32:25
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysCoderuleRuleMapper<T extends SysCoderuleRule> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewSysCoderuleRule
	 * @Description: TODO(查询view_sys_coderule_rule)
	 * @param viewSysCoderuleRule
	 * @return: List<ViewSysCoderuleRule>
	 * @author: codeGenerator
	 * @date: 2018-05-22 10:32:25
	 */
	List<ViewSysCoderuleRule> retrieveViewSysCoderuleRule(ViewSysCoderuleRule viewSysCoderuleRule);
	
	/**
	 * @Description:获取规则数据类型和名称、编号
	 * @author: xuyisu
	 * @return
	 * @date: 2018-05-23 10:32:25
	 */
	List<RuleDataTypeVo> getRuleDataType();
	
	/**
     * 获取当前rulecode获取规则信息
     * @param configcode
     * @return
     */
    SysCoderuleRule getRuleInfoByRuleCode(@Param("rulecode") String rulecode);
}
