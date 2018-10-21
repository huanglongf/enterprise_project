package com.lmis.pos.soldtoRule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: PosCrdOutServiceInterface
 * @Description: TODO(PO-soldto业务层接口类)
 * @author codeGenerator
 * @date 2018-06-22 17:09:27
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PosSoldtoRuleServiceInterface<T extends PersistentObject> {
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，不分页)
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-06-22 17:09:27
	 */
	LmisResult<?> executeSelect() throws Exception;
	
}
