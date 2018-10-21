package com.lmis.jbasis.client.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.jbasis.client.dao.JbasisClientMapper;
import com.lmis.jbasis.client.model.JbasisClient;
import com.lmis.jbasis.client.service.JbasisClientServiceInterface;

/**
 * @ClassName: JbasisClientServiceImpl
 * @Description: TODO(客户信息(基础数据)业务层实现类)
 * @author codeGenerator
 * @date 2018-05-16 14:41:27
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class JbasisClientServiceImpl<T extends JbasisClient> implements JbasisClientServiceInterface<T>{

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<JbasisClient> dynamicSqlService;

    @Autowired
    private JbasisClientMapper<T> jbasisClientMapper;

    @Autowired
    private HttpSession session;

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
    public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception{

        LmisResult<?> lmisResult = new LmisResult<>();
        JbasisClient client = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisClient>) dynamicSqlParam, new JbasisClient()).getTableModel();
        // 客户编码唯一校验
        if (ObjectUtils.isNull(client.getClientCode())){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            lmisResult.setMessage("客户编码不能为空");
            return lmisResult;
        }else{
            JbasisClient checkClient = new JbasisClient();
            checkClient.setIsDeleted(false);
            checkClient.setClientCode(client.getClientCode());
            if (jbasisClientMapper.retrieve((T) checkClient).size() > 0){
                lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
                lmisResult.setMessage("当前客户编码已存在");
                return lmisResult;
            }
        }
        // 客户名称唯一校验
        if (ObjectUtils.isNull(client.getClientName())){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            lmisResult.setMessage("客户名称不能为空");
            return lmisResult;
        }else{
            JbasisClient checkClient = new JbasisClient();
            checkClient.setIsDeleted(false);
            checkClient.setClientName(client.getClientName());
            if (jbasisClientMapper.retrieve((T) checkClient).size() > 0){
                lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
                lmisResult.setMessage("当前客户名称已存在");
                return lmisResult;
            }
        }
        // 插入操作
        return dynamicSqlService.executeInsert(dynamicSqlParam);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception{

        LmisResult<?> lmisResult = new LmisResult<>();
        JbasisClient client = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisClient>) dynamicSqlParam, new JbasisClient()).getTableModel();
        // 客户名称唯一校验
        if (ObjectUtils.isNull(client.getClientName())){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            lmisResult.setMessage("客户名称不能为空");
            return lmisResult;
        }else{
            JbasisClient checkClient = new JbasisClient();
            checkClient.setIsDeleted(false);
            checkClient.setClientName(client.getClientName());
            List<T> list = jbasisClientMapper.retrieve((T) checkClient);
            if (list.size() > 0 && !list.get(0).getClientCode().equals(client.getClientCode())){
                lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
                lmisResult.setMessage("当前客户名称已存在");
                return lmisResult;
            }
        }
        // 更新操作
        return dynamicSqlService.executeUpdate(dynamicSqlParam);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public LmisResult<?> deleteJbasisClient(T t) throws Exception{

        // 更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());
        // 删除操作
        return new LmisResult(LmisConstant.RESULT_CODE_SUCCESS, jbasisClientMapper.logicalDelete(t));
    }

    @Override
    public LmisResult<?> switchJbasisClient(T t) throws Exception{

        // 更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());
        // 启用店铺/禁用店铺
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisClientMapper.shiftValidity(t));
    }
}
