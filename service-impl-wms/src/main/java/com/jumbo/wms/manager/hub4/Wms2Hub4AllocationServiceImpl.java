package com.jumbo.wms.manager.hub4;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.lf.StaLfDao;
import com.jumbo.dao.lf.StaLineLfDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
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
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.lf.StaLf;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.lf.StaLineLf;
import com.jumbo.wms.model.warehouse.FreightMode;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
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
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.wmsInterface.WmsOutBound;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundDeliveryInfo;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundLine;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundVasLine;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.impl.DefaultReadStatus;
import net.sf.json.JSONObject;

@Service("wms2Hub4AllocationService")
@Transactional
public class Wms2Hub4AllocationServiceImpl extends BaseManagerImpl implements Wms2Hub4AllocationService {

    private static final long serialVersionUID = -5970160047795204996L;

    private static final Logger log = LoggerFactory.getLogger(Wms2Hub4AllocationServiceImpl.class);
    @Resource
    private ApplicationContext applicationContext;
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
    private StaLfDao staLfDao;
    @Autowired
    private StaLineLfDao staLineLfDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private GiftLineDao giftLineDao;

    /**
     * 创建调拨单
     */
    @Override
    public void createAllocationSta(StockTransApplication sta, List<StaLine> staLineList, StaDeliveryInfo sd, Map<StaInvoice, List<StaInvoiceLine>> map, WmsResponse re, StaLfCommand sc, Map<String, List<WmsOutBoundVasLine>> vasMap) {
        try {
            createOutBoundSta(sta, staLineList, sd, map, vasMap);
            staDao.flush();
        } catch (Exception e) {
            log.error("create sta error...", e);
            throw new BusinessException(WmsResponse.SYS_EXCEPTION, "创单失败！");
        }
        try {
            createStv(sta, re, 2, false);

        } catch (BusinessException e) {
            log.error("create stv error...", e);
            throw new BusinessException(WmsResponse.OCCUPATION_FAILURE, e.getMessage());
        } catch (Exception e) {
            log.error("create stv error...", e);
            throw new BusinessException(WmsResponse.OCCUPATION_FAILURE, e.getMessage());
        }
        try {
            if (sc != null) {
                newStaLf(sta, sc, vasMap);
            }
        } catch (Exception e) {
            log.error("create crw error...", e);
            throw new BusinessException(WmsResponse.SYS_EXCEPTION, "创建CRW单据失败");
        }
        // 再次校验作业单是否重复创建
        List<StockTransApplication> staList = staDao.findBySlipCodeOuIdType(sta.getRefSlipCode(), sta.getMainWarehouse().getId(), sta.getType());
        if (staList.size() > 1) {
            for (StockTransApplication s : staList) {
                if (s.getStatus() != StockTransApplicationStatus.CANCELED && !s.getCode().equals(sta.getCode())) {
                    throw new BusinessException(WmsResponse.DATA_TRANSMISSION, "单据重复创建");
                }
            }
        }
    }

