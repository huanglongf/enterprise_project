package com.lmis.setup.page.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: SetupPageServiceInterface
 * @Description: TODO(配置页面业务接口类)
 * @author Ian.Huang
 * @date 2017年12月28日 下午3:31:33 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SetupPageServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月28日 上午11:09:03
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月28日 上午11:13:06
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月28日 上午11:04:10
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月28日 上午11:04:28
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeDelete
	 * @Description: TODO(删除配置页面)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月28日 上午11:04:46
	 */
	LmisResult<?> deleteSetupPage(T t) throws Exception;
	
	/**
	 * @Title: copySetupPage
	 * @Description: TODO(复制页面布局)
	 * @param currentPageId 当前页面ID
	 * @param orignPageId 来源页面ID
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: wenhui.bai
	 * @date: 2018年06月04日 下午13:06:00
	 */
	LmisResult<?> copySetupPage(String currentPageId,String orignPageId) throws Exception;
}
