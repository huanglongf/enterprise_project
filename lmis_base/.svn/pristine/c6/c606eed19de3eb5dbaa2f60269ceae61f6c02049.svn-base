package com.lmis.sys.sequence.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.sequence.model.SysSequence;

/** 
 * @ClassName: SysSequenceMapper
 * @Description: TODO(自增长管理表DAO映射接口)
 * @author codeGenerator
 * @date 2018-03-06 17:21:55
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysSequenceMapper<T extends SysSequence> extends BaseMapper<T> {
	Long currval(@Param("seqId") String seqId);
	Long nextval(@Param("seqId") String seqId);
	int createKey(@Param("seqId") String seqId);
}
