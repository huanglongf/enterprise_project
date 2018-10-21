package com.jumbo.wms.manager.vmi.guess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.guess.GuessEcomAdjDataDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutAdditionalLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhGuessInventoryDao;
import com.jumbo.dao.warehouse.WhGuessInventoryRetailDao;
import com.jumbo.dao.warehouse.WhUaInventoryLogDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.util.mq.MqMsgUtil;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSalesModel;
import com.jumbo.wms.model.baseinfo.SkuSpType;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.WhGuessInventoryRetail;
import com.jumbo.wms.model.vmi.guess.GuessEcomAdjData;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutAdditionalLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.WhGuessInventory;
import com.jumbo.wms.model.vmi.warehouse.WhUaInventoryLog;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

// import cn.baozun.bh.connector.base.model.InventorySnapshots.InventorySnapshot;
import cn.baozun.bh.connector.guess.ConstantsGuess;
import cn.baozun.bh.connector.guess.model.DeliveryConfirmation;
import cn.baozun.bh.connector.guess.model.EcomAdj;
import cn.baozun.bh.connector.guess.model.InventorySnapshot;
import cn.baozun.bh.connector.guess.model.OrderRequests;
import cn.baozun.bh.connector.guess.model.OrderRequests.OrderRequest;
import cn.baozun.bh.connector.guess.model.ReturnReceivings;
import cn.baozun.bh.connector.guess.model.ReturnReceivings.ReturnReceiving;
import cn.baozun.bh.connector.guess.model.ReturnRequests;
import cn.baozun.bh.connector.guess.model.ReturnRequests.ReturnRequest;
import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
@Service("guessManager")
public class GuessManagerImpl extends BaseManagerImpl implements GuessManager {

    /**
     * 
     */
    private static final long serialVersionUID = 2841642286110506383L;

    @Autowired
    private MsgRaCancelDao msgRaCancelDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CustomerDao customerdao;

    @Autowired
    private MsgRtnOutAdditionalLineDao msgRtnOutAdditionalLineDao;

    /**
     * MQ消息模板
     */
    private JmsTemplate bhMqJmsTemplate;

    @Autowired
    private StaLineDao staLineDao;


    @Autowired
    private BaseinfoManager baseinfoManager;

    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;

    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private SequenceManager sequenceManager;

    @Autowired
    private MsgOutboundOrderLineDao lineDao;

    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;

    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;

    @Autowired
    private MsgInboundOrderLineDao msgILineDao;

    @Autowired
    private SkuDao skuDao;

    @Resource
    private ApplicationContext applicationContext;

    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;

    @Autowired
    private WarehouseDao warehouseDao;


    @Autowired
    private OperationUnitDao ouDao;


    @Autowired
    private InventoryCheckDifTotalLineDao icLineDao;


    @Autowired
    private InventoryStatusDao inventoryStatusDao;


    @Autowired
    private MsgRtnInboundOrderDao rtnOrderDao;

    @Autowired
    private MsgRtnInboundOrderLineDao rtnOrderLineDao;

    @Autowired
    private GuessEcomAdjDataDao guessEcomAdjDataDao;


    @Autowired
    private InventoryCheckDao invDao;

    @Autowired
    private BiChannelDao shopDao;


    @Autowired
    private WareHouseManager whManager;

    @Autowired
    private WhGuessInventoryDao whGuessInventoryDao;

    @Autowired
    private WhGuessInventoryRetailDao guessInventoryRetailDao;

    @Autowired
    private WhUaInventoryLogDao whUaInventoryLogDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    IdsManager idsManager;
    @Autowired
    WareHouseManager wareHouseManager;

