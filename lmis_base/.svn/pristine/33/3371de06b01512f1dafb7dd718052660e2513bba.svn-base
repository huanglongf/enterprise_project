package com.lmis.jbasis.contractBasicinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.lmis.jbasis.contractBasicinfo.dao.JbasisContractBasicinfoMapper;
import com.lmis.jbasis.contractBasicinfo.model.JbasisContractBasicinfo;
import com.lmis.jbasis.contractBasicinfo.service.JbasisContractBasicinfoServiceInterface;

/**
 * @ClassName: JbasisContractBasicinfoServiceImpl
 * @Description: TODO(计费协议业务层实现类)
 * @author codeGenerator
 * @date 2018-05-04 15:06:45
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class JbasisContractBasicinfoServiceImpl<T extends JbasisContractBasicinfo> implements JbasisContractBasicinfoServiceInterface<T>{

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<JbasisContractBasicinfo> dynamicSqlService;

    @Autowired
    private JbasisContractBasicinfoMapper<T> jbasisContractBasicinfoMapper;

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
        JbasisContractBasicinfo contractBasicinfo = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisContractBasicinfo>) dynamicSqlParam, new JbasisContractBasicinfo()).getTableModel();

        // TODO(业务校验)
        //合同编码唯一校验
        if (ObjectUtils.isNull(contractBasicinfo.getContractNo())){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            lmisResult.setMessage("合同编码不能为空");
            return lmisResult;
        }else{
            JbasisContractBasicinfo checkContractBasicinfo = new JbasisContractBasicinfo();
            checkContractBasicinfo.setIsDeleted(false);
            checkContractBasicinfo.setContractNo(contractBasicinfo.getContractNo());
            if (jbasisContractBasicinfoMapper.retrieve((T) checkContractBasicinfo).size() > 0){
                lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
                lmisResult.setMessage("当前合同编码已存在");
                return lmisResult;
            }
        }
        // 合同名称唯一校验
        if (ObjectUtils.isNull(contractBasicinfo.getContractName())){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            lmisResult.setMessage("合同名称不能为空");
            return lmisResult;
        }else{
            JbasisContractBasicinfo checkContractBasicinfo = new JbasisContractBasicinfo();
            checkContractBasicinfo.setIsDeleted(false);
            checkContractBasicinfo.setContractName(contractBasicinfo.getContractName());
            if (jbasisContractBasicinfoMapper.retrieve((T) checkContractBasicinfo).size() > 0){
                lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
                lmisResult.setMessage("当前合同名称已存在");
                return lmisResult;
            }
        }
        // 插入操作
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        contractBasicinfo.setContractSerialNo(sdf.format(new Date()).concat(contractBasicinfo.getContractNo())); 
        contractBasicinfo.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
        contractBasicinfo.setCreateBy(session.getAttribute("lmisUserId").toString());
        if (jbasisContractBasicinfoMapper.create((T) contractBasicinfo) == 0){
            return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "插入合同数据失败");
        }
        return new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS, "插入合同数据成功");
    }

    @SuppressWarnings("unchecked")
    @Override
    public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception{

        // TODO(业务校验)
        LmisResult<?> lmisResult = new LmisResult<>();
        JbasisContractBasicinfo contractBasicinfo = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisContractBasicinfo>) dynamicSqlParam, new JbasisContractBasicinfo()).getTableModel();
        //合同名称唯一校验
        if (ObjectUtils.isNull(contractBasicinfo.getContractName())){
            lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
            lmisResult.setMessage("合同名称不能为空");
            return lmisResult;
        }else{
            JbasisContractBasicinfo checkContractBasicinfo = new JbasisContractBasicinfo();
            checkContractBasicinfo.setIsDeleted(false);
            checkContractBasicinfo.setContractName(contractBasicinfo.getContractName());
            List<T> list = jbasisContractBasicinfoMapper.retrieve((T) checkContractBasicinfo);
            if (list.size() > 0 && !list.get(0).getContractNo().equals(contractBasicinfo.getContractNo())){
                lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
                lmisResult.setMessage("当前合同名称已存在");
                return lmisResult;
            }
        }
        contractBasicinfo.setUpdateBy(session.getAttribute("lmisUserId").toString());
        if (jbasisContractBasicinfoMapper.update((T) contractBasicinfo) == 0){
            return new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "更新合同数据失败");
        }
        return new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS, "更新合同数据成功");
    }

    @Override
    public LmisResult<?> deleteJbasisContractBasicinfo(T t) throws Exception{

        // 更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());

        // 删除操作
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisContractBasicinfoMapper.logicalDelete(t));
    }

    @Override
    public LmisResult<?> switchContractBasicinfo(T t) throws Exception{

        // 更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());

        // 启用合同/禁用合同
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisContractBasicinfoMapper.shiftValidity(t));
    }

}
