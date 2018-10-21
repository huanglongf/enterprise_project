package com.jumbo.wms.manager.hub4;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baoshui.CustomsDeclarationDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.lf.StaLfDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.SkuDeclarationDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.wmsInterface.IntfcCfmDao;
import com.jumbo.dao.wmsInterface.IntfcInvoiceCfmDao;
import com.jumbo.dao.wmsInterface.IntfcInvoiceLineCfmDao;
import com.jumbo.dao.wmsInterface.IntfcLineCfmDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.CustomsDeclarationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuDeclaration;
import com.jumbo.wms.model.lf.StaLf;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLine;
import com.jumbo.wms.model.warehouse.FreightMode;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclaration;
import com.jumbo.wms.model.wmsInterface.WmsInBound;
import com.jumbo.wms.model.wmsInterface.WmsInBoundLine;
import com.jumbo.wms.model.wmsInterface.WmsOutBound;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundDeliveryInfo;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundInvoice;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundInvoiceLine;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundLine;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundVasLine;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceLineCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcLineCfm;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.impl.DefaultReadStatus;
import net.sf.json.JSONObject;


@Service("wmsOrderServiceToHub4Manager")
public class WmsOrderServiceToHub4ManagerImpl extends BaseManagerImpl implements WmsOrderServiceToHub4Manager {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderServiceToHub4ManagerImpl.class);
    private static final long serialVersionUID = -2475047512786743802L;
    @Autowired
    private EventObserver eventObserver;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private BiChannelDao channelDao;
    @Autowired
    private OperationUnitDao opDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryStatusDao invStatus;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StaDeliveryInfoDao deliveryDao;
    @Autowired
    private StaInvoiceDao invoiceDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private IntfcCfmDao intfcCfmDao;
    @Autowired
    private IntfcLineCfmDao intfcLineCfmDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaDeliveryInfoDao sdiDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private IntfcInvoiceCfmDao intfcInvoiceCfmDao;
    @Autowired
    private IntfcInvoiceLineCfmDao intfcInvoiceLineCfmDao;
    @Autowired
    private StaInvoiceLineDao invoiceLineDao;
    @Autowired
    private PackageInfoDao infoDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private Wms2Hub4AllocationService wms2Hub4Service;
    @Autowired
    private StaLfDao staLfDao;
    @Autowired
    private StockTransTxLogDao stLogDao;
    @Autowired
    private CustomsDeclarationDao customsDeclarationDao;
    @Autowired
    private SkuDeclarationDao skuDeclarationDao;
    @Autowired
    private CustomsDeclarationManager customsDeclarationManager;

    @Override
    public WmsResponse createOrderInbound(List<WmsInBound> inbList) {
        Map<StockTransApplication, Map<String, StaLine>> mapList = new HashMap<StockTransApplication, Map<String, StaLine>>();
        WmsResponse re = new WmsResponse();
        for (WmsInBound inb : inbList) {
            log.info("create OrderInbound start:"+inb.getExtCode()+",startTime:"+System.currentTimeMillis());
            StockTransApplication sta = new StockTransApplication();
            sta.setDataSource(inb.getDataSource());
            sta.setOrderSource(inb.getDataSource());
            OperationUnit op;
            re.setUuid(UUIDUtil.getUUID());
            // 验证仓库是否存在
            if (!StringUtil.isEmpty(inb.getWhCode())) {
                op = opDao.getByCode(inb.getWhCode());
                if (op == null) {
                    // throw new BusinessException(1007);
                    re.setErrorCode(WmsResponse.WAREHOUSE_NOT_FOUND);
                    re.setMsg("warehouse not found");
                    re.setOrderCode(inb.getExtCode());
                    return re;
                } else {
                    sta.setMainWarehouse(op);
                }
            } else {
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setMsg("WhCode is  not null");
                re.setOrderCode(inb.getExtCode());
                return re;
            }
            // 上位系统单据唯一校验
            List<StockTransApplication> staList = staDao.findBySlipCodeOuIdType(inb.getExtCode(), op.getId(), StockTransApplicationType.valueOf(inb.getWmsType().intValue()));
            if (staList.size() > 0) {
                for (StockTransApplication s : staList) {
                    if (s.getStatus() != StockTransApplicationStatus.CANCELED) {
                        re.setErrorCode(WmsResponse.DATA_TRANSMISSION + "");
                        re.setOrderCode(inb.getExtCode());
                        re.setMsg("ExtCode already exist ");
                        return re;
                    }
                }

            }
            // 验证店铺是否存在
            if (!StringUtil.isEmpty(inb.getStoreCode())) {
                BiChannel ch = channelDao.getByCode(inb.getStoreCode());
                if (ch == null) {
                    // throw new BusinessException(1005);
                    re.setErrorCode(WmsResponse.SHOP_NOT_FOUND);
                    re.setOrderCode(inb.getExtCode());
                    re.setMsg("Shop not found");
                    return re;
                } else {
                    sta.setOwner(inb.getStoreCode());
                }
            } else {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(inb.getExtCode());
                re.setMsg("StoreCode is not null");
                return re;
            }
            if (StringUtil.isEmpty(inb.getExtCode())) {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(inb.getExtCode());
                re.setMsg("ExtCode is not null");
                return re;
            } else {
                sta.setRefSlipCode(inb.getExtCode());
            }

            sta.setRefSlipType(SlipType.NOTMAL);
            if (StringUtil.isEmpty(inb.getDataSource())) {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(inb.getExtCode());
                re.setMsg("DataSource is not null");
                return re;
            } else {
                sta.setOrderSource(inb.getDataSource());
            }
            if (StringUtil.isEmpty(inb.getExtPoCode())) {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(inb.getExtCode());
                re.setMsg("ExtPoCode is not null");
                return re;
            } else {
                sta.setSlipCode1(inb.getExtPoCode());
                sta.setSlipCode2(inb.getExtPoCode());
            }
            if (StringUtil.isEmpty(inb.getQtyPlanned() + "")) {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(inb.getExtCode());
                re.setMsg("QtyPlanned is not null");
                return re;
            } else {
                sta.setSkuQty(inb.getQtyPlanned());
            }
            if (StringUtil.isEmpty(inb.getWmsType() + "")) {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(inb.getExtCode());
                re.setMsg("WmsType is not null");
                return re;
            } else {
                try {
                    sta.setType(StockTransApplicationType.valueOf(inb.getWmsType()));
                } catch (Exception e) {
                    re.setErrorCode(WmsResponse.DOCUMENT_TYPE_DOES_NOT_EXIST);
                    re.setOrderCode(inb.getExtCode());
                    re.setMsg("WmsType not exist");
                    return re;
                }
            }
            sta.setCreateTime(new Date());
            sta.setExtType(inb.getExtPoType());
            sta.setStatus(StockTransApplicationStatus.CREATED);

            // 明细行校验
            List<StaLine> staLineList = new ArrayList<StaLine>();
            for (WmsInBoundLine line : inb.getWmsInBoundLines()) {
                StaLine staLine = new StaLine();
                // 检验店铺是否存在
                BiChannel ch = channelDao.getByCode(line.getStoreCode());
                if (ch != null) {
                    // 店铺存在，检验upc
                    Sku sku = skuDao.getByCode(line.getUpc());
                    if (sku != null) {
                        // 库存状态校验
                        op = opDao.getByCode(inb.getWhCode());
                        String skuStatusName = null;
                        try {
                            skuStatusName = getSkuStatusName(Integer.parseInt(line.getInvStatus()));
                        } catch (NumberFormatException e) {
                            re.setErrorCode(WmsResponse.STATUS_NOT_FOUND);
                            re.setOrderCode(inb.getExtCode());
                            re.setMsg("WmsType not exist");
                            return re;
                        }
                        InventoryStatus invstatus = invStatus.findByNameAndOu(skuStatusName, op.getId());
                        if (invstatus != null) {
                            staLine.setInvStatus(invstatus);
                        } else {
                            re.setErrorCode(WmsResponse.STATUS_NOT_FOUND);
                            re.setOrderCode(inb.getExtCode());
                            re.setMsg("InvStatus not exist");
                            return re;
                        }
                        staLine.setSku(sku);
                        staLine.setLineNo(line.getExtLineNo());
                        staLine.setSkuName(sku.getName());
                        staLine.setOrderQty(line.getQty());
                        staLine.setQuantity(line.getQty());
                        staLine.setOwner(ch.getCode());
                        staLine.setCompleteQuantity(0L);
                        staLine.setExtMemo(line.getExtMemo());

                        staLineList.add(staLine);
                    } else {
                        // throw new BusinessException(1008, line.getUpc());
                        re.setErrorCode(WmsResponse.SKU_NOT_EXIST);
                        re.setOrderCode(inb.getExtCode());
                        re.setMsg("sku not found");
                        return re;
                    }
                } else {
                    // throw new BusinessException(1005, line.getStoreCode());
                    re.setErrorCode(WmsResponse.SHOP_NOT_FOUND);
                    re.setOrderCode(inb.getExtCode());
                    re.setMsg("StoreCode not found");
                    return re;
                }
            }
            // 检验全部通过，创作业单
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            // StockTransApplication stas = staDao.save(sta);
            Map<String, StaLine> map = new HashMap<String, StaLine>();
            for (StaLine staLine : staLineList) {
                // 合并重复行
                if (map.containsKey(staLine.getSku().getId() + "" + staLine.getInvStatus().getId())) {
                    StaLine staLines = map.get(staLine.getSku().getId());
                    staLine.setQuantity(staLine.getQuantity() + staLines.getQuantity());
                    map.put(staLine.getSku().getId() + "" + staLine.getInvStatus().getId(), staLine);
                    continue;
                }
                map.put(staLine.getSku().getId() + "" + staLine.getInvStatus().getId(), staLine);
            }
            mapList.put(sta, map);
        }
        try {
            wms2Hub4Service.createInboundSta(mapList);
            log.info("create OrderInbound end:"+System.currentTimeMillis());
            re.setStatus(1);
            re.setMsg("success");
        } catch (Exception e) {
            // throw new BusinessException(1001);
            re.setErrorCode(WmsResponse.SYS_EXCEPTION + "");
            re.setMsg("creaet inboundSta filed:" + e.getMessage());
        }
        return re;
    }

    @Override
    public WmsResponse createOrderOutbound(List<WmsOutBound> obList) {

        WmsResponse res = new WmsResponse();
        res.setUuid(UUIDUtil.getUUID());
        /*
         * String str = wms2Hub4Service.crwOrderCreate(obList); if (!StringUtil.isEmpty(str)) { if
         * ("success".equals(str)) { res.setStatus(1); res.setMsg("success"); return res; } else {
         * String[] msg = str.split("_-_"); res.setErrorCode(WmsResponse.SYS_EXCEPTION);
         * res.setMsg(msg[0]); res.setOrderCode(msg[1]); return res; } }
         */
        
        for (WmsOutBound ob : obList) {
            Map<String, List<WmsOutBoundVasLine>> vasMap = new HashMap<String, List<WmsOutBoundVasLine>>();
            log.info("create OrderOutbound start:"+ob.getExtCode()+",startTime:"+System.currentTimeMillis());
            WmsResponse re = new WmsResponse();
            re.setUuid(UUIDUtil.getUUID());
            /*
             * String s = wms2Hub4Service.crwOrder(ob); if (!StringUtil.isEmpty(s)) { if
             * ("success".equals(s)) { re.setStatus(1); re.setMsg("success"); return re; } else {
             * re.setErrorCode(WmsResponse.SYS_EXCEPTION); re.setMsg(s);
             * re.setOrderCode(ob.getExtCode()); return re; } }
             */

            boolean isCrw = false;
            StaLfCommand staLfCommand = null;
            if ("Nike CRW线下配送店".equals(ob.getOwner())) {
                staLfCommand = findStaLfCommandInfo(ob);
                isCrw = true;
            }

            StockTransApplication sta = new StockTransApplication();
            OperationUnit op;
            // 验证仓库是否存在
            if (!StringUtil.isEmpty(ob.getWhCode())) {
                op = opDao.getByCode(ob.getWhCode());
                if (op == null) {
                    // throw new BusinessException(1007, ob.getWhCode());
                    re.setErrorCode(WmsResponse.WAREHOUSE_NOT_FOUND);
                    re.setMsg("warehouse not found");
                    re.setOrderCode(ob.getExtCode());
                    return re;
                } else {
                    sta.setMainWarehouse(op);
                }
            } else {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(ob.getExtCode());
                re.setMsg("Whcode is not null");
                return re;
            }
            // 上位系统单据唯一校验
            List<StockTransApplication> staList = staDao.findBySlipCodeOuIdType(ob.getExtCode(), op.getId(), StockTransApplicationType.valueOf(ob.getWmsType().intValue()));
            if (staList.size() > 0) {
                for (StockTransApplication s : staList) {
                    if (s.getStatus() != StockTransApplicationStatus.CANCELED) {
                        re.setErrorCode(WmsResponse.DATA_TRANSMISSION + "");
                        re.setOrderCode(ob.getExtCode());
                        re.setMsg("ExtCode already exist ");
                        return re;
                    }
                }
            }
            // 验证店铺是否存在
            if (!StringUtil.isEmpty(ob.getOwner())) {
                BiChannel ch = channelDao.getByCode(ob.getOwner());
                if (ch == null) {
                    // throw new BusinessException(1005, ob.getOwner());
                    re.setErrorCode(WmsResponse.SHOP_NOT_FOUND);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("shop not found");
                    return re;
                } else {
                    sta.setOwner(ob.getOwner());
                }
            } else {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setMsg("Owner is not null");
                re.setOrderCode(ob.getExtCode());
                return re;
            }
            // 单据类型检验
            if (StringUtil.isEmpty(ob.getWmsType() + "") && !isCrw) {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(ob.getExtCode());
                re.setMsg("WmsType is not null");
                return re;
            } else {
                try {
                    sta.setType(StockTransApplicationType.valueOf(ob.getWmsType()));
                } catch (Exception e) {
                    re.setErrorCode(WmsResponse.DOCUMENT_TYPE_DOES_NOT_EXIST);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("WmsType not found");
                    return re;
                }
            }
            if (StringUtil.isEmpty(ob.getRefSlipType() + "") && !isCrw) {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(ob.getExtCode());
                re.setMsg("RefSlipType is not null");
                return re;
            } else if (StringUtil.isEmpty(ob.getEcOrderCode()) && !isCrw) {
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(ob.getExtCode());
                re.setMsg("EcOrderCode is not null");
                return re;
            } else if (StringUtil.isEmpty(ob.getExtCode()) && !isCrw) {
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(ob.getExtCode());
                re.setMsg("ExtCode is not null");
                return re;
            } else if (StringUtil.isEmpty(ob.getDataSource())) {
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(ob.getExtCode());
                re.setMsg("DataSource is not null");
                return re;
            } else {
                sta.setRefSlipCode(ob.getExtCode());
                if (isCrw) {
                    sta.setSlipCode1(staLfCommand.getSlipcode1());
                    sta.setSlipCode2(staLfCommand.getSlipcode2());
                    sta.setFreightMode(FreightMode.valueOf(10));// 上门自取
                    if (staLfCommand.getPlantime() != null) {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date date;
                        try {
                            date = df.parse(staLfCommand.getPlantime());
                        } catch (ParseException e) {
                            re.setErrorCode(e.getMessage());
                            re.setOrderCode(ob.getExtCode());
                            re.setMsg("create wmsOutboundSta ParseException error:" + ob.getExtCode() + e.getMessage());
                            re.setStatus(0);
                            return re;
                        }
                        sta.setPlanOutboundTime(date);// 计划发货时间
                    }
                } else {
                    sta.setSlipCode1(ob.getEcOrderCode());
                    sta.setSlipCode2(ob.getEcOrderCode());
                    sta.setPlanOutboundTime(ob.getPlanOutboundTime());
                }
                try {
                    sta.setRefSlipType(SlipType.NOTMAL);
                } catch (Exception e) {
                    re.setErrorCode(WmsResponse.DOCUMENT_TYPE_DOES_NOT_EXIST);
                    re.setOrderCode(ob.getExtCode());
                    return re;
                }
                sta.setDataSource(ob.getDataSource());
            }
            sta.setMemo(ob.getMemo());
            sta.setIsLocked(ob.getIsLocked());
            sta.setOrderTransferFree(ob.getOrderTransferFree());
            sta.setStorecode(ob.getStoreCode());
            sta.setNikePickUpCode(ob.getNikePickUpCode());
            sta.setNikePickUpType(ob.getNikePickUpType());
            sta.setIsPreSale(ob.getIsPreSale());
            sta.setOrderCreateTime(ob.getOrderCreateTime());
            sta.setOrderDiscount(ob.getOrderDiscount());
            sta.setArriveTime(ob.getPlanArriveTime());
            sta.setIsTransUpgrade(ob.getIsTransUpgrade());
            sta.setExtType(ob.getExtType());
            sta.setCreateTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            // 出库物流信息检验
            WmsOutBoundDeliveryInfo obDeliverInfo = ob.getWmsOutBoundDeliveryInfo();
            StaDeliveryInfo sd = null;
            if (obDeliverInfo != null) {
                if (StringUtil.isEmpty(obDeliverInfo.getCountry()) && !isCrw) {
                    // throw new BusinessException(1002);
                    re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("country is not null..");
                    return re;
                } else if (StringUtil.isEmpty(obDeliverInfo.getCity()) && !isCrw) {
                    re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("City is not null..");
                    return re;
                } else if (StringUtil.isEmpty(obDeliverInfo.getProvince()) && !isCrw) {
                    re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("Province is not null..");
                    return re;
                } else if (StringUtil.isEmpty(obDeliverInfo.getDistrict()) && !isCrw) {
                    re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("District is not null..");
                    return re;
                } else if (StringUtil.isEmpty(obDeliverInfo.getAddress()) && !isCrw) {
                    re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("Address is not null..");
                    return re;
                } else if (StringUtil.isEmpty(obDeliverInfo.getTelephone()) && StringUtil.isEmpty(obDeliverInfo.getMoblie()) && !isCrw) {
                    re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("Telephone or Moblie is not null..");
                    return re;
                } else {
                    sd = new StaDeliveryInfo();
                    sd.setCountry(obDeliverInfo.getCountry());
                    sd.setCity(obDeliverInfo.getCity());
                    sd.setProvince(obDeliverInfo.getProvince());
                    sd.setDistrict(obDeliverInfo.getDistrict());
                    if (isCrw) {
                        staLfCommand.setCity(sd.getCity());
                        sd.setAddress(staLfCommand.getAddress1() + "," + staLfCommand.getAddress2() + "," + staLfCommand.getAddress3() + "," + staLfCommand.getAddress4());
                    } else {
                        sd.setAddress(obDeliverInfo.getAddress());
                    }
                    sd.setTelephone(obDeliverInfo.getTelephone());
                    sd.setMobile(obDeliverInfo.getMoblie());
                    sd.setReceiver(obDeliverInfo.getReceiver());
                    sd.setLpCode(obDeliverInfo.getLpCode());
                    sd.setZipcode(obDeliverInfo.getZipcode());
                    sd.setTrackingNo(obDeliverInfo.getTransNo());
                    sd.setIsCod(obDeliverInfo.getIsCod());
                    sd.setIsCodPos(obDeliverInfo.getIsCodPos());
                    // sd.setTransTimeType(TransTimeType.valueOf(obDeliverInfo.getTransTimeType()));
                    // sd.setTransType(TransType.valueOf(obDeliverInfo.getTransType()));
                    sd.setRemark(obDeliverInfo.getTransMemo());
                    sd.setCountryEn(obDeliverInfo.getCountryEn());
                    sd.setCityEn(obDeliverInfo.getCityEn());
                    sd.setDistrictEn(obDeliverInfo.getDistrictEn());
                    sd.setAddressEn(obDeliverInfo.getAddressEn());
                    sd.setReceiverEn(obDeliverInfo.getReceiverEn());
                }
            } else {
                // throw new BusinessException(1002);
                re.setErrorCode(WmsResponse.PARAMETER_INCOMPLETE);
                re.setOrderCode(ob.getExtCode());
                re.setMsg("WmsOutBoundDeliveryInfo is not null");
                return re;
            }
            // 出库单明细行检验
            List<StaLine> staLineList = new ArrayList<StaLine>();
            List<WmsOutBoundLine> wmsOutBoundLines = ob.getWmsOutBoundLines();

            Long skuQty = 0L;
            for (WmsOutBoundLine wmsOutBoundLine : wmsOutBoundLines) {
                StaLine staLine = new StaLine();
                if (isCrw) {
                    wmsOutBoundLine.setStoreCode(ob.getOwner());
                }
                // 检验店铺是否存在
                BiChannel ch = channelDao.getByCode(wmsOutBoundLine.getStoreCode());
                if (ch != null) {
                    // 店铺存在，检验upc
                    Sku sku = skuDao.getByCode(wmsOutBoundLine.getUpc());
                    if (sku != null) {
                        // 库存状态校验
                        op = opDao.getByCode(ob.getWhCode());
                        String skuStatusName = null;
                        try {
                            skuStatusName = getSkuStatusName(Integer.parseInt(wmsOutBoundLine.getInvStatus()));
                        } catch (NumberFormatException e) {
                            re.setErrorCode(WmsResponse.STATUS_NOT_FOUND);
                            re.setOrderCode(ob.getExtCode());
                            re.setMsg("InvStatus not exist");
                            return re;
                        }
                        InventoryStatus invstatus = invStatus.findByNameAndOu(skuStatusName, op.getId());
                        if (invstatus != null) {
                            staLine.setInvStatus(invstatus);
                        } else {
                            re.setErrorCode(WmsResponse.STATUS_NOT_FOUND);
                            re.setOrderCode(ob.getExtCode());
                            re.setMsg("InvStatus not exist");
                            return re;
                        }
                        staLine.setSku(sku);
                        staLine.setLineNo(wmsOutBoundLine.getLineNo());
                        staLine.setSkuName(sku.getName());
                        staLine.setOrderQty(wmsOutBoundLine.getQty());
                        staLine.setActivitySource(wmsOutBoundLine.getActiveCode());
                        staLine.setLineNo(wmsOutBoundLine.getLineNo());
                        staLine.setListPrice(wmsOutBoundLine.getListPrice());
                        staLine.setLineDiscount(wmsOutBoundLine.getLineDiscount());
                        staLine.setOrderTotalActual(wmsOutBoundLine.getOrderTotalActual());
                        // staLine.setOwner(wmsOutBoundLine.getOwner());
                        staLine.setOwner(ch.getCode());
                        staLine.setUnitPrice(wmsOutBoundLine.getUnitPrice());
                        staLine.setQuantity(wmsOutBoundLine.getQty());
                        staLine.setExtMemo(wmsOutBoundLine.getExtMemo());


                        skuQty += staLine.getQuantity();

                        // 添加增值服务
                        vasMap.put(sta.getRefSlipCode() + "-" + sku.getId(), wmsOutBoundLine.getWobvlList());

                        staLineList.add(staLine);
                    } else {
                        // throw new BusinessException(1008, wmsOutBoundLine.getUpc());
                        re.setErrorCode(WmsResponse.SKU_NOT_EXIST);
                        re.setOrderCode(ob.getExtCode());
                        re.setMsg("sku not found");
                        return re;
                    }
                } else {
                    // throw new BusinessException(1005, wmsOutBoundLine.getStoreCode());
                    re.setErrorCode(WmsResponse.SHOP_NOT_FOUND);
                    re.setOrderCode(ob.getExtCode());
                    re.setMsg("shop not found");
                    return re;
                }

            }
            sta.setSkuQty(skuQty);
            // 出库发票检验
            List<WmsOutBoundInvoice> wmsOutBoundInvoices = ob.getWmsOutBoundInvoices();
            Map<StaInvoice, List<StaInvoiceLine>> map = new HashMap<StaInvoice, List<StaInvoiceLine>>();
            if (wmsOutBoundInvoices != null) {
                for (WmsOutBoundInvoice wmsOutBoundInvoice : wmsOutBoundInvoices) {
                    StaInvoice invoice = new StaInvoice();
                    invoice.setInvoiceCode(wmsOutBoundInvoice.getInvoiceCode());
                    invoice.setAmt(wmsOutBoundInvoice.getAmt());
                    invoice.setCompany(wmsOutBoundInvoice.getCompany());
                    invoice.setDrawer(wmsOutBoundInvoice.getDrawer());
                    invoice.setIdentificationNumber(wmsOutBoundInvoice.getIdentificationNumber());
                    invoice.setInvoiceDate(wmsOutBoundInvoice.getInvoiceDate());
                    invoice.setAddress(wmsOutBoundInvoice.getAddress());
                    invoice.setPayer(wmsOutBoundInvoice.getPayer());
                    invoice.setPayee(wmsOutBoundInvoice.getPayee());
                    invoice.setTelephone(wmsOutBoundInvoice.getTelephone());
                    invoice.setUnitPrice(wmsOutBoundInvoice.getUnitPrice());
                    invoice.setItem(wmsOutBoundInvoice.getItem());
                    List<WmsOutBoundInvoiceLine> invoiceLineList = wmsOutBoundInvoice.getWmsOutBoundInvoiceLines();
                    List<StaInvoiceLine> invoiceLineLists = new ArrayList<StaInvoiceLine>();
                    for (WmsOutBoundInvoiceLine wmsOutBoundInvoiceLine : invoiceLineList) {
                        StaInvoiceLine invoiceLine = new StaInvoiceLine();
                        invoiceLine.setAmt(wmsOutBoundInvoiceLine.getAmt());
                        invoiceLine.setItem(wmsOutBoundInvoiceLine.getItem());
                        invoiceLine.setLineNO(wmsOutBoundInvoiceLine.getLineNO());
                        invoiceLine.setQty(wmsOutBoundInvoiceLine.getQty());
                        invoiceLine.setUnitPrice(wmsOutBoundInvoiceLine.getUnitPrice());
                        invoiceLineLists.add(invoiceLine);
                    }
                    map.put(invoice, invoiceLineLists);
                }
            }
            // 创出库单
            try {
                log.info("create OrderOutbound sta:"+System.currentTimeMillis());
                wms2Hub4Service.createAllocationSta(sta, staLineList, sd, map, re, staLfCommand, vasMap);
                log.info("create OrderOutbound end:"+System.currentTimeMillis());
                re.setStatus(1);
                re.setMsg("success");
                return re;
            } catch (BusinessException e) {
                log.error("create wmsOutboundSta filed:" + ob.getExtCode(), e);
                // throw new BusinessException(1001);
                re.setErrorCode(e.getErrorCode() + "");
                re.setOrderCode(ob.getExtCode());
                re.setMsg("create wmsOutboundSta filed:" + ob.getExtCode() + e.getMessage());
                re.setStatus(0);
                return re;
            }
        }
        return null;
    }


    private StaLfCommand findStaLfCommandInfo(WmsOutBound ob) {
        String memo = ob.getMemo();
        JSONObject jsonObject = JSONObject.fromObject(memo);
        StaLfCommand staLfCommand = new StaLfCommand();
        staLfCommand.setSlipcode1(jsonObject.getString("slipcode1"));
        staLfCommand.setSlipcode2(jsonObject.getString("slipcode2"));
        staLfCommand.setPlantime(jsonObject.getString("plantime"));
        staLfCommand.setAddress1(jsonObject.getString("address1"));
        staLfCommand.setAddress2(jsonObject.getString("address2"));
        staLfCommand.setAddress3(jsonObject.getString("address3"));
        staLfCommand.setAddress4(jsonObject.getString("address4"));
        staLfCommand.setCompanyName(jsonObject.getString("companyName"));
        staLfCommand.setDivisionCode(jsonObject.getString("divisionCode"));
        staLfCommand.setNfsStoreCode(jsonObject.getString("nfsStoreCode"));
        staLfCommand.setNikePo(jsonObject.getString("nikePo"));
        staLfCommand.setOrderType(jsonObject.getString("orderType"));
        staLfCommand.setPackSlipNo(jsonObject.getString("packSlipNo"));
        staLfCommand.setTransportMode(jsonObject.getString("transportMode"));
        staLfCommand.setTransportPra(jsonObject.getString("transportPra"));
        staLfCommand.setVasCode(jsonObject.getString("vasCode"));
        staLfCommand.setZip(jsonObject.getString("zip"));
        staLfCommand.setIsMoreWh(jsonObject.getBoolean("isMoreWh"));
        staLfCommand.setTransMethod(jsonObject.getString("transMethod"));

        return staLfCommand;
    }

    /**
     * 创建反馈信息给HUB4
     * 
     * @param staId
     */
    @Transactional
    public void createResponseInfo(Long staId, Integer transType) {
        if (staId == null && transType == null) {
            //
            throw new BusinessException();
        }
        // StockTransVoucher stv = null;
        // if (stvId != null) {
        // stv = stvDao.getByPrimaryKey(stvId);
        // if (staId == null) {
        // staId = stv.getSta().getId();
        // }
        // }

        StockTransApplication sta = staDao.getByPrimaryKey(staId);

        // 头信息
        createResponseInfo(sta, transType);

    }

    /**
     * 封装头信息
     * 
     * @param sta
     * @param stv
     */
    @Transactional
    private void createResponseInfo(StockTransApplication sta, Integer transType) {
        OperationUnit ou = opDao.getByPrimaryKey(sta.getMainWarehouse().getId());
        Long actualQty = 0L;
        Date transTime = null;
        // 获取出入库的STV
        if (transType == 1) {
            transTime = sta.getOutboundTime();
            // actualQty = staLineDao.findTotalQtyByStaId(sta.getId(), new
            // SingleColumnRowMapper<Long>(Long.class));
        } else {
            transTime = sta.getInboundTime();
        }
        if (transTime == null) {
            transTime = new Date();
        }
        List<StvLineCommand> slcList = stLogDao.findLogGroupListByStaId(transType == 1 ? 2 : 1, sta.getId(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        for (StvLineCommand sl : slcList) {
            actualQty += sl.getQuantity();
        }

		StaLf sl = staLfDao.getStaLfByStaId(sta.getId());
        String inCartonNo = null;
        if (transType == 0 && !sta.getRefSlipCode().equals(sta.getSlipCode1())) {
            inCartonNo = sta.getRefSlipCode();
        }

        // 判断是否已经封装过数据
        IntfcCfm intfcCfm = intfcCfmDao.findIntfcCfmByStaCode(sta.getCode(), sta.getId(), new BeanPropertyRowMapperExt<IntfcCfm>(IntfcCfm.class));

        if (intfcCfm != null) {
            return;
        }

        // 判断按箱收货的单子是否全部完成
        boolean isFinish = true;
        List<StockTransApplication> staList = staDao.getStaListBySlipCode1(sta.getSlipCode1());
        for (StockTransApplication sta1 : staList) {
            if (sta1.getType() == StockTransApplicationType.VMI_INBOUND_CONSIGNMENT && sta1.getStatus() != StockTransApplicationStatus.FINISHED && sta1.getStatus() != StockTransApplicationStatus.CANCEL_UNDO
                    && sta1.getStatus() != StockTransApplicationStatus.CANCELED) {
                isFinish = false;
            }
        }


        IntfcCfm ic = new IntfcCfm();
        ic.setUuid(UUIDUtil.getUUID());
        ic.setWmsCode(sta.getCode());
        if (transType == 0) {
            ic.setExtCode(sta.getSlipCode1());
            ic.setEcOrderCode(sta.getSlipCode1());
        } else {
            ic.setExtCode(sta.getRefSlipCode());
            ic.setEcOrderCode(sta.getSlipCode1());
        }
        ic.setStoreCode(sta.getOwner());
        ic.setWmsStatus(sta.getStatus());
        ic.setWmsType(sta.getType());
        ic.setExtType(sta.getExtType());
        ic.setWhCode(ou.getCode());
        ic.setTransactionTime(transTime);
        ic.setTransactionType(transType);
        ic.setFromLocation(sta.getFromLocation());
        ic.setToLocation(sta.getToLocation());
        // ic.setPlanQty(sta.getSkuQty());
        ic.setActualQty(actualQty);
        ic.setDataSource(sta.getDataSource());
        Map<String, String> loadKeyMap = new HashMap<String, String>();
        if (sl != null) {
            loadKeyMap.put("loadkey", sta.getSlipCode2());
            loadKeyMap.put("packSlipNo", sl.getPackSlipNo());
            ic.setExtMemo(JsonUtil.writeValue(loadKeyMap));
        } else {
            ic.setExtMemo(sta.getExtMemo());
        }
		ic.setStatus(1);
        if (isFinish) {
            ic.setIsFinished(1);
        } else {
            ic.setIsFinished(0);
        }
        ic.setStvId(sta.getId());
        intfcCfmDao.save(ic);

        // 明细
        if (StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType()) || StockTransApplicationType.VMI_RETURN.equals(sta.getType())) {
            createIntfcCfmLineByRtn(ic, sta, transType == 1 ? 2 : 1);
        } else {
            createIntfcCfmLine(ic, transType == 1 ? 2 : 1, inCartonNo);
        }

        // 发票
        createIntfcInvoiceCfm(ic, sta);

        // intfcCfmDao.flush();
    }

    /**
     * 封装明细行
     * 
     * @param ic
     * @param stv
     */
    @Transactional
    private void createIntfcCfmLine(IntfcCfm ic, Integer transType, String inCartonNo) {
        // List<StvLineCommand> slcList = stvLineDao.findByStvIdGroupBySkuLocationOwner(stv.getId(),
        // new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        List<StvLineCommand> slcList = stLogDao.findLogGroupListByStaId(transType, ic.getStvId(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        String transNos = "";
        String lpCode = "";
        StaDeliveryInfo sd = sdiDao.getByPrimaryKey(ic.getStvId());
        if (sd != null) {
            lpCode = sd.getLpCode();
            List<PackageInfo> infos = infoDao.findByStaId(sd.getId());
            if (infos != null) {
                for (PackageInfo info : infos) {
                    transNos += info.getTrackingNo() + ",";
                }
            }

        }

        for (StvLineCommand slc : slcList) {
            InventoryStatus status = inventoryStatusDao.getByPrimaryKey(slc.getIntInvstatus());
            Sku sku = skuDao.getByPrimaryKey(slc.getSkuId());
            List<StaLine> slList = staLineDao.findByOwnerAndStatus(ic.getStvId(), sku.getId(), slc.getOwner(), status.getId());
            Long planQty = 0L;
            String lineNo = null;
            String memo = null;
            if (slList != null) {
                for (StaLine staLine : slList) {
                    planQty += staLine.getQuantity();
                    lineNo = staLine.getLineNo();
                    memo = staLine.getExtMemo();
                }
            }

            IntfcLineCfm ilc = new IntfcLineCfm();
            ilc.setIntfcId(ic);
            ilc.setUpc(sku.getCode());
            ilc.setStoreCode(slc.getOwner());
            ilc.setSkuStyle(sku.getKeyProperties());
            ilc.setSkuColor(sku.getColor());
            ilc.setSkuSize(sku.getSkuSize());
            ilc.setInvStatus(status.getName());
            ilc.setBatchNumber(slc.getBatchCode());
            ilc.setActualQty(slc.getQuantity());
            ilc.setTransportServiceProvider(lpCode);
            ilc.setTrackingNumber(transNos);
            ilc.setPlanQty(planQty);
            ilc.setCartonNo(inCartonNo);
            ilc.setExtLineNo(lineNo);
            ilc.setExtMemo(memo);
            intfcLineCfmDao.save(ilc);
        }

    }


    /**
     * 封装明细行 类型 退仓
     * 
     * @param ic
     * @param stv
     */
    @Transactional
    private void createIntfcCfmLineByRtn(IntfcCfm ic, StockTransApplication sta, Integer transType) {
        List<Carton> cartonList = cartonDao.findCartonByStaId(sta.getId());
        // List<StvLineCommand> slcList = stvLineDao.findByStvIdGroupBySkuLocationOwner(stv.getId(),
        // new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        StaDeliveryInfo sd = sdiDao.getByPrimaryKey(sta.getId());

        // 判断是否有装箱，未装箱按普通逻辑走
        if (cartonList == null || cartonList.size() == 0) {
            createIntfcCfmLine(ic, transType, null);
            return;
        }

        List<IntfcLineCfm> ilcList = new ArrayList<IntfcLineCfm>();
        for (Carton carton : cartonList) {
            List<CartonLine> cLines = cartonDao.findCartonLineByCarId(carton.getId());
            if (cLines == null || cLines.size() == 0) {
                // createIntfcCfmLine(ic, stv, null);
                continue;
            }
            for (CartonLine cl : cLines) {
                InventoryStatus status = inventoryStatusDao.getByPrimaryKey(123L);
                Sku sku = skuDao.getByPrimaryKey(cl.getSku().getId());

                StaLine sl = staLineDao.findBySkuSta(sta.getId(), sku.getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));

                IntfcLineCfm ilc = new IntfcLineCfm();
                ilc.setIntfcId(ic);
                ilc.setUpc(sku.getCode());
                ilc.setStoreCode(sta.getOwner());
                ilc.setSkuStyle(sku.getKeyProperties());
                ilc.setSkuColor(sku.getColor());
                ilc.setSkuSize(sku.getSkuSize());
                ilc.setInvStatus(status.getName());
                // ilc.setBatchNumber(slc.getBatchCode());
                ilc.setActualQty(cl.getQty());
                ilc.setCartonNo(carton.getSeqNo());
                ilc.setOutboundBoxCode(carton.getCode());
                ilc.setTransportServiceProvider(sd.getLpCode());
                ilc.setTrackingNumber(StringUtil.isEmpty(carton.getTrackingNo()) ? sd.getTrackingNo() : carton.getTrackingNo());
                ilc.setExtLineNo(sl.getLineNo());
                ilc.setExtMemo(sl.getExtMemo());
                ilcList.add(ilc);
            }
        }
        for (IntfcLineCfm ilc : ilcList) {

            intfcLineCfmDao.save(ilc);
        }

    }



    /**
     * 封装发票及明细
     * 
     * @param ic
     * @param sta
     */
    @Transactional
    private void createIntfcInvoiceCfm(IntfcCfm ic, StockTransApplication sta) {
        List<StaInvoice> invoices = staInvoiceDao.getBySta(sta.getId());
        if (invoices == null || invoices.size() == 0) {
            return;
        }
        for (StaInvoice si : invoices) {
            IntfcInvoiceCfm iic = new IntfcInvoiceCfm();
            iic.setIntfcId(ic);
            iic.setInvoiceCode(si.getInvoiceCode());
            iic.setInvoiceDate(si.getInvoiceDate());
            iic.setPayer(si.getPayer());
            iic.setItem(si.getItem());
            iic.setQty(si.getQty());
            iic.setUnitPrice(si.getUnitPrice());
            iic.setAmt(si.getAmt());
            iic.setMemo(si.getMemo());
            iic.setPayee(si.getPayee());
            iic.setDrawer(si.getDrawer());
            iic.setExecutecount(si.getExecutecount());
            iic.setCompany(si.getCompany());
            iic.setIdentificationNumber(si.getIdentificationNumber());
            iic.setAddress(si.getAddress());
            iic.setTelephone(si.getTelephone());
            intfcInvoiceCfmDao.save(iic);
            List<StaInvoiceLine> invoiceLines = invoiceLineDao.getByStaInvoiceId(si.getId());
            if (invoiceLines != null) {
                for (StaInvoiceLine sil : invoiceLines) {
                    IntfcInvoiceLineCfm iil = new IntfcInvoiceLineCfm();
                    iil.setIntfcInvoiceId(iic);
                    iil.setItem(sil.getItem());
                    iil.setUnitPrice(sil.getUnitPrice());
                    iil.setQty(sil.getQty());
                    iil.setAmt(sil.getAmt());
                    iil.setLineNO(sil.getLineNO());
                    intfcInvoiceLineCfmDao.save(iil);
                }
            }
        }


    }

    /**
     * 更新IntfcCfm状态
     * 
     * @param icList
     * @param status
     */
    @Transactional
    public void modifyIntfcCfmStatusById(Long icId, Integer status, String errorMsg) {
        IntfcCfm ic = intfcCfmDao.getByPrimaryKey(icId);
        if (status == 2) {
            ic.setStatus(2);
        } else if (status == 3) {
            ic.setStatus(3);
            ic.setErrorCount(ic.getErrorCount() == null ? 1 : ic.getErrorCount() + 1);
            ic.setErrorMsg(errorMsg);
        } else if (status == 1) {
            ic.setErrorCount(ic.getErrorCount() == null ? 1 : ic.getErrorCount() + 1);
            ic.setErrorMsg(errorMsg);
        }
        intfcCfmDao.save(ic);
    }


    public WmsResponse createStv(StockTransApplication sta, WmsResponse rs, int direction, boolean isPartial) {
        BigDecimal transactionid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        if (transactionid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
        if (transactionType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
        }
        Map<String, InventoryStatus> invmap = new HashMap<String, InventoryStatus>();
        Map<String, WarehouseLocation> locationmap = new HashMap<String, WarehouseLocation>();
        int tdType = TransactionDirection.OUTBOUND.getValue();
        String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
        stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), null, tdType, sta.getMainWarehouse().getId(), transactionid.longValue());
        List<StaLine> staLineList = staLineDao.findByStaId(sta.getId());
        List<StaLineCommand> stalinecmds = new ArrayList<StaLineCommand>();
        for (StaLine staLine : staLineList) {
            StaLineCommand s = new StaLineCommand();
            s.setSkuCode(staLine.getSku().getCode());
            s.setQuantity(staLine.getQuantity());
            s.setIntInvstatusName(staLine.getInvStatus().getName());
            stalinecmds.add(s);
        }
        OperationUnit ou = operationUnitDao.getDefaultInboundWhByShopId(shop.getId());
        OperationUnit parOu = operationUnitDao.getByPrimaryKey(ou.getParentUnit().getId());
        Long cmpOuid = parOu.getParentUnit().getId();
        ReadStatus rs1 = new DefaultReadStatus();
        rs1.setStatus(ReadStatus.STATUS_SUCCESS);
        rs1 = excelReadManager.vmiReturnValidate(rs1, stalinecmds, staLineList, invmap, locationmap, cmpOuid, sta.getMainWarehouse().getId(), "SKU编码", shop.getCode(), transactionType, sta, shop);
        if (rs1.getStatus() != ReadStatus.STATUS_SUCCESS) {
            throw new BusinessException();
        }
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
        Long skuQty = 0L;
        // Map<String, StaLine> staMap = new HashMap<String, StaLine>();
        for (StaLineCommand cmd : stalinecmds) {
            InventoryStatus instatus = invStatus.getByPrimaryKey(cmd.getInvStatus().getId());
            StvLine stvLine = new StvLine();
            stvLine.setDirection(TransactionDirection.valueOf(direction));
            stvLine.setOwner(sta.getOwner());
            stvLine.setQuantity(cmd.getQuantity());
            stvLine.setSku(cmd.getSku());
            stvLine.setInvStatus(instatus);
            stvLine.setLocation(cmd.getWarehouseLocation());
            stvLine.setTransactionType(transactionType);
            stvLine.setWarehouse(ou);
            stvLine.setStv(stv);
            stvLineDao.save(stvLine);
        }
        sta.setSkuQty(skuQty);
        staDao.flush();
        // 出库校验库存
        if (direction == 2) {
            if (shop.getIsPartOutbound() == null || !shop.getIsPartOutbound()) {
                // 计算销售可用量KJL
                // 不允许 原来逻辑
                wareHouseManager.isInventoryNumber(sta.getId());
                wareHouseManager.occupyInventoryByStaId(sta.getId(), null, shop);
            } else {
                // 允许部分占用
                wareHouseManager.occupyInventoryByStaIdPartial(sta.getId(), null, shop, ou, transactionType, stv, true);
            }
        }
        stvLineDao.flush();
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
        return rs;
    }

    private String getSkuStatusName(int value) {
        switch (value) {
            case 1:
                return "良品";
            case 2:
                return "残次品";
            case 3:
                return "待报废";
            case 4:
                return "待处理品";
            case 5:
                return "临近保质期";
            case 6:
                return "良品不可销售";
            case 7:
                return "残次可销售";
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * 前海出入库单报关状态更改
     */
    @Override
    public WmsResponse qianHaiOrderStatusNotify(String orderCode, Integer status) {
        WmsResponse re = new WmsResponse();
        try {
            StockTransApplication sta = staDao.getByCode(orderCode);
            if (sta != null) {

                CustomsDeclaration customsDeclaration = customsDeclarationDao.getCustomsDeclarationByStaCode(sta.getCode());
                customsDeclarationManager.changeCustomsDeclarationDeclarationStatus(customsDeclaration.getId(), status);
                re.setStatus(1);
            } else {
                re.setStatus(0);
                re.setMsg("无此单据！");
            }

        } catch (Exception e) {
            log.error("qianHaiOrderStatusNotify exception:", e);
            re.setStatus(0);
            re.setMsg(e.getMessage());
        }
        return re;
    }

    /**
     * 前海商品报关状态修改
     */
    @Override
    public WmsResponse qianHaiSkuInfoStatusNotify(String hsCode, Integer status, Integer ciqStatus) {
        WmsResponse re = new WmsResponse();
        try {
            //List<SkuDeclaration> sdList = skuDeclarationDao.findSkuDeclarationMoreLocation(skuUpc, new BeanPropertyRowMapper<SkuDeclaration>(SkuDeclaration.class));
            List<SkuDeclaration> sdList = skuDeclarationDao.findSkuDeclarationByHsCode(hsCode, new BeanPropertyRowMapper<SkuDeclaration>(SkuDeclaration.class));
            if (sdList == null || sdList.size() == 0) {
                re.setStatus(0);
                re.setMsg("没有此商品！");
            } else {

                customsDeclarationManager.changeSkuDeclarationDeclarationStatus(sdList, status, ciqStatus);
                re.setStatus(1);
            }

        } catch (Exception e) {
            log.error("qianHaiOrderStatusNotify exception:", e);
            re.setStatus(0);
            re.setMsg(e.getMessage());
        }
        return re;
    }
}
