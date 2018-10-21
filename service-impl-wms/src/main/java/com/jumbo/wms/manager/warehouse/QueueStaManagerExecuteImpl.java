package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.warehouse.CreateOrderToPacDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.QueueGiftLineDao;
import com.jumbo.dao.warehouse.QueueLogGiftLineDao;
import com.jumbo.dao.warehouse.QueueLogStaDao;
import com.jumbo.dao.warehouse.QueueLogStaDeliveryInfoDao;
import com.jumbo.dao.warehouse.QueueLogStaInvoiceDao;
import com.jumbo.dao.warehouse.QueueLogStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueLogStaLineDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.dao.warehouse.QueueStaDeliveryInfoDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueStaLineDao;
import com.jumbo.dao.warehouse.QueueStaLineOwnerDao;
import com.jumbo.dao.warehouse.QueueStaLineOwnerLogDao;
import com.jumbo.dao.warehouse.QueueStaLineStockoutDao;
import com.jumbo.dao.warehouse.QueueStaLineStockoutLogDao;
import com.jumbo.dao.warehouse.QueueStaPaymentDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhOrderSpecialExecuteDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SoLineResponse;
import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.model.QueueStaLineOwnerLog;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.warehouse.CreateOrderToPac;
import com.jumbo.wms.model.warehouse.CreateOrderToPacResponse;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.QueueGiftLine;
import com.jumbo.wms.model.warehouse.QueueLogGiftLine;
import com.jumbo.wms.model.warehouse.QueueLogSta;
import com.jumbo.wms.model.warehouse.QueueLogStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueLogStaInvoice;
import com.jumbo.wms.model.warehouse.QueueLogStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueLogStaLine;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaCommand;
import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueStaInvoice;
import com.jumbo.wms.model.warehouse.QueueStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineCommand;
import com.jumbo.wms.model.warehouse.QueueStaLineOwner;
import com.jumbo.wms.model.warehouse.QueueStaLineStatus;
import com.jumbo.wms.model.warehouse.QueueStaLineStockout;
import com.jumbo.wms.model.warehouse.QueueStaLineStockoutLog;
import com.jumbo.wms.model.warehouse.QueueStaPayment;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WhOrderSpecialExecute;

import loxia.dao.Pagination;
import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * 过仓
 * 
 * @author cheng.su
 * 
 */
