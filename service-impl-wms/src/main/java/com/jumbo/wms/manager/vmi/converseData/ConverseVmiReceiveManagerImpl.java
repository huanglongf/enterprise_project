package com.jumbo.wms.manager.vmi.converseData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.converseData.ConversePriceContrastDao;
import com.jumbo.dao.vmi.converseData.ConverseVmiAdjustmentDao;
import com.jumbo.dao.vmi.converseData.ConverseVmiInvStatusChangeDao;
import com.jumbo.dao.vmi.converseData.ConverseVmiReceiveDao;
import com.jumbo.dao.vmi.converseData.ConverseVmiStockInDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.mq.MqBSSkuPriceMsg;
import com.jumbo.mq.MqMsgList;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.converseData.ConversePriceContrast;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiAdjustment;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiInvStatusChange;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiInvStatusChangeCommand;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiReceive;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiStockIn;
import com.jumbo.wms.model.vmi.itData.VMIAdjustmentStatus;
import com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus;
import com.jumbo.wms.model.vmi.itData.VMIReceiveInfoType;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;

@Transactional
@Service("converseVmiReceiveManager")
public class ConverseVmiReceiveManagerImpl extends BaseManagerImpl implements ConverseVmiReceiveManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5618235677287227809L;

    @Autowired
    StaLineDao staLineDao;
    @Autowired
    ConverseVmiReceiveDao cReceiveDao;
    @Autowired
    ConverseVmiStockInDao cStockInDao;
    @Autowired
    StockTransApplicationDao staDao;
    @Autowired
    ConverseVmiAdjustmentDao cAdjustmentDao;
    @Autowired
    SkuDao skuDao;
    @Autowired
    InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    ConverseVmiAdjustmentDao adjDao;
    @Autowired
    InventoryDao inventoryDao;
    @Autowired
    StvLineDao stvLineDao;
    @Autowired
    ConverseVmiInvStatusChangeDao statusChangeDao;
    @Autowired
    InventoryStatusDao isDao;
    @Autowired
    InventoryCheckDifferenceLineDao icdifLineDao;
    @Autowired
    private BaseinfoManager baseManger;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private ConversePriceContrastDao cpcDao;
    @Autowired
    private CommonConfigManager configManager;

    public void generateVMIReceiveInfoBySta(StockTransApplication sta) {
        if (!sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT)) {
            return;
        }
        List<StaLine> lines = staLineDao.findByStaId(sta.getId());
        Date date = new Date();
        ConverseVmiReceive receiveInfo = null;
        for (StaLine line : lines) {
            ConverseVmiStockIn instruction = cStockInDao.findCVSByStaLineId(line.getId());
            receiveInfo = new ConverseVmiReceive();
            receiveInfo.setCartonNumber(instruction.getCartonNo());
            receiveInfo.setFromLocation(instruction.getFromLocation());
            receiveInfo.setItemEanUpcCode(instruction.getItemEanUpcCode());
            receiveInfo.setLineSequenceNO(instruction.getLineSequenceNO());
            receiveInfo.setQuantity(line.getQuantity());
            receiveInfo.setReceiveDate(date);
            receiveInfo.setSapCarton(instruction.getCartonNo());
            receiveInfo.setTransferNO(instruction.getTransferNO());
            receiveInfo.setToLocation(instruction.getToLocation());
            // receiveInfo.setTotalLineSequenceNO(instruction.getLineSequenceNO());
            receiveInfo.setTransferPrefix(instruction.getFromLocation());
            receiveInfo.setStatus(VMIReceiveInfoStatus.NEW);
            receiveInfo.setType(1);// 1为收货反馈 ；5为退仓反馈 7为转店反馈
            cReceiveDao.save(receiveInfo);
        }
        sta.setVmiRCStatus(Boolean.TRUE);
        staDao.flush();
    }


    /**
     * 库存共享生成转单文件
     */
    public void generateToshopForPos() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date today = calendar.getTime();

        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date nowTime = calendar.getTime();

        String starDate = FormatUtil.formatDate(today, "yyyy-MM-dd");
        String endTime = FormatUtil.formatDate(nowTime, "yyyy-MM-dd");
        starDate = starDate + " 00:00:00";
        endTime = endTime + " 00:00:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            today = df.parse(starDate);
            nowTime = df.parse(endTime);

        } catch (ParseException e) {
            log.error("", e);
        }

        cReceiveDao.createToshopForPos(today, nowTime);

    }

    public void generateVMIReceiveFile(String fileDir, String date, VMIReceiveInfoType type) {
        updateReceiveInfoStatus(VMIReceiveInfoStatus.NEW.getValue(), VMIReceiveInfoStatus.PROCESSING.getValue(), type.getValue());
        List<ConverseVmiReceive> infos = findConverseReceiveInfos(VMIReceiveInfoStatus.PROCESSING.getValue(), type.getValue());
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (null != infos && infos.size() > 0) {
            // 新建生成文件的文件夹
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            StringBuilder contextRev = new StringBuilder();
            int revSeq = 1;
            // 循环入库的信息，根据入库信息生成数据
            for (ConverseVmiReceive rec : infos) {
                if (null == rec.getColorCode()) {
                    rec.setColorCode("");
                }
                if (null == rec.getSizeCode()) {
                    rec.setSizeCode("");
                }
                if (" " == rec.getBin()) {
                    rec.setBin("");
                }
                contextRev.append(autoAddBlank(rec.getTransferPrefix(), 10)).append("\t").append(autoAddBlank(rec.getCartonNumber(), 26)).append("\t").append(sdf.format(rec.getReceiveDate())).append("\t").append(autoAddBlank(rec.getFromLocation(), 10))
                        .append("\t").append(autoAddBlank(rec.getToLocation(), 10)).append("\t").append("").append("\t").append("").append("\t").append("").append("\t").append(autoAddBlank(rec.getInseamCode(), -1)).append("\t")
                        .append(autoAddBlank(rec.getItemEanUpcCode(), 26)).append("\t").append(autoAddBlank(rec.getQuantity() + "", 30)).append("\t").append(autoAddBlank(revSeq++ + "", 10)).append("\t")
                        .append(autoAddBlank(rec.getTotalLineSequenceNO() + "", 10)).append("\t").append(autoAddBlank(rec.getTransferNO(), 10)).append("\t").append(autoAddBlank(rec.getSapCarton(), 26));
                if (VMIReceiveInfoType.RTV == type) {
                    contextRev.append("\t").append(autoAddBlank(rec.getBin(), 26));
                }
                contextRev.append("\r\n");
            }
            if (null != contextRev) {
                String txtName = "";
                switch (type) {
                    case RECEIVE:
                        txtName = Constants.CONVERSE_RECEIVE_ST;
                        break;
                    case TRANSFEROUT:
                        txtName = Constants.CONVERSE_RECEIVE_TF;
                        break;
                    case RTV:
                        txtName = Constants.CONVERSE_RECEIVE_CSV;
                        break;
                }
                String fileName = fileDir + "//" + txtName + "_" + date + ".dat";
                File file = new File(fileName);
                writeDataToFile(file, contextRev.toString());
            }
            // 更新receive表状态字段
            updateReceiveInfoStatus(VMIReceiveInfoStatus.PROCESSING.getValue(), VMIReceiveInfoStatus.FINISH.getValue(), type.getValue());
        }
    }

    public void generateVMIAdjustmentFile(String fileDir, String date) {
        updateAdjustmentInfoStatus(VMIAdjustmentStatus.NEW.getValue(), VMIAdjustmentStatus.PROCESSING.getValue());
        List<ConverseVmiAdjustment> infos = findConverseAdjustmentInfos(VMIAdjustmentStatus.PROCESSING.getValue());
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (null != infos && infos.size() > 0) {
            // 新建生成文件的文件夹
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            StringBuilder context = new StringBuilder();
            // 循环入库的信息，根据入库信息生成数据
            for (ConverseVmiAdjustment cva : infos) {
                if (null == cva.getStoreCode()) {
                    cva.setStoreCode("");
                }
                if (null == cva.getLandedCost()) {
                    cva.setLandedCost(new BigDecimal(0));
                }
                if (null == cva.getRetailPrice()) {
                    cva.setRetailPrice(new BigDecimal(0));
                }
                context.append(autoAddBlank(cva.getRecordType(), 5)).append("\t").append(autoAddBlank(cva.getAutoReceive(), 1)).append("\t").append(autoAddBlank(cva.getStoreCode(), 10)).append("\t").append(autoAddBlank(cva.getQuantity() + "", 12))
                        .append("\t").append("").append("\t").append(autoAddBlank(cva.getQuantitySign() + "", 38)).append("\t").append("").append("\t").append(autoAddBlank(cva.getUPC(), 25)).append("\t").append(sdf.format(cva.getShippmentDate()))
                        .append("\t").append(autoAddBlank(cva.getBin(), 10)).append("\t").append(autoAddBlank(cva.getReasonCode(), 4)).append("\t").append(autoAddBlank(cva.getAdjCode(), 30)).append("\r\n");
            }
            if (null != context) {
                String fileName = fileDir + "//" + Constants.CONVERSE_ADJUSTMENT_IA + "_" + date + ".dat";
                File file = new File(fileName);
                writeDataToFile(file, context.toString());
            }
            // 更新adjustment表状态字段
            updateAdjustmentInfoStatus(VMIAdjustmentStatus.PROCESSING.getValue(), VMIAdjustmentStatus.FINISH.getValue());
        }
    }

    public void generateInvStatusChangeFile(String fileDir, String date) {
        updateInvStatusChangeInfoStatus(VMIReceiveInfoStatus.NEW.getValue(), VMIReceiveInfoStatus.PROCESSING.getValue());
        List<ConverseVmiInvStatusChangeCommand> infos = findConverseInvStatusChangeInfos(VMIReceiveInfoStatus.PROCESSING.getValue());
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (null != infos && infos.size() > 0) {
            // 新建生成文件的文件夹
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:MM:ss");

            StringBuilder context = new StringBuilder();
            // 循环入库的信息，根据入库信息生成数据
            for (ConverseVmiInvStatusChangeCommand cva : infos) {
                context.append(autoAddBlank(cva.getStoreCode(), 10)).append("\t").append("\t")
                        // productCode
                        .append("\t")
                        // color
                        .append("\t")
                        // size
                        .append(autoAddBlank(cva.getOrgi(), 1)).append("\t").append(autoAddBlank(cva.getDest(), 1)).append("\t").append(autoAddBlank(cva.getQuantity() + "", -1)).append("\t").append(sdf.format(cva.getTransDate())).append("\t")
                        .append(cva.getEanCode()).append("\r\n");
            }
            if (null != context) {
                String fileName = fileDir + "//" + Constants.CONVERSE_INVSTATUS_CHANGE + "_" + date + ".dat";
                File file = new File(fileName);
                writeDataToFile(file, context.toString());
            }
            // 更新invStatusChange表状态字段
            updateInvStatusChangeInfoStatus(VMIReceiveInfoStatus.PROCESSING.getValue(), VMIReceiveInfoStatus.FINISH.getValue());
        }
    }


    public List<ConverseVmiReceive> findConverseReceiveInfos(int status, int type) {
        return cReceiveDao.findReceiveInfosByStatus(status, type, new BeanPropertyRowMapperExt<ConverseVmiReceive>(ConverseVmiReceive.class));
    }

    public void updateReceiveInfoStatus(int fromStatus, int toStatus, int type) {
        cReceiveDao.updateReceiveInfoStatus(fromStatus, toStatus, type);
    }

    public List<ConverseVmiAdjustment> findConverseAdjustmentInfos(int status) {
        return cAdjustmentDao.findAdjustmentInfosByStatus(status, new BeanPropertyRowMapperExt<ConverseVmiAdjustment>(ConverseVmiAdjustment.class));
    }

    public List<ConverseVmiInvStatusChangeCommand> findConverseInvStatusChangeInfos(int status) {
        return statusChangeDao.findInvStatusChangeInfosByStatus(status, new BeanPropertyRowMapperExt<ConverseVmiInvStatusChangeCommand>(ConverseVmiInvStatusChangeCommand.class));
    }

    public void updateAdjustmentInfoStatus(int fromStatus, int toStatus) {
        cAdjustmentDao.updateAdjustmentInfoStatus(fromStatus, toStatus);
    }

    public void updateInvStatusChangeInfoStatus(int fromStatus, int toStatus) {
        statusChangeDao.updateInvStatusChangeInfoStatus(fromStatus, toStatus);
    }

    // 将数据写入文件
    public void writeDataToFile(File file, String data) {
        FileWriter fw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(data);
        } catch (IOException e1) {
            if (log.isErrorEnabled()) {
                log.error("Converse writeDataToFile IOException:", e1);
            }
        } finally {
            try {
                if (fw != null) fw.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        if (!inv.getType().equals(InventoryCheckType.VMI)) {
            return;
        }
        List<InventoryCheckDifferenceLine> lines = icdifLineDao.findByInventoryCheck(inv.getId());
        // List<InventoryCheckDifTotalLine> lines = icLineDao.findByInventoryCheck(inv.getId());
        Date date = new Date();
        ConverseVmiAdjustment adjustment = null;
        BiChannel shop = shopDao.getByPrimaryKey(inv.getShop().getId());
        if (shop == null) {
            return;
        }

        for (InventoryCheckDifferenceLine line : lines) {
            adjustment = new ConverseVmiAdjustment();
            adjustment.setShippmentDate(date);
            adjustment.setAdjCode(inv.getCode());
            Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
            if (sku != null) {
                adjustment.setUPC(sku.getExtensionCode2());
            }
            if (StringUtil.isEmpty(inv.getRemork())) {
                adjustment.setReasonCode("08");
            } else {
                adjustment.setReasonCode(inv.getRemork());
            }

            adjustment.setStoreCode(shop.getVmiCode());
            adjustment.setStatus(VMIReceiveInfoStatus.NEW);
            Long qty = line.getQuantity();
            if (qty >= 0) {
                adjustment.setQuantitySign("P");
                adjustment.setQuantity(qty);
            } else {
                adjustment.setQuantitySign("N");
                adjustment.setQuantity(0 - qty);
            }
            adjustment.setRecordType("11");
            adjustment.setAutoReceive("Y");
            InventoryStatus is = isDao.getByPrimaryKey(line.getStatus().getId());
            String bin = is.getName();
            if (bin.equals("良品") || bin.equals("可销售")) {
                bin = "A";
            } else if (bin.equals("残次品") || bin.equals("不可销售")) {
                bin = "C";
            } else if (bin.equals("待处理品")) {
                bin = "B";
            } else {
                bin = "A";
            }
            adjustment.setBin(bin);
            adjDao.save(adjustment);
        }

    }



    /**
     * 根据样品出入库生成调整数据
     */
    public void generateVMIReceiveInfoBySample(StockTransApplication sta) {
        // List<StaLine> lines = staLineDao.findByStaId(sta.getId());

        if (!StockTransApplicationType.SAMPLE_INBOUND.equals(sta.getType()) && !StockTransApplicationType.SAMPLE_OUTBOUND.equals(sta.getType())) {
            return;
        }

        List<StvLineCommand> lines = stvLineDao.findQtySkuByStaId(sta.getId(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        // List<InventoryCheckDifferenceLine> lines =
        // icdifLineDao.findByInventoryCheck(inv.getId());
        // List<InventoryCheckDifTotalLine> lines = icLineDao.findByInventoryCheck(inv.getId());
        Date date = new Date();
        ConverseVmiAdjustment adjustment = null;
        BiChannel shop = shopDao.getByCode(sta.getOwner());
        if (shop == null) {
            return;
        }

        for (StvLineCommand line : lines) {
            adjustment = new ConverseVmiAdjustment();
            adjustment.setShippmentDate(date);
            adjustment.setAdjCode(sta.getCode());
            if (line.getExtCode2() != null) {
                adjustment.setUPC(line.getExtCode2());
            }
            if (sta.getType().equals(StockTransApplicationType.SAMPLE_OUTBOUND)) {
                adjustment.setReasonCode("10");
                adjustment.setQuantitySign("N");
                adjustment.setQuantity(line.getQuantity());
            } else {
                adjustment.setReasonCode("11");
                adjustment.setQuantitySign("P");
                adjustment.setQuantity(line.getQuantity());
            }

            adjustment.setStoreCode(shop.getVmiCode());
            adjustment.setStatus(VMIReceiveInfoStatus.NEW);
            adjustment.setRecordType("11");
            adjustment.setAutoReceive("Y");
            // InventoryStatus is = isDao.getByPrimaryKey(line.getInvStatus().getId());
            String bin = line.getIntInvstatusName();
            if (bin.equals("良品") || bin.equals("可销售")) {
                bin = "A";
            } else if (bin.equals("残次品") || bin.equals("不可销售")) {
                bin = "C";
            } else if (bin.equals("待处理品")) {
                bin = "B";
            } else {
                bin = "A";
            }
            adjustment.setBin(bin);
            adjDao.save(adjustment);
        }

    }


    public void exportInventorySnapShot(String vmiCode, String dir) {
        List<InventoryCommand> inventotyList = inventoryDao.findCurrentConverseInventory(vmiCode, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
        if (inventotyList.size() == 0) {
            return;
        }
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "RIB_" + sdf1.format(date) + ".dat";
        File dirPath = new File(dir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        File file = new File(dir + "/" + fileName);
        writeRibFile(file, inventotyList, date, vmiCode, false);
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.CONVERSE_RP_GROUP);
        File fileSupplierSku = new File(config.get(Constants.CONVERSE_RP_FTP_UP_LOCALPATH) + "/" + fileName);
        writeRibFile(fileSupplierSku, inventotyList, date, vmiCode, true);
    }

    private void writeRibFile(File file, List<InventoryCommand> inventotyList, Date date, String vmiCode, boolean isSupplierSku) {
        StringBuilder detail = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            for (InventoryCommand ic : inventotyList) {
                detail.append(sdf.format(date)).append("\t").append("").append("\t") // TransDate,TransTime
                        .append(vmiCode).append("\t").append("").append("\t") // Store_Code,Product_Code
                        .append("").append("\t").append("").append("\t") // Product_Grid
                                                                         // Color_Description
                        .append("").append("\t").append(ic.getBarCode()).append("\t");// Size_Description,UPC

                if (isSupplierSku) {
                    detail.append("\t").append(ic.getSupplierSkuCode()).append("\t");
                }
                detail.append(ic.getQuantity()).append("\t").append("").append("\t").append(ic.getDistrictCode()).append("\r\n");// Onhand_Qty,Transit_Qty
            }
            fw.write(detail.toString());
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (fw != null) fw.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }

    }

    // 字段位数不足用空格补全
    public String autoAddBlank(String str, int length) {
        if ("".equals(str) || null == str || "null".equals(str)) {
            return "";
        }
        int strLen = 0;
        while (length > strLen) {
            str = str + " ";
            strLen = str.length();
        }
        return str;
    }

    public void generateInvStatusChange(StockTransApplication sta) {
        if (sta == null) {
            return;
        }
        List<StaLineCommand> stalines = staLineDao.findStalineByStaId2(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        ConverseVmiInvStatusChange statusChange = null;
        Date date = new Date();
        for (StaLineCommand staLine : stalines) {
            List<StvLine> stvLines = stvLineDao.findStvLinesByStaLineId(staLine.getId());
            for (StvLine stvLine : stvLines) {
                if (stvLine.getDirection().equals(TransactionDirection.INBOUND)) {
                    String innerShopCode = stvLine.getOwner();
                    String vmiCode = "";
                    if (innerShopCode != null) {
                        BiChannel shop = shopDao.getByCode(innerShopCode);
                        vmiCode = shop.getVmiCode();
                    }
                    statusChange = new ConverseVmiInvStatusChange();
                    statusChange.setOrigStatusId(staLine.getInvInvstatusId());
                    statusChange.setDestStatusId(stvLine.getInvStatus().getId());
                    statusChange.setEanCode(staLine.getBarCode());
                    statusChange.setQuantity(stvLine.getQuantity());
                    statusChange.setStatus(VMIReceiveInfoStatus.NEW);
                    statusChange.setStoreCode(vmiCode);
                    statusChange.setTransDate(sta.getFinishTime());
                    statusChange.setVersion(date);
                    statusChangeDao.save(statusChange);
                }
            }
        }
        sta.setVmiRCStatus(true);
        staDao.save(sta);
    }

    public void generateInvStatusChangeByInboundSta(StockTransApplication sta) {
        if (sta == null) {
            return;
        }
        List<StaLineCommand> stalines = staLineDao.findStalineByStaId2(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        ConverseVmiInvStatusChange statusChange = null;
        Date date = new Date();
        Warehouse wh = baseManger.findWarehouseByOuId(sta.getMainWarehouse().getId());
        Long ouId = wh.getOu().getId();
        OperationUnit ou = ouDao.getByPrimaryKey(ouId);
        Long companyId = null;
        if (ou.getParentUnit() != null) {
            OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
            if (ou1 != null) {
                companyId = ou1.getParentUnit().getId();
            }
        }
        InventoryStatus isForsale = isDao.findInvStatusForSale(companyId);
        for (StaLineCommand staLine : stalines) {
            Long isId = staLine.getInvInvstatusId();
            if (isId != null) {
                InventoryStatus is = isDao.getByPrimaryKey(isId);
                if (!is.getIsForSale()) {
                    statusChange = new ConverseVmiInvStatusChange();
                    statusChange.setOrigStatusId(isForsale.getId());
                    statusChange.setDestStatusId(is.getId());
                    statusChange.setEanCode(staLine.getBarCode());
                    statusChange.setQuantity(staLine.getQuantity());
                    statusChange.setStatus(VMIReceiveInfoStatus.NEW);
                    statusChange.setStoreCode(BiChannel.CHANNEL_VMICODE_CONVERSE_STORE);
                    statusChange.setTransDate(sta.getFinishTime());
                    statusChange.setVersion(date);
                    statusChangeDao.save(statusChange);
                }
            }
        }
        sta.setVmiRCStatus(true);
        // staDao.save(sta);
    }

    public void receiveProductPriceByMq(String message) {
        ConversePriceContrast priceContrast = null;
        try {
            String msgStr = MarshallerUtil.decodeBase64StringWithUTF8(message);
            MqMsgList msg = (MqMsgList) MarshallerUtil.buildJaxb(MqMsgList.class, msgStr);
            for (Object obj : msg.getMsgs()) {
                MqBSSkuPriceMsg skuPrice = (MqBSSkuPriceMsg) obj;
                if (skuPrice == null) {
                    return;
                } else {
                    if (skuPrice.getSupplierSkuCode() == null || skuPrice.getSupplierSkuCode().equals("")) {
                        return;
                    }
                }
                priceContrast = new ConversePriceContrast();
                priceContrast.setProduct(skuPrice.getSupplierSkuCode());
                priceContrast.setECPrice(skuPrice.getListPrice());
                priceContrast.setSendDate(skuPrice.getSendTime());
                priceContrast.setVersion(new Date());
                cpcDao.save(priceContrast);
            }


        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Override
    public void generateVMIReceiveInfoBySlipCode1(StockTransApplication sta) {
        List<StockTransApplication> stas = staDao.findBySlipCodeVmiConverse(sta.getSlipCode1(), sta.getOwner());
        for (StockTransApplication sta1 : stas) {
            if (sta1.getStatus().getValue() == 10) {

            } else if (sta1.getStatus().getValue() == 5) {

            } else {
                return;
            }
        }
        for (StockTransApplication sta1 : stas) {
            if (!sta1.getVmiRCStatus()) {
                List<StaLine> lines = staLineDao.findByStaId(sta1.getId());
                Date date = new Date();
                ConverseVmiReceive receiveInfo = null;
                for (StaLine line : lines) {
                    ConverseVmiStockIn instruction = cStockInDao.findCVSByStaLineId(line.getId());
                    receiveInfo = new ConverseVmiReceive();
                    receiveInfo.setCartonNumber(instruction.getCartonNo());
                    receiveInfo.setFromLocation(instruction.getFromLocation());
                    receiveInfo.setItemEanUpcCode(instruction.getItemEanUpcCode());
                    receiveInfo.setLineSequenceNO(instruction.getLineSequenceNO());
                    receiveInfo.setQuantity(line.getQuantity());
                    receiveInfo.setReceiveDate(date);
                    receiveInfo.setSapCarton(instruction.getCartonNo());
                    receiveInfo.setTransferNO(sta1.getSlipCode1());
                    receiveInfo.setToLocation(instruction.getToLocation());
                    // receiveInfo.setTotalLineSequenceNO(instruction.getLineSequenceNO());
                    receiveInfo.setTransferPrefix(instruction.getFromLocation());
                    receiveInfo.setStatus(VMIReceiveInfoStatus.NEW);
                    receiveInfo.setType(1);// 1为收货反馈 ；5为退仓反馈 7为转店反馈
                    cReceiveDao.save(receiveInfo);
                }
                sta1.setVmiRCStatus(Boolean.TRUE);
                staDao.flush();
            }
        }

    }

}
