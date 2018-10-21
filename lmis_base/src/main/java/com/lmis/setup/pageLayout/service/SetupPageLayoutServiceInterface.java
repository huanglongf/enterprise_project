package com.lmis.setup.pageLayout.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.setup.pageLayout.model.ViewSetupPageLayout;

/** 
 * @ClassName: SetupPageLayoutServiceInterface
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月8日 下午8:19:43 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SetupPageLayoutServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: queryPage
	 * @Description: TODO(查询页面布局)
	 * @param viewSetupPageLayout
	 * @param pageObject
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月8日 下午8:20:39
	 */
	LmisResult<?> queryPage(ViewSetupPageLayout viewSetupPageLayout, LmisPageObject pageObject);

	/**
	 * @Title: addSetupPageLayout
	 * @Description: TODO(新增页面布局)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月8日 下午8:20:43
	 */
	LmisResult<T> addSetupPageLayout(T t) throws Exception;
	
	/**
	 * @Title: updateSetupPageLayout
	 * @Description: TODO(更新页面布局)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月8日 下午8:21:12
	 */
	LmisResult<T> updateSetupPageLayout(T t) throws Exception;
	
	/**
	 * @Title: preDeleteSetupPageLayout
	 * @Description: TODO(删除页面布局前的信息提示功能)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 上午10:12:31
	 */
	LmisResult<T> preDeleteSetupPageLayout(T t) throws Exception;
	
	/**
	 * @Title: deleteSetupPageLayout
	 * @Description: TODO(删除页面布局)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月8日 下午8:21:23
	 */
	LmisResult<T> deleteSetupPageLayout(T t) throws Exception;
	
	/**
	 * @Title: getSetupPageLayout
	 * @Description: TODO(查看页面布局)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2018年1月9日 下午5:32:23
	 */
	LmisResult<T> getSetupPageLayout(T t) throws Exception;
	
	/**
	 * @Title: previewSetupPageLayout
	 * @Description: TODO(预览页面布局)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 下午5:49:22
	 */
	LmisResult<T> previewSetupPageLayout(T t) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:39:01
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(新增页面显示选项)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: wenhui.bai
	 * @date: 2017年12月11日 上午11:31:12
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(动态更新页面布局)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: wenhui.bai
	 * @date: 2018年5月23日 下午17:13:56
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年5月23日 下午17:13:56
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
}
