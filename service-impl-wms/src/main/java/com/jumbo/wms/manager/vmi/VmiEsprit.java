package com.jumbo.wms.manager.vmi;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.espData.ESPDeliveryDao;
import com.jumbo.dao.vmi.espData.ESPDeliveryReceiveDao;
import com.jumbo.dao.vmi.espData.ESPInvoiceBDPoDao;
import com.jumbo.dao.vmi.espData.ESPInvoicePercentageDao;
import com.jumbo.dao.vmi.espData.ESPOrderDao;
import com.jumbo.dao.vmi.espData.ESPPoTypeDao;
import com.jumbo.dao.vmi.espData.ESPReceivingDao;
import com.jumbo.dao.vmi.espData.ESPTransferOrderDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.EspritStoreDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.espData.ESPOrderManager;
import com.jumbo.wms.manager.vmi.espData.ESPReceiveDeliveryManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.EspritStoreCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.espData.ESPDelivery;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryReceiveCommand;
import com.jumbo.wms.model.vmi.espData.ESPInvoiceBDPo;
import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentage;
import com.jumbo.wms.model.vmi.espData.ESPOrder;
import com.jumbo.wms.model.vmi.espData.ESPOrderCommand;
import com.jumbo.wms.model.vmi.espData.ESPPoType;
import com.jumbo.wms.model.vmi.espData.ESPReceiving;
import com.jumbo.wms.model.vmi.espData.ESPTransferOrder;
import com.jumbo.wms.model.vmi.espData.EspritOrderPOType;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;

import loxia.dao.support.BeanPropertyRowMapperExt;

public class VmiEsprit extends VmiBaseBrand {

    private static final long serialVersionUID = -474841241891541796L;
    protected static final Logger log = LoggerFactory.getLogger(VmiEsprit.class);
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private ESPOrderManager orderManager;

    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private ESPOrderDao orderDao;
    @Autowired
    private ESPReceivingDao receivingDao;
    @Autowired
    private ESPTransferOrderDao transferOrderDao;
    @Autowired
    private InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private ESPPoTypeDao esptypeDao;
    @Autowired
    private ESPReceiveDeliveryManager espRDeliveryManager;
    @Autowired
    private ESPDeliveryDao espDeliveryDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private EspritStoreDao espritStoreDao;
    @Autowired
    private ESPInvoiceBDPoDao espInvoiceBDPoDao;
    @Autowired
    private ESPDeliveryReceiveDao eSPDeliveryReceiveDao;
    @Autowired
    private ESPInvoicePercentageDao espInvoicePercentageDao;


    public static final String DATE = "2012-01-01 00:00:00";
    public static final String INTERFACETYPE = "DeliveryReceiving";// 区分Esprit大仓收货与转入收货

    private Long getSequenceNumber(Date end) {
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date start = null;
        long t = 0L;
        try {
            start = smdf.parse(DATE);
            t = (end.getTime() - start.getTime()) / (3600 * 24 * 1000);
        } catch (ParseException e) {
            log.error("", e);
        }
        return t;
    }



