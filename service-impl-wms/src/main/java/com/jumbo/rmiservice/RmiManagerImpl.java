package com.jumbo.rmiservice;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.BiChannelBrandRefDao;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.invflow.WmsIMOccupiedAndReleaseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.QueueGiftLineDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.dao.warehouse.QueueStaDeliveryInfoDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueStaLineDao;
import com.jumbo.dao.warehouse.QueueStaLineOwnerDao;
import com.jumbo.dao.warehouse.QueueStaPaymentDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseCoverageAreaRefDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhOrderSpecialExecuteDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.rmi.warehouse.Invoice;
import com.jumbo.rmi.warehouse.InvoiceLine;
import com.jumbo.rmi.warehouse.Order;
import com.jumbo.rmi.warehouse.OrderLine;
import com.jumbo.rmi.warehouse.OrderLineGift;
import com.jumbo.rmi.warehouse.OrderResult;
import com.jumbo.rmi.warehouse.OrderSpecialExecute;
import com.jumbo.rmi.warehouse.OrderStaPayment;
import com.jumbo.rmi.warehouse.WarehousePriority;
import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.rmi.warehouse.vmi.VmiAsn;
import com.jumbo.rmi.warehouse.vmi.VmiAsnLine;
import com.jumbo.rmi.warehouse.vmi.VmiRto;
import com.jumbo.rmi.warehouse.vmi.VmiRtoLine;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuWarrantyCardType;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.Default.VmiAsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineDefault;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PaymentType;
import com.jumbo.wms.model.warehouse.QueueGiftLine;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueStaInvoice;
import com.jumbo.wms.model.warehouse.QueueStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineOwner;
import com.jumbo.wms.model.warehouse.QueueStaLineStatus;
import com.jumbo.wms.model.warehouse.QueueStaLineType;
import com.jumbo.wms.model.warehouse.QueueStaPayment;
import com.jumbo.wms.model.warehouse.SpecialSkuType;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucherType;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WhOrderSpecialExecute;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("rmiManager")
public class RmiManagerImpl extends BaseManagerImpl implements RmiManager {

