package com.jumbo.wms.manager.vmi.converseData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.converseData.ConverseEverGreenDao;
import com.jumbo.dao.vmi.converseData.ConverseListPriceChangeDao;
import com.jumbo.dao.vmi.converseData.ConversePriceChangeDao;
import com.jumbo.dao.vmi.converseData.ConverseProductInfoDao;
import com.jumbo.dao.vmi.converseData.ConverseVmiStockInDao;
import com.jumbo.dao.vmi.converseData.ConverseVmiStyleDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.Validator;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.itData.ITVMIParseDataManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.vmi.converseData.ConverseEverGreen;
import com.jumbo.wms.model.vmi.converseData.ConverseListPriceChange;
import com.jumbo.wms.model.vmi.converseData.ConversePriceChange;
import com.jumbo.wms.model.vmi.converseData.ConverseProductInfo;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiStockIn;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiStyle;
import com.jumbo.wms.model.vmi.itData.ConversePriceChangeStatus;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("converseVmiStockInManager")
public class ConverseVmiStockInManagerImpl extends BaseManagerImpl implements ConverseVmiStockInManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5607828776465924604L;

    @Autowired
    private ConverseVmiStockInDao converseStockInDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private ITVMIParseDataManager parseDataManager;
    @Autowired
    private ConverseVmiStyleDao converseVmiStyleDao;
    @Autowired
    private ConverseVmiStockInDao converseVmiStockInDao;
    @Autowired
    private ConversePriceChangeDao conversePriceChangeDao;
    @Autowired
    private ConverseEverGreenDao converseEverGreenDao;
    @Autowired
    private SkuBarcodeDao barCodeDao;
    @Autowired
    private ConverseProductInfoDao productInfoDao;
    @Autowired
    private ConverseListPriceChangeDao listPriceDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseManagerExe wmExe;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;

    public static Long brandId = 2747L;

    public List<String> findCartonNoList() {
        return converseStockInDao.findCartonNoList(new SingleColumnRowMapper<String>());
    }

    public List<ConverseVmiStockIn> findConverseInstructListByCartonNo(String referenceNo) {
        return converseStockInDao.findConverseInstructListByCartonNo(referenceNo);
    }

    public void generateInboundSta(String cartonNo) {
        List<ConverseVmiStockIn> instructionList = findConverseInstructListByCartonNo(cartonNo);
        Boolean msg = false;
        List<StockTransApplication> staList = staDao.findBySlipCodeStatus(cartonNo);
        if (null != staList && staList.size() > 0) {
            msg = true;
        }
        if (msg) {
            throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
        }
        OperationUnit ou = null;
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setRefSlipCode(cartonNo);
        sta.setSlipCode1(instructionList.get(0).getTransferNO());
        staDao.save(sta);
        // 订单状态与账号关联
        String innerShopCode = null;
        BiChannel shop = null;
        InventoryStatus is = null;
        Long skuQty = 0l;
        boolean isNoSkuError = false;
        for (ConverseVmiStockIn ins : instructionList) {
            if (ins.getSta() != null || ins.getStaLine() != null) {
                log.debug("===============this instruction has create STA================");
                throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
            }
            String vmiCode = ins.getToLocation();
            if (vmiCode == null || vmiCode.equals("")) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {vmiCode});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            if (shop == null) {
                shop = shopDao.getByVmiCode(vmiCode);
                if (shop == null) {
                    log.debug("=========VMICODE {} NOT FOUNT SHOP===========", new Object[] {vmiCode});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
            }
            wmExe.validateBiChannelSupport(null, shop.getCode());
            ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
            if (ou == null) {
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
            Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
            if (is == null) {
                Long companyId = null;
                if (ou.getParentUnit() != null) {
                    OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                    if (ou1 != null) {
                        companyId = ou1.getParentUnit().getId();
                    }
                }
                is = inventoryStatusDao.findInvStatusForSale(companyId);
            }

            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(ins.getItemEanUpcCode(), customerId, shop.getId());
            if (sku == null) {
                SkuBarcode skuBarCode = barCodeDao.findByBarCode(ins.getItemEanUpcCode(), customerId);
                if (skuBarCode != null && skuBarCode.getSku() != null) {
                    sku = skuDao.getByPrimaryKey(skuBarCode.getSku().getId());
                }
            }
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(ins.getItemEanUpcCode(), shop.getVmiCode());
                isNoSkuError = true;
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setQuantity(ins.getQuantity().longValue());
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            innerShopCode = shop.getCode();

            staLine.setOwner(innerShopCode);
            staLine.setInvStatus(is);
            staLineDao.save(staLine);
            skuQty += staLine.getQuantity();
            ins.setStaLine(staLine);
            ins.setSta(sta);
            converseStockInDao.save(ins);
        }
        if (isNoSkuError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        sta.setSkuQty(skuQty);
        log.debug("===============sta {} create success ================", new Object[] {sta.getCode()});
        if (StringUtils.hasText(innerShopCode) && ou != null && is != null) {
            sta.setOwner(innerShopCode);
            sta.setMainWarehouse(ou);
            sta.setMainStatus(is);
            staDao.save(sta);
            staDao.flush();
            staDao.updateSkuQtyById(sta.getId());
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 更新多条码
        // barCodeDao.createConverseBarCodes();
    }

    /**
     * 吊牌价修改
     */
    public boolean parseListpriceData(File listPriceFile, String bakDir) {
        if (!listPriceFile.exists()) {
            log.error("listPrice file is not exist");
            throw new BusinessException(0, "listPrice file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + listPriceFile.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(listPriceFile.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("listPrice file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(listPriceFile);
            BufferedReader br = null;
            String fileName = listPriceFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                String line = null;
                ConverseListPriceChange listprice = null;
                int lineNum = 0;
                Date date = new Date();
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split(",", -1);
                    if (items.length < 34) {
                        log.debug(getDate() + "listPrice File line (" + lineNum + ") read error ");
                        continue;
                    }
                    listprice = new ConverseListPriceChange();
                    listprice.setVersion(date);
                    listprice.setStatus(ConversePriceChangeStatus.NEW);
                    listprice.setStyleId(items[0].replace("\"", "").trim());
                    listprice.setSkuNO(items[1].replace("\"", "").trim());
                    String price = items[7].replace("\"", "").trim();
                    listprice.setColor(items[8].replace("\"", "").trim());
                    if (Validator.isDigNumber(price)) {
                        BigDecimal bd = new BigDecimal(price);
                        listprice.setPrice(bd);

                    } else {
                        listprice.setPrice(new BigDecimal(0.0));
                    }
                    listPriceDao.save(listprice);
                }
                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    removeFile(listPriceFile);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("Converse parseListpriceData IOException:", e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return true;
    }

    /**
     * 解析文件导入数据库
     */
    public boolean parseStyleData(File styleFile, String bakDir) {
        if (!styleFile.exists()) {
            log.error("style file is not exist");
            throw new BusinessException(0, "style file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + styleFile.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(styleFile.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("style file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(styleFile);
            BufferedReader br = null;
            String fileName = styleFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                String line = null;
                ConverseVmiStyle style = null;
                int lineNum = 0;
                Date date = new Date();
                String batchNum = FormatUtil.formatDate(new Date(), "yyyy-MM-dd");
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    log.debug("import style :" + lineNum);
                    String items[] = line.split(",", -1);
                    if (items.length < 34) {
                        log.debug(getDate() + "styleFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    style = new ConverseVmiStyle();
                    style.setStyleId(items[0].replace("\"", "").trim());
                    style.setSkuNO(items[1].replace("\"", "").trim());
                    style.setCHDescription(items[2].replace("\"", "").trim());
                    style.setENDescription(items[3].replace("\"", "").trim());
                    style.setBaseUint(items[4].replace("\"", "").trim());
                    style.setItemCategory(items[5].replace("\"", "").trim());
                    style.setPordectGroupCode(items[6].replace("\"", "").trim());
                    String price = items[7].replace("\"", "").trim();
                    if (Validator.isDigNumber(price)) {
                        BigDecimal bd = new BigDecimal(price);
                        style.setRetailPrice(bd);
                    } else {
                        style.setRetailPrice(new BigDecimal(0.0));
                    }
                    style.setColor(items[8].replace("\"", "").trim());
                    style.setEnColorDes(items[9].replace("\"", "").trim());
                    style.setChColorDes(items[10].replace("\"", "").trim());
                    style.setStyleSize(items[11].replace("\"", "").trim());
                    style.setCollection(items[12].replace("\"", "").trim());
                    style.setSeason(items[13].replace("\"", "").trim());
                    style.setTheme(items[14].replace("\"", "").trim());
                    style.setBrandCode(items[15].replace("\"", "").trim());
                    style.setGenderCode(items[16].replace("\"", "").trim());
                    style.setQuotaCategory(items[17].replace("\"", "").trim());
                    style.setProdClassCode(items[18].replace("\"", "").trim());
                    style.setLocalFlag(items[19].replace("\"", "").trim());
                    style.setSmuFlag(items[20].replace("\"", "").trim());
                    style.setMarketingRegion(items[21].replace("\"", "").trim());
                    style.setAgeGroup(items[22].replace("\"", "").trim());
                    style.setMfgProcess(items[23].replace("\"", "").trim());
                    style.setConsumerGroup(items[24].replace("\"", "").trim());
                    style.setPfg(items[25].replace("\"", "").trim());
                    style.setLifeCycleCode(items[26].replace("\"", "").trim());
                    style.setCut(items[27].replace("\"", "").trim());
                    style.setSrNumber(items[28].replace("\"", "").trim());
                    style.setUpcCode(items[29].replace("\"", "").trim());
                    String wholePrice = items[30].replace("\"", "").trim();
                    if (Validator.isDigNumber(wholePrice)) {
                        BigDecimal bdPrice = new BigDecimal(wholePrice);
                        style.setWholeSalePrice(bdPrice);
                    } else {
                        style.setWholeSalePrice(new BigDecimal(0.0));
                    }
                    style.setPictureName(items[31].replace("\"", "").trim());
                    String dateStr = items[32].replace("\"", "").trim();
                    Date deliveryDate;
                    try {
                        deliveryDate = stringToDate(dateStr, "yy-MM-dd");
                    } catch (ParseException e) {
                        log.error("", e);
                        continue;
                    }
                    style.setDeliveryDate(deliveryDate);
                    style.setEanCode(items[33].replace("\"", "").trim());
                    style.setVersion(date);
                    style.setBatchNum(batchNum);
                    converseVmiStyleDao.save(style);
                }
                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    removeFile(styleFile);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("Converse parseStyleData IOException:", e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return true;
    }

    /**
     * 解析文件导入数据库
     */
    public boolean parseStockInData(File stockInFile, String bakDir) {
        if (!stockInFile.exists()) {
            log.error("stockIn file is not exist");
            throw new BusinessException(0, "stockIn file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + stockInFile.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(stockInFile.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("stockIn file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(stockInFile);
            BufferedReader br = null;
            String fileName = stockInFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                String line = null;
                ConverseVmiStockIn stockIn = null;
                Date date = new Date();
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split("\t");
                    if (items.length < 7) {
                        log.debug(getDate() + "StockInFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    stockIn = new ConverseVmiStockIn();
                    stockIn.setCartonNo(items[0].replace("\"", "").trim());
                    String receiveDateStr = items[1].replace("\"", "").trim();
                    Date receiveDate = new Date();
                    try {
                        receiveDate = stringToDate(receiveDateStr, "yyyyMMdd");
                    } catch (ParseException e) {
                        log.error("", e);
                        continue;
                    }
                    stockIn.setReceiveDate(receiveDate);
                    stockIn.setFromLocation(items[2].replace("\"", "").trim());
                    stockIn.setToLocation(items[3].replace("\"", "").trim());
                    stockIn.setItemEanUpcCode(items[4].replace("\"", "").trim());
                    String quantityStr = items[5].replace("\"", "").trim();
                    double quantity = 0.00;
                    if (Validator.isDigNumber(quantityStr)) {
                        quantity = new Double(quantityStr);
                    }
                    stockIn.setQuantity(quantity);
                    String lineSeqStr = items[6].replace("\"", "").trim();
                    double lineSeq = 0.00;
                    if (Validator.isDigNumber(lineSeqStr)) {
                        lineSeq = new Double(lineSeqStr);
                    }
                    stockIn.setLineSequenceNO(lineSeq);
                    stockIn.setTransferNO(items[7].replace("\"", "").trim());
                    stockIn.setVersion(date);
                    converseVmiStockInDao.save(stockIn);
                }
                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    removeFile(stockInFile);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("Converse parseStockInData Exception:", e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return true;
    }

    /**
     * 解析文件导入数据库
     */
    public boolean parsePriceChangeData(File priceChangeFile, String bakDir) {
        if (!priceChangeFile.exists()) {
            log.error("priceChange file is not exist");
            throw new BusinessException(0, "priceChange file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + priceChangeFile.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(priceChangeFile.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("priceChange file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(priceChangeFile);
            BufferedReader br = null;
            String fileName = priceChangeFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                String line = null;
                ConversePriceChange priceChange = null;
                Date date = new Date();
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split("\t", -1);
                    if (items.length != 12) {
                        log.debug(getDate() + "priceChangeFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    priceChange = new ConversePriceChange();
                    priceChange.setCountryCode(items[0].replace("\"", "").trim());
                    priceChange.setGridName(items[1].replace("\"", "").trim());
                    priceChange.setStoreCode(items[2].replace("\"", "").trim());
                    priceChange.setProductCode(items[3].replace("\"", "").trim());
                    priceChange.setColorCode(items[4].replace("\"", "").trim());
                    priceChange.setSizeCode(items[5].replace("\"", "").trim());
                    priceChange.setSku(items[6].replace("\"", "").trim());
                    priceChange.setPriceType(items[7].replace("\"", "").trim());
                    priceChange.setCurrencyCode(items[8].replace("\"", "").trim());
                    String priceStr = items[9].replace("\"", "").trim();
                    double price = 0.00;
                    if (Validator.isDigNumber(priceStr)) {
                        price = new Double(priceStr);
                    }
                    priceChange.setPrice(price);
                    String tsStartDateStr = items[10].replace("\"", "").trim();
                    String tsEndDateStr = items[11].replace("\"", "").trim();
                    Date tsStartDate = new Date();
                    Date tsEndDate = new Date();
                    try {
                        tsStartDate = stringToDate(tsStartDateStr, "yyyy/M/d h:mm");
                        tsEndDate = stringToDate(tsEndDateStr, "yyyy/M/d h:mm");
                    } catch (ParseException e) {
                        log.error("", e);
                        continue;
                    }
                    priceChange.setTsStart(tsStartDate);
                    priceChange.setTsEnd(tsEndDate);
                    priceChange.setVersion(date);
                    priceChange.setStatus(ConversePriceChangeStatus.NEW);
                    conversePriceChangeDao.save(priceChange);
                }
                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    removeFile(priceChangeFile);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("Converse parsePriceChangeData Exception:", e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return true;
    }

    /**
     * 解析文件导入数据库
     */
    @Transactional
    public boolean parseEverGreenData(File everGreenFile, String bakDir) {
        if (!everGreenFile.exists()) {
            log.error("everGreen file is not exist");
            throw new BusinessException(0, "everGreen file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + everGreenFile.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(everGreenFile.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("everGreen file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(everGreenFile);
            BufferedReader br = null;
            String fileName = everGreenFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                String line = null;
                ConverseEverGreen everGreen = null;
                Date date = new Date();
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split("\t", -1);
                    if (items.length != 5) {
                        log.debug(getDate() + "everGreenFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    everGreen = new ConverseEverGreen();
                    everGreen.setStyle(items[0].replace("\"", "").trim());
                    everGreen.setColor(items[1].replace("\"", "").trim());
                    everGreen.setSize(items[2].replace("\"", "").trim());
                    everGreen.setSku(items[3].replace("\"", "").trim());
                    everGreen.setUpc(items[4].replace("\"", "").trim());
                    everGreen.setVersion(date);
                    converseEverGreenDao.save(everGreen);
                }
                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    removeFile(everGreenFile);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("Converse parseEverGreenData Exception:", e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return true;
    }

    @Transactional
    public boolean parseProductInfoData(File productInfoFile, String bakDir) {
        if (!productInfoFile.exists()) {
            log.error("everGreen file is not exist");
            throw new BusinessException(0, "everGreen file is not exist");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + productInfoFile.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(productInfoFile.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("everGreen file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(productInfoFile);
            BufferedReader br = null;
            String fileName = productInfoFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                String line = null;
                ConverseProductInfo productInfo = null;
                Date date = new Date();
                int lineNum = 0;
                while ((line = br.readLine()) != null) {
                    lineNum++;
                    String items[] = line.split("\t", -1);
                    if (items.length != 30) {
                        log.debug(getDate() + "everGreenFile line (" + lineNum + ") read error ");
                        continue;
                    }
                    String skuCode = items[2].replace("\"", "").trim();
                    productInfo = productInfoDao.findBySkuCode(skuCode);
                    if (productInfo == null) {
                        productInfo = new ConverseProductInfo();
                    }
                    productInfo.setLineNO(items[0].replace("\"", "").trim());
                    productInfo.setBu(items[1].replace("\"", "").trim());
                    productInfo.setSkuCode(items[2].replace("\"", "").trim());
                    productInfo.setCnName(items[3].replace("\"", "").trim());
                    productInfo.setEnName(items[4].replace("\"", "").trim());
                    productInfo.setIfEvengreen(items[5].replace("\"", "").trim());
                    productInfo.setCnColor(items[6].replace("\"", "").trim());
                    productInfo.setEnColor(items[7].replace("\"", "").trim());
                    productInfo.setCnMaterial(items[8].replace("\"", "").trim());
                    productInfo.setEnMaterial(items[9].replace("\"", "").trim());
                    productInfo.setCnStyle(items[10].replace("\"", "").trim());
                    productInfo.setEnStyle(items[11].replace("\"", "").trim());
                    productInfo.setCnCategory(items[12].replace("\"", "").trim());
                    productInfo.setEnCategory(items[13].replace("\"", "").trim());
                    productInfo.setCategory2(items[14].replace("\"", "").trim());
                    productInfo.setCnStory(items[15].replace("\"", "").trim());
                    productInfo.setEnStory(items[16].replace("\"", "").trim());
                    productInfo.setCnDescription(items[17].replace("\"", "").trim());
                    productInfo.setEnDescription(items[18].replace("\"", "").trim());
                    productInfo.setCnGender(items[19].replace("\"", "").trim());
                    productInfo.setEnGender(items[20].replace("\"", "").trim());
                    productInfo.setActualSize(items[21].replace("\"", "").trim());
                    productInfo.setRetailPrice(items[22].replace("\"", "").trim());
                    productInfo.setLaunchDate(items[23].replace("\"", "").trim());
                    productInfo.setLaunchDate2(items[24].replace("\"", "").trim());
                    productInfo.setLaunchDate3(items[25].replace("\"", "").trim());
                    productInfo.setSpecialStore(items[26].replace("\"", "").trim());
                    productInfo.setPrimaryStyle(items[27].replace("\"", "").trim());
                    productInfo.setCnPage(items[28].replace("\"", "").trim());
                    productInfo.setIsQtyMultipleOf6(items[29].replace("\"", "").trim());
                    productInfo.setVersion(date);

                    productInfoDao.save(productInfo);
                }
                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    removeFile(productInfoFile);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("Converse parseProductInfoData Exception:", e2);
                    }
                }
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return true;
    }


    /**
     * 获取当天时间
     * 
     * @return
     */
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        return sdf.format(today);
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

    public void changeStyleListPrice() {
        listPriceDao.updateCLPCStatus(ConversePriceChangeStatus.NEW.getValue(), ConversePriceChangeStatus.EXECUTING.getValue());

        // 更新style中间表的RTL_PRICE
        converseVmiStyleDao.updateRetailPriceByListPriceChange(ConversePriceChangeStatus.EXECUTING.getValue());
        // 更新SKUPrice
        // List<ProductCommand> productList = null;
        // List<ProductCommand> productList =
        // productDao.findConverseProductByStyle(ConversePriceChangeStatus.EXECUTING.getValue(),
        // brandId, 2, new BeanPropertyRowMapperExt<ProductCommand>(ProductCommand.class));
        // Date date = new Date();
        // for (ProductCommand product : productList) {
        // // productDao.updateProductListPrice(product.getId(), product.getNewPrice());
        // conversePriceChangeDao.updateOldPriceById(product.getStyle(), product.getColor(),
        // product.getListPrice());
        // // 写入发送MQ中间表
        // MqSkuPriceLog priceLog = new MqSkuPriceLog();
        // priceLog.setEffDate(date);
        // priceLog.setShopId(Constants.CNV_BS_SHOP_ID);
        // priceLog.setRetailPrice(product.getNewPrice());
        // priceLog.setSupplierSkuCode(product.getSupplierSkuCode());
        // priceLog.setJmCode(product.getCode());
        // priceLog.setOpType(5);
        // mqPriceLogDao.save(priceLog);
        // }
        listPriceDao.flush();
        listPriceDao.updateCLPCStatus(ConversePriceChangeStatus.EXECUTING.getValue(), ConversePriceChangeStatus.FINISH.getValue());
    }

}
