package com.jumbo.wms.manager.task.sms;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.system.SmsQueue;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface SmsTaskManager extends BaseManager {
    /**
     * 获取需要发送信息列表
     * 
     * @return
     */
    List<SmsQueue> getNeedSendSmsList();

    /**
     * 调用接口成功,添加msgId到中间表
     * 
     * @param smsQueue
     */
    void deleteFromInsertTo(SmsQueue smsQueue, Integer status, String sendTime);

    /**
     * 发送失败，更新失败次数
     * 
     * @param id
     */
    void updateErrorCountById(Long id);

    /**
     * 调用接口成功，更新添加msgId
     * 
     * @param smsQueue
     * @param rs
     */
    void updateSmsQueueMsgId(SmsQueue smsQueue, String rs);

    /**
     * 查询有msgId的中间表信息
     * 
     * @return
     */
    Map<String, List<SmsQueue>> getSmsQueueHaveMsgIdList();

    /**
     * 查询所有店铺的短信平台帐号密码
     * 
     * @return
     */
    List<BiChannel> getShopAccountList();

    /**
     * 直接设置失败次数为三不在发送此短信
     * 
     * @param id
     */
    void updateTheQueueSetFailedById(Long id);


}
