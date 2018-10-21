package com.jumbo.wms.manager.task.vmi;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.scm.baseservice.message.rocketmq.service.compensate.ConsumerCompensateService;
import com.baozun.scm.baseservice.message.rocketmq.service.compensate.ProducerCompensateService;
import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.MqTask;
import com.jumbo.wms.manager.BaseManagerImpl;


@SuppressWarnings("serial")
public class MqTaskImpl extends BaseManagerImpl implements MqTask {
    

    @Autowired(required = false)
    private ProducerCompensateService producerCompensateService;

    @Autowired(required = false)
    private ConsumerCompensateService consumerCompensateService;



    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void producerMsgDataCompensate(String topics) {
        producerCompensateService.producerMsgDataCompensateByLoxia(topics);
    }


    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void consumerMsgDataCompensate(String topics) {
        log.error("consumerMsgDataCompensate:"+topics);
        //consumerCompensateService.consumerMsgDataCompensateByLoxia(topics);
       // consumerCompensateService.consumerMsgDataCompensate(topics);
        try {
            consumerCompensateService.consumerMsgDataCompensateList(topics);
        } catch (Exception e) {
            log.error("consumerMsgDataCompensate_1", e);
        }

    }

}
