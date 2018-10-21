package com.jumbo.webservice.nike.manager;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.vmi.nikeDate.NikeCheckReceiveDao;
import com.jumbo.dao.vmi.nikeDate.NikeReturnReceiveDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.NikeTurnCreateDao;
import com.jumbo.dao.warehouse.StaCreateQueueDao;
import com.jumbo.dao.warehouse.StaCreateQueueLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.util.FormatUtil;
import com.jumbo.webservice.nike.command.InventoryObj;
import com.jumbo.webservice.nike.command.NikeDeliveryInfo;
import com.jumbo.webservice.nike.command.NikeOrderLineObj;
import com.jumbo.webservice.nike.command.NikeOrderObj;
import com.jumbo.webservice.nike.command.NikeOrderResultObj;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.NikeVMIInboundCommand;
import com.jumbo.wms.model.vmi.nikeData.NikeCheckReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeCheckReceiveCommand;
import com.jumbo.wms.model.vmi.nikeData.NikeReturnReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeStockReceive;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.NikeTurnCreate;
import com.jumbo.wms.model.warehouse.StaCreateQueue;
import com.jumbo.wms.model.warehouse.StaCreateQueueCommand;
import com.jumbo.wms.model.warehouse.StaCreateQueueLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("nikeOrderManager")
public class NikeOrderManagerImpl extends BaseManagerImpl implements NikeOrderManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8468064917818771847L;

    private static final String MSG = "msg";
    private static final String SKU_QTY = "sku_qty";

    @Resource(name = "nikeCheckReceiveReader")
    private ExcelReader nikeCheckReceiveReader;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private NikeManager nikeManager;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StaCreateQueueDao staCreateQueueDao;
    @Autowired
    private StaCreateQueueLineDao staCreateQueueLineDao;
    @Autowired
    private NikeCheckReceiveDao nikeCheckReceiveDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private GiftLineDao giftLineDao;
    @Autowired
    private WareHouseManagerExe wmExe;

    private EventObserver eventObserver;

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private OperationUnitDao ouDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;
    @Autowired
    private NikeReturnReceiveDao nikeReturnReceiveDao;
    @Autowired
    private NikeTurnCreateDao createDao;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    protected final static Logger log = LoggerFactory.getLogger(NikeOrderManagerImpl.class);


    public void createNikeVMIInbound(String slipCode, List<NikeVMIInboundCommand> list, BiChannel shop, OperationUnit whOu, User user) {
        Long companyId = null;
        if (whOu.getParentUnit() != null) {
            OperationUnit ou1 = ouDao.getByPrimaryKey(whOu.getParentUnit().getId());
            if (ou1 != null) {
                companyId = ou1.getParentUnit().getId();
            }
        }
        InventoryStatus is = inventoryStatusDao.findInvStatusForSale(companyId);
        // 创sta
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setRefSlipCode(slipCode);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), user.getId(), whOu.getId());
        sta.setLastModifyTime(new Date());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setOwner(shop.getCode());
        sta.setCreator(user);
        sta.setMainWarehouse(whOu);
        sta.setMainStatus(is);
        sta.setMemo(list.get(0).getExternOrderKey());
        staDao.save(sta);
        Map<Long, StaLine> map = new HashMap<Long, StaLine>();
        for (NikeVMIInboundCommand com : list) {
            if (com.getSku() == null) {
                log.debug("===============this instruction has create STA================");
                throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
            }
            Long qty = Long.valueOf(com.getQtyShipped().trim());
            // 合并重复行
            if (map.containsKey(com.getSku().getId())) {
                StaLine staLine = map.get(com.getSku().getId());
                staLine.setQuantity(staLine.getQuantity() + qty);
                staLineDao.save(staLine);
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setQuantity(qty);
            staLine.setSku(com.getSku());
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            staLine.setOwner(shop.getCode());
            staLine.setInvStatus(is);
            staLineDao.save(staLine);
            map.put(staLine.getSku().getId(), staLine);
        }
        staDao.flush();
        staDao.updateSkuQtyById(sta.getId());

    }



    /***
     * 100为A品， - 可销售 is forsales-1 120为B品，- 可销售is forsales-1 101为C品 - 不可销售is forsales-0 KJL
     * 添加注释：100对应良品 、101残次品、120待处理品 定义在inventoryStatus里面
     */
    private Map<Long, InventoryStatus> getInventoryStatus(Long cmpId) {
        if (cmpId == null) {
            return null;
        }
        List<InventoryStatus> invStatuslist = inventoryStatusDao.findIsForSaleByCompanyId(cmpId, new BeanPropertyRowMapperExt<InventoryStatus>(InventoryStatus.class));
        Map<Long, InventoryStatus> invStatus = new HashMap<Long, InventoryStatus>();
        for (InventoryStatus i : invStatuslist) {
            if (i.getIsForSale() != null && i.getIsForSale()) {
                if (i.getName().equals(InventoryStatus.INVENTORY_STATUS_GOOD)) {
                    invStatus.put(100L, i);
                }
            } else {
                if (i.getName().equals(InventoryStatus.INVENTORY_STATUS_IMPERFECTION)) {
                    invStatus.put(101L, i);
                }
                if (i.getName().equals(InventoryStatus.INVENTORY_STATUS_RETURN_PRODUCT)) {
                    invStatus.put(120L, i);
                }
            }
        }
        return invStatus;
    }

    /**
     * create sta
     * 
     * @param warehouseOU
     * @param preSlipCode
     * @param staType
     * @param remark
     */
    public void createStaForNike(StockTransApplication sta, OperationUnit warehouseOU, String preSlipCode, StockTransApplicationType staType, String remark) {
        sta.setMainWarehouse(warehouseOU);
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setOwner(Constants.NIKE_SHOP1_ID);
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setRefSlipType(SlipType.OUT_RETURN_REQUEST);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, warehouseOU.getId());
        sta.setRefSlipCode(Constants.NIKE_ORDER_PRE + preSlipCode);
        sta.setMemo(remark);
        sta.setType(staType);
        staDao.save(sta);
    }

    /**
     * STALINE 的构建
     * 
     * @param lineObjs
     * @param invStatus
     * @param status
     * @param sta
     * @return
     */
    private Map<String, Integer> constractStaLine(NikeOrderLineObj[] lineObjs, Map<Long, InventoryStatus> invStatus, StockTransApplication sta) {
        Map<String, Integer> rs = new HashMap<String, Integer>();
        StaLine staLine = null;
        InventoryStatus inventoryStatus = null;
        Sku sku = null;
        Integer skuQty = 0;
        Integer rtnValue = null;
        BiChannel c = companyShopDao.getByCode(sta.getOwner());
        for (NikeOrderLineObj nikeLine : lineObjs) {
            inventoryStatus = invStatus.get(nikeLine.getInvType());
            if (inventoryStatus == null) {
                rtnValue = 15; // 货品类型
                throw new BusinessException(ErrorCode.NIKE_WEBSERVERCE_INVENTORY_STATUS_NOT_FOUND);
            }
            sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(nikeLine.getUpcCode(), c.getCustomer().getId(), c.getId());
            if (sku == null) {
                rtnValue = 14; // upc不存在
                throw new BusinessException(ErrorCode.NIKE_WEBSERVERCE_SKU_NOT_FOUND);
            }
            staLine = new StaLine();
            staLine.setInvStatus(inventoryStatus);
            staLine.setSku(sku);
            staLine.setQuantity(new Long(nikeLine.getQty()));
            skuQty += nikeLine.getQty();
            staLine.setCompleteQuantity(0L);
            staLine.setOwner(Constants.NIKE_SHOP1_ID);
            staLine.setSta(sta);
            staLineDao.save(staLine);
        }
        rs.put(MSG, rtnValue);
        rs.put(SKU_QTY, skuQty);
        return rs;
    }

    /***
     * 接收NIKE订单信息
     */
    public Integer saveNikeOrder(NikeOrderObj nikeOrderObj) {
        Map<String, StockTransApplicationType> staTypeMap = getStaTypes();
        String slipCode = nikeOrderObj.getCode();
        String type = nikeOrderObj.getOrderType();
        StockTransApplicationType staType = staTypeMap.get(type);
        StaCreateQueue q = staCreateQueueDao.findBySlipCodeByType(slipCode, Constants.NIKE_RESOURCE, staType);
        if (q != null) {
            return 5;
        }
        try {
            // 保存信息
            q = new StaCreateQueue();
            // 申请单类型 staType

            q.setSource(Constants.NIKE_RESOURCE);
            q.setStaType(staType);
            q.setStatus(DefaultStatus.CREATED);
            NikeDeliveryInfo dInfo = nikeOrderObj.getNikeDeliveryInfo();
            q.setSlipCode(nikeOrderObj.getCode());
            if (dInfo != null) {
                q.setLpCode(dInfo.getLpCode());
                q.setCountry(dInfo.getCountry());
                q.setProvince(dInfo.getProvince());
                q.setCity(dInfo.getCity());
                q.setDistrict(dInfo.getDistrict());
                q.setAddress(dInfo.getAddress());
                q.setReceiver(dInfo.getReceiver());
                q.setTelephone(dInfo.getTelephone());
                q.setMobile(dInfo.getMobile());
                q.setZipcode(dInfo.getZipcode());
                q.setOrderCreateTime(dInfo.getOrderCreateTime());
                q.setOrderPlanReceiptTime(dInfo.getOrderPlanReceiptTime());
                if (dInfo.getTransType() != null) {
                    q.setTransType(TransType.valueOf(dInfo.getTransType()));
                }
                if (dInfo.getTransTimeType() != null) {
                    q.setTransTimeType(TransTimeType.valueOf(dInfo.getTransTimeType()));
                }
                String tranMemo = dInfo.getTransMemo();
                tranMemo = tranMemo == null ? null : (tranMemo.length() > 20 ? (tranMemo.substring(0, 17) + "...") : tranMemo);
                q.setTransMemo(tranMemo);
            }

            q.setRemark(nikeOrderObj.getRemark());
            // 作业类型
            Map<String, TransactionType> transactionTypes = getTransactionType();
            TransactionType transactiontype = transactionTypes.get(type);
            if (Constants.TRANSACTION_TYPE_CODE_SALES_OUTBOUND.equals(transactiontype.getCode())) {
                // 销售出库 - 总金额设定 = 订单金额 + 运费
                if (dInfo != null) {
                    q.setStaTotalAmount(dInfo.getTotalAmount());
                }
            }
            if (nikeOrderObj.getInvoiceTitle() != null) {
                q.setInvoiceTitle(nikeOrderObj.getInvoiceTitle());
                q.setInvoiceContent(nikeOrderObj.getInvoiceContent());
                q.setInvoiceTotalAmount(new BigDecimal(nikeOrderObj.getTotalAmount()));
                if (dInfo != null) {
                    q.setTransferFee(dInfo.getTotalAmount().subtract(new BigDecimal(nikeOrderObj.getTotalAmount())));
                    // 高尔夫 150 内购折扣卡 30 其余 0
                    if (q.getTransferFee().intValue() == 150 || q.getTransferFee().intValue() == 30) {
                        q.setStaTotalAmount(dInfo.getTotalAmount().subtract(q.getTransferFee()));
                        if (q.getStaTotalAmount().doubleValue() < 0) {
                            q.setStaTotalAmount(new BigDecimal(0));
                        }
                    } else {
                        q.setTransferFee(new BigDecimal(0));
                    }
                }
            }
            staCreateQueueDao.save(q);
            NikeOrderLineObj[] lineObjs = nikeOrderObj.getLineObjs();
            for (NikeOrderLineObj l : lineObjs) {
                StaCreateQueueLine line = new StaCreateQueueLine();
                line.setInvType(l.getInvType());
                line.setQty(l.getQty());
                line.setStaCrateQueue(q);
                line.setUpc(l.getUpcCode());
                line.setIsGiftCards(l.getIsGiftCards());
                line.setIsPackage(l.getIsPackage());
                line.setMemo(l.getMemo());
                line.setPackageMemo(l.getPackageMemo());
                staCreateQueueLineDao.save(line);
            }
            return 5;
        } catch (Exception e) {
            log.error("", e);
            return 100;
        }
    }

    /***
     * 接收NIKE订单信息
     */
    public Integer receiveNikeOrder(NikeOrderObj nikeOrderObj, String shopName, String warehouseCode) {
        Integer status = 5;
        // 判断有效单据是否已存在
        String refSlipCode = nikeOrderObj.getCode();
        // 申请单类型 staType
        String type = nikeOrderObj.getOrderType();
        Map<String, StockTransApplicationType> staTypeMap = getStaTypes();
        StockTransApplicationType staType = staTypeMap.get(type);
        // 店铺
        String owner = Constants.NIKE_SHOP1_ID;
        BiChannel companyShop = companyShopDao.getByCode(owner);
        if (companyShop == null) {
            throw new BusinessException(ErrorCode.NIKE_SHOP_NOT_FOUND, new Object[] {owner});
        }
        // 仓库
        OperationUnit warehouseOU = findWarehouseOuByShopid(companyShop.getId());
        if (warehouseOU == null) {
            throw new BusinessException(ErrorCode.NIKE_WRAEHOUSE_NOT_FOUND);
        }
        // 查找单据是否已存在
        if (findStaByWHAndRefCode(Constants.NIKE_ORDER_PRE + refSlipCode, staType.getValue()) != null) {
            status = 5;
            return status;
        }
        // 公司
        OperationUnit companyOu = warehouseOU.getParentUnit().getParentUnit();
        // 作业类型
        Map<String, TransactionType> transactionTypes = getTransactionType();
        if (transactionTypes == null || transactionTypes.isEmpty()) {
            throw new BusinessException(ErrorCode.NIKE_WEBSERVERCE_TRANSACTIONTYPE_NOT_FOUND);// 100
        }
        TransactionType transactiontype = transactionTypes.get(type);
        // 库存状态
        Map<Long, InventoryStatus> invStatus = getInventoryStatus(companyOu.getId());
        if (invStatus == null || invStatus.isEmpty()) {
            throw new BusinessException(ErrorCode.NIKE_WEBSERVERCE_INVENTORY_STATUS_NOT_FOUND);// 15
        }
        if (status.intValue() == 5) {
            NikeDeliveryInfo dInfo = nikeOrderObj.getNikeDeliveryInfo();
            StockTransApplication sta = new StockTransApplication();
            sta.setIsSn(false);
            sta.setType(staType);
            createStaForNike(sta, warehouseOU, refSlipCode, staType, nikeOrderObj.getRemark());
            NikeOrderLineObj[] lineObjs = nikeOrderObj.getLineObjs();
            StaDeliveryInfo staDeliveryInfo = null;
            // 判读是入库还是出库 1 入库， 2 出库
            if (transactiontype.getDirection().getValue() == 1) {
                // 构建入库单信息
                Map<String, Integer> rs = constractStaLine(lineObjs, invStatus, sta);
                Integer rtnValue = rs.get(MSG);
                if (rtnValue != null) {
                    status = rtnValue;
                    throw new BusinessException(ErrorCode.NIKE_WEBSERVERCE_BUSINESS_EXCEPTION); // 100
                }
                sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            } else if (transactiontype.getDirection().getValue() == 2) {
                // 创建出库单信息
                log.debug("receiveNikeOrder Direction : ******************************  {}", transactiontype.getDirection().getValue());
                sta.setIsNeedOccupied(true);
                Map<String, Integer> rs = constractStaLine(lineObjs, invStatus, sta);
                try {
                    sta.setSkuQty(rs.get(SKU_QTY).longValue());
                } catch (Exception e) {
                    log.error("sta set sku_qty error");
                }
                Integer rtnValue = rs.get(MSG);
                if (rtnValue != null) {
                    status = rtnValue;
                    throw new BusinessException(ErrorCode.NIKE_WEBSERVERCE_BUSINESS_EXCEPTION); // 100
                }
                if (dInfo == null) {
                    // 出库单据发货信息不允许为空
                    status = 12;
                    throw new BusinessException(ErrorCode.NIKE_WEBSERVERCE_OUTBOUND_INFO_IS_NULL);// 12
                } else {
                    // 检查是否有足够销售库存
                    Long staid = sta.getId();
                    List<StaLineCommand> occupyLines = staLineDao.findNotEnoughtSalesAvailInvBySta(staid, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                    if (occupyLines == null || occupyLines.size() == 0) {
                        try {
                            eventObserver.onEvent(new TransactionalEvent(sta));
                            status = 5;
                        } catch (BusinessException e) {
                            status = 11;
                            e.setErrorCode(ErrorCode.NIKE_WEBSERVERCE_BUSINESS_EXCEPTION);
                            log.error("", e);
                            throw e;
                        }
                    } else {
                        status = 11;
                        BusinessException root = new BusinessException(ErrorCode.NIKE_WEBSERVERCE_INVENTORY_IS_LACK);// 11
                        for (StaLineCommand l : occupyLines) {
                            BusinessException current = new BusinessException(ErrorCode.SKU_NOT_ENOUGHT_INVNENTORY, new Object[] {l.getBarCode(), l.getJmcode(), l.getKeyProperties() == null ? "" : l.getKeyProperties(), l.getQuantity()});
                            root.setLinkedException(current);
                        }
                        throw root;
                    }
                    staDao.save(sta);
                    if (status == 5) {
                        staDeliveryInfo = new StaDeliveryInfo();
                        staDeliveryInfo.setId(sta.getId());
                        String lpCode = dInfo.getLpCode();
                        staDeliveryInfo.setCountry(dInfo.getCountry());
                        staDeliveryInfo.setProvince(dInfo.getProvince());
                        staDeliveryInfo.setCity(dInfo.getCity());
                        staDeliveryInfo.setDistrict(dInfo.getDistrict());
                        staDeliveryInfo.setAddress(dInfo.getAddress());
                        staDeliveryInfo.setReceiver(dInfo.getReceiver());
                        staDeliveryInfo.setTelephone(dInfo.getTelephone());
                        staDeliveryInfo.setMobile(dInfo.getMobile());
                        staDeliveryInfo.setZipcode(dInfo.getZipcode());
                        if (nikeOrderObj.getInvoiceTitle() != null) {
                            staDeliveryInfo.setStoreComIsNeedInvoice(true);
                            staDeliveryInfo.setStoreComInvoiceTitle(nikeOrderObj.getInvoiceTitle());
                            staDeliveryInfo.setStoreComInvoiceContent(nikeOrderObj.getInvoiceContent());
                            staDeliveryInfo.setStoreComTotalAmount(new BigDecimal(nikeOrderObj.getTotalAmount())); // 开票金额
                            staDeliveryInfo.setTransferFee(dInfo.getTotalAmount().subtract(new BigDecimal(nikeOrderObj.getTotalAmount()))); // 物流运费
                        } else {
                            staDeliveryInfo.setStoreComIsNeedInvoice(false);
                        }
                        // 代收货款记录
                        if (transactiontype.getCode().equals(Constants.TRANSACTION_TYPE_CODE_SALES_OUTBOUND)) {
                            // 销售出库 - 总金额设定 = 订单金额 + 运费
                            sta.setTotalActual(dInfo.getTotalAmount());
                        }
                        Transportator tp = transportatorDao.findByCode(lpCode);
                        if (tp != null) {
                            staDeliveryInfo.setLpCode(lpCode);
                        }
                        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
                        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
                        staDeliveryInfo.setSta(sta);
                        staDeliveryInfoDao.save(staDeliveryInfo);
                    }
                }
            }
        }
        return status;
    }

    /****
     * key: nike的作业类型 value:
     * 
     * @return
     */
    private Map<String, StockTransApplicationType> getStaTypes() {
        Map<String, StockTransApplicationType> staTypeMap = new HashMap<String, StockTransApplicationType>();
        staTypeMap.put("UD100", StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES); // UD100、销售出库
        // - 销售出库
        staTypeMap.put("UD011", StockTransApplicationType.INBOUND_RETURN_REQUEST); // UD011、退货入库 -
        // 退换货入库
        staTypeMap.put("UD015", StockTransApplicationType.INBOUND_RETURN_REQUEST); // UD015、换货入库 -
        // 退换货入库
        staTypeMap.put("UD014", StockTransApplicationType.OUTBOUND_RETURN_REQUEST); // UD014、换货出库 -
        // 退换货出库
        return staTypeMap;
    }

    public void createNikeSalesSta(StockTransApplication sta) {
        // 查找单据是否已存在
        if (findStaByWHAndRefCode(Constants.NIKE_ORDER_PRE + sta.getRefSlipCode(), sta.getType().getValue()) != null) {
            throw new BusinessException("nike create sales sta exist error.");
        }
        StaCreateQueue q = staCreateQueueDao.findBySlipCodeByType(sta.getRefSlipCode(), Constants.NIKE_RESOURCE, sta.getType());
        if (q == null) {
            throw new BusinessException("nike create sales sta slip code not exist : " + sta.getRefSlipCode() + ".");
        }
        // 店铺
        String owner = Constants.NIKE_SHOP1_ID;
        BiChannel companyShop = companyShopDao.getByCode(owner);
        if (companyShop == null) {
            throw new BusinessException("'" + owner + "' nike shop not found.");
        }
        // 仓库
        OperationUnit warehouseOU = findWarehouseOuByShopid(companyShop.getId());
        if (warehouseOU == null) {
            throw new BusinessException("warehouse not found.");
        }
        sta.setRefSlipCode(Constants.NIKE_ORDER_PRE + sta.getRefSlipCode());
        sta.setMainWarehouse(warehouseOU);
        sta.setCreateTime(new Date());
        sta.setOwner(owner);
        wmExe.validateBiChannelSupport(null, owner);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, warehouseOU.getId());
        sta.setMemo(q.getRemark());
        sta.setIsSn(false);
        if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
            // 退货入库不占用可用量
            sta.setIsNeedOccupied(false);
        } else {
            sta.setIsNeedOccupied(true);
        }
        sta.setTotalActual(q.getStaTotalAmount());
        sta.setOrderCreateTime(q.getOrderCreateTime());
        // 计算最晚发时间 怎么计算
        if (q.getTransTimeType() != null && q.getOrderPlanReceiptTime() != null && q.getTransTimeType().equals(TransTimeType.SAME_DAY)) {
            // 根据 订单预计送达时间 来设置最晚发货时间，固定格式：订单预计送达时间 22:00:00 = 最晚发货时间：11:00:00
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String[] dateStr = formatDate.format(q.getOrderPlanReceiptTime()).split(" ");
            try {
                sta.setPlanLastOutboundTime(formatDate.parse(dateStr[0] + " 11:00:00"));
            } catch (Exception e) {
                log.debug(e.getMessage());
                throw new BusinessException("nike create sta error.:sta.setPlanLastOutboundTime(formatDate.parse(dateStr[0]+' 11:00:00'));");
            }
        }

        staDao.save(sta);
        // SAVE STA DELIVERY INFO
        StaDeliveryInfo staDeliveryInfo = new StaDeliveryInfo();
        staDeliveryInfo.setId(sta.getId());
        staDeliveryInfo.setCountry(q.getCountry());
        staDeliveryInfo.setProvince(q.getProvince());
        staDeliveryInfo.setCity(q.getCity());
        staDeliveryInfo.setDistrict(q.getDistrict());
        staDeliveryInfo.setAddress(q.getAddress());
        staDeliveryInfo.setReceiver(q.getReceiver());
        staDeliveryInfo.setTelephone(q.getTelephone());
        staDeliveryInfo.setMobile(q.getMobile());
        staDeliveryInfo.setZipcode(q.getZipcode());
        staDeliveryInfo.setTransType(q.getTransType());
        staDeliveryInfo.setTransTimeType(q.getTransTimeType());
        staDeliveryInfo.setTransMemo(q.getTransMemo());

        if (q.getInvoiceTitle() != null) {
            staDeliveryInfo.setStoreComIsNeedInvoice(true);
            staDeliveryInfo.setStoreComInvoiceTitle(q.getInvoiceTitle());
            staDeliveryInfo.setStoreComInvoiceContent(q.getInvoiceContent());
            staDeliveryInfo.setStoreComTotalAmount(q.getInvoiceTotalAmount()); // 开票金额
            staDeliveryInfo.setTransferFee(q.getTransferFee()); // 物流运费
        } else {
            staDeliveryInfo.setStoreComIsNeedInvoice(false);
        }
        String lpCode = q.getLpCode();
        Transportator tp = transportatorDao.findByCode(lpCode);
        if (tp != null) {
            staDeliveryInfo.setLpCode(lpCode);
        }
        staDeliveryInfo.setSta(sta);
        staDeliveryInfoDao.save(staDeliveryInfo);
        // save sta line
        List<StaCreateQueueLine> list = staCreateQueueLineDao.findByScqId(q.getId());
        if (list == null || list.size() == 0) {
            throw new BusinessException("nike sales sta create error : no line.");
        }
        // 公司
        OperationUnit companyOu = warehouseOU.getParentUnit().getParentUnit();
        // 库存状态
        Map<Long, InventoryStatus> invStatus = getInventoryStatus(companyOu.getId());
        constractStaLine(list, invStatus, sta);
        if (!StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
            staDao.flush();
            // 验证库存可用量
            List<StaLineCommand> occupyLines = staLineDao.findNotEnoughtSalesAvailInvBySta(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
            if (occupyLines == null || occupyLines.size() == 0) {
                try {
                    eventObserver.onEvent(new TransactionalEvent(sta));
                } catch (BusinessException e) {
                    e = new BusinessException("nike webserverce business exception.");
                    log.error("", e);
                    throw e;
                }
            } else {
                BusinessException head = new BusinessException("inventory not enough.");// 11
                BusinessException root = head;
                for (StaLineCommand l : occupyLines) {
                    BusinessException current = new BusinessException("[" + l.getSkuCode() + "]" + l.getQuantity() + "　");
                    root.setLinkedException(current);
                    root = current;
                }
                throw head;
            }
        }
        // staDao.flush();
        // //更新销售出库订单信息 已经秒杀相关信息
        // staPerfectManager.omsTOwmsUpdateSta(sta.getId());
    }

    /**
     * STALINE 的构建
     * 
     * @param lineObjs
     * @param invStatus
     * @param status
     * @param sta
     * @return
     */
    private void constractStaLine(List<StaCreateQueueLine> list, Map<Long, InventoryStatus> invStatus, StockTransApplication sta) {
        Long skuQty = 0L;
        boolean isSpecialPackaging = false;
        BiChannel c = companyShopDao.getByCode(sta.getOwner());
        for (StaCreateQueueLine nikeLine : list) {
            InventoryStatus inventoryStatus = invStatus.get(nikeLine.getInvType());
            if (inventoryStatus == null) {
                throw new BusinessException("nike webserverce inventory status not found.");
            }
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(nikeLine.getUpc(), c.getCustomer().getId(), c.getId());
            if (sku == null) {
                throw new BusinessException("nike webserverce SKU not found.[SKU:" + nikeLine.getUpc() + "]");
            }
            StaLine staLine = new StaLine();
            staLine.setInvStatus(inventoryStatus);
            staLine.setSku(sku);
            staLine.setQuantity(new Long(nikeLine.getQty()));
            skuQty += nikeLine.getQty();
            staLine.setCompleteQuantity(0L);
            staLine.setOwner(Constants.NIKE_SHOP1_ID);
            staLine.setSta(sta);
            staLineDao.save(staLine);

            if (nikeLine.getIsPackage() != null && nikeLine.getIsPackage()) {
                isSpecialPackaging = true;
                GiftLine gl = new GiftLine();
                gl.setMemo(nikeLine.getPackageMemo());
                gl.setStaLine(staLine);
                gl.setType(GiftType.GIFT_PACKAGE);
                giftLineDao.save(gl);
            }
            if (nikeLine.getIsGiftCards() != null && nikeLine.getIsGiftCards()) {
                isSpecialPackaging = true;
                GiftLine gl = new GiftLine();
                gl.setMemo(nikeLine.getMemo());
                gl.setStaLine(staLine);
                gl.setType(GiftType.NIKE_GIFT);
                giftLineDao.save(gl);
            }
        }
        sta.setIsSpecialPackaging(isSpecialPackaging);
        sta.setSkuQty(skuQty);
    }

    /***
     * UD100、销售出库 - 销售出库 UD011、退货入库 - 退换货入库 UD015、换货入库 - 退换货入库 UD014、换货出库 - 退换货出库
     * 
     * @return
     */
    private Map<String, TransactionType> getTransactionType() {
        Map<String, TransactionType> transtypes = new HashMap<String, TransactionType>();

        List<String> transTypeCodes = new ArrayList<String>();
        transTypeCodes.add(Constants.TRANSACTION_TYPE_CODE_SALES_OUTBOUND); // 销售出库
        transTypeCodes.add(Constants.TRANSACTION_TYPE_INBOUND_RETURN_REQUEST); // 退换货入库
        transTypeCodes.add(Constants.TRANSACTION_TYPE_OUTBOUND_RETURN_REQUEST); // 退换货出库

        List<TransactionType> tts = transactionTypeDao.findByCodes(transTypeCodes, new BeanPropertyRowMapperExt<TransactionType>(TransactionType.class));

        for (TransactionType transType : tts) {
            if (transType.getCode().equals(Constants.TRANSACTION_TYPE_CODE_SALES_OUTBOUND)) {
                // UD100、销售出库 - 销售出库
                transtypes.put("UD100", transType);
            } else if (transType.getCode().equals(Constants.TRANSACTION_TYPE_INBOUND_RETURN_REQUEST)) {
                // UD011、退货入库 - 退换货入库
                transtypes.put("UD011", transType);
                // UD015、换货入库 - 退换货入库
                transtypes.put("UD015", transType);
            } else if (transType.getCode().equals(Constants.TRANSACTION_TYPE_OUTBOUND_RETURN_REQUEST)) {
                // UD014、换货出库 - 退换货出库
                transtypes.put("UD014", transType);
            }
        }
        return transtypes;
    }

    /**
     * 根据店铺id查找 任意为收发仓的仓库
     * 
     * @param shopid
     * @return
     */
    @Deprecated
    public OperationUnit findWarehouseOuByShopid(Long shopid) {
        return ouDao.getByPrimaryKey(1061L);
        // Warehouse warehouse = null;
        // List<Warehouse> warehouses = warehouseDao.findByCompanysShopId(shopid, new
        // BeanPropertyRowMapper<Warehouse>(Warehouse.class));
        // for (Warehouse entry : warehouses) {
        // Warehouse wh = warehouseDao.getByPrimaryKey(entry.getId());
        // if (wh == null) continue;
        // if (wh.getOpMode().getValue() == WarehouseOpMode.NORMAL.getValue()) { // 收发仓
        // warehouse = wh;
        // break;
        // }
        // }
        // if (warehouse == null) {
        // log.error("warehosue is not founded for nike ***************************");
        // throw new BusinessException(ErrorCode.NIKE_WRAEHOUSE_NOT_FOUND);
        // }
        // OperationUnit warehouseOU = operationUnitDao.getByPrimaryKey(warehouse.getOu().getId());
        // return null;
    }

    /**
     * 查询单据是否已经存在 取消已处理-17， 取消未处理 -15 可以创建， 其他不创建
     * 
     * @param refCode
     * @param staType
     */
    private StockTransApplication findStaByWHAndRefCode(String refCode, int staType) {
        return staDao.findWHAndRefCodeStaType(refCode, staType, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
    }

    private StockTransApplication findBySlipCodeAndType(String slipCode, int type) {
        List<StockTransApplication> stas = staDao.findBySlipCodeAndType(slipCode, type, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (stas != null && !stas.isEmpty()) {
            for (StockTransApplication sta : stas) {
                if (!StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus()) && !StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {
                    return sta;
                }
            }
            return stas.get(0);
        }
        return null;
    }

    /***
     * nike订单信息查找
     */
    public NikeOrderResultObj getNikeOrderResultByCode(String warehouseCode, String orderCode, String opType) {
        // 申请单类型 staType
        Map<String, StockTransApplicationType> staTypeMap = getStaTypes();
        StockTransApplicationType staType = staTypeMap.get(opType);
        StockTransApplication sta = this.findBySlipCodeAndType(Constants.NIKE_ORDER_PRE + orderCode, staType.getValue());
        NikeOrderResultObj resultObj = new NikeOrderResultObj();
        resultObj.setCode(orderCode);
        resultObj.setResultType(opType);
        if (sta == null) { // 单据不存在
            resultObj.setStatus(99);
        } else {
            int status = sta.getStatus().getValue();
            if (status == 10 || status == 4) { // 全部完成 10
                resultObj.setStatus(5);
                resultObj.setFulfillTime(sta.getOutboundTime());
                StaDeliveryInfo stadeliveryInfo = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                if (stadeliveryInfo != null) {
                    resultObj.setLpCode(stadeliveryInfo.getLpCode());
                    resultObj.setTrackingNo(stadeliveryInfo.getTrackingNo());
                } else {
                    log.debug("stadeliveryInfo is null ***************************");
                }
                // 取消已处理 17 | 取消未处理 15
            } else if (status == 17 || status == 15) {
                resultObj.setStatus(10);
            } else {
                resultObj.setStatus(0);
            }
        }
        return resultObj;
    }

    /***
     * 反馈库存信息
     */
    public List<InventoryObj> getAvailableInventoryByWarehouse(String warehouseCode) {
        // String shopId = Constants.NIKE_SHOP_ID;
        // List<MqInventoryLogCommand> inventoryLogs = mqInventoryLogDao.findFullQtyForNike(shopId,
        // new BeanPropertyRowMapperExt<MqInventoryLogCommand>(MqInventoryLogCommand.class));
        //
        // if (inventoryLogs == null || inventoryLogs.isEmpty()) {
        // log.error("inventory for nike is null***************************");
        // }
        // if (inventoryLogs != null && inventoryLogs.size() > 0) {
        // List<InventoryObj> invObjs = new ArrayList<InventoryObj>();
        // InventoryObj invObj = null;
        // for (int i = 0; i < inventoryLogs.size(); i++) {
        // invObj = new InventoryObj();
        // invObj.setUpcCode(inventoryLogs.get(i).getBarcode());
        // invObj.setQuantity(inventoryLogs.get(i).getQty().intValue() < 0 ? 0 :
        // inventoryLogs.get(i).getQty().intValue());
        // invObjs.add(invObj);
        // }
        // return invObjs;
        // } else {
        // return null;
        // }
        return null;
    }


    /***
     * 反馈商品库存信息
     */
    public List<InventoryObj> getAvailableInventoryBySku(String supplierCode) {
        // String shopId = Constants.NIKE_SHOP_ID;
        // List<MqInventoryLogCommand> inventoryLogs = mqInventoryLogDao.findSkuQtyForNike(shopId,
        // supplierCode, new
        // BeanPropertyRowMapperExt<MqInventoryLogCommand>(MqInventoryLogCommand.class));
        // if (inventoryLogs == null || inventoryLogs.isEmpty()) {
        // log.error("inventory for nike is null***************************");
        // }
        // if (inventoryLogs != null && inventoryLogs.size() > 0) {
        // List<InventoryObj> invObjs = new ArrayList<InventoryObj>();
        // InventoryObj invObj = null;
        // for (int i = 0; i < inventoryLogs.size(); i++) {
        // invObj = new InventoryObj();
        // invObj.setUpcCode(inventoryLogs.get(i).getBarcode());
        // invObj.setQuantity(inventoryLogs.get(i).getQty().intValue() < 0 ? 0 :
        // inventoryLogs.get(i).getQty().intValue());
        // invObjs.add(invObj);
        // }
        // return invObjs;
        // } else {
        // return null;
        // }
        return null;
    }

    /**
     * 查寻NIKE过单信息
     */
    public Pagination<StaCreateQueueCommand> findNikeOrder(int start, int page, StaCreateQueueCommand cmd, Long ouid, Sort[] sorts) {
        // 店铺
        String owner = Constants.NIKE_SHOP1_ID;
        BiChannel companyShop = companyShopDao.getByCode(owner);
        if (companyShop == null) {
            throw new BusinessException(ErrorCode.NIKE_SHOP_NOT_FOUND, new Object[] {owner});
        }
        // 仓库
        OperationUnit warehouseOU = findWarehouseOuByShopid(companyShop.getId());
        if (warehouseOU == null) {
            throw new BusinessException(ErrorCode.NIKE_WRAEHOUSE_NOT_FOUND);
        }
        // 判断是否是相同的仓库
        if (warehouseOU.getId().equals(ouid)) {
            cmd.initValues();
            return staCreateQueueDao.findPageBySource(start, page, Constants.NIKE_RESOURCE, cmd.getSlipCode(), cmd.getIntStatus(), cmd.getIntStatusList(), new BeanPropertyRowMapperExt<StaCreateQueueCommand>(StaCreateQueueCommand.class), sorts);
        }
        return null;
    }

    /**
     * 状态修改
     */
    public void forUpdateStatus(List<Long> idList, DefaultStatus status) {
        if (idList != null && idList.size() > 0) {
            if (idList.size() > 100) {
                List<Long> tempList = new ArrayList<Long>();
                for (int i = 0; i < idList.size(); i++) {
                    tempList.add(idList.get(i));
                    if (i % 100 == 0) {
                        staCreateQueueDao.updateStatusByIds(status.getValue(), tempList);
                        tempList = new ArrayList<Long>();
                    }
                }
                if (tempList.size() > 0) {
                    staCreateQueueDao.updateStatusByIds(status.getValue(), tempList);
                }
            } else {
                staCreateQueueDao.updateStatusByIds(status.getValue(), idList);
            }
        }
    }

    @Override
    public void deleteCreateFinishQueue() {
        staCreateQueueDao.deleteFinishLine();
        staCreateQueueDao.deleteFinish();
    }

    /**
     * 查寻NIKE过单信息
     */
    public Pagination<NikeCheckReceiveCommand> findNikeCheckReceive(int start, int page, NikeCheckReceiveCommand cmd, Sort[] sorts, Long ouid) {
        // 店铺
        String owner = Constants.NIKE_SHOP1_ID;
        BiChannel companyShop = companyShopDao.getByCode(owner);
        if (companyShop == null) {
            throw new BusinessException(ErrorCode.NIKE_SHOP_NOT_FOUND, new Object[] {owner});
        }
        if (cmd == null) {
            cmd = new NikeCheckReceiveCommand();
        }
        return nikeCheckReceiveDao.findNikeCheckReceive(start, page, cmd.getValuesMap(), sorts, new BeanPropertyRowMapperExt<NikeCheckReceiveCommand>(NikeCheckReceiveCommand.class));
    }

    @SuppressWarnings("unchecked")
    public ReadStatus importNikecheckReceive(File staFile, int manualType, String remark, Long ouid, User creator) throws Exception {
        // 店铺
        Map<String, BiChannel> nikeShopMap = new HashMap<String, BiChannel>();
        BiChannel shop1 = companyShopDao.getByCode(Constants.NIKE_SHOP1_ID);
        BiChannel shop2 = companyShopDao.getByCode(Constants.NIKE_SHOP2_ID);
        BiChannel shop3 = companyShopDao.getByCode(Constants.NIKE_SHOP3_ID);
        BiChannel shop4 = companyShopDao.getByCode(Constants.NIKE_SHOP4_ID);
        nikeShopMap.put(shop1.getVmiCode(), shop1);
        nikeShopMap.put(shop2.getVmiCode(), shop2);
        nikeShopMap.put(shop3.getVmiCode(), shop3);
        nikeShopMap.put(shop4.getVmiCode(), shop4);
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = nikeCheckReceiveReader.readAll(new FileInputStream(staFile), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            List<BusinessException> errors = new ArrayList<BusinessException>();
            List<NikeCheckReceive> list = (List<NikeCheckReceive>) beans.get("nike");
            Integer index = 3;
            for (NikeCheckReceive nikeBean : list) {
                if (!nikeShopMap.containsKey(nikeBean.getOwnerCode())) {
                    errors.add(new BusinessException(ErrorCode.NIKE_CHECK_IMPORT_OWNER_CODE_ERROR, new Object[] {nikeBean.getOwnerCode()}));
                    continue;
                }
                if (nikeBean.getQuantity() == null) {
                    errors.add(new BusinessException(ErrorCode.NIKE_CHECK_IMPORT_QTY_ERROR, new Object[] {index}));
                    continue;
                }
                index++;
            }
            if (errors != null && !errors.isEmpty()) {
                readStatus.setStatus(ReadStatus.STATUS_SUCCESS - 1);
                readStatus.getExceptions().addAll(errors);

            } else {
                Date date = new Date();
                String checkCode = "NIKE_ADJ" + FormatUtil.formatDate(date, "yyyyMMddHHmmssSSS");
                String fileCode = nikeManager.getFileCode();
                for (NikeCheckReceive nikeBean : list) {
                    nikeBean.setCreateDate(date);
                    nikeBean.setOperator(creator.getUserName());
                    nikeBean.setManualType(manualType);
                    nikeBean.setRemark(remark);
                    nikeBean.setCheckCode(checkCode);
                    nikeBean.setStatus(NikeCheckReceive.CREATE_STATUS);
                    nikeBean.setType(NikeCheckReceive.MANUAL_CHECK_TYPE);
                    nikeBean.setReceiveDate(new Date());
                    nikeBean.setFileCode(fileCode);
                    nikeCheckReceiveDao.save(nikeBean);
                }
            }
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
        return readStatus;
    }

    @Override
    public Integer cancelOrder(String unionId, String orderCode, Integer type) {
        Integer status = CANCEL_EXCEPTION;
        if (Constants.UNION_ID_FOR_NIKE.equals(unionId)) {
            if (!(type == 1 || type == 2 || type == 3)) {
                status = CANCEL_NOTFOUND;
            } else {
                type = type.intValue() == 1 ? 25 : type.intValue() == 2 ? 41 : 42;
                List<StockTransApplicationCommand> staList = staDao.findStaBySlipCodeAndType(Constants.NIKE_ORDER_PRE + orderCode, type, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
                if (staList == null || staList.size() == 0) {
                    StaCreateQueue sq = staCreateQueueDao.findBySlipCodeNike(orderCode, Constants.NIKE_RESOURCE, new BeanPropertyRowMapper<StaCreateQueue>(StaCreateQueue.class));
                    if (sq == null) {
                        status = CANCEL_NOTFOUND;
                    } else {
                        staCreateQueueDao.deleteByPrimaryKey(sq.getId());
                        status = CANCEL_SUCCESS;
                    }
                } else {
                    if (staList.size() == 1) {
                        if (staList.get(0).getCancelTime() != null) {
                            status = CANCEL_HAVEDO;
                        } else {
                            try {
                                wareHouseManagerCancel.cancelSalesSta(staList.get(0).getId(), null);
                                status = CANCEL_SUCCESS;
                            } catch (BusinessException e) {
                                status = CANCEL_CANNOTDO;
                            }
                        }
                    } else {
                        boolean b = true;
                        for (StockTransApplicationCommand stc : staList) {
                            if (stc.getCancelTime() == null) {
                                b = false;
                                break;
                            }
                        }
                        if (b) {
                            status = CANCEL_HAVEDO;
                        } else {
                            b = true;
                            try {
                                cancelSalesStaList(staList);
                            } catch (Exception e) {
                                b = false;
                            }
                            if (b) {
                                status = CANCEL_HAVEDO;
                            } else {
                                status = CANCEL_CANNOTDO;
                            }
                        }
                    }
                }
            }
        }
        return status;
    }

    private void cancelSalesStaList(List<StockTransApplicationCommand> staList) {
        for (StockTransApplicationCommand stc : staList) {
            if (stc.getCancelTime() == null) {
                wareHouseManagerCancel.cancelSalesSta(stc.getId(), null);
            }
        }
    }

    @Override
    public void insertNikeStockReceivePac() {
        List<NikeTurnCreate> creates = createDao.findNikeTurnStatus();
        for (NikeTurnCreate nikeTurnCreate : creates) {
            String receiveDate = FormatUtil.formatDate(new Date(), "yyyyMMdd");
            Sku sku = skuDao.getByPrimaryKey(nikeTurnCreate.getSku().getId());

            nikeReturnReceiveDao.createTransferOutFeedbackForNikePac(sku.getExtensionCode2(), nikeTurnCreate.getQuantity(), Constants.NIKE_RESOURCE, nikeTurnCreate.getReferenceNo(), receiveDate, NikeReturnReceive.CREATE_STATUS,
                    NikeReturnReceive.RETURN_TRANSFER_OUT_TYPE, nikeTurnCreate.getToLocation(), nikeTurnCreate.getFromLocation());

            if (sku.getIsGift() == null || !sku.getIsGift()) {
                NikeStockReceive rec = new NikeStockReceive();
                rec.setReferenceNo(nikeTurnCreate.getReferenceNo());
                rec.setItemEanUpcCode(sku.getExtensionCode2());
                rec.setReceiveDate(new Date());
                rec.setQuantity(nikeTurnCreate.getQuantity());
                rec.setFromLocation(nikeTurnCreate.getFromLocation());
                rec.setToLocation(nikeTurnCreate.getToLocation());
                rec.setTransferPrefix(nikeTurnCreate.getFromLocation());
                rec.setStatus(NikeStockReceive.CREATE_STATUS);
                // 转店入库反馈 type 2
                rec.setType(NikeStockReceive.TRANSFER_INBOUND_RECEIVE_TYPE);
                rec.setCreateDate(new Date());
                rec.setPoStatus(NikeStockReceive.CLOSED);
                rec.setCartonStatus(NikeStockReceive.CLOSED);
                nikeStockReceiveDao.save(rec);
            }
            nikeTurnCreate.setStatus(10);
            createDao.save(nikeTurnCreate);
        }
        createDao.flush();


    }
}
