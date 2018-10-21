package com.jumbo.wms.manager.print.data;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.scm.baseservice.message.util.secret.AESGeneralUtil;
import com.baozun.scm.baseservice.message.util.secret.MD5Util;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.commandMapper.ImportMacaoHGDEntryListRowMapper;
import com.jumbo.dao.commandMapper.OutBoundPackageInfoObjRowMapper;
import com.jumbo.dao.commandMapper.PickingListObjRowMapper;
import com.jumbo.dao.lf.StaLfDao;
import com.jumbo.dao.lf.StaLineLfDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.HandOverListDao;
import com.jumbo.dao.warehouse.HandOverListLineDao;
import com.jumbo.dao.warehouse.ImperfectStvDao;
import com.jumbo.dao.warehouse.ImportPrintDataDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.RtwDieKingLineDao;
import com.jumbo.dao.warehouse.SkuImperfectDao;
import com.jumbo.dao.warehouse.SkuSizeConfigDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StoProCodeDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhTransAreaNoDao;
import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SalesTicketResult;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.manager.expressDelivery.TransManagerImpl;
import com.jumbo.wms.manager.print.BasePrintManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.jasperReport.ImportHGDEntryListObj;
import com.jumbo.wms.model.jasperReport.InventoryOccLine;
import com.jumbo.wms.model.jasperReport.InventoryOccupay;
import com.jumbo.wms.model.jasperReport.OutBoundPackageInfoObj;
import com.jumbo.wms.model.jasperReport.OutBoundPackingObj;
import com.jumbo.wms.model.jasperReport.OutBoundSendInfo;
import com.jumbo.wms.model.jasperReport.OutBoundSendInfoLine;
import com.jumbo.wms.model.jasperReport.PackingSummaryForNike;
import com.jumbo.wms.model.jasperReport.PickingListObj;
import com.jumbo.wms.model.jasperReport.PickingListSubLineObj;
import com.jumbo.wms.model.jasperReport.SpecialPackagingData;
import com.jumbo.wms.model.lf.StaLf;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.lf.StaLineLf;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLine;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.HandOverListLineCommand;
import com.jumbo.wms.model.warehouse.ImperfectStvCommand;
import com.jumbo.wms.model.warehouse.ImportPrintData;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PoCommand;
import com.jumbo.wms.model.warehouse.SkuImperfectCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StoProCode;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.VMIBackOrder;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WhInfoTimeRef;
import com.jumbo.wms.model.warehouse.WhTransAreaNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrderCommand;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.utils.PropertyUtil;


/**
 * 
 * 所有打印查询数据封装类 bin.hu
 * 
 */
@Transactional
@Service("warehousePrintData")
public class WarehousePrintDataImpl extends BasePrintManagerImpl implements WarehousePrintData {

    private static final long serialVersionUID = -7617991512582099069L;

    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private SkuSizeConfigDao skuSizeConfigDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private HandOverListLineDao handOverListLineDao;
    @Autowired
    private HandOverListDao handOverListDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;
    @Autowired
    private GiftLineDao giftLineDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WhTransAreaNoDao transAreaNoDao;
    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;
    @Autowired
    private ImportPrintDataDao importPrintDataDao;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    private SkuImperfectDao skuImperfectDao;
    @Autowired
    private ImperfectStvDao imperfectStvDao;
    @Autowired
    private StoProCodeDao stoProCodeDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private RtwDieKingLineDao rtwDieKingLineDao;
    @Autowired
    private StaLfDao staLfDao;
    @Autowired
    private StaLineLfDao staLineLfDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private ChooseOptionDao coDao;

    @Override
    public Long getPickListMergeSta(Long plid, Long ouid) {
        Long mergeSta = pickingListDao.getPickListMergeSta(plid, ouid, new SingleColumnRowMapper<Long>(Long.class));
        return mergeSta;
    }

