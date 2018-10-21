package com.lmis.sys.org.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.sys.org.model.SysOrg;
import com.lmis.sys.org.model.ViewSysOrg;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;

/** 
 * @ClassName: SysOrgServiceInterface
 * @Description: TODO(业务层接口类)
 * @author codeGenerator
 * @date 2018-01-09 13:08:38
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SysOrgServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @param Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 13:08:38
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 13:08:38
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam)throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 13:08:38
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam)throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 13:08:38
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam)throws Exception;
	
	/**
	 * @Title: deleteSysOrg
	 * @Description: TODO(删除)
	 * @param t
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-01-09 13:08:38
	 */
	LmisResult<?> deleteSysOrg(T t)throws Exception;

	LmisResult<?> addOrUpdateSysOrg(SysOrg org)throws Exception;

	List<SysOrg> findOrg(SysOrg sysOrg) throws Exception;

	LmisResult<?> switchSysOrg(T t) throws Exception;

	List<ViewSysOrg> findOrg(ViewSysOrg sysOrg) throws Exception;

	List<SysOrg> findSubordinateSysOrg(T t)throws Exception;

	LmisResult<?> exportOrg(int pageSize);

	LmisResult<?> getSysOrg(T t) throws Exception;

}
