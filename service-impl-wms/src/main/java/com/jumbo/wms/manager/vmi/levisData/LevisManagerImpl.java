package com.jumbo.wms.manager.vmi.levisData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.vmi.levis.LevisDeliveryOrderDao;
import com.jumbo.dao.vmi.levis.LevisDeliveryReceiveDao;
import com.jumbo.dao.vmi.levis.LevisSkmrDao;
import com.jumbo.dao.vmi.levis.LevisStkrDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.util.FormatUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.task.vmi.LevisTaskImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.vmi.levis.LevisDeliveryReceiveCommand;
import com.jumbo.wms.model.command.vmi.levis.LevisStkrCommand;
import com.jumbo.wms.model.vmi.levisData.LevisDeliveryOrder;
import com.jumbo.wms.model.vmi.levisData.LevisSkmr;
import com.jumbo.wms.model.vmi.levisData.LevisStkr;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

/**
 * LEVIS
 * 
 * @author Administrator
 * 
 */
@Transactional
@Service("levisManager")
public class LevisManagerImpl implements LevisManager {
    private static final long serialVersionUID = 9099999691374274767L;
    protected static final Logger log = LoggerFactory.getLogger(LevisManagerImpl.class);

    @Autowired
    private LevisDeliveryOrderDao levisDeliveryOrderDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private LevisDeliveryReceiveDao levisDeliveryReceiveDao;
    @Autowired
    private LevisSkmrDao levisSkmrDao;
    @Autowired
    private LevisStkrDao levisStkrDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;