    @Override
    public Map<Long, PickingListObj> findPickingListByPlid(Long plid, Integer pickZoneId, Long ouid, String psize, String flag) {
        Map<Long, PickingListObj> mapList = null;
        if (flag != null && "all".equals(flag)) {
            mapList = pickingListDao.findPickingListByPlidNew(plid, pickZoneId, ouid, psize, new PickingListObjRowMapper());
        } else {

            mapList = pickingListDao.findPickingListByPlid(plid, pickZoneId, ouid, psize, new PickingListObjRowMapper());
        }
        Iterator<Long> iter = mapList.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            List<PickingListSubLineObj> list = mapList.get(key).getLines();
            Integer qty = 0;
            for (PickingListSubLineObj each : list) {
                qty += each.getQty();
            }
            mapList.get(key).setQuantity(qty);
        }
        return mapList;
    }

    @Override
    public Map<Long, PickingListObj> findPickingListByPlid1(Long plid, Integer pickZoneId, Long ouid, String psize, String flag) {
        Map<Long, PickingListObj> mapList = null;
        if (flag != null && "all".equals(flag)) {
            mapList = pickingListDao.findPickingListByPlid1New(plid, pickZoneId, ouid, psize, new PickingListObjRowMapper());
        } else {
            mapList = pickingListDao.findPickingListByPlid1(plid, pickZoneId, ouid, psize, new PickingListObjRowMapper());
        }

        Iterator<Long> iter = mapList.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            List<PickingListSubLineObj> list = mapList.get(key).getLines();
            Integer qty = 0;
            for (PickingListSubLineObj each : list) {
                qty += each.getQty();
            }
            mapList.get(key).setQuantity(qty);
        }
        return mapList;
    }

    @Override
    @Transactional(readOnly = true)
    public PickingList getPickingListById(Long id) throws Exception {
        PickingList plist = pickingListDao.getByPrimaryKey(id);
        PickingList p = new PickingList();
        PropertyUtil.copyProperties(plist, p);
        p.setCreator(null);
        p.setHandOverList(null);
        p.setOperator(null);
        p.setStaList(null);
        p.setWarehouse(null);
        return p;
    }

    @Override
    public SkuSizeConfig getSkuSizeConfigById(Long id) {
        return skuSizeConfigDao.getByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SkuCategories getSkuCategoriesById(Long id) throws Exception {
        SkuCategories skuCategories = skuCategoriesDao.getByPrimaryKey(id);
        SkuCategories s = new SkuCategories();
        PropertyUtil.copyProperties(skuCategories, s);
        s.setChildrenSkuCategoriesList(null);
        s.setSkuCategories(null);
        return s;
    }

    @Override
    public WhInfoTimeRef getFirstPrintDate(String slipCode, Integer billType, Integer nodeType) {
        return whInfoTimeRefDao.getFirstPrintDate(slipCode, billType, nodeType, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
    }

    @Override
    public void insertWhInfoTime(String slipCode, int billType, int nodeType, Long userId, Long whOuId) {
        whInfoTimeRefDao.insertWhInfoTime2(slipCode, billType, nodeType, userId, whOuId);
    }

    @Override
    @Transactional(readOnly = true)
    public OperationUnit getOperationUnitById(Long id) throws Exception {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(id);
        OperationUnit o = new OperationUnit();
        PropertyUtil.copyProperties(ou, o);
        o.setChildrenUnits(null);
        o.setOuType(null);
        o.setParentUnit(null);
        return o;
    }

    @Override
    public List<StvLineCommand> findPurchaseSkuInfo(Long stvid, Long ouid) {
        return stvLineDao.findPurchaseSkuInfo(stvid, ouid, new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
    }

    @Override
    public List<HandOverListLineCommand> findLineDetailByHoListId(Long holid) {
        return handOverListLineDao.findLineDetailByHoListId(holid, new BeanPropertyRowMapper<HandOverListLineCommand>(HandOverListLineCommand.class));
    }

    @Override
    public List<HandOverListLineCommand> findLineDetailByHoListIdAD(Long holid) {
        return handOverListLineDao.findLineDetailByHoListIdAD(holid, new BeanPropertyRowMapper<HandOverListLineCommand>(HandOverListLineCommand.class));
    }

    @Override
    @Transactional(readOnly = true)
    public HandOverList getHandOverListById(Long id) throws Exception {
        HandOverList hList = handOverListDao.getByPrimaryKey(id);
        HandOverList h = new HandOverList();
        h.setId(hList.getId());
        h.setBillCount(hList.getBillCount());
        h.setCode(hList.getCode());
        h.setCreateTime(hList.getCreateTime());
        h.setHandOverTime(hList.getHandOverTime());
        h.setLastModifyTime(hList.getLastModifyTime());
        h.setLpcode(hList.getLpcode());
        h.setPackageCount(hList.getPackageCount());
        h.setPartyAOperator(hList.getPartyAOperator());
        h.setPartyBOperator(hList.getPartyBOperator());
        h.setPaytyAMobile(hList.getPaytyAMobile());
        h.setPaytyBMobile(hList.getPaytyBMobile());
        h.setPaytyBPassPort(hList.getPaytyBPassPort());
        h.setSender(hList.getSender());
        h.setStatus(hList.getStatus());
        h.setTotalWeight(hList.getTotalWeight());
        return h;
    }

    @Override
    public Integer findTotalSkuCountByHoId(Long hoid) {
        return staLineDao.findTotalSkuCountByHoId(hoid, new SingleColumnRowMapper<Integer>(Integer.class));
    }

    @Override
    public String findExpNameByLpcode(String lpCode) {
        return staDeliveryInfoDao.findExpNameByLpcode(lpCode, new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    public InventoryOccupay findVmiReturnOccupyInventoryByStaId(Long staid, Long ouid) {
        return staDao.findVmiReturnOccupyInventoryByStaId(staid, ouid, new BeanPropertyRowMapperExt<InventoryOccupay>(InventoryOccupay.class));
    }

    @Override
    @Transactional(readOnly = true)
    public StockTransApplication getStaById(Long id) throws Exception {
        StockTransApplication stockTransApplication = staDao.getByPrimaryKey(id);
        StockTransApplication s = new StockTransApplication();
        PropertyUtil.copyProperties(stockTransApplication, s);
        s.setCreator(null);
        s.setOutboundOperator(null);
        s.setInboundOperator(null);
        s.setMainWarehouse(null);
        s.setAddiWarehouse(null);
        s.setMainStatus(null);
        s.setAddiStatus(null);
        s.setPickingList(null);
        s.setHoList(null);
        s.setGroupSta(null);
        s.setSkuCategoriesId(null);
        if (stockTransApplication.getStaDeliveryInfo() == null) {
            s.setStaDeliveryInfo(null); // deliveryInfo 为空时
        } else {
            s.getStaDeliveryInfo().setPackageInfos(null); // deliveryInfo 不为 空时
        }

        return s;
    }


    public PackageInfo findByPackageInfoByLpcode(Long id) {
        return packageInfoDao.getByPrimaryKey(id);
    }


    public List<PackageInfo> findByPackageInfoByStaId(Long id) {
        return packageInfoDao.getByStaId(id);
    }

    @Override
    public InventoryOccupay findInventoryOccupay(Long staid, Long ouid) {
        InventoryOccupay inventoryoccupay = null;
        // 判断是否有目标仓库
        StockTransApplication stockTransApplication = staDao.getByPrimaryKey(staid);
        if (stockTransApplication.getAddiWarehouse() == null) {
            // old
            inventoryoccupay = staDao.findOutOfCossStaNotFinishedListByStaId(staid, ouid, new BeanPropertyRowMapperExt<InventoryOccupay>(InventoryOccupay.class));
            inventoryoccupay.setTargetWarehouse("");
        } else {
            inventoryoccupay = staDao.findOutOfAddWhIdNotFinishedListByStaId(staid, ouid, new BeanPropertyRowMapperExt<InventoryOccupay>(InventoryOccupay.class));
        }
        return inventoryoccupay;
    }

    @Override
    public InventoryOccupay findInventoryOccLineList(Long staid, InventoryOccupay inventoryoccupay) {
        List<StvLine> stvline = this.findStvLineListByStaId(staid);
        List<InventoryOccLine> line = new ArrayList<InventoryOccLine>();
        for (StvLine stvl : stvline) {
            InventoryOccLine inv = new InventoryOccLine();
            inv.setDistrict(stvl.getDistrict().getName());
            inv.setLocation(stvl.getLocation().getCode());
            inv.setBarCode(stvl.getSku().getBarCode());
            inv.setJmCode(stvl.getSku().getJmCode());
            inv.setKeyProperty(stvl.getSku().getKeyProperties());
            inv.setSkuName(stvl.getSku().getName());
            inv.setQuantity(stvl.getQuantity());
            inv.setInvStatus(stvl.getInvStatus().getName());
            line.add(inv);
        }
        inventoryoccupay.setLines(line);
        return inventoryoccupay;
    }

    public List<StvLine> findStvLineListByStaId(Long staId) {
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(staId);
        return stvLineDao.findStvLineListByStvId(stv.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Sku getSkuById(Long id) throws Exception {
        Sku sku = skuDao.getByPrimaryKey(id);
        Sku s = new Sku();
        PropertyUtil.copyProperties(sku, s);
        s.setBrand(null);
        s.setPaperSku(null);
        s.setCustomer(null);
        return s;
    }

    @Override
    public List<StvLineCommand> findPoConfirmStvLineBySta(Long staid) {
        return stvLineDao.findPoConfirmStvLineBySta(staid, new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
    }

    @Override
    public PoCommand findPoInfo(Long staid) {
        return staDao.findPoInfo(staid, new BeanPropertyRowMapperExt<PoCommand>(PoCommand.class));
    }

    @Override
    public List<StaLineCommand> findInBoundStaLineForPrint(Long staId) {
        return staLineDao.findInBoundStaLineForPrint(staId, true, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
    }

    @Override
    public OutBoundSendInfo findOutBoundSendInfo(Long staid) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        OutBoundSendInfo outBoundSendInfo = null;
        if (sta.getType().getValue() == StockTransApplicationType.TRANSIT_CROSS.getValue()) {
            // 库间移动
            outBoundSendInfo = staDao.findOutOfCossOutBoundSendInfoByStaId(staid, new BeanPropertyRowMapperExt<OutBoundSendInfo>(OutBoundSendInfo.class));
        } else {
            outBoundSendInfo = staDao.findVmiReturnOutBoundSendInfoByStaId(staid, new BeanPropertyRowMapperExt<OutBoundSendInfo>(OutBoundSendInfo.class));
        }
        return outBoundSendInfo;
    }

    @Override
    public OutBoundSendInfo findOutBoundSendInfoLine(Long staid, OutBoundSendInfo outBoundSendInfo) {
        List<StaLine> stalines = staLineDao.findByStaId(staid);
        log.debug("========================= stalines size : {} =====================", stalines.size());
        List<OutBoundSendInfoLine> line = new ArrayList<OutBoundSendInfoLine>();
        int index = 1;
        for (StaLine staline : stalines) {
            OutBoundSendInfoLine sendInfo = new OutBoundSendInfoLine();
            sendInfo.setOrdinal(index++);
            sendInfo.setBarCode(staline.getSku().getBarCode());
            sendInfo.setJmskuCode(staline.getSku().getCode());
            sendInfo.setSkuName(staline.getSku().getName());
            sendInfo.setSupplyCode(staline.getSku().getSupplierCode());
            sendInfo.setInventoryStatus(staline.getInvStatus().getName());
            sendInfo.setQuantity(staline.getQuantity());
            line.add(sendInfo);
        }
        outBoundSendInfo.setLines(line);
        return outBoundSendInfo;
    }

    @Override
    public OutBoundSendInfo findGucciOutBoundSendInfoLine(Long staid, OutBoundSendInfo outBoundSendInfo) {
        List<Carton> cList = cartonDao.findCartonByStaId(staid);// 通过STA查询箱号数据
        if (cList != null && !cList.isEmpty()) {
            List<OutBoundSendInfoLine> line = new ArrayList<OutBoundSendInfoLine>();
            log.debug("========================= CartonList size : {} =====================", cList.size());
            int index = 1;
            for (Carton carton : cList) {
                List<CartonLine> clList = cartonDao.findCartonLineByCarId(carton.getId());// 查询箱号下的明细
                if (clList != null && !clList.isEmpty()) {
                    for (CartonLine cartonLine : clList) {
                        OutBoundSendInfoLine sendInfo = new OutBoundSendInfoLine();
                        sendInfo.setCartonCode(carton.getCode());
                        sendInfo.setOrdinal(index++);
                        sendInfo.setBarCode(cartonLine.getSku().getBarCode());
                        sendInfo.setJmskuCode(cartonLine.getSku().getCode());
                        sendInfo.setSkuName(cartonLine.getSku().getName());
                        sendInfo.setSupplyCode(cartonLine.getSku().getSupplierCode());
                        sendInfo.setInventoryStatus("良品");
                        sendInfo.setQuantity(cartonLine.getQty());
                        line.add(sendInfo);
                    }
                }
            }
            outBoundSendInfo.setLines(line);
            return outBoundSendInfo;
        }
        return null;
    }

    @Override
    public List<PickingListCommand> findPickingListByPickingId(Long plCmdId, Integer pickZoneId, Long warehouseOuid, String flag) {
        if (flag != null && "all".equals(flag)) {
            return pickingListDao.findPickingListByPickingIdNew(plCmdId, pickZoneId, warehouseOuid, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
        } else {
            return pickingListDao.findPickingListByPickingId(plCmdId, pickZoneId, warehouseOuid, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
        }
    }

    @Override
    public List<VMIBackOrder> findBackListByStaId(String staid) {
        return staLineDao.findBackListByStaId(staid, new BeanPropertyRowMapper<VMIBackOrder>(VMIBackOrder.class));
    }

    @Override
    public List<PickingListCommand> findPickingListByPickingId3(Long plCmdId, Integer pickZoneId, Long warehouseOuid, String flag) {
        return pickingListDao.findPickingListByPickingId3(plCmdId, pickZoneId, warehouseOuid, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    @Override
    public Map<Long, OutBoundPackageInfoObj> findPrintCartonDetailInfo2(Long cid) {
        return cartonDao.findPrintCartonDetailInfo2(cid, new OutBoundPackageInfoObjRowMapper());
    }

    @Override
    public Map<Long, OutBoundPackageInfoObj> findPrintCartonDetailInfo3(Long cid) {
        return cartonDao.findPrintCartonDetailInfo3(cid, new OutBoundPackageInfoObjRowMapper());
    }

    @Override
    public List<OutBoundPackingObj> findOutBoundPackageByStaid(Long staid) {
        return staDao.findOutBoundPackageByStaid(staid, new BeanPropertyRowMapperExt<OutBoundPackingObj>(OutBoundPackingObj.class));
    }

    @Override
    public List<StvLineCommand> findOutBoundPackageLineByStaid(Long staid) {
        return stvLineDao.findOutBoundPackageLineByStaid(staid, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
    }

    @Override
    public String findLocationCodeByid(Long id) {
        return warehouseLocationDao.findLocationCodeByid(id, new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    public String findDistrictCodeByid(Long id) {
        return warehouseDistrictDao.findDistrictCodeByid(id, new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseDistrict getWarehouseDistrictById(Long id) throws Exception {
        WarehouseDistrict warehouseDistrict = warehouseDistrictDao.getByPrimaryKey(id);
        WarehouseDistrict w = new WarehouseDistrict();
        PropertyUtil.copyProperties(warehouseDistrict, w);
        w.setLocations(null);
        w.setOu(null);
        return w;
    }

    @Override
    public List<String> findAllAvailLocationsByDistrictId(Long id) {
        return warehouseLocationDao.findAllAvailLocationsByDistrictId(id, new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    @Transactional(readOnly = true)
    public GiftLine getGiftLineById(Long id) throws Exception {
        GiftLine giftLine = giftLineDao.getByPrimaryKey(id);
        GiftLine g = new GiftLine();
        PropertyUtil.copyProperties(giftLine, g);
        StaLine staLine = giftLine.getStaLine();
        StaLine sl = new StaLine();
        Sku sku = staLine.getSku();
        Sku s = new Sku();
        s.setBarCode(sku.getBarCode());
        s.setExtensionCode1(sku.getExtensionCode1());
        s.setSkuSize(sku.getSkuSize());
        sl.setOwner(staLine.getOwner());
        sl.setSku(s);
        StockTransApplication sta = staLine.getSta();
        StockTransApplication sa = new StockTransApplication();
        sa.setCode(sta.getCode());
        sa.setRefSlipCode(sta.getRefSlipCode());
        sa.setSlipCode1(sta.getSlipCode1());
        sl.setSta(sa);
        g.setStaLine(sl);
        return g;
    }

    @Override
    public List<SpecialPackagingData> findBurberryPrintInfo(Long staid) {
        return pickingListDao.findBurberryPrintInfo(staid, new BeanPropertyRowMapper<SpecialPackagingData>(SpecialPackagingData.class));
    }

    @Override
    public int getCoachSpecialStaType(Long staid) {
        int type = 0;
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        if (StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType())) {
            type = 1;
        } else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
            type = 2;
        } else if (StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType())) {
            type = 3;
        } else {
            type = -1;
        }
        return type;
    }

    @Override
    public SalesTicketResult getSalesTicket(String code, Integer type) {
        return rmi4Wms.getSalesTicket(code, type);
    }

    @Override
    public List<StaDeliveryInfoCommand> findPrintExpressBillData(Long pickingListId) {
        return staDeliveryInfoDao.findPrintExpressBillData(false, pickingListId, null, new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
    }

    @Override
    public String findAllOptionListByOptionKey(String optionkey, String categoryCode) {
        return chooseOptionDao.findAllOptionListByOptionKey(optionkey, "transTimeType", new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    public Transportator findByCode(String lpCode) {
        return transportatorDao.findByCode(lpCode);
    }

    @Override
    @Transactional(readOnly = true)
    public Warehouse getByOuId(Long whOuId) throws Exception {
        Warehouse warehouse = warehouseDao.getByOuId(whOuId);
        Warehouse w = new Warehouse();
        PropertyUtil.copyProperties(warehouse, w);
        w.setOu(null);
        w.setCustomer(null);
        return w;
    }

    @Override
    public HashMap<String, List<WhTransAreaNo>> transAreaCache() {
        return TransManagerImpl.transAreaCache;
    }

    @Override
    public List<WhTransAreaNo> getTransAreaByLpcode(String lpcode) {
        return transAreaNoDao.getTransAreaByLpcode(lpcode, new BeanPropertyRowMapperExt<WhTransAreaNo>(WhTransAreaNo.class));
    }

    @Override
    public BigDecimal getInsurance(String code, BigDecimal amount) {
        return channelInsuranceManager.getInsurance(code, amount);
    }

    @Override
    public List<Long> findAllStaByPickingList(Long id) {
        return staDao.findAllStaByPickingList(id, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public List<StaDeliveryInfoCommand> findPrintExpressBillData(Boolean isOnlyParent, Long plid, Long staid) {
        return staDeliveryInfoDao.findPrintExpressBillData(isOnlyParent, null, staid, new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
    }

    @Override
    public List<StaDeliveryInfoCommand> findPrintExpressBillData2(Long plid, Long staid, String packId) {
        return staDeliveryInfoDao.findPrintExpressBillData2(null, staid, packId, new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
    }

    @Override
    public List<ImportPrintData> selectAllData() {
        return importPrintDataDao.selectAllData();
    }

    @Override
    public void deleteAll(List<ImportPrintData> listInfo) {
        importPrintDataDao.deleteAll(listInfo);
    }

    @Override
    public Long getMainWarehouseId(Long staid) {
        Long wid = null;
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        wid = sta.getMainWarehouse().getId();
        return wid;
    }

    @Override
    public List<StaDeliveryInfoCommand> printSingleVmiDelivery(Long staid, Long cartonId) {
        return staDeliveryInfoDao.printSingleVmiDelivery(staid, cartonId, new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
    }

    @Override
    public List<StaDeliveryInfoCommand> printCategeryAndQtyDeliveryByStaId(Long staId) {
        return staDeliveryInfoDao.printCategeryAndQtyDeliveryByStaId(staId, new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
    }

    @Override
    public WmsInvoiceOrder getWmsInvoiceOrderById(Long id) {
        WmsInvoiceOrder w = wmsInvoiceOrderDao.getByPrimaryKey(id);
        return w;
    }

    @Override
    public List<WmsInvoiceOrderCommand> findWmsInvoiceOrderBillData(Boolean isOnlyParent, String BatchNo, Long wioId) {
        return wmsInvoiceOrderDao.findWmsInvoiceOrderBillData(isOnlyParent, null, wioId, new BeanPropertyRowMapperExt<WmsInvoiceOrderCommand>(WmsInvoiceOrderCommand.class));
    }

    @Override
    public List<StaDeliveryInfoCommand> findWmsInvoiceOrderBillData(Long id) {
        return staDeliveryInfoDao.findWmsInvoiceOrderBillDataByInvoiceId(id, new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
    }

    @Override
    public SkuImperfectCommand findSkuImperfect(Long id) {
        return skuImperfectDao.findSkuImperfect(id, new BeanPropertyRowMapperExt<SkuImperfectCommand>(SkuImperfectCommand.class));
    }

    @Override
    public ImperfectStvCommand findSkuImperfectStv(Long id) {
        return imperfectStvDao.findSkuImperfectStv(id, new BeanPropertyRowMapperExt<ImperfectStvCommand>(ImperfectStvCommand.class));
    }

    @Override
    public PackingSummaryForNike findPackingSummaryForNike(Long staId, Long cartonId) {

        com.jumbo.wms.model.system.ChooseOption ch = chooseOptionDao.findByCategoryCodeAndKey("findPackingSummaryForNike", "1");
        if (ch != null && ch.getOptionValue() != null) {
            return cartonDao.findPackingSummaryForNike2(staId, cartonId, new BeanPropertyRowMapperExt<PackingSummaryForNike>(PackingSummaryForNike.class));
        } else {
            return cartonDao.findPackingSummaryForNike(staId, new BeanPropertyRowMapperExt<PackingSummaryForNike>(PackingSummaryForNike.class));

        }
    }

    @Override
    public Long findPackingCheckCount(Long staId) {
        return cartonDao.findPackingCheckCount(staId, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public Carton findCartonById(Long cartonId) {
        return cartonDao.getByPrimaryKey(cartonId);
    }

    public StockTransApplication queryStaById(Long id) {
        return staDao.findStaByid2(id, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
    }

    @Override
    public List<StoProCode> queryStoProCode() {
        return stoProCodeDao.queryStoProCode();
    }

    @Override
    public Integer findIsPrintPackageDetail(Long holid, Long ouid) {
        return handOverListDao.findIsPrintPackageDetail(holid, ouid, new SingleColumnRowMapper<Integer>(Integer.class));
    }

    @Override
    public Map<Long, ImportHGDEntryListObj> printImportMacaoHGDEntryList(Long staid) {
        Map<Long, ImportHGDEntryListObj> map = staDao.printImportMacaoHGDEntryList(staid, new ImportMacaoHGDEntryListRowMapper());
        return map;
    }

    @Override
    public String queryTotalCatrgories(String slipCode) {
        return staDeliveryInfoDao.queryTotalCatrgories(slipCode, new SingleColumnRowMapper<String>(String.class));
    }

    /**
     * 装箱清单防伪编码
     * 
     * @param skuMsg
     * @param owner
     * @return
     */
    public String greAntiFakeCode(String skuMsg, String key) {
        MD5Util md5Util = new MD5Util();
        String pws = md5Util.getMd5_16New(key);
        String encryptStr = AESGeneralUtil.encrypt(skuMsg, pws);
        /*
         * try { String hehe = AESGeneralUtil.decrypt(encryptStr, pws); System.out.println(hehe); }
         * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
         */
        return encryptStr;
    }

    @Override
    public List<VMIBackOrder> findDiekingLineListByDiekingId(Long id, Long staId) {
        List<VMIBackOrder> vboList = rtwDieKingLineDao.findDiekingLineListByDiekingId(id, new BeanPropertyRowMapper<VMIBackOrder>(VMIBackOrder.class));
        if (vboList != null && vboList.size() > 0) {
            for (VMIBackOrder vbo : vboList) {
                Sku sku = skuDao.getByCode(vbo.getSkucode());
                StaLine sl = staLineDao.findListPriceByStaIdAndSkuId(staId, sku.getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
                GiftLine gl = giftLineDao.getGiftLineByStaLineIdAndType(sl.getId(), 90, new BeanPropertyRowMapper<GiftLine>(GiftLine.class));
                if (gl != null) {
                    vbo.setVas(gl.getMemo());
                }
            }

        }
        return vboList;
    }

    @Override
    public OutBoundSendInfo findRetrnWhPickingInfo(Long staId) {
        return staDao.findReturnOutWhPickingInfoByStaId(staId, new BeanPropertyRowMapperExt<OutBoundSendInfo>(OutBoundSendInfo.class));
    }

    @Override
    public OutBoundSendInfo findRetrnWhPickingInfoLine(Long staId, OutBoundSendInfo outBoundSendInfo) {
        List<Carton> cList = cartonDao.findCartonByStaIdSort(staId);// 通过STA查询箱号数据
        if (cList != null && !cList.isEmpty()) {
            List<OutBoundSendInfoLine> line = new ArrayList<OutBoundSendInfoLine>();
            int index = 0;
            for (Carton carton : cList) {
                List<CartonLine> clList = cartonDao.findCartonLineByCarId(carton.getId());// 查询箱号下的明细
                Long totalQty = cartonDao.findCartonQtyById(carton.getId(), new SingleColumnRowMapper<Long>(Long.class));
                if (clList != null && !clList.isEmpty()) {
                    index++;
                    for (CartonLine cartonLine : clList) {
                        OutBoundSendInfoLine sendInfo = new OutBoundSendInfoLine();
                        sendInfo.setCartonCode(carton.getCode());
                        sendInfo.setOrdinal(index);
                        sendInfo.setSkuCode(cartonLine.getSku().getExtensionCode1());
                        sendInfo.setQuantity(cartonLine.getQty());
                        sendInfo.setOrderKey(outBoundSendInfo.getOrderKey());
                        sendInfo.setPplNo(outBoundSendInfo.getPplNo());
                        sendInfo.setTotalQty(totalQty);
                        line.add(sendInfo);
                    }
                }
            }
            //Collections.sort(line);
            outBoundSendInfo.setLines(line);
            
            ChooseOption co=coDao.findByCategoryCodeAndKey("NIKE_WHCODE_MAPPING", outBoundSendInfo.getWhCode());
            if(co!=null) {
            	outBoundSendInfo.setWhCode(co.getOptionValue());
            }
            if((outBoundSendInfo.getIsMoreWh()!=null&&outBoundSendInfo.getIsMoreWh())||"N".equals(outBoundSendInfo.getTransMethod())) {
            	outBoundSendInfo.setReturnCode(outBoundSendInfo.getWhCode()+"-"+outBoundSendInfo.getReturnCode());
            }
            
            return outBoundSendInfo;
        }
        return null;
    }

    /**
     * nike crw 箱标签
     * 
     * @param cartonId
     * @return
     */
    public StaLfCommand findNikeOutBoundLabel(Long cartonId) {
        StaLfCommand slc = staLfDao.findNikeOutBoundLabel(cartonId, new BeanPropertyRowMapperExt<StaLfCommand>(StaLfCommand.class));
        if (slc != null) {
            slc.setOrderDefine(getOrderDefine(slc.getNikePo(), slc.getVasCode()));

            String address = StringUtil.isEmpty(slc.getCompanyName()) ? "" : slc.getCompanyName() + "\r\n";
            address += StringUtil.isEmpty(slc.getAddress1()) ? "" : slc.getAddress1() + "\r\n";
            address += StringUtil.isEmpty(slc.getAddress2()) ? "" : slc.getAddress2() + "\r\n";
            address += StringUtil.isEmpty(slc.getAddress3()) ? "" : slc.getAddress3() + "\r\n";
            address += StringUtil.isEmpty(slc.getAddress4()) ? "" : slc.getAddress4() + "\r\n";

            slc.setAddress1(address);
            
            ChooseOption co=coDao.findByCategoryCodeAndKey("NIKE_WHCODE_MAPPING", slc.getWhCode());
            if(co!=null) {
            	slc.setWhCode(co.getOptionValue());
            }
            if((slc.getIsMoreWh()!=null&&slc.getIsMoreWh())||"N".equals(slc.getTransMethod())) {
            	slc.setPackList(slc.getWhCode()+"-"+slc.getPackList());
            }

        }
        return slc;
    }

    public String getOrderDefine(String nikePo, String vasCode) {
        String orderDefine = null;
        if (nikePo != null) {
            try {
                switch (nikePo.charAt(8)) {
                    case 'L':
                        orderDefine = "LI";
                        break;
                    case 'F':
                        orderDefine = "RP";
                        break;
                    case 'Q':
                        orderDefine = "L/QS";
                        break;
                    case 'N':
                        break;
                    case 'A':
                        orderDefine = "1111";
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {}
        }
        if (StringUtil.isEmpty(orderDefine) && vasCode != null) {
            if (vasCode.equals("LI")) {
                orderDefine = "LI";
            } else if (vasCode.equals("RP")) {
                orderDefine = "RP";
            }
        }
        return orderDefine;
    }

    /**
     * nike crw POD单
     * 
     * @param staId
     * @return
     */
    public StaLfCommand printNikeCrwPod(Long staId, Long cartonId) {
        if (staId == null || staId == 0) {
            Carton c = cartonDao.getByPrimaryKey(cartonId);
            if (c == null) {
                return new StaLfCommand();
            }
            staId = c.getSta().getId();
        }
        StaLfCommand slc = staLfDao.findNikeCrwPod(staId, new BeanPropertyRowMapperExt<StaLfCommand>(StaLfCommand.class));

        if (slc != null && slc.getDivisionCode() != null) {
            if (slc.getDivisionCode().equals("20")) {

            } else if (slc.getDivisionCode().equals("10")) {
                slc.setApp(slc.getFw());
                slc.setFw(null);
            } else if (slc.getDivisionCode().equals("30")) {
                slc.setEq(slc.getFw());
                slc.setFw(null);
            }
        }
        if (slc != null) {
            if (StringUtil.isEmpty(slc.getCrd())) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(slc.getPlanDateTime());
                calendar.add(Calendar.DATE, Integer.valueOf(slc.getTransportPra()));

                slc.setUnloadingTime(df.format(calendar.getTime()));
            } else {
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                Date crdDate;
                try {
                    crdDate = df.parse(slc.getCrd());
                    df = new SimpleDateFormat("yyyy-MM-dd");
                    slc.setUnloadingTime(df.format(crdDate));
                } catch (ParseException e) {
                    log.error("Parse crdDate Exception " + slc.getCrd(), e);

                }
            }
        }
        
        ChooseOption co=coDao.findByCategoryCodeAndKey("NIKE_WHCODE_MAPPING", slc.getWhCode());
        if(co!=null) {
        	slc.setWhCode(co.getOptionValue());
        }
        if((slc.getIsMoreWh()!=null&&slc.getIsMoreWh())||"N".equals(slc.getTransMethod())) {
        	slc.setPackList(slc.getWhCode()+"-"+slc.getPackList());
        }


        return slc;
    }

    @Override
    public List<OutBoundSendInfo> findBoxLabelByCartonId(Long cartonId) {
        Carton carton = cartonDao.getByPrimaryKey(cartonId);
        List<OutBoundSendInfo> outList = new ArrayList<OutBoundSendInfo>();
        OutBoundSendInfo outInfo = new OutBoundSendInfo();
        List<OutBoundSendInfoLine> outInfoLineList = new ArrayList<OutBoundSendInfoLine>();
        List<CartonLine> clList = cartonDao.findCartonLineByCarId(carton.getId());
        StockTransApplication sta =staDao.getByPrimaryKey(carton.getSta().getId());
        OperationUnit ou=ouDao.getByPrimaryKey(sta.getMainWarehouse().getId());
        StaLf sl=staLfDao.getStaLfByStaId(sta.getId());
        outInfo.setCaseNumber(carton.getSta().getSlipCode2());
        for (CartonLine cartonLine : clList) {
            OutBoundSendInfoLine sendInfo = new OutBoundSendInfoLine();
            StaLineLf lfLine = staLineLfDao.getStaLineLfBystaIdAndskuId(carton.getSta().getId(), cartonLine.getSku().getId(), new BeanPropertyRowMapperExt<StaLineLf>(StaLineLf.class));
            sendInfo.setSupplyCode(cartonLine.getSku().getSupplierCode());
            sendInfo.setSkuCode(cartonLine.getSku().getExtensionCode1());
            sendInfo.setQuantity(cartonLine.getQty());
            sendInfo.setVas(lfLine.getVas());
            outInfoLineList.add(sendInfo);
        }
        ChooseOption co=coDao.findByCategoryCodeAndKey("NIKE_WHCODE_MAPPING", ou.getCode());
        if(co!=null) {
        	outInfo.setWhCode(co.getOptionValue());
        }else {
        	outInfo.setWhCode(ou.getCode());
        }
        if((sl.getIsMoreWh()!=null&&sl.getIsMoreWh())||"N".equals(sl.getTransMethod())) {
        	outInfo.setCaseNumber(outInfo.getWhCode()+"-"+outInfo.getCaseNumber());
        }
        
        Collections.sort(outInfoLineList);
        outInfo.setLines(outInfoLineList);
        outList.add(outInfo);
        return outList;
    }
}
