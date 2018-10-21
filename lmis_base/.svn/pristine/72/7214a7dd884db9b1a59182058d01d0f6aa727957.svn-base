package com.lmis.sys.userRole.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.sys.userRole.model.SysUserRole;
import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.framework.baseInfo.LmisResult;

/** 
 * @ClassName: SysUserRoleMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-09 16:43:38
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysUserRoleMapper<T extends SysUserRole> extends BaseMapper<T> {

	LmisResult<Map<String, Object>> findRole(List<T> userRoleList);

}
