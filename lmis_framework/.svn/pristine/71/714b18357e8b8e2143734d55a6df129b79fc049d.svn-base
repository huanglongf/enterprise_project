package com.lmis.sys.codeRule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: SysCoderuleInfoServiceInterface
 * @Description: TODO(业务层接口类)
 * @author codeGenerator
 * @date 2018-05-22 08:53:05
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SysCoderuleInfoServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 08:53:05
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 08:53:05
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 08:53:05
	 */
	LmisResult<?> executeInsert(T t) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 08:53:05
	 */
	LmisResult<?> executeUpdate(T t) throws Exception;
	
	/**
	 * @Title: deleteSysCoderuleInfo
	 * @Description: TODO(删除)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 08:53:05
	 */
	LmisResult<?> deleteSysCoderuleInfo(T t) throws Exception;
	/**
	 * 公共调用获取配置编号
	 * @author xuyisu
	 * @date 20180525
	 * @param configCode
	 * @return
	 */
    String GetCodeRule(String configCode);
    /**
     * 获取测试公共配置编号
     * @author xuyisu
     * @date 20180525
     * @param configCode
     * @return
     * @throws Exception
     */
    LmisResult<?> TestCodeRule(String configCode) throws Exception;
}