    public void newStaLf(StockTransApplication sta, StaLfCommand staLfCommand, Map<String, List<WmsOutBoundVasLine>> vasMap) throws Exception {
        StaLf staLf = new StaLf();
        staLf.setAddress1(staLfCommand.getAddress1());
        staLf.setAddress2(staLfCommand.getAddress2());
        staLf.setAddress3(staLfCommand.getAddress3());
        staLf.setAddress4(staLfCommand.getAddress4());
        staLf.setCity(staLfCommand.getCity());
        staLf.setCompanyName(staLfCommand.getCompanyName());
        staLf.setCrd(null);// crd时间
        staLf.setDivisionCode(staLfCommand.getDivisionCode());
        if ("10".equals(staLfCommand.getDivisionCode())) {
            staLf.setDivisionCodeTranslation("服装");
        } else if ("20".equals(staLfCommand.getDivisionCode())) {
            staLf.setDivisionCodeTranslation("鞋");
        } else if ("30".equals(staLfCommand.getDivisionCode())) {
            staLf.setDivisionCodeTranslation("装备");
        }
        staLf.setNfsStoreCode(staLfCommand.getNfsStoreCode());
        staLf.setNikePo(staLfCommand.getNikePo());
        staLf.setOrderType(staLfCommand.getOrderType());
        staLf.setOuId(sta.getMainWarehouse().getId());
        staLf.setPackSlipNo(staLfCommand.getPackSlipNo());
        staLf.setStaId(sta.getId());
        staLf.setTransportMode(staLfCommand.getTransportMode());
        staLf.setTransportPra(staLfCommand.getTransportPra());
        staLf.setVasCode(staLfCommand.getVasCode());
        staLf.setZip(staLfCommand.getZip());
        staLf.setIsMoreWh(staLfCommand.getIsMoreWh());
        staLf.setTransMethod(staLfCommand.getTransMethod());
        if (staLfCommand.getPlantime() != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(staLfCommand.getPlantime());
            staLf.setCrd(StringUtil.getLfCrd(date, Integer.valueOf(staLfCommand.getTransportPra()), staLfCommand.getNikePo()));
        }
        staLfDao.save(staLf);
        List<StaLineCommand> staLineList = staLineDao.findStaLineByOrderCode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        for (StaLineCommand staLineCommand : staLineList) {
            String vas = null;
            if (!StringUtil.isEmpty(staLineCommand.getExtMemo())) {
                String memo = staLineCommand.getExtMemo();
                JSONObject jsonObject = JSONObject.fromObject(memo);
                // StaLineLf sll = com.baozun.utilities.json.JsonUtil.readValue(memo,
                // StaLineLf.class);
                if (jsonObject != null) {
                    // vas = sll.getVas();
                    vas = jsonObject.getString("vas");
                }
            }
            StaLineLf staLineLf = new StaLineLf();
            staLineLf.setOuId(sta.getMainWarehouse().getId());
            staLineLf.setSkuId(staLineCommand.getSkuId());
            staLineLf.setStaLineId(staLineCommand.getId());

            if (StringUtil.isEmpty(vas)) {
                List<WmsOutBoundVasLine> wibvlList = vasMap.get(sta.getRefSlipCode() + "-" + staLineCommand.getSkuId());
                for (WmsOutBoundVasLine wobvl : wibvlList) {
                    if (StringUtil.isEmpty(vas)) {
                        vas = wobvl.getMemo();
                    } else {
                        vas = vas + "," + wobvl.getMemo();
                    }
                }

            }

            staLineLf.setVas(vas);
            staLineLf.setStaId(sta.getId());
            staLineLfDao.save(staLineLf);
        }
    };

    public String crwOrderCreate(List<WmsOutBound> obList) {
        for (WmsOutBound ob : obList) {
            String s = crwOrder(ob);
            if (!StringUtil.isEmpty(s)) {
                if ("success".equals(s)) {

                } else {
                    // 回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return s + "_-_" + ob.getExtCode();
                }
            } else {
                // 回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "";
            }
        }
        return "success";
    }

