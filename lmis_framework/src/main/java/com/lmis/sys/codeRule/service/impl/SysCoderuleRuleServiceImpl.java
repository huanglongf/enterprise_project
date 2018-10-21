package com.lmis.sys.codeRule.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.DateUtils;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.OauthUtil;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.constant.BaseConstant;
import com.lmis.sys.codeRule.constant.RuleConstants;
import com.lmis.sys.codeRule.dao.SysCoderuleRuleMapper;
import com.lmis.sys.codeRule.model.SysCoderuleRule;
import com.lmis.sys.codeRule.service.SysCoderuleRuleServiceInterface;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

/** 
 * @ClassName: SysCoderuleRuleServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-05-21 11:32:30
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SysCoderuleRuleServiceImpl<T extends SysCoderuleRule> implements SysCoderuleRuleServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<SysCoderuleRule> dynamicSqlService;
	@Autowired
	private SysCoderuleRuleMapper<T> sysCoderuleRuleMapper;
    @Autowired
	private OauthUtil oauthUtil;
	
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
		if(LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode())) return _lmisResult;
		List<Map<String, Object>> result =  (List<Map<String, Object>>) _lmisResult.getData();
		if(ObjectUtils.isNull(result)) throw new Exception(BaseConstant.EBASE000007);
		if(result.size() != 1) throw new Exception(BaseConstant.EBASE000008);
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

    @Override
	public LmisResult<?> executeInsert(T t) throws Exception {
	    LmisResult<T> _lmisResult=new LmisResult<T>();
        //判空
        
	    if(StrUtil.isBlank(t.getRuleName())) throw new Exception(BaseConstant.EBASE000018);
	    if(StrUtil.isBlank(t.getDataType())) throw new Exception(BaseConstant.EBASE000019);
	    
	    t.setRuleCode(DateUtils.CreatDate()+RandomUtil.randomNumbers(RuleConstants.RULE_RANDOM_VALUE));
	    t.setCreateBy(oauthUtil.GetCurentUserId());
	    t.setUpdateBy(oauthUtil.GetCurentUserId());
		// 插入操作
        if(sysCoderuleRuleMapper.create(t) == 1)  _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return _lmisResult;
	}

	@Override
	public LmisResult<?> executeUpdate(T t) throws Exception {
			
		// TODO(业务校验)
	    LmisResult<T> _lmisResult=new LmisResult<T>();
        //判空
	    if(StrUtil.isBlank(t.getId())) throw new Exception(BaseConstant.EBASE000004);
        if(t!=null&&StrUtil.isBlank(t.getRuleName())) throw new Exception(BaseConstant.EBASE000018);
        if(t!=null&&StrUtil.isBlank(t.getDataType())) throw new Exception(BaseConstant.EBASE000019);
        
        // 更新操作
        if(sysCoderuleRuleMapper.update(t) == 1)  _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return _lmisResult;
	}

	@Override
	public LmisResult<?> deleteSysCoderuleRule(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sysCoderuleRuleMapper.logicalDelete(t));
	}

    @Override
    public LmisResult<?>  getRuleDataType(){
        // TODO Auto-generated method stub
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sysCoderuleRuleMapper.getRuleDataType());
    }

}
