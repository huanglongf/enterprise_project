package com.jumbo.util.mq;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;

import cn.baozun.bh.connector.gdv.model.order.DSO;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DeliveryInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.GeneralInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.ReferenceInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.Remarks;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.FreightInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.PickingCriteria;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.ReferenceInformation.ReferenceOrder;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.ReferenceInformation.UserDefinedLongItem;
import cn.baozun.bh.connector.philips.model.GoodsIssue;
import cn.baozun.bh.connector.philips.model.GoodsMovement;
import cn.baozun.bh.connector.philips.model.GoodsReceipt;
import cn.baozun.bh.connector.philips.model.GoodsReceiptCustomerReturn;
import cn.baozun.bh.connector.philips.model.StockComparison;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.mq.MqMdPriceLog;
import com.jumbo.wms.model.mq.MqSkuPriceLog;
import com.jumbo.wms.model.vmi.order.VMIOrderCommand;
import com.jumbo.wms.model.vmi.order.VMIOrderLine;
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
import com.jumbo.mq.FlowInvoiceMsgBatch;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.mq.MqMdPriceMsg;
import com.jumbo.mq.MqMsgList;
import com.jumbo.mq.MqSkuPriceMsg;
import com.jumbo.util.FormatUtil;

public class MqMsgUtil {
    protected static final Logger log = LoggerFactory.getLogger(MqMsgUtil.class);


    public static List<MqSkuPriceMsg> constructMqSkuPriceMsg(List<MqSkuPriceLog> logs) {
        List<MqSkuPriceMsg> rtnData = new ArrayList<MqSkuPriceMsg>();
        for (MqSkuPriceLog resultLog : logs) {
            MqSkuPriceMsg resultMsg = new MqSkuPriceMsg();
            resultMsg.setJmCode(resultLog.getJmCode());
            resultMsg.setSupplierSkuCode(resultLog.getSupplierSkuCode());
            resultMsg.setEffDate(resultLog.getEffDate());
            resultMsg.setRetailPrice(resultLog.getRetailPrice());
            resultMsg.setPrePrice(resultLog.getPrePrice());
            resultMsg.setProPrice(resultLog.getProPrice());
            resultMsg.setOpType(resultLog.getOpType());
            rtnData.add(resultMsg);
        }
        return rtnData;
    }

    public static List<MqMdPriceMsg> constructMqMdPriceMsg(List<MqMdPriceLog> logs) {
        List<MqMdPriceMsg> rtnData = new ArrayList<MqMdPriceMsg>();
        for (MqMdPriceLog resultLog : logs) {
            MqMdPriceMsg resultMsg = new MqMdPriceMsg();
            resultMsg.setSupplierSkuCode(resultLog.getSupplierSkuCode());
            resultMsg.setEffDate(resultLog.getEffDate());
            resultMsg.setMdPrice(resultLog.getMdPrice());
            rtnData.add(resultMsg);
        }
        return rtnData;
    }

    /**
     * 将特定格式字符串(如:<enum type="string"><item>29</item><item>30</item></enum>) 解析为具体元素的集合
     * 
     * @param originValue
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static List<String> getListResolvedValue(String originValue) throws Exception {
        try {
            Document documentCon = DocumentHelper.parseText(originValue.trim());
            Element rootElement = documentCon.getRootElement();
            List<String> result = new ArrayList<String>();
            List<Element> itemElements = rootElement.elements("item");
            for (Element e : itemElements) {
                String item = e.getText();
                if (item != null) result.add(item.trim());
            }
            return result;
        } catch (DocumentException e) {
            log.error("", e);
            throw new Exception("颜色或尺码值域解析出错");
        }
    }

    /**
     * 将集合元素转换为指定格式字符串 如: <enum type="string"><item>29</item><item>30</item></enum>
     * 
     * @param values
     * @return
     */
    public static String getListResolvedValue(List<String> values) {
        StringBuffer rtn = new StringBuffer();
        rtn.append("<enum type=\"string\">");
        for (String value : values) {
            rtn.append("<item>").append(value.trim()).append("</item>");
        }
        rtn.append("</enum>");
        return rtn.toString();
    }

