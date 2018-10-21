package com.lmis.sys.role.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.role.model.SysRole;
import com.lmis.sys.role.model.ViewSysRole;

/** 
 * @ClassName: SysRoleMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-09 13:08:09
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysRoleMapper<T extends SysRole> extends BaseMapper<T> {
	/**
	 * @Title: retrieveViewSysRole
	 * @Description: TODO(查询view_sys_role)
	 * @param viewSysRole
	 * @return: List<ViewSysRole>
	 * @author: codeGenerator
	 * @date: 2018-01-18 13:23:32
	 */
	List<ViewSysRole> retrieveViewSysRole(ViewSysRole viewSysRole);
}
