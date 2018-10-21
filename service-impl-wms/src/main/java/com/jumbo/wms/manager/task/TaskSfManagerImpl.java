package com.jumbo.wms.manager.task;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransLpcodeWhRefDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
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
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.LogisticsTrackingDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.webservice.sf.SfOrderClientInter;
import com.jumbo.webservice.sfwarehouse.command.Item;
import com.jumbo.webservice.sfwarehouse.command.Items;
import com.jumbo.webservice.sfwarehouse.command.SerialNumber;
import com.jumbo.webservice.sfwarehouse.command.WmsCancelSailOrderRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsCancelSailOrderResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsInventoryAdjustPushInfoAdjust;
import com.jumbo.webservice.sfwarehouse.command.WmsInventoryAdjustPushInfoAdjustList;
import com.jumbo.webservice.sfwarehouse.command.WmsInventoryAdjustPushInfoRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsMerchantCatalogBatchRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsMerchantCatalogBatchResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsMerchantCatalogRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsMerchantCatalogResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderPushInfoRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderPushInfoRequestDetailList;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderPushInfoRequestHeader;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderPushInfoRequestItem;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderRequestDetail;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderRequestDetailList;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderRequestHeader;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderPushInfoRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderPushInfoRequestContainerItem;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderPushInfoRequestContainerList;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderPushInfoRequestDetailItem;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderPushInfoRequestDetailList;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderPushInfoRequestHeader;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderRequestDetail;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderRequestDetailList;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderRequestHeader;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsVendorRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsVendorResponse;
import com.jumbo.webservice.sfwarehouse.manager.SfWarehouseWebserviceClient;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.TransLpcodeWhRef;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
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
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.LogisticsTracking;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("taskSfManager")
public class TaskSfManagerImpl extends BaseManagerImpl implements TaskSfManager {

    /**
     * 
     */
    private static final long serialVersionUID = -4434491073407634342L;
    private static final String LYZP = "冷运到家";
    private static final String SXSP = "生鲜速配";
    TimeHashMap<String, String> cache = new TimeHashMap<String, String>();
    /**
     * 供应商编码
     */
    private static final String SOURCEID = "MF＿V1";// 供应商"BZDSHZ";
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
    private BiChannelDao channelDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private InventoryCheckDao invDao;
    @Autowired
    private InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    private InventoryCheckDifferenceLineDao icdLineDao;
    @Autowired
    private WareHouseManagerExe whExe;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private TransLpcodeWhRefDao transLpcodeWhRefDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private SkuSnLogDao snLogDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Autowired
    private SfOrderClientInter sfOrderClient;


