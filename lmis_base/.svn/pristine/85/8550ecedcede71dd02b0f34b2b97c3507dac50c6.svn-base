package com.lmis.jbasis.contractBasicinfo.service.impl;

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
import com.lmis.jbasis.contractBasicinfo.dao.JbasisContractBasicinfoDeatilMapper;
import com.lmis.jbasis.contractBasicinfo.model.JbasisContractBasicinfoDeatil;
import com.lmis.jbasis.contractBasicinfo.model.ViewJbasisContractBasicinfoDeatil;
import com.lmis.jbasis.contractBasicinfo.service.JbasisContractBasicinfoDeatilServiceInterface;

/**
 * @ClassName: JbasisContractBasicinfoDeatilServiceImpl
 * @Description: TODO(计费协议明细业务层实现类)
 * @author codeGenerator
 * @date 2018-05-10 13:45:43
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class JbasisContractBasicinfoDeatilServiceImpl<T extends JbasisContractBasicinfoDeatil> implements JbasisContractBasicinfoDeatilServiceInterface<T>{

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<JbasisContractBasicinfoDeatil> dynamicSqlService;

    @Autowired
    private HttpSession session;

    @Autowired
    private JbasisContractBasicinfoDeatilMapper<T> jbasisContractBasicinfoDeatilMapper;

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

    @Override
    public LmisResult<?> executeSelectList(ViewJbasisContractBasicinfoDeatil viewJbasisContractBasicinfoDeatil) throws Exception{
        List<ViewJbasisContractBasicinfoDeatil> result = jbasisContractBasicinfoDeatilMapper.retrieveViewJbasisContractBasicinfoDeatil(viewJbasisContractBasicinfoDeatil);
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
        LmisResult<?> lmisResult = new LmisResult<>();
        // TODO(业务校验)
        JbasisContractBasicinfoDeatil contractBasicinfoDeatil = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisContractBasicinfoDeatil>) dynamicSqlParam, new JbasisContractBasicinfoDeatil()).getTableModel();
        // 店铺编码唯一校验
        if (ObjectUtils.isNull(contractBasicinfoDeatil.getTypeCode())){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            lmisResult.setMessage("计费类型编码不能为空");
            return lmisResult;
        }else{
            JbasisContractBasicinfoDeatil checkContractBasicinfoDeatil = new JbasisContractBasicinfoDeatil();
            checkContractBasicinfoDeatil.setIsDeleted(false);
            checkContractBasicinfoDeatil.setContractNo(contractBasicinfoDeatil.getContractNo());
            checkContractBasicinfoDeatil.setTypeCode(contractBasicinfoDeatil.getTypeCode());
            if (jbasisContractBasicinfoDeatilMapper.retrieve((T) checkContractBasicinfoDeatil).size() > 0){
                lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
                lmisResult.setMessage("当前计费类型编码已存在");
                return lmisResult;
            }
        }

        // 插入操作
        return dynamicSqlService.executeInsert(dynamicSqlParam);
    }

    @Override
    public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception{

        // TODO(业务校验)
        
        // 更新操作
        return dynamicSqlService.executeUpdate(dynamicSqlParam);
    }

    @Override
    public LmisResult<?> deleteJbasisContractBasicinfoDeatil(T t) throws Exception{

        // TODO(业务校验)
        // 更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());

        // 删除操作
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisContractBasicinfoDeatilMapper.logicalDelete(t));
    }

}