    /**
     * 将指定消息发送到Mq对应队列中
     * 
     * @param destination Mq队列名
     * @param msgData mq消息对象
     * @param jmsTemplate jms消息模板
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void sendMsgToMq(JmsTemplate jmsTemplate, String destination, List msgData, Long msgBatchId) {
        MqMessageProducerUtil producer = new MqMessageProducerUtil();
        producer.setTemplate(jmsTemplate);
        producer.setDestination(destination);
        MqMsgList msg = new MqMsgList();
        msg.setMsgs(msgData);
        msg.setBatchNo(msgBatchId);
        String msgStr = MarshallerUtil.buildJaxb(msg);
        // System.out.println("-------------"+msgStr);
        String encodeMsg = MarshallerUtil.encodeBase64StringWithUTF8(msgStr);
        // System.out.println("----encodeMsg--"+encodeMsg);
        // System.out.println("------decode-------"+MarshallerUtil.decodeBase64StringWithUTF8(encodeMsg));
        producer.send(encodeMsg);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void sendInvoiceMsgToMq(JmsTemplate jmsTemplate, String destination, List msgData, Long msgBatchId) {
        MqMessageProducerUtil producer = new MqMessageProducerUtil();
        producer.setTemplate(jmsTemplate);
        producer.setDestination(destination);
        FlowInvoiceMsgBatch msg = new FlowInvoiceMsgBatch();
        msg.setMsgs(msgData);
        msg.setBatchNo(msgBatchId);
        String msgStr = MarshallerUtil.buildJaxb(msg);
        String encodeMsg = MarshallerUtil.encodeBase64StringWithUTF8(msgStr);
        producer.send(encodeMsg);
    }



    public static GoodsIssue.Header constructMqPhilipsGoodsIuuse(PhilipsGoodsIssue pGoodsIssue) {
        GoodsIssue.Header goodsIssueHeader = new GoodsIssue.Header();
        goodsIssueHeader.setGoodsIssueOrderCode(pGoodsIssue.getOrderCode());
        goodsIssueHeader.setGoodsIssueLogisticCode(pGoodsIssue.getLogisticCode());
        goodsIssueHeader.setGoodsIssueTrackingNumber(pGoodsIssue.getTrackingNo());
        goodsIssueHeader.setGoodsIssueDespatchTime(FormatUtil.formatDate(pGoodsIssue.getDespatchTime(), "yyyy-MM-dd HH:mm:ss"));
        return goodsIssueHeader;
    }

    public static List<GoodsIssue.Line> constructMqPhilipsGoodsIuuseLine(List<PhilipsGoodsIssueLine> lines) {
        List<GoodsIssue.Line> goodsIssueLines = new ArrayList<GoodsIssue.Line>();
        for (PhilipsGoodsIssueLine pLine : lines) {
            GoodsIssue.Line goodsIssueLine = new GoodsIssue.Line();
            goodsIssueLine.setGoodsIssueLineAcceptedQuantity(pLine.getAcceptedQty().toString());
            goodsIssueLine.setGoodsIssueLineBarcode(pLine.getBarcode());
            goodsIssueLine.setGoodsIssueLineDespatchQuantity(pLine.getDepatchedQty().toString());
            goodsIssueLine.setGoodsIssueLineGrossVolume(pLine.getGrossVolume().toString());
            goodsIssueLine.setGoodsIssueLineNumber(pLine.getLineNumber());
            goodsIssueLine.setGoodsIssueLineReceivedQuantity(pLine.getReceivedQty().toString());
            goodsIssueLine.setGoodsIssueLineSkuCode(pLine.getSkuCode());
            goodsIssueLine.setGoodsIssueLineTotalGrossWeight(pLine.getTotalGrossWeight().toString());
            goodsIssueLine.setGoodsIssueLineUnitNetWeight(pLine.getUnitNetWeight().toString());
            goodsIssueLines.add(goodsIssueLine);
        }
        return goodsIssueLines;
    }

    public static GoodsReceipt.Header constructMqPhilipsGoodsReceipt(PhilipsGoodsReceipt pGoodsReceipt) {
        GoodsReceipt.Header grhHeader = new GoodsReceipt.Header();
        grhHeader.setGoodsReceiptCode(pGoodsReceipt.getReceiptCode());
        grhHeader.setGoodsReceiptReceiverDatetime(FormatUtil.formatDate(pGoodsReceipt.getReceiveTime(), "yyyy-MM-dd HH:mm:ss"));
        grhHeader.setGoodsReceiptDeliveryNumber(pGoodsReceipt.getDeliveryNo());
        grhHeader.setGoodsReceiptInboundCode(pGoodsReceipt.getInboundCode());
        return grhHeader;
    }

    public static List<GoodsReceipt.Line> constructMqPhilipsGoodsReceiptLine(List<PhilipsGoodsReceiptLine> lines) {
        List<GoodsReceipt.Line> grlLines = new ArrayList<GoodsReceipt.Line>();
        for (PhilipsGoodsReceiptLine line : lines) {
            GoodsReceipt.Line grlLine = new GoodsReceipt.Line();
            grlLine.setGoodsReceiptLineNumber(line.getLineNumber());
            grlLine.setGoodsReceiptSkuCode(line.getSkuCode());
            grlLine.setGoodsReceiptPoCode(line.getPoCode());
            grlLine.setGoodsReceiptSkuBarcode(line.getBarcode());
            grlLine.setGoodsReceiptPlanQuantity(line.getPlanQuantity().toString());
            grlLine.setGoodsReceiptReceiverdQuantity(line.getReceivedQty().toString());
            grlLines.add(grlLine);
        }
        return grlLines;
    }

    public static GoodsReceiptCustomerReturn.Header constructMqPhilipsGoodsReceiptCusReturn(PhilipsCustomerReturnReceipt pCustomerReturnReceipt) {
        GoodsReceiptCustomerReturn.Header grcrHeader = new GoodsReceiptCustomerReturn.Header();
        grcrHeader.setGoodsReceiptCustomerReturnOrderCode(pCustomerReturnReceipt.getOrderCode());
        grcrHeader.setGoodsReceiptCustomerReturnReceivedDatetime(FormatUtil.formatDate(pCustomerReturnReceipt.getReceivedTime(), "yyyy-MM-dd HH:mm:ss"));
        return grcrHeader;
    }

    public static List<GoodsReceiptCustomerReturn.Line> constructMqPhilipsGoodsReceiptCusRLine(List<PhilipsCustomerReturnReceiptLine> lines) {
        List<GoodsReceiptCustomerReturn.Line> grcrLines = new ArrayList<GoodsReceiptCustomerReturn.Line>();
        for (PhilipsCustomerReturnReceiptLine line : lines) {
            GoodsReceiptCustomerReturn.Line grcrLine = new GoodsReceiptCustomerReturn.Line();
            grcrLine.setGoodsReceiptCustomerReturnLineNumber(line.getLineNumber());
            grcrLine.setGoodsReceiptCustomerReturnLineSkuCode(line.getSkuCode());
            grcrLine.setGoodsReceiptCustomerReturnLineBarcode(line.getBarcode());
            grcrLine.setGoodsReceiptCustomerReturnLineAcceptedQuantity(line.getAcceptedQty().toString());
            grcrLine.setGoodsReceiptCustomerReturnLineReceivedQuantity(line.getReceivedQty().toString());
            grcrLines.add(grcrLine);
        }
        return grcrLines;
    }

    public static GoodsMovement.Header constructMqPhilipsGoodsMovement(PhilipsGoodsMovement pGoodsMovement) {
        GoodsMovement.Header gmhHeader = new GoodsMovement.Header();
        gmhHeader.setGoodsMovementCode(pGoodsMovement.getMovementCode());
        gmhHeader.setGoodsMovementHeaderTime(FormatUtil.formatDate(pGoodsMovement.getHeaderTime(), "yyyy-MM-dd HH:mm:ss"));
        return gmhHeader;
    }

    public static List<GoodsMovement.Line> constructMqPhilipsGoodsMovementLine(List<PhilipsGoodsMovementLine> lines) {
        List<GoodsMovement.Line> gmLines = new ArrayList<GoodsMovement.Line>();
        for (PhilipsGoodsMovementLine line : lines) {
            GoodsMovement.Line gmlLine = new GoodsMovement.Line();
            gmlLine.setGoodsMovementLineSkuCode(line.getSkuCode());
            gmlLine.setGoodsMovementLineBarcode(line.getBarcode());
            gmlLine.setGoodsMovementLineFromLocation(line.getFromLocation());
            gmlLine.setGoodsMovementLineMovementType(line.getMovementType());
            gmlLine.setGoodsMovementLineNumber(line.getLineNumber());
            gmlLine.setGoodsMovementLineQuantity(line.getQuantity().toString());
            gmlLine.setGoodsMovementLineTime(FormatUtil.formatDate(line.getLineTime(), "yyyy-MM-dd HH:mm:ss"));
            gmlLine.setGoodsMovementLineToLocation(line.getToLocation());
            gmLines.add(gmlLine);
        }
        return gmLines;
    }

    public static StockComparison.Header constructMqPhilipsStockComparison(PhilipsStockComparison pStockComparison) {
        StockComparison.Header scHeader = new StockComparison.Header();
        scHeader.setStockComparisonTransCode(pStockComparison.getTransCode());
        scHeader.setStockComparisonTransTime(FormatUtil.formatDate(pStockComparison.getTransTime(), "yyyy-MM-dd HH:mm:ss"));
        return scHeader;
    }

    public static List<StockComparison.Line> constructMqPhilipsStockComparison(List<PhilipsStockComparisonLine> lines) {
        List<StockComparison.Line> scLines = new ArrayList<StockComparison.Line>();
        for (PhilipsStockComparisonLine line : lines) {
            StockComparison.Line scLine = new StockComparison.Line();
            scLine.setStockComparisonLineSkuCode(line.getSkuCode());
            scLine.setStockComparisonLineBarcode(line.getBarcode());
            scLine.setStockComparisonLineNumber(line.getLineNumber());
            scLine.setStockComparisonLineQuantity(line.getQuantity().toString());
            scLine.setStockComparisonLineStatusType(line.getStatusType());
            scLine.setStockComparisonLineLocation(line.getLocation());
            scLines.add(scLine);
        }
        return scLines;
    }

    public static void sendStringMsgToMq(JmsTemplate jmsTemplate, String destination, String msgData) {
        MqMessageProducerUtil producer = new MqMessageProducerUtil();
        producer.setTemplate(jmsTemplate);
        producer.setDestination(destination);
        producer.send(msgData);
    }




    @SuppressWarnings("static-access")
    public static DSO constructMqGodivaSalesOrder(MsgOutboundOrderCommand order, List<MsgOutboundOrderLineCommand> lines,List<StaInvoice> staInvoices) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DSO dso = new DSO();
        DSOInformation dsoinfo = new DSOInformation();
        dsoinfo.setStorerIdentifier(order.getSource());
        dsoinfo.setDate(sdf.format(new Date()));
        dsoinfo.setFileType("DSO");

        DSOHeader header = new DSOHeader();
        header.setActionType("A");
        String period = "";
        JSONObject json = null;
        GeneralInformation general = new GeneralInformation();
        if (StringUtils.hasText(order.getReleaseDate())) {
            json = JSONObject.fromObject(order.getReleaseDate());
            String psDate = json.getString("psDate");
            period = json.getString("period");
            general.setReleaseDate(psDate);
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(order.getCreateTime());
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天
            general.setReleaseDate(sdf.format(calendar.getTime()));
        }

        general.setUrgentOrder("N");
        if (!("1").equals(order.getPaymentType())) {
            general.setCashOnDelivery("N");
            general.setCashOnDeliveryAmount("0");
        } else {
            general.setCashOnDelivery("Y");
            BigDecimal totalActual = order.getTotalActual().add(order.getTransferFee());// 含运费的总金额
            general.setCashOnDeliveryAmount(totalActual.toString());
        }


        header.setGeneralInformation(general);


        ReferenceInformation ref = new ReferenceInformation();

        ReferenceOrder reforder = new ReferenceOrder();
        reforder.setReferenceOrderNumber(order.getSlipCode());
        reforder.setReferenceOrderType("DeliveryRefence");
        ref.getReferenceOrder().add(reforder);


        ReferenceOrder reforder3 = new ReferenceOrder();
        reforder3.setReferenceOrderType("InvoiceNumber");
        if (!("1").equals(order.getPaymentType())) {
            reforder3.setReferenceOrderNumber("0");
        } else {
            if (order.getTransferFee() != null) {
                reforder3.setReferenceOrderNumber(order.getTransferFee().toString());
            } else {
                reforder3.setReferenceOrderNumber("0");
            }

        }
        ref.getReferenceOrder().add(reforder3);

        for(StaInvoice staInvoice:staInvoices){
       //新增发票模块
       //发票抬头
        UserDefinedLongItem item4 = new UserDefinedLongItem();
   
        item4.setUserDefinedLongItemValue(staInvoice.getPayer());
        item4.setUserDefinedLongItemIndex(4);
        ref.getUserDefinedLongItem().add(item4);
        //发票内容
        
        UserDefinedLongItem item5 = new UserDefinedLongItem();
        
        item5.setUserDefinedLongItemValue("食品");
        item5.setUserDefinedLongItemIndex(5);
        ref.getUserDefinedLongItem().add(item5);
        //发票总金额
       UserDefinedLongItem item6 = new UserDefinedLongItem();
       
        item6.setUserDefinedLongItemValue(staInvoice.getAmt().add(order.getTransferFee()).toString());
        item6.setUserDefinedLongItemIndex(6);
        ref.getUserDefinedLongItem().add(item6);
        }
        
        UserDefinedLongItem item1 = new UserDefinedLongItem();
        item1.setUserDefinedLongItemValue(period);
        item1.setUserDefinedLongItemIndex(7);
        ref.getUserDefinedLongItem().add(item1);

        if (("1").equals(order.getPaymentType())) {
            UserDefinedLongItem item2 = new UserDefinedLongItem();
            item2.setUserDefinedLongItemValue("Cash Payment");
            item2.setUserDefinedLongItemIndex(8);

            ref.getUserDefinedLongItem().add(item2);
        }


        header.setReferenceInformation(ref);

        DeliveryInformation delivery = new DeliveryInformation();
        delivery.setConsigneeOrShipToType("ShipTo");
        delivery.setCompany("");// 收货公司
        delivery.setAddressLine1(order.getReserve1() == null ? "" : order.getReserve1());
        delivery.setAddressLine2(order.getReserve2() == null ? "" : order.getReserve2());
        delivery.setAddressLine3(order.getReserve3() == null ? "" : order.getReserve3());
        delivery.setAddressLine4(order.getCity() == null ? "" : order.getCity());
        delivery.setAddressLine5(order.getDistrict() == null ? "" : order.getDistrict());
        delivery.setZip(order.getZipcode());
        delivery.setContactName(order.getReceiver());
        if (order.getMobile() == null) {
            delivery.setContactPhone(order.getTelePhone());
        } else {
            delivery.setContactPhone(order.getMobile());
        }

        delivery.setRouting("KEAS");

        header.setDeliveryInformation(delivery);



        List<DSODetail> detailLine = new ArrayList<DSODetail>();
        
        List<MsgOutboundOrderLineCommand> detailLinesList = new ArrayList<MsgOutboundOrderLineCommand>();
        
        Map<String, MsgOutboundOrderLineCommand> detailLineMap = new HashMap<String, MsgOutboundOrderLineCommand>();
        
        /**
         * 特殊包装情况下合并同商品code的明细行
         */
        for (MsgOutboundOrderLineCommand line : lines) {
        	String barcode = line.getBarCode();
        	if(detailLineMap.get(barcode)!=null){
        		MsgOutboundOrderLineCommand detail = detailLineMap.get(barcode);
        		detailLineMap.get(barcode).setQty(detail.getQty() + line.getQty());
        		detailLineMap.get(barcode).setTotalActual(detail.getTotalActual().add(line.getTotalActual()));
        	}else{
        		detailLineMap.put(barcode, line);
        	}
        }
        
