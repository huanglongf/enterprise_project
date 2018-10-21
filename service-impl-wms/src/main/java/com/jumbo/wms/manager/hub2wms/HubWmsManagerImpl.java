package com.jumbo.wms.manager.hub2wms;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.CnOrderPropertyDao;
import com.jumbo.dao.hub2wms.MsgOrderCancelDao;
import com.jumbo.dao.hub2wms.ValueAddedServiceQueueDao;
import com.jumbo.dao.hub2wms.WmsConfirmOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsInfoLogOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceLineOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceLineQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderLineSnQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderPackingQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderStatusOmsDao;
import com.jumbo.dao.hub2wms.WmsRtnInOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsRtnOrderLineQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLineQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLogDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderPaymentQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsTransInfoOmsDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.AgvSkuDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InboundAgvToHubDao;
import com.jumbo.dao.warehouse.InboundAgvToHubLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaCancelDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.StaCancelManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.compensation.ClaimHead;
import com.jumbo.wms.model.compensation.ClaimProcess;
import com.jumbo.wms.model.compensation.ClaimResult;
import com.jumbo.wms.model.compensation.WmsClaimResult;
import com.jumbo.wms.model.hub2wms.MsgOrderCancel;
import com.jumbo.wms.model.hub2wms.ValueAddedService;
import com.jumbo.wms.model.hub2wms.ValueAddedServiceQueue;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsInfoLogOms;
import com.jumbo.wms.model.hub2wms.WmsInvLog;
import com.jumbo.wms.model.hub2wms.WmsOrderCancel;
import com.jumbo.wms.model.hub2wms.WmsOrderCancelLine;
import com.jumbo.wms.model.hub2wms.WmsOrderCancelResult;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoice;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLine;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLineOms;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLineQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceOms;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderLine;
import com.jumbo.wms.model.hub2wms.WmsOrderPacking;
import com.jumbo.wms.model.hub2wms.WmsOrderPackingQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderStatus;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsRtnOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnOrderLine;
import com.jumbo.wms.model.hub2wms.WmsRtnOrderLineQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrder;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLine;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLineQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLog;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderPayment;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderPaymentQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsShippingResult;
import com.jumbo.wms.model.hub2wms.WmsTransInfo;
import com.jumbo.wms.model.hub2wms.WmsTransInfoOms;
import com.jumbo.wms.model.hub2wms.threepl.CnOrderProperty;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand;
import com.jumbo.wms.model.warehouse.AgvSku;
import com.jumbo.wms.model.warehouse.InboundAgvToHub;
import com.jumbo.wms.model.warehouse.InboundAgvToHubLine;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.StaAdditionalLineCommand;
import com.jumbo.wms.model.warehouse.StaCancel;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;

@Transactional
@Service("hubWmsManager")
public class HubWmsManagerImpl extends BaseManagerImpl implements HubWmsManager {

    /**
     * 
     */
    private static final long serialVersionUID = -8067164763206668817L;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WmsSalesOrderQueueDao wmsSalesOrderQueueDao;
    @Autowired
    private WmsSalesOrderLineQueueDao wmsSalesOrderLineQueueDao;
    @Autowired
    private ValueAddedServiceQueueDao valueAddedServiceQueueDao;
    @Autowired
    private WmsOrderLineSnQueueDao wmsOrderLineSnQueueDao;
    @Autowired
    private WmsSalesOrderPaymentQueueDao wmsSalesOrderPaymentQueueDao;
    @Autowired
    private WmsOrderPackingQueueDao wmsOrderPackingQueueDao;
    @Autowired
    private WmsOrderInvoiceQueueDao wmsOrderInvoiceQueueDao;
    @Autowired
    private WmsOrderInvoiceLineQueueDao wmsOrderInvoiceLineQueueDao;
    @Autowired
    private StaCancelManager staCancelManager;
    @Autowired
    WmsRtnInOrderQueueDao inOrderQueueDao;
    @Autowired
    WmsRtnOrderLineQueueDao lineQueueDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private ChannelWhRefRefDao channelWhRefRefDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private WmsOrderInvoiceQueueDao invoiceQueueDao;
    @Autowired
    private WmsOrderInvoiceLineQueueDao invoiceLineQueueDao;
    @Autowired
    private MsgOrderCancelDao msgOrderCancelDao;
    @Autowired
    private WmsOrderStatusOmsDao wmsOrderStatusOmsDao;
    @Autowired
    private WmsInfoLogOmsDao infoLogOmsDao;
    @Autowired
    WmsTransInfoOmsDao infoOmsDao;
    @Autowired
    private WmsSalesOrderLogDao wmsSalesOrderLogDao;
    @Autowired
    private WmsOrderInvoiceOmsDao invoiceOmsDao;
    @Autowired
    private WmsOrderInvoiceLineOmsDao invoiceLineOmsDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgDao;
    @Autowired
    private StaCancelDao staCancelDao;
    @Autowired
    private StaAdditionalLineDao staAddLineDao;
    @Autowired
    private HubWmsService hubWmsService;
    @Autowired
    private StaCancelDao cancelDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private InventoryDao invDao;
    @Autowired
    private StvLineDao lineDao;
    @Autowired
    private CnOrderPropertyDao cnOrderPropertyDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private WmsConfirmOrderQueueDao wmsConfirmOrderQueueDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private InboundAgvToHubDao inboundAgvToHubDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private AgvSkuDao agvSkuDao;
    @Autowired
    private InboundAgvToHubLineDao inboundAgvToHubLineDao;

    private static final String COLSE_SUCCESS = "S0001";

    private static final String COLSE_SUCCESS1 = "S0002";


