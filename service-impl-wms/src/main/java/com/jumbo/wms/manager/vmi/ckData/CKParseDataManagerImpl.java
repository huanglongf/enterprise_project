package com.jumbo.wms.manager.vmi.ckData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.ckData.CKInboundFeedBackDao;
import com.jumbo.dao.vmi.ckData.CKInventoryDataDao;
import com.jumbo.dao.vmi.ckData.CKOutBoundFeedBackDao;
import com.jumbo.dao.vmi.ckData.CKProductDataDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.Validator;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.itData.ITVMIParseDataManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.ckData.CKInboundFeedBack;
import com.jumbo.wms.model.vmi.ckData.CKInventoryData;
import com.jumbo.wms.model.vmi.ckData.CKOutBoundFeedBack;
import com.jumbo.wms.model.vmi.ckData.CKProductData;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryStatus;

@Transactional
@Service("ckParseDataManager")
public class CKParseDataManagerImpl extends BaseManagerImpl implements CKParseDataManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1730563717435139220L;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;
    @Autowired
    private ITVMIParseDataManager parseDataManager;
    @Autowired
    private WareHouseManagerExe warehouseManagerExe;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private CKInboundFeedBackDao inboundDao;
    @Autowired
    private CKInventoryDataDao inventoryDao;
    @Autowired
    private CKOutBoundFeedBackDao outboundDao;
    @Autowired
    private CKProductDataDao productDao;
    @Autowired
    private MsgOutboundOrderDao outBoundOrderDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundLineDao;
    @Autowired
    private MsgInboundOrderDao inBoundOrderDao;
    @Autowired
    private CKInboundFeedBackDao ibFeedBackDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private CKInventoryDataDao ckInvDao;

    public boolean parseInBoundData(File inBoundFile, String bakDir) {
        if (!inBoundFile.exists()) {
            log.error("inbound file is not exist");
            throw new BusinessException(0, "inbound file is not exist");
        }
        boolean flag = false;
        try {
            flag = FileUtil.copyFile(inBoundFile.getAbsolutePath(), bakDir);
        } catch (Exception e) {
            log.error("inbound file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(inBoundFile);
            BufferedReader br = null;
            String fileName = inBoundFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf(File.separator) == -1 ? 0 : fileName.lastIndexOf(File.separator) + 1, fileName.length());
            bakFileName = bakDir + File.separator + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
            } catch (UnsupportedEncodingException e1) {
                if (log.isErrorEnabled()) {
                    log.error("CK parseInBoundData UnsupportedEncodingException:" + bakDir, e1);
                }
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            } catch (FileNotFoundException e1) {
                if (log.isErrorEnabled()) {
                    log.error("CK parseInBoundData FileNotFoundException:" + bakDir, e1);
                }
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            String line = null;
            CKInboundFeedBack inboundFB = null;
            int lineNum = 0;
            Date date = new Date();
            while (true) {
                try {
                    line = br.readLine();
                } catch (IOException e1) {
                    if (log.isErrorEnabled()) {
                        log.error("CK parseInBoundData IOException:" + bakDir, e1);
                    }
                }
                if (line == null || "".equals(line)) {
                    break;
                }
                lineNum++;
                String items[] = line.split(",", -1);
                if (items.length != 5) {
                    log.debug("inbound line (" + lineNum + ") read error ");
                    try {
                        if (br != null) {
                            br.close();
                        }
                        removeFile(bakFile);// 解析文件失败，把备份文件删掉
                    } catch (Exception e) {
                        log.error("", e);
                    }
                    throw new BusinessException(0, "inbound file is line:[" + lineNum + "] error");

                }
                inboundFB = new CKInboundFeedBack();
                inboundFB.setStaCode(items[0].replace("\"", "").trim());
                String dateStr = items[1].replace("\"", "").trim();
                Date inboundDate;
                try {
                    inboundDate = stringToDate(dateStr, "yyyyMMddHHmmss");
                    inboundFB.setInboundTime(inboundDate);
                } catch (ParseException e) {
                    log.error("", e);
                }
                inboundFB.setSkuCode(items[2].replace("\"", "").trim());
                String qty = items[3].replace("\"", "").trim();
                if (Validator.isDigNumber(qty)) {
                    BigDecimal quantity = new BigDecimal(qty);
                    inboundFB.setQty(quantity);
                } else {
                    inboundFB.setQty(new BigDecimal(0));
                }
                inboundFB.setInvStatus(items[4].replace("\"", "").trim());
                inboundFB.setCreateTime(date);
                inboundFB.setStatus(DefaultStatus.CREATED.getValue());
                inboundDao.save(inboundFB);
            }
            // 处理完的文件转移到另一个文件夹
            try {
                br.close();
                removeFile(inBoundFile);// 解析文件成功，把原文件删掉
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return true;
    }

    public boolean parseInventoryData(File inventoryFile, String bakDir) {
        if (!inventoryFile.exists()) {
            log.error("inventory file is not exist");
            throw new BusinessException(0, "inventory file is not exist");
        }
        boolean flag = false;
        try {
            flag = FileUtil.copyFile(inventoryFile.getAbsolutePath(), bakDir);
        } catch (Exception e) {
            log.error("inventory file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(inventoryFile);
            BufferedReader br = null;
            String fileName = inventoryFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf(File.separator) == -1 ? 0 : fileName.lastIndexOf(File.separator) + 1, fileName.length());
            bakFileName = bakDir + File.separator + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
            } catch (UnsupportedEncodingException e1) {
                log.error(" BufferedReader br is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            } catch (FileNotFoundException e1) {
                log.error(" BufferedReader br is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            String line = null;
            CKInventoryData inventoryData = null;
            int lineNum = 0;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String batchNum = sdf.format(date);
            while (true) {
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    log.error("", e);
                }
                if (line == null || "".equals(line)) {
                    break;
                }
                lineNum++;
                String items[] = line.split(",", -1);
                if (items.length != 5) {
                    log.debug("inventory line (" + lineNum + ") read error ");
                    try {
                        br.close();
                        removeFile(bakFile);// 解析文件失败，把备份文件删掉
                    } catch (Exception e) {
                        log.error("", e);
                    }
                    throw new BusinessException(0, "inventory file is line:[" + lineNum + "] error");
                }
                inventoryData = new CKInventoryData();
                inventoryData.setDataType(items[0].replace("\"", "").trim());
                inventoryData.setSkuCode(items[1].replace("\"", "").trim());
                inventoryData.setBranchCode(items[2].replace("\"", "").trim());
                inventoryData.setBranchDesc(items[3].replace("\"", "").trim());
                String qty = items[4].replace("\"", "").trim();
                if (Validator.isDigNumber(qty)) {
                    BigDecimal quantity = new BigDecimal(qty);
                    inventoryData.setQty(quantity);
                } else {
                    inventoryData.setQty(new BigDecimal(0));
                }
                inventoryData.setCreateTime(date);
                inventoryData.setStatus(DefaultStatus.CREATED.getValue());
                inventoryData.setBatchNum(batchNum);
                inventoryDao.save(inventoryData);
            }
            // 处理完的文件转移到另一个文件夹
            try {
                if (br != null) {
                    br.close();
                }
                removeFile(inventoryFile);// 解析文件成功，把原文件删掉
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return true;
    }

    public boolean parseInventoryDataForInvStart(File inventoryFile, String bakDir) {
        if (!inventoryFile.exists()) {
            log.error("inventory file is not exist");
            throw new BusinessException(0, "inventory file is not exist");
        }
        boolean flag = false;
        try {
            flag = FileUtil.copyFile(inventoryFile.getAbsolutePath(), bakDir);
        } catch (Exception e) {
            log.error("inventory file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(inventoryFile);
            BufferedReader br = null;
            String fileName = inventoryFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf(File.separator) == -1 ? 0 : fileName.lastIndexOf(File.separator) + 1, fileName.length());
            bakFileName = bakDir + File.separator + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
            } catch (UnsupportedEncodingException e1) {
                log.error(" BufferedReader br is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            } catch (FileNotFoundException e1) {
                log.error(" BufferedReader br is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            String line = null;
            CKInventoryData inventoryData = null;
            int lineNum = 0;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String batchNum = sdf.format(date);
            while (true) {
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    log.error("", e);
                }
                if (line == null || "".equals(line)) {
                    break;
                }
                lineNum++;
                if (line.startsWith("SKU") || line.startsWith("END")) {
                    continue;
                }
                String items[] = line.split("\t");
                if (items.length != 6) {
                    log.debug("inventory line (" + lineNum + ") read error ");
                    try {
                        if (br != null) {
                            br.close();
                        }
                        removeFile(bakFile);// 解析文件失败，把备份文件删掉
                    } catch (Exception e) {
                        log.error("", e);
                    }
                    throw new BusinessException(0, "inventory file is line:[" + lineNum + "] error");
                }
                inventoryData = new CKInventoryData();
                inventoryData.setDataType("ST");
                inventoryData.setSkuCode(items[0].replace("\"", "").trim());
                inventoryData.setBranchCode("100003");
                inventoryData.setBranchDesc("ECOM仓库");
                String qty = items[3].replace("\"", "").trim();
                if (Validator.isDigNumber(qty)) {
                    BigDecimal quantity = new BigDecimal(qty);
                    inventoryData.setQty(quantity);
                } else {
                    inventoryData.setQty(new BigDecimal(0));
                }
                inventoryData.setCreateTime(date);
                inventoryData.setStatus(DefaultStatus.CREATED.getValue());
                inventoryData.setBatchNum(batchNum);
                inventoryDao.save(inventoryData);
            }
            // 处理完的文件转移到另一个文件夹
            try {
                br.close();
                removeFile(inventoryFile);// 解析文件成功，把原文件删掉
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return true;
    }

    public boolean parseOutBoundData(File outBoundFile, String bakDir) {
        if (!outBoundFile.exists()) {
            log.error("outBound file is not exist");
            throw new BusinessException(0, "outBound file is not exist");
        }
        boolean flag = false;
        try {
            flag = FileUtil.copyFile(outBoundFile.getAbsolutePath(), bakDir);
        } catch (Exception e) {
            log.error("outBound file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(outBoundFile);
            BufferedReader br = null;
            String fileName = outBoundFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf(File.separator) == -1 ? 0 : fileName.lastIndexOf(File.separator) + 1, fileName.length());
            bakFileName = bakDir + File.separator + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
            } catch (UnsupportedEncodingException e1) {
                log.error("BufferedReader br is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            } catch (FileNotFoundException e1) {
                log.error("BufferedReader br is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            String line = null;
            CKOutBoundFeedBack outboundData = null;
            int lineNum = 0;
            Date date = new Date();
            while (true) {
                try {
                    line = br.readLine();
                } catch (IOException e1) {
                    if (log.isErrorEnabled()) {
                        log.error("CK parseInBoundData IOException:" + bakDir, e1);
                    }
                }
                if (line == null || "".equals(line)) {
                    break;
                }
                lineNum++;
                String items[] = line.split(",", -1);
                if (items.length != 5) {
                    log.debug("outBound line (" + lineNum + ") read error ");
                    try {
                        if (br != null) {
                            br.close();
                        }
                        removeFile(bakFile);// 解析文件失败，把备份文件删掉
                    } catch (Exception e) {
                        log.error("", e);
                    }
                    throw new BusinessException(0, "outBound file is line:[" + lineNum + "] error");
                }
                outboundData = new CKOutBoundFeedBack();
                outboundData.setStaCode(items[0].replace("\"", "").trim());
                outboundData.setLpCode(items[1].replace("\"", "").trim());
                outboundData.setTrackingNO(items[2].replace("\"", "").trim());
                String weight = items[3].replace("\"", "").trim();
                if (Validator.isDigNumber(weight)) {
                    Double wt = new Double(weight);
                    outboundData.setWeight(wt);
                } else {
                    outboundData.setWeight(new Double(0));
                }
                String dateStr = items[4].replace("\"", "").trim();
                Date inboundDate;
                try {
                    inboundDate = stringToDate(dateStr, "yyyyMMddHHmmss");
                    outboundData.setOutboundTime(inboundDate);
                } catch (ParseException e) {
                    log.error("", e);
                }

                outboundData.setCreateTime(date);
                outboundData.setStatus(DefaultStatus.CREATED.getValue());
                outboundDao.save(outboundData);
            }
            // 处理完的文件转移到另一个文件夹
            try {
                br.close();
                removeFile(outBoundFile);// 解析文件成功，把原文件删掉
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return true;
    }

    public boolean parseProductData(File productFile, String bakDir) {
        if (!productFile.exists()) {
            log.error("product file is not exist");
            throw new BusinessException(0, "product file is not exist");
        }
        boolean flag = false;
        try {
            flag = FileUtil.copyFile(productFile.getAbsolutePath(), bakDir);
        } catch (Exception e) {
            log.error("product file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(productFile);
            BufferedReader br = null;
            String fileName = productFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf(File.separator) == -1 ? 0 : fileName.lastIndexOf(File.separator) + 1, fileName.length());
            bakFileName = bakDir + File.separator + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
            } catch (UnsupportedEncodingException e1) {
                if (log.isErrorEnabled()) {
                    log.error("CK parseInBoundData UnsupportedEncodingException:" + bakDir, e1);
                }
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            } catch (FileNotFoundException e1) {
                if (log.isErrorEnabled()) {
                    log.error("CK parseInBoundData FileNotFoundException:" + bakDir, e1);
                }
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            String line = null;
            CKProductData productData = null;
            int lineNum = 0;
            Date date = new Date();
            while (true) {
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    log.error("", e);
                }
                if (line == null || "".equals(line)) {
                    break;
                }
                lineNum++;
                String items[] = line.split(",", -1);
                if (items.length != 15) {
                    log.debug("product line (" + lineNum + ") read error ");
                    try {
                        if (br != null) {
                            br.close();
                        }
                        removeFile(bakFile);// 解析文件失败，把备份文件删掉
                    } catch (Exception e) {
                        log.error("", e);
                    }
                    throw new BusinessException(0, "product file is line:[" + lineNum + "] error");
                }
                String skuCode = items[1].replace("\"", "").trim();
                productData = productDao.getCKProductDataBySkuCode(skuCode);
                if (productData == null) {
                    productData = new CKProductData();
                }
                productData.setDataType(items[0].replace("\"", "").trim());
                productData.setSkuCode(skuCode);
                productData.setDescription(items[2].replace("\"", "").trim());
                productData.setProdCode(items[3].replace("\"", "").trim());
                productData.setShortDesc(items[4].replace("\"", "").trim());
                productData.setColouCode(items[5].replace("\"", "").trim());
                productData.setColDescZH(items[6].replace("\"", "").trim());
                productData.setColourDesc(items[7].replace("\"", "").trim());
                productData.setSizesCode(items[8].replace("\"", "").trim());
                productData.setSIZESDESC(items[9].replace("\"", "").trim());
                String price = items[10].replace("\"", "").trim();
                if (Validator.isDigNumber(price)) {
                    Double detail = new Double(price);
                    productData.setOriginalSp(detail);
                } else {
                    productData.setOriginalSp(new Double(0));
                }

                productData.setLevel1Desc(items[11].replace("\"", "").trim());
                productData.setLevel2Desc(items[12].replace("\"", "").trim());
                productData.setLevel3Desc(items[13].replace("\"", "").trim());
                productData.setLevel4Desc(items[14].replace("\"", "").trim());

                productData.setCreateTime(date);
                productData.setStatus(DefaultStatus.CREATED.getValue());
                productDao.save(productData);
            }
            // 处理完的文件转移到另一个文件夹
            try {
                br.close();
                removeFile(productFile);// 解析文件成功，把原文件删掉
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return true;
    }

    // 退货入库通知
    public void writeInbound(String filePath) {
        log.debug("-------------CK inBoundNotify---------------start-------");
        // 1.读中间表
        // 查询状态为“执行中”的数据
        List<MsgInboundOrderCommand> orders = new ArrayList<MsgInboundOrderCommand>();
        // List<MsgInboundOrderCommand> orders =
        // inBoundOrderDao.findInboundBySourceANDStatus(Constants.CK_WH_SOURCE,
        // DefaultStatus.CREATED.getValue(), new
        // BeanPropertyRowMapper<MsgInboundOrderCommand>(MsgInboundOrderCommand.class));
        Date date = new Date();
        String dateStr2 = FormatUtil.formatDate(date, "yyyyMMddHHmmss");
        String fileName = "OMSR_" + dateStr2 + ".txt";
        StringBuilder sb = new StringBuilder();
        String branchCode = "";
        // 2.写到本地文件
        for (MsgInboundOrderCommand order : orders) {
            int invStatus = order.getIntStatus();
            if (invStatus == 1) { // 1,良品；0，残次品
                branchCode = Constants.CK_BRANCH_CODE_5;
            } else {
                branchCode = Constants.CK_BRANCH_CODE_4;
            }
            sb.append("OMSR").append(",") // 默认OMSR
                    .append(order.getStaCode()).append(",") // Reference
                    .append(dateStr2).append(",") // BusinessDate
                    .append(branchCode).append(",") // BRANCH_CODE
                    .append("").append(",") // BRANCH_DESC
                    .append("").append(",") // Address
                    .append("").append(",") // PO_NO（邮编）
                    .append(order.getReceiver() == null ? "" : order.getReceiver()).append(",") // RecviceBy-
                    .append(order.getMobile() == null ? "" : order.getMobile()).append(",")// TelPhone
                    .append(order.getSkuBarcode()).append(",") // SKU CODE
                    .append("").append(",") // DESCRIPTION
                    .append("").append(",") // ALT_CODE1
                    .append("").append(",") // SHORT_DESC
                    .append("").append(",") // COLOUR_CODE
                    .append("").append(",") // COLOUR_DESC
                    .append("").append(",") // COL_DESC_SHORT
                    .append("").append(",") // SIZES_CODE
                    .append("").append(",") // SIZES_DESC
                    .append(order.getQty()).append(",") // qty
                    .append(order.getTbCode() == null ? "" : order.getTbCode()).append(",") // Memo-tbCode
                    .append(order.getLpCode() == null ? "" : order.getLpCode()).append(",") // LP_CODE
                    .append(order.getTrackingNo() == null ? "" : order.getTrackingNo()) // Logistics_documents
                    .append("\r\n");

        }
        try {
            writeDataFile(sb.toString(), filePath, fileName);
        } catch (Exception e) {
            File f = new File(fileName);
            f.delete();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 将状态为“执行中”的状态改为“完成”
        for (MsgInboundOrderCommand order : orders) {
            inBoundOrderDao.updateMsgInboundStatus(order.getId(), DefaultStatus.FINISHED.getValue());
        }

        log.debug("-------------CK inBoundNotify---------------end-------");
    }

    // 销售出库通知
    public void writeOutbound(String filePath) {
        log.debug("-------------CK outBoundNotify---------------start-------");
        // 1.读中间表
        // 查询状态为“执行中”的数据
        List<MsgOutboundOrderCommand> orders = outBoundOrderDao.findMsgOutboundOrderBySource(Constants.CK_WH_SOURCE, DefaultStatus.CREATED.getValue(), -1L, new BeanPropertyRowMapper<MsgOutboundOrderCommand>(MsgOutboundOrderCommand.class));
        String fileName = "OMSS_" + FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".txt";
        StringBuilder sb = new StringBuilder();
        // Map<String , String> locsMap = new HashMap<String , String>();
        // 2.写到本地文件
        for (MsgOutboundOrderCommand order : orders) {
            String mobile = order.getMobile();
            String tele = "";
            if (order.getTelePhone() != null) {
                tele = order.getTelePhone();
            } else {
                tele = mobile;
            }
            String locationCode = "100003";
            sb.append("OMSS").append(",") // 默认OMSS
                    .append(order.getStaCode()).append(",") // Reference
                    .append("").append(",") // BusinessDate
                    .append(locationCode == null ? "" : locationCode).append(",") // BRANCH_CODE
                    .append("").append(",") // BRANCH_DESC
                    .append(order.getAddress()).append(",") // Address
                    .append(order.getZipcode()).append(",") // PO_NO（邮编）
                    .append(order.getReceiver()).append(",") // RecviceBy
                    .append(mobile == null ? "" : mobile).append(",") // TelPhone
                    .append(order.getSkuBarCode()).append(",") // SKU CODE
                    .append("").append(",") // DESCRIPTION
                    .append("").append(",") // ALT_CODE1
                    .append("").append(",") // SHORT_DESC
                    .append("").append(",") // COLOUR_CODE
                    .append("").append(",") // COLOUR_DESC
                    .append("").append(",") // COL_DESC_SHORT
                    .append("").append(",") // SIZES_CODE
                    .append("").append(",") // SIZES_DESC
                    .append(order.getQty()).append(",") // qty
                    .append(order.getUnitPrice().setScale(2).toString()).append(",") // unitPrice
                    .append(tele).append(",") // Memo
                    .append(order.getLpCode()).append("\r\n"); // LP_CODE

        }
        try {
            writeDataFile(sb.toString(), filePath, fileName);
        } catch (Exception e) {
            File f = new File(fileName);
            f.delete();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        for (MsgOutboundOrderCommand order : orders) {
            outBoundOrderDao.updateStatusById(DefaultStatus.FINISHED.getValue(), order.getId());
        }

        log.debug("-------------CK outBoundNotify---------------end-------");
    }

    /**
     * String转换date类型
     * 
     * @param str 支持格式自已定义
     * @return
     * @throws ParseException
     * @throws ParseException
     */
    public static Date stringToDate(String str, String format) throws ParseException {
        if (null == str || "".equals(str)) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(format);;
        return fmt.parse(str);
    }

    /**
     * 删除文件
     * 
     * @return
     */
    public boolean removeFile(File file) {
        return FileUtil.deleteFile(file.getAbsolutePath());
    }

    private boolean writeDataFile(String data, String localDir, String fileName) {
        if (data == null || data.trim().length() == 0) {
            log.debug("data is null ***********************  app exit");
            return false;
        }
        File dir = new File(localDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        boolean flag = false;
        if (data == null || data.length() == 0) return false;
        BufferedWriter br = null;
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        try {
            out = new FileOutputStream(localDir + File.separator + fileName, true);
            osw = new OutputStreamWriter(out, "UTF-8");
            br = new BufferedWriter(osw);
            br.write(data);
            flag = true;
        } catch (IOException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.IO_EXCEPTION);
        } finally {
            try {
                if (br != null) {
                    br.flush();
                    br.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("", e);
                throw new BusinessException(ErrorCode.IO_EXCEPTION);
            }
        }
        return flag;
    }

    public void executeIBFeedBackForData() {
        // 将中间表中未处理过的数据状态变成处理中
        ibFeedBackDao.updateInFeedbackStatus(DefaultStatus.CREATED.getValue(), DefaultStatus.EXECUTING.getValue());
        // 将中间表中状态为“处理中”的数据插入到反馈表中。
        msgRtnInboundDao.saveRtnInBoundFromTemplate(Constants.CK_WH_SOURCE, DefaultStatus.CREATED.getValue(), DefaultStatus.EXECUTING.getValue());
        // 查询仓库库存状态
        Warehouse wh = warehouseDao.getBySource(Constants.CK_WH_SOURCE, null);
        Long cmpId = wh.getOu().getParentUnit().getParentUnit().getId();
        InventoryStatus stsNotForSales = inventoryStatusDao.findByName(Constants.CK_GOOD_NOT_SALE_NAME, cmpId);
        InventoryStatus stsForDEFECTIVE = inventoryStatusDao.findByName(Constants.CK_DEFECTIVE_INV_STATUS_NAME, cmpId);
        msgRtnInboundLineDao.saveRtnInBoundLineFromTemplate(DefaultStatus.EXECUTING.getValue(), stsNotForSales.getId(), stsForDEFECTIVE.getId());
        // 将中间表中“处理中”的数据变成“完成”
        ibFeedBackDao.updateInFeedbackStatus(DefaultStatus.EXECUTING.getValue(), DefaultStatus.FINISHED.getValue());
    }

    private void executeCKInventoryCheck(InventoryCheck ic, Long ouId, String batchNum) {
        ckInvDao.updateInventoryDataStatus(DefaultStatus.CREATED.getValue(), DefaultStatus.EXECUTING.getValue(), batchNum);
        Warehouse wh = warehouseDao.getBySource(Constants.CK_WH_SOURCE, null);
        Long cmpId = wh.getOu().getParentUnit().getParentUnit().getId();
        InventoryStatus goodSts = inventoryStatusDao.findByName(Constants.CK_GOOD_INV_STATUS_NAME, cmpId);
        inventoryCheckDifferenceLineDao.saveCKinvCheckDifLine(ic.getId(), goodSts.getId(), SHOP_CODE_CK, ouId, DefaultStatus.EXECUTING.getValue(), batchNum);
        ckInvDao.updateInventoryDataStatus(DefaultStatus.EXECUTING.getValue(), DefaultStatus.FINISHED.getValue(), batchNum);
        inventoryCheckDifferenceLineDao.flush();

        Map<String, Object> invparams = new HashMap<String, Object>();
        invparams.put("in_inv_check_id", ic.getId());
        SqlParameter[] invSqlP = {new SqlParameter("in_inv_check_id", java.sql.Types.NUMERIC)};
        ckInvDao.executeSp("sp_inv_check_margen_dif_line", invSqlP, invparams);
        // 更新盘点批状态
        ic.setStatus(InventoryCheckStatus.UNEXECUTE);
        inventoryCheckDao.save(ic);
    }

    /**
     * 创库存盘点
     */
    public void generateInventoryCheck(String batchNum) {
        List<CKInventoryData> errDatas = ckInvDao.findLocCodeNotExistInLocation(DefaultStatus.CREATED.getValue(), -1L, batchNum);
        if (errDatas != null && errDatas.size() > 0) {
            log.debug("CK Invenroy Check batch Num:[" + batchNum + "]has no WH Code[" + errDatas.get(0).getBranchCode() + "]");
            throw new BusinessException();
        }
        List<CKInventoryData> invDatas = ckInvDao.findCKInvDataByStatus(DefaultStatus.CREATED.getValue(), batchNum);
        if (invDatas != null && invDatas.size() > 0) {
            // 创盘点
            InventoryCheck ic = wareHouseManagerExe.createCKInventoryCheck(-1L, "");
            // 创差异
            executeCKInventoryCheck(ic, -1L, batchNum);
            // 确认差异
            warehouseManagerExe.confirmInventoryCheck("SYSTEM", ic.getId());
        }
    }

}
