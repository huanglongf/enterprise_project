package com.lmis.pos.whsRateFillin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: PosWhsRateFillinServiceInterface
 * @Description: TODO(补货商品分配仓库比例分析业务层接口类)
 * @author codeGenerator
 * @date 2018-05-30 17:03:06
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosWhsRateFillinServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:03:06
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:03:06
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:03:06
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:03:06
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deletePosWhsRateFillin
	 * @Description: TODO(删除补货商品分配仓库比例分析)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:03:06
	 */
	LmisResult<?> deletePosWhsRateFillin(T t) throws Exception;
}
