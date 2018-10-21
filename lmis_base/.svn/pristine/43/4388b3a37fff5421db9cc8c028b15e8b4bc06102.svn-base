package com.lmis.setup.constant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/**
 * @author daikaihua
 * @date 2017年11月28日
 * @todo TODO
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface SetupConstantServiceInterface<T extends PersistentObject> {

	LmisResult<T> queryPage(T t, LmisPageObject pageObject) throws Exception;

	LmisResult<T> addSetupConstant(T t) throws Exception;
	
	LmisResult<T> updateSetupConstant(T t) throws Exception;
	
	LmisResult<T> deleteSetupConstant(T t) throws Exception;
	
	LmisResult<T> checkSetupConstant(T t) throws Exception;
}
