package com.jumbo.webservice.ids.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.WmsRtnInOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLogDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.ids.IdsInventorySynchronousDao;
import com.jumbo.dao.vmi.ids.IdsServerInformationDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.warehouse.AdvanceOrderAddInfoDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.ids.service.ServiceType;
import com.jumbo.webservice.sfNew.model.MongoLfPreOrder;
import com.jumbo.wms.daemon.RtnOrderTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.CommonLogRecordManager;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.ids.AEOOrderConfirm;
import com.jumbo.wms.model.vmi.ids.BaozunOrderRequest;
import com.jumbo.wms.model.vmi.ids.IdsInventorySynchronous;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.ids.OrderConfirm;
import com.jumbo.wms.model.vmi.ids.OrderConfirm.ConfirmResult;
import com.jumbo.wms.model.vmi.ids.WmsOrder;
import com.jumbo.wms.model.vmi.ids.WmsPreOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

import loxia.dao.support.BeanPropertyRowMapperExt;

@Service("idsManagerProxy")
public class IdsManagerProxyImpl extends BaseManagerImpl implements IdsManagerProxy {
    // protected static final Logger log = LoggerFactory.getLogger(IdsManagerProxyImpl.class);
    @Autowired
    private IdsInventorySynchronousDao idsInvSynDao;

    @Autowired
    private IdsManager idsManager;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;

    @Autowired
    private WarehouseDao whDao;

    @Autowired
    private StaLineDao staLineDao;

    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;

    @Autowired
    private InventoryCheckDao inventoryCheckDao;

    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;

    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;

    @Autowired
    private IdsServerInformationDao idsServerInformationDao;

    @Autowired
    private TaskOmsOutManager taskOmsOutManager;

    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrder;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;

    @Autowired
    private WmsSalesOrderLogDao wmsSalesOrderLogDao;

    @Autowired
    private AdvanceOrderAddInfoDao advanceOrderAddInfoDao;
    
    @Autowired
    private WmsRtnInOrderQueueDao inOrderQueueDao;

    @Autowired
    private CommonLogRecordManager commonLogRecordManager;
    
    @Autowired
    private RtnOrderTask rtnOrderTask;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;


    private static final long serialVersionUID = 2799808181327183883L;


    protected static final Logger log = LoggerFactory.getLogger(IdsManagerProxyImpl.class);

    /**
     * 获取品牌信息
     */
    public IdsServerInformation findServerInformationBySource(String source) {
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(source);
        return idsServerInformation;
    }

    /**
     * 获取出库batchNo
     */
    public Long findBatchNo() {
        Long batchNo = msgOutboundOrderDao.findBatchNo(new SingleColumnRowMapper<Long>(Long.class));
        return batchNo;
    }

    
    public String queryBatchcodeByOrderQueue() {
        String barchCode = inOrderQueueDao.queryBatchcode(new SingleColumnRowMapper<String>(String.class));
        return barchCode;
    }

    
    
    /**
     * 获取出库batchNo_pre
     */
    public Long findBatchNoPre() {
        Long batchNo = msgOutboundOrderDao.findBatchNoPre(new SingleColumnRowMapper<Long>(Long.class));
        return batchNo;
    }