    private EventObserver eventObserver;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named eventObserver");
        }
    }

    public void createStkrData() {
        Date today = new Date();
        List<LevisStkrCommand> list = levisStkrDao.findAll(new BeanPropertyRowMapper<LevisStkrCommand>(LevisStkrCommand.class));
        for (LevisStkrCommand cmd : list) {
            LevisStkr stkr = new LevisStkr();
            // if (!cmd.getStoreCode().equals("0020019205")) {
            // stkr.setDivision("CQQ");
            // }
            // if (cmd.getStoreCode().equals("0020025697")) {
            // stkr.setDivision("CTBK");
            // }
            // if (cmd.getStoreCode().equals("0020023525")) {
            // stkr.setDivision("COW1");
            // }
            stkr.setDivision("CBAO");
            stkr.setCreateDate(new Date());
            stkr.setCurrency("RMB");
            stkr.setStoreCode(cmd.getStoreCode());
            stkr.setProductCode(cmd.getSupplierSkuCode().replace("-", "") + "0");
            String memo = cmd.getKeyprop();
            String[] sizeColor = memo.split(" ");
            if (sizeColor.length > 1 && sizeColor[1] != null) {
                stkr.setColorCode(sizeColor[1]);
            } else {
                stkr.setColorCode("-");
            }
            stkr.setSizeCode(sizeColor[0]);
            stkr.setSku(stkr.getProductCode().substring(0, 8));
            stkr.setStockLevelDate(FormatUtil.formatDate(today, FormatUtil.DATE_FORMATE_YYYYMMDD));
            stkr.setLastMovementDate(cmd.getLastMovementDate());
            stkr.setQuantity(FormatUtil.addCharForString(cmd.getQuantity(), '0', 10, 1));
            String extendedExternalSku = stkr.getProductCode() + stkr.getSizeCode(); // sku
            if (!stkr.getColorCode().equals("-")) {
                extendedExternalSku = extendedExternalSku + stkr.getColorCode();
            }
            stkr.setExtendedExternalSku(extendedExternalSku);
            String retailPrice = "0";
            if (cmd.getListPrice() != null) {
                retailPrice = cmd.getListPrice().multiply(new BigDecimal(1000000)).setScale(0).toString();
            }
            retailPrice = FormatUtil.addCharForString(retailPrice, '0', 16, 1);
            stkr.setRetailPrice(retailPrice);
            levisStkrDao.save(stkr);
        }
    }

    /**
     * 生成LEVIS SKMR DATE
     * 
     * @param dateFormatStr 日期格式'yyyy-MM-dd'
     */
    public void createSkmrData(String dateFormatStr) {
        Map<String, String> moveTypeMap = new HashMap<String, String>();
        moveTypeMap.put("60", "60-P");// 调整入
        moveTypeMap.put("61", "60-N");// 调整出
        moveTypeMap.put("20", "20-P");// 收货
        moveTypeMap.put("95", "95-P");// 退大仓
        moveTypeMap.put("45", "45-N");// 转出
        moveTypeMap.put("40", "40-P");// 转入
        moveTypeMap.put("90", "90-P");// 销售出
        moveTypeMap.put("91", "90-N");// 退货入
        moveTypeMap.put("92", "60-P");// 换货入
        moveTypeMap.put("93", "60-N");// 换货出

        // 出入库记录
        List<LevisDeliveryReceiveCommand> list = levisDeliveryReceiveDao.findByDate(dateFormatStr);
        if (list != null) {
            for (LevisDeliveryReceiveCommand r : list) {
                LevisSkmr skmr = new LevisSkmr();
                String prdCode = r.getSupplierCode();
                String memo = r.getKeyProperties();
                String productCode = prdCode.replace("-", "") + "0";
                String colorCode = "";
                String sizeCode = "";
                String[] sizeColor = memo.split(" ");
                if (sizeColor.length > 1 && sizeColor[1] != null) {
                    colorCode = sizeColor[1];
                } else {
                    colorCode = "-";
                }
                sizeCode = sizeColor[0];
                skmr.setSizeCode(sizeCode);
                skmr.setColorCode(colorCode);
                skmr.setCreatDate(r.getCreateTime());
                skmr.setMovementDate(FormatUtil.formatDate(r.getCreateTime(), FormatUtil.DATE_FORMATE_YYYYMMDD));
                if ("45".equals(r.getType()) || "40".equals(r.getType())) {
                    skmr.setMovementReasonCode("0991");
                    skmr.setOtherStoreCode(r.getStoreCode2());
                }
                skmr.setMovementTime(FormatUtil.formatDate(r.getCreateTime(), FormatUtil.DATE_FORMATE_HHMMSS) + "00");
                String moveSign = moveTypeMap.get(r.getType());
                if (moveSign != null) {
                    skmr.setMovementType(moveSign.split("-")[0]);
                    skmr.setQuantitySign(moveSign.split("-")[1]);
                }
                skmr.setProductCode(productCode);
                skmr.setQuantity(FormatUtil.addCharForString(r.getQuantity(), '0', 10, 1));
                skmr.setSizeCode(sizeCode);
                String sku = productCode + sizeCode; // sku
                if (!colorCode.equals("-")) {
                    sku = sku + colorCode;
                }
                skmr.setSku(sku);
                skmr.setStartWeekDate(FormatUtil.formatDate(FormatUtil.getFirstDayOfWeek(r.getCreateTime()), FormatUtil.DATE_FORMATE_YYYYMMDD));
                skmr.setStoreCode(r.getStoreCode1());
                if (r.getSeqNo() != null) {
                    skmr.setTransNumber(FormatUtil.addCharForString(r.getSeqNo().toString(), '0', 10, 1));
                }
                if ("20".equals(r.getType())) {
                    skmr.setDocumentNumber(FormatUtil.addCharForString(r.getPoCode(), ' ', 20, -1));
                    skmr.setTransNumber(FormatUtil.addCharForString(r.getOrderCode(), '0', 10, 1));
                    skmr.setOtherStoreCode(FormatUtil.addCharForString(r.getStoreCode2(), '0', 10, 1));
                }
                // 销售相关
                if (r.getType().equals("90")) {
                    //skmr.setTransNumber(r.getOrderCode().substring(r.getOrderCode().length() - 10));
                    String seqNo=staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).toString();
                    if(seqNo.length()>=10){
                        skmr.setTransNumber(seqNo);
                    }else if(seqNo.length()==9){
                        skmr.setTransNumber("3"+seqNo);
                    }
                   
                } else if (r.getType().equals("91")) {
                    if (r.getOrderCode().length() > 10) {
                        StockTransApplication sta = staDao.findStaBySlipCodeConfirmOrder(r.getOrderCode(), StockTransApplicationType.INBOUND_RETURN_REQUEST);
                        String orderCode = staDao.queryPickingUser(new SingleColumnRowMapper<String>(String.class));
                        if (r.getOrderCode().contains("RAS")) {
                            sta.setPickingUser("810" + orderCode);
                            skmr.setTransNumber("810" + orderCode);
                        } else if (r.getOrderCode().contains("R")) {
                            sta.setPickingUser("800" + orderCode);
                            skmr.setTransNumber("800" + orderCode);
                        }
                        staDao.save(sta);
                    } else {
                        skmr.setTransNumber(r.getOrderCode().replace("RAS", "810").replace("R", "800"));
                    }
                } else if (r.getType().equals("92")) {
                    if (r.getOrderCode().length() > 10) {
                        StockTransApplication sta = staDao.findStaBySlipCodeConfirmOrder(r.getOrderCode(), StockTransApplicationType.INBOUND_RETURN_REQUEST);
                        String orderCode = staDao.queryPickingUser(new SingleColumnRowMapper<String>(String.class));
                        if (r.getOrderCode().contains("EAS")) {
                            skmr.setTransNumber("910" + orderCode);
                            sta.setPickingUser("910" + orderCode);
                        } else if (r.getOrderCode().contains("E")) {
                            skmr.setTransNumber("900" + orderCode);
                            sta.setPickingUser("900" + orderCode);
                        }
                        staDao.save(sta);
                    } else {
                        skmr.setTransNumber(r.getOrderCode().replace("EAS", "910").replace("E", "900"));
                    }
                } else if (r.getType().equals("93")) {
                    if (r.getOrderCode().length() > 10) {
                        StockTransApplication sta = staDao.findStaBySlipCodeConfirmOrder(r.getOrderCode(), StockTransApplicationType.OUTBOUND_RETURN_REQUEST);
                        String orderCode = staDao.queryPickingUser(new SingleColumnRowMapper<String>(String.class));
                        if (r.getOrderCode().contains("EAS")) {
                            skmr.setTransNumber("910" + orderCode);
                            sta.setPickingUser("910" + orderCode);
                        } else if (r.getOrderCode().contains("E")) {
                            skmr.setTransNumber("900" + orderCode);
                            sta.setPickingUser("900" + orderCode);
                        }
                        staDao.save(sta);
                    } else {
                        skmr.setTransNumber(r.getOrderCode().replace("EAS", "910").replace("E", "900"));
                    }
                }
                levisSkmrDao.save(skmr);
            }
        }
    }

    public void readStockInFile(File file, String localBackPath) {
        if (!file.getName().startsWith(LevisTaskImpl.LEVIS_STOCKIN_FILE_NAME_START)) {
            return;
        }
        FileReader r;
        try {
            r = new FileReader(file);
            BufferedReader read = new BufferedReader(r);
            String line = null;
            Map<String, Boolean> poMap = new HashMap<String, Boolean>();
            do {
                line = read.readLine();
                if (StringUtils.hasText(line)) {

                    String[] strs = line.split("\\{\\^\\^\\}");
                    if (strs.length >= 14) {
                        LevisDeliveryOrder o = new LevisDeliveryOrder();
                        o.setStoreCode1(strs[4].trim());
                        o.setStoreCode2(strs[5].trim());
                        o.setProductCode(strs[6].trim());
                        o.setInseamCode(strs[8].trim());
                        o.setSizeCode(strs[9].trim());
                        o.setQuantity(strs[11].trim());
                        o.setOrderCode(strs[12].trim());
                        o.setPoCode(strs[14].trim());
                        Boolean isExistPo = poMap.get(o.getPoCode());
                        // PO已经存在跳过,否则插叙数据
                        if (isExistPo != null) {
                            if (isExistPo) {
                                continue;
                            }
                        } else {
                            String poCode = levisDeliveryOrderDao.findSingelPoCode(o.getPoCode(), new SingleColumnRowMapper<String>());
                            if (poCode == null) {
                                // poCode 不存在,记录当前PO不存在
                                poMap.put(o.getPoCode(), false);
                            } else {
                                // 记录当前PO存在
                                poMap.put(o.getPoCode(), true);
                                continue;
                            }
                        }
                        levisDeliveryOrderDao.save(o);
                    }
                }
            } while (line != null);
            if (read != null) {
                read.close();
                r.close();
            }
            if (StringUtils.hasText(localBackPath)) {
                FileUtils.moveFileToDirectory(file, new File(localBackPath), true);
            }
        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public void generateInboundStaByCode(String poCode) {
        String vmiCode = levisDeliveryOrderDao.findShopVmiCodeByPoCode(poCode, new SingleColumnRowMapper<String>(String.class));
        BiChannel shop = companyShopDao.getByVmiCode(vmiCode);
        if (shop == null) {
            log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        OperationUnit ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        Long companyId = null;
        companyId = ou.getParentUnit().getParentUnit().getId();
        InventoryStatus sts = inventoryStatusDao.findInvStatusForSale(companyId);
        if (sts == null) {
            throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
        }
        List<LevisDeliveryOrder> ldos = levisDeliveryOrderDao.findByPoCode(poCode);
        if (ldos == null || ldos.size() == 0) {
            // 无创建作业单数据
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 创建STA
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setRefSlipCode(poCode);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setOwner(shop.getCode());
        sta.setMainWarehouse(ou);
        sta.setMainStatus(sts);
        sta.setMemo(ldos.get(0).getOrderCode());
        staDao.save(sta);
        log.debug("==========create======sta code : " + sta.getCode());
        // 创建sta line
        Long skuQty = 0l;
        boolean isNoSkuError = false;
        for (LevisDeliveryOrder ldo : ldos) {
            // levis sku 唯一编码
            String lvsSkuCode = ldo.getProductCode() + (StringUtils.hasText(ldo.getSizeCode()) ? ldo.getSizeCode() : "") + (StringUtils.hasText(ldo.getInseamCode()) ? ldo.getInseamCode() : "");
            log.debug("lvsSkuCode : {}", lvsSkuCode);
            lvsSkuCode = lvsSkuCode.replaceAll("-", "");
            // 创建商品
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(lvsSkuCode, shop.getCustomer().getId(), shop.getId());
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(lvsSkuCode, shop.getVmiCode());
                isNoSkuError = true;
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setQuantity(Long.parseLong(ldo.getQuantity()));
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            staLine.setOwner(shop.getCode());
            staLine.setInvStatus(sts);
            staLineDao.save(staLine);
            skuQty += staLine.getQuantity();
            ldo.setStaId(sta.getId());
            ldo.setStaLineId(staLine.getId());
            levisDeliveryOrderDao.save(ldo);
        }
        sta.setSkuQty(skuQty);
        staDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        log.debug("===============sta {} create success ================", new Object[] {sta.getCode()});
        if (isNoSkuError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }
}
