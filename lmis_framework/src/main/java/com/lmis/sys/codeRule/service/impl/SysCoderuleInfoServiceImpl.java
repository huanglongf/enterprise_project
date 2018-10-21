package com.lmis.sys.codeRule.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

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
import com.lmis.sys.codeRule.dao.SysCoderuleInfoMapper;
import com.lmis.sys.codeRule.model.SysCoderuleDeposit;
import com.lmis.sys.codeRule.model.SysCoderuleInfo;
import com.lmis.sys.codeRule.service.SysCoderuleDepositServiceInterface;
import com.lmis.sys.codeRule.service.SysCoderuleInfoServiceInterface;
import com.lmis.sys.codeRule.vo.RuleDepositVo;
import com.lmis.sys.codeRule.vo.RuleInfoVo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @ClassName: SysCoderuleInfoServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-05-17 13:16:34
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SysCoderuleInfoServiceImpl<T extends SysCoderuleInfo> implements SysCoderuleInfoServiceInterface<T>{

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<SysCoderuleInfo> dynamicSqlService;

    @Autowired
    private SysCoderuleInfoMapper<T> sysCoderuleInfoMapper;

    @Autowired
    private SysCoderuleDepositServiceInterface<SysCoderuleDeposit> sysCoderuleDepositServiceInterface;

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
            throw new Exception("查无记录，数据异常");
        if (result.size() != 1)
            throw new Exception("记录存在多条，数据异常");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeInsert(T t) throws Exception{

        LmisResult<T> _lmisResult = new LmisResult<T>();
        //判空
        if (StrUtil.isBlank(t.getConfigName()))
            throw new Exception(BaseConstant.EBASE000014);
        if (StrUtil.isBlank(t.getConfigCode()))
            throw new Exception(BaseConstant.EBASE000015);

        t.setCreateBy(oauthUtil.GetCurentUserId());
        t.setUpdateBy(oauthUtil.GetCurentUserId());
        //编号排重
        SysCoderuleInfo param = new SysCoderuleInfo();
        param.setIsDeleted(false);
        param.setConfigCode(t.getConfigCode());
        if (!ObjectUtils.isNull(sysCoderuleInfoMapper.retrieve((T) param)))
            throw new Exception(BaseConstant.EBASE000013);
        // 插入操作
        if (sysCoderuleInfoMapper.create(t) == 1)
            _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return _lmisResult;
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeUpdate(T t) throws Exception{

        LmisResult<T> _lmisResult = new LmisResult<T>();
        //判空
        if (StrUtil.isBlank(t.getId()))
            throw new Exception(BaseConstant.EBASE000004);
        if (StrUtil.isBlank(t.getConfigName()))
            throw new Exception(BaseConstant.EBASE000014);
        if (StrUtil.isBlank(t.getConfigCode()))
            throw new Exception(BaseConstant.EBASE000015);

        t.setCreateBy(oauthUtil.GetCurentUserId());
        t.setUpdateBy(oauthUtil.GetCurentUserId());
        
        // 唯一校验
        SysCoderuleInfo sysMessageById = sysCoderuleInfoMapper.getCoderuleInfoById(t.getId());
        if (sysMessageById != null){
            if (!sysMessageById.getConfigCode().equals(t.getConfigCode())){
                SysCoderuleInfo param = new SysCoderuleInfo();
                param.setIsDeleted(false);
                param.setConfigCode(t.getConfigCode());
                if (!ObjectUtils.isNull(sysCoderuleInfoMapper.retrieve((T) param)))
                    throw new Exception(BaseConstant.EBASE000013);
            }
        }
        
        // 插入操作
        if (sysCoderuleInfoMapper.update(t) == 1)
            _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return _lmisResult;
    }

    @Override
    public LmisResult<?> deleteSysCoderuleInfo(T t) throws Exception{

        // TODO(业务校验)

        // 删除操作
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sysCoderuleInfoMapper.logicalDelete(t));
    }

    //-------------------------------------------------------------------------------------------------------
    //----------------自定义方法
    //-------------------------------------------------------------------------------------------------------

    /**
     * 返回正常结果的数据
     */
    @Override
    public String GetCodeRule(String configCode){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        String s = SaveWithDeposit(configCode, true);
        lock.unlock();
        return s;

    }
    
    /**
     * 仅仅返回默认的demo数据
     * @throws Exception 
     */
    @Override
    public LmisResult<?> TestCodeRule(String configCode) throws Exception{
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        String s = SaveWithDeposit(configCode, false);
        lock.unlock();
        if(StrUtil.isBlank(s))
            throw new Exception(BaseConstant.WBASE000001);
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, s);

    }
    

    /**
     * 更新自增值
     * 
     * @param resultValue
     * @param configCode
     * @param upGrowthTime
     * @param date
     */
    public void SaveDeposit(String resultValue,String configCode,boolean upGrowthTime,DateTime date){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        SysCoderuleDeposit sysCoderuleDeposit = new SysCoderuleDeposit();
        sysCoderuleDeposit.setConfigCode(configCode);
        sysCoderuleDeposit.setResultValue(resultValue);
        sysCoderuleDeposit.setCreateTime(DateUtil.date());
        if (upGrowthTime){
            sysCoderuleDeposit.setGrowthTime(DateUtil.date());
        }else{
            sysCoderuleDeposit.setGrowthTime(date);
        }
        sysCoderuleDepositServiceInterface.InsertDeposit(sysCoderuleDeposit);
        lock.unlock();
    }

    /**
     * 保存自增值
     * 
     * @author xuyisu
     * @param resultValue
     *            生成的结果值
     * @param infoid
     *            信息id
     * @param date
     *            当前周期时间
     * @param depositId
     *            存储主键
     */
    public void UpdateDeposit(String resultValue,String configCode,DateTime date,String depositId){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        SysCoderuleDeposit sysCoderuleDeposit = new SysCoderuleDeposit();
        sysCoderuleDeposit.setId(depositId);
        sysCoderuleDeposit.setConfigCode(configCode);
        sysCoderuleDeposit.setResultValue(resultValue);
        sysCoderuleDeposit.setCreateTime(DateUtil.date());
        sysCoderuleDeposit.setGrowthTime(date);
        sysCoderuleDepositServiceInterface.UpdateDeposit(sysCoderuleDeposit);
        lock.unlock();
    }

    /**
     * 初始化自增数据
     * 
     * @return
     */
    public String CreateGROWTH(RuleInfoVo in){
        StringBuilder stringBuilder = new StringBuilder();
        if (StrUtil.isBlank(in.getStartValue())){
            for (int a = 0; a < in.getNumber() - 1; a++){
                stringBuilder.append("0");
            }
            stringBuilder.append(RuleConstants.RULE_GROWTH_START_VALUE);
        }else{
            for (int a = 0; a < in.getNumber() - in.getStartValue().length(); a++){
                stringBuilder.append("0");
            }
            stringBuilder.append(in.getStartValue());
        }

        return stringBuilder.toString();
    }

    /**
     * 更新自增数据
     * 
     * @param in
     * @param datenow
     * @param date
     * @param data
     * @return
     */
    public String UpdateGROWTH(RuleInfoVo in,DateTime datenow,DateTime date,String data){
        StringBuilder stringBuilder = new StringBuilder();
        int i = Integer.parseInt(data);
        if (in.getIncreValue() < 1){
            i++;
        }else{
            i = i + in.getIncreValue();
        }
        if (String.valueOf(i).length() < in.getNumber()){
            for (int a = 0; a < (in.getNumber() - String.valueOf(i).length()); a++){
                stringBuilder.append("0");
            }
            stringBuilder.append(i);
        }else{
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }

    /**
     * 当查到数据时，解析数据
     * 
     * @param name
     * @return
     */
    public String SaveWithDeposit(String configCode,boolean isTrue){
        //拿到需要拼接的数据
        String data = null;
        DateTime date = DateUtil.date();
        boolean upGrowthTime = false;
        //获取数据存储公式（一系列规则）
        List<RuleInfoVo> a01 = sysCoderuleInfoMapper.getInfoByConfigCode(configCode);
        if (a01 != null){
            StringBuilder stringBuilder = new StringBuilder();
            for (RuleInfoVo in : a01){
                if (in != null){
                    switch (in.getDataType()) {
                        case RuleConstants.RULE_CONSTANT://常量
                            if (StrUtil.isBlank(in.getStartValue())){
                                stringBuilder.append(RuleConstants.RULE_CONSTANT_VALUE);
                            }else{
                                stringBuilder.append(in.getStartValue());
                            }
                            break;
                        case RuleConstants.RULE_DATE://日期
                            if (in.getDataValuelg() == 0){
                                in.setDataValuelg(DateUtils.CreatDate().length());
                            }
                            stringBuilder.append(DateUtils.CreatDate());
                            break;
                        case RuleConstants.RULE_TIME://时间
                            stringBuilder.append(DateUtils.CreatTime());
                            break;
                        case RuleConstants.RULE_DATETIME://日期时间
                            stringBuilder.append(DateUtils.CreatDateTime());
                            break;
                        case RuleConstants.RULE_GROWTH://自增长
                            //如果自增长数据长度没有值，默认给长度为6位
                            if (in.getNumber() == 0){
                                in.setNumber(RuleConstants.RULE_GROWTH_NUMBER);
                            }

                            if (!isTrue){
                                String s = CreateGROWTH(in);
                                stringBuilder.append(s);
                                break;
                            }
                            //获取当前编码的更新周期、缓存的最新值、存储主键(用于更新)
                            RuleDepositVo depositByConfigName = sysCoderuleInfoMapper.getDepositByConfigCode(configCode);
                            if (depositByConfigName != null){
                                data = depositByConfigName.getResultValue();
                                if (depositByConfigName.getGrowthTime() != null){
                                    date = DateUtil.date(depositByConfigName.getGrowthTime());
                                }
                            }

                            //获取当前时间
                            DateTime datenow = DateUtil.date();
                            //如果没有设置更周周期，默认每天更新
                            if (StrUtil.isBlank(in.getUpdateCycle()))
                                in.setUpdateCycle(RuleConstants.UPDATE_CYCLE_DAY);
                            //如果当前没有值，创建一个默认的数据，默认数据么有提供的话，从1开始
                            if (data == null){
                                String s = CreateGROWTH(in);
                                stringBuilder.append(s);
                                SaveDeposit(s, configCode, false, date);
                                break;
                            }
                            switch (in.getUpdateCycle()) {
                                case RuleConstants.UPDATE_CYCLE_DAY:
                                    if (DateUtil.between(date, datenow, DateUnit.DAY) > 0){
                                        upGrowthTime = true;
                                        String s = CreateGROWTH(in);
                                        SaveDeposit(s, configCode, upGrowthTime, date);
                                        stringBuilder.append(s);
                                    }else{
                                        String s = UpdateGROWTH(in, datenow, date, data);
                                        UpdateDeposit(s, configCode, date, depositByConfigName.getDepositId());
                                        stringBuilder.append(s);
                                    }
                                    break;
                                case RuleConstants.UPDATE_CYCLE_WEEK:
                                    if (DateUtil.between(date, datenow, DateUnit.WEEK) > 0){
                                        upGrowthTime = true;
                                        String s = CreateGROWTH(in);
                                        SaveDeposit(s, configCode, upGrowthTime, date);
                                        stringBuilder.append(s);
                                    }else{
                                        String s = UpdateGROWTH(in, datenow, date, data);
                                        UpdateDeposit(s, configCode, date, depositByConfigName.getDepositId());
                                        stringBuilder.append(s);
                                    }
                                    break;
                                case RuleConstants.UPDATE_CYCLE_MONTH:
                                    if (DateUtil.betweenMonth(date, datenow, false) > 0){
                                        upGrowthTime = true;
                                        String s = CreateGROWTH(in);
                                        SaveDeposit(s, configCode, upGrowthTime, date);
                                        stringBuilder.append(s);
                                    }else{
                                        String s = UpdateGROWTH(in, datenow, date, data);
                                        UpdateDeposit(s, configCode, date, depositByConfigName.getDepositId());
                                        stringBuilder.append(s);
                                    }
                                    break;
                                case RuleConstants.UPDATE_CYCLE_YEAR:
                                    if (DateUtil.betweenYear(date, datenow, false) > 0){
                                        upGrowthTime = true;
                                        String s = CreateGROWTH(in);
                                        SaveDeposit(s, configCode, upGrowthTime, date);
                                        stringBuilder.append(s);
                                    }else{
                                        String s = UpdateGROWTH(in, datenow, date, data);
                                        UpdateDeposit(s, configCode, date, depositByConfigName.getDepositId());
                                        stringBuilder.append(s);

                                    }
                                    break;
                            }
                            break;
                        case RuleConstants.RULE_RANDOM://随机值
                            if (in.getNumber() > 0){
                                stringBuilder.append(RandomUtil.randomNumbers(in.getNumber()));
                            }else{
                                stringBuilder.append(RandomUtil.randomNumbers(RuleConstants.RULE_RANDOM_VALUE));
                            }
                            break;
                    }
                }
            }
            return stringBuilder.toString();
        }

        return null;
    }

}
