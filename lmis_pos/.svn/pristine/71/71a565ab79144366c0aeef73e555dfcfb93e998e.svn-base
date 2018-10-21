package com.lmis.pos.skuBase.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.skuBase.model.PosSkuBase;

/** 
 * @ClassName: PosSkuBaseServiceInterface
 * @Description: TODO(业务层接口类)
 * @author codeGenerator
 * @date 2018-06-11 16:43:53
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosSkuBaseServiceInterface {

	/**
	 * @Title: executeSelect
     * @Description: TODO(执行搜索语句，获取多条记录，可分页)
	 * @param posSkuBase
	 * @param pageObject
	 * @return
	 * @throws Exception
	 */
	LmisResult<PosSkuBase> executeSelect(PosSkuBase posSkuBase, LmisPageObject pageObject) throws Exception;
	
}
