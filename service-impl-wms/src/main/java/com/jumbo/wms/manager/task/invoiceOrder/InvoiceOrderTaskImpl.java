package com.jumbo.wms.manager.task.invoiceOrder;

import org.springframework.beans.factory.annotation.Autowired;
import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.InvoiceOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.FillInInvoiceManager;

/**
 * 批量发票订单获取运单号接口
 * 
 * @author PUCK SHEN
 * 
 */
public class InvoiceOrderTaskImpl extends BaseManagerImpl implements InvoiceOrderTask{
    /**
     * 
     */
    private static final long serialVersionUID = -2614682036554069115L;
    @Autowired
    private FillInInvoiceManager fillInInvoiceManager;
    
    /**
     * 批量发票订单获取运单号接口
     * @param whId
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Override
    public void invoiceOrderInterface() {
        fillInInvoiceManager.invoiceOrderInterface();
    }
}