    /**
     * NIKEcrw单据
     * 
     * @param ob
     * @throws Exception
     */
    public String crwOrder(WmsOutBound ob) {
        if (StringUtil.isEmpty(ob.getOwner())) {
            return "";
        }
        BiChannel bi = channelDao.getByCode(ob.getOwner());
        if (bi == null) {
            return "";
        }
        if (bi.getIsCrw() == null || !bi.getIsCrw()) {
            return "";
        }
        try {


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

            // StaLfCommand staLfCommand = com.baozun.utilities.json.JsonUtil.readValue(memo,
            // StaLfCommand.class);
            OperationUnit ou = operationUnitDao.getByCode(ob.getWhCode());
            if (ou == null) {
                return "仓库不存在！";
            }
            OperationUnit ou1 = operationUnitDao.getByPrimaryKey(ou.getParentUnit().getId());
            // 验证作业单

            StockTransApplication returnSta = staDao.getByCode1(ob.getExtCode(), ou.getId());
            if (null != returnSta) {
                return "单据重复推送！";
            }

            StockTransApplication sta = new StockTransApplication();
            sta.setDataSource(ob.getDataSource());
            sta.setRefSlipCode(ob.getExtCode());// slipcode
            sta.setSlipCode1(staLfCommand.getSlipcode1());// slipcode1
            sta.setSlipCode2(staLfCommand.getSlipcode2());// slipcode2
            sta.setType(StockTransApplicationType.valueOf(101));// 退大仓
            sta.setFreightMode(FreightMode.valueOf(10));// 上门自取
            if (staLfCommand.getPlantime() != null) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = df.parse(staLfCommand.getPlantime());
                sta.setPlanOutboundTime(date);// 计划发货时间
            }
            WmsOutBoundDeliveryInfo di = ob.getWmsOutBoundDeliveryInfo();
            StaDeliveryInfo stadelivery = new StaDeliveryInfo();
            stadelivery.setProvince(di.getProvince());
            stadelivery.setCity(di.getCity());
            staLfCommand.setCity(di.getCity());
            stadelivery.setDistrict(di.getDistrict());
            stadelivery.setAddress(staLfCommand.getAddress1() + "," + staLfCommand.getAddress2() + "," + staLfCommand.getAddress3() + "," + staLfCommand.getAddress4());
            stadelivery.setReceiver(di.getReceiver());
            stadelivery.setTelephone(di.getTelephone());
            stadelivery.setMobile(di.getMoblie());
            stadelivery.setLpCode(null);
            stadelivery.setCountry(di.getCountry());

            // 封装
            List<StaLineCommand> stalinecmds = new ArrayList<StaLineCommand>();
            List<WmsOutBoundLine> wobLineS = ob.getWmsOutBoundLines();
            for (WmsOutBoundLine wobl : wobLineS) {
                // String[] codes = sku.getKey().split(";");
                StaLineCommand staLineCommand = new StaLineCommand();
                staLineCommand.setSkuCode(wobl.getUpc());
                staLineCommand.setQuantity(wobl.getQty());
                staLineCommand.setIntInvstatusName("良品");
                staLineCommand.setExtMemo(wobl.getExtMemo());
                stalinecmds.add(staLineCommand);
            }
            String type = "SKU条码";// nikeCRW 定制基于upc（barcode）
            // 基于店铺进行店铺退仓操作line
            ReadStatus rs = new DefaultReadStatus();
            rs = excelReadManager.creStaForVMIReturnEsToLine(stalinecmds, rs, type, bi, sta, stadelivery, null, null, null, ou.getId(), ou1.getParentUnit().getId(), null, null, false, false, true, staLfCommand, null);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                String list = "生成单据异常：";
                for (Exception ex : rs.getExceptions()) {
                    if (ex instanceof BusinessException) {
                        BusinessException be = (BusinessException) ex;
                        // list += be.getErrorCode() + "-" + be.getArgs() + be.getMessage();
                        list += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    }
                }
                return list;
            }
        } catch (Exception e) {
            log.error("crwOrder", e);
            return "系统异常：" + e.getMessage();
        }

        return "success";
    }

    /**
     * 创建sta
     * 
     * @param sta
     * @param lines
     * @param sd
     * @param map
     */
    private void createOutBoundSta(StockTransApplication sta, List<StaLine> lines, StaDeliveryInfo sd, Map<StaInvoice, List<StaInvoiceLine>> map, Map<String, List<WmsOutBoundVasLine>> vasMap) {
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        StockTransApplication st = staDao.save(sta);
        sd.setId(st.getId());
        deliveryDao.save(sd);
        Map<String, StaLine> maps = new HashMap<String, StaLine>();
        for (StaLine staLine : lines) {
            staLine.setSta(st);
            // 合并重复行
            if (maps.containsKey(staLine.getSku().getId() + "" + staLine.getInvStatus().getId())) {
                StaLine staLines = maps.get(staLine.getSku().getId());
                staLines.setQuantity(staLine.getQuantity() + staLines.getQuantity());
                staLineDao.save(staLines);
                continue;
            }
            staLineDao.save(staLine);
            // 增加增值服务
            List<WmsOutBoundVasLine> wibvlList = vasMap.get(st.getRefSlipCode() + "-" + staLine.getSku().getId());
            if (wibvlList != null && wibvlList.size() > 0) {
                for (WmsOutBoundVasLine wibvl : wibvlList) {
                    GiftLine gl = new GiftLine();
                    gl.setMemo(wibvl.getMemo());
                    // gl.setSanCardCode(wibvl.getCardCode());
                    gl.setStaLine(staLine);
                    gl.setType(GiftType.HUB4_VAS_GIFT);
                    giftLineDao.save(gl);
                }

            }
            maps.put(staLine.getSku().getId() + "" + staLine.getInvStatus().getId(), staLine);
        }
        for (Map.Entry<StaInvoice, List<StaInvoiceLine>> entry : map.entrySet()) {
            StaInvoice invoice = entry.getKey();
            List<StaInvoiceLine> invoiceList = entry.getValue();
            invoice.setSta(st);
            StaInvoice ic = invoiceDao.save(invoice);
            for (StaInvoiceLine staInvoiceLine : invoiceList) {
                staInvoiceLine.setStaInvoice(ic);
                invoiceLineDao.save(staInvoiceLine);
            }
        }
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    /**
     * 创建stv
     * 
     * @param sta
     * @param rs
     * @param direction
     * @param isPartial
     * @return
     */
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
        Long cmpOuid = ou.getParentUnit().getParentUnit().getId();
        ReadStatus rs1 = new DefaultReadStatus();
        rs1.setStatus(ReadStatus.STATUS_SUCCESS);
        rs1 = excelReadManager.vmiReturnValidate(rs1, stalinecmds, staLineList, invmap, locationmap, cmpOuid, sta.getMainWarehouse().getId(), "SKU编码", shop.getCode(), transactionType, sta, shop);
        if (rs1.getStatus() != ReadStatus.STATUS_SUCCESS) {
            if (rs1.getExceptions() != null && rs1.getExceptions().size() > 0) {
                String errorMsg = "";
                for (Exception ex : rs1.getExceptions()) {
                    if (ex instanceof BusinessException) {
                        BusinessException bex = (BusinessException) ex;
                        String msg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs(), Locale.SIMPLIFIED_CHINESE);
                        errorMsg += msg + ",";
                    }
                }
                log.error(errorMsg);
                throw new BusinessException(errorMsg);
            }
            throw new BusinessException();
        }
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
        // Long skuQty = 0L;
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
        // sta.setSkuQty(skuQty);
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

    /**
     * 创建非销售类入库单
     */
    @Override
    public void createInboundSta(Map<StockTransApplication, Map<String, StaLine>> sta) {
        try {
            for (StockTransApplication s : sta.keySet()) {
                staDao.save(s);
                Map<String, StaLine> line = sta.get(s);
                for (String l : line.keySet()) {
                    StaLine staLine = line.get(l);
                    staLine.setSta(s);
                    staLineDao.save(staLine);
                }
                Warehouse warehouse = warehouseDao.getByOuId(s.getMainWarehouse().getId());
                if (warehouse != null && StringUtils.hasLength(warehouse.getVmiSource())) {
                    wareHouseManager.msgInorder(s, warehouse);
                }
            }

        } catch (Exception e) {
            log.error("createInboundSta sta error..." + e);
            throw new BusinessException(WmsResponse.SYS_EXCEPTION);
        }

    }

}