        detailLinesList.addAll(detailLineMap.values());
        
        for (MsgOutboundOrderLineCommand line : detailLinesList) {
            // String lineReserve1="",lineReserve2="",lineReserve3="",wishComment="";

            // 解析丝带，配饰信息
            if (StringUtils.hasText(line.getLineReserve1())) {
                if (line.getLineReserve1().length() > 0) {
                    if (line.getLineReserve1().indexOf("\r") != -1) {
                        line.setLineReserve1(line.getLineReserve1().replaceAll("\r", "\n"));
                    }
                }
                // json处理
                json = JSONObject.fromObject(line.getLineReserve1().replace("\n", " "));
                JSONArray arry = null;
                try {
                    arry = json.getJSONArray("skus");
                } catch (Exception e) {
                    log.error("", e);
                }
                if (arry == null) {
                    log.error("arry is null");
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                Object[] skus = arry.toArray();
                int qty = skus.length;
                // 如果qty=2 说明同样该商品应该有两件，此行要拆开分别对应不同的配饰信息
                for (int i = 1; i <= qty; i++) {

                    JSONObject js = (JSONObject) skus[i - 1];
                    if (js == null) {
                        continue;
                    }
                    String wishCard = (String) js.get("wishCard");
                    String lineReserve3 = wishCard;

                    if (line.getBarCode().equals(wishCard)) {
                        continue;
                    }
                    String lineReserve1 = (String) js.get("sidaiCode");
                    String lineReserve2 = (String) js.get("peishiCode");
                    String wishComment = (String) js.get("wishComment");
                    if (wishComment != null) {
                        if (wishComment.length() > 100) {
                            wishComment = wishComment.substring(0, 100);
                        }
                    } else {
                        wishComment = "";
                    }

                    DSODetail detail = new DSODetail();
                    FreightInformation information = new FreightInformation();
                    information.setSKU(line.getBarCode());
                    
                    if (qty == 2 && 2 == line.getQty()) {
                        information.setReleaseQuantity(String.valueOf(line.getQty() - 1));
                    } else {
                        information.setReleaseQuantity(String.valueOf(line.getQty()));
                    }

                    PickingCriteria pick = new PickingCriteria();
                    pick.setPickBy1("F");
                    /*
                     * if(order.getShopId().equals(Constants.GDV_BS_SHOP_ID)&&line.getLineReserve1()==
                     * null||StringUtils.hasText(line.getLineReserve1())&&line.getBarCode().equals(
                     * lineReserve3)){ continue; }
                     */

                    Remarks remarks = new Remarks();
                    if (order.getRemark() != null && !order.getRemark().equals("")) {
                        if (order.getRemark().length() > 20) {
                            remarks.setShortRemark1(order.getRemark().substring(0, 20));
                            remarks.setShortRemark2(order.getRemark().substring(20, order.getRemark().length()));
                        } else {
                            remarks.setShortRemark1(order.getRemark());
                        }
                    } else {
                        remarks.setShortRemark1(order.getRemark());
                    }
                    remarks.setLongRemark(wishComment);
                    header.setRemarks(remarks);

                    cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation refdetail = new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation();
                    if (StringUtils.hasText(lineReserve1)) {

                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem1 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem1.setUserDefinedLongItemIndex(20);
                        detailitem1.setUserDefinedLongItemValue(lineReserve1);
                        refdetail.getUserDefinedLongItem().add(detailitem1);
                    }
                    if (StringUtils.hasText(lineReserve2)) {
                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem2 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem2.setUserDefinedLongItemIndex(21);
                        detailitem2.setUserDefinedLongItemValue(lineReserve2);
                        refdetail.getUserDefinedLongItem().add(detailitem2);

                    }
                    if (StringUtils.hasText(lineReserve3)) {
                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem3 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem3.setUserDefinedLongItemIndex(22);
                        detailitem3.setUserDefinedLongItemValue(lineReserve3);
                        refdetail.getUserDefinedLongItem().add(detailitem3);
                    }

                    cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem4 =
                            new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                    detailitem4.setUserDefinedLongItemIndex(23);
                    if (("1").equals(order.getPaymentType())) {
                        if (qty == 2 && 2 == line.getQty()) {
                            detailitem4.setUserDefinedLongItemValue((line.getTotalActual().divide(new BigDecimal("2")).toString()));
                        } else {
                            detailitem4.setUserDefinedLongItemValue(line.getTotalActual().toString());
                        }
                    } else {
                        detailitem4.setUserDefinedLongItemValue("0");
                    }

                    refdetail.getUserDefinedLongItem().add(detailitem4);
                    detail.setReferenceInformation(refdetail);

                    detail.setFreightInformation(information);
                    detail.setPickingCriteria(pick);

                    detailLine.add(detail);


                }
            } else {

                DSODetail detail = new DSODetail();
                FreightInformation information = new FreightInformation();
                information.setSKU(line.getBarCode());
                information.setReleaseQuantity(line.getQty().toString());

                PickingCriteria pick = new PickingCriteria();
                pick.setPickBy1("F");
                /*
                 * if(order.getShopId().equals(Constants.GDV_BS_SHOP_ID)&&line.getLineReserve1()==null
                 * ){ continue; }
                 */

                if (header.getRemarks() == null) {
                    Remarks remarks = new Remarks();
                    if (order.getRemark() != null && !order.getRemark().equals("")) {
                        if (order.getRemark().length() > 20) {
                            remarks.setShortRemark1(order.getRemark().substring(0, 20));
                            remarks.setShortRemark2(order.getRemark().substring(20, order.getRemark().length()));
                        } else {
                            remarks.setShortRemark1(order.getRemark());
                        }
                    } else {
                        remarks.setShortRemark1(order.getRemark());
                    }
                    remarks.setLongRemark("");
                    header.setRemarks(remarks);
                }
                cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation refdetail = new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation();
                cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem4 =
                        new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                detailitem4.setUserDefinedLongItemIndex(23);
                if (("1").equals(order.getPaymentType())) {
                    detailitem4.setUserDefinedLongItemValue(line.getTotalActual().toString());
                } else {
                    detailitem4.setUserDefinedLongItemValue("0");
                }

                refdetail.getUserDefinedLongItem().add(detailitem4);
                detail.setReferenceInformation(refdetail);

                detail.setFreightInformation(information);
                detail.setPickingCriteria(pick);
                detailLine.add(detail);
            }
        }
        header.getDSODetail().addAll(detailLine);
        dsoinfo.setDSOHeader(header);
        dso.setDSOInformation(dsoinfo);
        return dso;
    }

