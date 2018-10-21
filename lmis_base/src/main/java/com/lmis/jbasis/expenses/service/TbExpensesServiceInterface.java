package com.lmis.jbasis.expenses.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.jbasis.expenses.model.TbExpressReturnStorage;
import com.lmis.jbasis.expenses.model.TbOperationfeeData;
import com.lmis.jbasis.expenses.model.TbStorageExpendituresData;
import com.lmis.jbasis.expenses.model.ViewTbWarehouseExpressData;

@Transactional(rollbackFor=Exception.class)
@Service
public interface TbExpensesServiceInterface<T>{

    LmisResult<?> selectTbExpressReturnStorage(DynamicSqlParam<TbExpressReturnStorage> tbExpressReturnStorage,LmisPageObject pageObject)throws Exception;

    LmisResult<?> selectTbWarehouseExpressData(DynamicSqlParam<ViewTbWarehouseExpressData> tbWarehouseExpressData,LmisPageObject pageObject)throws Exception;

    LmisResult<?> selectTbStorageExpendituresData(DynamicSqlParam<TbStorageExpendituresData> tbStorageExpendituresData,LmisPageObject pageObject)throws Exception;

    LmisResult<?> selectTbOperationfeeData(DynamicSqlParam<TbOperationfeeData> tbOperationfeeData,LmisPageObject pageObject)throws Exception;

}