    public List<MsgOutboundOrder> findOutboundOrderBySource(String source) {
        return msgOutboundOrderDao.findOutboundOrderBySource(source, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
    }

    public void updateMsgOutboundOrderById(Long batchId, List<Long> id) {
        msgOutboundOrderDao.updateMsgOutboundOrderById(batchId, id);
    }

    /**
     * 更新出库批次实体
     */
    public Integer updateMsgOutboundOrderBatchNo(Long batchNo, String source) {
        Integer updateCount = msgOutboundOrderDao.updateMsgOutboundOrderBatchNo(batchNo, source, null, DefaultStatus.CREATED.getValue(), new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
        return updateCount;
    }

    /**
     * 更新设置出库批次实体2
     */
    public Integer updateMsgOutboundOrderBatchNo2(Long batchNo, String source, Integer num) {
        Integer updateCount = msgOutboundOrderDao.updateMsgOutboundOrderBatchNo2(batchNo, source, null, DefaultStatus.CREATED.getValue(), num, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
        return updateCount;
    }

    
    public Integer updateQstaBatchCodeByOuid(String batchNo, Integer num) {
        Integer updateQty = inOrderQueueDao.updateQstaBatchCodeByOuid(batchNo, num);
        return updateQty;
    }

    
    /**
     * 更新设置 预售通知
     */
    public Integer updatePreOrderBatchNo(Long batchNo, String source, Integer num) {
        Integer updateCount = advanceOrderAddInfoDao.updatePreOrderBatchNo(batchNo, source, source, num);
        return updateCount;
    }



    /**
     * 获取OutboundOrderBatchNoBySource
     */
    public List<Long> findMsgOutboundOrderBatchNoBySource(String source) {
        List<Long> batchNoList = msgOutboundOrderDao.findMsgOutboundOrderBatchNoBySource(source, new SingleColumnRowMapper<Long>(Long.class));
        return batchNoList;
    }
    
    
    public List<String> findBatchCodeByDetial() {
        List<String> batchCodes = inOrderQueueDao.findBatchCodeByDetial(new SingleColumnRowMapper<String>(String.class));
        return batchCodes;
    }

    public List<Long> findPreOrderBatchNoBySource(String source) {
        List<Long> batchNoList = advanceOrderAddInfoDao.findPreOrderBatchNoBySource(source, new SingleColumnRowMapper<Long>(Long.class));
        return batchNoList;
    }

    /**
 * 
 */
    public List<MsgOutboundOrder> getOutBoundListByBatchNo(Long batchNo) {
        List<MsgOutboundOrder> orderList = msgOutboundOrderDao.getOutBoundListByBatchNo(batchNo);
        return orderList;
    }

    /**
     * 预售 批次 查询
     * 
     * @param batchNo
     * @return
     */
    public List<AdvanceOrderAddInfo> getPreOrderListByBatchNo(Long batchNo) {
        List<AdvanceOrderAddInfo> orderList = advanceOrderAddInfoDao.getPreOrderListByBatchNo(batchNo, new BeanPropertyRowMapper<AdvanceOrderAddInfo>(AdvanceOrderAddInfo.class));
        return orderList;
    }


    /**
     * 发送 预售 付款 通知
     */
    public void sendPreOrder(String source, String sourceWh, Long batchNo, List<AdvanceOrderAddInfo> ad, IdsServerInformation idsServerInformation) {
        MongoLfPreOrder preOrder = new MongoLfPreOrder();
        MongoLfPreOrder preOrder2 = new MongoLfPreOrder();
        Warehouse wh = whDao.getBySource(source, sourceWh);
        if (wh == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }
        String respXml = "";
        WmsPreOrder wmsorder = idsManager.createPreOrderRequestStr(batchNo, ad, idsServerInformation, source);
        if (wmsorder != null) {
            respXml = (String) MarshallerUtil.buildJaxb(wmsorder);
            if (StringUtils.hasText(respXml)) {
                String resultXml = "";
                log.info(source + "|sendPreOrder1->" + respXml);
                try {
                    preOrder.setBatchId(batchNo.toString());
                    preOrder.setSource(sourceWh);
                    preOrder.setType("1");
                    preOrder.setMsg(respXml);
                    mongoOperation.save(preOrder);
                } catch (Exception e) {
                    log.error(source + "|sendPreOrder1->" + respXml);
                }
                resultXml = ServiceType.sendMsgtoIds(ServiceType.LOGISTIC_ORDER_RELEASE, respXml, idsServerInformation.getServerUrl(), idsServerInformation.getKey(), idsServerInformation.getSign());
                log.info(source + "|sendPreOrder2->" + resultXml);
                try {
                    preOrder2.setBatchId(batchNo.toString());
                    preOrder2.setSource(sourceWh);
                    preOrder2.setType("2");
                    preOrder2.setMsg(respXml);
                    mongoOperation.save(preOrder);
                } catch (Exception e) {
                    log.error(source + "|sendPreOrder2->" + resultXml);
                }
                if (StringUtils.hasText(resultXml)) {
                    outPreOrderReceiveConfirm(resultXml);
                }
            }
        }
    }

    public void outPreOrderReceiveConfirm(String resultXml) {// 外包仓 预售
        OrderConfirm orderConfirm = (OrderConfirm) MarshallerUtil.buildJaxb(OrderConfirm.class, resultXml);
        if (orderConfirm != null) {
            // 状态更新成5 （仓库已经接收）
            String batchId = orderConfirm.getBatchID();
            List<ConfirmResult> resultList = orderConfirm.getResult();
            for (ConfirmResult result : resultList) {
                if ("true".equals(result.getSuccess())) {
                    advanceOrderAddInfoDao.updatePreStatusByBatchId(5, Long.valueOf(batchId), result.getExternDocKey());// 跟新OK
                } else if ("false".equals(result.getSuccess()) && "B12".equals(result.getReason())) {
                    advanceOrderAddInfoDao.updatePreStatusByBatchId(5, Long.valueOf(batchId), result.getExternDocKey());// 跟新OK
                } else {
                    advanceOrderAddInfoDao.updatePreStatusByBatchId(2, Long.valueOf(batchId), result.getExternDocKey());
                }
            }
        }
    }

   
    public void createRtnOrderBatchCode(String batchNo){
        try {
            rtnOrderTask.createRtnOrderBatchCode(batchNo);
        } catch (Exception e) {
           log.error("createRtnOrderBatchCode2",e);
        }
   }
    

    /**
     * 发送出库单
     */
    public void sendOrder(String source, String sourceWh, Long batchNo, List<MsgOutboundOrder> orderList, IdsServerInformation idsServerInformation) {
        Warehouse wh = whDao.getBySource(source, sourceWh);
        if (wh == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }

        String respXml = "";
        WmsOrder wmsorder = idsManager.createOrderRequestStr(batchNo, orderList, idsServerInformation, wh.getWhSourceSkuType());
        if (wmsorder != null) {
            log.debug("====>ids batchId:{} , orderNo:{}", batchNo, orderList.get(0).getStaCode());
            respXml = (String) MarshallerUtil.buildJaxb(wmsorder);

            if (StringUtils.hasText(respXml)) {
                String resultXml = "";
                // 放mongdb
                commonLogRecordManager.saveLog(batchNo.toString(), "orderRequest", respXml);
                // log.info(source + "|wmsOrderToIds->" + respXml);
                resultXml = ServiceType.sendMsgtoIds(ServiceType.LOGISTIC_ORDER_NOTIFY, respXml, idsServerInformation.getServerUrl(), idsServerInformation.getKey(), idsServerInformation.getSign());
                // log.info(source + "|IdsTowmsOrder->" + resultXml);
                commonLogRecordManager.saveLog(batchNo.toString(), "orderResponse", resultXml);
                if (StringUtils.hasText(resultXml)) {
                    outOrderReceiveConfirm(resultXml);
                }
            }
        }
    }



    /**
     * 发送出库订单
     */
    public void wmsOrderToIds(String source, String sourceWh) {
        log.debug("wmsOrderToIds-:" + source);
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(source);
        while (true) {

            Long batchNo = msgOutboundOrderDao.findBatchNo(new SingleColumnRowMapper<Long>(Long.class));
            Integer updateCount = msgOutboundOrderDao.updateMsgOutboundOrderBatchNo(batchNo, source, null, DefaultStatus.CREATED.getValue(), new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
            if (updateCount == null || updateCount == 0 || updateCount < 50) {
                break;
            }
        }
        List<Long> batchNoList = msgOutboundOrderDao.findMsgOutboundOrderBatchNoBySource(source, new SingleColumnRowMapper<Long>(Long.class));
        for (Long batchNo : batchNoList) {
            List<MsgOutboundOrder> orderList = null;
            // 查询是否有订单批次没有接收Response
            orderList = msgOutboundOrderDao.getOutBoundListByBatchNo(batchNo);
            // 发送数据
            if (orderList.size() > 0) {
                Warehouse wh = whDao.getBySource(source, sourceWh);
                if (wh == null) {
                    throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
                }

                String respXml = "";
                WmsOrder wmsorder = idsManager.createOrderRequestStr(batchNo, orderList, idsServerInformation, wh.getWhSourceSkuType());
                if (wmsorder != null) {
                    log.debug("====>ids batchId:{} , orderNo:{}", batchNo, orderList.get(0).getStaCode());
                    respXml = (String) MarshallerUtil.buildJaxb(wmsorder);

                    if (StringUtils.hasText(respXml)) {
                        String resultXml = "";
                        // log.info(source + "|wmsOrderToIds->" + respXml);
                        commonLogRecordManager.saveLog(batchNo.toString(), "orderRequest", respXml);
                        resultXml = ServiceType.sendMsgtoIds(ServiceType.LOGISTIC_ORDER_NOTIFY, respXml, idsServerInformation.getServerUrl(), idsServerInformation.getKey(), idsServerInformation.getSign());
                        // log.info(source + "|IdsTowmsOrder->" + resultXml);
                        commonLogRecordManager.saveLog(batchNo.toString(), "orderResponse", resultXml);
                        if (StringUtils.hasText(resultXml)) {
                            outOrderReceiveConfirm(resultXml);
                        }
                    }
                }
            }
        }
    }

    public void outOrderReceiveConfirm(String resultXml) {
        OrderConfirm orderConfirm = (OrderConfirm) MarshallerUtil.buildJaxb(OrderConfirm.class, resultXml);
        if (orderConfirm != null) {
            // 状态更新成5 （仓库已经接收）
            String batchId = orderConfirm.getBatchID();
            List<ConfirmResult> resultList = orderConfirm.getResult();
            for (ConfirmResult result : resultList) {
                if (result.getSuccess().equals("true")) {
                    msgOutboundOrderDao.updateStatusByBatchId2(DefaultStatus.EXECUTING.getValue(), Long.parseLong(batchId));
                } else {
                    msgOutboundOrderDao.clearStatusAndBatchByBatchId(DefaultStatus.SENT.getValue(), Long.parseLong(batchId));
                }
            }
        }
    }



    public void outOrderAEOReceiveConfirm(String resultXml) {
        resultXml = resultXml.replaceAll("xmlns:ns1=\"http:/service.ae.aeo.com/enterprise/schema/baozunorder/v1\"", "");
        resultXml = resultXml.replaceAll("xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\"", "");
        resultXml = resultXml.replaceAll("ns1:BaozunOrderResponse  ", "ns1:BaozunOrderResponse");
        resultXml = resultXml.replaceAll("ns1:", "");
        AEOOrderConfirm orderConfirm = (AEOOrderConfirm) MarshallerUtil.buildJaxb(AEOOrderConfirm.class, resultXml.trim());
        if (orderConfirm != null) {
            // 状态更新成5 （仓库已经接收）
            String batchId = orderConfirm.getBatchID();
            if (orderConfirm.getStatus().equalsIgnoreCase("SUCCESS")) {
                msgOutboundOrderDao.updateStatusByBatchId(DefaultStatus.EXECUTING.getValue(), Long.parseLong(batchId));
            } else {
                msgOutboundOrderDao.clearStatusAndBatchByBatchId(DefaultStatus.SENT.getValue(), Long.parseLong(batchId));
            }

        }
    }

    public void returnNotifyRequesformLF(String source) {
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(source);

        String respXml = idsManager.returnNotifyReques(source, idsServerInformation);
        String resultXml = null;
        if (StringUtils.hasText(respXml)) {
            try {
                // log.debug("退货接口发送报文" + source + "\n" + respXml);
                commonLogRecordManager.saveLog(source, "returnRequest", respXml);
                resultXml = ServiceType.sendMsgtoIds(ServiceType.LOGISTIC_RETURN_NOTIFY, respXml, idsServerInformation.getServerUrl(), idsServerInformation.getKey(), idsServerInformation.getSign());
                // log.debug("退货接口接收报文" + source + "\n" + resultXml);
                commonLogRecordManager.saveLog(source, "returnResponse", resultXml);
            } catch (Exception e) {
                // returnNotifyRequesformLF(source);// 连接出现异常重新发送
                log.error("退货接口报错" + source + "\n", e);
            }
            if (StringUtils.hasText(resultXml)) {
                outReturnOrderReceiveConfirm(resultXml);
                // returnNotifyRequesformLF(source);
            }
        }
    }

    public void outReturnOrderReceiveConfirm(String resultXml) {
        OrderConfirm orderConfirm = (OrderConfirm) MarshallerUtil.buildJaxb(OrderConfirm.class, resultXml);
        if (orderConfirm != null) {
            // 状态更新成5 （仓库已经接收）
            List<ConfirmResult> resultList = orderConfirm.getResult();
            for (ConfirmResult result : resultList) {
                MsgInboundOrder inboundOrder = msgInboundOrderDao.findByStaCode(result.getExternDocKey());
                if (inboundOrder != null) {
                    if (result.getSuccess().equals("true")) {
                        msgInboundOrderDao.updateInboundOrderByStaCode(DefaultStatus.FINISHED.getValue(), result.getExternDocKey());
                    } else if (result.getSuccess().equals("false")) {
                        if (result.getReason().equals("B17")) {
                            msgInboundOrderDao.updateInboundOrderByStaCode(DefaultStatus.FINISHED.getValue(), result.getExternDocKey());
                        } else {
                            msgInboundOrderDao.updateInboundOrderByStaCode(DefaultStatus.ERROR.getValue(), result.getExternDocKey());
                        }

                    }
                }
            }
        }
    }

    public void orderCancelResponseToLF(String source) {
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(source);
        String respXml = idsManager.orderCancelResponse(source, idsServerInformation);
        if (StringUtils.hasText(respXml)) {
            try {
                // log.info(source + "|orderCancelResponseToLF->" + respXml);
                commonLogRecordManager.saveLog(source, "orderCancelResponseToLFRequest", respXml);
                String resultXml = ServiceType.sendMsgtoIds(ServiceType.LOGISTIC_ORDER_CANCEL, respXml, idsServerInformation.getServerUrl(), idsServerInformation.getKey(), idsServerInformation.getSign());
                // log.info(source + "|LForderCancelResponseToBZ->" + respXml);
                commonLogRecordManager.saveLog(source, "orderCancelResponseToLFResponse", resultXml);
                if (resultXml != null) {
                    OrderConfirm orderConfirm = (OrderConfirm) MarshallerUtil.buildJaxb(OrderConfirm.class, resultXml);
                    if (orderConfirm != null) {
                        // 状态更新成5 （仓库已经接收）
                        List<ConfirmResult> resultList = orderConfirm.getResult();
                        for (ConfirmResult result : resultList) {
                            MsgOutboundOrderCancel msgOutboundOrderCancel = msgOutboundOrderCancelDao.getByStaCode(result.getExternDocKey());
                            if (msgOutboundOrderCancel != null) {
                                if (result.getSuccess().equals("true")) {
                                    msgOutboundOrderCancel.setIsCanceled(true);
                                } else if (result.getSuccess().equals("false")) {
                                    msgOutboundOrderCancel.setIsCanceled(false);
                                    if (StringUtils.hasText(result.getReason())) {
                                        if (result.getReason().equals("B11")) {
                                            msgOutboundOrderCancel.setIsCanceled(true);
                                        }
                                        if (result.getReason().equals("B13")) {
                                            msgOutboundOrderCancel.setIsCanceled(true);
                                        }
                                        if (result.getReason().equals("B18")) {
                                            msgOutboundOrderCancel.setIsCanceled(false);
                                        }
                                    }
                                }
                                msgOutboundOrderCancel.setStatus(MsgOutboundOrderCancelStatus.SENT);
                                msgOutboundOrderCancel.setMsg(result.getDescription());
                                msgOutboundOrderCancel.setUpdateTime(new Date());
                                msgOutboundOrderCancelDao.save(msgOutboundOrderCancel);
                            } else {
                                msgOutboundOrderCancelDao.updateOuOrderStatusById(result.getExternDocKey(), MsgOutboundOrderCancelStatus.UNKNOWN.getValue());
                            }
                        }
                    }
                } else {
                    orderCancelResponseToLF(source);
                }
            } catch (Exception e) {
                orderCancelResponseToLF(source);
                // 从新发送
                log.error("", e);
            }
        }
    }

    /**
     * 取消订单推送LF，实时接口
     */
    public ConfirmResult siginOrderCancelResponseToLF(MsgOutboundOrderCancel order, String source) {
        ConfirmResult orderFirm = new ConfirmResult();
        // 获取LF配置信息
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(source);
        // 数据转换格式
        String respXml = idsManager.siginOrderCancelResponse(order, idsServerInformation);
        log.info(order.getStaCode() + ":LF CaneleOrderRequest:" + respXml);
        if (StringUtils.hasText(respXml)) {
            try {
                // log.info("LF CaneleOrderToLFTask Begin..." + source + respXml);

                commonLogRecordManager.saveLog(source, "siginOrderCancelResponseToLFRequest", respXml);
                // 发送请求
                String resultXml = ServiceType.sendMsgtoIds(ServiceType.LOGISTIC_ORDER_CANCEL, respXml, idsServerInformation.getServerUrl(), idsServerInformation.getKey(), idsServerInformation.getSign());
                // log.info(order.getStaCode() + ":LF CaneleOrderResult:" + resultXml);
                commonLogRecordManager.saveLog(source, "siginOrderCancelResponseToLFResponse", resultXml);
                if (resultXml != null) {
                    // 解析请求
                    OrderConfirm orderConfirm = (OrderConfirm) MarshallerUtil.buildJaxb(OrderConfirm.class, resultXml);
                    if (orderConfirm != null) {
                        // 状态更新成5 （仓库已经接收）
                        List<ConfirmResult> resultList = orderConfirm.getResult();
                        orderFirm = resultList.get(0);
                    }
                }
            } catch (Exception e) {
                log.info("siginOrderCancelResponseToLF_ERROR:" + order.getStaCode(), e);
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
            }
        }
        log.debug("LF CaneleOrderToLFTask End..." + source);
        return orderFirm;
    }


    /**
     * 出入库作业
     * 
     * public void idsInventorySynREC(){ List<IdsInventorySynchronous> IdsInvSynList =
     * idsInvSynDao.findIdsInvSynByStatus("REC",new BeanPropertyRowMapperExt
     * <IdsInventorySynchronous>(IdsInventorySynchronous.class)); for (IdsInventorySynchronous inv :
     * IdsInvSynList) { if (inv != null) { if (("REC").equals(inv.getiTRType())) {
     * executeIDSInvSynREC(inv); } } } }
     */

    public void idsInventorySyn(String source) {

        List<IdsInventorySynchronous> idsInvSynListRec = idsInvSynDao.findIdsInvSynByStatus(source, "REC", new BeanPropertyRowMapperExt<IdsInventorySynchronous>(IdsInventorySynchronous.class));
        for (IdsInventorySynchronous inv : idsInvSynListRec) {
            if (inv != null) {
                if (("REC").equals(inv.getiTRType())) {
                    executeIDSInvSynREC(inv);
                }
            }
        }

        if (idsInvSynListRec.size() == 0) {
            List<IdsInventorySynchronous> IdsInvSynListADJ = idsInvSynDao.findIdsInvSyn(source, new BeanPropertyRowMapperExt<IdsInventorySynchronous>(IdsInventorySynchronous.class));
            for (IdsInventorySynchronous inv : IdsInvSynListADJ) {
                if (inv != null) {
                    executeIDSInvAdjustADJ(inv);
                }
            }
        }
    }

    public void executeIDSInvSynREC(IdsInventorySynchronous inv) {
        try {
            StockTransApplication stainfo = staDao.findStaBySlipCode(inv.getSource() + "_" + inv.getwMSDocKey());// 根据相关单据号判断订单是否已经创建
            if (stainfo == null) {
                StockTransApplication sta = idsManager.vmiInventorySynchronous(inv);
                if (sta != null) {
                    List<StaLine> staline = staLineDao.findByStaId(sta.getId());
                    wareHouseManager.purchaseReceiveStep(sta.getId(), staline, null);
                    idsInvSynDao.updateMsgStatusById(DefaultStatus.FINISHED.getValue(), sta.getCode(), inv.getId(), "");
                }
            } else {
                idsInvSynDao.updateMsgStatusById(DefaultStatus.FINISHED.getValue(), "", inv.getId(), "");
            }
        } catch (Exception e) {
            // idsInvSynDao.updateMsgStatusById(DefaultStatus.ERROR.getValue(), "", inv.getId(),
            // "");
            IdsInventorySynchronous inv1 = idsInvSynDao.getByPrimaryKey(inv.getId());
            inv1.setStatus(DefaultStatus.ERROR);
            inv1.setErrorCount(inv.getErrorCount() == null ? 1 : inv.getErrorCount() + 1);
            long currentTime = System.currentTimeMillis() + (10 * inv1.getErrorCount()) * 60 * 1000;
            Date date = new Date(currentTime);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = df.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dates = sdf.parse(nowTime);
                inv1.setNextExecuteTime(dates);
                idsInvSynDao.save(inv1);
            } catch (ParseException e1) {
                if (log.isErrorEnabled()) {
                    log.error("executeIDSInvSynREC ParseException:", e1);
                }
            } finally {

            }
            log.error("executeIDSInvSynREC error : " + inv.getBatchID() + e.toString());
            if (inv.getIsSend() == null) {
                taskOmsOutManager.sendEmailIdsInventory(inv);
            }
        }

    }

    public void executeIDSInvAdjustADJ(IdsInventorySynchronous inv) {
        try {
            InventoryCheck checkinfoCheck = inventoryCheckDao.findinvCheckBySlipCode(inv.getSource() + "_" + inv.getwMSDocKey());
            if (checkinfoCheck != null) {
                idsInvSynDao.updateMsgStaById(DefaultStatus.FINISHED.getValue(), inv.getId(), checkinfoCheck.getCode());
                return;
            }
            InventoryCheck check = idsManager.vmiInvAdjustByWh(inv);
            if (check != null) {
                idsInvSynDao.updateMsgStaById(DefaultStatus.FINISHED.getValue(), inv.getId(), check.getCode());
                idsManager.executionVmiAdjustment(check);
            } else {
                log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {inv.getwMSDocKey()});
                idsInvSynDao.updateMsgStaById(DefaultStatus.ERROR.getValue(), inv.getId(), null);
                // try {
                // throw new Exception("InventoryCheck create error" + inv.getwMSDocKey());
                // } catch (Exception e) {
                // idsInvSynDao.updateMsgStaById(DefaultStatus.ERROR.getValue(), inv.getId(), null);
                // log.error("", e);
                // if (inv.getIsSend() == null) {
                // taskOmsOutManager.sendEmailIdsInventory(inv);
                // }
                // }
            }
        } catch (BusinessException e) {
            log.error("error  ids  error code is :" + inv.getBatchID() + e.getErrorCode());
            // idsInvSynDao.updateMsgStaById(DefaultStatus.ERROR.getValue(), inv.getId(), null);
            IdsInventorySynchronous inv1 = idsInvSynDao.getByPrimaryKey(inv.getId());
            inv1.setStatus(DefaultStatus.ERROR);
            inv1.setErrorCount(inv.getErrorCount() == null ? 1 : inv.getErrorCount() + 1);
            long currentTime = System.currentTimeMillis() + (10 * inv1.getErrorCount()) * 60 * 1000;
            Date date = new Date(currentTime);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = df.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dates = sdf.parse(nowTime);
                inv1.setNextExecuteTime(dates);
                idsInvSynDao.save(inv1);
            } catch (ParseException e1) {
                if (log.isErrorEnabled()) {
                    log.error("executeIDSInvSynREC executeIDSInvAdjustADJ:", e1);
                }
            } finally {

            }
        } catch (Exception e) {
            log.error("executeIDSInvAdjustADJ" + inv.getBatchID(), e);
            // idsInvSynDao.updateMsgStaById(DefaultStatus.ERROR.getValue(), inv.getId(), null);
            IdsInventorySynchronous inv1 = idsInvSynDao.getByPrimaryKey(inv.getId());
            inv1.setStatus(DefaultStatus.ERROR);
            inv1.setErrorCount(inv.getErrorCount() == null ? 1 : inv.getErrorCount() + 1);
            long currentTime = System.currentTimeMillis() + (10 * inv1.getErrorCount()) * 60 * 1000;
            Date date = new Date(currentTime);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = df.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dates = sdf.parse(nowTime);
                inv1.setNextExecuteTime(dates);
                idsInvSynDao.save(inv1);
            } catch (ParseException e1) {
                if (log.isErrorEnabled()) {
                    log.error("executeIDSInvSynREC executeIDSInvAdjustADJ ParseException:", e1);
                }
            } finally {

            }
            if (inv.getIsSend() == null) {
                taskOmsOutManager.sendEmailIdsInventory(inv);
            }
        }
    }

