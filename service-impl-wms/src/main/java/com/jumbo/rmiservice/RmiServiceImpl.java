package com.jumbo.rmiservice;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLineQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLogDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.MsgInvoiceDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.dao.warehouse.ReturnApplicationDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.SystemLogDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.dao.warehouse.WholeTaskLogDao;
import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.rmi.warehouse.BaseResult;
import com.jumbo.rmi.warehouse.Invoice;
import com.jumbo.rmi.warehouse.InvoiceLine;
import com.jumbo.rmi.warehouse.InvoiceOrder;
import com.jumbo.rmi.warehouse.InvoiceOrderResult;
import com.jumbo.rmi.warehouse.OmsOutInfo;
import com.jumbo.rmi.warehouse.Order;
import com.jumbo.rmi.warehouse.OrderLine;
import com.jumbo.rmi.warehouse.OrderResult;
import com.jumbo.rmi.warehouse.OrderStaPayment;
import com.jumbo.rmi.warehouse.PackageResult;
import com.jumbo.rmi.warehouse.RmiChannelInfo;
import com.jumbo.rmi.warehouse.RmiSku;
import com.jumbo.rmi.warehouse.WarehousePriority;
import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.rmi.warehouse.vmi.VmiAsn;
import com.jumbo.rmi.warehouse.vmi.VmiAsnLine;
import com.jumbo.rmi.warehouse.vmi.VmiRto;
import com.jumbo.rmi.warehouse.vmi.VmiRtoLine;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.hub2wms.HubWmsManager;
import com.jumbo.wms.manager.listener.StaListenerManager;
import com.jumbo.wms.manager.sku.SkuManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.task.inv.TotalInvManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.warehouse.StaCancelManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoice;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLine;
import com.jumbo.wms.model.hub2wms.WmsSalesOrder;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLog;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderResult;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgInvoice;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.ReturnApplication;
import com.jumbo.wms.model.warehouse.ReturnApplicationStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WholeTaskLogStatus;
import com.jumbo.wms.model.warehouse.WholeTaskLogType;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrderCommand;

import cn.baozun.bh.util.JSONUtil;
import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONArray;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Service("rmiService")
public class RmiServiceImpl extends BaseManagerImpl implements RmiService {

    /**
     * 
     */
    private static final long serialVersionUID = -222398285793818376L;
    protected static final Logger logger = LoggerFactory.getLogger(RmiServiceImpl.class);
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private MsgRtnOutboundDao msgRtnDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private SkuManager skuManager;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private RmiManager rmiManager;
    @Autowired
    private ChannelWhRefRefDao channelWhRefRefDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaListenerManager staListenerManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private StaInvoiceLineDao staInvoiceLineDao;
    @Autowired
    private BaseinfoManager baseManger;
    @Autowired
    private TransportatorDao transDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private MsgInvoiceDao msgInvoiceDao;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private TotalInvManager totalInvManager;
    @Autowired
    private WholeTaskLogDao wholeTaskLogDao;
    @Autowired
    private SystemLogDao systemLogDao;
    @Autowired
    private ReturnApplicationDao returnApplicationDao;
    @Autowired
    private StaCancelManager staCancelManager;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private HubWmsManager hubWmsManager;
    @Autowired
    private WmsSalesOrderLogDao logDao;
    @Autowired
    private WmsSalesOrderLineQueueDao wmsSalesOrderLineQueueDao;
    private static final String RECEIVE_SUCCESS = "接收成功";
    /**
     * 物流商COD缓存
     */
    static TimeHashMap<String, List<Transportator>> transRoleCache = new TimeHashMap<String, List<Transportator>>();