    @SuppressWarnings("static-access")
    public static DSO constructMqGodivaOrder(VMIOrderCommand order, List<VMIOrderLine> lines) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DSO dso = new DSO();
        DSOInformation dsoinfo = new DSOInformation();
        dsoinfo.setStorerIdentifier("GODIVASH");
        dsoinfo.setDate(sdf.format(new Date()));
        dsoinfo.setFileType("DSO");

        DSOHeader header = new DSOHeader();
        header.setActionType(order.getOrderType());
        String period = "";
        JSONObject json = null;

        GeneralInformation general = new GeneralInformation();
        if (StringUtils.hasText(order.getReleaseDate())) {
            json = JSONObject.fromObject(order.getReleaseDate());
            String psDate = json.getString("psDate");
            period = json.getString("period");
            general.setReleaseDate(psDate);
        } else {
            // 天猫的订单没有releaseDate 值，设定该值在订单创建日期上加一天
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(order.getCreateTime());
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天
            general.setReleaseDate(sdf.format(calendar.getTime()));
        }

        general.setUrgentOrder("N");
        if (!("1").equals(order.getPaymentType())) {
            general.setCashOnDelivery("N");
            general.setCashOnDeliveryAmount("0");
        } else {
            general.setCashOnDelivery("Y");
            general.setCashOnDeliveryAmount(order.getTotal().toString());
        }


