package com.lmis.jbasis.transportVendor.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.jbasis.transportVendor.model.JbasisTransportVendor;

/** 
 * @ClassName: JbasisTransportVendorServiceInterface
 * @Description: TODO(运输供应商(基础数据)业务层接口类)
 * @author codeGenerator
 * @date 2018-04-19 11:14:05
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface JbasisTransportVendorServiceInterface<T extends PersistentObject> {

    /**
     * @Title: executeSelect
     * @Description: TODO(执行搜索语句，获取多条记录，可分页)
     * @param dynamicSqlParam
     * @param pageObject
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-19 11:14:05
     */
    LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
    
    /**
     * @Title: executeSelect
     * @Description: TODO(执行搜索语句，获取单条记录，不分页)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-19 11:14:05
     */
    LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
    
    /**
     * @Title: executeInsert
     * @Description: TODO(执行插入语句)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-19 11:14:05
     */
    LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
    
    /**
     * @Title: executeUpdate
     * @Description: TODO(执行更新语句)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-19 11:14:05
     */
    LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
    
    /**
     * @Title: deleteJbasisTransportVendor
     * @Description: TODO(删除运输供应商(基础数据))
     * @param t
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-19 11:14:05
     */
    LmisResult<?> deleteJbasisTransportVendor(T t) throws Exception;

    
    /**
     * @Title: deleteJbasisTransportVendor
     * @Description: TODO(获取运输供应商类型(基础数据))
     * @param t
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-19 11:14:05
     */
    LmisResult<?> executeSelectType(String code) throws Exception;

     LmisResult<?> isDiable(JbasisTransportVendor dynamicSqlParam) throws Exception;
}
