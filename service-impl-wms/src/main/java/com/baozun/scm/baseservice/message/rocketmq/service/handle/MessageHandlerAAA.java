package com.baozun.scm.baseservice.message.rocketmq.service.handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.common.MessageConsumerCommond;
import com.baozun.scm.baseservice.message.content.RocketMQConstants;
import com.baozun.scm.baseservice.message.rocketmq.service.MsgTranscationManagerImpl;
import com.baozun.scm.baseservice.message.rocketmq.service.dao.MessageMongoDbDao;
import com.baozun.scm.baseservice.message.util.secret.AESGeneralUtil;
import com.baozun.scm.baseservice.message.util.secret.MD5Util;
import com.baozun.scm.baseservice.message.util.tablenum.MongodbTableNum;
import com.baozun.scm.baseservice.schema.MsgHandleSchema;
import com.baozun.scm.mongodb.util.BeanUtil;
import com.baozun.utilities.json.JsonUtil;
import com.mongodb.DBObject;

/**
 * 消息组件分发各业务模块消息 描述: 版权: Copyright (c) 2016 公司: 宝尊电商 作者: kun.tan@baozun.cn 版本: 1.0 创建日期: 2017年6月7日
 * 创建时间: 下午7:05:26
 */
public class MessageHandlerAAA {


    private static Logger logger = Logger.getLogger(MessageHandlerAAA.class);

    /**
     * 新版业务分发
     */
    private List<MsgHandleSchema> businessList;

    /**
     * 事务执行体
     */
    @Autowired(required = false)
    private MsgTranscationManagerImpl msgTranscationManager;

    /**
     * 获取实际秘钥集合
     */
    private Map<String, String> keyValueMap = new HashMap<String, String>();

    /**
     * 将主题及标签组合
     */
    public Map<String, AbstractMessageService> map = new HashMap<String, AbstractMessageService>();

    /**
     * 主题与标签对应beanName+method
     */
    public Map<String, String> businessMap = new HashMap<String, String>();

    @Autowired(required = false)
    private MessageMongoDbDao messageMongoDbDao;

    /**
     * 执行业务逻辑
     * 
     * @param topic
     * @param tags
     * @param body
     * @param keys 全局ID 主要用于记录在消息状态表中，定位数据信息
     * @param messageId rocketMQ反馈的消息id 2017年6月7日 下午7:14:09
     */
    public void handle(String topic, String tags, String body, String keys, String messageId) {
        try {
            // 获取实际秘钥
            String secretInfo = "";
            if (keyValueMap.containsKey(topic)) {
                secretInfo = keyValueMap.get(topic);
            } else {
                MD5Util md5Util = new MD5Util();
                secretInfo = md5Util.getMd5_16New(topic.trim());
                keyValueMap.put(topic, secretInfo);
            }
            if (secretInfo != null && secretInfo.length() > 0) {
                boolean topicEncryptionFlag = RocketMQConstants.encryptionTopMap.get(topic) == null ? true : false;
                String decryptMsg = ""; // 消息体
                if (topicEncryptionFlag) {
                    decryptMsg = AESGeneralUtil.decrypt(body, secretInfo);
                } else {
                    decryptMsg = body;
                }
                MessageCommond msgCommond = JsonUtil.readValue(decryptMsg, MessageCommond.class);
                if (msgCommond.getIsMsgBodySend() != null && msgCommond.getIsMsgBodySend() == false) {
                    MongodbTableNum tableNum = new MongodbTableNum();
                    int num = tableNum.getTableSuffix(msgCommond.getMsgId() + msgCommond.getMsgType());
                    DBObject document = null;
                    document = this.messageMongoDbDao.findByObjectIdByCutTable(msgCommond.getMongodbObjectId(), "msg_mongodb_rocketmq" + num);
                    if (null == document) {
                        int index = 0;
                        while (index < 3) {
                            try {
                                index++;
                                Thread.sleep(1000);
                                document = this.messageMongoDbDao.findByObjectIdByCutTable(msgCommond.getMongodbObjectId(), "msg_mongodb_rocketmq" + num);
                                if (!StringUtils.isEmpty(document)) {
                                    logger.warn("application compensate time:【" + index + "】success !topic:" + topic + "key" + keys + "messageId" + messageId);
                                    break;
                                }
                            } catch (Exception e) {
                                logger.error("application compensate time:【" + index + "】 exception topic:" + topic + "key" + keys + "messageId" + messageId);
                                if (index < 3) {
                                    continue;
                                }
                            }
                        }
                        if (null == document) {
                            // 是否开启兼容性查询
                            if (RocketMQConstants.isMongodbCompatibleEnable()) {
                                num = tableNum.getTableSuffix(topic + msgCommond.getMsgId() + msgCommond.getMsgType());
                                document = this.messageMongoDbDao.findByObjectIdByCutTable(msgCommond.getMongodbObjectId(), "msg_mongodb_rocketmq" + num);
                                if (null == document) {
                                    logger.warn("application【OPEN】 for mongodb search by compatible ! but not find data");
                                    throw new Exception("application【OPEN】 for mongodb search by compatible ! but not find data");
                                }
                            } else {
                                logger.warn("application【NOT OPEN】 for mongodb search by compatible ! but not find data");
                                throw new Exception("application【NOT OPEN】 for mongodb search by compatible ! but not find data");
                            }
                        }
                    }
                    msgCommond = BeanUtil.dbObjectToBean(document, msgCommond);
                }
                // mongodb中消息体解密
                String msgTypeSecret = "";
                if (keyValueMap.containsKey(msgCommond.getMsgType())) {
                    msgTypeSecret = keyValueMap.get(msgCommond.getMsgType());
                } else {
                    MD5Util md5Util = new MD5Util();
                    msgTypeSecret = md5Util.getMd5_16New(msgCommond.getMsgType());
                    keyValueMap.put(msgCommond.getMsgType(), msgTypeSecret);
                }
                String newDecryptBodyMsg = "";
                try {
                    newDecryptBodyMsg = AESGeneralUtil.decrypt(msgCommond.getMsgBody(), msgTypeSecret);
                } catch (Exception e) {
                    logger.warn("mongodb dataBody resource content:" + msgCommond.getMsgBody());
                    newDecryptBodyMsg = msgCommond.getMsgBody();
                }
                msgCommond.setMsgBody(newDecryptBodyMsg);
                MessageConsumerCommond msgConsumerCommond = new MessageConsumerCommond();
                msgConsumerCommond.setMessageId(messageId);
                excuteHandle(topic, tags, msgConsumerCommond, msgCommond);
            }
        } catch (Exception e) {
            logger.error(topic + "business msgId【" + keys + "】MQ MESSAGEID【" + messageId + "】[MessageHandler exception:]" + e.getMessage(), e);
            throw new RuntimeException("[MessageHandler exception:]", e);
        }

    }

