package com.jumbo.wms.manager.hub2wms;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONArray;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.hub2wms.CnOrderPropertyDao;
import com.jumbo.dao.hub2wms.CnWmsOrderStatusUploadDao;
import com.jumbo.dao.hub2wms.WmsConfirmOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceLineQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderLineSnQueueDao;
import com.jumbo.dao.hub2wms.WmsRtnInOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsRtnOrderLineQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLineQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLogDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsShippingLineQueueDao;
import com.jumbo.dao.hub2wms.WmsShippingQueueDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.warehouse.AdPackageLineDealDao;
import com.jumbo.dao.warehouse.AdPackageLineDealLogDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.ReturnApplicationDao;
import com.jumbo.dao.warehouse.ReturnApplicationLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.rmi.warehouse.BaseResult;
import com.jumbo.rmi.warehouse.PackageResult;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.task.vmi.AdidasTaskImpl;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrder;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoice;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLine;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLineQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceQueue;
import com.jumbo.wms.model.hub2wms.WmsPackageHead;
import com.jumbo.wms.model.hub2wms.WmsPackageLine;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsRtnOrderLine;
import com.jumbo.wms.model.hub2wms.WmsRtnOrderLineQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLineQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLog;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsShipping;
import com.jumbo.wms.model.hub2wms.WmsShippingLine;
import com.jumbo.wms.model.hub2wms.WmsShippingLineQueue;
import com.jumbo.wms.model.hub2wms.WmsShippingQueue;
import com.jumbo.wms.model.hub2wms.threepl.CnOrderProperty;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsOrderStatusUpload;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessageTest;
import com.jumbo.wms.model.warehouse.AdPackageLineDeal;
import com.jumbo.wms.model.warehouse.AdPackageLineDealDto;
import com.jumbo.wms.model.warehouse.AdPackageLineDealLog;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.ReturnApplication;
import com.jumbo.wms.model.warehouse.ReturnApplicationCommand;
import com.jumbo.wms.model.warehouse.ReturnApplicationLine;
import com.jumbo.wms.model.warehouse.ReturnApplicationStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("rtnOrderManager")
public class RtnOrderManagerImpl extends BaseManagerImpl implements RtnOrderManager {
    /**
     * 
     */
    private static final long serialVersionUID = 6309878547028109022L;
    @Autowired
    private WmsRtnInOrderQueueDao inOrderQueueDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private WmsRtnOrderLineQueueDao orderLineQueueDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WmsOrderInvoiceQueueDao invoiceQueueDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private WmsOrderInvoiceLineQueueDao invoiceLineQueueDao;
    @Autowired
    private StaInvoiceLineDao staInvoiceLineDao;
    @Autowired
    private WmsSalesOrderLogDao logDao;
    @Autowired
    private StaDeliveryInfoDao deliveryInfoDao;
    @Autowired
    private WmsSalesOrderLineQueueDao salesOrderLineQueueDao;
    @Autowired
    private WmsConfirmOrderQueueDao confirmOrderQueueDao;
    @Autowired
    private WmsShippingQueueDao shippingQueueDao;
    @Autowired
    private WmsShippingLineQueueDao lineQueueDao;
    @Autowired
    private WmsSalesOrderQueueDao orderQueueDao;
    @Autowired
    private EventObserver eventObserver;
    @Autowired
    private ReturnApplicationDao returnApplicationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReturnApplicationLineDao returnApplicationLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WmsThreePLManager wmsThreePLManager;
    @Autowired
    private CnOrderPropertyDao cnOrderPropertyDao;
    @Autowired
    private CnWmsOrderStatusUploadDao cnWmsOrderStatusUploadDao;
    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private WmsOrderLineSnQueueDao wmsOrderLineSnQueueDao;
    @Autowired
    private AdPackageLineDealDao adPackageLineDealDao;
    @Autowired
    private AdPackageLineDealLogDao adPackageLineDealLogDao;
    
    
    public void createRtnOrder(WmsSalesOrderQueue orderQueue, WmsRtnInOrderQueue wmsRtnInOrderQueue) {
        // 判断标示为空则不执行
        if (orderQueue.getCanFlag() == null) {
            return;
        }
        // 判断换货单是否能创
        if (orderQueue.getCanFlag()) {
            if (log.isDebugEnabled()) {
                log.debug("createRtnInOrder is" + wmsRtnInOrderQueue.getOrderCode());
            }
            // 创建换货订单
            WmsConfirmOrder confirmOrder = createRtnOutOrder(orderQueue);
            // 删除数据
            deleteRtnOutOrder(orderQueue);

            // 创建退入订单
            createRtnInOrder(wmsRtnInOrderQueue, confirmOrder);
            // 删除数据
            deleteRtnOrder(wmsRtnInOrderQueue);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("deleteRtnOutOrder is");
            }
            Boolean isMq = false;
            List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_SALES_ORDER_SERVICE, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
            if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
                isMq = true;
            }

            WmsConfirmOrderQueue confirmOrderQueue = new WmsConfirmOrderQueue();
            if (isMq) {
                confirmOrderQueue.setIsMq("1");
            }
            confirmOrderQueue.setCreateTime(new Date());
            confirmOrderQueue.setIsDs(false);
            confirmOrderQueue.setMemo(orderQueue.getFlagResult());
            confirmOrderQueue.setOrderCode(orderQueue.getOrderCode());
            confirmOrderQueue.setOrderType(orderQueue.getOrderType());
            if (StringUtils.hasText(orderQueue.getFlagResult())) {
                if (orderQueue.getFlagResult().equals("订单无法匹配合适物流商!")) {
                    confirmOrderQueue.setErrorStatus("E000002");
                } else if (orderQueue.getFlagResult().equals("指定物流找不到合适发货仓!")) {
                    confirmOrderQueue.setErrorStatus("E000003");
                } else if (orderQueue.getFlagResult().equals("WMS未找到合适发货仓")) {
                    confirmOrderQueue.setErrorStatus("E000004");
                } else {
                    confirmOrderQueue.setErrorStatus("E000001");
                }
            } else {
                confirmOrderQueue.setErrorStatus("S000001");
            }
            confirmOrderQueue.setOwner(orderQueue.getOwner());
            confirmOrderQueue.setStatus(false);
            confirmOrderQueue.setSystemKey(orderQueue.getSystemKey());
            confirmOrderQueueDao.save(confirmOrderQueue);

            if (Constants.CAINIAO_DB_SYSTEM_KEY.equals(orderQueue.getSystemKey())) {
                CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(orderQueue.getOrderCode());
                if (cnOrderProperty != null) {
                    CnWmsOrderStatusUpload su = new CnWmsOrderStatusUpload();
                    su.setOrderType(cnOrderProperty.getOrderType());
                    su.setOrderCode(orderQueue.getOrderCode());
                    su.setContent(orderQueue.getFlagResult());
                    su.setOperator("宝尊仓库");
                    su.setOperateDate(new Date());
                    su.setStatus("WMS_REJECT");
                    su.setUploadStatus("0");
                    cnWmsOrderStatusUploadDao.save(su);
                }
            }