    /**
     * 入库作业当上架时反馈
     * 
     * @param sta
     * @param stv
     */
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        sta = staDao.getByPrimaryKey(sta.getId());
        Long rootStaId = sta.getGroupSta() == null ? null : sta.getGroupSta().getId();
        // 子作业单
        if (rootStaId != null) {
            if (INTERFACETYPE.equals(sta.getInterfaceType())) {
                if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
                    List<ESPDeliveryReceiveCommand> list = espRDeliveryManager.findShelveReceiveOrdersByStaId(sta.getId());// 调整为收货完成才生成反馈数据
                    if (0 == list.size()) {
                        throw new BusinessException(ErrorCode.VMI_ESPRIT_PURCHASE_DATA_ERROR);
                    }
                    ESPDeliveryReceiveCommand es = eSPDeliveryReceiveDao.findReceiveOrdersByStaId2(sta.getId(), new BeanPropertyRowMapperExt<ESPDeliveryReceiveCommand>(ESPDeliveryReceiveCommand.class));
                    if (es == null) {
                        log.error("es为null");
                        throw new BusinessException("es为null");
                    }
                    Date transDate = new Date();
                    SimpleDateFormat sdf = null;
                    ESPDelivery espD = null;
                    for (ESPDeliveryReceiveCommand r : list) {
                        // 根据sta。isEsprit为1进行新的反馈逻辑
                        if (sta.getIsEsprit() != null) {
                            ESPDeliveryReceiveCommand cmd = r;
                            espD = new ESPDelivery();
                            sdf = new SimpleDateFormat("yyyyMMdd");

                            espD.setHeaderFromgln(es.gethFromGln());
                            espD.setHeaderTogln(es.gethToGln());
                            espD.setHeaderFromnode(es.gethFromNode());
                            espD.setHeaderTonode(es.gethToNode());
                            //
                            // espD.setHeaderFromgln("4046655060392");
                            // espD.setHeaderTogln("4046655075778");
                            // espD.setHeaderFromnode("CECOMEWW");
                            // espD.setHeaderTonode("ERSHA");
                            //

                            espD.setHeaderNumberofRecords(String.valueOf(stv.getSkuQty()));
                            espD.setHeaderGenerationDate(sdf.format(transDate));
                            sdf = new SimpleDateFormat("HHmmss");
                            espD.setHeaderGenerationTime(sdf.format(transDate));
                            espD.setDeliveryDeliveryNO(sta.getRefSlipCode());
                            sdf = new SimpleDateFormat("yyyyMMdd");
                            espD.setDeliveryDeliveryDate(sdf.format(transDate));
                            espD.setDeliveryDeliverytype("R");
                            espD.setDeliveryDeliveryStatus("A");
                            espD.setDeliveryGoodsReceiptDate(sdf.format(sta.getCreateTime()));
                            espD.setDeliveryDeliveredfromGLN(sta.getFromLocation());
                            espD.setDeliveryDeliveredtoGLN(sta.getToLocation());
                            espD.setStaCode(sta.getCode());
                            espD.setStatus(ESPDelivery.STATUS_DOING);
                            espD.setCreateTime(new Date());
                            espD.setItemReceivedQTY(cmd.getSkuQty());
                            espD.setItemSku(cmd.getExtCode2());
                            espD.setVersion(new Date());
                            espDeliveryDao.save(espD);
                            espDeliveryDao.flush();


                        } else {// 原来逻辑
                            ESPDeliveryReceiveCommand cmd = r;
                            espD = new ESPDelivery();
                            sdf = new SimpleDateFormat("yyyyMMdd");
                            espD.setHeaderFromgln(ESPDelivery.DISPATCH_FROM_GLN);
                            espD.setHeaderTogln(ESPDelivery.DISPATCH_TO_GLN);
                            espD.setHeaderFromnode(ESPDelivery.DISPATCH_FROM_NODE);
                            espD.setHeaderTonode(ESPDelivery.DISPATCH_TO_NODE);
                            espD.setHeaderNumberofRecords(String.valueOf(stv.getSkuQty()));
                            espD.setHeaderGenerationDate(sdf.format(transDate));
                            sdf = new SimpleDateFormat("HHmmss");
                            espD.setHeaderGenerationTime(sdf.format(transDate));
                            espD.setDeliveryDeliveryNO(sta.getRefSlipCode());
                            sdf = new SimpleDateFormat("yyyyMMdd");
                            espD.setDeliveryDeliveryDate(sdf.format(transDate));
                            espD.setDeliveryDeliverytype(ESPDelivery.DELIVERED_TYPE);
                            espD.setDeliveryDeliveryStatus(ESPDelivery.DELIVERED_STATUS);
                            espD.setDeliveryGoodsReceiptDate(sdf.format(sta.getCreateTime()));
                            espD.setDeliveryDeliveredfromGLN(sta.getFromLocation());
                            espD.setDeliveryDeliveredtoGLN(sta.getToLocation());
                            espD.setStaCode(sta.getCode());
                            espD.setStatus(ESPDelivery.STATUS_DOING);
                            espD.setCreateTime(new Date());
                            espD.setItemReceivedQTY(cmd.getSkuQty());
                            espD.setItemSku(cmd.getExtCode2());
                            espD.setVersion(new Date());
                            espDeliveryDao.save(espD);
                            espDeliveryDao.flush();

                        }
                    }
                }
            } else {

                if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
                    List<ESPOrderCommand> recList = null;
                    Date date = new Date();
                    if (stv != null) {
                        recList = orderDao.findESPOrderCommandByStvId3(sta.getId(), new BeanPropertyRowMapperExt<ESPOrderCommand>(ESPOrderCommand.class));
                        stv = stvDao.getByPrimaryKey(stv.getId());
                    }
                    ESPOrder ord = orderDao.getLastedOrderByPO(sta.getRefSlipCode());
                    if (ord == null) {
                        ord = orderDao.getLastedOrderByPO(sta.getSlipCode1());
                    }
                    ESPReceiving receving = null;
                    Long sequenceNumber = getSequenceNumber(date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String seqNumber = String.format("%06d", sequenceNumber);
                    String receiveWarehouseDate = sdf.format(new Date());
                    if (recList != null) {
                        for (ESPOrderCommand command : recList) {
                            receving = new ESPReceiving();
                            receving.setHeaderFromGLN(Constants.ESPRIT_BAOZUN_GLN);
                            receving.setHeaderToGLN(Constants.ESPRIT_ESPRIT_GLN);
                            receving.setHeaderFromNode(Constants.BAOZUN_NODE);
                            receving.setHeaderToNode(Constants.ESPRIT_NODE);
                            receving.setHeaderSequenceNumber(seqNumber);
                            String receiveDate = sdf.format(stv.getFinishTime());
                            receving.setReceivingGoodsReceivedDate(receiveDate);
                            receving.setReceivingWarehouseReference("BZ" + receiveWarehouseDate);
                            if (command.getOdPO() != null && !"".equals(command.getOdPO())) {
                                receving.setReceivingOrdernumber(command.getOdPO());
                            } else {
                                receving.setReceivingOrdernumber(sta.getSlipCode1() == null ? sta.getRefSlipCode() : sta.getSlipCode1());
                            }

                            receving.setReceivingPoReference(ord.getOdPoreference());
                            receving.setInvoiceCurrency(sta.getCurrency());
                            if (sta.getTotalFOB() != null) {
                                receving.setInvoiceTotalfob(sta.getTotalFOB().toString());
                            } else {
                                receving.setInvoiceTotalfob("1.0");
                            }
                            Long qty = staLineDao.findTotalQtyByStaId(rootStaId, new SingleColumnRowMapper<Long>(Long.class));
                            if (qty != null) {
                                receving.setInvoiceTotalqty(qty.toString());
                            } else {
                                receving.setInvoiceTotalqty(sta.getSkuQty().toString());
                            }
                            if (sta.getTotalGTP() != null) {
                                receving.setInvoiceTotalgtp(sta.getTotalGTP().toString());
                            }
                            receving.setReceivingBuyergln(Constants.ESPRIT_BAOZUN_GLN);
                            receving.setReceivingDeliverypartygln(Constants.ESPRIT_ESPRIT_GLN);
                            receving.setReceivingWarehouse(Constants.ESPRIT_VIM_CODE);
                            receving.setItemSku(command.getBarCode());
                            receving.setItemReceivedqty(command.getTotalQuantity().toString());
                            receving.setVersion(date);
                            receivingDao.save(receving);
                        }
                    }
                    // 入库成功，写入转仓指令
                    if (stv == null) {
                        log.error("stv is null");
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    transferOrderDao.createTransferOrder(stv.getId());
                }
            }
        } // 主作业单
        else {
            // 反馈
            if (INTERFACETYPE.equals(sta.getInterfaceType())) {
                if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
                    List<ESPDeliveryReceiveCommand> list = espRDeliveryManager.findShelveReceiveOrdersByStaId(sta.getId());// 调整为收货完成才生成反馈数据
                    if (0 == list.size()) {
                        throw new BusinessException(ErrorCode.VMI_ESPRIT_PURCHASE_DATA_ERROR);
                    }
                    ESPDeliveryReceiveCommand es = eSPDeliveryReceiveDao.findReceiveOrdersByStaId2(sta.getId(), new BeanPropertyRowMapperExt<ESPDeliveryReceiveCommand>(ESPDeliveryReceiveCommand.class));
                    if (es == null) {
                        log.error("es为null");
                        throw new BusinessException("es为null");
                    }
                    Date transDate = new Date();
                    SimpleDateFormat sdf = null;
                    ESPDelivery espD = null;
                    for (ESPDeliveryReceiveCommand r : list) {
                        // 根据sta。isEsprit为1进行新的反馈逻辑
                        if (sta.getIsEsprit() != null) {
                            ESPDeliveryReceiveCommand cmd = r;
                            espD = new ESPDelivery();
                            sdf = new SimpleDateFormat("yyyyMMdd");

                            espD.setHeaderFromgln(es.gethFromGln());
                            espD.setHeaderTogln(es.gethToGln());
                            espD.setHeaderFromnode(es.gethFromNode());
                            espD.setHeaderTonode(es.gethToNode());

                            espD.setHeaderNumberofRecords(String.valueOf(stv.getSkuQty()));
                            espD.setHeaderGenerationDate(sdf.format(transDate));
                            sdf = new SimpleDateFormat("HHmmss");
                            espD.setHeaderGenerationTime(sdf.format(transDate));
                            espD.setDeliveryDeliveryNO(sta.getRefSlipCode());
                            sdf = new SimpleDateFormat("yyyyMMdd");
                            espD.setDeliveryDeliveryDate(sdf.format(transDate));
                            espD.setDeliveryDeliverytype("R");
                            espD.setDeliveryDeliveryStatus("A");
                            espD.setDeliveryGoodsReceiptDate(sdf.format(sta.getCreateTime()));
                            espD.setDeliveryDeliveredfromGLN(sta.getFromLocation());
                            espD.setDeliveryDeliveredtoGLN(sta.getToLocation());
                            espD.setStaCode(sta.getCode());
                            espD.setStatus(ESPDelivery.STATUS_DOING);
                            espD.setCreateTime(new Date());
                            espD.setItemReceivedQTY(cmd.getSkuQty());
                            espD.setItemSku(cmd.getExtCode2());
                            espD.setVersion(new Date());
                            espDeliveryDao.save(espD);
                            espDeliveryDao.flush();


                        } else {// 原来逻辑
                            ESPDeliveryReceiveCommand cmd = r;
                            espD = new ESPDelivery();
                            sdf = new SimpleDateFormat("yyyyMMdd");
                            espD.setHeaderFromgln(ESPDelivery.DISPATCH_FROM_GLN);
                            espD.setHeaderTogln(ESPDelivery.DISPATCH_TO_GLN);
                            espD.setHeaderFromnode(ESPDelivery.DISPATCH_FROM_NODE);
                            espD.setHeaderTonode(ESPDelivery.DISPATCH_TO_NODE);
                            espD.setHeaderNumberofRecords(String.valueOf(stv.getSkuQty()));
                            espD.setHeaderGenerationDate(sdf.format(transDate));
                            sdf = new SimpleDateFormat("HHmmss");
                            espD.setHeaderGenerationTime(sdf.format(transDate));
                            espD.setDeliveryDeliveryNO(sta.getRefSlipCode());
                            sdf = new SimpleDateFormat("yyyyMMdd");
                            espD.setDeliveryDeliveryDate(sdf.format(transDate));
                            espD.setDeliveryDeliverytype(ESPDelivery.DELIVERED_TYPE);
                            espD.setDeliveryDeliveryStatus(ESPDelivery.DELIVERED_STATUS);
                            espD.setDeliveryGoodsReceiptDate(sdf.format(sta.getCreateTime()));
                            espD.setDeliveryDeliveredfromGLN(sta.getFromLocation());
                            espD.setDeliveryDeliveredtoGLN(sta.getToLocation());
                            espD.setStaCode(sta.getCode());
                            espD.setStatus(ESPDelivery.STATUS_DOING);
                            espD.setCreateTime(new Date());
                            espD.setItemReceivedQTY(cmd.getSkuQty());
                            espD.setItemSku(cmd.getExtCode2());
                            espD.setVersion(new Date());
                            espDeliveryDao.save(espD);
                            espDeliveryDao.flush();

                        }
                    }
                }
            } else {
                List<ESPOrderCommand> recList = null;
                if (stv != null) {
                    recList = orderDao.findESPOrderCommandByStvId1(stv.getId(), new BeanPropertyRowMapperExt<ESPOrderCommand>(ESPOrderCommand.class));
                    stv = stvDao.getByPrimaryKey(stv.getId());
                }
                Date date = new Date();
                ESPOrder ord = orderDao.getLastedOrderByPO(sta.getRefSlipCode());
                ESPReceiving receving = null;
                Long sequenceNumber = getSequenceNumber(date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String seqNumber = String.format("%06d", sequenceNumber);
                if (recList != null) {
                    String receiveDate = sdf.format(new Date());
                    for (ESPOrderCommand command : recList) {
                        receving = new ESPReceiving();
                        receving.setHeaderFromGLN(Constants.ESPRIT_BAOZUN_GLN);
                        receving.setHeaderToGLN(Constants.ESPRIT_ESPRIT_GLN);
                        receving.setHeaderFromNode(Constants.BAOZUN_NODE);
                        receving.setHeaderToNode(Constants.ESPRIT_NODE);
                        receving.setHeaderSequenceNumber(seqNumber);
                        receving.setReceivingGoodsReceivedDate(receiveDate);
                        receving.setReceivingWarehouseReference("BZ" + receiveDate);
                        if (command.getOdPO() != null && !"".equals(command.getOdPO())) {
                            receving.setReceivingOrdernumber(command.getOdPO());
                        } else {
                            receving.setReceivingOrdernumber(sta.getRefSlipCode());
                        }

                        receving.setReceivingPoReference(ord.getOdPoreference());

                        receving.setInvoiceCurrency(sta.getCurrency());
                        if (sta.getTotalFOB() != null) {
                            receving.setInvoiceTotalfob(sta.getTotalFOB().toString());
                        } else {
                            receving.setInvoiceTotalfob("1.0");
                        }
                        Long qty = staLineDao.findTotalQtyByStaId(sta.getId(), new SingleColumnRowMapper<Long>(Long.class));
                        if (qty != null) {
                            receving.setInvoiceTotalqty(qty.toString());
                        } else {
                            receving.setInvoiceTotalqty(sta.getSkuQty().toString());
                        }
                        if (sta.getTotalGTP() != null) {
                            receving.setInvoiceTotalgtp(sta.getTotalGTP().toString());
                        }
                        receving.setReceivingBuyergln(Constants.ESPRIT_BAOZUN_GLN);
                        receving.setReceivingDeliverypartygln(Constants.ESPRIT_ESPRIT_GLN);
                        receving.setReceivingWarehouse(Constants.ESPRIT_VIM_CODE);
                        receving.setItemSku(command.getBarCode());
                        receving.setItemReceivedqty(command.getTotalQuantity().toString());
                        receving.setVersion(date);
                        receivingDao.save(receving);
                    }
                }
                // 入库成功，写入转仓指令
                if (stv == null) {
                    log.error("stv is null");
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                transferOrderDao.createTransferOrder(stv.getId());
            }
        }
    }


