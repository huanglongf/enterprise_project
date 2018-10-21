package com.lmis.basis.fixedAssetsAllot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.basis.fixedAssetsAllot.model.BasisFixedAssetsAllot;
import com.lmis.basis.fixedAssetsAllot.model.ViewBasisFixedAssetsAllot;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: BasisFixedAssetsAllotServiceInterface
 * @Description: TODO(固定资产调拨日志业务层接口类)
 * @author codeGenerator
 * @date 2018-04-02 10:47:58
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface BasisFixedAssetsAllotServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-04-02 10:47:58
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-04-02 10:47:58
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-04-02 10:47:58
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-04-02 10:47:58
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deleteBasisFixedAssetsAllot
	 * @Description: TODO(删除固定资产调拨日志)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-04-02 10:47:58
	 */
	LmisResult<?> deleteBasisFixedAssetsAllot(T t) throws Exception;

	LmisResult<?> selectBasisFixedAssetsAllot(ViewBasisFixedAssetsAllot allot, LmisPageObject pageObject) throws Exception;

	LmisResult<?> transferFixedAssets(BasisFixedAssetsAllot basisFixedAssetsAllot) throws Exception;
}
