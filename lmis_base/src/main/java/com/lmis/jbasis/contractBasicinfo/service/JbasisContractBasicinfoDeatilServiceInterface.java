package com.lmis.jbasis.contractBasicinfo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.jbasis.contractBasicinfo.model.ViewJbasisContractBasicinfoDeatil;

/** 
 * @ClassName: JbasisContractBasicinfoDeatilServiceInterface
 * @Description: TODO(计费协议明细业务层接口类)
 * @author codeGenerator
 * @date 2018-05-10 13:45:43
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface JbasisContractBasicinfoDeatilServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-10 13:45:43
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-10 13:45:43
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	   /**
     * @Title: executeSelectList
     * @Description: TODO(执行搜索语句，获取多条记录，不分页)
     * @param viewJbasisContractBasicinfoDeatil
     * @throws Exception
     * @return: LmisResult<?>
     * @author: codeGenerator
     * @date: 2018-05-10 13:45:43
     */
    LmisResult<?> executeSelectList(ViewJbasisContractBasicinfoDeatil viewJbasisContractBasicinfoDeatil) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-10 13:45:43
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-10 13:45:43
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deleteJbasisContractBasicinfoDeatil
	 * @Description: TODO(删除计费协议明细)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-10 13:45:43
	 */
	LmisResult<?> deleteJbasisContractBasicinfoDeatil(T t) throws Exception;
}
