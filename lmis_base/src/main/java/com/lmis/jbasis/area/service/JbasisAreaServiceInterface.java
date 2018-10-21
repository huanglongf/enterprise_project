package com.lmis.jbasis.area.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.jbasis.area.model.ViewJbasisArea;

/** 
 * @ClassName: JbasisAreaServiceInterface
 * @Description: TODO(地址库表（基础数据）业务层接口类)
 * @author codeGenerator
 * @date 2018-04-11 14:23:33
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface JbasisAreaServiceInterface<T extends PersistentObject> {

    /**
     * @Title: executeSelect
     * @Description: TODO(执行搜索语句，获取多条记录，可分页)
     * @param dynamicSqlParam
     * @param pageObject
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-11 14:23:33
     */
    LmisResult<?> executeSelect(T t) throws Exception;
    
    /**
     * @Title: executeSelect
     * @Description: TODO(执行搜索语句，获取单条记录，不分页)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-11 14:23:33
     */
    LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
    
    /**
     * @Title: executeInsert
     * @Description: TODO(执行插入语句)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-11 14:23:33
     */
    LmisResult<?> executeInsert(T t) throws Exception;
    
    /**
     * @Title: executeUpdate
     * @Description: TODO(执行更新语句)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-11 14:23:33
     */
    LmisResult<?> executeUpdate(T t) throws Exception;
    
    /**
     * @Title: deleteJbasisArea
     * @Description: TODO(删除地址库表（基础数据）)
     * @param t
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-04-11 14:23:33
     */
    LmisResult<?> deleteJbasisArea(T t) throws Exception;
}
