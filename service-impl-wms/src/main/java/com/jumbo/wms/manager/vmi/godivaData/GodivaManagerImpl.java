package com.jumbo.wms.manager.vmi.godivaData;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.connector.gdv.model.adjustment.Adjustment;
import cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation;
import cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader;
import cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.AdjustmentDetail;
import cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.AdjustmentDetail.AdjustmentDetailInformation;
import cn.baozun.bh.connector.gdv.model.inventory.Inventorys;
import cn.baozun.bh.connector.gdv.model.movement.Movement;
import cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation;
import cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader;
import cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader.MovementDetail;
import cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader.MovementDetail.MoveDetailInformation;
import cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM;
import cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation;
import cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader;
import cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.GeneralInformation;
import cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.ReferenceInformation.UserDefinedLongItem;
import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.boc.VmiInventorySnapshotDataDao;
import com.jumbo.dao.vmi.godivaData.GodivaInventoryAdjustmentDao;
import com.jumbo.dao.vmi.godivaData.GodivaInventoryAdjustmentLineDao;
import com.jumbo.dao.vmi.kerry.CarrierConsignmentDataDao;
import com.jumbo.dao.vmi.warehouse.CompanyShopShareDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WmsOtherOutBoundInvNoticeOmsDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.boc.VmiInventorySnapshotData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotDataCommand;
import com.jumbo.wms.model.vmi.godivaData.GodivaInventoryAdjustment;
import com.jumbo.wms.model.vmi.godivaData.GodivaInventoryAdjustmentLine;
import com.jumbo.wms.model.vmi.kerry.CarrierConsignmentData;
import com.jumbo.wms.model.vmi.warehouse.CompanyShopShare;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOms;

/**
 * 
 * @author wudan
 * 
 */
@Transactional
@Service("gdvParseDataManager")
public class GodivaManagerImpl implements GodivaManager {

    protected static final Logger log = LoggerFactory.getLogger(GodivaManagerImpl.class);

    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundOrderLineDao;
    @Autowired
    private GodivaInventoryAdjustmentDao godivaInventoryAdjustmentDao;
    @Autowired
    private GodivaInventoryAdjustmentLineDao godivaInventoryAdjustmentLineDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private CarrierConsignmentDataDao carrierConsignmentDataDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private InventoryCheckDifferenceLineDao icDifferenceLineDao;
    @Autowired
    private WarehouseLocationDao locDao;
    @Autowired
    private InventoryCheckDifTotalLineDao vmiinvCheckLineDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private CompanyShopShareDao companyShopShareDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutboundLineDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfodao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;

    @Autowired
    private VmiInventorySnapshotDataDao vmiInventorySnapshotDataDao;
    @Autowired
    private WmsOtherOutBoundInvNoticeOmsDao wmsOtherOutBoundInvNoticeOmsDao;
    @Autowired
    private HubWmsService hubWmsService;

    public Map<Long, String> whMap() {
        Map<Long, String> mapwh = new HashMap<Long, String>();
        mapwh.put(601L, "GODIVASH");
        mapwh.put(3122L, "GODIVABJ");
        mapwh.put(3122L, "GODIVAGZ");
        return mapwh;
    }

