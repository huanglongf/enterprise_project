package com.lmis.sys.roleFunctionButton.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.lmis.sys.roleFunctionButton.model.SysRoleFunctionButton;
import com.lmis.framework.baseDao.BaseMapper;

/** 
 * @ClassName: SysRoleFunctionButtonMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-09 17:35:11
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysRoleFunctionButtonMapper<T extends SysRoleFunctionButton> extends BaseMapper<T> {

	Integer updateBatch(List<String> fbIdList);

	List<SysRoleFunctionButton> getRoleFbByFbId(List<SysRoleFunctionButton> roleFbList);
	
}
