package com.lmis.sys.message.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: SysMessageServiceInterface
 * @Description: TODO(业务层接口类)
 * @author codeGenerator
 * @date 2018-05-22 20:04:53
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SysMessageServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 20:04:53
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 20:04:53
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 20:04:53
	 */
	LmisResult<?> executeInsert(T t) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 20:04:53
	 */
	LmisResult<?> executeUpdate(T t) throws Exception;
	
	/**
	 * @Title: deleteSysMessage
	 * @Description: TODO(删除)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-22 20:04:53
	 */
	LmisResult<?> deleteSysMessage(T t) throws Exception;
	
	/**
     * 根据编码获取系统消息
     * @param t
     * @return
     * @throws Exception
     */
    String getSysMessageByCode(String code) ;
    /**
     * 格式化取出的系统消息数据
     * @param code
     * @return
     */
    HashMap<String, String> GetMessageByCodeFormat(String code);
    /**
     * 根据传入的code值获取多个消息值
     * @author xuyisu
     * @date  2018年6月12日
     * 
     * @param listCode
     * @return
     */
    LmisResult<?> getSysMessages(List<String> listCode);
}