    /**
     * 校验：<br/>
     * 1、仓库不存在<br/>
     * 2、仓库存在不允许分仓<br/>
     * 3、非分仓不支持分仓锁定<br/>
     * 4、COD订单必须有COD金额,且不支持分仓<br/>
     * 4、退货单不支持分仓 5、是否存在明细行<br/>
     * 6、明细行内商品，渠道，仓库判断<br/>
     * 7、整单客户必须一致
     * 
     */
    @Override
    public void wmsSalesOrder(String systemKey, WmsSalesOrder order) throws Exception {
        boolean isNotCheckInv = false;// 标示是否过仓不校验库存，该标示在店铺上
        BiChannel bcl = biChannelDao.getByCode(order.getOwner());
        isNotCheckInv = bcl.getIsNotCheckInventory() == null ? false : bcl.getIsNotCheckInventory();
        // adidas refWarehouseCode(相关仓库编码) 映射取值本地WarehouseCode
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(systemKey, order.getRefWarehouseCode());
        if (op != null) {
            CnOrderProperty c = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(order.getOrderCode());
            if (c == null) {
                CnOrderProperty cop = new CnOrderProperty();
                cop.setOrderCode(order.getOrderCode());
                cop.setSystemKey(systemKey);
                if (order.getOrderType() == 21) {
                    cop.setOrderType("201");
                } else {
                    cop.setOrderType("502");
                }
                cop.setLpCode(order.getTransCode());
                cop.setStoreCode(order.getRefWarehouseCode());
                cnOrderPropertyDao.save(cop);

            }
            if (order.getTransCode() != null) {
                Transportator transCode = transportatorDao.findByCode(order.getTransCode());
                if (transCode == null) {
                    order.setTransCode(Transportator.OTHER);
                }
            }
            // if (AdidasTaskImpl.AD_CUSTOMER_CODE.equals(systemKey)) {
            /*
             * if (op == null) { log.info("HubWmsManagerImpl  wmsSalesOrder systemKey=" + systemKey
             * + ",refWarehouseCode=" + order.getRefWarehouseCode()); throw new
             * BusinessException(""); } else {
             */
            order.setWarehouseCode(op.getOptionValue());
            // }
        }

        BigDecimalConverter bc = new BigDecimalConverter(null);
        ConvertUtils.register(bc, java.math.BigDecimal.class);
        String warehouse = order.getWarehouseCode();
        Customer c = null;
        OperationUnit warehouseOu = null;
        Boolean isAllowDS = order.isAllowDS();
        Boolean isDSLocked = order.isDSLocked();
        Boolean isCod = order.isCod();
        BigDecimal codAmt = order.getCodAmt();
        Boolean isExists = wmsSalesOrderLineQueueDao.findOrderIfExists(order.getOrderCode(), new SingleColumnRowMapper<Boolean>(Boolean.class));
        if (isExists != null && isExists) {
            throw new BusinessException(ErrorCode.HW_ORDER_EXISTS);
        }
        try {
            // 进行取消时间判断
            StockTransApplication sta = wmsSalesOrderLineQueueDao.staStatusCancleByOrderCode(order.getOrderCode(), new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
            if (sta != null && sta.getCancelTime() != null && order.getSendTimeMq() != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
                Date sendTimeMq = df.parse(order.getSendTimeMq());
                if (sendTimeMq.getTime() <= sta.getCancelTime().getTime()) {
                    throw new BusinessException(ErrorCode.HW_DS_ERROE_CANCEL_TIME);
                }
            }
        } catch (Exception e) {
            log.error("staStatusCancleByOrderCode:" + order.getOrderCode(), e);
        }

        if (order.getOrderType() == 42) {
            if (isAllowDS) {
                // 换出业务不支持分仓
                throw new BusinessException(ErrorCode.HW_DS_ERROE_WHEN_RETURN);
            }
        }
        if (warehouse != null) {
            warehouseOu = ouDao.getByCode(warehouse);
            if (warehouseOu == null) {
                // 仓库不存在
                throw new BusinessException(ErrorCode.HW_WAREHOUSE_NOT_EXISTS);
            } else {
                Warehouse w = warehouseDao.getByOuId(warehouseOu.getId());
                c = w.getCustomer();
                if (isAllowDS) {
                    // 存在仓库不允许分仓逻辑
                    throw new BusinessException(ErrorCode.HW_DS_ERROE_WHEN_EXISTS_WAREHOUSE);
                }
            }
        }
        if (isDSLocked) {
            if (!isAllowDS) {
                // 非分仓不支持分仓锁定标识
                throw new BusinessException(ErrorCode.HW_DSLOCKED_ERROR_WHEN_NOT_DS);
            }
        }
        if (isCod) {
            if (codAmt == null) {
                // COD订单必须有COD金额
                throw new BusinessException(ErrorCode.HW_COD_MUST_HAVE_CODAMT);
            }
            if (isAllowDS) {
                // COD订单不支持分仓逻辑
                throw new BusinessException(ErrorCode.HW_DS_ERROE_WHEN_COD);
            }
        }

        // 开关 start
        ChooseOption opp = chooseOptionDao.findByCategoryCodeAndKey("ERROR_PRO_CITY_ZHI", "ERROR_PRO_CITY_ZHI");
        if (opp != null && opp.getOptionValue() != null) {
            // 检验省市必填
            if (StringUtil.isEmpty(order.getProvince()) || StringUtil.isEmpty(order.getCity())) {
                throw new BusinessException(ErrorCode.ERROR_PRO_CITY);
            }
        }
        // 开关 end

        List<WmsSalesOrderLine> wsl = order.getLines();

        // if (AdidasTaskImpl.AD_CUSTOMER_CODE.equals(systemKey)) {// adidas 设置店铺owner
        // for (WmsSalesOrderLine wmsSalesOrderLine : wsl) {
        // wmsSalesOrderLine.setOwner(order.getOrderSourcePlatform());
        // }
        // }

        if (wsl == null || wsl.size() == 0) {
            // 不存在明细行
            throw new BusinessException(ErrorCode.HW_NO_ORDER_LINE_EXISTS);
        } else {
            if (warehouseOu != null) {
                for (WmsSalesOrderLine wl : wsl) {
                    if (wl.getWarehouseCode() != null && !wl.getWarehouseCode().equals(warehouse)) {
                        // 头指定仓，明细行必须为空或者跟头上仓库一致
                        throw new BusinessException(ErrorCode.HW_LINE_WARHOUSE_ERROR);
                    }
                }
            }
            BusinessException beW = new BusinessException();
            BusinessException beWl = beW;
            BusinessException beO = new BusinessException();
            BusinessException beOl = beO;
            BusinessException beS = new BusinessException();
            BusinessException beSl = beS;
            if (order.getOwner().contains("adidas")) {
                // ad判断是否有重复行
                HashSet<String> set = new HashSet<String>();
                for (WmsSalesOrderLine wl : wsl) {
                    set.add(wl.getOrderLineNo());
                }
                if (!(set.size() == wsl.size())) {
                    // 订单明细行重复
                    throw new BusinessException(ErrorCode.HW_LINE_NO_REPETITION);
                }
            }
            for (WmsSalesOrderLine wl : wsl) {
                if (wl.getWarehouseCode() != null) {
                    OperationUnit ou = ouDao.getByCode(wl.getWarehouseCode());
                    if (ou == null) {
                        // 明细行对应的仓库编码系统找不到
                        BusinessException be = new BusinessException(ErrorCode.HW_LINE_WARHOUSE_NOT_FOUND, new Object[] {wl.getLineNo(), wl.getWarehouseCode()});
                        beWl.setLinkedException(be);
                        beWl = be;
                    } else {
                        Warehouse w = warehouseDao.getByOuId(ou.getId());
                        if (c != null) {
                            if (!c.getId().equals(w.getCustomer().getId())) {
                                // 整单客户不一致
                                throw new BusinessException(ErrorCode.HW_ORDER_CUSTOMER_ERROR);
                            }
                        } else {
                            c = w.getCustomer();
                        }
                    }
                }
                if (wl.getOwner() == null) {
                    // 存在明细行未指定店铺
                    throw new BusinessException(ErrorCode.HW_LINE_NO_OWNER_SET, new Object[] {wl.getLineNo()});
                } else {
                    BiChannel ch = biChannelDao.getByCode(wl.getOwner());
                    if (ch == null) {
                        // 明细行对应的店铺编码{0}WMS找不到对应渠道
                        BusinessException be = new BusinessException(ErrorCode.HW_LINE_OWNER_NOT_FOUND, new Object[] {wl.getLineNo(), wl.getOwner()});
                        beOl.setLinkedException(be);
                        beOl = be;
                    } else {
                        if (c != null) {
                            if (!c.getId().equals(ch.getCustomer().getId())) {
                                System.out.println(c.getId().toString() + "-------" + ch.getCustomer().getId().toString());
                                int k = 1;
                                // 整单客户不一致
                                throw new BusinessException(ErrorCode.HW_ORDER_CUSTOMER_ERROR);
                            }
                        } else {
                            c = ch.getCustomer();
                        }
                    }
                }
                if (wl.getSku() == null) {
                    // 存在明细行未指定商品
                    throw new BusinessException(ErrorCode.HW_LINE_NO_SKU_SET, new Object[] {wl.getLineNo()});
                } else {
                    Sku sku = skuDao.getByCodeAndCustomer(wl.getSku(), c.getId());
                    if (sku == null) {
                        // 明细行对应的商品{0}未找到
                        BusinessException be = new BusinessException(ErrorCode.HW_LINE_SKU_NOT_FOUND, new Object[] {wl.getLineNo(), wl.getSku()});
                        beSl.setLinkedException(be);
                        beSl = be;
                    }
                }
                if (beW.getLinkedException() != null) {
                    throw beW;
                }
                if (beO.getLinkedException() != null) {
                    throw beO;
                }
                if (beS.getLinkedException() != null) {
                    throw beS;
                }
            }
        }
        WmsSalesOrderQueue w = new WmsSalesOrderQueue();
        BeanUtils.copyProperties(w, order);
        w.setCus_id(c.getId());
        w.setSystemKey(systemKey);
        w.setReceiveTime(new Date());
        if (w.getWarehouseCode() != null) {
            w.setOu_id(ouDao.getByCode(w.getWarehouseCode()).getId());
        }
        if (null != w.getTransCode()) {
            if ("SFCOD".equals(w.getTransCode())) {
                w.setTransCode("SF");
            }
            if ("EMSCOD".equals(w.getTransCode())) {
                w.setTransCode("EMS");
            }
            if ("JDCOD".equals(w.getTransCode())) {
                w.setTransCode("JD");
            }
        }
        w.setOriginalLpCode(w.getTransCode());
        /** 过仓校验库存开关 **********************/
        if (isNotCheckInv) {
            w.setBeginFlag(5);
            w.setCanFlag(true);
        }
        /** 过仓校验库存开关 **********************/
        wmsSalesOrderQueueDao.save(w);
        List<WmsSalesOrderPayment> wpl = order.getPayments();
        if (wpl != null) {
            if (isAllowDS) {
                throw new BusinessException(ErrorCode.HW_HAVE_PAYMENT_CANNOT_DS);
            }
            for (WmsSalesOrderPayment wp : wpl) {
                WmsSalesOrderPaymentQueue pq = new WmsSalesOrderPaymentQueue();
                BeanUtils.copyProperties(pq, wp);
                pq.setWmsSalesOrderQueue(w);
                wmsSalesOrderPaymentQueueDao.save(pq);
            }
        }
        List<ValueAddedService> val = order.getVasList();
        if (val != null) {
            for (ValueAddedService vs : val) {// ad 礼品包装类型:60006 是否包含礼品卡 :60001
                if (!(vs.getType() == 60001 || vs.getType() == 60006 || vs.getType() == 10001 || vs.getType() == 10003 || vs.getType() == 10005 || vs.getType() == 10008 || vs.getType() == 10009 || vs.getType() == 100010 || vs.getType() == 50010 || vs
                        .getType() == 50030)) {
                    // 订单头增值服务类型{0}非系统定义类型
                    throw new BusinessException(ErrorCode.HW_ORDER_VS_ERROR, new Object[] {vs.getType()});
                } else {
                    ValueAddedServiceQueue vq = new ValueAddedServiceQueue();
                    BeanUtils.copyProperties(vq, vs);
                    vq.setWmsSalesOrderQueue(w);
                    valueAddedServiceQueueDao.save(vq);
                }
            }
        }
        List<WmsOrderPacking> wopl = order.getPackingList();
        if (wopl != null) {
            for (WmsOrderPacking wp : wopl) {
                WmsOrderPackingQueue wq = new WmsOrderPackingQueue();
                BeanUtils.copyProperties(wq, wp);
                wq.setWmsSalesOrderQueue(w);
                wmsOrderPackingQueueDao.save(wq);
            }
        }
        List<WmsOrderInvoice> wil = order.getInvoices();
        if (wil != null) {
            for (WmsOrderInvoice wi : wil) {
                WmsOrderInvoiceQueue wiq = new WmsOrderInvoiceQueue();
                BeanUtils.copyProperties(wiq, wi);
                wiq.setWmsSalesOrderQueue(w);
                wmsOrderInvoiceQueueDao.save(wiq);
                List<WmsOrderInvoiceLine> wll = wi.getDetials();
                for (WmsOrderInvoiceLine wl : wll) {
                    WmsOrderInvoiceLineQueue wlq = new WmsOrderInvoiceLineQueue();
                    BeanUtils.copyProperties(wlq, wl);
                    wlq.setWmsOrderInvoiceQueue(wiq);
                    wmsOrderInvoiceLineQueueDao.save(wlq);
                }
            }
        }
        for (WmsSalesOrderLine wl : wsl) {
            WmsSalesOrderLineQueue wlq = new WmsSalesOrderLineQueue();
            BeanUtils.copyProperties(wlq, wl);
            wlq.setWmsSalesOrderQueue(w);
            wlq.setSkuId(skuDao.getByCodeAndCustomer(wlq.getSku(), c.getId()).getId());
            /** 过仓校验库存开关 **********************/
            if (isNotCheckInv) {
                // 如果此开关打开则要求过仓时仓库必须填写
                wlq.setOuId(warehouseOu.getId());
            }
            /** 过仓校验库存开关 **********************/
            wmsSalesOrderLineQueueDao.save(wlq);
            List<ValueAddedService> vl = wl.getVasList();
            if (vl != null) {
                for (ValueAddedService v : vl) {// ad 礼品包装类型:60006 是否包含礼品卡 :60001 宣传册类型:60004
                                                // 是否刻字:60005
                    if (!(v.getType() == 50010 || v.getType() == 50030 || v.getType() == 50020 || v.getType() == 50050 || v.getType() == 50060 || v.getType() == 60006 || v.getType() == 60001 || v.getType() == 60004 || v.getType() == 60005)) {
                        // 订单行增值服务类型{0}非系统定义类型
                        throw new BusinessException(ErrorCode.HW_ORDER_LINE_VS_ERROR, new Object[] {v.getType()});
                    } else {
                        ValueAddedServiceQueue vq = new ValueAddedServiceQueue();
                        BeanUtils.copyProperties(vq, v);
                        vq.setWmsSalesOrderLineQueue(wlq);
                        valueAddedServiceQueueDao.save(vq);
                    }
                }
            }
            List<String> sns = wl.getSns();
            if (sns != null) {
                // 目前不用
                // for (String s : sns) {
                // WmsOrderLineSnQueue sq = new WmsOrderLineSnQueue();
                // sq.setSn(s);
                // sq.setWmsSalesOrderLineQueue(wlq);
                // wmsOrderLineSnQueueDao.save(sq);
                // }
            }
        }
        String json = JSONUtil.beanToJson(order);
        WmsSalesOrderLog wl = new WmsSalesOrderLog();
        wl.setCreateTime(new Date());
        wl.setOrderCode(order.getOrderCode());
        wl.setJsonInfo(json);
        wmsSalesOrderLogDao.save(wl);
        wmsSalesOrderLogDao.flush();
    }

    @Override
    public void validateOrderInfo(String system, WmsRtnOrder order) {
        // 验证渠道
        BiChannel channel = biChannelDao.getByCode(order.getRtnInOrder().getOwner());
        if (channel == null) {
            throw new BusinessException(ErrorCode.CHANNEL_NOT_FOUNT, new Object[] {order.getRtnInOrder().getOwner()});
        }
        OperationUnit mainWhOu = null;
        // 验证仓库
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(system, order.getRefWarehouseCode());
        if (op != null) {
            // if (null != system && system.equals(AdidasTaskImpl.AD_CUSTOMER_CODE)) {
            /*
             * if (op == null) { log.info("HubWmsManagerImpl  wmsSalesOrder systemKey=" + system +
             * ",refWarehouseCode=" + order.getRefWarehouseCode()); throw new BusinessException("");
             * } else {
             */
            mainWhOu = operationUnitDao.getByCode(op.getOptionValue());
            if (mainWhOu == null || !OperationUnitType.OUTYPE_WAREHOUSE.equals(mainWhOu.getOuType().getName())) {
                throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new Object[] {order.getRtnInOrder().getWarehouseCode()});
            }
            // }

        } else {
            mainWhOu = operationUnitDao.getByCode(order.getRtnInOrder().getWarehouseCode());
            if (mainWhOu == null || !OperationUnitType.OUTYPE_WAREHOUSE.equals(mainWhOu.getOuType().getName())) {
                throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new Object[] {order.getRtnInOrder().getWarehouseCode()});
            }

        }
        // 验证仓库基础信息
        Warehouse mainWh = warehouseDao.getByOuId(mainWhOu.getId());
        if (mainWh == null || mainWh.getCustomer() == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_INFO_NOT_SETTING, new Object[] {order.getRtnInOrder().getWarehouseCode()});
        }
        // 验证仓库与渠道绑定关系
        ChannelWhRef mref = channelWhRefRefDao.getChannelRef(mainWhOu.getId(), channel.getId());
        if (mref == null) {
            throw new BusinessException(ErrorCode.CHANNEL_WAREHOUSE_NO_REF, new Object[] {order.getRtnInOrder().getOwner(), order.getRtnInOrder().getWarehouseCode()});
        }
        order.getRtnInOrder().setWarehouseId(mainWhOu.getId());

    }

    @Override
    public void createStaByOrder(String systemKey, WmsRtnInOrder order) {
        if (order != null) {
            WmsRtnInOrderQueue inOrderQueue = new WmsRtnInOrderQueue();

            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(systemKey, order.getWarehouseCode());
            if (op != null) {
                CnOrderProperty c = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(order.getOrderCode());
                if (c == null && org.apache.commons.lang3.StringUtils.equals(systemKey, Constants.CAINIAO_DB_SYSTEM_KEY)) {
                    CnOrderProperty cop = new CnOrderProperty();
                    cop.setOrderCode(order.getOrderCode());
                    cop.setSystemKey(systemKey);
                    cop.setOrderType("501");
                    cop.setStoreCode(order.getWarehouseCode());
                    cnOrderPropertyDao.save(cop);
                }
                // if (AdidasTaskImpl.AD_CUSTOMER_CODE.equals(systemKey)) {
                // if (op == null) {
                // log.info("HubWmsManagerImpl  wmsSalesOrder systemKey=" + systemKey +
                // ",refWarehouseCode=" + order.getWarehouseCode());
                // throw new BusinessException("");
                // } else {
                order.setWarehouseCode(op.getOptionValue());
                OperationUnit ou = ouDao.getByCode(op.getOptionValue());
                inOrderQueue.setWarehouseCode(order.getWarehouseCode());
                inOrderQueue.setWarehouseId(ou.getId());
                // }
            } else {
                inOrderQueue.setWarehouseCode(order.getWarehouseCode());
                inOrderQueue.setWarehouseId(order.getWarehouseId());
            }
            inOrderQueue.setOrderType(order.getOrderType());
            inOrderQueue.setOrderCode(order.getOrderCode());
            inOrderQueue.setSourceOrderCode(order.getSourceOrderCode());
            inOrderQueue.setOwner(order.getOwner());
            inOrderQueue.setIsLocked(order.getIsLocked());
            inOrderQueue.setMemo(order.getMemo());
            inOrderQueue.setCountry(order.getCountry());
            inOrderQueue.setProvince(order.getProvince());
            inOrderQueue.setProvince1(order.getProvince1());
            inOrderQueue.setCity(order.getCity());
            inOrderQueue.setCity1(order.getCity1());
            inOrderQueue.setDistrict(order.getDistrict());
            inOrderQueue.setDistrict1(order.getDistrict1());
            inOrderQueue.setAddress(order.getAddress());
            inOrderQueue.setAddress1(order.getAddress1());
            inOrderQueue.setTelephone(order.getTelephone());
            inOrderQueue.setMoblie(order.getMoblie());
            inOrderQueue.setReceiver(order.getReceiver());
            inOrderQueue.setReceiver1(order.getReceiver1());
            inOrderQueue.setZipcode(order.getZipcode());
            inOrderQueue.setErrorCount(0);
            inOrderQueue.setSystemKey(systemKey);
            inOrderQueue.setOrderSourcePlatform(order.getOrderSourcePlatform());
            inOrderQueue.setLpcode(order.getLpcode());
            inOrderQueue.setTrackingNo(order.getTrackingNo());
            inOrderQueue.setOrderType2(order.getOrderType2());
            inOrderQueue.setPoints(order.getPoints());
            inOrderQueue.setReturnPoints(order.getReturnPoints());
            inOrderQueue.setIsUrgent(order.getIsUrgent());
            inOrderQueue.setIsBfOutbountCheck(order.getIsBfOutbountCheck());
            inOrderQueueDao.save(inOrderQueue);
            for (WmsRtnOrderLine line : order.getRtnLines()) {
                WmsRtnOrderLineQueue lineQueue = new WmsRtnOrderLineQueue();
                lineQueue.setLineNo(line.getLineNo());
                lineQueue.setSku(line.getSku());
                lineQueue.setQty(line.getQty());
                lineQueue.setSkuName(line.getSkuName());
                lineQueue.setListPrice(line.getListPrice());
                lineQueue.setUnitPrice(line.getUnitPrice());
                lineQueue.setLineAmt(line.getLineAmt());
                lineQueue.setOwner(line.getOwner());
                lineQueue.setInvStatus(line.getInvStatus());
                lineQueue.setExt_code(line.getExt_code());
                lineQueue.setColor(line.getColor());
                lineQueue.setLineSize(line.getSize());
                lineQueue.setOrderQty(line.getOrderQty());
                lineQueue.setWmsRtnInOrderQueue(inOrderQueue);
                if (org.apache.commons.lang3.StringUtils.equals(systemKey, Constants.CAINIAO_DB_SYSTEM_KEY)) {
                    lineQueue.setOrderLineNo(line.getLineNo());
                }
                lineQueueDao.save(lineQueue);
            }
            if (order.getRntInvoices() != null) {
                for (WmsOrderInvoice invoice : order.getRntInvoices()) {
                    WmsOrderInvoiceQueue invoiceQueue = new WmsOrderInvoiceQueue();
                    invoiceQueue.setInvoiceDate(invoice.getInvoiceDate());
                    invoiceQueue.setPayer(invoice.getPayer());
                    invoiceQueue.setInvoiceCode(invoice.getInvoiceCode());
                    invoiceQueue.setWmsInvoiceCode(invoice.getWmsInvoiceCode());
                    invoiceQueue.setItem(invoice.getItem());
                    invoiceQueue.setInvoiceNo(invoice.getInvoiceNo());
                    invoiceQueue.setQty(invoice.getQty());
                    invoiceQueue.setUnitPrice(invoice.getUnitPrice());
                    invoiceQueue.setAmt(invoice.getAmt());
                    invoiceQueue.setMemo(invoice.getMemo());
                    invoiceQueue.setPayee(invoice.getPayee());
                    invoiceQueue.setCompany(invoice.getCompany());
                    invoiceQueue.setDrawer(invoice.getDrawer());
                    invoiceQueue.setWmsRtnInOrderQueue(inOrderQueue);
                    invoiceQueueDao.save(invoiceQueue);
                    if (invoice.getDetials() != null) {
                        for (WmsOrderInvoiceLine invoiceLine : invoice.getDetials()) {
                            WmsOrderInvoiceLineQueue invoiceLineQueue = new WmsOrderInvoiceLineQueue();
                            invoiceLineQueue.setItem(invoiceLine.getItem());
                            invoiceLineQueue.setQty(invoiceLine.getQty());
                            invoiceLineQueue.setUnitPrice(invoiceLine.getUnitPrice());
                            invoiceLineQueue.setAmt(invoiceLine.getAmt());
                            invoiceLineQueue.setLineNo(invoiceLine.getLineNo());
                            invoiceLineQueue.setWmsOrderInvoiceQueue(invoiceQueue);
                            invoiceLineQueueDao.save(invoiceLineQueue);
                        }
                    }

                }

            }
        }
    }

    @Override
    public Pagination<WmsOrderStatus> wmsOrderFinish(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsOrderStatusOms> pagination = wmsOrderStatusOmsDao.wmsOrderConfirm(start, pagesize, starteTime, endTime, systemKey, new Sort[] {new Sort("id asc")}, new BeanPropertyRowMapperExt<WmsOrderStatusOms>(WmsOrderStatusOms.class));
        List<WmsOrderStatus> orderStatus = new ArrayList<WmsOrderStatus>();
        Pagination<WmsOrderStatus> order = new Pagination<WmsOrderStatus>();
        for (WmsOrderStatusOms oms : pagination.getItems()) {
            StockTransApplication sta = staDao.getByCode(oms.getShippingCode());
            List<WmsInvLog> infoLogs = new ArrayList<WmsInvLog>();
            WmsOrderStatus status = new WmsOrderStatus();
            status.setOrderCode(oms.getOrderCode());
            status.setShippingCode(oms.getShippingCode());
            status.setStatus(oms.getStatus());
            status.setOwner(oms.getOwner());
            status.setType(oms.getType());
            status.setOutboundTime(sta.getOutboundTime());// 出库时间
            status.setInboundTime(sta.getInboundTime());
            List<WmsInfoLogOms> infoLogOms = infoLogOmsDao.queryInfoLog(oms.getId(), new BeanPropertyRowMapperExt<WmsInfoLogOms>(WmsInfoLogOms.class));
            for (WmsInfoLogOms wmsInfoLogOms : infoLogOms) {
                WmsInvLog infoLog = new WmsInvLog();
                infoLog.setBarchNo(wmsInfoLogOms.getBarchNo());
                infoLog.setBtachCode(wmsInfoLogOms.getBtachCode());
                infoLog.setInvStatus(wmsInfoLogOms.getInvStatus());
                infoLog.setOwner(wmsInfoLogOms.getOwner());
                infoLog.setTranTime(wmsInfoLogOms.getTranTime());
                infoLog.setSku(wmsInfoLogOms.getSku());
                infoLog.setQty(wmsInfoLogOms.getQty());
                infoLog.setMarketability(wmsInfoLogOms.getIsSales()); // 新增是否可售By FXL
                if (StringUtils.hasText(wmsInfoLogOms.getSns())) {
                    String sn[] = wmsInfoLogOms.getSns().split(",");
                    List<String> sns = new ArrayList<String>();
                    for (int i = 0; i < sn.length; i++) {
                        sns.add(sn[i]);
                    }
                    infoLog.setSns(sns);
                }
                infoLog.setWarehouceCode(wmsInfoLogOms.getWarehouceCode());
                infoLogs.add(infoLog);
            }
            status.setInvLogs(infoLogs);

            List<WmsTransInfo> transInfos = new ArrayList<WmsTransInfo>();
            List<WmsTransInfoOms> transinfoLogOms = infoOmsDao.findOrderId(oms.getId(), new BeanPropertyRowMapperExt<WmsTransInfoOms>(WmsTransInfoOms.class));
            for (WmsTransInfoOms wmsTransInfoOms : transinfoLogOms) {
                WmsTransInfo info = new WmsTransInfo();
                info.setTransCode(wmsTransInfoOms.getTransCode());
                info.setTransNo(wmsTransInfoOms.getTransNo());
                info.setTransTimeType(wmsTransInfoOms.getTransTimeType());
                transInfos.add(info);
            }
            status.setTransInfos(transInfos);
            List<WmsOrderInvoice> invoices = new ArrayList<WmsOrderInvoice>();
            List<WmsOrderInvoiceOms> invoiceOms = invoiceOmsDao.findOrderId(oms.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceOms>(WmsOrderInvoiceOms.class));
            for (WmsOrderInvoiceOms wmsOrderInvoiceOms : invoiceOms) {
                WmsOrderInvoice invoice = new WmsOrderInvoice();
                invoice.setAmt(wmsOrderInvoiceOms.getAmt());
                invoice.setCompany(wmsOrderInvoiceOms.getCompany());
                invoice.setDrawer(wmsOrderInvoiceOms.getDrawer());
                invoice.setInvoiceCode(wmsOrderInvoiceOms.getInvoiceCode());
                invoice.setInvoiceDate(wmsOrderInvoiceOms.getInvoiceDate());
                invoice.setInvoiceNo(wmsOrderInvoiceOms.getInvoiceNo());
                invoice.setInvoiceCode(wmsOrderInvoiceOms.getInvoiceCode());
                invoice.setItem(wmsOrderInvoiceOms.getItem());
                invoice.setMemo(wmsOrderInvoiceOms.getMemo());
                invoice.setPayee(wmsOrderInvoiceOms.getPayee());
                invoice.setPayer(wmsOrderInvoiceOms.getPayer());
                invoice.setQty(wmsOrderInvoiceOms.getQty().longValue());
                invoice.setUnitPrice(wmsOrderInvoiceOms.getUnitPrice());
                invoice.setWmsInvoiceCode(wmsOrderInvoiceOms.getWmsInvoiceCode());
                invoices.add(invoice);
                List<WmsOrderInvoiceLineOms> invoiceLineOms = invoiceLineOmsDao.findInvoiceId(wmsOrderInvoiceOms.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceLineOms>(WmsOrderInvoiceLineOms.class));
                List<WmsOrderInvoiceLine> detials = new ArrayList<WmsOrderInvoiceLine>();
                for (WmsOrderInvoiceLineOms wmsOrderInvoiceLineOms : invoiceLineOms) {
                    WmsOrderInvoiceLine invoiceLine = new WmsOrderInvoiceLine();
                    invoiceLine.setAmt(wmsOrderInvoiceLineOms.getAmt());
                    invoiceLine.setItem(wmsOrderInvoiceLineOms.getItem());
                    invoiceLine.setLineNo(wmsOrderInvoiceLineOms.getLineNo());
                    invoiceLine.setQty(wmsOrderInvoiceLineOms.getQty().longValue());
                    invoiceLine.setUnitPrice(wmsOrderInvoiceLineOms.getUnitPrice());
                    detials.add(invoiceLine);
                }
                invoice.setDetials(detials);

            }

            status.setInvoices(invoices);
            orderStatus.add(status);
        }
        order.setItems(orderStatus);
        order.setStart(start);
        order.setSize(pagesize);
        order.setTotalPages(pagination.getTotalPages());
        return order;
    }

    @Override
    public Pagination<WmsOrderCancelResult> wmsCancelOrderConfirm(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsOrderCancelResult> pagination = new Pagination<WmsOrderCancelResult>();
        Pagination<MsgOrderCancel> msg = msgOrderCancelDao.wmsCancelOrderConfirm(start, pagesize, starteTime, endTime, systemKey, new Sort[] {new Sort("id asc")}, new BeanPropertyRowMapperExt<MsgOrderCancel>(MsgOrderCancel.class));
        List<WmsOrderCancelResult> cancelResults = new ArrayList<WmsOrderCancelResult>();
        for (MsgOrderCancel cancel : msg.getItems()) {
            List<WmsShippingResult> shippingResults = new ArrayList<WmsShippingResult>();
            WmsOrderCancelResult result = new WmsOrderCancelResult();
            StockTransApplication sta = staDao.getByCode(cancel.getStaCode());
            result.setOwner(sta.getOwner());
            result.setOrderCode(sta.getRefSlipCode());
            result.setOrderType(sta.getType().getValue());
            result.setStatus(sta.getStatus().getValue());
            WmsShippingResult shippingResult = new WmsShippingResult();
            cancel.setMsg(cancel.getMsg() == null ? "" : cancel.getMsg());
            if (cancel.getMsg().equals("物流不可达")) {
                shippingResult.setMemo("订单已取消");
                shippingResult.setShippingCode(sta.getCode());
                shippingResult.setStatusCode("S0010");
            } else {
                if (cancel.getIsCanceled()) {
                    shippingResult.setMemo("取消成功-订单已取消");
                    shippingResult.setShippingCode(sta.getCode());
                    shippingResult.setStatusCode("S0002");
                } else {
                    shippingResult.setMemo(cancel.getMsg());
                    shippingResult.setShippingCode(sta.getCode());
                    shippingResult.setStatusCode("E0002");
                }
            }
            shippingResults.add(shippingResult);

            result.setShippings(shippingResults);
            cancelResults.add(result);

        }
        pagination.setSize(msg.getSize());
        pagination.setStart(msg.getStart());
        pagination.setItems(cancelResults);
        pagination.setTotalPages(pagination.getTotalPages());
        return pagination;
    }

    @Override
    public boolean cancelSalesStaByshippingCode1(String shipping) {
        StockTransApplication sta = staDao.getByCode(shipping);
        if (sta == null) {
            // 判断单据是否存在
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        } else {
            // 查询单据是否都已经取消
            if (StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus()) || StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {} else {
                return staCancelManager.cancelStaForSales(sta);
            }
        }
        return true;
    }



    /**
     * 索赔数据同步接口
     * 
     * @param contactCode
     * @param systemKey
     * @param chList
     * @return
     */
    public List<WmsClaimResult> tomsClaimSync(String contactCode, String systemKey, List<ClaimHead> chList) {

        return null;
    }

    /**
     * 理赔处理反馈接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimProcess> receiveWmsClaimProcess(int start, int pagesize, String systemKey, Date startTime, Date endTime) {

        return null;
    }

    /**
     * 索赔结果反馈接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimResult> receiveWmsClaimResult(int start, int pagesize, String systemKey, Date startTime, Date endTime) {

        return null;
    }

    /*
    *//**
     * 小家电3C商家强制入菜鸟仓接口
     * 
     * @param OmsTmallOrder
     * @return BaseResult
     */
    /*
     * public BaseResult saveOMSTmalloutboundOrder(OmsTmallOrder order) { return null; }
     */


    @Override
    public Pagination<WmsOrderStatusOms> queryWmsOrder(int start, int pagesize, Date startTime, Date endTime, String shoppingCode, Sort[] sorts) {
        String[] codeList = (shoppingCode == null || "".equals(shoppingCode.trim())) ? null : shoppingCode.trim().split(",");
        Pagination<WmsOrderStatusOms> list = wmsOrderStatusOmsDao.queryWmsOrder(start, pagesize, startTime, endTime, codeList, sorts, new BeanPropertyRowMapperExt<WmsOrderStatusOms>(WmsOrderStatusOms.class));
        return list;
    }


    @Override
    public Pagination<MsgRtnOutboundCommand> findStaByStaCode(int start, int pagesize, String codeList, Sort[] sorts) {
        Pagination<MsgRtnOutboundCommand> list = new Pagination<MsgRtnOutboundCommand>();
        if (null != codeList && !"".equals(codeList)) {
            String[] codes = codeList.split(",");
            List<String> staCodeList = new ArrayList<String>();
            for (String code : codes) {
                staCodeList.add(code);
            }
            list = msgRtnOutboundDao.findStaByStaCode(start, pagesize, staCodeList, sorts, new BeanPropertyRowMapper<MsgRtnOutboundCommand>(MsgRtnOutboundCommand.class));
        }
        return list;
    }

    public void updateWmsOutBound(List<Long> id) {
        msgRtnOutboundDao.updateWmsOutBound(id);
    }

    @Override
    public void saveAndUpdateOrderStatus(List<Long> ids, String loginName) {
        for (Long id : ids) {
            // 复制为新的数据
            WmsOrderStatusOms orderStatus = wmsOrderStatusOmsDao.getByPrimaryKey(id);
            WmsOrderStatusOms newOrderStatus = new WmsOrderStatusOms();
            newOrderStatus.setCreatetime(new Date(System.currentTimeMillis() + 1000 * 60 * 3));
            newOrderStatus.setLogTime(new Date());
            newOrderStatus.setOrderCode(orderStatus.getOrderCode());
            newOrderStatus.setShippingCode(orderStatus.getShippingCode());
            newOrderStatus.setStatus(orderStatus.getStatus());
            newOrderStatus.setSystemKey(orderStatus.getSystemKey());
            newOrderStatus.setOwner(orderStatus.getOwner());
            newOrderStatus.setType(orderStatus.getType());
            newOrderStatus.setLoginName(loginName);
            wmsOrderStatusOmsDao.save(newOrderStatus);
            orderStatus.setIsOp(true);
            wmsOrderStatusOmsDao.flush();
            // 更新关联表的数据
            infoLogOmsDao.updateInfoLogByOrderStatus(id, newOrderStatus.getId());
            infoOmsDao.updateTransInfoByOrderStatus(id, newOrderStatus.getId());
            invoiceOmsDao.updateOrderInvoiceByOrderStatus(id, newOrderStatus.getId());
        }
    }

    @Override
    public Pagination<WmsOrderStatus> wmsOrderFinishStatus(int start, int pagesize, String systemKey, Date starteTime, Date endTime, Integer type) {
        Pagination<WmsOrderStatusOms> pagination =
                wmsOrderStatusOmsDao.wmsOrderConfirmByType(start, pagesize, starteTime, endTime, systemKey, type, new Sort[] {new Sort("id asc")}, new BeanPropertyRowMapperExt<WmsOrderStatusOms>(WmsOrderStatusOms.class));
        List<WmsOrderStatus> orderStatus = new ArrayList<WmsOrderStatus>();
        Pagination<WmsOrderStatus> order = new Pagination<WmsOrderStatus>();
        List<Integer> typeList = new ArrayList<Integer>();
        typeList.add(type);
        for (WmsOrderStatusOms oms : pagination.getItems()) {
            WmsOrderStatus status = new WmsOrderStatus();
            status.setOrderCode(oms.getOrderCode());
            StockTransApplication sta = staDao.findStaByOrderCode(oms.getShippingCode(), typeList, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
            if (null != sta) {
                status.setSourceOrderCode(sta.getSlipCode1());
                status.setFinishTime(sta.getInboundTime());
            }
            status.setCreateTime(oms.getCreatetime());
            status.setStatus(1);
            OperationUnit u = ouDao.getByPrimaryKey(sta.getMainWarehouse().getId());
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndValue(systemKey, u.getCode());
            status.setRefWarehouseCode(op.getOptionKey());
            List<WmsTransInfo> transInfos = new ArrayList<WmsTransInfo>();
            // List<WmsTransInfoOms> transinfoLogOms = infoOmsDao.findOrderId(oms.getId(), new
            // BeanPropertyRowMapperExt<WmsTransInfoOms>(WmsTransInfoOms.class));
            StaDeliveryInfo staDeliveryInfo = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            if (null != staDeliveryInfo) {
                WmsTransInfo info = new WmsTransInfo();
                info.setTransCode(staDeliveryInfo.getLpCode());
                info.setTransNo(staDeliveryInfo.getTrackingNo());
                transInfos.add(info);
            }
            status.setTransInfos(transInfos);
            List<WmsOrderLine> wmsOrderLines = new ArrayList<WmsOrderLine>();
            List<StaLineCommand> staLineCommandLists = staLineDao.findStaLineByOrderCode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            if (null != staLineCommandLists && staLineCommandLists.size() > 0) {
                for (StaLineCommand staLineCommandList : staLineCommandLists) {
                    WmsOrderLine wmsOrderLine = new WmsOrderLine();
                    wmsOrderLine.setOrderLineNo(staLineCommandList.getLineNo());
                    wmsOrderLine.setQty(staLineCommandList.getQuantity().intValue());
                    wmsOrderLine.setSku(staLineCommandList.getBarCode());
                    wmsOrderLines.add(wmsOrderLine);
                }

            }
            status.setWmsOrderLines(wmsOrderLines);
            orderStatus.add(status);
        }
        order.setItems(orderStatus);
        order.setStart(pagination.getStart());
        order.setSize(pagination.getSize());
        order.setTotalPages(pagination.getTotalPages());
        return order;
    }

    @Override
    public Pagination<WmsOrderStatus> wmsOrderCancel(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<MsgOutboundOrderCancelCommand> pagination =
                msgDao.findOutboundOrderCancelList(start, pagesize, systemKey, starteTime, endTime, 1, new Sort[] {new Sort("id asc")}, new BeanPropertyRowMapperExt<MsgOutboundOrderCancelCommand>(MsgOutboundOrderCancelCommand.class));
        List<WmsOrderStatus> orderStatus = new ArrayList<WmsOrderStatus>();
        Pagination<WmsOrderStatus> order = new Pagination<WmsOrderStatus>();
        for (MsgOutboundOrderCancelCommand oms : pagination.getItems()) {
            WmsOrderStatus status = new WmsOrderStatus();
            StockTransApplication sta = staDao.getByPrimaryKey(oms.getStaId());
            status.setOrderCode(sta.getRefSlipCode());
            status.setSourceOrderCode(sta.getSlipCode1());
            status.setFinishTime(oms.getCreateTime());
            status.setCreateTime(oms.getCreateTime());
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndValue(systemKey, oms.getWarehouseCode());
            status.setRefWarehouseCode(op.getOptionKey());
            status.setStatus(0);
            List<WmsTransInfo> transInfos = new ArrayList<WmsTransInfo>();
            WmsTransInfo info = new WmsTransInfo();
            info.setTransCode(oms.getTransCode());
            info.setTransNo(oms.getTransNo());
            // info.setTransTimeType(wmsTransInfoOms.getTransTimeType());
            transInfos.add(info);

            status.setTransInfos(transInfos);
            List<WmsOrderLine> wmsOrderLines = new ArrayList<WmsOrderLine>();
            List<StaCancel> staCancelLists = staCancelDao.findByStaCancelId(oms.getId(), new BeanPropertyRowMapperExt<StaCancel>(StaCancel.class));
            if (null != staCancelLists && staCancelLists.size() > 0) {
                for (StaCancel staCancelList : staCancelLists) {
                    WmsOrderLine wmsOrderLine = new WmsOrderLine();
                    wmsOrderLine.setOrderLineNo(staCancelList.getOrderLineNo());
                    wmsOrderLine.setQty(staCancelList.getQuantity().intValue());
                    Sku sku = skuDao.getByPrimaryKey(staCancelList.getSku().getId());
                    wmsOrderLine.setSku(sku.getBarCode());
                    wmsOrderLines.add(wmsOrderLine);
                }
            }
            status.setWmsOrderLines(wmsOrderLines);
            orderStatus.add(status);
            // msgDao.updateOrderCancelById(oms.getId(), new Date(), 10);
        }
        order.setItems(orderStatus);
        order.setStart(pagination.getStart());
        order.setSize(pagination.getSize());
        order.setTotalPages(pagination.getTotalPages());
        return order;
    }

    /**
     * 行取消--订单取消
     * 
     * @param lines
     * @param orderCode
     * @return
     */
    @Override
    public WmsOrderCancel cancelSalesStaByLineNo(List<WmsSalesOrderLine> lines, String orderCode) {
        WmsOrderCancel wmsOrderCancel = new WmsOrderCancel();
        wmsOrderCancel.setOrderCode(orderCode);
        List<WmsOrderCancelLine> orderLineNos = new ArrayList<WmsOrderCancelLine>();
        List<StockTransApplication> isStaExist = staDao.findBySlipCode1(orderCode);
        if (null != isStaExist && isStaExist.size() > 0) {
            StockTransApplication sta = staDao.getByPrimaryKey(isStaExist.get(0).getId());
            sta.setNoHaveReportMissing(true);// 强制过滤报缺(AD 拣货前部分取消 不用重复复核 true)
            for (WmsSalesOrderLine line : lines) {
                WmsOrderCancelLine wmsOrderCancelLine = new WmsOrderCancelLine();
                if (null != isStaExist.get(0).getIsShortPicking() && isStaExist.get(0).getIsShortPicking()) {
                    if (isStaExist.get(0).getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
                        invDao.releaseInvForAdLineCancel(line.getLineNo(), isStaExist.get(0).getId());
                        lineDao.deleteInvForAdLineCancel(line.getLineNo(), isStaExist.get(0).getId());
                    }
                    StaLine staLine2 = staLineDao.findStaLineIsCancel(true, line.getLineNo(), isStaExist.get(0).getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
                    staLineDao.updateStaLineIsCancel(true, 0L, line.getLineNo(), isStaExist.get(0).getId());
                    StaLine staLine = staLineDao.findCancelLineByStaIdAndLineNo(isStaExist.get(0).getId(), line.getLineNo(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
                    Long skuQty = isStaExist.get(0).getSkuQty() - staLine.getOrderQty();

                    if (staLine2 == null) {
                        staDao.updateStaSkuQty(isStaExist.get(0).getId(), skuQty);
                        StaLine sl = staLineDao.getByPrimaryKey(staLine.getId());
                        sta.setTotalActual(sta.getTotalActual().subtract(sl.getTotalActual()));
                        sta.setOrderTotalActual(sta.getTotalActual());
                    } else {// 已经取消
                        // staDao.updateStaSkuQty(isStaExist.get(0).getId(), skuQty);
                        // StaLine sl = staLineDao.getByPrimaryKey(staLine.getId());
                        // sta.setTotalActual(sta.getTotalActual().subtract(sl.getTotalActual()));
                        // sta.setOrderTotalActual(sta.getTotalActual());
                        log.info("AD 已经取消." + sta.getCode() + ";" + staLine.getId());
                    }
                    wmsOrderCancelLine.setStatus(1);
                    Integer count = staLineDao.findResultForOperation(staLine.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
                    if (null != count && count == 0) {
                        cancelSalesSta("adidas", orderCode, true);
                    }
                } else {
                    wmsOrderCancelLine.setStatus(0);
                }
                wmsOrderCancelLine.setLineNo(line.getLineNo());
                orderLineNos.add(wmsOrderCancelLine);
            }
        }
        wmsOrderCancel.setOrderLineNos(orderLineNos);
        return wmsOrderCancel;
    }

    /**
     * 整单取消--订单取消
     * 
     * @param lines
     * @param orderCode
     * @return
     */
    @Override
    public WmsOrderCancelResult cancelSalesSta(String systemKey, String orderCode, boolean platCancel) {
        List<StockTransApplication> sta = staDao.findBySlipCode1(orderCode);
        WmsOrderCancelResult rs = new WmsOrderCancelResult();
        for (StockTransApplication list : sta) {
            if (!list.getStatus().equals(StockTransApplicationStatus.CANCELED)) {
                if (null != sta && sta.size() > 0) {
                    boolean flag = false;
                    if (list.getStatus().equals(StockTransApplicationStatus.FROZEN)) {
                        flag = true;
                        staDao.updateStaStatusByid(list.getId(), StockTransApplicationStatus.CANCELED.getValue());
                        StockTransApplication stac = staDao.getByPrimaryKey(list.getId());
                        staCancelManager.releaseInventory2(stac);
                    } else {
                        rs = hubWmsService.cancelSalesStaByshippingCode(list.getCode());
                    }
                    if (flag || (null != rs.getShippings() && rs.getShippings().size() > 0 && (rs.getShippings().get(0).getStatusCode().equals(COLSE_SUCCESS) || rs.getShippings().get(0).getStatusCode().equals(COLSE_SUCCESS1)))) {
                        List<StaLine> sl = staLineDao.findByStaId(list.getId());
                        for (StaLine s : sl) {
                            if (!s.getQuantity().equals(0L)) {
                                s.setQuantity(0L);
                                if (platCancel) {
                                    s.setIsCancel(platCancel);
                                }
                            }
                        }
                        MsgOutboundOrderCancel msg = new MsgOutboundOrderCancel();
                        msg.setCreateTime(new Date());
                        msg.setErrorCount(0L);
                        msg.setIsCanceled(true);
                        msg.setSystemKey("adidas");
                        msg.setSource("adidas");
                        msg.setStatus(MsgOutboundOrderCancelStatus.CREATED);
                        msg.setUpdateTime(new Date());
                        msg.setStaCode(list.getCode());
                        msg.setStaId(list.getId());
                        msg.setSilpCode1(list.getSlipCode1());
                        OperationUnit u = ouDao.getByPrimaryKey(list.getMainWarehouse().getId());
                        // ChooseOption op = chooseOptionDao.findByCategoryCodeAndValue(systemKey,
                        // u.getCode());
                        msg.setWarehouseCode(u.getCode());
                        StaDeliveryInfo stainfo = staDeliveryInfoDao.getByPrimaryKey(list.getId());
                        msg.setTransCode(stainfo.getLpCode());
                        msg.setTransNo(stainfo.getTrackingNo());
                        msgDao.save(msg);
                        List<StaLineCommand> lists = staLineDao.findStaLineByOrderCode(list.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                        for (StaLineCommand staLine : lists) {
                            StaCancel cancel = new StaCancel();
                            cancel.setCreateDate(new Date());
                            cancel.setQuantity(0L);
                            cancel.setOrderLineNo(staLine.getLineNo());
                            cancel.setOwner("adidas");
                            Sku sku = skuDao.getSkuByBarcode(staLine.getBarCode());
                            cancel.setSku(sku);
                            cancel.setOrderCancel(msg);
                            cancelDao.save(cancel);
                        }

                    }
                }
            }
        }

        return rs;
    }

    @Override
    public Pagination<WmsOrderStatus> wmsOrderFinishByType(int start, int pagesize, String systemKey, Date starteTime, Date endTime, List<Integer> type) {
        Pagination<WmsOrderStatusOms> pagination = wmsOrderStatusOmsDao.wmsOrderFinishByType(start, pagesize, starteTime, endTime, systemKey, type, new Sort[] {new Sort("id asc")}, new BeanPropertyRowMapperExt<WmsOrderStatusOms>(WmsOrderStatusOms.class));
        List<WmsOrderStatus> orderStatus = new ArrayList<WmsOrderStatus>();
        Pagination<WmsOrderStatus> order = new Pagination<WmsOrderStatus>();
        for (WmsOrderStatusOms oms : pagination.getItems()) {
            WmsOrderStatus status = new WmsOrderStatus();
            StockTransApplication sta = staDao.findStaByOrderCode(oms.getShippingCode(), null, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
            status.setOrderCode(oms.getOrderCode());
            status.setSourceOrderCode(sta.getSlipCode1());
            status.setFinishTime(sta.getOutboundTime());
            status.setShippingCode(oms.getOrderCode());
            OperationUnit u = ouDao.getByPrimaryKey(sta.getMainWarehouse().getId());
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndValue(systemKey, u.getCode());
            status.setRefWarehouseCode(op.getOptionKey());
            status.setStatus(1);
            List<WmsTransInfo> transInfos = new ArrayList<WmsTransInfo>();
            List<WmsTransInfoOms> transinfoLogOms = infoOmsDao.findOrderId(oms.getId(), new BeanPropertyRowMapperExt<WmsTransInfoOms>(WmsTransInfoOms.class));
            for (WmsTransInfoOms wmsTransInfoOms : transinfoLogOms) {
                WmsTransInfo info = new WmsTransInfo();
                info.setTransCode(wmsTransInfoOms.getTransCode());
                info.setTransNo(wmsTransInfoOms.getTransNo());
                info.setTransTimeType(wmsTransInfoOms.getTransTimeType());
                StaAdditionalLineCommand staAddCommand = staAddLineDao.findAddLineByTrackingNo(wmsTransInfoOms.getTransNo(), wmsTransInfoOms.getTransCode(), new BeanPropertyRowMapper<StaAdditionalLineCommand>(StaAdditionalLineCommand.class));
                if (null != staAddCommand) {
                    if (null != staAddCommand.getAddLinewidth() && null != staAddCommand.getHeight() && null != staAddCommand.getLength()) {
                        BigDecimal addLinewidth = staAddCommand.getAddLinewidth().divide(new BigDecimal(1000));
                        BigDecimal height = staAddCommand.getHeight().divide(new BigDecimal(1000));
                        BigDecimal length = staAddCommand.getLength().divide(new BigDecimal(1000));
                        BigDecimal volume = addLinewidth.multiply(height).multiply(length);
                        info.setVolume(volume);
                    }
                    info.setWeight(staAddCommand.getWeight());
                }
                info.setCartonQuantity(1);
                transInfos.add(info);
            }
            status.setTransInfos(transInfos);
            List<WmsOrderLine> wmsOrderLines = new ArrayList<WmsOrderLine>();
            List<StaLineCommand> staLineCommandLists = staLineDao.findStaLineByOrderCode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            if (null != staLineCommandLists && staLineCommandLists.size() > 0) {
                for (StaLineCommand staLineCommandList : staLineCommandLists) {
                    WmsOrderLine wmsOrderLine = new WmsOrderLine();
                    wmsOrderLine.setOrderLineNo(staLineCommandList.getLineNo());
                    wmsOrderLine.setQty(staLineCommandList.getQuantity().intValue());
                    wmsOrderLine.setSku(staLineCommandList.getBarCode());
                    wmsOrderLines.add(wmsOrderLine);
                }

            }
            status.setWmsOrderLines(wmsOrderLines);
            orderStatus.add(status);
        }
        order.setItems(orderStatus);
        order.setStart(pagination.getStart());
        order.setSize(pagination.getSize());
        order.setTotalPages(pagination.getTotalPages());
        return order;
    }

    @Override
    public Pagination<WmsConfirmOrderQueue> queryWmsConfirmOrderQueue(int start, int pageSize, Date startTime, Date endTime, String shoppingCode, Sort[] sorts) {
        String[] codeList = (shoppingCode == null || "".equals(shoppingCode.trim())) ? null : shoppingCode.trim().split(",");
        Pagination<WmsConfirmOrderQueue> list = wmsConfirmOrderQueueDao.queryWmsConfirmOrderQueue(start, pageSize, startTime, endTime, codeList, sorts, new BeanPropertyRowMapperExt<WmsConfirmOrderQueue>(WmsConfirmOrderQueue.class));
        return list;
    }

    @Override
    public void updateWmsConfirmOrderQueue(List<Long> ids, String loginName) {
        for (Long id : ids) {
            // 更新数据
            WmsConfirmOrderQueue wmsConfirmOrderQueue = wmsConfirmOrderQueueDao.getByPrimaryKey(id);
            if (wmsConfirmOrderQueue != null) {
                wmsConfirmOrderQueue.setIsMq("1");
                wmsConfirmOrderQueue.setMqStatus("1");
                wmsConfirmOrderQueueDao.save(wmsConfirmOrderQueue);
            }
        }
    }


    public void insertAgvTransitInnerToHub(Long stvId) {
        List<StvLineCommand> stvLineCommandList = lineDao.findAgvTransitInnerByStvId(stvId, new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        if (null != stvLineCommandList && stvLineCommandList.size() > 0) {
            Boolean falg = false;
            for (StvLineCommand stvLineCommand : stvLineCommandList) {
                if (stvLineCommand.getIsMixTime() != null && !"".equals(stvLineCommand.getIsMixTime()) && "1".equals(stvLineCommand.getIsMixTime())) {
                    falg = true;
                    break;
                }
            }
            
            if (falg) {
            	 InboundAgvToHub inboundAgvToHub = new InboundAgvToHub();
                 StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
                 inboundAgvToHub.setApiName("inbound_order");
                 inboundAgvToHub.setStaId(stv.getSta().getId());
                 inboundAgvToHub.setCreateTime(new Date());
                 inboundAgvToHub.setErrorCount(0L);
                 inboundAgvToHub.setStatus(1L);
                 inboundAgvToHub.setType(String.valueOf(stv.getSta().getType().getValue()));
                 inboundAgvToHubDao.save(inboundAgvToHub);
                 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                for (StvLineCommand stvLineCommand : stvLineCommandList) {
                    if (stvLineCommand.getIsMixTime() != null && !"".equals(stvLineCommand.getIsMixTime()) && "1".equals(stvLineCommand.getIsMixTime())) {
                        InboundAgvToHubLine inboundAgvToHubLine = new InboundAgvToHubLine();
                        inboundAgvToHubLine.setInAgvId(inboundAgvToHub.getId());
                        inboundAgvToHubLine.setQty(stvLineCommand.getQuantity());
                        inboundAgvToHubLine.setSkuId(stvLineCommand.getSkuId());
                        Sku sku = skuDao.getByPrimaryKey(stvLineCommand.getSkuId());
                        if (sku.getStoremode().equals(InboundStoreMode.SHELF_MANAGEMENT)) {
                            if (stvLineCommand.getExpireDate() != null) {
                                inboundAgvToHubLine.setExpireDate(sdf.format(stvLineCommand.getExpireDate()));
                            }
                        }
                        inboundAgvToHubLineDao.save(inboundAgvToHubLine);
                    }
                }
            }
        }
    }


    @Override
    public void insertAgvInBoundToHub(Long stvId) {
        // List<StockTransTxLogCommand> stockTransTxLogCommandList=stDao.findLocLogByStaId(staId,
        // new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));

        List<StvLineCommand> stvLineCommandList = lineDao.findAgvStvLinesListByStvId(stvId, new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        if (null != stvLineCommandList && stvLineCommandList.size() > 0) {
            Boolean falg = false;
            for (StvLineCommand stvLineCommand : stvLineCommandList) {
                if (stvLineCommand.getIsMixTime() != null && !"".equals(stvLineCommand.getIsMixTime()) && "1".equals(stvLineCommand.getIsMixTime())) {
                    falg = true;
                    break;
                }
            }
            if (falg) {
                InboundAgvToHub inboundAgvToHub = new InboundAgvToHub();
                StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
                inboundAgvToHub.setApiName("inbound_order");
                inboundAgvToHub.setStaId(stv.getSta().getId());
                inboundAgvToHub.setCreateTime(new Date());
                inboundAgvToHub.setErrorCount(0L);
                inboundAgvToHub.setStatus(1L);
                inboundAgvToHub.setType(String.valueOf(stv.getSta().getType().getValue()));
                inboundAgvToHubDao.save(inboundAgvToHub);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                for (StvLineCommand stvLineCommand : stvLineCommandList) {
                    if (stvLineCommand.getIsMixTime() != null && !"".equals(stvLineCommand.getIsMixTime()) && "1".equals(stvLineCommand.getIsMixTime())) {
                        InboundAgvToHubLine inboundAgvToHubLine = new InboundAgvToHubLine();
                        inboundAgvToHubLine.setInAgvId(inboundAgvToHub.getId());
                        inboundAgvToHubLine.setQty(stvLineCommand.getQuantity());
                        inboundAgvToHubLine.setSkuId(stvLineCommand.getSkuId());
                        Sku sku = skuDao.getByPrimaryKey(stvLineCommand.getSkuId());
                        if (sku.getStoremode().equals(InboundStoreMode.SHELF_MANAGEMENT)) {
                            if (stvLineCommand.getExpireDate() != null) {
                                inboundAgvToHubLine.setExpireDate(sdf.format(stvLineCommand.getExpireDate()));
                            }
                        }
                        inboundAgvToHubLineDao.save(inboundAgvToHubLine);
                    }
                }
            }

        }

    }


    @Override
    public void insertAgvSku(Long createOrUpdate, Long skuId) {
        AgvSku agvSku = new AgvSku();
        agvSku.setCreateOrUpdate(createOrUpdate);
        if (createOrUpdate == 1) {
            agvSku.setApiName("kc.sku.create");
        } else {
            agvSku.setApiName("kc.sku.update");
        }
        agvSku.setCreateTime(new Date());
        agvSku.setErrorCount(0L);
        agvSku.setSkuId(skuId);
        agvSku.setStatus(1L);
        agvSkuDao.save(agvSku);
    }
}