    @Override
    public MsgRtnOutbound receiveGodivaOutboundByMq(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        MsgRtnOutbound rtnOutbound = new MsgRtnOutbound();
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            OutboundCFM outbound = (OutboundCFM) JSONUtil.jsonToBean(messageContent, OutboundCFM.class);
            OutboundConfirmation outconfirmation = outbound.getOutboundConfirmation();
            // 记录仓库标识
            rtnOutbound.setRemark(outconfirmation.getStorerIdentifier());
            log.debug("=========GODIVAOUTBOUND START===========");
            OutboundConfirmationHeader outboundHear = outconfirmation.getOutboundConfirmationHeader();
            GeneralInformation info = outboundHear.getGeneralInformation();
            rtnOutbound.setOutboundTime(dateFormat.parse(info.getReleaseDate()));
            cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.ReferenceInformation refinfo = outboundHear.getReferenceInformation();
            List<cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.ReferenceInformation.ReferenceOrder> referenceList = refinfo.getReferenceOrder();
            for (cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.ReferenceInformation.ReferenceOrder refer : referenceList) {
                if (("DeliveryRefence").equals(refer.getReferenceOrderType())) {
                    rtnOutbound.setSlipCode(refer.getReferenceOrderNumber());
                }
            }
            log.debug("=========GODIVAOUTBOUND CODE:===========" + rtnOutbound.getSlipCode());
            for (UserDefinedLongItem userdefined : refinfo.getUserDefinedLongItem()) {
                if (9 == userdefined.getUserDefinedLongItemIndex()) {
                    rtnOutbound.setLpCode(userdefined.getUserDefinedLongItemValue());
                }
                if (10 == userdefined.getUserDefinedLongItemIndex()) {
                    rtnOutbound.setTrackingNo(userdefined.getUserDefinedLongItemValue());
                }
            }
            if (StringUtils.hasText(rtnOutbound.getSlipCode())) {
                rtnOutbound.setStatus(DefaultStatus.CREATED);
                StockTransApplication sta = staDao.finrefSlipCode(rtnOutbound.getSlipCode());
                Long customerId = null;
                if (sta != null) {
                    rtnOutbound.setStaCode(sta.getCode());
                    customerId = wareHouseManagerQuery.getCustomerByWhouid(sta.getMainWarehouse().getId());
                }
                // 标记仓库
                rtnOutbound.setSource(outconfirmation.getStorerIdentifier());
                rtnOutbound.setCreateTime(new Date());
                rtnOutbound = msgRtnDao.save(rtnOutbound);
                List<cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.OutboundConfirmationDetail> detailList = outboundHear.getOutboundConfirmationDetail();
                for (cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.OutboundConfirmationDetail detail : detailList) {
                    cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.OutboundConfirmationDetail.FreightInformation freightInformation = detail.getFreightInformation();
                    MsgRtnOutboundLine line = new MsgRtnOutboundLine();
                    line.setBarCode(freightInformation.getSKU());
                    cn.baozun.bh.connector.gdv.model.outboundconfirm.OutboundCFM.OutboundConfirmation.OutboundConfirmationHeader.OutboundConfirmationDetail.OperationInformation operationInformation = detail.getOperationInformation();
                    line.setQty(Long.parseLong(operationInformation.getShippedQuantity()));
                    Sku sku = skuDao.getByBarcode(freightInformation.getSKU(), customerId);
                    if (sku != null) {
                        line.setSkuCode(sku.getCode());
                    }
                    line.setMsgOutbound(rtnOutbound);
                    msgRtnOutboundLineDao.save(line);
                }
                msgRtnDao.flush();
            }
        } catch (ParseException e) {
            log.error("", e);
        } catch (Exception ex) {
            log.error("", ex);
        }
        log.debug("=========GODIVAOUTBOUND END===========");
        return rtnOutbound;
    }

    @Override
    public List<MsgRtnInboundOrder> receiveGodivaOutInventoryMovement(String message) {

        /* Godiva 库存修改添加代码 */
        List<CompanyShopShare> shares = companyShopShareDao.findShopShares("GDV", new BeanPropertyRowMapperExt<CompanyShopShare>(CompanyShopShare.class));

        if (shares == null || shares.size() != 3) {
            log.debug("========= {Godiva} NOT FOUNT COMPANYSHOPSHARE OR {Godiva} COMPANYSHOPSHARE INFORMATION HAS ERRORS ===========receiveGodivaOutInventoryMovement");
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        /* Godiva 库存修改添加代码 */
        ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
        String messageContent = connectorMessage.getMessageContent();
        if (connectorMessage.getIsCompress()) {
            try {
                messageContent = ZipUtil.decompress(messageContent);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        Movement movement = (Movement) JSONUtil.jsonToBean(messageContent, Movement.class);
        List<MsgRtnInboundOrder> orderlist = new ArrayList<MsgRtnInboundOrder>();
        MovementInformation movementInformation = movement.getMovementInformation();
        InventoryStatus isForSaleTrue = null;
        InventoryStatus isForSaleFlase = null;
        OperationUnit ou = null;
        MovementHeader header = movementInformation.getMovementHeader();
        cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader.GeneralInformation general = header.getGeneralInformation();
        Warehouse wh = wareHouseManager.getWareHouseByVmiSource(movementInformation.getStorerIdentifier());
        if (isForSaleTrue == null && isForSaleFlase == null) {
            if (wh != null) {
                Long ouId = wh.getOu().getId();
                ou = ouDao.getByPrimaryKey(ouId);
                if (ou == null) {
                    log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {movementInformation.getStorerIdentifier()});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
            } else {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {movementInformation.getStorerIdentifier()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            Long companyId = null;
            if (ou.getParentUnit() != null) {
                OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                if (ou1 != null) {
                    companyId = ou1.getParentUnit().getId();
                }
            }
            isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
            isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);
        }
        List<MovementDetail> deilaList = header.getMovementDetail();
        Map<String, Map<InventoryStatus, MsgRtnInboundOrderLine>> map = new HashMap<String, Map<InventoryStatus, MsgRtnInboundOrderLine>>();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(wh.getOu().getId());
        for (MovementDetail detail : deilaList) {
            Map<InventoryStatus, MsgRtnInboundOrderLine> mapLine = null;
            MoveDetailInformation moveDetailInformation = detail.getMoveDetailInformation();
            if (("To").equals(moveDetailInformation.getDetailType())) {
                cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader.MovementDetail.GeneralInformation information = detail.getGeneralInformation();
                Map<InventoryStatus, MsgRtnInboundOrderLine> rtnLineMap = map.get(information.getSKU());
                if (rtnLineMap != null) {
                    if (!("0").equals(moveDetailInformation.getGoodQuantity())) {
                        MsgRtnInboundOrderLine orderLine = rtnLineMap.get(isForSaleTrue);
                        if (orderLine == null) {
                            orderLine = new MsgRtnInboundOrderLine();
                            orderLine.setBarcode(information.getSKU());
                            Sku sku = skuDao.getByBarcode(information.getSKU(), customerId);
                            if (sku != null) {
                                orderLine.setSkuId(sku.getId());
                                orderLine.setSkuCode(sku.getCode());
                            }
                            orderLine.setInvStatus(isForSaleTrue); // 良品
                            orderLine.setQty(Long.parseLong(moveDetailInformation.getGoodQuantity()));
                            orderLine.setGoodBase(Long.parseLong(moveDetailInformation.getGoodBase()));
                            orderLine.setDamagedBase(Long.parseLong(moveDetailInformation.getDamagedBase()));
                            orderLine.setRemark(information.getRemark());
                            rtnLineMap.put(isForSaleTrue, orderLine);
                        } else {
                            orderLine.setQty(orderLine.getQty() + Long.parseLong(moveDetailInformation.getGoodQuantity()));
                            orderLine.setGoodBase(orderLine.getGoodBase() + Long.parseLong(moveDetailInformation.getGoodBase()));
                            orderLine.setDamagedBase(orderLine.getDamagedBase() + Long.parseLong(moveDetailInformation.getDamagedBase()));
                            orderLine.setRemark(information.getRemark());
                            rtnLineMap.put(isForSaleTrue, orderLine);
                        }
                    }
                    if (!("0").equals(moveDetailInformation.getDamagedQuantity())) {
                        MsgRtnInboundOrderLine orderLine = rtnLineMap.get(isForSaleFlase);
                        if (orderLine == null) {
                            orderLine = new MsgRtnInboundOrderLine();
                            orderLine.setBarcode(information.getSKU());
                            Sku sku = skuDao.getByBarcode(information.getSKU(), customerId);
                            if (sku != null) {
                                orderLine.setSkuId(sku.getId());
                                orderLine.setSkuCode(sku.getCode());
                            }
                            orderLine.setInvStatus(isForSaleFlase); // 残次
                            orderLine.setQty(Long.parseLong(moveDetailInformation.getDamagedQuantity()));
                            orderLine.setGoodBase(Long.parseLong(moveDetailInformation.getGoodBase()));
                            orderLine.setDamagedBase(Long.parseLong(moveDetailInformation.getDamagedBase()));
                            orderLine.setRemark(information.getRemark());
                            rtnLineMap.put(orderLine.getInvStatus(), orderLine);
                        } else {
                            orderLine.setQty(orderLine.getQty() + Long.parseLong(moveDetailInformation.getDamagedQuantity()));
                            orderLine.setGoodBase(orderLine.getGoodBase() + Long.parseLong(moveDetailInformation.getGoodBase()));
                            orderLine.setDamagedBase(orderLine.getDamagedBase() + Long.parseLong(moveDetailInformation.getDamagedBase()));
                            orderLine.setRemark(information.getRemark());
                            rtnLineMap.put(isForSaleFlase, orderLine);
                        }
                    }
                    map.put(information.getSKU(), rtnLineMap);
                } else {
                    MsgRtnInboundOrderLine line = null;
                    mapLine = new HashMap<InventoryStatus, MsgRtnInboundOrderLine>();
                    if (!("0").equals(moveDetailInformation.getGoodQuantity())) {
                        line = new MsgRtnInboundOrderLine();
                        line.setBarcode(information.getSKU());
                        Sku sku = skuDao.getByBarcode(information.getSKU(), customerId);
                        if (sku != null) {
                            line.setSkuId(sku.getId());
                            line.setSkuCode(sku.getCode());
                        }
                        line.setInvStatus(isForSaleTrue); // 良品
                        line.setQty(Long.parseLong(moveDetailInformation.getGoodQuantity()));
                        line.setGoodBase(Long.parseLong(moveDetailInformation.getGoodBase()));
                        line.setDamagedBase(Long.parseLong(moveDetailInformation.getDamagedBase()));
                        line.setRemark(information.getRemark());
                        mapLine.put(line.getInvStatus(), line);
                    }
                    if (!("0").equals(moveDetailInformation.getDamagedQuantity())) {
                        line = new MsgRtnInboundOrderLine();
                        line.setBarcode(information.getSKU());
                        Sku sku = skuDao.getByBarcode(information.getSKU(), customerId);
                        if (sku != null) {
                            line.setSkuId(sku.getId());
                            line.setSkuCode(sku.getCode());
                        }
                        line.setInvStatus(isForSaleFlase); // 残次
                        line.setQty(Long.parseLong(moveDetailInformation.getDamagedQuantity()));
                        line.setGoodBase(Long.parseLong(moveDetailInformation.getGoodBase()));
                        line.setDamagedBase(Long.parseLong(moveDetailInformation.getDamagedBase()));
                        line.setRemark(information.getRemark());
                        mapLine.put(line.getInvStatus(), line);
                    }
                    map.put(information.getSKU(), mapLine);
                }
            }
        }

        if (("Add").equals(general.getRemark())) {
            orderlist = this.createMonve(general, movementInformation.getStorerIdentifier(), isForSaleTrue, isForSaleFlase, map, wh);
        } else if (("Minus").equals(general.getRemark())) {
            orderlist = this.createReturn(general, movementInformation.getStorerIdentifier(), isForSaleTrue, isForSaleFlase, map, wh);
        }
        return orderlist;

    }

    public List<MsgRtnInboundOrder> createReturn(cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader.GeneralInformation general, String storerIdentifier, InventoryStatus isForSaleTrue, InventoryStatus isForSaleFlase,
            Map<String, Map<InventoryStatus, MsgRtnInboundOrderLine>> map, Warehouse wh) {
        log.debug("========= createReturn start===========");
        List<MsgRtnInboundOrder> orderlist = new ArrayList<MsgRtnInboundOrder>();

        List<CompanyShopShare> sharesList = companyShopShareDao.findShopShares("GDV", new BeanPropertyRowMapperExt<CompanyShopShare>(CompanyShopShare.class));

        MsgRtnInboundOrder gdvReturn1 = new MsgRtnInboundOrder();
        MsgRtnInboundOrder gdvReturn2 = new MsgRtnInboundOrder();
        MsgRtnInboundOrder gdvReturn3 = new MsgRtnInboundOrder();

        CompanyShopShare share = sharesList.get(0);
        BiChannel shop = shopDao.getByPrimaryKey(share.getShop().getId());
        if (shop == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        CompanyShopShare share2 = sharesList.get(1);

        Map<String, Long> mapQty = findInventoriesByShop(shop.getCode(), wh.getOu().getId());

        BiChannel shop2 = shopDao.getByPrimaryKey(share2.getShop().getId());
        if (shop2 == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        Map<String, Long> mapQty2 = findInventoriesByShop(shop2.getCode(), wh.getOu().getId());

        CompanyShopShare share3 = sharesList.get(2);
        BiChannel shop3 = shopDao.getByPrimaryKey(share3.getShop().getId());
        if (shop3 == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        Map<String, Long> mapQty3 = findInventoriesByShop(shop3.getCode(), wh.getOu().getId());

        for (String sku : map.keySet()) {
            Map<InventoryStatus, MsgRtnInboundOrderLine> rtnLineMapinfo = map.get(sku);
            if (rtnLineMapinfo != null) {

                // 处理良品商品库存出库
                MsgRtnInboundOrderLine line = rtnLineMapinfo.get(isForSaleTrue);

                handleSkuMinuInvMsg(line, general, storerIdentifier, share, share2, share3, mapQty, mapQty2, mapQty3, orderlist, gdvReturn1, gdvReturn2, gdvReturn3);

                // 处理残次商品库存出库
                MsgRtnInboundOrderLine lineFlase = rtnLineMapinfo.get(isForSaleFlase);

                handleSkuMinuInvMsg(lineFlase, general, storerIdentifier, share, share2, share3, mapQty, mapQty2, mapQty3, orderlist, gdvReturn1, gdvReturn2, gdvReturn3);

            }

        }
        msgRtnInboundOrderDao.flush();
        log.debug("========= createReturn end===========");
        return orderlist;
    }

    /**
     * 根据商品处理出库信息。
     * 
     * @param line 出库商品信息
     * @param general
     * @param storerIdentifier
     * @param share 店铺1
     * @param share2 店铺2
     * @param share3 店铺3
     * @param mapQty 店铺1商品库存信息
     * @param mapQty2 店铺2商品库存信息
     * @param mapQty3 店铺3商品库存信息
     * @param orderlist
     * @param gdvReturn3
     * @param gdvReturn2
     * @param gdvReturn1
     */
    private void handleSkuMinuInvMsg(MsgRtnInboundOrderLine line, cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader.GeneralInformation general, String storerIdentifier, CompanyShopShare share, CompanyShopShare share2,
            CompanyShopShare share3, Map<String, Long> mapQty, Map<String, Long> mapQty2, Map<String, Long> mapQty3, List<MsgRtnInboundOrder> orderlist, MsgRtnInboundOrder gdvReturn1, MsgRtnInboundOrder gdvReturn2, MsgRtnInboundOrder gdvReturn3) {
        log.debug("========= handleSkuInvMsg start===========");

        if (line != null) {
            String key = line.getBarcode() + "_" + line.getInvStatus().getId();
            if (mapQty.containsKey(key)) {
                if (gdvReturn1 == null || gdvReturn1.getId() == null) {
                    this.createGdvMsgRtnMovement(general, storerIdentifier, share.getShop().getId(), gdvReturn1);
                    /* gdvReturn1 = msgRtnInboundOrderDao.save(gdvReturn1); */
                    orderlist.add(gdvReturn1);
                }
                line.setMsgRtnInOrder(gdvReturn1);
                if (line.getQty() > mapQty.get(key)) {
                    Long rtnqtyLong = line.getQty() - mapQty.get(key);
                    line.setQty(mapQty.get(key));
                    // 判断店铺2是否存在该商品
                    if (mapQty2.containsKey(key)) {
                        if (gdvReturn2 == null || gdvReturn2.getId() == null) {
                            this.createGdvMsgRtnMovement(general, storerIdentifier, share2.getShop().getId(), gdvReturn2);
                            orderlist.add(gdvReturn2);
                        }
                        MsgRtnInboundOrderLine line2 = new MsgRtnInboundOrderLine();
                        // 设置值
                        setOrderLineMsg(line, gdvReturn2, line2);
                        // 判断第二个店铺要减去的库存数量
                        if (rtnqtyLong > mapQty2.get(key)) {

                            Long rtnqty = rtnqtyLong - mapQty2.get(key);
                            line2.setQty(mapQty2.get(key));
                            // 判断店铺3是否存在该商品
                            if (mapQty3.containsKey(key)) {
                                if (gdvReturn3 == null || gdvReturn3.getId() == null) {
                                    this.createGdvMsgRtnMovement(general, storerIdentifier, share3.getShop().getId(), gdvReturn3);
                                    orderlist.add(gdvReturn3);
                                }

                                MsgRtnInboundOrderLine line3 = new MsgRtnInboundOrderLine();

                                // 设置值
                                setOrderLineMsg(line, gdvReturn3, line3);

                                // 判断第三个店铺要减去的库存数量
                                if (rtnqty > mapQty3.get(key)) {
                                    line3.setQty(mapQty3.get(key));
                                } else {
                                    line3.setQty(rtnqty);
                                }
                                msgRtnInboundOrderLineDao.save(line3);
                            }

                        } else {
                            line2.setQty(rtnqtyLong);
                        }
                        msgRtnInboundOrderLineDao.save(line2);
                    } else if (mapQty3.containsKey(key)) {
                        if (gdvReturn3 == null || gdvReturn3.getId() == null) {
                            this.createGdvMsgRtnMovement(general, storerIdentifier, share3.getShop().getId(), gdvReturn3);
                            orderlist.add(gdvReturn3);
                        }
                        MsgRtnInboundOrderLine line3 = new MsgRtnInboundOrderLine();
                        // 设置值
                        setOrderLineMsg(line, gdvReturn3, line3);
                        // 判断第二个店铺要减去的库存数量
                        if (rtnqtyLong > mapQty3.get(key)) {
                            line3.setQty(mapQty3.get(key));

                        } else {
                            line3.setQty(rtnqtyLong);
                        }
                        msgRtnInboundOrderLineDao.save(line3);
                    }
                }
                msgRtnInboundOrderLineDao.save(line);
            } else if (mapQty2.containsKey(key)) {
                if (gdvReturn2 == null || gdvReturn2.getId() == null) {
                    this.createGdvMsgRtnMovement(general, storerIdentifier, share2.getShop().getId(), gdvReturn2);
                    orderlist.add(gdvReturn2);
                }
                MsgRtnInboundOrderLine line2 = new MsgRtnInboundOrderLine();
                // 设置值
                setOrderLineMsg(line, gdvReturn2, line2);
                if (line.getQty() > mapQty2.get(key)) {
                    line2.setQty(mapQty2.get(key));
                    Long rtnqtyLong = line.getQty() - mapQty2.get(key);
                    if (mapQty3.containsKey(key)) {
                        if (gdvReturn3 == null || gdvReturn3.getId() == null) {
                            this.createGdvMsgRtnMovement(general, storerIdentifier, share3.getShop().getId(), gdvReturn3);
                            orderlist.add(gdvReturn3);
                        }
                        MsgRtnInboundOrderLine line3 = new MsgRtnInboundOrderLine();
                        // 设置值
                        setOrderLineMsg(line, gdvReturn3, line3);
                        // 判断第二个店铺要减去的库存数量
                        if (rtnqtyLong > mapQty3.get(key)) {
                            line3.setQty(mapQty3.get(key));

                        } else {
                            line3.setQty(rtnqtyLong);
                        }
                        msgRtnInboundOrderLineDao.save(line3);
                    }
                } else {
                    line2.setQty(line.getQty());
                }
                msgRtnInboundOrderLineDao.save(line2);
            } else if (mapQty3.containsKey(key)) {
                if (gdvReturn3 == null || gdvReturn3.getId() == null) {
                    this.createGdvMsgRtnMovement(general, storerIdentifier, share3.getShop().getId(), gdvReturn3);
                    orderlist.add(gdvReturn3);
                }
                MsgRtnInboundOrderLine line3 = new MsgRtnInboundOrderLine();
                // 设置值
                setOrderLineMsg(line, gdvReturn3, line3);
                if (line.getQty() > mapQty3.get(key)) {
                    line3.setQty(mapQty3.get(key));
                } else {
                    line3.setQty(line.getQty());
                }
                msgRtnInboundOrderLineDao.save(line3);
            }
        }
        log.debug("========= handleSkuInvMsg end===========");

    }

    /**
     * 设置对象中的值
     * 
     * @param line
     * @param gdvReturn
     * @param newLine
     */
    private void setOrderLineMsg(MsgRtnInboundOrderLine line, MsgRtnInboundOrder gdvReturn, MsgRtnInboundOrderLine newLine) {
        log.debug("========= setOrderLineMsg start===========");
        newLine.setBarcode(line.getBarcode());
        newLine.setInvStatus(line.getInvStatus());
        newLine.setGoodBase(line.getGoodBase());
        newLine.setDamagedBase(line.getDamagedBase());
        newLine.setRemark(line.getRemark());
        newLine.setSkuCode(line.getSkuCode());
        newLine.setSkuId(line.getSkuId());
        newLine.setMsgRtnInOrder(gdvReturn);
        log.debug("========= setOrderLineMsg end===========");

    }

    public void createGdvMsgRtnMovement(cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader.GeneralInformation general, String storerIdentifier, Long shopId, MsgRtnInboundOrder gdvReturn2) {
        gdvReturn2.setCreateTime(new Date());
        gdvReturn2.setSource(storerIdentifier);
        gdvReturn2.setSourceWh(storerIdentifier);
        gdvReturn2.setType(StockTransApplicationType.VMI_RETURN.getValue());
        gdvReturn2.setStatus(DefaultStatus.CREATED);
        gdvReturn2.setRefSlipCode(general.getMoveKey());
        gdvReturn2.setRemark(general.getRemark());
        gdvReturn2.setShopId(shopId);
        gdvReturn2 = msgRtnInboundOrderDao.save(gdvReturn2);
    }

    public List<MsgRtnInboundOrder> createMonve(cn.baozun.bh.connector.gdv.model.movement.Movement.MovementInformation.MovementHeader.GeneralInformation general, String storerIdentifier, InventoryStatus isForSaleTrue, InventoryStatus isForSaleFlase,
            Map<String, Map<InventoryStatus, MsgRtnInboundOrderLine>> map, Warehouse wh) {

        List<MsgRtnInboundOrder> orderlist = new ArrayList<MsgRtnInboundOrder>();
        MsgRtnInboundOrder gdvMovement = new MsgRtnInboundOrder();

        List<CompanyShopShare> sharesList = companyShopShareDao.findShopShares("GDV", new BeanPropertyRowMapperExt<CompanyShopShare>(CompanyShopShare.class));

        if (sharesList == null) {
            log.debug("========= {} NOT FOUNT SHARESLIST===========", new Object[] {wh.getOu().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        CompanyShopShare share = sharesList.get(0);
        BiChannel shop = share.getShop();
        if (shop == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        gdvMovement.setCreateTime(new Date());
        gdvMovement.setSource(storerIdentifier);
        gdvMovement.setSourceWh(storerIdentifier);
        gdvMovement.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue());
        gdvMovement.setStatus(DefaultStatus.CREATED);
        gdvMovement.setRefSlipCode(general.getMoveKey());
        gdvMovement.setRemark(general.getRemark());
        gdvMovement.setShopId(share.getShop().getId());
        gdvMovement = msgRtnInboundOrderDao.save(gdvMovement);
        orderlist.add(gdvMovement);
        MsgRtnInboundOrder gdvMovement2 = new MsgRtnInboundOrder();
        CompanyShopShare share2 = sharesList.get(1);
        gdvMovement2.setCreateTime(new Date());
        gdvMovement2.setSource(storerIdentifier);
        gdvMovement2.setSourceWh(storerIdentifier);
        gdvMovement2.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue());
        gdvMovement2.setStatus(DefaultStatus.CREATED);
        gdvMovement2.setRefSlipCode(general.getMoveKey());
        gdvMovement2.setRemark(general.getRemark());
        gdvMovement2.setShopId(share2.getShop().getId());
        gdvMovement2 = msgRtnInboundOrderDao.save(gdvMovement2);
        orderlist.add(gdvMovement2);

        MsgRtnInboundOrder gdvMovement3 = new MsgRtnInboundOrder();
        CompanyShopShare share3 = sharesList.get(2);
        gdvMovement3.setCreateTime(new Date());
        gdvMovement3.setSource(storerIdentifier);
        gdvMovement3.setSourceWh(storerIdentifier);
        gdvMovement3.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue());
        gdvMovement3.setStatus(DefaultStatus.CREATED);
        gdvMovement3.setRefSlipCode(general.getMoveKey());
        gdvMovement3.setRemark(general.getRemark());
        gdvMovement3.setShopId(share3.getShop().getId());
        gdvMovement3 = msgRtnInboundOrderDao.save(gdvMovement3);
        orderlist.add(gdvMovement3);

        for (String sku : map.keySet()) {

            Map<InventoryStatus, MsgRtnInboundOrderLine> rtnLineMapinfo = map.get(sku);

            if (rtnLineMapinfo != null) {
                // 处理良品商品入库信息
                MsgRtnInboundOrderLine line = rtnLineMapinfo.get(isForSaleTrue);
                handleSkuInvAddMsg(line, share, share2, share3, gdvMovement, gdvMovement2, gdvMovement3);
                // 处理残次商品入库信息
                MsgRtnInboundOrderLine linedetail = rtnLineMapinfo.get(isForSaleFlase);
                handleSkuInvAddMsg(linedetail, share, share2, share3, gdvMovement, gdvMovement2, gdvMovement3);
                msgRtnInboundOrderLineDao.flush();
            }
        }
        msgRtnInboundOrderDao.flush();
        return orderlist;
    }

    /**
     * 根据商品处理入库信息。
     * 
     * @param line 入库商品信息
     * @param share 店铺1
     * @param share2 店铺2
     * @param share3 店铺3
     * @param gdvMovement 店铺1商品品牌信息
     * @param gdvMovement2 店铺2商品品牌信息
     * @param gdvMovement3 店铺3商品品牌信息
     */
    private void handleSkuInvAddMsg(MsgRtnInboundOrderLine line, CompanyShopShare share, CompanyShopShare share2, CompanyShopShare share3, MsgRtnInboundOrder gdvMovement, MsgRtnInboundOrder gdvMovement2, MsgRtnInboundOrder gdvMovement3) {
        if (line != null) {

            Long totalQty = line.getQty();

            // 店铺1入库信息
            line.setQty(totalQty * share.getInboundRatio() / 100);
            line.setMsgRtnInOrder(gdvMovement);
            msgRtnInboundOrderLineDao.save(line);
            // 店铺2入库信息
            MsgRtnInboundOrderLine line2 = new MsgRtnInboundOrderLine();
            line2.setBarcode(line.getBarcode());
            line2.setSkuCode(line.getSkuCode());
            line2.setSkuId(line.getSkuId());
            line2.setInvStatus(line.getInvStatus());
            line2.setGoodBase(line.getGoodBase());
            line2.setDamagedBase(line.getDamagedBase());
            line2.setRemark(line.getRemark());
            line2.setQty(totalQty * share2.getInboundRatio() / 100);
            line2.setMsgRtnInOrder(gdvMovement2);
            msgRtnInboundOrderLineDao.save(line2);
            // 店铺3入库信息
            MsgRtnInboundOrderLine line3 = new MsgRtnInboundOrderLine();
            line3.setBarcode(line.getBarcode());
            line3.setSkuCode(line.getSkuCode());
            line3.setSkuId(line.getSkuId());
            line3.setInvStatus(line.getInvStatus());
            line3.setGoodBase(line.getGoodBase());
            line3.setDamagedBase(line.getDamagedBase());
            line3.setRemark(line.getRemark());
            line3.setQty(totalQty - totalQty * share.getInboundRatio() / 100 - totalQty * share2.getInboundRatio() / 100);
            line3.setMsgRtnInOrder(gdvMovement3);
            msgRtnInboundOrderLineDao.save(line3);

        }
    }

    public void createStaByInboundOrder(MsgRtnInboundOrder msgRtnorder) {
        log.debug("-------------Godiva create STA-----------------start-------");
        String innerShopCode = null;
        Warehouse wh = wareHouseManager.getWareHouseByVmiSource(msgRtnorder.getSource());
        if (wh == null) {
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        BiChannel shop = shopDao.getByPrimaryKey(msgRtnorder.getShopId());
        if (shop == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        innerShopCode = shop.getCode();

        // 根据MoveKey判断指令是否已经创建
        StockTransApplication stockTransApplication = staDao.findStaByslipCodeandShopId(msgRtnorder.getRefSlipCode(), innerShopCode);

        if (stockTransApplication != null) {
            if (stockTransApplication.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                msgRtnInboundOrderDao.updateOrderStauts(msgRtnorder.getId(), DefaultStatus.FINISHED.getValue());
                log.debug("The list is complete");
                return;
            }
        }
        // 指令中所有的sku都不存在
        List<MsgRtnInboundOrderLine> lineList = msgRtnInboundOrderLineDao.findInboundOrderLines(msgRtnorder.getId(), new BeanPropertyRowMapperExt<MsgRtnInboundOrderLine>(MsgRtnInboundOrderLine.class));
        if (lineList == null || lineList.size() == 0) {
            log.debug("The Linelist is null");
            return;
        }
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setRefSlipCode(msgRtnorder.getRefSlipCode());

        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, wh.getOu().getId());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setOwner(innerShopCode);
        sta.setMainWarehouse(wh.getOu());

        staDao.save(sta);
        staDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        List<MsgRtnInboundOrderLine> lines = msgRtnInboundOrderLineDao.findRtnOrderLineByRId(msgRtnorder.getId());
        Long skuQty = 0l;
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(wh.getOu().getId());
        for (MsgRtnInboundOrderLine msgRtnLine : lines) {
            Sku sku = skuDao.getByBarcode(msgRtnLine.getBarcode(), customerId);
            if (sku == null) {
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setQuantity(msgRtnLine.getQty());
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            staLine.setOwner(innerShopCode);
            staLine.setInvStatus(msgRtnLine.getInvStatus());
            staLineDao.save(staLine);
            skuQty += staLine.getQuantity();
        }
        sta.setSkuQty(skuQty);
        staLineDao.flush();
        msgRtnInboundOrderDao.updateStaCodeToMsg(sta.getCode(), msgRtnorder.getId());

        log.debug("-------------Godiva create STA-----------------end-------");
    }

    @Override
    public Map<String, List<GodivaInventoryAdjustment>> receiveGodivaInventoryAdjustment(String message) {

        log.debug("=========GodivaInventoryAdjustment START===========");

        ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);

        // String confirmId = connectorMessage.getConfirmId();
        String messageContent = connectorMessage.getMessageContent();
        if (connectorMessage.getIsCompress()) {
            try {
                messageContent = ZipUtil.decompress(messageContent);
            } catch (Exception e) {
                log.error("", e);
            }
        }

        Adjustment adjustment = (Adjustment) JSONUtil.jsonToBean(messageContent, Adjustment.class);


        AdjustmentInformation information = adjustment.getAdjustmentInformation();


        AdjustmentHeader header = information.getAdjustmentHeader();
        cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.GeneralInformation general = header.getGeneralInformation();
        // String adjustmentKey=general.getAdjustmentKey();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        OperationUnit ou = null;
        List<AdjustmentDetail> detailList = header.getAdjustmentDetail();


        Warehouse warehouse = warehouseDao.findWarehouseByVmiSource(information.getStorerIdentifier());
        InventoryStatus isForSaleTrue = null;
        InventoryStatus isForSaleFlase = null;

        if (warehouse == null) {
            log.error("KERRY->GDV Adjustment error ! Warehouse is null");
            throw new BusinessException();
        }


        // 根据组织查询库存状态
        if (isForSaleTrue == null && isForSaleFlase == null) {

            Long ouId = warehouse.getOu().getId();
            ou = ouDao.getByPrimaryKey(ouId);
            if (ou == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {information.getStorerIdentifier()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            Long companyId = null;
            if (ou.getParentUnit() != null) {
                OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                if (ou1 != null) {
                    companyId = ou1.getParentUnit().getId();
                }
            }
            isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
            isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);
        }
        // 保存调整入数量
        Map<String, Map<InventoryStatus, GodivaInventoryAdjustmentLine>> mapAdd = new HashMap<String, Map<InventoryStatus, GodivaInventoryAdjustmentLine>>();
        // 保存调整出数量
        Map<String, Map<InventoryStatus, GodivaInventoryAdjustmentLine>> mapMinus = new HashMap<String, Map<InventoryStatus, GodivaInventoryAdjustmentLine>>();

        for (AdjustmentDetail detail : detailList) {
            cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.AdjustmentDetail.GeneralInformation info = detail.getGeneralInformation();

            AdjustmentDetailInformation detailinfo = detail.getAdjustmentDetailInformation();

            // 良品
            if (0 < Long.parseLong(detailinfo.getAdjustmentGood())) {

                Map<InventoryStatus, GodivaInventoryAdjustmentLine> mapadjust = mapAdd.get(info.getSKU());
                if (mapadjust == null) {
                    mapadjust = new HashMap<InventoryStatus, GodivaInventoryAdjustmentLine>();

                    // 创建行
                    GodivaInventoryAdjustmentLine line = new GodivaInventoryAdjustmentLine();
                    line.setAdjustmentKey(info.getAdjustmentKey());
                    line.setWarehouseCode(info.getWarehouseCode());
                    line.setLocationIdentifier(info.getLocationIdentifier());
                    line.setSku(info.getSKU());
                    try {
                        line.setEffectiveDate(dateFormat.parse(info.getEffectiveDate()));
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    line.setAvailableGood(Long.parseLong(detailinfo.getAvailableGood()));
                    line.setAvailableDamaged(Long.parseLong(detailinfo.getAvailableDamaged()));

                    line.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()));
                    line.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()));
                    // 设置良品数量
                    line.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentGood()));
                    line.setInvStatus(isForSaleTrue);
                    mapadjust.put(isForSaleTrue, line);
                } else {
                    GodivaInventoryAdjustmentLine orgLine = mapadjust.get(isForSaleTrue);
                    if (orgLine == null) {
                        // 创建行
                        GodivaInventoryAdjustmentLine line = new GodivaInventoryAdjustmentLine();
                        line.setAdjustmentKey(info.getAdjustmentKey());
                        line.setWarehouseCode(info.getWarehouseCode());
                        line.setLocationIdentifier(info.getLocationIdentifier());
                        line.setSku(info.getSKU());
                        try {
                            line.setEffectiveDate(dateFormat.parse(info.getEffectiveDate()));
                        } catch (ParseException e) {
                            log.error("", e);
                        }
                        line.setAvailableGood(Long.parseLong(detailinfo.getAvailableGood()));
                        line.setAvailableDamaged(Long.parseLong(detailinfo.getAvailableDamaged()));

                        line.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()));
                        line.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()));
                        // 设置良品数量
                        line.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentGood()));
                        line.setInvStatus(isForSaleTrue);
                        mapadjust.put(isForSaleTrue, line);
                    } else {
                        orgLine.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentGood()) + orgLine.getAdjustmentQty());
                        orgLine.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()) + orgLine.getAvailableGoodBase());
                        orgLine.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()) + orgLine.getAvailableDamagedBase());
                        mapadjust.put(isForSaleTrue, orgLine);
                    }

                }
                mapAdd.put(info.getSKU(), mapadjust);
            } else if (0 > Long.parseLong(detailinfo.getAdjustmentGood())) {

                Map<InventoryStatus, GodivaInventoryAdjustmentLine> mapadjustMinus = mapMinus.get(info.getSKU());
                if (mapadjustMinus == null) {
                    mapadjustMinus = new HashMap<InventoryStatus, GodivaInventoryAdjustmentLine>();

                    // 创建行
                    GodivaInventoryAdjustmentLine line = new GodivaInventoryAdjustmentLine();
                    line.setAdjustmentKey(info.getAdjustmentKey());
                    line.setWarehouseCode(info.getWarehouseCode());
                    line.setLocationIdentifier(info.getLocationIdentifier());
                    line.setSku(info.getSKU());
                    try {
                        line.setEffectiveDate(dateFormat.parse(info.getEffectiveDate()));
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    line.setAvailableGood(Long.parseLong(detailinfo.getAvailableGood()));
                    line.setAvailableDamaged(Long.parseLong(detailinfo.getAvailableDamaged()));

                    line.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()));
                    line.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()));
                    // 设置良品数量
                    line.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentGood()));
                    line.setInvStatus(isForSaleTrue);

                    mapadjustMinus.put(isForSaleTrue, line);
                } else {
                    GodivaInventoryAdjustmentLine orgLine = mapadjustMinus.get(isForSaleTrue);
                    if (orgLine == null) {
                        // 创建行
                        GodivaInventoryAdjustmentLine line = new GodivaInventoryAdjustmentLine();
                        line.setAdjustmentKey(info.getAdjustmentKey());
                        line.setWarehouseCode(info.getWarehouseCode());
                        line.setLocationIdentifier(info.getLocationIdentifier());
                        line.setSku(info.getSKU());
                        try {
                            line.setEffectiveDate(dateFormat.parse(info.getEffectiveDate()));
                        } catch (ParseException e) {

                            log.error("", e);
                        }
                        line.setAvailableGood(Long.parseLong(detailinfo.getAvailableGood()));
                        line.setAvailableDamaged(Long.parseLong(detailinfo.getAvailableDamaged()));

                        line.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()));
                        line.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()));
                        // 设置良品数量
                        line.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentGood()));
                        line.setInvStatus(isForSaleTrue);
                        mapadjustMinus.put(isForSaleTrue, line);
                    } else {
                        orgLine.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentGood()) + orgLine.getAdjustmentQty());
                        orgLine.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()) + orgLine.getAvailableGoodBase());
                        orgLine.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()) + orgLine.getAvailableDamagedBase());
                        mapadjustMinus.put(isForSaleTrue, orgLine);
                    }

                }
                mapMinus.put(info.getSKU(), mapadjustMinus);
            }

            // 残次
            if (0 < Long.parseLong(detailinfo.getAdjustmentDamaged())) {

                Map<InventoryStatus, GodivaInventoryAdjustmentLine> mapadjust = mapAdd.get(info.getSKU());
                if (mapadjust == null) {

                    mapadjust = new HashMap<InventoryStatus, GodivaInventoryAdjustmentLine>();

                    // 创建行
                    GodivaInventoryAdjustmentLine line = new GodivaInventoryAdjustmentLine();
                    line.setAdjustmentKey(info.getAdjustmentKey());
                    line.setWarehouseCode(info.getWarehouseCode());
                    line.setLocationIdentifier(info.getLocationIdentifier());
                    line.setSku(info.getSKU());
                    try {
                        line.setEffectiveDate(dateFormat.parse(info.getEffectiveDate()));
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    line.setAvailableGood(Long.parseLong(detailinfo.getAvailableGood()));
                    line.setAvailableDamaged(Long.parseLong(detailinfo.getAvailableDamaged()));

                    line.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()));
                    line.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()));
                    // 设置调整的残次数量
                    line.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentDamaged()));
                    line.setInvStatus(isForSaleFlase);
                    mapadjust.put(isForSaleFlase, line);
                } else {
                    GodivaInventoryAdjustmentLine orgLine = mapadjust.get(isForSaleFlase);
                    if (orgLine == null) {

                        // 创建行
                        GodivaInventoryAdjustmentLine line = new GodivaInventoryAdjustmentLine();
                        line.setAdjustmentKey(info.getAdjustmentKey());
                        line.setWarehouseCode(info.getWarehouseCode());
                        line.setLocationIdentifier(info.getLocationIdentifier());
                        line.setSku(info.getSKU());
                        try {
                            line.setEffectiveDate(dateFormat.parse(info.getEffectiveDate()));
                        } catch (ParseException e) {
                            log.error("", e);
                        }
                        line.setAvailableGood(Long.parseLong(detailinfo.getAvailableGood()));
                        line.setAvailableDamaged(Long.parseLong(detailinfo.getAvailableDamaged()));
                        if (("0").equals(detailinfo.getAdjustmentGood())) {
                            line.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()));
                            line.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()));
                        }
                        // 设置良品数量
                        line.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentDamaged()));
                        line.setInvStatus(isForSaleFlase);

                        mapadjust.put(isForSaleFlase, line);

                    } else {
                        orgLine.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentDamaged()) + orgLine.getAdjustmentQty());
                        if (("0").equals(detailinfo.getAdjustmentGood())) {
                            orgLine.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()) + orgLine.getAvailableGoodBase());
                            orgLine.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()) + orgLine.getAvailableDamagedBase());
                        }
                        mapadjust.put(isForSaleFlase, orgLine);
                    }

                }
                mapAdd.put(info.getSKU(), mapadjust);
            } else if (0 > Long.parseLong(detailinfo.getAdjustmentDamaged())) {

                Map<InventoryStatus, GodivaInventoryAdjustmentLine> mapadjustDamagedMinus = mapMinus.get(info.getSKU());
                if (mapadjustDamagedMinus == null) {
                    mapadjustDamagedMinus = new HashMap<InventoryStatus, GodivaInventoryAdjustmentLine>();

                    // 创建行
                    GodivaInventoryAdjustmentLine line = new GodivaInventoryAdjustmentLine();
                    line.setAdjustmentKey(info.getAdjustmentKey());
                    line.setWarehouseCode(info.getWarehouseCode());
                    line.setLocationIdentifier(info.getLocationIdentifier());
                    line.setSku(info.getSKU());
                    try {
                        line.setEffectiveDate(dateFormat.parse(info.getEffectiveDate()));
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    line.setAvailableGood(Long.parseLong(detailinfo.getAvailableGood()));
                    line.setAvailableDamaged(Long.parseLong(detailinfo.getAvailableDamaged()));

                    line.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()));
                    line.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()));
                    // 设置调整的残次数量
                    line.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentDamaged()));
                    line.setInvStatus(isForSaleFlase);

                    mapadjustDamagedMinus.put(isForSaleFlase, line);
                } else {
                    GodivaInventoryAdjustmentLine orgLine = mapadjustDamagedMinus.get(isForSaleFlase);

                    if (orgLine == null) {
                        // 创建行
                        GodivaInventoryAdjustmentLine line = new GodivaInventoryAdjustmentLine();
                        line.setAdjustmentKey(info.getAdjustmentKey());
                        line.setWarehouseCode(info.getWarehouseCode());
                        line.setLocationIdentifier(info.getLocationIdentifier());
                        line.setSku(info.getSKU());
                        try {
                            line.setEffectiveDate(dateFormat.parse(info.getEffectiveDate()));
                        } catch (ParseException e) {
                            log.error("", e);
                        }
                        line.setAvailableGood(Long.parseLong(detailinfo.getAvailableGood()));
                        line.setAvailableDamaged(Long.parseLong(detailinfo.getAvailableDamaged()));
                        if (("0").equals(detailinfo.getAdjustmentGood())) {
                            line.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()));
                            line.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()));
                        }
                        // 设置调整的残次数量
                        line.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentDamaged()));
                        line.setInvStatus(isForSaleFlase);
                        mapadjustDamagedMinus.put(isForSaleFlase, line);
                    } else {
                        orgLine.setAdjustmentQty(Long.parseLong(detailinfo.getAdjustmentDamaged()) + orgLine.getAdjustmentQty());
                        if (("0").equals(detailinfo.getAdjustmentGood())) {
                            orgLine.setAvailableGoodBase(Long.parseLong(detailinfo.getAvailableGoodBase()) + orgLine.getAvailableGoodBase());
                            orgLine.setAvailableDamagedBase(Long.parseLong(detailinfo.getAvailableDamagedBase()) + orgLine.getAvailableDamagedBase());
                        }

                        mapadjustDamagedMinus.put(isForSaleFlase, orgLine);
                    }

                }
                mapMinus.put(info.getSKU(), mapadjustDamagedMinus);
            }

        }
        Map<String, List<GodivaInventoryAdjustment>> rtnMap = new HashMap<String, List<GodivaInventoryAdjustment>>();
        if (mapMinus.size() > 0) {
            // 按照比率出
            List<GodivaInventoryAdjustment> rentlistMinus = this.creaAjustMinus(general, information.getStorerIdentifier(), mapMinus, isForSaleTrue, isForSaleFlase);

            rtnMap.put("Minus", rentlistMinus);
        }
        if (mapAdd.size() > 0) {
            // 按照比率入
            List<GodivaInventoryAdjustment> rentlistAdd = this.creaAjustAdd(general, information.getStorerIdentifier(), mapAdd, isForSaleTrue, isForSaleFlase);
            rtnMap.put("Add", rentlistAdd);
        }
        return rtnMap;

    }

    public List<GodivaInventoryAdjustment> creaAjustMinus(cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.GeneralInformation general, String storerIdentifier,
            Map<String, Map<InventoryStatus, GodivaInventoryAdjustmentLine>> mapMinus, InventoryStatus isForSaleTrue, InventoryStatus isForSaleFlase) {

        List<CompanyShopShare> shares = companyShopShareDao.findShopShares("GDV", new BeanPropertyRowMapperExt<CompanyShopShare>(CompanyShopShare.class));

        Warehouse warehouse = warehouseDao.findWarehouseByVmiSource(storerIdentifier);

        if (warehouse == null) {
            log.error("KERRY->GDV Adjustment error ! Warehouse is null");
            throw new BusinessException();
        }

        // 库存调整出
        List<GodivaInventoryAdjustment> ajustMinusList = new ArrayList<GodivaInventoryAdjustment>();
        CompanyShopShare share = shares.get(0);
        GodivaInventoryAdjustment adjust = new GodivaInventoryAdjustment();
        createAdjustment(general, storerIdentifier, share, adjust);
        ajustMinusList.add(adjust);
        CompanyShopShare share2 = shares.get(1);
        GodivaInventoryAdjustment adjust2 = new GodivaInventoryAdjustment();
        CompanyShopShare share3 = shares.get(2);
        GodivaInventoryAdjustment adjust3 = new GodivaInventoryAdjustment();

        log.debug("========= CREATE GODIVAINVENTORYADJUSTMENT ===========");

        BiChannel shop = shopDao.getByPrimaryKey(share.getShop().getId());
        if (shop == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {share.getShop().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        Map<String, Long> mapQty = findInventoriesByShop(shop.getCode(), warehouse.getOu().getId());

        BiChannel shop2 = shopDao.getByPrimaryKey(share2.getShop().getId());
        if (shop2 == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {share2.getShop().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        Map<String, Long> mapQty2 = findInventoriesByShop(shop2.getCode(), warehouse.getOu().getId());

        BiChannel shop3 = shopDao.getByPrimaryKey(share3.getShop().getId());
        if (shop3 == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {share3.getShop().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        Map<String, Long> mapQty3 = findInventoriesByShop(shop3.getCode(), warehouse.getOu().getId());

        for (String sku : mapMinus.keySet()) {
            Map<InventoryStatus, GodivaInventoryAdjustmentLine> adjustMinusMap = mapMinus.get(sku);
            if (adjustMinusMap != null) {
                // VMI良品商品出库调整
                GodivaInventoryAdjustmentLine aLineMinus = adjustMinusMap.get(isForSaleTrue);
                handleVMIMinuInvMsg(aLineMinus, general, storerIdentifier, share, share2, share3, mapQty, mapQty2, mapQty3, adjust, adjust2, adjust3, ajustMinusList);
                // VMI残次品商品出库调整
                GodivaInventoryAdjustmentLine linedetail = adjustMinusMap.get(isForSaleFlase);
                handleVMIMinuInvMsg(linedetail, general, storerIdentifier, share, share2, share3, mapQty, mapQty2, mapQty3, adjust, adjust2, adjust3, ajustMinusList);
                godivaInventoryAdjustmentLineDao.flush();
            }
        }
        godivaInventoryAdjustmentDao.flush();

        return ajustMinusList;
    }

    /**
     * vmi调整出库信息
     * 
     * @param aLineMinus vmi调整出库商品库存信息
     * @param general
     * @param storerIdentifier
     * @param share 店铺1
     * @param share2 店铺2
     * @param share3 店铺3
     * @param mapQty 店铺1商品库存信息
     * @param mapQty2 店铺2商品库存信息
     * @param mapQty3 店铺3商品库存信息
     * @param adjust 店铺1信息
     * @param adjust2 店铺2信息
     * @param adjust3 店铺3信息
     * @param ajustMinusList
     */
    private void handleVMIMinuInvMsg(GodivaInventoryAdjustmentLine aLineMinus, cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.GeneralInformation general, String storerIdentifier, CompanyShopShare share,
            CompanyShopShare share2, CompanyShopShare share3, Map<String, Long> mapQty, Map<String, Long> mapQty2, Map<String, Long> mapQty3, GodivaInventoryAdjustment adjust, GodivaInventoryAdjustment adjust2, GodivaInventoryAdjustment adjust3,
            List<GodivaInventoryAdjustment> ajustMinusList) {

        if (aLineMinus != null) {
            String key = aLineMinus.getSku() + "_" + aLineMinus.getInvStatus().getId();
            if (mapQty.containsKey(key)) {
                // 判断商品是否存在于店铺1，存在处理店铺1中的商品信息出库
                Long total = 0l;
                Long rtnLong = Math.abs(aLineMinus.getAdjustmentQty());

                if (rtnLong > mapQty.get(key)) {
                    total = rtnLong - mapQty.get(key);
                    aLineMinus.setAdjustmentQty(-mapQty.get(key));
                    if (mapQty2.containsKey(key)) {
                        // 判断商品是否存在于店铺2，存在处理店铺2中的商品信息出库
                        if (adjust2 == null || adjust2.getId() == null) {
                            createAdjustment(general, storerIdentifier, share2, adjust2);
                            ajustMinusList.add(adjust2);
                        }

                        GodivaInventoryAdjustmentLine line2 = new GodivaInventoryAdjustmentLine();

                        line2.setAdjustmentKey(aLineMinus.getAdjustmentKey());
                        line2.setWarehouseCode(aLineMinus.getWarehouseCode());
                        line2.setLocationIdentifier(aLineMinus.getLocationIdentifier());
                        line2.setSku(aLineMinus.getSku());

                        line2.setEffectiveDate(aLineMinus.getEffectiveDate());

                        line2.setAvailableGood(aLineMinus.getAvailableGood());
                        line2.setAvailableDamaged(aLineMinus.getAvailableDamaged());

                        line2.setAvailableGoodBase(aLineMinus.getAvailableGoodBase());
                        line2.setAvailableDamagedBase(aLineMinus.getAvailableDamagedBase());

                        line2.setInvStatus(aLineMinus.getInvStatus());
                        if (total > mapQty2.get(key)) {
                            line2.setAdjustmentQty(-mapQty2.get(key));
                            if (mapQty3.containsKey(key)) {
                                // 判断商品是否存在于店铺3，存在处理店铺3中的商品信息出库
                                Long lastTotal = total - mapQty2.get(key);
                                if (adjust3 == null || adjust3.getId() == null) {
                                    createAdjustment(general, storerIdentifier, share3, adjust3);
                                    ajustMinusList.add(adjust3);
                                }

                                GodivaInventoryAdjustmentLine line3 = new GodivaInventoryAdjustmentLine();

                                line3.setAdjustmentKey(aLineMinus.getAdjustmentKey());
                                line3.setWarehouseCode(aLineMinus.getWarehouseCode());
                                line3.setLocationIdentifier(aLineMinus.getLocationIdentifier());
                                line3.setSku(aLineMinus.getSku());

                                line3.setEffectiveDate(aLineMinus.getEffectiveDate());

                                line3.setAvailableGood(aLineMinus.getAvailableGood());
                                line3.setAvailableDamaged(aLineMinus.getAvailableDamaged());

                                line3.setAvailableGoodBase(aLineMinus.getAvailableGoodBase());
                                line3.setAvailableDamagedBase(aLineMinus.getAvailableDamagedBase());

                                line3.setInvStatus(aLineMinus.getInvStatus());
                                if (lastTotal > mapQty3.get(key)) {
                                    line3.setAdjustmentQty(-mapQty3.get(key));
                                } else {
                                    line3.setAdjustmentQty(-lastTotal);
                                }

                                line3.setGodivaInventoryAdjustment(adjust3);
                                godivaInventoryAdjustmentLineDao.save(line3);
                            }
                        } else {
                            line2.setAdjustmentQty(-total);
                        }

                        line2.setGodivaInventoryAdjustment(adjust2);
                        godivaInventoryAdjustmentLineDao.save(line2);
                    } else if (mapQty3.containsKey(key)) {
                        // 判断商品是否存在于店铺3，存在处理店铺3中的商品信息出库
                        if (adjust3 == null || adjust3.getId() == null) {
                            createAdjustment(general, storerIdentifier, share3, adjust3);
                            ajustMinusList.add(adjust3);
                        }

                        GodivaInventoryAdjustmentLine line3 = new GodivaInventoryAdjustmentLine();

                        line3.setAdjustmentKey(aLineMinus.getAdjustmentKey());
                        line3.setWarehouseCode(aLineMinus.getWarehouseCode());
                        line3.setLocationIdentifier(aLineMinus.getLocationIdentifier());
                        line3.setSku(aLineMinus.getSku());

                        line3.setEffectiveDate(aLineMinus.getEffectiveDate());

                        line3.setAvailableGood(aLineMinus.getAvailableGood());
                        line3.setAvailableDamaged(aLineMinus.getAvailableDamaged());

                        line3.setAvailableGoodBase(aLineMinus.getAvailableGoodBase());
                        line3.setAvailableDamagedBase(aLineMinus.getAvailableDamagedBase());

                        line3.setInvStatus(aLineMinus.getInvStatus());
                        if (total > mapQty3.get(key)) {
                            line3.setAdjustmentQty(-mapQty3.get(key));
                        } else {
                            line3.setAdjustmentQty(-total);
                        }

                        line3.setGodivaInventoryAdjustment(adjust3);
                        godivaInventoryAdjustmentLineDao.save(line3);
                    }

                }
                aLineMinus.setGodivaInventoryAdjustment(adjust);
                godivaInventoryAdjustmentLineDao.save(aLineMinus);
            } else if (mapQty2.containsKey(key)) {
                // 判断商品是否存在于店铺2，存在处理店铺2中的商品信息出库
                if (adjust2 == null || adjust2.getId() == null) {
                    createAdjustment(general, storerIdentifier, share2, adjust2);
                    ajustMinusList.add(adjust2);
                }
                GodivaInventoryAdjustmentLine line2 = new GodivaInventoryAdjustmentLine();
                line2.setAdjustmentKey(aLineMinus.getAdjustmentKey());
                line2.setWarehouseCode(aLineMinus.getWarehouseCode());
                line2.setLocationIdentifier(aLineMinus.getLocationIdentifier());
                line2.setSku(aLineMinus.getSku());

                line2.setEffectiveDate(aLineMinus.getEffectiveDate());

                line2.setAvailableGood(aLineMinus.getAvailableGood());
                line2.setAvailableDamaged(aLineMinus.getAvailableDamaged());

                line2.setAvailableGoodBase(aLineMinus.getAvailableGoodBase());
                line2.setAvailableDamagedBase(aLineMinus.getAvailableDamagedBase());

                line2.setInvStatus(aLineMinus.getInvStatus());
                if (aLineMinus.getAdjustmentQty() > mapQty2.get(key)) {
                    line2.setAdjustmentQty(-mapQty2.get(key));

                    if (mapQty3.containsKey(key)) {
                        // 判断商品是否存在于店铺3，存在处理店铺3中的商品信息出库
                        Long lastTotal = aLineMinus.getAdjustmentQty() - mapQty2.get(key);
                        if (adjust3 == null || adjust3.getId() == null) {
                            createAdjustment(general, storerIdentifier, share3, adjust3);
                            ajustMinusList.add(adjust3);
                        }

                        GodivaInventoryAdjustmentLine line3 = new GodivaInventoryAdjustmentLine();

                        line3.setAdjustmentKey(aLineMinus.getAdjustmentKey());
                        line3.setWarehouseCode(aLineMinus.getWarehouseCode());
                        line3.setLocationIdentifier(aLineMinus.getLocationIdentifier());
                        line3.setSku(aLineMinus.getSku());

                        line3.setEffectiveDate(aLineMinus.getEffectiveDate());

                        line3.setAvailableGood(aLineMinus.getAvailableGood());
                        line3.setAvailableDamaged(aLineMinus.getAvailableDamaged());

                        line3.setAvailableGoodBase(aLineMinus.getAvailableGoodBase());
                        line3.setAvailableDamagedBase(aLineMinus.getAvailableDamagedBase());

                        line3.setInvStatus(aLineMinus.getInvStatus());
                        if (lastTotal > mapQty3.get(key)) {
                            line3.setAdjustmentQty(-mapQty3.get(key));
                        } else {
                            line3.setAdjustmentQty(-lastTotal);
                        }

                        line3.setGodivaInventoryAdjustment(adjust3);
                        godivaInventoryAdjustmentLineDao.save(line3);
                    }

                } else {
                    line2.setAdjustmentQty(-aLineMinus.getAdjustmentQty());
                }
                line2.setGodivaInventoryAdjustment(adjust2);
                godivaInventoryAdjustmentLineDao.save(line2);

            } else if (mapQty3.containsKey(key)) {
                // 判断商品是否存在于店铺3，存在处理店铺3中的商品信息出库
                if (adjust3 == null || adjust3.getId() == null) {
                    createAdjustment(general, storerIdentifier, share3, adjust3);
                    ajustMinusList.add(adjust3);
                }
                GodivaInventoryAdjustmentLine line3 = new GodivaInventoryAdjustmentLine();
                line3.setAdjustmentKey(aLineMinus.getAdjustmentKey());
                line3.setWarehouseCode(aLineMinus.getWarehouseCode());
                line3.setLocationIdentifier(aLineMinus.getLocationIdentifier());
                line3.setSku(aLineMinus.getSku());

                line3.setEffectiveDate(aLineMinus.getEffectiveDate());

                line3.setAvailableGood(aLineMinus.getAvailableGood());
                line3.setAvailableDamaged(aLineMinus.getAvailableDamaged());

                line3.setAvailableGoodBase(aLineMinus.getAvailableGoodBase());
                line3.setAvailableDamagedBase(aLineMinus.getAvailableDamagedBase());

                line3.setInvStatus(aLineMinus.getInvStatus());
                if (aLineMinus.getAdjustmentQty() > mapQty3.get(key)) {
                    line3.setAdjustmentQty(-mapQty3.get(key));
                } else {
                    line3.setAdjustmentQty(-aLineMinus.getAdjustmentQty());
                }
                line3.setGodivaInventoryAdjustment(adjust3);
                godivaInventoryAdjustmentLineDao.save(line3);
            }

        }

    }

    public void createAdjustment(cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.GeneralInformation general, String storerIdentifier, CompanyShopShare share2, GodivaInventoryAdjustment adjust2) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        adjust2.setAdjustmentKey(general.getAdjustmentKey());
        adjust2.setSource(storerIdentifier);
        adjust2.setStatus(DefaultStatus.CREATED);
        adjust2.setType("out");
        try {
            adjust2.setReportDate(dateFormat.parse(general.getReportDate()));
            adjust2.setConfirmDate(dateFormat.parse(general.getConfirmDate()));
        } catch (ParseException e) {
            log.error("", e);
        }
        adjust2.setRemarks(general.getRemarks());
        adjust2.setCreateBy(general.getCreateBy());
        adjust2.setCreateDate(new Date());
        adjust2.setShopId(share2.getShop().getId());
        adjust2 = godivaInventoryAdjustmentDao.save(adjust2);
    }

    public List<GodivaInventoryAdjustment> creaAjustAdd(cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.GeneralInformation general, String storerIdentifier,
            Map<String, Map<InventoryStatus, GodivaInventoryAdjustmentLine>> mapAdd, InventoryStatus isForSaleTrue, InventoryStatus isForSaleFlase) {
        List<CompanyShopShare> shares = companyShopShareDao.findShopShares("GDV", new BeanPropertyRowMapperExt<CompanyShopShare>(CompanyShopShare.class));
        // 库存调整入
        List<GodivaInventoryAdjustment> adjustmentMap = new ArrayList<GodivaInventoryAdjustment>();
        CompanyShopShare share = shares.get(0);
        GodivaInventoryAdjustment adjust = createAdjustmentIn(general, storerIdentifier, share);
        adjustmentMap.add(adjust);

        CompanyShopShare share2 = shares.get(1);
        GodivaInventoryAdjustment adjust2 = createAdjustmentIn(general, storerIdentifier, share2);
        adjustmentMap.add(adjust2);
        CompanyShopShare share3 = shares.get(2);
        GodivaInventoryAdjustment adjust3 = createAdjustmentIn(general, storerIdentifier, share3);
        adjustmentMap.add(adjust3);
        log.debug("========= CREATE GODIVAINVENTORYADJUSTMENT ===========");

        for (String sku : mapAdd.keySet()) {
            Map<InventoryStatus, GodivaInventoryAdjustmentLine> adjustMinusMap = mapAdd.get(sku);
            if (adjustMinusMap != null) {
                // 良品商品库存入库处理
                GodivaInventoryAdjustmentLine aLineMinus = adjustMinusMap.get(isForSaleTrue);

                handleVMIAddInvMsg(aLineMinus, share, share2, share3, adjust, adjust2, adjust3);
                // 残次品商品库存入库处理
                GodivaInventoryAdjustmentLine linedetail = adjustMinusMap.get(isForSaleFlase);
                handleVMIAddInvMsg(linedetail, share, share2, share3, adjust, adjust2, adjust3);
                godivaInventoryAdjustmentLineDao.flush();
            }
        }
        godivaInventoryAdjustmentDao.flush();
        return adjustmentMap;
    }

    /**
     * vmi商品入库调整
     * 
     * @param aLineMinus vmi商品库存信息
     * @param share 店铺1
     * @param share2 店铺2
     * @param share3 店铺3
     * @param adjust 店铺1库存调整信息
     * @param adjust2 店铺2库存调整信息
     * @param adjust3 店铺3库存调整信息
     */
    private void handleVMIAddInvMsg(GodivaInventoryAdjustmentLine aLineMinus, CompanyShopShare share, CompanyShopShare share2, CompanyShopShare share3, GodivaInventoryAdjustment adjust, GodivaInventoryAdjustment adjust2, GodivaInventoryAdjustment adjust3) {
        if (aLineMinus != null) {
            Long totalQty = aLineMinus.getAdjustmentQty();
            aLineMinus.setAdjustmentQty(totalQty * share.getInboundRatio() / 100);
            aLineMinus.setGodivaInventoryAdjustment(adjust);
            godivaInventoryAdjustmentLineDao.save(aLineMinus);
            GodivaInventoryAdjustmentLine line2 = new GodivaInventoryAdjustmentLine();

            line2.setAdjustmentKey(aLineMinus.getAdjustmentKey());
            line2.setWarehouseCode(aLineMinus.getWarehouseCode());
            line2.setLocationIdentifier(aLineMinus.getLocationIdentifier());
            line2.setSku(aLineMinus.getSku());

            line2.setEffectiveDate(aLineMinus.getEffectiveDate());

            line2.setAvailableGood(aLineMinus.getAvailableGood());
            line2.setAvailableDamaged(aLineMinus.getAvailableDamaged());

            line2.setAvailableGoodBase(aLineMinus.getAvailableGoodBase());
            line2.setAvailableDamagedBase(aLineMinus.getAvailableDamagedBase());
            line2.setInvStatus(aLineMinus.getInvStatus());

            line2.setAdjustmentQty(totalQty * share2.getInboundRatio() / 100);
            line2.setGodivaInventoryAdjustment(adjust2);
            godivaInventoryAdjustmentLineDao.save(line2);

            GodivaInventoryAdjustmentLine line3 = new GodivaInventoryAdjustmentLine();

            line3.setAdjustmentKey(aLineMinus.getAdjustmentKey());
            line3.setWarehouseCode(aLineMinus.getWarehouseCode());
            line3.setLocationIdentifier(aLineMinus.getLocationIdentifier());
            line3.setSku(aLineMinus.getSku());

            line3.setEffectiveDate(aLineMinus.getEffectiveDate());

            line3.setAvailableGood(aLineMinus.getAvailableGood());
            line3.setAvailableDamaged(aLineMinus.getAvailableDamaged());

            line3.setAvailableGoodBase(aLineMinus.getAvailableGoodBase());
            line3.setAvailableDamagedBase(aLineMinus.getAvailableDamagedBase());
            line3.setInvStatus(aLineMinus.getInvStatus());

            line3.setAdjustmentQty(totalQty - totalQty * share.getInboundRatio() / 100 - totalQty * share2.getInboundRatio() / 100);
            line3.setGodivaInventoryAdjustment(adjust3);
            godivaInventoryAdjustmentLineDao.save(line3);
        }

    }

    /**
     * 创建库存调整入的信息
     * 
     * @param general
     * @param storerIdentifier
     * @param share2
     */
    public GodivaInventoryAdjustment createAdjustmentIn(cn.baozun.bh.connector.gdv.model.adjustment.Adjustment.AdjustmentInformation.AdjustmentHeader.GeneralInformation general, String storerIdentifier, CompanyShopShare share) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        GodivaInventoryAdjustment adjust = new GodivaInventoryAdjustment();
        adjust.setSource(storerIdentifier);
        adjust.setAdjustmentKey(general.getAdjustmentKey());
        adjust.setType("in");
        adjust.setStatus(DefaultStatus.CREATED);
        try {
            adjust.setReportDate(dateFormat.parse(general.getReportDate()));
            adjust.setConfirmDate(dateFormat.parse(general.getConfirmDate()));
        } catch (ParseException e) {
            log.error("", e);
        }
        adjust.setRemarks(general.getRemarks());
        adjust.setCreateBy(general.getCreateBy());
        adjust.setCreateDate(new Date());
        adjust.setShopId(share.getShop().getId());
        adjust = godivaInventoryAdjustmentDao.save(adjust);
        return adjust;
    }

    public void executeGdvInventoryAdjustment(Map<String, List<GodivaInventoryAdjustment>> adjustMap) throws Exception {
        List<CompanyShopShare> shares = companyShopShareDao.findShopShares("GDV", new BeanPropertyRowMapperExt<CompanyShopShare>(CompanyShopShare.class));
        for (CompanyShopShare share : shares) {
            List<GodivaInventoryAdjustment> ajustAddList = adjustMap.get("Add");
            if (ajustAddList != null) {
                for (GodivaInventoryAdjustment adjustment : ajustAddList) {
                    if (share.getShop().getId().equals(adjustment.getShopId())) {

                        BiChannel owner = companyShopDao.getByPrimaryKey(adjustment.getShopId());

                        if (owner == null) {
                            log.error("KERRY->GDV CompanyShop is null");
                            throw new BusinessException();
                        }

                        InventoryCheck check = inventoryCheckDao.finInventoryCheckBySlipCodeandShop(adjustment.getAdjustmentKey(), owner.getId());
                        if (check != null) {
                            if (InventoryCheckStatus.FINISHED.equals(check.getStatus())) {

                                godivaInventoryAdjustmentDao.updateStatusByInvId(InventoryCheckStatus.FINISHED.getValue(), adjustment.getId());
                                return;
                            }
                        }
                        proadjustment(adjustment);
                    }
                }
            }
            List<GodivaInventoryAdjustment> ajustMinusList = adjustMap.get("Minus");
            if (ajustMinusList != null) {
                for (GodivaInventoryAdjustment adjustment : ajustMinusList) {
                    if (share.getShop().getId().equals(adjustment.getShopId())) {

                        BiChannel owner = companyShopDao.getByPrimaryKey(adjustment.getShopId());

                        if (owner == null) {
                            log.error("KERRY->GDV CompanyShop is null");
                            throw new BusinessException();
                        }

                        InventoryCheck check = inventoryCheckDao.finInventoryCheckBySlipCodeandShop(adjustment.getAdjustmentKey(), owner.getId());
                        if (check != null) {
                            if (InventoryCheckStatus.FINISHED.equals(check.getStatus())) {

                                godivaInventoryAdjustmentDao.updateStatusByInvId(InventoryCheckStatus.FINISHED.getValue(), adjustment.getId());
                                return;
                            }
                        }
                        proadjustment(adjustment);
                    }
                }
            }

        }
    }

    public InventoryCheck creteInventoryChecks(GodivaInventoryAdjustment godival) {
        OperationUnit ou = null;
        Warehouse warehouse = warehouseDao.findWarehouseByVmiSource(godival.getSource());
        InventoryStatus isForSaleTrue = null;
        InventoryStatus isForSaleFlase = null;

        if (warehouse == null) {
            log.error("KERRY->GDV Adjustment error ! Warehouse is null");
            throw new BusinessException();
        }

        // List<CompanyShop> shopList =
        // companyShopDao.findShopListByWarehouse2(warehouse.getOu().getId(), new
        // BeanPropertyRowMapperExt<CompanyShop>(CompanyShop.class));
        // if (shopList == null || shopList.size() == 0) {
        // log.error("KERRY->GDV Adjustment error ! Shop is null");
        // throw new BusinessException();
        // }

        BiChannel owner = companyShopDao.getByPrimaryKey(godival.getShopId());

        if (owner == null) {
            log.error("KERRY->GDV CompanyShop is null");
            throw new BusinessException();
        }
        wareHouseManagerExe.validateBiChannelSupport(null, owner.getCode());
        // 根据组织查询库存状态
        if (isForSaleTrue == null && isForSaleFlase == null) {

            Long ouId = warehouse.getOu().getId();
            ou = ouDao.getByPrimaryKey(ouId);
            if (ou == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {godival.getSource()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            Long companyId = null;
            if (ou.getParentUnit() != null) {
                OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                if (ou1 != null) {
                    companyId = ou1.getParentUnit().getId();
                }
            }
            isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
            isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);
        }

        GodivaInventoryAdjustment adjustment = godivaInventoryAdjustmentDao.getByPrimaryKey(godival.getId());
        if (adjustment == null) {
            log.error("KERRY->GDV Adjustment is null");
            throw new BusinessException();
        }

        InventoryCheck inventoryCheck = new InventoryCheck();
        inventoryCheck.setCreateTime(new Date());
        inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
        inventoryCheck.setType(InventoryCheckType.VMI);
        inventoryCheck.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), inventoryCheck));

        // 设置组织
        inventoryCheck.setOu(warehouse.getOu());
        inventoryCheck.setShop(owner);
        inventoryCheck.setSlipCode(adjustment.getAdjustmentKey());// 相关单据号
        inventoryCheck.setRemork(adjustment.getRemarks());// 备注

        inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
        inventoryCheck = inventoryCheckDao.save(inventoryCheck);
        inventoryCheckDao.flush();
        List<GodivaInventoryAdjustmentLine> godLineList = godivaInventoryAdjustmentLineDao.findGodivaInventoryAdjustmentLineById(adjustment.getId());
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
        for (GodivaInventoryAdjustmentLine line : godLineList) {
            Sku sku = skuDao.getByBarcode(line.getSku(), customerId);
            if (sku == null) {
                log.error("KERRY->GDV sku is null");
                throw new BusinessException();
            }

            InventoryCheckDifTotalLine icDifTotalLine = new InventoryCheckDifTotalLine();
            icDifTotalLine.setSku(sku);
            icDifTotalLine.setQuantity(line.getAdjustmentQty());
            icDifTotalLine.setInventoryCheck(inventoryCheck);
            icDifTotalLine.setStatus(line.getInvStatus());
            vmiinvCheckLineDao.save(icDifTotalLine);

        }
        vmiinvCheckLineDao.flush();

        godivaInventoryAdjustmentDao.updateGodAdjustInvCode(inventoryCheck.getCode(), adjustment.getId());

        return inventoryCheck;
    }

    public void executionVmiAdjustment(InventoryCheck ic) {
        if (ic == null || !InventoryCheckStatus.CREATED.equals(ic.getStatus())) {
            log.error("IDS->WMS Error! Adjustment execution ,Status is error!");
            throw new BusinessException();
        }
        List<InventoryCheckDifTotalLine> lineList = vmiinvCheckLineDao.findByInventoryCheck(ic.getId());
        WarehouseLocation location = locDao.findOneWarehouseLocationByOuid(ic.getOu().getId());
        for (InventoryCheckDifTotalLine line : lineList) {
            InventoryCheckDifferenceLine icdifference = new InventoryCheckDifferenceLine();
            icdifference.setSku(line.getSku());
            icdifference.setQuantity(line.getQuantity());
            icdifference.setInventoryCheck(ic);
            icdifference.setLocation(location);
            icdifference.setStatus(line.getStatus());
            if (ic != null && ic.getShop() != null && ic.getShop().getId() != null) {
                BiChannel shop = ic.getShop();
                icdifference.setOwner(shop.getCode());
            }
            icDifferenceLineDao.save(icdifference);
        }
        ic.setStatus(InventoryCheckStatus.UNEXECUTE);
        inventoryCheckDao.save(ic);
        inventoryCheckDao.flush();
        // 执行
    }

    public void createStaFroGdvRtn(MsgRtnInboundOrder rtnInboundOrder) {

        Warehouse warehouse = warehouseDao.findWarehouseByVmiSource(rtnInboundOrder.getSource());
        if (warehouse == null) throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        OperationUnit operationUnit = ouDao.getByPrimaryKey(warehouse.getOu().getId());
        if (operationUnit == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_OU_NOT_FOUND);
        }
        BigDecimal transactionid = transactionTypeDao.findByStaType(StockTransApplicationType.VMI_RETURN.getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        if (transactionid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
        if (transactionType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
        }
        // 指令中所有的sku都不存在
        List<MsgRtnInboundOrderLine> lineList = msgRtnInboundOrderLineDao.findInboundOrderLines(rtnInboundOrder.getId(), new BeanPropertyRowMapperExt<MsgRtnInboundOrderLine>(MsgRtnInboundOrderLine.class));
        if (lineList == null || lineList.size() == 0) {
            log.error("The Linelist is null");
            return;
        }
        createGdvReturn(rtnInboundOrder.getId(), transactionType, operationUnit);
    }

    private void createGdvReturn(Long gdvDataId, TransactionType transType, OperationUnit ou) {
        MsgRtnInboundOrder rtnInboundOrder = msgRtnInboundOrderDao.getByPrimaryKey(gdvDataId);
        BiChannel shop = shopDao.getByPrimaryKey(rtnInboundOrder.getShopId());
        if (shop == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {rtnInboundOrder.getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        String innerShopCode = shop.getCode();
        // 根据MoveKey判断指令是否已经创建
        StockTransApplication stockTransApplication = staDao.findStaByslipCodeandShopId(rtnInboundOrder.getRefSlipCode(), innerShopCode);
        if (stockTransApplication != null) {
            if (stockTransApplication.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                msgRtnInboundOrderDao.updateOrderStauts(rtnInboundOrder.getId(), DefaultStatus.FINISHED.getValue());
                log.debug("The list is complete");
                return;
            }
        }
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_RETURN);
        sta.setMainWarehouse(ou);
        sta.setRefSlipCode(rtnInboundOrder.getRefSlipCode());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        sta.setIsNeedOccupied(true);
        sta.setOwner(innerShopCode);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setIsNotPacsomsOrder(true);
        staDao.save(sta);
        staDao.flush();
        List<MsgRtnInboundOrderLine> gdvRtnDataLines = msgRtnInboundOrderLineDao.findRtnOrderLineByRId(gdvDataId);
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());

        for (MsgRtnInboundOrderLine gdvRtnDataLine : gdvRtnDataLines) {
            StaLine staLine = new StaLine();
            staLine.setQuantity(gdvRtnDataLine.getQty());
            staLine.setCompleteQuantity(0L);
            staLine.setSta(sta);
            staLine.setInvStatus(gdvRtnDataLine.getInvStatus());
            if (("").equals(gdvRtnDataLine.getBarcode())) {
                log.debug("===========================sku is  null sku :{} ===============================", gdvRtnDataLine.getSkuCode());
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            Sku sku = skuDao.getByBarcode(gdvRtnDataLine.getBarcode(), customerId);
            if (sku == null) {
                continue;
            }
            staLine.setSku(sku);
            staLineDao.save(staLine);
        }
        staLineDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        msgRtnInboundOrderDao.updateStaCodeToMsg(sta.getCode(), rtnInboundOrder.getId());

        // 占用库存
        StockTransVoucher stv = occupyInventoryByStaId(sta.getId(), transType, ou);
        // 新增其他出库占用明细记录中间表通知oms/pac,定时任务通知
        wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);

        wareHouseManagerExe.validateBiChannelSupport(stv, null);
        // 删除库存出库
        wareHouseManager.removeInventory(sta, stv);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        // 其他出库更新中间表，传递明细给oms/pac
        WmsOtherOutBoundInvNoticeOms wtoms = wmsOtherOutBoundInvNoticeOmsDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
        if (wtoms != null) {
            wmsOtherOutBoundInvNoticeOmsDao.updateOtherOutBoundInvNoticeOmsByStaCode(sta.getCode(), 10l);
        }
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), null, sta.getMainWarehouse().getId());
        staLineDao.flush();

    }

    // vmi 退仓- vmi(etam / ids )占用库存
    public StockTransVoucher occupyInventoryByStaId(Long staId, TransactionType transType, OperationUnit ou) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 库存占用
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("in_sta_id", staId);
        SqlOutParameter s = new SqlOutParameter("error_sku_id", Types.VARCHAR);
        SqlParameter[] sqlParameters = {new SqlParameter("in_sta_id", Types.NUMERIC), s};
        Map<String, Object> result = null;
        result = staDao.executeSp("sp_occupy_inv_for_rtn_no_loc", sqlParameters, params);
        String errorSku = (String) result.get("error_sku_id");
        BusinessException root = null;
        if (StringUtils.hasText(errorSku)) {
            String[] skus = errorSku.split(",");
            for (String str : skus) {
                String[] strs = str.split(Constants.STA_SKUS_SLIPT_STR);
                Long skuId = Long.parseLong(strs[0]);
                Long qty = Long.parseLong(strs[1]);
                if (root == null) {
                    root = new BusinessException(ErrorCode.OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY);
                }
                BusinessException current = root;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                }
                Sku sku = skuDao.getByPrimaryKey(skuId);
                // System.out.println(sku.getBarCode() + "--" + qty);
                BusinessException be = new BusinessException(ErrorCode.SKU_NO_INVENTORY_QTY, new Object[] {sku.getName(), sku.getCode(), sku.getBarCode(), qty});
                current.setLinkedException(be);
            }
            throw root;
        } else {
            // 创建 STV ,STV LINE
            // save stv
            String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
            StockTransVoucher stvCreate = new StockTransVoucher();
            stvCreate.setCode(code);
            stvCreate.setSta(sta);
            stvCreate.setLastModifyTime(new Date());
            stvCreate.setStatus(StockTransVoucherStatus.CREATED);
            stvCreate.setTransactionType(transType);
            stvCreate.setDirection(TransactionDirection.OUTBOUND);
            stvCreate.setWarehouse(ou);
            stvDao.save(stvCreate);
            // find stave
            stvDao.flush();
            stvLineDao.createByStaId(staId);
            // 更新sta状态为库存占用
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta.getMainWarehouse().getId());
            staDao.save(sta);
            stvDao.flush();
            // IM
            hubWmsService.insertOccupiedAndRelease(sta.getId());
            return stvCreate;
        }
    }

    public Map<String, Long> findInventoriesByShop(String innerShopCode, Long ouId) {

        List<Inventory> list = inventoryDao.finInventoriesByinvOwner(innerShopCode, ouId);
        Map<String, Long> mapQty = new HashMap<String, Long>();
        for (Inventory sl : list) {
            if (sl.getSku() != null && sl.getStatus() != null) {
                String key = sl.getSku().getBarCode() + "_" + sl.getStatus().getId();
                if (mapQty.containsKey(key)) {
                    mapQty.put(key, mapQty.get(key) + sl.getQuantity());
                } else {
                    mapQty.put(key, sl.getQuantity());
                }
            }
        }
        return mapQty;
    }

    /**
     * 执行库存调整
     * 
     * @param ajust
     */
    public void proadjustment(GodivaInventoryAdjustment ajust) throws Exception {
        InventoryCheck ic = null;

        // 创建库存调整
        ic = creteInventoryChecks(ajust);
        // 执行库存调整
        executionVmiAdjustment(ic);
        godivaInventoryAdjustmentDao.updateStatusByInvId(DefaultStatus.FINISHED.getValue(), ajust.getId());

        if (ic != null) {

            wareHouseManager.confirmVMIInvCKAdjustment(ic);

        }
    }

    /**
     * gdv 执行移动入库/退大仓
     * 
     * @param msgRtnorder
     */
    public void executeGdvMovement(List<MsgRtnInboundOrder> msgRtnorderList) {
        for (MsgRtnInboundOrder msgRtnorder : msgRtnorderList) {
            if (null != msgRtnorder && StringUtils.hasText(msgRtnorder.getRefSlipCode())) {
                if (("Add").equals(msgRtnorder.getRemark())) {
                    createStaByInboundOrder(msgRtnorder);
                    msgRtnorder = msgRtnInboundOrderDao.getByPrimaryKey(msgRtnorder.getId());
                    if (msgRtnorder != null && StringUtils.hasText(msgRtnorder.getStaCode())) {
                        // updateInOrderSkuIdByBarCode(msgRtnorder.getId());
                        try {
                            wareHouseManagerProxy.msgInBoundWareHouse(msgRtnorder);
                        } catch (Exception e) {
                            if (e instanceof BusinessException) {
                                log.error("executeGdvMovement error ,error code is : {}", ((BusinessException) e).getErrorCode());
                            } else {
                                log.error("", e);
                            }
                        }
                    }

                } else if (("Minus").equals(msgRtnorder.getRemark())) {
                    createStaFroGdvRtn(msgRtnorder);
                    msgRtnInboundOrderDao.updateOrderStauts(msgRtnorder.getId(), DefaultStatus.FINISHED.getValue());
                }

            }
        }
    }

    public void executeSaveCarrier() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        try {
            startDate = dateFormat.parse("2013-01-28");

            Date endDate = dateFormat.parse("2013-02-01");

            List<MsgRtnOutbound> rtnOutbounds = msgRtnDao.findGdvFailInfo("kerryeas", startDate, endDate, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));

            for (MsgRtnOutbound outbound : rtnOutbounds) {
                CarrierConsignmentData carrierDate = new CarrierConsignmentData();
                carrierDate.setOrderNo(outbound.getSlipCode());
                carrierDate.setCarrierCode(outbound.getLpCode());
                carrierDate.setCarrierNumber(outbound.getTrackingNo());

                carrierDate.setReleaseDate(outbound.getOutboundTime());
                carrierDate.setStatus(0);
                carrierDate.setCreateDate(new Date());
                carrierConsignmentDataDao.save(carrierDate);
                msgRtnDao.updateStatusByID(outbound.getId(), DefaultStatus.INEXECUTION.getValue());
            }

        } catch (ParseException e) {
            log.error("", e);
        }
    }

    public StockTransApplication createOutSalesOrder(Long rtnOutOrderId) {
        MsgRtnOutbound rtnmsg = msgRtnDao.getByPrimaryKey(rtnOutOrderId);
        StockTransApplication sta = null;
        if (rtnmsg != null) {
            log.debug("-------------Godiva create STA-----------------start-------");
            String innerShopCode = null;
            Warehouse wh = wareHouseManager.getWareHouseByVmiSource(rtnmsg.getSource());
            if (wh == null) {
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            BiChannel shop = null;
            // 根据单号判断单据属于哪个店铺
            String shopType = "";
            String salesCode = rtnmsg.getSlipCode();
            if (salesCode != null && salesCode.length() > 0) {
                shopType = salesCode.substring(0, 2);
                if (("TB").equals(shopType)) {} else if (("GD").equals(shopType) || ("G").equals(shopType)) {
                    shop = shopDao.getByVmiCode(BiChannel.CHANNEL_VMICODE_GDV_STORE);
                }
            }

            if (shop == null) {
                log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
            innerShopCode = shop.getCode();
            sta = new StockTransApplication();
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            // sta.setCreator(user);
            sta.setOwner(innerShopCode);
            sta.setIsNeedOccupied(true);
            sta.setMainWarehouse(wh.getOu());
            sta.setRefSlipCode(rtnmsg.getSlipCode());
            sta.setRefSlipType(SlipType.SALES_ORDER);
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, wh.getOu().getId());
            sta.setType(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES);
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            StaDeliveryInfo sd = new StaDeliveryInfo();

            sd.setLpCode(rtnmsg.getLpCode());
            if (rtnmsg.getTrackingNo() != null && !rtnmsg.getTrackingNo().equals("")) {
                sd.setTrackingNo(rtnmsg.getTrackingNo());
            } else {
                sd.setTrackingNo(rtnmsg.getSlipCode());
            }
            sd.setSta(sta);
            sta.setStaDeliveryInfo(sd);
            sta = staDao.save(sta);
            sta.getStaDeliveryInfo().setId(sta.getId());
            sd = staDeliveryInfodao.save(sd);
            sta.setStaDeliveryInfo(sd);
            staDao.flush();
            List<MsgRtnOutboundLine> lines = msgRtnOutboundLineDao.findMsgRtnOutboundLines(rtnmsg.getId());
            Long customerId = wareHouseManagerQuery.getCustomerByWhouid(wh.getOu().getId());
            for (MsgRtnOutboundLine line : lines) {
                Sku sku = skuDao.getByBarcode(line.getBarCode(), customerId);
                if (sku == null) {
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {line.getBarCode()});
                }
                StaLine staLine = new StaLine();
                staLine.setQuantity(line.getQty());
                staLine.setSku(sku);
                staLine.setSta(sta);
                staLine.setOwner(innerShopCode);
                staLineDao.save(staLine);
            }
            staLineDao.flush();
            msgRtnDao.updateMsgRtnOutBoundStaCode(rtnOutOrderId, sta.getCode());
        }

        return sta;
    }

    public Map<String, String> warehouseMap() {
        Map<String, String> mapwh = new HashMap<String, String>();
        mapwh.put("SH", Constants.VIM_WH_SOURCE_KERRYEAS_SH);
        mapwh.put("BJ", Constants.VIM_WH_SOURCE_KERRYEAS_BJ);
        return mapwh;
    }

    public List<VmiInventorySnapshotData> receiveGodivaInventory(String message) {

        ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
        List<VmiInventorySnapshotData> snapshotDataList = new ArrayList<VmiInventorySnapshotData>();
        try {
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            Inventorys inventorygdv = (Inventorys) JSONUtil.jsonToBean(messageContent, Inventorys.class);
            String fileName = inventorygdv.getSourceFileName();
            String source = inventorygdv.getSource();

            InventoryStatus isForSaleTrue = null;
            Warehouse wh = null;
            OperationUnit ou = null;

            for (cn.baozun.bh.connector.gdv.model.inventory.Inventorys.Inventory inventory : inventorygdv.getInventorys()) {
                String vmiSouce = "";
                if (StringUtils.hasText(inventory.getWarehouse())) {
                    if (("SH").equals(inventory.getWarehouse())) {
                        vmiSouce = Constants.VIM_WH_SOURCE_KERRYEAS_SH;
                    } else if (("BJ").equals(inventory.getWarehouse())) {
                        vmiSouce = Constants.VIM_WH_SOURCE_KERRYEAS_BJ;
                    }
                }

                InventoryStatus is = null;

                if (wh == null) {
                    wh = warehouseDao.getBySource(vmiSouce, vmiSouce);
                    if (wh == null) {
                        log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {source});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    } else {
                        Long ouId = wh.getOu().getId();
                        ou = ouDao.getByPrimaryKey(ouId);
                        if (ou == null) {
                            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {source});
                            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                        }
                    }
                }

                if (is == null) {
                    Long companyId = null;
                    if (ou.getParentUnit() != null) {
                        OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                        if (ou1 != null) {
                            companyId = ou1.getParentUnit().getId();
                        }
                    }
                    isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
                    // isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);

                }

                VmiInventorySnapshotData data = new VmiInventorySnapshotData();
                data.setCreateData(new Date());
                data.setSource(this.warehouseMap().get(inventory.getWarehouse()));
                data.setUpc(inventory.getSku());
                data.setOnhandQty(inventory.getOnhandQty().longValue());
                data.setVmiStatus(DefaultStatus.CREATED);
                data.setFileName(fileName);
                data.setSource("GODIVA");
                data.setWarehouse(vmiSouce);
                data.setInventoryStatus(isForSaleTrue);
                vmiInventorySnapshotDataDao.save(data);
                snapshotDataList.add(data);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return snapshotDataList;
    }

    public InventoryCheck gdvCreateInventoryCheck(Warehouse wh, BiChannel shop) {
        InventoryCheck inventoryCheck = new InventoryCheck();
        inventoryCheck.setCreateTime(new Date());
        inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
        inventoryCheck.setType(InventoryCheckType.VMI);
        inventoryCheck.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), inventoryCheck));
        inventoryCheck.setOu(wh.getOu());
        inventoryCheck.setShop(shop);
        inventoryCheck = inventoryCheckDao.save(inventoryCheck);
        return inventoryCheck;
    }


    /**
     * 全量库存
     * 
     * godiva 分配库存是按照京东：官网：淘宝 1:4:5来分配的，多余的都分配给淘宝 比例是配置在表T_MA_SHOP_SHARE 中 方法命名是按照T_MA_SHOP_SHARE
     * sort顺序来的，因为是倒序查找该表数据的，因此目前命名实际为： shop1为官网，shop2为京东，shop3为淘宝
     * 
     * @param datas
     */
    public List<InventoryCheck> executeInventoryChecks(List<VmiInventorySnapshotDataCommand> datas, List<CompanyShopShare> sharesList, Warehouse wh) {


        log.debug("===============DGV InventoryCheck {} create start ================");
        OperationUnit ou = null;
        BiChannel shop2 = null;
        BiChannel shop1 = null;
        BiChannel shop3 = null;
        List<InventoryCheck> inventoryCheckList = new ArrayList<InventoryCheck>();
        CompanyShopShare share2 = sharesList.get(1);
        shop2 = shopDao.getByPrimaryKey(share2.getShop().getId());
        if (shop2 == null) {
            log.debug("========= {Godiva } COMPANYSHOPSHARE NOT FOUNT SHOP===========executeInventoryChecks");
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        List<Inventory> list2 = inventoryDao.finInventoriesByinvOwner(shop2.getCode(), wh.getOu().getId());
        Map<String, Long> mapQty2 = new HashMap<String, Long>();
        for (Inventory sl : list2) {
            if (sl.getSku() != null && sl.getStatus() != null) {
                String key = sl.getSku().getBarCode() + "_" + sl.getStatus().getId();
                if (mapQty2.containsKey(key)) {
                    mapQty2.put(key, mapQty2.get(key) + sl.getQuantity());
                } else {
                    mapQty2.put(key, sl.getQuantity());
                }
            }
        }

        InventoryCheck inventoryCheck1 = null;
        InventoryCheck inventoryCheck2 = null;
        InventoryCheck inventoryCheck3 = null;
        CompanyShopShare share1 = sharesList.get(0);
        shop1 = shopDao.getByPrimaryKey(share1.getShop().getId());
        if (shop1 == null) {
            log.debug("========= {Godiva } COMPANYSHOPSHARE NOT FOUNT SHOP===========executeInventoryChecks");
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop1.getCode());
        List<Inventory> list1 = inventoryDao.finInventoriesByinvOwner(shop1.getCode(), wh.getOu().getId());
        Map<String, Long> mapQty1 = new HashMap<String, Long>();
        for (Inventory sl : list1) {
            if (sl.getSku() != null && sl.getStatus() != null) {
                String key = sl.getSku().getBarCode() + "_" + sl.getStatus().getId();
                if (mapQty1.containsKey(key)) {
                    mapQty1.put(key, mapQty1.get(key) + sl.getQuantity());
                } else {
                    mapQty1.put(key, sl.getQuantity());
                }
            }
        }

        CompanyShopShare share3 = sharesList.get(2);
        shop3 = shopDao.getByPrimaryKey(share3.getShop().getId());
        if (shop3 == null) {
            log.debug("========= {Godiva } COMPANYSHOPSHARE NOT FOUNT SHOP===========executeInventoryChecks");
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        List<Inventory> list3 = inventoryDao.finInventoriesByinvOwner(shop3.getCode(), wh.getOu().getId());
        Map<String, Long> mapQty3 = new HashMap<String, Long>();
        for (Inventory sl : list3) {
            if (sl.getSku() != null && sl.getStatus() != null) {
                String key = sl.getSku().getBarCode() + "_" + sl.getStatus().getId();
                if (mapQty3.containsKey(key)) {
                    mapQty3.put(key, mapQty3.get(key) + sl.getQuantity());
                } else {
                    mapQty3.put(key, sl.getQuantity());
                }
            }
        }

        Long ouId = wh.getOu().getId();
        ou = ouDao.getByPrimaryKey(ouId);
        if (ou == null) {
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
        for (VmiInventorySnapshotDataCommand datainfo : datas) {
            try {
                if (!StringUtils.hasText(datainfo.getUpc())) {
                    log.debug("===============GDV create InventoryCheck error:upc is null================");
                    continue;
                }
                // 判断skuCode在系统中是否存在
                Sku sku = skuDao.getByBarcode(datainfo.getUpc(), customerId);
                if (sku == null) {
                    log.debug("===============SKU {} IS NULL ================", new Object[] {datainfo.getUpc()});
                    continue;
                }
                InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(datainfo.getInventoryStatusId());

                if (inventoryStatus == null) {
                    log.debug("===============InventoryStatus {} NOT FOND ================", new Object[] {datainfo.getUpc()});
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {datainfo.getUpc()});
                }
                if (datainfo.getOnhandQty() > 0) {
                    Long onhand = datainfo.getOnhandQty();

                    if (inventoryCheck1 == null) {
                        inventoryCheck1 = this.gdvCreateInventoryCheck(wh, shop1);
                        inventoryCheckList.add(inventoryCheck1);
                    }



                    InventoryCheckDifTotalLine icDifTotalLinegw = new InventoryCheckDifTotalLine();
                    icDifTotalLinegw.setSku(sku);
                    icDifTotalLinegw.setQuantity(onhand * share1.getInboundRatio() / 100);
                    icDifTotalLinegw.setInventoryCheck(inventoryCheck1);
                    icDifTotalLinegw.setStatus(inventoryStatus);
                    vmiinvCheckLineDao.save(icDifTotalLinegw);


                    if (inventoryCheck2 == null) {
                        inventoryCheck2 = this.gdvCreateInventoryCheck(wh, shop2);
                        inventoryCheckList.add(inventoryCheck2);
                    }

                    InventoryCheckDifTotalLine icDifTotalLinetb = new InventoryCheckDifTotalLine();
                    icDifTotalLinetb.setSku(sku);
                    icDifTotalLinetb.setQuantity(onhand * share2.getInboundRatio() / 100);
                    icDifTotalLinetb.setInventoryCheck(inventoryCheck2);
                    icDifTotalLinetb.setStatus(inventoryStatus);
                    vmiinvCheckLineDao.save(icDifTotalLinetb);

                    if (inventoryCheck3 == null) {
                        inventoryCheck3 = this.gdvCreateInventoryCheck(wh, shop3);
                        inventoryCheckList.add(inventoryCheck3);
                    }

                    InventoryCheckDifTotalLine icDifTotalLinejd = new InventoryCheckDifTotalLine();
                    icDifTotalLinejd.setSku(sku);
                    icDifTotalLinejd.setQuantity(onhand - onhand * share2.getInboundRatio() / 100 - onhand * share1.getInboundRatio() / 100);
                    icDifTotalLinejd.setInventoryCheck(inventoryCheck3);
                    icDifTotalLinejd.setStatus(inventoryStatus);
                    vmiinvCheckLineDao.save(icDifTotalLinejd);


                } else if (datainfo.getOnhandQty() < 0) {
                    // 查询出对应sku 的所有商品先减官网再减京东最后减淘宝
                    String key = datainfo.getUpc() + "_" + datainfo.getInventoryStatusId();
                    if (mapQty1.containsKey(key)) {

                        if (inventoryCheck1 == null) {
                            inventoryCheck1 = this.gdvCreateInventoryCheck(wh, shop1);
                            inventoryCheckList.add(inventoryCheck1);
                        }

                        if (Math.abs(datainfo.getOnhandQty()) > mapQty1.get(key)) {

                            Long rtnqtyLong = Math.abs(datainfo.getOnhandQty()) - mapQty1.get(key);

                            InventoryCheckDifTotalLine icDifTotalLine1 = new InventoryCheckDifTotalLine();
                            icDifTotalLine1.setSku(sku);
                            icDifTotalLine1.setInventoryCheck(inventoryCheck1);
                            icDifTotalLine1.setStatus(inventoryStatus);
                            icDifTotalLine1.setQuantity(-mapQty1.get(key));
                            vmiinvCheckLineDao.save(icDifTotalLine1);

                            if (mapQty2.containsKey(key)) {

                                if (inventoryCheck2 == null) {
                                    inventoryCheck2 = this.gdvCreateInventoryCheck(wh, shop2);
                                    inventoryCheckList.add(inventoryCheck2);
                                }
                                InventoryCheckDifTotalLine icDifTotalLine2 = new InventoryCheckDifTotalLine();
                                icDifTotalLine2.setSku(sku);
                                icDifTotalLine2.setInventoryCheck(inventoryCheck2);
                                icDifTotalLine2.setStatus(inventoryStatus);
                                // 判断第二个店铺要减去的库存数量
                                if (rtnqtyLong > mapQty2.get(key)) {
                                    icDifTotalLine2.setQuantity(-mapQty2.get(key));

                                    Long rtnLastLong = rtnqtyLong - mapQty2.get(key);
                                    if (mapQty3.containsKey(key)) {

                                        if (inventoryCheck3 == null) {
                                            inventoryCheck3 = this.gdvCreateInventoryCheck(wh, shop3);
                                            inventoryCheckList.add(inventoryCheck3);
                                        }
                                        InventoryCheckDifTotalLine icDifTotalLine3 = new InventoryCheckDifTotalLine();
                                        icDifTotalLine3.setSku(sku);
                                        icDifTotalLine3.setInventoryCheck(inventoryCheck3);
                                        icDifTotalLine3.setStatus(inventoryStatus);
                                        // 判断第三个店铺要减去的库存数量
                                        if (rtnLastLong > mapQty3.get(key)) {
                                            icDifTotalLine3.setQuantity(-mapQty3.get(key));
                                        } else {
                                            icDifTotalLine3.setQuantity(-rtnLastLong);
                                        }
                                        vmiinvCheckLineDao.save(icDifTotalLine3);

                                    }

                                } else {
                                    icDifTotalLine2.setQuantity(-rtnqtyLong);
                                }
                                vmiinvCheckLineDao.save(icDifTotalLine2);

                            } else if (mapQty3.containsKey(key)) {
                                if (inventoryCheck3 == null) {
                                    inventoryCheck3 = this.gdvCreateInventoryCheck(wh, shop3);
                                    inventoryCheckList.add(inventoryCheck3);
                                }
                                InventoryCheckDifTotalLine icDifTotalLine3 = new InventoryCheckDifTotalLine();
                                icDifTotalLine3.setSku(sku);
                                icDifTotalLine3.setInventoryCheck(inventoryCheck3);
                                icDifTotalLine3.setStatus(inventoryStatus);
                                // 判断第三个店铺要减去的库存数量
                                if (rtnqtyLong > mapQty3.get(key)) {
                                    icDifTotalLine3.setQuantity(-mapQty3.get(key));
                                } else {
                                    icDifTotalLine3.setQuantity(-rtnqtyLong);
                                }
                                vmiinvCheckLineDao.save(icDifTotalLine3);
                            }

                        } else {

                            InventoryCheckDifTotalLine icDifTotalLine1 = new InventoryCheckDifTotalLine();
                            icDifTotalLine1.setSku(sku);
                            icDifTotalLine1.setInventoryCheck(inventoryCheck1);
                            icDifTotalLine1.setStatus(inventoryStatus);
                            icDifTotalLine1.setQuantity(datainfo.getOnhandQty());
                            vmiinvCheckLineDao.save(icDifTotalLine1);

                        }
                    } else if (mapQty2.containsKey(key)) {

                        if (inventoryCheck2 == null) {
                            inventoryCheck2 = this.gdvCreateInventoryCheck(wh, shop2);
                            inventoryCheckList.add(inventoryCheck2);
                        }
                        InventoryCheckDifTotalLine icDifTotalLine2 = new InventoryCheckDifTotalLine();
                        icDifTotalLine2.setSku(sku);
                        icDifTotalLine2.setInventoryCheck(inventoryCheck2);
                        icDifTotalLine2.setStatus(inventoryStatus);

                        // 判断第二个店铺要减去的库存数量
                        if (Math.abs(datainfo.getOnhandQty()) > mapQty2.get(key)) {
                            icDifTotalLine2.setQuantity(-mapQty2.get(key));
                            Long rtnLastLong = Math.abs(datainfo.getOnhandQty()) - mapQty2.get(key);
                            if (mapQty3.containsKey(key)) {

                                if (inventoryCheck3 == null) {
                                    inventoryCheck3 = this.gdvCreateInventoryCheck(wh, shop3);
                                    inventoryCheckList.add(inventoryCheck3);
                                }
                                InventoryCheckDifTotalLine icDifTotalLine3 = new InventoryCheckDifTotalLine();
                                icDifTotalLine3.setSku(sku);
                                icDifTotalLine3.setInventoryCheck(inventoryCheck3);
                                icDifTotalLine3.setStatus(inventoryStatus);
                                // 判断第三个店铺要减去的库存数量
                                if (rtnLastLong > mapQty3.get(key)) {
                                    icDifTotalLine3.setQuantity(-mapQty3.get(key));
                                } else {
                                    icDifTotalLine3.setQuantity(-rtnLastLong);
                                }
                                vmiinvCheckLineDao.save(icDifTotalLine3);

                            }

                        } else {
                            icDifTotalLine2.setQuantity(datainfo.getOnhandQty());
                        }
                        vmiinvCheckLineDao.save(icDifTotalLine2);
                    } else if (mapQty3.containsKey(key)) {
                        if (inventoryCheck3 == null) {
                            inventoryCheck3 = this.gdvCreateInventoryCheck(wh, shop3);
                            inventoryCheckList.add(inventoryCheck3);
                        }
                        InventoryCheckDifTotalLine icDifTotalLine3 = new InventoryCheckDifTotalLine();
                        icDifTotalLine3.setSku(sku);
                        icDifTotalLine3.setInventoryCheck(inventoryCheck3);
                        icDifTotalLine3.setStatus(inventoryStatus);

                        // 判断第三个店铺要减去的库存数量
                        if (Math.abs(datainfo.getOnhandQty()) > mapQty3.get(key)) {
                            icDifTotalLine3.setQuantity(-mapQty3.get(key));
                        } else {
                            icDifTotalLine3.setQuantity(datainfo.getOnhandQty());
                        }
                        vmiinvCheckLineDao.save(icDifTotalLine3);
                    }

                }
                String invCodeString = "";
                if (inventoryCheck1 != null) {
                    invCodeString += inventoryCheck1.getCode();
                }
                if (inventoryCheck2 != null) {
                    invCodeString += inventoryCheck2.getCode();
                }
                if (inventoryCheck3 != null) {
                    invCodeString += inventoryCheck3.getCode();
                }

                vmiInventorySnapshotDataDao.updategdvVmiInventoryStaCode(invCodeString, null, datainfo.getUpc(), datainfo.getFileName(), datainfo.getWarehouse());

            } catch (Exception e) {
                vmiInventorySnapshotDataDao.updategdvVmiInventoryStaCode(null, new Long(DefaultStatus.ERROR.getValue()), datainfo.getUpc(), datainfo.getFileName(), datainfo.getWarehouse());
                log.debug("===============SKU {} CREATE ERROR ================", new Object[] {datainfo.getUpc()});
                log.error("", e);
            }
        }

        return inventoryCheckList;
    }


    public List<InventoryCheck> gdvCreateInventoryCheck(String fileName, String vimSource, List<CompanyShopShare> sharesList) {

        List<InventoryCheck> inventoryCheckList = null;
        try {
            Long ouid = 0l;
            Warehouse warehouse = null;
            if (StringUtils.hasText(vimSource)) {
                warehouse = warehouseDao.findWarehouseByVmiSource(vimSource);
                if (warehouse != null) {
                    ouid = warehouse.getOu().getId();
                } else {
                    log.debug("=============== {GDVCREATEINVENTORYCHECK} BOCSTOREREFERENCE ERROR================", new Object[] {vimSource});
                    throw new Exception("没有找到仓库信息!VMISOURCE:" + vimSource);
                }
            }

            // 根据sh，bj 两个仓库分别查询库存
            List<VmiInventorySnapshotDataCommand> snapshotDataCommands = vmiInventorySnapshotDataDao.findGdvInventoryData(fileName, ouid, vimSource, new BeanPropertyRowMapper<VmiInventorySnapshotDataCommand>(VmiInventorySnapshotDataCommand.class));
            if (snapshotDataCommands.size() == 0) {
                return null;
            }
            inventoryCheckList = this.executeInventoryChecks(snapshotDataCommands, sharesList, warehouse);

        } catch (Exception e) {
            log.error("", e);
        }

        return inventoryCheckList;
    }

}
