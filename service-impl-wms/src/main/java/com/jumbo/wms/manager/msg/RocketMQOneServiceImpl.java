package com.jumbo.wms.manager.msg;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.bizhub.exception.BusinessException;
import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.msg.MessageConsumerDao;
import com.jumbo.dao.msg.MessageProducerErrorDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.CreateOrderToPacDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.dao.warehouse.QueueStaDeliveryInfoDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueStaLineDao;
import com.jumbo.dao.warehouse.QueueStaPaymentDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.rmi.warehouse.BaseResult;
import com.jumbo.rmi.warehouse.Order;
import com.jumbo.rmiservice.RmiService;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.webservice.nike.manager.NikeManager;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.inventorySnapshot.InventorySnapshotManagerProxy;
import com.jumbo.wms.manager.outbound.AdCheckManager;
import com.jumbo.wms.manager.outbound.OutboundInfoManager;
import com.jumbo.wms.manager.warehouse.CreatePickingListManagerProxy;
import com.jumbo.wms.manager.warehouse.CustomsDeclarationManager;
import com.jumbo.wms.manager.warehouse.QstaManager;
import com.jumbo.wms.manager.warehouse.QueueStaManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseOutBoundManager;
import com.jumbo.wms.model.CreatePickingListByMQ;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.hub2wms.WmsSalesOrder;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderResult;
import com.jumbo.wms.model.inventorySnapShot.ImInvWhFullInventory;
import com.jumbo.wms.model.msg.MessageConsumer;
import com.jumbo.wms.model.msg.MessageConsumerDto;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.msg.MongoDBMessageTest;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInHub;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("rocketMQOneService")
public class RocketMQOneServiceImpl {
    // AbstractMessageService

    protected static final Logger log = LoggerFactory.getLogger(RocketMQOneServiceImpl.class);

    @Autowired
    private RocketMQProducerServer producerServer;

    @Autowired
    HubWmsService hubWmsService;

    @Autowired
    MessageProducerErrorDao messageProducerErrorDao;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @Value("${mq.mq.wms3.sale.outbount}")
    public String MQ_WMS3_SALE_OUTBOUNT;


    @Value("${mq.mq.wms3.sales.order.service}")
    public String MQ_WMS3_SALES_ORDER_SERVICE;// 订单接受 直连 主题

    @Value("${mq.mq.wms3.sales.order.create}")
    public String MQ_WMS3_SALES_ORDER_CREATE;// 订单接受 非直连 主题

    @Value("${mq.mq.wms3.sales.order.service.return}")
    public String MQ_WMS3_SALES_ORDER_SERVICE_RETURN;// 直连创单反馈 主题

    @Value("${transaction.isEnable}")
    public Boolean TRANSACTIONISENABLE;//事物開關
    
    @Value("${transaction.topic}")
    public String TRANSACTIONISTOPIC;//事物開關TOPIC
    
    


    @Autowired
    RmiService rmiService;

    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;


    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;

    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private WareHouseManager wareHouseManager;

    @Autowired
    private QueueStaDao queueStaDao;

    @Autowired
    private SkuDao skuDao;

    @Autowired
    private OperationUnitDao operationUnitDao;

    @Autowired
    private QueueStaInvoiceDao queueStaInvoiceDao;

    @Autowired
    private QueueStaInvoiceLineDao queueStaInvoiceLineDao;

    @Autowired
    private QueueStaPaymentDao queueStaPaymentDao;
    @Autowired
    private QueueStaDeliveryInfoDao queueStaDeliveryInfoDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    private CreateOrderToPacDao createOrderToPacDao;
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    private QueueStaManagerExecute queueStaManagerExecute;
    @Autowired
    private MessageConsumerDao messageConsumerDao;

    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private AdCheckManager adCheckManager;

    @Autowired
    private OutboundInfoManager outboundInfoManager;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;

