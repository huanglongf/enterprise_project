package com.lmis.sys.errLog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.errLog.model.SysErrLog;

/** 
 * @ClassName: SysErrLogMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-09 14:31:13
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysErrLogMapper<T extends SysErrLog> extends BaseMapper<T> {
	
}
