package com.lmis.pos.whsAllocate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.pos.common.model.PosPurchaseOrderMain;
import com.lmis.pos.whsAllocate.model.PosWhsAllocateDate;

/** 
 * @ClassName: PosWhsAllocateServiceInterface
 * @Description: TODO(PO分仓结果业务层接口类)
 * @author codeGenerator
 * @date 2018-05-30 14:49:24
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service("posWhsAllocateService2")
public interface PosWhsAllocateService<T extends PersistentObject> {

	/**
	 * @Title: getQtySum
	 * @Description: TODO(获取拆分后明细商品总量)
	 * @param ids
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2018年6月21日 下午3:49:53
	 */
	int getQtySum(List<String> ids);
	
	/**
	 * @Title: rollbackBatch
	 * @Description: TODO(批量回滚插入的分仓结果)
	 * @param lmisUserId
	 * @param ids
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2018年6月21日 上午11:21:01
	 */
	int rollbackBatch(String lmisUserId, List<String> ids);
	
	/**
	 * @Title: poAllocatedCheck
	 * @Description: TODO(PO分仓结果校验)
	 * @param _ids
	 * @return: LmisResult<?>
	 * @author: Ian.Huang
	 * @date: 2018年6月21日 下午1:23:01
	 */
	LmisResult<?> poAllocatedCheck(String _ids);
	
	/**
	 * @Title: poAllocatedRollback
	 * @Description: TODO(PO单取消分仓)
	 * @param posPurchaseOrderMain
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2018年6月21日 下午2:32:22
	 */
	void poAllocatedRollback(PosPurchaseOrderMain posPurchaseOrderMain) throws Exception;
	
	/**
	 * @Title: logicalDeleteRollback
	 * @Description: TODO(逻辑删除回滚操作)
	 * @param updateBy
	 * @param ids
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2018年6月21日 下午11:57:12
	 */
	int logicalDeleteRollback(String updateBy, List<String> ids);

    LmisResult<?> selectPosWhsAllocate(PosWhsAllocateDate t,LmisPageObject pageObject) throws Exception;
	
	LmisResult<?> findPosWhsAllocate(T t, LmisPageObject pageObject);

}
