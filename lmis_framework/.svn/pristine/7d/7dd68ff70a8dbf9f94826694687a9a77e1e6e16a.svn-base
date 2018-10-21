package com.lmis.sys.codeRule.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.OauthUtil;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.constant.BaseConstant;
import com.lmis.sys.codeRule.constant.RuleConstants;
import com.lmis.sys.codeRule.dao.SysCoderuleDataMapper;
import com.lmis.sys.codeRule.dao.SysCoderuleRuleMapper;
import com.lmis.sys.codeRule.model.SysCoderuleData;
import com.lmis.sys.codeRule.model.SysCoderuleRule;
import com.lmis.sys.codeRule.service.SysCoderuleDataServiceInterface;

import cn.hutool.core.util.StrUtil;

/**
 * @ClassName: SysCoderuleDataServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-05-17 13:16:02
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SysCoderuleDataServiceImpl<T extends SysCoderuleData> implements SysCoderuleDataServiceInterface<T>{

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<SysCoderuleData> dynamicSqlService;

    @Autowired
    private SysCoderuleDataMapper<T> sysCoderuleDataMapper;

    @Autowired
    private SysCoderuleRuleMapper<SysCoderuleRule> sysCoderuleRuleMapper;

    @Autowired
    private OauthUtil oauthUtil;

    @Override
    public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam,LmisPageObject pageObject) throws Exception{
        return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
        LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
        if (LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode()))
            return _lmisResult;
        List<Map<String, Object>> result = (List<Map<String, Object>>) _lmisResult.getData();
        if (ObjectUtils.isNull(result))
            throw new Exception(BaseConstant.EBASE000007);
        if (result.size() != 1)
            throw new Exception(BaseConstant.EBASE000008);
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
    }

    @Override
    public LmisResult<?> executeInsert(T t) throws Exception{

        LmisResult<T> _lmisResult = new LmisResult<T>();
        //判空

        if (StrUtil.isBlank(t.getRuleCode()))
            throw new Exception(BaseConstant.EBASE000016);
        if (StrUtil.isBlank(t.getConfigCode()))
            throw new Exception(BaseConstant.EBASE000015);
        if (StrUtil.isNotBlank(t.getStartValue()))
            t.setDataValuelg(t.getStartValue().length());
        CheckValueByRuleCode(t);
        //获取已经存储的数量
        int dataCountByConfigCode = sysCoderuleDataMapper.getDataCountByConfigCode(t.getConfigCode());
        t.setDataOrder(++dataCountByConfigCode);
        t.setCreateBy(oauthUtil.GetCurentUserId());
        t.setUpdateBy(oauthUtil.GetCurentUserId());
        // 插入操作
        if (sysCoderuleDataMapper.create(t) == 1)
            _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return _lmisResult;
        // 插入操作
    }

    @Override
    public LmisResult<?> executeUpdate(T t) throws Exception{

        LmisResult<T> _lmisResult = new LmisResult<T>();
        //判空
        if (StrUtil.isBlank(t.getId()))
            throw new Exception(BaseConstant.EBASE000004);
        if (StrUtil.isBlank(t.getRuleCode()))
            throw new Exception(BaseConstant.EBASE000016);
        if (StrUtil.isBlank(t.getConfigCode()))
            throw new Exception(BaseConstant.EBASE000015);
        if (StrUtil.isNotBlank(t.getStartValue()))
            t.setDataValuelg(t.getStartValue().length());
        CheckValueByRuleCode(t);
        t.setCreateBy(oauthUtil.GetCurentUserId());
        t.setUpdateBy(oauthUtil.GetCurentUserId());
        // 插入操作
        if (sysCoderuleDataMapper.update(t) == 1)
            _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return _lmisResult;
    }
    
    
    /**
     * 根据传入的编号查看此类型数据的正确性
     * @author xuyisu
     * @date  2018年5月29日
     * 
     * @param t
     * @throws Exception
     */
    public void CheckValueByRuleCode(T t) throws Exception
    {
      //根据规则编号获取规则信息
        SysCoderuleRule sysCoderuleRule = sysCoderuleRuleMapper.getRuleInfoByRuleCode(t.getRuleCode());
        if (sysCoderuleRule != null){
            if (StrUtil.isNotBlank(sysCoderuleRule.getDataType())){
                if (sysCoderuleRule.getDataType().equals(RuleConstants.RULE_GROWTH)){
                    Pattern pattern = Pattern.compile(BaseConstant.SYS_CODERULE_DATA_GROWTH);
                    Matcher matcher = pattern.matcher(t.getStartValue().trim());
                    if (!matcher.matches()){
                        throw new Exception(BaseConstant.EBASE000017);
                    }
                }
                if(sysCoderuleRule.getDataType().equals(RuleConstants.RULE_CONSTANT))
                {
                    Pattern pattern = Pattern.compile(BaseConstant.SYS_RULE_LETTER_NUM);
                    Matcher matcher = pattern.matcher(t.getStartValue().trim());
                    Pattern pattern_letter = Pattern.compile(BaseConstant.SYS_RULE_LETTER);
                    Matcher matcher_letter = pattern_letter.matcher(t.getStartValue().trim());
                    Pattern pattern_num = Pattern.compile(BaseConstant.SYS_RULE_NUM);
                    Matcher matcher_num = pattern_num.matcher(t.getStartValue().trim());
                    boolean b = matcher.matches()||matcher_letter.matches()||matcher_num.matches();
                    if (!b){
                        throw new Exception(BaseConstant.EBASE000020);
                    }
                }
            }
        }
    }
    

    @Override
    public LmisResult<?> deleteSysCoderuleData(T t) throws Exception{

        // TODO(业务校验)

        // 删除操作
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sysCoderuleDataMapper.logicalDelete(t));
    }

}
