package com.lmis.basis.fixedAssets.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.basis.fixedAssets.model.BasisFixedAssets;
import com.lmis.basis.fixedAssets.model.ViewBasisFixedAssets;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: BasisFixedAssetsServiceInterface
 * @Description: TODO(固定资产业务层接口类)
 * @author codeGenerator
 * @date 2018-01-22 13:28:46
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface BasisFixedAssetsServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-22 13:28:46
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-22 13:28:46
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-22 13:28:46
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-22 13:28:46
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deleteBasisFixedAssets
	 * @Description: TODO(删除固定资产)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-22 13:28:46
	 */
	LmisResult<?> deleteBasisFixedAssets(T t) throws Exception;

	LmisResult<?> switchBasisFixedAssets(T t) throws Exception;

	List<BasisFixedAssets> findAssets(BasisFixedAssets assets);

	List<ViewBasisFixedAssets> lazyfindAssetsView(ViewBasisFixedAssets assets);

	List<ViewBasisFixedAssets> findAssetsView(ViewBasisFixedAssets assets);

	LmisResult<?> exportFixedAssets(int pageSize) throws Exception;
}