    /**
     * 根据中间表创建入库单据
     */
    @Override
    public void generateInboundSta() {
        // 修改
        List<ESPOrderCommand> espOrderCommandChangeList = orderManager.getOrdersGroupBySeqNumAndPO("Change");
        for (ESPOrderCommand espComd : espOrderCommandChangeList) {
            try {
                orderManager.generateChangeInboundSta(espComd, Constants.ESPRIT_VIM_CODE);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        // create
        List<ESPOrderCommand> espOrderCommandList = orderManager.getOrdersGroupBySeqNumAndPO("Create");
        for (ESPOrderCommand espComd : espOrderCommandList) {
            try {
                orderManager.generateNewInboungSta(espComd, Constants.ESPRIT_VIM_CODE);
            } catch (Exception e) {
                loggerErrorMsg(e);
            }
        }
        // cancel
        List<ESPOrderCommand> espOrderCommandCancelList = orderManager.getOrdersGroupBySeqNumAndPO("Cancel");
        for (ESPOrderCommand espComd : espOrderCommandCancelList) {
            try {
                orderManager.generetaCancelInboundSta(espComd);
            } catch (Exception e) {
                loggerErrorMsg(e);
            }
        }
    }

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        if (!inv.getType().equals(InventoryCheckType.VMI)) {
            return;
        }
        String po = inv.getSlipCode();
        if (po == null || po.equals("")) {
            throw new BusinessException(ErrorCode.VMI_ADJUSTMENT_PO_ERROR);
        }
        ESPOrder ord = orderDao.getLastedOrderByPO(po);
        StockTransApplication sta = staDao.findStaBySlipCode(po);
        if (ord == null || sta == null) {
            throw new BusinessException(ErrorCode.VMI_ADJUSTMENT_PO_ERROR);
        }
        // 查寻发票类型
        ESPPoType poType = esptypeDao.findByPo(po);
        if (poType == null) {
            throw new BusinessException(ErrorCode.ESP_PO_NOT_TYPE, new Object[] {po});
        }
        // 发票类型为 5 进口的需要查寻维护的发票数据
        if (EspritOrderPOType.IMPORT.equals(poType.getType())) {
            List<ESPInvoiceBDPo> invoiceList = espInvoiceBDPoDao.findByPo(po);
            if (invoiceList == null || invoiceList.size() == 0) {
                throw new BusinessException(ErrorCode.ESP_PO_NOT_TYPE_INV, new Object[] {po});
            }
            if (log.isInfoEnabled()) {
                log.info("5query po:" + po + " ref invoice Size:" + invoiceList.size());
            }
            ESPInvoicePercentage invoice = espInvoicePercentageDao.findByInvoice(invoiceList.get(0).getInvoiceNumber());
            if (invoice == null) {
                throw new BusinessException(ErrorCode.ESP_PO_INV_IS_NOT_NULL, new Object[] {invoiceList.get(0).getInvoiceNumber()});
            }
            inv.setInvoiceNumber(invoice.getInvoiceNum());
            inv.setDutyPercentage(invoice.getDutyPercentage());
            inv.setMiscFeePercentage(invoice.getMiscFeePercentage());
        } else {
            if (log.isInfoEnabled()) {
                log.info("not 5 query po:" + po + " ref invoice number:" + poType.getInvoiceNumber());
            }
            if (StringUtil.isEmpty(poType.getInvoiceNumber())) {
                throw new BusinessException(ErrorCode.ESP_PO_NOT_TYPE_INV, new Object[] {po});
            }
            inv.setInvoiceNumber(poType.getInvoiceNumber());
            inv.setDutyPercentage(poType.getDutyPercentage());
            inv.setMiscFeePercentage(poType.getMiscFeePercentage());
        }
        // 查询发票号
        List<InventoryCheckDifTotalLine> lines = icLineDao.findByInventoryCheck(inv.getId());
        Date date = new Date();
        ESPReceiving receving = null;
        Long sequenceNumber = getSequenceNumber(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // SimpleDateFormat fd = new SimpleDateFormat("MMdd");
        String seqNumber = String.format("%06d", sequenceNumber);
        ESPTransferOrder transferOrder = null;
        // 发票系数判断
        if (StringUtil.isEmpty(inv.getInvoiceNumber())) {
            throw new BusinessException(ErrorCode.ESP_PO_NOT_INV);
        }
        for (InventoryCheckDifTotalLine line : lines) {
            Long qty = line.getQuantity();
            if (qty <= 0) {
                throw new BusinessException(ErrorCode.VMI_ADJUSTMENT_QYT_ERROR);
            }
            receving = new ESPReceiving();
            receving.setHeaderFromGLN(Constants.ESPRIT_BAOZUN_GLN);
            receving.setHeaderToGLN(Constants.ESPRIT_ESPRIT_GLN);
            receving.setHeaderFromNode(Constants.BAOZUN_NODE);
            receving.setHeaderToNode(Constants.ESPRIT_NODE);
            receving.setHeaderSequenceNumber(seqNumber);
            String receiveDate = sdf.format(date);
            receving.setReceivingGoodsReceivedDate(receiveDate);
            receving.setReceivingWarehouseReference("BZ" + receiveDate);
            receving.setReceivingOrdernumber(ord.getOdPO());
            receving.setReceivingPoReference(ord.getOdPoreference());
            if (inv.getDutyPercentage() != null) {
                receving.setReceivingDutyPercentage(inv.getDutyPercentage().toString());
            }
            if (inv.getMiscFeePercentage() != null) {
                receving.setReceivingMiscfeepercentage(inv.getMiscFeePercentage().toString());
            }
            /*
             * if (!EspritOrderPOType.IMPORT.equals(poType.getType())) {
             * receving.setInvoiceInvoicenumber(inv.getInvoiceNumber() + "B" + fd.format(date)); }
             * else { receving.setInvoiceInvoicenumber(inv.getInvoiceNumber()); }
             */
            receving.setInvoiceCurrency(sta.getCurrency());
            if (sta.getTotalFOB() != null) {
                receving.setInvoiceTotalfob(sta.getTotalFOB().toString());
            } else {
                receving.setInvoiceTotalfob("1.0");
            }
            if (sta.getSkuQty() != null) {
                receving.setInvoiceTotalqty(sta.getSkuQty().toString());
            } else {
                receving.setInvoiceTotalqty(staLineDao.findTotalQtyByStaId(sta.getId(), new SingleColumnRowMapper<Long>(Long.class)).toString());
            }
            if (sta.getTotalGTP() != null) {
                receving.setInvoiceTotalgtp(sta.getTotalGTP().toString());
            }
            receving.setReceivingBuyergln(Constants.ESPRIT_BAOZUN_GLN);
            receving.setReceivingDeliverypartygln(Constants.ESPRIT_ESPRIT_GLN);
            receving.setReceivingWarehouse(Constants.ESPRIT_VIM_CODE);
            Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
            if (sku != null) {
                receving.setItemSku(sku.getBarCode());
            }
            receving.setItemReceivedqty(qty.toString());
            receving.setVersion(date);
            receivingDao.save(receving);
            // 入库成功，写入转仓指令
            transferOrder = new ESPTransferOrder();
            transferOrder.setHeaderFromGLN(ord.getHeaderFromGLN());
            transferOrder.setHeaderFromNode(ord.getHeaderFromNode());
            transferOrder.setHeaderToGLN(ord.getHeaderToGLN());
            transferOrder.setHeaderToNode(ord.getHeaderToNode());
            transferOrder.setHeaderGenerationDate(ord.getHeaderGenerationDate());
            transferOrder.setHeaderGenerationTime(ord.getHeaderGenerationTime());
            transferOrder.setHeaderNumberOfrecords(ord.getHeaderNumberOfrecords());
            transferOrder.setHeaderSequenceNumber(ord.getHeaderSequenceNumber());
            transferOrder.setOdBuyingSeasonCode(ord.getOdBuyingSeasonCode());
            transferOrder.setOdBuyingSeasonYear(ord.getOdBuyingSeasonYear());
            transferOrder.setOdCountryoforigin(ord.getOdCountryoforigin());
            transferOrder.setOdCurrency(ord.getOdCurrency());
            transferOrder.setOdExfactoryDate(ord.getOdExfactoryDate());
            transferOrder.setOdExpectedDeliveryDate(ord.getOdExpectedDeliveryDate());
            transferOrder.setOdFactory(ord.getOdFactory());
            transferOrder.setOdFobinCurrency(ord.getOdFobinCurrency());
            transferOrder.setOdFromNodeGLN(ord.getOdFromNodeGLN());
            transferOrder.setOdGlobalTransferPrice(ord.getOdGlobalTransferPrice());
            transferOrder.setOdPoreference(ord.getOdPoreference());
            transferOrder.setOdPortoFloading(ord.getOdPortoFloading());
            transferOrder.setOdShippingMethod(ord.getOdShippingMethod());
            if (sku == null) {
                log.error("sku is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            transferOrder.setOdSku(sku.getBarCode());
            transferOrder.setOdStatusineDifile(ord.getOdStatusineDifile());
            transferOrder.setOdStyle(ord.getOdStyle());
            transferOrder.setOdSupplier(ord.getOdSupplier());
            transferOrder.setOdToNodeGLN(ord.getOdToNodeGLN());
            transferOrder.setInvCheck(inv);
            transferOrder.setStatus(ord.getStatus());
            transferOrder.setVersion(date);
            transferOrder.setOdOrderQty(qty.toString());
            transferOrder.setOdPO(ord.getOdPO());
            transferOrderDao.save(transferOrder);
        }
    }

    /**
     * Vmi调拨（转仓）单据创建
     */
    public void generateVMITranscationWH() {
        List<Long> staList = orderManager.findStaIdNotGenerateTransfer();
        for (Long staId : staList) {
            if (staId == null) {
                continue;
            }
            List<ESPTransferOrder> orderListTOTmall = orderManager.findOrdersByStaIdEndWithO(staId);
            if (orderListTOTmall != null && orderListTOTmall.size() > 0) {
                try {
                    orderManager.generateTransferWHSTAFromESPOrder(orderListTOTmall, Constants.ESPRIT_TMALL, Constants.ESPRIT_VIM_CODE);
                } catch (Exception e) {
                    loggerErrorMsg(e);
                }
            }
            List<ESPTransferOrder> orderListTOCN = orderManager.findOrdersByStaIdNotEndWithO(staId);
            if (orderListTOCN != null && orderListTOCN.size() > 0) {
                try {
                    orderManager.generateTransferWHSTAFromESPOrder(orderListTOCN, Constants.ESPRIT_CN, Constants.ESPRIT_VIM_CODE);
                } catch (Exception e) {
                    loggerErrorMsg(e);
                }
            }
        }
        List<Long> invList = orderManager.findInvIdNotGenerateTransfer();
        for (Long invId : invList) {
            if (invId == null) {
                continue;
            }
            List<ESPTransferOrder> orderListTOTmall = orderManager.findOrdersByInvIdEndWithO(invId);
            if (orderListTOTmall != null && orderListTOTmall.size() > 0) {
                try {
                    orderManager.generateTransferWHSTAFromESPOrder(orderListTOTmall, Constants.ESPRIT_TMALL, Constants.ESPRIT_VIM_CODE);
                } catch (Exception e) {
                    loggerErrorMsg(e);
                }
            }
            List<ESPTransferOrder> orderListTOCN = orderManager.findOrdersByInvIdNotEndWithO(invId);
            if (orderListTOCN != null && orderListTOCN.size() > 0) {
                try {
                    orderManager.generateTransferWHSTAFromESPOrder(orderListTOCN, Constants.ESPRIT_CN, Constants.ESPRIT_VIM_CODE);
                } catch (Exception e) {
                    loggerErrorMsg(e);
                }
            }
        }
    }


    /**
     * 创建VMI转店反馈数据
     * 
     * @param sta
     */
    public void generateReceivingTransfer(StockTransApplication sta) {}

    /**
     * 创建VMI转仓（调拨）反馈数据
     * 
     * @param sta
     */
    public void generateReceivingFlitting(StockTransApplication sta) {}

    /**
     * 入库作业当收货时创建反馈/VMI为收货上架时
     * 
     * @param sta
     */
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {}

    /**
     * 通过SKUCode取商品基础信息（不同品牌取商品基本信息的表不同）
     * 
     * @param skuCode
     * @return
     */
    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        SkuCommand skuCommand = null;
        List<SkuCommand> lists = skuDao.findSKUCommandFromESPData(skuCode, ":", new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
        if (lists != null && lists.size() > 0) {
            skuCommand = lists.get(0);
        }
        return skuCommand;
    }

    /**
     * VMI入库收货反馈（作业单完成时反馈）
     * 
     * @param sta
     */
    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {}

    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {

    }

    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {

    }

    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        return null;
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        // 转入数据，创入库单
        /*
         * List<ESPDeliveryReceiveCommand> esList =
         * espRDeliveryManager.getReceiveDatasGroupByBatchNoAndPN(null);
         * for(ESPDeliveryReceiveCommand espComd : esList){ try {
         * espRDeliveryManager.generateNewInboundSta(espComd, Constants.ESP_VIRTUAL_SHOP_VMICODE); }
         * catch (Exception e) { loggerErrorMsg(e); } }
         */
        return null;
    }

    @Override
    public void generateRtnWh(StockTransApplication sta) {
        sta = staDao.getByPrimaryKey(sta.getId());
        // BiChannel sh = companyShopDao.getByCode(sta.getOwner());
        if (INTERFACETYPE.equals(sta.getInterfaceType())) {
            // Esprit转出反馈
            List<StvLineCommand> stvLines = stvLineDao.findOutboundStvLinesByStaId(sta.getId(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
            // 合并重复明细
            List<StvLineCommand> sls = new ArrayList<StvLineCommand>();
            for (StvLineCommand sc : stvLines) {
                StvLineCommand cmd = sc;
                if (sls.size() > 0) {
                    boolean isExist = false;
                    String extCode2 = cmd.getExtCode2();
                    if (StringUtil.isEmpty(extCode2)) {
                        throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                    }
                    ListIterator<StvLineCommand> iter = sls.listIterator();
                    while (iter.hasNext()) {
                        StvLineCommand c = iter.next();
                        if (!StringUtils.isEmpty(extCode2)) {
                            if (extCode2.equals(c.getExtCode2())) {
                                isExist = true;
                                c.setQuantity(c.getQuantity() + cmd.getQuantity());
                                iter.set(c);
                                break;
                            }
                        }
                    }
                    if (false == isExist) {
                        sls.add(cmd);
                    }
                } else {
                    sls.add(cmd);
                }
            }
            Date transDate = new Date();
            SimpleDateFormat sdf = null;
            ESPDelivery espD = null;

            if (sta.getIsEsprit() == null) {// 逻辑不变
                for (StvLineCommand r : sls) {
                    StvLineCommand cmd = r;
                    espD = new ESPDelivery();
                    sdf = new SimpleDateFormat("yyyyMMdd");
                    espD.setHeaderFromgln(ESPDelivery.DELIVERED_FROM_GLN);
                    espD.setHeaderTogln(ESPDelivery.DELIVERED_TO_GLN);
                    espD.setHeaderFromnode(ESPDelivery.DELIVERED_FROM_NODE);
                    espD.setHeaderTonode(ESPDelivery.DELIVERED_TO_NODE);
                    espD.setDeliveryDeliveryDate(sdf.format(transDate));
                    espD.setHeaderNumberofRecords(String.valueOf(sta.getSkuQty()));
                    espD.setHeaderGenerationDate(sdf.format(transDate));
                    espD.setDeliveryGoodsReceiptDate(sdf.format(sta.getCreateTime()));
                    sdf = new SimpleDateFormat("HHmmss");
                    espD.setHeaderGenerationTime(sdf.format(transDate));
                    espD.setDeliveryDeliveryNO(sta.getRefSlipCode());
                    espD.setDeliveryDeliverytype(ESPDelivery.DELIVERED_TYPE);
                    espD.setDeliveryDeliveryStatus(ESPDelivery.DELIVERED_STATUS);
                    espD.setDeliveryDeliveredfromGLN(sta.getFromLocation());
                    espD.setDeliveryDeliveredtoGLN(sta.getToLocation());
                    espD.setStaCode(sta.getCode());
                    if (null != sta.getToLocation() && "4046655078762".equals(sta.getToLocation())) {
                        espD.setDeliveryDeliverytype("P");
                    }
                    espD.setStatus(ESPDelivery.STATUS_DOING);
                    espD.setCreateTime(new Date());
                    espD.setItemReceivedQTY(String.valueOf(cmd.getQuantity()));
                    String skuCode = cmd.getExtCode2();
                    if (StringUtil.isEmpty(skuCode)) {
                        throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                    }
                    espD.setItemSku(skuCode);
                    espD.setVersion(new Date());
                    espDeliveryDao.save(espD);
                    espDeliveryDao.flush();
                }
            } else {// 新增逻辑
                EspritStoreCommand es = espritStoreDao.findEspritEn(null, sta.getCtCode(), null, null, null, new BeanPropertyRowMapperExt<EspritStoreCommand>(EspritStoreCommand.class));
                if (es == null) {
                    log.error("findEspritEn en 为空" + sta.getCtCode());
                    throw new BusinessException("findEspritEn en 为空:" + sta.getCtCode());
                }
                for (StvLineCommand r : sls) {
                    StvLineCommand cmd = r;
                    espD = new ESPDelivery();
                    sdf = new SimpleDateFormat("yyyyMMdd");
                    espD.setHeaderFromgln("4046655075778");
                    espD.setHeaderTogln(es.getCityGln());// 城市gln
                    espD.setHeaderFromnode("CECOMEWW");
                    espD.setHeaderTonode(es.getCityCode());// 城市编码
                    espD.setDeliveryDeliveryDate(sdf.format(transDate));
                    espD.setHeaderNumberofRecords(String.valueOf(sta.getSkuQty()));
                    espD.setHeaderGenerationDate(sdf.format(transDate));
                    espD.setDeliveryGoodsReceiptDate(sdf.format(sta.getCreateTime()));
                    sdf = new SimpleDateFormat("HHmmss");
                    espD.setHeaderGenerationTime(sdf.format(transDate));
                    espD.setDeliveryDeliveryNO(sta.getRefSlipCode());
                    espD.setDeliveryDeliverytype("P");
                    espD.setDeliveryDeliveryStatus("D");
                    espD.setDeliveryDeliveredfromGLN("4046655075778");
                    espD.setDeliveryDeliveredtoGLN(sta.getToLocation());// 店铺gln
                    espD.setStaCode(sta.getCode());
                    espD.setStatus(ESPDelivery.STATUS_DOING);
                    espD.setCreateTime(new Date());
                    espD.setItemReceivedQTY(String.valueOf(cmd.getQuantity()));
                    String skuCode = cmd.getExtCode2();
                    if (StringUtil.isEmpty(skuCode)) {
                        throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                    }
                    espD.setItemSku(skuCode);
                    espD.setVersion(new Date());
                    espDeliveryDao.save(espD);
                    espDeliveryDao.flush();
                }

            }
        }
    }

    /**
     * Esprit转店退仓出库生成：W100001序列
     */
    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        String slipCode = "W";
        slipCode += String.valueOf(staDao.findEspritRDSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
        return slipCode;
    }

    /**
     * Esprit转入收货关单反馈
     */
    @Override
    public void generateReceivingWhenClose(StockTransApplication sta) {
        sta = staDao.getByPrimaryKey(sta.getId());
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        if (INTERFACETYPE.equals(sta.getInterfaceType())) {
            // Esprit转入收货单关闭反馈
            List<ESPDeliveryReceiveCommand> list = espRDeliveryManager.findReceiveOrdersByCloseStaId(sta.getId());
            if (0 == list.size()) {
                return;
            }
            Date transDate = new Date();
            SimpleDateFormat sdf = null;
            ESPDelivery espD = null;

            if (sta.getIsEsprit() != null) {
                ESPDeliveryReceiveCommand es = eSPDeliveryReceiveDao.findReceiveOrdersByStaId2(sta.getId(), new BeanPropertyRowMapperExt<ESPDeliveryReceiveCommand>(ESPDeliveryReceiveCommand.class));
                if (es == null) {
                    log.error("es为null");
                    throw new BusinessException("es为null");
                }
                for (ESPDeliveryReceiveCommand r : list) {
                    ESPDeliveryReceiveCommand cmd = r;
                    espD = new ESPDelivery();
                    sdf = new SimpleDateFormat("yyyyMMdd");
                    // espD.setHeaderFromgln("4046655060392");
                    // espD.setHeaderTogln("4046655075778");
                    // espD.setHeaderFromnode("CECOMEWW");
                    // espD.setHeaderTonode("ERSHA");

                    espD.setHeaderFromgln(es.gethFromGln());
                    espD.setHeaderTogln(es.gethToGln());
                    espD.setHeaderFromnode(es.gethFromNode());
                    espD.setHeaderTonode(es.gethToNode());

                    espD.setHeaderNumberofRecords("0");// 反馈0
                    espD.setHeaderGenerationDate(sdf.format(transDate));
                    sdf = new SimpleDateFormat("HHmmss");
                    espD.setHeaderGenerationTime(sdf.format(transDate));
                    espD.setDeliveryDeliveryNO(sta.getRefSlipCode());
                    sdf = new SimpleDateFormat("yyyyMMdd");
                    espD.setDeliveryDeliveryDate(sdf.format(transDate));
                    espD.setDeliveryDeliverytype("R");
                    espD.setDeliveryDeliveryStatus("A");
                    espD.setDeliveryGoodsReceiptDate(sdf.format(sta.getCreateTime()));
                    espD.setDeliveryDeliveredfromGLN(sta.getFromLocation());
                    espD.setDeliveryDeliveredtoGLN(sta.getToLocation());
                    espD.setStaCode(sta.getCode());
                    espD.setStatus(ESPDelivery.STATUS_CLOSE_DOING);// 关单反馈
                    espD.setCreateTime(new Date());
                    espD.setItemReceivedQTY(cmd.getSkuQty());// 反馈数量
                    espD.setItemSku(cmd.getExtCode2());
                    espD.setVersion(new Date());
                    espDeliveryDao.save(espD);
                    espDeliveryDao.flush();
                }

            } else {// 原来逻辑
                for (ESPDeliveryReceiveCommand r : list) {
                    ESPDeliveryReceiveCommand cmd = r;
                    espD = new ESPDelivery();
                    sdf = new SimpleDateFormat("yyyyMMdd");
                    espD.setHeaderFromgln(ESPDelivery.DISPATCH_FROM_GLN);
                    espD.setHeaderTogln(ESPDelivery.DISPATCH_TO_GLN);
                    espD.setHeaderFromnode(ESPDelivery.DISPATCH_FROM_NODE);
                    espD.setHeaderTonode(ESPDelivery.DISPATCH_TO_NODE);
                    espD.setHeaderNumberofRecords("0");// 反馈0
                    espD.setHeaderGenerationDate(sdf.format(transDate));
                    sdf = new SimpleDateFormat("HHmmss");
                    espD.setHeaderGenerationTime(sdf.format(transDate));
                    espD.setDeliveryDeliveryNO(sta.getRefSlipCode());
                    sdf = new SimpleDateFormat("yyyyMMdd");
                    espD.setDeliveryDeliveryDate(sdf.format(transDate));
                    espD.setDeliveryDeliverytype(ESPDelivery.DELIVERED_TYPE);
                    espD.setDeliveryDeliveryStatus(ESPDelivery.DELIVERED_STATUS);
                    espD.setDeliveryGoodsReceiptDate(sdf.format(sta.getCreateTime()));
                    espD.setDeliveryDeliveredfromGLN(sta.getFromLocation());
                    espD.setDeliveryDeliveredtoGLN(sta.getToLocation());
                    espD.setStaCode(sta.getCode());
                    espD.setStatus(ESPDelivery.STATUS_CLOSE_DOING);// 关单反馈
                    espD.setCreateTime(new Date());
                    espD.setItemReceivedQTY(cmd.getSkuQty());// 反馈数量
                    espD.setItemSku(cmd.getExtCode2());
                    espD.setVersion(new Date());
                    espDeliveryDao.save(espD);
                    espDeliveryDao.flush();
                }
            }
        } else {
            StockTransVoucher stv = stvDao.findStvCreatedByStaId2(sta.getId());
            Long rootStaId = sta.getGroupSta() == null ? null : sta.getGroupSta().getId();
            if (null != rootStaId) {
                Long qty = 0L;
                // 更新主作业单执行量
                List<StaLineCommand> staLineMainList = staLineDao.findStaLineByStaId5(rootStaId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                List<StaLineCommand> staLineList = staLineDao.findStaLineByStaId5(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                for (StaLineCommand staLineCommand : staLineList) {
                    for (StaLineCommand staLineMain : staLineMainList) {
                        if (staLineCommand.getSkuId().equals(staLineMain.getSkuId())) {
                            qty = staLineMain.getCompleteQuantity() == null ? 0 : staLineMain.getCompleteQuantity();
                            qty = qty + staLineCommand.getCompleteQuantity();
                            staLineDao.updateStaLineCompleteQuantity(staLineMain.getId(), qty);
                            break;
                        }
                    }
                }
                List<ESPOrderCommand> recList = null;
                Date date = new Date();
                // 子作业单反馈
                if (stv != null) {
                    recList = orderDao.findESPOrderCommandByStvId3(sta.getId(), new BeanPropertyRowMapperExt<ESPOrderCommand>(ESPOrderCommand.class));
                    stv = stvDao.getByPrimaryKey(stv.getId());
                }
                ESPOrder ord = orderDao.getLastedOrderByPO(sta.getRefSlipCode());
                if (ord == null) {
                    ord = orderDao.getLastedOrderByPO(sta.getSlipCode1());
                }
                ESPReceiving receving = null;
                Long sequenceNumber = getSequenceNumber(date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String receiveDate = sdf.format(new Date());
                String seqNumber = String.format("%06d", sequenceNumber);
                if (recList != null) {
                    for (ESPOrderCommand command : recList) {
                        receving = new ESPReceiving();
                        receving.setHeaderFromGLN(Constants.ESPRIT_BAOZUN_GLN);
                        receving.setHeaderToGLN(Constants.ESPRIT_ESPRIT_GLN);
                        receving.setHeaderFromNode(Constants.BAOZUN_NODE);
                        receving.setHeaderToNode(Constants.ESPRIT_NODE);
                        receving.setHeaderSequenceNumber(seqNumber);
                        receving.setReceivingGoodsReceivedDate(receiveDate);
                        receving.setReceivingWarehouseReference("BZ" + receiveDate);
                        if (command.getOdPO() != null && !"".equals(command.getOdPO())) {
                            receving.setReceivingOrdernumber(command.getOdPO());
                        } else {
                            receving.setReceivingOrdernumber(sta.getSlipCode1() == null ? sta.getRefSlipCode() : sta.getSlipCode1());
                        }
                        receving.setReceivingPoReference(ord.getOdPoreference());
                        receving.setInvoiceCurrency(sta.getCurrency());
                        if (sta.getTotalFOB() != null) {
                            receving.setInvoiceTotalfob(sta.getTotalFOB().toString());
                        } else {
                            receving.setInvoiceTotalfob("1.0");
                        }
                        Long qty1 = staLineDao.findTotalQtyByStaId(rootStaId, new SingleColumnRowMapper<Long>(Long.class));
                        if (qty1 != null) {
                            receving.setInvoiceTotalqty(qty1.toString());
                        } else {
                            receving.setInvoiceTotalqty(sta.getSkuQty().toString());
                        }
                        if (sta.getTotalGTP() != null) {
                            receving.setInvoiceTotalgtp(sta.getTotalGTP().toString());
                        }
                        receving.setReceivingBuyergln(Constants.ESPRIT_BAOZUN_GLN);
                        receving.setReceivingDeliverypartygln(Constants.ESPRIT_ESPRIT_GLN);
                        receving.setReceivingWarehouse(Constants.ESPRIT_VIM_CODE);
                        receving.setItemSku(command.getBarCode());
                        receving.setItemReceivedqty(command.getTotalQuantity().toString());
                        receving.setVersion(date);
                        receivingDao.save(receving);
                    }
                }
            }
        }
    }

}