    /**
     * 
     */
    private static final long serialVersionUID = -3778143978523447547L;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private WarehouseCoverageAreaRefDao warehouseCoverageAreaRefDao;
    @Autowired
    private ChannelWhRefRefDao warehouseShopRefDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private ChannelWhRefRefDao channelWhRefRefDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private QueueStaPaymentDao queueStaPaymentDao;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    private EventObserver eventObserver;
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private QueueStaDeliveryInfoDao queueStaDeliveryInfoDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    private QueueGiftLineDao queueGiftLineDao;
    @Autowired
    private QueueStaInvoiceDao queueStaInvoiceDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private QueueStaInvoiceLineDao queueStaInvoiceLineDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private VmiAsnDao vmiAsnDao;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private WhOrderSpecialExecuteDao whOrderSpecialExecuteDao;
    @Autowired
    private VmiRtoDao vmiRtoDao;
    @Autowired
    private VmiRtoLineDao vmiRtoLineDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private BiChannelBrandRefDao biChannelBrandRefDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private QueueStaLineOwnerDao queueStaLineOwnerDao;
    @Autowired
    private WmsIMOccupiedAndReleaseDao wmsIMOccupiedAndReleaseDao;



    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:RmiManagerImpl");
        }
    }

    /**
     * 验证明细行
     * 
     * @param order
     */
    private void validateOrderLine(Order order) {
        // 明细行所有库存状态基于主仓库公司获取
        OperationUnit mainWhOu = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());
        OperationUnit mainCmpOu = mainWhOu.getParentUnit().getParentUnit();
        Map<String, InventoryStatus> map = new HashMap<String, InventoryStatus>();
        for (OrderLine l : order.getLines()) {
            if (StringUtils.hasText(l.getInvStatus())) {
                InventoryStatus sts = map.get(l.getInvStatus());
                if (sts == null) {
                    sts = inventoryStatusDao.getByName(l.getInvStatus(), mainCmpOu.getId());
                    if (sts == null) {
                        throw new BusinessException(ErrorCode.INV_STATUS_NOT_FOUND, new Object[] {l.getInvStatus()});
                    }
                    l.setInvStatusId(sts.getId());
                    map.put(l.getInvStatus(), sts);
                } else {
                    l.setInvStatusId(sts.getId());
                }
            }
            Sku sku = skuDao.getByCode(l.getSkuCode());
            if (sku == null) {
                throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, new Object[] {l.getSkuCode()});
            }
            if (!sku.getCustomer().getId().equals(order.getCustomerId())) {
                throw new BusinessException(ErrorCode.CUSTOMER_NOT_ERROR, new Object[] {order.getCustomerCode()});
            }

        }
    }

    /**
     * 
     * @param order
     */
    public void validateOrderInfo(Order order) {
        int type = 0;
        if (order.getType() == 1) {
            type = 21;
        } else if (order.getType() == 2) {
            type = 41;
            for (OrderLine line1 : order.getLines()) {
                if (line1.getDirection() == 2) {
                    type = 42;
                }
            }

        }
        if (type == 42) {
            if (!StringUtils.hasText(order.getAddWhOuCode())) {
                List<StockTransApplication> sta = staDao.findStaBySlipCode1(order.getSlipCode1());
                if (sta.size() == 0) {
                    throw new BusinessException(ErrorCode.ON_SALES_STA_ERROR, new Object[] {order.getAddWhOuCode()});
                }
                if (sta.size() > 1) {
                    throw new BusinessException(ErrorCode.ON_SALES_STA, new Object[] {order.getAddWhOuCode()});
                } else {
                    order.setAddWhOuId(sta.get(0).getMainWarehouse().getId());
                }

            }

        }

        // 验证客户
        Customer customer = customerDao.getByCode(order.getCustomerCode());
        if (customer == null) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, new Object[] {order.getCustomerCode()});
        }
        order.setCustomerId(customer.getId());
        // 验证渠道
        BiChannel channel = biChannelDao.getByCode(order.getOwner());
        if (channel == null) {
            throw new BusinessException(ErrorCode.CHANNEL_NOT_FOUNT, new Object[] {order.getOwner()});
        }

        // 验证渠道客户关系
        if (channel.getCustomer() == null || !customer.getId().equals(channel.getCustomer().getId())) {
            throw new BusinessException(ErrorCode.CHANNEL_NOT_IN_CUSTOMER, new Object[] {order.getOwner(), order.getCustomerCode()});
        }
        wareHouseManagerExe.validateBiChannelSupport(null, channel.getCode());
        // 验证仓库
        OperationUnit mainWhOu = operationUnitDao.getByCode(order.getMainWhOuCode());
        if (mainWhOu == null || !OperationUnitType.OUTYPE_WAREHOUSE.equals(mainWhOu.getOuType().getName())) {
            throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new Object[] {order.getMainWhOuCode()});
        }
        // 验证仓库基础信息
        Warehouse mainWh = warehouseDao.getByOuId(mainWhOu.getId());
        if (mainWh == null || mainWh.getCustomer() == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_INFO_NOT_SETTING, new Object[] {order.getMainWhOuCode()});
        }
        // 验证仓库绑定客户
        if (!customer.getId().equals(mainWh.getCustomer().getId())) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_IN_CUSTOMER, new Object[] {order.getMainWhOuCode(), order.getCustomerCode()});
        }
        // 验证仓库与渠道绑定关系
        ChannelWhRef mref = channelWhRefRefDao.getChannelRef(mainWhOu.getId(), channel.getId());
        if (mref == null) {
            throw new BusinessException(ErrorCode.CHANNEL_WAREHOUSE_NO_REF, new Object[] {order.getOwner(), order.getMainWhOuCode()});
        }
        order.setMainWhOuId(mainWhOu.getId());
        // 验证附加渠道
        if (StringUtils.hasText(order.getAddOwner())) {
            BiChannel addChannel = biChannelDao.getByCode(order.getAddOwner());
            if (addChannel == null) {
                throw new BusinessException(ErrorCode.CHANNEL_NOT_FOUNT, new Object[] {order.getAddOwner()});
            }
            // 校验渠道是否属于当前客户
            if (addChannel.getCustomer() == null || !customer.getId().equals(addChannel.getCustomer().getId())) {
                throw new BusinessException(ErrorCode.CHANNEL_NOT_IN_CUSTOMER, new Object[] {order.getAddOwner(), order.getCustomerCode()});
            }
            // 当不存在附属仓库时校验附加渠道与主仓库绑定关系
            if (!StringUtils.hasText(order.getAddWhOuCode())) {
                ChannelWhRef maddref = channelWhRefRefDao.getChannelRef(mainWhOu.getId(), addChannel.getId());
                if (maddref == null) {
                    throw new BusinessException(ErrorCode.CHANNEL_WAREHOUSE_NO_REF, new Object[] {order.getAddOwner(), order.getMainWhOuCode()});
                }
            }
        }
        // 验证附加仓库
        if (StringUtils.hasText(order.getAddWhOuCode())) {
            OperationUnit addWhOu = operationUnitDao.getByCode(order.getAddWhOuCode());
            if (addWhOu == null || !OperationUnitType.OUTYPE_WAREHOUSE.equals(addWhOu.getOuType().getName())) {
                throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new Object[] {order.getAddWhOuCode()});
            }
            // 验证附属仓库基础信息
            Warehouse addWh = warehouseDao.getByOuId(addWhOu.getId());
            if (addWh == null || addWh.getCustomer() == null) {
                throw new BusinessException(ErrorCode.WAREHOUSE_INFO_NOT_SETTING, new Object[] {order.getAddWhOuCode()});
            }
            // 验证附属仓库绑定客户
            if (!customer.getId().equals(addWh.getCustomer().getId())) {
                throw new BusinessException(ErrorCode.WAREHOUSE_NOT_IN_CUSTOMER, new Object[] {order.getAddWhOuCode(), order.getCustomerCode()});
            }
            // 验证附属仓库与店铺绑定关系
            if (StringUtils.hasText(order.getAddOwner())) {
                // 存在附加店铺,验证绑定关系
                BiChannel addChannel = biChannelDao.getByCode(order.getAddOwner());
                ChannelWhRef addref = channelWhRefRefDao.getChannelRef(addWhOu.getId(), addChannel.getId());
                if (addref == null) {
                    throw new BusinessException(ErrorCode.CHANNEL_WAREHOUSE_NO_REF, new Object[] {order.getAddOwner(), order.getAddWhOuCode()});
                }
                wareHouseManagerExe.validateBiChannelSupport(null, addChannel.getCode());
            } else {
                // 不存在附加店铺,验证主店铺与附属仓库关系
                ChannelWhRef addref = channelWhRefRefDao.getChannelRef(addWhOu.getId(), channel.getId());
                if (addref == null) {
                    throw new BusinessException(ErrorCode.CHANNEL_WAREHOUSE_NO_REF, new Object[] {order.getOwner(), order.getAddWhOuCode()});
                }
            }
            order.setAddWhOuId(addWhOu.getId());
        }
        // 验证库存状态
        OperationUnit mainCmpOu = mainWhOu.getParentUnit().getParentUnit();
        if (StringUtils.hasText(order.getInvStatus())) {
            InventoryStatus mainSts = inventoryStatusDao.getByName(order.getInvStatus(), mainCmpOu.getId());
            if (mainSts == null) {
                throw new BusinessException(ErrorCode.INV_STATUS_NOT_FOUND, new Object[] {order.getInvStatus()});
            }
            order.setInvStatusId(mainSts.getId());
        }
        // 判断是否存在分付出仓库，如果存在则附属库存状态跟附属仓库公司否则跟主仓库公司
        if (StringUtils.hasText(order.getInvAddStatus())) {
            if (StringUtils.hasText(order.getAddWhOuCode())) {
                OperationUnit addWhOu = operationUnitDao.getByPrimaryKey(order.getAddWhOuId());
                OperationUnit addCmpOu = addWhOu.getParentUnit().getParentUnit();
                InventoryStatus addSts = inventoryStatusDao.getByName(order.getInvAddStatus(), addCmpOu.getId());
                if (addSts == null) {
                    throw new BusinessException(ErrorCode.INV_STATUS_NOT_FOUND, new Object[] {order.getInvAddStatus()});
                }
                order.setInvAddStatusId(addSts.getId());
            } else {
                InventoryStatus addSts = inventoryStatusDao.getByName(order.getInvAddStatus(), mainCmpOu.getId());
                if (addSts == null) {
                    throw new BusinessException(ErrorCode.INV_STATUS_NOT_FOUND, new Object[] {order.getInvAddStatus()});
                }
                order.setInvAddStatusId(addSts.getId());
            }
        }
        validateOrderLine(order);
    }

    @Override
    public String createStaByOrder(Order order) {
        switch (StockTransApplicationType.valueOf(order.getType())) {
            case TRANSIT_CROSS:// 库间移动
            case SAME_COMPANY_TRANSFER:// 同公司调拨
            case DIFF_COMPANY_TRANSFER:// 不同公司调拨
                createStaByOrderAuto(order);
                return null;
            case VMI_OWNER_TRANSFER:// VMI转店
                String code = createOwnerTransFerSta(order);
                return code;
            case OUTBOUND_PURCHASE:// 采购出库
            case SAMPLE_OUTBOUND:// 样品领用出库
            case SKU_EXCHANGE_OUTBOUND:// 商品置换出库
            case REAPAIR_OUTBOUND:// 送修出库
            case OUTBOUND_PACKAGING_MATERIALS:// 包材领用出库
            case WELFARE_USE:// 福利领用
            case FIXED_ASSETS_USE:// 固定资产领用
            case SCARP_OUTBOUND:// 报废出库
            case SALES_PROMOTION_USE:// 促销领用
            case LOW_VALUE_CONSUMABLE_USE:// 低值易耗品
            case OUTBOUND_SETTLEMENT:// 结算经销出库
            case OUTBOUND_CONSIGNMENT:// 代销出库
                createSingleOutboundSta(order);
                return null;
            case SERIAL_NUMBER_OUTBOUND:// 串号拆分出库
            case SERIAL_NUMBER_GROUP_OUTBOUND:// 串号组合出库
                createOutAndInSta(order);
                return null;
            case INBOUND_PURCHASE:// 采购入库
            case SAMPLE_INBOUND:// 样品领用入库
            case SKU_EXCHANGE_INBOUND:// 商品置换入库
            case REAPAIR_INBOUND:// 送修入库
            case SKU_RETURN_INBOUND:// 货返入库
            case INBOUND_GIFT:// 赠品入库
            case INBOUND_RETURN_PURCHASE:// 采购退货调整入库
                createSingleInboundSta(order);
                return null;
            case ORDER_CODE:// 订单号
            case INBOUND_RETURN_REQUEST_CODE:// 退换货申请单号
                createQueueSta(order);
            default:
                break;
        }
        return null;

    }

    /**
     * 串号出库（拆分，组合）同时创建出和入 锁定入作业单
     * 
     * @param order
     */
    private void createOutAndInSta(Order order) {
        List<OrderLine> list = order.getLines();
        List<OrderLine> outList = new ArrayList<OrderLine>();
        List<OrderLine> inList = new ArrayList<OrderLine>();
        for (OrderLine line : list) {
            if (line.getDirection() == 2) {
                outList.add(line);
            } else {
                inList.add(line);
            }
        }
        order.setLines(outList);
        // 先创出
        createSingleOutboundSta(order);
        order.setLines(inList);
        // 创建入库
        createSingleInboundSta(order);
    }

    /**
     * 通用入库作业单创建 样品领用入库、商品置换入库、送修入库、串号入库
     * 
     * @param order
     */
    private void createSingleInboundSta(Order order) {
        StockTransApplication sta = new StockTransApplication();
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());
        TransactionType t = null;
        if (order.getType() == StockTransApplicationType.SERIAL_NUMBER_GROUP_OUTBOUND.getValue()) {
            order.setIsLocked(true);
            order.setType(StockTransApplicationType.SERIAL_NUMBER_GROUP_INBOUND.getValue());
        }
        if (order.getType() == StockTransApplicationType.SERIAL_NUMBER_OUTBOUND.getValue()) {
            order.setIsLocked(true);
            order.setType(StockTransApplicationType.SERIAL_NUMBER_INBOUND.getValue());
        }
        t = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(StockTransApplicationType.valueOf(order.getType())));
        if (t != null) {
            sta.setRefSlipCode(order.getCode());
            sta.setSlipCode1(order.getSlipCode1());
            sta.setSlipCode2(order.getSlipCode2());
            sta.setSlipCode3(order.getSlipCode3());
            sta.setIsLocked(order.getIsLocked());
            sta.setType(StockTransApplicationType.valueOf(order.getType()));
            sta.setMainWarehouse(ou1);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            sta.setIsPf(order.getIsPf());// 唯品会 1：采购收货 标示批发
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
            sta.setIsNeedOccupied(false);
            sta.setOwner(order.getOwner());
            sta.setMemo(order.getMemo());
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            sta.setSupplierName(order.getSupplierName());
            sta.setPaymentTime(order.getPaymentTime());
            sta.setPaymentType(order.getPaymentType());
            if (order.getSlipType() == SlipType.REVERSE_NP_ADJUSTMENT_INBOUND.getValue()) {
                sta.setRefSlipType(SlipType.REVERSE_NP_ADJUSTMENT_INBOUND);
            }

            staDao.save(sta);
            if (order.getType() != StockTransApplicationType.INBOUND_PURCHASE.getValue() && order.getType() != StockTransApplicationType.INBOUND_GIFT.getValue() && order.getType() != StockTransApplicationType.SKU_RETURN_INBOUND.getValue()
                    && order.getType() != StockTransApplicationType.SAMPLE_INBOUND.getValue() && order.getType() != StockTransApplicationType.SKU_EXCHANGE_INBOUND.getValue() && order.getType() != StockTransApplicationType.REAPAIR_INBOUND.getValue()
                    && order.getType() != StockTransApplicationType.SERIAL_NUMBER_GROUP_INBOUND.getValue() && order.getType() != StockTransApplicationType.SERIAL_NUMBER_INBOUND.getValue()
                    && order.getType() != StockTransApplicationType.INBOUND_RETURN_PURCHASE.getValue()) {
                InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(order.getInvStatusId(), ou1.getParentUnit().getParentUnit().getId());
                sta.setMainStatus(invStatus);
                // 合并明细，只合并同商品、因为库存状态一致
                Map<String, OrderLine> map = new HashMap<String, OrderLine>();
                for (OrderLine line : order.getLines()) {
                    if (map.get(line.getSkuCode()) == null) {
                        map.put(line.getSkuCode(), line);
                    } else {
                        OrderLine newLine = map.get(line.getSkuCode());
                        newLine.setQty(newLine.getQty() + line.getQty());
                        map.put(line.getSkuCode(), newLine);
                    }
                }
                List<OrderLine> lineList = new ArrayList<OrderLine>();
                lineList.addAll(map.values());
                for (OrderLine line1 : lineList) {
                    Sku sku = skuDao.getByCode(line1.getSkuCode());
                    StaLine sl = new StaLine();
                    sl.setSta(sta);
                    sl.setSku(sku);
                    sl.setOwner(order.getOwner());
                    sl.setQuantity(line1.getQty());
                    sl.setInvStatus(invStatus);
                    staLineDao.save(sl);
                }
            } else {
                // 合并明细商品和状态
                Map<String, OrderLine> map = new HashMap<String, OrderLine>();
                for (OrderLine line : order.getLines()) {
                    if (map.get(line.getSkuCode() + "-" + line.getInvStatusId()) == null) {
                        map.put(line.getSkuCode() + "-" + line.getInvStatusId(), line);
                    } else {
                        OrderLine newLine = map.get(line.getSkuCode() + "-" + line.getInvStatusId());
                        newLine.setQty(newLine.getQty() + line.getQty());
                        map.put(line.getSkuCode() + "-" + line.getInvStatusId(), newLine);
                    }
                }
                List<OrderLine> lineList = new ArrayList<OrderLine>();
                lineList.addAll(map.values());
                for (OrderLine line1 : lineList) {
                    Sku sku = skuDao.getByCode(line1.getSkuCode());
                    StaLine sl = new StaLine();
                    sl.setSta(sta);
                    sl.setSku(sku);
                    sl.setOwner(order.getOwner());
                    sl.setQuantity(line1.getQty());
                    sl.setCompleteQuantity(0L);
                    sl.setSkuCost(line1.getSkuCost() == null ? new BigDecimal(0) : line1.getSkuCost());
                    InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(line1.getInvStatusId(), ou1.getParentUnit().getParentUnit().getId());
                    sl.setInvStatus(invStatus);
                    staLineDao.save(sl);
                }
            }
            staDao.flush();
            staDao.updateSkuQtyById(sta.getId());
            staDao.updateIsSnSta(sta.getId());
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
        } else {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {StockTransApplicationType.valueOf(order.getType())});
        }
    }

    /**
     * 通用出库作业单创建 样品领用出库、商品置换出库、送修出库、串号出库
     * 
     * @param order
     */
    private void createSingleOutboundSta(Order order) {
        StockTransApplication sta = new StockTransApplication();
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());
        sta.setRefSlipCode(order.getCode());
        sta.setSlipCode1(order.getSlipCode1());
        sta.setSlipCode2(order.getSlipCode2());
        sta.setSlipCode3(order.getSlipCode3());
        sta.setMainWarehouse(ou1);
        sta.setOwner(order.getOwner());
        sta.setCreateTime(new Date());
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setType(StockTransApplicationType.valueOf(order.getType()));
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setIsPf(order.getIsPf());// 唯品会 采购出库批发
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        sta.setLastModifyTime(new Date());
        sta.setIsNeedOccupied(true);
        sta.setIsLocked(order.getIsLocked());
        sta.setIsNotPacsomsOrder(order.getIsNotPacsomsOrder());
        staDao.save(sta);
        staDao.flush();
        if (order.getDeliveryInfo() != null) {
            StaDeliveryInfo di = new StaDeliveryInfo();
            di.setCountry(order.getDeliveryInfo().getCountry() == null ? "中国" : order.getDeliveryInfo().getCountry());
            di.setProvince(order.getDeliveryInfo().getProvince());
            di.setCity(order.getDeliveryInfo().getCity());
            di.setDistrict(order.getDeliveryInfo().getDistrict());
            di.setAddress(order.getDeliveryInfo().getAddress());
            di.setTelephone(order.getDeliveryInfo().getTelephone());
            di.setMobile(order.getDeliveryInfo().getMobile());
            di.setReceiver(order.getDeliveryInfo().getReceiver());
            di.setZipcode(order.getDeliveryInfo().getZipcode());
            di.setIsCod(order.getDeliveryInfo().getIsCod());
            di.setLpCode(order.getDeliveryInfo().getLpCode());
            di.setTrackingNo(order.getDeliveryInfo().getTrackingNo());
            di.setSta(sta);
            di.setId(sta.getId());
            di.setLastModifyTime(new Date());
            staDeliveryInfoDao.save(di);
            sta.setStaDeliveryInfo(di);
        } else {
            StaDeliveryInfo di = new StaDeliveryInfo();
            di.setSta(sta);
            di.setId(sta.getId());
            di.setLastModifyTime(new Date());
            staDeliveryInfoDao.save(di);
            sta.setStaDeliveryInfo(di);
        }
        // 强制约束头跟明细库存状态一致
        if (order.getType() != StockTransApplicationType.OUTBOUND_PURCHASE.getValue() && order.getType() != StockTransApplicationType.SAMPLE_OUTBOUND.getValue() && order.getType() != StockTransApplicationType.SKU_EXCHANGE_OUTBOUND.getValue()
                && order.getType() != StockTransApplicationType.REAPAIR_OUTBOUND.getValue() && order.getType() != StockTransApplicationType.WELFARE_USE.getValue() && order.getType() != StockTransApplicationType.FIXED_ASSETS_USE.getValue()
                && order.getType() != StockTransApplicationType.SCARP_OUTBOUND.getValue() && order.getType() != StockTransApplicationType.SALES_PROMOTION_USE.getValue() && order.getType() != StockTransApplicationType.LOW_VALUE_CONSUMABLE_USE.getValue()
                && order.getType() != StockTransApplicationType.SERIAL_NUMBER_GROUP_OUTBOUND.getValue() && order.getType() != StockTransApplicationType.SERIAL_NUMBER_OUTBOUND.getValue()
                && order.getType() != StockTransApplicationType.OUTBOUND_PACKAGING_MATERIALS.getValue() && order.getType() != StockTransApplicationType.OUTBOUND_SETTLEMENT.getValue()
                && order.getType() != StockTransApplicationType.OUTBOUND_CONSIGNMENT.getValue()) {
            InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(order.getInvStatusId(), ou1.getParentUnit().getParentUnit().getId());
            sta.setMainStatus(invStatus);
            // 合并明细，只合并同商品、因为库存状态一致
            Map<String, OrderLine> map = new HashMap<String, OrderLine>();
            for (OrderLine line : order.getLines()) {
                log.debug("RMI CODE" + order.getCode() + "---------------" + line.getExpireDate());
                if (map.get(line.getSkuCode() + "_" + line.getExpireDate()) == null) {
                    map.put(line.getSkuCode() + "_" + line.getExpireDate(), line);
                } else {
                    OrderLine newLine = map.get(line.getSkuCode() + "_" + line.getExpireDate());
                    newLine.setQty(newLine.getQty() + line.getQty());
                    map.put(line.getSkuCode() + "_" + line.getExpireDate(), newLine);
                }
            }
            List<OrderLine> lineList = new ArrayList<OrderLine>();
            lineList.addAll(map.values());
            for (OrderLine line1 : lineList) {
                Sku sku = skuDao.getByCode(line1.getSkuCode());
                StaLine sl = new StaLine();
                sl.setSta(sta);
                sl.setSku(sku);
                sl.setOwner(order.getOwner());
                sl.setQuantity(line1.getQty());
                sl.setInvStatus(invStatus);
                sl.setExpireDate(line1.getExpireDate());
                staLineDao.save(sl);
            }
        } else {
            // 合并明细商品和状态
            Map<String, OrderLine> map = new HashMap<String, OrderLine>();
            for (OrderLine line : order.getLines()) {
                log.debug("RMI CODE" + order.getCode() + "---------------" + line.getExpireDate());
                if (map.get(line.getSkuCode() + "-" + line.getInvStatusId() + "-" + line.getExpireDate()) == null) {
                    map.put(line.getSkuCode() + "-" + line.getInvStatusId() + "-" + line.getExpireDate(), line);
                } else {
                    OrderLine newLine = map.get(line.getSkuCode() + "-" + line.getInvStatusId() + "-" + line.getExpireDate());
                    newLine.setQty(newLine.getQty() + line.getQty());
                    map.put(line.getSkuCode() + "-" + line.getInvStatusId() + "-" + line.getExpireDate(), newLine);
                }
            }
            List<OrderLine> lineList = new ArrayList<OrderLine>();
            lineList.addAll(map.values());
            for (OrderLine line1 : lineList) {
                Sku sku = skuDao.getByCode(line1.getSkuCode());
                StaLine sl = new StaLine();
                sl.setSta(sta);
                sl.setSku(sku);
                sl.setOwner(order.getOwner());
                sl.setQuantity(line1.getQty());
                InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(line1.getInvStatusId(), ou1.getParentUnit().getParentUnit().getId());
                sl.setInvStatus(invStatus);
                sl.setExpireDate(line1.getExpireDate());
                staLineDao.save(sl);
            }
        }
        staDao.save(sta);
        staDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        staDao.updateIsSnSta(sta.getId());
        occupyInvntoryForSta(sta);
        if (!StockTransApplicationType.OUTBOUND_PACKAGING_MATERIALS.equals(sta.getType())) {// 包材领用出库由于原接口已经反馈
            wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);// 新增预定义出库库存占用记录中间表，反馈PAC
        }
        /***** mongoDB库存变更添加逻辑 ******************************/
        sta.setStatus(StockTransApplicationStatus.CREATED);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        /***** mongoDB库存变更添加逻辑 ******************************/
    }

    /**
     * VMI转店
     * 
     * @param order
     */
    private String createOwnerTransFerSta(Order order) {
        StockTransApplication sta = new StockTransApplication();
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());
        if (!StringUtils.hasLength(order.getAddOwner())) {
            // 接口数据中附加店铺为空!
            throw new BusinessException(ErrorCode.EI_ADD_OWNER_IS_NULL, new Object[] {order.getAddOwner()});
        }
        BiChannel cs = companyShopDao.getByCode(order.getAddOwner());
        if (cs == null) {
            // 接口数据对应的附加店铺不存在!
            throw new BusinessException(ErrorCode.EI_ADD_OWNER_NOT_EXISTS);
        }
        String owner1 = warehouseShopRefDao.getShopInfoByWarehouseAndShopCode(ou1.getId(), order.getAddOwner(), new SingleColumnRowMapper<String>(String.class));
        if (owner1 == null) {
            // 仓库和附加店铺没有绑定!
            throw new BusinessException(ErrorCode.EI_TARGET_WH_SHOP_NOREF);
        }
        if (order.getOwner().equals(order.getAddOwner())) {
            // 店铺和附加店铺一致!
            throw new BusinessException(ErrorCode.EI_TARGET_ORG_SHOP_ERROR);
        }
        BiChannel shop = companyShopDao.getByCode(order.getOwner());
        if (shop != null) {
            if (!((shop.getVmiCode() != null && cs.getVmiCode() != null) || (shop.getVmiCode() == null && cs.getVmiCode() == null))) {
                throw new BusinessException(ErrorCode.EI_TWO_SHOP_MUST_VMI_SAME);
            }
        } else {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        wareHouseManagerExe.validateBiChannelSupport(null, order.getOwner());
        wareHouseManagerExe.validateBiChannelSupport(null, order.getAddOwner());
        InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(order.getInvStatusId(), ou1.getParentUnit().getParentUnit().getId());
        sta.setRefSlipCode(order.getCode());
        sta.setSlipCode1(order.getSlipCode1());
        sta.setSlipCode2(order.getSlipCode2());
        sta.setSlipCode3(order.getSlipCode3());
        // sta.setMainStatus(invStatus);
        sta.setType(StockTransApplicationType.valueOf(order.getType()));
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setMainWarehouse(ou1);
        sta.setOwner(order.getOwner());
        sta.setAddiOwner(order.getAddOwner());
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setLastModifyTime(new Date());
        sta.setIsNeedOccupied(true);
        sta.setMemo(order.getMemo());
        sta.setIsNotPacsomsOrder(order.getIsNotPacsomsOrder());
        sta.setSystemKey(Constants.PACS);// 转店订单来源与PACS
        if (shop != null && shop.getVmiCode() != null) {
            VmiInterface vf = vmiFactory.getBrandVmi(shop.getVmiCode());
            if (vf != null) {
                sta.setRefSlipCode(vf.generateRtnStaSlipCode(shop.getVmiCode(), sta.getType()));
            }
        }
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        // 合并明细行记录同商品(库存状态一致)
        Map<String, OrderLine> map = new HashMap<String, OrderLine>();
        for (OrderLine line : order.getLines()) {
            if (map.get(line.getSkuCode()) == null) {
                map.put(line.getSkuCode(), line);
            } else {
                OrderLine newLine = map.get(line.getSkuCode());
                newLine.setQty(newLine.getQty() + line.getQty());
                map.put(line.getSkuCode(), newLine);
            }
        }
        staDao.save(sta);
        staDao.flush();
        List<OrderLine> lineList = new ArrayList<OrderLine>();
        lineList.addAll(map.values());
        for (OrderLine line1 : lineList) {
            Sku sku = skuDao.getByCode(line1.getSkuCode());
            invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(line1.getInvStatusId(), ou1.getParentUnit().getParentUnit().getId());
            StaLine sl = new StaLine();
            sl.setSta(sta);
            sl.setSku(sku);
            sl.setOwner(order.getOwner());
            sl.setQuantity(line1.getQty());
            sl.setInvStatus(invStatus);
            staLineDao.save(sl);
        }
        staLineDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        staDao.updateIsSnSta(sta.getId());
        // occupyInvntory
        occupyInvntoryForSta(sta);
        /***** mongoDB库存变更添加逻辑 ******************************/
        // 先扭转为新建，注册监听，再改为配货中，注册监听
        sta.setStatus(StockTransApplicationStatus.CREATED);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        /***** mongoDB库存变更添加逻辑 ******************************/
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        return sta.getRefSlipCode();
    }

    /**
     * 库间移动、同公司调拨、不同公司调拨
     * 
     * @param order
     */
    private void createStaByOrderAuto(Order order) {
        StockTransApplication sta = new StockTransApplication();
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());
        if (order.getAddWhOuId() == null) {
            // 接口数据中附加仓库组织ID为空!
            throw new BusinessException(ErrorCode.EI_ADD_WH_IS_NULL);
        }
        OperationUnit ou2 = operationUnitDao.getByPrimaryKey(order.getAddWhOuId());
        if (ou2 == null) {
            // 接口数据中附加仓库组织ID对应的仓库不存在!
            throw new BusinessException(ErrorCode.EI_ADD_WH_NOT_EXISTS);
        }
        if (ou1.getId().equals(ou2.getId())) {
            // 仓库组织和附加仓库组织一致!
            throw new BusinessException(ErrorCode.EI_SOURCE_TO_WH_SAME);
        }
        if (order.getType() == StockTransApplicationType.TRANSIT_CROSS.getValue() || order.getType() == StockTransApplicationType.SAME_COMPANY_TRANSFER.getValue()) {
            if (!ou1.getParentUnit().getParentUnit().getId().equals(ou2.getParentUnit().getParentUnit().getId())) {
                // 仓库组织和附加仓库组织不在同一公司下
                throw new BusinessException(ErrorCode.EI_ORWH_TARGETWH_NOTSAME_COMPANY);
            }
        }
        BiChannel shop = companyShopDao.getByCode(order.getOwner());
        if (order.getType() == StockTransApplicationType.SAME_COMPANY_TRANSFER.getValue() || order.getType() == StockTransApplicationType.DIFF_COMPANY_TRANSFER.getValue()) {
            if (!StringUtils.hasLength(order.getAddOwner())) {
                // 接口数据中附加店铺为空!
                throw new BusinessException(ErrorCode.EI_ADD_OWNER_IS_NULL, new Object[] {order.getAddOwner()});
            }
            BiChannel cs = companyShopDao.getByCode(order.getAddOwner());
            if (cs == null) {
                // 接口数据对应的附加店铺不存在!
                throw new BusinessException(ErrorCode.EI_ADD_OWNER_NOT_EXISTS);
            }
            String owner1 = warehouseShopRefDao.getShopInfoByWarehouseAndShopCode(ou2.getId(), order.getAddOwner(), new SingleColumnRowMapper<String>(String.class));
            if (owner1 == null) {
                // 附加仓库和附加店铺没有绑定!
                throw new BusinessException(ErrorCode.EI_TARGET_WH_SHOP_NOREF);
            }
            // if (order.getOwner().equals(order.getAddOwner())) {
            // // 店铺和附加店铺一致!
            // throw new BusinessException(ErrorCode.EI_TARGET_ORG_SHOP_ERROR);
            // }
            wareHouseManagerExe.validateBiChannelSupport(null, order.getOwner());
            if (!((shop.getVmiCode() != null && cs.getVmiCode() != null) || (shop.getVmiCode() == null && cs.getVmiCode() == null))) {
                throw new BusinessException(ErrorCode.EI_TWO_SHOP_MUST_VMI_SAME);
            }
            if (order.getType() == StockTransApplicationType.DIFF_COMPANY_TRANSFER.getValue()) {
                if (ou1.getParentUnit().getParentUnit().getId().equals(ou2.getParentUnit().getParentUnit().getId())) {
                    // 仓库和附加仓库在同一公司下!
                    // throw new BusinessException(ErrorCode.EI_ORWH_TARGETWH_SAME_COMPANY);
                }
                if (order.getInvAddStatusId() == null) {
                    // 接口数据中附加库存状态ID为空!
                    throw new BusinessException(ErrorCode.EI_ADD_STATUSID_IS_NULL);
                }
                InventoryStatus invStatus1 = inventoryStatusDao.getByPrimaryKeyAndOuId(order.getInvAddStatusId(), ou2.getParentUnit().getParentUnit().getId());
                if (invStatus1 == null) {
                    // 接口数据中附加库存状态ID对应的库存状态不存在!
                    throw new BusinessException(ErrorCode.EI_ADD_STATUS_NOT_FOUND);
                } else {
                    sta.setAddiStatus(invStatus1);
                }
            }
        }
        InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(order.getInvStatusId(), ou1.getParentUnit().getParentUnit().getId());
        sta.setRefSlipCode(order.getCode());
        sta.setSlipCode1(order.getSlipCode1());
        sta.setSlipCode2(order.getSlipCode2());
        sta.setSlipCode3(order.getSlipCode3());
        sta.setMainStatus(invStatus);
        sta.setType(StockTransApplicationType.valueOf(order.getType()));
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setMainWarehouse(ou1);
        sta.setAddiWarehouse(ou2);
        sta.setOwner(order.getOwner());
        sta.setMemo(order.getMemo());
        if (order.getType() != StockTransApplicationType.TRANSIT_CROSS.getValue()) {
            sta.setAddiOwner(order.getAddOwner());
        } else {
            sta.setAddiOwner(order.getOwner());
        }
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setLastModifyTime(new Date());
        sta.setIsNeedOccupied(true);
        sta.setIsNotPacsomsOrder(order.getIsNotPacsomsOrder());
        if (order.getType() != StockTransApplicationType.TRANSIT_CROSS.getValue()) {
            if (shop != null && shop.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(shop.getVmiCode());
                String slipCode = vf.generateRtnStaSlipCode(shop.getVmiCode(), sta.getType());
                sta.setRefSlipCode(slipCode);
            }
        }
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        staDao.save(sta);
        // 合并明细行记录同商品(库存状态一致)
        Map<String, OrderLine> map = new HashMap<String, OrderLine>();
        for (OrderLine line : order.getLines()) {
            if (map.get(line.getSkuCode()) == null) {
                map.put(line.getSkuCode(), line);
            } else {
                OrderLine newLine = map.get(line.getSkuCode());
                newLine.setQty(newLine.getQty() + line.getQty());
                map.put(line.getSkuCode(), newLine);
            }
        }
        List<OrderLine> lineList = new ArrayList<OrderLine>();
        lineList.addAll(map.values());
        for (OrderLine line1 : lineList) {
            Sku sku = skuDao.getByCode(line1.getSkuCode());
            StaLine sl = new StaLine();
            sl.setSta(sta);
            sl.setSku(sku);
            sl.setOwner(order.getOwner());
            sl.setQuantity(line1.getQty());
            sl.setInvStatus(invStatus);
            staLineDao.save(sl);
        }
        staDao.save(sta);
        staDao.flush();
        if (order.getDeliveryInfo() != null) {
            StaDeliveryInfo di = new StaDeliveryInfo();
            di.setCountry(order.getDeliveryInfo().getCountry() == null ? "中国" : order.getDeliveryInfo().getCountry());
            di.setProvince(order.getDeliveryInfo().getProvince());
            di.setCity(order.getDeliveryInfo().getCity());
            di.setDistrict(order.getDeliveryInfo().getDistrict());
            di.setAddress(order.getDeliveryInfo().getAddress());
            di.setTelephone(order.getDeliveryInfo().getTelephone());
            di.setMobile(order.getDeliveryInfo().getMobile());
            di.setReceiver(order.getDeliveryInfo().getReceiver());
            di.setZipcode(order.getDeliveryInfo().getZipcode());
            di.setIsCod(order.getDeliveryInfo().getIsCod());
            di.setLpCode(order.getDeliveryInfo().getLpCode());
            di.setTrackingNo(order.getDeliveryInfo().getTrackingNo());
            di.setSta(sta);
            di.setId(sta.getId());
            staDeliveryInfoDao.save(di);
            sta.setStaDeliveryInfo(di);
        } else {
            StaDeliveryInfo di = new StaDeliveryInfo();
            di.setSta(sta);
            di.setId(sta.getId());
            staDeliveryInfoDao.save(di);
            sta.setStaDeliveryInfo(di);
        }
        staLineDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        staDao.updateIsSnSta(sta.getId());
        occupyInvntoryForSta(sta);
        /***** mongoDB库存变更添加逻辑 ******************************/
        sta.setStatus(StockTransApplicationStatus.CREATED);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        /***** mongoDB库存变更添加逻辑 ******************************/

    }

    /**
     * 库存占用生成STV
     * 
     * @param sta
     */
    private void occupyInvntoryForSta(StockTransApplication sta) {
        StockTransApplication sta1 = staDao.getByPrimaryKey(sta.getId());
        if (!StockTransApplicationStatus.CREATED.equals(sta1.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta1.getCode()});
        }
        TransactionType t = null;
        t = transactionTypeDao.findByCode(TransactionType.returnTypeOutbound(sta1.getType()));
        if (t != null) {
            wareHouseManager.occupyInventoryCommonMethod(sta1.getId());
            sta1.setStatus(StockTransApplicationStatus.OCCUPIED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta1.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta1.getMainWarehouse() == null ? null : sta1.getMainWarehouse() == null ? null : sta1
                    .getMainWarehouse().getId());
            sta1.setLastModifyTime(new Date());
            int tdType = TransactionDirection.OUTBOUND.getValue();
            String code = stvDao.getCode(sta1.getId(), new SingleColumnRowMapper<String>());
            stvDao.createStv(code, sta1.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), null, tdType, sta.getMainWarehouse().getId(), t.getId());
            if ("1".equals(sta.getIsPf())) {
                stvLineDao.createForCrossByStaId2(sta1.getId());
            } else {
                stvLineDao.createForCrossByStaId(sta1.getId());
            }

        } else {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta1.getType()});
        }

    }

    @Override
    public void confirmUpdateStatus(String code) {
        InventoryCheck invcheck = inventoryCheckDao.getBySlipCode(code);
        if (invcheck == null) {
            throw new BusinessException(ErrorCode.EI_NOT_EXISTS_INV_CHECK);
        }
        inventoryCheckDao.updateStatusByCode(invcheck.getCode());
    }

    /**
     * 根据相关单据号取消作业单
     */
    public boolean cancelOrder(String slipCode) {
        List<StockTransApplication> list = staDao.findBySlipCode(slipCode);
        for (StockTransApplication sta : list) {
            switch (sta.getType()) {
                case INBOUND_RETURN_REQUEST:
                    return false;
                case OUTBOUND_RETURN_REQUEST:
                    return false;
                case OUTBOUND_SALES:
                    return false;
                case OUT_SALES_ORDER_OUTBOUND_SALES:
                    return false;
                case VMI_INBOUND_CONSIGNMENT:
                    return false;
                case VMI_ADJUSTMENT_INBOUND:
                    return false;
                case VMI_ADJUSTMENT_OUTBOUND:
                    return false;
                case VMI_RETURN:
                    return false;
                case VMI_TRANSFER_RETURN:
                    return false;
                default:
                    break;
            }

            switch (sta.getStatus()) {
                case CANCEL_UNDO:
                    // 取消未处理
                    break;
                case CANCELED:
                    // 取消
                    break;
                case INTRANSIT:
                    // 已转出，取消失败
                    return false;
                case PARTLY_RETURNED:
                    // 部分入库，取消失败
                    return false;
                case FINISHED:
                    // 完成，取消失败
                    return false;
                default:
                    // 取消作业单
                    sta.setStatus(StockTransApplicationStatus.CANCELED);
                    // 订单状态与账号关联
                    whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse() == null ? null : sta
                            .getMainWarehouse().getId());
                    sta.setLastModifyTime(new Date());
                    inventoryDao.releaseInventoryByStaId(sta.getId());
                    sta.setCancelTime(new Date());
                    sta.setSysUpdateTime(new Date());
                    staDao.save(sta);
                    staDao.flush();
                    /***** mongoDB库存变更添加逻辑 ******************************/
                    try {
                        eventObserver.onEvent(new TransactionalEvent(sta));
                    } catch (BusinessException e) {
                        throw e;
                    }
                    break;
            }
        }
        return true;
    }

    public boolean cancelBatchCode(String slipCode) {
        if (StringUtil.isEmpty(slipCode)) {
            return false;
        }
        StockTransVoucher stv = stvDao.findByStvCode(slipCode);
        if (stv == null || !StockTransVoucherType.INBOUND_EXPENSE.equals(stv.getType())) return false;
        stv.setStatus(StockTransVoucherStatus.CANCELED);
        stv.setFinishTime(new Date());
        stv.setLastModifyTime(new Date());
        stvDao.save(stv);
        return true;
    }

    /**
     * 盘点审核不通过
     */
    public boolean rejectInvCheck(String slipCode) {
        InventoryCheck ic = inventoryCheckDao.getBySlipCode(slipCode);
        if (InventoryCheckStatus.CHECKWHMANAGER.equals(ic.getStatus())) {
            ic.setStatus(InventoryCheckStatus.CHECKWHINVENTORY);
            List<StockTransApplication> staList = staDao.findBySlipCode(ic.getCode());
            for (StockTransApplication sta : staList) {
                sta.setStatus(StockTransApplicationStatus.CREATED);
                inventoryDao.releaseInventoryByStaId(sta.getId());
            }
            // 有盘亏数据 方向生成通知IM数据t_wh_im_occupied_release
            wmsIMOccupiedAndReleaseDao.insertWmsOccupiedAndReleaseByInvCheckLossCancel(ic.getId(), ic.getCode());
            wmsIMOccupiedAndReleaseDao.flush();
            inventoryDao.releaseInventoryByOpcOcde(ic.getCode());
            return true;
        } else {
            return false;
        }
    }

    private void createQueueSta(Order order) {
        boolean isNotCheckInv = false;// 标示是否过仓不校验库存，该标示在店铺上
        QueueSta qsta = new QueueSta();
        qsta.setOrdercode(order.getCode());
        qsta.setSlipcode1(order.getSlipCode1());
        qsta.setSlipcode2(order.getSlipCode2());
        qsta.setSlipCode3(order.getSlipCode3());
        qsta.setIscreatedlocked(order.getIsLocked());
        qsta.setOwner(order.getOwner());
        // 判断店铺是否校验库存
        BiChannel bcl = biChannelDao.getByCode(order.getOwner());
        isNotCheckInv = bcl.getIsNotCheckInventory() == null ? false : bcl.getIsNotCheckInventory();
        qsta.setAddOwner(order.getAddOwner());
        qsta.setType(order.getType());
        qsta.setInvaddstatusid(order.getInvAddStatusId());
        qsta.setChannelList(order.getChannelList());
        qsta.setCustomerId(order.getCustomerId());
        qsta.setMainWhOuCode(order.getMainWhOuCode());
        qsta.setArriveTime(order.getArriveTime());
        qsta.setArriveTimeType(order.getArriveTimeType());
        qsta.setAddWhOuCode(order.getAddWhOuCode());
        qsta.setToLocation(order.getToLocation());
        qsta.setIsPrintPrice(order.getIsPrintPrice());
        qsta.setExtMemo2(order.getExtMemo2());
        if (StringUtils.hasText(order.getAddOwner())) {
            qsta.setInvstatusid(1l);
            String[] channelList = order.getChannelList().split(";");
            if (channelList.length > 1) {
                throw new BusinessException(ErrorCode.ERROR_NOT_STATUS);
            }
        } else {
            qsta.setInvstatusid(order.getInvStatusId());
        }
        if (Constants.COACH_OWNER1.equals(order.getOwner()) || Constants.COACH_OWNER2.equals(order.getOwner())) {
            qsta.setIsspecialpackaging(true);
        } else {
            qsta.setIsspecialpackaging(order.getIsSpecialPackaging());
        }
        if (order.getSpecialType() != null && order.getSpecialType() == 1) {
            qsta.setSpecialType(SpecialSkuType.NORMAL);
        }
        qsta.setTransferfee(order.getTransferFee());
        qsta.setTotalactual(order.getTotalActual());
        qsta.setMkPosCode(order.getMkPosCode());
        qsta.setIsInvoice(order.getIsInvoice());
        qsta.setOrdertransferfree(order.getOrderTranserFree());
        qsta.setOrdertaotalactual(order.getOrderTotalActual());
        qsta.setOrdertotalbfdiscount(order.getOrderTotalBfDiscount());
        qsta.setOrdercreatetime(order.getOrderCreateTime());
        qsta.setCreatetime(new Date());
        qsta.setIsInvoice(order.getIsInvoice());
        qsta.setActivitysource(order.getActivitySource());
        qsta.setCodAmount(order.getCodAmount());
        // 预计发货时间测试
        qsta.setPlanArriveTime(order.getPlanArriveTime());
        qsta.setPlanOutboundTime(order.getPlanOutboundTime());
        qsta.setIsTransUpgrade(order.getIsTransUpgrade());
        qsta.setOrderSourcePlatform(order.getOrderSourcePlatform());
        qsta.setStorecode(order.getStorecode());
        qsta.setErrorcount(0);
        qsta.setIsPreSale(order.getIsPreSale());// 是否是预售订单
        qsta.setIsOnlineInvoice(order.getIsOnlineInvoice());
        qsta.setIsMacaoOrder(order.getIsMacaoOrder());// 是否澳门件
        if (order.getType() == 1) {
            qsta.setType(21);
        } else if (order.getType() == 2) {
            qsta.setType(41);
            for (OrderLine line1 : order.getLines()) {
                if (line1.getDirection() == 2) {
                    qsta.setType(42);
                }
            }

        }
        if (qsta.getType() == 42) {
            qsta.setMainwhouid(order.getAddWhOuId());
            qsta.setAddwhouid(order.getMainWhOuId());
            if ("1".equals(IS_PACS_INVENTORY)) {
                OperationUnit ou1 = operationUnitDao.getByCode(order.getMainWhOuCode());
                Warehouse warehouse = warehouseDao.getByOuId(ou1.getId());
                if (warehouse != null && warehouse.getVmiSource() != null && warehouse.getVmiSource().equals("af")) {

                } else {
                    qsta.setMainWhOuCode(order.getAddWhOuCode());
                    qsta.setAddWhOuCode(order.getMainWhOuCode());
                }

            }
        } else {
            qsta.setMainwhouid(order.getMainWhOuId());
            qsta.setAddwhouid(order.getAddWhOuId());
        }
        qsta.setMemo(order.getMemo());
        queueStaDao.save(qsta);
        // 特殊类型中间表 NIKE 礼品卡
        if(order.getOrderSpecialExecuteList()!=null&&order.getOrderSpecialExecuteList().size()>0){
            for(OrderSpecialExecute orderSpecialExecute:order.getOrderSpecialExecuteList()){
                WhOrderSpecialExecute excute = new WhOrderSpecialExecute();
                excute.setQueueSta(qsta);
                excute.setType(StaSpecialExecuteType.valueOf(orderSpecialExecute.getType()));
                excute.setMemo(orderSpecialExecute.getMemo());
                whOrderSpecialExecuteDao.save(excute);
            }
        }else{
            if (order.getOrderSpecialExecute() != null) {
                WhOrderSpecialExecute excute = new WhOrderSpecialExecute();
                excute.setQueueSta(qsta);
                excute.setType(StaSpecialExecuteType.valueOf(order.getOrderSpecialExecute().getType()));
                excute.setMemo((order.getOrderSpecialExecute().getMemo()));
                whOrderSpecialExecuteDao.save(excute);
            }
        }
        
        if (order.getDeliveryInfo() != null) {
            QueueStaDeliveryInfo di = new QueueStaDeliveryInfo();
            di.setId(qsta.getId());
            di.setCountry(order.getDeliveryInfo().getCountry() == null ? "中国" : order.getDeliveryInfo().getCountry());
            di.setProvince(order.getDeliveryInfo().getProvince());
            di.setCity(order.getDeliveryInfo().getCity());
            di.setDistrict(order.getDeliveryInfo().getDistrict());
            di.setIsCodPos(order.getDeliveryInfo().getIsCodPos());
            di.setConvenienceStore(order.getDeliveryInfo().getConvenienceStore());
            di.setAddress(order.getDeliveryInfo().getAddress());
            di.setTelephone(order.getDeliveryInfo().getTelephone());
            di.setMobile(order.getDeliveryInfo().getMobile());
            di.setReceiver(order.getDeliveryInfo().getReceiver());
            di.setZipcode(order.getDeliveryInfo().getZipcode());
            di.setIscode(order.getDeliveryInfo().getIsCod());
            di.setLpcode(order.getDeliveryInfo().getLpCode());
            di.setSender(order.getDeliveryInfo().getSender());
            di.setAddressEn(order.getDeliveryInfo().getAddressEn());
            di.setCityEn(order.getDeliveryInfo().getCityEn());
            di.setCountryEn(order.getDeliveryInfo().getCountryEn());
            di.setDistrictEn(order.getDeliveryInfo().getDistrictEn());
            di.setProvinceEn(order.getDeliveryInfo().getProvinceEn());
            di.setReceiverEn(order.getDeliveryInfo().getReceiverEn());
            di.setRemarkEn(order.getDeliveryInfo().getRemarkEn());
            di.setSendLpcode(order.getDeliveryInfo().getSendLpcode());
            di.setSendMobile(order.getDeliveryInfo().getSendMobile());
            di.setSendTransNo(order.getDeliveryInfo().getSendTransNo());
            di.setTrackingno(order.getDeliveryInfo().getTrackingNo());
            di.setRemark(order.getDeliveryInfo().getRemark());
            di.setTransmemo(order.getDeliveryInfo().getTransMemo());
            // 未填
            di.setInsuranceAmount(order.getDeliveryInfo().getInsuranceAmount());
            if (order.getDeliveryInfo().getTransType() == null) {
                di.setTranstype(1);
            } else {
                di.setTranstype(order.getDeliveryInfo().getTransType());
            }
            if (order.getDeliveryInfo().getTransTimeType() == null) {
                di.setTranstimetype(1);
            } else {
                di.setTranstimetype(order.getDeliveryInfo().getTransTimeType());
            }
            di.setTransmemo(order.getDeliveryInfo().getTransMemo());
            di.setOrderusercode(order.getDeliveryInfo().getOrderUserCode());
            di.setOrderusermail(order.getDeliveryInfo().getOrderUserMail());
            queueStaDeliveryInfoDao.save(di);
            queueStaDeliveryInfoDao.flush();
        }
        Warehouse warehouse = warehouseDao.getByOuId(order.getMainWhOuId());
        boolean isAllGiftLine = true;
        for (OrderLine l : order.getLines()) {
            if (QueueStaLineType.LINE_TYPE_TURE.getValue() == l.getLineType()) {
                isAllGiftLine = false;
                break;
            }

        }
        if (isAllGiftLine) {
            log.warn("line type is all gift, order code is : " + order.getCode());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        if (StringUtils.hasText(warehouse.getVmiSource())) {
            if (qsta.getIsspecialpackaging()) {
                for (OrderLine line1 : order.getLines()) {
                    QueueStaLine sl = new QueueStaLine();
                    sl.setTotalactual(line1.getTotalActual());
                    sl.setOrdertotalactual(line1.getOtderTotalActual());
                    sl.setOrdertotalbfdiscount(line1.getOrderTotalBfDiscount());
                    sl.setQueueSta(qsta);
                    sl.setSkucode(line1.getSkuCode());
                    sl.setOwner(line1.getOwner());
                    sl.setQty(line1.getQty());
                    sl.setListprice(line1.getListPrice());
                    sl.setUnitprice(line1.getUnitPrice());
                    sl.setDirection(line1.getDirection());
                    sl.setInvstatusid(line1.getInvStatusId());
                    ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("channel", "isMerge");
                    if (!chooseOption.getOptionValue().equals(order.getOwner())) {
                        sl.setSkuName(line1.getSkuName() == null ? null : (line1.getSkuName().length() > 50 ? (line1.getSkuName().substring(0, 50)) : line1.getSkuName()));
                    } else {
                        sl.setSkuName(line1.getSkuName() == null ? null : line1.getSkuName());
                    }

                    sl.setActivitysource(line1.getActivitySource());
                    sl.setLineType(QueueStaLineType.valueOf(line1.getLineType()));
                    sl.setLineStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                    qsta.setSkuqty((qsta.getSkuqty() == null ? 0 : qsta.getSkuqty()) + sl.getQty());
                    queueStaLineDao.save(sl);
                    if (line1.getGifts() != null) {
                        for (OrderLineGift lineGift : line1.getGifts()) {
                            QueueGiftLine giftLine = new QueueGiftLine();
                            giftLine.setMemo(lineGift.getMemo());
                            giftLine.setQueueStaLine(sl);
                            giftLine.setType(lineGift.getType());
                            queueGiftLineDao.save(giftLine);

                        }
                    }
                }
            } else {
                // 拆分合并
                merge(order, qsta);
            }
        } else {
            // 拆分合并
            merge(order, qsta);
        }
        if (order.getIsInvoice()) {
            if (order.getInvoiceList() == null || order.getInvoiceList().size() == 0) {
                String error = "order.InvoiceList is null!order:" + order.getCode();
                log.debug(error);
                throw new BusinessException(error);
            }
            try {
                for (Invoice invoice : order.getInvoiceList()) {
                    QueueStaInvoice queueStaInvoice = new QueueStaInvoice();
                    queueStaInvoice.setqStaId(qsta);
                    queueStaInvoice.setInvoiceDate(invoice.getInvoiceDate());
                    queueStaInvoice.setPayer(invoice.getPayer());
                    queueStaInvoice.setItem(invoice.getItem());
                    queueStaInvoice.setQty(invoice.getQty());
                    queueStaInvoice.setUnitPrice(invoice.getUnitPrice());
                    queueStaInvoice.setAmt(invoice.getAmt());
                    queueStaInvoice.setMemo(invoice.getMemo());
                    queueStaInvoice.setPayee(invoice.getPayee());
                    queueStaInvoice.setDrawer(invoice.getDrawer());
                    queueStaInvoice.setAddress(invoice.getAddress());
                    queueStaInvoice.setIdentificationNumber(invoice.getIdentificationNumber());
                    queueStaInvoice.setTelephone(invoice.getTelephone());
                    queueStaInvoiceDao.save(queueStaInvoice);
                    for (InvoiceLine invoiceLine : invoice.getDetials()) {
                        QueueStaInvoiceLine staInvoiceLine = new QueueStaInvoiceLine();
                        staInvoiceLine.setAmt(invoiceLine.getAmt());
                        staInvoiceLine.setItem(invoiceLine.getIteam());
                        staInvoiceLine.setQty(invoiceLine.getQty());
                        staInvoiceLine.setQueueStaInvoice(queueStaInvoice);
                        staInvoiceLine.setUnitPrice(invoiceLine.getUnitPrice());
                        queueStaInvoiceLineDao.save(staInvoiceLine);

                    }
                }
            } catch (Exception e) {
                String error = "order.InvoiceList error!order:" + order.getCode();
                log.error(error);
                log.error(e.getMessage());
                throw new BusinessException(error);
            }
        }
        // 保存费用列表
        try {
            if (order.getOrderPays() == null) {} else {
                for (OrderStaPayment pays : order.getOrderPays()) {
                    QueueStaPayment queueStaPayment = new QueueStaPayment();
                    queueStaPayment.setAmt(pays.getAmt());
                    queueStaPayment.setMemo(pays.getMemo());
                    queueStaPayment.setType(PaymentType.valueOf(pays.getType()));
                    queueStaPayment.setqStaId(qsta);
                    queueStaPaymentDao.save(queueStaPayment);

                }
            }
        } catch (Exception e) {
            String error = "order.OrderPays error!order:" + order.getCode();
            log.error(error);
            log.error(e.getMessage());
            throw new BusinessException(error);
        }
        if (isNotCheckInv && qsta.getType() != 41) {
            qsta.setBeginflag(5);
            qsta.setCanflag(true);
        }
        queueStaDao.save(qsta);
        queueStaDao.flush();
        if (isNotCheckInv && qsta.getType() != 41) {
            List<QueueStaLine> staLineList = queueStaLineDao.findByStaId(qsta.getId());
            for (QueueStaLine queueStaLine : staLineList) {
                QueueStaLineOwner queueStaLineOwner = new QueueStaLineOwner();
                queueStaLineOwner.setOwner(queueStaLine.getOwner() == null ? qsta.getOwner() : queueStaLine.getOwner());
                queueStaLineOwner.setQty(queueStaLine.getQty());
                queueStaLineOwner.setSkuCode(queueStaLine.getSkucode());
                queueStaLineOwner.setQueueStaLine(queueStaLine);
                queueStaLineOwnerDao.save(queueStaLineOwner);
            }
        }



    }

    public void merge(Order order, QueueSta qsta) {
        List<OrderLine> lineList = null;
        Boolean isCoachOwner = Constants.COACH_OWNER1.equals(order.getOwner()) || Constants.COACH_OWNER2.equals(order.getOwner());
        Map<String, OrderLine> map = new HashMap<String, OrderLine>();
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("channel", "isMerge");
        if (!chooseOption.getOptionValue().equals(order.getOwner())) {
            for (OrderLine orderLine : order.getLines()) {
                if (orderLine.getGifts() == null || orderLine.getGifts().size() == 0) {
                    if (orderLine.getDirection() == TransactionDirection.OUTBOUND.getValue()) {
                        if (isCoachOwner) {
                            Integer warrantyCardType = skuDao.findSkuWarrantyCardType(orderLine.getSkuCode(), new SingleColumnRowMapper<Integer>(Integer.class));
                            if (warrantyCardType != null && SkuWarrantyCardType.NO_CHECK.getValue() == warrantyCardType) {
                                throw new BusinessException(ErrorCode.GIFT_LINE_IS_NULL, new Object[] {orderLine.getSkuCode(), 0});
                            }
                        }
                        String key = orderLine.getSkuCode() + "-" + orderLine.getInvStatusId() + "-" + orderLine.getDirection();
                        if (!map.containsKey(key)) {
                            map.put(key, orderLine);
                        } else {
                            OrderLine newLine = map.get(key);
                            newLine.setQty(newLine.getQty() + orderLine.getQty());
                            newLine.setTotalActual(newLine.getTotalActual() == null ? new BigDecimal(0) : newLine.getTotalActual());
                            newLine.setOtderTotalActual(newLine.getOtderTotalActual() == null ? new BigDecimal(0) : newLine.getOtderTotalActual());
                            newLine.setOrderTotalBfDiscount(newLine.getOrderTotalBfDiscount() == null ? new BigDecimal(0) : newLine.getOrderTotalBfDiscount());
                            newLine.setTotalActual(newLine.getTotalActual().add(orderLine.getTotalActual() == null ? new BigDecimal(0) : orderLine.getTotalActual()));
                            newLine.setOtderTotalActual(newLine.getOtderTotalActual().add(orderLine.getOtderTotalActual() == null ? new BigDecimal(0) : orderLine.getOtderTotalActual()));
                            newLine.setOrderTotalBfDiscount(newLine.getOrderTotalBfDiscount().add(orderLine.getOrderTotalBfDiscount() == null ? new BigDecimal(0) : orderLine.getOrderTotalBfDiscount()));
                        }
                    } else if (orderLine.getDirection() == TransactionDirection.INBOUND.getValue()) {
                        String key = orderLine.getSkuCode() + "-" + orderLine.getInvStatusId() + "-" + orderLine.getDirection();
                        if (!map.containsKey(key)) {
                            map.put(key, orderLine);
                        } else {
                            OrderLine newLine = map.get(key);
                            newLine.setQty(newLine.getQty() + orderLine.getQty());
                            newLine.setTotalActual(newLine.getTotalActual() == null ? new BigDecimal(0) : newLine.getTotalActual());
                            newLine.setOtderTotalActual(newLine.getOtderTotalActual() == null ? new BigDecimal(0) : newLine.getOtderTotalActual());
                            newLine.setOrderTotalBfDiscount(newLine.getOrderTotalBfDiscount() == null ? new BigDecimal(0) : newLine.getOrderTotalBfDiscount());
                            newLine.setTotalActual(newLine.getTotalActual().add(orderLine.getTotalActual() == null ? new BigDecimal(0) : orderLine.getTotalActual()));
                            newLine.setOtderTotalActual(newLine.getOtderTotalActual().add(orderLine.getOtderTotalActual() == null ? new BigDecimal(0) : orderLine.getOtderTotalActual()));
                            newLine.setOrderTotalBfDiscount(newLine.getOrderTotalBfDiscount().add(orderLine.getOrderTotalBfDiscount() == null ? new BigDecimal(0) : orderLine.getOrderTotalBfDiscount()));
                        }
                    }
                }
            }
            lineList = new ArrayList<OrderLine>();
            lineList.addAll(map.values());
        } else {
            lineList = new ArrayList<OrderLine>();
            for (OrderLine orderLine : order.getLines()) {
                if (orderLine.getDirection() == TransactionDirection.OUTBOUND.getValue()) {
                    orderLine.setQty(orderLine.getQty());
                    orderLine.setTotalActual(orderLine.getTotalActual() == null ? new BigDecimal(0) : orderLine.getTotalActual());
                    orderLine.setOtderTotalActual(orderLine.getOtderTotalActual() == null ? new BigDecimal(0) : orderLine.getOtderTotalActual());
                    orderLine.setOrderTotalBfDiscount(orderLine.getOrderTotalBfDiscount() == null ? new BigDecimal(0) : orderLine.getOrderTotalBfDiscount());
                    lineList.add(orderLine);
                } else if (orderLine.getDirection() == TransactionDirection.INBOUND.getValue()) {
                    orderLine.setQty(orderLine.getQty());
                    orderLine.setTotalActual(orderLine.getTotalActual() == null ? new BigDecimal(0) : orderLine.getTotalActual());
                    orderLine.setOtderTotalActual(orderLine.getOtderTotalActual() == null ? new BigDecimal(0) : orderLine.getOtderTotalActual());
                    orderLine.setOrderTotalBfDiscount(orderLine.getOrderTotalBfDiscount() == null ? new BigDecimal(0) : orderLine.getOrderTotalBfDiscount());
                    lineList.add(orderLine);
                }

            }

        }

        for (OrderLine line1 : lineList) {
            QueueStaLine sl = new QueueStaLine();
            sl.setQueueSta(qsta);
            sl.setSkucode(line1.getSkuCode());
            sl.setOwner(line1.getOwner());
            sl.setQty(line1.getQty());
            sl.setOrdertotalactual(line1.getOtderTotalActual());
            sl.setTotalactual(line1.getTotalActual());
            sl.setListprice(line1.getListPrice());
            if (!chooseOption.getOptionValue().equals(order.getOwner())) {
                sl.setSkuName(line1.getSkuName() == null ? null : (line1.getSkuName().length() > 50 ? (line1.getSkuName().substring(0, 50)) : line1.getSkuName()));
            } else {
                sl.setSkuName(line1.getSkuName() == null ? null : line1.getSkuName());
            }
            sl.setUnitprice(line1.getUnitPrice());
            sl.setDirection(line1.getDirection());
            sl.setInvstatusid(line1.getInvStatusId());
            sl.setActivitysource(line1.getActivitySource());
            sl.setOrdertotalbfdiscount(line1.getOrderTotalBfDiscount());
            sl.setLineType(QueueStaLineType.valueOf(line1.getLineType()));
            sl.setLineStatus(QueueStaLineStatus.LINE_STATUS_TURE);
            qsta.setSkuqty((qsta.getSkuqty() == null ? 0 : qsta.getSkuqty()) + sl.getQty());
            queueStaLineDao.save(sl);
        }
        for (OrderLine line1 : order.getLines()) {
            QueueStaLine sl;
            if (line1.getGifts() != null && line1.getGifts().size() > 0) {
                if (line1.getQty() >= 1) {
                    for (int i = 0; i < line1.getQty(); i++) {
                        sl = new QueueStaLine();
                        BigDecimal qty = new BigDecimal(line1.getQty() - i);
                        line1.setTotalActual(line1.getTotalActual() == null ? new BigDecimal(0) : line1.getTotalActual());
                        BigDecimal total = line1.getTotalActual().divide(qty, BigDecimal.ROUND_HALF_UP);
                        line1.setTotalActual(line1.getTotalActual().subtract(total));
                        sl.setTotalactual(total);
                        line1.setOtderTotalActual(line1.getOtderTotalActual() == null ? new BigDecimal(0) : line1.getOtderTotalActual());
                        BigDecimal orderTotal = line1.getOtderTotalActual().divide(qty, BigDecimal.ROUND_HALF_UP);
                        line1.setOtderTotalActual(line1.getOtderTotalActual().subtract(orderTotal));
                        sl.setOrdertotalactual(orderTotal);
                        line1.setOrderTotalBfDiscount(line1.getOrderTotalBfDiscount() == null ? new BigDecimal(0) : line1.getOrderTotalBfDiscount());
                        BigDecimal orderBf = line1.getOrderTotalBfDiscount().divide(qty, BigDecimal.ROUND_HALF_UP);
                        line1.setOrderTotalBfDiscount(line1.getOrderTotalBfDiscount().subtract(orderBf));
                        sl.setOrdertotalbfdiscount(orderBf);
                        sl.setQueueSta(qsta);
                        sl.setSkucode(line1.getSkuCode());
                        sl.setLineType(QueueStaLineType.valueOf(line1.getLineType()));
                        sl.setLineStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                        sl.setSkuName(line1.getSkuName() == null ? null : (line1.getSkuName().length() > 50 ? (line1.getSkuName().substring(0, 50)) : line1.getSkuName()));
                        sl.setOwner(line1.getOwner());
                        sl.setQty(1L);
                        sl.setListprice(line1.getListPrice());
                        sl.setUnitprice(line1.getUnitPrice());
                        sl.setDirection(line1.getDirection());
                        sl.setInvstatusid(line1.getInvStatusId());
                        sl.setActivitysource(line1.getActivitySource());
                        qsta.setSkuqty((qsta.getSkuqty() == null ? 0 : qsta.getSkuqty()) + sl.getQty());
                        queueStaLineDao.save(sl);
                        // 判断商品是否须要保修卡信息
                        boolean isCoachSku = false;
                        // 判断是否 存在保修卡信息
                        boolean isSkuWarrantyCardType = false;
                        if (isCoachOwner) {
                            Integer warrantyCardType = skuDao.findSkuWarrantyCardType(line1.getSkuCode(), new SingleColumnRowMapper<Integer>(Integer.class));
                            if (warrantyCardType != null && SkuWarrantyCardType.NO_CHECK.getValue() == warrantyCardType) {
                                isCoachSku = true;
                            }
                        }
                        for (OrderLineGift lineGift : line1.getGifts()) {
                            if (isCoachSku && GiftType.COACH_CARD.getValue() == lineGift.getType()) {
                                try {
                                    int month = Integer.valueOf(lineGift.getMemo().trim());
                                    if (month < 1) {
                                        throw new Exception();
                                    }
                                } catch (Exception e) {
                                    throw new BusinessException(ErrorCode.GIFT_LINE_IS_NULL, new Object[] {line1.getSkuCode(), 1});
                                }
                                isSkuWarrantyCardType = true;
                            }
                            QueueGiftLine giftLine = new QueueGiftLine();
                            giftLine.setMemo(lineGift.getMemo());
                            giftLine.setQueueStaLine(sl);
                            giftLine.setType(lineGift.getType());
                            queueGiftLineDao.save(giftLine);
                        }
                        // 需要保修卡信息, 但是又不存在保修卡信息 抛出错误
                        if (isCoachSku && !isSkuWarrantyCardType) {
                            throw new BusinessException(ErrorCode.GIFT_LINE_IS_NULL, new Object[] {line1.getSkuCode(), 2});
                        }
                    }
                }
            }
        }
    }

    @Override
    public OrderResult orderSkuBatchCode(Order order) {
        if (StringUtil.isEmpty(order.getCode())) {
            throw new BusinessException(ErrorCode.EI_SLIP_CODE_IS_NULL);
        }
        List<StockTransApplication> staList = staDao.getStaListBySlipCode1(order.getSlipCode1());
        StockTransApplication sta = null;
        for (StockTransApplication tempSta : staList) {
            if (tempSta.getType().getValue() == order.getType()) {
                sta = tempSta;
            }
        }
        if (sta == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_STA);
        }
        // 判断是否存在费用化单号 如果已经存在直接返回
        StockTransVoucher stv = stvDao.findByStvCode(order.getCode());
        if (stv != null) {
            return createOrderResultByStv(sta, stv);
        }
        if (!StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.RMI_SERVICE_STA_UNFINISHED, new Object[] {sta.getCode(), sta.getSlipCode1()});
        }
        if (order.getLines() == null || order.getLines().size() == 0) {
            throw new BusinessException(ErrorCode.EI_DETAIL_LINE_IS_NULL);
        }
        List<StvLineCommand> stvLines = stvLineDao.findStvLineBatchCodeAndInboundTime(null, sta.getSlipCode1(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        List<OrderLine> olList = order.getLines();
        BusinessException errorHand = new BusinessException();
        BusinessException errorEnd = errorHand;
        List<StvLine> stvLineList = new ArrayList<StvLine>();
        Map<String, StvLine> slMap = new HashMap<String, StvLine>();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        for (int j = 0; j < olList.size(); j++) {
            OrderLine ol = olList.get(j);
            if (ol.getQty() == null || ol.getQty() == 0) {
                errorEnd.setLinkedException(new BusinessException(ErrorCode.RMI_SERVICE_SKU_QTY_ERROR, new Object[] {ol.getSkuCode(), ol.getQty()}));
                errorEnd = errorEnd.getLinkedException();
                continue;
            }
            String key1 = ol.getSkuCode();
            Sku sku = skuDao.getByCode(ol.getSkuCode());
            // 是否为保质期商品
            if (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                key1 += "_" + formatDate.format(wareHouseManagerProxy.getFormatExpireDate(ol.getProductionDate(), ol.getExpireDate(), sku));
            }
            Long olQty = ol.getQty();
            for (int i = 0; i < stvLines.size(); i++) {
                StvLineCommand com = stvLines.get(i);
                // 获取对应商品
                String key2 = com.getSkuCode() + (StringUtil.isEmpty(com.getStrExpireDate()) ? "" : ("_" + com.getStrExpireDate()));
                if (key2.equals(key1)) {
                    InventoryStatus is = new InventoryStatus();
                    is.setId(com.getIntInvstatus());
                    Long qty = 0L;
                    if (com.getQuantity() > ol.getQty()) {
                        qty = ol.getQty();
                        com.setQuantity(com.getQuantity() - ol.getQty());
                        ol.setQty(0L);
                    } else {
                        qty = com.getQuantity();
                        ol.setQty(ol.getQty() - com.getQuantity());
                        stvLines.remove(i--);
                    }
                    String key =
                            (StringUtil.isEmpty(com.getStrExpireDate()) ? "" : com.getStrExpireDate()) + "_" + (com.getValidDate() == null ? "" : com.getValidDate()) + "_" + (StringUtil.isEmpty(com.getStrPoductionDate()) ? "" : com.getStrPoductionDate())
                                    + "_" + com.getBatchCode() + "_" + is.getId().toString() + "_" + sku.getId().toString() + "_" + com.getOwner();
                    if (slMap.containsKey(key)) {
                        StvLine temp = slMap.get(key);
                        temp.setQuantity(temp.getQuantity() + qty);
                    } else {
                        StvLine l = createStvLine(com.getBatchCode(), TransactionDirection.INBOUND, com.getInBoundTime(), com.getProductionDate(), com.getValidDate(), com.getExpireDate(), is, sku, com.getOwner(), qty);
                        slMap.put(key, l);
                        stvLineList.add(l);
                    }
                    if (ol.getQty() == 0) break;
                }
            }
            // 此处证明 费用化数量不够
            if (ol.getQty() > 0) {
                errorEnd.setLinkedException(new BusinessException(ErrorCode.RMI_SERVICE_SKU_THE_COST_OF, new Object[] {ol.getSkuCode(), olQty}));
                errorEnd = errorEnd.getLinkedException();
            }
        }
        if (errorHand.getLinkedException() != null) {
            throw errorHand;
        }
        stv = new StockTransVoucher();
        stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        stv.setCode(order.getCode());
        stv.setCreateTime(new Date());
        stv.setLastModifyTime(new Date());
        stv.setDirection(TransactionDirection.INBOUND);
        stv.setOwner(sta.getOwner());
        stv.setSta(sta);
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setType(StockTransVoucherType.INBOUND_EXPENSE);
        stv.setWarehouse(sta.getMainWarehouse());
        stv.setTransactionType(transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_EXPENSE_INBOUND));
        stvDao.save(stv);
        Long skuQty = 0l;
        for (StvLine line : stvLineList) {
            line.setWarehouse(stv.getWarehouse());
            line.setTransactionType(stv.getTransactionType());
            line.setStv(stv);
            stvLineDao.save(line);
            skuQty += line.getQuantity();
        }
        stv.setSkuQty(skuQty);
        stv.setStatus(StockTransVoucherStatus.FINISHED);
        stv.setFinishTime(new Date());
        stv.setLastModifyTime(new Date());
        stvDao.save(stv);
        stvDao.flush();
        // 返回值
        return createOrderResultByStv(sta, stv);
    }

    private OrderResult createOrderResultByStv(StockTransApplication sta, StockTransVoucher stv) {
        OrderResult or = new OrderResult();
        or.setCode(stv.getCode());
        or.setSlipCode(sta.getSlipCode1());
        or.setStatus(OrderResult.STATUS_SUCCESS);
        or.setType(sta.getType().getValue());
        or.setWhId(stv.getWarehouse().getId());
        List<OrderLine> roLineList = new ArrayList<OrderLine>();
        for (StvLine line : stvLineDao.findStvLineListByStvId(stv.getId())) {
            OrderLine roLine = new OrderLine();
            roLine.setBatchCode(line.getBatchCode());
            roLine.setDirection(line.getDirection().getValue());
            roLine.setInvStatusId(line.getInvStatus().getId());
            roLine.setOwner(line.getOwner());
            roLine.setQty(line.getQuantity());
            roLine.setSkuCode(line.getSku().getCode());
            roLineList.add(roLine);
        }
        or.setLines(roLineList);
        return or;
    }

    private StvLine createStvLine(String batchCode, TransactionDirection direction, Date inBoundTime, Date productionDate, Integer validDate, Date expireDate, InventoryStatus is, Sku sku, String owner, Long qty) {
        StvLine l = new StvLine();
        l.setBatchCode(batchCode);
        l.setDirection(direction);
        l.setInBoundTime(inBoundTime);
        l.setProductionDate(productionDate);
        l.setValidDate(validDate);
        l.setExpireDate(expireDate);
        l.setInvStatus(is);
        l.setOwner(owner);
        l.setQuantity(qty);
        l.setSku(sku);
        return l;
    }

    @Override
    public boolean procurementOrder(String slipCode) {
        List<StockTransApplication> list = staDao.findBySlipCode(slipCode);
        for (StockTransApplication sta : list) {
            if (sta.getType() == StockTransApplicationType.OUTBOUND_PURCHASE) {
                if ("1".equals(sta.getIsPf())) {
                    if (sta.getStatus() == StockTransApplicationStatus.PACKING) {
                        throw new BusinessException("装箱中不可以取消");
                    }
                }
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse() == null ? null : sta
                        .getMainWarehouse().getId());
                inventoryDao.releaseInventoryByStaId(sta.getId());
                /***** mongoDB库存变更添加逻辑 ******************************/
                try {
                    eventObserver.onEvent(new TransactionalEvent(sta));
                } catch (BusinessException e) {
                    throw e;
                }
            } else {
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            }
            sta.setLastModifyTime(new Date());
            staDao.save(sta);
        }
        return true;
    }

    @Override
    public List<WarehousePriority> getPriorityWHByCity(String province, String city, Long cusId, Long cId) {
        return warehouseCoverageAreaRefDao.findWarehouseByCoverageAreaChannleId(province, city, cusId, cId, new BeanPropertyRowMapper<WarehousePriority>(WarehousePriority.class));
    }

    @Override
    public WmsResponse vmiAsn(VmiAsn VmiAsn, WmsResponse wr) {
        VmiAsnCommand vmiC = vmiAsnDao.findVmiAsnByuuid(VmiAsn.getUuid(), new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
        if (vmiC != null) {
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("uuid:" + VmiAsn.getUuid() + " 已存在");
            wr.setUuid(VmiAsn.getUuid());
            return wr;
        } else {
            wr.setStatus(WmsResponse.STATUS_SUCCESS);
            wr.setMsg("接收成功");
            wr.setUuid(VmiAsn.getUuid());
        }
        List<VmiAsnCommand> vmiR = vmiAsnDao.findVmiAsnByOrder(VmiAsn.getStoreCode(), VmiAsn.getOrderCode(), new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
        if (null != vmiR && vmiR.size() > 0) {
            VmiAsnDefault v = new VmiAsnDefault();
            v.setUuid(VmiAsn.getUuid());
            v.setStoreCode(VmiAsn.getStoreCode());
            v.setArriveDate(VmiAsn.getArriveDate());
            v.setFromLocation(VmiAsn.getFromLocation());
            v.setToLoaction(VmiAsn.getToLocation());
            v.setOrderCode(VmiAsn.getOrderCode());
            v.setExtMemo(VmiAsn.getExtMemo());
            v.setWhCode(VmiAsn.getWhCode());
            v.setCreateTime(new Date());
            v.setFinishTime(new Date());
            v.setStatus(VmiGeneralStatus.REPEATORDER);
            v.setVersion(1);
            v.setType(VmiAsn.getType());
            vmiAsnDao.save(v);
            for (VmiAsnLine vd : VmiAsn.getAsnLines()) {
                VmiAsnLineDefault va = new VmiAsnLineDefault();
                va.setCartonNo(vd.getCartonNo());
                va.setAsnId(v);
                va.setExtMemo(vd.getExtMemo());
                va.setQty(vd.getQty());
                va.setStatus(VmiGeneralStatus.REPEATORDER);
                va.setUpc(vd.getUpc());
                va.setVersion(1);
                vmiAsnLineDao.save(va);
            }
            return wr;
        }
        VmiAsnDefault v = new VmiAsnDefault();
        v.setUuid(VmiAsn.getUuid());
        v.setStoreCode(VmiAsn.getStoreCode());
        v.setArriveDate(VmiAsn.getArriveDate());
        v.setFromLocation(VmiAsn.getFromLocation());
        v.setToLoaction(VmiAsn.getToLocation());
        v.setOrderCode(VmiAsn.getOrderCode());
        v.setExtMemo(VmiAsn.getExtMemo());
        v.setWhCode(VmiAsn.getWhCode());
        v.setCreateTime(new Date());
        v.setFinishTime(new Date());
        v.setStatus(VmiGeneralStatus.NEW);
        v.setVersion(1);
        v.setType(VmiAsn.getType());
        vmiAsnDao.save(v);
        for (VmiAsnLine vd : VmiAsn.getAsnLines()) {
            VmiAsnLineDefault va = new VmiAsnLineDefault();
            va.setCartonNo(vd.getCartonNo());
            va.setAsnId(v);
            va.setExtMemo(vd.getExtMemo());
            va.setQty(vd.getQty());
            va.setStatus(VmiGeneralStatus.NEW);
            va.setUpc(vd.getUpc());
            va.setVersion(1);
            vmiAsnLineDao.save(va);
        }
        return wr;
    }

    /**
     * VMI退仓指令
     */
    @Override
    public WmsResponse vmiRto(VmiRto vmiRto, WmsResponse wr) {
        VmiRtoCommand vmiC = vmiRtoDao.findVmiRtoByUuid(vmiRto.getUuid(), new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
        if (vmiC != null) {
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("uuid:" + vmiRto.getUuid() + " 已存在");
            wr.setUuid(vmiRto.getUuid());
            return wr;
        } else {
            wr.setStatus(WmsResponse.STATUS_SUCCESS);
            wr.setMsg("接收成功");
            wr.setUuid(vmiRto.getUuid());
        }
        List<VmiRtoCommand> vmiR = vmiRtoDao.findVmiRtoByOrder(vmiRto.getStoreCode(), vmiRto.getOrderCode(), new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
        if (null != vmiR && vmiR.size() > 0) {
            VmiRtoDefault v = new VmiRtoDefault();
            v.setUuid(vmiRto.getUuid());
            v.setStoreCode(vmiRto.getStoreCode());
            v.setArriveDate(vmiRto.getArriveDate());
            v.setOrderCode(vmiRto.getOrderCode());
            v.setExtMemo(vmiRto.getExtMemo());
            v.setWhCode(vmiRto.getWhCode());
            v.setCreateTime(new Date());
            v.setFinishTime(new Date());
            v.setStatus(VmiGeneralStatus.REPEATORDER);
            v.setVersion(1);
            vmiRtoDao.save(v);
            for (VmiRtoLine vd : vmiRto.getRtoLines()) {
                VmiRtoLineDefault vr = new VmiRtoLineDefault();
                vr.setCartonNo(vd.getCartonNo());
                vr.setRto(v);
                vr.setExtMemo(vd.getExtMemo());
                vr.setQty(vd.getQty());
                vr.setStatus(VmiGeneralStatus.REPEATORDER);
                vr.setUpc(vd.getUpc());
                vr.setLineNo(vd.getLineNo());
                vr.setConsigneeKey(vd.getConsigneeKey());
                vr.setOriginalQty(vd.getOriginalQty());
                vr.setUom(vd.getUom());
                vr.setVersion(1);
                vmiRtoLineDao.save(vr);
            }
            return wr;
        }
        VmiRtoDefault v = new VmiRtoDefault();
        v.setUuid(vmiRto.getUuid());
        v.setStoreCode(vmiRto.getStoreCode());
        v.setArriveDate(vmiRto.getArriveDate());
        v.setOrderCode(vmiRto.getOrderCode());
        v.setExtMemo(vmiRto.getExtMemo());
        v.setWhCode(vmiRto.getWhCode());
        v.setCreateTime(new Date());
        v.setFinishTime(new Date());
        v.setStatus(VmiGeneralStatus.NEW);
        v.setVersion(1);
        vmiRtoDao.save(v);
        for (VmiRtoLine vd : vmiRto.getRtoLines()) {
            VmiRtoLineDefault vr = new VmiRtoLineDefault();
            vr.setCartonNo(vd.getCartonNo());
            vr.setRto(v);
            vr.setExtMemo(vd.getExtMemo());
            vr.setQty(vd.getQty());
            vr.setStatus(VmiGeneralStatus.NEW);
            vr.setUpc(vd.getUpc());
            vr.setLineNo(vd.getLineNo());
            vr.setConsigneeKey(vd.getConsigneeKey());
            vr.setOriginalQty(vd.getOriginalQty());
            vr.setUom(vd.getUom());
            vr.setVersion(1);
            vmiRtoLineDao.save(vr);
        }
        return wr;
    }

    @Override
    public Boolean cancelProcurement(String code) {
        List<StockTransApplication> list = staDao.findBySlipCodeCancel(code);
        for (StockTransApplication sta : list) {
            switch (sta.getType()) {
                case INBOUND_PURCHASE:
                    break;
                default:
                    return false;
            }
            switch (sta.getStatus()) {
                case CANCEL_UNDO:
                    // 取消未处理
                    return false;
                case INTRANSIT:
                    // 已转出，取消失败
                    return false;
                case PARTLY_RETURNED:
                    // 部分入库，取消失败
                    return false;
                case FINISHED:
                    // 完成，取消失败
                    return false;
                default:
                    // 取消作业单
                    sta.setStatus(StockTransApplicationStatus.CANCELED);
                    // 订单状态与账号关联
                    whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                    sta.setLastModifyTime(new Date());
                    sta.setCancelTime(new Date());
                    sta.setSysUpdateTime(new Date());
                    staDao.save(sta);
                    staDao.flush();
                    break;
            }
        }
        return true;
    }

    @Override
    public Boolean closeProcurement(String code) {
        log.debug("close------->" + code);
        List<StockTransApplication> list = staDao.findBySlipCodeCancel(code);
        for (StockTransApplication sta : list) {
            switch (sta.getType()) {
                case INBOUND_PURCHASE:
                    break;
                case INBOUND_GIFT:
                    break;
                case SKU_RETURN_INBOUND:
                    break;
                default:
                    return false;
            }
            switch (sta.getStatus()) {
                case CANCEL_UNDO:
                    // 取消未处理
                    return false;
                case INTRANSIT:
                    // 已转出，取消失败
                    return false;
                case PARTLY_RETURNED:
                    // 部分入库，关闭成功
                    // 完成作业单作业单
                    sta.setStatus(StockTransApplicationStatus.FINISHED);
                    sta.setLastModifyTime(new Date());
                    sta.setSysUpdateTime(new Date());
                    sta.setFinishTime(new Date());
                    staDao.save(sta);
                    staDao.flush();
                    break;
                case FINISHED:
                    // 完成，关闭失败
                    return false;
                default:
                    // 关闭失败
                    return false;
            }
        }
        return true;
    }

    @Override
    public List<WarehousePriority> getPriorityWHByCityChannleId(String province, String city, Long cusId, Long channleId) {
        return warehouseCoverageAreaRefDao.findWarehouseByCoverageAreaChannleId(province, city, cusId, channleId, new BeanPropertyRowMapper<WarehousePriority>(WarehousePriority.class));
    }

    @Override
    public void updateStoreBrand(String shopCode, List<String> brandCodeList) {
        BiChannel shop = biChannelDao.getByCode(shopCode);
        if (shop == null) {
            throw new BusinessException("店铺未找到,店铺编码:" + shopCode);
        }
        // 删除shop的关联
        biChannelBrandRefDao.deleteBiChannelBrandRef(shop.getId());
        for (String brandCode : brandCodeList) {
            Brand brand = brandDao.getByCode(brandCode);
            if (brand == null) {
                throw new BusinessException("品牌未找到,品牌编码:" + brandCode);
            }
            // 添加关联
            biChannelBrandRefDao.insertBiChannelBrandRef(shop.getId(), brand.getId());
        }
    }
}
