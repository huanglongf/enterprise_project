package com.jumbo.dao.system;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.jumbo.wms.model.system.SmsQueue;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface SmsQueueDao extends GenericEntityDao<SmsQueue, Long> {
    @NativeQuery
    List<SmsQueue> getNeedSendSmsList(BeanPropertyRowMapper<SmsQueue> beanPropertyRowMapper);

    @NativeUpdate
    void updateErrorCountById(@QueryParam("id") Long id);

    @NativeQuery
    List<SmsQueue> getSmsQueueHaveMsgIdList(BeanPropertyRowMapper<SmsQueue> beanPropertyRowMapper);

    /**
     * KJL 设置该短信不发送 失败次数为3
     * 
     * @param id
     */
    @NativeUpdate
    void updateTheQueueSetFailedById(@QueryParam(value = "id") Long id);
    
}
