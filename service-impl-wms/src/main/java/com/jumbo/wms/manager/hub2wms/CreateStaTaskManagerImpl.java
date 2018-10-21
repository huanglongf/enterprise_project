package com.jumbo.wms.manager.hub2wms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.BiChannelSpecialActionDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.CnOrderPropertyDao;
import com.jumbo.dao.hub2wms.CnWmsOrderStatusUploadDao;
import com.jumbo.dao.hub2wms.OrderWarehousePriorityDao;
import com.jumbo.dao.hub2wms.ValueAddedServiceQueueDao;
import com.jumbo.dao.hub2wms.WmsConfirmOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceLineQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderPackingQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLineQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderPaymentQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsShippingLineQueueDao;
import com.jumbo.dao.hub2wms.WmsShippingQueueDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.warehouse.AdvanceOrderAddInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CreateOrderToPacDao;
import com.jumbo.dao.warehouse.CreateOrderToPacLogDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.LitreSingleDao;
import com.jumbo.dao.warehouse.NikeTurnCreateDao;
import com.jumbo.dao.warehouse.PriorityChannelOmsDao;
import com.jumbo.dao.warehouse.QueueGiftLineDao;
import com.jumbo.dao.warehouse.QueueLogGiftLineDao;
import com.jumbo.dao.warehouse.QueueLogStaDao;
import com.jumbo.dao.warehouse.QueueLogStaDeliveryInfoDao;
import com.jumbo.dao.warehouse.QueueLogStaInvoiceDao;
import com.jumbo.dao.warehouse.QueueLogStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueLogStaLineDao;
import com.jumbo.dao.warehouse.QueueLogStaPaymentDao;
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
import com.jumbo.dao.warehouse.SfCloudWarehouseConfigDao;
import com.jumbo.dao.warehouse.StaCancelDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaPaymentDao;
import com.jumbo.dao.warehouse.StaSpecialExecutedDao;
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
import com.jumbo.rmi.warehouse.WarehousePriority;
import com.jumbo.rmiservice.RmiManager;
import com.jumbo.rmiservice.RmiService;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.MongoDBInventoryManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.trans.TransSuggestManager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.warehouse.QueueStaManagerExecute;
import com.jumbo.wms.model.LitreSingle;
import com.jumbo.wms.model.MongoDBInventory;
import com.jumbo.wms.model.MongoDBInventoryPac;
import com.jumbo.wms.model.QueueStaLineOwnerLog;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.TransUpgradeType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.SkuWarrantyCardType;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.hub2wms.OrderWarehousePriority;
import com.jumbo.wms.model.hub2wms.ValueAddedServiceQueue;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLineQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderPackingQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLineQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderPaymentQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsShippingLineQueue;
import com.jumbo.wms.model.hub2wms.WmsShippingQueue;
import com.jumbo.wms.model.hub2wms.threepl.CnOrderProperty;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsOrderStatusUpload;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.msg.MongoDBMessageTest;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.CreateOrderToPac;
import com.jumbo.wms.model.warehouse.CreateOrderToPacLog;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.NikeTurnCreate;
import com.jumbo.wms.model.warehouse.PackingType;
import com.jumbo.wms.model.warehouse.PaymentType;
import com.jumbo.wms.model.warehouse.PriorityChannelOms;
import com.jumbo.wms.model.warehouse.QueueGiftLine;
import com.jumbo.wms.model.warehouse.QueueLogGiftLine;
import com.jumbo.wms.model.warehouse.QueueLogSta;
import com.jumbo.wms.model.warehouse.QueueLogStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueLogStaInvoice;
import com.jumbo.wms.model.warehouse.QueueLogStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueLogStaLine;
import com.jumbo.wms.model.warehouse.QueueLogStaPayment;
import com.jumbo.wms.model.warehouse.QueueMqSta;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueStaInvoice;
import com.jumbo.wms.model.warehouse.QueueStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineOwner;
import com.jumbo.wms.model.warehouse.QueueStaLineStatus;
import com.jumbo.wms.model.warehouse.QueueStaLineStockout;
import com.jumbo.wms.model.warehouse.QueueStaLineStockoutLog;
import com.jumbo.wms.model.warehouse.QueueStaPayment;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfig;
import com.jumbo.wms.model.warehouse.SpecialSkuType;
import com.jumbo.wms.model.warehouse.StaCancel;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaPayment;
import com.jumbo.wms.model.warehouse.StaSpecialExecute;
import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WhOrderSpecialExecute;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.utils.DateUtil;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public class CreateStaTaskManagerImpl extends BaseManagerImpl implements CreateStaTaskManager {
    private static final Logger log = LoggerFactory.getLogger(CreateStaTaskManagerImpl.class);
    TimeHashMap<String, List<MessageConfig>> pacCache = new TimeHashMap<String, List<MessageConfig>>();
    TimeHashMap<String, List<MessageConfig>> omsCache = new TimeHashMap<String, List<MessageConfig>>();

    /**
     * 
     */
    private static final long serialVersionUID = 5527586218331213043L;
    // private static int THREADNUM = 0;
    // 库存不足：仓库A[SKU0001-缺(3)]
    private static String INVNOTENOUGH = "库存不足：{0}";
    private static String INVNOTENOUGH_W = "仓库{0}[{1}]";
    private static String INVNOTENOUGH_W_D = "{0}-缺({1})";
    // 库存不足：商品A[SHOO1-缺(3)]
    private static String INVNOTENOUGH_S = "商品{0}[{1}]";
    private static String INVNOTENOUGH_S_D = "{0}-缺({1})";
    private static String PRIORITY_W = "优先出库仓：{0}";
    private static String INVNOTENOUGH_S_Q = "{0} 缺货[{1}]";
    // 更新库存失败
    private static String UPDATEINVERROR = "更新库存失败 需要重新计算";

    @Resource
    private ApplicationContext applicationContext;
    private EventObserver eventObserver;
    @Autowired
    private WmsSalesOrderQueueDao wmsSalesOrderQueueDao;
    @Autowired
    private WmsSalesOrderLineQueueDao wmsSalesOrderLineQueueDao;
    @Autowired
    private OrderWarehousePriorityDao orderWarehousePriorityDao;
    @Autowired
    private RmiService rmiService;
    @Autowired
    private QueueStaPaymentDao queueStaPaymentDao;
    @Autowired
    private MongoDBInventoryManager mongoDBInventoryManager;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private ValueAddedServiceQueueDao valueAddedServiceQueueDao;
    @Autowired
    private WmsSalesOrderPaymentQueueDao wmsSalesOrderPaymentQueueDao;
    @Autowired
    private WmsOrderPackingQueueDao wmsOrderPackingQueueDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaSpecialExecutedDao staSpecialExecutedDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private BiChannelSpecialActionDao biChannelSpecialActionDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private PriorityChannelOmsDao priorityChannelOmsDao;
    @Autowired
    private GiftLineDao giftLineDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WmsOrderInvoiceQueueDao wmsOrderInvoiceQueueDao;
    @Autowired
    private WmsOrderInvoiceLineQueueDao wmsOrderInvoiceLineQueueDao;
    @Autowired
    private StaPaymentDao staPaymentDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private StaInvoiceLineDao staInvoiceLineDao;
    @Autowired
    private WmsConfirmOrderQueueDao wmsConfirmOrderQueueDao;
    @Autowired
    private WmsShippingQueueDao wmsShippingQueueDao;
    @Autowired
    private WmsShippingLineQueueDao wmsShippingLineQueueDao;
    @Autowired
    private TransSuggestManager transSuggestManager;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private NikeTurnCreateDao createDao;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private RmiManager rmiManager;
    @Autowired
    private LitreSingleDao litreSingleDao;
    @Autowired
    private StaCancelDao cancelDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private QueueLogStaPaymentDao queueLogStaPaymentDao;
    @Autowired
    OperationUnitDao operationUnitDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    QueueStaLineOwnerDao lineOwnerDao;
    @Autowired
    WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private WhOrderSpecialExecuteDao whOrderSpecialExecuteDao;
    @Autowired
    private QueueStaDeliveryInfoDao queueStaDeliveryInfoDao;
    @Autowired
    QueueGiftLineDao queueGiftLineDao;
    @Autowired
    QueueStaInvoiceDao queueStaInvoiceDao;
    @Autowired
    QueueStaInvoiceLineDao queuestaInvoiceLineDao;
    @Autowired
    QueueLogStaDao queueLogStaDao;
    @Autowired
    Rmi4Wms rmi4Wms;
    @Autowired
    QueueLogStaDeliveryInfoDao queueLogStaDeliveryInfoDao;
    @Autowired
    QueueLogStaLineDao queueLogStaLineDao;
    @Autowired
    QueueLogGiftLineDao logGiftLineDao;
    @Autowired
    QueueLogStaInvoiceDao queueLogStaInvoiceDao;
    @Autowired
    QueueLogStaInvoiceLineDao queueLogStaInvoiceLineDao;
    @Autowired
    QueueStaLineOwnerDao queueStaLineOwnerDao;
    @Autowired
    QueueStaLineOwnerLogDao lineOwnerLogDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    InventoryStatusDao inventoryStatusDao;
    @Autowired
    QueueStaManagerExecute queueStaManagerExecute;
    @Autowired
    QueueStaLineStockoutDao lineStockoutDao;
    @Autowired
    private SfCloudWarehouseConfigDao sfCloudWarehouseConfigDao;
    @Autowired
    private QueueStaLineStockoutLogDao lineStockoutLogDao;
    @Autowired
    private WmsThreePLManager wmsThreePLManager;
    @Autowired
    private CnOrderPropertyDao cnOrderPropertyDao;
    @Autowired
    private CnWmsOrderStatusUploadDao cnWmsOrderStatusUploadDao;
    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private CreateOrderToPacDao createOrderToPacDao;
    @Autowired
    private AdvanceOrderAddInfoDao advanceOrderAddInfoDao;
    @Autowired
    private CreateOrderToPacLogDao createOrderToPacLogDao;


    @Autowired
    private RocketMQProducerServer producerServer;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @Value("${mq.mq.wms3.create.sta.pac}")
    public String WMS3_MQ_CREATE_STA_PAC;

    @Value("${mq.mq.wms3.create.sta.oms}")
    public String WMS3_MQ_CREATE_STA_OMS;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:RmiManagerImpl");
        }
    }

    /**
     * 1 一次查询1000单，分批次 2 计算批次内仓库订单，计算分仓 3 按仓启动线程 4 计算库存是否可用，成功打标识，不成功，下个仓库
     */
    @Override
    public void setFlagForStaData() {

    }

    /**
     * 1. 先查出优先级最高的订单 2. 查询特殊定制店铺的订单数量 3.减去前面两步的数量给到普通店铺 4 若有剩余 则分配到压测店铺
     */
    @Override
    public void setBeginFlagForOrder() {
        // 1、默认更新批次号，标识本次定时任务将执行的单据
        Integer num = Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(Constants.ORDER_LIMIT, Constants.THREAD_OR_LIMIT_NUM, new SingleColumnRowMapper<String>(String.class)));
        Integer updateOrderNum = wmsSalesOrderQueueDao.updateBeginFlagByPriorityOrder();
        if (null == updateOrderNum) {
            updateOrderNum = 0;
        }
        String priorityOms = chooseOptionManager.getSystemThreadOptionValueByKey(Constants.ORDER_PRIORITY_OMS);
        if (null != priorityOms && !"".equals(priorityOms)) {
            Integer priorityNum = Integer.parseInt(priorityOms.split("/")[0]);
            Integer commonNum = Integer.parseInt(priorityOms.split("/")[1]);
            List<PriorityChannelOms> priorityChannelOmsList = priorityChannelOmsDao.findAllPriority();
            List<PriorityChannelOms> analyzeOwnerList = priorityChannelOmsDao.findAllPriorityByQty();
            if (null != priorityChannelOmsList && priorityChannelOmsList.size() > 0) {
                Long sumPriority = priorityChannelOmsDao.findCountAllPriority(new SingleColumnRowMapper<Long>(Long.class));
                BigDecimal priorityNumBigDecimal = new BigDecimal(priorityNum);
                BigDecimal commonNumBigDecimal = new BigDecimal(commonNum);
                BigDecimal commonOrderNum = new BigDecimal(commonNumBigDecimal.multiply(new BigDecimal(num - updateOrderNum)).divide(priorityNumBigDecimal.add(commonNumBigDecimal)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
                BigDecimal priorityOrderNum = new BigDecimal(priorityNumBigDecimal.multiply(new BigDecimal(num - updateOrderNum)).divide(priorityNumBigDecimal.add(commonNumBigDecimal)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
                BigDecimal sumPriorityBigDecimal = new BigDecimal(sumPriority);
                Integer countSum = 0;
                List<String> ownerList = new ArrayList<String>();
                for (PriorityChannelOms list : priorityChannelOmsList) {
                    BigDecimal qtyBigDecimal = new BigDecimal(list.getQty());
                    Integer updateByOwnerNum = wmsSalesOrderQueueDao.updateBeginFlagByOwner(priorityOrderNum.multiply(qtyBigDecimal).divide(sumPriorityBigDecimal).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(), list.getCode());
                    countSum += updateByOwnerNum;
                    ownerList.add(list.getCode());
                }
                Integer updateCommonNum = wmsSalesOrderQueueDao.updateBeginFlagBycommonOwner(priorityOrderNum.subtract(new BigDecimal(countSum)).add(commonOrderNum).intValue(), ownerList);
                BigDecimal testOrderNum = priorityOrderNum.subtract(new BigDecimal(countSum)).add(commonOrderNum).subtract(new BigDecimal(updateCommonNum));
                if (null != testOrderNum && testOrderNum.intValue() > 0) {
                    // List<PriorityChannelOms> analyzeOwnerList =
                    // priorityChannelOmsDao.findAllPriorityByQty();
                    if (null != analyzeOwnerList && analyzeOwnerList.size() > 0) {
                        wmsSalesOrderQueueDao.updateBeginFlagByAnalyzeOwner(testOrderNum.intValue());
                    }
                }
            } else {
                if (null != analyzeOwnerList && analyzeOwnerList.size() > 0) {
                    Integer commonNumber = wmsSalesOrderQueueDao.setBeginFlagForOrderNotin(num - updateOrderNum);
                    if (null == commonNumber) {
                        commonNumber = 0;
                    }
                    BigDecimal analyzeOwner = new BigDecimal(num - updateOrderNum - commonNumber);
                    if (null != analyzeOwner && analyzeOwner.intValue() > 0) {
                        wmsSalesOrderQueueDao.updateBeginFlagByAnalyzeOwner(analyzeOwner.intValue());
                    }
                } else {
                    wmsSalesOrderQueueDao.setBeginFlagForOrder(num - updateOrderNum);
                }

            }
        } else {
            wmsSalesOrderQueueDao.setBeginFlagForOrder(num - updateOrderNum);
        }

    }

    @Override
    public void setBeginFlagForOrderPac(Long id) {
        // 1、默认更新批次号，标识本次定时任务将执行的单据
        Integer num = Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(Constants.ORDER_LIMIT_PAC, Constants.THREAD_OR_LIMIT_NUM, new SingleColumnRowMapper<String>(String.class)));
        queueStaDao.setBeginFlagForOrderPac(num, id);
    }

    @Override
    public Boolean getAllNeedSetFlagOrder() {
        // 查询是否所有的待标识单据都打了标识
        return wmsSalesOrderQueueDao.getIsAllHaveFlag(new SingleColumnRowMapper<Boolean>(Boolean.class));
    }

    @Override
    public Boolean getAllNeedSetFlagOrderPac() {
        // 查询是否所有的待标识单据都打了标识
        return queueStaDao.getIsAllHaveFlagPac(new SingleColumnRowMapper<Boolean>(Boolean.class));
    }

    @Override
    public List<Long> getNeedExecuteWarehouse() {
        return ouDao.getNeedExecuteWarehouse(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public List<Long> getAllOrderToCreate() {
        return wmsSalesOrderQueueDao.getAllOrderToCreate(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public List<Long> getAllOrderInvCheckCreate() {
        Long rowNum = Long.parseLong(chooseOptionDao.findAllOptionListByOptionKey(Constants.ORDER_LIMIT, Constants.THREAD_OR_LIMIT_NUM, new SingleColumnRowMapper<String>(String.class)));
        return wmsSalesOrderQueueDao.getAllOrderInvCheckCreate(rowNum, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public List<Long> getAllOrderInvCheckCreateByPac() {
        return queueStaDao.getAllOrderToCreatePac2(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public List<Long> getAllOrderToCreatePac() {
        return queueStaDao.getAllOrderToCreatePac(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public void setFlagByWarehouse(Long id) {}

    /**
     * 创建sta<br/>
     * 算法及赋值：<br/>
     * 1、查询打了标识的中间表数据，可以创单走步骤 2，不可以创单走步骤3 <br/>
     * 2、创单生成sta,记录日志，生成单据确认中间表信息 <br/>
     * 2.1、根据明细行标识的仓库ID，可以区分该单是否拆单及需要拆几单，然后进行遍历创单逻辑(打了删除标记的明细行不参与创单)<br/>
     * 2.2、分仓逻辑每个sta对应的发票头和发票明细要根据订单行重新汇总<br/>
     * 3、记录日志，生成单据确认中间表信息
     */
    @SuppressWarnings("deprecation")
    @Override
    public void createStaById(Long id) {
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.createStaById....Enter id:" + id);
        }
        Boolean isMq = false;
        // 直连创单反馈开关
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_SALES_ORDER_SERVICE_RETURN, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
            isMq = true;
        }

        WmsSalesOrderQueue wq = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        if (wq == null) {
            log.error("CreateStaTaskManagerImpl.createStaById.error_id:" + id);
            return;
        }
        StockTransApplication sta1 = staDao.findStaBySlipCodeNotCancel(wq.getOrderCode());
        if (sta1 != null) {
            throw new BusinessException(ErrorCode.HW_ORDER_EXISTS);
        }
        if (wq.getCanFlag()) {
            // 汇总明细行有几个仓库，等价于要拆几单
            List<Long> groupId = wmsSalesOrderLineQueueDao.findOuByOrderId(wq.getId(), new SingleColumnRowMapper<Long>(Long.class));
            List<ValueAddedServiceQueue> vsl = valueAddedServiceQueueDao.getAllAddedServiceByOrderId(wq.getId(), new BeanPropertyRowMapper<ValueAddedServiceQueue>(ValueAddedServiceQueue.class));
            List<WmsSalesOrderPaymentQueue> wpl = wmsSalesOrderPaymentQueueDao.getAllPaymentByOrderId(wq.getId(), new BeanPropertyRowMapper<WmsSalesOrderPaymentQueue>(WmsSalesOrderPaymentQueue.class));
            // 此处用来设置是否特殊处理逻辑
            // List<WmsOrderPackingQueue> wpql =
            // wmsOrderPackingQueueDao.getAllPackingByOrderId(wq.getId(), new
            // BeanPropertyRowMapper<WmsOrderPackingQueue>(WmsOrderPackingQueue.class));
            wmsOrderPackingQueueDao.getAllPackingByOrderId(wq.getId(), new BeanPropertyRowMapper<WmsOrderPackingQueue>(WmsOrderPackingQueue.class));
            BiChannel bichannel = biChannelDao.getByCode(wq.getOwner());
            // NIKE特殊逻辑，生成转店码
            String turnCode = "";
            if (StringUtils.hasText(bichannel.getVmiCode())) {
                if (bichannel.getVmiCode().equals("3251")) {
                    VmiInterface vf = vmiFactory.getBrandVmi(bichannel.getVmiCode());
                    if (vf != null) {
                        turnCode = vf.generateRtnStaSlipCode(bichannel.getVmiCode(), StockTransApplicationType.VMI_OWNER_TRANSFER);
                    }
                }
            }
            // 确认头。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
            WmsConfirmOrderQueue wcq = new WmsConfirmOrderQueue();
            if (isMq) {
                wcq.setIsMq("1");
            }
            wcq.setSystemKey(wq.getSystemKey());
            wcq.setCreateTime(new Date());
            wcq.setIsDs((groupId != null && groupId.size() > 1) ? true : false);
            wcq.setOrderCode(wq.getOrderCode());
            wcq.setStatus(true);
            wcq.setOwner(wq.getOwner());
            wcq.setOrderType(wq.getOrderType());
            wcq.setIsmeet(wq.getIsmeet());
            wcq.setMemo(wq.getFlagResult());
            wcq.setOrderSourcePlatform(wq.getOrderSourcePlatform());// adidas 订单来源
            wcq.setErrorStatus("S000001");
            wmsConfirmOrderQueueDao.save(wcq);
            // 循环拆单操作
            /*
             * 1、发票金额怎么算
             */
            if (groupId != null) {
                for (Long ouId : groupId) {
                    StockTransApplication sta = new StockTransApplication();
                    // TODO:系统来源标识、订单来源、菜鸟外包仓单号
                    sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
                    sta.setCreateTime(new Date());
                    sta.setLastModifyTime(new Date());
                    sta.setIsNeedOccupied(true);
                    sta.setStatus(StockTransApplicationStatus.CREATED);
                    sta.setSlipCode1(wq.getSourceOrderCode());
                    if (StringUtils.hasText(wq.getSlipCode2())) {
                        sta.setSlipCode2(wq.getSlipCode2());
                    } else {
                        sta.setSlipCode2(wq.getSourceOrderCode());
                    }
                    sta.setRefSlipCode(wq.getOrderCode());
                    sta.setRefSlipType(SlipType.SALES_ORDER);
                    sta.setType(StockTransApplicationType.OUTBOUND_SALES);
                    sta.setMainWarehouse(ouDao.getByPrimaryKey(ouId));
                    sta.setOwner(wq.getOwner());
                    sta.setOrderCreateTime(wq.getOrderCreateTime());
                    sta.setToLocation(wq.getStoreCode());// O2O
                    sta.setChannelList(wq.getAcceptOwners());
                    sta.setOrderSource(wq.getOrderSource());
                    sta.setSystemKey(wq.getSystemKey());
                    if (wq.isLocked() || wq.isDSLocked()) {
                        sta.setIsLocked(true);
                    }
                    sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
                    // TODO:slipCode2,slipCode3
                    sta.setActivitySource(wq.getActiveCode());// TODO:confirm
                    sta.setArriveTime(wq.getPlanArriveTime());// TODO:confirm
                    sta.setPlanOutboundTime(wq.getPlanOutboundTime());// TODO:confirm
                    sta.setPlanLastOutboundTime(wq.getPlanOutboundTime());// TODO:confirm
                    sta.setPaymentType("");// TODO:confirm
                    sta.setOrderTransferFree(wq.getFreight());
                    sta.setMemo(wq.getMemo());
                    sta.setOrderSourcePlatform(wq.getOrderSourcePlatform());
                    sta.setIsShortPicking(wq.getIsShortPicking()); // 是否短配
                    sta.setPoints(wq.getPoints()); // 积分
                    sta.setReturnPoints(wq.getReturnPoints()); // 返点积分
                    sta.setIsUrgent(wq.getIsUrgent()); // 是否紧急
                    sta.setIsOutboundCheck(wq.getIsBfOutbountCheck()); // 是否检验
                    sta.setOrderType(wq.getOrderType2()); // 作业类型
                    sta.setNikePickUpCode(wq.getNikePickUpCode());
                    sta.setNikePickUpType(wq.getNikePickUpType());
                    sta.setIsNikePick(wq.getNikeIsPick());
                    sta.setIsPreSale(wq.getIsPreSale());// 是否预售
                    staDao.save(sta);
                    WmsShippingQueue wsq = new WmsShippingQueue();
                    wsq.setQueue(wcq);
                    wsq.setShippingCode(sta.getCode());
                    wsq.setTransNo(wq.getTransNo());
                    wsq.setWhCode(ouDao.getByPrimaryKey(ouId).getCode());
                    wmsShippingQueueDao.save(wsq);
                    StaDeliveryInfo di = new StaDeliveryInfo();
                    di.setId(sta.getId());
                    di.setConvenienceStore(wq.getConvenienceStore());
                    di.setCountry(wq.getCountry());
                    di.setTrackingNo(wq.getTransNo());
                    di.setProvince(wq.getProvince());
                    di.setCity(wq.getCity());
                    di.setDistrict(wq.getDistrict());
                    di.setAddress(wq.getAddress());
                    di.setTelephone(wq.getTelephone());
                    di.setIsCodPos(wq.isCodPos());
                    di.setMobile(wq.getMoblie());
                    di.setReceiver(wq.getReceiver());
                    di.setZipcode(wq.getZipcode());
                    di.setIsCod(wq.isCod());
                    di.setStoreComIsNeedInvoice(null);// 根据发票汇总
                    di.setAddressEn(wq.getAddress1());
                    di.setCityEn(wq.getCity1());
                    di.setCountryEn(wq.getCountry());
                    di.setDistrictEn(wq.getDistrict1());
                    di.setProvinceEn(wq.getProvince1());
                    di.setReceiverEn(wq.getReceiver1());
                    di.setRemarkEn(wq.getMemo());
                    di.setRemark(wq.getMemo());
                    di.setTransferFee(wq.getFreight());
                    di.setTransType(TransType.valueOf(wq.getTransType()));
                    di.setOrderUserCode(wq.getOrderUserCode());
                    di.setOrderUserMail(wq.getOrderUserMail());
                    di.setInsuranceAmount(null);// 保价金额
                    di.setLastModifyTime(new Date());
                    di.setTransMemo(wq.getTransMemo());
                    List<BiChannelSpecialAction> bsa = biChannelSpecialActionDao.getChannelByAndType(bichannel.getId(), BiChannelSpecialActionType.PRINT_EXPRESS_BILL);
                    if (bsa != null && bsa.size() > 0) {
                        for (BiChannelSpecialAction bcsa : bsa) {
                            if (StringUtils.hasText(bcsa.getTransAddMemo())) {
                                di.setTransMemo(bcsa.getTransAddMemo());
                            }
                        }
                    }
                    staDeliveryInfoDao.save(di);
                    List<BiChannelSpecialAction> csaList = biChannelSpecialActionDao.getChannelByAndType(bichannel.getId(), BiChannelSpecialActionType.SPECIAL_PACKING);
                    if (csaList != null && csaList.size() > 0) {
                        for (BiChannelSpecialAction csa : csaList) {
                            StaSpecialExecuteType type = null;
                            try {
                                type = StaSpecialExecuteType.valueOf(Integer.valueOf(csa.getTemplateType()));
                            } catch (Exception e) {
                                throw new BusinessException(ErrorCode.ERROR_SPECIAL_TEMPLATE_TYPE_ERROR);
                            }
                            StaSpecialExecute sse = new StaSpecialExecute();
                            sse.setType(type);
                            sse.setSta(sta);
                            staSpecialExecutedDao.save(sse);
                        }
                        sta.setSpecialType(SpecialSkuType.NORMAL);
                    }
                    if (Constants.BURBERRY_OWNER1.equals(sta.getOwner()) && (TransDeliveryType.LAND.equals(sta.getDeliveryType()) || TransDeliveryType.AVIATION.equals(sta.getDeliveryType()))) {
                        di.setRemark("汽运");
                    }
                    sta.setDeliveryType((wq.getTransType() == 6 ? TransDeliveryType.AVIATION : TransDeliveryType.ORDINARY));
                    sta.setStaDeliveryInfo(di);
                    if (vsl != null) {
                        for (ValueAddedServiceQueue vs : vsl) {
                            StaSpecialExecute spe = new StaSpecialExecute();
                            spe.setMemo(vs.getMemo());
                            spe.setType(StaSpecialExecuteType.valueOf(vs.getType()));
                            spe.setSta(sta);
                            staSpecialExecutedDao.save(spe);
                        }
                    }
                    if (wpl != null) {
                        for (WmsSalesOrderPaymentQueue wp : wpl) {
                            StaPayment sp = new StaPayment();
                            sp.setAmt(wp.getAmt());
                            sp.setType(PaymentType.valueOf(wp.getType()));
                            sp.setMemo(wp.getMemo());
                            staPaymentDao.save(sp);
                        }
                    }
                    Map<Long, List<WmsOrderInvoiceLineQueue>> mi = new HashMap<Long, List<WmsOrderInvoiceLineQueue>>();
                    BigDecimal t = new BigDecimal(0);
                    BigDecimal d = new BigDecimal(0);
                    // String diLpCode = null;
                    // bug修复 指定物流商
                    String diLpCode = wq.getTransCode();
                    List<WmsSalesOrderLineQueue> ll = wmsSalesOrderLineQueueDao.findLineByOuAndOrderId(ouId, wq.getId(), new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
                    if (ll != null) {
                        for (WmsSalesOrderLineQueue lq : ll) {
                            if (diLpCode == null) {
                                diLpCode = lq.getLpCode();
                            }
                            StaLine sl = new StaLine();
                            Sku sku = skuDao.getByPrimaryKey(lq.getSkuId());
                            sl.setSta(sta);
                            sl.setSku(sku);
                            sl.setOwner(lq.getOwner());
                            sl.setQuantity(lq.getQty());
                            Sku sku1 = skuDao.getByPrimaryKey(lq.getSkuId());
                            sl.setSkuName(lq.getSkuName() == null ? sku1.getName() : lq.getSkuName());
                            sl.setColor(lq.getColor() == null ? sku1.getColor() : lq.getColor());
                            sl.setLineSize(lq.getSkuSize() == null ? sku1.getSkuSize() : lq.getSkuSize());
                            sl.setTotalActual(lq.getLineAmt());
                            sl.setOrderTotalActual(lq.getLineAmt());
                            if (lq.getLineAmt() != null) {
                                t = t.add(lq.getLineAmt());
                            }
                            sl.setLineDiscount(lq.getLineDiscount());
                            lq.setLineDiscount(lq.getLineDiscount() == null ? new BigDecimal(0) : lq.getLineDiscount());
                            d = d.add(lq.getLineDiscount() == null ? new BigDecimal(0) : lq.getLineDiscount());
                            sl.setListPrice(lq.getListPrice());
                            sl.setUnitPrice(lq.getUnitPrice());
                            sl.setLineSize(lq.getSkuSize()); // 商品尺寸
                            sl.setColor(lq.getColor()); // 商品颜色
                            sl.setOrderQty(lq.getOrderQty()); // 平台订单计划量
                            sl.setLineNo(lq.getLineNo());
                            sl.setOrderLineNo(lq.getOrderLineNo()); // 平台订单行号
                            staLineDao.save(sl);

                            if (StringUtils.hasText(bichannel.getVmiCode())) {
                                if (bichannel.getVmiCode().equals("3251")) {
                                    if (lq.getIsGift() == false) {
                                        BiChannel bichannelLine = biChannelDao.getByCode(lq.getOwner());
                                        NikeTurnCreate nikeTurnCreate = new NikeTurnCreate();
                                        nikeTurnCreate.setCreateTime(new Date());
                                        nikeTurnCreate.setFromLocation(bichannelLine.getVmiCode());
                                        nikeTurnCreate.setSku(sku);
                                        nikeTurnCreate.setQuantity(lq.getQty());
                                        nikeTurnCreate.setStaCode(sta.getCode());
                                        nikeTurnCreate.setToLocation(bichannel.getVmiCode());
                                        nikeTurnCreate.setStatus(1);
                                        nikeTurnCreate.setReferenceNo(turnCode);
                                        createDao.save(nikeTurnCreate);
                                    }
                                }
                            }
                            WmsShippingLineQueue wslq = new WmsShippingLineQueue();
                            wslq.setLineNo(lq.getLineNo());
                            wslq.setShippingQueue(wsq);
                            wslq.setSku(lq.getSku());
                            wslq.setQty(lq.getQty());
                            wmsShippingLineQueueDao.save(wslq);
                            List<WmsOrderInvoiceLineQueue> wil = wmsOrderInvoiceLineQueueDao.getAllInvoiceLineByLineNo(wq.getId(), lq.getLineNo(), new BeanPropertyRowMapper<WmsOrderInvoiceLineQueue>(WmsOrderInvoiceLineQueue.class));
                            if (wil != null) {
                                for (WmsOrderInvoiceLineQueue wi : wil) {
                                    Long hId = wi.getId();// 此时为头ID
                                    if (mi.containsKey(hId)) {
                                        wi.setId(sl.getId());// 此时更新为sta line ID 记录数据库用
                                        mi.get(hId).add(wi);
                                    } else {
                                        List<WmsOrderInvoiceLineQueue> ml = new ArrayList<WmsOrderInvoiceLineQueue>();
                                        wi.setId(sl.getId());// 此时更新为sta line ID 记录数据库用
                                        ml.add(wi);
                                        mi.put(hId, ml);
                                    }
                                }
                            }
                            // 礼品卡等信息
                            List<ValueAddedServiceQueue> addedServiceQueue = valueAddedServiceQueueDao.getAllAddedServiceByOrderLineId(lq.getId(), new BeanPropertyRowMapper<ValueAddedServiceQueue>(ValueAddedServiceQueue.class));
                            if (addedServiceQueue != null && addedServiceQueue.size() > 0) {
                                for (ValueAddedServiceQueue giftLine : addedServiceQueue) {
                                    GiftLine line = new GiftLine();
                                    line.setMemo(giftLine.getMemo());
                                    line.setSanCardCode(giftLine.getCardCode());
                                    line.setStaLine(sl);
                                    sta.setSpecialType(SpecialSkuType.NORMAL);
                                    if (giftLine.getType() == 50010) {
                                        line.setType(GiftType.NIKE_GIFT);
                                    } else if (giftLine.getType() == 50030) {
                                        line.setType(GiftType.GIFT_PACKAGE);
                                    } else if (giftLine.getType() == 50050) {// 商品印刷
                                        line.setType(GiftType.GIFT_PRINT);
                                    } else {
                                        line.setType(GiftType.valueOf(giftLine.getType()));
                                    }

                                    giftLineDao.save(line);
                                }
                            }
                        }
                    }
                    if (t != null && 0 == t.compareTo(BigDecimal.ZERO)) {
                        sta.setTotalActual(wq.getOrderAmt());
                        sta.setOrderTotalActual(wq.getOrderAmt());
                        di.setInsuranceAmount(wq.getOrderAmt());// 保价金额
                    } else {
                        sta.setTotalActual(t);
                        sta.setOrderTotalActual(t);
                        di.setInsuranceAmount(t);// 保价金额
                    }
                    sta.setOrderDiscount(d);
                    di.setLpCode(diLpCode);
                    wsq.setTransCode(diLpCode);
                    // 记录发票
                    DecimalFormat CODE_FORMAT = new DecimalFormat("00");
                    int ivNO = 1;
                    String vmemo = sta.getRefSlipCode() + "_IV_";
                    if (mi != null && mi.isEmpty() == false) {
                        di.setStoreComIsNeedInvoice(true);
                    }
                    if (wq.getOwner().equals("5Nike-Global Inline官方商城") || wq.getOwner().equals("5Nike-Global Swoosh 官方商城") || wq.getOwner().equals("1NIKE-GLOBLE官方旗舰店")) {
                        List<WmsOrderInvoiceQueue> iqs = wmsOrderInvoiceQueueDao.findBySoId(wq.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceQueue>(WmsOrderInvoiceQueue.class));
                        for (WmsOrderInvoiceQueue iq : iqs) {
                            StaInvoice si = new StaInvoice();
                            si.setInvoiceCode(iq.getInvoiceCode());
                            si.setSta(sta);
                            si.setDrawer(iq.getDrawer());
                            si.setInvoiceDate(iq.getInvoiceDate());
                            si.setPayer(iq.getPayer());
                            si.setPayee(iq.getPayee());
                            si.setItem(iq.getItem());
                            si.setCompany(iq.getCompany());
                            si.setQty(iq.getQty().intValue());
                            si.setAmt(iq.getAmt());
                            si.setAddress(iq.getAddress());
                            si.setIdentificationNumber(iq.getIdentificationNumber());
                            si.setTelephone(iq.getTelephone());
                            staInvoiceDao.save(si);
                            List<WmsOrderInvoiceLineQueue> invoiceLineQueue = wmsOrderInvoiceLineQueueDao.findByInvoiceId(iq.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceLineQueue>(WmsOrderInvoiceLineQueue.class));
                            for (WmsOrderInvoiceLineQueue il : invoiceLineQueue) {
                                StaInvoiceLine sil = new StaInvoiceLine();
                                sil.setAmt(il.getAmt());
                                sil.setItem(il.getItem());
                                sil.setStaInvoice(si);
                                sil.setUnitPrice(il.getUnitPrice());
                                sil.setQty(Integer.parseInt(new Long(il.getQty()).toString()));
                                sil.setLineNO(il.getLineNo());
                                staInvoiceLineDao.save(sil);
                            }
                        }

                    } else {
                        for (Long inId : mi.keySet()) {
                            String memo = vmemo + CODE_FORMAT.format(ivNO) + "/";
                            ivNO++;
                            WmsOrderInvoiceQueue iq = wmsOrderInvoiceQueueDao.getByPrimaryKey(inId);
                            StaInvoice si = new StaInvoice();
                            si.setInvoiceCode(iq.getInvoiceCode());
                            si.setSta(sta);
                            si.setDrawer(iq.getDrawer());
                            si.setInvoiceDate(iq.getInvoiceDate());
                            si.setPayer(iq.getPayer());
                            si.setPayee(iq.getPayee());
                            si.setItem(iq.getItem());
                            si.setCompany(iq.getCompany());
                            si.setQty(iq.getQty().intValue());
                            si.setAddress(iq.getAddress());
                            si.setIdentificationNumber(iq.getIdentificationNumber());
                            si.setTelephone(iq.getTelephone());

                            staInvoiceDao.save(si);
                            BigDecimal sumAmt = new BigDecimal(0);
                            // 计算发票总金额
                            List<WmsOrderInvoiceLineQueue> ilq = mi.get(inId);
                            for (WmsOrderInvoiceLineQueue il : ilq) {
                                StaInvoiceLine sil = new StaInvoiceLine();
                                sil.setAmt(il.getAmt());
                                sumAmt = sumAmt.add(sil.getAmt());
                                sil.setItem(il.getItem());
                                sil.setStaInvoice(si);
                                sil.setUnitPrice(il.getUnitPrice());
                                sil.setQty(Integer.parseInt(new Long(il.getQty()).toString()));
                                sil.setLineNO(il.getLineNo());
                                memo += construnctSkuMemo(il.getUnitPrice(), il.getItem()) + il.getQty().intValue() + "/";
                                staInvoiceLineDao.save(sil);
                            }
                            si.setAmt(sumAmt);
                            si.setUnitPrice(sumAmt);
                            try {
                                si.setMemo(subStr(memo, 230));
                            } catch (UnsupportedEncodingException e) {
                                log.error("meme too long error....." + wq.getOrderCode());
                                throw new BusinessException("memo too long .........");
                            }
                        }
                    }

                    List<LitreSingle> litreSingles = litreSingleDao.findmainWarehouseOwner(ouId, wq.getOwner(), wq.getProvince(), wq.getCity(), wq.getDistrict(), new BeanPropertyRowMapper<LitreSingle>(LitreSingle.class));
                    if (litreSingles.size() > 0) {
                        if ("SF".equals(di.getLpCode())) { // 时效升级只针对顺丰快递
                            try {
                                Date date = new Date();
                                // 判断时效为普通类型
                                LitreSingle rule = litreSingles.get(0);
                                String[] timeOne = null;
                                String[] timeTwo = null;
                                rule.setTotalActual(rule.getTotalActual() == null ? new BigDecimal(0) : rule.getTotalActual());
                                if (rule.getStartTime() != null && rule.getEndTime() != null) {
                                    timeOne = rule.getStartTime().split("-");
                                    timeTwo = rule.getEndTime().split("-");
                                }
                                String[] timeOneStart = timeOne[0].split(":");
                                String[] timeOneEnd = timeOne[1].split(":");
                                String[] timeTwoStart = timeTwo[0].split(":");
                                String[] timeTwoEnd = timeTwo[1].split(":");
                                Integer timeOneStartHour = Integer.parseInt(timeOneStart[0]);
                                Integer timeOneStartMinutes = Integer.parseInt(timeOneStart[1]);
                                Integer timeOneEndHour = Integer.parseInt(timeOneEnd[0]);
                                Integer timeOneEndMinutes = Integer.parseInt(timeOneEnd[1]);
                                Integer timeTwoStartHour = Integer.parseInt(timeTwoStart[0]);
                                Integer timeTwoStartMinutes = Integer.parseInt(timeTwoStart[1]);
                                Integer timeTwoEndHour = Integer.parseInt(timeTwoEnd[0]);
                                Integer timeTwoEndMinutes = Integer.parseInt(timeTwoEnd[1]);

                                if (wq.getTransTimeType() == 1) {
                                    if (date.getHours() >= timeOneStartHour && date.getHours() <= timeOneEndHour) {
                                        if (date.getHours() == timeOneStartHour && date.getMinutes() < timeOneStartMinutes) {
                                            di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                                        } else if (date.getHours() == timeOneEndHour && date.getMinutes() > timeOneEndMinutes) {
                                            di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                                        } else {
                                            if (sta.getTotalActual().add(di.getTransferFee()).compareTo(rule.getTotalActual()) == 1) {
                                                di.setTransTimeType(TransTimeType.SAME_DAY);
                                            } else {
                                                di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                                            }
                                        }
                                    } else if (date.getHours() >= timeTwoStartHour && date.getHours() <= timeTwoEndHour) {
                                        if (date.getHours() == timeTwoStartHour && date.getMinutes() < timeTwoStartMinutes) {
                                            di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                                        } else if (date.getHours() == timeTwoEndHour && date.getMinutes() > timeTwoEndMinutes) {
                                            di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                                        } else {
                                            if (sta.getTotalActual().add(di.getTransferFee()).compareTo(rule.getTotalActual()) == 1) {
                                                di.setTransTimeType(TransTimeType.SAME_DAY);
                                            } else {
                                                di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                                            }
                                        }
                                    } else {
                                        di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                                    }
                                } else {
                                    di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                                }
                            } catch (Exception e) {
                                log.error("Upgrade Express Error:" + wq.getOrderCode());
                                di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                            }
                        } else {
                            di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                        }
                    } else {
                        di.setTransTimeType(TransTimeType.valueOf(wq.getTransTimeType()));
                    }
                    staDao.flush();
                    staLineDao.flush();
                    staDeliveryInfoDao.flush();
                 /*   if (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging()) {
                        Boolean b = staLineDao.checkIsSpecailSku(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
                        if (b) {
                            sta.setIsSpecialPackaging(true);
                        }
                    }*/
                    try {
                        eventObserver.onEvent(new TransactionalEvent(sta));
                    } catch (BusinessException e) {
                        throw e;
                    }
                    wsq.setTransCode(sta.getStaDeliveryInfo().getLpCode());
                    // SF时效（云仓）
                    try {
                        if (Transportator.SF.equals(sta.getStaDeliveryInfo().getLpCode()) && TransTimeType.ORDINARY.equals(sta.getStaDeliveryInfo().getTransTimeType()) && StringUtil.isEmpty(wq.getOriginalLpCode())) {

                            BiChannel b = biChannelDao.getByCode(sta.getOwner());
                            SfCloudWarehouseConfig sfCloudWarehouse =
                                    sfCloudWarehouseConfigDao
                                            .findCloudConfigByOu(ouId, b.getId(), sta.getStaDeliveryInfo().getProvince(), sta.getStaDeliveryInfo().getCity(), new BeanPropertyRowMapper<SfCloudWarehouseConfig>(SfCloudWarehouseConfig.class)); // findCloudConfigByOu(ouId,
                            if (sfCloudWarehouse == null) {// 匹配改省，全部市
                                SfCloudWarehouseConfig sfCloudWarehouse2 =
                                        sfCloudWarehouseConfigDao.findCloudConfigByOu(ouId, b.getId(), sta.getStaDeliveryInfo().getProvince(), "全部", new BeanPropertyRowMapper<SfCloudWarehouseConfig>(SfCloudWarehouseConfig.class)); // findCloudConfigByOu(ouId,
                                if (sfCloudWarehouse2 == null) {} else {
                                    if (sfCloudWarehouse2.getTimeType() == null) {} else {
                                        di.setTransTimeType(TransTimeType.valueOf(sfCloudWarehouse2.getTimeType()));
                                    }
                                }
                            } else {
                                if (sfCloudWarehouse.getTimeType() == null) {} else {
                                    di.setTransTimeType(TransTimeType.valueOf(sfCloudWarehouse.getTimeType()));
                                }
                            }

                        }
                    } catch (Exception e) {
                        log.error("sfTimeType.....RefSlipCode:=" + sta.getRefSlipCode());
                    }
                    // ///
                }
            }
        } else {
            WmsConfirmOrderQueue wcq = new WmsConfirmOrderQueue();
            if (isMq) {
                wcq.setIsMq("1");
            }
            wcq.setSystemKey(wq.getSystemKey());
            wcq.setCreateTime(new Date());
            wcq.setIsDs(false);
            wcq.setOrderCode(wq.getOrderCode());
            wcq.setStatus(false);
            wcq.setOwner(wq.getOwner());
            wcq.setIsmeet(wq.getIsmeet());
            wcq.setOrderType(wq.getOrderType());
            wcq.setMemo(wq.getFlagResult());
            wcq.setOrderSourcePlatform(wq.getOrderSourcePlatform());
            if (StringUtils.hasText(wq.getFlagResult())) {
                if (wq.getFlagResult().equals("订单无法匹配合适物流商!")) {
                    wcq.setErrorStatus("E000002");
                } else if (wq.getFlagResult().equals("指定物流找不到合适发货仓!")) {
                    wcq.setErrorStatus("E000003");
                } else if (wq.getFlagResult().equals("WMS未找到合适发货仓")) {
                    wcq.setErrorStatus("E000004");
                } else {
                    wcq.setErrorStatus("E000001");
                }
            } else {
                wcq.setErrorStatus("Y000001");
            }

            wmsConfirmOrderQueueDao.save(wcq);
            if (wq.getSystemKey().equals("adidas")) {
                MsgOutboundOrderCancel msg = new MsgOutboundOrderCancel();
                msg.setCreateTime(new Date());
                msg.setErrorCount(0L);
                msg.setIsCanceled(true);
                msg.setSystemKey("adidas");
                msg.setSource("adidas");
                msg.setStatus(MsgOutboundOrderCancelStatus.CREATED);
                msg.setUpdateTime(new Date());
                msg.setStaCode(wq.getOrderCode());
                msg.setSilpCode1(wq.getSourceOrderCode());
                msg.setWarehouseCode(wq.getWarehouseCode());
                String lpcode = "";
                List<WmsSalesOrderLineQueue> ll = wmsSalesOrderLineQueueDao.findLineByOuAndOrderId(wq.getOu_id(), wq.getId(), new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
                for (WmsSalesOrderLineQueue lq : ll) {
                    StaCancel cancel = new StaCancel();
                    lpcode = lq.getLpCode();
                    cancel.setCreateDate(new Date());
                    cancel.setQuantity(lq.getQty());
                    cancel.setTotalActual(lq.getLineAmt());
                    cancel.setUnitPrice(lq.getUnitPrice());
                    cancel.setOwner(lq.getOwner());
                    cancel.setOrderCancel(msg);
                    cancel.setOrderSourcePlatform(wq.getOrderSourcePlatform());
                    cancel.setOrderLineNo(lq.getOrderLineNo());
                    cancel.setIsShortPicking(wq.getIsShortPicking());
                    cancelDao.save(cancel);
                }
                msg.setTransCode(lpcode);
                msg.setTransNo(wq.getTransNo());
                msgOutboundOrderCancelDao.save(msg);

            }
            if (Constants.CAINIAO_DB_SYSTEM_KEY.equals(wq.getSystemKey())) {
                CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(wcq.getOrderCode());
                if (cnOrderProperty != null) {
                    CnWmsOrderStatusUpload su = new CnWmsOrderStatusUpload();
                    su.setOrderType(cnOrderProperty.getOrderType());
                    su.setOrderCode(wcq.getOrderCode());
                    su.setContent(wcq.getMemo());
                    su.setOperator("宝尊仓库");
                    su.setOperateDate(new Date());
                    su.setStatus("WMS_REJECT");
                    su.setUploadStatus("0");
                    cnWmsOrderStatusUploadDao.save(su);
                }
            }
        }
        createLogByOrder(id);
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.createStaById....End id:" + id);
        }
    }



    private String subStr(String str, int subSLength) throws UnsupportedEncodingException {
        if (str == null)
            return "";
        else {
            int tempSubLength = subSLength;// 截取字节数
            String subStr = str.substring(0, str.length() < subSLength ? str.length() : subSLength);// 截取的子串
            int subStrByetsL = subStr.getBytes("GBK").length;// 截取子串的字节长度
            // int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度
            // 说明截取的字符串中包含有汉字
            while (subStrByetsL > tempSubLength) {
                int subSLengthTemp = --subSLength;
                subStr = str.substring(0, subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
                subStrByetsL = subStr.getBytes("GBK").length;
            }
            return subStr;
        }
    }

    // 单品sku描述 100.00* 2/
    public static String construnctSkuMemo(BigDecimal price, String skuName) {
        String postfix = " " + price.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "*";
        if ((skuName.length() + postfix.length()) > 218) { // 单品sku描述字符串超长时，sku名称取一半
            skuName = skuName.substring(0, skuName.length() / 2) + "...";
        }
        skuName = skuName.replaceAll("/", " ");
        skuName = skuName.replaceAll("\n", " ");
        return skuName + postfix;
    }

    /**
     * 转化JSON数据，记录日志信息
     * 
     * @param id
     */
    private void createLogByOrder(Long id) {
        orderWarehousePriorityDao.cleanDataByOrderId(id);
        wmsOrderInvoiceLineQueueDao.cleanDataByOrderId(id);
        wmsOrderInvoiceQueueDao.cleanDataByOrderId(id);
        valueAddedServiceQueueDao.cleanDataByOrderId(id);
        valueAddedServiceQueueDao.cleanDataByOrderLineId(id);
        wmsOrderPackingQueueDao.cleanDataByOrderId(id);
        wmsSalesOrderPaymentQueueDao.cleanDataByOrderId(id);
        wmsSalesOrderLineQueueDao.cleanDataByOrderId(id);
        int i = wmsSalesOrderQueueDao.cleanDataByOrderId(id);
        if (i == 0) {
            log.error("并发导致问题" + id);
            throw new BusinessException("");
        }
    }

    @Override
    public void setCanCreateAndEndFlagForOrder() {

    }



    @Override
    public List<WmsSalesOrderQueue> getAllOrderHaveFlag() {
        return wmsSalesOrderQueueDao.findAllOrderHaveFlag(new BeanPropertyRowMapper<WmsSalesOrderQueue>(WmsSalesOrderQueue.class));
    }

    @Override
    public void setDefaultCanFlagIsFalse(Long id) {
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        if (null != q && null != q.getCanFlag() && !q.getCanFlag()) {
            createTomsOrdersendToMq(q.getId());
        }
    }

    /**
     * 第一步：获取推荐物流 第二步：判断是否指定仓库，指定仓库走第三步，非指定仓库走第四步 第三步：如果指定物流，且仓库不强制校验物流
     */
    @Override
    public void setDefaultWarehouseById(Long id) {
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.setDefaultWarehouseById.....Enter id:" + id);
        }
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        String infoMsg = "setDefaultWarehouseById-Order:" + q.getOrderCode();
        Map<Long, String> tl = transSuggestManager.suggestTransServiceForOrder(id);
        if (tl.size() == 0) {
            if (log.isDebugEnabled()) {
                log.debug(infoMsg + "-SuggestTrans:null");
            }
        } else {
            StringBuffer temp = new StringBuffer("");
            for (Long l : tl.keySet()) {
                temp = temp.append(l + ":" + tl.get(l) + ";");
            }
            if (log.isDebugEnabled()) {
                log.debug(infoMsg + "-SuggestTrans:" + temp);
            }
        }
        BiChannel c = biChannelDao.getByCode(q.getOwner());
        List<WarehousePriority> wl = rmiService.getPriorityWHByCity(q.getProvince(), q.getCity(), q.getCus_id(), c.getId());
        if (wl == null || wl.size() == 0) {
            if (log.isDebugEnabled()) {
                log.debug(infoMsg + "-PriorityWH:null");
            }
        } else {
            StringBuffer temp = new StringBuffer("");
            for (WarehousePriority l : wl) {
                temp = temp.append(l.getOuId() + ":" + l.getPriority() + ";");
            }
            if (log.isDebugEnabled()) {
                log.debug(infoMsg + "-PriorityWH:" + temp);
            }
        }
        if (q.getWarehouseCode() == null) {
            if (q.getTransCode() != null) {
                if (tl.size() == 0) {
                    q.setCanFlag(false);
                    q.setFlagResult("指定物流找不到合适发货仓!");
                } else {
                    if (wl == null || wl.size() == 0) {
                        q.setCanFlag(false);
                        q.setFlagResult("仓库优先级发货规则未找到合适发货仓");
                    } else {
                        for (WarehousePriority wp : wl) {
                            if (tl.containsKey(wp.getOuId())) {
                                OrderWarehousePriority op = new OrderWarehousePriority();
                                op.setWmsSalesOrderQueue(q);
                                op.setOuId(wp.getOuId());
                                op.setNo(wp.getPriority());
                                op.setLpCode(q.getTransCode());
                                orderWarehousePriorityDao.save(op);
                            } else {
                                Warehouse w = warehouseDao.getByOuId(wp.getOuId());
                                if (w.getIsTransMust() == null || !w.getIsTransMust()) {
                                    OrderWarehousePriority op = new OrderWarehousePriority();
                                    op.setWmsSalesOrderQueue(q);
                                    op.setOuId(wp.getOuId());
                                    op.setNo(wp.getPriority());
                                    op.setLpCode(q.getTransCode());
                                    orderWarehousePriorityDao.save(op);
                                }
                            }
                        }
                    }
                }
            } else {
                if (tl.size() == 0) {
                    q.setCanFlag(false);
                    q.setFlagResult("订单无法匹配合适物流商!");
                } else {
                    if (wl == null || wl.size() == 0) {
                        q.setCanFlag(false);
                        q.setFlagResult("仓库优先级发货规则未找到合适发货仓");
                    } else {
                        for (WarehousePriority wp : wl) {
                            if (tl.containsKey(wp.getOuId())) {
                                OrderWarehousePriority op = new OrderWarehousePriority();
                                op.setWmsSalesOrderQueue(q);
                                op.setOuId(wp.getOuId());
                                op.setNo(wp.getPriority());
                                op.setLpCode(tl.get(wp.getOuId()));
                                orderWarehousePriorityDao.save(op);
                            } else {
                                Warehouse w = warehouseDao.getByOuId(wp.getOuId());
                                if (w.getIsTransMust() == null || !w.getIsTransMust()) {
                                    OrderWarehousePriority op = new OrderWarehousePriority();
                                    op.setWmsSalesOrderQueue(q);
                                    op.setOuId(wp.getOuId());
                                    op.setNo(wp.getPriority());
                                    orderWarehousePriorityDao.save(op);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Warehouse w = warehouseDao.findWarehouseByOUCode(q.getWarehouseCode());
            if (w.getIsTransMust() == null || !w.getIsTransMust()) {

            } else {
                if (q.getTransCode() != null) {
                    if (tl.size() == 0) {
                        q.setCanFlag(false);
                        q.setFlagResult("指定物流仓库不支持!");
                    }
                    // 否则，代表给定的物流校验通过
                } else {
                    if (tl.size() == 0) {
                        q.setCanFlag(false);
                        q.setFlagResult("未找到可用物流商!请关注仓库物流配置。");
                    } else {
                        // 使用推荐物流，系统逻辑只会给出一个推荐物流，可做如下转换
                        List<String> sl = new ArrayList<String>();
                        sl.addAll(tl.values());
                        // List<String> sl = (List<String>) tl.values();
                        q.setTransCode(sl.get(0));
                    }
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.setDefaultWarehouseById.....End id:" + id);
        }
    }

    /**
     * 根据仓库查询需要设置标识的单子
     */
    @Override
    public List<WmsSalesOrderQueue> getToSetFlagOrderByWarehouse(Long id) {
        return wmsSalesOrderQueueDao.getOrderToSetFlagByOuId(id, new BeanPropertyRowMapper<WmsSalesOrderQueue>(WmsSalesOrderQueue.class));
    }

    /**
     * 直连过仓计算库存
     */
    @Override
    public void setFlagToOrderNew(Long id) {
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.setFlagToOrderNew...Enter id:" + id);
        }
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.setFlagToOrderNew  orderCode:" + q.getOrderCode());
        }
        List<WmsSalesOrderLineQueue> wlql = wmsSalesOrderLineQueueDao.getOrderLineQueueById(q.getId(), new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
        Map<Long, Long> lineOU = new HashMap<Long, Long>();
        List<Long> zmemo = new ArrayList<Long>();// 放置可能库存不足的赠品存放id
        List<String> memo = new ArrayList<String>();// 存储库存不足等失败信息
        List<MongoDBInventory> ml = new ArrayList<MongoDBInventory>();// 保存最后需要更新的mongoDB库存
        // List<String> admemo = new ArrayList<String>();// 存储库存AD不足等失败信息
        // List<Long> adId = new ArrayList<Long>();// 放置可能库存不足的短配id
        // Map<String, Boolean> adStatus = new HashMap<String, Boolean>();
        // 获取出库单出库仓列表
        boolean qtyCheck = false;
        boolean soqCheck = true;
        if (q.getWarehouseCode() != null) {
            // 指定仓
            // 判断库存设置标识
            singleWarehouseInvCheckNew(wlql, zmemo, memo, ml, lineOU, q.getWarehouseCode());
            if (memo.size() > 0) {
                q.setFlagResult(getSubStr(MessageFormat.format(INVNOTENOUGH, MessageFormat.format(INVNOTENOUGH_W, q.getWarehouseCode(), getStringByStringList(",", memo.toArray())))));
                q.setCanFlag(false);
            } else {
                // 更新MongoDB库存
                boolean b = updateInfoWhenCanCreateNew(ml, lineOU, zmemo, q.getId());
                if (!b) {
                    // 更新库存失败 整单失败
                    // 更新库存失败 需要重新计算
                    q.setFlagResult(UPDATEINVERROR);
                    q.setCanFlag(false);
                }
            }
        } else {
            List<String> list = getOuListByOrderId(q.getId());
            // 非指定仓 预先校验明细商品数量是否所有仓库总和满足
            for (WmsSalesOrderLineQueue wlq : wlql) {
                // 单行商品数量
                Long qty = wlq.getQty();
                Long total = 0L;
                for (String ouCode : list) {
                    // 循环所有仓库存
                    // System.out.println(ouCode + "-" + wlq.getOwner() + "-" + wlq.getSkuId());
                    MongoDBInventory inv = mongoDBInventoryManager.getInventory(ouCode, wlq.getOwner(), wlq.getSkuId());
                    // System.out.println(inv.getAvailQty());
                    if (null == inv) {
                        // 没有库存循环下个仓库
                        continue;
                    } else {
                        if (inv.getAvailQty() > 0) {
                            total = total + inv.getAvailQty();
                        }
                        if (total >= qty) {
                            // 如果数量满足 直接跳出
                            qtyCheck = true;
                            break;
                        } else {
                            continue;
                        }
                    }
                }
                if (!qtyCheck) {
                    // 赠品不计算库存
                    if (wlq.getIsGift()) {
                        zmemo.add(wlq.getId());
                    } else {
                        // 单行库存不满足 整单失败
                        soqCheck = false;
                        // String s = "";
                        // if (wlq.getOwner().equals("5Nike-Global Inline官方商城") ||
                        // wlq.getOwner().equals("5Nike-Global Swoosh 官方商城") ||
                        // wlq.getOwner().equals("1NIKE-GLOBLE官方旗舰店")) {
                        // s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty() -
                        // total);
                        // } else {
                        // s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                        // }
                        String s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty() - total);
                        memo.add(s);
                    }
                }
            }
            if (!soqCheck) {
                // 订单缺少库存修改对应错误信息
                q.setFlagResult(getSubStr(MessageFormat.format(INVNOTENOUGH, MessageFormat.format(INVNOTENOUGH_W, "", getStringByStringList(",", memo.toArray())))));
                q.setCanFlag(false);
            } else {
                boolean ouInvCheck = false;
                // 不缺少库存 逐仓计算库存 占用库存
                // 不指定仓 循环所有仓进行占用
                for (String ouCode : list) {
                    // 判断库存设置标识
                    ml.clear();
                    lineOU.clear();
                    memo.clear();
                    boolean isSuccess = singleWarehouseInvCheckNew(wlql, zmemo, memo, ml, lineOU, ouCode);
                    if (!isSuccess) {
                        continue;
                        // }
                        // if (memo.size() > 0) {
                    } else {
                        // 更新MongoDB库存
                        boolean b = updateInfoWhenCanCreateNew(ml, lineOU, zmemo, q.getId());
                        if (!b) {
                            // 更新库存失败 整单失败
                            // 更新库存失败 需要重新计算
                            memo.add(UPDATEINVERROR);
                            continue;
                        } else {
                            // 更新成功直接结束任务
                            ouInvCheck = true;
                            break;
                        }
                    }
                }
                if (!ouInvCheck) {
                    // 所有仓整仓占用失败
                    if (q.isAllowDS()) {
                        // 允许分仓逻辑
                        // 清除memo数据
                        memo.clear();
                        ml.clear();
                        lineOU.clear();
                        for (WmsSalesOrderLineQueue wq : wlql) {
                            Boolean isEnough = false;// 标识是否该行库存不足
                            List<String> smsg = new ArrayList<String>();
                            for (String ou : list) {
                                OperationUnit unit = ouDao.getByCode(ou);
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....Params wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                }
                                MongoDBInventory mv = mongoDBInventoryManager.getInventory(ou, wq.getOwner(), wq.getSkuId());
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....End wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                    log.info("MongoDBQueryResult:" + (mv == null ? "NULL" : mv.getAvailQty()));
                                }
                                if (mv == null) {
                                    String msg = MessageFormat.format(INVNOTENOUGH_S_D, ou, wq.getQty());
                                    smsg.add(msg);
                                } else {
                                    if (mv.getAvailQty() < wq.getQty()) {
                                        String msg = MessageFormat.format(INVNOTENOUGH_S_D, ou, wq.getQty() - mv.getAvailQty());
                                        smsg.add(msg);
                                    } else {
                                        isEnough = true;
                                        MongoDBInventory mi = new MongoDBInventory();
                                        mi.setWhCode(ou);
                                        mi.setOwner(wq.getOwner());
                                        mi.setSkuId(wq.getSkuId());
                                        mi.setAvailQty(wq.getQty());// 此处只是用来代替变化量 就是当前计划量
                                        ml.add(mi);
                                        lineOU.put(wq.getId(), unit.getId());
                                        break;
                                    }
                                }
                            }
                            if (!isEnough) {
                                if (wq.getIsGift()) {
                                    zmemo.add(wq.getId());
                                } else {
                                    String lm = MessageFormat.format(INVNOTENOUGH_S, wq.getSku(), getStringByStringList(",", smsg.toArray()));
                                    memo.add(lm);
                                }
                            }
                        }
                        if (memo.size() > 0) {
                            String flagResult = MessageFormat.format(INVNOTENOUGH, getStringByStringList(";", memo.toArray()));
                            q.setFlagResult(getSubStr(flagResult));
                            q.setCanFlag(false);
                        } else {
                            // 更新MongoDB库存
                            boolean b = updateInfoWhenCanCreateNew(ml, lineOU, zmemo, q.getId());
                            if (!b) {
                                // 更新库存失败 整单失败
                                // 更新库存失败 需要重新计算
                                q.setFlagResult(UPDATEINVERROR);
                                q.setCanFlag(false);
                            }
                        }
                    } else {
                        // 不允许分仓 直接保存错误信息 结束出库单本次操作
                        q.setFlagResult(getSubStr(MessageFormat.format(INVNOTENOUGH, MessageFormat.format(INVNOTENOUGH_W, "", getStringByStringList(",", memo.toArray())))));
                        q.setCanFlag(false);
                    }
                }
            }
        }
    }

    /**
     * 单仓库存校验
     */
    private boolean singleWarehouseInvCheckNew(List<WmsSalesOrderLineQueue> wlql, List<Long> zmemo, List<String> memo, List<MongoDBInventory> ml, Map<Long, Long> lineOU, String ouCode) {
        OperationUnit unit = ouDao.getByCode(ouCode);
        Map<String, MongoDBInventory> map = new HashMap<String, MongoDBInventory>();
        boolean b = true;
        for (WmsSalesOrderLineQueue wlq : wlql) {
            String inventoryKey = unit.getCode() + "-" + wlq.getOwner() + "-" + wlq.getSkuId();
            // 原则上单仓单店必须只能查出最多一条数据
            MongoDBInventory inv = null;
            if (map.get(inventoryKey) != null) {
                inv = map.get(inventoryKey);
            } else {
                if (log.isInfoEnabled()) {
                    log.info("MongoDB queryBegin:------" + inventoryKey);
                }
                // if (log.isInfoEnabled()) {
                // log.info("CreateStaTaskManagerImpl.singleWarehouseInvCheckNew mongoDB " + ouCode
                // + "-" + wlq.getOwner() + "-" + wlq.getSkuId());
                // }
                // System.out.println(unit.getCode() + "-" + wlq.getOwner() + "-" + wlq.getSkuId());
                inv = mongoDBInventoryManager.getInventory(unit.getCode(), wlq.getOwner(), wlq.getSkuId());
                // System.out.println(inv.getAvailQty());
                if (log.isInfoEnabled()) {
                    log.info("MongoDB queryEnd:------" + inventoryKey);
                }
            }
            if (inv == null) {
                if (log.isInfoEnabled()) {
                    log.info("CreateStaTaskManagerImpl.singleWarehouseInvCheckNew mongoDB inv null" + ouCode + "-" + wlq.getOwner() + "-" + wlq.getSkuId());
                }
                if (wlq.getIsGift()) {
                    zmemo.add(wlq.getId());
                } else {
                    // String s = "";
                    // if (wlq.getOwner().equals("5Nike-Global Inline官方商城") ||
                    // wlq.getOwner().equals("5Nike-Global Swoosh 官方商城") ||
                    // wlq.getOwner().equals("1NIKE-GLOBLE官方旗舰店")) {
                    // s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                    // } else {
                    // s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                    // }
                    String s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                    memo.add(s);
                    b = false;
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("CreateStaTaskManagerImpl.singleWarehouseInvCheckNew mongoDB inv not null " + ouCode + "-" + wlq.getOwner() + "-" + wlq.getSkuId() + " qty:" + inv.getAvailQty());
                }
                if (inv.getAvailQty() < wlq.getQty()) {
                    if (wlq.getIsGift()) {
                        zmemo.add(wlq.getId());
                    } else {
                        // String s = "";
                        // if (wlq.getOwner().equals("5Nike-Global Inline官方商城") ||
                        // wlq.getOwner().equals("5Nike-Global Swoosh 官方商城") ||
                        // wlq.getOwner().equals("1NIKE-GLOBLE官方旗舰店")) {
                        // s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty() -
                        // inv.getAvailQty());
                        // } else {
                        // s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                        // }
                        String s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty() - inv.getAvailQty());
                        memo.add(s);
                        b = false;
                    }
                } else {
                    inv.setAvailQty(inv.getAvailQty() - wlq.getQty());
                    MongoDBInventory mi = new MongoDBInventory();
                    mi.setWhCode(unit.getCode());
                    mi.setOwner(wlq.getOwner());
                    mi.setSkuId(wlq.getSkuId());
                    mi.setAvailQty(wlq.getQty());// 此处只是用来代替变化量 就是当前计划量
                    ml.add(mi);
                    lineOU.put(wlq.getId(), unit.getId());
                }
            }
            map.put(inventoryKey, inv);
        }
        return b;
    }

    @Override
    public void setFlagToOrder(Long id, Long ouId) {
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.setFlagToOrder...Enter id:" + id + ",ouId:" + ouId);
        }
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        List<WmsSalesOrderLineQueue> wlql = wmsSalesOrderLineQueueDao.getOrderLineQueueById(q.getId(), new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
        Map<Long, Long> lineOU = new HashMap<Long, Long>();
        List<Long> zmemo = new ArrayList<Long>();// 放置可能库存不足的赠品存放id
        List<String> memo = new ArrayList<String>();// 存储库存不足等失败信息
        List<String> admemo = new ArrayList<String>();// 存储库存AD不足等失败信息
        List<Long> adId = new ArrayList<Long>();// 放置可能库存不足的短配id
        List<MongoDBInventory> ml = new ArrayList<MongoDBInventory>();// 保存最后需要更新的mongoDB库存
        Map<String, Boolean> adStatus = new HashMap<String, Boolean>();
        adStatus.put("flag", false);
        // boolean adStatus = false; // 判断是否创单
        /****************************************************************************** 头上指定仓逻辑 ******************************************************/
        if (q.getWarehouseCode() != null) {// 指定仓
            /**
             * 指定仓逻辑即订单头上指定仓库，对于此种订单，WMS只能按照指定仓库发货，严格校验所有明细行库存（除赠品行外）必须满足计划库存<br/>
             * 对于赠品行，如果其他行库存均满足，只有赠品行库存不足，则可以将赠品行剔除，该单标识可以创单<br/>
             * 算法：<br/>
             * 1、循环明细行，根据明细行指定的仓库、渠道、商品查询mongoDB库存<br/>
             * 2、校验系统库存跟计划库存差异，标识单行库存情况<br/>
             * 3、汇总所有行记录的库存满足情况标识整单<br/>
             * 4、更新mongoDB库存数量(剔除可能的赠品行)
             */
            // 判断库存设置标识
            singleWarehouseInvCheck(wlql, zmemo, memo, ml, lineOU, ouId, admemo, adId, q.getIsShortPicking() == null ? false : q.getIsShortPicking(), adStatus);
            if (memo.size() > 0) {
                q.setFlagResult(getSubStr(MessageFormat.format(INVNOTENOUGH, MessageFormat.format(INVNOTENOUGH_W, q.getWarehouseCode(), getStringByStringList(",", memo.toArray())))));
                q.setCanFlag(false);
                // wmsThreePLManager.createCnInvQtyDeficiency(q.getOrderCode());
            } else {
                // 成功更新信息
                if (adStatus.get("flag")) {
                    if (q.getSystemKey().equals("adidas")) {
                        updateInfoWhenCanCreateAD(ml, lineOU, adId, zmemo, q.getId());
                    } else {
                        updateInfoWhenCanCreate(ml, lineOU, zmemo, q.getId());
                    }
                } else {
                    q.setFlagResult(getSubStr(MessageFormat.format(INVNOTENOUGH, MessageFormat.format(INVNOTENOUGH_W, q.getWarehouseCode(), getStringByStringList(",", admemo.toArray())))));
                    q.setCanFlag(false);
                }

            }
        } else {// 不指定仓
            // 需要分仓的单仓判断已执行完，最后分仓判断
            Boolean b = false;// 标识明细行是否有指定仓库
            for (WmsSalesOrderLineQueue wlq : wlql) {
                if (wlq.getWarehouseCode() != null) {
                    b = true;
                    break;
                }
            }
            if (ouId == null) {
                if (q.isAllowDS()) {// 允许分仓逻辑
                    if (b) {
                        /**
                         * 明细行有仓库，允许分仓逻辑，首先执行单仓逻辑<br/>
                         * 1、先执行有仓明细行库存校验<br/>
                         * 1.1、一行明细行失败，直接跳出循环，走最终分仓逻辑<br/>
                         * 1.2、有仓明细行库存校验成功，走无仓明细行单仓逻辑<br/>
                         * 2、无仓明细行单仓逻辑<br/>
                         * 2.1无仓明细行单仓成功，标识订单可创，结束<br/>
                         * 2.2无仓明细行单仓失败，走最终分仓逻辑<br/>
                         */
                        // 1明细行有指定仓库的，之前逻辑不会走，执行后续逻辑
                        // 1.1 先走单仓逻辑，按仓循环
                        List<String> list = getOuListByOrderId(q.getId());
                        // 单仓，一条明细行失败，直接跳出，最后判断单仓不满足直接走分仓
                        Boolean lineEnough = true;// 判断指定仓库行的是否有库存，如果这个都不满足，不用走单仓，直接分仓逻辑封装库存失败信息
                        for (WmsSalesOrderLineQueue wq : wlql) {
                            if (wq.getWarehouseCode() != null) {
                                OperationUnit unit = ouDao.getByCode(wq.getWarehouseCode());
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....Params wh:" + wq.getWarehouseCode() + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                }
                                MongoDBInventory mv = mongoDBInventoryManager.getInventory(wq.getWarehouseCode(), wq.getOwner(), wq.getSkuId());
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....End wh:" + wq.getWarehouseCode() + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                    log.info("MongoDBQueryResult:" + (mv == null ? "NULL" : mv.getAvailQty()));
                                }
                                if (mv == null) {
                                    if (wq.getIsGift()) {
                                        zmemo.add(wq.getId());
                                    } else {
                                        lineEnough = false;
                                        break;
                                    }
                                } else {
                                    if (mv.getAvailQty() < wq.getQty()) {
                                        if (wq.getIsGift()) {
                                            zmemo.add(wq.getId());
                                        } else {
                                            lineEnough = false;
                                            break;
                                        }
                                    } else {
                                        MongoDBInventory mi = new MongoDBInventory();
                                        mi.setWhCode(wq.getWarehouseCode());
                                        mi.setOwner(wq.getOwner());
                                        mi.setSkuId(wq.getSkuId());
                                        mi.setAvailQty(wq.getQty());// 此处只是用来代替变化量 就是当前计划量
                                        ml.add(mi);
                                        lineOU.put(wq.getId(), unit.getId());
                                    }
                                }
                            }
                        }
                        if (lineEnough) {// 指定仓库行有库存，走单仓逻辑验证其他行
                            for (String ou : list) {
                                Boolean ouFlag = true;
                                OperationUnit unit = ouDao.getByCode(ou);
                                for (WmsSalesOrderLineQueue wq : wlql) {
                                    if (wq.getWarehouseCode() == null) {
                                        if (log.isInfoEnabled()) {
                                            log.info("MongoDBQuery.....Params wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                        }
                                        MongoDBInventory mv = mongoDBInventoryManager.getInventory(ou, wq.getOwner(), wq.getSkuId());
                                        if (log.isInfoEnabled()) {
                                            log.info("MongoDBQuery.....End wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                            log.info("MongoDBQueryResult:" + (mv == null ? "NULL" : mv.getAvailQty()));
                                        }
                                        if (mv == null) {
                                            if (wq.getIsGift()) {
                                                zmemo.add(wq.getId());
                                            } else {
                                                ouFlag = false;
                                                break;
                                            }
                                        } else {
                                            if (mv.getAvailQty() < wq.getQty()) {
                                                if (wq.getIsGift()) {
                                                    zmemo.add(wq.getId());
                                                } else {
                                                    ouFlag = false;
                                                    break;
                                                }
                                            } else {
                                                MongoDBInventory mi = new MongoDBInventory();
                                                mi.setWhCode(ou);
                                                mi.setOwner(wq.getOwner());
                                                mi.setSkuId(wq.getSkuId());
                                                mi.setAvailQty(wq.getQty());// 此处只是用来代替变化量 就是当前计划量
                                                ml.add(mi);
                                                lineOU.put(wq.getId(), unit.getId());
                                            }
                                        }

                                    }
                                }
                                /**
                                 * 单仓库存全部满足，直接标识成功，无需后续循环逻辑，所以return
                                 */
                                if (ouFlag) {
                                    updateInfoWhenCanCreate(ml, lineOU, zmemo, q.getId());
                                    return;
                                }
                            }
                        }
                        // 1.2再走分仓逻辑
                        ml.clear();// ************************必须清空之前*****************************
                        lineOU.clear();
                        /**
                         * 明细行有指定仓库的分仓逻辑<br/>
                         * 1、先校验明细行库存<br/>
                         * 2
                         */
                        for (WmsSalesOrderLineQueue wq : wlql) {
                            if (wq.getWarehouseCode() != null) {
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....Params wh:" + wq.getWarehouseCode() + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                }
                                MongoDBInventory mv = mongoDBInventoryManager.getInventory(wq.getWarehouseCode(), wq.getOwner(), wq.getSkuId());
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....End wh:" + wq.getWarehouseCode() + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                    log.info("MongoDBQueryResult:" + (mv == null ? "NULL" : mv.getAvailQty()));
                                }
                                if (mv == null) {
                                    if (wq.getIsGift()) {
                                        zmemo.add(wq.getId());
                                    } else {
                                        String msg = MessageFormat.format(INVNOTENOUGH_S_D, wq.getWarehouseCode(), wq.getQty());
                                        memo.add(MessageFormat.format(INVNOTENOUGH_S, wq.getSku(), msg));
                                    }
                                } else {
                                    if (mv.getAvailQty() < wq.getQty()) {
                                        if (wq.getIsGift()) {
                                            zmemo.add(wq.getId());
                                        } else {
                                            String msg = MessageFormat.format(INVNOTENOUGH_S_D, wq.getWarehouseCode(), wq.getQty());
                                            memo.add(MessageFormat.format(INVNOTENOUGH_S, wq.getSku(), msg));
                                        }
                                    } else {
                                        MongoDBInventory mi = new MongoDBInventory();
                                        mi.setWhCode(wq.getWarehouseCode());
                                        mi.setOwner(wq.getOwner());
                                        mi.setSkuId(wq.getSkuId());
                                        mi.setAvailQty(wq.getQty());// 此处只是用来代替变化量 就是当前计划量
                                        ml.add(mi);
                                    }
                                }
                            } else {
                                Boolean isEnough = false;// 标识是否该行库存不足
                                List<String> smsg = new ArrayList<String>();
                                for (String ou : list) {
                                    OperationUnit unit = ouDao.getByCode(ou);
                                    if (log.isInfoEnabled()) {
                                        log.info("MongoDBQuery.....Params wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                    }
                                    MongoDBInventory mv = mongoDBInventoryManager.getInventory(ou, wq.getOwner(), wq.getSkuId());
                                    if (log.isInfoEnabled()) {
                                        log.info("MongoDBQuery.....End wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                        log.info("MongoDBQueryResult:" + (mv == null ? "NULL" : mv.getAvailQty()));
                                    }
                                    if (mv == null) {
                                        String msg = MessageFormat.format(INVNOTENOUGH_S_D, ou, wq.getQty());
                                        smsg.add(msg);
                                    } else {
                                        if (mv.getAvailQty() < wq.getQty()) {
                                            String msg = MessageFormat.format(INVNOTENOUGH_S_D, ou, wq.getQty() - mv.getAvailQty());
                                            smsg.add(msg);
                                        } else {
                                            isEnough = true;
                                            MongoDBInventory mi = new MongoDBInventory();
                                            mi.setWhCode(ou);
                                            mi.setOwner(wq.getOwner());
                                            mi.setSkuId(wq.getSkuId());
                                            mi.setAvailQty(wq.getQty());// 此处只是用来代替变化量 就是当前计划量
                                            ml.add(mi);
                                            lineOU.put(wq.getId(), unit.getId());
                                            break;
                                        }
                                    }
                                }
                                if (!isEnough) {
                                    if (wq.getIsGift()) {
                                        zmemo.add(wq.getId());
                                    } else {
                                        String lm = MessageFormat.format(INVNOTENOUGH_S, wq.getSku(), getStringByStringList(",", smsg.toArray()));
                                        memo.add(lm);
                                    }
                                }
                            }
                        }
                        if (memo.size() > 0) {
                            String flagResult = MessageFormat.format(INVNOTENOUGH, getStringByStringList(";", memo.toArray()));
                            q.setFlagResult(getSubStr(flagResult));
                            q.setCanFlag(false);
                        } else {
                            updateInfoWhenCanCreate(ml, lineOU, zmemo, q.getId());
                        }

                    } else {
                        /**
                         * 允许分仓，但是明细行无指定仓逻辑，可以确认在此之前已经走过单仓逻辑，本次只用执行分仓逻辑<br/>
                         * 算法：<br/>
                         * 逐行循环仓库计算库存，一个仓库满足，该行满足，中止该行仓库循环<br/>
                         * 
                         */
                        // 2明细行无指定仓库的，清除之前单仓库存失败的反馈，走分仓逻辑
                        q.setFlagResult(null);
                        List<String> list = getOuListByOrderId(q.getId());
                        for (WmsSalesOrderLineQueue wq : wlql) {
                            Boolean isEnough = false;// 标识是否该行库存不足
                            List<String> smsg = new ArrayList<String>();
                            for (String ou : list) {
                                OperationUnit unit = ouDao.getByCode(ou);
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....Params wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                }
                                MongoDBInventory mv = mongoDBInventoryManager.getInventory(ou, wq.getOwner(), wq.getSkuId());
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....End wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                    log.info("MongoDBQueryResult:" + (mv == null ? "NULL" : mv.getAvailQty()));
                                }
                                if (mv == null) {
                                    String msg = MessageFormat.format(INVNOTENOUGH_S_D, ou, wq.getQty());
                                    smsg.add(msg);
                                } else {
                                    if (mv.getAvailQty() < wq.getQty()) {
                                        String msg = MessageFormat.format(INVNOTENOUGH_S_D, ou, wq.getQty() - mv.getAvailQty());
                                        smsg.add(msg);
                                    } else {
                                        isEnough = true;
                                        MongoDBInventory mi = new MongoDBInventory();
                                        mi.setWhCode(ou);
                                        mi.setOwner(wq.getOwner());
                                        mi.setSkuId(wq.getSkuId());
                                        mi.setAvailQty(wq.getQty());// 此处只是用来代替变化量 就是当前计划量
                                        ml.add(mi);
                                        lineOU.put(wq.getId(), unit.getId());
                                        break;
                                    }
                                }
                            }
                            if (!isEnough) {
                                if (wq.getIsGift()) {
                                    zmemo.add(wq.getId());
                                } else {
                                    String lm = MessageFormat.format(INVNOTENOUGH_S, wq.getSku(), getStringByStringList(",", smsg.toArray()));
                                    memo.add(lm);
                                }
                            }
                        }
                        if (memo.size() > 0) {
                            String flagResult = MessageFormat.format(INVNOTENOUGH, getStringByStringList(";", memo.toArray()));
                            q.setFlagResult(getSubStr(flagResult));
                            q.setCanFlag(false);
                        } else {
                            updateInfoWhenCanCreate(ml, lineOU, zmemo, q.getId());
                        }
                    }

                } else {// 不允许分仓逻辑
                    if (b) {
                        // 1 明细行有指定仓库的,之前逻辑都不会走，执行后续逻辑
                        List<String> list = getOuListByOrderId(q.getId());
                        Map<String, MongoDBInventory> map = new HashMap<String, MongoDBInventory>();
                        for (WmsSalesOrderLineQueue wq : wlql) {
                            if (wq.getWarehouseCode() != null) {
                                OperationUnit unit = ouDao.getByCode(wq.getWarehouseCode());
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....Params wh:" + wq.getWarehouseCode() + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                }
                                String inventoryKey = wq.getWarehouseCode() + "-" + wq.getOwner() + "-" + wq.getSkuId();
                                MongoDBInventory mv = null;
                                if (map.get(inventoryKey) != null) {
                                    mv = map.get(inventoryKey);
                                } else {
                                    mv = mongoDBInventoryManager.getInventory(wq.getWarehouseCode(), wq.getOwner(), wq.getSkuId());
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("MongoDBQuery.....End wh:" + wq.getWarehouseCode() + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                    log.info("MongoDBQueryResult:" + (mv == null ? "NULL" : mv.getAvailQty()));
                                }
                                if (mv == null) {
                                    if (wq.getIsGift()) {
                                        zmemo.add(wq.getId());
                                    } else {
                                        String msg = MessageFormat.format(INVNOTENOUGH_W_D, wq.getSku(), wq.getQty());
                                        memo.add(MessageFormat.format(INVNOTENOUGH_W, wq.getWarehouseCode(), msg));
                                    }
                                } else {
                                    if (mv.getAvailQty() < wq.getQty()) {
                                        if (wq.getIsGift()) {
                                            zmemo.add(wq.getId());
                                        } else {
                                            String msg = MessageFormat.format(INVNOTENOUGH_W_D, wq.getSku(), wq.getQty() - mv.getAvailQty());
                                            memo.add(MessageFormat.format(INVNOTENOUGH_W, wq.getWarehouseCode(), msg));
                                        }
                                    } else {
                                        mv.setAvailQty(mv.getAvailQty() - wq.getQty());
                                        MongoDBInventory mi = new MongoDBInventory();
                                        mi.setWhCode(wq.getWarehouseCode());
                                        mi.setOwner(wq.getOwner());
                                        mi.setSkuId(wq.getSkuId());
                                        mi.setAvailQty(wq.getQty());// 此处只是用来代替变化量 就是当前计划量
                                        ml.add(mi);
                                        lineOU.put(wq.getId(), unit.getId());
                                    }
                                }
                                map.put(inventoryKey, mv);
                            }

                        }
                        List<Long> gfId = new ArrayList<Long>();
                        for (String ou : list) {
                            gfId.clear();// 此处每次更新仓库，要清空，因为赠品库存不做强制校验
                            OperationUnit unit = ouDao.getByCode(ou);
                            List<MongoDBInventory> mll = new ArrayList<MongoDBInventory>();
                            List<String> ouMsg = new ArrayList<String>();
                            Map<Long, Long> idMap = new HashMap<Long, Long>();
                            for (WmsSalesOrderLineQueue wq : wlql) {
                                if (wq.getWarehouseCode() == null) {
                                    if (log.isInfoEnabled()) {
                                        log.info("MongoDBQuery.....Params wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                    }
                                    MongoDBInventory mv = mongoDBInventoryManager.getInventory(ou, wq.getOwner(), wq.getSkuId());
                                    if (log.isInfoEnabled()) {
                                        log.info("MongoDBQuery.....End wh:" + ou + ",owner:" + wq.getOwner() + ",skuId:" + wq.getSkuId());
                                        log.info("MongoDBQueryResult:" + (mv == null ? "NULL" : mv.getAvailQty()));
                                    }
                                    if (mv == null) {
                                        if (wq.getIsGift()) {
                                            gfId.add(wq.getId());
                                        } else {
                                            String msg = MessageFormat.format(INVNOTENOUGH_W_D, wq.getSku(), wq.getQty());
                                            ouMsg.add(msg);
                                        }
                                    } else {
                                        if (mv.getAvailQty() < wq.getQty()) {
                                            if (wq.getIsGift()) {
                                                gfId.add(wq.getId());
                                            } else {
                                                String msg = MessageFormat.format(INVNOTENOUGH_W_D, wq.getSku(), wq.getQty() - mv.getAvailQty());
                                                ouMsg.add(msg);
                                            }
                                        } else {
                                            MongoDBInventory mi = new MongoDBInventory();
                                            mi.setWhCode(ou);
                                            mi.setOwner(wq.getOwner());
                                            mi.setSkuId(wq.getSkuId());
                                            mi.setAvailQty(wq.getQty());// 此处只是用来代替变化量 就是当前计划量
                                            mll.add(mi);
                                            idMap.put(wq.getId(), unit.getId());
                                        }
                                    }

                                }
                            }
                            if (ouMsg.size() > 0) {
                                memo.add(MessageFormat.format(INVNOTENOUGH_W, ou, getStringByStringList(",", ouMsg.toArray())));
                            }
                            if (memo.size() == 0) {
                                ml.addAll(mll);
                                lineOU.putAll(idMap);
                                zmemo.addAll(gfId);
                                updateInfoWhenCanCreate(mll, lineOU, zmemo, q.getId());
                                return;
                            }
                        }
                        q.setCanFlag(false);
                        q.setFlagResult(getSubStr(MessageFormat.format(INVNOTENOUGH, getStringByStringList(";", memo.toArray()))));

                    } else {
                        // 2 明细行无指定仓库的,经过之前所有循环仍旧库存不足，则直接更新库存不足
                        if (q.getOwner().equals("5Nike-Global Inline官方商城") || q.getOwner().equals("5Nike-Global Swoosh 官方商城") || q.getOwner().equals("1NIKE-GLOBLE官方旗舰店")) {
                            boolean status = true;
                            for (WmsSalesOrderLineQueue wq : wlql) {
                                Long qtysum = 0l;
                                if (!wq.getIsGift()) {
                                    List<MongoDBInventory> mvs = mongoDBInventoryManager.getInventoryOwner(wq.getOwner(), wq.getSkuId());
                                    if (mvs != null) {
                                        for (MongoDBInventory mv : mvs) {
                                            qtysum += mv.getAvailQty();
                                        }
                                        if (qtysum < wq.getQty()) {
                                            Long qty = wq.getQty() - qtysum;
                                            String s = MessageFormat.format(INVNOTENOUGH_S_Q, wq.getSku(), qty.toString());
                                            memo.add(s);
                                            status = false;
                                        }
                                    } else {
                                        String s = MessageFormat.format(INVNOTENOUGH_S_Q, wq.getSku(), wq.getQty().toString());
                                        memo.add(s);
                                        status = false;
                                    }
                                }
                            }

                            if (status) {
                                q.setIsmeet(status);
                                BiChannel channle = biChannelDao.findBiChannelByCode(q.getOwner(), new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class));
                                if (channle == null) {

                                }
                                List<WarehousePriority> wl = rmiManager.getPriorityWHByCityChannleId(q.getProvince(), q.getCity(), q.getCus_id(), channle.getId());
                                if (wl.size() > 0) {
                                    String msg = MessageFormat.format(PRIORITY_W, wl.get(0).getWhCode());
                                    memo.add(msg);
                                }
                                q.setFlagResult(q.getFlagResult() + ";" + getStringByStringList(";", memo.toArray()));
                            } else {
                                q.setFlagResult(getStringByStringList(",", memo.toArray()));
                                q.setIsmeet(status);
                            }
                        }
                        q.setCanFlag(false);
                    }
                }
            } else {// 继续单仓逻辑
                /**
                 * 此处继续单仓逻辑解释如下：<br/>
                 * 订单头上未指定仓库的订单，无论可不可以分仓，如果WMS根据地址匹配出多个发货仓，原则上都要对这些订单按照仓库优先级循环进行单仓占用库存<br/>
                 * 算法：<br/>
                 * 1、如果菜鸟逻辑，订单行指定了发货仓，此处无需处理【更新循环游标(最终目标是让下一次循环执行下一个优先级仓库)】，直接跳过(最后会有单独的计算逻辑)<br/>
                 * 2、如果非菜鸟逻辑，则执行本次循环到的发货仓(逻辑同指定仓逻辑)<br/>
                 * 2.1、库存满足：标识可创单<br/>
                 * 2.2、库存不足：更新循环游标(最终目标是让下一次循环执行下一个优先级仓库)
                 * 
                 */
                if (b) {
                    /** 菜鸟逻辑更新循环游标 ****/
                    updateDefaultFlagById(q.getId());
                } else {
                    OperationUnit unit = ouDao.getByPrimaryKey(ouId);
                    singleWarehouseInvCheck(wlql, zmemo, memo, ml, lineOU, ouId, admemo, adId, q.getIsShortPicking() == null ? false : q.getIsShortPicking(), adStatus);
                    if (memo.size() == 0) {
                        updateInfoWhenCanCreate(ml, lineOU, zmemo, id);
                    } else {
                        // TODO:设置默认仓库游标下移
                        updateDefaultFlagById(q.getId());
                        String ms1 = MessageFormat.format(INVNOTENOUGH_W, unit.getCode(), getStringByStringList(",", memo.toArray()));
                        String ms = MessageFormat.format(INVNOTENOUGH, ms1);
                        q.setFlagResult(getSubStr(q.getFlagResult() == null ? ms : q.getFlagResult() + ";" + ms1));
                    }
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.setFlagToOrder...End id:" + id + ",ouId:" + ouId);
        }
    }

    /**
     * @param id
     * @return
     */
    private List<String> getOuListByOrderId(Long id) {
        List<OrderWarehousePriority> list1 = orderWarehousePriorityDao.getWarehouseByOrder(id, new BeanPropertyRowMapper<OrderWarehousePriority>(OrderWarehousePriority.class));
        Collections.sort(list1, new Comparator<OrderWarehousePriority>() {
            @Override
            public int compare(OrderWarehousePriority o1, OrderWarehousePriority o2) {
                return o1.getId().compareTo(o2.getId());
            }

        });;
        List<String> list = new ArrayList<String>();
        for (OrderWarehousePriority op : list1) {
            list.add(op.getOuCode());
        }
        return list;
    }

    private String getSubStr(String string) {
        if (null != string) {
            if (string.length() > 3000) {
                return string.substring(0, 3000);
            }
            return string;
        }
        return null;
    }

    /**
     * 标记可以创单，更新需要更新的信息
     * 
     * @param ml
     * @param lineOU
     * @param zmemo
     * @param id
     */
    private void updateInfoWhenCanCreate(List<MongoDBInventory> ml, Map<Long, Long> lineOU, List<Long> zmemo, Long id) {
        mongoDBInventoryManager.updateMongoDBInventory(ml);
        // 更新行仓库标识
        updateLineWarehouseId(lineOU, id);
        // 剔除可能的赠品行
        updateGiftLineDelete(zmemo);
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        q.setFlagResult(null);
        q.setCanFlag(true);
    }

    /**
     * 标记可以创单，更新需要更新的信息
     * 
     * @param ml
     * @param lineOU
     * @param zmemo
     * @param id
     */
    private boolean updateInfoWhenCanCreateNew(List<MongoDBInventory> ml, Map<Long, Long> lineOU, List<Long> zmemo, Long id) {
        boolean result = true;
        result = mongoDBInventoryManager.updateMongoDBInventoryNew(ml);
        if (!result) {
            // mongoDB更新失败 直接返回false
            return result;
        }
        // 更新行仓库标识
        updateLineWarehouseId(lineOU, id);
        // 剔除可能的赠品行
        updateGiftLineDelete(zmemo);
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        q.setFlagResult(null);
        q.setCanFlag(true);
        return result;
    }

    /**
     * 标记可以创单，更新需要更新的信息
     * 
     * @param ml
     * @param lineOU
     * @param zmemo
     * @param id
     */
    private void updateInfoWhenCanCreatePac(List<MongoDBInventoryPac> ml, List<Long> zmemo, Long id) {
        mongoDBInventoryManager.updateMongoDBInventoryPac(ml);
        // 剔除可能的赠品行
        updateGiftLineDelete(zmemo);
        QueueSta q = queueStaDao.getByPrimaryKey(id);
        q.setFlagResult(null);
        q.setCanflag(true);
    }

    /**
     * 标记可以创单AD，更新需要更新的信息
     * 
     * @param ml
     * @param lineOU
     * @param zmemo
     * @param id
     */
    private void updateInfoWhenCanCreateAD(List<MongoDBInventory> ml, Map<Long, Long> lineOU, List<Long> adId, List<Long> zmemo, Long id) {
        mongoDBInventoryManager.updateMongoDBInventory(ml);
        // 更新行仓库标识
        updateLineWarehouseId(lineOU, id);
        // 剔除可能的赠品行
        updateGiftLineDelete(zmemo);
        // 清空销售数量
        updateQty(adId);
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        q.setFlagResult(null);
        q.setCanFlag(true);
    }

    /**
     * 单仓库存校验
     * 
     * @param wlql
     * @param zmemo
     * @param memo
     * @param ml
     * @param lineOU
     * @param ouId
     */
    private void singleWarehouseInvCheck(List<WmsSalesOrderLineQueue> wlql, List<Long> zmemo, List<String> memo, List<MongoDBInventory> ml, Map<Long, Long> lineOU, Long ouId, List<String> admemo, List<Long> adId, boolean isShortPicking,
            Map<String, Boolean> status) {
        OperationUnit unit = ouDao.getByPrimaryKey(ouId);
        Map<String, MongoDBInventory> map = new HashMap<String, MongoDBInventory>();
        for (WmsSalesOrderLineQueue wlq : wlql) {
            String inventoryKey = unit.getCode() + "-" + wlq.getOwner() + "-" + wlq.getSkuId();
            // 原则上单仓单店必须只能查出最多一条数据
            MongoDBInventory inv = null;
            if (map.get(inventoryKey) != null) {
                inv = map.get(inventoryKey);
            } else {
                if (log.isInfoEnabled()) {
                    log.info("MongoDB queryBegin:------" + inventoryKey);
                }
                inv = mongoDBInventoryManager.getInventory(unit.getCode(), wlq.getOwner(), wlq.getSkuId());
                if (log.isInfoEnabled()) {
                    log.info("MongoDB queryEnd:------" + inventoryKey);
                }

            }
            if (inv == null) {
                if (wlq.getIsGift()) {
                    zmemo.add(wlq.getId());
                } else {
                    // 判断是否支持短配
                    if (isShortPicking) {
                        String s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                        admemo.add(s);
                        adId.add(wlq.getId());
                        lineOU.put(wlq.getId(), unit.getId());
                    } else {
                        String s = "";
                        if (wlq.getOwner().equals("5Nike-Global Inline官方商城") || wlq.getOwner().equals("5Nike-Global Swoosh 官方商城") || wlq.getOwner().equals("1NIKE-GLOBLE官方旗舰店")) {
                            s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                        } else {
                            s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                        }
                        memo.add(s);
                    }
                }
            } else {
                if (inv.getAvailQty() < wlq.getQty()) {
                    if (wlq.getIsGift()) {
                        zmemo.add(wlq.getId());
                    } else {
                        // 判断是否支持短配
                        if (isShortPicking) {
                            String s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                            admemo.add(s);
                            adId.add(wlq.getId());
                        } else {
                            String s = "";
                            if (wlq.getOwner().equals("5Nike-Global Inline官方商城") || wlq.getOwner().equals("5Nike-Global Swoosh 官方商城") || wlq.getOwner().equals("1NIKE-GLOBLE官方旗舰店")) {
                                s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty() - inv.getAvailQty());
                            } else {
                                s = MessageFormat.format(INVNOTENOUGH_W_D, wlq.getSku(), wlq.getQty());
                            }
                            memo.add(s);
                        }
                    }
                } else {
                    inv.setAvailQty(inv.getAvailQty() - wlq.getQty());
                    MongoDBInventory mi = new MongoDBInventory();
                    mi.setWhCode(unit.getCode());
                    mi.setOwner(wlq.getOwner());
                    mi.setSkuId(wlq.getSkuId());
                    mi.setAvailQty(wlq.getQty());// 此处只是用来代替变化量 就是当前计划量
                    ml.add(mi);
                    lineOU.put(wlq.getId(), unit.getId());
                    status.put("flag", true);
                }
            }
            map.put(inventoryKey, inv);
        }
    }

    /**
     * 将库存不足的赠品行特殊标识，创单不考虑该行
     * 
     * @param zmemo
     */
    private void updateGiftLineDelete(List<Long> zmemo) {
        for (Long id : zmemo) {
            WmsSalesOrderLineQueue lq = wmsSalesOrderLineQueueDao.getByPrimaryKey(id);
            lq.setIsDelete(true);
        }
    }

    /**
     * 将库存不足的赠品行特殊标识，创单不考虑该行
     * 
     * @param zmemo
     */
    private void updateQty(List<Long> zmemo) {
        for (Long id : zmemo) {
            WmsSalesOrderLineQueue lq = wmsSalesOrderLineQueueDao.getByPrimaryKey(id);
            lq.setQty(0l);
        }
    }

    /**
     * 默认仓游标下移
     * 
     * @param id
     */
    private void updateDefaultFlagById(Long id) {
        orderWarehousePriorityDao.updateFlagById(id);
    }

    private void updateLineWarehouseId(Map<Long, Long> lineOU, Long id) {
        Map<Long, String> map = new HashMap<Long, String>();
        WmsSalesOrderQueue queue = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        List<OrderWarehousePriority> list = orderWarehousePriorityDao.getWarehouseAndLpByOrder(id, new BeanPropertyRowMapper<OrderWarehousePriority>(OrderWarehousePriority.class));
        if (list.size() == 0) {
            map.put(queue.getOu_id(), queue.getTransCode());
        } else {
            for (OrderWarehousePriority p : list) {
                map.put(p.getOuId(), p.getLpCode());
            }
        }
        for (Long key : lineOU.keySet()) {
            WmsSalesOrderLineQueue lq = wmsSalesOrderLineQueueDao.getByPrimaryKey(key);
            lq.setOuId(lineOU.get(key));
            lq.setLpCode(map.get(lineOU.get(key)));
        }
    }

    private String getStringByStringList(String string, Object[] array) {
        StringBuffer b = new StringBuffer();
        for (Object s : array) {
            b.append(s.toString() + string);
        }
        return b.toString().substring(0, b.length() - 1);
    }

    @Override
    public Integer getSystemThreadValueByKey(String key) {
        return chooseOptionManager.getSystemThreadValueByKey(key);
    }

    /**
     * 设置立峰创建批次
     */
    @Override
    public Integer getLFPiCiValueByKey(String key) {
        return chooseOptionManager.getLFPiCiValueByKey(key);
    }

    @Override
    public Integer getChooseOptionByCodeKey(String code,String key) {
        return chooseOptionManager.getChooseOptionByCodeKey(code,key);
    }

    
    
    @Override
    public Integer getSystemThreadValueByKeyAndDes(String key, Boolean available) {
        return chooseOptionManager.getSystemThreadValueByKeyAndDes(key, available);
    }

    // @Override
    // public void initInventoryForOnceUse() {
    // mongoDBInventoryManager.initInventoryForOnceUse();
    // }

    @Override
    public void initInventoryByOuId(String warehouseCode) {
        mongoDBInventoryManager.initInventoryByOuId(warehouseCode);

    }

    @Override
    public void deleteInventoryForOnceUse() {
        mongoDBInventoryManager.deleteInventoryForOnceUse();
    }

    /**
     * 
     */
    @Override
    public List<String> findInitInventoryOwnerList() {
        mongoDBInventoryManager.createTableForMongoInv();
        return biChannelDao.findInitInventoryOwnerList(new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    public void initInventoryForOnceUseByOwner(String owner) {
        mongoDBInventoryManager.initInventoryForOnceUseByOwner(owner);
    }

    @Override
    public List<String> findInitInventoryWarehouseCodeList() {
        return operationUnitDao.findInitInventoryWarehouseCodeList(new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    public List<String> findInitInventoryWarehouseCodeListDelete() {
        return operationUnitDao.findInitInventoryWarehouseCodeListDelete(new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    public List<QueueSta> getToSetBatchOrderByWarehouse(Long id) {
        return queueStaDao.findSetFlagByOuId(id, new BeanPropertyRowMapper<QueueSta>(QueueSta.class));
    }

    @Override
    public void setFlagToOrderPac(Long id, Long ouId) {
        if (log.isInfoEnabled()) {
            log.info("CreateStaTaskManagerImpl.setFlagToOrder...Enter id:" + id + ",ouId:" + ouId);
        }
        QueueSta q = queueStaDao.getByPrimaryKey(id);
        try {
            StockTransApplication sta = staDao.findStaBySlipCodeNotCancel(q.getOrdercode());
            if (sta == null) {
                List<QueueStaLine> lines = queueStaLineDao.findSkuQtyByStaId(q.getId(), new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
                List<Long> zmemo = new ArrayList<Long>();// 放置可能库存不足的赠品存放id
                List<String> memo = new ArrayList<String>();// 存储库存不足等失败信息
                Map<String, List<MongoDBInventoryPac>> map = new HashMap<String, List<MongoDBInventoryPac>>();
                Map<String, MongoDBInventoryPac> map1 = new HashMap<String, MongoDBInventoryPac>();
                List<MongoDBInventoryPac> ml = new ArrayList<MongoDBInventoryPac>();// 保存最后需要更新的mongoDB库存
                // 查询组织
                OperationUnit unit = ouDao.getByPrimaryKey(ouId);
                // 查询仓库
                Warehouse warehouse = warehouseDao.getByOuId(ouId);
                // 如退货入直接打创单标识
                if (q.getType() == 41) {
                    q.setCanflag(true);
                } else {
                    for (QueueStaLine qstaLine : lines) {
                        // 判断是否AF逻辑
                        if (warehouse != null && warehouse.getVmiSource() != null && warehouse.getVmiSource().equals("af")) {
                            List<MongoDBInventoryPac> invs = null;
                            // 查询商品
                            Sku sku = skuDao.getByCode(qstaLine.getSkucode());
                            String inventoryKey = unit.getCode() + "-" + q.getOwner() + "-" + sku.getExtensionCode3();
                            // 判断缓存是否存在库存信息
                            if (map.get(inventoryKey) == null) {
                                log.info("MongoDB queryBegin:------" + inventoryKey);
                                // 通过EXT_CODE3查询出所有关联商品
                                invs = mongoDBInventoryManager.getInventoryExtCode3Pac(unit.getCode(), q.getOwner(), sku.getExtensionCode3());
                                log.info("MongoDB queryEnd:------" + inventoryKey);
                            } else {
                                invs = map.get(inventoryKey);
                            }
                            // 判断库存是否足够
                            if (invs != null && invs.size() == 0) {
                                // 判断赠品
                                if (qstaLine.getLineType().getValue() == 5) {
                                    zmemo.add(qstaLine.getId());
                                } else {
                                    // 库存不足记录
                                    QueueStaLineStockout lineStockout = new QueueStaLineStockout();
                                    lineStockout.setQty(qstaLine.getQty());
                                    lineStockout.setQueueStaLine(qstaLine);
                                    lineStockout.setSkuCode(qstaLine.getSkucode());
                                    lineStockoutDao.save(lineStockout);
                                    String s = MessageFormat.format(INVNOTENOUGH_W_D, qstaLine.getSkucode(), qstaLine.getQty());
                                    memo.add(s);
                                }
                            } else {
                                String error = "";
                                boolean skuStatus = true;
                                // 循环校验相关库存是否足够
                                for (MongoDBInventoryPac invPac : invs) {
                                    // 优先判断当前商品库存是否足够
                                    if (invPac.getSkuCode().equals(qstaLine.getSkucode())) {
                                        if (invPac.getAvailQty() < qstaLine.getQty()) {
                                            QueueStaLineStockout lineStockout = new QueueStaLineStockout();
                                            lineStockout.setQty(qstaLine.getQty() - invPac.getAvailQty());
                                            lineStockout.setQueueStaLine(qstaLine);
                                            lineStockout.setSkuCode(qstaLine.getSkucode());
                                            lineStockoutDao.save(lineStockout);
                                            error = MessageFormat.format(INVNOTENOUGH_W_D, qstaLine.getSkucode(), qstaLine.getQty() - invPac.getAvailQty());
                                            skuStatus = false;
                                        } else {
                                            invPac.setAvailQty(invPac.getAvailQty() - qstaLine.getQty());
                                            MongoDBInventoryPac mi = new MongoDBInventoryPac();
                                            mi.setWhCode(unit.getCode());
                                            mi.setOwner(qstaLine.getOwner());
                                            mi.setSkuCode(qstaLine.getSkucode());
                                            mi.setExtCode(invPac.getExtCode());
                                            mi.setAvailQty(qstaLine.getQty());// 此处只是用来代替变化量 就是当前计划量
                                            ml.add(mi);
                                            QueueStaLineOwner lineOwner = new QueueStaLineOwner();
                                            lineOwner.setOwner(invPac.getOwner());
                                            lineOwner.setQty(qstaLine.getQty());
                                            lineOwner.setQueueStaLine(qstaLine);
                                            lineOwner.setSkuCode(invPac.getSkuCode());
                                            lineOwnerDao.save(lineOwner);
                                            skuStatus = true;
                                            break;
                                        }
                                    } else {
                                        skuStatus = false;
                                    }
                                }
                                if (!skuStatus) {
                                    for (MongoDBInventoryPac invPac : invs) {
                                        if (!invPac.getSkuCode().equals(qstaLine.getSkucode())) {
                                            if (invPac.getAvailQty() < qstaLine.getQty()) {
                                                QueueStaLineStockout lineStockout = new QueueStaLineStockout();
                                                lineStockout.setQty(qstaLine.getQty() - invPac.getAvailQty());
                                                lineStockout.setQueueStaLine(qstaLine);
                                                lineStockout.setSkuCode(qstaLine.getSkucode());
                                                lineStockoutDao.save(lineStockout);
                                                error = MessageFormat.format(INVNOTENOUGH_W_D, qstaLine.getSkucode(), qstaLine.getQty() - invPac.getAvailQty());
                                            } else {
                                                invPac.setAvailQty(invPac.getAvailQty() - qstaLine.getQty());
                                                MongoDBInventoryPac mi = new MongoDBInventoryPac();
                                                mi.setWhCode(unit.getCode());
                                                mi.setOwner(invPac.getOwner());
                                                mi.setSkuCode(invPac.getSkuCode());
                                                mi.setExtCode(invPac.getExtCode());
                                                mi.setAvailQty(qstaLine.getQty());// 此处只是用来代替变化量
                                                                                  // 就是当前计划量
                                                ml.add(mi);
                                                QueueStaLineOwner lineOwner = new QueueStaLineOwner();
                                                lineOwner.setOwner(invPac.getOwner());
                                                lineOwner.setQty(qstaLine.getQty());
                                                lineOwner.setQueueStaLine(qstaLine);
                                                lineOwner.setSkuCode(invPac.getSkuCode());
                                                lineOwnerDao.save(lineOwner);
                                                skuStatus = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!skuStatus) {
                                    memo.add(error);
                                }
                            }
                            map.put(inventoryKey, invs);

                        } else {
                            // 判断是否PAC共享库存
                            if (qstaLine.getOwner() != null && !qstaLine.getOwner().equals("")) {
                                String inventoryKey = unit.getCode() + "-" + qstaLine.getOwner() + "-" + qstaLine.getSkucode();
                                MongoDBInventoryPac inv = null;
                                if (map1.get(inventoryKey) == null) {
                                    log.info("MongoDB queryBegin:------" + inventoryKey);
                                    inv = mongoDBInventoryManager.getInventoryPacOwner(unit.getCode(), qstaLine.getOwner(), qstaLine.getSkucode());
                                    log.info("MongoDB queryEnd:------" + inventoryKey);
                                } else {
                                    inv = map1.get(inventoryKey);
                                }
                                // 判断库存是否足够
                                if (inv == null) {
                                    if (qstaLine.getLineType().getValue() == 5) {
                                        zmemo.add(qstaLine.getId());
                                    } else {
                                        QueueStaLineStockout lineStockout = new QueueStaLineStockout();
                                        lineStockout.setQty(qstaLine.getQty());
                                        lineStockout.setQueueStaLine(qstaLine);
                                        lineStockout.setSkuCode(qstaLine.getSkucode());
                                        lineStockoutDao.save(lineStockout);
                                        String s = MessageFormat.format(INVNOTENOUGH_W_D, qstaLine.getSkucode(), qstaLine.getQty());
                                        memo.add(s);
                                    }
                                } else {
                                    if (inv.getAvailQty() < qstaLine.getQty()) {
                                        if (qstaLine.getLineType().getValue() == 5) {
                                            zmemo.add(qstaLine.getId());
                                        } else {
                                            QueueStaLineStockout lineStockout = new QueueStaLineStockout();
                                            lineStockout.setQty(qstaLine.getQty() - inv.getAvailQty());
                                            lineStockout.setQueueStaLine(qstaLine);
                                            lineStockout.setSkuCode(qstaLine.getSkucode());
                                            lineStockoutDao.save(lineStockout);
                                            String s = MessageFormat.format(INVNOTENOUGH_W_D, qstaLine.getSkucode(), qstaLine.getQty() - inv.getAvailQty());
                                            memo.add(s);
                                        }
                                    } else {
                                        inv.setAvailQty(inv.getAvailQty() - qstaLine.getQty());
                                        MongoDBInventoryPac mi = new MongoDBInventoryPac();
                                        mi.setWhCode(unit.getCode());
                                        mi.setOwner(qstaLine.getOwner());
                                        mi.setSkuCode(qstaLine.getSkucode());
                                        mi.setExtCode(inv.getExtCode());
                                        mi.setAvailQty(qstaLine.getQty());// 此处只是用来代替变化量 就是当前计划量
                                        ml.add(mi);
                                        QueueStaLineOwner lineOwner = new QueueStaLineOwner();
                                        lineOwner.setOwner(qstaLine.getOwner());
                                        lineOwner.setQty(qstaLine.getQty());
                                        lineOwner.setQueueStaLine(qstaLine);
                                        lineOwner.setSkuCode(qstaLine.getSkucode());
                                        lineOwnerDao.save(lineOwner);
                                    }
                                }
                                map1.put(inventoryKey, inv);
                            } else {
                                // 通过ChannelList配置查询共享库存
                                String inventoryKey = unit.getCode() + "-" + q.getChannelList() + "-" + qstaLine.getSkucode();
                                List<MongoDBInventoryPac> invs = null;
                                if (map.get(inventoryKey) == null) {
                                    log.info("MongoDB queryBegin:------" + inventoryKey);
                                    invs = mongoDBInventoryManager.getInventoryPac(unit.getCode(), q.getChannelList(), qstaLine.getSkucode());
                                    log.info("MongoDB queryEnd:------" + inventoryKey);
                                } else {
                                    invs = map.get(inventoryKey);
                                }
                                if (invs == null) {
                                    if (qstaLine.getLineType().getValue() == 5) {
                                        zmemo.add(qstaLine.getId());
                                    } else {
                                        QueueStaLineStockout lineStockout = new QueueStaLineStockout();
                                        lineStockout.setQty(qstaLine.getQty());
                                        lineStockout.setQueueStaLine(qstaLine);
                                        lineStockout.setSkuCode(qstaLine.getSkucode());
                                        lineStockoutDao.save(lineStockout);
                                        String s = MessageFormat.format(INVNOTENOUGH_W_D, qstaLine.getSkucode(), qstaLine.getQty());
                                        memo.add(s);
                                    }
                                } else {
                                    Long sum = 0l;
                                    for (MongoDBInventoryPac invPac : invs) {
                                        // 累计所有共享店铺库存
                                        sum += invPac.getAvailQty();
                                    }
                                    // 判断所有共享店铺库存是否足够
                                    if (sum < qstaLine.getQty()) {
                                        if (qstaLine.getLineType().getValue() == 5) {
                                            zmemo.add(qstaLine.getId());
                                        } else {
                                            QueueStaLineStockout lineStockout = new QueueStaLineStockout();
                                            lineStockout.setQty(qstaLine.getQty() - sum);
                                            lineStockout.setQueueStaLine(qstaLine);
                                            lineStockout.setSkuCode(qstaLine.getSkucode());
                                            lineStockoutDao.save(lineStockout);
                                            String s = MessageFormat.format(INVNOTENOUGH_W_D, qstaLine.getSkucode(), qstaLine.getQty() - sum);
                                            memo.add(s);
                                        }
                                    } else {
                                        for (MongoDBInventoryPac invPac : invs) {
                                            if (invPac.getAvailQty() < qstaLine.getQty()) {
                                                if (qstaLine.getLineType().getValue() == 5) {
                                                    zmemo.add(qstaLine.getId());
                                                } else {
                                                    if (invPac.getAvailQty() > 0) {
                                                        qstaLine.setQty(qstaLine.getQty() - invPac.getAvailQty());
                                                        MongoDBInventoryPac mi = new MongoDBInventoryPac();
                                                        mi.setWhCode(unit.getCode());
                                                        mi.setOwner(invPac.getOwner());
                                                        mi.setSkuCode(qstaLine.getSkucode());
                                                        mi.setExtCode(invPac.getExtCode());
                                                        mi.setAvailQty(qstaLine.getQty());

                                                        ml.add(mi);
                                                        QueueStaLineOwner lineOwner = new QueueStaLineOwner();
                                                        lineOwner.setOwner(invPac.getOwner());
                                                        lineOwner.setQty(invPac.getAvailQty());
                                                        lineOwner.setQueueStaLine(qstaLine);
                                                        lineOwner.setSkuCode(qstaLine.getSkucode());
                                                        lineOwnerDao.save(lineOwner);
                                                        invPac.setAvailQty(0l);
                                                    }
                                                }
                                            } else {
                                                invPac.setAvailQty(invPac.getAvailQty() - qstaLine.getQty());
                                                MongoDBInventoryPac mi = new MongoDBInventoryPac();
                                                mi.setWhCode(unit.getCode());
                                                mi.setOwner(invPac.getOwner());
                                                mi.setSkuCode(qstaLine.getSkucode());
                                                mi.setExtCode(invPac.getExtCode());
                                                mi.setAvailQty(qstaLine.getQty());// 此处只是用来代替变化量
                                                                                  // 就是当前计划量
                                                ml.add(mi);
                                                QueueStaLineOwner lineOwner = new QueueStaLineOwner();
                                                lineOwner.setOwner(invPac.getOwner());
                                                lineOwner.setQty(qstaLine.getQty());
                                                lineOwner.setQueueStaLine(qstaLine);
                                                lineOwner.setSkuCode(qstaLine.getSkucode());
                                                lineOwnerDao.save(lineOwner);
                                                break;
                                            }

                                        }
                                        map.put(inventoryKey, invs);
                                    }

                                }

                            }
                        }
                    }
                    if (memo.size() > 0) {
                        q.setFlagResult(getSubStr(MessageFormat.format(INVNOTENOUGH, MessageFormat.format(INVNOTENOUGH_W, q.getMainWhOuCode(), getStringByStringList(",", memo.toArray())))));
                        q.setCanflag(false);
                    } else {
                        // 成功更新信息
                        updateInfoWhenCanCreatePac(ml, zmemo, q.getId());
                    }
                }
                if (log.isInfoEnabled()) {
                    log.info("CreateStaTaskManagerImpl.setFlagToOrder...End id:" + id + ",ouId:" + ouId);
                }
            } else {
                // 记录日志表信息
                createLogSta(q.getId(), null);
                // 删除中间表信息
                List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(q.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                for (QueueStaInvoice invoice : invoices) {
                    List<QueueStaInvoiceLine> queueStaInvoiceLines = queuestaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                    for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                        queuestaInvoiceLineDao.cleanDataByLineId(queueStaInvoiceLine.getId());
                    }
                    queueStaInvoiceDao.cleanDataByInvoiceId(invoice.getId());
                }


                List<QueueStaLine> ql = queueStaLineDao.findByStaId(q.getId());

                for (QueueStaLine line : ql) {
                    List<QueueGiftLine> giftLines = queueGiftLineDao.getByfindQstaline(line.getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
                    for (QueueGiftLine queueGiftLine : giftLines) {
                        queueGiftLineDao.lineDelete(queueGiftLine.getId());
                    }
                    queueStaLineDao.cleanDataByLineId(line.getId());
                }
                queueStaLineDao.flush();
                // 新删除 费用列表
                List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(q.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
                for (QueueStaPayment queueStaPayment : pays) {
                    queueStaPaymentDao.deleteStaPayment(queueStaPayment.getId());
                }
                // 删除t_wh_order_special_execute
                whOrderSpecialExecuteDao.delSpecialExecute(q.getId());
                int status = queueStaDao.deleteQueuesta(q.getId());
                if (status == 1) {} else {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {q.getId()});
                }
                if (q.getQueueStaDeliveryInfo() != null) {
                    queueStaDeliveryInfoDao.cleanDataByStaId(q.getId());
                }


            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("setFlagToOrderPac:" + q.getOrdercode(), e);
            }
        }

    }

    public void createTomsOrdersendToMq2(Long id) {
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        if (null != q && null != q.getCanFlag() && (q.getOrderType() == 21 || q.getOrderType() == 23 || q.getOrderType() == 31)) {
            q.setBeginFlag(1);
        }
    }



    public void createTomsOrdersendToMq(Long id) {
        WmsSalesOrderQueue q = wmsSalesOrderQueueDao.getByPrimaryKey(id);
        if (null != q && null != q.getCanFlag() && (q.getOrderType() == 21 || q.getOrderType() == 23 || q.getOrderType() == 31)) {
            List<WmsSalesOrderLineQueue> list = new ArrayList<WmsSalesOrderLineQueue>();
            List<QueueMqSta> qStaIdList = new ArrayList<QueueMqSta>();
            List<MessageConfig> configs = new ArrayList<MessageConfig>();
            configs = getOmsCache(MqStaticEntity.WMS3_MQ_CREATE_STA_OMS);
            // configs = messageConfigDao.findMessageConfigByParameter(null,
            // MqStaticEntity.WMS3_MQ_CREATE_STA_OMS, null, new
            // BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
            // MQ 开关
            if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
                QueueMqSta queueMqSta = new QueueMqSta();
                queueMqSta.setId(q.getId());
                qStaIdList.add(queueMqSta);
                String msg = JsonUtil.writeValue(qStaIdList);
                String ouId = null;
                // 发送MQ
                MessageCommond mc = new MessageCommond();
                mc.setMsgId(q.getId() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                if (null != q.getCanFlag() && q.getCanFlag()) {
                    if (q.isAllowDS()) {
                        list = wmsSalesOrderLineQueueDao.getOrderLineQueueById(id, new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
                        if (null != list && list.size() > 0) {
                            ouId = list.get(0).getOuId().toString();
                        }
                    } else {
                        if (null != q.getWarehouseCode()) {
                            ouId = ouDao.getByCode(q.getWarehouseCode()).getId().toString();
                        } else {
                            list = wmsSalesOrderLineQueueDao.getOrderLineQueueById(id, new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
                            if (null != list && list.size() > 0) {
                                if (null != list.get(0).getWarehouseCode()) {
                                    ouId = ouDao.getByCode(list.get(0).getWarehouseCode()).getId().toString();
                                } else {
                                    if (null != list.get(0).getOuId()) {
                                        ouId = list.get(0).getOuId().toString();
                                    }
                                }
                            }
                        }
                    }
                }
                mc.setMsgType("com.jumbo.wms.manager.hub2wms.CreateStaTaskManagerImpl.createTomsOrdersendToMq");
                mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                mc.setMsgBody(msg);
                mc.setIsMsgBodySend(false);
                try {
                    if (null != ouId && !"".equals(ouId)) {
                        producerServer.sendDataMsgConcurrently(WMS3_MQ_CREATE_STA_OMS, ouId, mc);
                    } else {
                        producerServer.sendDataMsgConcurrently(WMS3_MQ_CREATE_STA_OMS, mc);
                    }
                } catch (Exception e) {
                    log.debug("createTomsOrdersendToMq:" + q.getId(), e.toString());
                    q.setErrorCount(1);
                    // 失败处理
                }
                if (q.getMqLogTime() == null) {
                    q.setMqLogTime(new Date());
                }
                MongoDBMessage mdbm = new MongoDBMessage();
                BeanUtils.copyProperties(mc, mdbm);
                mdbm.setMsgBody(msg);
                mdbm.setStaCode(q.getId().toString());
                mdbm.setOtherUniqueKey(q.getOrderCode());
                mdbm.setMemo(WMS3_MQ_CREATE_STA_OMS);
                if(q.getOwner() !=null && q.getOwner().contains("IT后端测试")){
                    MongoDBMessageTest mdbmTest = new MongoDBMessageTest();
                    BeanUtils.copyProperties(mdbm, mdbmTest);
                    mongoOperation.save(mdbmTest);
                }else{
                    mongoOperation.save(mdbm);
                }
                
            }
        }
    }


    @Override
    public void deleteInventoryForOuId() {
        mongoDBInventoryManager.deleteInventoryForOuId();
    }

    /**
     * 非直连创单反馈补偿
     * 
     * @return
     */
    public List<Long> findOrderCreateOrderToPac() {
        List<Long> listId = createOrderToPacDao.findOrderCreateOrderToPac();
        return listId;
    }

    /**
     * 补偿 发送MQ通知pac
     * 
     * @param qstaId
     */
    public void sendCreateOrderMQToPacById(Long cotpId) {
        CreateOrderToPac cotp = createOrderToPacDao.getByPrimaryKey(cotpId);
        if (cotp == null) {
            return;
        }
        // 发送MQ
        MessageCommond mc = new MessageCommond();
        mc.setMsgId(cotpId + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
        mc.setIsMsgBodySend(false);
        mc.setMsgType("com.jumbo.wms.manager.hub2wms.CreateStaTaskManagerImpl.sendCreateOrderMQToPac");
        mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mc.setMsgBody(cotp.getContext());
        log.debug("rmi Call pac Create request interface by MQ begin:" + cotp.getSlipCode());
        try {
            // producerServer.sendDataMsgConcurrently(MqStaticEntity.WMS3_MQ_RTN_ORDER_CREATE,
            // cotp.getOwner(), mc);
            producerServer.sendDataMsgConcurrently(MQ_WMS32PAC_RTN_ORDER_CREATE, cotp.getOwner(), mc);
        } catch (Exception e) {
            log.error("rmi Call pac Create request interface by MQ error:" + cotp.getSlipCode());
            throw new BusinessException(ErrorCode.MESSAGE_SEND_ERROR, cotp.getSlipCode());
        }
        // 保存进mongodb
        MongoDBMessage mdbm = new MongoDBMessage();
        BeanUtils.copyProperties(mc, mdbm);
        mdbm.setStaCode(cotp.getSlipCode());
        mdbm.setOtherUniqueKey(cotp.getSlipCode());
        mdbm.setMsgBody(cotp.getContext());
        mdbm.setMemo(MQ_WMS32PAC_RTN_ORDER_CREATE);
        mongoOperation.save(mdbm);
        // cotp.setStatus(10);
        // createOrderToPacDao.save(cotp);.
        createOrderToPacDao.delete(cotp);

        CreateOrderToPacLog cotpl = new CreateOrderToPacLog();
        BeanUtils.copyProperties(cotp, cotpl);
        cotpl.setId(null);
        cotpl.setLogTime(new Date());
        createOrderToPacLogDao.save(cotpl);
    }


    /**
     * 推送MQ失败，更新状态
     * 
     * @param mpMsg
     */
    public void updateCreateOrderMQToPacStatus(String mpMsg) {
        String[] msg = mpMsg.split("_qstaToPacId_");
        if (msg == null || StringUtil.isEmpty(msg[0])) {
            return;
        }
        CreateOrderToPac cotp = createOrderToPacDao.getByPrimaryKey(Long.parseLong(msg[1]));
        if (cotp == null) {
            return;
        }
        cotp.setStatus(0);
        createOrderToPacDao.save(cotp);
    }

    /**
     * 发送MQ通知pac
     * 
     * @param qstaId
     */
    public void sendCreateOrderMQToPac(String qstaId) {
        String[] msg = qstaId.split("_qstaToPacId_");
        if (msg == null || StringUtil.isEmpty(msg[0])) {
            return;
        }
        // CreateOrderToPac cotp =
        // createOrderToPacDao.getCreateOrderToPacByQstaId(Long.parseLong(msg[1]));
        CreateOrderToPac cotp = createOrderToPacDao.getByPrimaryKey(Long.parseLong(msg[1]));
        if (cotp == null) {
            return;
        }
        // 发送MQ
        MessageCommond mc = new MessageCommond();
        mc.setMsgId(msg[1] + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
        mc.setIsMsgBodySend(false);
        mc.setMsgType("com.jumbo.wms.manager.hub2wms.CreateStaTaskManagerImpl.sendCreateOrderMQToPac");
        mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mc.setMsgBody(msg[0]);
        log.debug("rmi Call pac Create request interface by MQ begin:" + cotp.getSlipCode());
        try {
            // producerServer.sendDataMsgConcurrently(MqStaticEntity.WMS3_MQ_RTN_ORDER_CREATE,
            // cotp.getOwner(), mc);
            producerServer.sendDataMsgConcurrently(MQ_WMS32PAC_RTN_ORDER_CREATE, cotp.getOwner(), mc);
        } catch (Exception e) {
            log.error("rmi Call pac Create request interface by MQ error:" + cotp.getSlipCode());
            throw new BusinessException(ErrorCode.MESSAGE_SEND_ERROR, cotp.getSlipCode());
        }
        // 保存进mongodb
        MongoDBMessage mdbm = new MongoDBMessage();
        BeanUtils.copyProperties(mc, mdbm);
        mdbm.setStaCode(cotp.getSlipCode());
        mdbm.setOtherUniqueKey(cotp.getSlipCode());
        mdbm.setMsgBody(msg[0]);
        mdbm.setMemo(MQ_WMS32PAC_RTN_ORDER_CREATE);
        mongoOperation.save(mdbm);
        // cotp.setStatus(10);
        // createOrderToPacDao.save(cotp);
        createOrderToPacDao.delete(cotp);
        
        CreateOrderToPacLog cotpl = new CreateOrderToPacLog();
        BeanUtils.copyProperties(cotp, cotpl);
        cotpl.setId(null);
        cotpl.setLogTime(new Date());
        createOrderToPacLogDao.save(cotpl);
    }

    @Override
    public String createStaByIdPac(Long id) {
        String reqMsg = null;
        QueueSta queueSta = queueStaDao.getByPrimaryKey(id);
        queueSta.setBeginflag(1);
        StockTransApplication sta = staDao.findStaBySlipCodeNotCancel(queueSta.getOrdercode());
        if (sta == null) {
            List<QueueStaLine> staLines = queueStaLineDao.queryStaId(id, new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
            if (queueSta.getCanflag() != null && queueSta.getCanflag()) {
                List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(id, new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                QueueStaDeliveryInfo deliveryInfo = queueStaDeliveryInfoDao.getByPrimaryKey(id);
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
                StaCreatedResponse createdResponse = new StaCreatedResponse();
                if (queueSta.getType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
                    createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
                } else {
                    createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_RA);
                }
                createdResponse.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_SUCCESS);
                createdResponse.setCreateTime(new Date());// BI
                createdResponse.setShopCode(queueSta.getOwner());// BI
                createdResponse.setSlipCode(queueSta.getOrdercode());
                List<SoLineResponse> shortageDetails = new ArrayList<SoLineResponse>();
                createdResponse.setSlipCode(queueSta.getOrdercode());
                Warehouse warehouse = warehouseDao.getByOuId(queueSta.getMainwhouid());
                if (warehouse != null && warehouse.getVmiSource() != null && warehouse.getVmiSource().equals("af")) {
                    if (queueSta.getType() != StockTransApplicationType.INBOUND_RETURN_REQUEST.getValue()) {
                        for (QueueStaLine l : staLines) {
                            if (l.getLineStatus() != QueueStaLineStatus.LINE_STATUS_FALSE) {
                                List<QueueStaLineOwner> lineOwner = lineOwnerDao.queryStaLineId(l.getId(), new BeanPropertyRowMapperExt<QueueStaLineOwner>(QueueStaLineOwner.class));
                                if (lineOwner != null && lineOwner.size() > 0) {
                                    for (QueueStaLineOwner queueStaLineOwner : lineOwner) {
                                        SoLineResponse detial = new SoLineResponse();
                                        detial.setJmSkuCode(queueStaLineOwner.getSkuCode());
                                        detial.setQty(queueStaLineOwner.getQty());
                                        shortageDetails.add(detial);
                                    }
                                }
                            }
                        }
                    }
                }
                createdResponse.setSoLineResponses(shortageDetails);
                // 反馈OMS成功
                try {
                    reqMsg = sendCreateResultToOms(createdResponse, queueSta.getId());
                } catch (Exception e) {
                    log.error("sendCreateResultToOms---Error", e);
                    throw new BusinessException();
                }

                if (log.isInfoEnabled()) {
                    log.info("createSta sendCreateResultToOms end, qstaId:{}", queueSta.getId());
                }
            } else {
                List<SoLineResponse> shortageDetails = new ArrayList<SoLineResponse>();
                for (QueueStaLine queueStaLine : staLines) {
                    List<QueueStaLineStockout> lineStockout = lineStockoutDao.queryStaLineId(queueStaLine.getId(), new BeanPropertyRowMapperExt<QueueStaLineStockout>(QueueStaLineStockout.class));
                    for (QueueStaLineStockout queueStaLineStockout : lineStockout) {
                        SoLineResponse lineResponse = new SoLineResponse();
                        lineResponse.setJmSkuCode(queueStaLineStockout.getSkuCode());
                        lineResponse.setQty(queueStaLineStockout.getQty());
                        shortageDetails.add(lineResponse);
                    }
                }
                // 反馈OMS库存不足,通知OMS
                StaCreatedResponse rs = new StaCreatedResponse();
                // 设置反馈OMS类型
                if (queueSta.getType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
                    rs.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
                } else {
                    rs.setType(StaCreatedResponse.BASE_RESULT_TYPE_RA);
                }
                // 设置类型库存不足
                rs.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_INV_SHORTAGE);
                rs.setSlipCode(queueSta.getOrdercode());
                rs.setSoLineResponses(shortageDetails);
                rs.setCreateTime(new Date());
                rs.setShopCode(queueSta.getOwner());
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_SALES_INV_NOT_ENOUGH), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                rs.setMsg(errorMsg.toString());
                // 通知OMS
                try {
                    reqMsg = sendNoEnoughtInvToOms(rs, queueSta.getId());
                } catch (Exception e) {
                    log.error("sendNoEnoughtInvToOms---Error", e);
                    throw new BusinessException();
                }

            }
            if (log.isInfoEnabled()) {
                log.info("checkSalesInventoryAndCreate end, qstaId:{}", queueSta.getId());
            }
        } else {
            // 记录日志表信息
            createLogSta(queueSta.getId(), null);
            // 删除中间表信息
            List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
            for (QueueStaInvoice invoice : invoices) {
                List<QueueStaInvoiceLine> queueStaInvoiceLines = queuestaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                    queuestaInvoiceLineDao.cleanDataByLineId(queueStaInvoiceLine.getId());
                }
                queueStaInvoiceDao.cleanDataByInvoiceId(invoice.getId());
            }


            List<QueueStaLine> ql = queueStaLineDao.findByStaId(queueSta.getId());

            for (QueueStaLine line : ql) {
                List<QueueGiftLine> giftLines = queueGiftLineDao.getByfindQstaline(line.getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
                for (QueueGiftLine queueGiftLine : giftLines) {
                    queueGiftLineDao.lineDelete(queueGiftLine.getId());
                }
                queueStaLineDao.cleanDataByLineId(line.getId());
            }
            queueStaLineDao.flush();
            // 新删除 费用列表
            List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
            for (QueueStaPayment queueStaPayment : pays) {
                queueStaPaymentDao.deleteStaPayment(queueStaPayment.getId());
            }
            // 删除t_wh_order_special_execute
            whOrderSpecialExecuteDao.delSpecialExecute(queueSta.getId());
            int status = queueStaDao.deleteQueuesta(queueSta.getId());
            if (status == 1) {} else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {queueSta.getId()});
            }
            if (queueSta.getQueueStaDeliveryInfo() != null) {
                queueStaDeliveryInfoDao.cleanDataByStaId(queueSta.getId());
            }

        }
        return reqMsg;
    }

    /**
     * 调用OMS接口
     * 
     * 1.OMS成功，无需转仓，删除队列数据，记录日志
     * 
     * 2.OMS成功，需转仓，修改队列仓库，记录日志
     * 
     * 3.OMS接口无法连接或系统,回滚事务
     * 
     * 4.OMS反馈失败或单据取消 throw BusinessException ErrorCode=OMS_SYSTEM_ERROR 回滚事务
     */
    private String sendNoEnoughtInvToOms(StaCreatedResponse createdResponse, Long qstaId) {
        return queueStaManagerExecute.sendCreateResultToOms(createdResponse, qstaId);
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

            sta.setMainWarehouse(ou1);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setIsNeedOccupied(false);
            sta.setOwner(qsta.getOwner());
            sta.setAddiOwner(qsta.getAddOwner());
            sta.setTotalActual(qsta.getTotalactual());
            sta.setOrderTotalActual(qsta.getOrdertaotalactual());
            sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
            // sta.setMemo(qsta.getMemo());
            sta.setExtMemo(qsta.getMemo());
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

    /**
     * 销售出库创建
     * 
     * @param qsta
     * @param deliveryInfo
     * @param staLines
     * @param invoices
     */
    public void createSta(QueueSta qsta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLine> staLines, List<QueueStaInvoice> invoices) {

        if (log.isInfoEnabled()) {
            log.info("check createStaForSales and create sta by batchCode start,ouId is:{}, batchCode is:{}", qsta.getMainwhouid(), qsta.getBatchcode());
        }
        // 创建STA
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(qsta.getMainwhouid());
        sta.setRefSlipCode(qsta.getOrdercode());
        sta.setSlipCode1(qsta.getSlipcode1());
        sta.setSlipCode2(qsta.getSlipcode2());
        sta.setSlipCode3(qsta.getSlipCode3());
        sta.setOwner(qsta.getOwner());
        sta.setAddiOwner(qsta.getAddOwner());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        sta.setLastModifyTime(new Date());
        sta.setType(StockTransApplicationType.valueOf(qsta.getType()));
        if (StockTransApplicationType.OUTBOUND_SALES.getValue() == qsta.getType()) {
            sta.setRefSlipType(SlipType.SALES_ORDER);
        } else {
            sta.setRefSlipType(SlipType.RETURN_REQUEST);
            sta.setIsLocked(true);
        }
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setVersion(1);
        sta.setChannelList(qsta.getChannelList());
        sta.setSkuQty(qsta.getSkuqty());
        sta.setIsNeedOccupied(true);
        sta.setMainWarehouse(ou1);
        Long ouId = ou1.getId();
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ouId);
        sta.setTotalActual(qsta.getTotalactual());
        sta.setOrderCreateTime(qsta.getOrdercreatetime());
        sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
        sta.setActivitySource(qsta.getActivitysource());
        sta.setIsSpecialPackaging(qsta.getIsspecialpackaging());
        sta.setSpecialType(qsta.getSpecialType());
        sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
        sta.setOrderTotalActual(qsta.getOrdertaotalactual());
        sta.setOrderTransferFree(qsta.getOrdertransferfree());
        sta.setToLocation(qsta.getToLocation());
        sta.setPlanOutboundTime(qsta.getPlanOutboundTime());
        sta.setStorePlanArriveTime(qsta.getPlanArriveTime());
        sta.setHkArriveTime(qsta.getArriveTime());
        sta.setOrderSourcePlatform(qsta.getOrderSourcePlatform());
        sta.setIsPreSale(qsta.getIsPreSale());// 是否预售
        sta.setIsOnlineInvoice(qsta.getIsOnlineInvoice());
        sta.setIsMacaoOrder(qsta.getIsMacaoOrder());// 是否澳门件
        sta.setExtMemo2(qsta.getExtMemo2());
        if (qsta.getIsPrintPrice() != null) {
            sta.setIsPrintPrice(qsta.getIsPrintPrice().booleanValue());
        }
        // 平日送
        if (sta.getHkArriveTime() != null) {
            Date date = new Date();
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
            String datetime = dateFormater.format(date);
            if (!datetime.equals(dateFormater.format(sta.getHkArriveTime()))) {
                if (date.getTime() < qsta.getArriveTime().getTime()) {
                    sta.setIsLocked(true);
                }
            }
        }
        // WMS预计送达时间
        BiChannel bichannel = companyShopDao.getByCode(sta.getOwner());
        bichannel.setIsTransUpgrade(bichannel.getIsTransUpgrade() == null ? false : bichannel.getIsTransUpgrade());
        bichannel.setIsLockRransUpgrade(bichannel.getIsLockRransUpgrade() == null ? false : bichannel.getIsTransUpgrade());
        // 判断是渠道是否升单
        if (log.isDebugEnabled()) {
            log.debug("qstaId:{}, planOutboundTime:{}", qsta.getId(), qsta.getPlanOutboundTime());
        }
        if (qsta.getPlanOutboundTime() != null) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{},bichannel is:{}, bichannel isTransUpgrade:{}", new Object[] {qsta.getId(), bichannel.getCode(), bichannel.getIsTransUpgrade()});
            }
            if (bichannel.getIsTransUpgrade()) {
                // 判断平台是否需要升单
                if (log.isDebugEnabled()) {
                    log.debug("qstaId:{},qsta isTransUpgrade:{}, bichannel isLockRransUpgrade:{}", new Object[] {qsta.getId(), qsta.getIsTransUpgrade(), bichannel.getIsLockRransUpgrade()});
                }
                if (qsta.getIsTransUpgrade()) {

                    // 是否需要升单锁定
                    if (bichannel.getIsLockRransUpgrade()) {
                        sta.setIsLocked(true);
                        sta.setIsTransUpgrade(true);
                        sta.setTransUpgradeType(TransUpgradeType.STORE_REQUIREMENET);
                    } else {
                        sta.setIsTransUpgrade(true);
                        sta.setIsLocked(false);
                        sta.setTransUpgradeType(TransUpgradeType.STORE_REQUIREMENET);
                    }
                    // 根据时间判断是否升单
                } else {
                    // 获取当前时间
                    Calendar calendar = Calendar.getInstance();
                    String dateTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                    // 获取预计发货日期
                    Calendar date = Calendar.getInstance();
                    date.setTime(qsta.getPlanArriveTime());
                    String arriveTime = date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH);
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{}, qsta arriveTime:{}, current dateTime:{}, bichannel isLockRransUpgrade:{}", new Object[] {qsta.getId(), arriveTime, dateTime, bichannel.getIsLockRransUpgrade()});
                    }
                    // 同天逻辑
                    if (arriveTime.equals(dateTime)) {
                        if (bichannel.getIsLockRransUpgrade()) {
                            // 当前时间大于16点
                            if (calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
                                // 预计发货时间大于16
                                if (date.get(Calendar.HOUR_OF_DAY) >= 16) {
                                    // 不进行升单
                                    sta.setIsTransUpgrade(false);
                                    sta.setIsLocked(false);
                                } else {
                                    // 预计发货时间小于16点需要进行升单
                                    sta.setIsTransUpgrade(true);
                                    sta.setIsLocked(true);
                                    sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                                }
                            } else {
                                sta.setIsTransUpgrade(false);
                                sta.setIsLocked(false);
                            }
                        } else {
                            // 当前时间大于16点
                            if (calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
                                // 预计发货时间大于16
                                if (date.get(Calendar.HOUR_OF_DAY) >= 16) {
                                    // 不进行升单
                                    sta.setIsTransUpgrade(false);
                                    sta.setIsLocked(false);
                                } else {
                                    // 预计发货时间小于16点需要进行升单
                                    sta.setIsTransUpgrade(true);
                                    sta.setIsLocked(false);
                                    sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                                }
                            } else {
                                sta.setIsTransUpgrade(false);
                                sta.setIsLocked(false);
                            }
                        }
                        // 隔天逻辑
                    } else {
                        if (bichannel.getIsLockRransUpgrade()) {
                            // 获取相隔天数
                            int month = calendar.get(Calendar.DAY_OF_MONTH) - date.get(Calendar.DAY_OF_MONTH);
                            // 等于一天
                            if (month == 1) {
                                // 当前时间大于16点
                                if (calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
                                    // 预计发货时间大于16点
                                    sta.setIsTransUpgrade(true);
                                    sta.setIsLocked(true);
                                    sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);

                                } else {
                                    // 判断预计发货时间大于16点
                                    if (date.get(Calendar.HOUR_OF_DAY) >= 16) {
                                        // 无需升单
                                        sta.setIsTransUpgrade(false);
                                        sta.setIsLocked(false);
                                    } else {
                                        // 预计发货时间小于16点需要进行升单
                                        sta.setIsTransUpgrade(true);
                                        sta.setIsLocked(true);
                                        sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                                    }
                                }
                            } else if (month > 1) {
                                // 相隔超过一天升单
                                sta.setIsTransUpgrade(true);
                                sta.setIsLocked(true);
                                sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                            } else {
                                // 相隔超过一天升单
                                sta.setIsTransUpgrade(true);
                                sta.setIsLocked(true);
                                sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                            }
                        } else {
                            // 获取相隔天数
                            int month = calendar.get(Calendar.MONTH) - date.get(Calendar.MONDAY);
                            // 等于一天
                            if (month == 1) {
                                // 当前时间大于16点
                                if (calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
                                    // 预计发货时间大于16点
                                    sta.setIsTransUpgrade(true);
                                    sta.setIsLocked(false);
                                    sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);

                                } else {
                                    // 判断预计发货时间大于16点
                                    if (date.get(Calendar.HOUR_OF_DAY) >= 16) {
                                        // 无需升单
                                        sta.setIsTransUpgrade(false);
                                        sta.setIsLocked(false);
                                    } else {
                                        // 预计发货时间小于16点需要进行升单
                                        sta.setIsTransUpgrade(true);
                                        sta.setIsLocked(false);
                                        sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                                    }
                                }
                            } else if (month > 1) {
                                // 相隔超过一天升单
                                sta.setIsTransUpgrade(true);
                                sta.setIsLocked(false);
                                sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                            } else {
                                // 相隔超过一天升单
                                sta.setIsTransUpgrade(true);
                                sta.setIsLocked(false);
                                sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                            }
                        }
                    }
                }
            }
        }
        // 澳门件判断是否需要打印海关单
        if (qsta != null && qsta.getIsMacaoOrder() != null && qsta.getIsMacaoOrder()) {
            if (bichannel.getIsPrintMaCaoHGD() != null && bichannel.getIsPrintMaCaoHGD()) {
                // 勾选了需要打印海关单开关并且金额高于设定值
                long totalactual = qsta.getTotalactual() == null ? 0l : qsta.getTotalactual().longValue();
                long setMoneyLmit = bichannel.getMoneyLmit() == null ? 0l : bichannel.getMoneyLmit().longValue();
                if (totalactual >= setMoneyLmit) {
                    sta.setIsPrintMaCaoHGD(true);
                } else {
                    sta.setIsPrintMaCaoHGD(false);
                }
            }
        }
        staDao.save(sta);
        // 创建特殊类型，删除特殊类型中间表,记录日志表
        List<WhOrderSpecialExecute> spExe = whOrderSpecialExecuteDao.getByQueueId(qsta.getId());
        if (spExe != null&&spExe.size()>0) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{}, qsta order special execute is not null,save special execute", qsta.getId());
            }
            for(WhOrderSpecialExecute whOrderSpecialExecute:spExe){
                StaSpecialExecute staSpExe = new StaSpecialExecute();
                staSpExe.setSta(sta);
                staSpExe.setType(whOrderSpecialExecute.getType());
                staSpExe.setMemo(whOrderSpecialExecute.getMemo());
                staSpecialExecutedDao.save(staSpExe);
            }
            // 删除特殊类型中间表,记录日志表
            // whOrderSpecialExecuteDao.deleteByPrimaryKey(spExe.getId());
            // whOrderSpecialExecuteDao.inserSpecialExecuteLog(qsta.getId(),
            // spExe.getType().getValue(), spExe.getMemo());
        }
        // 创建物流信息
        StaDeliveryInfo di = null;
        if (deliveryInfo != null) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{}, qsta delivery info is not null,save sta delivery info", qsta.getId());
            }
            di = new StaDeliveryInfo();
            di.setCountry(deliveryInfo.getCountry());
            di.setTrackingNo(deliveryInfo.getTrackingno());
            di.setProvince(deliveryInfo.getProvince());
            di.setCity(deliveryInfo.getCity());
            di.setDistrict(deliveryInfo.getDistrict());
            di.setAddress(deliveryInfo.getAddress());
            di.setTelephone(deliveryInfo.getTelephone());
            di.setIsCodPos(deliveryInfo.getIsCodPos());
            di.setConvenienceStore(deliveryInfo.getConvenienceStore());
            di.setMobile(deliveryInfo.getMobile());
            di.setReceiver(deliveryInfo.getReceiver());
            di.setZipcode(deliveryInfo.getZipcode());
            di.setIsCod(deliveryInfo.getIscode());
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

            di.setStoreComIsNeedInvoice(qsta.getIsInvoice());
            di.setAddressEn(deliveryInfo.getAddressEn());
            di.setCityEn(deliveryInfo.getCityEn());
            di.setCountryEn(deliveryInfo.getCountryEn());
            di.setDistrictEn(deliveryInfo.getDistrictEn());
            di.setProvinceEn(deliveryInfo.getProvinceEn());
            di.setReceiverEn(deliveryInfo.getReceiverEn());
            di.setRemarkEn(deliveryInfo.getRemarkEn());
            di.setRemark(deliveryInfo.getRemark());
            di.setTransferFee(qsta.getTransferfee());
            di.setTransType(TransType.valueOf(deliveryInfo.getTranstype()));
            di.setTransTimeType(TransTimeType.valueOf(deliveryInfo.getTranstimetype()));
            di.setTransMemo(deliveryInfo.getTransmemo());
            // BiChannel bichannel = companyShopDao.getByCode(sta.getOwner());
            List<BiChannelSpecialAction> bsa = biChannelSpecialActionDao.getChannelByAndType(bichannel.getId(), BiChannelSpecialActionType.PRINT_EXPRESS_BILL);
            if (bsa != null && bsa.size() > 0) {
                for (BiChannelSpecialAction bcsa : bsa) {
                    if (StringUtils.hasText(bcsa.getTransAddMemo())) {
                        di.setTransMemo(bcsa.getTransAddMemo());
                    }
                }
            }
            di.setId(sta.getId());
            di.setOrderUserCode(deliveryInfo.getOrderusercode());
            di.setOrderUserMail(deliveryInfo.getOrderusermail());
            di.setInsuranceAmount(deliveryInfo.getInsuranceAmount());
            di.setLastModifyTime(new Date());
            sta.setDeliveryType((deliveryInfo.getTranstype() == 6 ? TransDeliveryType.AVIATION : TransDeliveryType.ORDINARY));
            sta.setMemo(deliveryInfo.getRemark());
            staDeliveryInfoDao.save(di);
            sta.setStaDeliveryInfo(di);
        }
        Long sum = 0l;
        Long originalSum = 0l;
        for (QueueStaLine staLine : staLines) {
            if (staLine.getDirection() == TransactionDirection.OUTBOUND.getValue()) {
                Sku sku = skuDao.getByCode(staLine.getSkucode());
                if (sku.getSnType() != null) {
                    if (sku.getSnType() == SkuSnType.NO_BARCODE_SKU) {
                        sta.setIsBkCheck(true);
                    }
                }

                if (staLine.getLineStatus() == QueueStaLineStatus.LINE_STATUS_TURE) {
                    originalSum += staLine.getQty();
                    List<QueueStaLineOwner> lineOwners = queueStaLineOwnerDao.queryStaLineId(staLine.getId(), new BeanPropertyRowMapper<QueueStaLineOwner>(QueueStaLineOwner.class));
                    if (lineOwners.size() > 0) {
                        for (QueueStaLineOwner queueStaLineOwner : lineOwners) {
                            StaLine sl1 = new StaLine();
                            sl1.setSta(sta);
                            sku = skuDao.getByCode(queueStaLineOwner.getSkuCode());
                            if (sku.getSnType() != null) {
                                if (sku.getSnType() == SkuSnType.NO_BARCODE_SKU) {
                                    sta.setIsBkCheck(true);
                                }
                            }
                            sl1.setSku(sku);
                            sl1.setOwner(queueStaLineOwner.getOwner());
                            sl1.setQuantity(queueStaLineOwner.getQty());
                            sl1.setVersion(1);
                            sl1.setSkuName(staLine.getSkuName());
                            sl1.setTotalActual(staLine.getTotalactual());
                            sl1.setOrderTotalActual(staLine.getOrdertotalactual());
                            sl1.setOrderTotalBfDiscount(staLine.getOrdertotalbfdiscount());
                            sl1.setListPrice(staLine.getListprice());
                            sl1.setUnitPrice(staLine.getUnitprice());
                            sum += queueStaLineOwner.getQty();
                            staLineDao.save(sl1);
                            List<QueueGiftLine> giftLines = queueGiftLineDao.getByfindQstaline(staLine.getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
                            if (giftLines != null && giftLines.size() > 0) {
                                // 判断是否是保修卡信息
                                boolean isWarrantyCard = (sku.getWarrantyCardType() != null && SkuWarrantyCardType.NO_CHECK.equals(sku.getWarrantyCardType()));
                                if (log.isDebugEnabled()) {
                                    log.debug("qstaId:{}, is no check warrantyCard:{}", qsta.getId(), isWarrantyCard);
                                }
                                for (QueueGiftLine giftLine : giftLines) {
                                    GiftLine line = new GiftLine();
                                    int temp = 0; // 用于计算非礼盒的类型
                                    if (GiftType.CK_GIFT_BOX.getValue() != giftLine.getType()) {
                                        temp++;
                                    }
                                    line.setType(GiftType.valueOf(giftLine.getType()));
                                    if (GiftType.COACH_CARD.getValue() == giftLine.getType()) {
                                        if (log.isDebugEnabled()) {
                                            log.debug("qstaId:{},coach gift type:{}, is no check warrantyCard:{}", new Object[] {qsta.getId(), giftLine.getType(), isWarrantyCard});
                                        }
                                        if (!isWarrantyCard) continue; // 如果 是无需保修商品 那么OMS 过来的保修卡信息
                                                                       // 将不做记录
                                        try {
                                            int month = Integer.valueOf(giftLine.getMemo());
                                            Calendar cal = Calendar.getInstance();
                                            cal.setTime(new Date());
                                            cal.add(Calendar.MONTH, month);
                                            line.setMemo(FormatUtil.formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
                                        } catch (Exception e) {
                                            throw new BusinessException(ErrorCode.GIFT_LINE_IS_NULL, new Object[] {sku.getCode(), 4});
                                        }
                                    } else if (GiftType.CK_GIFT_BOX.getValue() == giftLine.getType()) {
                                        // 新增CK礼盒类型，判断如包含礼盒类型，则往作业单头上打标 by FXL
                                        sta.setPackingType(PackingType.GIFT_BOX);
                                        // 如果特殊包装明细里只包含礼盒包装，则不进入特殊处理逻辑
                                        if (temp == 0) {
                                            sta.setSpecialType(null);
                                        }
                                        line.setMemo(giftLine.getMemo());
                                    } else {
                                        line.setMemo(giftLine.getMemo());
                                    }
                                    line.setStaLine(sl1);
                                    giftLineDao.save(line);
                                }
                            }
                        }
                    } else {
                        log.error("QueueStaLineOwner--------missing");
                        // 接口异常
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                }
            }
        }
        if (!originalSum.equals(sum)) {
            throw new BusinessException(ErrorCode.STA_STALINE_NUMBER_ERROR);
        }
    /*    if (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging()) {
            Boolean b = staLineDao.checkIsSpecailSku(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
            if (b) {
                if (log.isDebugEnabled()) {
                    log.debug("qstaId:{}, check staLine has specail sku, set sta isSpecialPackaging", qsta.getId(), true);
                }
                sta.setIsSpecialPackaging(true);
            }
        }*/
        // 特殊处理
        BiChannel cha = companyShopDao.getByCode(sta.getOwner());
        List<BiChannelSpecialAction> csaList = biChannelSpecialActionDao.getChannelByAndType(cha.getId(), BiChannelSpecialActionType.SPECIAL_PACKING);
        if (csaList != null && csaList.size() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{}, bichannel special action is not null,save staSpecialExecute and sta specialType as:{}", qsta.getId(), SpecialSkuType.NORMAL);
            }
            for (BiChannelSpecialAction csa : csaList) {
                StaSpecialExecuteType type = null;
                try {
                    type = StaSpecialExecuteType.valueOf(Integer.valueOf(csa.getTemplateType()));
                } catch (Exception e) {
                    throw new BusinessException(ErrorCode.ERROR_SPECIAL_TEMPLATE_TYPE_ERROR);
                }
                StaSpecialExecute sse = new StaSpecialExecute();
                sse.setType(type);
                sse.setSta(sta);
                staSpecialExecutedDao.save(sse);
            }
            sta.setSpecialType(SpecialSkuType.NORMAL);
            // sta.setIsSpecialPackaging(true);
        }
        // 如果是Burberry 又是陆运 那么在备注里面存放 汽运
        if (Constants.BURBERRY_OWNER1.equals(sta.getOwner()) && (TransDeliveryType.LAND.equals(sta.getDeliveryType()) || TransDeliveryType.AVIATION.equals(sta.getDeliveryType()))) {
            di.setRemark("汽运");
        }
        if (qsta.getIsInvoice()) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{}, qsta invoice is not null,save sta invoice", qsta.getId());
            }
            for (QueueStaInvoice queueStaInvoice : invoices) {
                StaInvoice invoice = new StaInvoice();
                invoice.setSta(sta);
                invoice.setPayer(queueStaInvoice.getPayer());
                invoice.setAmt(queueStaInvoice.getAmt());
                invoice.setDrawer(queueStaInvoice.getDrawer());
                invoice.setInvoiceDate(queueStaInvoice.getInvoiceDate());
                invoice.setItem(queueStaInvoice.getItem());
                invoice.setMemo(queueStaInvoice.getMemo());
                invoice.setPayee(queueStaInvoice.getPayee());
                invoice.setQty(queueStaInvoice.getQty());
                invoice.setUnitPrice(queueStaInvoice.getUnitPrice());
                invoice.setIdentificationNumber(queueStaInvoice.getIdentificationNumber());
                invoice.setAddress(queueStaInvoice.getAddress());
                invoice.setTelephone(queueStaInvoice.getTelephone());
                staInvoiceDao.save(invoice);
                List<QueueStaInvoiceLine> queueStaInvoiceLines = queuestaInvoiceLineDao.findByInvoiceId(queueStaInvoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                    StaInvoiceLine staInvoiceLine = new StaInvoiceLine();
                    staInvoiceLine.setAmt(queueStaInvoiceLine.getAmt());
                    staInvoiceLine.setItem(queueStaInvoiceLine.getItem());
                    staInvoiceLine.setQty(queueStaInvoiceLine.getQty());
                    staInvoiceLine.setUnitPrice(queueStaInvoiceLine.getUnitPrice());
                    staInvoiceLine.setStaInvoice(invoice);
                    staInvoiceLineDao.save(staInvoiceLine);
                }
            }
        }
        // 新费用列表
        List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(qsta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
        for (QueueStaPayment wp : pays) {
            StaPayment sp = new StaPayment();
            sp.setAmt(wp.getAmt());
            sp.setType(wp.getType());
            sp.setMemo(wp.getMemo());
            sp.setSta(sta);
            staPaymentDao.save(sp);
        }

        staDao.save(sta);
        staDao.flush();
        if (log.isInfoEnabled()) {
            log.info("check TransactionalEvent and create sta by batchCode start,ouId is:{}, batchCode is:{}", qsta.getMainwhouid(), qsta.getBatchcode());
        }
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        if (log.isInfoEnabled()) {
            log.info("check inventory and create sta by batchCode start,ouId is:{}, batchCode is:{}", qsta.getMainwhouid(), qsta.getBatchcode());
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
        String orderCode = queueSta.getOrdercode();
        CreateOrderToPac cotp = new CreateOrderToPac();
        cotp.setContext(JsonUtil.writeValue(createdResponseList));
        cotp.setCreateTime(new Date());
        cotp.setQstaId(qstaId);
        cotp.setSlipCode(orderCode);
        cotp.setStatus(1);
        cotp.setOwner(queueSta.getOwner());
        createOrderToPacDao.save(cotp);

        // ============= 删除队列数据===============
        // 删除中间表信息
        List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
        for (QueueStaInvoice invoice : invoices) {
            List<QueueStaInvoiceLine> queueStaInvoiceLines = queuestaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
            for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                queuestaInvoiceLineDao.cleanDataByLineId(queueStaInvoiceLine.getId());
            }
            queueStaInvoiceDao.cleanDataByInvoiceId(invoice.getId());
        }


        List<QueueStaLine> ql = queueStaLineDao.findByStaId(qstaId);

        for (QueueStaLine line : ql) {
            List<QueueGiftLine> giftLines = queueGiftLineDao.getByfindQstaline(line.getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
            for (QueueGiftLine queueGiftLine : giftLines) {
                queueGiftLineDao.lineDelete(queueGiftLine.getId());
            }
            queueStaLineDao.cleanDataByLineId(line.getId());
        }
        queueStaLineDao.flush();
        // 新删除 费用列表
        List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
        for (QueueStaPayment queueStaPayment : pays) {
            queueStaPaymentDao.deleteStaPayment(queueStaPayment.getId());
        }
        int status = queueStaDao.deleteQueuesta(qstaId);
        if (status == 1) {} else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {qstaId});
        }
        if (queueSta.getQueueStaDeliveryInfo() != null) {
            queueStaDeliveryInfoDao.cleanDataByStaId(qstaId);
        }
        return cotp.getContext() + "_qstaToPacId_" + cotp.getId();

        /*
         * List<StaCreatedResponse> createdResponseList = new ArrayList<StaCreatedResponse>();
         * createdResponseList.add(createdResponse); // 发送MQ MessageCommond mc = new
         * MessageCommond(); // mc.setMsgId(configs.get(0).getMsgId()); mc.setMsgId(qstaId + "," +
         * System.currentTimeMillis()); mc.setMsgType("sendCreateResultToOms2");
         * mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
         * mc.setMsgBody(JsonUtil.obj2jsonStrIncludeAll(createdResponseList));
         * log.debug("rmi Call pac Create request interface by MQ begin:" + orderCode); try {
         * producerServer.sendDataMsgConcurrently(MqStaticEntity.WMS3_MQ_RTN_ORDER_CREATE, mc); }
         * catch (Exception e) { log.debug("rmi Call pac Create request interface by MQ error:" +
         * orderCode); throw new BusinessException(ErrorCode.MESSAGE_SEND_ERROR, orderCode); } //
         * 保存进mongodb MongoDBMessage mdbm = new MongoDBMessage(); BeanUtils.copyProperties(mc,
         * mdbm); mdbm.setStaCode(orderCode); mdbm.setOtherUniqueKey(orderCode);
         * mongoOperation.save(mdbm);
         */


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
    public String sendCreateResultToOms(StaCreatedResponse createdResponse, Long qstaId) throws BusinessException {
        if (log.isInfoEnabled()) {
            log.info("sendCreateResultToOms start, qstaId:{}", qstaId);
        }
        if (createdResponse != null) {
            // 调用OMS接口
            BaseResult result = null;
            try {
                // 记录日志表信息
                createLogSta(qstaId, createdResponse);
                if (log.isInfoEnabled()) {
                    log.info("rmi sendCreateResultToOms wmsCreateStaFeedback start, qstaId:{}", qstaId);
                }

                // MQ改造
                List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_RTN_ORDER_CREATE, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
                if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开

                    return sendCreateResultToOms2(createdResponse, qstaId);
                }

                result = rmi4Wms.wmsCreateStaFeedback(createdResponse);
                log.info(result.getMsg() + "==============" + result.getStatus() + "=========" + qstaId);
                if (log.isInfoEnabled()) {
                    log.info("rmi sendCreateResultToOms wmsCreateStaFeedback end, qstaId:{}, slipCode:{}, status:{}", new Object[] {qstaId, result.getSlipCode(), result.getStatus()});
                }
            } catch (Exception e) {
                log.error("error when connect oms to wmsCreateStaFeedback");
                // 接口异常
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            QueueSta queueSta = queueStaDao.getByPrimaryKey(qstaId);
            if (result.getStatus() == BaseResult.BASE_RESULT_STATUS_SUCCESS) {
                // ===========反馈成功,判断OMS是否需要修改仓库================
                if (result.getWhCode() != null) {
                    // ==============判断是否需要修改仓库ID========
                    // 修改仓库ID
                    queueSta.setMainWhOuCode(result.getWhCode());
                    OperationUnit mainWhOu = operationUnitDao.getByCode(result.getWhCode());
                    if (mainWhOu == null || !OperationUnitType.OUTYPE_WAREHOUSE.equals(mainWhOu.getOuType().getName())) {
                        throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new Object[] {result.getWhCode()});
                    }
                    queueSta.setMainwhouid(mainWhOu.getId());
                } else {
                    // ============= 删除队列数据===============
                    // 删除中间表信息
                    List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                    for (QueueStaInvoice invoice : invoices) {
                        List<QueueStaInvoiceLine> queueStaInvoiceLines = queuestaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                        for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                            queuestaInvoiceLineDao.cleanDataByLineId(queueStaInvoiceLine.getId());
                        }
                        queueStaInvoiceDao.cleanDataByInvoiceId(invoice.getId());
                    }


                    List<QueueStaLine> ql = queueStaLineDao.findByStaId(qstaId);

                    for (QueueStaLine line : ql) {
                        List<QueueGiftLine> giftLines = queueGiftLineDao.getByfindQstaline(line.getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
                        for (QueueGiftLine queueGiftLine : giftLines) {
                            queueGiftLineDao.lineDelete(queueGiftLine.getId());
                        }
                        queueStaLineDao.cleanDataByLineId(line.getId());
                    }
                    queueStaLineDao.flush();
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
                log.error("when send message to oms,OMS系统异常");
                throw new BusinessException(ErrorCode.OMS_OUT_ERROR);
            } else {
                // 系统异常
                log.error("when send message to oms,the message is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("sendCreateResultToOms end, qstaId:{}", qstaId);
        }
        return null;
    }

    public void createLogSta(Long qStaId, StaCreatedResponse error) {
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qStaId);
        QueueStaDeliveryInfo deliveryInfo = queueSta.getQueueStaDeliveryInfo();
        List<QueueStaLine> staLines = queueStaLineDao.queryStaId(qStaId, new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
        List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(qStaId, new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
        QueueLogSta logsta = new QueueLogSta();
        logsta.setOrdercode(queueSta.getOrdercode());
        logsta.setSlipcode1(queueSta.getSlipcode1());
        logsta.setSlipcode2(queueSta.getSlipcode2());
        logsta.setSlipCode3(queueSta.getSlipCode3());
        logsta.setMainwhouid(queueSta.getMainwhouid());
        logsta.setAddwhouid(queueSta.getAddwhouid() == null ? 0 : queueSta.getAddwhouid());
        logsta.setIscreatedlocked(queueSta.getIscreatedlocked());
        logsta.setOwner(queueSta.getOwner());
        logsta.setQstaId(queueSta.getId());
        logsta.setCreatetime(queueSta.getCreatetime());
        logsta.setAddOwner(queueSta.getAddOwner());
        logsta.setBatchCode(queueSta.getBatchcode());
        logsta.setArriveTime(queueSta.getArriveTime());
        logsta.setArriveTimeType(queueSta.getArriveTimeType());
        logsta.setMkPosCode(queueSta.getMkPosCode());
        logsta.setType(queueSta.getType());
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
        logsta.setLogtime(new Date());
        logsta.setToLocation(queueSta.getToLocation());
        logsta.setIsPreSale(queueSta.getIsPreSale());// 是否预售
        logsta.setIsOnlineInvoice(queueSta.getIsOnlineInvoice());
        logsta.setIsMacaoOrder(queueSta.getIsMacaoOrder());// 是否澳门件
        logsta.setIsPrintPrice(queueSta.getIsPrintPrice());
        logsta.setExtMemo2(queueSta.getExtMemo2());
        if (queueSta.getIsTransUpgrade() != null && queueSta.getIsTransUpgrade()) {
            logsta.setTransUpgrade(true);
        } else {
            logsta.setTransUpgrade(false);
        }
        logsta.setPlanArriveTime(queueSta.getPlanArriveTime());
        logsta.setPlanOutboundTime(queueSta.getPlanOutboundTime());
        logsta.setSpecialType(queueSta.getSpecialType());
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
            logsta.setErrormsg("OMS取消订单或重复推送");
        }

        logsta.setStatus(1);
        logsta.setActivitysource(queueSta.getActivitysource());
        logsta.setMemo(queueSta.getMemo());
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
                List<QueueStaInvoiceLine> queueStaInvoiceLines = queuestaInvoiceLineDao.findByInvoiceId(queueStaInvoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
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
        // 新 费用列表log
        List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
        for (QueueStaPayment queueStaPayment : pays) {
            QueueLogStaPayment logStaPayment = new QueueLogStaPayment();
            logStaPayment.setAmt(queueStaPayment.getAmt());
            logStaPayment.setMemo(queueStaPayment.getMemo());
            logStaPayment.setqStaId(logsta);
            queueLogStaPaymentDao.save(logStaPayment);
        }
    }

    @Override
    public List<Long> getNeedExecuteWarehousePac() {
        return ouDao.getNeedExecuteWarehousePac(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public List<Long> getBeginFlagForOrderPac() {
        return ouDao.getBeginFlagForOrderPac(new SingleColumnRowMapper<Long>(Long.class));
    }

    public void createOrdersendToMq(Long id) {
        QueueSta q = queueStaDao.getByPrimaryKey(id);
        List<QueueMqSta> qStaIdList = new ArrayList<QueueMqSta>();
        List<MessageConfig> configs = new ArrayList<MessageConfig>();
        String msg = null;
        configs = getPacCache(MqStaticEntity.WMS3_MQ_CREATE_STA_PAC);
        // configs = messageConfigDao.findMessageConfigByParameter(null,
        // MqStaticEntity.WMS3_MQ_CREATE_STA_PAC, null, new
        // BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        // MQ 开关
        if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
            QueueMqSta queueMqSta = new QueueMqSta();
            queueMqSta.setId(q.getId());
            qStaIdList.add(queueMqSta);
            msg = JsonUtil.writeValue(qStaIdList);
            // 发送MQ
            MessageCommond mc = new MessageCommond();
            mc.setMsgId(q.getId() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
            // mc.setTags(q.getMainwhouid().toString());
            mc.setMsgType("com.jumbo.wms.manager.hub2wms.CreateStaTaskManagerImpl.setFlagToOrderPac");
            mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            mc.setMsgBody(msg);
            mc.setIsMsgBodySend(false);
            try {
                if (null != q.getMainwhouid()) {
                    producerServer.sendDataMsgConcurrently(WMS3_MQ_CREATE_STA_PAC, q.getMainwhouid().toString(), mc);
                } else {
                    producerServer.sendDataMsgConcurrently(WMS3_MQ_CREATE_STA_PAC, mc);
                }
            } catch (Exception e) {
                q.setErrorMqCount(1);
                q.setFlagResult("消息发送MQ失败");
                log.debug("createOrdersendToMq:" + q.getId(), e.toString());
            }
            MongoDBMessage mdbm = new MongoDBMessage();
            BeanUtils.copyProperties(mc, mdbm);
            mdbm.setMsgBody(msg);
            mdbm.setStaCode(q.getId().toString());
            mdbm.setOtherUniqueKey(q.getOrdercode());
            mdbm.setMemo(WMS3_MQ_CREATE_STA_PAC);
            if(q.getOwner() !=null && q.getOwner().contains("IT后端测试")){
                MongoDBMessageTest mdbmTest = new MongoDBMessageTest();
                BeanUtils.copyProperties(mdbm, mdbmTest);
                mongoOperation.save(mdbmTest);
            }else{
                mongoOperation.save(mdbm);
            }
            
        }

    }

    public List<MessageConfig> getPacCache(String code) {
        if (StringUtil.isEmpty(code)) return null;
        List<MessageConfig> messageConfig = pacCache.get(code);
        // 缓存中的数据不存在或者已过期
        if (messageConfig == null) {
            messageConfig = cacheTransRole();
        }
        return messageConfig;
    }

    public synchronized List<MessageConfig> cacheTransRole() {
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_CREATE_STA_PAC, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        pacCache.put(MqStaticEntity.WMS3_MQ_CREATE_STA_PAC, configs, 60 * 1000);
        return configs;
    }

    public List<MessageConfig> getOmsCache(String code) {
        if (StringUtil.isEmpty(code)) return null;
        List<MessageConfig> messageConfig = omsCache.get(code);
        // 缓存中的数据不存在或者已过期
        if (messageConfig == null) {
            messageConfig = omsRole();
        }
        return messageConfig;
    }

    public synchronized List<MessageConfig> omsRole() {
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_CREATE_STA_OMS, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        omsCache.put(MqStaticEntity.WMS3_MQ_CREATE_STA_OMS, configs, 60 * 1000);
        return configs;
    }

    @Override
    public List<WmsSalesOrderQueue> getWmsSalesOrderQueueByBeginFlag(Integer beginFlag) {
        return wmsSalesOrderQueueDao.getWmsSalesOrderQueueByBeginFlag(beginFlag, new BeanPropertyRowMapper<WmsSalesOrderQueue>(WmsSalesOrderQueue.class));
    }

    @Override
    public List<AdvanceOrderAddInfo> findPreSalesOrder(String source) {
        return advanceOrderAddInfoDao.findPreSalesOrder(source, new BeanPropertyRowMapper<AdvanceOrderAddInfo>(AdvanceOrderAddInfo.class));
    }

    @Override
    public String beflag5ToOme(Long id) {
        String str = null;
        List<MessageConfig> configs = new ArrayList<MessageConfig>();
        configs = getPacCache(MqStaticEntity.WMS3_MQ_CREATE_STA_PAC);
        // MQ 开关
        if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
            str = "1";
            QueueSta q = queueStaDao.getByPrimaryKey(id);
            q.setBeginflag(1);
            queueStaDao.save(q);
        } else {
            str = "2";
        }
        return str;

    }


}
