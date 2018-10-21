/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.manager.checklist;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import loxia.utils.DateUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.taskLock.TaskLockLogDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.warehouse.AutoOutBoundEntity;

/**
 * 检查服务可用性
 * 
 * @author sjk
 * 
 */
@Service("checkListManager")
public class CheckListManagerImpl extends BaseManagerImpl implements CheckListManager {

    private static final long serialVersionUID = 2522112219154871153L;

    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private TaskLockLogDao taskLockLogDao;

    @Value("${mq.mq.wms3.outbound_pickinglist}")
    private String WMS3_MQ_OUTBOUND_BY_PICKINGLIST;

    @Autowired
    private RocketMQProducerServer producerServer;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @Autowired
    private MessageConfigDao messageConfigDao;

    @Autowired
    private WareHouseManager wareHouseManager;

    TimeHashMap<String, List<MessageConfig>> outBoundMessage = new TimeHashMap<String, List<MessageConfig>>();


    /**
     * 检查dubbo 服务可用性
     */
    @Override
    public boolean checkDubboService(Date checkDate) {
        if (checkDate != null) {
            log.info(FormatUtil.formatDate(checkDate, FormatUtil.DATE_FORMATE_YYYYMMDD + FormatUtil.DATE_FORMATE_HHMMSS));
        }
        return true;
    }

    /**
     * 检查数据库可用性
     */
    @Override
    @Transactional(readOnly = true)
    public boolean checkDd(Date checkDate) {
        if (checkDate != null) {
            log.info(FormatUtil.formatDate(checkDate, FormatUtil.DATE_FORMATE_YYYYMMDD + FormatUtil.DATE_FORMATE_HHMMSS));
        }
        try {
            OperationUnit ou = operationUnitDao.getByPrimaryKey(2L);
            if (ou != null) {
                log.info("check list : {}", ou.getName());
            } else {
                log.info("check list : ou is null");
            }
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkTaskIsOk(Date checkDate) {
        if (checkDate != null) {
            log.info(FormatUtil.formatDate(checkDate, FormatUtil.DATE_FORMATE_YYYYMMDD + FormatUtil.DATE_FORMATE_HHMMSS));
        }
        try {
            // 检查定时日志表，半小时内的行数，小于30 视为有异常，做邮件通知
            Integer count = taskLockLogDao.findCountRowByDate(new SingleColumnRowMapper<Integer>(Integer.class));
            if (count < 30) {
                log.info("check list task row count: {}", count);
            } else {
                log.info("check list task row count OK: {} ", count);
                return true;
            }
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
        return false;
    }



    public void outBoundByPistListToMq(List<AutoOutBoundEntity> autoOutBoundEntityList) {
        if (null != autoOutBoundEntityList && autoOutBoundEntityList.size() > 0) {
            List<MessageConfig> configs = new ArrayList<MessageConfig>();
            configs = outBoundByPistList(MqStaticEntity.WMS3_MQ_OUTBOUND_BY_PICKINGLIST);
            String msg = JsonUtil.writeValue(autoOutBoundEntityList);
            // MQ 开关
            if (configs != null && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {// 开
                // 发送MQ
                MessageCommond mc = new MessageCommond();
                mc.setMsgId(autoOutBoundEntityList.get(0).getStaId() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                mc.setMsgType("com.jumbo.wms.manager.checklist.outBoundByPistListToMq");
                mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                mc.setMsgBody(msg);
                mc.setIsMsgBodySend(false);
                try {
                    producerServer.sendDataMsgConcurrently(WMS3_MQ_OUTBOUND_BY_PICKINGLIST, mc);
                } catch (Exception e) {
                    log.error("outBoundByPistListToMq:" + autoOutBoundEntityList.get(0).getStaId());
                }
                MongoDBMessage mdbm = new MongoDBMessage();
                BeanUtils.copyProperties(mc, mdbm);
                mdbm.setMsgBody(msg);
                mdbm.setStaCode(autoOutBoundEntityList.get(0).getStaId().toString());
                mdbm.setOtherUniqueKey(autoOutBoundEntityList.get(0).getStaId().toString());
                mdbm.setMemo(WMS3_MQ_OUTBOUND_BY_PICKINGLIST);
                mongoOperation.save(mdbm);
            } else {
                wareHouseManager.autoOutBoundByAll(msg);
            }
        }
    }


    public List<MessageConfig> outBoundByPistList(String code) {
        if (StringUtil.isEmpty(code)) return null;
        List<MessageConfig> messageConfig = outBoundMessage.get(code);
        // 缓存中的数据不存在或者已过期
        if (messageConfig == null) {
            messageConfig = outBoundByPistListCache();
        }
        return messageConfig;
    }

    public synchronized List<MessageConfig> outBoundByPistListCache() {
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_OUTBOUND_BY_PICKINGLIST, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        outBoundMessage.put(MqStaticEntity.WMS3_MQ_OUTBOUND_BY_PICKINGLIST, configs, 60 * 1000);
        return configs;
    }

    /**
     * 检查请求服务器时间
     * 
     * @throws Exception
     */
    @Override
    public String checkServicePing() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        String result = "service主机名:" + address.getHostName() + " service主机别名: " + address.getCanonicalHostName() + " service IP地址: " + address.getHostAddress();
        return result;
    }

}