    @PostConstruct
    private void init() {
        try {
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named bhMqJmsTemplate");
        }
    }

    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }



    /**
     * 发送出库订单
     */
    public void sendSalesOrderToHub(String whSource) {
        List<OrderRequest> orderList = new ArrayList<OrderRequest>();
        OrderRequests requests = new OrderRequests();

        Integer pNum = null;
        try {
            pNum = Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(Constants.GUESS_OUTBOUNT_NUM, Constants.GUESS_OUTBOUNT_NUM, new SingleColumnRowMapper<String>(String.class)));
        } catch (Exception e) {
            pNum = 600;
        }
        List<MsgOutboundOrder> list = msgOutboundOrderDao.findSOOutboundOrder(whSource, true, null, DefaultStatus.CREATED.getValue(), pNum, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));

        for (MsgOutboundOrder order : list) {

            StockTransApplication sta = staDao.findStaByCode(order.getStaCode());
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            if (sd == null) {
                sd = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            }
            if (sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {

                String transDate = FormatUtil.formatDate(order.getCreateTime(), "yyyyMMdd");
                String address = StringUtil.getRealAddress(order.getAddress(), order.getProvince(), order.getCity(), order.getDistrict(), false);
                String newAddress = address;
                if (sd.getTransCityCode() != null && !StringUtil.isEmpty(sd.getTransCityCode())) {
                    newAddress = address + "@@" + sd.getTransCityCode();
                }
                StringBuilder notes = new StringBuilder();
                notes.append(order.getUserId() == null ? "Exchange" : order.getUserId()).append("|");
                notes.append(order.getUserId() == null ? "Exchange" : order.getUserId()).append("|");
                notes.append(order.getReceiver() == null ? "" : order.getReceiver()).append("|");
                notes.append(order.getProvince() == null ? "" : order.getProvince()).append("|");
                notes.append(order.getCity() == null ? "" : order.getCity()).append("|");
                notes.append(order.getDistrict() == null ? "" : order.getDistrict()).append("|");
                if (Transportator.KERRY_A.equals(order.getLpCode())) {
                    if (newAddress.length() > 50) {
                        notes.append(address).append("|");
                    } else {
                        notes.append(newAddress).append("|");
                    }
                } else {
                    notes.append(address).append("|");
                }
                notes.append(order.getTransNo() == null ? "" : order.getTransNo()).append("|");
                notes.append(order.getMobile() == null ? (order.getTelePhone() == null ? "" : order.getTelePhone()) : order.getMobile());


                List<MsgOutboundOrderLine> lists = lineDao.findeMsgOutLintBymsgOrderId2(order.getId());
                ChooseOption co = chooseOptionDao.findByCategoryCodeAndValue("deliveryinfo", order.getLpCode());
                ChooseOption coShop = chooseOptionDao.findByCategoryCodeAndValue("guessShop", order.getShopId() + "");
                ChooseOption tco = chooseOptionDao.findByCategoryCodeAndValue("guess", "guess");
                for (MsgOutboundOrderLine msgLine : lists) {

                    OrderRequest orderRequest = new OrderRequest();
                    orderRequest.setType("O");

                    // orderRequest.setStoreCodeId(order.getShopId() == 2862 ? "170" : "202");
                    orderRequest.setStoreCodeId(coShop.getOptionKey());
                    orderRequest.setBaozunOrder(order.getStaCode());
                    if (sta != null) {
                        orderRequest.setTmallOrder(sta.getSlipCode1());
                    }
                    if (order.getSourceWh() != null && order.getSourceWh().equals("guess")) {
                        orderRequest.setBinCode("BZMAIN");
                    } else if (order.getSourceWh() != null && order.getSourceWh().equals("guess001")) {
                        orderRequest.setBinCode("MAIN");
                    }

                    orderRequest.setShortSku(msgLine.getSku().getExtensionCode2());
                    // orderRequest.setShortSku(msgLine.getSku().getBarCode());
                    orderRequest.setQty(msgLine.getQty().toString());
                    orderRequest.setNote(notes.toString());
                    orderRequest.setCarrier(co == null ? "0" : co.getOptionKey());
                    orderRequest.setTransDate(transDate.substring(2, transDate.length()));
                    // orderRequest.setTrackingNumber(order.getTransNo());
                    if (null != tco && "1".equals(tco.getOptionKey())) {
                        orderRequest.setTrackingNumber("");
                    } else {
                        orderRequest.setTrackingNumber(order.getTransNo());
                    }

                    orderRequest.setSellingPrice(String.valueOf(msgLine.getUnitPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
                    orderList.add(orderRequest);
                }
                msgOutboundOrderDao.updateMsgoutboundOrder(order.getId(), DefaultStatus.FINISHED.getValue());
            }
        }

        if (list.size() == 0 || orderList.size() == 0) {
            return;
        }

        requests.setOrderRequests(orderList);
        String msg = JSONUtil.beanToJson(requests);


        ConnectorMessage connectorMessage = new ConnectorMessage();

        connectorMessage.setPlatformCode(ConstantsGuess.BH_CONNECTOR_PLATFORM_CODE_GUESS);
        connectorMessage.setInterfaceCode(ConstantsGuess.BH_CONNECTOR_INTERFACE_CODE_GUESS_ORDER);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));


        msg = JSONUtil.beanToJson(connectorMessage);
        log.info("oms2bh_guess_order_1");
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "oms2bh_guess_order", msg);
        log.info("oms2bh_guess_order_2");

    }


    /**
     * 发送退货单到hub
     */
    public void customerReturnRequestToHub(String whSource) {
        ReturnRequests guessRequests = new ReturnRequests();
        List<ReturnRequest> orders = new ArrayList<ReturnRequest>();
        List<MsgInboundOrder> inboundList = msgInboundOrderDao.findMsgReturnInboundByStatus(Constants.VIM_WH_SOURCE_GUESS, null, StockTransApplicationType.INBOUND_RETURN_REQUEST);
        ChooseOption co = new ChooseOption();
        for (MsgInboundOrder msgInboundOrder : inboundList) {
            StockTransApplication sta = staDao.findStaByCode(msgInboundOrder.getStaCode());
            StaDeliveryInfo staDeliveryInfo = null;
            if (sta != null) {
                staDeliveryInfo = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            }

            ReturnRequest returnRequest = new ReturnRequest();
            returnRequest.setTranType("PO");
            returnRequest.setRecordType("H");
            returnRequest.setDoctype("TR");
            returnRequest.setDocument("R" + msgInboundOrder.getRefSlipCode().replace("AS", ""));
            returnRequest.setShipToWareHouse("SHE");
            returnRequest.setShipFromStore("000170");

            returnRequest.setVendorName(sta.getSlipCode1());
            returnRequest.setAddress1(staDeliveryInfo.getTelephone());
            returnRequest.setAddress2(staDeliveryInfo.getMobile());
            returnRequest.setVendorCity("");
            returnRequest.setVendorState(msgInboundOrder.getRefSlipCode());
            returnRequest.setVendorZip("");
            returnRequest.setShipDate("00000000");
            returnRequest.setSchedRecvDate("00000000");
            returnRequest.setUpc("0000000000000");
            returnRequest.setStyle("");
            returnRequest.setColorShort("");
            returnRequest.setSizeDescription("");
            String qtyStr = "000000000";
            Long staTotal = staLineDao.findTotalQtyByStaId(sta.getId(), new SingleColumnRowMapper<Long>(Long.class));
            String staLong = staTotal.toString();
            String value = qtyStr.substring(staLong.length(), qtyStr.length());

            returnRequest.setQuantity(value + staLong);
            returnRequest.setCartonAsn(msgInboundOrder.getStaCode());
            returnRequest.setSaleDate("00000000");
            // returnRequest.setTransferNumber("00000000");
            returnRequest.setTransferNumber(msgInboundOrder.getRefSlipCode().substring(3, msgInboundOrder.getRefSlipCode().length()));
            returnRequest.setFunction("1");
            orders.add(returnRequest);
            List<MsgInboundOrderLine> msgLineList = msgILineDao.fomdMsgInboundOrderLineByOId(msgInboundOrder.getId());

            for (MsgInboundOrderLine inLinecomd : msgLineList) {
                ReturnRequest returnLine = new ReturnRequest();
                returnLine.setTranType("PO");
                returnLine.setRecordType("D");
                returnLine.setDoctype("TR");
                returnLine.setDocument("R" + msgInboundOrder.getRefSlipCode().replace("AS", ""));
                returnLine.setShipToWareHouse("SHE");
                returnLine.setShipFromStore("000170");
                returnLine.setVendorName("");
                returnLine.setAddress1("");
                returnLine.setAddress2("");
                returnLine.setVendorCity("");
                returnLine.setVendorState(msgInboundOrder.getRefSlipCode());
                returnLine.setVendorZip("");
                returnLine.setShipDate("00000000");
                returnLine.setSchedRecvDate("00000000");


                Sku sku = skuDao.getByPrimaryKey(inLinecomd.getSku().getId());
                co = chooseOptionDao.findByCategoryCodeAndValue("guess", "guess");
                if (null != co && "1".equals(co.getOptionKey())) {
                    returnLine.setUpc(sku.getExtensionCode2());
                } else {
                    returnLine.setUpc(sku.getBarCode());
                }
                returnLine.setStyle(sku.getSupplierCode());
                returnLine.setColorShort(sku.getColor());
                returnLine.setSizeDescription(sku.getSkuSize());
                returnLine.setQuantity(inLinecomd.getQty().toString());
                returnLine.setCartonAsn("00000000000000000000");
                returnLine.setSaleDate("00000000");
                returnLine.setTransferNumber(msgInboundOrder.getRefSlipCode().substring(3, msgInboundOrder.getRefSlipCode().length()));
                returnLine.setFunction("1");
                orders.add(returnLine);
            }
            msgInboundOrderDao.updateInboundOrderByStaCode(DefaultStatus.FINISHED.getValue(), sta.getCode());
        }


        if (inboundList.size() == 0) {
            return;
        }
        guessRequests.setReturnRequests(orders);

        String msg = JSONUtil.beanToJson(guessRequests);


        ConnectorMessage connectorMessage = new ConnectorMessage();

        connectorMessage.setPlatformCode(ConstantsGuess.BH_CONNECTOR_PLATFORM_CODE_GUESS);
        connectorMessage.setInterfaceCode(ConstantsGuess.BH_CONNECTOR_INTERFACE_CODE_GUESS_RETURN_REQUEST);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));


        msg = JSONUtil.beanToJson(connectorMessage);
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "oms2bh_guess_return_request", msg);
    }

    /**
     * 发送退货取消单到hub
     */
    public void customerReturnCancelRequestToHub() {
        ReturnRequests guessRequests = new ReturnRequests();
        List<ReturnRequest> orders = new ArrayList<ReturnRequest>();
        List<MsgRaCancel> msgRaList = msgRaCancelDao.findNewMsgBySource(Constants.VIM_WH_SOURCE_GUESS, new BeanPropertyRowMapperExt<MsgRaCancel>(MsgRaCancel.class));

        for (MsgRaCancel msgRaCancel : msgRaList) {

            MsgInboundOrder msgInboundOrder = msgInboundOrderDao.findByStaCode(msgRaCancel.getStaCode());
            if (msgInboundOrder != null) {
                if (!msgInboundOrder.getStatus().equals(DefaultStatus.FINISHED)) {
                    return;
                }
            }

            StockTransApplication sta = staDao.findStaByCode(msgRaCancel.getStaCode());
            StaDeliveryInfo staDeliveryInfo = null;
            if (sta != null) {
                staDeliveryInfo = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            }

            ReturnRequest returnRequest = new ReturnRequest();
            returnRequest.setTranType("PO");
            returnRequest.setRecordType("H");
            returnRequest.setDoctype("TR");
            returnRequest.setDocument("R" + msgRaCancel.getSlipCode().replace("AS", ""));
            returnRequest.setShipToWareHouse("SHE");
            returnRequest.setShipFromStore("000" + (msgInboundOrder.getShopId() == 2862 ? "170" : "202"));

            returnRequest.setVendorName(sta.getSlipCode1());
            returnRequest.setAddress1(staDeliveryInfo.getTelephone());
            returnRequest.setAddress2(staDeliveryInfo.getMobile());
            returnRequest.setVendorCity("");
            returnRequest.setVendorState(msgRaCancel.getSlipCode());
            returnRequest.setVendorZip("");
            returnRequest.setShipDate("00000000");
            returnRequest.setSchedRecvDate("00000000");
            returnRequest.setUpc("0000000000000");
            returnRequest.setStyle("");
            returnRequest.setColorShort("");
            returnRequest.setSizeDescription("");
            String qtyStr = "000000000";
            Long staTotal = staLineDao.findTotalQtyByStaId(sta.getId(), new SingleColumnRowMapper<Long>(Long.class));
            String staLong = staTotal.toString();
            String value = qtyStr.substring(staLong.length(), qtyStr.length());

            returnRequest.setQuantity(value + staLong);
            returnRequest.setCartonAsn(msgRaCancel.getStaCode());
            returnRequest.setSaleDate("00000000");
            // returnRequest.setTransferNumber("00000000");
            returnRequest.setTransferNumber(msgRaCancel.getSlipCode().substring(3, msgRaCancel.getSlipCode().length()));
            returnRequest.setFunction("5");
            orders.add(returnRequest);
            List<MsgInboundOrderLine> msgLineList = msgILineDao.fomdMsgInboundOrderLineByOId(msgInboundOrder.getId());

            for (MsgInboundOrderLine inLinecomd : msgLineList) {
                ReturnRequest returnLine = new ReturnRequest();
                returnLine.setTranType("PO");
                returnLine.setRecordType("D");
                returnLine.setDoctype("TR");
                returnLine.setDocument("R" + msgRaCancel.getSlipCode().replace("AS", ""));
                returnLine.setShipToWareHouse("SHE");
                returnLine.setShipFromStore("000" + (msgInboundOrder.getShopId() == 2862 ? "170" : "202"));
                returnLine.setVendorName("");
                returnLine.setAddress1("");
                returnLine.setAddress2("");
                returnLine.setVendorCity("");
                returnLine.setVendorState(msgRaCancel.getSlipCode());
                returnLine.setVendorZip("");
                returnLine.setShipDate("00000000");
                returnLine.setSchedRecvDate("00000000");


                Sku sku = skuDao.getByPrimaryKey(inLinecomd.getSku().getId());
                returnLine.setUpc(sku.getBarCode());
                returnLine.setStyle(sku.getSupplierCode());
                returnLine.setColorShort(sku.getColor());
                returnLine.setSizeDescription(sku.getSkuSize());
                returnLine.setQuantity(inLinecomd.getQty().toString());
                returnLine.setCartonAsn("00000000000000000000");
                returnLine.setSaleDate("00000000");
                returnLine.setTransferNumber(msgRaCancel.getSlipCode().substring(3, msgRaCancel.getSlipCode().length()));
                returnLine.setFunction("5");
                orders.add(returnLine);
            }
            msgRaCancelDao.updateStatusById(msgRaCancel.getId(), DefaultStatus.FINISHED.getValue());
        }


        if (msgRaList.size() == 0) {
            return;
        }
        guessRequests.setReturnRequests(orders);

        String msg = JSONUtil.beanToJson(guessRequests);


        ConnectorMessage connectorMessage = new ConnectorMessage();

        connectorMessage.setPlatformCode(ConstantsGuess.BH_CONNECTOR_PLATFORM_CODE_GUESS);
        connectorMessage.setInterfaceCode(ConstantsGuess.BH_CONNECTOR_INTERFACE_CODE_GUESS_RETURN_REQUEST);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));


        msg = JSONUtil.beanToJson(connectorMessage);
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "oms2bh_guess_return_request", msg);
    }

    /**
     * DeliveryConfirmation
     * 
     * @param message
     */
    public void customerDeliveryConfirmation(String message) {
        log.info("=========GUESS CUSTOMER DELIVERY CONFIRMATION START===========");
        DateFormat df = new SimpleDateFormat("yyMMdd");
        boolean isNeedStuff = false;
        try {

            List<?> obj = JSONUtil.jsonToList(message);
            if (obj == null || obj.size() == 0) {
                log.info("**CUSTOMER DELIVERY CONFIRMATION is null ");
                return;
            }

            for (int i = 0; i < obj.size(); i++) {
                DeliveryConfirmation deliveryinfo = JSONUtil.jsonToBean(JSONUtil.beanToJson(obj.get(i)), DeliveryConfirmation.class);
                MsgRtnOutbound rtnOutbound = new MsgRtnOutbound();
                rtnOutbound.setStaCode(deliveryinfo.getBaozunOrder());
                rtnOutbound.setTid(deliveryinfo.getTmallOrder());
                rtnOutbound.setSource(Constants.VIM_WH_SOURCE_GUESS);
                rtnOutbound.setSourceWh(Constants.VIM_WH_SOURCE_GUESS);
                String lpCode = chooseOptionDao.findAllOptionListByOptionKey(deliveryinfo.getCarrier(), "deliveryinfo", new SingleColumnRowMapper<String>(String.class));
                rtnOutbound.setLpCode(lpCode == null ? "SF" : lpCode);
                rtnOutbound.setOutboundTime(df.parse(deliveryinfo.getTransDate()));
                rtnOutbound.setCreateTime(new Date());
                rtnOutbound.setStatus(DefaultStatus.CREATED);

                String tracking = deliveryinfo.getTracking().trim();
                if (StringUtils.hasText(tracking)) {

                    if (tracking.indexOf("-") != -1) {
                        isNeedStuff = true;
                        String array[] = tracking.split("-");

                        rtnOutbound.setTrackingNo(array[0]);
                        // 包裹总重量
                        BigDecimal weigth = new BigDecimal(array[1]);
                        rtnOutbound.setWeight(weigth);

                    } else {
                        rtnOutbound.setTrackingNo(deliveryinfo.getTracking());
                    }

                    msgRtnOutboundDao.save(rtnOutbound);
                    msgRtnOutboundDao.flush();


                    if (isNeedStuff) {

                        String array[] = tracking.split("-");

                        // 商品长度（mm）
                        BigDecimal length = new BigDecimal(array[2]);
                        // 商品宽度（mm）
                        BigDecimal width = new BigDecimal(array[3]);

                        // 商品高度（mm）
                        BigDecimal height = new BigDecimal(array[4]);

                        Sku sku = skuDao.findGuessMaterial("GUESSHC", length.multiply(new BigDecimal(10)), width.multiply(new BigDecimal(10)), height.multiply(new BigDecimal(10)));
                        if (sku == null) {
                            Sku cretaSku = new Sku();

                            cretaSku.setBarCode("GUESSHC-" + length + "-" + width + "-" + height);
                            cretaSku.setCode("GUESSHC-" + length + "-" + width + "-" + height);
                            cretaSku.setExtensionCode1("GUESSHC-" + length + "-" + width + "-" + height);
                            cretaSku.setJmCode("GUESSHC" + length + width + height);
                            cretaSku.setCustomerSkuCode("GUESSHC" + length + width + height);
                            cretaSku.setName("GUESS 耗材商品");
                            cretaSku.setSupplierCode("GUESSHC");
                            Brand brand = brandDao.getByPrimaryKey(2247L);
                            cretaSku.setBrand(brand);
                            Customer customer = customerdao.getByPrimaryKey(2L);
                            cretaSku.setCustomer(customer);
                            cretaSku.setWidth(width.multiply(new BigDecimal(10)));
                            cretaSku.setHeight(height.multiply(new BigDecimal(10)));
                            cretaSku.setLength(length.multiply(new BigDecimal(10)));
                            cretaSku.setIsSnSku(false);
                            cretaSku.setSalesMode(SkuSalesModel.CONSIGNMENT);
                            cretaSku.setSpType(SkuSpType.CONSUMPTIVE_MATERIAL);
                            cretaSku.setIsGift(false);
                            cretaSku.setLastModifyTime(new Date());
                            skuDao.save(cretaSku);
                            skuDao.flush();

                            MsgRtnOutAdditionalLine line = new MsgRtnOutAdditionalLine();
                            line.setBarCode(cretaSku.getBarCode());
                            line.setMsgRtnOutbound(rtnOutbound);
                            line.setQty(1L);
                            line.setSkuCode(cretaSku.getCode());
                            msgRtnOutAdditionalLineDao.save(line);
                        } else {
                            MsgRtnOutAdditionalLine line = new MsgRtnOutAdditionalLine();
                            line.setBarCode(sku.getBarCode());
                            line.setMsgRtnOutbound(rtnOutbound);
                            line.setQty(1L);
                            line.setSkuCode(sku.getCode());
                            msgRtnOutAdditionalLineDao.save(line);
                        }
                    }

                }
            }
            log.info("=========GUESS CUSTOMER DELIVERY CONFIRMATION END===== ======");

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("guess customerDeliveryConfirmation Exception:" + message, e);
            }
        }
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
                log.error("guess writeDataToFile Exception:", e1);
            }
        } finally {
            try {
                if (fw != null) fw.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    @Override
    public void customerReturnReceiving(String message) {

        log.info("=========GUESS CUSTOMERRETURNRECEIVING START===========");
        InventoryStatus isForSaleFlase = null;
        InventoryStatus isForSaleTrue = null;
        InventoryStatus is = null;
        OperationUnit ou = null;
        Warehouse wh = null;

        if (wh == null) {
            wh = warehouseDao.getBySource(Constants.VIM_WH_SOURCE_GUESS, Constants.VIM_WH_SOURCE_GUESS);
            if (wh == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            } else {
                Long ouId = wh.getOu().getId();
                ou = ouDao.getByPrimaryKey(ouId);
                if (ou == null) {
                    log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
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
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);

            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {

                messageContent = ZipUtil.decompress(messageContent);

            }

            ReturnReceivings objv = JSONUtil.jsonToBean(message, ReturnReceivings.class);
            List<ReturnReceiving> returnReceivinginfo = objv.getReturnReceivings();
            for (ReturnReceiving returnReceiving : returnReceivinginfo) {

                MsgRtnInboundOrder msgrtnInbound = new MsgRtnInboundOrder();
                if (StringUtils.hasText(returnReceiving.getShipment())) {
                    msgrtnInbound.setStaCode(returnReceiving.getShipment().substring(0, 13));
                } else {
                    msgrtnInbound.setStaCode("");
                }
                msgrtnInbound.setSource(Constants.VIM_WH_SOURCE_GUESS);
                msgrtnInbound.setSourceWh(Constants.VIM_WH_SOURCE_GUESS);
                msgrtnInbound.setInboundTime(new Date());
                msgrtnInbound.setCreateTime(new Date());
                msgrtnInbound.setStatus(DefaultStatus.CREATED);

                msgrtnInbound = rtnOrderDao.save(msgrtnInbound);

                MsgRtnInboundOrderLine rtnline = new MsgRtnInboundOrderLine();
                // Sku sku = skuDao.getByBarcode1(returnReceiving.getUpcBarcode().trim());
                Sku sku = skuDao.getByExtCode2AndCustomerId(returnReceiving.getUpcBarcode().trim(), wh.getCustomer().getId());
                if (sku != null) {
                    rtnline.setSkuCode(sku.getCode());
                    rtnline.setBarcode(sku.getBarCode());
                    rtnline.setSkuId(sku.getId());
                }

                if (StringUtils.hasText(returnReceiving.getQtyExpected())) {
                    String qty = returnReceiving.getQtyExpected().substring(10, 11).trim();
                    rtnline.setQty(Long.valueOf(qty));
                } else {
                    rtnline.setQty(0l);
                }
                if (StringUtils.hasText(returnReceiving.getQualityCode())) {
                    if (("1").equals(returnReceiving.getQualityCode())) {
                        rtnline.setInvStatus(isForSaleTrue);
                    } else if (("2").equals(returnReceiving.getQualityCode())) {
                        rtnline.setInvStatus(isForSaleFlase);
                    }
                }
                rtnline.setMsgRtnInOrder(msgrtnInbound);
                rtnOrderLineDao.save(rtnline);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("=========GUESS CUSTOMERRETURNRECEIVING END===========");
    }

    @Override
    public void GuessEcomAdjData(String message) {
        log.info("=========GUESS EcomAdjData START===========");
        try {

            List<?> obj = JSONUtil.jsonToList(message);
            if (obj == null || obj.size() == 0) {
                log.info("**EcomAdj is null ");
                return;
            }

            for (int i = 0; i < obj.size(); i++) {
                EcomAdj ecomAdj = JSONUtil.jsonToBean(JSONUtil.beanToJson(obj.get(i)), EcomAdj.class);
                GuessEcomAdjData data = new GuessEcomAdjData();
                data.setAdjCode(ecomAdj.getAdjCode());
                data.setReasonCode(ecomAdj.getReasonCode());
                data.setBinCode(ecomAdj.getBinCode());
                data.setStatus(DefaultStatus.CREATED);
                data.setShortSku(ecomAdj.getShortSku());
                double qty = Double.parseDouble(ecomAdj.getQty());
                data.setQty(Math.round(qty));
                data.setCreateDate(new Date());
                guessEcomAdjDataDao.save(data);
            }
            log.info("=========GUESS ECOMADJ END===== ======");

        } catch (Exception e) {
            log.error("", e);
        }

    }


    public InventoryCheck vmiInvAdjustByWh(GuessEcomAdjData guessEcomAdjData) {
        boolean isCreateSuccess = true;
        InventoryCheck ic = null;
        if (guessEcomAdjData != null) {
            log.debug("=============== INVENTORYCHECK {} start success ================", new Object[] {Constants.VIM_WH_SOURCE_GUESS});

            OperationUnit ou = null;
            BiChannel shop = null;
            Warehouse wh = null;

            InventoryStatus isForSaleTrue = null;
            InventoryStatus isForSaleFlase = null;

            if (guessEcomAdjData != null) {
                ic = new InventoryCheck();
                try {
                    ic.setCreateTime(new Date());
                    ic.setStatus(InventoryCheckStatus.CREATED);
                    ic.setSlipCode(guessEcomAdjData.getAdjCode());
                    ic.setType(InventoryCheckType.VMI);
                    ic.setInvType(1);

                    ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));

                    invDao.save(ic);

                    if (shop == null) {

                        shop = shopDao.getByvmiWHSource(Constants.VIM_WH_SOURCE_GUESS);
                    }
                    if (shop == null) {
                        log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                    if (wh == null) {
                        wh = whManager.getWareHouseByVmiSource(Constants.VIM_WH_SOURCE_GUESS);
                    }
                    if (wh == null) {
                        log.debug("=========WAREHOUSE UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                    Long ouId = wh.getOu().getId();
                    if (ouId == null) {
                        log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                    // OperationUnit ouShop = ouDao.getByPrimaryKey(shop.getOu().getId());
                    // if (ouShop == null) {
                    // log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[]
                    // {Constants.VIM_WH_SOURCE_GUESS});
                    // throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    // }
                    ou = ouDao.getByPrimaryKey(ouId);
                    if (ou == null) {
                        log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }

                    if (isForSaleFlase == null) {
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


                    List<GuessEcomAdjData> guessEcomAdjList = guessEcomAdjDataDao.findGuessEcomAdjDataDataListByCode(guessEcomAdjData.getAdjCode(), new BeanPropertyRowMapperExt<GuessEcomAdjData>(GuessEcomAdjData.class));
                    boolean isExist = false;
                    for (GuessEcomAdjData guessEcomAdj : guessEcomAdjList) {

                        Sku sku = whManager.getByExtCode2AndCustomerAndShopId(guessEcomAdj.getShortSku().trim(), shop.getCustomer().getId(), shop.getId());
                        if (sku == null) {
                            // 如果sku在系统中没有，就生成，生成失败返回false
                            baseinfoManager.sendMsgToOmsCreateSku(guessEcomAdj.getShortSku(), shop.getVmiCode());
                            isExist = true;
                            continue;
                        }
                        InventoryCheckDifTotalLine line = new InventoryCheckDifTotalLine();
                        line.setInventoryCheck(ic);
                        line.setSku(sku);
                        line.setQuantity(new Long(guessEcomAdj.getQty()));
                        if ("BZMAIN".equals(guessEcomAdj.getBinCode())) {
                            line.setStatus(isForSaleTrue);
                        } else if ("DEFECT".equals(guessEcomAdj.getBinCode())) {
                            line.setStatus(isForSaleFlase);
                        }
                        icLineDao.save(line);
                        ic.setShop(shop);
                        ic.setOu(ou);
                        invDao.save(ic);
                    }
                    if (isExist) {
                        throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
                    }
                } catch (BusinessException e) {
                    log.warn("vmiInvAdjustByWh , error : {}", e.getErrorCode());
                    isCreateSuccess = false;
                    guessEcomAdjDataDao.updateGuessEcomAdjDataByCode("", DefaultStatus.ERROR.getValue(), guessEcomAdjData.getAdjCode());
                } catch (Exception e) {
                    log.error("", e);
                    isCreateSuccess = false;
                    guessEcomAdjDataDao.updateGuessEcomAdjDataByCode("", DefaultStatus.ERROR.getValue(), guessEcomAdjData.getAdjCode());
                }

                if (isCreateSuccess && ou != null && shop != null) {
                    // 设置组织
                    ic.setOu(wh.getOu());
                    ic.setShop(shop);
                    invDao.flush();
                    guessEcomAdjDataDao.updateGuessEcomAdjDataByCode(ic.getCode(), DefaultStatus.FINISHED.getValue(), guessEcomAdjData.getAdjCode());
                    log.debug("===============InventoryCheck {} create success ================", new Object[] {ic.getCode()});
                } else {
                    log.debug("===============InventoryCheck {} create error ================", new Object[] {ic.getCode()});
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        }
        return ic;
    }

    @Override
    public void receiveWhGuessInventoryByMq(String message) {
        WhGuessInventory whGuessInventory = null;
        try {
            List<?> obj = JSONUtil.jsonToList(message);
            if (obj == null || obj.size() == 0) {
                log.info("**GUESS INVENTORY SNAPSHOT is null ");
                return;
            }
            Integer i = null;
            for (i = 0; i < obj.size(); i++) {
                InventorySnapshot inventorySnapshot = (InventorySnapshot) JSONUtil.jsonToBean(JSONUtil.beanToJson(obj.get(i)), InventorySnapshot.class);
                whGuessInventory = new WhGuessInventory();
                setWhGuessInventory(whGuessInventory, inventorySnapshot);
                whGuessInventoryDao.save(whGuessInventory);
            }
            whGuessInventoryDao.flush();
        } catch (Exception e) {
            throw new BusinessException();
        }
    }

    @Override
    public void receiveWhGuessInventoryRetailByMq(String message) {
        log.info("receiveWhGuessInventoryRetailByMq>>>>>>>>>>>>>>>>>>>>>-begin");
        WhGuessInventoryRetail whGuessInventory = null;
        try {
            List<?> obj = JSONUtil.jsonToList(message);
            if (obj == null || obj.size() == 0) {
                log.info("**GUESS INVENTORY SNAPSHOT is null ");
                return;
            }
            Integer i = null;
            for (i = 0; i < obj.size(); i++) {
                InventorySnapshot inventorySnapshot = (InventorySnapshot) JSONUtil.jsonToBean(JSONUtil.beanToJson(obj.get(i)), InventorySnapshot.class);
                whGuessInventory = new WhGuessInventoryRetail();
                setWhGuessInventoryRetail(whGuessInventory, inventorySnapshot);
                guessInventoryRetailDao.save(whGuessInventory);
            }
            whGuessInventoryDao.flush();
        } catch (Exception e) {
            throw new BusinessException();
        }

    }

    @Override
    public void insertGuessInventoryRetailLog() {
        try {
            whUaInventoryLogDao.insertGuessInventoryRetailLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("guess insertGuessInventoryRetailLog Exception:", e);
            }
        }

    }

    private void setWhGuessInventoryRetail(WhGuessInventoryRetail whGuessInventory, InventorySnapshot inventorySnapshot) {
        whGuessInventory.setTransDate(inventorySnapshot.getTransDate());
        whGuessInventory.setTransTime(inventorySnapshot.getTransTime());
        whGuessInventory.setStoreCode(inventorySnapshot.getStoreCode());
        whGuessInventory.setBinCode(inventorySnapshot.getBinCode());
        whGuessInventory.setProductCode(inventorySnapshot.getProductCode());
        whGuessInventory.setColorDescription(inventorySnapshot.getColorDescription());
        whGuessInventory.setSizeDescription(inventorySnapshot.getSizeDescription());
        whGuessInventory.setShortSku(inventorySnapshot.getShortSku());
        whGuessInventory.setOnhandQty(inventorySnapshot.getOnhandQty());
        whGuessInventory.setCreateTime(new Date());
    }


    private void setWhGuessInventory(WhGuessInventory whGuessInventory, InventorySnapshot inventorySnapshot) {
        whGuessInventory.setTransDate(inventorySnapshot.getTransDate());
        whGuessInventory.setTransTime(inventorySnapshot.getTransTime());
        whGuessInventory.setStoreCode(inventorySnapshot.getStoreCode());
        whGuessInventory.setBinCode(inventorySnapshot.getBinCode());
        whGuessInventory.setProductCode(inventorySnapshot.getProductCode());
        whGuessInventory.setColorDescription(inventorySnapshot.getColorDescription());
        whGuessInventory.setSizeDescription(inventorySnapshot.getSizeDescription());
        whGuessInventory.setShortSku(inventorySnapshot.getShortSku());
        whGuessInventory.setOnhandQty(inventorySnapshot.getOnhandQty());
        whGuessInventory.setCreateTime(new Date());
    }

    public void insertGuessInventoryLog() {
        try {
            whUaInventoryLogDao.insertGuessInventoryLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("guess insertGuessInventoryLog Exception:", e);
            }
        }
    }

    @Override
    public void createInventoryRetail() {
        OperationUnit ou = null;
        BiChannel shop = null;
        Warehouse wh = null;
        InventoryStatus isForSaleTrue = null;
        InventoryStatus isForSaleFlase = null;
        InventoryCheck ic = new InventoryCheck();
        ic.setCreateTime(new Date());
        ic.setStatus(InventoryCheckStatus.CREATED);
        ic.setType(InventoryCheckType.VMI);
        ic.setInvType(1);

        ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));

        invDao.save(ic);

        if (shop == null) {
            shop = shopDao.getByvmiWHSource(Constants.VIM_WH_SOURCE_GUESS);
        }
        if (shop == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        if (wh == null) {
            wh = whManager.getWareHouseByVmiSource(Constants.VIM_WH_SOURCE_GUESS_RETAIL);
        }
        if (wh == null) {
            log.debug("=========WAREHOUSE UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        Long ouId = wh.getOu().getId();
        if (ouId == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        ou = ouDao.getByPrimaryKey(ouId);
        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        if (isForSaleFlase == null) {
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


        List<WhUaInventoryLog> inventoryLogs = whUaInventoryLogDao.findUaInventoryLogByRe(new BeanPropertyRowMapper<WhUaInventoryLog>(WhUaInventoryLog.class));
        if (inventoryLogs.size() == 0) {
            throw new BusinessException(ErrorCode.NOT_FIND_STV);
        }

        inventoryLogs = whUaInventoryLogDao.findbyUaInventoryLog(new BeanPropertyRowMapper<WhUaInventoryLog>(WhUaInventoryLog.class));
        if (inventoryLogs.size() == 0) {
            throw new BusinessException(ErrorCode.NOT_FIND_STV);
        }

        for (WhUaInventoryLog inventoryLog : inventoryLogs) {

            Sku sku = whManager.getByExtCode2AndCustomerAndShopId(inventoryLog.getSku().trim(), shop.getCustomer().getId(), shop.getId());
            if (sku == null) {
                // 如果sku在系统中没有，就生成，生成失败返回false
                baseinfoManager.sendMsgToOmsCreateSku(inventoryLog.getSku().trim(), shop.getVmiCode());
                continue;
            }
            InventoryCheckDifTotalLine line = new InventoryCheckDifTotalLine();
            line.setInventoryCheck(ic);
            line.setSku(sku);
            Long qty = Long.parseLong(inventoryLog.getQuantity() == null ? "0" : inventoryLog.getQuantity());
            line.setQuantity(new Long(inventoryLog.getTotalQty()) - qty);
            if ("良品".equals(inventoryLog.getShorts())) {
                line.setStatus(isForSaleTrue);
            } else {
                line.setStatus(isForSaleFlase);
            }
            icLineDao.save(line);
        }
        ic.setShop(shop);
        ic.setOu(ou);
        invDao.save(ic);

        idsManager.executionVmiAdjustment(ic);


    }



    @Override
    public void createInventoryByAll() {
        OperationUnit ou = null;
        BiChannel shop = null;
        Warehouse wh = null;
        InventoryStatus isForSaleTrue = null;
        InventoryStatus isForSaleFlase = null;
        InventoryCheck ic = new InventoryCheck();
        ic.setCreateTime(new Date());
        ic.setStatus(InventoryCheckStatus.CREATED);
        ic.setType(InventoryCheckType.VMI);
        ic.setInvType(1);

        ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));

        invDao.save(ic);

        if (shop == null) {
            shop = shopDao.getByvmiWHSource(Constants.VIM_WH_SOURCE_GUESS);
        }
        if (shop == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        if (wh == null) {
            wh = whManager.getWareHouseByVmiSource(Constants.VIM_WH_SOURCE_GUESS);
        }
        if (wh == null) {
            log.debug("=========WAREHOUSE UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        Long ouId = wh.getOu().getId();
        if (ouId == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        ou = ouDao.getByPrimaryKey(ouId);
        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {Constants.VIM_WH_SOURCE_GUESS});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        if (isForSaleFlase == null) {
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

        List<WhUaInventoryLog> inventoryLogs = whUaInventoryLogDao.findbyUaInventoryLogByAll(new BeanPropertyRowMapper<WhUaInventoryLog>(WhUaInventoryLog.class));

        if (inventoryLogs.size() == 0) {
            throw new BusinessException(ErrorCode.NOT_FIND_STV);
        }

        inventoryLogs = whUaInventoryLogDao.findbyUaInventoryLogByAll(new BeanPropertyRowMapper<WhUaInventoryLog>(WhUaInventoryLog.class));
        if (inventoryLogs.size() == 0) {
            throw new BusinessException(ErrorCode.NOT_FIND_STV);
        }

        for (WhUaInventoryLog inventoryLog : inventoryLogs) {

            Sku sku = whManager.getByExtCode2AndCustomerAndShopId(inventoryLog.getSku().trim(), shop.getCustomer().getId(), shop.getId());
            if (sku == null) {
                // 如果sku在系统中没有，就生成，生成失败返回false
                baseinfoManager.sendMsgToOmsCreateSku(inventoryLog.getSku().trim(), shop.getVmiCode());
                continue;
            }
            InventoryCheckDifTotalLine line = new InventoryCheckDifTotalLine();
            line.setInventoryCheck(ic);
            line.setSku(sku);
            Long qty = Long.parseLong(inventoryLog.getQuantity() == null ? "0" : inventoryLog.getQuantity());
            line.setQuantity(new Long(inventoryLog.getTotalQty()) - qty);
            if ("良品".equals(inventoryLog.getShorts())) {
                line.setStatus(isForSaleTrue);
            } else {
                line.setStatus(isForSaleFlase);
            }
            icLineDao.save(line);
        }
        ic.setShop(shop);
        ic.setOu(ou);
        invDao.save(ic);

        idsManager.executionVmiAdjustment(ic);


    }

    @Override
    public void createInventory(InventoryCheck ic) {
        wareHouseManager.confirmVMIInvCKAdjustment(ic);

    }


}
