package com.lmis.basis.costCenter.service;

import com.lmis.basis.costCenter.model.BasisCostCenter;
import com.lmis.basis.costCenter.model.ViewBasisCostCenter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

import java.util.List;

/** 
 * @ClassName: BasisCostCenterServiceInterface
 * @Description: TODO(成本中心业务层接口类)
 * @author codeGenerator
 * @date 2018-01-31 15:43:21
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface BasisCostCenterServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deleteBasisCostCenter
	 * @Description: TODO(删除成本中心)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	LmisResult<?> deleteBasisCostCenter(T t) throws Exception;

	/**
	 * @Title: switchBasisCostCenter
	 * @Description: TODO(启用/禁用成本中心)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	LmisResult<?> switchBasisCostCenter(T t) throws Exception;

	/**
	 * @Title: findCostCenter
	 * @Description: TODO(查找成本中心)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	List<ViewBasisCostCenter> findCostCenter(ViewBasisCostCenter sysOrg) throws Exception;

	/**
	 * @Title: addOrUpdateCostCenter
	 * @Description: TODO(新增/修改成本中心)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	LmisResult<?> addOrUpdateCostCenter(BasisCostCenter costCenter)throws Exception;

	LmisResult<?> exportCostCenter(int pageSize) throws Exception;
}
