package com.lmis.sys.userRoleOrg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.lmis.sys.userRoleOrg.model.SysUserRoleOrg;
import com.lmis.framework.baseDao.BaseMapper;

/** 
 * @ClassName: SysUserRoleOrgMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-09 17:10:23
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysUserRoleOrgMapper<T extends SysUserRoleOrg> extends BaseMapper<T> {

	Integer updateBatch(List<String> orgIdList);

	List<SysUserRoleOrg> getUserRoleOrgByOrgId(List<SysUserRoleOrg> userRoleOrgList);
	
}