    public void receiveAEOSkuMasterByMq(String message) {
        idsManager.receiveAEOSkuMasterByMq(message);
    }

    public void executeIDSInvSynIQC(IdsInventorySynchronous inv) {
        String staCode = "";
        try {
            // StockTransApplication sta=idsManager.vmiInvUpdateStatus(inv);
            // staCode=sta.getCode();
            StockTransApplication sta = staDao.findStaByCode("X200008667358");
            if (sta != null) {
                idsInvSynDao.updateMsgStaById(DefaultStatus.FINISHED.getValue(), inv.getId(), sta.getCode());
                wareHouseManagerExe.executeInvStatusChangeForImpory(sta.getId(), 0l, false, true);
            }
        } catch (Exception e) {
            idsInvSynDao.updateMsgStaById(DefaultStatus.ERROR.getValue(), inv.getId(), staCode);
            log.error("", e);
        }

    }

    public Map<String, IdsServerInformation> findIdsServiceInfo() {
        Map<String, IdsServerInformation> result = new HashMap<String, IdsServerInformation>();
        List<IdsServerInformation> list = idsServerInformationDao.findAll();
        for (IdsServerInformation s : list) {
            result.put(s.getBzKey(), s);
        }
        return result;
    }

    @Override
    public void wmsAeoOrderToIds(String source, String sourceWh) {
        log.debug("wmsOrderToAEORMI-:" + source);
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(source);
        while (true) {
            Long batchNo = msgOutboundOrderDao.findBatchNo(new SingleColumnRowMapper<Long>(Long.class));
            Integer updateCount = msgOutboundOrderDao.updateMsgOutboundOrderBatchNo(batchNo, source, null, DefaultStatus.CREATED.getValue(), new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
            if (updateCount == null || updateCount == 0 || updateCount < 50) {
                break;
            }
        }
        try {
            ChooseOption choose = chooseOptionDao.findByCategoryCode("AeoRMSUrl");
            List<Long> batchNoList = msgOutboundOrderDao.findMsgOutboundOrderBatchNoBySource(source, new SingleColumnRowMapper<Long>(Long.class));
            for (Long batchNo : batchNoList) {
                List<MsgOutboundOrder> orderList = null;
                // 查询是否有订单批次没有接收Response
                orderList = msgOutboundOrderDao.getOutBoundListByBatchNo(batchNo);
                // 发送数据
                if (orderList.size() > 0) {
                    Warehouse wh = whDao.getBySource(source, sourceWh);
                    if (wh == null) {
                        throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
                    }

                    String respXml = "";
                    BaozunOrderRequest wmsorder = idsManager.createAeoOrderRequestStr(batchNo, orderList, wh.getWhSourceSkuType(), idsServerInformation);
                    if (wmsorder != null) {
                        log.debug("====>ids batchId:{} , orderNo:{}", batchNo, orderList.get(0).getStaCode());
                        respXml = (String) MarshallerUtil.buildJaxb(wmsorder);

                        if (StringUtils.hasText(respXml)) {
                            String resultXml = "";
                            // log.info(source + "|wmsOrderToAEORMI->Send" + respXml);
                            commonLogRecordManager.saveLog(batchNo.toString(), "wmsOrderToAEORMISend", respXml);
                            resultXml = ServiceType.sendMsgtoAeo(choose.getCategoryName(), respXml, choose.getOptionKey(), choose.getOptionValue());
                            // log.info(source + "|wmsOrderToAEORMI->Receive" + resultXml);
                            commonLogRecordManager.saveLog(batchNo.toString(), "wmsOrderToAEORMIReceive", resultXml);
                            if (StringUtils.hasText(resultXml)) {
                                outOrderAEOReceiveConfirm(resultXml);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("wmsAeoOrderToIds-:" + source + e.toString());
        }
    }


    @Override
    public void inboundIdsNike(String source) {
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(source);

        String respXml = idsManager.inboundIdsNike(source, idsServerInformation);
        String resultXml = null;
        if (StringUtils.hasText(respXml)) {
            try {
                commonLogRecordManager.saveLog(source, "inboundIdsNikeRequest", respXml);
                // log.error("退货接口发送报文" + source + "\n" + respXml);
                resultXml = ServiceType.sendMsgtoIds(ServiceType.LOGISTIC_RETURN_NOTIFY, respXml, idsServerInformation.getServerUrl(), idsServerInformation.getKey(), idsServerInformation.getSign());
                // log.error("退货接口接收报文" + source + "\n" + resultXml);
                commonLogRecordManager.saveLog(source, "inboundIdsNikeResponse", resultXml);
            } catch (Exception e) {
                // returnNotifyRequesformLF(source);// 连接出现异常重新发送
                log.error("退货接口报错" + source + "\n", e);
            }
            if (StringUtils.hasText(resultXml)) {
                outReturnOrderReceiveConfirm(resultXml);
                // returnNotifyRequesformLF(source);
            }
        }
    }

    @Override
    public void idsNikeFeedbackTask(String source) {
        List<MsgRtnInboundOrder> msgOrderNewInfo = msgRtnInboundOrder.findInboundByStatus(source, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (msgOrderNewInfo.size() > 0) {
            log.debug("===========================msgOrderNewInfo size : {}  ===================== ", msgOrderNewInfo.size());
            for (MsgRtnInboundOrder msgNewInorder : msgOrderNewInfo) {
                try {
                    wareHouseManagerProxy.msgInBoundWareHouse(msgNewInorder);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        log.error("idsFeedbackTask error ,error code is : {}", ((BusinessException) e).getErrorCode());
                    } else {
                        log.error("", e);
                    }
                }
            }
        }

    }

    public void deleteSoLog() {
        wmsSalesOrderLogDao.deleteWmsSalesOrderLogById();
    }

}
