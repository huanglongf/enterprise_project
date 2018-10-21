package com.lmis.pos.crdOut.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: PosCrdOutServiceInterface
 * @Description: TODO(不拆分CRD设置业务层接口类)
 * @author codeGenerator
 * @date 2018-06-01 17:09:27
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosCrdOutServiceInterface<T extends PersistentObject> {
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，不分页)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> executeSelect(T t) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> executeInsert(T t) throws Exception;
	
	
	/**
	 * @Title: deletePosWhs
	 * @Description: TODO(删除不拆分crd日期)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> deletePosCrdData(T t) throws Exception;
}
