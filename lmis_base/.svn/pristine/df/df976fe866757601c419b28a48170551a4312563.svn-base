package com.lmis.jbasis.transportVendor.service.impl;

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
import com.lmis.jbasis.transportVendor.dao.JbasisTransportVendorMapper;
import com.lmis.jbasis.transportVendor.model.JbasisTransportVendor;
import com.lmis.jbasis.transportVendor.service.JbasisTransportVendorServiceInterface;
import com.lmis.jbasis.warehouse.model.JbasisWarehouse;

/** 
 * @ClassName: JbasisTransportVendorServiceImpl
 * @Description: TODO(运输供应商(基础数据)业务层实现类)
 * @author codeGenerator
 * @date 2018-04-19 11:14:05
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class JbasisTransportVendorServiceImpl<T extends JbasisTransportVendor> implements JbasisTransportVendorServiceInterface<T> {
    
    @Resource(name="dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<JbasisTransportVendor> dynamicSqlService;
    @Autowired
    private JbasisTransportVendorMapper<T> jbasisTransportVendorMapper;
    @Autowired
    private HttpSession session;
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
        if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
        if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
    }

    @Override
    public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
            
        // TODO(业务校验)
        JbasisTransportVendor transportVendor = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisTransportVendor>) dynamicSqlParam, new JbasisTransportVendor()).getTableModel();
        if("".equals(transportVendor.getContact())||transportVendor.getContact()==null||
                        "".equals(transportVendor.getTransportType())||transportVendor.getTransportType()==null||
                        "".equals(transportVendor.getTransportName())||transportVendor.getTransportName()==null||
                        "".equals(transportVendor.getTransportCode())||transportVendor.getTransportCode()==null||
                        "".equals(transportVendor.getPhone())||transportVendor.getPhone()==null)throw new Exception("数据不全");
        JbasisTransportVendor jtv = new JbasisTransportVendor();
        jtv.setTransportCode(transportVendor.getTransportCode());
        List<T> retrieve = jbasisTransportVendorMapper.retrieve((T) jtv);
        if(retrieve.size()>0)throw new Exception("该物流商编码已存在");
        jtv.setIsDeleted(false);
        jtv.setTransportCode(null);
        jtv.setTransportName(transportVendor.getTransportName());
        List<T> retrieve2 = jbasisTransportVendorMapper.retrieve((T) jtv);
        if(retrieve2.size()>0)throw new Exception("该物流商名称已存在");
        String checkType = jbasisTransportVendorMapper.checkType(transportVendor.getTransportType());
        if(checkType==null||"".equals(checkType))throw new Exception("物流商类型错误");
        // 插入操作
        return dynamicSqlService.executeInsert(dynamicSqlParam);
    }

    @Override
    public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
            
        // TODO(业务校验)
        JbasisTransportVendor transportVendor = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisTransportVendor>) dynamicSqlParam, new JbasisTransportVendor()).getTableModel();
        JbasisTransportVendor jtv = new JbasisTransportVendor();
        jtv.setIsDeleted(false);
        jtv.setTransportName(transportVendor.getTransportName());
        List<T> retrieve2 = jbasisTransportVendorMapper.retrieve((T) jtv);
        for (int i = 0; i < retrieve2.size(); i++){
            if(!retrieve2.get(i).getId().equals(transportVendor.getId()))throw new Exception("该物流商名称已存在");
        }
        // 更新操作
        return dynamicSqlService.executeUpdate(dynamicSqlParam);
    }

    @Override
    public LmisResult<?> deleteJbasisTransportVendor(T t) throws Exception {
            
        // TODO(业务校验)
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());
        // 删除操作
        return new LmisResult(LmisConstant.RESULT_CODE_SUCCESS, jbasisTransportVendorMapper.logicalDelete(t));
    }

    @Override
    public LmisResult<?> executeSelectType(String code) throws Exception{
        // TODO Auto-generated method stub
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,  jbasisTransportVendorMapper.findVendorType(code));
    }

    @Override
    public LmisResult<?> isDiable(JbasisTransportVendor dynamicSqlParam) throws Exception{
        // TODO Auto-generated method stub
        dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,  jbasisTransportVendorMapper.shiftValidity((T) dynamicSqlParam));
    }

}
