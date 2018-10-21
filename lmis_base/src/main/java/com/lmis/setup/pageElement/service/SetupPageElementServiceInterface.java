package com.lmis.setup.pageElement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;

/** 
 * @ClassName: SetupPageElementServiceInterface
 * @Description: TODO(页面元素业务层接口)
 * @author Ian.Huang
 * @date 2017年12月11日 上午11:30:18 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SetupPageElementServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: queryPage
	 * @Description: TODO(查询页面元素)
	 * @param viewSetupPageElement
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 上午11:30:45
	 */
	LmisResult<?> queryPage(ViewSetupPageElement viewSetupPageElement, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param sql
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
	 * @Title: addSetupPageElement
	 * @Description: TODO(新增页面元素)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 上午11:31:12
	 */
	LmisResult<T> addSetupPageElement(T t) throws Exception;
	
	/**
	 * @Title: updateSetupPageElement
	 * @Description: TODO(更新页面元素)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 上午11:31:34
	 */
	LmisResult<T> updateSetupPageElement(T t) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(动态更新页面元素)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年1月9日 下午8:45:56
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deleteSetupPageElement
	 * @Description: TODO(删除页面元素)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2017年12月11日 上午11:32:16
	 */
	LmisResult<T> deleteSetupPageElement(T t) throws Exception;
	
	/**
	 * @Title: getSetupPageElement
	 * @Description: TODO(查看页面元素)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<T>
	 * @author: Ian.Huang
	 * @date: 2018年1月9日 下午5:38:30
	 */
	LmisResult<T> getSetupPageElement(T t) throws Exception;
	
	/**
	 * @Title: redisForPageElements
	 * @Description: TODO(同步页面元素数据)
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年1月9日 上午10:13:43
	 */
	LmisResult<?> redisForPageElements() throws Exception;
	
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年5月33日
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * 批量方式同步页面元素
	 * @return
	 * @throws Exception
	 */
	LmisResult<?> redisForPageElementsMset() throws Exception;
}
