package com.jumbo.wms.manager.msg;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lark.common.dao.Page;
import lark.common.dao.Pagination;
import lark.common.dao.Sort;
import loxia.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.scm.baseservice.message.common.MessageConsumerCommond;
import com.baozun.scm.baseservice.message.exception.BusinessException;
import com.baozun.scm.baseservice.message.manager.MessageConsumerManager;
import com.jumbo.dao.msg.MessageConsumerDao;
import com.jumbo.wms.model.msg.MessageConsumer;

@Service
public class MessageConsumerManagerImpl implements MessageConsumerManager {
    protected static final Logger log = LoggerFactory.getLogger(MessageConsumerManagerImpl.class);

    @Autowired
    private MessageConsumerDao consumerDao;

    public int saveOrUpdate(MessageConsumerCommond messageConsumerCommond) throws BusinessException {
        MessageConsumer mc = new MessageConsumer();
        mc.setId(messageConsumerCommond.getId());
        mc.setMsgId(messageConsumerCommond.getMsgId());
        mc.setMsgType(messageConsumerCommond.getMsgType());
        mc.setTopic(messageConsumerCommond.getTopic());
        mc.setTags(messageConsumerCommond.getTags());
        mc.setStatus(messageConsumerCommond.getStatus());
        mc.setCompensateCount(messageConsumerCommond.getCompensateCount());
        mc.setBrand("2");
        try {
            mc.setReceiveTime(DateUtil.parse(messageConsumerCommond.getReceiveTime(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
           // log.error("parse ReceiveTime error!", e);
        }
        try {
            mc.setDealTime(DateUtil.parse(messageConsumerCommond.getDealTime(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
           // log.error("parse DealTime error!", e);
        }
        consumerDao.save(mc);
        messageConsumerCommond.setId(mc.getId());
        return 1;
    }

    public MessageConsumerCommond findById(String msgId, String msgType) throws BusinessException {
        return this.consumerDao.findMsgByMsgId(msgId, msgType, new BeanPropertyRowMapper<MessageConsumerCommond>(MessageConsumerCommond.class));
    }

    @SuppressWarnings("unchecked")
    public Pagination<MessageConsumerCommond> findByParams(Page page, Sort[] sorts, Map params) throws BusinessException {
        Long id = null;
        String msgId = null;
        String msgType = null;
        List<String> topics = null;
        Integer status = null;
        String receiveTime = null;
        Date receiveTimeDate = null;
        String dealTime = null;
        Date dealTimeDate = null;
        String tags = null;
        if (params != null) {
            try {
                id = (Long) params.get("id");
                msgId = (String) params.get("msgId");
                msgType = (String) params.get("msgType");
                topics = (List<String>) params.get("topics");
                status = (Integer) params.get("status");
                receiveTime = (String) params.get("receiveTime");
                try {
                    if (receiveTime != null) {
                        receiveTimeDate = DateUtil.parse(receiveTime, "yyyy-MM-dd HH:mm:ss");
                    }
                } catch (Exception e) {
                    log.error("parse params ReceiveTime error!", e);
                }
                dealTime = (String) params.get("dealTime");
                try {
                    if (dealTime != null) {
                        dealTimeDate = DateUtil.parse(dealTime, "yyyy-MM-dd HH:mm:ss");
                    }
                } catch (Exception e) {
                    log.error("parse params DealTime error!", e);
                }
                tags = (String) params.get("tags");
            } catch (Exception e) {
                log.error("get customer params error!", e);
            }
        }
        return this.consumerDao.findListByQueryMapWithPage(page.getStart(), page.getSize(), id, msgId, msgType, topics, status, receiveTimeDate, dealTimeDate, tags, sorts, new BeanPropertyRowMapper<MessageConsumerCommond>(MessageConsumerCommond.class));
    }

    @Override
    public List<MessageConsumerCommond> findByMsgList(Map params) throws BusinessException {
        Long id = null;
        String msgId = null;
        String msgType = null;
        List<String> topics = null;
        Integer status = null;
        String receiveTime = null;
        Date receiveTimeDate = null;
        String dealTime = null;
        Date dealTimeDate = null;
        String tags = null;
        if (params != null) {
            id = (Long) params.get("id");
            msgId = (String) params.get("msgId");
            msgType = (String) params.get("msgType");
            topics = (List<String>) params.get("topics");
            status = (Integer) params.get("status");
            receiveTime = (String) params.get("receiveTime");
            try {
                if (receiveTime != null) {
                    receiveTimeDate = DateUtil.parse(receiveTime, "yyyy-MM-dd HH:mm:ss");
                }
            } catch (Exception e) {
               // log.error("parse params ReceiveTime error!", e);
            }
            dealTime = (String) params.get("dealTime");
            try {
                if (dealTime != null) {
                    dealTimeDate = DateUtil.parse(dealTime, "yyyy-MM-dd HH:mm:ss");
                }
            } catch (Exception e) {
                //log.error("parse params DealTime error!", e);
            }
            tags = (String) params.get("tags");
        }
        return this.consumerDao.findByMsgList(id, msgId, msgType, topics, status, receiveTimeDate, dealTimeDate, tags, new BeanPropertyRowMapper<MessageConsumerCommond>(MessageConsumerCommond.class));
    }
}
