package com.lmis.setup.constantSql.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: SetupConstantSqlServiceInterface
 * @Description: TODO(配置选项业务层接口)
 * @author Ian.Huang
 * @date 2017年12月11日 下午2:43:40 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SetupConstantSqlServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(查询页面显示选项)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 下午2:43:57
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;

	/**
	 * @Title: checkSetupConstantSql
	 * @Description: TODO(查看页面显示选项)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年1月9日 下午5:28:09
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: addSetupPageElement
	 * @Description: TODO(新增页面显示选项)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 上午11:31:12
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: updateSetupConstantSql
	 * @Description: TODO(更新页面显示选项)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 下午2:56:09
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeSetupConstantSql
	 * @Description: TODO(执行页面显示选项)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月12日 下午7:39:10
	 */
	LmisResult<T> executeSetupConstantSql(T t) throws Exception;
	
	/**
	 * @Title: deleteSetupConstantSql
	 * @Description: TODO(删除页面显示选项)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 下午2:56:27
	 */
	LmisResult<?> deleteSetupConstantSql(T t) throws Exception;
	/**
	 * 根据传入的 SQL 语句进行校验只能为 select 语句，select 语句中不包含操作关键字
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	LmisResult<?> checkValidSQL(String sql) throws Exception;
}
