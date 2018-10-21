package com.jumbo.wms.manager.task.hub2wms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.support.excel.ReadStatus;
import loxia.support.excel.impl.DefaultReadStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.OperationUnitTypeDao;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.SkuModifyLogDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.CnInvCountReturnOrderItemDao;
import com.jumbo.dao.hub2wms.CnMsgConfirmReplyDao;
import com.jumbo.dao.hub2wms.CnOrderPropertyDao;
import com.jumbo.dao.hub2wms.CnOutOrderItemDao;
import com.jumbo.dao.hub2wms.CnOutOrderNotifyDao;
import com.jumbo.dao.hub2wms.CnOutReceiverInfoDao;
import com.jumbo.dao.hub2wms.CnSkuInfoDao;
import com.jumbo.dao.hub2wms.CnSnSampleDao;
import com.jumbo.dao.hub2wms.CnSnSampleRuleDao;
import com.jumbo.dao.hub2wms.CnWmsInventoryCountDao;
import com.jumbo.dao.hub2wms.CnWmsStockInOrderConfirmDao;
import com.jumbo.dao.hub2wms.CnWmsStockInOrderLineDao;
import com.jumbo.dao.hub2wms.CnWmsStockInOrderNotifyDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.rmi.warehouse.RmiSku;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.CnInterfaceTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.WmsThreePLManager;
import com.jumbo.wms.manager.sku.SkuManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.DataStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.hub2wms.threepl.CnInvCountReturnOrderItem;
import com.jumbo.wms.model.hub2wms.threepl.CnMsgConfirmReply;
import com.jumbo.wms.model.hub2wms.threepl.CnOrderProperty;
import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderItem;
import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.CnOutReceiverInfo;
import com.jumbo.wms.model.hub2wms.threepl.CnSkuInfo;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsInventoryCount;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderConfirm;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderLine;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderNotify;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.FreightMode;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("cnInterfaceTask")
public class CnInterfaceImpl extends BaseManagerImpl implements CnInterfaceTask {

