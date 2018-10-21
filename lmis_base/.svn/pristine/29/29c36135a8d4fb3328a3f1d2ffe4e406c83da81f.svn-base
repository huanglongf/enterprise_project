package com.lmis.basis.orgStaff.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.basis.orgStaff.model.BasisOrgStaff;
import com.lmis.basis.orgStaff.model.ViewBasisOrgStaff;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.sys.org.model.SysOrg;

/** 
 * @ClassName: BasisOrgStaffServiceInterface
 * @Description: TODO(成本中心与员工关系业务层接口类)
 * @author codeGenerator
 * @date 2018-01-24 10:05:26
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface BasisOrgStaffServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-24 10:05:26
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-24 10:05:26
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-24 10:05:26
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-24 10:05:26
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deleteBasisOrgStaff
	 * @Description: TODO(删除成本中心与员工关系)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-24 10:05:26
	 */
	LmisResult<?> deleteBasisOrgStaff(T t) throws Exception;

	LmisResult<?> addBasisOrgStaff(List<BasisOrgStaff> orgStaffList) throws Exception;

	LmisResult<?> updateBasisOrgStaff(T t) throws Exception;

	LmisResult<?> selectBasisOrgStaff(ViewBasisOrgStaff t) throws Exception;

	LmisResult<?> clearBasisOrgStaff(BasisOrgStaff basisOrgStaff)throws Exception;

	LmisResult<?> checkSysOrg(SysOrg sysOrg) throws Exception;

	LmisResult<?> findOrgStaffByOrgId(ViewBasisOrgStaff basisOrgStaff) throws Exception;

}