            // 删除换出数据
            deleteRtnOutOrder(orderQueue);
            // 删除换入数据
            deleteRtnOrder(wmsRtnInOrderQueue);
        }



    }

    public void deleteRtnOutOrder(WmsSalesOrderQueue orderQueue) {
        List<WmsOrderInvoiceQueue> invoices = invoiceQueueDao.findBySoId(orderQueue.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceQueue>(WmsOrderInvoiceQueue.class));
        for (WmsOrderInvoiceQueue wmsOrderInvoiceQueue : invoices) {

            List<WmsOrderInvoiceLineQueue> queueStaInvoice = invoiceLineQueueDao.findByInvoiceId(wmsOrderInvoiceQueue.getId(), new BeanPropertyRowMapper<WmsOrderInvoiceLineQueue>(WmsOrderInvoiceLineQueue.class));
            for (WmsOrderInvoiceLineQueue queueStaInvoiceLine : queueStaInvoice) {
                queueStaInvoiceLine.setWmsOrderInvoiceQueue(null);
                invoiceLineQueueDao.delete(queueStaInvoiceLine);

            }
            wmsOrderInvoiceQueue.setWmsSalesOrderQueue(null);
            invoiceQueueDao.delete(wmsOrderInvoiceQueue);
        }
        invoiceLineQueueDao.flush();
        invoiceQueueDao.flush();

        // List<WmsOrderLineSnQueue> snList =
        // wmsOrderLineSnQueueDao.findOrderSnLineQueueById(orderQueue.getId(), new
        // BeanPropertyRowMapper<WmsOrderLineSnQueue>(WmsOrderLineSnQueue.class));
        // if (snList != null) {
        // for (WmsOrderLineSnQueue sn : snList) {
        // wmsOrderLineSnQueueDao.delete(sn);
        // }
        // }

        List<WmsSalesOrderLineQueue> staLines = salesOrderLineQueueDao.getOrderLineQueueById(orderQueue.getId(), new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
        for (WmsSalesOrderLineQueue wmsRtnOrderLineQueue : staLines) {
            salesOrderLineQueueDao.delete(wmsRtnOrderLineQueue);
        }
        orderQueueDao.delete(orderQueue);
        orderQueueDao.flush();

    }

    @Transactional
    public void createRtnOrder(WmsRtnInOrderQueue inOrderQueue) {
        // 创建退入订单
        createRtnInOrder(inOrderQueue, null);
        // 记录日志
        createRtnOrderlog(inOrderQueue);
        //
        deleteRtnOrder(inOrderQueue);
    }

    public void deleteRtnOrder(WmsRtnInOrderQueue inOrderQueue) {
        WmsRtnInOrderQueue orderQueue = inOrderQueueDao.getByPrimaryKey(inOrderQueue.getId());
        List<WmsOrderInvoiceQueue> invoices = invoiceQueueDao.findByRtnId(orderQueue.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceQueue>(WmsOrderInvoiceQueue.class));
        for (WmsOrderInvoiceQueue wmsOrderInvoiceQueue : invoices) {

            List<WmsOrderInvoiceLineQueue> queueStaInvoice = invoiceLineQueueDao.findByInvoiceId(wmsOrderInvoiceQueue.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceLineQueue>(WmsOrderInvoiceLineQueue.class));
            for (WmsOrderInvoiceLineQueue queueStaInvoiceLine : queueStaInvoice) {
                queueStaInvoiceLine.setWmsOrderInvoiceQueue(null);
                invoiceLineQueueDao.delete(queueStaInvoiceLine);

            }
            wmsOrderInvoiceQueue.setWmsRtnInOrderQueue(null);
            wmsOrderInvoiceQueue.setWmsRtnInOrderQueue1(null);
            invoiceQueueDao.delete(wmsOrderInvoiceQueue);
        }
        invoiceLineQueueDao.flush();
        invoiceQueueDao.flush();
        List<WmsRtnOrderLineQueue> staLines = orderLineQueueDao.queryRtnId(orderQueue.getId(), new BeanPropertyRowMapperExt<WmsRtnOrderLineQueue>(WmsRtnOrderLineQueue.class));
        for (WmsRtnOrderLineQueue wmsRtnOrderLineQueue : staLines) {
            wmsRtnOrderLineQueue.setWmsRtnInOrderQueue(null);
            orderLineQueueDao.delete(wmsRtnOrderLineQueue);
        }
        inOrderQueueDao.delete(orderQueue);
    }

    public WmsConfirmOrder createRtnOutOrder(WmsSalesOrderQueue orderQueue) {
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        OperationUnit ou1 = operationUnitDao.getByCode(orderQueue.getWarehouseCode());
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        sta.setType(StockTransApplicationType.valueOf(orderQueue.getOrderType()));
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setRefSlipCode(orderQueue.getOrderCode());
        sta.setSlipCode1(orderQueue.getSourceOrderCode());
        sta.setOwner(orderQueue.getOwner());
        sta.setChannelList(orderQueue.getAcceptOwners());
        sta.setIsLocked(orderQueue.isLocked());
        sta.setIsNeedOccupied(true);
        sta.setMemo(orderQueue.getMemo());
        sta.setRefSlipType(SlipType.RETURN_REQUEST);
        sta.setMainWarehouse(ou1);
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setOrderTransferFree(orderQueue.getFreight());
        sta.setOrderCreateTime(orderQueue.getOrderCreateTime());
        sta.setIsLocked(true);
        sta.setActivitySource(orderQueue.getActiveCode());
        sta.setOrderTotalActual(orderQueue.getOrderAmt());
        sta.setOrderTotalBfDiscount(orderQueue.getOrderDiscount());
        sta.setTotalActual(orderQueue.getOrderAmt());
        sta.setSystemKey(orderQueue.getSystemKey());
        staDao.save(sta);
        StaDeliveryInfo di = new StaDeliveryInfo();
        di.setId(sta.getId());
        di.setCountry(orderQueue.getCountry());
        di.setProvince(orderQueue.getProvince());
        di.setProvinceEn(orderQueue.getProvince1());
        di.setCity(orderQueue.getCity());
        di.setCityEn(orderQueue.getCity1());
        di.setDistrict(orderQueue.getDistrict());
        di.setDistrictEn(orderQueue.getDistrict1());
        di.setAddress(orderQueue.getAddress());
        di.setAddressEn(orderQueue.getAddress1());
        di.setTelephone(orderQueue.getTelephone());
        di.setMobile(orderQueue.getMoblie());
        di.setReceiver(orderQueue.getReceiver());
        di.setReceiverEn(orderQueue.getReceiver1());
        di.setZipcode(orderQueue.getZipcode());
        di.setOrderUserCode(orderQueue.getOrderUserCode());
        di.setOrderUserMail(orderQueue.getOrderUserMail());
        di.setLpCode(orderQueue.getTransCode());
        di.setTrackingNo(orderQueue.getTransNo());
        di.setTransTimeType(TransTimeType.valueOf(orderQueue.getTransTimeType()));
        di.setIsCod(orderQueue.isCod());
        di.setRemark(orderQueue.getTransMemo());
        deliveryInfoDao.save(di);
        sta.setStaDeliveryInfo(di);
        staDao.save(sta);
        List<WmsSalesOrderLineQueue> staLines = salesOrderLineQueueDao.getOrderLineQueueById(orderQueue.getId(), new BeanPropertyRowMapper<WmsSalesOrderLineQueue>(WmsSalesOrderLineQueue.class));
        List<StaLine> lines = new ArrayList<StaLine>();
        for (WmsSalesOrderLineQueue salesOrderLineQueue : staLines) {
            StaLine sl = new StaLine();
            Sku sku = skuDao.getByCode(salesOrderLineQueue.getSku());
            sl.setSta(sta);
            sl.setSku(sku);
            sl.setActivitySource(sl.getActivitySource());
            sl.setSkuName(salesOrderLineQueue.getSkuName());
            sl.setOwner(salesOrderLineQueue.getOwner());
            sl.setQuantity(salesOrderLineQueue.getQty());
            sl.setExpireDate(salesOrderLineQueue.geteExpDate());
            sl.setVersion(1);
            sl.setSkuName(salesOrderLineQueue.getSkuName());
            sl.setTotalActual(salesOrderLineQueue.getLineAmt());
            sl.setListPrice(salesOrderLineQueue.getListPrice());
            sl.setUnitPrice(salesOrderLineQueue.getUnitPrice());
            sl.setLineNo(salesOrderLineQueue.getLineNo());
            sl.setOrderLineNo(salesOrderLineQueue.getOrderLineNo());
            lines.add(sl);
            staLineDao.save(sl);
        }
        List<WmsOrderInvoiceQueue> invoices = invoiceQueueDao.findBySoId(orderQueue.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceQueue>(WmsOrderInvoiceQueue.class));
        for (WmsOrderInvoiceQueue wmsOrderInvoiceQueue : invoices) {
            StaInvoice invoice = new StaInvoice();
            invoice.setSta(sta);
            invoice.setPayer(wmsOrderInvoiceQueue.getPayer());
            invoice.setAmt(wmsOrderInvoiceQueue.getAmt());
            invoice.setInvoiceCode(wmsOrderInvoiceQueue.getInvoiceCode());
            invoice.setDrawer(wmsOrderInvoiceQueue.getDrawer());
            invoice.setInvoiceDate(wmsOrderInvoiceQueue.getInvoiceDate());
            invoice.setItem(wmsOrderInvoiceQueue.getItem());
            invoice.setMemo(wmsOrderInvoiceQueue.getMemo());
            invoice.setPayee(wmsOrderInvoiceQueue.getPayee());
            invoice.setQty(wmsOrderInvoiceQueue.getQty().intValue());
            invoice.setUnitPrice(wmsOrderInvoiceQueue.getUnitPrice());
            invoice.setAddress(wmsOrderInvoiceQueue.getAddress());
            invoice.setIdentificationNumber(wmsOrderInvoiceQueue.getIdentificationNumber());
            invoice.setTelephone(wmsOrderInvoiceQueue.getTelephone());

            staInvoiceDao.save(invoice);
            List<WmsOrderInvoiceLineQueue> queueStaInvoice = invoiceLineQueueDao.findByInvoiceId(wmsOrderInvoiceQueue.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceLineQueue>(WmsOrderInvoiceLineQueue.class));
            for (WmsOrderInvoiceLineQueue queueStaInvoiceLine : queueStaInvoice) {
                StaInvoiceLine staInvoiceLine = new StaInvoiceLine();
                staInvoiceLine.setAmt(queueStaInvoiceLine.getAmt());
                staInvoiceLine.setItem(queueStaInvoiceLine.getItem());
                staInvoiceLine.setLineNO(queueStaInvoiceLine.getLineNo());
                staInvoiceLine.setQty(queueStaInvoiceLine.getQty().intValue());
                staInvoiceLine.setUnitPrice(queueStaInvoiceLine.getUnitPrice());
                staInvoiceLine.setStaInvoice(invoice);
                staInvoiceLineDao.save(staInvoiceLine);
            }
        }
        staDao.flush();
        deliveryInfoDao.flush();
        staLineDao.flush();
        staInvoiceDao.flush();
        staInvoiceLineDao.flush();
        wmsThreePLManager.createCnWmsOrderStatusUpload(sta.getId());
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        // 记录反馈数据
        return confirmOrder(sta, di, lines);
    }

    public void createRtnInOrder(WmsRtnInOrderQueue wmsRtnInOrderQueue, WmsConfirmOrder confirmOrder) {
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(wmsRtnInOrderQueue.getWarehouseId());
        sta.setRefSlipCode(wmsRtnInOrderQueue.getOrderCode());
        sta.setSlipCode1(wmsRtnInOrderQueue.getSourceOrderCode());
        sta.setOwner(wmsRtnInOrderQueue.getOwner());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        sta.setLastModifyTime(new Date());
        sta.setType(StockTransApplicationType.valueOf(wmsRtnInOrderQueue.getOrderType()));
        sta.setRefSlipType(SlipType.valueOf(3));
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setVersion(1);
        sta.setIsLocked(true);
        sta.setMainWarehouse(ou1);
        sta.setOrderSourcePlatform(wmsRtnInOrderQueue.getOrderSourcePlatform());
        sta.setPoints(wmsRtnInOrderQueue.getPoints()); // 积分
        sta.setReturnPoints(wmsRtnInOrderQueue.getReturnPoints()); // 返点积分
        sta.setIsUrgent(wmsRtnInOrderQueue.getIsUrgent()); // 是否紧急
        sta.setIsOutboundCheck(wmsRtnInOrderQueue.getIsBfOutbountCheck()); // 是否检验
        sta.setOrderType(wmsRtnInOrderQueue.getOrderType2()); // 作业类型
        sta.setSystemKey(wmsRtnInOrderQueue.getSystemKey());
        sta.setMemo(wmsRtnInOrderQueue.getMemo());
        staDao.save(sta);
        StaDeliveryInfo di = new StaDeliveryInfo();
        di.setLpCode(wmsRtnInOrderQueue.getLpcode());
        di.setCountry(wmsRtnInOrderQueue.getCountry());
        di.setProvince(wmsRtnInOrderQueue.getProvince());
        di.setCity(wmsRtnInOrderQueue.getCity());
        di.setDistrict(wmsRtnInOrderQueue.getDistrict());
        di.setAddress(wmsRtnInOrderQueue.getAddress());
        di.setTelephone(wmsRtnInOrderQueue.getTelephone());
        di.setMobile(wmsRtnInOrderQueue.getMoblie());
        di.setReceiver(wmsRtnInOrderQueue.getReceiver());
        di.setZipcode(wmsRtnInOrderQueue.getZipcode());

        di.setAddressEn(wmsRtnInOrderQueue.getAddress1());
        di.setCityEn(wmsRtnInOrderQueue.getCity1());
        di.setDistrictEn(wmsRtnInOrderQueue.getDistrict1());
        di.setProvinceEn(wmsRtnInOrderQueue.getProvince1());
        di.setReceiverEn(wmsRtnInOrderQueue.getReceiver1());
        di.setTransMemo(wmsRtnInOrderQueue.getMemo());
        di.setId(sta.getId());
        di.setLastModifyTime(new Date());
        di.setTrackingNo(wmsRtnInOrderQueue.getTrackingNo());// 关联退换货快递单号
        deliveryInfoDao.save(di);
        sta.setStaDeliveryInfo(di);
        staDao.save(sta);

        List<WmsRtnOrderLineQueue> staLines = orderLineQueueDao.queryRtnId(wmsRtnInOrderQueue.getId(), new BeanPropertyRowMapperExt<WmsRtnOrderLineQueue>(WmsRtnOrderLineQueue.class));
        List<StaLine> lines = new ArrayList<StaLine>();
        Boolean msg = false;
        for (WmsRtnOrderLineQueue wmsRtnOrderLineQueue : staLines) {
            StaLine sl = new StaLine();
            Sku sku = skuDao.getByCode(wmsRtnOrderLineQueue.getSku());
            sl.setSta(sta);
            sl.setSku(sku);
            sl.setOwner(wmsRtnOrderLineQueue.getOwner());
            sl.setQuantity(wmsRtnOrderLineQueue.getQty());
            sl.setVersion(1);
            sl.setSkuName(wmsRtnOrderLineQueue.getSkuName());
            sl.setTotalActual(wmsRtnOrderLineQueue.getLineAmt());
            sl.setOrderTotalActual(wmsRtnOrderLineQueue.getLineAmt());
            sl.setListPrice(wmsRtnOrderLineQueue.getListPrice());
            sl.setUnitPrice(wmsRtnOrderLineQueue.getUnitPrice());
            sl.setLineSize(wmsRtnOrderLineQueue.getLineSize()); // 商品尺寸
            sl.setColor(wmsRtnOrderLineQueue.getColor()); // 商品颜色
            sl.setOrderQty(wmsRtnOrderLineQueue.getOrderQty()); // 平台订单计划量
            sl.setLineNo(wmsRtnOrderLineQueue.getLineNo());
            sl.setOrderLineNo(wmsRtnOrderLineQueue.getOrderLineNo()); // 平台订单行号
            String statusName = "";
            if (wmsRtnOrderLineQueue != null) {
                if (AdidasTaskImpl.AD_CUSTOMER_CODE.equals(wmsRtnInOrderQueue.getSystemKey())) {
                    try {
                        msg = staLineDao.findStaIsSpecailSku(wmsRtnInOrderQueue.getSourceOrderCode(), sku.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (msg) {
                        statusName = "残次品";
                    } else {
                        if ("normal".equals(wmsRtnOrderLineQueue.getInvStatus())) {
                            statusName = "良品";
                        } else if ("damage".equals(wmsRtnOrderLineQueue.getInvStatus())) {
                            statusName = "残次品";
                        } else {
                            statusName = "待处理品";
                        }
                    }
                } else {
                    if ("normal".equals(wmsRtnOrderLineQueue.getInvStatus())) {
                        statusName = "良品";
                    } else if ("damage".equals(wmsRtnOrderLineQueue.getInvStatus())) {
                        statusName = "残次品";
                    } else {
                        statusName = "待处理品";
                    }
                }

            }
            InventoryStatus invStatus = inventoryStatusDao.findByName(statusName, ou1.getParentUnit().getParentUnit().getId());
            sl.setInvStatus(invStatus);
            lines.add(sl);
            staLineDao.save(sl);
        }
        // List<WmsOrderInvoiceQueue> invoices =
        // invoiceQueueDao.findByRtnId(wmsRtnInOrderQueue.getId(), new
        // BeanPropertyRowMapperExt<WmsOrderInvoiceQueue>(WmsOrderInvoiceQueue.class));
        // for (WmsOrderInvoiceQueue wmsOrderInvoiceQueue : invoices) {
        // StaInvoice invoice = new StaInvoice();
        // invoice.setSta(sta);
        // invoice.setPayer(wmsOrderInvoiceQueue.getPayer());
        // invoice.setAmt(wmsOrderInvoiceQueue.getAmt());
        // invoice.setDrawer(wmsOrderInvoiceQueue.getDrawer());
        // invoice.setInvoiceCode(wmsOrderInvoiceQueue.getInvoiceCode());
        // invoice.setInvoiceDate(wmsOrderInvoiceQueue.getInvoiceDate());
        // invoice.setItem(wmsOrderInvoiceQueue.getItem());
        // invoice.setMemo(wmsOrderInvoiceQueue.getMemo());
        // invoice.setPayee(wmsOrderInvoiceQueue.getPayee());
        // invoice.setQty(wmsOrderInvoiceQueue.getQty().intValue());
        // invoice.setUnitPrice(wmsOrderInvoiceQueue.getUnitPrice());
        // staInvoiceDao.save(invoice);
        // List<WmsOrderInvoiceLineQueue> queueStaInvoice =
        // invoiceLineQueueDao.findByInvoiceId(wmsOrderInvoiceQueue.getId(), new
        // BeanPropertyRowMapperExt<WmsOrderInvoiceLineQueue>(WmsOrderInvoiceLineQueue.class));
        // for (WmsOrderInvoiceLineQueue queueStaInvoiceLine : queueStaInvoice) {
        // StaInvoiceLine staInvoiceLine = new StaInvoiceLine();
        // staInvoiceLine.setAmt(queueStaInvoiceLine.getAmt());
        // staInvoiceLine.setLineNO(queueStaInvoiceLine.getLineNo());
        // staInvoiceLine.setItem(queueStaInvoiceLine.getItem());
        // staInvoiceLine.setQty(queueStaInvoiceLine.getQty().intValue());
        // staInvoiceLine.setUnitPrice(queueStaInvoiceLine.getUnitPrice());
        // staInvoiceLine.setStaInvoice(invoice);
        // staInvoiceLineDao.save(staInvoiceLine);
        // }
        // }
        staDao.updateSkuQtyById(sta.getId());
        staDao.updateIsSnSta(sta.getId());
        staDao.flush();
        deliveryInfoDao.flush();
        staLineDao.flush();
        staInvoiceDao.flush();
        staInvoiceLineDao.flush();
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }

        // 记录反馈数据
        if (confirmOrder != null) {

            createConfirmOrder(sta, di, lines, confirmOrder);
        } else {
            createConfirmOrder(sta, di, lines, null);
            wmsThreePLManager.createCnWmsOrderStatusUpload(sta.getId());
        }
    }

    public void createRtnOrderlog(WmsRtnInOrderQueue wmsRtnInOrderQueue) {
        WmsRtnInOrder inOrder = new WmsRtnInOrder();
        inOrder.setAddress(wmsRtnInOrderQueue.getAddress());
        inOrder.setAddress1(wmsRtnInOrderQueue.getAddress1());
        inOrder.setCity(wmsRtnInOrderQueue.getCity());
        inOrder.setCity1(wmsRtnInOrderQueue.getCity1());
        inOrder.setCountry(wmsRtnInOrderQueue.getCountry());
        inOrder.setDistrict(wmsRtnInOrderQueue.getDistrict());
        inOrder.setDistrict1(wmsRtnInOrderQueue.getDistrict1());
        inOrder.setIsLocked(wmsRtnInOrderQueue.getIsLocked());
        inOrder.setMemo(wmsRtnInOrderQueue.getMemo());
        inOrder.setMoblie(wmsRtnInOrderQueue.getMoblie());
        List<WmsRtnOrderLineQueue> staLines = orderLineQueueDao.queryRtnId(wmsRtnInOrderQueue.getId(), new BeanPropertyRowMapperExt<WmsRtnOrderLineQueue>(WmsRtnOrderLineQueue.class));
        List<WmsRtnOrderLine> orderLines = new ArrayList<WmsRtnOrderLine>();
        for (WmsRtnOrderLineQueue wmsRtnOrderLineQueue : staLines) {
            WmsRtnOrderLine line = new WmsRtnOrderLine();
            line.setExt_code(wmsRtnOrderLineQueue.getExt_code());
            line.setInvStatus(wmsRtnOrderLineQueue.getInvStatus());
            line.setLineAmt(wmsRtnOrderLineQueue.getLineAmt());
            line.setLineNo(wmsRtnOrderLineQueue.getLineNo());
            line.setListPrice(wmsRtnOrderLineQueue.getListPrice());
            line.setOwner(wmsRtnOrderLineQueue.getOwner());
            line.setQty(wmsRtnOrderLineQueue.getQty());
            line.setSku(wmsRtnOrderLineQueue.getSku());
            line.setSkuName(wmsRtnOrderLineQueue.getSkuName());
            line.setUnitPrice(wmsRtnOrderLineQueue.getUnitPrice());
            orderLines.add(line);
        }
        List<WmsOrderInvoiceQueue> invoices = invoiceQueueDao.findByRtnId(wmsRtnInOrderQueue.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceQueue>(WmsOrderInvoiceQueue.class));
        List<WmsOrderInvoice> orderInvoices = new ArrayList<WmsOrderInvoice>();
        for (WmsOrderInvoiceQueue wmsOrderInvoiceQueue : invoices) {
            WmsOrderInvoice invoice = new WmsOrderInvoice();
            invoice.setAmt(wmsOrderInvoiceQueue.getAmt());
            invoice.setCompany(wmsOrderInvoiceQueue.getCompany());
            List<WmsOrderInvoiceLineQueue> queueStaInvoice = invoiceLineQueueDao.findByInvoiceId(wmsOrderInvoiceQueue.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceLineQueue>(WmsOrderInvoiceLineQueue.class));
            List<WmsOrderInvoiceLine> invoiceLines = new ArrayList<WmsOrderInvoiceLine>();
            for (WmsOrderInvoiceLineQueue wmsOrderInvoiceLineQueue : queueStaInvoice) {
                WmsOrderInvoiceLine invoiceLine = new WmsOrderInvoiceLine();
                invoiceLine.setAmt(wmsOrderInvoiceLineQueue.getAmt());
                invoiceLine.setItem(wmsOrderInvoiceLineQueue.getItem());
                invoiceLine.setLineNo(wmsOrderInvoiceLineQueue.getLineNo());
                invoiceLine.setQty(wmsOrderInvoiceLineQueue.getQty());
                invoiceLine.setUnitPrice(wmsOrderInvoiceLineQueue.getUnitPrice());
                invoiceLines.add(invoiceLine);
            }

            invoice.setDetials(invoiceLines);
            invoice.setDrawer(wmsOrderInvoiceQueue.getDrawer());
            invoice.setInvoiceCode(wmsOrderInvoiceQueue.getInvoiceCode());
            invoice.setInvoiceDate(wmsOrderInvoiceQueue.getInvoiceDate());
            invoice.setInvoiceNo(wmsOrderInvoiceQueue.getInvoiceNo());
            invoice.setItem(wmsOrderInvoiceQueue.getItem());
            invoice.setMemo(wmsOrderInvoiceQueue.getMemo());
            invoice.setPayee(wmsOrderInvoiceQueue.getPayee());
            invoice.setPayer(wmsOrderInvoiceQueue.getPayer());
            invoice.setQty(wmsOrderInvoiceQueue.getQty());
            invoice.setUnitPrice(wmsOrderInvoiceQueue.getUnitPrice());
            invoice.setWmsInvoiceCode(wmsOrderInvoiceQueue.getWmsInvoiceCode());
        }
        inOrder.setOrderCode(wmsRtnInOrderQueue.getOrderCode());
        inOrder.setOwner(wmsRtnInOrderQueue.getOwner());
        inOrder.setProvince(wmsRtnInOrderQueue.getProvince());
        inOrder.setZipcode(wmsRtnInOrderQueue.getZipcode());
        inOrder.setWarehouseId(wmsRtnInOrderQueue.getWarehouseId());
        inOrder.setWarehouseCode(wmsRtnInOrderQueue.getWarehouseCode());
        inOrder.setTelephone(wmsRtnInOrderQueue.getTelephone());
        inOrder.setRtnLines(orderLines);
        inOrder.setSourceOrderCode(wmsRtnInOrderQueue.getSourceOrderCode());
        inOrder.setRntInvoices(orderInvoices);
        inOrder.setReceiver1(wmsRtnInOrderQueue.getReceiver1());
        inOrder.setReceiver(wmsRtnInOrderQueue.getReceiver());
        inOrder.setProvince1(wmsRtnInOrderQueue.getProvince1());
        inOrder.setProvince(wmsRtnInOrderQueue.getProvince());
        JSONArray jsonArray = JSONArray.fromObject(inOrder);
        WmsSalesOrderLog log = new WmsSalesOrderLog();
        log.setCreateTime(new Date());
        log.setJsonInfo(jsonArray.toString());
        log.setOrderCode(inOrder.getOrderCode());
        logDao.save(log);
    }

    public WmsConfirmOrder confirmOrder(StockTransApplication sta, StaDeliveryInfo deliveryInfo, List<StaLine> staLines) {
        WmsConfirmOrder confirmOrder = new WmsConfirmOrder();
        confirmOrder.setIsDs(false);
        confirmOrder.setMemo("成功");
        confirmOrder.setOrderCode(sta.getRefSlipCode());
        confirmOrder.setOrderType(sta.getType().getValue());
        confirmOrder.setOwner(sta.getOwner());
        confirmOrder.setStatus(true);
        List<WmsShipping> list = new ArrayList<WmsShipping>();
        WmsShipping queue = new WmsShipping();
        queue.setShippingCode(sta.getCode());
        queue.setTransCode(deliveryInfo.getLpCode());
        queue.setTransNo(deliveryInfo.getTrackingNo());
        list.add(queue);

        List<WmsShippingLine> lines = new ArrayList<WmsShippingLine>();
        for (StaLine line : staLines) {
            WmsShippingLine lineQueue = new WmsShippingLine();
            Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
            lineQueue.setLineNo(line.getLineNo());
            lineQueue.setQty(line.getQuantity());
            lineQueue.setSku(sku.getCode());
            lines.add(lineQueue);

        }
        queue.setLines(lines);
        confirmOrder.setShippings(list);
        return confirmOrder;
    }

    public void createConfirmOrder(StockTransApplication sta, StaDeliveryInfo deliveryInfo, List<StaLine> staLines, WmsConfirmOrder confirmOrder) {
        Boolean isMq = false;
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_SALES_ORDER_SERVICE, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
            isMq = true;
        }
        WmsConfirmOrderQueue confirmOrderQueue = new WmsConfirmOrderQueue();
        if (isMq) {
            confirmOrderQueue.setIsMq("1");
        }
        confirmOrderQueue.setCreateTime(new Date());
        confirmOrderQueue.setIsDs(false);
        // confirmOrderQueue.setMemo(orderQueue.getFlagResult());
        confirmOrderQueue.setOrderCode(sta.getRefSlipCode());
        if (confirmOrder != null) {
            confirmOrderQueue.setOrderType(confirmOrder.getOrderType());
        } else {
            confirmOrderQueue.setOrderType(sta.getType().getValue());
        }
        confirmOrderQueue.setOwner(sta.getOwner());
        confirmOrderQueue.setStatus(true);
        confirmOrderQueue.setSystemKey(sta.getSystemKey());
        confirmOrderQueue.setErrorStatus("S000001");
        confirmOrderQueueDao.save(confirmOrderQueue);
        if (confirmOrder != null) {
            for (WmsShipping shipping : confirmOrder.getShippings()) {
                WmsShippingQueue queue = new WmsShippingQueue();
                queue.setQueue(confirmOrderQueue);
                queue.setShippingCode(shipping.getShippingCode());
                queue.setTransCode(shipping.getTransCode());
                queue.setTransNo(shipping.getTransNo());
                shippingQueueDao.save(queue);
                for (WmsShippingLine staLine : shipping.getLines()) {
                    WmsShippingLineQueue lineQueue = new WmsShippingLineQueue();
                    lineQueue.setLineNo(staLine.getLineNo());
                    lineQueue.setShippingQueue(queue);
                    lineQueue.setQty(staLine.getQty());
                    lineQueue.setSku(staLine.getSku());
                    lineQueueDao.save(lineQueue);
                }
            }

        }
        WmsShippingQueue queue = new WmsShippingQueue();
        queue.setQueue(confirmOrderQueue);
        queue.setShippingCode(sta.getCode());
        queue.setTransCode(deliveryInfo.getLpCode());
        queue.setTransNo(deliveryInfo.getTrackingNo());
        shippingQueueDao.save(queue);
        for (StaLine line : staLines) {
            WmsShippingLineQueue lineQueue = new WmsShippingLineQueue();
            Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
            lineQueue.setLineNo(line.getLineNo());
            lineQueue.setQty(line.getQuantity());
            lineQueue.setShippingQueue(queue);
            lineQueue.setSku(sku.getCode());
            lineQueueDao.save(lineQueue);
        }

    }

    /**
     * 
     * @param start 页数
     * @param pagesize 页数大小
     * @param systemKey 订单来源
     * @param starteTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public Pagination<WmsPackageHead> getRtnInOrderByTime(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        String brand=null;
       /* if("adidas".equals(systemKey)){
            brand="1";
        }*/
        Pagination<ReturnApplicationCommand> appPage =
                returnApplicationDao.findReturnAppList2(start, pagesize, new BeanPropertyRowMapperExt<ReturnApplicationCommand>(ReturnApplicationCommand.class), null, null, null, null, null, null, null, null, null, null, null, null, null, systemKey,
                        starteTime, endTime, null, null,null,brand);
        Pagination<WmsPackageHead> app = new Pagination<WmsPackageHead>();
        List<WmsPackageHead> wmsPage = new ArrayList<WmsPackageHead>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ReturnApplicationCommand rapp : appPage.getItems()) {
            WmsPackageHead head = new WmsPackageHead();
            head.setWmsBillCode(rapp.getCode());
            BiChannel bic = biChannelDao.getByName(rapp.getOwner());
            if (bic != null) {
                head.setShopOwner(bic.getCode());
            }
            head.setMobile(rapp.getMobile());
            try {
                head.setCheckedTime(sdf.parse(sdf.format(rapp.getCreateTime())));
            } catch (ParseException e) {
                if (log.isErrorEnabled()) {
                    log.error("getRtnInOrderByTime-ParseException", e);
                }
            }
            head.setRemark(rapp.getMemo());
            head.setRtnPersion(rapp.getReturnUser());
            head.setSlipCode1(rapp.getSlipCode2()); // 平台订单号
            head.setSlipCode2(rapp.getSlipCode1()); // 销售相关单据号
            head.setSlipCode3(rapp.getSlipCode()); // 退货入相关单据号
            head.setTelephone(rapp.getTelephone());
            head.setTrackingNo(rapp.getTrankNo());
            head.setTransCode(rapp.getLpCode());
            OperationUnit unit = operationUnitDao.getByPrimaryKey(rapp.getWhOuId());
            head.setWarehouseCode(unit.getCode());
            head.setRtnPersion(rapp.getReturnUser());
            User user = userDao.getByPrimaryKey(rapp.getCreateUserId());
            head.setWmsOperator(user.getUserName());
            head.setCheckedTime(rapp.getCreateTime());
            head.setReceiveDate(rapp.getReceiveDate());//
            if (rapp.getExtended2() != null && rapp.getExtended2().equals("1")) {
                head.setFileName(rapp.getCode() + ".doc");
            } else {
                head.setFileName(null);
            }
            if (rapp.getCode().substring(0, 1).equals("W")) {
                head.setPackageType("1");// 包裹类型1：退回包裹无指令
            } else {
                head.setPackageType("2");// 包裹类型2：退回包裹有指令
            }
            List<WmsPackageLine> lineList = new ArrayList<WmsPackageLine>();
            List<ReturnApplicationLine> pageLine = returnApplicationLineDao.getRtnLineByRtnId(rapp.getId());
            for (ReturnApplicationLine line : pageLine) {
                WmsPackageLine lines = new WmsPackageLine();
                Sku sku = skuDao.getByPrimaryKey(line.getSkuId());
                InventoryStatus invStatus = inventoryStatusDao.findByStatusIdAndOu(line.getInvStatusId(), rapp.getWhOuId());
                if (invStatus != null) {
                    lines.setInvStatus(invStatus.getName());
                }
                lines.setSkuCode(sku.getCode());
                lines.setQty(line.getQty());
                lines.setWmsOrderType(line.getWmsOrdeType());//wms工单类型
                lineList.add(lines);
            }
            head.setLines(lineList);
            wmsPage.add(head);
        }
        app.setItems(wmsPage);
        app.setSize(pagesize);
        app.setStart(start);
        return app;
    }



    /**
     * 异常包裹指令状态反馈接口
     * 
     * @param code wms单据编码
     * @param slipCode 反馈相关单据号
     * @return
     */
    public BaseResult RtnPackageResult(PackageResult result) {

        BaseResult bs = new BaseResult();
        List<StockTransApplication> staList = new ArrayList<StockTransApplication>();
        if (result != null) {
            ReturnApplication ra = returnApplicationDao.getReturnByCode(result.getWmsBillCode());
            if (ra != null) {
                if (result.getStatus() == 1) { // 反馈成功，更新单据号状态和单号
                    staList = staDao.findBySlipCode(result.getSlipCode());
                    if (staList.size() == 0) {
                        bs.setStatus(BaseResult.STATUS_ERROR);
                        bs.setMsg("接收失败： 反馈的单据号在WMS中不存在！单据号：" + result.getSlipCode()); // 反馈失败
                    } else {
                        ra.setOmsStatus(1);
                        ra.setFeedBackTime(new Date());
                        ra.setReturnSlipCode(result.getSlipCode());
                        ra.setOmsRemark(result.getRemark());
                        ra.setSatus(ReturnApplicationStatus.NO_RELATION_TO_BE_SURE);
                        bs.setStatus(BaseResult.STATUS_SUCCESS);
                        bs.setMsg("接收成功"); // 反馈成功
                    }
                } else if (result.getStatus() == 2) { // 拒收
                    staList = staDao.findBySlipCode(result.getSlipCode());
                    if (staList.size() == 0) {
                        bs.setStatus(BaseResult.STATUS_ERROR);
                        bs.setMsg("接收失败： 反馈的单据号在WMS中不存在！单据号：" + result.getSlipCode()); // 反馈失败
                    } else {
                        ra.setOmsStatus(2);
                        ra.setFeedBackTime(new Date());
                        ra.setOmsRemark(result.getRemark());
                        ra.setReturnSlipCode1(result.getSlipCode());
                        ra.setSatus(ReturnApplicationStatus.NO_RELATION_TO_BE_SURE);
                        bs.setStatus(BaseResult.STATUS_SUCCESS);
                        bs.setMsg("接收成功");
                    }

                } else { // 无法确认
                    ra.setOmsStatus(3);
                    ra.setOmsRemark(result.getRemark());
                    ra.setFeedBackTime(new Date());
                    ra.setSatus(ReturnApplicationStatus.NO_RELATION_TO_BE_SURE);
                    bs.setStatus(BaseResult.STATUS_SUCCESS);
                    bs.setMsg("接收成功");
                }
                returnApplicationDao.save(ra);
            } else {
                bs.setStatus(BaseResult.STATUS_ERROR);
                bs.setMsg("单据号不存在或已反馈！退换货指令：" + result.getWmsBillCode()); // 反馈失败
            }
        } else {
            bs.setStatus(BaseResult.STATUS_ERROR);
            bs.setMsg("OMS系统异常"); // 反馈失败
        }
        return bs;
    }

    public BaseResult returnOrderUpdate(String returnInstruction, String transferNum, String status) {
        BaseResult baseResult = new BaseResult();
        if (null != transferNum) {
            List<ReturnApplication> returnApplication = returnApplicationDao.getReturnByLpCode1(transferNum);
            if (null != returnApplication && returnApplication.size() > 0) {
                if (null != returnInstruction && !"".equals(returnInstruction)) {
                    returnApplicationDao.updateReturnApplication(1, 3, returnInstruction, transferNum);
                } else {
                    if (null != status && !"".equals(status) && status.equals("已入库")) {
                        returnApplicationDao.updateReturnApplication(1, 3, returnInstruction, transferNum);
                    }
                    if (null != status && !"".equals(status) && status.equals("未入库")) { // 拒收
                        returnApplicationDao.updateReturnApplication(2, 3, returnInstruction, transferNum);
                    }
                }
                baseResult.setStatus(BaseResult.STATUS_SUCCESS);
            } else {
                baseResult.setStatus(BaseResult.STATUS_ERROR);
                baseResult.setMsg("运单号不存在");
            }
        } else {
            baseResult.setStatus(BaseResult.STATUS_ERROR);
            baseResult.setMsg("运单号不能为空");
        }
        return baseResult;
    }

    @Override
    public BaseResult adReturnOrderUpdate(AdPackageLineDealDto adPackageLineDealDto) {
        log.error("adReturnOrderUpdate:" + JsonUtil.writeValue(adPackageLineDealDto));
        BaseResult baseResult = new BaseResult();

        if(adPackageLineDealDto.getAdOrderId()==null){
            baseResult.setStatus(BaseResult.STATUS_ERROR);
            baseResult.setMsg("AD工单编号不能为空");
            return baseResult;
        }
        
        if(adPackageLineDealDto.getWmsOrderId()==null){
            baseResult.setStatus(BaseResult.STATUS_ERROR);
            baseResult.setMsg("WMS工单编号不能为空");
            return baseResult;
        }
        
        AdPackageLineDealDto adPackageLineDealDto2= adPackageLineDealDao.getAdPackageLineDealDtoByAdOrderId(adPackageLineDealDto.getAdOrderId(),new BeanPropertyRowMapperExt<AdPackageLineDealDto>(AdPackageLineDealDto.class));
        AdPackageLineDealLog adLog=new AdPackageLineDealLog();
            if(adPackageLineDealDto2==null){
                ReturnApplication returnApplication=returnApplicationDao.getReturnByCode2(adPackageLineDealDto.getWmsOrderId());
                    if(returnApplication==null){
                        baseResult.setStatus(BaseResult.STATUS_ERROR);
                        baseResult.setMsg("WMS工单编号实体查询不到");
                        return baseResult;
                    }
                    //查wms事件编码
                    Sku sku=skuDao.getByCode(adPackageLineDealDto.getSkuCode());
                    if(sku==null){
                        baseResult.setStatus(BaseResult.STATUS_ERROR);
                        baseResult.setMsg(adPackageLineDealDto.getSkuCode()+"商品不存在");
                        return baseResult;
                    }
                    AdPackageLineDeal adDeal=new AdPackageLineDeal();
                    BeanUtils.copyProperties(adPackageLineDealDto, adDeal);
                    adDeal.setCreateTime(new Date());
                    adDeal.setLastUpdateTime(new Date());
                    adDeal.setIsSend(1);
                    adDeal.setNum(0);
                    adDeal.setLpCode(returnApplication.getLpCode());
                    adDeal.setExtended(returnApplication.getExtended());//作业单号  
                    adDeal.setOpStatus(0);
                    adDeal.setSkuId(sku.getId());
                    adDeal.setStatus(1);
                    adPackageLineDealDao.save(adDeal);
                    //加LOG
                    BeanUtils.copyProperties(adDeal, adLog);
                    adLog.setId(null);
                    adLog.setType(2);//800ts
                    adLog.setLastUpdateTime(new Date());
                    adPackageLineDealLogDao.save(adLog);
                }else{
                    AdPackageLineDeal deal= adPackageLineDealDao.getByPrimaryKey(adPackageLineDealDto2.getId());
                    if(deal.getStatus()==3){
                        baseResult.setStatus(BaseResult.STATUS_ERROR);
                        baseResult.setMsg(deal.getAdOrderId() +"工单已经关闭");
                        return baseResult;
                    }
                    deal.setAdStatus(adPackageLineDealDto.getAdStatus());
                    deal.setAdOrderType(adPackageLineDealDto.getAdOrderType());
                    deal.setLastUpdateTime(new Date());
                    deal.setAdRemark(adPackageLineDealDto.getAdRemark());
                    deal.setIsSend(1);
                    deal.setNum(0);
                    if(adPackageLineDealDto.getReturnInstruction()!=null){
                        deal.setReturnInstruction(adPackageLineDealDto.getReturnInstruction());
                    }
                    log.error(adPackageLineDealDto.getAdStatus()+"ad1");
                    if("工单关闭".equals(adPackageLineDealDto.getAdStatus())){
                        log.error(adPackageLineDealDto.getAdStatus()+"工单关闭2");
                        deal.setOpStatus(1);
                        deal.setStatus(3);//完成
                    }else{
                        deal.setOpStatus(0);
                        deal.setStatus(1);
                    }
                    adPackageLineDealDao.save(deal);
                    adPackageLineDealDao.flush();
                    //加LOG
                    BeanUtils.copyProperties(deal, adLog);
                    adLog.setId(null);
                    adLog.setCreateTime(new Date());
                    adLog.setType(2);//800ts
                    adLog.setLastUpdateTime(new Date());
                    adPackageLineDealLogDao.save(adLog);
                }
        baseResult.setStatus(BaseResult.STATUS_SUCCESS);
        return baseResult;
    }
    
    
    @Override
    public BaseResult adReturnOrderUpdateTest() {
        AdPackageLineDealDto adPackageLineDealDto=new AdPackageLineDealDto();
       ////////////////////////////////测试////////////////////////
        adPackageLineDealDto.setReturnInstruction("OD123456");//退货指令 N
        adPackageLineDealDto.setTrankNo("SF123ABC");//快递单号 Y
        adPackageLineDealDto.setAdStatus("工单关闭");//800ts处理结果N
        adPackageLineDealDto.setWmsStatus("退货已入库");//WMS处理结果N
        adPackageLineDealDto.setAdRemark("工单关闭");//ad反馈备注N
        adPackageLineDealDto.setWmsOrderId("W1530759688424");//WMS事件编号Y
        adPackageLineDealDto.setWmsOrderType("无指令退货");//WMS工单类型Y
        adPackageLineDealDto.setAdOrderId("1844");//800ts工单编号Y
        adPackageLineDealDto.setAdOrderType("无指令退货");//ad工单类型
        adPackageLineDealDto.setSkuCode("4057291649315");//SKU编号Y
        adPackageLineDealDto.setQuantity(5L);//数量Y
        /////////////////////////
     
        

        log.error("adReturnOrderUpdate:" + JsonUtil.writeValue(adPackageLineDealDto));
        BaseResult baseResult = new BaseResult();
        
        if(adPackageLineDealDto.getAdOrderId()==null){
            baseResult.setStatus(BaseResult.STATUS_ERROR);
            baseResult.setMsg("AD工单编号不能为空");
            return baseResult;
        }
        if(adPackageLineDealDto.getWmsOrderId()==null){
            baseResult.setStatus(BaseResult.STATUS_ERROR);
            baseResult.setMsg("WMS工单编号不能为空");
            return baseResult;
        }
        AdPackageLineDealDto adPackageLineDealDto2= adPackageLineDealDao.getAdPackageLineDealDtoByAdOrderId(adPackageLineDealDto.getAdOrderId(),new BeanPropertyRowMapperExt<AdPackageLineDealDto>(AdPackageLineDealDto.class));
        AdPackageLineDealLog adLog=new AdPackageLineDealLog();
            if(adPackageLineDealDto2==null){
                ReturnApplication returnApplication =returnApplicationDao.getReturnByCode2(adPackageLineDealDto.getWmsOrderId());
                if(returnApplication==null){
                    baseResult.setStatus(BaseResult.STATUS_ERROR);
                    baseResult.setMsg("WMS工单编号实体查询不到");
                    return baseResult;
                }
                    //查wms事件编码
                    Sku sku=skuDao.getByCode(adPackageLineDealDto.getSkuCode());
                    if(sku==null){
                        baseResult.setStatus(BaseResult.STATUS_ERROR);
                        baseResult.setMsg(adPackageLineDealDto.getSkuCode()+"商品不存在");
                        return baseResult;
                    }
                    AdPackageLineDeal adDeal=new AdPackageLineDeal();
                    BeanUtils.copyProperties(adPackageLineDealDto, adDeal);
                    adDeal.setCreateTime(new Date());
                    adDeal.setLastUpdateTime(new Date());
                    adDeal.setIsSend(1);
                    adDeal.setNum(0);
                    adDeal.setLpCode(returnApplication.getLpCode());
                    adDeal.setExtended(returnApplication.getExtended());//作业单号
                    adDeal.setOpStatus(0);
                    adDeal.setSkuId(sku.getId());
                    adDeal.setStatus(1);
                    adPackageLineDealDao.save(adDeal);
                    //加LOG
                    BeanUtils.copyProperties(adDeal, adLog);
                    adLog.setId(null);
                    adLog.setType(2);//800ts
                    adLog.setLastUpdateTime(new Date());
                    adPackageLineDealLogDao.save(adLog);
                }else{
                    AdPackageLineDeal deal= adPackageLineDealDao.getByPrimaryKey(adPackageLineDealDto2.getId());
                    if(deal.getStatus()==3){
                        baseResult.setStatus(BaseResult.STATUS_ERROR);
                        baseResult.setMsg(deal.getAdOrderId() +"工单已经关闭");
                        return baseResult;
                    }
                    deal.setAdStatus(adPackageLineDealDto.getAdStatus());
                    deal.setAdOrderType(adPackageLineDealDto.getAdOrderType());
                    deal.setLastUpdateTime(new Date());
                    deal.setAdRemark(adPackageLineDealDto.getAdRemark());
                    deal.setOpStatus(0);
                    deal.setIsSend(1);
                    deal.setNum(0);
                    deal.setStatus(1);
                    log.error(adPackageLineDealDto.getAdStatus()+"工单关闭1");
                    if("工单关闭".equals(adPackageLineDealDto.getAdStatus())){
                        log.error(adPackageLineDealDto.getAdStatus()+"工单关闭2");
                        deal.setOpStatus(1);
                        deal.setStatus(3);//完成
                    }
                    adPackageLineDealDao.save(deal);
                    //加LOG
                    BeanUtils.copyProperties(deal, adLog);
                    adLog.setId(null);
                    adLog.setCreateTime(new Date());
                    adLog.setType(2);//800ts
                    adLog.setLastUpdateTime(new Date());
                    adPackageLineDealLogDao.save(adLog);
                }
        baseResult.setStatus(BaseResult.STATUS_SUCCESS);
        return baseResult;
    
    }
}