    protected static final Logger log = LoggerFactory.getLogger(CnInterfaceImpl.class);
    private static final long serialVersionUID = -58759933835062370L;
    private EventObserver eventObserver;
    @Autowired
    private CnSkuInfoDao cnSkuInfoDao;
    @Autowired
    private CnSnSampleDao cnSnSampleDao;
    @Autowired
    private CnSnSampleRuleDao cnSnSampleRuleDao;
    @Autowired
    private SkuManager skuManager;
    @Autowired
    private SkuModifyLogDao skuModifyLogDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private CnWmsStockInOrderNotifyDao cnWmsStockInOrderNotifyDao;
    @Autowired
    private CnWmsStockInOrderLineDao cnWmsStockInOrderLineDao;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private OperationUnitTypeDao operationUnitTypeDao;
    @Autowired
    private WmsThreePLManager wmsThreePLManager;
    @Autowired
    private CnOutOrderNotifyDao cnOutOrderNotifyDao;
    @Autowired
    private CnOutReceiverInfoDao cnOutReceiverInfoDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private CnOutOrderItemDao cnOutOrderItemDao;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private CnMsgConfirmReplyDao cnMsgConfirmReplyDao;
    @Autowired
    private CnWmsStockInOrderConfirmDao cnWmsStockInOrderConfirmDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private CnOrderPropertyDao cnOrderPropertyDao;
    @Autowired
    private InventoryCheckDifTotalLineDao inventoryCheckDifTotalLineDao;
    @Autowired
    private CnWmsInventoryCountDao cnWmsInventoryCountDao;
    @Autowired
    private CnInvCountReturnOrderItemDao cnInvCountReturnOrderItemDao;
    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:RmiManagerImpl");
        }
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createOrUpdateCnSku() {
        List<CnSkuInfo> csiList = cnSkuInfoDao.getCnSkuInfoListByWaiting();
        if (csiList != null && csiList.size() > 0) {
            for (CnSkuInfo csi : csiList) {
                try {

                    createSkuByCnSku(csi);
                    csi.setStatus(1);
                    cnSkuInfoDao.save(csi);
                } catch (Exception e) {
                    log.error("cainiao DB createSku:", e);
                }

            }

        }

    }

    /**
     * 菜鸟大宝创建商品
     * 
     * @param csi
     */
    public void createSkuByCnSku(CnSkuInfo csi) {
        RmiSku sku = new RmiSku();
        sku.setCustomerCode(Constants.CAINIAO_DB_CUSTOMER_CODE);
        // sku.setBarCode(csi.getBarCode());
        sku.setBrandCode(Constants.CAINIAO_DB_CUSTOMER_CODE);
        sku.setBrandName("菜鸟大宝");
        // sku.setListPrice(rmiSku.getListPrice());
        sku.setCustomerSkuCode(csi.getSkuCode());
        sku.setColor(csi.getColor());
        sku.setColorName(csi.getColor());
        // sku.setChildSnQty(rmiSku.getChildSnQty());
        // sku.setEnName(rmiSku.getEnName());
        // sku.setChildSnQty(rmiSku.getChildSnQty());
        sku.setExtensionCode1(csi.getItemId());
        sku.setExtensionCode2(csi.getItemCode());
        /*
         * if (null != sku.getExtensionCode2()) { sku.setExtCode2AndCustomer(c.getId() + "_" +
         * sku.getExtensionCode2()); }
         */
        // sku.setExtensionCode3(rmiSku.getExtensionCode3());
        // sku.setStoremode(rmiSku.getStroeMode() == null ? InboundStoreMode.TOGETHER :
        // InboundStoreMode.valueOf(rmiSku.getStroeMode()));
        if (csi.getIsShelflife() != null && csi.getIsShelflife() == true) {
            sku.setStroeMode(33);
            sku.setTimeType(3);
        } else if (csi.getIsPoMgt() != null && csi.getIsPoMgt() == true) {
            sku.setStroeMode(22);
        } else {
            sku.setStroeMode(11);
        }
        if (csi.getLifecycle() != null) {
            sku.setValidDate(csi.getLifecycle());
        }
        if (csi.getAdventLifecycle() != null) {
            sku.setWarningDate(csi.getAdventLifecycle());
        }
        /*
         * if (rmiSku.getWarningDateLv1() != null) {
         * sku.setWarningDateLv1(rmiSku.getWarningDateLv1()); } if (rmiSku.getWarningDateLv2() !=
         * null) { sku.setWarningDateLv2(rmiSku.getWarningDateLv2()); }
         */
        if (csi.getGrossWeight() != null) {
            sku.setGrossWeight(BigDecimal.valueOf(csi.getGrossWeight()));
        }
        // sku.setIsGift(rmiSku.getIsGift());
        /*
         * if (sku.getDeliveryType() == null ||
         * TransDeliveryType.ORDINARY.equals(sku.getDeliveryType())) { sku.setDeliveryType(); }
         */
        sku.setIsRailway(true);
        if (csi.getIsSnMgt() != null) {
            sku.setIsSnSku(csi.getIsSnMgt());
        }
        // sku.setCountryOfOrigin(rmiSku.getCountryOfOrigin());
        // sku.setCategory(rmiSku.getCategory());
        sku.setJmCode(csi.getItemCode());
        sku.setKeyProperties(csi.getSpecification());
        sku.setName(csi.getName());
        // if (rmiSku.getSalesMode() == null) {
        sku.setSalesMode(1);
        // } else {
        // sku.setSalesMode(SkuSalesModel.valueOf(rmiSku.getSalesMode()));
        // }
        if (sku.getSkuSize() == null) {
            sku.setSkuSize(csi.getSize());
        }

        sku.setSupplierCode(csi.getItemCode());
        if (sku.getWarrantyCardType() == null) {
            sku.setWarrantyCardType(1);
        }
        if (csi.getHeight() != null) {
            sku.setHeight(BigDecimal.valueOf(csi.getHeight()));
        }
        if (csi.getWidth() != null) {
            sku.setWidth(BigDecimal.valueOf(csi.getWidth()));
        }
        if (csi.getLength() != null) {
            sku.setLength(BigDecimal.valueOf(csi.getLength()));
        }
        sku.setSkuType(0);
        if ("NORMAL".equals(csi.getType())) {
            sku.setIsConsumable(false);// 平台商品类型
        } else {
            sku.setIsConsumable(true);
        }
        // sku.setBarCode(csi.getBarCode());
        String[] barcodes = csi.getBarCode().split(";");
        List<String> barcodeList = new ArrayList<String>();
        if (barcodes != null && barcodes.length > 0) {
            for (String addbarcode : barcodes) {
                if (sku.getBarCode() == null) {
                    sku.setBarCode(addbarcode);
                    continue;
                }
                barcodeList.add(addbarcode);
            }
            sku.setAddBarcodes(barcodeList);
        }


        skuManager.rmiUpdateSku(sku);

    }

    /**
     * 根据中间表创建VMI入库单据
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateInbountSta() {
        List<CnWmsStockInOrderNotify> inboundOrders = cnWmsStockInOrderNotifyDao.getInboundOrdersByStatus(DataStatus.CREATED.getValue());
        if (inboundOrders == null || inboundOrders.isEmpty()) return;
        for (CnWmsStockInOrderNotify csio : inboundOrders) {
            try {
                createInbountSta(csio);
            } catch (Exception e) {
                log.error("=========cainiaoStockInOrder{} create sta faild!===========", new Object[] {csio.getOrderCode()});
                csio.setStatus(DataStatus.ERROR.getValue());
                cnWmsStockInOrderNotifyDao.save(csio);
            }
        }
    }

    public void createInbountSta(CnWmsStockInOrderNotify csio) {
        // 防止重复创单
        if (csio.getStaId() != null) {
            log.error("=========this caiNiaoStockInOrder：{} has create STA!===========", new Object[] {csio.getOrderCode()});
            throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
        }
        CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(csio.getOrderCode());
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setRefSlipCode(csio.getOrderCode());
        sta.setSlipCode2(csio.getPrevOrderCode());// 原仓储作业单号
        sta.setOrderType(csio.getOrderType() + "");
        sta.setLastModifyTime(new Date());
        sta.setMemo(csio.getRemark());
        if (cnOrderProperty != null) {
            sta.setSystemKey(cnOrderProperty.getSystemKey());
        }
        staDao.save(sta);
        BiChannel shop = shopDao.getByCode(csio.getOwnerUserId());
        if (shop == null) {
            log.error("=========BiChannel {} NOT FOUNT===========", new Object[] {csio.getOwnerUserId()});
            throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
        }
        // 渠道校验
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.CAINIAO_DB_SYSTEM_KEY, csio.getStoreCode());
        OperationUnit ou = ouDao.getByCode(op.getOptionValue());
        if (ou == null) {
            log.error("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {op.getOptionValue()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        Long companyId = ou.getId();
        if (ou.getParentUnit() != null) {
            OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
            if (ou1 != null) {
                companyId = ou1.getParentUnit().getId();
            }
        }
        InventoryStatus is = null;
        List<CnWmsStockInOrderLine> lines = cnWmsStockInOrderLineDao.getByStoreInOrderId(csio.getId());
        if (lines != null && !lines.isEmpty()) {
            long skuQty = 0l;
            String innerShopCode = null;
            for (CnWmsStockInOrderLine line : lines) {
                String itemId = line.getItemId();
                // 判断sku是否存在
                Customer c = customerDao.getByCode(Constants.CAINIAO_DB_CUSTOMER_CODE);
                Sku sku = skuDao.getByExtCode1AndCustomerId(itemId, c.getId());
                if (sku == null || sku.getVersion() != line.getItemVersion()) {
                    // 调用查询接口创建或更新商品
                    log.info("===============SKU {} start create or update ================", new Object[] {itemId});
                    List<Long> itemList = new ArrayList<Long>();
                    itemList.add(Long.parseLong(line.getItemId()));
                    wmsThreePLManager.wmsSkuInfoQuery(csio.getStoreCode(), Long.parseLong(csio.getOwnerUserId()), itemList, sta.getSystemKey());
                    sku = skuDao.getByExtCode1AndCustomerId(itemId, c.getId());
                    if (sku == null) {
                        log.error("===============SKU {} query faild ================", new Object[] {itemId});
                        throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {itemId});
                    }
                };
                StaLine staLine = new StaLine();
                staLine.setQuantity(line.getItemQuantity().longValue());
                staLine.setSku(sku);
                staLine.setCompleteQuantity(0L);// 已执行数量
                staLine.setSta(sta);
                innerShopCode = shop.getCode();
                skuQty += staLine.getQuantity();
                staLine.setOwner(innerShopCode);
                staLine.setOrderLineNo(line.getOrderItemId());// 入库单明细id
                List<InventoryStatus> isList = inventoryStatusDao.findInvStatusListByCompany(companyId, null);
                if (isList != null && !isList.isEmpty()) {
                    for (InventoryStatus invs : isList) {
                        System.out.println(invs.getName().equals(InventoryStatus.INVENTORY_STATUS_GOOD));
                        if ((line.getInventoryType().equals(Constants.SELLABLE_INVENTORY) || line.getInventoryType().equals(Constants.ON_ROAD_INVENTORY)) && invs.getName().equals(InventoryStatus.INVENTORY_STATUS_GOOD)) {
                            is = invs;// 良品
                            break;
                        }
                        if ((line.getInventoryType().equals(Constants.DEFECTIVE_GOODS) || line.getInventoryType().equals(Constants.DAMAGE_TO_MACHINERY) || line.getInventoryType().equals(Constants.CASES_OF_LOSS) || line.getInventoryType().equals(
                                Constants.FREEZE_INVENTORY))
                                && invs.getName().equals(InventoryStatus.INVENTORY_STATUS_IMPERFECTION)) {
                            is = invs; // 残次
                            break;
                        }
                    }
                };
                staLine.setInvStatus(is);
                staLine = staLineDao.save(staLine);
            }
            csio.setStaId(sta.getId());
            csio.setStatus(DataStatus.FINISHED.getValue());
            cnWmsStockInOrderNotifyDao.save(csio);
            sta.setSkuQty(skuQty);
            staDao.updateSkuQtyById(sta.getId());
            if (StringUtils.hasText(innerShopCode) && ou != null && is != null) {
                sta.setOwner(innerShopCode);
                sta.setMainWarehouse(ou);
                sta.setMainStatus(is);
                staDao.save(sta);
                staDao.flush();
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            log.info("===============sta {} create success ================", new Object[] {sta.getCode()});
        }
        // 将入库单创建记录写入状态表
        try {
            if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
            } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
                whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
            }
            wmsThreePLManager.createCnWmsOrderStatusUpload(sta.getId());
            // 回传接单指令
            wmsThreePLManager.wmsOrderStatusUpload(sta.getRefSlipCode());
        } catch (Exception e) {
            log.error("=========cainiaoStockInOrder{} upload accept-status faild faild!===========", new Object[] {csio.getOrderCode()});
        }
    }

    /**
     * 单据状态回传
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadStatus() {
        wmsThreePLManager.wmsOrderStatusUpload(null);
    }

    /**
     * 入库单确认
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inOrderMsgToCaiNiao() {
        List<CnWmsStockInOrderConfirm> ciList = cnWmsStockInOrderConfirmDao.getByStatus("0");
        if (ciList == null || ciList.isEmpty()) return;
        for (CnWmsStockInOrderConfirm ci : ciList) {
            if (ci.getStaId() != null) {
                wmsThreePLManager.stockInOrderConfirmResponse(ci);
            }
        }
    }

    /**
     * 生成菜鸟VMI退仓
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createCaiNiaoVmiRtn() {
        List<CnOutOrderNotify> oonList = cnOutOrderNotifyDao.getNewCnOutOrderNotify();
        if (oonList == null || oonList.size() == 0) {
            return;
        }
        for (CnOutOrderNotify oon : oonList) {
            boolean isNoQty = false;
            try {

                createVmiRtn(oon);
            } catch (BusinessException e) {
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    if (ErrorCode.PREDEFINED_OUT_CREATE_INV_ERROR == current.getErrorCode()) {
                        isNoQty = true;
                        /*
                         * CnOutOrderNotify n = cnOutOrderNotifyDao.getByPrimaryKey(oon.getId());
                         * wmsThreePLManager.createCnInvQtyDeficiency(oon.getOrderCode());
                         * n.setStatus(-1);
                         */
                    }
                }
            } catch (Exception e) {
                log.error("createCaiNiaoVmiRtn error:", e);
            }
            if (isNoQty) {
                // cnOutOrderNotifyDao.save(oon);
            }
        }
    }

    /**
     * 生成菜鸟VMI退仓
     * 
     * @param oon
     */
    public void createVmiRtn(CnOutOrderNotify oon) {
        CnOutReceiverInfo ori = cnOutReceiverInfoDao.getCnOutReceiverInfoByNotifyId(oon.getId());
        List<CnOutOrderItem> ooiList = cnOutOrderItemDao.getCnOutOrderItemByNotifyId(oon.getId());
        CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCode(oon.getOrderCode());
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_RETURN);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        // sta.setSlipCode1(this.slipCode1);
        // sta.setActivitySource(activitySource);
        // sta.setImperfectType(this.imperfectType);
        sta.setRefSlipCode(oon.getOrderCode());
        sta.setFreightMode(FreightMode.SELF_PICKUP);
        if (cnOrderProperty != null) {
            sta.setSystemKey(cnOrderProperty.getSystemKey());
        }
        StaDeliveryInfo stadelivery = new StaDeliveryInfo();
        stadelivery.setProvince(ori.getReceiverProvince());
        stadelivery.setCity(ori.getReceiverCity());
        stadelivery.setDistrict(ori.getReceiverArea());
        stadelivery.setAddress(ori.getReceiverAddress());
        stadelivery.setReceiver(ori.getReceiverName());
        stadelivery.setTelephone(ori.getReceiverPhone());
        stadelivery.setMobile(ori.getReceiverMobile());
        // stadelivery.setLpCode();

        StockTransApplication returnSta = staDao.findReturnMaxWarehouseOrder(oon.getOrderCode(), new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
        if (null != returnSta) {
            throw new BusinessException(ErrorCode.RETURN_ORDER_STA_IS_CREATED, new Object[] {oon.getOrderCode()});
        }

        BiChannel shop = biChannelDao.getByCode(oon.getOwnerUserId());
        if (shop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.CAINIAO_DB_SYSTEM_KEY, oon.getStoreCode());
        OperationUnit ou = ouDao.getByCode(op.getOptionValue());

        Long cmpOuid = ou.getParentUnit().getParentUnit().getId();

        String type = Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE;
        if (ooiList != null && ooiList.size() > 1000) {
            throw new BusinessException(ErrorCode.NIKE_IMPORT_SIZE);
        }
        List<StaLineCommand> stalinecmds = new ArrayList<StaLineCommand>();
        if (ooiList != null && ooiList.size() > 0) {
            for (CnOutOrderItem oo : ooiList) {
                List<Long> caiNiaoSkuId = new ArrayList<Long>();
                caiNiaoSkuId.add(Long.parseLong(oo.getItemId()));
                wmsThreePLManager.wmsSkuInfoQuery(oon.getStoreCode(), Long.parseLong(oon.getOwnerUserId()), caiNiaoSkuId, sta.getSystemKey());
                List<CnSkuInfo> csiList = cnSkuInfoDao.getCnSkuInfoListByItemid(oon.getOwnerUserId(), oo.getItemId());
                StaLineCommand slc = new StaLineCommand();
                slc.setSkuCode(csiList.get(0).getSkuCode());
                if (oo.getInventoryType() == 1) {
                    slc.setIntInvstatusName("良品");
                } else {
                    slc.setIntInvstatusName("残次品");
                }
                slc.setQuantity(oo.getItemQuantity().longValue());
                stalinecmds.add(slc);
            }
        }

        // 获取菜鸟商品

        List<StaLine> stalines = new ArrayList<StaLine>();

        Map<String, InventoryStatus> invmap = new HashMap<String, InventoryStatus>();
        Map<String, WarehouseLocation> locationmap = new HashMap<String, WarehouseLocation>();



        BigDecimal transactionid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        if (transactionid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
        if (transactionType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
        }
        ReadStatus rs = new DefaultReadStatus();
        rs.setStatus(ReadStatus.STATUS_SUCCESS);
        rs = excelReadManager.vmiReturnValidate(rs, stalinecmds, stalines, invmap, locationmap, cmpOuid, ou.getId(), type, shop.getCode(), transactionType, sta, shop);
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return;
        }
        InventoryStatus inventoryStatus = null;

        // save sta
        // sta.setToLocation(toLocation);
        sta.setMainWarehouse(ou);
        sta.setCreateTime(new Date());
        sta.setOwner(shop.getCode());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setDeliveryType(TransDeliveryType.ORDINARY);
        // 订单状态与账号关联
        if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        }
        sta.setIsNeedOccupied(true);
        sta.setMemo(oon.getRemark());
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());

        sta.setIsNeedOccupied(true);
        staDao.save(sta);
        staDao.flush();
        stadelivery.setId(sta.getId());
        stadelivery.setCountry("中国");
        stadelivery.setTransTimeType(TransTimeType.ORDINARY);
        staDeliveryInfoDao.save(stadelivery);

        /*
         * if (!StringUtil.isEmpty(stadelivery.getLpCode())) { // 获取对应快递单号
         * transOlManager.matchingTransNo(sta.getId(), stadelivery.getLpCode(), ouid); }
         */
        // save stv
        int tdType = TransactionDirection.OUTBOUND.getValue();
        String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());

        stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), null, tdType, sta.getMainWarehouse().getId(), transactionid.longValue());


        // find stv
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
        // save staline
        Long skuQty = 0L;
        Map<String, StaLine> staMap = new HashMap<String, StaLine>();
        for (final StaLineCommand cmd : stalinecmds) {
            String key = cmd.getSku().getId() + "_" + cmd.getIntInvstatusName();
            inventoryStatus = invmap.get(cmd.getIntInvstatusName());
            StaLine staLine = null;
            if (staMap.get(key) == null) {
                staLine = new StaLine();
                staLine.setOwner(shop.getCode());
                staLine.setQuantity(cmd.getQuantity());
                skuQty += cmd.getQuantity();
                staLine.setCompleteQuantity(0L);
                staLine.setSta(sta);
                staLine.setInvStatus(inventoryStatus);
                staLine.setSku(cmd.getSku());
            } else {
                skuQty += cmd.getQuantity();
                staLine = staMap.get(key);
                staLine.setQuantity(staLine.getQuantity() + cmd.getQuantity());
            }
            staMap.put(key, staLine);

            // save stvlie
            StvLine stvLine = new StvLine();
            stvLine.setDirection(TransactionDirection.OUTBOUND);
            stvLine.setOwner(sta.getOwner());
            stvLine.setQuantity(cmd.getQuantity());
            stvLine.setSku(cmd.getSku());
            // stvLine.setInvStatus(invmap.get(cmd.getIntInvstatusName()));
            stvLine.setInvStatus(inventoryStatus);
            stvLine.setLocation(cmd.getWarehouseLocation());
            stvLine.setTransactionType(transactionType);
            stvLine.setWarehouse(ou);
            stvLine.setStv(stv);
            stvLine.setDistrict(cmd.getWarehouseLocation().getDistrict());
            stvLineDao.save(stvLine);
        }
        for (String s : staMap.keySet()) {
            StaLine staLine = staMap.get(s);
            staLineDao.save(staLine);
        }
        sta.setSkuQty(skuQty);
        staLineDao.flush();
        stvLineDao.flush();
        // 计算销售可用量KJL
        whManager.isInventoryNumber(sta.getId());
        // 库存占用
        whManager.occupyInventoryByStaId(sta.getId(), null, shop);
        stvLineDao.flush();
        // 新增其他出库占用明细记录中间表通知oms/pac,定时任务通知
        // whManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l,
        // WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);

        /***** 调整逻辑：前置退仓增量 ************************************/
        excelReadManager.incrementInv(sta.getId());
        /***** Edit by KJL 2015-03-17 ***********************************/
        /***** mongoDB库存变更添加逻辑 ******************************/
        sta.setStatus(StockTransApplicationStatus.CREATED);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        wmsThreePLManager.createCnWmsOrderStatusUpload(sta.getId());
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        /***** mongoDB库存变更添加逻辑 ******************************/

        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        oon.setStatus(1);
        cnOutOrderNotifyDao.save(oon);
        try { // 回传接单指令
            wmsThreePLManager.wmsOrderStatusUpload(sta.getRefSlipCode());
        } catch (Exception e) {
            log.error("=========cainiaoStockOutOrder{} upload accept-status faild!===========", new Object[] {sta.getRefSlipCode()});
        }

    }

    /**
     * 菜鸟出库通知
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void outOrderMsgToCaiNiao() {
        List<CnMsgConfirmReply> crList = cnMsgConfirmReplyDao.getCnMsgConfirmReplyByNew();
        if (crList != null && crList.size() > 0) {
            for (CnMsgConfirmReply cr : crList) {
                StockTransApplication sta = staDao.getByPrimaryKey(cr.getStaId());
                String msg = null;
                if (sta.getType() == StockTransApplicationType.VMI_RETURN) {
                    msg = wmsThreePLManager.stockOutOrderConfirm(cr.getStaId());
                } else {
                    msg = wmsThreePLManager.wmsConsignOrderConfirm(cr.getStaId());
                }
                if (msg != null) {
                    cr.setErrorMsg(msg);
                    Integer count = cr.getErrorCount();
                    if (count == null) {
                        count = 0;
                    }
                    count++;
                    if (count > 5) {
                        cr.setStatus(99);
                    } else {
                        cr.setStatus(0);
                    }
                    cr.setErrorCount(count);
                } else {
                    cr.setStatus(1);
                }
                cnMsgConfirmReplyDao.save(cr);
            }
        }
    }

    /**
     * VMI库存调整通知菜鸟（盘点数据生成）
     */
    @Override
    public void makeVMIInvAdjNoticeToCaiNiao(InventoryCheck ic) {
        try {
            if (ic == null || ic.getType().getValue() != 2 || ic.getShop() == null || ic.getOu() == null) return;
            // 判断盘点单是否属于菜鸟
            Customer c = customerDao.getByCode(Constants.CAINIAO_DB_CUSTOMER_CODE);
            BiChannel shop = null;
            if (ic.getShop() != null) {
                shop = biChannelDao.getByCodeAndCustomerId(ic.getShop().getCode(), c.getId());
                if (shop == null) return;
            }
            OperationUnit ou = ic.getOu();
            ChooseOption op = null;
            if (ou != null) {
                op = chooseOptionDao.findByCategoryCodeAndValue(Constants.CAINIAO_DB_SYSTEM_KEY, ou.getCode());
                if (op == null) return;
            }
            CnWmsInventoryCount invCount = new CnWmsInventoryCount();
            invCount.setStoreCode(op.getOptionKey());
            invCount.setOwnerUserId(shop.getCode());
            List<InventoryCheckDifferenceLine> icDifLines = inventoryCheckDifferenceLineDao.findByInventoryCheck(ic.getId());
            List<CnInvCountReturnOrderItem> listAdd = new ArrayList<CnInvCountReturnOrderItem>();// 入库
            List<CnInvCountReturnOrderItem> listMinus = new ArrayList<CnInvCountReturnOrderItem>();// 出库
            if (icDifLines != null && !icDifLines.isEmpty()) {
                for (InventoryCheckDifferenceLine line : icDifLines) {
                    CnInvCountReturnOrderItem item = new CnInvCountReturnOrderItem();
                    if (line.getSku() != null) {
                        item.setItemId(line.getSku().getExtensionCode1());
                    }
                    item.setDetailOutBizCode(ic.getSlipCode());// 差异单的明细外部业务编号
                    item.setDueDate(line.getExpireDate());
                    item.setProduceDate(line.getProductionDate());
                    InventoryStatus invStatus = line.getStatus();
                    if (invStatus != null) {
                        if (invStatus.getName().equals(InventoryStatus.INVENTORY_STATUS_GOOD)) {
                            item.setInventoryType(Constants.SELLABLE_INVENTORY);
                        } else {
                            item.setInventoryType(Constants.DEFECTIVE_GOODS);
                        }
                    }
                    if (line.getQuantity() > 0) {
                        item.setQuantity(line.getQuantity().intValue());
                        listAdd.add(item);
                    } else {
                        item.setQuantity(-line.getQuantity().intValue());
                        listMinus.add(item);
                    }
                }
            }
            invCount.setCkId(ic.getId());
            invCount.setStatus("0");
            // 盘盈(入库)
            if (listAdd != null && !listAdd.isEmpty()) {
                invCount.setOrderType(702);
                cnWmsInventoryCountDao.save(invCount);
                cnWmsInventoryCountDao.flush();
                invCount.setOutBizCode(invCount.getId() + "");
                cnWmsInventoryCountDao.save(invCount);
                for (CnInvCountReturnOrderItem itemIn : listAdd) {
                    itemIn.setCnInventoryCount(invCount);
                    cnInvCountReturnOrderItemDao.save(itemIn);
                }
            }
            // 盘亏(出库)
            if (listMinus != null && !listMinus.isEmpty()) {
                CnWmsInventoryCount invCountOut = new CnWmsInventoryCount();
                BeanUtils.copyProperties(invCount, invCountOut, new String[] {"id", "orderType", "errorCount", "errorMsg", "rtOrderCode"});
                invCountOut.setOrderType(701);
                cnWmsInventoryCountDao.save(invCountOut);
                cnWmsInventoryCountDao.flush();
                invCountOut.setOutBizCode(invCountOut.getId() + "");
                cnWmsInventoryCountDao.save(invCountOut);
                for (CnInvCountReturnOrderItem itemOut : listMinus) {
                    itemOut.setCnInventoryCount(invCountOut);
                    cnInvCountReturnOrderItemDao.save(itemOut);
                }
            }
        } catch (Exception e) {
            log.error("菜鸟VMI库存调整（盘点）数据生成失败!InventoryCheck.id=" + ic.getId(), e);
        }
    }

    /**
     * 库存状态修改通知菜鸟（盘点数据生成）
     */
    @Override
    public void createInvStatusUpdateNoticeToCaiNiao(Long staId) {
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta == null || sta.getType().getValue() != 45) return;
            CnWmsInventoryCount invCount = new CnWmsInventoryCount();
            OperationUnit ou = sta.getMainWarehouse();
            ChooseOption op = null;
            if (ou != null) {
                op = chooseOptionDao.findByCategoryCodeAndValue(Constants.CAINIAO_DB_SYSTEM_KEY, ou.getCode());
            }
            if (ou == null || op == null) {
                log.error("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {op.getOptionValue()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            Customer c = customerDao.getByCode(Constants.CAINIAO_DB_CUSTOMER_CODE);
            BiChannel shop = biChannelDao.getByCodeAndCustomerId(sta.getOwner(), c.getId());
            if (shop == null) {
                log.error("=========BiChannel {} NOT FOUNT===========", new Object[] {sta.getOwner()});
                throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
            }
            invCount.setStoreCode(op.getOptionKey());
            invCount.setOwnerUserId(shop.getCode());
            invCount.setAdjustBizKey(sta.getCode());// 调整主单号
            invCount.setRemark("库存状态调整！");
            invCount.setStatus("0");
            List<StockTransVoucher> stvs = stvDao.findStvFinishListByStaId(staId);
            if (stvs != null && !stvs.isEmpty()) {
                for (StockTransVoucher stv : stvs) {
                    // 盘盈(入库)
                    if (stv.getDirection().getValue() == 1) {
                        invCount.setOrderType(702);
                        cnWmsInventoryCountDao.save(invCount);
                        cnWmsInventoryCountDao.flush();
                        invCount.setOutBizCode(invCount.getId() + "");
                        cnWmsInventoryCountDao.save(invCount);
                        stvLinesToCnItems(stv.getId(), invCount, 1);
                    }
                    // 盘亏(出库)
                    if (stv.getDirection().getValue() == 2) {
                        CnWmsInventoryCount invCountOut = new CnWmsInventoryCount();
                        BeanUtils.copyProperties(invCount, invCountOut, new String[] {"id", "orderType", "errorCount", "errorMsg", "rtOrderCode"});
                        invCountOut.setOrderType(701);
                        cnWmsInventoryCountDao.save(invCountOut);
                        cnWmsInventoryCountDao.flush();
                        invCountOut.setOutBizCode(invCountOut.getId() + "");
                        cnWmsInventoryCountDao.save(invCountOut);
                        stvLinesToCnItems(stv.getId(), invCountOut, 2);
                    }
                }
            }
        } catch (Exception e) {
            log.error("菜鸟库存状态修改（盘点）数据生成失败!staId=" + staId, e);
        }
    }

    private void stvLinesToCnItems(Long stvId, CnWmsInventoryCount invCount, Integer direction) {

        List<StvLine> stvInLines = stvLineDao.findStvLineListByStvId(stvId);
        if (stvInLines != null && !stvInLines.isEmpty()) {
            for (StvLine line : stvInLines) {
                if (line.getDirection().getValue() != direction) continue;
                CnInvCountReturnOrderItem item = new CnInvCountReturnOrderItem();
                if (line.getSku() != null) {
                    item.setItemId(line.getSku().getExtensionCode1());
                }
                item.setDueDate(line.getExpireDate());
                item.setProduceDate(line.getProductionDate());
                if (line.getInvStatus().getName().equals(InventoryStatus.INVENTORY_STATUS_GOOD)) {
                    item.setInventoryType(Constants.SELLABLE_INVENTORY);
                } else {
                    item.setInventoryType(Constants.DEFECTIVE_GOODS);
                }
                item.setQuantity(line.getQuantity().intValue());
                item.setCnInventoryCount(invCount);
                cnInvCountReturnOrderItemDao.save(item);
            }
        }
    }

    /**
     * VMI库存调整通知菜鸟（盘点数据发送）
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadVMIInvAdjustmentToCaiNiao() {
        wmsThreePLManager.wmsInventoryCount(null);
    }
}
