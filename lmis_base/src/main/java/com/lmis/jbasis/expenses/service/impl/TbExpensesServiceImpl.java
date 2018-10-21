package com.lmis.jbasis.expenses.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dynamicSql.dao.DynamicSqlMapper;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.model.Element;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.jbasis.expenses.dao.TbExpensesMapper;
import com.lmis.jbasis.expenses.model.TbExpressReturnStorage;
import com.lmis.jbasis.expenses.model.TbOperationfeeData;
import com.lmis.jbasis.expenses.model.TbStorageExpendituresData;
import com.lmis.jbasis.expenses.model.ViewTbWarehouseExpressData;
import com.lmis.jbasis.expenses.service.TbExpensesServiceInterface;
import com.lmis.jbasis.transportVendor.dao.JbasisTransportVendorMapper;
import com.lmis.jbasis.transportVendor.model.JbasisTransportVendor;


@Transactional(rollbackFor = Exception.class)
@Service
public class TbExpensesServiceImpl<T> implements TbExpensesServiceInterface<T>{
    @Resource(name="dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<JbasisTransportVendor> dynamicSqlService;
    @Autowired
    private TbExpensesMapper jbasisTransportVendorMapper;
    @Autowired
    private HttpSession session;
    @Autowired
    private DynamicSqlMapper<T> dynamicSqlMapper;
    @Override
    public LmisResult<?> selectTbExpressReturnStorage(DynamicSqlParam<TbExpressReturnStorage> tbExpressReturnStorage,LmisPageObject pageObject) throws Exception{
        // TODO Auto-generated method stub
        return commonExecute(tbExpressReturnStorage,pageObject);
    }

    @Override
    public LmisResult<?> selectTbWarehouseExpressData(DynamicSqlParam<ViewTbWarehouseExpressData> tbWarehouseExpressData,LmisPageObject pageObject) throws Exception{
        // TODO Auto-generated method stub
        return commonExecute(tbWarehouseExpressData,pageObject);
    }

    @Override
    public LmisResult<?> selectTbStorageExpendituresData(DynamicSqlParam<TbStorageExpendituresData> tbStorageExpendituresData,LmisPageObject pageObject) throws Exception{
        // TODO Auto-generated method stub
        if(tbStorageExpendituresData.getElements()!=null){
            for (int i = 0; i < tbStorageExpendituresData.getElements().size(); i++){
                if("J_S_KCKZ_P1_E04".equals(tbStorageExpendituresData.getElements().get(i).getElementId())||"J_S_KCKZ_P1_E05".equals(tbStorageExpendituresData.getElements().get(i).getElementId())){
                    tbStorageExpendituresData.getElements().get(i).setValue("\'"+tbStorageExpendituresData.getElements().get(i).getValue()+"\'");
                }
            }
             
        }
        tbStorageExpendituresData.setOrderByStr(pageObject.getLmisOrderByStr());
        LmisResult _lmisResult = dynamicSqlService.getDynamicSearchSql(tbStorageExpendituresData);
        if ("E3001".equals(_lmisResult.getCode())) {
            return _lmisResult;
        } else {
            Page page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
            String sqlStr = _lmisResult.getData().toString();
            sqlStr = sqlStr.replace("version,", "");
            sqlStr = sqlStr.replace("AND is_deleted = false", "");
            sqlStr = sqlStr.replace("WHERE is_deleted = false", "");
            
            jbasisTransportVendorMapper.executeSelect(sqlStr);
            LmisResult lmisResult = new LmisResult();
            lmisResult.setMetaAndData(page.toPageInfo());
            lmisResult.setCode("S1001");
            return lmisResult;
        }
    }

    @Override
    public LmisResult<?> selectTbOperationfeeData(DynamicSqlParam<TbOperationfeeData> tbOperationfeeData,LmisPageObject pageObject) throws Exception{
        // TODO Auto-generated method stub
        return commonExecute(tbOperationfeeData,pageObject);
    }
    
    private LmisResult<?> commonExecute(DynamicSqlParam<?> dDynamicSqlParam , LmisPageObject pageObject) throws Exception{
        dDynamicSqlParam.setOrderByStr(pageObject.getLmisOrderByStr());
        LmisResult _lmisResult = dynamicSqlService.getDynamicSearchSql(dDynamicSqlParam);
        if ("E3001".equals(_lmisResult.getCode())) {
            return _lmisResult;
        } else {
            Page page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
            String sqlStr = _lmisResult.getData().toString();
            sqlStr = sqlStr.replace("version,", "");
            sqlStr = sqlStr.replace("AND is_deleted = false", "");
            sqlStr = sqlStr.replace("WHERE is_deleted = false", "");
            
            dynamicSqlMapper.executeSelect(sqlStr);
            LmisResult lmisResult = new LmisResult();
            lmisResult.setMetaAndData(page.toPageInfo());
            lmisResult.setCode("S1001");
            return lmisResult;
        }
    }

}
