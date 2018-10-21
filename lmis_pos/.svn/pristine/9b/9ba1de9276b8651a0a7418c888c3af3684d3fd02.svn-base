package com.lmis.pos.whsSurplusSc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusSc;

/** 
 * @ClassName: PosWhsSurplusScServiceInterface
 * @Description: TODO(仓库剩余入库能力分析业务层接口类)
 * @author codeGenerator
 * @date 2018-05-30 17:16:40
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosWhsSurplusScServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:16:40
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:16:40
	 */
	LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:16:40
	 */
	LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:16:40
	 */
	LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception;
	
	/**
	 * @Title: deletePosWhsSurplusSc
	 * @Description: TODO(删除仓库剩余入库能力分析)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-30 17:16:40
	 */
	LmisResult<?> deletePosWhsSurplusSc(T t) throws Exception;
	
	/**
	 * @Title: updatePlannedInAbility
	 * @Description: TODO(更新可计划入库能力)
	 * @param whsCode
	 * @param bu
	 * @param crd
	 * @param qty
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年6月1日 下午2:25:48
	 */
	LmisResult<?> updatePlannedInAbility(String whsCode, String bu, String crd, Integer qty) throws Exception;
	
	/**
	 * 仓库剩余能力分析查询
	 * @param t
	 * @param pageObject
	 * @return
	 */

	LmisResult<?> selectPosWhsSurplusScList(T t,LmisPageObject pageObject);
	
}
