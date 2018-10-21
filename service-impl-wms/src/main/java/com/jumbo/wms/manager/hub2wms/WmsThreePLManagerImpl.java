package com.jumbo.wms.manager.hub2wms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.json.JSON;
import com.baozun.bizhub.manager.taobao.HubCainiaoWmsManager;
import com.baozun.bizhub.model.taobao.cainiao.HubConsignOrderConfirmRequest;
import com.baozun.bizhub.model.taobao.cainiao.HubConsignOrderConfirmRequest.HubConsignOrderItem;
import com.baozun.bizhub.model.taobao.cainiao.HubConsignOrderConfirmRequest.HubConsignOrderItem.HubConsignItemInventory;
import com.baozun.bizhub.model.taobao.cainiao.HubConsignOrderConfirmRequest.HubConsignTmsOrder;
import com.baozun.bizhub.model.taobao.cainiao.HubConsignOrderConfirmRequest.HubConsignTmsOrder.HubConsignTmsItem;
import com.baozun.bizhub.model.taobao.cainiao.HubConsignOrderConfirmResponse;
import com.baozun.bizhub.model.taobao.cainiao.HubInventoryCountRequest;
import com.baozun.bizhub.model.taobao.cainiao.HubInventoryCountRequest.HubInventoryCountReturnOrderItem;
import com.baozun.bizhub.model.taobao.cainiao.HubInventoryCountResponse;
import com.baozun.bizhub.model.taobao.cainiao.HubItemQueryRequest;
import com.baozun.bizhub.model.taobao.cainiao.HubItemQueryResponse;
import com.baozun.bizhub.model.taobao.cainiao.HubItemQueryResponse.HubItem;
import com.baozun.bizhub.model.taobao.cainiao.HubOrderStatusUploadRequest;
import com.baozun.bizhub.model.taobao.cainiao.HubOrderStatusUploadResponse;
import com.baozun.bizhub.model.taobao.cainiao.HubStockInOrderConfirmRequest;
import com.baozun.bizhub.model.taobao.cainiao.HubStockInOrderConfirmRequest.HubStockInCheckItem;
import com.baozun.bizhub.model.taobao.cainiao.HubStockInOrderConfirmRequest.HubStockInOrderItem;
import com.baozun.bizhub.model.taobao.cainiao.HubStockInOrderConfirmRequest.HubStockInOrderItem.HubStockInItemInventory;
import com.baozun.bizhub.model.taobao.cainiao.HubStockInOrderConfirmResponse;
import com.baozun.bizhub.model.taobao.cainiao.HubStockOutOrderConfirmRequest;
import com.baozun.bizhub.model.taobao.cainiao.HubStockOutOrderConfirmRequest.HubStockOutOrderItem;
import com.baozun.bizhub.model.taobao.cainiao.HubStockOutOrderConfirmRequest.HubStockOutOrderItem.HubStockOutItemInventory;
import com.baozun.bizhub.model.taobao.cainiao.HubStockOutOrderConfirmResponse;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.hub2wms.CnInvCountReturnOrderItemDao;
import com.jumbo.dao.hub2wms.CnOrderPropertyDao;
import com.jumbo.dao.hub2wms.CnOutOrderItemDao;
import com.jumbo.dao.hub2wms.CnOutOrderNotifyDao;
import com.jumbo.dao.hub2wms.CnOutReceiverInfoDao;
import com.jumbo.dao.hub2wms.CnOutSenderInfoDao;
import com.jumbo.dao.hub2wms.CnSenderInfoDao;
import com.jumbo.dao.hub2wms.CnSkuInfoDao;
import com.jumbo.dao.hub2wms.CnSnSampleDao;
import com.jumbo.dao.hub2wms.CnSnSampleRuleDao;
import com.jumbo.dao.hub2wms.CnStockInItemInventoryDao;
import com.jumbo.dao.hub2wms.CnStockInOrderLineDao;
import com.jumbo.dao.hub2wms.CnWmsInventoryCountDao;
import com.jumbo.dao.hub2wms.CnWmsOrderStatusUploadDao;
import com.jumbo.dao.hub2wms.CnWmsStockInCaseInfoDao;
import com.jumbo.dao.hub2wms.CnWmsStockInCaseItemDao;
import com.jumbo.dao.hub2wms.CnWmsStockInOrderConfirmDao;
import com.jumbo.dao.hub2wms.CnWmsStockInOrderLineDao;
import com.jumbo.dao.hub2wms.CnWmsStockInOrderNotifyDao;
import com.jumbo.dao.hub2wms.HubStockInCheckItemDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.CnInterfaceTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnOrderLine;
import com.jumbo.wms.model.hub2wms.threepl.CnInvCountReturnOrderItem;
import com.jumbo.wms.model.hub2wms.threepl.CnOrderProperty;
import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderItem;
import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.CnOutReceiverInfo;
import com.jumbo.wms.model.hub2wms.threepl.CnOutSenderInfo;
import com.jumbo.wms.model.hub2wms.threepl.CnSenderInfo;
import com.jumbo.wms.model.hub2wms.threepl.CnSkuInfo;
import com.jumbo.wms.model.hub2wms.threepl.CnSnSample;
import com.jumbo.wms.model.hub2wms.threepl.CnSnSampleRule;
import com.jumbo.wms.model.hub2wms.threepl.CnStockInItemInventory;
import com.jumbo.wms.model.hub2wms.threepl.CnStockInOrderLine;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsInventoryCount;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsOrderStatusUpload;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInCaseInfo;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInCaseItem;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderConfirm;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderLine;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.SenderInfo;
import com.jumbo.wms.model.hub2wms.threepl.WmsSkuInfo;
import com.jumbo.wms.model.hub2wms.threepl.WmsSkuInfo.SnSample;
import com.jumbo.wms.model.hub2wms.threepl.WmsSkuInfo.SnSample.SampleRule;
import com.jumbo.wms.model.hub2wms.threepl.WmsStockInCaseInfo;
import com.jumbo.wms.model.hub2wms.threepl.WmsStockInCaseItem;
import com.jumbo.wms.model.hub2wms.threepl.WmsStockInOrderItem;
import com.jumbo.wms.model.hub2wms.threepl.WmsStockInOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.WmsStockOutOrderItem;
import com.jumbo.wms.model.hub2wms.threepl.WmsStockOutOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.WmsThreeplRespose;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

