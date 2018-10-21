package com.jumbo.wms.manager.boc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.connector.base.constants.ConstantsBase;
import cn.baozun.bh.connector.base.model.InventoryMovements;
import cn.baozun.bh.connector.base.model.InventoryMovements.InventoryMovement;
import cn.baozun.bh.connector.base.model.InventorySnapshots;
import cn.baozun.bh.connector.base.model.InventorySnapshots.InventorySnapshot;
import cn.baozun.bh.connector.base.model.MasterDatas;
import cn.baozun.bh.connector.base.model.MasterDatas.MasterData;
import cn.baozun.bh.connector.base.model.Order;
import cn.baozun.bh.connector.base.model.Order.OrderLine;
import cn.baozun.bh.connector.base.model.OrderCancelConfirmations;
import cn.baozun.bh.connector.base.model.OrderCancelConfirmations.OrderCancelConfirmation;
import cn.baozun.bh.connector.base.model.OrderCancels;
import cn.baozun.bh.connector.base.model.OrderCancels.OrderCancel;
import cn.baozun.bh.connector.base.model.OrderOutbounds;
import cn.baozun.bh.connector.base.model.OrderOutbounds.OrderOutbound;
import cn.baozun.bh.connector.base.model.Orders;
import cn.baozun.bh.connector.base.model.Prices;
import cn.baozun.bh.connector.base.model.Prices.Price;
import cn.baozun.bh.connector.base.model.ReturnOrder;
import cn.baozun.bh.connector.base.model.ReturnOrder.ReturnOrderLine;
import cn.baozun.bh.connector.base.model.ReturnOrderInbound;
import cn.baozun.bh.connector.base.model.ReturnOrderInbound.ReturnOrderInboundLine;
import cn.baozun.bh.connector.base.model.ReturnOrderInbounds;
import cn.baozun.bh.connector.base.model.ReturnOrders;
import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.boc.MasterDataDao;
import com.jumbo.dao.boc.PriceDataDao;
import com.jumbo.dao.boc.VmiInventoryMovementDataDao;
import com.jumbo.dao.boc.VmiInventorySnapshotDataDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgInventoryStatusDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.mq.MqMsgUtil;
import com.jumbo.webservice.biaogan.manager.InOutBoundManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.boc.PriceData;
import com.jumbo.wms.model.boc.VmiInventoryMovementData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotDataCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

@Transactional
@Service("masterDataManager")
public class MasterDataManagerImpl implements MasterDataManager {

    protected static final Logger log = LoggerFactory.getLogger(MasterDataManagerImpl.class);

    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private MasterDataDao masterDataDao;
    @Autowired
    private InventoryCheckDifTotalLineDao vmiinvCheckLineDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private WarehouseLocationDao locDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private PriceDataDao priceDataDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private InventoryCheckDifferenceLineDao icDifferenceLineDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundOrderLineDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    /**
     * MQ消息模板
     */
    private JmsTemplate bhMqJmsTemplate;

    @Autowired
    private InOutBoundManager inoutManager;

    @Resource
    private ApplicationContext applicationContext;

    @Autowired
    private MsgInboundOrderLineDao msgILineDao;

    @Autowired
    private MsgInventoryStatusDao msgInventoryStatusDao;

    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private VmiInventorySnapshotDataDao vmiInventorySnapshotDataDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private VmiInventoryMovementDataDao vmiInventoryMovementDataDao;

