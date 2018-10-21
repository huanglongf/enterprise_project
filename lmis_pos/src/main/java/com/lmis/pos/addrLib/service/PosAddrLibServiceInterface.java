package com.lmis.pos.addrLib.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: PosAddrLibServiceInterface
 * @Description: TODO(地址库表（基础数据）业务层接口类)
 * @author codeGenerator
 * @date 2018-05-29 10:46:34
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosAddrLibServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param t
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:46:34
	 */
	LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:46:34
	 */
	LmisResult<?> executeSelect(T t) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:46:34
	 */
	LmisResult<?> executeInsert(T t) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:46:34
	 */
	LmisResult<?> executeUpdate(T t) throws Exception;
	
	/**
	 * @Title: deletePosAddrLib
	 * @Description: TODO(删除地址库表（基础数据）)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:46:34
	 */
	LmisResult<?> deletePosAddrLib(T t) throws Exception;
}