        header.setGeneralInformation(general);


        ReferenceInformation ref = new ReferenceInformation();

        ReferenceOrder reforder = new ReferenceOrder();
        reforder.setReferenceOrderNumber(order.getCode());
        reforder.setReferenceOrderType("DeliveryRefence");
        ref.getReferenceOrder().add(reforder);


        ReferenceOrder reforder3 = new ReferenceOrder();
        reforder3.setReferenceOrderType("InvoiceNumber");
        if (order.getLogisticFee() != null) {
            reforder3.setReferenceOrderNumber(order.getLogisticFee().toString());
        }
        ref.getReferenceOrder().add(reforder3);

        UserDefinedLongItem item1 = new UserDefinedLongItem();
        item1.setUserDefinedLongItemValue(period);
        item1.setUserDefinedLongItemIndex(7);
        ref.getUserDefinedLongItem().add(item1);

        if (("1").equals(order.getPaymentType())) {
            UserDefinedLongItem item2 = new UserDefinedLongItem();
            item2.setUserDefinedLongItemValue("Cash Payment");
            item2.setUserDefinedLongItemIndex(8);

            ref.getUserDefinedLongItem().add(item2);
        }


        header.setReferenceInformation(ref);

        DeliveryInformation delivery = new DeliveryInformation();
        delivery.setConsigneeOrShipToType("ShipTo");
        delivery.setCompany("");// 收货公司
        delivery.setAddressLine1(order.getReserve1() == null ? "" : order.getReserve1());
        delivery.setAddressLine2(order.getReserve2() == null ? "" : order.getReserve2());
        delivery.setAddressLine3(order.getReserve3() == null ? "" : order.getReserve3());
        delivery.setAddressLine4(order.getCity() == null ? "" : order.getCity());
        delivery.setAddressLine5(order.getDistrict() == null ? "" : order.getDistrict());
        delivery.setZip(order.getZipCode());
        delivery.setContactName(order.getReceiver());
        if (order.getMobile() == null) {
            delivery.setContactPhone(order.getTelephone());
        } else {
            delivery.setContactPhone(order.getMobile());
        }

