package com.lmis.sys.message.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.OauthUtil;
import com.lmis.common.util.RedisUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.constant.BaseConstant;
import com.lmis.sys.message.dao.SysMessageMapper;
import com.lmis.sys.message.model.SysMessage;
import com.lmis.sys.message.service.SysMessageServiceInterface;

import cn.hutool.core.util.StrUtil;

/**
 * @ClassName: SysMessageServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-05-22 20:04:53
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SysMessageServiceImpl<T extends SysMessage> implements SysMessageServiceInterface<T>{

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<SysMessage> dynamicSqlService;

    @Autowired
    private SysMessageMapper<T> sysMessageMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private OauthUtil oauthUtil;

    /**
     * 成功/异常消息编号
     */
    @Value("${redis.key.sys.message}")
    String redisSysMessage;

    /**
     * 成功/异常消息缓存时间
     */
    @Value("${redis.key.sys.message.timeout}")
    int redisSysMessageExprire;

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

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeInsert(T t) throws Exception{

        LmisResult<T> _lmisResult = new LmisResult<T>();
        //校验数据
        if (t != null && StrUtil.isBlank(t.getCode().trim()))
            throw new Exception(BaseConstant.EBASE000004);
        if (t != null && StrUtil.isBlank(t.getMessage().trim()))
            throw new Exception(BaseConstant.EBASE000005);
        if (t.getCode().trim().length() != BaseConstant.SYS_MESSAGE_CODE_LENGTH)
            throw new Exception(BaseConstant.EBASE000011);
        t.setCode(t.getCode().toUpperCase());
        t.setCreateBy(oauthUtil.GetCurentUserId());
        t.setUpdateBy(oauthUtil.GetCurentUserId());

        //数据规则校验
        Pattern pattern = Pattern.compile(BaseConstant.SYS_MESSAGE_REGULAR_EXPRESSION);
        Matcher matcher = pattern.matcher(t.getCode().trim());
        boolean b = matcher.matches();
        if (!b){
            throw new Exception(BaseConstant.EBASE000012);
        }
        //数据查重
        // 唯一校验
        SysMessage param = new SysMessage();
        param.setIsDeleted(false);
        param.setCode(t.getCode());
        if (!ObjectUtils.isNull(sysMessageMapper.retrieve((T) param)))
            throw new Exception(BaseConstant.EBASE000003);

        /// 插入操作
        if (sysMessageMapper.create(t) == 1)
            _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        //同步缓存
        redisUtils.set(redisSysMessage + t.getCode(), t.getMessage(), redisSysMessageExprire);
        return _lmisResult;
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeUpdate(T t) throws Exception{

        LmisResult<T> _lmisResult = new LmisResult<T>();
        //校验数据
        if (t != null && StrUtil.isBlank(t.getId()))
            throw new Exception(BaseConstant.EBASE000004);
        if (t != null && StrUtil.isBlank(t.getCode().trim()))
            throw new Exception(BaseConstant.EBASE000005);
        if (t != null && StrUtil.isBlank(t.getMessage().trim()))
            throw new Exception(BaseConstant.EBASE000006);
        t.setUpdateBy(oauthUtil.GetCurentUserId());
        if (t.getCode().trim().length() != BaseConstant.SYS_MESSAGE_CODE_LENGTH)
            throw new Exception(BaseConstant.EBASE000011);
        t.setCode(t.getCode().toUpperCase());
        //数据规则校验
        Pattern pattern = Pattern.compile(BaseConstant.SYS_MESSAGE_REGULAR_EXPRESSION);
        Matcher matcher = pattern.matcher(t.getCode().trim());
        boolean b = matcher.matches();
        if (!b){
            throw new Exception(BaseConstant.EBASE000012);
        }
        //数据查重
        // 唯一校验
        SysMessage sysMessageById = sysMessageMapper.getSysMessageById(t.getId());
        if (sysMessageById != null){
            if (!sysMessageById.getCode().equals(t.getCode())){
                SysMessage param = new SysMessage();
                param.setIsDeleted(false);
                param.setCode(t.getCode());
                if (!ObjectUtils.isNull(sysMessageMapper.retrieve((T) param)))
                    throw new Exception(BaseConstant.EBASE000003);
            }
        }

        /// 插入操作
        if (sysMessageMapper.update(t) == 1)
            _lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        //同步缓存
        redisUtils.set(redisSysMessage + t.getCode(), t.getMessage(), redisSysMessageExprire);
        return _lmisResult;
    }

    @Override
    public LmisResult<?> deleteSysMessage(T t) throws Exception{

        // TODO(业务校验)
        SysMessage sysMessageById = sysMessageMapper.getSysMessageById(t.getId());
        if(sysMessageById.getCode().equals(BaseConstant.EBASE000001)||sysMessageById.getCode().equals(BaseConstant.EBASE000002))
        {
            throw new Exception(BaseConstant.EBASE000021);
        }
        if (sysMessageById != null){
            redisUtils.delete(redisSysMessage + sysMessageById.getCode());
        }
        // 删除操作
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sysMessageMapper.logicalDelete(t));
    }

    /**
     * 根据编码获取系统消息
     * 
     * @param t
     * @return
     * @throws Exception
     */
    @Override
    public String getSysMessageByCode(String code){
        // TODO(业务校验)
        if (StrUtil.isBlank(code))
            return getSysMessageByCode(BaseConstant.EBASE000002);
        code = code.toUpperCase();
        String message = redisUtils.get(redisSysMessage + code);
        if (StrUtil.isBlank(message)){
            SysMessage sysMessageById = sysMessageMapper.getSysMessageByCode(code);
            message = sysMessageById == null ? getSysMessageByCode(BaseConstant.EBASE000001) : sysMessageById.getMessage();
            if (StrUtil.isNotBlank(message)){
                redisUtils.set(redisSysMessage + code, message, redisSysMessageExprire);
                return message;
            }
        }
        return message;
    }

    /**
     * 
     * @param code
     * @return
     */
    @Override
    public HashMap<String, String> GetMessageByCodeFormat(String code){
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String msg = getSysMessageByCode(code);
        String result = code.substring(0, 1);
        String res = null;
        if (result.endsWith("S")){
            res = "success";
        }else if (result.equals("W")){
            res = "warning";
        }else{
            res = "error";
        }
        hashMap.put("type", res);
        hashMap.put("code", code);
        hashMap.put("msg", msg);
        return hashMap;

    }
    
    @Override
    public LmisResult<?> getSysMessages(List<String> listCode){
        // TODO Auto-generated method stub
        return new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS, sysMessageMapper.getSysMessages(listCode));
    }

}
