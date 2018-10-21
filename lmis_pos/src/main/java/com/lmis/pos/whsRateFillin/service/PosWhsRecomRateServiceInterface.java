package com.lmis.pos.whsRateFillin.service;

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
public interface PosWhsRecomRateServiceInterface<T extends PersistentObject> {

	LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception;
	
}
