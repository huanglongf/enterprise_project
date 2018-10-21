package com.jumbo.wms.manager.mq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.bh.connector.cch.constants.ConstantsCch;
import cn.baozun.bh.connector.cch.model.Sales;
import cn.baozun.bh.connector.cch.model.StockReturns;
import cn.baozun.bh.connector.cch.model.StockTransConfirms;
import cn.baozun.bh.connector.cch.model.StockTransConfirms.StockTransConfirm;
import cn.baozun.bh.connector.cch.model.StockTransConfirms.StockTransConfirm.ReceiverInfo;
import cn.baozun.bh.connector.cch.model.StockTransConfirms.StockTransConfirm.ReceiverLine;
import cn.baozun.bh.connector.gdv.constants.ConstantsGdv;
import cn.baozun.bh.connector.gdv.model.order.DSO;
import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.connector.philips.constants.ConstantsPhilips;
import cn.baozun.bh.connector.philips.model.GoodsIssue;
import cn.baozun.bh.connector.philips.model.GoodsMovement;
import cn.baozun.bh.connector.philips.model.GoodsReceipt;
import cn.baozun.bh.connector.philips.model.GoodsReceiptCustomerReturn;
import cn.baozun.bh.connector.philips.model.StockComparison;
import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.mq.MqInvoicePrintMsgLogDao;
import com.jumbo.dao.mq.MqMdPriceLogDao;
import com.jumbo.dao.mq.MqMsgBatchDao;
import com.jumbo.dao.mq.MqPriceLogDao;
import com.jumbo.dao.vmi.cch.CchStockReturnInfoDao;
import com.jumbo.dao.vmi.cch.CchStockReturnLineDao;
import com.jumbo.dao.vmi.cch.CchStockTransConfirmDao;
import com.jumbo.dao.vmi.cch.CchStockTransConfirmLineDao;
import com.jumbo.dao.vmi.order.VMIOrderLineDao;
import com.jumbo.dao.vmi.order.VmiOrderOpLogDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnReceiptDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnReceiptLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsIssueDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsIssueLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsMovementDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsMovementLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsReceiptDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsReceiptLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsStockComparisonDao;
import com.jumbo.dao.vmi.philipsData.PhilipsStockComparisonLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.mq.FlowInvoicePrintMsg;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.mq.MqMdPriceMsg;
import com.jumbo.mq.MqSkuPriceMsg;
import com.jumbo.util.mq.MqMsgUtil;
import com.jumbo.util.zip.ZipUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.cacheData.CacheParseDataManager;
import com.jumbo.wms.model.DataStatus;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.mq.MqInvoicePrintMsgLog;
import com.jumbo.wms.model.mq.MqMdPriceLog;
import com.jumbo.wms.model.mq.MqMsgBatch;
import com.jumbo.wms.model.mq.MqMsgBatchStatus;
import com.jumbo.wms.model.mq.MqSkuPriceLog;
import com.jumbo.wms.model.vmi.cch.CchStockReturnInfo;
import com.jumbo.wms.model.vmi.cch.CchStockReturnLine;
import com.jumbo.wms.model.vmi.cch.CchStockTransConfirm;
import com.jumbo.wms.model.vmi.cch.CchStockTransConfirmLine;
import com.jumbo.wms.model.vmi.order.VMIOrderCommand;
import com.jumbo.wms.model.vmi.order.VMIOrderLine;
import com.jumbo.wms.model.vmi.order.VmiOrderOpLog;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnReceipt;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnReceiptLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsIssue;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsIssueLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsMovement;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsMovementLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsReceipt;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsReceiptLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsStockComparison;
import com.jumbo.wms.model.vmi.philipsData.PhilipsStockComparisonLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLineCommand;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Service("mqManager")
public class MqManagerImpl extends BaseManagerImpl implements MqManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -9109070099320848211L;

    public static final String MS_CODE_START = "MSFT-";
    @Autowired
    private VmiOrderOpLogDao orderLogDao;
    @Autowired
    private MqMsgBatchDao mqMsgBatchDao;
    @Autowired
    private MqPriceLogDao mqPriceLogDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private MqMdPriceLogDao mqMdPriceLogDao;
    @Autowired
    private CchStockTransConfirmDao confirmDao;
    @Autowired
    private CchStockTransConfirmLineDao confirmLineDao;
    @Autowired
    private CchStockReturnLineDao srLineDao;
    @Autowired
    private CchStockReturnInfoDao srDao;
    @Autowired
    private CacheParseDataManager cchManager;
    @Autowired
    private PhilipsGoodsIssueDao pGoodsIssueDao;
    @Autowired
    private PhilipsGoodsIssueLineDao pGoodsIssueLineDao;
    @Autowired
    private VMIOrderLineDao orderLineDao;
    @Autowired
    private PhilipsGoodsReceiptDao pGoodsReceiptDao;
    @Autowired
    private PhilipsGoodsReceiptLineDao pGoodsReceiptLineDao;
    @Autowired
    private PhilipsCustomerReturnReceiptDao pCustomerReturnReceiptDao;
    @Autowired
    private PhilipsCustomerReturnReceiptLineDao pCustomerReturnReceiptLineDao;
    @Autowired
    private PhilipsGoodsMovementDao pGoodsMovementDao;
    @Autowired
    private PhilipsGoodsMovementLineDao pGoodsMovementLineDao;
    @Autowired
    private PhilipsStockComparisonDao pStockComparisonDao;
    @Autowired
    private PhilipsStockComparisonLineDao pStockComparisonLineDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MqInvoicePrintMsgLogDao mqInvoicePrintMsgLogDao;

    @Autowired
    private StaInvoiceDao staInvoiceDao;

    @Autowired
    private StockTransApplicationDao staDao;

    /**
     * MQ消息模板
     */
    private JmsTemplate bhMqJmsTemplate;

    @PostConstruct
    protected void init() {
        try {
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named bhMqJmsTemplate Class:MqManagerImpl");
        }
    }

    @Transactional
    public void sendGDVMqOrder(VMIOrderCommand ord, String mqName, Long shopId, String vmiCode) {
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_ORDER);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        VmiOrderOpLog log = orderLogDao.getOrderLogByOrderCode(ord.getCode());
        // 转Json
        String msg = "";
        if (BiChannel.CHANNEL_VMICODE_GDV_STORE.equals(vmiCode) || BiChannel.CHANNEL_VMICODE_GDV_TB.equals(vmiCode)) {
            // gdv
            List<VMIOrderLine> lines = orderLineDao.findByCode(ord.getCode());
            DSO dso = MqMsgUtil.constructMqGodivaOrder(ord, lines);
            msg = MarshallerUtil.buildJaxb(dso);
            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsGdv.BH_CONNECTOR_PLATFORM_CODE_GDV);
            connectorMessage.setInterfaceCode(ConstantsGdv.BH_CONNECTOR_INTERFACE_CODE_GDV_ORDER);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_XML);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            msg = JSONUtil.beanToJson(connectorMessage);
            log.setOpType("1");// 如果是取消订单需要修改状态为发送成功！
        }
        if (msg == null || "".equals(msg)) {
            return;
        }
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);
        msgBatch.setStatus(MqMsgBatchStatus.SENDED);
        msgBatch.setSendTime(Calendar.getInstance().getTime());
        log.setMsgBatchId(msgBatch);
        orderLogDao.save(log);
    }

    /**
     * 发送价格信息至MQ队列
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public void sendMqSkuPrice(List<MqSkuPriceLog> logs, String queueName, Long shopId) {
        // 更新相关log信息后再发送,避免mq消息发送成功本地log更新失败的情况.
        // 1.更新MqSkuLog发送状态以及发送时间
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_PRICE);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        Long msgBatchId = msgBatch.getId();
        for (MqSkuPriceLog lg : logs) {
            lg.setMsgBatchId(msgBatchId);
            mqPriceLogDao.save(lg);
        }
        // 2.批量发送mq消息
        List<MqSkuPriceMsg> mqskuPriceMsgs = MqMsgUtil.constructMqSkuPriceMsg(logs);
        MqMsgUtil.sendMsgToMq(bhMqJmsTemplate, queueName, mqskuPriceMsgs, msgBatchId);
        msgBatch.setStatus(MqMsgBatchStatus.SENDED);
        msgBatch.setSendTime(Calendar.getInstance().getTime());
        log.debug("MQ_INV_SENDED:SHOP{},MSG_TYPE{},QUEUE{}, SIZE{}", Arrays.asList(shopId, MqMsgBatch.MQ_MSG_TYPE_PRICE, queueName, mqskuPriceMsgs.size()));
    }

    /**
     * 创建订单处理反馈信息
     */
    @Transactional
    /**
     * BY店铺OU创建同步全量库存记录
     */
    public void createFullInventoryLog(Long ouid) {
        // companyShopDao.desableMqInvByOu(ouid);
        // try {
        // createMqFullInventoryLog(ouid);
        // } catch (Exception e) {
        // log.error("", e);
        // log.error(e.getMessage());
        // } finally {
        // companyShopDao.enableMqInvByOu(ouid);
        // }
    }

    /**
     * 发送markdown价格信息至MQ队列
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public void sendMqMdPrice(List<MqMdPriceLog> logs, String queueName, Long shopId) {
        // 更新相关log信息后再发送,避免mq消息发送成功本地log更新失败的情况.
        // 1.更新MqSkuLog发送状态以及发送时间
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_PRICE);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        Long msgBatchId = msgBatch.getId();
        for (MqMdPriceLog lg : logs) {
            lg.setMsgBatchId(msgBatchId);
            mqMdPriceLogDao.save(lg);
        }
        // 2.批量发送mq消息
        List<MqMdPriceMsg> mqMdPriceMsgs = MqMsgUtil.constructMqMdPriceMsg(logs);
        MqMsgUtil.sendMsgToMq(bhMqJmsTemplate, queueName, mqMdPriceMsgs, msgBatchId);
        msgBatch.setStatus(MqMsgBatchStatus.SENDED);
        msgBatch.setSendTime(Calendar.getInstance().getTime());
        log.debug("MQ_INV_SENDED:SHOP{},MSG_TYPE{},QUEUE{}, SIZE{}", Arrays.asList(shopId, MqMsgBatch.MQ_MSG_TYPE_PRICE, queueName, mqMdPriceMsgs.size()));
    }

    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }

    @Transactional
    public void sendMqGDVSalesOrder(MsgOutboundOrderCommand order, String mqName) {
        List<MsgOutboundOrderLineCommand> lineList = msgOutboundOrderLineDao.findMsgoutLineByMsgId(order.getId(), new BeanPropertyRowMapperExt<MsgOutboundOrderLineCommand>(MsgOutboundOrderLineCommand.class));
        if (lineList.size() > 0) {
            StockTransApplication sta = staDao.findStaByCode(order.getStaCode());
            List<StaInvoice> staInvoices = staInvoiceDao.getBySta(sta.getId());
            DSO dso = MqMsgUtil.constructMqGodivaSalesOrder(order, lineList, staInvoices);
            // 转Json
            String msg = "";
            msg = MarshallerUtil.buildJaxb(dso);
            // System.out.println(msg);
            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsGdv.BH_CONNECTOR_PLATFORM_CODE_GDV);
            connectorMessage.setInterfaceCode(ConstantsGdv.BH_CONNECTOR_INTERFACE_CODE_GDV_ORDER);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_XML);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            msg = JSONUtil.beanToJson(connectorMessage);

            if (msg == null || msg.trim().length() == 0) {
                return;
            }
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);
            msgOutboundOrderDao.updateStatusById(DefaultStatus.FINISHED.getValue(), order.getId());
        }
    }

    @Transactional
    public void sendMqCCHSale(Long shopId, String shopCode, Date date, String mqName) {
        Sales sale = cchManager.getSSSalesFile(shopId, shopCode, date);
        String msg = JSONUtil.beanToJson(sale);
        ConnectorMessage connectorMessage = new ConnectorMessage();
        connectorMessage.setPlatformCode(ConstantsCch.BH_CONNECTOR_PLATFORM_CODE_CCH);
        connectorMessage.setInterfaceCode(ConstantsCch.BH_CONNECTOR_INTERFACE_CODE_CCH_SALES);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));
        String mqMsg = JSONUtil.beanToJson(connectorMessage);
        // 发送mq消息
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, mqMsg);
    }

    /**
     * 发送CCH收货反馈
     * 
     * @param headers
     * @param mqName
     * @param shopId
     */
    @Transactional
    public void sendMqCCHAsnReceive(List<CchStockTransConfirm> headers, String mqName, Long shopId) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_ASNRECEIVE);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        StockTransConfirms confirms = new StockTransConfirms();
        for (CchStockTransConfirm header : headers) {
            StockTransConfirm confirm = new StockTransConfirm();
            StockTransConfirm.ReceiverInfo info = new ReceiverInfo();
            info.setReceptionNumber(header.getReceptionNumber());
            info.setReceptionDate(header.getReceptionDate());
            info.setReceptionHour(header.getReceptionHour());
            info.setReceivedBy(header.getReceivedBy() == null ? "" : header.getReceivedBy());
            info.setSlipNumber(header.getSlipNumber());
            info.setShipmentNumber(header.getShipmentNumber());
            confirm.setInfo(info);
            List<CchStockTransConfirmLine> lines = confirmLineDao.findGroupLineByConfirm(header.getId(), new BeanPropertyRowMapperExt<CchStockTransConfirmLine>(CchStockTransConfirmLine.class));
            Map<String, Long> map = new HashMap<String, Long>();
            for (CchStockTransConfirmLine line : lines) {
                String key = line.getSkuCode();
                if (map.get(key) == null) {
                    map.put(key, line.getQuantity2());
                } else {
                    Long qty = map.get(key) + line.getQuantity2();
                    map.put(key, qty);
                }
            }
            List<StockTransConfirm.ReceiverLine> rLines = new ArrayList<StockTransConfirm.ReceiverLine>();
            for (Entry<String, Long> ent : map.entrySet()) {
                StockTransConfirm.ReceiverLine rLine = new ReceiverLine();
                rLine.setReceiverQuantity(ent.getValue().toString());
                rLine.setSkuCode(ent.getKey());
                rLines.add(rLine);
            }
            confirm.setLines(rLines);
            confirms.getStockTransConfirms().add(confirm);
        }

        // 转XML
        String msg = JSONUtil.beanToJson(confirms);
        ConnectorMessage connectorMessage = new ConnectorMessage();
        connectorMessage.setPlatformCode(ConstantsCch.BH_CONNECTOR_PLATFORM_CODE_CCH);
        connectorMessage.setInterfaceCode(ConstantsCch.BH_CONNECTOR_INTERFACE_CODE_CCH_STOCKTRANSCONFIRM);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));

        String mqMsg = JSONUtil.beanToJson(connectorMessage);
        // 发送mq消息
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, mqMsg);
        msgBatch.setStatus(MqMsgBatchStatus.SENDED);
        msgBatch.setSendTime(Calendar.getInstance().getTime());
        mqMsgBatchDao.save(msgBatch);
        for (CchStockTransConfirm header : headers) {
            header.setMsgBatchId(msgBatch.getId());
            header.setStatus(DataStatus.FINISHED.getValue());
            confirmDao.save(header);
        }

    }


    @Transactional
    public void sendMqPhilipsGoodsIssue(PhilipsGoodsIssue pGoodsIssue, String mqName, Long shopId) {
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_GOODS_ISSUE);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        // 转Json
        String msg = "";
        // goodsissue
        try {
            pGoodsIssue.setCreateTime(new Date());
            pGoodsIssue.setStatus(DataStatus.FINISHED.getValue());
            // goodsissueline
            List<PhilipsGoodsIssueLine> lines = pGoodsIssueLineDao.getPhilipsGoodsIssueLinesByIssueId(pGoodsIssue.getId(), new BeanPropertyRowMapperExt<PhilipsGoodsIssueLine>(PhilipsGoodsIssueLine.class));
            for (PhilipsGoodsIssueLine pGoodsIssueLine : lines) {
                pGoodsIssueLine.setCreateTime(new Date());
                pGoodsIssueLine.setStatus(DataStatus.FINISHED.getValue());
                pGoodsIssueLineDao.save(pGoodsIssueLine);
            }
            GoodsIssue goodsIssue = new GoodsIssue();
            goodsIssue.setHeader(MqMsgUtil.constructMqPhilipsGoodsIuuse(pGoodsIssue));
            goodsIssue.setLines(MqMsgUtil.constructMqPhilipsGoodsIuuseLine(lines));
            // 转Json
            msg = JSONUtil.beanToJson(goodsIssue);

            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsPhilips.BH_CONNECTOR_PLATFORM_CODE_PHILIPS);
            connectorMessage.setInterfaceCode(ConstantsPhilips.BH_CONNECTOR_INTERFACE_CODE_PHILIPS_GOODS_ISSUE);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            msg = JSONUtil.beanToJson(connectorMessage);
            if (msg == null || "".equals(msg)) {
                return;
            }
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);
            msgBatch.setStatus(MqMsgBatchStatus.SENDED);
            msgBatch.setSendTime(Calendar.getInstance().getTime());
            pGoodsIssue.setBatchId(msgBatch.getId());
            pGoodsIssueDao.save(pGoodsIssue);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Transactional
    public void sendMqPhilipsGoodsReceipt(PhilipsGoodsReceipt philipsGoodsReceipt, String mqName, Long shopId) {
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_GOODS_RECEIPT);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        // 转Json
        String msg = "";
        // GoodsReceipt
        try {
            philipsGoodsReceipt.setCreateTime(new Date());
            philipsGoodsReceipt.setStatus(DataStatus.FINISHED.getValue());
            // pGoodsReceiptDao.getGoodsReceiptsNoBatchId(new
            // BeanPropertyRowMapperExt<PhilipsGoodsReceipt>(PhilipsGoodsReceipt.class));
            // GoodsReceiptline
            List<PhilipsGoodsReceiptLine> lines = pGoodsReceiptLineDao.getGoodsReceiptLinesByGoodsrId(philipsGoodsReceipt.getId(), new BeanPropertyRowMapperExt<PhilipsGoodsReceiptLine>(PhilipsGoodsReceiptLine.class));
            for (PhilipsGoodsReceiptLine pGoodsReceiptLine : lines) {
                pGoodsReceiptLine.setCreateTime(new Date());
                pGoodsReceiptLine.setStatus(DataStatus.FINISHED.getValue());
                pGoodsReceiptLineDao.save(pGoodsReceiptLine);
            }
            GoodsReceipt goodsReceipt = new GoodsReceipt();
            goodsReceipt.setHeader(MqMsgUtil.constructMqPhilipsGoodsReceipt(philipsGoodsReceipt));
            goodsReceipt.setLines(MqMsgUtil.constructMqPhilipsGoodsReceiptLine(lines));
            // 转Json
            msg = JSONUtil.beanToJson(goodsReceipt);

            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsPhilips.BH_CONNECTOR_PLATFORM_CODE_PHILIPS);
            connectorMessage.setInterfaceCode(ConstantsPhilips.BH_CONNECTOR_INTERFACE_CODE_PHILIPS_GOODS_RECEIPT);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            msg = JSONUtil.beanToJson(connectorMessage);
            if (msg == null || "".equals(msg)) {
                return;
            }
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);
            msgBatch.setStatus(MqMsgBatchStatus.SENDED);
            msgBatch.setSendTime(Calendar.getInstance().getTime());
            philipsGoodsReceipt.setBatchId(msgBatch.getId());
            pGoodsReceiptDao.save(philipsGoodsReceipt);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Transactional
    public void sendMqPhilipsGoodsReceiptReturn(PhilipsCustomerReturnReceipt pCustomerReturnReceipt, String mqName, Long shopId) {
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_GOODS_RECEIPT_RETURN);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        // 转Json
        String msg = "";
        // CustomerReturnReceipt
        try {
            pCustomerReturnReceipt.setCreateTime(new Date());
            pCustomerReturnReceipt.setStatus(DataStatus.FINISHED.getValue());
            // CustomerReturnReceiptline
            List<PhilipsCustomerReturnReceiptLine> lines =
                    pCustomerReturnReceiptLineDao.getCusReturnReceiptLineByGoodsrrId(pCustomerReturnReceipt.getId(), new BeanPropertyRowMapperExt<PhilipsCustomerReturnReceiptLine>(PhilipsCustomerReturnReceiptLine.class));
            for (PhilipsCustomerReturnReceiptLine pCusReturnReceiptLine : lines) {
                pCusReturnReceiptLine.setCreateTime(new Date());
                pCusReturnReceiptLine.setStatus(DataStatus.FINISHED.getValue());
                pCustomerReturnReceiptLineDao.save(pCusReturnReceiptLine);
            }
            GoodsReceiptCustomerReturn gReceiptCustomerReturn = new GoodsReceiptCustomerReturn();
            gReceiptCustomerReturn.setHeader(MqMsgUtil.constructMqPhilipsGoodsReceiptCusReturn(pCustomerReturnReceipt));
            gReceiptCustomerReturn.setLines(MqMsgUtil.constructMqPhilipsGoodsReceiptCusRLine(lines));
            // 转Json
            msg = JSONUtil.beanToJson(gReceiptCustomerReturn);

            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsPhilips.BH_CONNECTOR_PLATFORM_CODE_PHILIPS);
            connectorMessage.setInterfaceCode(ConstantsPhilips.BH_CONNECTOR_INTERFACE_CODE_PHILIPS_GOODS_RECEIPT_CUSTOMER_RETURN);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            msg = JSONUtil.beanToJson(connectorMessage);
            if (msg == null || "".equals(msg)) {
                return;
            }
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);
            msgBatch.setStatus(MqMsgBatchStatus.SENDED);
            msgBatch.setSendTime(Calendar.getInstance().getTime());
            pCustomerReturnReceipt.setBatchId(msgBatch.getId());
            pCustomerReturnReceiptDao.save(pCustomerReturnReceipt);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    @Transactional
    public void sendMqPhilipsGoodsMovement(PhilipsGoodsMovement pGoodsMovement, String mqName, Long shopId) {
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_GOODS_MOVEMENT);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        // 转Json
        String msg = "";
        // GoodsMovement
        try {
            pGoodsMovement.setCreateTime(new Date());
            pGoodsMovement.setStatus(DataStatus.FINISHED.getValue());
            // GoodsMovementline
            List<PhilipsGoodsMovementLine> lines = pGoodsMovementLineDao.getGoodsMovementLinesByGoodsmId(pGoodsMovement.getId(), new BeanPropertyRowMapperExt<PhilipsGoodsMovementLine>(PhilipsGoodsMovementLine.class));
            for (PhilipsGoodsMovementLine pGoodsMovementLine : lines) {
                pGoodsMovementLine.setCreateTime(new Date());
                pGoodsMovementLine.setStatus(DataStatus.FINISHED.getValue());
                pGoodsMovementLineDao.save(pGoodsMovementLine);
            }
            GoodsMovement goodsMovement = new GoodsMovement();
            goodsMovement.setHeader(MqMsgUtil.constructMqPhilipsGoodsMovement(pGoodsMovement));
            goodsMovement.setLines(MqMsgUtil.constructMqPhilipsGoodsMovementLine(lines));
            // 转Json
            msg = JSONUtil.beanToJson(goodsMovement);

            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsPhilips.BH_CONNECTOR_PLATFORM_CODE_PHILIPS);
            connectorMessage.setInterfaceCode(ConstantsPhilips.BH_CONNECTOR_INTERFACE_CODE_PHILIPS_GOODS_MOVEMENT);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            msg = JSONUtil.beanToJson(connectorMessage);
            if (msg == null || "".equals(msg)) {
                return;
            }
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);
            msgBatch.setStatus(MqMsgBatchStatus.SENDED);
            msgBatch.setSendTime(Calendar.getInstance().getTime());
            pGoodsMovement.setBatchId(msgBatch.getId());
            pGoodsMovementDao.save(pGoodsMovement);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Transactional
    public void sendMqPhilipsGoodsStockComparison(PhilipsStockComparison pStockComparison, String mqName, Long shopId) {
        log.debug("***************** philips sendMqPhilipsGoodsStockComparison start ***************");
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_GOODS_STOCK_COMPARISON);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);
        // 转Json
        String msg = "";
        // StockComparison
        try {
            pStockComparison.setCreateTime(new Date());
            pStockComparison.setStatus(DataStatus.FINISHED.getValue());
            // StockComparisonline
            List<PhilipsStockComparisonLine> lines = pStockComparisonLineDao.getComparisonLinesByStockId(pStockComparison.getId(), new BeanPropertyRowMapperExt<PhilipsStockComparisonLine>(PhilipsStockComparisonLine.class));
            for (PhilipsStockComparisonLine pStockComparisonLine : lines) {
                pStockComparisonLine.setCreateTime(new Date());
                pStockComparisonLine.setStatus(DataStatus.FINISHED.getValue());
                pStockComparisonLineDao.save(pStockComparisonLine);
            }
            StockComparison stockComparison = new StockComparison();
            stockComparison.setHeader(MqMsgUtil.constructMqPhilipsStockComparison(pStockComparison));
            stockComparison.setLines(MqMsgUtil.constructMqPhilipsStockComparison(lines));
            // 转Json
            msg = JSONUtil.beanToJson(stockComparison);

            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsPhilips.BH_CONNECTOR_PLATFORM_CODE_PHILIPS);
            connectorMessage.setInterfaceCode(ConstantsPhilips.BH_CONNECTOR_INTERFACE_CODE_PHILIPS_STOCK_COMPARISON);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            msg = JSONUtil.beanToJson(connectorMessage);
            if (msg == null || "".equals(msg)) {
                return;
            }
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, msg);
            msgBatch.setStatus(MqMsgBatchStatus.SENDED);
            msgBatch.setSendTime(Calendar.getInstance().getTime());
            pStockComparison.setBatchId(msgBatch.getId());
            pStockComparisonDao.save(pStockComparison);
            log.debug("***************** philips sendMqPhilipsGoodsStockComparison end ***************");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void constructMqInvoicePrintMsgLog(FlowInvoicePrintMsg printMsg) {
        try {
            MqInvoicePrintMsgLog invMsgLog = new MqInvoicePrintMsgLog();
            invMsgLog.setAmt(printMsg.getAmt());
            invMsgLog.setDeviceNo(printMsg.getDeviceNo());
            invMsgLog.setDrawer(printMsg.getDrawer());
            invMsgLog.setInvoiceCode(printMsg.getInvoiceCode());
            invMsgLog.setInvoiceNo(printMsg.getInvoiceNo());
            invMsgLog.setInvoiceType(printMsg.getInvoiceType());
            invMsgLog.setMemo(printMsg.getMemo());
            invMsgLog.setPayee(printMsg.getPayee());
            invMsgLog.setPayer(printMsg.getPayer());
            invMsgLog.setPayeeCompany(printMsg.getPayeeCompany());
            invMsgLog.setPrintDate(printMsg.getPrintDate());
            invMsgLog.setStatus(new Integer(1));
            mqInvoicePrintMsgLogDao.save(invMsgLog);
        } catch (Exception e) {
            log.error("-----------saving MqInvoicePrintMsgLog exception--------------");
            log.debug("payer:" + printMsg.getPayer() + "|| memo:" + (printMsg.getMemo() == null ? "" : printMsg.getMemo()));
            log.error("", e);
        }
    }

    /**
     * 批次发送CCH退大仓反馈
     */
    @Transactional
    public void sendMqCCHRTVList(List<CchStockReturnInfo> cchStockReturnInfo, String mqName, Long shopId, Long staId) {
        MqMsgBatch msgBatch = new MqMsgBatch();
        msgBatch.setMsgType(MqMsgBatch.MQ_MSG_TYPE_ASNRECEIVE);
        msgBatch.setCreateTime(Calendar.getInstance().getTime());
        msgBatch.setStatus(MqMsgBatchStatus.CREATED);
        msgBatch.setShopId(shopId);
        mqMsgBatchDao.save(msgBatch);

        StockReturns stockReturns = new StockReturns();
        List<StockReturns.StockReturn> stockReturnList = new ArrayList<StockReturns.StockReturn>();

        for (CchStockReturnInfo header : cchStockReturnInfo) {
            StockReturns.StockReturn stockReturn = new StockReturns.StockReturn();
            StockReturns.ReturnInfo info = new StockReturns.ReturnInfo();
            info.setCarrierShipNumber("63");
            info.setWarehouseCode(String.valueOf(header.getWarehouseCode()));
            info.setDate(header.getReturnDate());
            info.setReturnNumber(header.getReturnNumber().toString());
            stockReturn.setInfo(info);
            List<CchStockReturnLine> lines = srLineDao.findLineByStockReturn(header.getId());
            if (lines == null || lines.size() == 0) {
                continue;
            }
            List<StockReturns.TransferLine> rLines = new ArrayList<StockReturns.TransferLine>();
            Map<String, Long> map = new HashMap<String, Long>();
            for (CchStockReturnLine line : lines) {
                String key = line.getSkuCode();
                if (map.get(key) == null) {
                    map.put(key, line.getQuantityShipped());
                } else {
                    Long qty = map.get(key) + line.getQuantityShipped();
                    map.put(key, qty);
                }
            }
            for (Entry<String, Long> ent : map.entrySet()) {
                StockReturns.TransferLine rLine = new StockReturns.TransferLine();
                rLine.setQuantityShipped(ent.getValue().toString());
                rLine.setSkuCode(ent.getKey());
                rLines.add(rLine);
            }
            stockReturn.setLines(rLines);
            stockReturnList.add(stockReturn);
        }
        stockReturns.setStockReturns(stockReturnList);

        // 转XML
        String msg = JSONUtil.beanToJson(stockReturns);
        ConnectorMessage connectorMessage = new ConnectorMessage();
        connectorMessage.setPlatformCode(ConstantsCch.BH_CONNECTOR_PLATFORM_CODE_CCH);
        connectorMessage.setInterfaceCode(ConstantsCch.BH_CONNECTOR_INTERFACE_CODE_CCH_STOCKRETURN);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));

        String mqMsg = JSONUtil.beanToJson(connectorMessage);
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, mqName, mqMsg);
        msgBatch.setStatus(MqMsgBatchStatus.SENDED);
        msgBatch.setSendTime(Calendar.getInstance().getTime());

        // 更新
        srDao.updateCchStockReturnInfoByStaId(msgBatch.getId(), DataStatus.FINISHED.getValue(), staId);
        mqMsgBatchDao.save(msgBatch);
    }
}
