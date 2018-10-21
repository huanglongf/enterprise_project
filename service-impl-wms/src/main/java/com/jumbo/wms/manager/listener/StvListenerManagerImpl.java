package com.jumbo.wms.manager.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.CnOrderPropertyDao;
import com.jumbo.dao.hub2wms.CnStockInItemInventoryDao;
import com.jumbo.dao.hub2wms.CnStockInOrderLineDao;
import com.jumbo.dao.hub2wms.CnWmsStockInOrderConfirmDao;
import com.jumbo.dao.hub2wms.WmsInfoLogOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceLineOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderInvoiceOmsDao;
import com.jumbo.dao.hub2wms.WmsOrderStatusOmsDao;
import com.jumbo.dao.hub2wms.WmsTransInfoOmsDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.msg.MessageProducerErrorDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.InventoryTransactionToOmsDao;
import com.jumbo.dao.warehouse.InvetoryChangeDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.WmsInvChangeToOmsDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.ExpiredDateInfo;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SnFeedbackInfo;
import com.jumbo.pac.manager.extsys.wms.rmi.model.WarrantyCardResp;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.InvChangeLogNoticPacThread;
import com.jumbo.wms.manager.hub4.WmsOrderServiceToHub4Manager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.manager.vmi.gucciData.GucciManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.hub2wms.WmsInfoLogOms;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLineOms;
import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceOms;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;
import com.jumbo.wms.model.hub2wms.WmsTransInfoOms;
import com.jumbo.wms.model.hub2wms.threepl.CnOrderProperty;
import com.jumbo.wms.model.hub2wms.threepl.CnStockInItemInventory;
import com.jumbo.wms.model.hub2wms.threepl.CnStockInOrderLine;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderConfirm;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MessageProducerError;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.InventoryTransactionToOms;
import com.jumbo.wms.model.warehouse.InvetoryChange;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.ReturnReasonType;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.WmsInvChangeToOms;

import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Service("stvListenerManager")
public class StvListenerManagerImpl extends BaseManagerImpl implements StvListenerManager {
    /**
     * 
     */
    private static final long serialVersionUID = 2515421112149933667L;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    public Rmi4Wms rmi4Wms;
    @Autowired
    public StockTransApplicationDao staDao;
    @Autowired
    public StvLineDao stvLineDao;
    @Autowired
    public GiftLineDao giftLineDao;
    @Autowired
    public BiChannelDao companyShopDao;
    @Autowired
    public SkuDao skuDao;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private WareHouseManagerExe whExe;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WmsTransInfoOmsDao infoOmsDao;
    @Autowired
    private WmsInfoLogOmsDao infoLogOmsDao;
    @Autowired
    private StaInvoiceLineDao invoiceLineDao;
    @Autowired
    private WmsOrderInvoiceOmsDao invoiceOmsDao;
    @Autowired
    private WmsOrderInvoiceLineOmsDao invoiceLineOmsDao;
    @Autowired
    private WmsOrderStatusOmsDao orderStatusOmsDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private PackageInfoDao infoDao;
    @Autowired
    private InventoryTransactionToOmsDao inventoryTransactionToOmsDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private WmsInvChangeToOmsDao wmsInvChangeToOmsDao;
    @Autowired
    private CnWmsStockInOrderConfirmDao cnWmsStockInOrderConfirmDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private CnStockInOrderLineDao cnStockInOrderLineDao;
    @Autowired
    private CnStockInItemInventoryDao cnStockInItemInventoryDao;
    @Autowired
    private CnOrderPropertyDao cnOrderPropertyDao;
    @Autowired
    private GucciManager gucciManager;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private InvetoryChangeDao invetoryChangeDao;

    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private RocketMQProducerServer producerServer;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @Autowired
    MessageProducerErrorDao messageProducerErrorDao;

    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;

    @Autowired
    private WmsOrderServiceToHub4Manager wmsOrderServiceToHub4Manager;

