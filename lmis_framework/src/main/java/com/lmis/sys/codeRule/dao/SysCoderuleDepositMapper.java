package com.lmis.sys.codeRule.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.codeRule.model.SysCoderuleDeposit;

/** 
 * @ClassName: SysCoderuleDepositMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-17 13:16:21
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysCoderuleDepositMapper<T extends SysCoderuleDeposit> extends BaseMapper<T> {
	
}
