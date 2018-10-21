package com.lmis.pos.whs.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: PosWhsServiceInterface
 * @Description: TODO(仓库主表业务层接口类)
 * @author codeGenerator
 * @date 2018-05-29 10:13:18
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosWhsScheduleServiceInterface<T extends PersistentObject> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param t
	 * @param pageObject
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-05-29 10:13:18
	 */
	LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception;
	
	Boolean executeDelete(T t) throws Exception;
	
	LmisResult<?> excelDownLoad(T t) throws Exception;
	
	LmisResult<?> excelUploadLog(List<Map<String, Object>> list, String batchId) throws Exception;
	
}
