package com.jumbo.wms.manager.vmi.jdData;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.bh.util.JSONUtil;
import cn.baozun.model.jd.EtmsWayBillCode;
import cn.baozun.model.jd.EtmsWayBillSend;

import com.jumbo.util.mq.MqMsgUtil;
import com.jumbo.wms.manager.BaseManagerImpl;

@Service("jdManager")
public class JdManagerImpl extends BaseManagerImpl implements JdManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5230844614849153753L;
    @Resource
    private ApplicationContext applicationContext;

    /**
     * MQ消息模板
     */
    private JmsTemplate bhMqJmsTemplate;

    @PostConstruct
    protected void init() {
        try {
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named bhMqJmsTemplate");
        }
    }

    @Transactional
    public void sendMqGetJdTransNo(Long omsShopId, String mqName) {
        EtmsWayBillCode billCode = new EtmsWayBillCode();
        billCode.setPreNum("100");
        billCode.setOmsShopId(omsShopId);
        billCode.setCustomerCode("021K4995");
        String msg = JSONUtil.beanToJson(billCode);
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);
    }

    @Transactional
    public void senMqJdReceiveOrder(EtmsWayBillSend billSend, String mqName) {
        String msg = JSONUtil.beanToJson(billSend);
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);

    }

}