    @Override
    public BaseResult orderCreate(Order order) {



        try {
            String reqJson = JSONUtil.beanToJson(order);
            logger.info("orderCreate" + order.getCode());
            logger.info(reqJson);
        } catch (Exception e) {
            logger.error("orderCreate" + order.getCode() + e);
        }



        BaseResult bs = new BaseResult();
        try {
            bs.setType(order.getType());
            rmiManager.validateOrderInfo(order);
            int type2 = 0;
            if (order.getType() == 1) {
                type2 = 21;
            } else if (order.getType() == 2) {
                type2 = 41;
                for (OrderLine line1 : order.getLines()) {
                    if (line1.getDirection() == 2) {
                        type2 = 42;
                    }
                }
            }
            int type = order.getType();
            // 判断是否是反向NP调整入库
            if (type == SlipType.REVERSE_NP_ADJUSTMENT_INBOUND.getValue()) {
                order.setSlipType(type);
                order.setType(StockTransApplicationType.INBOUND_RETURN_PURCHASE.getValue());
                type = StockTransApplicationType.INBOUND_RETURN_PURCHASE.getValue();
            }

            try {
                StockTransApplicationType.valueOf(type);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.STA_TYPE_ERROR);
            }

            if (type == StockTransApplicationType.ORDER_CODE.getValue() || type == StockTransApplicationType.INBOUND_RETURN_REQUEST_CODE.getValue()) {
                if (order.getCode() == null) {
                    // 接口数据中前置单据号为空
                    throw new BusinessException(ErrorCode.EI_SLIP_CODE_IS_NULL);
                }
                QueueSta sta = queueStaDao.findStaBySlipCodeNotCancel(order.getCode());
                if (sta != null) {
                    bs.setStatus(1);
                    bs.setCode(order.getCode());
                    String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_ALLREADY_SUCCESS), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                    bs.setMsg(errorMsg);
                    return bs;
                }
                try {
                    // 进行取消时间判断
                    StockTransApplication sta2 = wmsSalesOrderLineQueueDao.staStatusCancleByOrderCode(order.getCode(), new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
                    if (sta2 != null && sta2.getCancelTime() != null && order.getSendTimeMq() != null) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
                        Date sendTimeMq = df.parse(order.getSendTimeMq());
                        if (sendTimeMq.getTime() <= sta2.getCancelTime().getTime()) {
                            throw new BusinessException(ErrorCode.HW_DS_ERROE_CANCEL_TIME);
                        }
                    }
                } catch (Exception e) {
                    log.error("staStatusCancleByOrderCode" + order.getCode());
                }

                // Step1:校验商品是否存在
                validateSkuByCode(order.getLines());
                // Step1:校验基本信息
                validateBasis(order);
                if (order.getChannelList() == null) {
                    for (OrderLine line : order.getLines()) {
                        if (line.getOwner() == null) {
                            throw new BusinessException(ErrorCode.ERROR_TOTAL_ACTUAL);
                        }
                    }
                }
                if (order.getDeliveryInfo() != null) {
                    int o = order.getOrderTotalActual().compareTo(BigDecimal.ZERO);
                    int r = order.getTotalActual().compareTo(BigDecimal.ZERO);
                    if (r == -1) {
                        throw new BusinessException(ErrorCode.ERROR_TOTAL_ACTUAL);
                    }
                    if (o == -1) {
                        throw new BusinessException(ErrorCode.ERROR_TOTAL_ACTUAL);
                    }
                    // 检验省市必填
                    if (type2 == 21 || type2 == 42) {
                        if (StringUtil.isEmpty(order.getDeliveryInfo().getProvince()) || StringUtil.isEmpty(order.getDeliveryInfo().getCity())) {
                            throw new BusinessException(ErrorCode.ERROR_PRO_CITY);
                        }
                    }

                    // 检验物流商
                    if (order.getDeliveryInfo().getLpCode() == null) {
                        order.getDeliveryInfo().setLpCode("");
                    }

                    order.getDeliveryInfo().setIsCod(order.getDeliveryInfo().getIsCod() == null ? false : order.getDeliveryInfo().getIsCod());
                    if (order.getDeliveryInfo().getIsCod() != null && order.getDeliveryInfo().getIsCod()) {
                        if (order.getCodAmount() == null) {
                            throw new BusinessException(ErrorCode.ERROR_COD_AMOUNT);
                        }
                        if (!order.getDeliveryInfo().getLpCode().equals(Transportator.JD) && !order.getDeliveryInfo().getLpCode().equals(Transportator.JDCOD)) {
                            int e = order.getCodAmount().compareTo(BigDecimal.ZERO);
                            int a = order.getCodAmount().compareTo(BigDecimal.ZERO);
                            if (e == 0 || e == -1) {
                                throw new BusinessException(ErrorCode.ERROR_TOTAL_ACTUAL);
                            }
                            if (a == 0 || a == -1) {
                                throw new BusinessException(ErrorCode.ERROR_TOTAL_ACTUAL);
                            }
                        }
                        boolean mk = false;
                        List<ChooseOption> chooselist = chooseOptionManager.findOptionListByCategoryCode(Constants.MK_FEE_CONFIG);
                        if (chooselist.size() > 0) {
                            for (ChooseOption chooseOption : chooselist) {
                                if (chooseOption.getOptionKey().equals(order.getOwner())) {
                                    mk = true;
                                    break;
                                }

                            }
                        }
                        if (mk) {// mk cod定制
                            BigDecimal others = BigDecimal.ZERO;
                            if (order.getOrderPays() != null) {
                                for (OrderStaPayment payment : order.getOrderPays()) {
                                    others = others.add(payment.getAmt());
                                }
                            }
                            int acl = order.getTotalActual().add(order.getTransferFee()).add(others).compareTo(order.getCodAmount());
                            if (acl == -1) {
                                throw new BusinessException(ErrorCode.ERROR_TOTAL_ACTUAL);
                            }
                            int free = order.getTransferFee().compareTo(order.getCodAmount());
                            if (free == 0 || free == -1) {
                                order.setTotalActual(order.getCodAmount().subtract(order.getTransferFee()).subtract(others));
                            } else {
                                order.setTransferFee(order.getCodAmount());
                                order.setTotalActual(order.getCodAmount().subtract(order.getTransferFee()).subtract(others));
                            }
                            if (!order.getTransferFee().add(order.getTotalActual()).add(others).equals(order.getCodAmount())) {
                                throw new BusinessException(ErrorCode.ERROR_COD_AMOUNT);
                            }
                        } else {
                            int acl = order.getTotalActual().add(order.getTransferFee()).compareTo(order.getCodAmount());
                            if (acl == -1) {
                                throw new BusinessException(ErrorCode.ERROR_TOTAL_ACTUAL);
                            }
                            int free = order.getTransferFee().compareTo(order.getCodAmount());
                            if (free == 0 || free == -1) {
                                order.setTotalActual(order.getCodAmount().subtract(order.getTransferFee()));
                            } else {
                                order.setTransferFee(order.getCodAmount());
                                order.setTotalActual(order.getCodAmount().subtract(order.getTransferFee()));
                            }
                            if (!order.getTransferFee().add(order.getTotalActual()).equals(order.getCodAmount())) {
                                throw new BusinessException(ErrorCode.ERROR_COD_AMOUNT);
                            }
                        }
                        validateTransportator(order.getDeliveryInfo().getLpCode());
                    }
                    if (Transportator.JD.equals(order.getDeliveryInfo().getLpCode()) || Transportator.JDCOD.equals(order.getDeliveryInfo().getLpCode())) {
                        if (type == StockTransApplicationType.INBOUND_RETURN_REQUEST_CODE.getValue()) {
                            throw new BusinessException(ErrorCode.ERROR_JD_RETURN);
                        }
                    }

                }
                rmiManager.createStaByOrder(order);
            }
            // 出库通用校验和创单：（加类型即可)
            // VMI转店、库间移动、同公司调拨、不同公司调拨、样品领用出库、送修出库、商品置换出库、串号出库、包材领用出库、固定资产领用、福利领用、报废出库、促销领用、低值易耗品
            if (type == StockTransApplicationType.OUTBOUND_PURCHASE.getValue() || type == StockTransApplicationType.VMI_OWNER_TRANSFER.getValue() || type == StockTransApplicationType.TRANSIT_CROSS.getValue()
                    || type == StockTransApplicationType.SAME_COMPANY_TRANSFER.getValue() || type == StockTransApplicationType.DIFF_COMPANY_TRANSFER.getValue() || type == StockTransApplicationType.SAMPLE_OUTBOUND.getValue()
                    || type == StockTransApplicationType.SKU_EXCHANGE_OUTBOUND.getValue() || type == StockTransApplicationType.REAPAIR_OUTBOUND.getValue() || type == StockTransApplicationType.SERIAL_NUMBER_OUTBOUND.getValue()
                    || type == StockTransApplicationType.SERIAL_NUMBER_GROUP_OUTBOUND.getValue() || type == StockTransApplicationType.OUTBOUND_PACKAGING_MATERIALS.getValue() || type == StockTransApplicationType.FIXED_ASSETS_USE.getValue()
                    || type == StockTransApplicationType.WELFARE_USE.getValue() || type == StockTransApplicationType.SCARP_OUTBOUND.getValue() || type == StockTransApplicationType.SALES_PROMOTION_USE.getValue()
                    || type == StockTransApplicationType.LOW_VALUE_CONSUMABLE_USE.getValue() || type == StockTransApplicationType.OUTBOUND_SETTLEMENT.getValue() || type == StockTransApplicationType.OUTBOUND_CONSIGNMENT.getValue()) {
                if (order.getLines() == null || order.getLines().size() == 0) {
                    // 传入接口的请求明细行为空
                    throw new BusinessException(ErrorCode.EI_DETAIL_LINE_IS_NULL);
                }
                // Step1:校验商品是否存在
                validateSkuByCode(order.getLines());
                if (type == StockTransApplicationType.SERIAL_NUMBER_GROUP_OUTBOUND.getValue() || type == StockTransApplicationType.SERIAL_NUMBER_OUTBOUND.getValue()) {
                    // Step1.1:有出有入，校验必须出入都有，并且方向必须是1，2
                    validateDirection(order);
                }
                // Step2:校验库存状态
                validateInvStatus(order);
                // Step3:校验销售可用量
                validateIsInventoryEnough(order);
                rmiManager.createStaByOrder(order);
            }
            // 入库通用校验和创单:
            // 样品领用入库、送修入库、商品置换入库 ，采购退货调整入库
            if (type == StockTransApplicationType.INBOUND_PURCHASE.getValue() || type == StockTransApplicationType.SKU_RETURN_INBOUND.getValue() || type == StockTransApplicationType.INBOUND_GIFT.getValue()
                    || type == StockTransApplicationType.SAMPLE_INBOUND.getValue() || type == StockTransApplicationType.SKU_EXCHANGE_INBOUND.getValue() || type == StockTransApplicationType.REAPAIR_INBOUND.getValue()
                    || type == StockTransApplicationType.INBOUND_RETURN_PURCHASE.getValue()) {
                if (type == StockTransApplicationType.SAMPLE_INBOUND.getValue()) {
                    StockTransApplication sta1 = staDao.getStaBySlipCode1(order.getSlipCode1());
                    if (sta1 == null) {
                        throw new BusinessException(ErrorCode.EI_NOT_EXISTS_OUTBOUND);
                    }
                }
                if (order.getLines() == null || order.getLines().size() == 0) {
                    // 传入接口的请求明细行为空
                    throw new BusinessException(ErrorCode.EI_DETAIL_LINE_IS_NULL);
                }
                if (type == StockTransApplicationType.INBOUND_PURCHASE.getValue()) {
                    List<StockTransApplication> sta = staDao.findStaByCodeNotCancel1(order.getCode());
                    if (sta != null && sta.size() > 0) {
                        bs.setStatus(1);
                        bs.setCode(order.getCode());
                        String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_ALLREADY_SUCCESS), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                        bs.setMsg(errorMsg);
                        return bs;
                    }
                }
                // Step1:校验商品是否存在
                validateSkuByCode(order.getLines());
                // Step2:校验库存状态
                validateInvStatus(order);
                rmiManager.createStaByOrder(order);
            }
            bs.setStatus(1);
            bs.setCode(order.getCode());
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_SUCCESS), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            // 创单成功
            bs.setMsg(errorMsg);
        } catch (BusinessException e) {
            if (log.isInfoEnabled()) {
                log.info("error code : {}", order.getCode());
            }
            String errorMsg = "";
            bs.setCode(order.getCode());
            errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            bs.setStatus(0);
            bs.setMsg(errorMsg);
            if (log.isInfoEnabled()) {
                log.info("errorMsg : {}", errorMsg);
            }
        } catch (Exception e) {
            log.error("error code : {}", order.getCode(), e);
            bs.setStatus(0);
            bs.setCode(order.getCode());
            String errorMsg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SYSTEM_ERROR, new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            bs.setMsg(errorMsg);
            log.error("errorMsg : {}", errorMsg);
        }
        return bs;
    }

    /**
     * 校验有出有入的单子
     * 
     * @param lines
     */
    private void validateDirection(Order order) {
        List<OrderLine> lines = order.getLines();
        // 存储错误的方向
        List<Integer> errorDirection = new ArrayList<Integer>();
        Map<String, Long> map = new HashMap<String, Long>();
        Map<String, Long> map1 = new HashMap<String, Long>();
        int i = 0, j = 0;// 分别记录出入库明细行
        for (OrderLine line : lines) {
            if (line.getDirection() == null) {
                // 接口明细中存在空的作业方向!
                throw new BusinessException(ErrorCode.EI_EXISTS_NULL_DIRECTION);
            } else {
                if (line.getDirection() == 1) {
                    i++;
                    if (map1.get(line.getSkuCode()) == null) {
                        map1.put(line.getSkuCode(), line.getQty());
                    } else {
                        map1.put(line.getSkuCode(), map1.get(line.getSkuCode()) + line.getQty());
                    }
                } else if (line.getDirection() == 2) {
                    j++;
                    if (map.get(line.getSkuCode()) == null) {
                        map.put(line.getSkuCode(), line.getQty());
                    } else {
                        map.put(line.getSkuCode(), map.get(line.getSkuCode()) + line.getQty());
                    }
                } else {
                    errorDirection.add(line.getDirection());
                }
            }
        }
        if (i == 0 || j == 0) {
            // 接口明细中不能只有出或者入!
            throw new BusinessException(ErrorCode.EI_NOT_IN_OR_OUT);
        }
        if (errorDirection.size() > 0) {
            // 接口中事务方向{0}为非法的，只能在1,2中选择!
            throw new BusinessException(ErrorCode.EI_DIRECTION_ERROR, new Object[] {errorDirection});
        }
        if (order.getType() == StockTransApplicationType.SERIAL_NUMBER_OUTBOUND.getValue()) {
            if (map.size() > 1) {
                // 出库商品只能出一种
                throw new BusinessException(ErrorCode.EI_CANNOT_SKU_GT_ONE);
            }
            Long outQty = null;
            for (String s : map.keySet()) {
                outQty = map.get(s);
            }
            for (String s : map1.keySet()) {
                if (map1.get(s) == 0L || !(map1.get(s) % outQty == 0)) {
                    // 入库必须是出库的整数倍
                    throw new BusinessException(ErrorCode.EI_IN_MUST_OUT_INTEGER);
                }
            }
        }
    }

    /**
     * 校验商品是否存在
     * 
     * @param lines
     * @return
     */
    private void validateSkuByCode(List<OrderLine> lines) {
        List<String> codeList = new ArrayList<String>();
        Map<String, String> map = new HashMap<String, String>();
        for (OrderLine line : lines) {
            if (!StringUtils.hasText(line.getSkuCode())) {
                // 存在商品为空的明细行！
                throw new BusinessException(ErrorCode.EI_EXISTS_NULL_SKU);
            }
            if (line.getQty() == null || line.getQty().compareTo(0L) <= 0) {
                throw new BusinessException(ErrorCode.EI_EXISTS_NULL_QTY);
            }
            if (line.getLineType() != null && 5 == line.getLineType()) {
                throw new BusinessException(ErrorCode.ORDER_LINE_TYPE_ERROR);
            }
            map.put(line.getSkuCode(), line.getSkuCode());
        }
        codeList.addAll(map.values());
        List<String> errorList = new ArrayList<String>();
        for (String s : codeList) {
            Sku sku = skuDao.getByCode(s);
            if (sku == null) {
                errorList.add(s);
            }
        }
        if (errorList.size() > 0) {
            // 编码{0}对应的商品不存在！
            throw new BusinessException(ErrorCode.EI_SKU_NOTEXISTS, new Object[] {errorList});
        }
    }

    public void validateTransportator(String code) {
        List<Transportator> list = getTransRole("COD");
        for (Transportator transportator : list) {
            System.out.println(transportator.getExpCode() + "<>" + code);
            if (transportator.getExpCode().equals(code)) {
                if (transportator.getIsSupportCod()) {
                    break;
                } else {
                    throw new BusinessException(ErrorCode.ERROR_TRANS_PORTATOR);
                }
            }
        }


    }

    public List<Transportator> getTransRole(String owner) {
        if (StringUtil.isEmpty(owner)) return null;
        List<Transportator> result = transRoleCache.get(owner);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            result = cacheTransRole(owner);
        }
        return result;
    }

    /**
     * 根据店铺获取一定时间内的缓存数据
     * 
     * @param owner
     * @return
     */
    private synchronized List<Transportator> cacheTransRole(String owner) {
        List<Transportator> result = transDao.findAll();
        transRoleCache.put(owner, result, 4 * 60 * 1000);
        return result;
    }

    /**
     * 校验接口数据中的库存状态包含基本信息验证
     * 
     * @param order
     */
    private void validateInvStatus(Order order) {
        if (order.getMainWhOuId() == null) {
            // 接口数据中仓库组织为空!
            throw new BusinessException(ErrorCode.EI_MAIN_WH_IS_NULL);
        }
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());
        if (ou1 == null) {
            // 接口数据对应的仓库组织不存在!
            throw new BusinessException(ErrorCode.EI_MAIN_WH_NOT_EXISTS);
        }
        if (!StringUtils.hasLength(order.getOwner())) {
            // 接口数据中店铺为空!
            throw new BusinessException(ErrorCode.EI_START_OWNER_IS_NULL);
        }
        BiChannel shop = biChannelDao.getByCode(order.getOwner());
        if (shop == null) {
            // 接口数据对应的店铺不存在!
            throw new BusinessException(ErrorCode.EI_OWNER_NOT_EXISTS, new Object[] {order.getOwner()});
        }
        String owner = channelWhRefRefDao.getShopInfoByWarehouseAndShopCode(ou1.getId(), order.getOwner(), new SingleColumnRowMapper<String>(String.class));
        if (owner == null) {
            // 接口数据对应的仓库组织和店铺没有绑定!
            throw new BusinessException(ErrorCode.EI_SOURCE_WH_SHOP_NOREF);
        }
        Long ouId = operationUnitDao.getCompanyIdByWarehouseOu(ou1.getId(), new SingleColumnRowMapper<Long>(Long.class));
        int type = order.getType();
        // 强制约束头信息和明细中库存状态一致
        // 库间移动、同公司调拨、不同公司调拨、VMI转店
        if (type == StockTransApplicationType.VMI_OWNER_TRANSFER.getValue() || type == StockTransApplicationType.TRANSIT_CROSS.getValue() || type == StockTransApplicationType.SAME_COMPANY_TRANSFER.getValue()
                || type == StockTransApplicationType.DIFF_COMPANY_TRANSFER.getValue()) {
            if (order.getInvStatusId() == null) {
                // 接口数据中头信息库存状态为空
                throw new BusinessException(ErrorCode.EI_DATA_INVSTATUASID_IS_NULL);
            }
            InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(order.getInvStatusId(), ouId);
            if (invStatus == null) {
                // 接口数据中头信息对应的库存状态不存在!
                throw new BusinessException(ErrorCode.EI_INVENTORY_STATUS_NOT_FOUND);
            }
        } else {// 明细中存在不同库存状态的
            Map<Long, Long> map = new HashMap<Long, Long>();
            for (OrderLine line : order.getLines()) {
                if (line.getInvStatusId() == null) {
                    // 接口中存在没有库存状态的明细行!
                    throw new BusinessException(ErrorCode.EI_DETAIL_EXISTS_NO_INVSTATUS_LINE);
                } else {
                    map.put(line.getInvStatusId(), line.getInvStatusId());
                }
            }
            List<Long> list = new ArrayList<Long>();
            list.addAll(map.values());
            List<Long> invList = inventoryStatusDao.findInvStatusByIdAndOu(list, ouId, new SingleColumnRowMapper<Long>(Long.class));
            for (Long id : invList) {
                map.remove(id);
            }
            if (map.size() > 0) {
                // 接口数据明细行中库存状态ID{0}对应的库存状态不存在!
                throw new BusinessException(ErrorCode.EI_DETAIL_INVSTATUS_NOT_EXISTS, new Object[] {map.values()});
            }
        }
    }

    /**
     * 出库校验销售可用量是否足够
     * 
     * @param order
     * @return
     */
    private void validateIsInventoryEnough(Order order) {
        List<String> rs = new ArrayList<String>();
        List<OrderLine> lines = order.getLines();
        List<String> codeList = new ArrayList<String>();
        Map<String, Long> qtyMap = new HashMap<String, Long>();
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());
        Long ouId = operationUnitDao.getCompanyIdByWarehouseOu(ou1.getId(), new SingleColumnRowMapper<Long>(Long.class));
        // 强制约束头和明细库存状态一致时 只有出的
        // 库间移动、同公司调拨、不同公司调拨、VMI转店
        if (order.getType() == StockTransApplicationType.VMI_OWNER_TRANSFER.getValue() || order.getType() == StockTransApplicationType.TRANSIT_CROSS.getValue() || order.getType() == StockTransApplicationType.SAME_COMPANY_TRANSFER.getValue()
                || order.getType() == StockTransApplicationType.DIFF_COMPANY_TRANSFER.getValue()) {
            InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(order.getInvStatusId(), ouId);
            if (invStatus.getIsForSale() != null && invStatus.getIsForSale()) {
                // 合并明细行数量，然后校验
                for (int i = 0; i < lines.size(); i++) {
                    if (qtyMap.get(lines.get(i).getSkuCode()) == null) {
                        codeList.add(lines.get(i).getSkuCode());
                        qtyMap.put(lines.get(i).getSkuCode(), lines.get(i).getQty());
                    } else {
                        qtyMap.put(lines.get(i).getSkuCode(), qtyMap.get(lines.get(i).getSkuCode()) + lines.get(i).getQty());
                    }
                }
            }
        } else if (order.getType() == StockTransApplicationType.SERIAL_NUMBER_OUTBOUND.getValue() || order.getType() == StockTransApplicationType.SERIAL_NUMBER_GROUP_OUTBOUND.getValue()) {
            // 有出有入的校验 明细行不同的库存状态
            for (OrderLine line : lines) {
                if (line.getDirection() == 2) {
                    InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(line.getInvStatusId(), ouId);
                    if (invStatus.getIsForSale() != null && invStatus.getIsForSale()) {
                        if (qtyMap.get(line.getSkuCode()) == null) {
                            codeList.add(line.getSkuCode());
                            qtyMap.put(line.getSkuCode(), line.getQty());
                        } else {
                            qtyMap.put(line.getSkuCode(), qtyMap.get(line.getSkuCode()) + line.getQty());
                        }
                    }
                }
            }
        } else {// 明细存在不同的库存状态时
            for (OrderLine line : lines) {
                InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(line.getInvStatusId(), ouId);
                if (invStatus.getIsForSale() != null && invStatus.getIsForSale()) {
                    if (qtyMap.get(line.getSkuCode()) == null) {
                        codeList.add(line.getSkuCode());
                        qtyMap.put(line.getSkuCode(), line.getQty());
                    } else {
                        qtyMap.put(line.getSkuCode(), qtyMap.get(line.getSkuCode()) + line.getQty());
                    }
                }
            }
        }
        // 验证销售可用量
        for (String s : codeList) {
            SkuCommand sku = skuDao.findSalesQtyByOuAndOwner(order.getOwner(), order.getMainWhOuId(), s, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
            Long salesQty = sku == null ? 0L : sku.getSalesQty() == null ? 0L : sku.getSalesQty();
            Long qty = qtyMap.get(s);
            if (salesQty.compareTo(qty) < 0) {
                // [{0}]，缺{1}件;
                Sku sku1 = skuDao.getByCode(s);
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_SALES_INV_NOT_ENOUGH), new Object[] {sku1.getExtensionCode1(), qty - salesQty}, Locale.SIMPLIFIED_CHINESE);
                rs.add(errorMsg);
            }
        }
        if (rs.size() > 0) {
            StringBuffer errorMsg = new StringBuffer();
            for (String s : rs) {
                errorMsg.append(s);
            }
            // 销售可用量不足:{0}
            throw new BusinessException(ErrorCode.EI_NOT_ENOUGH_SALES_INVENTORY, new Object[] {errorMsg});
        }
    }

    @Override
    public BaseResult execInvCheck(String code) {
        BaseResult bs = new BaseResult();
        try {
            rmiManager.confirmUpdateStatus(code);
            bs.setStatus(1);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_FINISHED), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            bs.setMsg(errorMsg);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            bs.setStatus(0);
            bs.setMsg(errorMsg);
        } catch (Exception e) {
            log.debug("", e);
            bs.setStatus(0);
            String errorMsg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SYSTEM_ERROR, new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            bs.setMsg(errorMsg);
        }
        return bs;
    }

    @Transactional
    @Override
    public BaseResult unlockOrder(String code) {
        BaseResult bs = new BaseResult();
        try {
            StockTransApplication sta = new StockTransApplication();
            List<StockTransApplication> stalist = staDao.findBySlipCode(code);
            for (int i = 0; i < stalist.size(); i++) {
                if (stalist.get(i).getType().getValue() == 45 || stalist.get(i).getType().getValue() == 61 || stalist.get(i).getType().getValue() == 217 || stalist.get(i).getType().getValue() == 219) {
                    sta = stalist.get(i);
                }
            }

            if (sta == null) {
                StringBuffer errorMsg = new StringBuffer();
                throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND, new Object[] {errorMsg});
            } else {
                // 未完成、非取消的
                if (sta.getStatus().getValue() != 10 && sta.getStatus().getValue() != 17) {
                    sta.setIsLocked(false);
                    staDao.save(sta);
                    Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
                    if (StringUtils.hasLength(wh.getVmiSource())) {
                        if (sta.getType().getValue() == 61 || sta.getType().getValue() == 217 || sta.getType().getValue() == 219) {
                            if (sta.getType().getValue() == 61) {
                                staListenerManager.generateMsgForVmiOut(sta, wh);
                            } else {
                                wareHouseManager.msgInorder(sta, wh);
                            }
                        }
                    }
                    bs.setStatus(1);
                    String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_FINISHED), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                    // 执行成功
                    bs.setMsg(errorMsg);
                } else if (sta.getStatus().getValue() == 10) {
                    bs.setStatus(1);
                    String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.STA_ALREADY_FINISHED), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                    bs.setMsg(errorMsg);
                } else if (sta.getStatus().getValue() == 17) {
                    bs.setStatus(1);
                    String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.STA_CANCELED), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                    bs.setMsg(errorMsg);
                }
            }
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            bs.setStatus(0);
            bs.setMsg(errorMsg);
        } catch (Exception e) {
            bs.setStatus(0);
            String errorMsg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION, new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            bs.setMsg(errorMsg);
        }
        return bs;
    }

    @Override
    public BaseResult cancelOrder(String code) {
        BaseResult bs = new BaseResult();
        try {
            boolean rs = rmiManager.cancelOrder(code);
            if (rs) {
                bs.setStatus(BaseResult.STATUS_SUCCESS);
            } else {
                bs.setStatus(BaseResult.STATUS_ERROR);
            }
        } catch (Exception e) {
            log.error("", e);
            bs.setStatus(BaseResult.STATUS_ERROR);
        }
        return bs;
    }

    @Override
    public BaseResult cancelOrderBatchCode(String code) {
        BaseResult bs = new BaseResult();
        try {
            boolean rs = rmiManager.cancelBatchCode(code);
            if (rs) {
                bs.setStatus(BaseResult.STATUS_SUCCESS);
            } else {
                bs.setStatus(BaseResult.STATUS_ERROR);
            }
        } catch (Exception e) {
            log.error("", e);
            bs.setStatus(BaseResult.STATUS_ERROR);
        }
        return bs;
    }

    /**
     * 获取订单商品批次
     */
    @Override
    public OrderResult orderSkuBatchCode(Order order) {
        OrderResult or = new OrderResult();
        try {
            // 作业类型
            int type = order.getType();
            try {
                StockTransApplicationType.valueOf(type);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.STA_TYPE_ERROR);
            }
            // 样品费用化
            if (type == StockTransApplicationType.SAMPLE_OUTBOUND.getValue()) {
                if (order.getCode() == null) {
                    // 接口数据中前置单据号为空
                    throw new BusinessException(ErrorCode.EI_SLIP_CODE_IS_NULL);
                }
                or = rmiManager.orderSkuBatchCode(order);
            } else {
                throw new BusinessException(ErrorCode.RMI_SERVICE_DOES_NOTSUPPORT_TYPE);
            }
        } catch (BusinessException e) {
            log.debug("", e);
            String errorMsg = "";
            if (e.getLinkedException() == null) {
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            } else {
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs(), Locale.SIMPLIFIED_CHINESE);
                }
            }
            or.setStatus(OrderResult.STATUS_ERROR);
            or.setMsg(errorMsg);
        } catch (Exception e) {
            log.error("", e);
            String errorMsg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SYSTEM_ERROR, new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            or.setStatus(OrderResult.STATUS_ERROR);
            or.setMsg(errorMsg);
        }
        return or;
    }

    /**
     * 盘点审核不通过
     */
    @Override
    public BaseResult rejectInvCheck(String code) {
        BaseResult bs = new BaseResult();
        try {
            boolean rs = rmiManager.rejectInvCheck(code);
            if (rs) {
                bs.setStatus(BaseResult.STATUS_SUCCESS);
            } else {
                bs.setStatus(BaseResult.STATUS_ERROR);
            }
        } catch (Exception e) {
            bs.setStatus(BaseResult.STATUS_ERROR);
        }
        return bs;
    }

    @Override
    public List<BaseResult> orderCreate(List<Order> order) {
        List<BaseResult> baseResults = new ArrayList<BaseResult>();

        for (int i = 0; i < order.size(); i++) {
            BaseResult result = orderCreate(order.get(i));
            baseResults.add(result);
        }
        return baseResults;
    }

    public void validateBasis(Order order) {
        if (order.getMainWhOuId() == null) {
            // 接口数据中仓库组织为空!
            throw new BusinessException(ErrorCode.EI_MAIN_WH_IS_NULL);
        }
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());
        if (ou1 == null) {
            // 接口数据对应的仓库组织不存在!
            throw new BusinessException(ErrorCode.EI_MAIN_WH_NOT_EXISTS);
        }
        if (!StringUtils.hasLength(order.getOwner())) {
            // 接口数据中店铺为空!
            throw new BusinessException(ErrorCode.EI_START_OWNER_IS_NULL);
        }
        BiChannel shop = biChannelDao.getByCode(order.getOwner());
        if (shop == null) {
            // 接口数据对应的店铺不存在!
            throw new BusinessException(ErrorCode.EI_OWNER_NOT_EXISTS, new Object[] {order.getOwner()});
        }
    }

    @Override
    public BaseResult unlockOrderOut(String code, List<Invoice> invoices) {
        BaseResult bs = new BaseResult();
        try {

            List<StockTransApplication> sta = staDao.findStaByCodeNotCancel(code);
            if (sta == null) {
                StringBuffer errorMsg = new StringBuffer();
                throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND, new Object[] {errorMsg});
            } else {
                // 未完成、非取消的
                if (sta.size() == 2) {
                    for (int i = 0; i < sta.size(); i++) {
                        if (sta.get(i).getType().getValue() == StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue()) {
                            if (sta.get(i).getIsLocked()) {
                                if (sta.get(i).getStatus().getValue() == StockTransApplicationStatus.CREATED.getValue()) {
                                    saveStaInvoice(invoices, sta.get(i));
                                } else {
                                    StringBuffer errorMsg = new StringBuffer();
                                    throw new BusinessException(ErrorCode.ERROR_STA_NOT_STATUS, new Object[] {errorMsg});
                                }
                                sta.get(i).setIsLocked(false);
                                staDao.save(sta.get(i));
                                switch (sta.get(i).getRefSlipType()) {
                                    case RETURN_REQUEST:
                                        if (sta != null) {
                                            // 判断换货出库单据仓库是否是外包仓
                                            Warehouse outWh = baseManger.findWarehouseByOuId(sta.get(i).getMainWarehouse().getId());
                                            if (outWh != null && StringUtils.hasText(outWh.getVmiSource())) {
                                                // 生成出库通知
                                                generateRaOutboundMsgForVmi(sta.get(i), outWh);
                                            }
                                        }
                                        String innerShopCode = sta.get(i).getOwner();
                                        if (innerShopCode != null) {
                                            BiChannel sh = biChannelDao.getByCode(innerShopCode);
                                            if (sh != null && sh.getVmiCode() != null) {
                                                VmiInterface vf = vmiFactory.getBrandVmi(sh.getVmiCode());
                                                vf.generateInvStatusChangeByInboundSta(sta.get(i));
                                            }
                                        }
                                        // 若店铺上设置为不需要同步so处理反馈信息， 则返回不处理。
                                        // mqManager.createMqSoResultLog(sta.get(i).getId(),
                                        // MqSoResultLog.MQ_SR_LOG_OP_TYPE_RA_IN);
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                saveStaInvoice(invoices, sta.get(i));
                            }
                        }
                    }

                } else {
                    for (int i = 0; i < sta.size(); i++) {
                        if (sta.get(i).getType().getValue() == StockTransApplicationType.INBOUND_RETURN_REQUEST.getValue()) {
                            if (sta.get(i).getStatus().getValue() == StockTransApplicationStatus.FINISHED.getValue()) {
                                saveStaInvoice(invoices, sta.get(i));
                            }
                        } else {
                            StringBuffer errorMsg = new StringBuffer();
                            throw new BusinessException(ErrorCode.ERROR_STA_NOT_STATUS, new Object[] {errorMsg});
                        }
                    }
                }

                bs.setStatus(1);
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_FINISHED), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                // 执行成功
                bs.setMsg(errorMsg);

            }
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            bs.setStatus(0);
            bs.setMsg(errorMsg);
        } catch (Exception e) {
            log.error("", e);
            bs.setStatus(0);
            String errorMsg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION, new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            bs.setMsg(errorMsg);
        }
        return bs;
    }

    /**
     * 生成换货出库通知消息
     * 
     * @param sta
     * @param wh
     */
    private void generateRaOutboundMsgForVmi(StockTransApplication sta, Warehouse wh) {
        // VMI 判断是否是vmi
        StaDeliveryInfo stainfo = sta.getStaDeliveryInfo();
        MsgOutboundOrder msg = new MsgOutboundOrder();
        Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
        msg.setUuid(id);
        msg.setSource(wh.getVmiSource());
        msg.setSourceWh(wh.getVmiSourceWh());
        msg.setStaCode(sta.getCode());
        if (sta.getStaDeliveryInfo() != null && StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
            msg.setLpCode(sta.getStaDeliveryInfo().getLpCode());
        }
        msg.setTransferFee(stainfo.getTransferFee());
        msg.setReceiver(stainfo.getReceiver());
        msg.setZipcode(stainfo.getZipcode());
        msg.setStaType(sta.getType().getValue());
        msg.setTelePhone(stainfo.getTelephone());
        msg.setMobile(stainfo.getMobile());
        msg.setCountry(stainfo.getCountry());
        msg.setProvince(stainfo.getProvince());
        msg.setCity(stainfo.getCity());
        msg.setDistrict(stainfo.getDistrict());
        msg.setAddress(stainfo.getAddress());
        msg.setAddressEnglish(stainfo.getAddressEn());
        msg.setCityEnglish(stainfo.getCityEn());
        msg.setRemarkEnglish(stainfo.getRemarkEn());
        msg.setReceiverEnglish(stainfo.getReceiverEn());
        msg.setProvinceEnglish(stainfo.getProvinceEn());
        msg.setDistrictEnglish(stainfo.getDistrictEn());
        msg.setTotalActual(sta.getTotalActual());
        msg.setRemark(stainfo.getRemark());
        msg.setStatus(DefaultStatus.CREATED);
        // 如果wh的isSFolorder值为true，并且VMI_SOURCE有值，则对MsgOutboundOrder进行加锁
        if (wh.getVmiSource() != null && wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() == true && stainfo.getLpCode() != null
                && (Transportator.SF.equals(stainfo.getLpCode()) || Transportator.SFCOD.equals(stainfo.getLpCode()) || Transportator.SFDSTH.equals(stainfo.getLpCode()))) {
            msg.setIsLocked(true);
        }
        // 是否货到付款与付款金额
        msg.setIsCodOrder(stainfo.getIsCod());
        if (stainfo.getTransferFee() != null) {
            BigDecimal cost = stainfo.getTransferFee().add(sta.getTotalActual());
            msg.setTotalActual(cost);
        }

        if (msg.getShopId() == null && sta.getOwner() != null) {
            BiChannel shop = biChannelDao.getByCode(sta.getOwner());
            if (shop != null) {
                msg.setShopId(shop.getId());
            }
        }
        MsgOutboundOrder m = msgOutboundOrderDao.save(msg);
        List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
        for (StaLine line : stalines) {
            MsgOutboundOrderLine msgline = new MsgOutboundOrderLine();
            msgline.setMsgOrder(m);
            msgline.setSkuName(line.getSkuName());
            msgline.setQty(line.getQuantity());
            msgline.setSku(line.getSku());
            msgline.setUnitPrice(line.getUnitPrice());
            msgline.setTotalActual(line.getTotalActual());
            msgline = msgOutboundOrderLineDao.save(msgline);
        }
        // 记录外包仓发票信息
        if (wh.getIsOutInvoice() != null && wh.getIsOutInvoice() && stainfo.getStoreComIsNeedInvoice() != null && stainfo.getStoreComIsNeedInvoice()) {
            // 查票
            List<StaInvoice> ivList = staInvoiceDao.getBySta(sta.getId());
            for (StaInvoice iv : ivList) {
                MsgInvoice mic = new MsgInvoice();
                mic.setAmt1(iv.getAmt());
                mic.setCreateTime(new Date());
                mic.setDrawer(iv.getDrawer());
                mic.setInvoiceTime(iv.getInvoiceDate());
                mic.setItem1(iv.getItem());
                mic.setPayee(iv.getPayee());
                mic.setPayer(iv.getPayer());
                mic.setQty1(iv.getQty() == null ? 0L : iv.getQty().longValue());
                mic.setRemark(iv.getMemo());
                mic.setSource(wh.getVmiSource());
                mic.setSourceWh(wh.getVmiSourceWh());
                mic.setStaCode(sta.getCode());
                mic.setStatus(DefaultStatus.CREATED);
                msgInvoiceDao.save(mic);
            }
        }
    }

    private void saveStaInvoice(List<Invoice> invoices, StockTransApplication sta) {
        List<StaInvoice> staInvoices = staInvoiceDao.getBySta(sta.getId());
        if (staInvoices.size() == 0) {
            for (Invoice invoice : invoices) {
                StaInvoice staInvoice = new StaInvoice();
                staInvoice.setAmt(invoice.getAmt());
                staInvoice.setDrawer(invoice.getDrawer());
                staInvoice.setInvoiceDate(invoice.getInvoiceDate());
                staInvoice.setItem(invoice.getItem());
                staInvoice.setMemo(invoice.getMemo());
                staInvoice.setPayee(invoice.getPayee());
                staInvoice.setPayer(invoice.getPayer());
                staInvoice.setQty(invoice.getQty());
                staInvoice.setUnitPrice(invoice.getUnitPrice());
                staInvoice.setAddress(invoice.getAddress());
                staInvoice.setIdentificationNumber(invoice.getIdentificationNumber());
                staInvoice.setTelephone(invoice.getTelephone());
                staInvoice.setSta(sta);
                staInvoice.setIdentificationNumber(invoice.getIdentificationNumber());
                staInvoice.setTelephone(invoice.getTelephone());
                staInvoice.setAddress(invoice.getAddress());
                staInvoiceDao.save(staInvoice);
                for (InvoiceLine invoiceLine : invoice.getDetials()) {
                    StaInvoiceLine staInvoiceLine = new StaInvoiceLine();
                    staInvoiceLine.setAmt(invoiceLine.getAmt());
                    staInvoiceLine.setItem(invoiceLine.getIteam());
                    staInvoiceLine.setQty(invoiceLine.getQty());
                    staInvoiceLine.setStaInvoice(staInvoice);
                    staInvoiceLine.setUnitPrice(invoiceLine.getUnitPrice());
                    staInvoiceLineDao.save(staInvoiceLine);
                }
            }
        }
    }

    /**
     * RMI更新商品
     */
    @Override
    public BaseResult updateSku(RmiSku rmiSku) {
        BaseResult rs = new BaseResult();
        try {
            skuManager.rmiUpdateSku(rmiSku);
            rs.setStatus(BaseResult.STATUS_SUCCESS);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(rmiSku.getCustomerSkuCode(), e);
            }
            rs.setStatus(BaseResult.STATUS_ERROR);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                rs.setMsg(applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE));
            } else {
                rs.setMsg("system error.");
            }
        }
        return rs;
    }

    /**
     * 根据客户编码查询渠道信息
     */
    @Override
    public List<RmiChannelInfo> getChannelListByCustomer(String code) {
        return baseinfoManager.findAllRmiChannelInofByCustomer(code);
    }

    @Override
    public RmiChannelInfo getChannelInfoByCode(String code) {
        return baseinfoManager.findRmiChannelRefWarehouse(code);
    }

    /**
     * 保存出库信息
     */
    public BaseResult saveoutboundOrder(OmsOutInfo out) {
        BaseResult bs = new BaseResult();
        if (out.getSlipCode() != null && out.getLogistic() != null && out.getTrackingCode() != null) {
            List<StockTransApplication> sta = staDao.findBySlipCode(out.getSlipCode());
            if (sta != null && sta.size() > 0) {
                if (out.getStaCode() == null) {
                    for (StockTransApplication s : sta) {
                        if (s.getCode() == null) {
                            bs.setStatus(BaseResult.STATUS_ERROR);
                            bs.setMsg("作业单关联的作业单的staCode为空");
                            return bs;
                        } else {
                            out.setStaCode(s.getCode());
                        }
                    }
                }
                StockTransApplication entity = staDao.getByCode(out.getStaCode());
                MsgRtnOutbound outOrder = new MsgRtnOutbound();
                outOrder.setStatus(DefaultStatus.CREATED);
                outOrder.setSource("OMS");
                if (null != entity && entity.getIsSn()) {
                    outOrder.setIsSn(1);
                } else {
                    outOrder.setIsSn(0);
                }
                outOrder.setStaCode(out.getStaCode());
                outOrder.setSlipCode(out.getSlipCode());
                outOrder.setLpCode(out.getLogistic());
                outOrder.setTrackingNo(out.getTrackingCode());
                if (null != out.getWeight() && !"".equals(out.getWeight())) {
                    log.debug("==========");
                    outOrder.setWeight(new BigDecimal(out.getWeight()));
                }
                outOrder.setCreateTime(new Date());
                outOrder.setVersion(0);
                outOrder.setIsSend(0);
                outOrder = msgRtnDao.save(outOrder);
                bs.setStatus(BaseResult.STATUS_SUCCESS);
                bs.setMsg("出库反馈数据保存成功");
                msgRtnDao.flush();
            } else {
                bs.setStatus(BaseResult.STATUS_ERROR);
                bs.setMsg("作业单不存在");
            }
        } else {
            bs.setStatus(BaseResult.STATUS_ERROR);
            bs.setMsg("单据号/快递简称/运单号不能为空");
        }
        return bs;
    }

    @Override
    public BaseResult procurementOrder(String code) {
        BaseResult bs = new BaseResult();
        try {
            boolean rs = rmiManager.procurementOrder(code);
            if (rs) {
                bs.setStatus(BaseResult.STATUS_SUCCESS);
            } else {
                bs.setStatus(BaseResult.STATUS_ERROR);
            }
        } catch (BusinessException e) {
            log.error("", e);
            bs.setStatus(BaseResult.STATUS_ERROR);
            bs.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("", e);
            bs.setStatus(BaseResult.STATUS_ERROR);
        }
        return bs;
    }

    /**
     * 查询当前可用可存的效期范围
     */
    @Override
    public RmiSku findDateBySkuExtCode1(String owner, String skuExtCode1) {
        boolean isQuery = checkDate(); // 查询时间限制
        if (!isQuery) {
            return null;
        }
        InventoryCommand inv = inventoryDao.findInventoryByCode1(skuExtCode1, owner, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
        RmiSku sku = new RmiSku();
        if (!StringUtil.isEmpty(inv.getExtCode1())) {
            sku.setOwner(inv.getShopName());
            sku.setExtensionCode1(inv.getExtCode1());
            sku.setMaxExpDate(inv.getMaxExpDate());
            sku.setMinExpDate(inv.getMinExpDate());
            sku.setMaxProductDate(inv.getMaxPDate());
            sku.setMinProductDate(inv.getMinPDate());
        }
        return sku;
    }

    /**
     * 查询当前可用可存的效期范围 最大支持100条
     */
    @Override
    public List<RmiSku> findListBySkuExtCode1(String owner, List<String> skuExtCode1) {
        boolean isQuery = checkDate(); // 查询时间限制
        if (!isQuery) {
            return null;
        }
        int temp = 0;
        List<RmiSku> list = new ArrayList<RmiSku>();
        for (String string : skuExtCode1) {
            temp++;
            if (temp >= 100) {
                continue;
            }
            InventoryCommand inv = inventoryDao.findInventoryByCode1(string, owner, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
            if (!StringUtil.isEmpty(inv.getExtCode1())) {
                RmiSku sku = new RmiSku();
                sku.setOwner(inv.getShopName());
                sku.setExtensionCode1(inv.getExtCode1());
                sku.setMaxExpDate(inv.getMaxExpDate());
                sku.setMinExpDate(inv.getMinExpDate());
                sku.setMaxProductDate(inv.getMaxPDate());
                sku.setMinProductDate(inv.getMinPDate());
                list.add(sku);
            }
        }
        return list;
    }

    /**
     * 查询时间限制。9点-22点 限制查询
     * 
     * @return
     */
    private boolean checkDate() {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        String sysDate = df.format(new Date());
        String date1 = "09:00:00";
        String date2 = "22:00:00";
        try {
            Date sysd = df.parse(sysDate);
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            if (sysd.getTime() >= d1.getTime() && sysd.getTime() <= d2.getTime()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("checkDate-parse-exception", e);
            }
        }
        return false;
    }



    /**
     * oms通知wms上传文件
     */
    public BaseResult createupLoadTask() {
        BaseResult bs = new BaseResult();
        // 记录系统日志
        systemLogDao.insertSystemLog("fullInventory", "interfaceZipFileCM", "Execute"); // (接口接收上传文件执行)
        String fileName = totalInvManager.getFileName();
        String date = String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd"));
        if (!fileName.contains(date)) { // 判断是否存在当天
            // 记录系统日志
            systemLogDao.insertSystemLog("fullInventory", "interfaceZipFile", "noFile"); // (接口接收上传文件失败)
            bs.setStatus(BaseResult.STATUS_ERROR);
            bs.setMsg("不存在当日库存文件");
        } else {
            // 记录系统日志
            systemLogDao.insertSystemLog("fullInventory", "interfaceZipFileCM", "Success"); // (接口接收上传文件成功)
            // 生成上传任务
            wholeTaskLogDao.insertTaskLog(WholeTaskLogType.INV_FILE_UPLOAD.getValue(), WholeTaskLogStatus.NEW.getValue());
            bs.setStatus(BaseResult.STATUS_SUCCESS);
            bs.setMsg("接收成功");
        }
        return bs;
    }

    /**
     * 异常包裹指令状态反馈接口
     * 
     * @param code wms单据编码
     * @param slipCode 反馈相关单据号
     * @return
     */
    public BaseResult PackageResult(PackageResult result) {
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
                        ra.setOmsRemark(result.getRemark());
                        ra.setReturnSlipCode1(result.getSlipCode());
                        ra.setSatus(ReturnApplicationStatus.NO_RELATION_TO_BE_SURE);
                        bs.setStatus(BaseResult.STATUS_SUCCESS);
                        bs.setMsg("接收成功");
                    }

                } else { // 无法确认
                    ra.setOmsStatus(3);
                    ra.setOmsRemark(result.getRemark());
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

    /**
     * 通用VMI接收收货指令
     */
    @Override
    public WmsResponse VmiAsn(VmiAsn vmiAsn) {
        WmsResponse wr = new WmsResponse();
        // 初步校验数据完整性
        if (StringUtil.isEmpty(vmiAsn.getUuid())) {
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("uuid为空");
            wr.setUuid(vmiAsn.getUuid());
            return wr;
        }
        if (StringUtil.isEmpty(vmiAsn.getStoreCode())) {
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("品牌店铺编码为空");
            wr.setUuid(vmiAsn.getUuid());
            return wr;
        }
        if (StringUtil.isEmpty(vmiAsn.getOrderCode())) {
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("收货单据编码为空");
            wr.setUuid(vmiAsn.getUuid());
            return wr;
        }
        VmiAsnLine[] vLines = vmiAsn.getAsnLines();
        for (VmiAsnLine v : vLines) {
            if (StringUtil.isEmpty(v.getCartonNo())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("入库单箱号为空");
                wr.setUuid(vmiAsn.getUuid());
                return wr;
            }
            if (StringUtil.isEmpty(v.getUpc())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("商品upc为空");
                wr.setUuid(vmiAsn.getUuid());
                return wr;
            }
            if (v.getQty() == null) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("数量为空");
                wr.setUuid(vmiAsn.getUuid());
                return wr;
            }
            if (v.getQty().intValue() <= 0) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("数量必须大于0");
                wr.setUuid(vmiAsn.getUuid());
                return wr;
            }
        }
        try {
            wr = rmiManager.vmiAsn(vmiAsn, wr);
        } catch (Exception e) {
            log.error("", e);
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("系统异常");
            wr.setUuid(vmiAsn.getUuid());
        }
        return wr;
    }

    /**
     * 扩展VMI接收收货指令
     */
    @Override
    public List<WmsResponse> vmiAsnExt(List<VmiAsn> vmiAsnList) {
        List<WmsResponse> wrList = new ArrayList<WmsResponse>();
        if (null == vmiAsnList || vmiAsnList.size() == 0) {
            return null;
        }
        for (VmiAsn vmiAsn : vmiAsnList) {
            WmsResponse wr = new WmsResponse();
            wr.setStatus(WmsResponse.STATUS_SUCCESS);
            wr.setOrderCode(vmiAsn.getOrderCode());
            // 校验数据完整性
            if (StringUtil.isEmpty(vmiAsn.getUuid())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("uuid为空");
                wr.setUuid(vmiAsn.getUuid());
            }
            if (StringUtil.isEmpty(vmiAsn.getStoreCode())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "品牌店铺编码为空");
                wr.setUuid(vmiAsn.getUuid());
            }
            if (StringUtil.isEmpty(vmiAsn.getOrderCode())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "收货单据编码为空");
                wr.setUuid(vmiAsn.getUuid());
            }
            VmiAsnLine[] vLines = vmiAsn.getAsnLines();
            for (VmiAsnLine v : vLines) {
                if (StringUtil.isEmpty(v.getCartonNo())) {
                    wr.setStatus(WmsResponse.STATUS_ERROR);
                    wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "入库单箱号为空");
                    wr.setUuid(vmiAsn.getUuid());
                }
                if (StringUtil.isEmpty(v.getUpc())) {
                    wr.setStatus(WmsResponse.STATUS_ERROR);
                    wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "商品upc为空");
                    wr.setUuid(vmiAsn.getUuid());
                }
                if (v.getQty() == null) {
                    wr.setStatus(WmsResponse.STATUS_ERROR);
                    wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "数量为空");
                    wr.setUuid(vmiAsn.getUuid());
                }
            }
            try {
                if (WmsResponse.STATUS_SUCCESS == wr.getStatus()) {
                    wr = rmiManager.vmiAsn(vmiAsn, wr);
                }
            } catch (Exception e) {
                log.error("====>hub call vmiAsnExt exception:", e);
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("系统异常");
                wr.setUuid(vmiAsn.getUuid());
            }
            wrList.add(wr);
        }
        return wrList;
    }

    /**
     * VMI接收退仓指令
     */
    @Override
    public WmsResponse vmiRto(VmiRto vmiRto) {
        WmsResponse wr = new WmsResponse();
        // 校验数据完整性
        if (StringUtil.isEmpty(vmiRto.getUuid())) {
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("uuid为空");
            wr.setUuid(vmiRto.getUuid());
            return wr;
        }
        if (StringUtil.isEmpty(vmiRto.getStoreCode())) {
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("品牌店铺编码为空");
            wr.setUuid(vmiRto.getUuid());
            return wr;
        }
        if (StringUtil.isEmpty(vmiRto.getOrderCode())) {
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("单据编码为空");
            wr.setUuid(vmiRto.getUuid());
            return wr;
        }
        VmiRtoLine[] vLines = vmiRto.getRtoLines();
        for (VmiRtoLine v : vLines) {
            if (StringUtil.isEmpty(v.getCartonNo())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("箱号为空");
                wr.setUuid(vmiRto.getUuid());
                return wr;
            }
            if (StringUtil.isEmpty(v.getUpc())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("商品upc为空");
                wr.setUuid(vmiRto.getUuid());
                return wr;
            }
            if (v.getQty() == null) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("数量为空");
                wr.setUuid(vmiRto.getUuid());
                return wr;
            }
        }
        try {
            wr = rmiManager.vmiRto(vmiRto, wr);
        } catch (Exception e) {
            log.error("====>hub call vmiRto exception:", e);
            wr.setStatus(WmsResponse.STATUS_ERROR);
            wr.setMsg("系统异常");
            wr.setUuid(vmiRto.getUuid());
        }
        return wr;
    }

    /**
     * 扩展VMI接收退仓指令
     */
    @Override
    public List<WmsResponse> vmiRtoExt(List<VmiRto> vmiRtoList) {
        if (null == vmiRtoList || vmiRtoList.size() == 0) {
            return null;
        }
        List<WmsResponse> wrList = new ArrayList<WmsResponse>();
        for (VmiRto vmiRto : vmiRtoList) {
            WmsResponse wr = new WmsResponse();
            wr.setStatus(WmsResponse.STATUS_SUCCESS);
            wr.setOrderCode(vmiRto.getOrderCode());
            // 校验数据完整性
            if (StringUtil.isEmpty(vmiRto.getUuid())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("uuid为空");
                wr.setUuid(vmiRto.getUuid());
            }
            if (StringUtil.isEmpty(vmiRto.getStoreCode())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "品牌店铺编码为空");
                wr.setUuid(vmiRto.getUuid());
            }
            if (StringUtil.isEmpty(vmiRto.getOrderCode())) {
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "单据编码为空");
                wr.setUuid(vmiRto.getUuid());
            }
            VmiRtoLine[] vLines = vmiRto.getRtoLines();
            for (VmiRtoLine v : vLines) {
                if (StringUtil.isEmpty(v.getCartonNo())) {
                    wr.setStatus(WmsResponse.STATUS_ERROR);
                    wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "箱号为空");
                    wr.setUuid(vmiRto.getUuid());
                }
                if (StringUtil.isEmpty(v.getUpc())) {
                    wr.setStatus(WmsResponse.STATUS_ERROR);
                    wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "商品upc为空");
                    wr.setUuid(vmiRto.getUuid());
                }
                if (v.getQty() == null) {
                    wr.setStatus(WmsResponse.STATUS_ERROR);
                    wr.setMsg((StringUtil.isEmpty(wr.getMsg()) ? "" : (wr.getMsg() + ",")) + "数量为空");
                    wr.setUuid(vmiRto.getUuid());
                }
            }
            try {
                if (WmsResponse.STATUS_SUCCESS == wr.getStatus()) {
                    wr = rmiManager.vmiRto(vmiRto, wr);
                }
            } catch (Exception e) {
                log.error("====>hub call vmiRtoExt exception:", e);
                wr.setStatus(WmsResponse.STATUS_ERROR);
                wr.setMsg("系统异常");
                wr.setUuid(vmiRto.getUuid());
            }
            wrList.add(wr);
        }
        return wrList;
    }

    @Override
    public BaseResult cancelStaByOrderCode(String code) {
        BaseResult bs = new BaseResult();
        try {
            log.info("so code is :[{}]", code);
            boolean result = staCancelManager.cancelSalesStaBySlipCode(code);
            if (result) {
                bs.setStatus(1);
                bs.setMsg("success");
            } else {
                bs.setStatus(5);
                bs.setMsg("wait");
            }
        } catch (BusinessException e) {
            log.warn("cancelStaByOrderCode ,code :{},error code : {}", code, e.getErrorCode());
            bs.setStatus(0);
            if (e.getErrorCode() == ErrorCode.STA_NOT_FOUND) {
                bs.setCode("NoFindRoNum");
            }
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            bs.setMsg(errorMsg);
            BusinessException le = e.getLinkedException();
            if (le != null) {
                log.error("cancel sta error : error code is : [{}]", le.getErrorCode());
                String errorMsg1 = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + le.getErrorCode()), le.getArgs(), Locale.SIMPLIFIED_CHINESE);
                bs.setMsg(errorMsg1);
            }
        }
        return bs;
    }


    @Override
    public List<WarehousePriority> getPriorityWHByCity(String province, String city, Long cusId, Long cId) {
        List<WarehousePriority> wList = new ArrayList<WarehousePriority>();
        wList = rmiManager.getPriorityWHByCity(province, city, cusId, cId);
        return wList;
    }

    @Override
    public BaseResult cancelProcurement(String code) {
        BaseResult bs = new BaseResult();
        try {
            boolean rs = rmiManager.cancelProcurement(code);
            if (rs) {
                bs.setStatus(BaseResult.STATUS_SUCCESS);
            } else {
                bs.setStatus(BaseResult.STATUS_ERROR);
            }
        } catch (Exception e) {
            log.error("", e);
            bs.setStatus(BaseResult.STATUS_ERROR);
        }
        return bs;
    }

    @Override
    public BaseResult closeProcurement(String code) {
        BaseResult bs = new BaseResult();
        try {
            boolean rs = rmiManager.closeProcurement(code);
            if (rs) {
                bs.setStatus(BaseResult.STATUS_SUCCESS);
            } else {
                bs.setStatus(BaseResult.STATUS_ERROR);
            }
        } catch (Exception e) {
            log.error("", e);
            bs.setStatus(BaseResult.STATUS_ERROR);
        }
        return bs;
    }

    @Override
    public InvoiceOrderResult invoiceOrderService(String systemKey, InvoiceOrder order) {
        InvoiceOrderResult result = new InvoiceOrderResult();
        if (!Constants.TOMS.equals(systemKey) && !Constants.GOMS.equals(systemKey) && !Constants.PACS.equals(systemKey) && !Constants.HKOMS.equals(systemKey)) {
            result.setStatus(0);// 1：接收成功0：接收失败
            result.setOrderCode(order.getOrderCode());
            result.setMemo("系统来源标识错误！");
            return result;
        }
        if (order.getOrderCode() != null) {
            List<WmsInvoiceOrderCommand> ww = wmsInvoiceOrderDao.findWmsInvoiceOrderIsExist(order.getOrderCode(), new BeanPropertyRowMapperExt<WmsInvoiceOrderCommand>(WmsInvoiceOrderCommand.class));
            if (ww.size() > 0) {
                result.setStatus(0);// 1：接收成功0：接收失败
                result.setOrderCode(order.getOrderCode());// pac的订单号
                result.setMemo("发票订单不能重复发送 ！");
                return result;
            }
        }
        if (order.getTransCode() != null) {
            if (!(order.getTransCode().equals(Transportator.SF) || order.getTransCode().equals(Transportator.STO) || order.getTransCode().equals(Transportator.EMS))) {
                result.setStatus(0);// 1：接收成功0：接收失败
                result.setOrderCode(order.getOrderCode());// pac的订单号
                result.setMemo("物流商只支持SF,STO,EMS");
                return result;
            }
        } else {
            order.setTransCode(Transportator.EMS);
        }
        WmsInvoiceOrder wio = new WmsInvoiceOrder();
        BeanUtils.copyProperties(order, wio);
        wio.setInvoiceTelephone(order.getInvoiceTelephone());
        wio.setInvoiceAddress(order.getInvoiceAddress());
        wio.setIdentificationNumber(order.getIdentificationNumber());
        wio.setCreateTime(new Date());
        wio.setBatchCode(null);// 批次号
        wio.setVersion(0);
        wio.setStatus(StockTransApplicationStatus.CREATED);
        wio.setPgIndex(null);// 批次顺序号
        wio.setOrderCode(order.getOrderCode());// wms订单号
        wio.setLpCode(order.getTransCode());
        wio.setTransNo(null);
        wio.setOwner(order.getOwner());
        wio.setProvince(order.getProvince());
        wio.setCity(order.getCity());
        wio.setDistrict(order.getDistrict());
        wio.setAddress(order.getAddress());
        if (order.getTelephone() == null && order.getMobile() == null) {
            result.setStatus(0);// 1：接收成功0：接收失败
            result.setOrderCode(order.getOrderCode());
            result.setMemo("电话或手机必须保留一个！");
            return result;
        } else {
            wio.setTelephone(order.getTelephone() == null ? order.getMobile() : order.getTelephone());
            wio.setMobile(order.getMobile() == null ? order.getTelephone() : order.getMobile());
        }
        wio.setReceiver(order.getReceiver());
        wio.setZipcode(order.getZipcode());
        wio.setSystemKey(systemKey);
        wio.setLastModifyTime(new Date());
        wmsInvoiceOrderDao.save(wio);
        List<WmsOrderInvoice> woi = order.getInvoices();
        for (WmsOrderInvoice woInvoice : woi) {
            StaInvoice si = new StaInvoice();
            si.setInvoiceDate(woInvoice.getInvoiceDate().substring(0, 19));
            si.setPayer(woInvoice.getPayer());
            si.setInvoiceCode(woInvoice.getInvoiceCode());//
            si.setItem(woInvoice.getItem());
            si.setQty(woInvoice.getQty().intValue());
            si.setUnitPrice(woInvoice.getUnitPrice());
            si.setAmt(woInvoice.getAmt());
            si.setMemo(woInvoice.getMemo());
            si.setPayee(woInvoice.getPayee());
            si.setCompany(woInvoice.getCompany());
            si.setDrawer(woInvoice.getDrawer());
            si.setInvoiceOrder(wio);// 发票单
            si.setAddress(woInvoice.getAddress());
            si.setIdentificationNumber(woInvoice.getIdentificationNumber());
            si.setTelephone(woInvoice.getTelephone());
            staInvoiceDao.save(si);

            List<WmsOrderInvoiceLine> woil = woInvoice.getDetials();
            if (woil != null) {
                for (WmsOrderInvoiceLine woiLine : woil) {
                    StaInvoiceLine sil = new StaInvoiceLine();
                    sil.setAmt(woiLine.getAmt());
                    sil.setItem(woiLine.getItem());
                    sil.setLineNO(woiLine.getLineNo());
                    sil.setQty(woiLine.getQty().intValue());
                    sil.setStaInvoice(si);
                    sil.setUnitPrice(woiLine.getUnitPrice());
                    staInvoiceLineDao.save(sil);
                }
            }
        }
        result.setOrderCode(order.getOrderCode());
        result.setStatus(1);// 1：接收成功0：接收失败
        return result;
    }

    @Override
    public WmsSalesOrderResult orderCreatelink(String systemKey, WmsSalesOrder order) {
        WmsSalesOrderResult result = new WmsSalesOrderResult();
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsSalesOrderService is" + order.getOrderCode());
            }
            JSONArray jsonArray = JSONArray.fromObject(order);
            WmsSalesOrderLog log = new WmsSalesOrderLog();
            log.setCreateTime(new Date());
            log.setJsonInfo(jsonArray.toString());
            log.setOrderCode(order.getOrderCode());
            logDao.save(log);
            hubWmsManager.wmsSalesOrder(systemKey, order);
            result.setStatus(1);
            result.setOrderCode(order.getOrderCode());
            result.setOrderSource(order.getOrderSource());
            result.setMemo(RECEIVE_SUCCESS);
        } catch (BusinessException e) {
            log.error("wmsSalesOrderService BusinessException:" + order.getOrderCode() + " " + e.getErrorCode());
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            result.setStatus(0);
            if (e.getErrorCode() == ErrorCode.HW_ORDER_EXISTS) {
                result.setStatus(1);
            }
            result.setOrderCode(order.getOrderCode());
            result.setOrderSource(order.getOrderSource());
            result.setMemo(errorMsg);
        } catch (Exception e) {
            log.error("wmsSalesOrderService Exception:" + order.getOrderCode(), e);
            result.setStatus(0);
            result.setOrderCode(order.getOrderCode());
            result.setOrderSource(order.getOrderSource());
            result.setMemo(applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR, null, Locale.SIMPLIFIED_CHINESE));
        }
        return result;
    }

    @Override
    public List<WmsSalesOrderResult> orderCreatelinks(String systemKey, List<WmsSalesOrder> order) {
        if (log.isDebugEnabled()) {
            log.debug("wmsSalesOrderService Begin-----------");
        }
        List<WmsSalesOrderResult> wrl = new ArrayList<WmsSalesOrderResult>();
        for (WmsSalesOrder wo : order) {
            WmsSalesOrderResult result = orderCreatelink(systemKey, wo);
            wrl.add(result);
        }
        return wrl;
    }

    @Override
    public BaseResult updateStoreBrand(String shopCode, List<String> brandCodeList) {
        BaseResult result = new BaseResult();
        try {
            rmiManager.updateStoreBrand(shopCode, brandCodeList);
            result.setStatus(BaseResult.STATUS_SUCCESS);
            result.setMsg("SUCCESS");
        } catch (BusinessException be) {
            log.error("updateStoreBrand error: {}", be.getMessage());
            result.setStatus(BaseResult.STATUS_ERROR);
            result.setMsg(be.getMessage());
        } catch (Exception e) {
            log.error("updateStoreBrand error:", e);
            result.setStatus(BaseResult.STATUS_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