@Transactional
@Service("queueStaManagerExecute")
public class QueueStaManagerExecuteImpl implements QueueStaManagerExecute {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3883206111788908000L;
    protected static final Logger log = LoggerFactory.getLogger(QueueStaManagerExecuteImpl.class);

    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private CreateStaManager createStaManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private QueueLogStaDao queueLogStaDao;
    @Autowired
    private QueueStaPaymentDao queueStaPaymentDao;
    @Autowired
    private QueueLogStaDeliveryInfoDao queueLogStaDeliveryInfoDao;
    @Autowired
    private QueueLogStaLineDao queueLogStaLineDao;
    @Autowired
    private QueueStaDeliveryInfoDao queueStaDeliveryInfoDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private EventObserver eventObserver;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private QueueGiftLineDao queueGiftLineDao;
    @Autowired
    private QueueLogGiftLineDao logGiftLineDao;
    @Autowired
    private QueueStaInvoiceDao queueStaInvoiceDao;
    @Autowired
    private QueueLogStaInvoiceDao queueLogStaInvoiceDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private QueueStaInvoiceLineDao queueStaInvoiceLineDao;
    @Autowired
    private QueueLogStaInvoiceLineDao queueLogStaInvoiceLineDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private WhOrderSpecialExecuteDao whOrderSpecialExecuteDao;
    @Autowired
    private QueueStaLineStockoutDao lineStockoutDao;
    @Autowired
    QueueStaLineStockoutLogDao lineStockoutLogDao;
    @Autowired
    QueueStaLineOwnerDao queueStaLineOwnerDao;
    @Autowired
    QueueStaLineOwnerLogDao lineOwnerLogDao;
    @Autowired
    private CreateOrderToPacDao createOrderToPacDao;
    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private RocketMQProducerServer producerServer;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    /**
     * 增加队列错误次数
     * 
     * @param qStaId
     * @param addCount
     */
    public void addErrorCountForQsta(Long qStaId, int addCount) {
        if (log.isInfoEnabled()) {
            log.info("addErrorCountForQsta start, qstaId:{}", qStaId);
        }
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qStaId);
        queueSta.setErrorcount(queueSta.getErrorcount() == null ? 0 : queueSta.getErrorcount() + addCount);
        queueStaDao.save(queueSta);
        queueStaDao.flush();
        if (log.isInfoEnabled()) {
            log.info("addErrorCountForQsta end, qstaId:{}", qStaId);
        }
    }

    @Override
    public void createsta(Long qStaId) {
        if (log.isInfoEnabled()) {
            log.info("createsta and sendCreateResultToOms start, qstaId:{}", qStaId);
        }
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qStaId);
        QueueStaDeliveryInfo deliveryInfo = queueStaDeliveryInfoDao.getByPrimaryKey(qStaId);
        List<QueueStaLine> staLines = queueStaLineDao.queryStaId(qStaId, new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
        List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(qStaId, new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
        if (log.isDebugEnabled()) {
            log.debug("create sta, qstaId:{}, type:{}", qStaId, queueSta.getType());
        }
        if (StockTransApplicationType.OUTBOUND_SALES.getValue() == queueSta.getType()) {
            // 销售出库创建
            createSta(queueSta, deliveryInfo, staLines, invoices);
        } else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.getValue() == queueSta.getType()) {
            // 入库通用校验和创单:
            // 退货入库
            createSingleInboundSta(queueSta, deliveryInfo, staLines);
        } else if (StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue() == queueSta.getType()) {
            // 换货入库
            // 换货出
            // 退货入
            createSingleInboundSta(queueSta, deliveryInfo, staLines);
            // 换货出库
            createSta(queueSta, deliveryInfo, staLines, null);
        }

        // ===============生成OMS数据========================
        StaCreatedResponse createdResponse = new StaCreatedResponse();
        if (queueSta.getType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
            createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
        } else {
            createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_RA);
        }
        createdResponse.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_SUCCESS);
        createdResponse.setSlipCode(queueSta.getOrdercode());
        // 反馈OMS成功
        sendCreateResultToOms(createdResponse, qStaId);
        if (log.isInfoEnabled()) {
            log.info("createsta and sendCreateResultToOms end, qstaId:{}", qStaId);
        }
    }

    @Override
    public void createsta(Long qStaId, List<QueueStaLineCommand> lineCommand) {
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qStaId);
        QueueStaDeliveryInfo deliveryInfo = queueStaDeliveryInfoDao.getByPrimaryKey(qStaId);
        List<QueueStaLine> staLines = queueStaLineDao.queryStaId(qStaId, new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
        List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(qStaId, new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
        if (StockTransApplicationType.OUTBOUND_SALES.getValue() == queueSta.getType()) {
            // 销售出库创建
            createStaManager.createStaForSalesAF(queueSta, deliveryInfo, lineCommand, invoices, staLines);
        } else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.getValue() == queueSta.getType()) {
            // 入库通用校验和创单:
            // 退货入库
            createSingleInboundSta(queueSta, deliveryInfo, staLines);
        } else if (StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue() == queueSta.getType()) {
            // 换货入库

            createSingleInboundSta(queueSta, deliveryInfo, staLines);
            // 换货出库
            createStaManager.createStaForSalesAF(queueSta, deliveryInfo, lineCommand, null, staLines);
        }

        // ===============生成OMS数据========================
        StaCreatedResponse createdResponse = new StaCreatedResponse();
        if (queueSta.getType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
            createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
        } else {
            createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_RA);
        }
        createdResponse.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_SUCCESS);
        List<SoLineResponse> shortageDetails = new ArrayList<SoLineResponse>();
        for (QueueStaLineCommand l : lineCommand) {
            if (l.getLineStatus() != QueueStaLineStatus.LINE_STATUS_FALSE) {
                SoLineResponse detial = new SoLineResponse();
                detial.setJmSkuCode(l.getSkucode());
                detial.setQty(l.getQty());
                shortageDetails.add(detial);
            }
        }

        createdResponse.setSoLineResponses(shortageDetails);
        createdResponse.setSlipCode(queueSta.getOrdercode());
        // 反馈OMS成功
        createdResponse.setMsg(null);
        sendCreateResultToOms(createdResponse, qStaId);
    }

    /**
     * 通用入库作业单创建
     * 
     * @param order
     */
    private void createSingleInboundSta(QueueSta qsta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLine> staLines) {
        if (log.isDebugEnabled()) {
            log.debug("create return inbound sta start, qstaId:{}, sta type:{}", qsta.getId(), 41);
        }
        StockTransApplication sta = new StockTransApplication();
        List<QueueStaLine> line = queueStaLineDao.findByStaIdIn(qsta.getId());
        TransactionType t = null;
        OperationUnit ou1 = null;
        if (qsta.getType() == 42) {
            sta.setType(StockTransApplicationType.valueOf(41));

            ou1 = operationUnitDao.getByPrimaryKey(qsta.getAddwhouid());
        } else {
            ou1 = operationUnitDao.getByPrimaryKey(qsta.getMainwhouid());
            sta.setType(StockTransApplicationType.valueOf(qsta.getType()));
        }
        t = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (t != null) {
            sta.setRefSlipCode(qsta.getOrdercode());
            sta.setSlipCode1(qsta.getSlipcode1());
            sta.setSlipCode2(qsta.getSlipcode2());
            sta.setSlipCode3(qsta.getSlipCode3());
            sta.setIsLocked(qsta.getIscreatedlocked());
            sta.setRefSlipType(SlipType.valueOf(3));
            sta.setStorecode(qsta.getStorecode());
            sta.setMainWarehouse(ou1);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1 == null ? null : ou1.getId());
            sta.setIsNeedOccupied(false);
            sta.setOwner(qsta.getOwner());
            sta.setAddiOwner(qsta.getAddOwner());
            sta.setTotalActual(qsta.getTotalactual());
            sta.setOrderTotalActual(qsta.getOrdertaotalactual());
            sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
            // sta.setMemo(qsta.getMemo());
            sta.setIsLocked(true);
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            staDao.save(sta);
            if (deliveryInfo != null) {
                StaDeliveryInfo di = new StaDeliveryInfo();
                di.setId(sta.getId());
                di.setReceiver(deliveryInfo.getSender());
                if (qsta.getType() == 42 || qsta.getType() == 41) {
                    di.setLpCode(deliveryInfo.getSendLpcode());
                    if ("SFCOD".equals(deliveryInfo.getSendLpcode())) {
                        di.setLpCode("SF");
                    }
                    if ("EMSCOD".equals(deliveryInfo.getSendLpcode())) {
                        di.setLpCode("EMS");
                    }
                    if ("JDCOD".equals(deliveryInfo.getSendLpcode())) {
                        di.setLpCode("JD");
                    }

                } else {

                    di.setLpCode(deliveryInfo.getLpcode());
                    if ("SFCOD".equals(deliveryInfo.getLpcode())) {
                        di.setLpCode("SF");
                    }
                    if ("EMSCOD".equals(deliveryInfo.getLpcode())) {
                        di.setLpCode("EMS");
                    }
                    if ("JDCOD".equals(deliveryInfo.getLpcode())) {
                        di.setLpCode("JD");
                    }
                }
                di.setMobile(deliveryInfo.getSendMobile());
                di.setTrackingNo(deliveryInfo.getSendTransNo());
                di.setInsuranceAmount(deliveryInfo.getInsuranceAmount());
                di.setRemarkEn(deliveryInfo.getRemarkEn());// PAC退换货备注
                di.setRemark(deliveryInfo.getRemark());// PAC退换货备注
                di.setLastModifyTime(new Date());
                staDeliveryInfoDao.save(di);
                sta.setStaDeliveryInfo(di);

            }
            for (QueueStaLine line1 : line) {
                if (line1.getDirection() == TransactionDirection.INBOUND.getValue()) {
                    Sku sku = skuDao.getByCode(line1.getSkucode());
                    if (sku.getSnType() != null) {
                        if (sku.getSnType() == SkuSnType.NO_BARCODE_SKU) {
                            sta.setIsBkCheck(true);
                        }
                    }
                    StaLine sl = new StaLine();
                    sl.setSta(sta);
                    sl.setSku(sku);
                    sl.setOwner(sta.getOwner());
                    sl.setQuantity(line1.getQty());
                    sl.setCompleteQuantity(0L);
                    sl.setUnitPrice(line1.getUnitprice());
                    sl.setTotalActual(line1.getTotalactual());
                    sl.setOrderTotalActual(line1.getOrdertotalactual());
                    sl.setOrderTotalBfDiscount(line1.getOrdertotalbfdiscount());
                    sl.setListPrice(line1.getListprice());
                    InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(line1.getInvstatusid(), ou1.getParentUnit().getParentUnit().getId());
                    sl.setInvStatus(invStatus);
                    staLineDao.save(sl);
                }
            }
            staDao.flush();
            staLineDao.flush();
            staDao.updateSkuQtyById(sta.getId());
            staDao.updateIsSnSta(sta.getId());
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
        } else {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {StockTransApplicationType.valueOf(qsta.getType())});
        }
        if (log.isDebugEnabled()) {
            log.debug("create return inbound sta end, qstaId:{}, sta type:{}", qsta.getId(), 41);
        }
    }

    private void createLogSta(Long qStaId, StaCreatedResponse error) {
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qStaId);
        QueueStaDeliveryInfo deliveryInfo = queueStaDeliveryInfoDao.getByPrimaryKey(qStaId);
        List<QueueStaLine> staLines = queueStaLineDao.queryStaId(qStaId, new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
        List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(qStaId, new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
        QueueLogSta logsta = new QueueLogSta();
        logsta.setOrdercode(queueSta.getOrdercode());
        logsta.setSlipcode1(queueSta.getSlipcode1());
        logsta.setSlipcode2(queueSta.getSlipcode2());
        logsta.setSlipCode3(queueSta.getSlipCode3());
        logsta.setMainwhouid(queueSta.getMainwhouid());
        logsta.setQstaId(queueSta.getId());
        logsta.setAddwhouid(queueSta.getAddwhouid() == null ? 0 : queueSta.getAddwhouid());
        logsta.setIscreatedlocked(queueSta.getIscreatedlocked());
        logsta.setOwner(queueSta.getOwner());
        logsta.setAddOwner(queueSta.getAddOwner());
        logsta.setBatchCode(queueSta.getBatchcode());
        logsta.setType(queueSta.getType());
        logsta.setStorecode(queueSta.getStorecode());
        logsta.setCreatetime(queueSta.getCreatetime());
        logsta.setMkPosCode(queueSta.getMkPosCode());
        logsta.setArriveTime(queueSta.getArriveTime());
        logsta.setLogtime(new Date());
        logsta.setArriveTimeType(queueSta.getArriveTimeType());
        // logsta.setInvstatusid(queueSta.getInvstatusid());
        // logsta.setInvaddstatusid(queueSta.getInvaddstatusid());
        logsta.setIsspecialpackaging(queueSta.getIsspecialpackaging());
        logsta.setTotalactual(queueSta.getTotalactual());
        logsta.setOrdertaotalactual(queueSta.getOrdertaotalactual());
        logsta.setTransferfee(queueSta.getTransferfee());
        logsta.setOrdertransferfree(queueSta.getOrdertransferfree());
        logsta.setOrdertotalbfdiscount(queueSta.getOrdertotalbfdiscount());
        logsta.setOrdercreatetime(queueSta.getOrdercreatetime());
        logsta.setIsInvoice(queueSta.getIsInvoice());
        logsta.setChannelList(queueSta.getChannelList());
        logsta.setCustomerId(queueSta.getCustomerId());
        logsta.setCodAmount(queueSta.getCodAmount());
        logsta.setMainWhOuCode(queueSta.getMainWhOuCode());
        logsta.setAddWhOuCode(queueSta.getAddWhOuCode());
        logsta.setSpecialType(queueSta.getSpecialType());
        logsta.setToLocation(queueSta.getToLocation());
        logsta.setPlanArriveTime(queueSta.getPlanArriveTime());
        logsta.setPlanOutboundTime(queueSta.getPlanOutboundTime());
        logsta.setIsPreSale(queueSta.getIsPreSale());// 是否预售
        logsta.setIsOnlineInvoice(queueSta.getIsOnlineInvoice());
		logsta.setIsMacaoOrder(queueSta.getIsMacaoOrder());// 是否澳门件
        logsta.setExtMemo2(queueSta.getExtMemo2());
        if (queueSta.getIsTransUpgrade() != null && queueSta.getIsTransUpgrade()) {
            logsta.setTransUpgrade(true);
        } else {
            logsta.setTransUpgrade(false);
        }
        if (error != null) {
            if (StringUtils.hasText(error.getMsg())) {
                if (error != null && error.getSoLineResponses() != null) {
                    for (int i = 0; i < error.getSoLineResponses().size(); i++) {
                        if (logsta.getErrormsg() == null) {
                            logsta.setErrormsg("");
                        }
                        logsta.setErrormsg(logsta.getErrormsg() + "[" + error.getSoLineResponses().get(i).getJmSkuCode() + "缺少" + error.getSoLineResponses().get(i).getQty() + "]");
                    }

                }
                if (logsta.getErrormsg().length() > 500) {
                    logsta.setErrormsg(logsta.getErrormsg().substring(0, 400));
                }
            }
        } else {
            logsta.setErrormsg("OMS取消订单");
        }

        logsta.setStatus(1);
        logsta.setActivitysource(queueSta.getActivitysource());
        queueLogStaDao.save(logsta);
        queueLogStaDao.flush();
        if (deliveryInfo != null) {
            QueueLogStaDeliveryInfo di = new QueueLogStaDeliveryInfo();
            di.setId(logsta.getId());
            di.setCountry(deliveryInfo.getCountry());
            di.setProvince(deliveryInfo.getProvince());
            di.setCity(deliveryInfo.getCity());
            di.setDistrict(deliveryInfo.getDistrict());
            di.setAddress(deliveryInfo.getAddress());
            di.setTelephone(deliveryInfo.getTelephone());
            di.setMobile(deliveryInfo.getMobile());
            di.setReceiver(deliveryInfo.getReceiver());
            di.setZipcode(deliveryInfo.getZipcode());
            di.setIscode(deliveryInfo.getIscode());
            di.setLpcode(deliveryInfo.getLpcode());
            di.setIsCodPos(deliveryInfo.getIsCodPos());
            di.setConvenienceStore(deliveryInfo.getConvenienceStore());
            di.setTrackingno(deliveryInfo.getTrackingno());
            di.setRemark(deliveryInfo.getRemark());
            di.setTranstype(deliveryInfo.getTranstimetype());
            di.setAddressEn(deliveryInfo.getAddressEn());
            di.setCityEn(deliveryInfo.getCityEn());
            di.setCountryEn(deliveryInfo.getCountryEn());
            di.setDistrictEn(deliveryInfo.getDistrictEn());
            di.setProvinceEn(deliveryInfo.getProvinceEn());
            di.setReceiverEn(deliveryInfo.getReceiverEn());
            di.setRemarkEn(deliveryInfo.getRemarkEn());
            di.setTranstimetype(deliveryInfo.getTranstimetype());
            di.setSender(deliveryInfo.getSender());
            di.setSendLpcode(deliveryInfo.getSendLpcode());
            di.setSendMobile(deliveryInfo.getSendMobile());
            di.setSendTransNo(deliveryInfo.getSendTransNo());
            di.setTransmemo(deliveryInfo.getTransmemo());
            di.setOrderusermail(deliveryInfo.getOrderusermail());
            di.setOrderusercode(deliveryInfo.getOrderusercode());
            di.setInsuranceAmount(deliveryInfo.getInsuranceAmount());
            queueLogStaDeliveryInfoDao.save(di);
        }
        for (int i = 0; i < staLines.size(); i++) {
            QueueLogStaLine logStaLine = new QueueLogStaLine();
            logStaLine.setSkucode(staLines.get(i).getSkucode());
            logStaLine.setQty(staLines.get(i).getQty());
            logStaLine.setTotalactual(staLines.get(i).getTotalactual());
            logStaLine.setOrdertotalactual(staLines.get(i).getOrdertotalactual());
            logStaLine.setListprice(staLines.get(i).getListprice());
            logStaLine.setUnitprice(staLines.get(i).getUnitprice());
            logStaLine.setDirection(staLines.get(i).getDirection());
            logStaLine.setOwner(staLines.get(i).getOwner());
            logStaLine.setInvstatusid(staLines.get(i).getInvstatusid() == null ? 1 : staLines.get(i).getInvstatusid());
            logStaLine.setQueueLogSta(logsta);
            logStaLine.setSkuName(staLines.get(i).getSkuName());
            logStaLine.setLineStatus(staLines.get(i).getLineStatus());
            logStaLine.setLineType(staLines.get(i).getLineType());
            logStaLine.setActivitysource(staLines.get(i).getActivitysource());
            logStaLine.setOrdertotalbfdiscount(staLines.get(i).getOrdertotalbfdiscount());
            queueLogStaLineDao.save(logStaLine);
            List<QueueStaLineStockout> staLineStockout = lineStockoutDao.queryStaLineId(staLines.get(i).getId(), new BeanPropertyRowMapper<QueueStaLineStockout>(QueueStaLineStockout.class));
            for (QueueStaLineStockout queueStaLineStockout : staLineStockout) {
                QueueStaLineStockoutLog lineStockoutLog = new QueueStaLineStockoutLog();
                lineStockoutLog.setLogStaLine(logStaLine);
                lineStockoutLog.setQty(queueStaLineStockout.getQty());
                lineStockoutLog.setSkuCode(queueStaLineStockout.getSkuCode());
                lineStockoutLogDao.save(lineStockoutLog);
                lineStockoutDao.lineStockoutDelete(queueStaLineStockout.getId());
            }
            List<QueueStaLineOwner> lineOwners = queueStaLineOwnerDao.queryStaLineId(staLines.get(i).getId(), new BeanPropertyRowMapperExt<QueueStaLineOwner>(QueueStaLineOwner.class));
            for (QueueStaLineOwner queueStaLineOwner : lineOwners) {
                QueueStaLineOwnerLog lineOwnerLog = new QueueStaLineOwnerLog();
                lineOwnerLog.setOwner(queueStaLineOwner.getOwner());
                lineOwnerLog.setQty(queueStaLineOwner.getQty());
                lineOwnerLog.setQueueStaLine(logStaLine);
                lineOwnerLog.setSkuCode(queueStaLineOwner.getSkuCode());
                lineOwnerLogDao.save(lineOwnerLog);
                queueStaLineOwnerDao.lineOwnerDelete(queueStaLineOwner.getId());
            }
            List<QueueGiftLine> giftLines = queueGiftLineDao.getByfindQstaline(staLines.get(i).getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
            if (giftLines != null) {
                for (QueueGiftLine giftLine : giftLines) {
                    QueueLogGiftLine logGiftLine = new QueueLogGiftLine();
                    logGiftLine.setMemo(giftLine.getMemo());
                    logGiftLine.setQueueLogStaLine(logStaLine);
                    logGiftLine.setType(giftLine.getType());
                    logGiftLineDao.save(logGiftLine);
                    queueGiftLineDao.delete(giftLine);
                }
            }
        }
        if (queueSta.getIsInvoice()) {
            for (QueueStaInvoice queueStaInvoice : invoices) {
                QueueLogStaInvoice logStaInvoice = new QueueLogStaInvoice();
                logStaInvoice.setAmt(queueStaInvoice.getAmt());
                logStaInvoice.setDrawer(queueStaInvoice.getDrawer());
                logStaInvoice.setInvoiceDate(queueStaInvoice.getInvoiceDate());
                logStaInvoice.setItem(queueStaInvoice.getItem());
                logStaInvoice.setMemo(queueStaInvoice.getMemo());
                logStaInvoice.setPayee(queueStaInvoice.getPayee());
                logStaInvoice.setPayer(queueStaInvoice.getPayer());
                logStaInvoice.setqlgStaId(logsta);
                logStaInvoice.setQty(queueStaInvoice.getQty());
                logStaInvoice.setUnitPrice(queueStaInvoice.getUnitPrice());
                logStaInvoice.setAddress(queueStaInvoice.getAddress());
                logStaInvoice.setIdentificationNumber(queueStaInvoice.getIdentificationNumber());
                logStaInvoice.setTelephone(queueStaInvoice.getTelephone());
                queueLogStaInvoiceDao.save(logStaInvoice);
                List<QueueStaInvoiceLine> queueStaInvoiceLines = queueStaInvoiceLineDao.findByInvoiceId(queueStaInvoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                    QueueLogStaInvoiceLine queueLogStaInvoiceLine = new QueueLogStaInvoiceLine();
                    queueLogStaInvoiceLine.setAmt(queueStaInvoiceLine.getAmt());
                    queueLogStaInvoiceLine.setItem(queueStaInvoiceLine.getItem());
                    queueLogStaInvoiceLine.setQty(queueStaInvoiceLine.getQty());
                    queueLogStaInvoiceLine.setUnitPrice(queueStaInvoiceLine.getUnitPrice());
                    queueLogStaInvoiceLine.setQueueLogStaInvoice(logStaInvoice);
                    queueLogStaInvoiceLineDao.save(queueLogStaInvoiceLine);
                }
            }
        }
        List<WhOrderSpecialExecute> spExe = whOrderSpecialExecuteDao.getByQueueId(queueSta.getId());
        if (spExe != null&&spExe.size()>0) {
            // 删除特殊类型中间表,记录日志表
            for(WhOrderSpecialExecute whOrderSpecialExecute:spExe){
                whOrderSpecialExecuteDao.deleteByPrimaryKey(whOrderSpecialExecute.getId());
                whOrderSpecialExecuteDao.inserSpecialExecuteLog(logsta.getId(), whOrderSpecialExecute.getType().getValue(), whOrderSpecialExecute.getMemo());
            }
        }
    }

    @Transactional
    public void createSta(QueueSta queueSta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLine> staLines, List<QueueStaInvoice> invoices) {
        int type = queueSta.getType();
        if (log.isDebugEnabled()) {
            log.debug("createSta start, qstaId:{}, sta type:{}, has addOwner:{}", new Object[] {queueSta.getId(), type, StringUtils.hasText(queueSta.getAddOwner())});
        }
        if (StringUtils.hasText(queueSta.getAddOwner())) {
            // 创建转店
            String tmpOwner = queueSta.getAddOwner();
            queueSta.setAddOwner(queueSta.getOwner());
            queueSta.setOwner(tmpOwner);
            if (log.isDebugEnabled()) {
                log.debug("create owner transfor sta, qstaId:{}", queueSta.getId());
            }
            String code = createStaManager.createOwnerTransForSta(queueSta);
            StockTransApplication sta = staDao.findStaBySlipCodeNotCancel(code);
            try {
                // 执行转店
                if (log.isDebugEnabled()) {
                    log.debug("execute owner transfor sta, qstaId:{}", queueSta.getId());
                }
                wareHouseManager.executeVmiTransferOutBound(sta.getId(), null, queueSta.getAddwhouid());

            } catch (Exception e) {
                log.error("qstaId:" + queueSta.getId(), e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        // 创建销售出库
        queueSta.setType(type);
        if (StringUtils.hasText(queueSta.getAddOwner())) {
            String tmpOwner = queueSta.getAddOwner();
            queueSta.setAddOwner(queueSta.getOwner());
            queueSta.setOwner(tmpOwner);
        }
        if (log.isDebugEnabled()) {
            log.debug("create sales sta, qstaId:{}", queueSta.getId());
        }
        createStaManager.createStaForSales(queueSta, deliveryInfo, staLines, invoices);
        if (log.isDebugEnabled()) {
            log.debug("createSta end, qstaId:{}, sta type:{}, has addOwner:{}", new Object[] {queueSta.getId(), type, StringUtils.hasText(queueSta.getAddOwner())});
        }
    }


    /**
     * 删除队列数据，增加日志
     * 
     * @param qstaId
     */
    public void removeQstaAddLog(Long qstaId) {
        QueueSta qsta = queueStaDao.getByPrimaryKey(qstaId);
        // 记录日志表信息
        createLogSta(qstaId, null);
        // 删除中间表信息
        List<QueueStaLine> ql = queueStaLineDao.findByStaId(qstaId);
        for (QueueStaLine line : ql) {
            queueStaLineDao.delete(line);
        }
        queueStaDao.delete(qsta);
        if (qsta.getQueueStaDeliveryInfo() != null) {
            queueStaDeliveryInfoDao.delete(qsta.getQueueStaDeliveryInfo());
        }
    }

    /**
     * 记录创单反馈，发送MQ消息
     * 
     * @param createdResponse
     * @param qstaId
     */
    public String sendCreateResultToOms2(StaCreatedResponse createdResponse, Long qstaId) {
        List<StaCreatedResponse> createdResponseList = new ArrayList<StaCreatedResponse>();
        createdResponseList.add(createdResponse);
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qstaId);
        queueSta.setErrorcount(200);
        String orderCode = queueSta.getOrdercode();
        CreateOrderToPac cotp = new CreateOrderToPac();
        cotp.setContext(JsonUtil.writeValue(createdResponseList));
        cotp.setCreateTime(new Date());
        cotp.setQstaId(qstaId);
        cotp.setSlipCode(orderCode);
        cotp.setStatus(1);
        cotp.setOwner(queueSta.getOwner());
        createOrderToPacDao.save(cotp);
        /*
         * List<StaCreatedResponse> createdResponseList = new ArrayList<StaCreatedResponse>();
         * createdResponseList.add(createdResponse); // 发送MQ MessageCommond mc = new
         * MessageCommond(); // mc.setMsgId(configs.get(0).getMsgId()); mc.setMsgId(qstaId + "," +
         * System.currentTimeMillis()); mc.setMsgType("sendCreateResultToOms2");
         * mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
         * mc.setMsgBody(JsonUtil.obj2jsonStrIncludeAll(createdResponseList));
         * log.debug("rmi Call pac Create request interface by MQ begin:" +
         * queueSta.getOrdercode()); try {
         * producerServer.sendDataMsgConcurrently(MqStaticEntity.WMS3_MQ_RTN_ORDER_CREATE, mc); }
         * catch (Exception e) { log.debug("rmi Call pac Create request interface by MQ error:" +
         * queueSta.getOrdercode()); throw new BusinessException(ErrorCode.MESSAGE_SEND_ERROR,
         * queueSta.getOrdercode()); } // 保存进mongodb MongoDBMessage mdbm = new MongoDBMessage();
         * BeanUtils.copyProperties(mc, mdbm); mdbm.setStaCode(queueSta.getOrdercode());
         * mdbm.setOtherUniqueKey(queueSta.getOrdercode()); mongoOperation.save(mdbm);
         */
        return cotp.getContext() + "_qstaToPacId_" + cotp.getId();
    }

    /**
     * 调用OMS接口
     * 
     * 1.OMS成功，无需转仓，删除队列数据，记录日志
     * 
     * 2.OMS成功，需转仓，修改队列仓库，记录日志
     * 
     * 3.OMS接口无法连接或系统 throw BusinessException ErrorCode=SYSTEM_ERROR 回滚事务
     * 
     * 4.OMS反馈失败或单据取消 throw BusinessException ErrorCode=OMS_SYSTEM_ERROR 回滚事务
     */
    public String sendCreateResultToOms(StaCreatedResponse createdResponse, Long qstaId) {
        if (createdResponse != null) {
            // 调用OMS接口
            BaseResult result = null;
            try {

                // 记录日志表信息
                if (log.isDebugEnabled()) {
                    log.debug("qstaId:{}, log queue", qstaId);
                }
                createLogSta(qstaId, createdResponse);
                if (log.isInfoEnabled()) {
                    log.info("rmi wms notify pac start, qstaId:{}, responseType:{}, responseStatus:{}", qstaId, createdResponse.getType(), createdResponse.getStatus());
                }
                // MQ改造
                List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_RTN_ORDER_CREATE, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
                if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开

                    return sendCreateResultToOms2(createdResponse, qstaId);
                }
                result = rmi4Wms.wmsCreateStaFeedback(createdResponse);
                if (log.isInfoEnabled()) {
                    log.info("rmi wms notify pac end, qstaId:{}, responseType:{}, responseStatus:{}", qstaId, createdResponse.getType(), createdResponse.getStatus());
                    log.info("rmi wms result pac end status:{},WhCode:{},msg:{}", result.getWhCode(), result.getWhCode(), result.getMsg());
                }

            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("error when connect oms to wmsCreateStaFeedback-qstaId:" + qstaId, e);
                }
                // 接口异常
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            if (result == null) {
                // 系统异常，未获取OMS反馈信息
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            if (log.isDebugEnabled()) {
                log.info("pac return result,qstaId:{}, resultStatus:{}, resultWhCode is not null:{}", qstaId, result.getStatus(), result.getWhCode());
            }
            QueueSta queueSta = queueStaDao.getByPrimaryKey(qstaId);
            if (result.getStatus() == BaseResult.BASE_RESULT_STATUS_SUCCESS) {
                // ===========反馈成功,判断OMS是否需要修改仓库================
                if (result.getWhCode() != null) {
                    // ==============判断是否需要修改仓库ID========
                    // 修改仓库ID
                    queueSta.setMainWhOuCode(result.getWhCode());
                    queueSta.setBeginflag(null);
                    queueSta.setCanflag(null);
                    queueSta.setFlagResult(null);
                    OperationUnit mainWhOu = operationUnitDao.getByCode(result.getWhCode());
                    if (mainWhOu == null || !OperationUnitType.OUTYPE_WAREHOUSE.equals(mainWhOu.getOuType().getName())) {
                        throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new Object[] {result.getWhCode()});
                    }
                    queueSta.setMainwhouid(mainWhOu.getId());
                    queueSta.setBeginflag(null);
                    queueSta.setCanflag(null);
                    queueSta.setFlagResult(null);
                } else {
                    // ============= 删除队列数据===============
                    // 删除中间表信息
                    List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                    for (QueueStaInvoice invoice : invoices) {
                        List<QueueStaInvoiceLine> queueStaInvoiceLines = queueStaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                        for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                            queueStaInvoiceLineDao.cleanDataByLineId(queueStaInvoiceLine.getId());
                        }
                        queueStaInvoiceDao.cleanDataByInvoiceId(invoice.getId());
                    }
                    List<QueueStaLine> ql = queueStaLineDao.findByStaId(qstaId);
                    for (QueueStaLine line : ql) {
                        queueStaLineDao.cleanDataByLineId(line.getId());
                    }
                    // 新删除 费用列表
                    List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
                    for (QueueStaPayment queueStaPayment : pays) {
                        queueStaPaymentDao.deleteStaPayment(queueStaPayment.getId());
                    }
                    // 删除t_wh_order_special_execute
                    whOrderSpecialExecuteDao.delSpecialExecute(qstaId);
                    int status = queueStaDao.deleteQueuesta(qstaId);
                    if (status == 1) {} else {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {qstaId});
                    }
                    if (queueSta.getQueueStaDeliveryInfo() != null) {
                        queueStaDeliveryInfoDao.cleanDataByStaId(qstaId);
                    }

                }
            } else if (result.getStatus() == BaseResult.BASE_RESULT_STATUS_CANCEL) {
                throw new BusinessException(ErrorCode.OMS_ORDER_CANACEL);
            } else if (result.getStatus() == BaseResult.BASE_RESULT_STATUS_ERROR) {
                // OMS系统异常
                throw new BusinessException(ErrorCode.OMS_OUT_ERROR);
            } else {
                // 系统异常
                log.error("when send message to oms,the message is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        return null;
    }

    public void inStaline(List<QueueStaLine> line, StockTransApplication sta, OperationUnit ou1) {
        for (QueueStaLine line1 : line) {
            if (line1.getDirection() == TransactionDirection.INBOUND.getValue()) {
                Sku sku = skuDao.getByCode(line1.getSkucode());
                StaLine sl = new StaLine();
                sl.setSta(sta);
                sl.setSku(sku);
                sl.setOwner(sta.getOwner());
                sl.setQuantity(line1.getQty());
                sl.setCompleteQuantity(0L);
                sl.setUnitPrice(line1.getUnitprice());
                sl.setOrderTotalActual(line1.getOrdertotalactual());
                sl.setOrderTotalBfDiscount(line1.getOrdertotalbfdiscount());
                sl.setListPrice(line1.getListprice());
                InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(line1.getInvstatusid(), ou1.getParentUnit().getParentUnit().getId());
                sl.setInvStatus(invStatus);
                staLineDao.save(sl);
            }
        }
    }

    /**
     * @author LuYingMing
     * @date 2016年7月25日 下午2:46:36
     * @see com.jumbo.wms.manager.warehouse.QueueStaManagerExecute#findMsgToWcsByParams(int, int,
     *      java.util.Map)
     */
    @Override
    public Pagination<QueueStaCommand> findQueueStaByParams(int start, int pageSize, Map<String, Object> params) {

        return queueStaDao.findQueueStaByParams(start, pageSize, params, new BeanPropertyRowMapper<QueueStaCommand>(QueueStaCommand.class));
    }

    /**
     * @author LuYingMing
     * @date 2016年7月25日 下午5:33:46
     * @see com.jumbo.wms.manager.warehouse.QueueStaManagerExecute#resetToZero(java.lang.Long)
     */
    @Override
    public void resetToZero(Long queueStaId) {
        queueStaDao.updateZeroByErrorCode(queueStaId);
    }

    /**
     * @author LuYingMing
     * @date 2016年7月25日 下午5:33:50
     * @see com.jumbo.wms.manager.warehouse.QueueStaManagerExecute#resetTo99(java.lang.Long)
     */
    @Override
    public void resetTo99(Long queueStaId) {
        String batchCode = queueStaDao.queryBatchcode(new SingleColumnRowMapper<String>(String.class));
        if (!StringUtil.isEmpty(batchCode) && batchCode.length() > 0 && null != queueStaId) {
            queueStaDao.update99ByErrorCode(queueStaId, batchCode);
        } else {
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.VMI_ESPRIT_BATCH_NO_ISNULL), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            log.error("测试之用:" + errorMsg);
            // 批次号batch_no为空无法创入库单
            throw new BusinessException(ErrorCode.VMI_ESPRIT_BATCH_NO_ISNULL);
        }
    }


    /**
     * PAC反馈创单确认结果
     * 
     * @param msg
     */
    public void excuteOrderCreateResponse(String msg) {
        // String msg = message.getMsgBody();
        List<com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult> resultList = JsonUtil.readList(msg, com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult.class);
        if (resultList == null) {
            return;
        }
        for (com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult result : resultList) {

            QueueSta queueSta = queueStaDao.findStaBySlipCodeNotCancel(result.getSlipCode());
            // CreateOrderToPac cotp =
            // createOrderToPacDao.getCreateOrderToPacByCode(result.getSlipCode());
            // cotp.setResult(msg);

            CreateOrderToPacResponse cotpc = new CreateOrderToPacResponse();
            cotpc.setResult(msg);
            cotpc.setSlipCode(result.getSlipCode());
            cotpc.setCreateTime(new Date());
            if (result.getStatus() == com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult.BASE_RESULT_STATUS_SUCCESS && queueSta != null) {
                // ===========反馈成功,判断OMS是否需要修改仓库================
                if (result.getWhCode() != null) {
                    // ==============判断是否需要修改仓库ID========
                    // 修改仓库ID
                    OperationUnit mainWhOu = operationUnitDao.getByCode(result.getWhCode());
                    if (mainWhOu == null || !OperationUnitType.OUTYPE_WAREHOUSE.equals(mainWhOu.getOuType().getName())) {
                        log.error("仓库找不到：" + result.getWhCode());
                        cotpc.setResult(cotpc.getResult() + "##仓库找不到");
                        // throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new
                        // Object[] {result.getWhCode()});
                        return;
                    }
                    queueSta.setMainWhOuCode(result.getWhCode());
                    queueSta.setMainwhouid(mainWhOu.getId());
                    queueSta.setBeginflag(null);
                    queueSta.setCanflag(null);
                    queueSta.setFlagResult(null);
                    queueSta.setErrorcount(0);
                } else {
                    // ============= 删除队列数据===============
                    // 删除中间表信息
                    List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                    for (QueueStaInvoice invoice : invoices) {
                        List<QueueStaInvoiceLine> queueStaInvoiceLines = queueStaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                        for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                            queueStaInvoiceLineDao.cleanDataByLineId(queueStaInvoiceLine.getId());
                        }
                        queueStaInvoiceDao.cleanDataByInvoiceId(invoice.getId());
                    }
                    List<QueueStaLine> ql = queueStaLineDao.findByStaId(queueSta.getId());
                    for (QueueStaLine line : ql) {
                        queueStaLineDao.cleanDataByLineId(line.getId());
                    }
                    // 新删除 费用列表
                    List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
                    for (QueueStaPayment queueStaPayment : pays) {
                        queueStaPaymentDao.deleteStaPayment(queueStaPayment.getId());
                    }
                    int status = queueStaDao.deleteQueuesta(queueSta.getId());
                    if (status == 1) {} else {
                        log.error("删除中间表队列失败：" + queueSta.getId());
                        // throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[]
                        // {queueSta.getId()});
                    }
                    if (queueSta.getQueueStaDeliveryInfo() != null) {
                        queueStaDeliveryInfoDao.cleanDataByStaId(queueSta.getId());
                    }

                }
            } else {
                // log.info("when send message to oms,the message is null");
            }
            mongoOperation.save(cotpc);
        }
    }

}