    @Override
    @Transactional
    public void stvFinished(StockTransVoucher stv) {
        // 判断店铺可用性
        whExe.validateBiChannelSupport(stv, null);
        StockTransApplication sta = staDao.getByPrimaryKey(stv.getSta().getId());
        StockTransApplicationType staType = sta.getType();
        // 判断是否调整单
        // if (sta.getType().equals(StockTransApplicationType.INVENTORY_STATUS_CHANGE) &&
        // sta.getRefSlipCode() != null) {
        // InventoryCheck ic = icDao.findByCode(sta.getRefSlipCode());
        // if (ic != null) {
        // return;
        // }
        // }
        if (// 入库
        staType.equals(StockTransApplicationType.SERIAL_NUMBER_INBOUND)// 串号拆分入库
                || staType.equals(StockTransApplicationType.SAMPLE_INBOUND) // 样品领用入库
                || staType.equals(StockTransApplicationType.SKU_EXCHANGE_INBOUND)// 商品置换入库
                || staType.equals(StockTransApplicationType.REAPAIR_INBOUND) // 送修入库
                || staType.equals(StockTransApplicationType.SERIAL_NUMBER_GROUP_INBOUND)// 串号组合入库
                || staType.equals(StockTransApplicationType.INBOUND_PURCHASE) // 采购入库
                || staType.equals(StockTransApplicationType.INBOUND_GIFT)// 赠品入库
                || staType.equals(StockTransApplicationType.SKU_RETURN_INBOUND) // 货返入库
                || staType.equals(StockTransApplicationType.INBOUND_RETURN_PURCHASE) // 采购退货调整入库
                || (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST) && (!SlipType.OUT_RETURN_REQUEST.equals(sta.getRefSlipType()))) // 退换货申请-退货入库
                // 出入库都包括
                || staType.equals(StockTransApplicationType.INVENTORY_STATUS_CHANGE) // 库存状态修改
                || staType.equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) // 同公司调拨
                || staType.equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER) // 不同公司调拨
                || staType.equals(StockTransApplicationType.TRANSIT_CROSS)// 库间移动
                // 出库
                || staType.equals(StockTransApplicationType.OUTBOUND_PURCHASE)// 采购出库
                || staType.equals(StockTransApplicationType.SAMPLE_OUTBOUND) // 样品领用出库
                || staType.equals(StockTransApplicationType.WELFARE_USE) // 福利领用
                || staType.equals(StockTransApplicationType.SERIAL_NUMBER_OUTBOUND) // 串号拆分出库
                || staType.equals(StockTransApplicationType.SERIAL_NUMBER_GROUP_OUTBOUND) // 串号组合出库
                || staType.equals(StockTransApplicationType.SKU_EXCHANGE_OUTBOUND) // 商品置换出库
                || staType.equals(StockTransApplicationType.OUTBOUND_PACKAGING_MATERIALS) // 包材领用出库
                || staType.equals(StockTransApplicationType.FIXED_ASSETS_USE) // 固定资产领用
                || staType.equals(StockTransApplicationType.SCARP_OUTBOUND) // 报废出库
                || staType.equals(StockTransApplicationType.REAPAIR_OUTBOUND) // 送修出库
                || staType.equals(StockTransApplicationType.SALES_PROMOTION_USE) // 促销领用
                || staType.equals(StockTransApplicationType.LOW_VALUE_CONSUMABLE_USE) // 低值易耗品
                || staType.equals(StockTransApplicationType.INBOUND_SETTLEMENT)// 结算经销入库
                || staType.equals(StockTransApplicationType.INBOUND_CONSIGNMENT)// 代销入库
                || staType.equals(StockTransApplicationType.INBOUND_MOBILE)// 移库入库
                || staType.equals(StockTransApplicationType.INBOUND_OTHERS)// 其他入库
                || staType.equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT)// VMI移库入库
        ) {
            if (StringUtils.hasText(sta.getDataSource())) {
                // wmsOrderServiceToHub4Manager.createResponseInfo(sta.getId(), stv.getId());
                return;
            }
            Boolean isMq = false;
            List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_RTN_ORDER_SERVICE, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
            if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
                isMq = true;
            }
            if (StringUtils.hasText(sta.getSystemKey()) && !sta.getSystemKey().equals("pacs")) {
                if (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST) || staType.equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST)) {
                    WmsOrderStatusOms statusOms = new WmsOrderStatusOms();
                    statusOms.setOrderCode(sta.getRefSlipCode());
                    statusOms.setLogTime(new Date());
                    statusOms.setSystemKey(sta.getSystemKey());
                    statusOms.setStatus(1);
                    statusOms.setOwner(sta.getOwner());
                    statusOms.setShippingCode(sta.getCode());
                    statusOms.setType(sta.getType().getValue());
                    if (isMq) {
                        statusOms.setIsMq("1");
                    }
                    orderStatusOmsDao.save(statusOms);
                    // 记录出入库物流信息
                    if (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
                        WmsTransInfoOms infoOms = new WmsTransInfoOms();
                        infoOms.setOrderStatusOms(statusOms);
                        infoOms.setTransCode(sta.getStaDeliveryInfo().getLpCode());
                        infoOms.setTransNo(sta.getStaDeliveryInfo().getTrackingNo());
                        if (sta.getStaDeliveryInfo().getTransTimeType() != null) {
                            infoOms.setTransTimeType(sta.getStaDeliveryInfo().getTransTimeType().getValue());
                        }
                        infoOmsDao.save(infoOms);
                    } else {
                        List<PackageInfo> infos = infoDao.findByStaId(sta.getId());
                        for (PackageInfo info : infos) {
                            WmsTransInfoOms infoOms = new WmsTransInfoOms();
                            infoOms.setOrderStatusOms(statusOms);
                            infoOms.setTransCode(info.getLpCode());
                            infoOms.setTransNo(info.getTrackingNo());
                            if (sta.getStaDeliveryInfo().getTransTimeType() != null) {
                                infoOms.setTransTimeType(sta.getStaDeliveryInfo().getTransTimeType().getValue());
                            }
                            infoOmsDao.save(infoOms);
                        }
                    }
                    Map<String,String> skuLineNoMap=new HashMap<String,String>();
                    List<StaLine> staLineList=staLineDao.findByStaId(sta.getId());
                    if(null!=staLineList&&staLineList.size()>0){
                        for(StaLine staLine:staLineList){
                            skuLineNoMap.put(staLine.getSku().getId()+"-"+staLine.getQuantity(),staLine.getLineNo());
                        }
                    }
                    // 记录出入库流水
                    List<StockTransTxLogCommand> stockTransTxLogs = stockTransTxLogDao.findLogByStvId(stv.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                    Map<String, List<SkuSnLogCommand>> map = new HashMap<String, List<SkuSnLogCommand>>();
                    for (StockTransTxLogCommand logCommand : stockTransTxLogs) {
                        WmsInfoLogOms infoLogOms = new WmsInfoLogOms();
                        InventoryStatus status = inventoryStatusDao.getByPrimaryKey(logCommand.getStatusId());
                        Sku sku = skuDao.getByPrimaryKey(logCommand.getSkuId());
                        infoLogOms.setSku(sku.getCustomerSkuCode());
                        infoLogOms.setBtachCode(logCommand.getBatchCode());
                        infoLogOms.setTranTime(logCommand.getTranTime());
                        infoLogOms.setOwner(logCommand.getOwner());
                        if(skuLineNoMap.containsKey(logCommand.getSkuId()+"-"+logCommand.getQuantity())){
                            infoLogOms.setLineNo(skuLineNoMap.get(logCommand.getSkuId()+"-"+logCommand.getQuantity()));
                        }
                        // 新增是否可售By FXL
                        if (logCommand.getMarketAbility() == 1) {
                            infoLogOms.setIsSales(true);
                        } else {
                            infoLogOms.setIsSales(false);
                        }
                        if (status.getName().equals("良品")) {
                            infoLogOms.setInvStatus("normal");
                        } else if (status.getName().equals("残次品")) {
                            infoLogOms.setInvStatus("damage");
                        } else {
                            infoLogOms.setInvStatus("pending");
                        }
                        OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(logCommand.getWhId());
                        infoLogOms.setQty(logCommand.getQuantity());
                        infoLogOms.setWarehouceCode(operationUnit.getCode());
                        sku.setIsSnSku(sku.getIsSnSku() == null ? false : sku.getIsSnSku());
                        if (sku.getIsSnSku()) {
                            List<SkuSnLogCommand> mapstvcmd = map.get(sku.getId().toString());
                            if (mapstvcmd != null) {
                                Long qty = logCommand.getQuantity();
                                for (int i = 0; i < mapstvcmd.size(); i++) {
                                    if (qty == mapstvcmd.size()) {
                                        if (infoLogOms.getSns() == null) {
                                            infoLogOms.setSns(mapstvcmd.get(i).getSn());
                                        } else {
                                            infoLogOms.setSns(infoLogOms.getSns() + "," + mapstvcmd.get(i).getSn());
                                        }
                                    } else {

                                        if (infoLogOms.getSns() == null) {
                                            infoLogOms.setSns(mapstvcmd.get(i).getSn());
                                            --qty;
                                        } else {
                                            infoLogOms.setSns(infoLogOms.getSns() + "," + mapstvcmd.get(i).getSn());
                                            --qty;
                                        }
                                        if (qty == 0) {
                                            String[] sn = infoLogOms.getSns().split(",");
                                            for (int j = 0; j < sn.length; j++) {
                                                for (int h = 0; h < mapstvcmd.size(); h++) {
                                                    if (sn[j].equals(mapstvcmd.get(h).getSn())) {
                                                        mapstvcmd.remove(h);
                                                    }
                                                }
                                            }
                                            map.put(sku.getId().toString(), mapstvcmd);
                                            break;
                                        }
                                    }
                                }

                            } else {
                                List<SkuSnLogCommand> stvcmd = skuSnLogDao.findOutboundSnBySkuId(stv.getId(), sku.getId(), new BeanPropertyRowMapper<SkuSnLogCommand>(SkuSnLogCommand.class));
                                Long qty = logCommand.getQuantity();
                                for (int i = 0; i < stvcmd.size(); i++) {
                                    if (qty == stvcmd.size()) {
                                        if (infoLogOms.getSns() == null) {
                                            infoLogOms.setSns(stvcmd.get(i).getSn());
                                        } else {
                                            infoLogOms.setSns(infoLogOms.getSns() + "," + stvcmd.get(i).getSn());
                                        }
                                    } else {
                                        if (infoLogOms.getSns() == null) {
                                            infoLogOms.setSns(stvcmd.get(i).getSn());
                                            --qty;
                                        } else {
                                            infoLogOms.setSns(infoLogOms.getSns() + "," + stvcmd.get(i).getSn());
                                            --qty;
                                        }
                                    }
                                    if (qty == 0) {
                                        String[] sn = infoLogOms.getSns().split(",");
                                        for (int j = 0; j < sn.length; j++) {
                                            for (int h = 0; h < stvcmd.size(); h++) {
                                                if (sn[j].equals(stvcmd.get(h).getSn())) {
                                                    stvcmd.remove(h);
                                                }
                                            }
                                        }
                                        map.put(sku.getId().toString(), stvcmd);
                                        break;
                                    }

                                }
                            }
                        }
                        infoLogOms.setWmsOrderStatusOms(statusOms);
                        infoLogOmsDao.save(infoLogOms);
                    }
                    map.clear();
                    // 记录发票信息
                    List<StaInvoice> invoices = staInvoiceDao.getBySta(sta.getId());
                    for (StaInvoice invoice : invoices) {
                        WmsOrderInvoiceOms invoiceOms = new WmsOrderInvoiceOms();
                        invoiceOms.setAmt(invoice.getAmt());
                        invoiceOms.setInvoiceCode(invoice.getInvoiceCode());
                        invoiceOms.setDrawer(invoice.getDrawer());
                        invoiceOms.setInvoiceDate(invoice.getInvoiceDate());
                        invoiceOms.setItem(invoice.getItem());
                        invoiceOms.setMemo(invoice.getMemo());
                        invoiceOms.setPayee(invoice.getPayee());
                        invoiceOms.setCompany(invoice.getCompany());
                        invoiceOms.setPayer(invoice.getPayer());
                        invoiceOms.setQty(invoice.getQty().intValue());
                        invoiceOms.setUnitPrice(invoice.getUnitPrice());
                        invoiceOmsDao.save(invoiceOms);
                        List<StaInvoiceLine> invoiceLines = invoiceLineDao.getByStaInvoiceId(invoice.getId());
                        for (StaInvoiceLine staInvoiceLine : invoiceLines) {
                            WmsOrderInvoiceLineOms invoiceLineOms = new WmsOrderInvoiceLineOms();
                            invoiceLineOms.setAmt(staInvoiceLine.getAmt());
                            invoiceLineOms.setInvoiceId(invoiceOms);
                            invoiceLineOms.setItem(staInvoiceLine.getItem());
                            invoiceLineOms.setLineNo(staInvoiceLine.getLineNO());
                            invoiceLineOms.setQty(staInvoiceLine.getQty());
                            invoiceLineOms.setUnitPrice(staInvoiceLine.getUnitPrice());
                            invoiceLineOmsDao.save(invoiceLineOms);
                        }
                    }
                }
                // 菜鸟入库确认数据封装
                if (sta.getSystemKey().equals(Constants.CAINIAO_DB_SYSTEM_KEY) && (staType.equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) || staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST))) {
                    CnWmsStockInOrderConfirm stockInConfirm = new CnWmsStockInOrderConfirm();
                    stockInConfirm.setOrderCode(sta.getRefSlipCode());
                    if (staType.equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT)) {
                        // 普通入库
                        stockInConfirm.setOrderType(Integer.parseInt(sta.getOrderType()));
                    } else {
                        // 退货入库
                        // 判断是501退货入库指令 or 502换货出库指令（502无需确认入库）
                        CnOrderProperty cnOrderProperty = cnOrderPropertyDao.getCnOrderPropertyByOrderCodeAndType(sta.getRefSlipCode(), "502");
                        if (cnOrderProperty != null) return;
                        stockInConfirm.setOrderType(501);// 退货入库
                    }
                    // 0 最后一次确认或是一次性确认； 1 多次确认；
                    if (sta.getStatus().getValue() == 5) {
                        stockInConfirm.setConfirmType(1);
                    } else if (sta.getStatus().getValue() == 10) {
                        stockInConfirm.setConfirmType(0);
                    }
                    stockInConfirm.setOrderConfirmTime(stv.getFinishTime());
                    stockInConfirm.setStatus("0");
                    stockInConfirm.setStaId(sta.getId());
                    cnWmsStockInOrderConfirmDao.save(stockInConfirm);
                    cnWmsStockInOrderConfirmDao.flush();
                    // 外部业务编码
                    stockInConfirm.setOutBizCode(stockInConfirm.getId() + "");
                    if (staType.equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT)) {
                        // 入库商品信息列表
                        List<StaLine> lines = staLineDao.findByStaId(sta.getId());
                        if (lines != null && !lines.isEmpty()) {
                            for (StaLine line : lines) {
                                CnStockInOrderLine stockInOrderLine = new CnStockInOrderLine();
                                stockInOrderLine.setOrderItemId(line.getOrderLineNo());
                                Sku sku = line.getSku();
                                try {
                                    stockInOrderLine.setWeight(sku.getGrossWeight().longValue());
                                    Integer v = (int) (sku.getLength().longValue() * sku.getWidth().longValue() * sku.getHeight().longValue() * 0.001);
                                    stockInOrderLine.setVolume(v);// 体积 单位 立方厘米
                                    stockInOrderLine.setLength(sku.getLength().intValue());// 长 单位
                                                                                           // mm
                                    stockInOrderLine.setWidth(sku.getWidth().intValue());// 宽 单位 mm
                                    stockInOrderLine.setHeight(sku.getHeight().intValue());// 高 单位
                                                                                           // mm
                                } catch (Exception e) {
                                    log.error("菜鸟普通入库确认，设置回传的商品的长宽高或体积有误！itemId=" + sku.getExtensionCode1(), e);
                                }
                                stockInOrderLine.setStockInOrderConfirm(stockInConfirm);
                                cnStockInOrderLineDao.save(stockInOrderLine);
                                // 入库商品库存
                                CnStockInItemInventory inv = new CnStockInItemInventory();
                                if (org.apache.commons.lang3.StringUtils.equals(line.getInvStatus().getName(), InventoryStatus.INVENTORY_STATUS_GOOD)) {
                                    inv.setInventoryType(Constants.SELLABLE_INVENTORY);
                                } else {
                                    inv.setInventoryType(Constants.DEFECTIVE_GOODS);
                                }
                                // 每次上架数量
                                StvLine skuLine = stvLineDao.findStvLineByStvAndSku(stv.getId(), sku.getId());
                                if (skuLine != null) {
                                    inv.setQuantity(skuLine.getQuantity().intValue());
                                }
                                inv.setStockInOrderLine(stockInOrderLine);
                                cnStockInItemInventoryDao.save(inv);
                            }
                        }
                    } else if (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
                        // 入库商品信息列表
                        List<StaLine> lines = staLineDao.findByStaId(sta.getId());
                        if (lines != null && !lines.isEmpty()) {
                            for (StaLine line : lines) {
                                CnStockInOrderLine stockInOrderLine = new CnStockInOrderLine();
                                stockInOrderLine.setOrderItemId(line.getOrderLineNo());
                                Sku sku = line.getSku();
                                try {
                                    stockInOrderLine.setWeight(sku.getGrossWeight().longValue());
                                    Integer v = (int) (sku.getLength().longValue() * sku.getWidth().longValue() * sku.getHeight().longValue() * 0.001);
                                    stockInOrderLine.setVolume(v);// 体积 单位 立方厘米
                                    // 长 单位 mm
                                    stockInOrderLine.setLength(sku.getLength().intValue());
                                    // 宽 单位 mm
                                    stockInOrderLine.setWidth(sku.getWidth().intValue());
                                    // 高 单位 mm
                                    stockInOrderLine.setHeight(sku.getHeight().intValue());
                                } catch (Exception e) {
                                    log.error("菜鸟退货入库确认，设置回传的商品的长宽高或体积有误！itemId=" + sku.getExtensionCode1(), e);
                                }
                                stockInOrderLine.setStockInOrderConfirm(stockInConfirm);
                                cnStockInOrderLineDao.save(stockInOrderLine);
                                // 退货入库商品库存
                                List<StvLine> skvLines = stvLineDao.findStvLinesByStvAndSku(stv.getId(), sku.getId());
                                if (skvLines != null && !skvLines.isEmpty()) {
                                    for (StvLine stvLine : skvLines) {
                                        CnStockInItemInventory inv = new CnStockInItemInventory();
                                        InventoryStatus invStatus = inventoryStatusDao.findByStatusIdAndOu(stvLine.getInvStatus().getId(), stvLine.getWarehouse().getId());
                                        if (org.apache.commons.lang3.StringUtils.equals(invStatus.getName(), InventoryStatus.INVENTORY_STATUS_GOOD)) {
                                            inv.setInventoryType(Constants.SELLABLE_INVENTORY);
                                        } else {
                                            inv.setInventoryType(Constants.DEFECTIVE_GOODS);
                                        }
                                        // 每次上架数量
                                        inv.setQuantity(stvLine.getQuantity().intValue());
                                        inv.setStockInOrderLine(stockInOrderLine);
                                        cnStockInItemInventoryDao.save(inv);
                                    }
                                }
                            }
                        }
                    }
                }
                // Gucci退货入库数据生成
                BiChannel shop = companyShopDao.getByVmiCode(Constants.GUCCI_BRAND_VMI_CODE);
                if (shop != null) {
                    if (org.apache.commons.lang3.StringUtils.equals(shop.getVmiCode(), Constants.GUCCI_BRAND_VMI_CODE) && staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
                        gucciManager.generateSaleReturnInboundData(sta, stv);
                    }
                }
            } else {
                if (StockTransApplicationType.INBOUND_PURCHASE.equals(sta.getType())) {
                    InventoryTransactionToOms ito = new InventoryTransactionToOms();
                    ito.setStaCode(sta.getCode());
                    ito.setStvCode(stv.getCode());
                    ito.setCreateTime(new Date());
                    ito.setStvId(stv.getId());
                    ito.setStatus(DefaultStatus.CREATED);
                    ito.setIsSend(false);
                    ito.setErrorCount(0L);
                    inventoryTransactionToOmsDao.save(ito);
                } else if (isNeedRecordType(sta)) {
                    WmsInvChangeToOms wio = new WmsInvChangeToOms();
                    wio.setStaCode(sta.getCode());
                    wio.setStaId(sta.getId());
                    wio.setStvCode(stv.getCode());
                    wio.setStvId(stv.getId());
                    wio.setCreateTime(new Date());
                    wio.setDataStatus(StockTransApplicationStatus.FINISHED);
                    wio.setErrorCount(0);
                    wio.setExeStatus(DefaultStatus.CREATED);
                    wio.setPriority(3);// 此处默认为3
                    wio.setOrderType(1);
                    wmsInvChangeToOmsDao.save(wio);
                } else {
                    inventoryTransactionToOms(stv, false);
                }
            }
        }
    }

    /**
     * 判断对应作业类型是否要在调用的地方记入中间表
     * 
     * @param type
     * @return
     */
    @Transactional
    private boolean isNeedRecordType(StockTransApplication sta) {
        StockTransApplicationType type = sta.getType();
        switch (type) {
            case INBOUND_SETTLEMENT:// 结算经销入库
            case INBOUND_CONSIGNMENT:// 代销入库
            case INBOUND_MOBILE:// 移库入库
            case INBOUND_OTHERS:// 其他入库
            case VMI_INBOUND_CONSIGNMENT:// WMI移库入库
            case TRANSIT_CROSS:// 库间移动
                return true;
            case INVENTORY_STATUS_CHANGE:// 库存状态修改
                if (sta.getRefSlipCode() == null || sta.getRefSlipCode().startsWith("I")) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    @Transactional
    public void inventoryTransactionToOms(StockTransVoucher stv1, Boolean b) {
        Boolean isMq = false;
        StockTransVoucher stv = stvDao.getByPrimaryKey(stv1.getId());
        StockTransApplication sta = staDao.getByPrimaryKey(stv.getSta().getId());

        StaDeliveryInfoCommand deliveryInfo = staDeliveryInfoDao.findTheStaDeliveryInfoByStaId(stv.getSta().getId(), new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
        StockTransApplicationType staType = sta.getType();
        SlipType staSlipType = sta.getRefSlipType();
        if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(staType)) {// 非直连 退货入库
            List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_RTN_ORDER_SERVICE_PAC, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
            if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
                // 非直连外包仓 不走MQ
                Warehouse wareHouse = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
                if (wareHouse != null && wareHouse.getVmiSource() == null) {
                    isMq = true;
                }
            }
        }
        try {
            staDao.flush();
            // Date maxTransactionTime = staDao.getMaxTransactionTime(sta.getId(), new
            // SingleColumnRowMapper<Date>(Date.class));
            Date maxTransactionTime = stv.getFinishTime();
            if (maxTransactionTime == null) {
                maxTransactionTime = new Date();
            }
            OperationBill operationBill = new OperationBill();
            operationBill.setMaxTransactionTime(maxTransactionTime);
            operationBill.setCode(stv.getSta().getCode());
            operationBill.setWhCode(stv.getWarehouse().getCode());
            if (staSlipType != null && staSlipType.equals(SlipType.REVERSE_NP_ADJUSTMENT_INBOUND)) {
                operationBill.setType(SlipType.REVERSE_NP_ADJUSTMENT_INBOUND.getValue());
            } else if (isNeedRecordType(sta)) {
                operationBill.setType(222);
            } else {
                operationBill.setType(sta.getType().getValue());
            }
            operationBill.setSlipCode(stv.getSta().getRefSlipCode());
            if (StockTransApplicationType.INBOUND_PURCHASE.equals(staType)) {
                operationBill.setSysSourceCode(chooseOptionDao.findAllOptionListByOptionKey(Constants.SYS_SOURCE_CODE, Constants.SYS_SOURCE_CODE, new SingleColumnRowMapper<String>(String.class)));
                operationBill.setBatchNo(stv.getCode());
            }
            // 按箱收货反馈采购订单入库数量是，基于Carton Sta查询关联Root Sta,反馈明细基于Carton sta反馈，单据信息基于Root Sta反馈
            if (!StringUtils.isEmpty(sta.getGroupSta()) && (StockTransApplicationType.INBOUND_PURCHASE.equals(staType) || StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.equals(staType))) {
                Long rootstaId = sta.getGroupSta().getId();
                StockTransApplication rootsta = staDao.getByPrimaryKey(rootstaId);
                operationBill.setCode(rootsta.getCode());
                operationBill.setSlipCode(rootsta.getRefSlipCode());
            }
            if (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
                if (sta.getStaDeliveryInfo().getReturnReasonType() != null) {
                    List<ChooseOption> statusList = chooseOptionDao.findOptionListByCategoryCode("returnReasonType");
                    for (ChooseOption chooseOption : statusList) {
                        if (ReturnReasonType.valueOf(sta.getStaDeliveryInfo().getReturnReasonType().getValue()).toString().equals(chooseOption.getOptionKey())) {
                            operationBill.setInBoundRemark(chooseOption.getOptionValue() + ":" + (sta.getStaDeliveryInfo().getReturnReasonMemo() == null ? "" : sta.getStaDeliveryInfo().getReturnReasonMemo()));
                            operationBill.setInboundRemarkCode(String.valueOf(sta.getStaDeliveryInfo().getReturnReasonType().getValue()));
                        }
                    }
                }
            }
            List<ExpiredDateInfo> list_ = new ArrayList<ExpiredDateInfo>();
            operationBill.setMemo(stv.getMemo());
            if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER)) {
                operationBill.setSlipCode(stv.getSta().getSlipCode1());
            }
            if (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
                List<SkuSnLogCommand> skus = skuSnLogDao.findOutboundSnBySta(sta.getId(), new BeanPropertyRowMapper<SkuSnLogCommand>(SkuSnLogCommand.class));
                List<SnFeedbackInfo> snFeedbackInfos = new ArrayList<SnFeedbackInfo>();
                for (SkuSnLogCommand sku : skus) {
                    SnFeedbackInfo feedbackInfo = new SnFeedbackInfo();
                    feedbackInfo.setSnNo(sku.getSn());
                    feedbackInfo.setJmskuCode(sku.getSkuCode());
                    snFeedbackInfos.add(feedbackInfo);
                }
                operationBill.setInboundSnInfo(snFeedbackInfos);
            }
            List<OperationBillLine> billLines = new ArrayList<OperationBillLine>();
            List<StvLine> list = stvLineDao.findStvLineListByStvId(stv.getId());
            if (stv.getDirection().getValue() == 1) {// 入
                operationBill.setDirection(OperationBill.ONLY_INBOUND);
                for (int i = 0; i < list.size(); i++) {
                    StvLine line = list.get(i);
                    InventoryStatus status = inventoryStatusDao.getByPrimaryKey(line.getInvStatus().getId());
                    OperationBillLine operationBillLine = new OperationBillLine();
                    operationBillLine.setInvBatchCode(line.getBatchCode());
                    operationBillLine.setInvStatusCode(status.getName());
                    operationBillLine.setQty(line.getQuantity());
                    Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                    operationBillLine.setSkuCode(sku.getCustomerSkuCode());
                    operationBillLine.setWhCode(line.getWarehouse().getCode());
                    if (deliveryInfo != null) {
                        operationBillLine.setTrackingNo(deliveryInfo.getTrackingNo());
                        operationBillLine.setTransCode(deliveryInfo.getLpCode());
                    }
                    // 新增是否可销售
                    SkuCommand sc = skuDao.getSkuIsForsale(sku.getBarCode(), line.getWarehouse().getId(), line.getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                    operationBillLine.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                    BiChannel companyShop = companyShopDao.getByCode(line.getOwner());
                    if (companyShop == null) {
                        throw new BusinessException("");
                    }
                    // 店铺切换 接口调整-调整渠道编码
                    operationBillLine.setShopCode(line.getOwner());
                    billLines.add(operationBillLine);
                    if (InboundStoreMode.SHELF_MANAGEMENT.equals(line.getSku().getStoremode())) {
                        ExpiredDateInfo d = new ExpiredDateInfo();
                        d.setExpiredDate(line.getExpireDate());
                        d.setInvStatusCode(line.getInvStatus().getName());
                        d.setJmskuCode(sku.getJmCode());
                        d.setQty(line.getQuantity());
                        list_.add(d);
                    }
                }
                operationBill.setInboundLines(billLines);
                operationBill.setInboundExpiredDateInfo(list_.size() > 0 ? list_ : null);
                List<WarrantyCardResp> warrantyCardList = new ArrayList<WarrantyCardResp>();
                List<GiftLineCommand> glList = giftLineDao.findGiftByStaAndType(sta.getId(), GiftType.COACH_CARD.getValue(), new BeanPropertyRowMapper<GiftLineCommand>(GiftLineCommand.class));
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (GiftLineCommand gl : glList) {
                    WarrantyCardResp resp = new WarrantyCardResp();
                    resp.setCardNo(gl.getSanCardCode());
                    resp.setDirection(2);
                    Date date = null;
                    if (!StringUtil.isEmpty(gl.getMemo())) {
                        try {
                            date = formatDate.parse(gl.getMemo());
                        } catch (Exception e) {
                            log.error("String to Date format error! StringDate:" + gl.getMemo());
                            throw new BusinessException(ErrorCode.STRING_TO_DATE_FORMAT_ERROR);
                        }
                    }
                    resp.setExpirationDate(date);
                    resp.setJmskuCode(gl.getSkuCode());
                    warrantyCardList.add(resp);
                }
                operationBill.setWarrantyCardList(warrantyCardList);
            } else if (stv.getDirection().getValue() == 2) {// 出
                operationBill.setDirection(OperationBill.ONLY_OUTBOUND);
                for (int i = 0; i < list.size(); i++) {
                    StvLine line = list.get(i);
                    OperationBillLine operationBillLine = new OperationBillLine();
                    operationBillLine.setInvBatchCode(line.getBatchCode());
                    operationBillLine.setInvStatusCode(line.getInvStatus().getName());
                    operationBillLine.setQty(line.getQuantity());
                    Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                    operationBillLine.setSkuCode(sku.getCustomerSkuCode());
                    operationBillLine.setWhCode(line.getWarehouse().getCode());
                    // 新增是否可销售
                    SkuCommand sc = skuDao.getSkuIsForsale(sku.getBarCode(), line.getWarehouse().getId(), line.getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                    operationBillLine.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                    if (line.getOwner() == null) {
                        log.error("send msg to oms , owner is null!");
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    if (InboundStoreMode.SHELF_MANAGEMENT.equals(line.getSku().getStoremode())) {
                        ExpiredDateInfo d = new ExpiredDateInfo();
                        d.setExpiredDate(line.getExpireDate());
                        d.setInvStatusCode(line.getInvStatus().getName());
                        d.setJmskuCode(sku.getJmCode());
                        d.setQty(line.getQuantity());
                        list_.add(d);
                    }
                    // 店铺切换 接口调整-调整渠道编码
                    operationBillLine.setShopCode(line.getOwner());
                    billLines.add(operationBillLine);
                }
                operationBill.setOutboundLines(billLines);
                operationBill.setOutboundExpiredDateInfo(list_.size() > 0 ? list_ : null);
            }
            List<OperationBill> operationBillList = new ArrayList<OperationBill>();
            operationBillList.add(operationBill);

            if (isMq) {// 退换货入库 通过mq 通知pac
                MessageCommond mes = new MessageCommond();
                try {
                    String opBill = JsonUtil.writeValue(operationBillList);
                    mes.setIsMsgBodySend(false);
                    mes.setMsgId(stv.getId().toString() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                    mes.setMsgType("com.jumbo.wms.manager.listener.StvListenerManagerImpl.inventoryTransactionToOms");
                    mes.setMsgBody(opBill);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    mes.setSendTime(sdf.format(date));
                    producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_CREATE_RETURN, sta.getOwner(), mes);
                    // 保存进mongodb
                    MongoDBMessage mdbm = new MongoDBMessage();
                    BeanUtils.copyProperties(mes, mdbm);
                    mdbm.setStaCode(sta.getCode());
                    mdbm.setOtherUniqueKey(stv.getId().toString());
                    mdbm.setMsgBody(opBill);
                    mdbm.setMemo(MQ_WMS3_SALES_ORDER_CREATE_RETURN);
                    mongoOperation.save(mdbm);
                    log.debug("rmi Call oms inventoryTransactionToOms response interface by MQ end:" + stv.getId());
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("inventoryTransactionToOms isMQ  error---:" + stv.getCode(), e);
                    }
                    // 插入中间表 待手动触发
                    if (b) {
                        MessageProducerError error = new MessageProducerError();
                        BeanUtils.copyProperties(error, mes);
                        error.setMsgId(System.currentTimeMillis() + "");
                        error.setCreateTime(new Date());
                        error.setMsgBody(null);
                        error.setStvId(stv.getId());
                        error.setStaCode(sta.getCode());
                        messageProducerErrorDao.save(error);
                    } else {
                        log.error("inventoryTransactionToOms isMQ  error2---:" + stv.getCode());
                        throw new BusinessException("入库放mq失败");
                    }

                }
            } else {
                try {
                    log.info("Call oms outbound response interface------------------->BEGIN" + sta.getCode());
                    // 如果是库存状态修改异步化改造
                    if (StockTransApplicationType.INVENTORY_STATUS_CHANGE.equals(sta.getType())) {
                        String choose = chooseOptionDao.findAllOptionListByOptionKey("inventoryChange", "INVENTORY_STATUS_CHANGE", new SingleColumnRowMapper<String>(String.class));
                        if (null != choose && !"".equals(choose) && "1".equals(choose)) {
                            InvetoryChange invetoryChange = new InvetoryChange();
                            invetoryChange.setStaId(sta.getId());
                            invetoryChange.setStvId(stv.getId());
                            invetoryChange.setStatus(1L);
                            invetoryChange.setErrorCount(0L);
                            invetoryChangeDao.save(invetoryChange);
                        } else {
                            InvChangeLogNoticPacThread t = new InvChangeLogNoticPacThread(sta.getId(), stv.getId());
                            Thread tx = new Thread(t);
                            tx.start();
                        }
                    } else {
                        BaseResult baseResult = rmi4Wms.wmsOperationsFeedback(operationBill);
                        try {
                            log.info(sta.getCode() + ":" + net.sf.json.JSONObject.fromObject(operationBill).toString());
                        } catch (Exception e) {
                            log.error("", e);
                        }
                        if (baseResult.getStatus() == 0) {
                            throw new BusinessException(ErrorCode.OMS_SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
                        }
                        log.info("Call oms outbound response interface------------------->END" + sta.getCode());
                    }
                } catch (BusinessException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("stvListener", e);
                    throw new BusinessException(ErrorCode.RMI_OMS_FAILURE, new Object[] {""});
                }
            }
        } catch (BusinessException e) {
            log.error("stvListener1", e);
            throw e;
        } catch (Exception e) {
            log.error("stvListener2", e);
            throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
        }
    }
}
