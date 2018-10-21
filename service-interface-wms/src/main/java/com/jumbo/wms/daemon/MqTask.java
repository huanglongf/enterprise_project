package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

/**
 * MQ定时任务
 * 
 * @author lzb
 * 
 */

public interface MqTask extends BaseManager {

    /**
     * 生产者补偿
     * 
     * @param topics
     */
    public void producerMsgDataCompensate(String topics);

    /**
     * 消费者补偿
     * 
     */
    public void consumerMsgDataCompensate(String topics);

}
