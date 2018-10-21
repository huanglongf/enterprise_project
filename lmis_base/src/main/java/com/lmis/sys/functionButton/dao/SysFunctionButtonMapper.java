package com.lmis.sys.functionButton.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.lmis.sys.functionButton.model.SysFunctionButton;
import com.lmis.framework.baseDao.BaseMapper;

/** 
 * @ClassName: SysFunctionButtonMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-09 13:09:08
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysFunctionButtonMapper<T extends SysFunctionButton> extends BaseMapper<T> {
	List<T> sortQuery(T t);
}