    @Autowired
    private CreatePickingListManagerProxy createPickingListManagerProxy;

    @Autowired
    private QstaManager qstaManager;
    @Autowired
    private InventorySnapshotManagerProxy inventorySnapshotManagerProxy;

    @Autowired
    private NikeManager nikeManager;
    @Autowired
    private CustomsDeclarationManager customsDeclarationManager;



    public void saveMessageConsumer(MessageCommond message) {
        MessageConsumer messageConsumer = new MessageConsumer();
        messageConsumer.setMsgId(message.getMsgId());
        messageConsumer.setMsgType(message.getMsgType());
        messageConsumer.setReceiveTime(new Date());
        messageConsumer.setDealTime(new Date());
        messageConsumerDao.save(messageConsumer);
        messageConsumerDao.flush();
    }

    public void updateMessageConsumerStatus(MessageCommond message) {// 0===>1
        if(isInsertMessageConsumer(message)){
        messageConsumerDao.updateMsgByMsgId(message.getMsgId(), message.getMsgType());
        }
    }
    

    public Boolean isInsertMessageConsumer(MessageCommond message) {//校驗 是否啟用自己的檢驗
          if(TRANSACTIONISENABLE && TRANSACTIONISTOPIC.contains(message.getTopic()) ){
              log.error(message.getMsgId()+";"+message.getTopic());
              return false;
          }
            return true;
    }
    

