package com.jumbo.wms.manager.msg;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lark.common.dao.Page;
import lark.common.dao.Pagination;
import lark.common.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.scm.baseservice.message.common.MessageProducerCommond;
import com.baozun.scm.baseservice.message.exception.BusinessException;
import com.baozun.scm.baseservice.message.manager.MessageProducerManager;
import com.jumbo.dao.msg.MessageProducerDao;
import com.jumbo.wms.model.msg.MessageProducer;

@Service
public class MessageProducerManagerImpl implements MessageProducerManager {
    protected static final Logger log = LoggerFactory.getLogger(MessageProducerManagerImpl.class);

    @Autowired
    private MessageProducerDao producerDao;

    public void saveOrUpdate(MessageProducerCommond messageProducerCommond) throws BusinessException {
        MessageProducer mp = new MessageProducer();
        mp.setId(messageProducerCommond.getId());
        mp.setMsgId(messageProducerCommond.getMsgId());
        mp.setMsgType(messageProducerCommond.getMsgType());
        mp.setTopic(messageProducerCommond.getTopic());
        mp.setStatus(messageProducerCommond.getStatus());
        mp.setTags(messageProducerCommond.getTags());
        try {
            mp.setSendTime(DateUtil.parse(messageProducerCommond.getSendTime(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            log.error("parse SendTime error!", e);
        }
        try {
            mp.setSendTime(DateUtil.parse(messageProducerCommond.getSendTime(), "yyyy-MM-dd HH/mm/ss"));
        } catch (Exception e) {
            log.error("parse SendTime error!", e);
        }
        try {
            mp.setFeedbackTime(DateUtil.parse(messageProducerCommond.getFeedbackTime(), "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            log.error("parse FeedbackTime error!", e);
        }
        producerDao.save(mp);
        messageProducerCommond.setId(mp.getId());
    }

    public MessageProducerCommond findMsgById(String msgId, String msgType) throws BusinessException {
        return this.producerDao.findMsgByMsgId(msgId, msgType, new BeanPropertyRowMapper<MessageProducerCommond>(MessageProducerCommond.class));
    }

    @SuppressWarnings("unchecked")
    public Pagination<MessageProducerCommond> findByParams(Page page, Sort[] sorts, Map params) throws BusinessException {
        Long id = null;
        String msgId = null;
        String msgType = null;
        List<String> topics = null;
        Integer status = null;
        String sendTime = null;
        Date sendTimeDate = null;
        String feedbackTime = null;
        Date feedbackTimeDate = null;
        String tags = null;
        if (params != null) {
            try {
                id = (Long) params.get("id");
                topics = (List<String>) params.get("topics");
                status = (Integer) params.get("status");
                sendTime = (String) params.get("sendTime");
                try {
                    if (sendTime != null) {
                        sendTimeDate = DateUtil.parse(sendTime, "yyyy-MM-dd HH:mm:ss");
                    }
                } catch (Exception e) {
                    log.error("parse params sendTime error!", e);
                }
                feedbackTime = (String) params.get("feedbackTime");
                try {
                    if (feedbackTime != null) {
                        feedbackTimeDate = DateUtil.parse(feedbackTime, "yyyy-MM-dd HH:mm:ss");
                    }
                } catch (Exception e) {
                    log.error("parse params feedbackTime error!", e);
                }
                tags = (String) params.get("tags");
            } catch (Exception e) {
                log.error("get producer params error!", e);
            }
        }
        return this.producerDao.findListByQueryMapWithPage(page.getStart(), page.getSize(), id, msgId, msgType, topics, status, sendTimeDate, feedbackTimeDate, tags, sorts,
                new BeanPropertyRowMapperExt<MessageProducerCommond>(MessageProducerCommond.class));
    }

    @Override
    public List<MessageProducerCommond> findMsgList(Map params) throws BusinessException {
        Long id = null;
        String msgId = null;
        String msgType = null;
        List<String> topics = null;
        Integer status = null;
        String sendTime = null;
        Date sendTimeDate = null;
        String feedbackTime = null;
        Date feedbackTimeDate = null;
        String tags = null;
        if (params != null) {
                id = (Long) params.get("id");
                topics = (List<String>) params.get("topics");
                status = (Integer) params.get("status");
                sendTime = (String) params.get("sendTime");
                try {
                    if (sendTime != null) {
                        sendTimeDate = DateUtil.parse(sendTime, "yyyy-MM-dd HH:mm:ss");
                    }
                } catch (Exception e) {
                    log.error("parse params sendTime error!", e);
                }
                feedbackTime = (String) params.get("feedbackTime");
                try {
                    if (feedbackTime != null) {
                        feedbackTimeDate = DateUtil.parse(feedbackTime, "yyyy-MM-dd HH:mm:ss");
                    }
                } catch (Exception e) {
                    log.error("parse params feedbackTime error!", e);
                }
                tags = (String) params.get("tags");
        }
        return this.producerDao.findMsgList(id, msgId, msgType, topics, status, sendTimeDate, feedbackTimeDate, tags, new BeanPropertyRowMapperExt<MessageProducerCommond>(MessageProducerCommond.class));
    }

    @Override
    public MessageProducerCommond findMsgById(String arg0, String arg1, String arg2) throws BusinessException {
        // TODO Auto-generated method stub
        return this.producerDao.findMsgByMsgId(arg0, arg1, new BeanPropertyRowMapper<MessageProducerCommond>(MessageProducerCommond.class));
    }
}
