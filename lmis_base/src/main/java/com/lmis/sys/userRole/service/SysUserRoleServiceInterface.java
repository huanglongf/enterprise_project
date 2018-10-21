package com.lmis.sys.userRole.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.sys.userRole.model.SysUserRole;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;

/** 
 * @ClassName: SysUserRoleServiceInterface
 * @Description: TODO(业务层接口类)
 * @author codeGenerator
 * @date 2018-01-09 16:43:38
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SysUserRoleServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @param Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 16:43:38
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 16:43:38
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 16:43:38
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 16:43:38
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deleteSysUserRole
	 * @Description: TODO(删除)
	 * @param t
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 16:43:38
	 */
	LmisResult<?> deleteSysUserRole(T t) throws Exception;


	List<T> findUserRoleList(SysUserRole sysUserRole) throws Exception;

	LmisResult<?> addUserRole(List<SysUserRole> userRoleList) throws Exception;

	LmisResult<?> deleteUserRole(List<SysUserRole> userRoleList) throws Exception;

}
