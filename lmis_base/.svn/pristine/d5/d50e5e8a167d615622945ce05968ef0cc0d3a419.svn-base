package com.lmis.sys.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.user.model.SysUser;
import com.lmis.sys.user.model.ViewSysUser;

/** 
 * @ClassName: SysUserMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-09 12:51:12
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysUserMapper<T extends SysUser> extends BaseMapper<T> {

	/**
	 * @Title: retrieveViewSysUser
	 * @Description: TODO(查询view_sys_user)
	 * @param viewSysUser
	 * @return: List<ViewSysUser>
	 * @author: codeGenerator
	 * @date: 2018-01-18 13:22:20
	 */
	List<ViewSysUser> retrieveViewSysUser(ViewSysUser viewSysUser);
}