        delivery.setRouting("KEAS");

        header.setDeliveryInformation(delivery);
        List<DSODetail> detailLine = new ArrayList<DSODetail>();
        for (VMIOrderLine line : lines) {
            // 解析丝带，配饰信息
            if (StringUtils.hasText(line.getLineReserve1())) {
                // json处理
                json = JSONObject.fromObject(line.getLineReserve1().replace("\n", " "));
                JSONArray arry = null;
                try {
                    arry = json.getJSONArray("skus");
                } catch (Exception e) {
                    log.error("", e);
                }
                if (arry == null) {
                    log.error("arry is null");
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                Object[] skus = arry.toArray();
                int qty = skus.length;
                // 如果qty=2 说明同样该商品应该有两件，此行要拆开分别对应不同的配饰信息
                for (int i = 1; i <= qty; i++) {

                    JSONObject js = (JSONObject) skus[i - 1];
                    if (js == null) {
                        continue;
                    }
                    String wishCard = (String) js.get("wishCard");
                    String lineReserve3 = wishCard;
                    if (line.getBarCode().equals(wishCard)) {
                        continue;
                    }
                    String lineReserve1 = (String) js.get("sidaiCode");
                    String lineReserve2 = (String) js.get("peishiCode");
                    String wishComment = (String) js.get("wishComment");

                    if (wishComment.length() > 100) {
                        wishComment = wishComment.substring(0, 100);
                    }

                    DSODetail detail = new DSODetail();
                    FreightInformation information = new FreightInformation();
                    information.setSKU(line.getBarCode());
                    if (qty == 2 && 2 == line.getQuantity()) {
                        information.setReleaseQuantity(String.valueOf(line.getQuantity() - 1));
                    } else {
                        information.setReleaseQuantity(String.valueOf(line.getQuantity()));
                    }

                    PickingCriteria pick = new PickingCriteria();
                    pick.setPickBy1("F");
                    /*
                     * if(order.getShopId().equals(Constants.GDV_BS_SHOP_ID)&&line.getLineReserve1()==
                     * null||StringUtils.hasText(line.getLineReserve1())&&line.getBarCode().equals(
                     * lineReserve3)){ continue; }
                     */

                    Remarks remarks = new Remarks();
                    if (order.getRemark() != null && !order.getRemark().equals("")) {
                        if (order.getRemark().length() > 20) {
                            remarks.setShortRemark1(order.getRemark().substring(0, 20));
                            remarks.setShortRemark2(order.getRemark().substring(20, order.getRemark().length()));
                        } else {
                            remarks.setShortRemark1(order.getRemark());
                        }
                    } else {
                        remarks.setShortRemark1(order.getRemark());
                    }
                    remarks.setLongRemark(wishComment);
                    header.setRemarks(remarks);

                    cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation refdetail = new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation();
                    if (StringUtils.hasText(lineReserve1)) {

                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem1 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem1.setUserDefinedLongItemIndex(20);
                        detailitem1.setUserDefinedLongItemValue(lineReserve1);
                        refdetail.getUserDefinedLongItem().add(detailitem1);
                    }
                    if (StringUtils.hasText(lineReserve2)) {
                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem2 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem2.setUserDefinedLongItemIndex(21);
                        detailitem2.setUserDefinedLongItemValue(lineReserve2);
                        refdetail.getUserDefinedLongItem().add(detailitem2);

                    }
                    if (StringUtils.hasText(lineReserve3)) {
                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem3 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem3.setUserDefinedLongItemIndex(22);
                        detailitem3.setUserDefinedLongItemValue(lineReserve3);
                        refdetail.getUserDefinedLongItem().add(detailitem3);
                    }

                    cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem4 =
                            new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                    detailitem4.setUserDefinedLongItemIndex(23);
                    if (("1").equals(order.getPaymentType())) {
                        if (qty == 2 && 2 == line.getQuantity()) {
                            detailitem4.setUserDefinedLongItemValue((line.getTotal().divide(new BigDecimal("2")).toString()));
                        } else {
                            detailitem4.setUserDefinedLongItemValue(line.getTotal().toString());
                        }
                    } else {
                        detailitem4.setUserDefinedLongItemValue("0");
                    }

                    refdetail.getUserDefinedLongItem().add(detailitem4);
                    detail.setReferenceInformation(refdetail);

                    detail.setFreightInformation(information);
                    detail.setPickingCriteria(pick);

                    detailLine.add(detail);


                }
            } else {

                DSODetail detail = new DSODetail();
                FreightInformation information = new FreightInformation();
                information.setSKU(line.getBarCode());
                information.setReleaseQuantity(line.getQuantity().toString());

                PickingCriteria pick = new PickingCriteria();
                pick.setPickBy1("F");
                /*
                 * if(order.getShopId().equals(Constants.GDV_BS_SHOP_ID)&&line.getLineReserve1()==null
                 * ){ continue; }
                 */

                if (header.getRemarks() == null) {
                    Remarks remarks = new Remarks();
                    if (order.getRemark() != null && !order.getRemark().equals("")) {
                        if (order.getRemark().length() > 20) {
                            remarks.setShortRemark1(order.getRemark().substring(0, 20));
                            remarks.setShortRemark2(order.getRemark().substring(20, order.getRemark().length()));
                        } else {
                            remarks.setShortRemark1(order.getRemark());
                        }
                    } else {
                        remarks.setShortRemark1(order.getRemark());
                    }
                    remarks.setLongRemark("");
                    header.setRemarks(remarks);
                }



                cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation refdetail = new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation();


                cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem4 =
                        new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                detailitem4.setUserDefinedLongItemIndex(23);
                if (("1").equals(order.getPaymentType())) {
                    detailitem4.setUserDefinedLongItemValue(line.getTotal().toString());
                } else {
                    detailitem4.setUserDefinedLongItemValue("0");
                }

                refdetail.getUserDefinedLongItem().add(detailitem4);
                detail.setReferenceInformation(refdetail);

                detail.setFreightInformation(information);
                detail.setPickingCriteria(pick);
                detailLine.add(detail);
            }
        }
        header.getDSODetail().addAll(detailLine);
        dsoinfo.setDSOHeader(header);
        dso.setDSOInformation(dsoinfo);
        return dso;
    }

}
