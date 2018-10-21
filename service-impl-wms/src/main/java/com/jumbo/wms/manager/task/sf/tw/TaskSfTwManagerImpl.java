/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
 */
package com.jumbo.wms.manager.task.sf.tw;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.utils.DateUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransLpcodeWhRefDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundContainerLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.vmi.warehouse.MsgSKUSyncDao;
import com.jumbo.dao.vmi.warehouse.RtnSnDetailDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.LogisticsTrackingDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.sf.tw.SfTwOrderClient;
import com.jumbo.webservice.sf.tw.inbound.command.InboundPurchaseOrderRequest;
import com.jumbo.webservice.sf.tw.inbound.command.InboundReqBody;
import com.jumbo.webservice.sf.tw.inbound.command.InboundReqHead;
import com.jumbo.webservice.sf.tw.inbound.command.InboundReqItem;
import com.jumbo.webservice.sf.tw.inbound.command.InboundReqItems;
import com.jumbo.webservice.sf.tw.inbound.command.InboundReqPurchaseOrder;
import com.jumbo.webservice.sf.tw.inbound.command.InboundReqPurchaseOrders;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRequest;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRespPurchaseOrder;
import com.jumbo.webservice.sf.tw.inbound.command.InboundResponse;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnError;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnErrorResponse;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnPurchaseOrderInboundResponse;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnReqItem;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnReqItems;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnReqPurchaseOrder;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnReqSerialNumbers;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnRequest;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnRespBody;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRtnResponse;
import com.jumbo.webservice.sf.tw.inventory.command.InventoryChangeRequest;
import com.jumbo.webservice.sf.tw.inventory.command.InventoryChangeResponse;
import com.jumbo.webservice.sf.tw.inventory.command.InventoryReqChange;
import com.jumbo.webservice.sf.tw.inventory.command.InventoryRequest;
import com.jumbo.webservice.sf.tw.inventory.command.InventoryRespBody;
import com.jumbo.webservice.sf.tw.inventory.command.InventoryResponse;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqBody;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqCarrierAddedService;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqCarrierAddedServices;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqCustomsDeclarationInfo;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqHead;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqOrderAddedService;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqOrderAddedServices;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqOrderCarrier;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqOrderInvoice;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqOrderItem;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqOrderItems;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqOrderReceiverInfo;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqOrderSenderInfo;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqSaleOrder;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundReqSaleOrders;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRequest;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRespSaleOrder;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundResponse;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnError;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnErrorResponse;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnReqContainer;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnReqItem;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnReqItems;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnReqSaleOrder;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnReqSerialNumbers;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnRequest;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnRespBody;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnResponse;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRtnSaleOrderOutboundDetailResponse;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundSaleOrderRequest;
import com.jumbo.webservice.sf.tw.sku.command.Container;
import com.jumbo.webservice.sf.tw.sku.command.Containers;
import com.jumbo.webservice.sf.tw.sku.command.SkuItemRequest;
import com.jumbo.webservice.sf.tw.sku.command.SkuReqBarCode;
import com.jumbo.webservice.sf.tw.sku.command.SkuReqBody;
import com.jumbo.webservice.sf.tw.sku.command.SkuReqHead;
import com.jumbo.webservice.sf.tw.sku.command.SkuReqItem;
import com.jumbo.webservice.sf.tw.sku.command.SkuReqItemCategory;
import com.jumbo.webservice.sf.tw.sku.command.SkuReqItems;
import com.jumbo.webservice.sf.tw.sku.command.SkuRequest;
import com.jumbo.webservice.sf.tw.sku.command.SkuRespItem;
import com.jumbo.webservice.sf.tw.sku.command.SkuResponse;
import com.jumbo.webservice.sf.tw.vendor.command.ReqVendor;
import com.jumbo.webservice.sf.tw.vendor.command.ReqVendors;
import com.jumbo.webservice.sf.tw.vendor.command.VendorRequest;
import com.jumbo.webservice.sf.tw.vendor.command.VendorsReqBody;
import com.jumbo.webservice.sf.tw.vendor.command.VendorsReqHead;
import com.jumbo.webservice.sf.tw.vendor.command.VendorsRequest;
import com.jumbo.webservice.sf.tw.vendor.command.VendorsResponse;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.TransLpcodeWhRef;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustmentLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundContainerLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;
import com.jumbo.wms.model.vmi.warehouse.RtnSnDetail;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.LogisticsTracking;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.taobao.api.internal.util.codec.Base64;

/**
 * 
 * @author lichuan
 * 
 */
@Transactional
@Service("taskSfTwManager")
public class TaskSfTwManagerImpl extends BaseManagerImpl implements TaskSfTwManager {
    /**
     * 顺丰台湾
     */
    private static final long serialVersionUID = -4434491073407634342L;
    @Autowired
    private MsgSKUSyncDao msgSKUSyncDao;
    @Autowired
    private MsgInboundOrderLineDao msgInboundOrderLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundOrderLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private RtnSnDetailDao rtnSnDetailDao;
    @Autowired
    private LogisticsTrackingDao logisticsTrackingDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutboundLineDao;
    @Autowired
    private MsgRtnOutboundContainerLineDao msgRtnOutboundContainerLineDao;
    @Autowired
    private MsgRtnAdjustmentDao msgRtnAdjustmentDao;
    @Autowired
    private MsgRtnAdjustmentLineDao msgRtnAdjustmentLineDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private TransLpcodeWhRefDao transLpcodeWhRefDao;


