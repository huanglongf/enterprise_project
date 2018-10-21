package com.lmis.pos.poSplitCollect.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.model.PosPurchaseOrderMain;

/** 
 * @ClassName: PosCrdOutServiceInterface
 * @Description: TODO(PO拆分汇总分析业务层接口类)
 * @author codeGenerator
 * @date 2018-06-22 17:09:27
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface PoSplitCollectServiceInterface {
	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行搜索语句，获取多条记录，不分页)
	 * @throws Exception
	 * @return: LmisResult<?>
	 * @author: codeGenerator
	 * @date: 2018-06-22 17:09:27
	 */
	LmisResult<?> selectPoSplitCollectData(PosPurchaseOrderMain posPurchaseOrderMain) throws Exception;
	
}
