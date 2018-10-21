package com.lmis.pos.whsOutPlan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanLog;

/** 
 * @ClassName: PosWhsOutPlanServiceInterface
 * @Description: TODO(仓库出库计划业务层接口类)
 * @author codeGenerator
 * @date 2018-05-29 15:13:58
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosWhsOutPlanServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param dynamicSqlParam
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 15:13:58
	 */
	LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception;
	
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取单条记录，不分页)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 15:13:58
	 */
	LmisResult<?> executeSelect(T t) throws Exception;
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 15:13:58
	 */
	LmisResult<?> executeInsert(T t) throws Exception;
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新语句)
	 * @param dynamicSqlParam
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 15:13:58
	 */
	LmisResult<?> executeUpdate(T t) throws Exception;
	
	/**
	 * @Title: deletePosWhsOutPlan
	 * @Description: TODO(删除仓库出库计划)
	 * @param t
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 15:13:58
	 */
	LmisResult<?> deletePosWhsOutPlan(T t) throws Exception;

	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
    LmisResult<?> PosWhsOutPlanUpload(MultipartFile file) throws Exception;

    LmisResult<?> selectPosWhsOutPlanLog(PosWhsOutPlanLog posWhsOutPlanLog,LmisPageObject pageObject)throws Exception;

    LmisResult<?> deletePosWhsOutPlanLog(PosWhsOutPlanLog posWhsOutPlanLog)throws Exception;

    LmisResult<?> checkPosWhsList()throws Exception;
}