    /**
     * 消费者自动补偿完成相关业务
     * 
     * @param dataRow 2017年6月8日 下午3:51:20
     */
    @Deprecated
    public void businessAutoCompensate(MessageConsumerCommond dataRow) {
        try {
            logger.info("消费者补偿消息数据:" + dataRow.toString());
            try {
                MongodbTableNum tableNum = new MongodbTableNum();
                int num = tableNum.getTableSuffix(dataRow.getTopic() + dataRow.getMsgId() + dataRow.getMsgType());
                MessageCommond mongoRow = this.messageMongoDbDao.getProducerMessageCommondBody(dataRow.getMsgId(), dataRow.getMsgType(), "msg_mongodb_rocketmq" + num);
                if (mongoRow == null) {
                    logger.warn(dataRow.getId() + "【补偿】未获取到消息body数据!");
                    return;
                }
                excuteHandle(dataRow.getTopic(), dataRow.getTags(), dataRow, mongoRow);
            } catch (Exception e) {
                logger.error("消费者获取消息对象数据入数据仓库异常", e);
            }
        } catch (Exception e) {
            logger.error("消费者自动补偿实现业务功能异常:", e);
        }
    }

    /**
     * 业务内容分发
     * 
     * @param topic
     * @param tags
     * @param body
     * @param keys 2017年6月30日 下午2:42:23
     * @author kun.tan desc:因消费端有存在订阅所有标签(*), 不明确订阅哪一个tags,因此当发现生产者发送了N个tags, 而消费者只订阅采用*来消费，需做如下处理.
     */
    public void excuteHandle(String topic, String tags, MessageConsumerCommond msgConsumerCommond, MessageCommond msgCommond) {
        try {
            if (null != businessList && businessList.size() > 0) {
                String beanNameAndMethod = businessMap.get(topic + tags);
                if (null == beanNameAndMethod || "".equals(beanNameAndMethod) || beanNameAndMethod.length() < 0) {
                    for (MsgHandleSchema msgHandle : businessList) {
                        // 业务方根据topic+*找具体的业务实现方法<优先级高于tags>
                        if (msgHandle.getTags().equals("*") && msgHandle.getTopic().equals(topic)) {
                            businessMap.put(topic + "*", msgHandle.getBeanNameId() + "||" + msgHandle.getMethod());
                            msgTranscationManager.businessProcess(msgHandle.getBeanNameId(), msgHandle.getMethod(), msgConsumerCommond, msgCommond);
                            break;
                        }
                        // 业务方根据topic+tag找具体的业务实现方法
                        if (msgHandle.getTopic().equals(topic) && msgHandle.getTags().equals(tags)) {
                            businessMap.put(topic + tags, msgHandle.getBeanNameId() + "||" + msgHandle.getMethod());
                            msgTranscationManager.businessProcess(msgHandle.getBeanNameId(), msgHandle.getMethod(), msgConsumerCommond, msgCommond);
                            break;
                        }
                    }
                } else {
                    String[] beanNameMethods = beanNameAndMethod.split("\\|\\|");
                    msgTranscationManager.businessProcess(beanNameMethods[0], beanNameMethods[1], msgConsumerCommond, msgCommond);
                }
            }
        } catch (Exception e) {
            if (StaleObjectStateException.class == e.getClass()) {
                logger.warn("wms3.0 org.hibernate.StaleObjectStateException", e);
                return;
            }
            if (UnexpectedRollbackException.class == e.getClass()) {
                logger.warn("wms3.0 org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only", e);
                return;
            }
            logger.error("++++消息分发异常++++", e);
            throw new RuntimeException("++++消息分发异常++++", e);
        }

    }

    public List<MsgHandleSchema> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<MsgHandleSchema> businessList) {
        this.businessList = businessList;
    }

    public String toString() {
        return "MessageHandler [businessList=" + businessList + "]";
    }

    public MsgTranscationManagerImpl getMsgTranscationManager() {
        return msgTranscationManager;
    }

    public void setMsgTranscationManager(MsgTranscationManagerImpl msgTranscationManager) {
        this.msgTranscationManager = msgTranscationManager;
    }

    public MessageMongoDbDao getMessageMongoDbDao() {
        return messageMongoDbDao;
    }

    public void setMessageMongoDbDao(MessageMongoDbDao messageMongoDbDao) {
        this.messageMongoDbDao = messageMongoDbDao;
    }

}