    @PostConstruct
    protected void init() {
        try {
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named bhMqJmsTemplate Class:MasterDataManagerImpl");
        }
    }

    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }

    @Override
    public void receiveMasterDateByMq(String message) {
        log.info("=========MasterDate START===========");

        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);

            String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            MasterDatas masterdatas = (MasterDatas) JSONUtil.jsonToBean(messageContent, MasterDatas.class);
            String Source = masterdatas.getSource();
            String fileName = masterdatas.getSourceFileName();
            Collection<MasterData> datas = masterdatas.getMasterData();
            for (MasterData m : datas) {
                com.jumbo.wms.model.boc.MasterData mdata = new com.jumbo.wms.model.boc.MasterData();
                mdata.setUpc(m.getUpc());
                mdata.setBarCode(m.getBarcode());
                mdata.setArticleNumber(m.getArticleNumber());
                mdata.setBrand(m.getBrand());
                mdata.setName(m.getName());
                mdata.setEnName(m.getEnName());
                mdata.setListPrice(m.getListPrice());
                mdata.setColor(m.getColor());
                mdata.setSize1(m.getSize1());
                mdata.setSize2(m.getSize2());
                mdata.setYear(m.getYear());
                mdata.setSeason(m.getSeason());
                mdata.setCategory(m.getCategory());
                mdata.setLength(m.getLength());
                mdata.setWidth(m.getWidth());
                mdata.setHeight(m.getHeight());
                mdata.setWeight(m.getWeight());
                mdata.setUserDefined1(m.getUserdefined1());
                mdata.setUserDefined2(m.getUserdefined2());
                mdata.setUserDefined3(m.getUserdefined3());
                mdata.setUserDefined4(m.getUserdefined4());
                mdata.setUserDefined5(m.getUserdefined5());
                mdata.setStatus(DefaultStatus.CREATED);
                mdata.setFileName(fileName);
                mdata.setConfirmId(confirmId);
                mdata.setCreateTime(new Date());
                mdata.setSource(Source);
                mdata = masterDataDao.save(mdata);
                masterDataDao.flush();
                log.info("=========MASTERDATE SUCCESS===========");
            }
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public void receivePriceData(String message) {
        log.info("=========PriceData START===========");

        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);

            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {

                messageContent = ZipUtil.decompress(messageContent);
            }

            Prices prices = (Prices) JSONUtil.jsonToBean(messageContent, Prices.class);
            String Source = prices.getSource();
            String fileName = prices.getSourceFileName();
            List<Price> priceList = prices.getPrices();

            for (Price price : priceList) {
                PriceData priceData = new PriceData();
                priceData.setCreateTime(new Date());
                priceData.setStatus(DefaultStatus.CREATED);
                priceData.setUpc(price.getUpc());
                priceData.setBarCode(price.getBarcode());
                priceData.setArticleNumber(price.getArticleNumber());
                priceData.setPrice(price.getPrice());
                priceData.setStartDate(df.parse(price.getStartDate()));
                priceData.setSource(Source);
                priceData.setFileName(fileName);
                priceDataDao.save(priceData);
            }
            priceDataDao.flush();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 出库通知
     */
    public void mqSendSalesOrder(String source) {

        log.info("=========mqSendSalesOrder START=====" + source + "====1==");
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");

        Orders orders = new Orders();
        List<Order> orderList = new ArrayList<Order>();
        List<MsgOutboundOrder> msgOutboundOrders = msgOutboundOrderDao.findSOOutboundOrder(source, false, null, DefaultStatus.CREATED.getValue(), null, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
        log.info("=========mqSendSalesOrder MSG:=====" + source + "===" + msgOutboundOrders + "==2====");
        for (MsgOutboundOrder order : msgOutboundOrders) {
            log.info("=========mqSendSalesOrder order:=" + order.getStaCode() + "======3====");
            StockTransApplication sta = staDao.findStaByCode(order.getStaCode());
            log.info("=========mqSendSalesOrder sta:=" + sta.getCode() + "======4====");
            Order o = new Order();
            o.setOrderCode(order.getStaCode());
            o.setSource(order.getSource());
            o.setStoreCode(order.getShopId().toString());// 使用ID 还是用shopid
            o.setOrderTime(df.format(order.getCreateTime()));
            o.setTotalAmount(order.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP));
            o.setSlipCode(order.getOuterOrderCode());
            o.setUserId(order.getUserId());
            // 查询是否换货出去
            if (order.getStaType() == 42) {
                if (sta != null) {
                    if (sta.getSlipCode1().indexOf("TB") != -1) {
                        o.setSlipCode(sta.getSlipCode2());
                    } else {
                        o.setSlipCode(sta.getSlipCode1());
                    }
                    o.setOrderTime(df.format(sta.getCreateTime()));
                    o.setUserId(sta.getStaDeliveryInfo().getOrderUserCode());
                }
            }
            // 根据行上金额统计整单不含积分金额
            BigDecimal cashAmount = sta.getTotalActual();
            if (cashAmount != null) {
                o.setCashAmount(cashAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                o.setCashAmount(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
            }

            if (order.getTransferFee() != null) {
                o.setShippingAmount(order.getTransferFee().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                o.setShippingAmount(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
            }

            if (("1").equals(order.getPaymentType())) {
                o.setIsCod("1");
                o.setCodAmount(order.getTotalActual().add(order.getTransferFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                o.setIsCod("0");
                o.setCodAmount(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
            }

            o.setRemark(replaceSpecialCharacter(order.getRemark()));
            o.setReceiver(order.getReceiver());
            o.setZipcode(order.getZipcode());
            o.setTelephone(order.getTelePhone());
            o.setMobile(order.getMobile());
            o.setProvince(order.getProvince());
            o.setCity(order.getCity());
            o.setDistrict(order.getDistrict());
            o.setAddress(replaceSpecialCharacter(order.getAddress()));
            o.setEmail(order.getMemberEmail());
            if (("naturecare").equals(order.getSource())) {
                o.setRemarkEnglish(order.getRemarkEnglish());
                o.setUserIdEnglish(order.getUserIDEnglish());
                o.setReceiverEnglish(order.getReceiverEnglish());
                o.setProvinceEnglish(order.getProvinceEnglish());
                o.setCityEnglish(order.getCityEnglish());
                o.setDistrictEnglish(order.getDistrictEnglish());
                o.setAddressEnglish(order.getAddressEnglish());
            }
            List<OrderLine> orderLines = new ArrayList<OrderLine>();
            List<MsgOutboundOrderLine> msgLine = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(order.getId());
            log.info("=========mqSendSalesOrder MsgOutBundLine:=" + msgLine.size() + "======5====");
            int i = 0;
            for (MsgOutboundOrderLine msgOutboundOrder : msgLine) {
                log.info("=========mqSendSalesOrder MsgOutBundLine:=" + msgOutboundOrder.getId() + "======6====");
                OrderLine line = new OrderLine();
                i++;
                line.setLineNo(String.valueOf(i));
                line.setOrderCode(order.getStaCode());
                if (("naturecare").equals(order.getSource())) {
                    if (msgOutboundOrder.getSku() != null) {
                        line.setUpc(msgOutboundOrder.getSku().getBarCode());
                    }
                } else {
                    if (msgOutboundOrder.getSku() != null) {
                        line.setUpc(msgOutboundOrder.getSku().getExtensionCode2());
                    }
                }
                line.setPrice(msgOutboundOrder.getUnitPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
                line.setQuantity(new BigDecimal(msgOutboundOrder.getQty()));
                orderLines.add(line);
            }
            o.setOrderLines(orderLines);

            orderList.add(o);
            log.info("=========mqSendSalesOrder MsgOutBundLine:=" + orderList.size() + "======7====");

            msgOutboundOrderDao.updateMsgoutboundOrder(order.getId(), DefaultStatus.FINISHED.getValue());
        }

        if (orderList.size() == 0) {
            return;
        }
        orders.setOrders(orderList);
        orders.setSource(source);
        String msg = JSONUtil.beanToJson(orders);
        ConnectorMessage connectorMessage = new ConnectorMessage();

        connectorMessage.setPlatformCode(ConstantsBase.BH_CONNECTOR_PLATFORM_CODE_BASE);
        connectorMessage.setInterfaceCode(ConstantsBase.BH_CONNECTOR_INTERFACE_CODE_BASE_ORDER);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));

        msg = JSONUtil.beanToJson(connectorMessage);
        log.info("=========mqSendSalesOrder   msg:" + msg + "===8====");

        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "oms2bh_base_order_production", msg);
        log.info("=========mqSendSalesOrder END=====" + source + "====9==");
    }

    /**
     * 出库反馈
     * 
     * @param message
     */
    public List<MsgRtnOutbound> receiveMsgRtnOutBoundData(String message) {

        log.info("=========receiveMsgRtnOutBoundDat START===========");
        List<MsgRtnOutbound> msgRtnOutbound = new ArrayList<MsgRtnOutbound>();
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);

            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {

                messageContent = ZipUtil.decompress(messageContent);
            }
            OrderOutbounds orderOutbounds = (OrderOutbounds) JSONUtil.jsonToBean(messageContent, OrderOutbounds.class);
            String sourceWh = orderOutbounds.getSource();
            List<OrderOutbound> orderOutboundsList = orderOutbounds.getOrderOutbounds();
            for (OrderOutbound order : orderOutboundsList) {
                MsgRtnOutbound rtnOutbound = new MsgRtnOutbound();
                rtnOutbound.setCreateTime(new Date());
                rtnOutbound.setSource(sourceWh);
                rtnOutbound.setSourceWh(sourceWh);
                rtnOutbound.setStatus(DefaultStatus.CREATED);
                rtnOutbound.setStaCode(order.getOrderCode());
                rtnOutbound.setOutboundTime(df.parse(order.getOutboundTime()));
                rtnOutbound.setLpCode(order.getLogisticCode());
                rtnOutbound.setTrackingNo(order.getTrackingNo());
                rtnOutbound.setWeight(order.getWeight());
                rtnOutbound.setRemark(order.getRemark());
                rtnOutbound = msgRtnOutboundDao.save(rtnOutbound);
                msgRtnOutbound.add(rtnOutbound);
            }
            msgRtnOutboundDao.flush();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
        }
        return msgRtnOutbound;
    }

    /**
     * 根据仓库反馈执行出库
     * 
     * @param msgoutList 传回出库信息
     */
    public void executeMsgRtnOutbound(List<MsgRtnOutbound> msgoutList) {
        for (MsgRtnOutbound msgRtnbound : msgoutList) {
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
                log.error("executeMsgRtnOutbound error ! OUT STA :" + e.getErrorCode());
            } catch (Exception ex) {
                wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.CANCELED.getValue());
                log.info("=========BOCEXECUTEORDEROUTBOUND ERROR===========");
                log.error("" + ex);
            }
        }

    }

    /**
     * 获取退货订单
     * 
     * @param source
     * @return
     */
    public void receiveMsgInboundOrder(String source) {

        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        List<ReturnOrder> returnOrderList = new ArrayList<ReturnOrder>();
        ReturnOrders returnOrders = new ReturnOrders();
        List<MsgInboundOrder> inboundList = msgInboundOrderDao.findMsgReturnInboundByStatus(source, null, StockTransApplicationType.INBOUND_RETURN_REQUEST);
        for (MsgInboundOrder msgInboundOrder : inboundList) {
            StockTransApplication sta = staDao.getByCode(msgInboundOrder.getStaCode());
            StaDeliveryInfo d = sta.getStaDeliveryInfo();
            // MsgInboundOrderCommand comd =
            // msgInboundOrderDao.findMsgInboundOrderbyRefSlipCode(msgInboundOrder.getRefSlipCode(),
            // new
            // BeanPropertyRowMapper<MsgInboundOrderCommand>(MsgInboundOrderCommand.class));
            ReturnOrder order = new ReturnOrder();
            order.setOrderCode(msgInboundOrder.getStaCode());
            order.setStoreCode(msgInboundOrder.getShopId().toString());
            if (d != null) {
                order.setTrackingNo(d.getTrackingNo());
                order.setCustomer(d.getReceiver());
            }
            if (sta.getSlipCode1().indexOf("TB") != -1) {
                order.setSlipCode(sta.getSlipCode2());
            } else {
                order.setSlipCode(sta.getSlipCode1());
            }

            order.setApplyTime(df.format(msgInboundOrder.getCreateTime()));
            order.setTelephone(msgInboundOrder.getTelephone());
            order.setMobile(msgInboundOrder.getMobile());

            order.setAmount(msgInboundOrder.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP));
            order.setRemark(replaceSpecialCharacter(msgInboundOrder.getRemark()));
            List<ReturnOrderLine> returnOrderLine = new ArrayList<ReturnOrderLine>();
            List<MsgInboundOrderLine> msgLineList = msgILineDao.fomdMsgInboundOrderLineByOId(msgInboundOrder.getId());
            int i = 0;
            for (MsgInboundOrderLine inLinecomd : msgLineList) {
                i++;
                ReturnOrderLine line = new ReturnOrderLine();

                line.setOrderCode(msgInboundOrder.getStaCode());
                line.setLineNo(String.valueOf(i));
                line.setQuantity(new BigDecimal(inLinecomd.getQty()));
                line.setUpc(inLinecomd.getSku().getExtensionCode2());
                line.setPrlce(inLinecomd.getUnitPrice().setScale(2, BigDecimal.ROUND_HALF_UP));

                String vmiStatus = "";
                if (inLinecomd.getInvStatus() != null) {
                    vmiStatus = msgInventoryStatusDao.findInventoryStatusByBzStatus(inLinecomd.getInvStatus().getId(), source, new SingleColumnRowMapper<String>(String.class));
                }
                line.setStatus(vmiStatus);// 根据仓库查询库存状态
                line.setRemark("");// 要取什么地方的的备注
                returnOrderLine.add(line);
            }
            order.setReturnOrderLines(returnOrderLine);
            returnOrderList.add(order);

            msgInboundOrderDao.updateMsgInboundStatus(msgInboundOrder.getId(), DefaultStatus.FINISHED.getValue());
        }

        if (inboundList.size() == 0) {
            return;
        }

        returnOrders.setReturnOrders(returnOrderList);
        returnOrders.setSource(source);
        String msg = JSONUtil.beanToJson(returnOrders);
        // System.out.println(msg);
        ConnectorMessage connectorMessage = new ConnectorMessage();

        connectorMessage.setPlatformCode(ConstantsBase.BH_CONNECTOR_PLATFORM_CODE_BASE);
        connectorMessage.setInterfaceCode(ConstantsBase.BH_CONNECTOR_INTERFACE_CODE_BASE_RETURN_ORDER);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));

        msg = JSONUtil.beanToJson(connectorMessage);
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "oms2bh_base_returnorder_production", msg);

    }

    /**
     * 
     * @param message
     * @return
     */
    public List<MsgRtnInboundOrder> findMsgRtnOutbounds(String message) {
        List<MsgRtnInboundOrder> msgRtnInboundOrders = new ArrayList<MsgRtnInboundOrder>();

        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);

            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {

                messageContent = ZipUtil.decompress(messageContent);
            }

            ReturnOrderInbounds returnOrderInbounds = (ReturnOrderInbounds) JSONUtil.jsonToBean(messageContent, ReturnOrderInbounds.class);
            String source = returnOrderInbounds.getSource();
            // String filename = returnOrderInbounds.getSourceFileName();
            List<ReturnOrderInbound> returnOrderInboundList = returnOrderInbounds.getReturnOrderInbounds();
            for (ReturnOrderInbound returnOrderInbound : returnOrderInboundList) {
                MsgRtnInboundOrder msgRtnInboundOrder = new MsgRtnInboundOrder();
                msgRtnInboundOrder.setCreateTime(new Date());
                msgRtnInboundOrder.setSource(source);
                msgRtnInboundOrder.setStatus(DefaultStatus.CREATED);
                msgRtnInboundOrder.setStaCode(returnOrderInbound.getOrderCode());
                msgRtnInboundOrder.setInboundTime(df.parse(returnOrderInbound.getInboundTime()));
                msgRtnInboundOrder.setRemark(returnOrderInbound.getRemark());
                msgRtnInboundOrder = msgRtnInboundOrderDao.save(msgRtnInboundOrder);
                StockTransApplication sta = staDao.getByCode(returnOrderInbound.getOrderCode());
                BiChannel c = shopDao.getByCode(sta.getOwner());
                Map<String, InventoryStatus> invoiceNumMap = inoutManager.findMsgInvStatusByStaCode(msgRtnInboundOrder.getStaCode());
                for (ReturnOrderInboundLine line : returnOrderInbound.getReInboundLines()) {
                    MsgRtnInboundOrderLine inboundOrderLine = new MsgRtnInboundOrderLine();
                    Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(line.getUpc(), c.getCustomer().getId(), c.getId());
                    if (sku == null) {
                        throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
                    } else {
                        inboundOrderLine.setSkuCode(sku.getCode());
                        inboundOrderLine.setBarcode(sku.getBarCode());
                    }
                    inboundOrderLine.setQty(line.getQuantity().longValue());
                    inboundOrderLine.setInvStatus(invoiceNumMap.get(line.getStatus()));
                    inboundOrderLine.setMsgRtnInOrder(msgRtnInboundOrder);
                    msgRtnInboundOrderLineDao.save(inboundOrderLine);

                }
                msgRtnInboundOrders.add(msgRtnInboundOrder);
            }
            msgRtnInboundOrderDao.flush();

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
        }
        return msgRtnInboundOrders;
    }

    /**
     * 接收品牌库存消息，放入增量出入库表
     */
    @Override
    public void receiveInventoryMovement(String message) {
        log.info("==============================RECEIVEINVENTORYMOVEMENT BEGIN=======================================");
        try {
            InventoryMovements invmms = (InventoryMovements) JSONUtil.jsonToBean(message, InventoryMovements.class);
            List<InventoryMovement> invm = invmms.getInventoryMovement();
            log.info("receive InventoryMovement message size:" + invm.size());
            for (InventoryMovement vim : invm) {
                VmiInventoryMovementData vimd = new VmiInventoryMovementData();
                vimd.setCreateTime(new Date());
                vimd.setBillNo(vim.getBillNo());
                vimd.setWarehouse(vim.getWarehouse());
                vimd.setStoreCode(vim.getStoreCode());
                vimd.setMoveDate(vim.getMoveDate());
                vimd.setMoveType(vim.getMoveType());
                vimd.setUpc(vim.getUpc());
                vimd.setQuantity(vim.getQuantity());
                if (vim.getStatus().equals("0")) {
                    vimd.setStatus("残次品");
                }
                if (vim.getStatus().equals("1")) {
                    vimd.setStatus("良品");
                }
                vimd.setExecuteStatus("1");
                vimd.setSource(invmms.getSource());
                vimd.setFileName(invmms.getSourceFileName());

                vmiInventoryMovementDataDao.save(vimd);
            }
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
        }
        log.info("==============================RECEIVEINVENTORYMOVEMENT END=======================================");
    }

    /**
     * 库存信息保存
     * 
     * @param message
     * @return
     */
    public List<VmiInventorySnapshotData> receiveInventorySnapshot(String message) {

        log.info("=========INVENTORYSNAPSHOT START===========");

        List<VmiInventorySnapshotData> inventoryDatas = new ArrayList<VmiInventorySnapshotData>();

        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);

            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {

                messageContent = ZipUtil.decompress(messageContent);
            }

            InventorySnapshots iSnapshots = (InventorySnapshots) JSONUtil.jsonToBean(messageContent, InventorySnapshots.class);
            InventoryStatus is = null;
            InventoryStatus isForSaleTrue = null;
            InventoryStatus isForSaleFlase = null;
            Warehouse wh = null;
            OperationUnit ou = null;
            String fileName = iSnapshots.getSourceFileName();
            List<InventorySnapshot> snapshots = iSnapshots.getInventorySnapshots();
            String source = iSnapshots.getSource();

            if (wh == null) {
                wh = warehouseDao.getBySource(source, source);
                if (wh == null) {
                    log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {source});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                } else {
                    Long ouId = wh.getOu().getId();
                    ou = ouDao.getByPrimaryKey(ouId);
                    if (ou == null) {
                        log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {source});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                }
            }

            if (is == null) {
                Long companyId = null;
                if (ou.getParentUnit() != null) {
                    OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                    if (ou1 != null) {
                        companyId = ou1.getParentUnit().getId();
                    }
                }
                isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
                isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);

            }
            for (InventorySnapshot iSnapshot : snapshots) {

                VmiInventorySnapshotData data = new VmiInventorySnapshotData();
                data.setFileName(fileName);
                data.setVmiStatus(DefaultStatus.CREATED);
                data.setCreateData(new Date());
                data.setSource(source);
                data.setWarehouse(iSnapshot.getWarehouse());
                data.setStoreCode(iSnapshot.getStoreCode());
                data.seteDate(iSnapshot.getDate());
                data.setInventoryStatus(Integer.parseInt(iSnapshot.getInventoryStatus()) == 0 ? isForSaleFlase : isForSaleTrue);
                data.setUpc(iSnapshot.getUpc());
                data.setOnhandQty(iSnapshot.getOnhandQty().longValue());
                vmiInventorySnapshotDataDao.save(data);
                inventoryDatas.add(data);
            }
            vmiInventorySnapshotDataDao.flush();

        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
        }
        return inventoryDatas;
    }

    /**
     * 增量库存
     * 
     * @param List
     */
    public InventoryCheck executeInventoryCheck(List<VmiInventoryMovementData> datas) {
        InventoryStatus is = null;
        Long companyId = null;
        OperationUnit ou = null;
        String innerShopCode = null;
        Warehouse wh = null;
        BiChannel shop = null;
        InventoryCheck inventoryCheck = new InventoryCheck();
        inventoryCheck.setCreateTime(new Date());
        inventoryCheck.setSlipCode(datas.get(0).getBillNo());
        inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
        inventoryCheck.setType(InventoryCheckType.VMI);
        inventoryCheck.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), inventoryCheck));
        inventoryCheck = inventoryCheckDao.save(inventoryCheck);
        if (null == inventoryCheck.getCode() && inventoryCheck.getCode().trim().equals("")) {
            log.debug("===============updateInvMovementListByBillNo {} update error ================", new Object[] {inventoryCheck.getSlipCode()});
            throw new BusinessException("盘点单单号不存在！");
        }
        try {
            vmiInventoryMovementDataDao.updateInvMovementListICCodeByBillNo(inventoryCheck.getSlipCode(), inventoryCheck.getCode());
        } catch (Exception e) {
            log.debug("===============updateInvMovementListByBillNo {} update error ================", new Object[] {inventoryCheck.getSlipCode()});
            throw new BusinessException("更新主档信息对应盘点单号失败！error：" + e);
        }

        for (VmiInventoryMovementData datainfo : datas) {
            try {

                if (StringUtils.hasText(datainfo.getStoreCode())) {
                    Long sidLong = Long.parseLong(datainfo.getStoreCode());
                    shop = shopDao.getByPrimaryKey(sidLong);
                } else {
                    throw new Exception("缺少店铺标识!---------storeCode");
                }
                if (shop == null) {
                    throw new Exception("没有找到店铺信息!storeCode:" + datainfo.getStoreCode());
                }
                wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
                if (wh == null) {
                    wh = warehouseDao.getBySource(datainfo.getSource(), datainfo.getSource());
                    if (wh == null) {
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    } else {
                        Long ouId = wh.getOu().getId();
                        ou = ouDao.getByPrimaryKey(ouId);
                        if (ou == null) {
                            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                        }
                    }
                }
                if (is == null) {
                    if (ou.getParentUnit() != null) {
                        OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                        if (ou1 != null) {
                            companyId = ou1.getParentUnit().getId();
                        }
                    }
                }
                if (!StringUtils.hasText(datainfo.getUpc())) {
                    throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
                }
                Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(datainfo.getUpc(), shop.getCustomer().getId(), shop.getId());
                if (sku == null) {
                    baseinfoManager.sendMsgToOmsCreateSku(datainfo.getUpc(), shop.getVmiCode());
                    log.info("SKU不存在！UPC：" + datainfo.getUpc());
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
                }
                InventoryStatus inventoryStatus = inventoryStatusDao.getByName(datainfo.getStatus(), companyId);
                if (inventoryStatus == null) {
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {datainfo.getUpc()});
                }
                InventoryCheckDifTotalLine icDifTotalLine = new InventoryCheckDifTotalLine();
                icDifTotalLine.setSku(sku);
                icDifTotalLine.setQuantity(Long.parseLong(datainfo.getQuantity().toString()));
                icDifTotalLine.setInventoryCheck(inventoryCheck);
                icDifTotalLine.setStatus(inventoryStatus);
                vmiinvCheckLineDao.save(icDifTotalLine);
                innerShopCode = shop.getCode();
            } catch (Exception e) {
                log.error("", e);
            }
        }
        if (StringUtils.hasText(innerShopCode) && ou != null) {
            // 设置组织
            inventoryCheck.setOu(wh.getOu());
            inventoryCheck.setShop(shop);
            inventoryCheckDao.flush();
            log.debug("===============InventoryCheck {} create success ================", new Object[] {inventoryCheck.getCode()});
        } else {
            log.debug("===============InventoryCheck {} create error ================", new Object[] {inventoryCheck.getCode()});
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return inventoryCheck;
    }

    /**
     * 根据增量出入库商品信息创建商品
     * 
     * @param upc
     * @param storeCode
     * @return
     */
    public void executionVmiAdjMovement(InventoryCheck ic) {
        if (ic == null) {
            log.error("IDS->WMS Error! Adjustment execution ,ic is null!");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } else if (ic != null && !InventoryCheckStatus.CREATED.equals(ic.getStatus())) {
            log.error("IDS->WMS Error! Adjustment execution ,Status is error!");
            throw new BusinessException();
        }
        List<InventoryCheckDifTotalLine> lineList = vmiinvCheckLineDao.findByInventoryCheck(ic.getId());
        WarehouseLocation location = locDao.findOneWarehouseLocationByOuid(ic.getOu().getId());
        InventoryStatus invStatus = null;
        for (InventoryCheckDifTotalLine line : lineList) {
            InventoryCheckDifferenceLine icdifference = new InventoryCheckDifferenceLine();
            icdifference.setSku(line.getSku());
            icdifference.setQuantity(line.getQuantity());
            icdifference.setInventoryCheck(ic);
            icdifference.setLocation(location);
            icdifference.setStatus(invStatus);
            if (ic != null && ic.getShop() != null && ic.getShop().getId() != null) {
                icdifference.setOwner(ic.getShop().getCode());
            }
            icDifferenceLineDao.save(icdifference);
        }
        ic.setStatus(InventoryCheckStatus.UNEXECUTE);
        inventoryCheckDao.save(ic);
        inventoryCheckDao.flush();
        // 执行
    }

    /**
     * 全量库存
     * 
     * @param datas
     */
    public InventoryCheck executeInventoryChecks(List<VmiInventorySnapshotDataCommand> datas) {
        OperationUnit ou = null;
        String innerShopCode = null;
        Warehouse wh = null;
        BiChannel shop = null;
        InventoryCheck inventoryCheck = new InventoryCheck();
        inventoryCheck.setCreateTime(new Date());
        inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
        inventoryCheck.setType(InventoryCheckType.VMI);
        inventoryCheck.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), inventoryCheck));
        inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
        inventoryCheck = inventoryCheckDao.save(inventoryCheck);
        StringBuilder contextRev = new StringBuilder();
        for (VmiInventorySnapshotDataCommand datainfo : datas) {
            try {
                if (StringUtils.hasText(datainfo.getStoreCode())) {
                    Long sidLong = Long.parseLong(datainfo.getStoreCode());
                    shop = shopDao.getByPrimaryKey(sidLong);
                } else {
                    throw new Exception("缺少店铺标识!---------storeCode");
                }
                if (shop == null) {
                    throw new Exception("没有找到店铺信息!storeCode:" + datainfo.getStoreCode());
                }
                wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
                if (wh == null) {
                    wh = warehouseDao.getBySource(datainfo.getSource(), datainfo.getSource());
                    if (wh == null) {
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    } else {
                        Long ouId = wh.getOu().getId();
                        ou = ouDao.getByPrimaryKey(ouId);
                        if (ou == null) {
                            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                        }
                    }
                }
                if (!StringUtils.hasText(datainfo.getUpc())) {
                    System.out.println(datainfo.getUpc());
                    throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
                }
                Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(datainfo.getUpc(), shop.getCustomer().getId(), shop.getId());
                if (sku == null) {
                    contextRev.append(datainfo.getUpc()).append("\r\n");
                    baseinfoManager.sendMsgToOmsCreateSku(datainfo.getUpc(), shop.getVmiCode());
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
                }
                InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(datainfo.getInventoryStatusId());
                if (inventoryStatus == null) {
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {datainfo.getUpc()});
                }
                InventoryCheckDifTotalLine icDifTotalLine = new InventoryCheckDifTotalLine();
                icDifTotalLine.setSku(sku);
                icDifTotalLine.setQuantity(datainfo.getOnhandQty());
                icDifTotalLine.setInventoryCheck(inventoryCheck);
                icDifTotalLine.setStatus(inventoryStatus);
                vmiinvCheckLineDao.save(icDifTotalLine);
                innerShopCode = shop.getCode();
                vmiInventorySnapshotDataDao.updateVmiInventorySnapshotStaCode(inventoryCheck.getCode(), datainfo.getId());
            } catch (Exception e) {
                vmiInventorySnapshotDataDao.updateVmiInventorySnapshotStatus(DefaultStatus.ERROR.getValue(), datainfo.getId());
                log.error("", e);
            }
        }

        writesku(contextRev.toString());
        if (StringUtils.hasText(innerShopCode) && ou != null) {
            // 设置组织
            inventoryCheck.setOu(wh.getOu());
            inventoryCheck.setShop(shop);
            inventoryCheckDao.flush();
            log.debug("===============InventoryCheck {} create success ================", new Object[] {inventoryCheck.getCode()});
        } else {
            log.debug("===============InventoryCheck {} create error ================", new Object[] {inventoryCheck.getCode()});
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return inventoryCheck;
    }

    public void writesku(String skucode) {
        File dir = new File("F:");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // if (null != contextRev) {
        // }

        String txtName = "follie_sku";
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // Date todaydate = new Date();
        // String dateString = sdf.format(todaydate);
        Random rnd = new Random();
        int num = 100 + rnd.nextInt(900);
        String fileName = "F:" + "//" + txtName + "_" + "IT_SKU" + num + ".txt";
        File file = new File(fileName);
        writeDataToFile(file, skucode.toString());

    }

    // 将数据写入文件
    public void writeDataToFile(File file, String data) {
        FileWriter fw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(data);
        } catch (IOException e1) {
            if (log.isErrorEnabled()) {
                log.error("writeDataToFile IOException", e1);
            }
        } finally {
            try {
                if (fw != null) fw.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    /**
     * 根据入库商品信息创建商品
     * 
     * @param upc
     * @param storeCode
     * @return
     */
    public void executionVmiAdjustment(InventoryCheck ic) {
        if (ic == null) {
            log.error("BOC->WMS Error! Adjustment execution ,ic is null!");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } else if (ic != null && !InventoryCheckStatus.CREATED.equals(ic.getStatus())) {
            log.error("BOC->WMS Error! Adjustment execution ,Status is error!");
            throw new BusinessException();
        }
        List<InventoryCheckDifTotalLine> lineList = vmiinvCheckLineDao.findByInventoryCheck(ic.getId());
        WarehouseLocation location = locDao.findByLocationCode("FA-1-1-1", ic.getOu().getId());
        if (location == null) {
            log.error("BOC->WMS Error! Adjustment execution ,WarehouseLocation is null!");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        for (InventoryCheckDifTotalLine line : lineList) {
            InventoryCheckDifferenceLine icdifference = new InventoryCheckDifferenceLine();
            icdifference.setSku(line.getSku());
            icdifference.setQuantity(line.getQuantity());
            icdifference.setInventoryCheck(ic);
            icdifference.setLocation(location);
            icdifference.setStatus(line.getStatus());
            if (ic != null && ic.getShop() != null && ic.getShop().getId() != null) {
                icdifference.setOwner(ic.getShop().getCode());
            }
            icDifferenceLineDao.save(icdifference);
        }
        ic.setStatus(InventoryCheckStatus.UNEXECUTE);
        inventoryCheckDao.save(ic);
        inventoryCheckDao.flush();
        // 执行
    }

    /**
     * 换行符/英文逗号/tab符替换为空格/单引号/双引号/左右尖括弧
     * 
     * @param sourceStr
     * @return
     */
    public static String replaceSpecialCharacter(String sourceStr) {
        if (sourceStr == null || sourceStr.equals("")) {
            return sourceStr;
        } else {
            sourceStr = sourceStr.replaceAll("\n", " ");
            sourceStr = sourceStr.replaceAll("\r", " ");
            sourceStr = sourceStr.replaceAll(",", " ");
            sourceStr = sourceStr.replaceAll("  ", " ");
            sourceStr = sourceStr.replaceAll("'", " ");
            sourceStr = sourceStr.replaceAll("\"", " ");
            sourceStr = sourceStr.replaceAll("<", " ");
            sourceStr = sourceStr.replaceAll(">", " ");
            return sourceStr;
        }

    }

    @Override
    public void bocExecuteCreateCancelOrder(String source) {
        List<MsgOutboundOrderCancel> orderlist = msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(source);

        OrderCancels orderCancels = new OrderCancels();
        List<OrderCancel> orderCancelList = new ArrayList<OrderCancel>();
        if (orderlist.size() > 0) {
            log.debug("=========CREATE SKU STAR===========" + orderlist.size());
            for (MsgOutboundOrderCancel cancel : orderlist) {
                OrderCancel orderCancel = new OrderCancel();
                orderCancel.setOrderCode(cancel.getStaCode());
                orderCancelList.add(orderCancel);
                msgOutboundOrderCancelDao.updateMsgOrderCancelById(cancel.getId(), MsgOutboundOrderCancelStatus.SENT.getValue());
            }
            orderCancels.setOrderCancels(orderCancelList);
        }

        if (orderlist.size() == 0) {
            return;
        }

        orderCancels.setSource(source);
        String msg = JSONUtil.beanToJson(orderCancels);

        ConnectorMessage connectorMessage = new ConnectorMessage();

        connectorMessage.setPlatformCode(ConstantsBase.BH_CONNECTOR_PLATFORM_CODE_BASE);
        connectorMessage.setInterfaceCode(ConstantsBase.BH_CONNECTOR_INTERFACE_CODE_BASE_ORDER_CANCEL);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));

        msg = JSONUtil.beanToJson(connectorMessage);
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "oms2bh_base_ordercancel_production", msg);

    }

    @Override
    public void updateBocPrice() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date today = calendar.getTime();
        String starDate = FormatUtil.formatDate(today, "yyyy-MM-dd");
        List<PriceData> priceDataList = priceDataDao.findPriceDatabystartDate(starDate, new BeanPropertyRowMapper<PriceData>(PriceData.class));
        for (PriceData priceData : priceDataList) {
            priceDataDao.updateStatePriceDatabyId(DefaultStatus.FINISHED.getValue(), priceData.getId());
        }

    }

    public void receiveOrderCancelConfirmation(String message) {
        log.info("=========OrderCancelConfirmation START===========");
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            OrderCancelConfirmations orderCancelConfirmations = (OrderCancelConfirmations) JSONUtil.jsonToBean(messageContent, OrderCancelConfirmations.class);
            List<OrderCancelConfirmation> infoList = orderCancelConfirmations.getOrderCancelConfirmation();
            for (OrderCancelConfirmation confirmation : infoList) {
                MsgOutboundOrderCancel msgOutboundOrderCancel = msgOutboundOrderCancelDao.getByStaCode(confirmation.getOrderCode());
                if (msgOutboundOrderCancel != null) {
                    msgOutboundOrderCancel.setIsCanceled(Integer.parseInt(confirmation.getStatus()) == 0 ? false : true);
                    msgOutboundOrderCancel.setMsg(confirmation.getRemark());
                    msgOutboundOrderCancelDao.save(msgOutboundOrderCancel);
                } else {
                    msgOutboundOrderCancelDao.updateOuOrderStatusById(confirmation.getOrderCode(), MsgOutboundOrderCancelStatus.INEXECUTION.getValue());
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