/**
 * WMS对外做 外包仓接口
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
@SuppressWarnings("all")
@Service("wmsThreePLManager")
public class WmsThreePLManagerImpl extends BaseManagerImpl implements WmsThreePLManager {

    private static final long serialVersionUID = 4411633149136187944L;
    protected static final Logger log = LoggerFactory.getLogger(WmsThreePLManagerImpl.class);
    @Autowired
    private HubCainiaoWmsManager hubCainiaoWmsManager;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private MsgInboundOrderLineDao msgInboundOrderLineDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutboundLineDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private CnSkuInfoDao cnSkuInfoDao;
    @Autowired
    private CnSnSampleDao cnSnSampleDao;
    @Autowired
    private CnSnSampleRuleDao cnSnSampleRuleDao;
    @Autowired
    private CnOutOrderNotifyDao cnOutOrderNotifyDao;
    @Autowired
    private CnOutReceiverInfoDao cnOutReceiverInfoDao;
    @Autowired
    private CnOutOrderItemDao cnOutOrderItemDao;
    @Autowired
    private CnOutSenderInfoDao cnOutSenderInfoDao;
    @Autowired
    private CnSenderInfoDao cnSenderInfoDao;
    @Autowired
    private CnWmsStockInOrderNotifyDao cnWmsStockInOrderNotifyDao;
    @Autowired
    private CnWmsStockInOrderLineDao cnWmsStockInOrderLineDao;
    @Autowired
    private CnWmsStockInCaseInfoDao cnWmsStockInCaseInfoDao;
    @Autowired
    private CnWmsStockInCaseItemDao cnWmsStockInCaseItemDao;
    @Autowired
    private HubWmsService hubWmsService;
    @Autowired
    private CnWmsStockInOrderConfirmDao cnWmsStockInOrderConfirmDao;
    @Autowired
    private CnStockInOrderLineDao cnStockInOrderLineDao;
    @Autowired
    private CnWmsOrderStatusUploadDao cnWmsOrderStatusUploadDao;
    @Autowired
    private CnWmsInventoryCountDao cnWmsInventoryCountDao;
    @Autowired
    private CnInvCountReturnOrderItemDao cnInvCountReturnOrderItemDao;
    @Autowired
    private HubStockInCheckItemDao hubStockInCheckItemDao;
    @Autowired
    private CnStockInItemInventoryDao cnStockInItemInventoryDao;
    @Autowired
    private CnInterfaceTask cnInterfaceTask;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private CnOrderPropertyDao cnOrderPropertyDao;
    @Autowired
    private StockTransVoucherDao stvDao;

    /**
     * 3.3.2 商品信息通知 DB-WMS
     * 
     * @param skuInfo
     */
    public WmsThreeplRespose wmsSkuInfoNotify(String systemKey, WmsSkuInfo skuInfo) {
        WmsThreeplRespose wtr = new WmsThreeplRespose();
        try {
            log.info("cainiao create sku :" + JSON.json(skuInfo));
            if (skuInfo == null) {
                wtr.setSuccess(false);
                wtr.setErrorCode("S12");
                wtr.setErrorMsg("参数为空！");
                return wtr;
            }
            CnSkuInfo si = wmsSkuInfoToCnSkuInfo(skuInfo);
            si.setSkuCode(systemKey + "_" + si.getItemId());
            si.setStatus(1);
            si.setSystemKey(systemKey);
            cnSkuInfoDao.save(si);
            List<CnSnSample> cssList = new ArrayList<CnSnSample>();
            List<CnSnSampleRule> cssrList = new ArrayList<CnSnSampleRule>();

            snSampleToCnSnSample(skuInfo.getSnSampleList(), cssList, cssrList, si);
            if (cssList != null && cssList.size() > 0) {
                for (CnSnSample css : cssList) {
                    cnSnSampleDao.save(css);
                }
                if (cssrList != null && cssrList.size() > 0) {
                    for (CnSnSampleRule cssr : cssrList) {
                        cnSnSampleRuleDao.save(cssr);
                    }
                }
            }
            // 是否是删除商品
            if (StringUtils.equals("DELETE", skuInfo.getActionType())) {
                boolean b = deleteSkuByCaiNiao(skuInfo, wtr);
                if (!b) {
                    si.setStatus(-1);
                } else {
                    wtr.setSuccess(true);
                }
            } else {

                cnInterfaceTask.createSkuByCnSku(si);
                wtr.setSuccess(true);
            }
        } catch (Exception e) {
            log.error("itemId :" + skuInfo.getItemId(), e);
            wtr.setSuccess(false);
            wtr.setErrorCode("S13");
            wtr.setErrorMsg("WMS 系统异常！");
            return wtr;
        }
        return wtr;
    }



    /**
     * 菜鸟接口删除商品
     * 
     * @param skuInfo
     * @return
     */
    public boolean deleteSkuByCaiNiao(WmsSkuInfo skuInfo, WmsThreeplRespose wtr) {
        // 1、查询商品是否存在
        // Brand brand=brandDao.getByCode(skuInfo.getBrand());
        Customer c = customerDao.getByCode(Constants.CAINIAO_DB_CUSTOMER_CODE);
        Sku sku = skuDao.getByExtCode1AndCustomer(skuInfo.getItemId(), c.getId());
        if (sku != null) {
            // 2、判断是否有库存
            // List<Inventory> invList = inventoryDao.findNotZeroInvBySkuId(sku.getId(), new
            // BeanPropertyRowMapper<Inventory>(Inventory.class));
            Long inv = inventoryDao.findInvQtyBySkuId(sku.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (inv == null || inv == 0) {
                skuBarcodeDao.deleteBySkuId(sku.getId());
                // 3、商品编码后面加_D
                sku.setCode(sku.getCode() + "_D");
                sku.setBarCode(sku.getBarCode() + "_D");
                sku.setExtensionCode1(sku.getExtensionCode1() == null ? null : sku.getExtensionCode1() + "_D");
                sku.setExtensionCode2(sku.getExtensionCode2() == null ? null : sku.getExtensionCode2() + "_D");
                sku.setExtCode2AndCustomer(sku.getExtCode2AndCustomer() == null ? null : sku.getExtCode2AndCustomer() + "_D");
                sku.setCustomerSkuCode(sku.getCustomerSkuCode() == null ? null : sku.getCustomerSkuCode() + "_D");

                skuDao.save(sku);
            } else {
                wtr.setSuccess(false);
                wtr.setErrorCode("S13");
                wtr.setErrorMsg("此商品在宝尊仓库中有库存，无法删除！");
                return false;
            }

        } else {
            wtr.setSuccess(false);
            wtr.setErrorCode("S13");
            wtr.setErrorMsg("宝尊没有此商品！");
            return false;
        }

        return true;
    }

    public CnSkuInfo wmsSkuInfoToCnSkuInfo(WmsSkuInfo skuInfo) {
        CnSkuInfo si = new CnSkuInfo();
        BeanUtils.copyProperties(skuInfo, si);
        return si;
    }

    public void snSampleToCnSnSample(List<SnSample> ss, List<CnSnSample> cssList, List<CnSnSampleRule> cssrList, CnSkuInfo si) {

        if (ss != null) {
            for (SnSample s : ss) {
                CnSnSample css = new CnSnSample();
                BeanUtils.copyProperties(s, css);
                css.setCnSkuInfo(si);
                cssList.add(css);
                List<SampleRule> srList = s.getSampleRuleList();
                if (srList != null) {
                    for (SampleRule sr : srList) {
                        CnSnSampleRule cssr = new CnSnSampleRule();
                        BeanUtils.copyProperties(sr, cssr);
                        cssr.setCnSnSample(css);
                        cssrList.add(cssr);
                    }
                }
            }
        }
    }

    /**
     * 3.3.3 WMS查询订单商品信息
     * 
     * @param source 来源 :DB
     * @param providerTpId 货主ID
     * @param itemList skuItemList
     */
    public void wmsSkuInfoQuery(String source, Long providerTpId, List<Long> itemList, String systemKey) {
        // 封装请求参数
        HubItemQueryRequest req = new HubItemQueryRequest();
        req.setItemIds(itemList);
        req.setProviderTpId(providerTpId);
        // 调用hub接口
        HubItemQueryResponse rep = hubCainiaoWmsManager.itemQuery(source, req);
        List<HubItem> hiList = rep.getItemList();
        if (hiList != null && hiList.size() > 0) {
            for (HubItem hubItem : hiList) {
                CnSkuInfo csi = cnSkuInfoDao.getCnSkuInfoListByVersion(hubItem.getProviderIpId().toString(), hubItem.getItemId().toString(), hubItem.getVersion().longValue());
                if (csi != null) {
                    continue;
                }
                WmsSkuInfo wmsSkuInfo = new WmsSkuInfo();
                hubItem2WmsSkuInfo(hubItem, wmsSkuInfo);
                wmsSkuInfoNotify(systemKey, wmsSkuInfo);
            }
        } else {
            log.error("get cainiao sku : itemId=" + itemList.toString() + "   " + rep.getErrCode() + "_" + rep.getErrMsg());
        }
    }

    public void hubItem2WmsSkuInfo(HubItem hubItem, WmsSkuInfo wmsSkuInfo) {
        String[] s = {"snSampleList", "sampleRuleList", "itemId", "extendFields"};
        BeanUtils.copyProperties(hubItem, wmsSkuInfo, s);
        wmsSkuInfo.setItemId(hubItem.getItemId().toString());
        wmsSkuInfo.setOwnerUserId(hubItem.getProviderIpId().toString());
        wmsSkuInfo.setItemVersion(hubItem.getVersion().longValue());
        wmsSkuInfo.setName(hubItem.getItemName());
        Map<String, Object> ooiMap = hubItem.getExtendFields();
        if (ooiMap != null && ooiMap.size() > 0) {
            wmsSkuInfo.setExtendFields(ooiMap.toString());
        }
        List<com.baozun.bizhub.model.taobao.cainiao.HubItemQueryResponse.HubItem.SnSample> ssList = hubItem.getSnSampleList();
        if (ssList != null && ssList.size() > 0) {
            List<SnSample> ssList2 = new ArrayList<SnSample>();
            for (com.baozun.bizhub.model.taobao.cainiao.HubItemQueryResponse.HubItem.SnSample ss : ssList) {
                SnSample ss2 = new SnSample();
                BeanUtils.copyProperties(ss, ss2, s);
                List<com.baozun.bizhub.model.taobao.cainiao.HubItemQueryResponse.HubItem.SnSample.SampleRule> sr = ss.getSampleRuleList();
                if (sr != null && sr.size() > 0) {
                    List<SampleRule> srList2 = new ArrayList<SampleRule>();
                    for (com.baozun.bizhub.model.taobao.cainiao.HubItemQueryResponse.HubItem.SnSample.SampleRule sr1 : sr) {
                        SampleRule sr2 = new SampleRule();
                        BeanUtils.copyProperties(sr1, sr2);
                        srList2.add(sr2);
                    }
                    ss2.setSampleRuleList(srList2);
                }
                ssList2.add(ss2);
            }
            wmsSkuInfo.setSnSampleList(ssList2);
        }
    }

    /**
     * 3.3.6 入库通知单接口
     */
    @Override
    public WmsThreeplRespose wmsStockInOrderNotify(String systemKey, WmsStockInOrderNotify wmsStockInOrderNotify) {
        WmsThreeplRespose wtr = new WmsThreeplRespose();
        try {
            if (wmsStockInOrderNotify == null) {
                wtr.setSuccess(false);
                wtr.setErrorCode("S12");
                wtr.setErrorMsg("非法的请求参数，请求为空");
                return wtr;
            }
            // 控制重复下发(同一入库指令分批次下发不支持)
            CnWmsStockInOrderNotify order = cnWmsStockInOrderNotifyDao.getByOrderCode(wmsStockInOrderNotify.getOrderCode());
            if (order != null) {
                wtr.setSuccess(false);
                wtr.setErrorCode("S20");
                wtr.setErrorMsg("业务报文校验参数不通过,orderCode:" + wmsStockInOrderNotify.getOrderCode() + "已下发！");
                return wtr;
            }
            // 保存映射关系中间表
            CnOrderProperty c = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(wmsStockInOrderNotify.getOrderCode());
            if (c == null) {
                CnOrderProperty cop = new CnOrderProperty();
                cop.setOrderCode(wmsStockInOrderNotify.getOrderCode());
                cop.setSystemKey(systemKey);
                cop.setOrderType(wmsStockInOrderNotify.getOrderType() + "");
                cop.setStoreCode(wmsStockInOrderNotify.getStoreCode());
                cnOrderPropertyDao.save(cop);
            }

            // 接受并保存数据
            String[] s = {"senderInfo", "caseInfoList", "orderItemList"};
            CnWmsStockInOrderNotify cnSION = new CnWmsStockInOrderNotify();
            BeanUtils.copyProperties(wmsStockInOrderNotify, cnSION, s);
            // 分批下发控制信息
            if (wmsStockInOrderNotify.getBatchSendCtrlParam() != null) {
                cnSION.setTotalOrderItemCount(wmsStockInOrderNotify.getBatchSendCtrlParam().getTotalOrderItemCount());
                cnSION.setDistributeType(wmsStockInOrderNotify.getBatchSendCtrlParam().getDistributeType());
            }
            cnSION.setStatus(0);
            cnWmsStockInOrderNotifyDao.save(cnSION);
            // 发件人信息
            CnSenderInfo cnSenderInfo = new CnSenderInfo();
            if (wmsStockInOrderNotify.getSenderInfo() == null) {
                wtr.setSuccess(false);
                wtr.setErrorCode("S20");
                wtr.setErrorMsg("业务报文校验参数不通过，发件人信息为空");
                return wtr;
            }
            BeanUtils.copyProperties(wmsStockInOrderNotify.getSenderInfo(), cnSenderInfo);
            cnSenderInfo.setId(cnSION.getId());
            cnSenderInfoDao.save(cnSenderInfo);
            // 入库单商品信息列表
            List<WmsStockInOrderItem> orderItemList = wmsStockInOrderNotify.getOrderItemList();
            if (orderItemList != null && !orderItemList.isEmpty()) {
                for (WmsStockInOrderItem sioi : orderItemList) {
                    CnWmsStockInOrderLine csiol = new CnWmsStockInOrderLine();
                    BeanUtils.copyProperties(sioi, csiol);
                    csiol.setStockInOrderNotify(cnSION);
                    cnWmsStockInOrderLineDao.save(csiol);
                }
            }
            // 装箱列表
            List<WmsStockInCaseInfo> caseInfoList = wmsStockInOrderNotify.getCaseInfoList();
            if (caseInfoList != null && !caseInfoList.isEmpty()) {
                for (WmsStockInCaseInfo sici : caseInfoList) {
                    CnWmsStockInCaseInfo csici = new CnWmsStockInCaseInfo();
                    BeanUtils.copyProperties(sici, csici, new String[] {"caseItemList"});
                    csici.setStockInOrderNotify(cnSION);
                    cnWmsStockInCaseInfoDao.save(csici);
                    // 装箱明细信息
                    List<WmsStockInCaseItem> caseItemList = sici.getCaseItemList();
                    if (caseItemList != null && !caseItemList.isEmpty()) {
                        for (WmsStockInCaseItem sicItem : caseItemList) {
                            CnWmsStockInCaseItem csicItem = new CnWmsStockInCaseItem();
                            BeanUtils.copyProperties(sicItem, csicItem);
                            csicItem.setStockInCaseInfo(csici);
                            cnWmsStockInCaseItemDao.save(csicItem);
                        }
                    }
                }
            }
            // 退货入库同步处理(未使用，有独立直连接口)
            if (wmsStockInOrderNotify.getOrderType() == 501) {
                WmsRtnOrder rntOrder = new WmsRtnOrder();
                rntOrder.setRefWarehouseCode(wmsStockInOrderNotify.getStoreCode());
                rntOrder.setOrderCode(wmsStockInOrderNotify.getOrderCode());
                // 退货入口单据
                WmsRtnInOrder rtnInOrder = new WmsRtnInOrder();
                rtnInOrder.setOwner(wmsStockInOrderNotify.getOwnerUserId());
                rtnInOrder.setSourceOrderCode(wmsStockInOrderNotify.getPrevOrderCode());
                rtnInOrder.setOrderType(wmsStockInOrderNotify.getOrderType());
                rtnInOrder.setLpcode(wmsStockInOrderNotify.getTmsServiceCode());
                rtnInOrder.setTrackingNo(wmsStockInOrderNotify.getTmsOrderCode());
                SenderInfo si = wmsStockInOrderNotify.getSenderInfo();
                if (si != null) {
                    // 收件人信息
                    rtnInOrder.setCountry(si.getSenderCountry());
                    rtnInOrder.setReceiver(si.getSenderName());
                    rtnInOrder.setProvince(si.getSenderProvince());
                    rtnInOrder.setCity(si.getSenderCity());
                    rtnInOrder.setDistrict(si.getSenderArea());
                    rtnInOrder.setZipcode(si.getSenderZipCode());
                    rtnInOrder.setAddress(si.getSenderAddress());
                    rtnInOrder.setTelephone(si.getSenderPhone());
                    rtnInOrder.setMoblie(si.getSenderMobile());
                }
                rtnInOrder.setMemo(wmsStockInOrderNotify.getReturnReason());
                rtnInOrder.setOrderSourcePlatform(wmsStockInOrderNotify.getOrderSource() + "");
                rntOrder.setRtnInOrder(rtnInOrder);
                // 入库单商品信息
                List<WmsRtnOrderLine> rolineList = new ArrayList<WmsRtnOrderLine>();
                if (orderItemList != null && !orderItemList.isEmpty()) {
                    for (WmsStockInOrderItem sioi : orderItemList) {
                        WmsRtnOrderLine roline = new WmsRtnOrderLine();
                        if (sioi != null) {
                            roline.setSkuName(sioi.getItemName());
                            roline.setSku(sioi.getItemCode());
                            roline.setQty(sioi.getItemQuantity().longValue());
                        }
                        rolineList.add(roline);
                    }
                }
                rtnInOrder.setRtnLines(rolineList);
                // hubWmsService.wmsRtnOrderService(Constants.CAINIAO_DB_SYSTEM_KEY, rntOrder);
                // 修改退货入库单状态
                // cnSION.setStatus(5);
                // cnWmsStockInOrderNotifyDao.save(cnSION);
            } else {
                // tip:非退货入库定时任务异步处理
            }
            wtr.setSuccess(true);
        } catch (Exception e) {
            log.error("入库通知单接口异常！" + "orderCode:" + wmsStockInOrderNotify.getOrderCode(), e);
            wtr.setSuccess(false);
            wtr.setErrorCode("S07");
            wtr.setErrorMsg("系统异常，请重试");
            return wtr;
        }
        return wtr;
    }

    /**
     * 3.3.12 入库订单确认
     * 
     * @param source 菜鸟的仓库编码
     * @param staId 作业单id
     */
    @Override
    public Boolean stockInOrderConfirmResponse(CnWmsStockInOrderConfirm stockInConfirm) {
        try {
            StockTransApplication sta = null;
            if (stockInConfirm.getStaId() != null) {
                sta = staDao.getByPrimaryKey(stockInConfirm.getStaId());
                if (sta == null) {
                    throw new BusinessException("没有找到菜鸟入库确认单相关作业单信息!staId:" + stockInConfirm.getStaId());
                }
            }
            // 确保该入库单的其他中间状态已经回传
            wmsOrderStatusUpload(sta.getRefSlipCode());
            List<CnStockInOrderLine> lines = cnStockInOrderLineDao.getByStockInOrderConfirmId(stockInConfirm.getId());
            // 封装请求参数
            HubStockInOrderConfirmRequest req = new HubStockInOrderConfirmRequest();
            req.setOrderCode(stockInConfirm.getOrderCode());
            req.setOrderType(stockInConfirm.getOrderType());
            req.setConfirmType(stockInConfirm.getConfirmType());
            req.setOrderConfirmTime(stockInConfirm.getOrderConfirmTime());
            req.setOutBizCode(stockInConfirm.getOutBizCode());
            if (lines != null && !lines.isEmpty()) {
                for (CnStockInOrderLine line : lines) {
                    // 订单商品信息
                    List<HubStockInOrderItem> orderItems = req.getOrderItems();
                    HubStockInOrderItem orderItem = new HubStockInOrderItem();
                    BeanUtils.copyProperties(line, orderItem, new String[] {"stockInOrderConfirm"});
                    orderItems.add(orderItem);
                    // 商品库存
                    List<HubStockInItemInventory> items = orderItem.getItems();
                    List<CnStockInItemInventory> invs = cnStockInItemInventoryDao.getByStockInItemLineId(line.getId());
                    if (invs != null && !invs.isEmpty()) {
                        for (CnStockInItemInventory inv : invs) {
                            HubStockInItemInventory hubInv = new HubStockInItemInventory();
                            BeanUtils.copyProperties(inv, hubInv, new String[] {"stockInOrderLine"});
                            items.add(hubInv);
                        }
                    }
                }
            }
            // 订单商品校验信息
            List<HubStockInCheckItem> checkItems = req.getCheckItems();
            List<HubStockInCheckItem> cnCheckItems = hubStockInCheckItemDao.getCheckItemByStaId(sta.getId(), new BeanPropertyRowMapper<HubStockInCheckItem>(HubStockInCheckItem.class));
            if (cnCheckItems != null && !cnCheckItems.isEmpty()) {
                for (HubStockInCheckItem i : cnCheckItems) {
                    checkItems.add(i);
                }
            }
            // 菜鸟下发的仓储编码
            String source = null;
            CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(sta.getRefSlipCode());
            if (cnOrderProperty != null) {
                source = cnOrderProperty.getStoreCode();
            }
            if (source == null) {
                log.error("菜鸟入库确认接口未找到必要参数Source!sta{}", new Object[] {sta.getId()});
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            // 调用hub接口
            HubStockInOrderConfirmResponse rep = hubCainiaoWmsManager.stockInOrderConfirm(source, req);
            if (rep.getResults() != null && rep.RESULT_RIGHT == rep.getResults()) {
                stockInConfirm.setStatus("1");
                cnWmsStockInOrderConfirmDao.save(stockInConfirm);
                return true;
            } else {
                log.error("菜鸟DB-WMS对接,入库订单确认接口反馈：" + "ErrorCode:" + rep.getErrCode() + ",ErrorMsg:" + rep.getErrMsg());
                Integer count = stockInConfirm.getErrorCount();
                if (count == null) {
                    count = 0;
                }
                count++;
                stockInConfirm.setErrorCount(count);
                if (count >= 3) {
                    stockInConfirm.setStatus("-2");
                }
                stockInConfirm.setErrorMsg("ErrorCode:" + rep.getErrCode() + ",ErrorMsg:" + rep.getErrMsg());
                cnWmsStockInOrderConfirmDao.save(stockInConfirm);
                return false;
            }
        } catch (Exception e) {
            log.error("菜鸟DB-WMS对接,入库订单确认接口异常！", e);
            if (stockInConfirm != null) {
                stockInConfirm.setStatus("-1");
                stockInConfirm.setErrorMsg("WMS系统异常！");
                cnWmsStockInOrderConfirmDao.save(stockInConfirm);
            }
        }
        return false;
    }

    /**
     * 3.3.11 销售订单发货确认接口
     * 
     * @returnk
     */
    public String wmsConsignOrderConfirm(Long staId) {
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            List<StaLine> slList = staLineDao.findByStaId(staId);
            List<PackageInfo> piList = packageInfoDao.getByStaId(staId);
            CnOrderProperty c = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(sta.getRefSlipCode());

            // 封装请求参数
            HubConsignOrderConfirmRequest req = new HubConsignOrderConfirmRequest();
            req.setOrderCode(sta.getRefSlipCode());
            req.setOrderType(sta.getType().getValue() == 21 ? 201 : 502);
            req.setConfirmType(0);
            req.setOrderConfirmTime(sta.getOutboundTime());
            req.setOutBizCode(sta.getCode());
            // req.setOrderCode("LBX0000079756155");
            // req.setOrderType(201);
            // req.setConfirmType(0);
            // req.setOrderConfirmTime(new Date());
            List<HubConsignTmsItem> ti = new ArrayList<HubConsignTmsOrder.HubConsignTmsItem>();
            List<HubConsignOrderItem> hcoiList = req.getOrderItems();
            for (StaLine sl : slList) {
                HubConsignTmsItem t = new HubConsignTmsItem();
                HubConsignOrderItem hcoi = new HubConsignOrderItem();
                List<HubConsignItemInventory> items = hcoi.getItems();
                if (!StringUtil.isEmpty(sl.getOrderLineNo())) {
                    hcoi.setOrderItemId(sl.getOrderLineNo());
                } else {
                    hcoi.setOrderItemId(sl.getLineNo());
                }
                hcoi.setOwnUserId(sta.getOwner());
                hcoi.setIsCompleted(true);
                HubConsignItemInventory hcii = new HubConsignItemInventory();
                hcii.setInventoryType(1);
                hcii.setQuantity(sl.getQuantity().intValue());
                items.add(hcii);
                hcoi.setItems(items);
                hcoiList.add(hcoi);

                Sku s = skuDao.getByPrimaryKey(sl.getSku().getId());
                t.setItemId(s.getExtensionCode1());
                t.setItemQuantity(sl.getQuantity().intValue());
                ti.add(t);
            }
            // req.setOutBizCode("S200000796990");
            if (piList != null && piList.size() > 0) {
                List<HubConsignTmsOrder> hctoList = req.getTmsOrders();
                for (PackageInfo pi : piList) {
                    HubConsignTmsOrder hcto = new HubConsignTmsOrder();
                    hcto.setPackageCode(pi.getId().toString());
                    hcto.setTmsCode(pi.getLpCode());
                    hcto.setTmsOrderCode(pi.getTrackingNo());
                    hcto.setPackageWeight(Integer.valueOf(pi.getWeight().toString()) * 1000);// 重量单位转换
                    hcto.setPackageHeight(1);
                    hcto.setPackageLength(1);
                    hcto.setPackageWidth(1);
                    List<HubConsignTmsItem> hctiList = hcto.getTmsItems();
                    hctiList.addAll(ti);
                    hctoList.add(hcto);
                }
            }

            log.error(JSON.json(req));
            // 调用hub接口
            HubConsignOrderConfirmResponse rep = hubCainiaoWmsManager.consignOrderConfirm(c.getStoreCode(), req);
            if (rep.getResults() != null && rep.RESULT_RIGHT == rep.getResults()) {
                // stockInConfirm.setStatus("1");
                // cnWmsStockInOrderConfirmDao.save(stockInConfirm);
                return null;
            } else {
                log.error("菜鸟DB-WMS对接,销售订单发货确认接口反馈：" + "ErrorCode:" + rep.getErrCode() + "ErrorMsg:" + rep.getErrMsg());
                // stockInConfirm.setStatus("2");
                // cnWmsStockInOrderConfirmDao.save(stockInConfirm);
                return "ErrorCode:" + rep.getErrCode() + "ErrorMsg:" + rep.getErrMsg();
            }
        } catch (Exception e) {
            log.error("菜鸟DB-WMS对接,销售订单发货确认接口异常！", e);
            return e.getMessage();
        }
    }

    /**
     * 3.3.7 出库单通知接口
     * 
     * @return
     */
    public WmsThreeplRespose wmsStockOutOrderNotify(String systemKey, WmsStockOutOrderNotify wmsStockOutOrderNotify) {
        WmsThreeplRespose wtr = new WmsThreeplRespose();
        try {
            if (wmsStockOutOrderNotify == null) {
                wtr.setSuccess(false);
                wtr.setErrorCode("S12");
                wtr.setErrorMsg("参数为空！");
                return wtr;
            }
            CnOutOrderNotify coon = cnOutOrderNotifyDao.getCnOutOrderNotifyByOrderCode(wmsStockOutOrderNotify.getOrderCode());
            if (coon != null) {
                wtr.setSuccess(true);
                return wtr;
            }
            // 出库单据头信息
            CnOutOrderNotify n = new CnOutOrderNotify();
            String[] s = {"extendFields"};
            BeanUtils.copyProperties(wmsStockOutOrderNotify, n, s);
            Map<Object, Object> m = wmsStockOutOrderNotify.getExtendFields();
            String ef = "";
            if (m != null && m.size() > 0) {
                ef = m.toString();
            }
            n.setExtendFields(ef);
            if (wmsStockOutOrderNotify.getBatchSendCtrlParam() != null) {
                n.setTotalOrderItemCount(wmsStockOutOrderNotify.getBatchSendCtrlParam().getTotalOrderItemCount());
                n.setDistributeType(wmsStockOutOrderNotify.getBatchSendCtrlParam().getDistributeType());

            }
            n.setStatus(0);
            cnOutOrderNotifyDao.save(n);
            // 收货人信息
            CnOutReceiverInfo ri = null;
            if (wmsStockOutOrderNotify.getReceiverInfo() != null) {
                ri = new CnOutReceiverInfo();
                BeanUtils.copyProperties(wmsStockOutOrderNotify.getReceiverInfo(), ri);
                ri.setCnOutOrderNotify(n);
                cnOutReceiverInfoDao.save(ri);
            }
            // 发件人信息
            CnOutSenderInfo si = null;
            if (wmsStockOutOrderNotify.getSenderInfo() != null) {
                si = new CnOutSenderInfo();
                BeanUtils.copyProperties(wmsStockOutOrderNotify.getSenderInfo(), si);
                si.setCnOutOrderNotify(n);
                cnOutSenderInfoDao.save(si);
            }
            // 出库单商品列表
            List<WmsStockOutOrderItem> oil = wmsStockOutOrderNotify.getOrderItemList();
            if (oil != null && oil.size() > 0) {
                for (WmsStockOutOrderItem ooi : oil) {
                    CnOutOrderItem cooi = new CnOutOrderItem();
                    BeanUtils.copyProperties(ooi, cooi, s);
                    Map<Object, Object> ooiMap = ooi.getExtendFields();
                    if (ooiMap != null && ooiMap.size() > 0) {
                        cooi.setExtendFields(ooiMap.toString());
                    }
                    cooi.setCnOutOrderNotify(n);
                    cnOutOrderItemDao.save(cooi);
                }
            }

            CnOrderProperty c = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(wmsStockOutOrderNotify.getOrderCode());
            if (c == null) {
                CnOrderProperty cop = new CnOrderProperty();
                cop.setOrderCode(wmsStockOutOrderNotify.getOrderCode());
                cop.setSystemKey(systemKey);
                cop.setOrderType(wmsStockOutOrderNotify.getOrderType() + "");
                cop.setStoreCode(wmsStockOutOrderNotify.getStoreCode());
                cnOrderPropertyDao.save(cop);
            }
            wtr.setSuccess(true);
        } catch (Exception e) {
            log.error("itemId :", e);
            wtr.setSuccess(false);
            wtr.setErrorCode("S13");
            wtr.setErrorMsg("WMS 系统异常！");
            return wtr;
        }
        return wtr;
    }

    /**
     * 3.3.14 出库订单确认
     * 
     * @param source 来源 :DB
     * @param orderCode 菜鸟仓储中心入库订单编码
     */
    @Override
    public String stockOutOrderConfirm(Long staId) {
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            List<PackageInfo> piList = packageInfoDao.getByStaId(staId);
            CnOutOrderNotify oon = cnOutOrderNotifyDao.getCnOutOrderNotifyByOrderCode(sta.getRefSlipCode());
            List<StaLine> slList = staLineDao.findByStaId(staId);
            CnOrderProperty c = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(sta.getRefSlipCode());
            List<CnOutOrderItem> oi = cnOutOrderItemDao.getCnOutOrderItemByNotifyId(oon.getId());

            // 封装请求参数
            HubStockOutOrderConfirmRequest req = new HubStockOutOrderConfirmRequest();
            req.setOrderCode(sta.getRefSlipCode());
            req.setOrderType(oon.getOrderType());
            req.setConfirmType(0);
            req.setOrderConfirmTime(sta.getOutboundTime());
            req.setOutBizCode(sta.getCode());
            // req.setOrderCode("LBX000000961139886");
            // req.setOrderType(901);
            // req.setConfirmType(0);
            // req.setOrderConfirmTime(new Date());
            // req.setOutBizCode("S200000797161");
            // if (piList != null && piList.size() > 0) {
            // List<HubPackageInfo> hctoList = req.getPackageInfos();
            // for (PackageInfo pi : piList) {
            HubConsignTmsOrder hcto = new HubConsignTmsOrder();
            // hcto.setPackageCode(pi.getLpCode());
            // hcto.setTmsCode(pi.getTrackingNo());
            // hcto.setPackageWeight(Integer.valueOf(pi.getWeight().toString()) * 1000);// 重量单位转换
            // }
            // }
            List<HubStockOutOrderItem> hsooiList = req.getOrderItems();
            for (CnOutOrderItem sl : oi) {
                HubStockOutOrderItem hsooi = new HubStockOutOrderItem();
                hsooi.setOrderItemId(sl.getOrderItemId());
                hsooi.setItemId(sl.getItemId());
                hsooi.setItemCode(sl.getItemCode());
                hsooi.setOwnUserId(oon.getOwnerUserId());
                hsooi.setIsCompleted(true);
                List<HubStockOutItemInventory> oiiList = hsooi.getItems();
                HubStockOutItemInventory oii = new HubStockOutItemInventory();
                oii.setInventoryType(sl.getInventoryType());
                oii.setQuantity(sl.getItemQuantity());
                oiiList.add(oii);
                hsooiList.add(hsooi);
            }

            log.error(JSON.json(req));
            // 调用hub接口
            HubStockOutOrderConfirmResponse rep = hubCainiaoWmsManager.stockOutOrderConfirm(c.getStoreCode(), req);
            if (rep.getResults() != null && rep.RESULT_RIGHT == rep.getResults()) {
                // stockInConfirm.setStatus("1");
                // cnWmsStockInOrderConfirmDao.save(stockInConfirm);
                return null;
            } else {
                log.error("菜鸟DB-WMS对接,出库订单确认接口反馈：" + "ErrorCode:" + rep.getErrCode() + "ErrorMsg:" + rep.getErrMsg());
                // stockInConfirm.setStatus("2");
                // cnWmsStockInOrderConfirmDao.save(stockInConfirm);
                return "ErrorCode:" + rep.getErrCode() + "ErrorMsg:" + rep.getErrMsg();
            }
        } catch (Exception e) {
            log.error("菜鸟DB-WMS对接,出库订单确认接口异常！", e);
            return e.getMessage();
        }
    }

    /**
     * 3.3.9 单据状态回传接口
     * 
     * @param orderCode 菜鸟仓储中心入库订单编码(为空默认查所有未上传)
     */
    @Override
    public void wmsOrderStatusUpload(String orderCode) {
        List<CnWmsOrderStatusUpload> orderStatusList = null;
        if (orderCode == null) {
            orderStatusList = cnWmsOrderStatusUploadDao.getByUploadStatus("0");
        } else {
            // 查询指定订单号的状态
            orderStatusList = cnWmsOrderStatusUploadDao.getByOrderCodeAndUploadStatus(orderCode, "0");
        }
        if (orderStatusList == null || orderStatusList.isEmpty()) {
            return;
        }
        for (CnWmsOrderStatusUpload orderStatus : orderStatusList) {
            try {
                // 菜鸟下发的仓储编码
                String source = null;
                CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(orderStatus.getOrderCode());
                if (cnOrderProperty != null) {
                    source = cnOrderProperty.getStoreCode();
                }
                if (source == null) {
                    log.error("菜鸟状态回传接口未找到必要参数Source!sta{}", new Object[] {orderStatus.getStaId()});
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                // 封装请求参数
                HubOrderStatusUploadRequest req = new HubOrderStatusUploadRequest();
                BeanUtils.copyProperties(orderStatus, req, new String[] {"staId", "uploadStatus", "errorCount", "errorMsg"});
                // 调用hub接口
                HubOrderStatusUploadResponse rep = hubCainiaoWmsManager.orderStatusUpload(source, req);
                if (rep.getResults() != null && rep.RESULT_RIGHT == rep.getResults()) {
                    orderStatus.setUploadStatus("1");
                    cnWmsOrderStatusUploadDao.save(orderStatus);
                } else {
                    log.info("菜鸟DB-WMS对接,单据状态回传接口反馈：" + "orderCode:" + orderStatus.getOrderCode() + "    ErrorCode:" + rep.getErrCode() + ",ErrorMsg:" + rep.getErrMsg());
                    Integer count = orderStatus.getErrorCount();
                    if (count == null) {
                        count = 0;
                    }
                    count++;
                    orderStatus.setErrorCount(count);
                    if (count >= 3) {
                        orderStatus.setUploadStatus("-2");
                    }
                    orderStatus.setErrorMsg("ErrorCode:" + rep.getErrCode() + ",ErrorMsg:" + rep.getErrMsg());
                    cnWmsOrderStatusUploadDao.save(orderStatus);
                }
            } catch (Exception e) {
                log.error("单据状态回传接口异常", e);
                orderStatus.setUploadStatus("-1");
                orderStatus.setErrorMsg("WMS系统异常！");
                cnWmsOrderStatusUploadDao.save(orderStatus);
            }
        }
    }

    /**
     * 创建菜鸟回传信息by sta
     * 
     * @param staId
     */
    public void createCnWmsOrderStatusUpload(Long staId) {
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta == null) {
                return;
            }
            if (!Constants.CAINIAO_DB_SYSTEM_KEY.equals(sta.getSystemKey())) {
                return;
            }
            String rtnType = null;
            String content = null;
            switch (sta.getStatus()) {
                case CREATED:
                    // 新建
                    rtnType = "WMS_ACCEPT";
                    content = "宝尊已创单";
                    break;
                case OCCUPIED:
                    // 库存占用
                    rtnType = "WMS_PICK";
                    content = "拣货中";
                    break;
                case FAILED:
                    // 配货失败
                    rtnType = "WMS_LACK";
                    content = "库存不足";
                    break;
                case CANCELED:
                    // 取消完成
                    break;
                case PARTLY_RETURNED:
                    // 部分转入
                    if (sta.getType().getValue() == 81 && StringUtils.equals(sta.getSystemKey(), Constants.CAINIAO_DB_SYSTEM_KEY)) {// VMI移库入库
                        rtnType = "WMS_ARRIVE";
                        content = "货到仓库";
                    }
                    break;
                case CANCEL_UNDO:
                    // 取消未处理
                    break;
                case CHECKED:
                    // 核对
                    rtnType = "WMS_CHECK";
                    content = "核对";
                    break;
                case INTRANSIT:
                    // 已转出
                    rtnType = "WMS_PACKAGE";
                    content = "打包出库待揽件";
                    break;
                case FINISHED:
                    // 完成
                    if (sta.getType().getValue() == 81 && StringUtils.equals(sta.getSystemKey(), Constants.CAINIAO_DB_SYSTEM_KEY)) {// VMI移库入库
                        rtnType = "WMS_ARRIVE";
                        content = "货到仓库";
                    }
                    break;
                default:
                    break;
            }
            if (rtnType == null) return;
            String staType = null;
            CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(sta.getRefSlipCode());
            if (cnOrderProperty != null) {
                staType = cnOrderProperty.getOrderType();
            }
            CnWmsOrderStatusUpload su = new CnWmsOrderStatusUpload();
            su.setOrderType(staType);
            su.setOrderCode(sta.getRefSlipCode());
            su.setContent(content);
            su.setOperator("宝尊仓库");
            su.setOperateDate(new Date());
            su.setStatus(rtnType);
            su.setUploadStatus("0");
            su.setStaId(staId);
            cnWmsOrderStatusUploadDao.save(su);
        } catch (Exception e) {
            log.error("创建菜鸟状态回传数据异常！staId=" + staId, e);
        }
    }

    /**
     * 创建菜鸟缺货回传
     * 
     * @param orderCode
     */
    public void createCnInvQtyDeficiency(String orderCode) {
        CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(orderCode);
        if (cnOrderProperty == null) {
            return;
        }
        if (!Constants.CAINIAO_DB_SYSTEM_KEY.equals(cnOrderProperty.getSystemKey())) {
            return;
        }
        CnWmsOrderStatusUpload su = new CnWmsOrderStatusUpload();
        su.setOrderType(cnOrderProperty.getOrderType());
        su.setOrderCode(orderCode);
        su.setContent("库存不足");
        su.setOperator("宝尊仓库");
        su.setOperateDate(new Date());
        su.setStatus("WMS_LACK");
        su.setUploadStatus("0");
        cnWmsOrderStatusUploadDao.save(su);
    }

    /**
     * 创建菜鸟回传信息by stv
     * 
     * @param stvId
     */
    public void createCnWmsOrderStatusUploadByStv(Long stvId) {
        try {
            StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
            StockTransApplication sta = stv.getSta();
            if (sta == null || !StringUtils.equals(sta.getSystemKey(), Constants.CAINIAO_DB_SYSTEM_KEY) || (sta.getType().getValue() != 41 && sta.getType().getValue() != 81)) {
                return;
            }
            String rtnType = null;
            String content = null;
            // 入库
            if (stv.getDirection().getValue() == 1) {
                switch (stv.getStatus()) {
                    case CANCELED:
                        // 取消
                        break;
                    case CREATED:
                        // 已创建
                        if (sta.getType().getValue() == 41) {
                            rtnType = "WMS_ARRIVE";
                            content = "货到仓库";
                        } else {
                            rtnType = "WMS_ARRIVALREGISTER";
                            content = "到货登记";
                        }
                        break;
                    case CONFIRMED:
                        // (收货)已确认
                        rtnType = "WMS_ARRIVE";
                        content = "货到仓库";
                        break;
                    case CHECK:
                        // (审核)已核对
                        rtnType = "WMS_RECEIVED";
                        content = "收货审核完成";
                        break;
                    case FINISHED:
                        // 已完成
                        rtnType = "WMS_ONSALE";
                        content = "上架完成";
                        break;
                    default:
                        break;
                }
            }
            if (rtnType == null) return;
            String staType = null;
            CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(sta.getRefSlipCode());
            if (cnOrderProperty != null) {
                staType = cnOrderProperty.getOrderType();
            }
            CnWmsOrderStatusUpload su = new CnWmsOrderStatusUpload();
            su.setOrderType(staType);
            su.setOrderCode(sta.getRefSlipCode());
            su.setContent(content);
            su.setOperator("宝尊仓库");
            su.setOperateDate(new Date());
            su.setStatus(rtnType);
            su.setUploadStatus("0");
            su.setStaId(sta.getId());
            cnWmsOrderStatusUploadDao.save(su);
        } catch (Exception e) {
            log.error("创建菜鸟状态回传数据异常！stvId=" + stvId, e);
        }
    }

    /**
     * 3.3.10 盘点
     */
    @Override
    public void wmsInventoryCount(String storeCode) {
        List<CnWmsInventoryCount> invCounts = null;
        if (storeCode != null) {
            invCounts = cnWmsInventoryCountDao.getByStoreCode(storeCode, "0");
        } else {
            invCounts = cnWmsInventoryCountDao.getByStatus("0");
        }
        if (invCounts == null || invCounts.isEmpty()) return;
        for (CnWmsInventoryCount invCount : invCounts) {
            try {
                // 封装请求参数
                HubInventoryCountRequest req = new HubInventoryCountRequest();
                BeanUtils.copyProperties(invCount, req, new String[] {"ckId", "status", "errorCount", "errorMsg", "rtOrderCode"});
                // 盘点单商品信息
                List<CnInvCountReturnOrderItem> invRtOrlists = cnInvCountReturnOrderItemDao.getByInvCountId(invCount.getId());
                List<HubInventoryCountReturnOrderItem> itemList = req.getItemList();
                if (invRtOrlists != null && !invRtOrlists.isEmpty()) {
                    for (CnInvCountReturnOrderItem invRtOrItem : invRtOrlists) {
                        HubInventoryCountReturnOrderItem item = new HubInventoryCountReturnOrderItem();
                        BeanUtils.copyProperties(invRtOrItem, item, new String[] {"cnInventoryCount"});
                        itemList.add(item);
                    }
                }
                // 调用hub接口
                HubInventoryCountResponse rep = hubCainiaoWmsManager.inventoryCount(req.getStoreCode(), req);
                if (rep.getResults() != null && rep.RESULT_RIGHT == rep.getResults()) {
                    invCount.setStatus("1");
                    invCount.setRtOrderCode(rep.getOrderCode());
                    cnWmsInventoryCountDao.save(invCount);
                } else {
                    log.error("菜鸟DB-WMS对接,单据状态回传接口反馈：" + "ErrorCode:" + rep.getErrCode() + "ErrorMsg:" + rep.getErrMsg());
                    Integer count = invCount.getErrorCount();
                    if (count == null) {
                        count = 0;
                    }
                    count++;
                    invCount.setErrorCount(count);
                    if (count >= 3) {
                        invCount.setStatus("-2");
                    }
                    invCount.setErrorMsg("ErrorCode:" + rep.getErrCode() + ",ErrorMsg:" + rep.getErrMsg());
                    cnWmsInventoryCountDao.save(invCount);
                }
            } catch (Exception e) {
                log.error("菜鸟DB-WMS对接,盘点接口异常！", e);
                invCount.setStatus("-1");
                invCount.setErrorMsg("WMS系统异常！");
                cnWmsInventoryCountDao.save(invCount);
            }
        }
    }
}