    /**
     * 同步商品到台湾顺丰
     */
    @Override
    public void sendSkuItemsService(List<MsgSKUSync> skus, String sfFlag) throws Exception {
        List<Long> skuIds = new ArrayList<Long>();
        List<Long> successIds = new ArrayList<Long>();
        List<Long> errorsIds = new ArrayList<Long>();
        SkuRequest req = new SkuRequest();
        SkuReqHead head = new SkuReqHead();
        head.setAccessCode(SF_TW_ACCESS_CODE);// 接入编码
        head.setCheckword(SF_TW_CHECKWORD);// 校验码
        SkuReqBody body = new SkuReqBody();
        SkuItemRequest skuReq = new SkuItemRequest();
        skuReq.setCompanyCode(sfFlag);// 货主代码
        SkuReqItems items = new SkuReqItems();
        for (MsgSKUSync s : skus) {
            SkuReqItem item = new SkuReqItem();
            item.setSkuNo(s.getSkuCode());
            item.setItemName(s.getSkuName());
            item.setDescription(s.getSkuName());
            item.setTransportProperty("");
            item.setStorageTemperature(1);
            item.setTransportTemperature(1);
            item.setSerialNumTrackInbound(s.getIsSn() == null || !s.getIsSn() ? "N" : "Y");
            item.setSerialNumTrackOutbound(s.getIsSn() == null || !s.getIsSn() ? "N" : "Y");
            SkuReqBarCode barcode = new SkuReqBarCode();
            barcode.setBarCode1(s.getBarCode());
            item.setBarCode(barcode);
            SkuReqItemCategory cate = new SkuReqItemCategory();
            cate.setItemCategory1("");
            item.setItemCategory(cate);
            Containers containers = new Containers();
            Container cont = new Container();
            cont.setPackUm("CS");
            cont.setUmDescr("件");
            cont.setConversionQty("");
            cont.setHeight("");
            cont.setWeight("");
            cont.setLength("");
            cont.setDimensionUm("");
            cont.setWeight("");
            cont.setWeightUm("");
            containers.getContainer().add(cont);
            item.setContainers(containers);
            items.getItem().add(item);
            skuIds.add(s.getId());
        }
        skuReq.setItems(items);
        body.setItemRequest(skuReq);
        req.setHead(head);
        req.setBody(body);
        try {
            SkuResponse response = SfTwOrderClient.sendSkuItemsService(req);
            if ((null == response ? true : (null == response.getBody() ? true : (null == response.getBody().getItemResponse() ? true : (null == response.getBody().getItemResponse().getItems() ? true : (null == response.getBody().getItemResponse()
                    .getItems().getItem() ? true : false)))))) return;
            List<SkuRespItem> respItems = response.getBody().getItemResponse().getItems().getItem();
            if ("OK".equals(response.getHead())) {
                if (skuIds.size() == respItems.size()) {
                    // 同步成功
                    updateSkuSyncStatus(skuIds, "SUCCESS");
                } else {
                    for (SkuRespItem i : respItems) {
                        String skuNo = i.getSkuNo();
                        String result = i.getResult();
                        Long skuId = null;
                        for (MsgSKUSync ss : skus) {
                            if (skuNo.equals(ss.getSkuCode())) {
                                skuId = ss.getId();
                                break;
                            }
                        }
                        if (null == skuId) continue;
                        if ("1".equals(result)) {
                            successIds.add(skuId);
                        } else {
                            errorsIds.add(skuId);
                        }
                    }
                    if (0 < successIds.size()) {
                        // 同步成功
                        updateSkuSyncStatus(successIds, "SUCCESS");
                    }
                    if (0 < errorsIds.size()) {
                        // 同步失败
                        updateSkuSyncStatus(errorsIds, "ERROR");
                    }
                }
            } else {
                for (SkuRespItem i : respItems) {
                    String skuNo = i.getSkuNo();
                    String result = i.getResult();
                    Long skuId = null;
                    for (MsgSKUSync ss : skus) {
                        if (skuNo.equals(ss.getSkuCode())) {
                            skuId = ss.getId();
                            break;
                        }
                    }
                    if (null == skuId) continue;
                    if ("1".equals(result)) {
                        successIds.add(skuId);
                    } else {
                        errorsIds.add(skuId);
                    }
                }
                if (0 < successIds.size()) {
                    // 同步成功
                    updateSkuSyncStatus(successIds, "SUCCESS");
                }
                if (0 < errorsIds.size()) {
                    // 同步失败
                    updateSkuSyncStatus(errorsIds, "ERROR");
                }
            }
        } catch (BusinessException e) {
            log.error(e.getMessage());
            log.error("", e);
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }


    /**
     * 修改商品状态
     */
    private void updateSkuSyncStatus(List<Long> longList, String result) {
        for (Long id : longList) {
            try {
                MsgSKUSync m = msgSKUSyncDao.getByPrimaryKey(id);
                if (result.equals("SUCCESS")) {
                    // 同步成功
                    m.setStatus(DefaultStatus.FINISHED);
                } else {
                    // 同步失败
                    m.setStatus(DefaultStatus.CANCELED);
                }
                m.setUpdateTime(new Date());
                msgSKUSyncDao.save(m);
            } catch (Exception e) {

            }
        }
    }


    /**
     * 入库单推送到台湾顺丰
     */
    @Override
    public void sendInboundOrderService(MsgInboundOrder inOrder) throws Exception {
        InboundRequest req = new InboundRequest();
        InboundReqHead head = new InboundReqHead();
        head.setAccessCode(SF_TW_ACCESS_CODE);// 接入编码
        head.setCheckword(SF_TW_CHECKWORD);// 校验码
        req.setHead(head);
        InboundReqBody body = new InboundReqBody();
        InboundPurchaseOrderRequest purchaseReq = new InboundPurchaseOrderRequest();
        InboundReqPurchaseOrders orders = new InboundReqPurchaseOrders();
        purchaseReq.setCompanyCode(SF_TW_COMPANY_CODE);// 货主代码
        purchaseReq.setPurchaseOrders(orders);
        body.setPurchaseOrderRequest(purchaseReq);
        req.setBody(body);
        StockTransApplication sta = staDao.getByCode(inOrder.getStaCode());
        InboundReqPurchaseOrder order = new InboundReqPurchaseOrder();
        order.setWarehouseCode(inOrder.getSourceWh());// 仓库代码
        order.setErpOrder(inOrder.getStaCode());
        order.setErpOrderType(staType2SfOrderTypeRu(sta.getType()));// 订单类型
        order.setSFOrderType(order.getErpOrderType());
        order.setOrderDate(FormatUtil.formatDate(inOrder.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        order.setScheduledReceiptDate(inOrder.getPlanArriveTime() == null ? FormatUtil.formatDate(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd hh:mm:ss") : FormatUtil.formatDate(inOrder.getPlanArriveTime(), "yyyy-MM-dd hh:mm:ss"));
        order.setVendorCode(SF_TW_VENDOR_CODE);// 供应商
        order.setNote("");
        // order.setReceiptMode("N");
        order.setUserDef1(sta.getSlipCode2() == null ? sta.getRefSlipCode() : sta.getSlipCode2());
        InboundReqItems items = new InboundReqItems();
        List<MsgInboundOrderLine> lineList = msgInboundOrderLineDao.fomdMsgInboundOrderLineByOId(inOrder.getId());
        if (lineList.size() > 0) {
            Integer i = 1;
            for (MsgInboundOrderLine line : lineList) {
                InboundReqItem item = new InboundReqItem();
                item.setErpOrderLineNum((i++).toString());
                item.setSkuNo(line.getSku().getCode());
                item.setQty((null != line.getQty() ? line.getQty().toString() : ""));
                item.setQtyUm("件");
                item.setLot("STANDARD");
                item.setNote("");
                item.setPrice("");
                // item.setInventoryStatus("10");

                if (line.getInvStatus() == null) {
                    item.setInventoryStatus("10");
                } else {
                    if ("良品".equals(line.getInvStatus().getName())) {
                        item.setInventoryStatus("10");
                    } else {
                        item.setInventoryStatus("20");// 次品
                    }
                }



                item.setUserDef1("");
                items.getItem().add(item);
            }
            order.setItems(items);
        } else {
            log.error("SFTW====> inbound order line is null,msgInboundOrderId:{0}", inOrder.getId());
            throw new BusinessException(ErrorCode.SF_INBOUND_ORDER_LINE_IS_NULL, new Object[] {inOrder.getId()});
        }
        orders.setPurchaseOrder(order);
        try {
            InboundResponse response = SfTwOrderClient.sendPurchaseOrderService(req);
            if (null == response ? true : (null == response.getBody() ? true : (null == response.getBody().getPurchaseOrderResponse() ? true : (null == response.getBody().getPurchaseOrderResponse().getPurchaseOrders() ? true : false)))) return;
            List<InboundRespPurchaseOrder> respOrders = response.getBody().getPurchaseOrderResponse().getPurchaseOrders().getPurchaseOrder();
            if ("OK".equals(response.getHead())) {
                MsgInboundOrder msgOrder = msgInboundOrderDao.getByPrimaryKey(inOrder.getId());
                msgOrder.setStatus(DefaultStatus.FINISHED);
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> sendInboundOrderService---->Success!" + inOrder.getId() + ":" + inOrder.getStaCode());
                }
            } else {
                for (InboundRespPurchaseOrder o : respOrders) {
                    System.err.println(o.getResult());
                    String orderNo = o.getErpOrder();
                    String result = o.getResult();
                    if (((StringUtil.isEmpty(orderNo)) ? "" : orderNo).equals(inOrder.getStaCode())) {
                        if ("1".equals(result)) {
                            MsgInboundOrder msgOrder = msgInboundOrderDao.getByPrimaryKey(inOrder.getId());
                            msgOrder.setStatus(DefaultStatus.FINISHED);
                            if (log.isDebugEnabled()) {
                                log.debug("SFTW====> sendInboundOrderService---->Success!" + inOrder.getId() + ":" + inOrder.getStaCode());
                            }
                        } else {
                            MsgInboundOrder msgOrder = msgInboundOrderDao.getByPrimaryKey(inOrder.getId());
                            msgOrder.setStatus(DefaultStatus.CANCELED);
                            if (log.isDebugEnabled()) {
                                log.debug("SFTW====> sendInboundOrderService---->Failed!" + inOrder.getId() + ":" + inOrder.getStaCode());
                            }
                        }
                    }
                }
            }
        } catch (BusinessException e) {
            log.error(e.getMessage());
            log.error("SFTW====> sendInboundOrderService exception", e);
            throw e;
        } catch (Exception e) {
            log.error("SFTW====> sendInboundOrderService exception", e);
            throw e;
        }
    }

    private String staType2SfOrderTypeRu(StockTransApplicationType type) {// 入
        switch (type.getValue()) {
            case 11:
                return "采购入库";// 10:采购入库
            case 41:
                return "退货入库";// 20:退货入库
            case 15:
                return "赠品入库";// 40:赠品入库
            case 12:
                return "其它入库";// 60:其它入库
            case 13:
                return "其它入库";// 60:其它入库
            case 14:
                return "其它入库";// 60:其它入库
            case 16:
                return "其它入库";// 60:其它入库
            case 81:
                return "其它入库";// 60:其它入库
            case 211:
                return "其它入库";// 60:其它入库
            case 101:
                return "退仓订单";// 50:退仓订单
            case 210:
                return "退仓订单";// 50:退仓订单
            case 21:
            case 25:
                return "销售订单";// 10:销售订单
            case 42:
                return "换货订单";// 30：换货订单
            case 62:
                return "正常出库"; // 结算经销出库
            case 216:
                return "正常出库"; // 串号拆分出库
            case 218:
                return "正常出库"; // 串号组合出库
            case 214:
                return "正常出库"; // 送修出库
            case 215:
                return "其它入库"; // 送修入库
            case 217:
                return "其它入库"; // 串号拆分入库
            case 219:
                return "其它入库"; // 串号组合入库
            case 32:
                return "其它入库"; // 库间移动
            default:
                throw new IllegalArgumentException();
        }
    }

    private String staType2SfOrderTypeChu(StockTransApplicationType type) {// 出
        switch (type.getValue()) {
            case 11:
                return "采购入库";// 10:采购入库
            case 41:
                return "退货入库";// 20:退货入库
            case 15:
                return "赠品入库";// 40:赠品入库
            case 12:
                return "其它入库";// 60:其它入库
            case 13:
                return "其它入库";// 60:其它入库
            case 14:
                return "其它入库";// 60:其它入库
            case 16:
                return "其它入库";// 60:其它入库
            case 81:
                return "其它入库";// 60:其它入库
            case 211:
                return "其它入库";// 60:其它入库
            case 101:
                return "退仓订单";// 50:退仓订单
            case 210:
                return "退仓订单";// 50:退仓订单
            case 21:
            case 25:
                return "销售订单";// 10:销售订单
            case 42:
                return "换货订单";// 30：换货订单
            case 62:
                return "正常出库"; // 结算经销出库
            case 216:
                return "正常出库"; // 串号拆分出库
            case 218:
                return "正常出库"; // 串号组合出库
            case 214:
                return "正常出库"; // 送修出库
            case 215:
                return "其它入库"; // 送修入库
            case 217:
                return "其它入库"; // 串号拆分入库
            case 219:
                return "其它入库"; // 串号组合入库
            case 32:
                return "其它出库"; // 库间移动
            default:
                throw new IllegalArgumentException();
        }
    }


    private String carrier2LpCode(String carrier) {
        if (carrier == null) {
            throw new BusinessException(ErrorCode.SF_CARRIER_ERROR);
        }
        if (carrier.equals("顺丰速运")) {
            return "SF";
        }
        return "";
    }

    /**
     * 接收台湾顺丰入库反馈
     */
    @Override
    public String receiveInboundOrderPushService(String logisticsInterface, String dataDigest) {
        System.out.println(logisticsInterface);
        String responseXml = "";
        try {
            if (StringUtil.isEmpty(logisticsInterface)) {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveInboundOrderService logistics_interface is null");
                }
                InboundRtnResponse response = new InboundRtnResponse();
                response.setHead("Ok");
                InboundRtnRespBody body = new InboundRtnRespBody();
                InboundRtnPurchaseOrderInboundResponse inboundResp = new InboundRtnPurchaseOrderInboundResponse();
                inboundResp.setResult("2");
                inboundResp.setNote("推送入库明细logistics_interface空异常!");
                body.setPurchaseOrderInboundResponse(inboundResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
            if (StringUtil.isEmpty(dataDigest)) {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveInboundOrderService data_digest is null");
                }
                InboundRtnResponse response = new InboundRtnResponse();
                response.setHead("Ok");
                InboundRtnRespBody body = new InboundRtnRespBody();
                InboundRtnPurchaseOrderInboundResponse inboundResp = new InboundRtnPurchaseOrderInboundResponse();
                inboundResp.setResult("2");
                inboundResp.setNote("推送入库明细data_digest空异常!");
                body.setPurchaseOrderInboundResponse(inboundResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
            boolean isValid = digestValidation(logisticsInterface, dataDigest);
            if (log.isDebugEnabled()) {
                log.debug("SFTW====> receiveInboundOrderService digest validation is : {}", isValid);
            }
            if (isValid) {
                InboundRtnRequest request = (InboundRtnRequest) MarshallerUtil.buildJaxb(InboundRtnRequest.class, logisticsInterface);
                if ((null == request ? true : (null == request.getBody() ? true : (null == request.getBody().getPurchaseOrderInboundRequest() ? true : (null == request.getBody().getPurchaseOrderInboundRequest().getPurchaseOrders() ? true : false))))) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveInboundOrderService logistics_interface xml structure is wrong");
                    }
                    InboundRtnResponse response = new InboundRtnResponse();
                    response.setHead("Ok");
                    InboundRtnRespBody body = new InboundRtnRespBody();
                    InboundRtnPurchaseOrderInboundResponse inboundResp = new InboundRtnPurchaseOrderInboundResponse();
                    inboundResp.setResult("2");
                    inboundResp.setNote("报文结构不正确!");
                    body.setPurchaseOrderInboundResponse(inboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
                List<InboundRtnReqPurchaseOrder> orders = request.getBody().getPurchaseOrderInboundRequest().getPurchaseOrders().getPurchaseOrder();
                if (null == orders || 0 == orders.size()) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveInboundOrderService no orders");
                    }
                    InboundRtnResponse response = new InboundRtnResponse();
                    response.setHead("Ok");
                    InboundRtnRespBody body = new InboundRtnRespBody();
                    InboundRtnPurchaseOrderInboundResponse inboundResp = new InboundRtnPurchaseOrderInboundResponse();
                    inboundResp.setResult("2");
                    inboundResp.setNote("推送入库明细无入库单!");
                    body.setPurchaseOrderInboundResponse(inboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
                if (1 < orders.size()) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveInboundOrderService more than one order");
                    }
                    InboundRtnResponse response = new InboundRtnResponse();
                    response.setHead("Ok");
                    InboundRtnRespBody body = new InboundRtnRespBody();
                    InboundRtnPurchaseOrderInboundResponse inboundResp = new InboundRtnPurchaseOrderInboundResponse();
                    inboundResp.setResult("2");
                    inboundResp.setNote("推送入库明细入库单超过1单!");
                    body.setPurchaseOrderInboundResponse(inboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
                List<String> errorOrders = new ArrayList<String>();
                List<String> reasons = new ArrayList<String>();
                for (InboundRtnReqPurchaseOrder order : orders) {
                    try {
                        StockTransApplication sta = staDao.findStaByCode(order.getErpOrder());
                        if (sta == null) {
                            reasons.add("未找到对应的作业单");
                            // 未找到对应的入库单
                            throw new BusinessException(ErrorCode.SF_ORDER_NOT_FOUND);
                        }
                        MsgRtnInboundOrder mri = new MsgRtnInboundOrder();
                        mri.setCreateTime(new Date());
                        mri.setStaCode(order.getErpOrder());
                        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
                        if (wh.getVmiSource() != null) {
                            mri.setSource(wh.getVmiSource());
                            mri.setSourceWh(wh.getVmiSourceWh());
                        } else {
                            if (sta.getAddiWarehouse() != null) {
                                Warehouse wh2 = warehouseDao.getByOuId(sta.getAddiWarehouse().getId());
                                mri.setSource(wh2.getVmiSource());
                                mri.setSourceWh(wh2.getVmiSourceWh());
                            }
                        }

                        mri.setStatus(DefaultStatus.CREATED);
                        mri.setType(sta.getType().getValue());
                        msgRtnInboundOrderDao.save(mri);
                        msgRtnInboundOrderDao.flush();
                        InboundRtnReqItems items = order.getItems();
                        List<InboundRtnReqItem> item = items.getItem();
                        for (InboundRtnReqItem i : item) {
                            MsgRtnInboundOrderLine line = new MsgRtnInboundOrderLine();
                            line.setSkuCode(i.getSkuNo());
                            line.setSkuId(null == skuDao.getByCode(i.getSkuNo()) ? null : skuDao.getByCode(i.getSkuNo()).getId());
                            line.setQty(null != i.getActualQty() ? i.getActualQty().longValue() : 0L);
                            if (i.getInventoryStatus() == null || "".equals(i.getInventoryStatus()) || !("10".equals(i.getInventoryStatus()) || "20".equals(i.getInventoryStatus()))) {
                                String invStatus = (i.getInventoryStatus() == null || "".equals(i.getInventoryStatus())) ? "" : i.getInventoryStatus();
                                reasons.add(invStatus + "库存状态异常");
                                throw new BusinessException(ErrorCode.SF_INTERFACE_INVSTATUS_ERROR);
                            }
                            InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu("10".equals(i.getInventoryStatus()) == true ? "良品" : "残次品", sta.getMainWarehouse().getId());
                            if (invStatus == null) {
                                reasons.add("宝尊库存状态异常");
                                throw new BusinessException(ErrorCode.SF_INVSTATUS_WMS_ERROR);
                            }
                            line.setInvStatus(invStatus);
                            line.setMsgRtnInOrder(mri);
                            msgRtnInboundOrderLineDao.save(line);
                            msgRtnInboundOrderLineDao.flush();
                            InboundRtnReqSerialNumbers sns = i.getSerialNumbers();
                            if (null != sns && null != sns.getSerialNumber() && sns.getSerialNumber().size() > 0) {
                                List<String> sList = sns.getSerialNumber();
                                if (sList.size() != line.getQty()) {
                                    reasons.add("SN异常");
                                    throw new BusinessException(ErrorCode.SF_SN_ERROR);
                                }
                                for (String s : sList) {
                                    RtnSnDetail sd = new RtnSnDetail();
                                    sd.setSn(s);
                                    sd.setInLine(line);
                                    rtnSnDetailDao.save(sd);
                                }
                            }
                        }
                    } catch (Exception e) {
                        errorOrders.add(order.getErpOrder());
                    }
                }
                if (0 == errorOrders.size()) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveInboundOrderService receive success");
                    }
                    InboundRtnResponse response = new InboundRtnResponse();
                    response.setHead("Ok");
                    InboundRtnRespBody body = new InboundRtnRespBody();
                    InboundRtnPurchaseOrderInboundResponse inboundResp = new InboundRtnPurchaseOrderInboundResponse();
                    inboundResp.setResult("1");
                    inboundResp.setNote("推送入库明细成功!");
                    body.setPurchaseOrderInboundResponse(inboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveInboundOrderService receive fail, exceptions occur");
                    }
                    InboundRtnResponse response = new InboundRtnResponse();
                    response.setHead("Ok");
                    InboundRtnRespBody body = new InboundRtnRespBody();
                    InboundRtnPurchaseOrderInboundResponse inboundResp = new InboundRtnPurchaseOrderInboundResponse();
                    inboundResp.setResult("2");
                    inboundResp.setNote("推送入库明细失败!异常订单：" + errorOrders.toString() + "。失败原因：" + reasons.toString());
                    body.setPurchaseOrderInboundResponse(inboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveInboundOrderService digest validate fail");
                }
                InboundRtnResponse response = new InboundRtnResponse();
                response.setHead("Ok");
                InboundRtnRespBody body = new InboundRtnRespBody();
                InboundRtnPurchaseOrderInboundResponse inboundResp = new InboundRtnPurchaseOrderInboundResponse();
                inboundResp.setResult("2");
                inboundResp.setNote("签名验证失败");
                body.setPurchaseOrderInboundResponse(inboundResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
        } catch (Exception e) {
            log.error("SFTW====> receiveInboundOrderService throw exception,xml is: " + logisticsInterface, e);
            InboundRtnErrorResponse errorResp = new InboundRtnErrorResponse();
            InboundRtnError rtnError = new InboundRtnError();
            rtnError.setCode("NNNNN");
            rtnError.setValue("推送入库明细处理异常：" + logisticsInterface);
            errorResp.setError(rtnError);
            responseXml = MarshallerUtil.buildJaxb(errorResp);
        }
        return responseXml;
    }

    @SuppressWarnings("unused")
    private String digest(String checkData) {
        return new String(Base64.encodeBase64(DigestUtils.md5(checkData.getBytes(Charset.forName("UTF-8")))), Charset.forName("UTF-8"));
    }

    @SuppressWarnings("unused")
    private String digest(String checkData, String charset, String charset2) {
        if ("".equals(charset2)) {
            return new String(Base64.encodeBase64(DigestUtils.md5(checkData.getBytes(Charset.forName(charset)))));
        } else {
            return new String(Base64.encodeBase64(DigestUtils.md5(checkData.getBytes(Charset.forName(charset)))), Charset.forName(charset2));
        }
    }

    private boolean digestValidation(String logisticsInterface, String dataDigest) {
        boolean result = true;
        if (!"".equals(logisticsInterface)) {
            logisticsInterface = StringUtils.trim(logisticsInterface);
        }
        // log.error("SFTW====> disgestLI ,logisticsInterface is:[{}], paramDigetst is:[{}], sfDigest is[{}], bzDigest is:[{}]",
        // new Object[] {logisticsInterface, dataDigest, SfTwDigest.digest(logisticsInterface,
        // "UTF-8"), digest(logisticsInterface)});
        // log.error("SFTW====> disgestLI-CS ,logisticsInterface is:[{}], paramDigetst is:[{}], sfDigestGBK is[{}], bzDigestGBK is:[{}], bzDegiestGB2 is :[{}], bzDegiestISO8 is :[{}]",
        // new Object[] {logisticsInterface, dataDigest, SfTwDigest.digest(logisticsInterface,
        // "GBK"), digest(logisticsInterface, "GBK", "GBK"), digest(logisticsInterface, "GB2312",
        // ""), digest(logisticsInterface, "ISO-8859-1", "")});
        String checkData = logisticsInterface + SF_TW_CHECKWORD;
        // log.error("SFTW====> disgestCW ,checkData is:[{}], paramDigetst is:[{}], sfDigest is[{}], bzDigest is:[{}]",
        // new Object[] {checkData, dataDigest, SfTwDigest.digest(checkData, "UTF-8"),
        // digest(checkData)});
        // log.error("SFTW====> disgestCW-CS ,checkData is:[{}], paramDigetst is:[{}], sfDigestGBK is[{}], bzDigestGBK is:[{}], bzDegiestGB2 is :[{}], bzDegiestISO8 is :[{}]",
        // new Object[] {checkData, dataDigest, SfTwDigest.digest(checkData, "GBK"),
        // digest(checkData, "GBK", "GBK"), digest(checkData, "GB2312", ""), digest(checkData,
        // "ISO-8859-1", "")});
        String digest = new String(Base64.encodeBase64(DigestUtils.md5(checkData.getBytes(Charset.forName("UTF-8")))), Charset.forName("UTF-8"));
        log.error("SFTW====> digestValidation, checkData is:[{}], digest is:[{}]", checkData, digest);
        if (!digest.equals(dataDigest)) {
            result = false;
        }
        return result;
    }

    /**
     * 下发供应商到台湾顺丰
     * 
     * @throws Exception
     */
    @Override
    public String sendVendorService() {
        String responseXml = "";
        VendorsRequest request = new VendorsRequest();
        VendorsReqHead head = new VendorsReqHead();
        head.setAccessCode(SF_TW_ACCESS_CODE);
        head.setCheckword(SF_TW_CHECKWORD);
        request.setHead(head);
        VendorsReqBody body = new VendorsReqBody();
        VendorRequest vReq = new VendorRequest();
        vReq.setCompanyCode(SF_TW_COMPANY_CODE);
        ReqVendors vendors = new ReqVendors();
        ReqVendor vendor = new ReqVendor();
        vendor.setVendorCode(SF_TW_VENDOR_CODE);
        vendor.setVendorName("上海宝尊");
        vendor.setAddress("上海市闸北区万荣路1188号");
        vendor.setCountry("中国");
        vendor.setProvince("上海");
        vendor.setCity("上海市");
        vendors.getVendor().add(vendor);
        vReq.setVendors(vendors);
        body.setVendorRequest(vReq);
        request.setBody(body);
        try {
            VendorsResponse response = SfTwOrderClient.sendVendorService(request);
            if (null == response ? true : (null == response.getBody() ? true : (null == response.getBody().getVendorResponse() ? true : (null == response.getBody().getVendorResponse().getVendors() ? true : (null == response.getBody().getVendorResponse()
                    .getVendors().getVendor() ? true : false))))) return "";
            responseXml = MarshallerUtil.buildJaxb(response);
        } catch (BusinessException e) {
            log.error(e.getMessage());
            log.error("SFTW====> sendInboundOrderService exception", e);
            responseXml = e.getMessage();
        } catch (Exception e) {
            log.error("SFTW====> sendInboundOrderService exception", e);
            responseXml = e.getMessage();
        }
        return responseXml;
    }

    /**
     * 出库单通知到台湾顺丰
     */
    @Override
    public void sendOutboundOrderService(MsgOutboundOrder outOrder) throws Exception {
        StockTransApplication sta = staDao.getByCode(outOrder.getStaCode());
        BiChannel bi = biChannelDao.getByCode(sta.getOwner());
        if (bi.getVmiWHSource() == null) {
            // 渠道货主为空
            log.error("outbound order notice msg VmiWHSource is null " + outOrder.getId());
            throw new BusinessException(ErrorCode.SF_OUTBOUND_ORDER_FLAG_IS_NULL, new Object[] {outOrder.getId()});
        }
        OutboundRequest req = new OutboundRequest();
        OutboundReqHead head = new OutboundReqHead();
        head.setAccessCode(SF_TW_ACCESS_CODE);
        head.setCheckword(SF_TW_CHECKWORD);
        req.setHead(head);
        OutboundReqBody body = new OutboundReqBody();
        OutboundSaleOrderRequest saleReq = new OutboundSaleOrderRequest();
        saleReq.setCompanyCode(SF_TW_COMPANY_CODE);
        body.setSaleOrderRequest(saleReq);
        req.setBody(body);
        OutboundReqSaleOrders orders = new OutboundReqSaleOrders();
        saleReq.setSaleOrders(orders);
        OutboundReqSaleOrder order = new OutboundReqSaleOrder();
        order.setWarehouseCode(outOrder.getSourceWh());
        order.setSfOrderType(staType2SfOrderTypeChu(StockTransApplicationType.valueOf(outOrder.getStaType())));
        order.setErpOrderType(order.getSfOrderType());
        order.setErpOrder(outOrder.getStaCode());
        order.setOrderNote(outOrder.getRemark());
        order.setTradeOrderDateTime(FormatUtil.formatDate(outOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        order.setPayDateTime("");
        order.setCurrencyCode("TWD");
        order.setCompanyNote("");
        order.setShopName(bi.getName());
        order.setTradePlatform("交易平台");
        order.setTradeOrder(sta.getSlipCode2() == null ? sta.getRefSlipCode() : sta.getSlipCode2());
        order.setBuyerId("");
        order.setCompleteDelivery("");
        order.setFromFlag("");
        order.setDeliveryDate("");
        order.setDeliveryRequested("");
        order.setPaymentMethod("");
        order.setPaymentNumber("");
        order.setFreight(null != sta.getOrderTransferFree() ? sta.getOrderTransferFree().toString() : "0.0");
        order.setOrderTotalAmount(null != sta.getTotalActual() ? sta.getTotalActual().toString() : "0.0");
        order.setOrderDiscount("");
        order.setOtherCharge("");
        BigDecimal total = (sta.getOrderTotalActual() == null ? new BigDecimal(0) : sta.getOrderTotalActual()).add((sta.getOrderTransferFree() == null ? new BigDecimal(0) : sta.getOrderTransferFree()));
        order.setActualAmount((null == sta.getTotalActual() ? "" : sta.getTotalActual().toString()));
        order.setPriority("3");
        order.setDeliveryModel("");
        order.setIsInvoice("");
        order.setIsAllowSplit("");
        order.setOrgErpOrder("");
        order.setOrgTradeOrder("");
        order.setInProcessWaybillNo("");
        order.setCustomerNetCode("");
        order.setCustomerNetName("");
        order.setCustomerAreaCode("");
        OutboundReqCustomsDeclarationInfo declare = new OutboundReqCustomsDeclarationInfo();
        order.setCustomsDeclarationInfo(declare);
        OutboundReqOrderAddedServices addServices = new OutboundReqOrderAddedServices();
        OutboundReqOrderAddedService addService = new OutboundReqOrderAddedService();
        StaDeliveryInfo si = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
        addServices.setOrderAddedService(addService);
        order.setOrderAddedServices(addServices);
        OutboundReqOrderCarrier carry = new OutboundReqOrderCarrier();
        carry.setMonthlyAccount(bi.getSfFlag());// 月结账号
        // carry.setMonthlyAccount(SF_TW_MONTHLY_ACCOUNT);
        OutboundReqCarrierAddedServices carryAddServices = new OutboundReqCarrierAddedServices();
        List<OutboundReqCarrierAddedService> list = new ArrayList<OutboundReqCarrierAddedService>();
        if (null != si.getIsCod() && si.getIsCod()) {
            OutboundReqCarrierAddedService cAddService = new OutboundReqCarrierAddedService();
            cAddService.setServiceCode(SfTwServiceCode.COD_CODE);
            cAddService.setAttr01(total.toString());
            cAddService.setAttr02(bi.getSfFlag());
            list.add(cAddService);
            carry.setPaymentOfCharge("寄付");
        } else {
            carry.setPaymentOfCharge("寄付");
        }
        if (null != si.getInsuranceAmount() && si.getInsuranceAmount().compareTo(new BigDecimal(0)) > 0) {
            OutboundReqCarrierAddedService cAddService = new OutboundReqCarrierAddedService();
            cAddService.setServiceCode(SfTwServiceCode.INSUERANCE_CODE);
            cAddService.setAttr01(si.getInsuranceAmount().toString());
            list.add(cAddService);
        }
        carryAddServices.setCarrierAddedService(list);
        carry.setCarrierAddedServices(carryAddServices);
        TransLpcodeWhRef wr = transLpcodeWhRefDao.getWhCarrierByLpCodeAndSource(outOrder.getLpCode(), Constants.VIM_WH_SOURCE_SF_TW);
        carry.setCarrier(null != wr ? wr.getCarrier() : "");
        carry.setWaybillNo(si.getTrackingNo());
        order.setOrderCarrier(carry);
        OutboundReqOrderReceiverInfo receiver = new OutboundReqOrderReceiverInfo();
        receiver.setReceiverCompany("个人");
        receiver.setReceiverName(outOrder.getReceiver());
        receiver.setReceiverEmail("");
        receiver.setReceiverZipCode(outOrder.getZipcode());
        receiver.setReceiverCountry(null != outOrder.getCountry() ? outOrder.getCountry() : "中国");
        receiver.setReceiverProvince(outOrder.getProvince());
        receiver.setReceiverCity(outOrder.getCity());
        receiver.setReceiverArea(outOrder.getDistrict());
        receiver.setReceiverAddress(outOrder.getAddress());
        receiver.setReceiverMobile(outOrder.getMobile() == null ? outOrder.getTelePhone() : outOrder.getMobile());
        receiver.setReceiverPhone(outOrder.getTelePhone());
        receiver.setReceiverIdCard("");
        receiver.setReceiverIdType("");
        order.setOrderReceiverInfo(receiver);
        OutboundReqOrderSenderInfo sender = new OutboundReqOrderSenderInfo();
        order.setOrderSenderInfo(sender);
        List<MsgOutboundOrderLine> lineList = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(outOrder.getId());
        if (lineList == null || lineList.size() == 0) {
            throw new BusinessException(ErrorCode.SF_OUTBOUND_ORDER_LINE_IS_NULL, new Object[] {outOrder.getId()});
        } else {
            OutboundReqOrderItems items = new OutboundReqOrderItems();
            Integer i = 1;
            for (MsgOutboundOrderLine line : lineList) {
                OutboundReqOrderItem item = new OutboundReqOrderItem();
                item.setErpOrderLineNum((i++).toString());
                item.setSkuNo(line.getSku().getCode());
                item.setItemName(line.getSkuName());
                if (line.getInvStatus() == null) {

                } else {
                    if ("良品".equals(line.getInvStatus().getName())) {} else {
                        item.setInventoryStatus("次品");// 残次品
                    }
                }

                item.setItemUom("件");
                item.setItemQuantity((null != line.getQty() ? line.getQty().toString() : ""));
                items.getOrderItem().add(item);
            }
            order.setOrderItems(items);
        }
        OutboundReqOrderInvoice invoice = new OutboundReqOrderInvoice();// 发票
        order.setOrderInvoice(invoice);
        orders.getSaleOrder().add(order);
        try {
            OutboundResponse response = SfTwOrderClient.sendSaleOrderService(req);
            if (null == response ? true : (null == response.getBody() ? true : (null == response.getBody().getSaleOrderResponse() ? true : (null == response.getBody().getSaleOrderResponse().getSaleOrders() ? true : false)))) return;
            List<OutboundRespSaleOrder> respOrders = response.getBody().getSaleOrderResponse().getSaleOrders().getSaleOrder();
            if ("OK".equals(response.getHead())) {
                MsgOutboundOrder msgOrder = msgOutboundOrderDao.getByPrimaryKey(outOrder.getId());
                msgOrder.setStatus(DefaultStatus.FINISHED);
                msgOrder.setUpdateTime(new Date());
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> sendOutboundOrderService---->Success!" + outOrder.getId() + ":" + outOrder.getStaCode());
                }
            } else {
                for (OutboundRespSaleOrder o : respOrders) {
                    String orderNo = o.getErpOrder();
                    String result = o.getResult();
                    if (((StringUtil.isEmpty(orderNo)) ? "" : orderNo).equals(outOrder.getStaCode())) {
                        if ("1".equals(result)) {
                            MsgOutboundOrder msgOrder = msgOutboundOrderDao.getByPrimaryKey(outOrder.getId());
                            msgOrder.setStatus(DefaultStatus.FINISHED);
                            msgOrder.setUpdateTime(new Date());
                            if (log.isDebugEnabled()) {
                                log.debug("SFTW====> sendOutboundOrderService---->Success!" + outOrder.getId() + ":" + outOrder.getStaCode());
                            }
                        } else {
                            MsgOutboundOrder msgOrder = msgOutboundOrderDao.getByPrimaryKey(outOrder.getId());
                            msgOrder.setStatus(DefaultStatus.CANCELED);
                            msgOrder.setUpdateTime(new Date());
                            if (log.isDebugEnabled()) {
                                log.debug("SFTW====> sendOutboundOrderService---->Failed!" + outOrder.getId() + ":" + outOrder.getStaCode());
                            }
                        }
                    }
                }
            }
        } catch (BusinessException e) {
            log.error(e.getMessage());
            log.error("SFTW====> sendOutboundOrderService exception", e);
            throw e;
        } catch (Exception e) {
            log.error("SFTW====> sendOutboundOrderService exception", e);
            throw e;
        }
    }

    /**
     * 接收台湾顺丰出库反馈
     */
    @Override
    public String receiveOutboundOrderPushService(String logisticsInterface, String dataDigest) {
        System.out.println(logisticsInterface);
        String responseXml = "";
        try {
            if (StringUtil.isEmpty(logisticsInterface)) {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveOutboundOrderPushService logistics_interface is null");
                }
                OutboundRtnResponse response = new OutboundRtnResponse();
                response.setHead("Ok");
                OutboundRtnRespBody body = new OutboundRtnRespBody();
                OutboundRtnSaleOrderOutboundDetailResponse outboundResp = new OutboundRtnSaleOrderOutboundDetailResponse();
                outboundResp.setResult("2");
                outboundResp.setNote("推送出库明细logistics_interface空异常!");
                body.setSaleOrderOutboundDetailResponse(outboundResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
            if (StringUtil.isEmpty(dataDigest)) {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveOutboundOrderPushService data_digest is null");
                }
                OutboundRtnResponse response = new OutboundRtnResponse();
                response.setHead("Ok");
                OutboundRtnRespBody body = new OutboundRtnRespBody();
                OutboundRtnSaleOrderOutboundDetailResponse outboundResp = new OutboundRtnSaleOrderOutboundDetailResponse();
                outboundResp.setResult("2");
                outboundResp.setNote("推送出库明细data_digest空异常!");
                body.setSaleOrderOutboundDetailResponse(outboundResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
            boolean isValid = digestValidation(logisticsInterface, dataDigest);
            if (log.isDebugEnabled()) {
                log.debug("SFTW====> receiveOutboundOrderPushService digest validation is : {}", isValid);
            }
            if (isValid) {
                OutboundRtnRequest request = (OutboundRtnRequest) MarshallerUtil.buildJaxb(OutboundRtnRequest.class, logisticsInterface);
                if ((null == request ? true : (null == request.getBody() ? true : (null == request.getBody().getSaleOrderOutboundDetailRequest() ? true : (null == request.getBody().getSaleOrderOutboundDetailRequest().getSaleOrders() ? true : false))))) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveOutboundOrderPushService logistics_interface xml structure is wrong");
                    }
                    OutboundRtnResponse response = new OutboundRtnResponse();
                    response.setHead("Ok");
                    OutboundRtnRespBody body = new OutboundRtnRespBody();
                    OutboundRtnSaleOrderOutboundDetailResponse outboundResp = new OutboundRtnSaleOrderOutboundDetailResponse();
                    outboundResp.setResult("2");
                    outboundResp.setNote("报文结构不正确!");
                    body.setSaleOrderOutboundDetailResponse(outboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
                List<OutboundRtnReqSaleOrder> orders = request.getBody().getSaleOrderOutboundDetailRequest().getSaleOrders().getSaleOrder();
                if (null == orders || 0 == orders.size()) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveOutboundOrderPushService no orders");
                    }
                    OutboundRtnResponse response = new OutboundRtnResponse();
                    response.setHead("Ok");
                    OutboundRtnRespBody body = new OutboundRtnRespBody();
                    OutboundRtnSaleOrderOutboundDetailResponse outboundResp = new OutboundRtnSaleOrderOutboundDetailResponse();
                    outboundResp.setResult("2");
                    outboundResp.setNote("推送出库明细无出库单!");
                    body.setSaleOrderOutboundDetailResponse(outboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
                if (1 < orders.size()) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveOutboundOrderPushService more than one order");
                    }
                    OutboundRtnResponse response = new OutboundRtnResponse();
                    response.setHead("Ok");
                    OutboundRtnRespBody body = new OutboundRtnRespBody();
                    OutboundRtnSaleOrderOutboundDetailResponse outboundResp = new OutboundRtnSaleOrderOutboundDetailResponse();
                    outboundResp.setResult("2");
                    outboundResp.setNote("推送出库明细出库单超过1单!");
                    body.setSaleOrderOutboundDetailResponse(outboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
                List<String> errorOrders = new ArrayList<String>();
                List<String> reasons = new ArrayList<String>();
                for (OutboundRtnReqSaleOrder order : orders) {
                    try {
                        StockTransApplication sta = staDao.findStaByCode(order.getErpOrder());
                        if (sta == null) {
                            reasons.add("未找到对应的作业单");
                            // 未找到对应的入库单
                            throw new BusinessException(ErrorCode.SF_ORDER_NOT_FOUND);
                        }
                        LogisticsTracking lt = new LogisticsTracking();
                        lt.setCompany(SF_TW_COMPANY_CODE);
                        lt.setWarehouse(order.getWarehouseCode());
                        lt.setErpOrder(order.getErpOrder());
                        lt.setShipMentId(order.getShipmentId());
                        lt.setWayBillNo(order.getWayBillNo());
                        lt.setActualShipDateTime(FormatUtil.getDate(order.getActualShipDateTime()));
                        lt.setCarrier(order.getCarrier());
                        lt.setCarrierService(order.getCarrierProduct());
                        lt.setUserStamp("");
                        lt.setStatusTime(new Date());
                        lt.setStatusCode(order.getDataStatus());
                        lt.setStatusRemark("");
                        lt.setSta(sta);
                        lt.setLpCode(carrier2LpCode(order.getCarrier()));
                        lt.setUserStamp(order.getUserDef1());
                        logisticsTrackingDao.save(lt);
                        MsgRtnOutbound mro = new MsgRtnOutbound();
                        mro.setCreateTime(new Date());
                        mro.setUpdateTime(new Date());
                        mro.setStaCode(order.getErpOrder());
                        if (sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES) || sta.getType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST) || sta.getType().equals(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES)
                                || sta.getType().equals(StockTransApplicationType.VMI_RETURN) || sta.getType().equals(StockTransApplicationType.SAMPLE_OUTBOUND) || sta.getType().equals(StockTransApplicationType.SERIAL_NUMBER_GROUP_OUTBOUND)
                                || sta.getType().equals(StockTransApplicationType.OUTBOUND_SETTLEMENT) || sta.getType().equals(StockTransApplicationType.REAPAIR_OUTBOUND) || sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS)
                                || sta.getType().equals(StockTransApplicationType.SERIAL_NUMBER_OUTBOUND)) {
                            mro.setStatus(DefaultStatus.CREATED);
                        } else {
                            mro.setStatus(DefaultStatus.INEXECUTION);
                        }
                        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
                        mro.setTrackName(order.getCarrier());
                        mro.setTrackingNo(order.getWayBillNo());
                        mro.setSource(wh.getVmiSource());
                        mro.setSourceWh(wh.getVmiSourceWh());
                        mro.setSfCode(order.getShipmentId());
                        TransLpcodeWhRef wr = transLpcodeWhRefDao.getLpCodeByWhAndCarrier(Constants.VIM_WH_SOURCE_SF_TW, order.getCarrier());
                        mro.setLpCode(null != wr ? wr.getLpCode() : "");
                        msgRtnOutboundDao.save(mro);
                        msgRtnOutboundDao.flush();
                        if (null == order.getItems() || null == order.getItems().getItem() || 0 == order.getItems().getItem().size()) {
                            reasons.add("推送出库明细无商品数据!");
                            // 无商品数量明细
                            throw new BusinessException(ErrorCode.SF_OUT_DETAIL_IS_NULL);

                        } else {
                            OutboundRtnReqItems items = order.getItems();
                            List<OutboundRtnReqItem> item = items.getItem();
                            for (OutboundRtnReqItem i : item) {
                                MsgRtnOutboundLine line = new MsgRtnOutboundLine();
                                line.setSkuCode(i.getSkuNo());
                                line.setBarCode((null == skuDao.getByCode(i.getSkuNo()) ? "" : skuDao.getByCode(i.getSkuNo()).getBarCode()));
                                line.setQty(null != i.getActualQty() ? i.getActualQty().longValue() : 0L);
                                line.setMsgOutbound(mro);
                                msgRtnOutboundLineDao.save(line);
                                msgRtnOutboundLineDao.flush();
                            }
                        }
                        if (null == order.getContainers() || null == order.getContainers().getContainer() || 0 == order.getContainers().getContainer().size() || null == order.getContainers().getContainer().get(0).getContainerItems()
                                || null == order.getContainers().getContainer().get(0).getContainerItems().getItem() || null == order.getContainers().getContainer().get(0).getContainerItems().getItem()
                                || 0 == order.getContainers().getContainer().get(0).getContainerItems().getItem().size()) {
                            reasons.add("推送出库明细无箱数据!");
                            throw new BusinessException(ErrorCode.SF_OUT_CONTAINER_IS_NULL);
                        } else {
                            BigDecimal wt = new BigDecimal("0.0");
                            List<OutboundRtnReqContainer> cList = order.getContainers().getContainer();
                            for (OutboundRtnReqContainer c : cList) {
                                List<OutboundRtnReqItem> iList = c.getContainerItems().getItem();
                                for (OutboundRtnReqItem i : iList) {
                                    MsgRtnOutboundContainerLine cLine = new MsgRtnOutboundContainerLine();
                                    cLine.setMsgRtnOutbound(mro);
                                    cLine.setContainerId(c.getContainerNo());// 箱号
                                    cLine.setSkuCode(i.getSkuNo());
                                    cLine.setSku(((null == skuDao.getByCode(i.getSkuNo()) ? null : skuDao.getByCode(i.getSkuNo()))));
                                    cLine.setQty(null != i.getActualQty() ? i.getActualQty().longValue() : 0L);
                                    cLine.setBatchCode(i.getLot());
                                    cLine.setQtyUm(i.getQtyUm());
                                    cLine.setWeight(i.getWeight());
                                    cLine.setWeightUm(i.getWeightUm());
                                    cLine.setUserStamp(i.getUserDef1());
                                    msgRtnOutboundContainerLineDao.save(cLine);
                                    wt = wt.add((null == i.getWeight() ? new BigDecimal("0.0") : i.getWeight()));
                                    OutboundRtnReqSerialNumbers sns = i.getSerialNumbers();
                                    if (null != sns && null != sns.getSerialNumber() && 0 < sns.getSerialNumber().size()) {
                                        List<String> sList = sns.getSerialNumber();
                                        if (sList.size() != cLine.getQty()) {
                                            reasons.add("SN异常");
                                            throw new BusinessException(ErrorCode.SF_SN_ERROR);
                                        }
                                        for (String s : sList) {
                                            RtnSnDetail sd = new RtnSnDetail();
                                            sd.setSn(s);
                                            sd.setOutLine(cLine);
                                            rtnSnDetailDao.save(sd);
                                        }
                                    }

                                }
                            }
                            if (-1 == new BigDecimal("0.0").compareTo(wt)) {
                                mro.setWeight(wt);
                                msgRtnOutboundDao.save(mro);
                                msgRtnOutboundDao.flush();
                            }
                        }
                    } catch (Exception e) {
                        errorOrders.add(order.getErpOrder());
                    }
                }
                if (0 == errorOrders.size()) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveOutboundOrderPushService receive success");
                    }
                    OutboundRtnResponse response = new OutboundRtnResponse();
                    response.setHead("Ok");
                    OutboundRtnRespBody body = new OutboundRtnRespBody();
                    OutboundRtnSaleOrderOutboundDetailResponse outboundResp = new OutboundRtnSaleOrderOutboundDetailResponse();
                    outboundResp.setResult("1");
                    outboundResp.setNote("推送出库明细成功!");
                    body.setSaleOrderOutboundDetailResponse(outboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveOutboundOrderPushService receive fail, exceptions occur");
                    }
                    OutboundRtnResponse response = new OutboundRtnResponse();
                    response.setHead("Ok");
                    OutboundRtnRespBody body = new OutboundRtnRespBody();
                    OutboundRtnSaleOrderOutboundDetailResponse outboundResp = new OutboundRtnSaleOrderOutboundDetailResponse();
                    outboundResp.setResult("2");
                    outboundResp.setNote("推送出库明细失败!异常订单：" + errorOrders.toString() + "。失败原因：" + reasons.toString());
                    body.setSaleOrderOutboundDetailResponse(outboundResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveOutboundOrderPushService digest validate fail");
                }
                OutboundRtnResponse response = new OutboundRtnResponse();
                response.setHead("Ok");
                OutboundRtnRespBody body = new OutboundRtnRespBody();
                OutboundRtnSaleOrderOutboundDetailResponse outboundResp = new OutboundRtnSaleOrderOutboundDetailResponse();
                outboundResp.setResult("2");
                outboundResp.setNote("签名验证失败");
                body.setSaleOrderOutboundDetailResponse(outboundResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
        } catch (Exception e) {
            log.error("SFTW====> receiveOutboundOrderPushService throw exception,xml is: " + logisticsInterface, e);
            OutboundRtnErrorResponse errorResp = new OutboundRtnErrorResponse();
            OutboundRtnError rtnError = new OutboundRtnError();
            rtnError.setCode("NNNNN");
            rtnError.setValue("推送出库明细处理异常：" + logisticsInterface);
            errorResp.setError(rtnError);
            responseXml = MarshallerUtil.buildJaxb(errorResp);
        }
        return responseXml;
    }

    /**
     * 接收台湾顺丰库存变化
     */
    @Override
    public String receiveInventoryChangePushService(String logisticsInterface, String dataDigest) {
        System.out.println(logisticsInterface);
        String responseXml = "";
        try {
            if (StringUtil.isEmpty(logisticsInterface)) {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveInventoryChangePushService logistics_interface is null");
                }
                InventoryResponse response = new InventoryResponse();
                InventoryRespBody body = new InventoryRespBody();
                InventoryChangeResponse invResp = new InventoryChangeResponse();
                invResp.setResult("2");
                invResp.setNote("库存变化推送logistics_interface空异常!");
                body.setInventoryChangeResponse(invResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
            if (StringUtil.isEmpty(dataDigest)) {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveInventoryChangePushService data_digest is null");
                }
                InventoryResponse response = new InventoryResponse();
                InventoryRespBody body = new InventoryRespBody();
                InventoryChangeResponse invResp = new InventoryChangeResponse();
                invResp.setResult("2");
                invResp.setNote("库存变化推送data_digest空异常!");
                body.setInventoryChangeResponse(invResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
            boolean isValid = digestValidation(logisticsInterface, dataDigest);
            if (log.isDebugEnabled()) {
                log.debug("SFTW====> receiveInventoryChangePushService digest validation is : {}", isValid);
            }
            if (isValid) {
                InventoryRequest request = (InventoryRequest) MarshallerUtil.buildJaxb(InventoryRequest.class, logisticsInterface);
                if (null == request ? true : (null == request.getBody() ? true : (null == request.getBody().getInventoryChangeRequest() ? true : (null == request.getBody().getInventoryChangeRequest().getInventoryChanges() ? true : false)))) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveInventoryChangePushService logistics_interface xml structure is wrong");
                    }
                    InventoryResponse response = new InventoryResponse();
                    InventoryRespBody body = new InventoryRespBody();
                    InventoryChangeResponse invResp = new InventoryChangeResponse();
                    invResp.setResult("2");
                    invResp.setNote("报文结构不正确!");
                    body.setInventoryChangeResponse(invResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
                List<InventoryReqChange> invs = request.getBody().getInventoryChangeRequest().getInventoryChanges().getInventoryChange();
                if (null == invs || 0 == invs.size()) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> receiveInventoryChangePushService no detail");
                    }
                    InventoryResponse response = new InventoryResponse();
                    InventoryRespBody body = new InventoryRespBody();
                    InventoryChangeResponse invResp = new InventoryChangeResponse();
                    invResp.setResult("2");
                    invResp.setNote("库存变化推送无明细!");
                    body.setInventoryChangeResponse(invResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
                List<String> reasons = new ArrayList<String>();
                try {
                    InventoryChangeRequest invChangeReq = request.getBody().getInventoryChangeRequest();
                    MsgRtnAdjustment mj = new MsgRtnAdjustment();
                    mj.setCreateTime(new Date());
                    mj.setStatus(DefaultStatus.CREATED);
                    mj.setSource(Constants.VIM_WH_SOURCE_SF_TW);
                    // mj.setSourceWh();
                    mj.setIdsKey("SFTW" + (new Date().getTime() % 1000000));
                    mj.setOwnerSource(invChangeReq.getCompanyCode());
                    for (InventoryReqChange inv : invs) {
                        String warehouseCode = inv.getWarehouseCode();
                        if (null == mj.getSourceWh()) {
                            mj.setSourceWh(warehouseCode);
                        }
                        MsgRtnAdjustmentLine line = new MsgRtnAdjustmentLine();
                        line.setAdjustment(mj);
                        String optType = inv.getOptType();
                        if ("1".equals(optType)) {
                            line.setQty((null != inv.getQty() ? inv.getQty().longValue() : 0L));// 入库
                        } else if ("2".equals(optType)) {
                            line.setQty((null != inv.getQty() ? -inv.getQty().longValue() : 0L));// 出库
                        } else {
                            reasons.add("optType:[" + optType + "]库存操作类型异常");
                            throw new BusinessException(ErrorCode.SF_ADJUST_LINE_DIRECTION_ERROR);
                        }
                        Sku sku = skuDao.getByCode(inv.getSkuNo());
                        if (null == sku) {
                            reasons.add((null != inv.getSkuNo() ? inv.getSkuNo() : "") + "商品不存在");
                            throw new BusinessException(ErrorCode.SF_INTERFACE_SKU_ERROR);
                        } else {
                            line.setSkuId(sku.getId());
                        }
                        if (null == inv.getInventoryStatus() || !("10".equals(inv.getInventoryStatus()) || "20".equals(inv.getInventoryStatus()))) {
                            reasons.add((null != inv.getInventoryStatus() ? inv.getInventoryStatus() : "") + "库存状态不正确");
                            throw new BusinessException(ErrorCode.SF_INTERFACE_INVSTATUS_ERROR);
                        }
                        InventoryStatus status = inventoryStatusDao.getByNameAndWarehouse(true == "10".equals(inv.getInventoryStatus()) ? "良品" : "残次品", Constants.VIM_WH_SOURCE_SF_TW, inv.getWarehouseCode());
                        if (status == null) {
                            reasons.add((null != inv.getInventoryStatus() ? inv.getInventoryStatus() : "") + "库存状态不正确");
                            throw new BusinessException(ErrorCode.SF_INVSTATUS_WMS_ERROR);
                        }
                        line.setInvStatus(status);
                        msgRtnAdjustmentLineDao.save(line);
                    }
                    msgRtnAdjustmentDao.save(mj);
                } catch (Exception e) {
                    log.error("SFTW====> receiveInventoryChangePushService push error: " + reasons.toString(), e);
                }
                if (0 == reasons.size()) {
                    InventoryResponse response = new InventoryResponse();
                    InventoryRespBody body = new InventoryRespBody();
                    InventoryChangeResponse invResp = new InventoryChangeResponse();
                    invResp.setResult("1");
                    invResp.setNote("库存变化推送成功!");
                    body.setInventoryChangeResponse(invResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                } else {
                    InventoryResponse response = new InventoryResponse();
                    InventoryRespBody body = new InventoryRespBody();
                    InventoryChangeResponse invResp = new InventoryChangeResponse();
                    invResp.setResult("2");
                    invResp.setNote(reasons.toString());
                    body.setInventoryChangeResponse(invResp);
                    response.setBody(body);
                    responseXml = MarshallerUtil.buildJaxb(response);
                    return responseXml;
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> receiveInventoryChangePushService digest validate fail");
                }
                InventoryResponse response = new InventoryResponse();
                InventoryRespBody body = new InventoryRespBody();
                InventoryChangeResponse invResp = new InventoryChangeResponse();
                invResp.setResult("2");
                invResp.setNote("签名验证失败");
                body.setInventoryChangeResponse(invResp);
                response.setBody(body);
                responseXml = MarshallerUtil.buildJaxb(response);
                return responseXml;
            }
        } catch (Exception e) {
            log.error("SFTW====> receiveInventoryChangePushService throw exception,xml is: " + logisticsInterface, e);
            OutboundRtnErrorResponse errorResp = new OutboundRtnErrorResponse();
            OutboundRtnError rtnError = new OutboundRtnError();
            rtnError.setCode("NNNNN");
            rtnError.setValue("库存变化推送处理异常：" + logisticsInterface);
            errorResp.setError(rtnError);
            responseXml = MarshallerUtil.buildJaxb(errorResp);
        }
        return responseXml;
    }

}
