package com.lmis.pos.whsAllocate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.pos.common.model.PosPurchaseOrderDetail;
@Transactional(rollbackFor=Exception.class)
@Service("posWhsCompartmentService")
public interface PosWhsCompartmentService<T extends PersistentObject> {
    
    /**
     * @Title: allocatePurchaseOrder
     * @Description: TODO(分单接口方法)
     * @param poDetail
     * @throws Exception
     * @return: void
     * @author: Ian.Huang
     * @date: 2018年6月5日 上午1:20:18
     */
    void allocatePurchaseOrder(PosPurchaseOrderDetail poDetail) throws Exception;
    
    /**
     * @Title: logicalDelete
     * @Description: TODO(逻辑删除)
     * @param t
     * @return: void
     * @author: Ian.Huang
     * @date: 2018年6月22日 上午12:36:27
     */
    void logicalDelete(T t);
    
}
