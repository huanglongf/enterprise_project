package com.jumbo.wms.manager.task.express;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.daemon.GainTransNoMQTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;

import loxia.utils.DateUtil;

@Transactional
@Service("gainTransNoMQTask")
public class GainTransNoMQTaskImpl extends BaseManagerImpl implements GainTransNoMQTask {

    private static final long serialVersionUID = 1775870360990026738L;

    @Autowired
    private RocketMQProducerServer producerServer;

    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StockTransApplicationDao staDao;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Value("${mq.mq.mq.wms3.get.transNo}")
    private String MQ_WMS3_GET_TRANSNO;

    @Override
    public void gainTransNoMQ(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo di = staDeliveryInfoDao.getByPrimaryKey(staId);

        String msg = null;
        List<StockTransApplication> staList = new ArrayList<StockTransApplication>();
        StockTransApplication sta1 = new StockTransApplication();
        sta1.setId(staId);
        staList.add(sta1);
        msg = JsonUtil.writeValue(staList);

        MessageCommond mc = new MessageCommond();
        mc.setMsgId(staId + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
        mc.setIsMsgBodySend(false);
        mc.setMsgType("com.jumbo.wms.manager.task.express.SetTransNoMQTaskImpl.setTransNoMQ");
        mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mc.setMsgBody(msg);

        try {
            // producerServer.sendDataMsgConcurrently(MqStaticEntity.WMS3_MQ_RTN_ORDER_CREATE,
            // cotp.getOwner(), mc);
            producerServer.sendDataMsgConcurrently(MQ_WMS3_GET_TRANSNO, sta.getMainWarehouse().getId() + "", mc);
        } catch (Exception e) {
            log.error("rmi Call get trans no request interface by MQ error:" + sta.getCode());
            throw new BusinessException(ErrorCode.MESSAGE_SEND_ERROR, sta.getCode());
        }
        // 保存进mongodb
        MongoDBMessage mdbm = new MongoDBMessage();
        BeanUtils.copyProperties(mc, mdbm);
        mdbm.setStaCode(sta.getCode());
        mdbm.setOtherUniqueKey(sta.getRefSlipCode());
        mdbm.setMsgBody(msg);
        mdbm.setMemo(MQ_WMS3_GET_TRANSNO);
        mongoOperation.save(mdbm);
        di.setMqGetTransNo(1);
        staDeliveryInfoDao.save(di);
    }


}
