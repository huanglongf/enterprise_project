package com.jumbo.wms.manager.vmi.gucciData;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.support.excel.ReadStatus;
import loxia.support.excel.impl.DefaultReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoLineDao;
import com.jumbo.dao.vmi.gucciData.GucciVMIInFeedbackDao;
import com.jumbo.dao.vmi.gucciData.GucciVMIInFeedbackLineDao;
import com.jumbo.dao.vmi.gucciData.GucciVMIRtnFBDao;
import com.jumbo.dao.vmi.gucciData.GucciVMIRtnFBLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CartonDao;
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
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFeedback;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFeedbackLine;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFB;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFBLine;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLine;
import com.jumbo.wms.model.warehouse.FreightMode;
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

/**
 * Gucci
 * 
 */
@Transactional
@Service("gucciManager")
public class GucciManagerImpl implements GucciManager {
    private static final long serialVersionUID = -2686259863533120249L;
    protected static final Logger log = LoggerFactory.getLogger(GucciManagerImpl.class);
    private static final String categoryCode = "SYS_EMAIL";

    @Resource
    private ApplicationContext applicationContext;
    private EventObserver eventObserver;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    // @Autowired
    // private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WareHouseManager wareHouseManager;
    // @Autowired
    // private GucciVMIInstructionDao gucciVMIInstructionDao;
    // @Autowired
    // private GucciVMIInstructionLineDao gucciVMIInstructionLineDao;
    @Autowired
    private GucciVMIInFeedbackDao gucciVMIInFeedbackDao;
    @Autowired
    private GucciVMIInFeedbackLineDao gucciVMIInFeedbackLineDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private VmiRtoDao vmiRtoDao;
    @Autowired
    private VmiRtoLineDao vmiRtoLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private GucciVMIRtnFBDao gucciVMIRtnFBDao;
    @Autowired
    private GucciVMIRtnFBLineDao gucciVMIRtnFBLineDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private VmiAsnDao vmiAsnDao;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named eventObserver");
        }
    }

    /**
     * 根据入库指令JDADocumentNo生成入库单
     */
    public void generateStaByJDADocumentNo(String JDADocumentNo) {
        // GucciVMIInstruction vmiIn = gucciVMIInstructionDao.findByJDADocumentNo(JDADocumentNo);
        // // 防止重复创单
        // if (vmiIn.getStaId() != null) {
        // log.error("=========THIS GUCCI VMI INSTRUCTION {} HAS CREATE STA!===========", new
        // Object[] {JDADocumentNo});
        // throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
        // }
        // BiChannel shop = companyShopDao.getByVmiCode(Constants.GUCCI_BRAND_VMI_CODE);
        // if (shop == null) {
        // log.debug("=========SHOP {} NOT FOUNT ===========", new Object[]
        // {Constants.GUCCI_BRAND_VMI_CODE});
        // throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        // }
        // wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        // OperationUnit ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
        // if (ou == null) {
        // log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[]
        // {Constants.GUCCI_BRAND_VMI_CODE});
        // throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        // }
        // Long companyId = null;
        // companyId = ou.getParentUnit().getParentUnit().getId();
        // InventoryStatus sts = inventoryStatusDao.findInvStatusForSale(companyId);
        // if (sts == null) {
        // throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
        // }
        // // 创建sta
        // StockTransApplication sta = new StockTransApplication();
        // sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        // sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new
        // SingleColumnRowMapper<BigDecimal>()).longValue());
        // sta.setCreateTime(new Date());
        // sta.setRefSlipCode(JDADocumentNo);// 入库指令
        // sta.setLastModifyTime(new Date());
        // sta.setStatus(StockTransApplicationStatus.CREATED);
        // // 订单状态与账号关联
        // whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(),
        // WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null,
        // ou.getId());
        // sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        // sta.setOwner(shop.getCode());
        // sta.setMainWarehouse(ou);
        // sta.setMainStatus(sts);
        // sta.setSystemKey(Constants.GUCCI_SYSTEM_KEY);
        // staDao.save(sta);
        // log.debug("==========create======sta code : " + sta.getCode());
        // // 创建sta line
        // List<GucciVMIInstructionLine> lines =
        // gucciVMIInstructionLineDao.findLinesByInstructionId(vmiIn.getId());
        // if (lines == null || lines.size() == 0) {
        // // 无创建作业单明细数据
        // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        // }
        // Long skuQty = 0l;
        // boolean isNoSkuError = false;
        // for (GucciVMIInstructionLine line : lines) {
        // // gucci sku 唯一编码
        // String gucciSkuCode = line.getSkuNumber();
        // // 创建商品
        // Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(gucciSkuCode,
        // shop.getCustomer().getId(), shop.getId());
        // if (sku == null) {
        // baseinfoManager.sendMsgToOmsCreateSku(gucciSkuCode, shop.getVmiCode());
        // isNoSkuError = true;
        // continue;
        // }
        // StaLine staLine = new StaLine();
        // staLine.setQuantity(line.getQtyToBeReceived());
        // staLine.setSku(sku);
        // staLine.setCompleteQuantity(0L);// 已执行数量
        // staLine.setSta(sta);
        // staLine.setOwner(shop.getCode());
        // staLine.setInvStatus(sts);
        // staLine.setOrderLineNo(line.getRowNumberDetail());
        // staLineDao.save(staLine);
        // skuQty += staLine.getQuantity();
        // }
        // sta.setSkuQty(skuQty);
        // staDao.flush();
        // staDao.updateSkuQtyById(sta.getId());
        // log.debug("===============sta {} create success ================", new Object[]
        // {sta.getCode()});
        // if (isNoSkuError) {
        // throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        // }
        // try {
        // eventObserver.onEvent(new TransactionalEvent(sta));
        // } catch (BusinessException e) {
        // throw e;
        // }
        // vmiIn.setStaId(sta.getId());
        // vmiIn.setStatus(1);
        // gucciVMIInstructionDao.save(vmiIn);
    }

    /**
     * 生成入库反馈数据
     */
    @Override
    public void generateInBoundFeedbackDate(StockTransApplication sta) {
        try {
            BiChannel shop = companyShopDao.getByVmiCode(Constants.GUCCI_BRAND_VMI_CODE);
            // 按箱收货（或者按单）
            if (shop.getAsnOrderType().equals(AsnOrderType.TYPETWO) || shop.getAsnOrderType().equals(AsnOrderType.TYPEONE)) {
                String JDADocumentNo = sta.getSlipCode1();
                if (JDADocumentNo == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                // 判断该入库指令下的所有作业单是否已全部完成
                List<StockTransApplication> staList = staDao.findBySlipCode1Inbound(JDADocumentNo);
                StringBuffer sb = new StringBuffer();
                Boolean result = judgeIfAllStaFinished(staList, sb);
                // 只要入库指令下有一单未完成，便不生成反馈数据
                if (result) return;
                // 入库指令如果存在部分商品作业单未创建，此时也不反馈
                Integer num = vmiAsnDao.findVmiAsnHasFinishedCount(Constants.GUCCI_BRAND_VMI_CODE, sta.getSlipCode1(), new SingleColumnRowMapper<Integer>(Integer.class));
                if (num != null && num.intValue() == 0) {
                    return;
                }
                generateHeadAndDetaiData(sta, JDADocumentNo, staList, sb);
            }
            // 按单收货
            // if (shop.getAsnOrderType().equals(AsnOrderType.TYPEONE)) {
            // String JDADocumentNo = sta.getRefSlipCode();
            // if (JDADocumentNo == null) {
            // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            // }
            // List<StockTransApplication> staList = staDao.findBySlipCodeStatus(JDADocumentNo);
            // StringBuffer sb = new StringBuffer();
            // Boolean result = judgeIfAllStaFinished(staList, sb);
            // if (result) return;
            // generateHeadAndDetaiData(sta, JDADocumentNo, staList, sb);
            // }
        } catch (Exception e) {
            log.error("Gucci生成入库反馈数据异常！JDADocumentNo：" + sta.getSlipCode1(), e);
        }
    }

    /**
     * 判断该指令下的作业单是否都已经是完成状态
     * 
     * @param staList
     * @param sb
     * @return
     */
    public Boolean judgeIfAllStaFinished(List<StockTransApplication> staList, StringBuffer sb) {
        Boolean flag = false;
        if (staList != null && !staList.isEmpty()) {
            for (StockTransApplication s : staList) {
                if (!s.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                    flag = true;
                    break;
                }
                sb.append(s.getCode() + ";");
            }
        }
        return flag;
    }

    /**
     * 生成反馈头和反馈明细数据
     * 
     * @param sta
     * @param JDADocumentNo
     * @param staList
     * @param sb
     * @throws JSONException
     */
    public void generateHeadAndDetaiData(StockTransApplication sta, String JDADocumentNo, List<StockTransApplication> staList, StringBuffer sb) throws JSONException {
        // 反馈头信息
        GucciVMIInFeedback gb = new GucciVMIInFeedback();
        gb.setJDADocumentNumber(sta.getSlipCode1());
        gb.setStaCode(sb.toString().substring(0, sb.toString().lastIndexOf(";")));
        gb.setReceiptDate(new Date());
        JSONObject inObj = new loxia.support.json.JSONObject(sta.getExtMemo());
        gb.setJDADocumentType(inObj.get("type") == null ? null : inObj.get("type").toString());
        String warehouseCode = inObj.get("warehouseCode") == null ? null : inObj.get("warehouseCode").toString();
        gb.setJDAWarehouseCode(warehouseCode);
        String vendorNumber = inObj.get("vendorNumber") == null ? null : inObj.get("vendorNumber").toString();
        gb.setVendorNumber(vendorNumber);
        String brandCode = inObj.get("brandCode") == null ? null : inObj.get("brandCode").toString();
        gb.setBrandCode(brandCode);
        gb.setJDADocumentDate(sta.getArriveTime());
        gb.setPhysicalRecDate(new Date());
        gb.setType(0);// 普通入库
        gb.setStatus(0);
        gucciVMIInFeedbackDao.save(gb);
        // 实际收货统计 key：sku的extCode2;value：数量
        Map<String, Long> skuQtysMap = new HashMap<String, Long>();
        if (staList != null && !staList.isEmpty()) {
            for (StockTransApplication s : staList) {
                List<StaLine> lines = staLineDao.findByStaId(s.getId());
                for (StaLine staLine : lines) {
                    String key = staLine.getSku().getExtensionCode2();
                    if (skuQtysMap.get(key) == null) {
                        skuQtysMap.put(key, staLine.getCompleteQuantity());
                    } else {
                        Long qty = skuQtysMap.get(key);
                        skuQtysMap.put(key, qty + staLine.getCompleteQuantity());
                    }
                }
            }
        }
        log.info(sta.getSlipCode1() + ":" + skuQtysMap.toString());
        // 反馈明细行 逐行扣减
        List<VmiAsnLineCommand> asnLines = vmiAsnLineDao.findVmiAsnLineListIgnoreStatus(JDADocumentNo, null, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
        if (asnLines != null && !asnLines.isEmpty()) {
            // 存放合并前数据
            Map<String, GucciVMIInFeedbackLine> temp = new HashMap<String, GucciVMIInFeedbackLine>();
            for (VmiAsnLineCommand asnLine : asnLines) {
                GucciVMIInFeedbackLine l = new GucciVMIInFeedbackLine();
                l.setGucciVMIInFeedback(gb);
                l.setBrandCode(brandCode);
                l.setJDADocumentNumber(JDADocumentNo);
                l.setJDAWarehouseCode(warehouseCode);
                l.setVendorNumber(vendorNumber);
                l.setInvStatus("良品");// 默认良品
                JSONObject obj = new JSONObject(asnLine.getExtMemo());
                String lineNo = obj.get("lineNo") == null ? null : obj.get("lineNo").toString();
                String JDAPoNumber = obj.get("JDAPoNumber") == null ? null : obj.get("JDAPoNumber").toString();
                l.setRowNumberDetail(lineNo);// 入库单行号
                l.setJDAPoNumber(JDAPoNumber);
                l.setSkuNumber(asnLine.getUpc());
                // 计划收获量
                l.setQtyToBeReceived(asnLine.getQty());
                // 实际收货量
                Long totalQty = skuQtysMap.get(asnLine.getUpc());
                if (totalQty == null) {
                    totalQty = 0l;
                }
                if (totalQty - asnLine.getQty() >= 0) {
                    l.setQtyReceived(asnLine.getQty());
                    skuQtysMap.put(asnLine.getUpc(), totalQty - asnLine.getQty());
                } else {
                    l.setQtyReceived(totalQty);
                    skuQtysMap.put(asnLine.getUpc(), 0l);
                }
                // gucciVMIInFeedbackLineDao.save(l);
                temp.put(l.getRowNumberDetail(), l);
            }
            // 存放合并后数据， 按照JDA_dumentNO,PONumber,SkuUPC合并重建反馈数据
            Map<String, GucciVMIInFeedbackLine> data = new HashMap<String, GucciVMIInFeedbackLine>();
            for (Map.Entry<String, GucciVMIInFeedbackLine> m : temp.entrySet()) {
                GucciVMIInFeedbackLine pre = m.getValue();
                String key = pre.getJDADocumentNumber() + ":" + pre.getJDAPoNumber() + ":" + pre.getSkuNumber();
                GucciVMIInFeedbackLine aim = data.get(key);
                if (aim == null) {
                    data.put(key, pre);
                } else {
                    if (StringUtils.equals(pre.getJDADocumentNumber(), aim.getJDADocumentNumber()) && StringUtils.equals(pre.getJDAPoNumber(), aim.getJDAPoNumber()) && StringUtils.equals(pre.getSkuNumber(), aim.getSkuNumber())) {
                        aim.setQtyReceived(aim.getQtyReceived() + pre.getQtyReceived());
                        aim.setQtyToBeReceived(aim.getQtyToBeReceived() + pre.getQtyToBeReceived());
                    }
                }
            }
            // save
            int row = 0;
            for (Map.Entry<String, GucciVMIInFeedbackLine> d : data.entrySet()) {
                GucciVMIInFeedbackLine l = d.getValue();
                l.setRowNumberDetail("" + (++row));// 4位流水
                gucciVMIInFeedbackLineDao.save(l);
            }
        }
    }

    /**
     * 创建退大仓作业单
     */
    @Override
    public void generateVmiRtn(VmiRtoCommand vmiRtoCommand) {
        try {
            StockTransApplication sta = new StockTransApplication();
            sta.setType(StockTransApplicationType.VMI_RETURN);
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            sta.setRefSlipCode(vmiRtoCommand.getOrderCode());// 批次号+toLocation
            sta.setFreightMode(FreightMode.LOGISTICS_DELIVERY);// 货物运送方式
            // 重复性校验
            StockTransApplication returnSta = staDao.findReturnMaxWarehouseOrder(vmiRtoCommand.getOrderCode(), new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
            if (null != returnSta) {
                log.info("退仓指令单号【" + vmiRtoCommand.getOrderCode() + "】已创作业单，无法再次创建");
                throw new BusinessException(ErrorCode.RETURN_ORDER_STA_IS_CREATED, new Object[] {vmiRtoCommand.getOrderCode()});
            }
            StaDeliveryInfo stadelivery = new StaDeliveryInfo();
            JSONObject inObj = new loxia.support.json.JSONObject(vmiRtoCommand.getExtMemo());
            String address = inObj.get("toLocation") == null ? null : inObj.get("toLocation").toString();
            stadelivery.setAddress(address);
            BiChannel shop = companyShopDao.getByVmiCode(Constants.GUCCI_BRAND_VMI_CODE);
            if (shop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
            wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
            OperationUnit ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
            Long cmpOuid = ou.getParentUnit().getParentUnit().getId();
            String type = Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE;
            // 退仓数据
            List<VmiRtoLineCommand> rtoLines = vmiRtoLineDao.findRtoLineListByRtoId(vmiRtoCommand.getId(), new BeanPropertyRowMapper<VmiRtoLineCommand>(VmiRtoLineCommand.class));
            if (rtoLines != null && rtoLines.size() > 1000) {
                throw new BusinessException(ErrorCode.NIKE_IMPORT_SIZE);
            }
            boolean isNoSkuError = false;
            List<StaLineCommand> stalinecmds = new ArrayList<StaLineCommand>();
            // 查找Gucci配置退残次品的tolocation
            /*
             * ChooseOption op =
             * chooseOptionDao.findByCategoryCodeAndKey(Constants.GUCCI_SYSTEM_KEY,
             * Constants.GUCCI_DAMAGE_TO_LOCATION); Boolean flag = false; if (op != null &&
             * op.getOptionValue() != null) { String[] toLocations =
             * StringUtils.split(op.getOptionValue(), ","); List<String> list =
             * Arrays.asList(toLocations); if (address != null && list.contains(address)) { flag =
             * true; } }
             */
            if (rtoLines != null && rtoLines.size() > 0) {
                for (VmiRtoLineCommand rtoLine : rtoLines) {
                    StaLineCommand slc = new StaLineCommand();
                    if (rtoLine.getSkuCode() != null) {
                        slc.setSkuCode(rtoLine.getSkuCode());
                    } else {
                        // 创建商品
                        baseinfoManager.sendMsgToOmsCreateSku(rtoLine.getUpc(), shop.getVmiCode());
                        isNoSkuError = true;
                        continue;
                    }
                    // 默认调拨良品（22495、22497等调用残次品）:
                    /*
                     * if (flag) { slc.setIntInvstatusName("残次品"); } else {
                     * slc.setIntInvstatusName("良品"); }
                     */
                    slc.setIntInvstatusName(rtoLine.getInvStatus());
                    slc.setQuantity(rtoLine.getQty());
                    slc.setExtMemo(rtoLine.getExtMemo());
                    stalinecmds.add(slc);
                }
            }
            if (isNoSkuError) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
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
                log.info("Gucci退大仓数据校验未通过！OrderCode:" + vmiRtoCommand.getOrderCode());
                try {
                    log.info("OrderCode:" + vmiRtoCommand.getOrderCode() + ":" + net.sf.json.JSONObject.fromObject(rs).toString());
                } catch (Exception e) {
                    log.error("", e);
                }
                return;
            }
            InventoryStatus inventoryStatus = null;
            sta.setToLocation(address);
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
            // save sta
            sta.setIsNeedOccupied(true);
            sta.setExtMemo(vmiRtoCommand.getExtMemo());
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setSystemKey(Constants.GUCCI_SYSTEM_KEY);
            sta.setIsNotPacsomsOrder(true);
            staDao.save(sta);
            staDao.flush();
            stadelivery.setId(sta.getId());
            stadelivery.setCountry("中国");
            stadelivery.setTransTimeType(TransTimeType.ORDINARY);
            staDeliveryInfoDao.save(stadelivery);
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
                staLine.setExtMemo(cmd.getExtMemo());
                staMap.put(key, staLine);
                // save stvline
                StvLine stvLine = new StvLine();
                stvLine.setDirection(TransactionDirection.OUTBOUND);
                stvLine.setOwner(sta.getOwner());
                stvLine.setQuantity(cmd.getQuantity());
                stvLine.setSku(cmd.getSku());
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
            wareHouseManager.isInventoryNumber(sta.getId());
            // 库存占用
            wareHouseManager.occupyInventoryByStaId(sta.getId(), null, shop);
            stvLineDao.flush();
            // 新增其他出库占用明细记录中间表通知oms/pac,定时任务通知
            wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);
            /***** 调整逻辑：前置退仓增量 *********************************/
            excelReadManager.incrementInv(sta.getId());
            /***** Edit by KJL 2015-03-17 *************************/
            /***** mongoDB库存变更添加逻辑 ******************************/
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
            // pickingListNumber由反馈时候生成，转变为在创建的时候生成
            sta.setSlipCode2(generateNo());// 不要放在slipCode1，否则会导致取消执行发送给pac，然后报错
            staDao.save(sta);
            VmiRtoDefault vmiRto = vmiRtoDao.getByPrimaryKey(vmiRtoCommand.getId());
            if (vmiRto != null) {
                vmiRto.setStatus(VmiGeneralStatus.FINISHED);
                vmiRto.setSta(sta);
                vmiRtoDao.save(vmiRto);
            }
        } catch (Exception e) {
            log.error("创建gucci退大仓作业单失败！orderCode:" + vmiRtoCommand.getOrderCode(), e);
        }
    }

    /**
     * 生成退大仓反馈数据
     */
    @Override
    public void generateVmiRtnFeedbackDate(StockTransApplication sta) {
        if (StringUtils.equals(sta.getSystemKey(), Constants.GUCCI_SYSTEM_KEY)) {
            sta = staDao.getByPrimaryKey(sta.getId());
            // 如果不是调拨指令的退仓单据，不做反馈
            List<VmiRtoCommand> vmiRto = vmiRtoDao.findVmiRtoByVmiCodeAndOrderCode(Constants.GUCCI_BRAND_VMI_CODE, sta.getRefSlipCode(), null, new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
            if (vmiRto == null || vmiRto.isEmpty()) {
                return;
            }
            if (sta.getType().equals(StockTransApplicationType.VMI_RETURN)) {
                GucciVMIRtnFB rtw = new GucciVMIRtnFB();// 退大仓反馈表
                rtw.setToJDALocation(sta.getToLocation());
                rtw.setGoodsIssueDate(new Date());
                rtw.setCreateTime(new Date());
                String batchNumber = null;
                String documentType = null;
                String jDAWarehouseCode = null;
                String brandCode = null;
                String PDNumberHead = null;
                try {
                    JSONObject inObj = new loxia.support.json.JSONObject(sta.getExtMemo());
                    batchNumber = inObj.get("batchNumber") == null ? null : inObj.get("batchNumber").toString();
                    rtw.setJDABatchNumber(batchNumber);
                    documentType = inObj.get("type") == null ? null : inObj.get("type").toString();
                    rtw.setDocumentType(documentType);
                    jDAWarehouseCode = inObj.get("warehouseCode") == null ? null : inObj.get("warehouseCode").toString();
                    rtw.setJDAWarehouseCode(jDAWarehouseCode);
                    brandCode = inObj.get("brandCode") == null ? null : inObj.get("brandCode").toString();
                    rtw.setBrandCode(brandCode);
                    PDNumberHead = inObj.get("pdNumber") == null ? null : inObj.get("pdNumber").toString();
                    rtw.setPDNumber(PDNumberHead);
                } catch (Exception e) {
                    log.error("JSON解析异常!staCode=" + sta.getCode(), e);
                }
                rtw.setStaCode(sta.getCode());
                rtw.setStatus(0);
                gucciVMIRtnFBDao.save(rtw);
                // 退大仓明细行
                List<Carton> cList = cartonDao.findCartonByStaId(sta.getId());// 通过STA查询箱号数据
                if (cList == null || cList.isEmpty()) {
                    // gucci退大仓需要反馈出库箱号
                    throw new BusinessException(ErrorCode.OUT_OF_BOUND_FAILURE, new Object[] {sta.getCode() + "][Gucci有调拨指令的退大仓需要装箱并反馈箱号"});
                }
                if (cList.size() > 0) {
                    // 退大仓指令中需要退货的总数量统计
                    Map<String, Long> totalQty = new HashMap<String, Long>();
                    List<StaLine> lines = staLineDao.findByStaId(sta.getId());
                    String PDNumber = null;
                    for (StaLine staLine : lines) {
                        try {
                            JSONObject inObj = new loxia.support.json.JSONObject(staLine.getExtMemo());
                            PDNumber = inObj.get("pdNumber") == null ? PDNumberHead : inObj.get("pdNumber").toString();
                        } catch (Exception e) {
                            log.error("JSON解析异常!staCode=" + sta.getCode(), e);
                        }
                        // 行上pdNumber为空,从头上读取
                        if (PDNumber == null || (PDNumber != null && (PDNumber.equals("") || PDNumber.equals("NULL")))) {
                            PDNumber = PDNumberHead;
                            log.info("line PDNumber is null,set head PDNumber：" + PDNumber + " staCode:" + sta.getCode());
                        }
                        String key = staLine.getSku().getExtensionCode2() + ":" + PDNumber;
                        if (totalQty.get(key) == null) {
                            totalQty.put(key, staLine.getQuantity());
                        } else {
                            Long qty = totalQty.get(key);
                            totalQty.put(key, qty + staLine.getQuantity());
                        }
                    }
                    Map<String, Long> totalQtyStatic = new HashMap<String, Long>();// 保持不变
                    totalQtyStatic.putAll(totalQty);
                    // 如果有箱号数据反馈箱号数据
                    int rownum = 0;// 行号
                    // String sequence = generateNo();// 序列号
                    String sequence = sta.getSlipCode2();
                    for (Carton c : cList) {
                        List<CartonLine> clList = cartonDao.findCartonLineByCarId(c.getId());// 查询箱号下的明细
                        for (CartonLine cl : clList) {
                            Sku sku = skuDao.getByPrimaryKey(cl.getSku().getId());
                            if (sku == null) {
                                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                            }
                            String upc = sku.getExtensionCode2();
                            if (StringUtil.isEmpty(upc)) {
                                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                            }
                            GucciVMIRtnFBLine rtwL = new GucciVMIRtnFBLine();
                            rtwL.setSkuNumber(upc);
                            // rtwL.setShippedQuantity(cl.getQty());// 实际装箱数量
                            rtwL.setCartonNumber(c.getCode());// 箱号
                            rtwL.setJDABatchNumber(batchNumber);
                            rtwL.setToJDALocation(sta.getToLocation());
                            rtwL.setDocumentType(documentType);
                            rtwL.setJDAWarehouseCode(jDAWarehouseCode);
                            rtwL.setBrandCode(brandCode);
                            rtwL.setGucciVMIRtnFBId(rtw);
                            // 从totalQty寻找该商品对应的pdNumber和数量，逐行扣减
                            for (Map.Entry<String, Long> m : totalQty.entrySet()) {
                                String[] splitStrs = StringUtil.splitStr(m.getKey(), ":");
                                String upcTemp = splitStrs[0];
                                String PDNumberTemp = splitStrs[1];
                                Long qtyLeft = m.getValue();
                                if (StringUtils.equals(upc, upcTemp)) {
                                    // 1、先判断是否还有数量,有则分配扣减，无则继续循环
                                    if (qtyLeft > 0) {
                                        // 同商品进行扣减，刷新map中剩余数量
                                        if (cl.getQty() - qtyLeft > 0) {
                                            // 2、实际装箱数量大于map中剩余数，
                                            rtwL.setPDNumber(PDNumberTemp);
                                            rtwL.setRequestedQuantity(totalQtyStatic.get(upc + ":" + PDNumberTemp));// upc应退大仓总数量
                                            rtwL.setShippedQuantity(qtyLeft);// 拆分数量
                                            totalQty.put(m.getKey(), 0l);
                                            gucciVMIRtnFBLineDao.save(rtwL);
                                            // 3、反馈明细需要进一步进行拆分，直至全部扣完(反馈明细表现为：同商品、同箱号，但是不同pdNumber)
                                            Boolean flag = false;
                                            Long leftFenpeiQty = cl.getQty() - qtyLeft;// 剩余待分配数量
                                            for (Map.Entry<String, Long> m2 : totalQty.entrySet()) {
                                                String[] splitStrs2 = StringUtil.splitStr(m2.getKey(), ":");
                                                String upcTemp2 = splitStrs2[0];
                                                String PDNumberTemp2 = splitStrs2[1];
                                                Long qtyLeft2 = m2.getValue();
                                                if (StringUtils.equals(upc, upcTemp2)) {
                                                    if (qtyLeft2 > 0) {
                                                        if (leftFenpeiQty - qtyLeft2 > 0) {
                                                            GucciVMIRtnFBLine rtwL2 = new GucciVMIRtnFBLine();
                                                            BeanUtils.copyProperties(rtwL, rtwL2, new String[] {"id", "PDNumber", "requestedQuantity"});
                                                            rtwL.setPckNumber(sequence);
                                                            rtwL.setPickingListNo("" + (++rownum));
                                                            rtwL.setPDNumber(PDNumberTemp2);
                                                            rtwL.setRequestedQuantity(totalQtyStatic.get(upc + ":" + PDNumberTemp2));
                                                            rtwL.setShippedQuantity(qtyLeft2);// 拆分数量
                                                            totalQty.put(m2.getKey(), 0l);
                                                            leftFenpeiQty = leftFenpeiQty - qtyLeft2;
                                                            gucciVMIRtnFBLineDao.save(rtwL2);
                                                        } else {
                                                            GucciVMIRtnFBLine rtwL2 = new GucciVMIRtnFBLine();
                                                            BeanUtils.copyProperties(rtwL, rtwL2, new String[] {"id", "PDNumber", "requestedQuantity"});
                                                            rtwL.setPckNumber(sequence);
                                                            rtwL.setPickingListNo("" + (++rownum));
                                                            rtwL.setPDNumber(PDNumberTemp2);
                                                            rtwL.setRequestedQuantity(totalQtyStatic.get(upc + ":" + PDNumberTemp2));
                                                            rtwL.setShippedQuantity(leftFenpeiQty);// 全部分配完毕
                                                            totalQty.put(m2.getKey(), qtyLeft2 - leftFenpeiQty);
                                                            leftFenpeiQty = 0l;
                                                            gucciVMIRtnFBLineDao.save(rtwL2);
                                                            flag = true;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            if (flag) break;
                                        } else {
                                            rtwL.setPckNumber(sequence);
                                            rtwL.setPickingListNo("" + (++rownum));
                                            rtwL.setPDNumber(PDNumberTemp);
                                            rtwL.setRequestedQuantity(totalQtyStatic.get(upc + ":" + PDNumberTemp));// upc应退大仓总数量
                                            rtwL.setShippedQuantity(cl.getQty());// 实际装箱数量
                                            totalQty.put(m.getKey(), qtyLeft - cl.getQty());
                                            gucciVMIRtnFBLineDao.save(rtwL);
                                            break;
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 生成年份+8位序列号：2017000000001
     * 
     * @param rownum
     * @return
     */
    public String generateNo() {
        try {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            Long sequence = gucciVMIRtnFBLineDao.generatePckNumberSequence(new SingleColumnRowMapper<Long>(Long.class));
            String PckNumber = formatter.format(date) + new DecimalFormat("00000000").format(sequence);// 年份+8位序列号：201700000001
            return PckNumber;
        } catch (Exception e) {
            log.error("序列号生成异常！", e);
        }
        return null;
    }

    /**
     * 生成退货入库反馈数据
     */
    @Override
    public void generateSaleReturnInboundData(StockTransApplication sta, StockTransVoucher stv) {
        // try {
        // // 反馈头信息
        // GucciVMIInFeedback gb = new GucciVMIInFeedback();
        // gb.setJDADocumentNumber(sta.getRefSlipCode());
        // gb.setStaCode(sta.getCode());
        // gb.setReceiptDate(new Date());
        // // 解析退货指令相关附属字段
        // String warehouseCode = null;
        // String vendorNumber = null;
        // String brandCode = null;
        // Map<String, Long> totalQty = new HashMap<String, Long>();
        // try {
        // JSONObject inObj = new loxia.support.json.JSONObject(sta.getExtMemo());
        // DateFormat df = new SimpleDateFormat("yyyyMMdd");
        // Date arriveTime = null;
        // try {
        // arriveTime = df.parse(inObj.get("date") == null ? null : inObj.get("date").toString());
        // gb.setJDADocumentDate(arriveTime);
        // } catch (Exception e) {
        // log.error("Gucci退货入库时间解析异常！", e);
        // }
        // gb.setJDADocumentType(inObj.get("type") == null ? null : inObj.get("type").toString());
        // warehouseCode = inObj.get("warehouseCode") == null ? null :
        // inObj.get("warehouseCode").toString();
        // gb.setJDAWarehouseCode(warehouseCode);
        // vendorNumber = inObj.get("vendorNumber") == null ? null :
        // inObj.get("vendorNumber").toString();
        // gb.setVendorNumber(vendorNumber);
        // brandCode = inObj.get("brandCode") == null ? null : inObj.get("brandCode").toString();
        // gb.setBrandCode(brandCode);
        // // 应收数量统计
        // JSONArray jsonArray = inObj.getJSONArray("lineMemoList");
        // for (int i = 0; i < jsonArray.length(); i++) {
        // String line = jsonArray.getString(i);
        // JSONObject inObjLine = new JSONObject(line);
        // String skuNumber = inObjLine.get("skuCode") == null ? null :
        // inObjLine.get("skuCode").toString();
        // String poNumber = inObjLine.get("poNumber") == null ? null :
        // inObjLine.get("poNumber").toString();
        // String qty = inObjLine.get("qty") == null ? null : inObjLine.get("qty").toString();
        // String key = skuNumber + ":" + poNumber;
        // if (totalQty.get(key) == null) {
        // totalQty.put(key, Long.parseLong(qty));
        // } else {
        // Long num = totalQty.get(key);
        // totalQty.put(key, num + Long.parseLong(qty));
        // }
        // }
        // } catch (Exception e) {
        // log.error("json解析异常！+staCode=" + sta.getCode(), e);
        // }
        // gb.setPhysicalRecDate(new Date());
        // gb.setType(1);// 数据类型：退货入库
        // gb.setStatus(0);
        // gucciVMIInFeedbackDao.save(gb);
        // // 生成退货明细数据
        // List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId());
        // Map<String, Long> staticQty = new HashMap<String, Long>();
        // staticQty.putAll(totalQty);
        // if (stvLines != null && !stvLines.isEmpty()) {
        // int row = 0;
        // for (StvLine line : stvLines) {
        // GucciVMIInFeedbackLine l = new GucciVMIInFeedbackLine();
        // l.setGucciVMIInFeedback(gb);
        // l.setBrandCode(brandCode);
        // l.setJDADocumentNumber(sta.getRefSlipCode());
        // l.setJDAWarehouseCode(warehouseCode);
        // l.setVendorNumber(vendorNumber);
        // l.setRowNumberDetail("" + (++row));// 行号
        // Sku sku = line.getSku();
        // String upc = sku.getExtensionCode2();
        // l.setSkuNumber(sku.getExtensionCode2());
        // // 收货库存状态
        // InventoryStatus invStatus =
        // inventoryStatusDao.findByStatusIdAndOu(line.getInvStatus().getId(),
        // line.getWarehouse().getId());
        // if (StringUtils.equals(invStatus.getName(), InventoryStatus.INVENTORY_STATUS_GOOD)) {
        // l.setInvStatus("良品");
        // } else {
        // l.setInvStatus(Constants.GUCCI_DAMAGE);
        // }
        // // 计划收获量
        // for (Map.Entry<String, Long> m : totalQty.entrySet()) {
        // String[] splitStrs = StringUtil.splitStr(m.getKey(), ":");
        // String upcTemp = splitStrs[0];
        // String poNumberTemp = splitStrs[1];
        // Long qtyLeft = m.getValue();
        // if (StringUtils.equals(upc, upcTemp)) {
        // if (qtyLeft > 0) {
        // // 同商品进行扣减，刷新map中剩余数量
        // if (line.getQuantity() - qtyLeft > 0) {
        // l.setJDAPoNumber(poNumberTemp);
        // l.setQtyToBeReceived(staticQty.get(upc + ":" + poNumberTemp));// upc应退货入总数量
        // l.setQtyReceived(qtyLeft);// 实际收货数量(拆分出的)
        // totalQty.put(m.getKey(), 0l);
        // gucciVMIInFeedbackLineDao.save(l);
        // // 进一步进行拆分，直至全部扣完
        // Boolean flag = false;
        // Long leftFenpeiQty = line.getQuantity() - qtyLeft;// 剩余待分配数量
        // for (Map.Entry<String, Long> m2 : totalQty.entrySet()) {
        // String[] splitStrs2 = StringUtil.splitStr(m2.getKey(), ":");
        // String upcTemp2 = splitStrs2[0];
        // String poNumberTemp2 = splitStrs2[1];
        // Long qtyLeft2 = m2.getValue();
        // if (StringUtils.equals(upc, upcTemp2)) {
        // if (qtyLeft2 > 0) {
        // if (leftFenpeiQty - qtyLeft2 > 0) {
        // GucciVMIInFeedbackLine l2 = new GucciVMIInFeedbackLine();
        // BeanUtils.copyProperties(l, l2, new String[] {"id", "JDAPoNumber", "qtyToBeReceived"});
        // l2.setRowNumberDetail("" + (++row));// 行号
        // l2.setJDAPoNumber(poNumberTemp2);
        // l2.setQtyToBeReceived(staticQty.get(upc + ":" + poNumberTemp2));
        // l2.setQtyReceived(qtyLeft2);// 拆分数量
        // totalQty.put(m2.getKey(), 0l);
        // leftFenpeiQty = leftFenpeiQty - qtyLeft2;
        // gucciVMIInFeedbackLineDao.save(l2);
        // } else {
        // GucciVMIInFeedbackLine l2 = new GucciVMIInFeedbackLine();
        // BeanUtils.copyProperties(l, l2, new String[] {"id", "JDAPoNumber", "qtyToBeReceived"});
        // l2.setRowNumberDetail("" + (++row));// 行号
        // l2.setJDAPoNumber(poNumberTemp2);
        // l2.setQtyToBeReceived(staticQty.get(upc + ":" + poNumberTemp2));
        // l2.setQtyReceived(leftFenpeiQty);// 全部分配完毕
        // totalQty.put(m2.getKey(), qtyLeft2 - leftFenpeiQty);
        // leftFenpeiQty = 0l;
        // gucciVMIInFeedbackLineDao.save(l2);
        // flag = true;
        // break;
        // }
        // }
        // }
        // }
        // if (flag) break;
        // } else {
        // l.setRowNumberDetail("" + (++row));// 行号
        // l.setJDAPoNumber(poNumberTemp);
        // l.setQtyToBeReceived(staticQty.get(upc + ":" + poNumberTemp));
        // l.setQtyReceived(line.getQuantity());
        // totalQty.put(m.getKey(), qtyLeft - line.getQuantity());
        // gucciVMIInFeedbackLineDao.save(l);
        // break;
        // }
        // }
        // }
        // }
        // }
        // }
        // } catch (Exception e) {
        // log.error("Gucci生成退货入库反馈数据异常！staCode:" + sta.getCode(), e);
        // }
    }

    /**
     * 退大仓送货单定制
     */
    @Override
    public String[] outboundJasper(Long staid) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        try {
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            if (!StringUtils.equals(shop.getVmiCode(), Constants.GUCCI_BRAND_VMI_CODE)) {
                return null;
            }
            List<VmiRtoCommand> vmiRto = vmiRtoDao.findVmiRtoByVmiCodeAndOrderCode(Constants.GUCCI_BRAND_VMI_CODE, sta.getRefSlipCode(), null, new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
            if (vmiRto == null || vmiRto.isEmpty()) {
                return null;
            }
            String pickingListNumber = sta.getSlipCode2();
            if (pickingListNumber == null) {
                pickingListNumber = gucciVMIRtnFBLineDao.getPickingListNumber(staid, new SingleColumnRowMapper<String>(String.class));
            }
            String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbound_send_infor_g_main.jasper";
            String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbound_send_infor_g_sub.jasper";
            return new String[] {reportPath, subReportPath, pickingListNumber};
        } catch (Exception e) {
            log.error("Gucci送货单定制异常！！staCode:" + sta.getCode(), e);
        }
        return null;
    }

    /**
     * 退大仓装箱单定制
     */
    @Override
    public String[] outBoundPackageInfoJasper(Long cartonid) {
        Carton carton = cartonDao.getByPrimaryKey(cartonid);
        StockTransApplication sta = staDao.getByPrimaryKey(carton.getSta().getId());
        try {
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            if (!StringUtils.equals(shop.getVmiCode(), Constants.GUCCI_BRAND_VMI_CODE)) {
                return null;
            }
            List<VmiRtoCommand> vmiRto = vmiRtoDao.findVmiRtoByVmiCodeAndOrderCode(Constants.GUCCI_BRAND_VMI_CODE, sta.getRefSlipCode(), null, new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
            if (vmiRto == null || vmiRto.isEmpty()) {
                return null;
            }
            String pickingListNumber = sta.getSlipCode2();
            if (pickingListNumber == null) {
                pickingListNumber = gucciVMIRtnFBLineDao.getPickingListNumber(sta.getId(), new SingleColumnRowMapper<String>(String.class));
            }
            String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbond_package_g_main.jasper";
            String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbond_package_g_detail.jasper";;
            return new String[] {reportPath, subReportPath, pickingListNumber, carton.getCode()};
        } catch (Exception e) {
            log.error("Gucci装箱单定制异常！！staCode:" + sta.getCode(), e);
        }
        return null;
    }

    /**
     * 超过6小时未收到商品主档信息发送预警邮件
     */
    @Override
    public void sendEmailWhenInstructionHasNoSku() {
        List<String> vList = vmiAsnDao.findGucciVmiAsnNoSku(Constants.GUCCI_BRAND_VMI_CODE, new SingleColumnRowMapper<String>(String.class));
        if (vList == null || (vList != null && vList.isEmpty())) {
            return;
        }
        if (vList.size() > 0) {
            ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, Constants.GUCCI_INSTRUCTION_NO_SKU);
            if (!StringUtil.isEmpty(c.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("SKU_NOTICE");
                if (template != null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(template.getMailBody() + " \n");
                    for (String v : vList) {
                        sb.append(v + " \n");
                    }
                    boolean bool = false;
                    bool = MailUtil.sendMail(template.getSubject() + "[Gucci]", c.getOptionValue(), "", sb.toString(), false, null);
                    if (bool) {
                        log.debug("邮件通知成功！");
                    } else {
                        log.debug("邮件通知失败,请联系系统管理员！");
                    }
                } else {
                    log.debug("邮件模板不存在或被禁用！");
                }
            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
        }
    }
}
