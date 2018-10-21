package com.jumbo.wms.manager.task.vmiwarehouse;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskOutsourcingWH;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;

public class TaskOutsourcingWHImpl extends BaseManagerImpl implements TaskOutsourcingWH {
    /**
	 * 
	 */
    private static final long serialVersionUID = 6105361080357123463L;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrder;

    /**
     * 入库反馈
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inOutBoundRtn() {
        List<MsgRtnInboundOrder> msgOrderNewInfo = msgRtnInboundOrder.findInboundByStatus(Constants.VIM_WH_SOURCE_GQS, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (msgOrderNewInfo.size() > 0) {
            for (MsgRtnInboundOrder msgNewInorder : msgOrderNewInfo) {
                try {
                    wareHouseManagerProxy.msgInBoundWareHouse(msgNewInorder);
                } catch (BusinessException be){
                	log.error("inOutBoundRtn error ,error code is : {}", be.getErrorCode());
                } catch (UnexpectedRollbackException e ){ 
                	log.error("inOutBoundRtn error ,Transaction rolled back because it has been marked as rollback-only, id : {}", msgNewInorder.getId());
                } catch (Exception e) {
                	log.error("", e);
                }
            }
        }
    }
}