    @Override
    public void sendBatchSkuToSf(List<MsgSKUSync> skus, String sfFlag) throws Exception {
        WmsMerchantCatalogBatchRequest request = new WmsMerchantCatalogBatchRequest();
        List<Item> newList = new ArrayList<Item>();// 新建商品列表
        List<Long> newLong = new ArrayList<Long>();// 新建商品ID
        // List<String> newCodeList = new ArrayList<String>();
        List<Item> saveList = new ArrayList<Item>();// 修改商品列表
        List<Long> saveLong = new ArrayList<Long>();// 修改商品ID
        Items items = new Items();
        for (MsgSKUSync s : skus) {
            Item item = new Item();
            item.setItem(s.getSkuCode());
            item.setDescription(s.getSkuName());
            item.setX_ref_item_1(s.getBarCode());
            item.setStorage_template(SF_SKU_UNIT);
            item.setQuantity_um_1(SF_SKU_UNIT);
            item.setSerial_num_track_outbound(s.getIsSn() == null || !s.getIsSn() ? "N" : "Y");
            item.setSerial_num_track_inbound(item.getSerial_num_track_outbound());
            if (s.getIntType() == 1) {
                newList.add(item);// 新建
                newLong.add(s.getId());
            } else {
                saveList.add(item);// 修改
                saveLong.add(s.getId());
            }
        }
        if (newList.size() > 0) {
            items.setItem(newList);
            // 新建
            request.setCheckword(getSfCheckWork());
            request.setCompany(sfFlag);// 货主
            request.setInterface_action_code("NEW");
            request.setItemlist(items);
            try {
                WmsMerchantCatalogBatchResponse response = sfOrderClient.wmsMerchantCatalogBatch(request);
                if (response.getResult().equals("1")) {
                    // 同步成功
                    sendBatchSkuSuccess(newLong, "SUCCESS");
                } else {
                    // 同步失败
                    sendBatchSkuSuccess(newLong, "ERROR");
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
        if (saveList.size() > 0) {
            items.setItem(saveList);
            // 修改
            request.setCheckword(getSfCheckWork());
            request.setCompany(sfFlag);// 货主
            request.setInterface_action_code("SAVE");
            request.setItemlist(items);
            try {
                WmsMerchantCatalogBatchResponse response = sfOrderClient.wmsMerchantCatalogBatch(request);
                if (response == null) {
                    log.warn("sendBatchSkuToSf,rs is null");
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                if (response.getResult().equals("1")) {
                    // 同步成功
                    sendBatchSkuSuccess(saveLong, "SUCCESS");
                } else {
                    // 同步失败
                    sendBatchSkuSuccess(saveLong, "ERROR");
                }
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                log.error("", e);
                throw e;
            }
        }
    }

    // private void sendBatchSkuFailed(List<ItemList> list, List<String> codeList, DefaultStatus
    // type) {
    // // sendBatchSkuSuccess(type, codeList);
    //
    // }

    /**
     * 同步商品成功/失败修改商品状态
     */
    private void sendBatchSkuSuccess(List<Long> longList, String result) {
        for (Long id : longList) {
            MsgSKUSync m = msgSKUSyncDao.getByPrimaryKey(id);
            if (result.equals("SUCCESS")) {
                // 同步失败
                m.setStatus(DefaultStatus.FINISHED);
            } else {
                // 同步成功
                m.setStatus(DefaultStatus.CANCELED);
            }
            m.setUpdateTime(new Date());
            msgSKUSyncDao.save(m);
        }
        // for (String code : codeList) {
        // msgSKUSyncDao.updateStatusBySkuCodeAndSource(status, batchNo)
        // }
    }

    @Override
    public void sendSingleSkuToSf(MsgSKUSync s) throws Exception {
        WmsMerchantCatalogRequest request = new WmsMerchantCatalogRequest();
        request.setCheckword(getSfCheckWork());
        request.setCompany(SF_COMPANY);
        request.setItem(s.getSkuCode());
        request.setDescription(s.getSkuName());
        request.setX_ref_item_1(s.getBarCode());
        request.setStorage_template(SF_SKU_UNIT);
        request.setQuantity_um_1(SF_SKU_UNIT);
        request.setSerial_num_track_outbound(s.getIsSn() == null || !s.getIsSn() ? "N" : "Y");
        request.setSerial_num_track_inbound(request.getSerial_num_track_outbound());
        request.setInterface_action_code(s.getIntType() == 1 ? "NEW" : "SAVE");
        try {
            // WmsMerchantCatalogResponse response =
            // SfWarehouseWebserviceClient.wmsMerchantCatalog(request);
            WmsMerchantCatalogResponse response = sfOrderClient.wmsMerchantCatalog(request);
            if (response.getResult().equals("1")) {
                msgSKUSyncDao.updateStatusById(DefaultStatus.FINISHED.getValue(), s.getId());
            } else {
                msgSKUSyncDao.updateStatusById(DefaultStatus.CANCELED.getValue(), s.getId());
            }
        } catch (BusinessException e) {
            log.debug(e.getMessage());
            log.error("", e);
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("sendSingleSkuToSf Exception:" + s.getSkuCode(), e);
            }
            // log.error("", e);
            throw e;
        }
    }

    @Override
    public WmsVendorResponse sendVendorToSf() throws Exception {
        WmsVendorRequest request = new WmsVendorRequest();
        request.setCheckword(getSfCheckWork());
        request.setCompany(SF_COMPANY);
        request.setVendor(SOURCEID);
        request.setVendor_name("宝尊上海");
        request.setAddress("上海市闸北区万荣路1188号");
        request.setState("上海市");
        request.setCity("上海市");
        request.setCountry("中国");
        request.setInterface_action_code("NEW");
        try {
            // WmsVendorResponse response = SfWarehouseWebserviceClient.wmsVendor(request);
            WmsVendorResponse response = sfOrderClient.wmsVendor(request);
            if (response.getResult().equals("1")) {
                log.debug("sendVendorToSf---->Success!" + response.getVendor() + ":" + response.getRemark());
            } else {
                log.debug("sendVendorToSf---->Failed!" + response.getVendor() + ":" + response.getRemark());
            }
            return response;
        } catch (BusinessException e) {
            log.debug(e.getMessage());
            log.error("", e);
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("sendVendorToSf Exception:", e);
            }
            // log.error("", e);
            throw e;
        }

    }

    @Override
    public void sendInboundOrderToSf(MsgInboundOrder inOrder) throws Exception {
        WmsPurchaseOrderRequest request = new WmsPurchaseOrderRequest();
        request.setCheckword(getSfCheckWork());
        WmsPurchaseOrderRequestHeader header = new WmsPurchaseOrderRequestHeader();
        header.setCompany(SF_COMPANY);
        header.setWarehouse(inOrder.getSourceWh());// 固定的几个值
        header.setErp_order_num(inOrder.getStaCode());
        header.setErp_order_type(convertStaTypeToSfType(inOrder.getType()));
        header.setOrder_date(FormatUtil.formatDate(inOrder.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        header.setScheduled_receipt_date(inOrder.getPlanArriveTime() == null ? FormatUtil.formatDate(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd hh:mm:ss") : FormatUtil.formatDate(inOrder.getPlanArriveTime(), "yyyy-MM-dd hh:mm:ss"));// 大于当前时间就行
        header.setSource_id(SOURCEID);// 固定值********************************************************************
        if (convertStaTypeToSfType(inOrder.getType()).equals("退货入库")) {
            // TODO SF仓库对接待调整单据号
            String code = "";
            if (code == null || code.equals("")) {
                // 原始出库单据号**********
                throw new BusinessException("");
            }
            header.setOriginal_order_no(code);// 待定
        }
        request.setHeader(header);
        List<MsgInboundOrderLine> lineList = msgInboundOrderLineDao.fomdMsgInboundOrderLineByOId(inOrder.getId());
        if (lineList.size() > 0) {
            WmsPurchaseOrderRequestDetailList list = new WmsPurchaseOrderRequestDetailList();
            List<WmsPurchaseOrderRequestDetail> detailList = new ArrayList<WmsPurchaseOrderRequestDetail>();
            int i = 1;
            for (MsgInboundOrderLine line : lineList) {
                WmsPurchaseOrderRequestDetail detail = new WmsPurchaseOrderRequestDetail();
                detail.setErp_order_line_num(new BigDecimal(i++));
                detail.setItem(line.getSku().getCode());
                detail.setTotal_qty(new BigDecimal(line.getQty()));
                detailList.add(detail);
            }
            list.setItem(detailList);
            request.setDetailList(list);
        } else {
            // 入库通知明细为空；
            log.error("inbound order notice msg line is null" + inOrder.getId());
            throw new BusinessException(ErrorCode.SF_INBOUND_ORDER_LINE_IS_NULL, new Object[] {inOrder.getId()});
        }
        try {
            WmsPurchaseOrderResponse response = SfWarehouseWebserviceClient.wmsPurchaseOrder(request);
            if (response.getResult().equals("1")) {
                MsgInboundOrder order = msgInboundOrderDao.getByPrimaryKey(inOrder.getId());
                order.setStatus(DefaultStatus.FINISHED);
                log.debug("sendInboundOrderToSf---->Success!" + response.getOrderid() + ":" + response.getRemark());
            } else {
                MsgInboundOrder order = msgInboundOrderDao.getByPrimaryKey(inOrder.getId());
                order.setStatus(DefaultStatus.CANCELED);
                log.debug("sendInboundOrderToSf---->Failed!" + response.getOrderid() + ":" + response.getRemark());
            }
        } catch (BusinessException e) {
            log.warn("sendInboundOrderToSf ,order ：{}, error code : {}", inOrder.getStaCode(),e.getErrorCode());
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("sendInboundOrderToSf Exception:" + inOrder.getStaCode(), e);
            }
            throw e;
        }
    }

    /**
     * 入库单(采购订单)接口
     */
    @Override
    public void sendInboundOrderToSf1(MsgInboundOrder inOrder) throws Exception {
        WmsPurchaseOrderRequest request = new WmsPurchaseOrderRequest();
        request.setCheckword(getSfCheckWork());
        WmsPurchaseOrderRequestHeader header = new WmsPurchaseOrderRequestHeader();
        if (inOrder == null) {
            log.warn("sta is not found error ,inOrder is null");
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        StockTransApplication sta = staDao.getByCode(inOrder.getStaCode());
        if (sta == null) {
            log.warn("sta is not found error , sta : {}", inOrder.getStaCode());
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        BiChannel bi = biChannelDao.getByCode(sta.getOwner());
        if (bi.getVmiWHSource() == null) {
            // 渠道货主为空
            log.error("inbound order notice msg VmiWHSource is null " + inOrder.getId());
            throw new BusinessException(ErrorCode.SF_INBOUND_ORDER_FLAG_IS_NULL, new Object[] {inOrder.getId()});
        }
        header.setCompany(bi.getVmiWHSource());
        header.setWarehouse(inOrder.getSourceWh());// 固定的几个值
        header.setErp_order_num(inOrder.getStaCode());
        header.setErp_order_type(convertStaTypeToSfType(inOrder.getType()));
        header.setOrder_date(FormatUtil.formatDate(inOrder.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        header.setScheduled_receipt_date(inOrder.getPlanArriveTime() == null ? FormatUtil.formatDate(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd hh:mm:ss") : FormatUtil.formatDate(inOrder.getPlanArriveTime(), "yyyy-MM-dd hh:mm:ss"));// 大于当前时间就行
        header.setSource_id(SOURCEID);// 固定值********************************************************************
        header.setUser_def5(sta.getSlipCode2() == null ? sta.getRefSlipCode() : sta.getSlipCode2());
        // header.setOriginal_order_no(sta.getSlipCode2() == null ? sta.getRefSlipCode() :
        // sta.getSlipCode2());
        // header.setUser_def50(sta.getSlipCode2());
        if (sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            // if (convertStaTypeToSfType(inOrder.getType()).equals("退货入库")) {
            // TODO SF仓库对接待调整单据号
            // String code = "";
            // if (code == null || code.equals("")) {
            // // 原始出库单据号**********
            // throw new BusinessException("");
            // }
            // header.setOriginal_order_no(code);// 待定
            // header.setOriginal_order_no(sta.getStaDeliveryInfo().getRemark() == null ?
            // sta.getRefSlipCode() : sta.getStaDeliveryInfo().getRemark());// 待定
            header.setUser_def5(sta.getSlipCode2());
        }
        request.setHeader(header);
        List<MsgInboundOrderLine> lineList = msgInboundOrderLineDao.fomdMsgInboundOrderLineByOId(inOrder.getId());
        if (lineList.size() > 0) {
            WmsPurchaseOrderRequestDetailList list = new WmsPurchaseOrderRequestDetailList();
            List<WmsPurchaseOrderRequestDetail> detailList = new ArrayList<WmsPurchaseOrderRequestDetail>();
            int i = 1;
            for (MsgInboundOrderLine line : lineList) {
                WmsPurchaseOrderRequestDetail detail = new WmsPurchaseOrderRequestDetail();
                detail.setErp_order_line_num(new BigDecimal(i++));
                detail.setItem(line.getSku().getCode());
                detail.setTotal_qty(new BigDecimal(line.getQty()));
                detailList.add(detail);
            }
            list.setItem(detailList);
            request.setDetailList(list);
        } else {
            // 入库通知明细为空；
            log.error("inbound order notice msg line is null" + inOrder.getId());
            throw new BusinessException(ErrorCode.SF_INBOUND_ORDER_LINE_IS_NULL, new Object[] {inOrder.getId()});
        }
        try {
            WmsPurchaseOrderResponse response = sfOrderClient.wmsPurchaseOrder(request);
            if (response.getResult().equals("1")) {
                MsgInboundOrder order = msgInboundOrderDao.getByPrimaryKey(inOrder.getId());
                order.setStatus(DefaultStatus.FINISHED);
                log.debug("sendInboundOrderToSf---->Success!" + response.getOrderid() + ":" + response.getRemark());
            } else {
                MsgInboundOrder order = msgInboundOrderDao.getByPrimaryKey(inOrder.getId());
                order.setStatus(DefaultStatus.CANCELED);
                log.debug("sendInboundOrderToSf---->Failed!" + response.getOrderid() + ":" + response.getRemark());
            }
        } catch (BusinessException e) {
            log.warn("sendInboundOrderToSf1 , code :{},error :{}",inOrder.getStaCode(),e.getErrorCode());
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("sendInboundOrderToSf1 Exception:" + inOrder.getStaCode(), e);
            }
            throw e;
        }
    }

    private String convertStaTypeToSfType(StockTransApplicationType type) {
        switch (type.getValue()) {
            case 11:
                return "采购入库";
            case 13:
                return "其它入库";
            case 14:
                return "其它入库";
            case 12:
                return "其它入库";
            case 15:
                return "赠品入库";
            case 16:
                return "其它入库";
            case 41:
                // return "退货入库";
                return "其它入库";
            case 21:
            case 25:
                return "销售订单";
            case 81:
                return "其它入库";
            case 22:
            case 101:
                return "正常出库";
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void cancelOrderNoticeSf(String code) throws Exception {
        WmsCancelSailOrderRequest request = new WmsCancelSailOrderRequest();
        request.setCheckword(getSfCheckWork());
        request.setCompany(SF_COMPANY);
        request.setOrderid(code);
        try {
            // WmsCancelSailOrderResponse response =
            // SfWarehouseWebserviceClient.wmsCanclSailOrder(request);
            WmsCancelSailOrderResponse response = sfOrderClient.wmsCanclSailOrder(request);
            if (response.getResult().equals("1")) {
                log.debug("cancelOrderNoticeSf---->Success!" + response.getOrderid() + ":" + response.getRemark());
            } else {
                log.debug("cancelOrderNoticeSf---->Failed!" + response.getOrderid() + ":" + response.getRemark());
            }
        } catch (BusinessException e) {
            log.debug(e.getMessage());
            log.error("", e);
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("cancelOrderNoticeSf Exception:" + code, e);
            }
            throw e;
        }

    }

    @Override
    public void sendOutboundOrderToSf(MsgOutboundOrder outOrder) throws Exception {
        WmsSailOrderRequest request = new WmsSailOrderRequest();
        request.setCheckword(getSfCheckWork());
        WmsSailOrderRequestHeader header = new WmsSailOrderRequestHeader();
        header.setCompany(SF_COMPANY);
        header.setWarehouse(outOrder.getSourceWh());// 待定
        header.setErp_order(outOrder.getStaCode());
        header.setOrder_date(FormatUtil.formatDate(outOrder.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        header.setShip_to_name("个人");// 待定 收件人公司
        header.setShip_to_attention_to(outOrder.getReceiver());
        header.setShip_to_country(outOrder.getCountry());
        header.setShip_to_province(outOrder.getProvince());
        header.setShip_to_city(outOrder.getCity());
        header.setShip_to_area(outOrder.getDistrict());
        header.setShip_to_address(outOrder.getAddress());
        header.setShip_to_postal_code(outOrder.getZipcode());
        header.setShip_to_phone_num(outOrder.getMobile() == null ? outOrder.getTelePhone() : outOrder.getMobile());
        header.setShip_to_tel_num(outOrder.getTelePhone());
        header.setMonthly_account(SF_MONTHLY_ACCOUNT);
        header.setOrder_type(convertStaTypeToSfType(StockTransApplicationType.valueOf(outOrder.getStaType())));
        request.setHeader(header);
        List<MsgOutboundOrderLine> lineList = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(outOrder.getId());
        if (lineList == null || lineList.size() == 0) {
            throw new BusinessException(ErrorCode.SF_OUTBOUND_ORDER_LINE_IS_NULL, new Object[] {outOrder.getId()});
        } else {
            WmsSailOrderRequestDetailList list = new WmsSailOrderRequestDetailList();
            List<WmsSailOrderRequestDetail> detailList = new ArrayList<WmsSailOrderRequestDetail>();
            int i = 1;
            for (MsgOutboundOrderLine line : lineList) {
                WmsSailOrderRequestDetail detail = new WmsSailOrderRequestDetail();
                detail.setErp_order_line_num(i++);
                detail.setItem(line.getSku().getCode());
                detail.setItem_name(line.getSku().getName());
                detail.setUom(SF_SKU_UNIT);
                detail.setQty(new BigDecimal(line.getQty()));
                detailList.add(detail);
            }
            list.setItem(detailList);
            request.setDetailList(list);
        }
        try {
            WmsSailOrderResponse response = SfWarehouseWebserviceClient.wmsSailOrder(request);
            if (response.getResult().equals("1")) {
                MsgOutboundOrder order = msgOutboundOrderDao.getByPrimaryKey(outOrder.getId());
                order.setStatus(DefaultStatus.FINISHED);
                order.setUpdateTime(new Date());
                log.debug("sendOutboundOrderToSf---->Success!" + response.getOrderid() + ":" + response.getRemark());
            } else {
                MsgOutboundOrder order = msgOutboundOrderDao.getByPrimaryKey(outOrder.getId());
                order.setStatus(DefaultStatus.CANCELED);
                order.setUpdateTime(new Date());
                log.debug("sendOutboundOrderToSf---->Failed!" + response.getOrderid() + ":" + response.getRemark());
            }
        } catch (BusinessException e) {
            log.debug(e.getMessage());
            log.error("", e);
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("sendOutboundOrderToSf Exception:" + outOrder.getStaCode(), e);
            }
            throw e;
        }

    }


    @Override
    public void sendOutboundOrderToSf1(MsgOutboundOrder outOrder) throws Exception {
        WmsSailOrderRequest request = new WmsSailOrderRequest();
        request.setCheckword(getSfCheckWork());
        WmsSailOrderRequestHeader header = new WmsSailOrderRequestHeader();
        StockTransApplication sta = staDao.getByCode(outOrder.getStaCode());
        BiChannel bi = biChannelDao.getByCode(sta.getOwner());
        if (bi.getVmiWHSource() == null) {
            // 渠道货主为空
            log.error("outbound order notice msg VmiWHSource is null " + outOrder.getId());
            throw new BusinessException(ErrorCode.SF_OUTBOUND_ORDER_FLAG_IS_NULL, new Object[] {outOrder.getId()});
        }

        StaDeliveryInfo si = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
        header.setCompany(bi.getVmiWHSource());
        header.setWarehouse(outOrder.getSourceWh());
        header.setErp_order(outOrder.getStaCode());
        header.setOrder_type(convertStaTypeToSfType(StockTransApplicationType.valueOf(outOrder.getStaType())));
        header.setOrder_date(FormatUtil.formatDate(outOrder.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        header.setShip_to_name("个人");// 待定 收件人公司
        header.setShip_to_attention_to(outOrder.getReceiver());
        header.setShip_to_country(outOrder.getCountry());
        header.setShip_to_province(outOrder.getProvince());
        header.setShip_to_city(outOrder.getCity());
        header.setShip_to_area(outOrder.getDistrict());
        header.setShip_to_address(outOrder.getAddress());
        header.setShip_to_postal_code(outOrder.getZipcode());
        header.setShip_to_phone_num(outOrder.getMobile() == null ? outOrder.getTelePhone() : outOrder.getMobile());
        header.setShip_to_tel_num(outOrder.getTelePhone());
        header.setMonthly_account(bi.getSfFlag());

        BigDecimal total = (sta.getOrderTotalActual() == null ? new BigDecimal(0) : sta.getOrderTotalActual()).add((sta.getOrderTransferFree() == null ? new BigDecimal(0) : sta.getOrderTransferFree()));
        header.setOrder_total_amount(sta.getTotalActual());
        header.setFreight(sta.getOrderTransferFree());
        if (si.getIsCod() != null && si.getIsCod()) {
            header.setCod("Y");
            header.setAmount(total);
            header.setPayment_of_charge("收方付");
        } else {
            header.setPayment_of_charge("寄付");
        }
        DecimalFormat df = new DecimalFormat("0.00");
        header.setDeclared_value(sta.getTotalActual() == null ? df.format(new BigDecimal(0)) : df.format(sta.getTotalActual()));
        TransLpcodeWhRef wr = transLpcodeWhRefDao.getWhCarrierByLpCodeAndSource(outOrder.getLpCode(), Constants.VIM_WH_SOURCE_SF);
        if (wr != null) {
            header.setCarrier(wr.getCarrier());
            header.setCarrier_service(wr.getCarrierService());
            if (outOrder.getProvince().contains("上海")) {
                header.setCarrier_service(LYZP);
                if (outOrder.getDistrict() != null && (outOrder.getDistrict().contains("崇明") || outOrder.getDistrict().contains("长兴") || outOrder.getDistrict().contains("横沙"))) {
                    header.setCarrier_service(SXSP);
                }
            } else {
                header.setCarrier_service(SXSP);
            }
        }
        // 发票信息不确定，暂时保留
        /*
         * StaInvoice staInvoice = staInvoiceDao.getByPrimaryKey(sta.getId()); if
         * (outOrder.getIsNeedInvoice() != null && outOrder.getIsNeedInvoice() && staInvoice != null
         * ) { header.setInvoice("Y"); header.setInvoice_title(staInvoice.getPayer());
         * header.setInvoice_content(staInvoice.getMemo()); header.setInvoice_type("普通发票"); }
         */
        header.setUser_def50(sta.getSlipCode2() == null ? sta.getRefSlipCode() : sta.getSlipCode2());
        request.setHeader(header);
        List<MsgOutboundOrderLine> lineList = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(outOrder.getId());
        if (lineList == null || lineList.size() == 0) {
            throw new BusinessException(ErrorCode.SF_OUTBOUND_ORDER_LINE_IS_NULL, new Object[] {outOrder.getId()});
        } else {
            WmsSailOrderRequestDetailList list = new WmsSailOrderRequestDetailList();
            List<WmsSailOrderRequestDetail> detailList = new ArrayList<WmsSailOrderRequestDetail>();
            int i = 1;
            for (MsgOutboundOrderLine line : lineList) {
                WmsSailOrderRequestDetail detail = new WmsSailOrderRequestDetail();
                detail.setErp_order_line_num(i++);
                detail.setItem(line.getSku().getCode());
                detail.setItem_name(line.getSku().getName());
                detail.setUom(SF_SKU_UNIT);
                detail.setQty(new BigDecimal(line.getQty()));
                detailList.add(detail);
            }
            list.setItem(detailList);
            request.setDetailList(list);
        }
        try {
            WmsSailOrderResponse response = sfOrderClient.wmsSailOrder(request);
            if (response.getResult().equals("1")) {
                MsgOutboundOrder order = msgOutboundOrderDao.getByPrimaryKey(outOrder.getId());
                order.setStatus(DefaultStatus.FINISHED);
                order.setUpdateTime(new Date());
                log.debug("sendOutboundOrderToSf---->Success!" + response.getOrderid() + ":" + response.getRemark());
            } else {
                MsgOutboundOrder order = msgOutboundOrderDao.getByPrimaryKey(outOrder.getId());
                order.setStatus(DefaultStatus.CANCELED);
                order.setUpdateTime(new Date());
                log.debug("sendOutboundOrderToSf---->Failed!" + response.getOrderid() + ":" + response.getRemark());
            }
        } catch (BusinessException e) {
            log.warn("sendOutboundOrderToSf1,sta code : {},error: {}",outOrder.getStaCode(),e.getErrorCode());
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("sendOutboundOrderToSf1 Exception:" + outOrder.getStaCode(), e);
            }
            throw e;
        }

    }

    @Override
    public void wmsPurchaseOrderPushInfo(String requestXml) {
        if (requestXml == null) {
            throw new BusinessException(ErrorCode.SF_INTERFACE_XML_IS_NULL);
        }
        WmsPurchaseOrderPushInfoRequest request = (WmsPurchaseOrderPushInfoRequest) MarshallerUtil.buildJaxb(WmsPurchaseOrderPushInfoRequest.class, requestXml);
        WmsPurchaseOrderPushInfoRequestHeader header = request.getHeader();
        StockTransApplication sta = staDao.findStaByCode(header.getErp_order_num());
        if (sta == null) {
            // 未找到对应的入库单
            throw new BusinessException(ErrorCode.SF_ORDER_NOT_FOUND);
        }
        MsgRtnInboundOrder mri = new MsgRtnInboundOrder();
        mri.setCreateTime(new Date());
        mri.setStaCode(header.getErp_order_num());
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        mri.setSource(wh.getVmiSource());
        mri.setSourceWh(wh.getVmiSourceWh());
        mri.setStatus(DefaultStatus.CREATED);
        mri.setType(sta.getType().getValue());
        msgRtnInboundOrderDao.save(mri);
        msgRtnInboundOrderDao.flush();
        WmsPurchaseOrderPushInfoRequestDetailList list = request.getDetailList();
        List<WmsPurchaseOrderPushInfoRequestItem> detailList = list.getItem();
        for (WmsPurchaseOrderPushInfoRequestItem item : detailList) {
            MsgRtnInboundOrderLine line = new MsgRtnInboundOrderLine();
            line.setSkuCode(item.getSku_no());
            line.setSkuId(skuDao.getByCode(item.getSku_no()) == null ? null : skuDao.getByCode(item.getSku_no()).getId());
            line.setQty(item.getQty().longValue());
            if (item.getInventory_sts() == null || item.getInventory_sts().equals("") || !(item.getInventory_sts().equals("10") || item.getInventory_sts().equals("20"))) {
                throw new BusinessException(ErrorCode.SF_INTERFACE_INVSTATUS_ERROR);
            }
            InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu(item.getInventory_sts().equals("10") == true ? "良品" : "残次品", sta.getMainWarehouse().getId());
            if (invStatus == null) {
                throw new BusinessException(ErrorCode.SF_INVSTATUS_WMS_ERROR);
            }
            line.setInvStatus(invStatus);
            line.setMsgRtnInOrder(mri);
            msgRtnInboundOrderLineDao.save(line);
            msgRtnInboundOrderLineDao.flush();
            SerialNumber serialNumber = item.getSerialNumberList();
            if (serialNumber != null && serialNumber.getSerial_number() != null && serialNumber.getSerial_number().size() > 0) {
                List<String> sList = serialNumber.getSerial_number();
                if (sList.size() != line.getQty()) {
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
    }

    @Override
    public void wmsInventoryAdjustPushInfo(String requestXml) {
        if (requestXml == null) {
            throw new BusinessException(ErrorCode.SF_INTERFACE_XML_IS_NULL);
        }
        WmsInventoryAdjustPushInfoRequest request = (WmsInventoryAdjustPushInfoRequest) MarshallerUtil.buildJaxb(WmsInventoryAdjustPushInfoRequest.class, requestXml);
        WmsInventoryAdjustPushInfoAdjustList al = request.getAdjustlist();
        if (al == null || al.getAdjust() == null || al.getAdjust().size() == 0) {
            throw new BusinessException(ErrorCode.SF_ADJUST_LINE_ERROR);
        }
        MsgRtnAdjustment mj = new MsgRtnAdjustment();
        mj.setCreateTime(new Date());
        mj.setStatus(DefaultStatus.CREATED);
        mj.setSource(Constants.VIM_WH_SOURCE_SF);
        mj.setSourceWh(request.getWarehouse());
        mj.setIdsKey("SF" + (new Date().getTime() % 1000000));
        mj.setOwnerSource(request.getCompany());
        msgRtnAdjustmentDao.save(mj);
        List<WmsInventoryAdjustPushInfoAdjust> list = al.getAdjust();
        for (WmsInventoryAdjustPushInfoAdjust adj : list) {
            MsgRtnAdjustmentLine line = new MsgRtnAdjustmentLine();
            if (adj.getDirection() == null || !(adj.getDirection().equals("From") || adj.getDirection().equals("To"))) {
                throw new BusinessException(ErrorCode.SF_ADJUST_LINE_DIRECTION_ERROR);
            }
            line.setAdjustment(mj);
            if (adj.getDirection().equals("From")) {
                line.setQty(-adj.getQuantity().longValue());
            } else {
                line.setQty(adj.getQuantity().longValue());
            }
            Sku sku = skuDao.getByCode(adj.getItem());
            if (sku == null) {
                throw new BusinessException(ErrorCode.SF_INTERFACE_SKU_ERROR);
            } else {
                line.setSkuId(sku.getId());
            }
            if (adj.getInventory_sts() == null || !(adj.getInventory_sts().equals("10") || adj.getInventory_sts().equals("20"))) {
                throw new BusinessException(ErrorCode.SF_INTERFACE_INVSTATUS_ERROR);
            }
            InventoryStatus status = inventoryStatusDao.getByNameAndWarehouse(adj.getInventory_sts().equals("10") == true ? "良品" : "残次品", Constants.VIM_WH_SOURCE_SF, request.getWarehouse());
            if (status == null) {
                throw new BusinessException(ErrorCode.SF_INVSTATUS_WMS_ERROR);
            }
            line.setInvStatus(status);
            msgRtnAdjustmentLineDao.save(line);
        }

    }

    @Override
    public void wmsSailOrderPushInfo(String requestXml) {
        if (requestXml == null) {
            throw new BusinessException(ErrorCode.SF_INTERFACE_XML_IS_NULL);
        }
        WmsSailOrderPushInfoRequest request = (WmsSailOrderPushInfoRequest) MarshallerUtil.buildJaxb(WmsSailOrderPushInfoRequest.class, requestXml);
        WmsSailOrderPushInfoRequestHeader header = request.getHeader();
        StockTransApplication sta = staDao.findStaByCode(header.getErpOrder());
        if (sta == null) {
            // 未找到对应的入库单
            throw new BusinessException(ErrorCode.SF_ORDER_NOT_FOUND);
        }
        LogisticsTracking lt = new LogisticsTracking();
        lt.setCompany(header.getCompany());
        lt.setWarehouse(header.getWarehouse());
        lt.setErpOrder(header.getErpOrder());
        lt.setShipMentId(header.getShipment_id());
        lt.setWayBillNo(header.getWaybill_no());
        lt.setActualShipDateTime(FormatUtil.getDate(header.getActual_ship_date_time()));
        lt.setCarrier(header.getCarrier());
        lt.setCarrierService(header.getCarrier_service());
        lt.setUserStamp(header.getUser_stamp());
        lt.setStatusTime(FormatUtil.getDate(header.getStatus_time()));
        lt.setStatusCode(header.getStatus_code());
        lt.setStatusRemark(header.getStatus_remark());
        lt.setSta(sta);
        if (header.getStatus_code().equals("700")) {
            header.setCarrier("顺丰速运");
            lt.setLpCode(getLpCodeByName(header.getCarrier()));
        } else {
            lt.setLpCode(getLpCodeByName(header.getCarrier()));
        }
        logisticsTrackingDao.save(lt);

        if (header.getStatus_code().equals("700")) {
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            MsgRtnOutbound mo = new MsgRtnOutbound();
            mo.setCreateTime(new Date());
            mo.setUpdateTime(new Date());
            mo.setStaCode(header.getErpOrder());

            if (isSfUp() == null) {// 老逻辑
                if (sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES) || sta.getType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST) || sta.getType().equals(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES)) {
                    mo.setStatus(DefaultStatus.CREATED);
                } else {
                    mo.setStatus(DefaultStatus.INEXECUTION);
                }
            } else {
                if (sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES) || sta.getType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST) || sta.getType().equals(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES)) {
                    // mo.setStatus(DefaultStatus.CREATED);
                    mo.setStatus(DefaultStatus.INEXECUTION);
                } else {
                    mo.setStatus(DefaultStatus.INEXECUTION);
                }
            }


            TransLpcodeWhRef wr = transLpcodeWhRefDao.getLpCodeByWhAndCarrier(Constants.VIM_WH_SOURCE_SF, header.getCarrier());
            mo.setLpCode(wr.getLpCode());
            mo.setTrackName(header.getCarrier());
            mo.setTrackingNo(header.getWaybill_no());
            mo.setSource(wh.getVmiSource());
            mo.setSourceWh(wh.getVmiSourceWh());
            mo.setSfCode(header.getShipment_id());
            msgRtnOutboundDao.save(mo);
        } else if (header.getStatus_code().equals("900")) {
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            MsgRtnOutbound mo = new MsgRtnOutbound();
            mo.setCreateTime(new Date());
            mo.setUpdateTime(new Date());
            mo.setStaCode(header.getErpOrder());
            // if (sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES) ||
            // sta.getType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST) ||
            // sta.getType().equals(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES)) {
            // mo.setStatus(DefaultStatus.SENT);
            // } else {
            // mo.setStatus(DefaultStatus.CREATED);
            // }
            //
            if (isSfUp() == null) {// 老逻辑
                if (sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES) || sta.getType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST) || sta.getType().equals(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES)) {
                    mo.setStatus(DefaultStatus.SENT);
                } else {
                    mo.setStatus(DefaultStatus.INEXECUTION);
                }
            } else {
                if (sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES) || sta.getType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST) || sta.getType().equals(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES)) {
                    mo.setStatus(DefaultStatus.CREATED);
                } else {
                    mo.setStatus(DefaultStatus.INEXECUTION);
                }
            }

            TransLpcodeWhRef wr = transLpcodeWhRefDao.getLpCodeByWhAndCarrier(Constants.VIM_WH_SOURCE_SF, header.getCarrier());
            mo.setLpCode(wr.getLpCode());
            mo.setTrackName(header.getCarrier());
            mo.setTrackingNo(header.getWaybill_no());
            mo.setSource(wh.getVmiSource());
            mo.setSourceWh(wh.getVmiSourceWh());
            msgRtnOutboundDao.save(mo);
            msgRtnOutboundDao.flush();
            WmsSailOrderPushInfoRequestDetailList detailList = request.getDetailList();
            if (detailList == null || detailList.getItem() == null || detailList.getItem().size() == 0) {
                throw new BusinessException(ErrorCode.SF_OUT_DETAIL_IS_NULL);
            } else {
                List<WmsSailOrderPushInfoRequestDetailItem> di = detailList.getItem();
                for (WmsSailOrderPushInfoRequestDetailItem item : di) {
                    MsgRtnOutboundLine line = new MsgRtnOutboundLine();
                    line.setMsgOutbound(mo);
                    line.setSkuCode(item.getItem());
                    line.setQty(item.getQuantity().longValue());
                    line.setBarCode(skuDao.getByCode(item.getItem()) == null ? null : skuDao.getByCode(item.getItem()).getBarCode());
                    msgRtnOutboundLineDao.save(line);
                }
            }
            WmsSailOrderPushInfoRequestContainerList cl = request.getContainerList();
            if (cl == null || cl.getItem() == null || cl.getItem().size() == 0) {
                throw new BusinessException(ErrorCode.SF_OUT_CONTAINER_IS_NULL);
            } else {
                List<WmsSailOrderPushInfoRequestContainerItem> clist = cl.getItem();
                for (WmsSailOrderPushInfoRequestContainerItem cci : clist) {
                    MsgRtnOutboundContainerLine line = new MsgRtnOutboundContainerLine();
                    line.setMsgRtnOutbound(mo);
                    line.setContainerId(cci.getContainer_id());
                    line.setSkuCode(cci.getItem());
                    line.setSku(skuDao.getByCode(cci.getItem()) == null ? null : skuDao.getByCode(cci.getItem()));
                    line.setBatchCode(cci.getLot());
                    line.setQty(cci.getQuantity().longValue());
                    line.setQtyUm(cci.getQuantity_um());
                    line.setWeight(new BigDecimal(cci.getWeight().floatValue()));
                    line.setWeightUm(cci.getWeight_um());
                    line.setUserStamp(cci.getUser_stamp());
                    msgRtnOutboundContainerLineDao.save(line);
                    SerialNumber sl = cci.getSerialNumberList();
                    if (sl != null && sl.getSerial_number() != null && sl.getSerial_number().size() > 0) {
                        List<String> slist = sl.getSerial_number();
                        if (slist.size() != cci.getQuantity().intValue()) {
                            throw new BusinessException(ErrorCode.SF_SN_ERROR);
                        }
                        for (String s : slist) {
                            RtnSnDetail sd = new RtnSnDetail();
                            sd.setSn(s);
                            sd.setOutLine(line);
                            rtnSnDetailDao.save(sd);
                        }
                    }

                }
            }
        }
    }

    /**
     * 根据顺丰物流商 对应WMS物流编号
     * 
     * @param carrier
     * @return
     */
    private String getLpCodeByName(String carrier) {
        if (carrier == null) {
            throw new BusinessException(ErrorCode.SF_CARRIER_ERROR);
        }
        if (carrier.equals("顺丰速运")) {
            return "SF";
        }
        return "";
    }


    /**
     * SF库存调整任务
     */
    public InventoryCheck vmiAdjustMentRtn(MsgRtnAdjustment msgRtnADJ) throws Exception {
        Warehouse wh = warehouseDao.getWHByVmiSource(msgRtnADJ.getSource(), msgRtnADJ.getSourceWh());
        if (wh == null) {
            log.error("SF->WMS Adjustment error ! Warehouse is null");
            throw new BusinessException();
        }

        List<BiChannel> shopList = channelDao.getAllRefShopByWhOuId(wh.getOu().getId());
        if (shopList == null || shopList.size() == 0) {
            log.error("SF->WMS Adjustment error ! Shop is null");
            throw new BusinessException();
        }
        // if (shopList.size() != 1) {
        // log.error("IDS->WMS Adjustment error ! Shop not 1");
        // throw new BusinessException();
        // }
        // 通过SF货主查询WMS对应渠道信息
        BiChannel shop = biChannelDao.getByVmiWHSource(msgRtnADJ.getOwnerSource());
        if (shop == null) {
            log.error("SF->WMS Adjustment error ! Shop is null");
            throw new BusinessException();
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        InventoryCheck ic = new InventoryCheck();
        ic.setCreateTime(new Date());
        ic.setStatus(InventoryCheckStatus.CREATED);
        ic.setType(InventoryCheckType.VMI);
        ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));
        ic.setSlipCode(msgRtnADJ.getIdsKey());
        ic.setRemork("ids adjustment");
        ic.setOu(wh.getOu());
        ic.setShop(shop);
        invDao.save(ic);
        invDao.flush();
        InventoryCheckDifTotalLine line = null;
        InventoryCheckDifferenceLine dline = null;
        List<MsgRtnAdjustmentLine> listLine = msgRtnAdjustmentLineDao.queryLineByADJId(msgRtnADJ.getId());
        if (listLine == null || listLine.size() == 0) {
            throw new BusinessException();
        }
        for (MsgRtnAdjustmentLine adjLine : listLine) {
            wh.getOu();
            if (adjLine.getId() != null) {
                Sku sku = skuDao.getByPrimaryKey(adjLine.getSkuId());
                if (sku == null) {
                    throw new BusinessException();
                }
                Long locationId = inventoryDao.findInventoryByLocationOuIdOwnerSku(shop.getCode(), sku.getId(), wh.getOu().getId(), new SingleColumnRowMapper<Long>(Long.class));
                if (locationId == null) {
                    throw new BusinessException();
                }
                WarehouseLocation w = warehouseLocationDao.getByPrimaryKey(locationId);
                if (w == null) {
                    throw new BusinessException();
                }
                line = new InventoryCheckDifTotalLine();
                dline = new InventoryCheckDifferenceLine();
                line.setInventoryCheck(ic);
                line.setSku(sku);
                line.setStatus(adjLine.getInvStatus());
                line.setQuantity(adjLine.getQty());
                dline.setSku(sku);
                dline.setInventoryCheck(ic);
                dline.setStatus(adjLine.getInvStatus());
                dline.setQuantity(adjLine.getQty());
                dline.setOwner(shop.getCode());
                dline.setLocation(w);
                icLineDao.save(line);
                icdLineDao.save(dline);
            }
        }
        icLineDao.flush();
        icdLineDao.flush();
        try {
            ic.setStatus(InventoryCheckStatus.CHECKWHINVENTORY);
            invDao.save(ic);
            whExe.calculationAdjustingFordifferences(ic.getId(), null); // 创建
            // 调整盘点库存已确认InventoryCheck
            // whExe.occupyInventoryCheckSta(ic.getId()); // 占用
            wareHouseManagerExe.managerConfirmCheck(ic.getId(), ic.getCode(), null, false); // 占用
            // 调整盘点状态InventoryCheck 8
            ic.setStatus(InventoryCheckStatus.CONFIRMOMS);
            invDao.save(ic);
            // InventoryCheck占用库存
            wareHouseManager.confirmInventoryCheckEbs(null, ic.getId(), null, false);// 执行
            // whExe.exeAdjustingSta(ic.getId(), null); // 执行
            // updateADJStatus(msgRtnADJ, DefaultStatus.FINISHED);
        } catch (Exception e) {
            log.error("" + e);
            throw new BusinessException();
        }
        return ic;
    }

    @Override
    public void exeReplenishOutbound(MsgRtnOutbound m) {
        MsgRtnOutbound m1 = msgRtnOutboundDao.getByPrimaryKey(m.getId());
        m1.setStatus(DefaultStatus.REXEERROR);
        StockTransApplication sta = staDao.getByCode(m.getStaCode());
        wareHouseManager.snOccupiedForRtnOutbound(sta.getId());
        if (sta.getIsSn() != null && sta.getIsSn()) {
            StockTransVoucher stv = stvDao.findStvBySta(sta.getId(), new BeanPropertyRowMapper<StockTransVoucher>(StockTransVoucher.class));
            // 记录sn日志
            snLogDao.createOutboundByStvIdSql(stv.getId());
            // 删除sn号
            snDao.deleteSNByStvIdSql(stv.getId());
        }
        PackageInfo pg = packageInfoDao.findByTrackingNo(m.getTrackingNo());
        pg.setWeight(m.getWeight());
        sta.getStaDeliveryInfo().setWeight(m.getWeight());
        m1.setStatus(DefaultStatus.FINISHED);
    }

    public String getSfCheckWork() {
        String str = isSfUp();
        if (str == null) {// 老
            return SF_CHECKWORD;
        } else {
            return str;
        }
    }

    public String isSfUp() {
        String result = cache.get("sfUp");
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey("sfUp", "sfUp");
            cache.put("sfUp", op.getOptionValue(), 8 * 60 * 1000);// 8min
        }
        String str = cache.get("sfUp");
        if (str == null) {// 老
            return null;
        } else {// 新逻辑
            return str;
        }
    }



}