    public void dealWithMessageConsumer(MessageCommond message) {
        if(isInsertMessageConsumer(message)){
        MessageConsumerDto en = messageConsumerDao.findMsgByMsgIdByDto(message.getMsgId(), message.getMsgType(), new BeanPropertyRowMapper<MessageConsumerDto>(MessageConsumerDto.class));
        if (en == null) {
            MessageConsumer messageConsumer = new MessageConsumer();
            messageConsumer.setMsgId(message.getMsgId());
            messageConsumer.setMsgType(message.getMsgType());
            messageConsumer.setTopic(message.getTopic());
            messageConsumer.setReceiveTime(new Date());
            messageConsumer.setDealTime(new Date());
            messageConsumer.setStatus(0);
            messageConsumer.setBrand("1");
            messageConsumerDao.save(messageConsumer);
            messageConsumerDao.flush();
        } else {
            Date d = new Date();
            Calendar ca = Calendar.getInstance();
            ca.setTime(en.getReceiveTime());
            // ca.add(Calendar.HOUR_OF_DAY, 1);
            ca.add(Calendar.MINUTE, 20);
            Date d2 = ca.getTime();
            if (d.compareTo(d2) >= 1 && 0 == en.getStatus()) {// 删除中间表
                messageConsumerDao.delMsgByMsgId(message.getMsgId(), message.getMsgType());
                MessageConsumer messageConsumer = new MessageConsumer();
                messageConsumer.setMsgId(message.getMsgId());
                messageConsumer.setMsgType(message.getMsgType());
                messageConsumer.setTopic(message.getTopic());
                messageConsumer.setReceiveTime(new Date());
                messageConsumer.setDealTime(new Date());
                messageConsumer.setBrand("1");
                messageConsumer.setStatus(0);
                messageConsumerDao.save(messageConsumer);
                messageConsumerDao.flush();
            } else if (1 == en.getStatus()) {
                log.info("dealWithMessageConsumer_状态1:" + message.getMsgId() + ";" + message.getMsgType());
                throw new BusinessException(0);
            } else {// 抛异常
                throw new RuntimeException();
            }
        }
        }
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void autoOutBoundByAll(MessageCommond message) {
        try {
            dealWithMessageConsumer(message);
        } catch (Exception e) {
            log.info("autoOutBoundByAll_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        String msg = message.getMsgBody();
        try {
            wareHouseManager.autoOutBoundByAll(msg);
        } catch (Exception e) {
            log.warn("autoOutBoundByAll message :{}", msg, e);
        }
        try {
            updateMessageConsumerStatus(message);
        } catch (Exception e) {
            log.info("autoOutBoundByAll_重复:" + message.getMsgId() + ";" + message.getMsgType());
        }
    }


    /**
     * 直连订单接受（非ad）
     * 
     * @param message
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional
    public void excuteWmsSalesOrder(MessageCommond message) throws IllegalAccessException, InvocationTargetException {
        log.error("excuteWmsSalesOrder start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("createStaByIdOmsAndMq_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }

        WmsSalesOrderResult wmsSalesOrderResult = null;
        List<WmsSalesOrderResult> wmsSalesOrderResultList = null;
        String msg = message.getMsgBody();
        List<WmsSalesOrder> wmsSalesOrderList = JsonUtil.readList(msg, WmsSalesOrder.class);
        if (wmsSalesOrderList == null) {
            log.error("excuteWmsSalesOrderService wmsSalesOrderList is null:" + message.getMsgId());
        } else {
            for (WmsSalesOrder wmsSalesOrder : wmsSalesOrderList) {
                wmsSalesOrder.setSendTimeMq(message.getSendTime());
                wmsSalesOrderResult = hubWmsService.wmsSalesOrderService("toms", wmsSalesOrder);
                wmsSalesOrderResultList = new ArrayList<WmsSalesOrderResult>();
                wmsSalesOrderResultList.add(wmsSalesOrderResult);
                String reqJson = JsonUtil.writeValue(wmsSalesOrderResultList);
                MessageCommond mes = new MessageCommond();
                try {
                    mes.setMsgId(message.getMsgId() + ":" + UUIDUtil.getUUID());
                    mes.setIsMsgBodySend(false);
                    mes.setMsgType("com.jumbo.wms.manager.msg.excuteWmsSalesOrderService");
                    mes.setMsgBody(reqJson);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    mes.setSendTime(sdf.format(date));
                    // producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_SERVICE, mes);
                    producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_SERVICE, wmsSalesOrder.getOwner(), mes);
                    // 保存进mongodb
                    try {
                        MongoDBMessage mdbm = new MongoDBMessage();
                        BeanUtils.copyProperties(mes, mdbm);
                        mdbm.setStaCode(null);
                        mdbm.setOtherUniqueKey(wmsSalesOrder.getOrderCode());
                        mdbm.setMsgBody(reqJson);
                        mdbm.setMemo(MQ_WMS3_SALES_ORDER_SERVICE);
                        if(wmsSalesOrder.getOwner() !=null && wmsSalesOrder.getOwner().contains("IT后端测试")){
                            MongoDBMessageTest mdbmTest = new MongoDBMessageTest();
                            BeanUtils.copyProperties(mdbm, mdbmTest);
                            mongoOperation.save(mdbmTest);
                        }else{
                            mongoOperation.save(mdbm);
                        }
                    } catch (Exception e) {
                        log.error("excuteWmsSalesOrder_MONG_" + message.getMsgId());
                    }
                } catch (Exception e) {
                    log.info("excuteWmsSalesOrder_bu:" + message.getMsgId() + ";" + message.getMsgType());
                    throw new BusinessException(0);
                }
            }
            updateMessageConsumerStatus(message);

        }
        log.error("excuteWmsSalesOrderService end:" + message.getMsgId());
    }

    /**
     * 非直连订单接受
     * 
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional
    public void excuteOrderCreate(MessageCommond message) throws IllegalAccessException, InvocationTargetException {
        log.info("excuteOrderCreate start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("createStaByIdOmsAndMq_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }

        BaseResult baseResult = null;
        List<StaCreatedResponse> staCreatedResponseList = null;
        StaCreatedResponse staCreatedResponse = null;
        String msg = message.getMsgBody();
        List<Order> orderList = JsonUtil.readList(msg, Order.class);
        if (orderList == null) {
            log.info("excuteOrderCreate orderList is null:" + message.getMsgId());
        } else {
            for (Order order : orderList) {
                order.setSendTimeMq(message.getSendTime());
                baseResult = rmiService.orderCreate(order);
                staCreatedResponse = new StaCreatedResponse();
                staCreatedResponseList = new ArrayList<StaCreatedResponse>();
                staCreatedResponse.setStatus(baseResult.getStatus());
                staCreatedResponse.setMsg(baseResult.getMsg());
                staCreatedResponse.setSlipCode(baseResult.getCode());
                staCreatedResponse.setType(baseResult.getType());
                staCreatedResponseList.add(staCreatedResponse);

                String reqJson = JsonUtil.writeValue(staCreatedResponseList);
                MessageCommond mes = new MessageCommond();
                try {
                    mes.setMsgId(message.getMsgId() + ":" + UUIDUtil.getUUID());
                    mes.setIsMsgBodySend(false);
                    mes.setMsgType("com.jumbo.wms.manager.msg.excuteOrderCreate");
                    mes.setMsgBody(reqJson);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    mes.setSendTime(sdf.format(date));
                    // producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_CREATE, mes);
                    producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_CREATE, order.getOwner(), mes);
                    // 保存进mongodb
                    try {
                        MongoDBMessage mdbm = new MongoDBMessage();
                        BeanUtils.copyProperties(mes, mdbm);
                        mdbm.setStaCode(null);
                        mdbm.setOtherUniqueKey(order.getCode());
                        mdbm.setMsgBody(reqJson);
                        mdbm.setMemo(MQ_WMS3_SALES_ORDER_CREATE);
                        if(order.getOwner() !=null && order.getOwner().contains("IT后端测试")){
                            MongoDBMessageTest mdbmTest = new MongoDBMessageTest();
                            BeanUtils.copyProperties(mdbm, mdbmTest);
                            mongoOperation.save(mdbmTest);
                        }else{
                            mongoOperation.save(mdbm);
                        }
                    } catch (Exception e) {
                        log.error("excuteOrderCreate_MONG_" + message.getMsgId());
                    }

                } catch (Exception e) {
                    log.info("excuteOrderCreate_bu:" + message.getMsgId() + ";" + message.getMsgType());
                    throw new BusinessException(0);
                }
            }
            updateMessageConsumerStatus(message);
        }
        log.info("excuteOrderCreate end:" + message.getMsgId());
    }

    /**
     * 出库反馈 越海 WLB 2018
     * 
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void excuteRtnOutbountYh(MessageCommond message) throws IllegalAccessException, InvocationTargetException {
        log.info("excuteRtnOutbountYh start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("createStaByIdOmsAndMq_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        
        String msg = message.getMsgBody();
        MsgRtnOutbound order = JsonUtil.readValue(msg, MsgRtnOutbound.class);
        try {
            wareHouseManagerProxy.callVmiSalesStaOutBound(order.getId());
        } catch (BusinessException e) {
            log.error("excuteRtnOutbountYh_error1" + e);
            log.error(".......excuteRtnOutbountYh Outbound1 execute failed........" + order.getStaCode());
            log.error("excuteRtnOutbountYh error ! OUT STA :" + e.getErrorCode());
            wareHouseManager.updateMsgRtnOutboundMq(order.getId(), 0);
        } catch (Exception e) {
            log.error("excuteRtnOutbountYh_error2" + e);
            log.info("=========excuteRtnOutbountYh ERROR===========" + order.getStaCode());
            wareHouseManager.updateMsgRtnOutboundMq(order.getId(), 0);
        }
        log.error("excuteRtnOutbountYh end:" + message.getMsgId());
       try {
            updateMessageConsumerStatus(message);
        } catch (Exception e) {
            log.info("autoOutBoundByAll_重复:" + message.getMsgId() + ";" + message.getMsgType());
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void autoOutbound(MessageCommond message) {
        log.info("autoOutbound start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("createStaByIdOmsAndMq_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        String msg = message.getMsgBody();
        Long id = null;
        try {
            try {
                JSONArray json = JSONArray.fromObject(msg);
                if (null != json && json.size() > 0) {
                    JSONObject j = (JSONObject) json.get(0);
                    id = j.getLong("id");
                }

            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("autoOutbound exception", e);
                }
            }
            StockTransApplication sta = staDao.getByPrimaryKey(id);
            if (null != sta && !StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus()) && !StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {
                wareHouseManager.salesCreatePage(sta.getMainWarehouse().getId(), new Long(21), sta.getCode(), new BigDecimal(1), sta == null ? null : sta.getId(), "YTO", null, "");
            }
        } catch (Exception e) {
            log.warn("autoOutbound,message :{}", msg, e);
        }
        try {
            updateMessageConsumerStatus(message);
        } catch (Exception e) {
            log.info("autoOutBoundByAll_重复:" + message.getMsgId() + ";" + message.getMsgType());
        }

    }


    /**
     * 出库反馈 LF
     * 
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void excuteRtnOutbountLf(MessageCommond message) throws IllegalAccessException, InvocationTargetException {
        log.info("excuteRtnOutbountLf start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("createStaByIdOmsAndMq_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }

        String msg = message.getMsgBody();
        MsgRtnOutbound msgRtnbound = JsonUtil.readValue(msg, MsgRtnOutbound.class);
        StockTransApplication sta = staDao.findStaByCode(msgRtnbound.getStaCode());
        try {
            if (sta != null) {
                if (sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                    wareHouseManagerProxy.callVmiSalesStaOutBound(msgRtnbound.getId());
                } else if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                    wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.FINISHED.getValue());
                }
            } else {
                wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.CANCELED.getValue());
            }
        } catch (BusinessException e) {
            log.error(".......excuteRtnOutbountLf Outbound1 execute failed........" + msgRtnbound.getStaCode());
            // log.error("excuteRtnOutbountLf_error1" + e);
            wareHouseManager.updateMsgRtnOutboundMq(msgRtnbound.getId(), 0);
        } catch (Exception ex) {
            wareHouseManager.updateMsgRtnOutboundMq(msgRtnbound.getId(), 0);
            log.info("=========excuteRtnOutbountLf ERROR===========" + msgRtnbound.getStaCode());
            log.error("excuteRtnOutbountLf_error2" + ex);
        }
        try {
            updateMessageConsumerStatus(message);
        } catch (Exception e) {
            log.info("autoOutBoundByAll_重复:" + message.getMsgId() + ";" + message.getMsgType());
        }
        log.info("excuteRtnOutbountLf end:" + message.getMsgId());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createStaByIdPacAndMq(MessageCommond message) {
        log.info("createStaByIdPacAndMq start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("createStaByIdOmsAndMq_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        String msg = message.getMsgBody();
        Long id = null;
        try {
            JSONArray json = JSONArray.fromObject(msg);
            if (null != json && json.size() > 0) {
                JSONObject j = (JSONObject) json.get(0);
                id = j.getLong("id");
            }

        } catch (Exception e) {
            log.error("createStaByIdPacAndMqBegin" + id);
        }
        if (log.isDebugEnabled()) {
            log.debug("createStaByIdPacAndMqBegin" + id);
        }
        try {
            try {
                String mpMsg = createStaTaskManager.createStaByIdPac(id);
                if (!StringUtil.isEmpty(mpMsg)) {
                    try {
                        createStaTaskManager.sendCreateOrderMQToPac(mpMsg);
                        // createStaTaskManager.sendCreateOrderMQToPac(mpMsg + "_qstaId_" + id);
                    } catch (Exception e) {
                        createStaTaskManager.updateCreateOrderMQToPacStatus(mpMsg);
                        Log.info("sendCreateOrderMQToPac  " + mpMsg, e);
                    }
                }
            } catch (Exception e) {
                log.error("createStaByIdPac----", e);
                // 增加错误次数
                if (log.isDebugEnabled()) {
                    log.info("qstaId:" + id, e);
                }
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                        // 删除数据，记录日志
                        if (log.isDebugEnabled()) {
                            log.info("qstaId:{}, pac order cancel, delete queue and log queue", id);
                        }
                        queueStaManagerExecute.removeQstaAddLog(id);
                    } else {
                        if (log.isErrorEnabled()) {
                            log.error("createStaByIdPacAndMq:" + id, e);
                        }
                        // 增加错误次数
                        if (log.isDebugEnabled()) {
                            log.info("qstaId:{},create sta throw exception, add error count 1 for qsta,qsta order code is:{}", id);
                        }
                        queueStaManagerExecute.addErrorCountForQsta(id, 1);
                    }
                } else {
                    if (log.isErrorEnabled()) {
                        log.error("createStaByIdPacAndMq:" + id, e);
                    }
                    // 增加错误次数
                    if (log.isDebugEnabled()) {
                        log.info("qstaId:{},create sta throw exception, add error count 1 for qsta,qsta order code is:{}", id);
                    }
                    queueStaManagerExecute.addErrorCountForQsta(id, 1);
                }
            }
        } catch (Exception e) {
            log.warn("createStaByIdPacAndMq ,message : {}", message.getMsgId(), e);
        }

        try {
            updateMessageConsumerStatus(message);
        } catch (Exception e) {
            log.info("autoOutBoundByAll_重复:" + message.getMsgId() + ";" + message.getMsgType());
        }
        log.info("createStaByIdPacAndMqEnd end:" + message.getMsgId());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createStaByIdOmsAndMq(MessageCommond message) {
        log.error("createStaByIdOmsAndMq start:" + message.getMsgId());
        log.error("createStaByIdOmsAndMq:"+message.getMsgId()+";"+message.getTopic());
        log.error("createStaByIdOmsAndMq3:" +TRANSACTIONISENABLE+";"+TRANSACTIONISTOPIC);
   /*     try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("createStaByIdOmsAndMq_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
*/
        String msg = message.getMsgBody();
        Long id = null;
        try {
            JSONArray json = JSONArray.fromObject(msg);
            if (null != json && json.size() > 0) {
                JSONObject j = (JSONObject) json.get(0);
                id = j.getLong("id");
            }

        } catch (Exception e) {
            log.error("createStaByIdOmsAndMq_", e);
        }
        /*try {
            if (id != null) {
                createStaTaskManager.createStaById(id);
            }
            updateMessageConsumerStatus(message);
        } catch (Exception e) {
            log.error("createStaByIdOmsAndM1q" + id, e);
        }*/
        log.error("createStaByIdOmsAndMq end:" + message.getMsgId());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void excuteOrderCreateResponse(MessageCommond message) {
        log.info("excuteOrderCreateResponse start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("excuteOrderCreateResponse_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }

        String msg = "";
        try {
            if (message != null) {
                msg = message.getMsgBody();
                queueStaManagerExecute.excuteOrderCreateResponse(msg);
            }
        } catch (BusinessException e) {
            log.warn("excuteOrderCreateResponse BusinessException " + msg + " errorCode" + e.getErrorCode());
        } catch (Exception e) {
            log.error("excuteOrderCreateResponse Exception " + msg, e);
            // throw new BusinessException(0);
        }

        try {
            updateMessageConsumerStatus(message);
        } catch (Exception e) {
            log.info("excuteOrderCreateResponse_重复:" + message.getMsgId() + ";" + message.getMsgType());
        }
        log.info("excuteOrderCreateResponse end:" + message.getMsgId());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void excuteSetTransNoMQ(MessageCommond message) {
        log.info("excuteSetTransNoMQ start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("excuteSetTransNoMQ_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        Long staId = null;
        String msg = message.getMsgBody();
        JSONArray json = JSONArray.fromObject(msg);
        try {
            if (null != json && json.size() > 0) {
                JSONObject j = (JSONObject) json.get(0);
                staId = j.getLong("id");
            }
        } catch (Exception e) {
            log.warn("excuteSetTransNoMQ,sta id is null ,msg id : {}", message.getMsgId());
        }
        if (staId != null) {
            try {
                try {
                    transOlManager.matchingTransNoMqByStaId(staId);
                } catch (BusinessException e) {
                    wareHouseManagerExecute.msgUnLockedError(staId);
                    log.error("mq get trans no BusinessException staId:" + staId + " ; errorCode " + e.getErrorCode());
                } catch (Exception e) {
                    wareHouseManagerExecute.msgUnLockedError(staId);
                    log.error("mq get trans no error staId:" + staId, e);
                }
                transOlManager.resetMqGetTransNoFlag(staId);
            } catch (Exception e) {
                log.error("mq get trans no error staId:" + staId, e);
                throw new BusinessException(0);
            }
        }

        try {
            updateMessageConsumerStatus(message);
        } catch (Exception e) {
            log.info("excuteSetTransNoMQ_重复:" + message.getMsgId() + ";" + message.getMsgType());
        }
        log.info("excuteSetTransNoMQ end:" + message.getMsgId());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createPickingListByMQ(MessageCommond message) {
        log.info("createPickingListByMQ start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("createStaByIdOmsAndMq_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        try {
            String msg = message.getMsgBody();
            List<CreatePickingListByMQ> createPickingListByMQ = JsonUtil.readList(msg, CreatePickingListByMQ.class);
            if (null != createPickingListByMQ && createPickingListByMQ.size() > 0) {
                String loc = createPickingListByMQ.get(0).getLoc();
                if (null != loc && "" != loc) {
                    List<Long> staIdList = new ArrayList<Long>();
                    List<InventoryCommand> invlist = warehouseOutBoundManager.findStaIdByLoc(createPickingListByMQ.get(0).getOuId(), Long.parseLong(loc));
                    if (null != invlist && invlist.size() > 0) {
                        for (InventoryCommand inv : invlist) {
                            staIdList.add(inv.getStaId());
                        }
                    }
                    if (null != staIdList && staIdList.size() > 0) {
                        Long msgId =
                                createPickingListManagerProxy.createPickingListBySta(null, staIdList, createPickingListByMQ.get(0).getOuId(), createPickingListByMQ.get(0).getUserId(), PickingListCheckMode.PICKING_CHECK, null, null, null, null, null,
                                        null, null, null, null, false, true, Long.parseLong(loc), createPickingListByMQ.get(0).getIsOtoPicking());

                        if (msgId != null) {
                            qstaManager.createPickingListToWCS(msgId);
                        }
                    }
                } else {
                    Long msgId =
                            createPickingListManagerProxy.createPickingListBySta(createPickingListByMQ.get(0).getSkusIdAndQty(), createPickingListByMQ.get(0).getStaIdList(), createPickingListByMQ.get(0).getOuId(), createPickingListByMQ.get(0).getId(),
                                    createPickingListByMQ.get(0).getCheckMode(), createPickingListByMQ.get(0).getIsSp(), createPickingListByMQ.get(0).getIsSn(), createPickingListByMQ.get(0).getCategoryId(), createPickingListByMQ.get(0).getCityList(),
                                    createPickingListByMQ.get(0).getIsTransAfter(), createPickingListByMQ.get(0).getSsList(), createPickingListByMQ.get(0).getIsCod(), createPickingListByMQ.get(0).getIsQs(), createPickingListByMQ.get(0).getIsOtwoo(),
                                    createPickingListByMQ.get(0).getB(), false, null, createPickingListByMQ.get(0).getIsOtoPicking());
                    if (msgId != null) {
                        qstaManager.createPickingListToWCS(msgId);
                    }

                }
            }
        } catch (Exception e) {
            log.error("createPickingListByMQ1_msgId:" + message.getMsgId(), e);
        }
        log.info("createPickingListByMQ end:" + message.getMsgId());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void excuteOccupyInventory(MessageCommond message) {
        log.info("excuteOccupyInventory start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("excuteOccupyInventoryMQ_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        String staId = JsonUtil.readValue(message.getMsgBody(), String.class);
        try {
            wareHouseManagerProxy.newOccupiedInventoryBySta(Long.parseLong(staId), null, null);
        } catch (BusinessException e) {
            log.error("excuteOccupyInventory.newOccupiedInventoryBySta error staid:" + staId, e.getErrorCode());
        } catch (Exception e) {
            log.error("excuteOccupyInventory.newOccupiedInventoryBySta error staid:" + staId, e);
        }
        if (log.isInfoEnabled()) {
            log.info("excuteOccupyInventory end..........");
        }
        updateMessageConsumerStatus(message);
        log.info("excuteOccupyInventory end:" + message.getMsgId());

    }

    /**
     * nike收货
     * 
     * @param message
     */

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void nikeInboundHub(MessageCommond message) {
        log.info("nikeInboundHub start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("nikeInboundHub_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        List<NikeVmiStockInHub> list = JsonUtil.readList(message.getMsgBody(), NikeVmiStockInHub.class);
        try {
            nikeManager.nikeInboundHub(list);
        } catch (BusinessException e) {
            log.info("nikeInboundHub_1:" + message.getMsgId(), e);
        } catch (Exception e) {
            log.info("nikeInboundHub_2:" + message.getMsgId(), e);
        }
        updateMessageConsumerStatus(message);
        log.info("nikeInboundHub end:" + message.getMsgId());
        }

    /**
     * 监听IM需要全量信息 生成对应全量库存 bin.hu
     * 
     * @param message
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void inventorySnapShotByIm(MessageCommond message) {
        log.info("inventorySnapShotByIm start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("inventorySnapShotByImMQ_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }
        try {
            ImInvWhFullInventory m = JsonUtil.readValue(message.getMsgBody(), ImInvWhFullInventory.class);
            inventorySnapshotManagerProxy.InventorySnapshotToIm(m.getOwnerCode(), null, null);
        } catch (Exception e) {
            log.error("inventorySnapShotByIm.InventorySnapshotToIm error message.getMsgId():" + message.getMsgId(), e);
        }
        if (log.isInfoEnabled()) {
            log.info("inventorySnapShotByIm end..........");
        }
        updateMessageConsumerStatus(message);
        log.info("inventorySnapShotByIm end:" + message.getMsgId());
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void nikeQianHaiSkuInfo(MessageCommond message) {
        log.info("nikeQianHaiSkuInfo start:" + message.getMsgId());
        try {
            dealWithMessageConsumer(message);
        } catch (BusinessException e) {
            return;
        } catch (Exception e) {
            log.info("nikeQianHaiSkuInfo_重复:" + message.getMsgId() + ";" + message.getMsgType());
            throw new BusinessException(0);
        }

        try {
            customsDeclarationManager.newSkuDeclaration(message.getMsgBody());
        } catch (BusinessException e) {
            log.error("nikeQianHaiSkuInfo_0 MsgBody:" + message.getMsgId(), message.getMsgBody());
            log.error("nikeQianHaiSkuInfo_1:" + message.getMsgId(), e);
        } catch (Exception e) {
            log.error("nikeQianHaiSkuInfo_2:" + message.getMsgId(), e);
            log.error("nikeQianHaiSkuInfo_4 MsgBody:" + message.getMsgId(), message.getMsgBody());
        }
        updateMessageConsumerStatus(message);
        log.info("nikeQianHaiSkuInfo end:" + message.getMsgId());
    }
}
