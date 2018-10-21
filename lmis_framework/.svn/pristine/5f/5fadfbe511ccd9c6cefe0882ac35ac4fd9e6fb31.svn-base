package com.lmis.common.dynamicSql.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;

/** 
 * @ClassName: DynamicSqlServiceInterface
 * @Description: TODO(动态SQL业务层接口)
 * @author Ian.Huang
 * @date 2017年12月19日 下午3:16:19 
 * 
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface DynamicSqlServiceInterface<T> {
	
	/**
	 * @Title: getWhereSql
	 * @Description: TODO(4.5.3获取查询条件)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月20日 下午1:32:19
	 */
	LmisResult<?> getWhereSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: getColumnsAndOrderby
	 * @Description: TODO(4.5.4获取查询结果)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月20日 下午1:32:12
	 */
	LmisResult<?> getColumnsAndOrderby(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: dynamicSelectSql
	 * @Description: TODO(4.5.5构建查询SQL语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月19日 下午8:12:59
	 */
	LmisResult<?> getDynamicSearchSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: dynamicInsertSql
	 * @Description: TODO(4.5.7获取新增/修改SQL语句【新增】)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月25日 下午5:03:07
	 */
	LmisResult<?> getDynamicInsertSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: dynamicUpdateSql
	 * @Description: TODO(4.5.7获取新增/修改SQL语句【修改】)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月25日 下午5:04:09
	 */
	LmisResult<?> getDynamicUpdateSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: getDynamicCheckSql
	 * @Description: TODO(4.5.8获取查看SQL语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月28日 下午4:37:31
	 */
	LmisResult<?> getDynamicCheckSql(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeSearch
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:39:01
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<?> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeQuery
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:39:11
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入方法)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:39:39
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新方法)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:40:04
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<?> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: generateTableModel
	 * @Description: TODO(生成表对象实体并填值)
	 * @param dynamicSqlParam
	 * @param obj
	 * @throws Exception
	 * @return: DynamicSqlParam<T>
	 * @author: Ian.Huang
	 * @date: 2018年1月12日 下午3:08:14
	 */
	DynamicSqlParam<T> generateTableModel(DynamicSqlParam<T> dynamicSqlParam, T obj) throws Exception;
	
}
