package com.lmis.jbasis.client.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/**
 * @ClassName: JbasisClientServiceInterface
 * @Description: TODO(客户信息(基础数据)业务层接口类)
 * @author codeGenerator
 * @date 2018-05-16 14:41:27
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public interface JbasisClientServiceInterface<T extends PersistentObject> {

    /**
     * @Title: executeSelect
     * @Description: TODO(执行搜索语句，获取多条记录，可分页)
     * @param dynamicSqlParam
     * @param pageObject
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-05-16 14:41:27
     */
    LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam,LmisPageObject pageObject) throws Exception;

    /**
     * @Title: executeSelect
     * @Description: TODO(执行搜索语句，获取单条记录，不分页)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-05-16 14:41:27
     */
    LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;

    /**
     * @Title: executeInsert
     * @Description: TODO(执行插入语句)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-05-16 14:41:27
     */
    LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;

    /**
     * @Title: executeUpdate
     * @Description: TODO(执行更新语句)
     * @param dynamicSqlParam
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-05-16 14:41:27
     */
    LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;

    /**
     * @Title: deleteJbasisClient
     * @Description: TODO(删除客户信息(基础数据))
     * @param t
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-05-16 14:41:27
     */
    LmisResult<?> deleteJbasisClient(T t) throws Exception;

    /**
     * 启用禁用客户
     * switchJbasisClient
     * @param t
     * @return
     * @throws Exception
     */
    LmisResult<?> switchJbasisClient(T t) throws Exception;
}
