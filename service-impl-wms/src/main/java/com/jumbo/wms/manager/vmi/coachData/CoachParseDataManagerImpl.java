/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.manager.vmi.coachData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import loxia.support.excel.ExcelReader;
import loxia.support.excel.ExcelUtil;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.definition.ExcelSheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import cn.baozun.bh.connector.coach.model.CoachPrice;
import cn.baozun.bh.connector.coach.model.CoachProduct;
import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.coachData.CoachInboundInstructionDataDao;
import com.jumbo.dao.vmi.coachData.CoachPriceDataDao;
import com.jumbo.dao.vmi.coachData.CoachProductDataDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.FileUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.itData.ITVMIParseDataManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.coachData.CoachInboundInstructionData;
import com.jumbo.wms.model.vmi.coachData.CoachPriceData;
import com.jumbo.wms.model.vmi.coachData.CoachProductData;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.opensymphony.xwork2.ActionContext;
import com.sdicons.json.mapper.MapperException;

@Transactional
@Service("coachParseDataManager")
public class CoachParseDataManagerImpl extends BaseManagerImpl implements CoachParseDataManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1666520226016584494L;

    @Autowired
    private CoachProductDataDao coachProductDataDao;
    @Autowired
    private CoachPriceDataDao coachPriceDataDao;
    @Autowired
    ITVMIParseDataManager parseDataManager;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private CoachInboundInstructionDataDao coachInboundInstructDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseManagerExe wmExe;
    private ExcelReader coachParseProductImportReader;
    private ExcelReader coachParsePriceImportReader;

    @Resource(name = "coachInboundInstructionReader")
    private ExcelReader coachInboundInstructionReader;
    @Resource(name = "coachInboundSkuReader")
    private ExcelReader coachInboundSkuReader;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;

    /**
     * COACH PRODUCT 解析xml文件 param : productFile param : bakDir
     */
    @SuppressWarnings("unchecked")
    public boolean coachParseProductImport(File productFile, String bakDir) {
        log.debug("===========coachParseProductImport start============");
        if (!productFile.exists()) {
            log.error("productFile file is not exist");
            throw new BusinessException(0, "productFile file is not exist");
        }
        boolean flag = false;
        try {
            flag = FileUtil.copyFile(productFile.getAbsolutePath(), bakDir);
        } catch (Exception e) {
            log.error("", e);
            log.error("productFile file copy error");
        }

        if (flag) {
            String fileName = productFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf(File.separator) == -1 ? 0 : fileName.lastIndexOf(File.separator) + 1, fileName.length());
            bakFileName = bakDir + File.separator + bakFileName;
            File bakFile = new File(bakFileName);
            Map<String, Object> coaches = new HashMap<String, Object>();
            Date date = new Date();
            try {
                ReadStatus rs = coachParseProductImportReader.readSheet(new FileInputStream(productFile), 0, coaches);
                if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                    return false;
                }
                List<CoachProductData> stalList = (List<CoachProductData>) coaches.get("data");
                for (CoachProductData coach : stalList) {
                    coach.setCreateTime(date);
                    coach.setMasterStatus(0);
                    coachProductDataDao.save(coach);
                }
            } catch (Exception ex) {
                if (log.isErrorEnabled()) {
                    log.error("coachParseProductImport Exception:" + bakDir, ex);
                }
            }
        }

        removeFile(productFile);// 解析文件成功，把原文件删掉
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus generateCoachInventoryImport(File coachFile, Long shopId, String slipCode) {
        log.debug("===========generateCoachInventoryImport start============");
        Map<String, Object> coaches = new HashMap<String, Object>();
        List<CoachInboundInstructionData> stalList = null;
        ReadStatus rs = null;
        try {
            rs = coachInboundInstructionReader.readSheet(new FileInputStream(coachFile), 0, coaches);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                // return rs;
            }
            stalList = (List<CoachInboundInstructionData>) coaches.get("data");

            if (stalList != null && !(stalList.isEmpty())) {
                rs = validateVmiInventory(rs, stalList);
            }
            if (rs.getExceptions().size() == 0) {
                generateInboundSta(stalList, shopId, slipCode);
            } else {
                return rs;
            }
        } catch (Exception ex) {
            if (rs != null) {
                rs.getExceptions().add(ex);
            }
            if (log.isErrorEnabled()) {
                log.error("generateCoachInventoryImport Exception:" + shopId, ex);
            }
        }
        return rs;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateInboundSta(List<CoachInboundInstructionData> instructionList, Long shopId, String slipCode) throws Exception {
        OperationUnit ou = null;
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setRefSlipCode(slipCode);
        staDao.save(sta);
        // 订单状态与账号关联
        String innerShopCode = null;
        InventoryStatus is = null;
        BiChannel shop = shopDao.getByPrimaryKey(shopId);
        if (shop == null) {
            // 抛异常，报错
            throw new Exception("没有找到店铺信息!shopId:" + shopId);
        }
        wmExe.validateBiChannelSupport(null, shop.getCode());
        ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {shop.getVmiCode()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
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
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());

        Date date = new Date();
        Long skuQty = 0l;
        boolean isNoSkuError = false;
        for (CoachInboundInstructionData ins : instructionList) {
            if (ins.getSta() != null || ins.getStaLine() != null) {
                log.debug("===============this instruction has create STA================");
                throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
            }
            String skuCode = ins.getBarCode().trim();
            // 判断skuCode在系统中是否存在
            Sku sku = skuDao.getByBarcode(skuCode, customerId);
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(skuCode, shop.getVmiCode());
                isNoSkuError = true;
                continue;
            }
            log.debug("===============SKU {} CREATE SUCCESS ================", new Object[] {skuCode});
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
            ins.setCreateTime(date);
            ins.setToLocation(shop.getVmiCode());
            ins.setInnerShopCode(shop.getCode());
            ins.setStatus(5);

            coachInboundInstructDao.save(ins);
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
    }

    /**
     * 检验导入的VMI入库指令数据是否正确
     * 
     * @param rs
     * @param stalList
     * @return
     */
    private ReadStatus validateVmiInventory(ReadStatus rs, List<CoachInboundInstructionData> stalList) {
        final ExcelSheet sheets = coachInboundInstructionReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;
        for (CoachInboundInstructionData cmd : stalList) {
            if (cmd.getBarCode() == null || cmd.getBarCode().isEmpty()) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getBarCode()}));
            }
            offsetRow++;
        }
        return rs;
    }

    /**
     * 校验字符串是否是数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * COACH PRICE 解析xml文件 param : priceFile param : bakDir
     */
    @SuppressWarnings("unchecked")
    public boolean coachParsePriceImport(File priceFile, String bakDir) {
        log.debug("===========coachParsePriceImport start============");
        if (!priceFile.exists()) {
            log.error("priceFile file is not exist");
            throw new BusinessException(0, "priceFile file is not exist");
        }
        boolean flag = false;
        try {
            flag = FileUtil.copyFile(priceFile.getAbsolutePath(), bakDir);
        } catch (Exception e) {
            log.error("", e);
        }

        if (flag) {
            String fileName = priceFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf(File.separator) == -1 ? 0 : fileName.lastIndexOf(File.separator) + 1, fileName.length());
            bakFileName = bakDir + File.separator + bakFileName;
            File bakFile = new File(bakFileName);
            Map<String, Object> prices = new HashMap<String, Object>();
            try {
                ReadStatus rs = coachParsePriceImportReader.readSheet(new FileInputStream(priceFile), 0, prices);
                if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                    removeFile(bakFile);// 解析文件失败，把备份文件删掉
                    return false;
                }
                List<CoachPriceData> stalList = (List<CoachPriceData>) prices.get("data");
                Date date = new Date();
                for (CoachPriceData price : stalList) {
                    price.setCreateTime(date);
                    price.setStatus(0);
                    coachPriceDataDao.save(price);
                }
            } catch (Exception ex) {
                if (log.isErrorEnabled()) {
                    log.error("coachParsePriceImport Exception:" + bakDir, ex);
                }
            }
        }

        removeFile(priceFile);// 解析文件成功，把原文件删掉
        return true;
    }

    /**
     * COACH PRODUCT 解析csv文件 param：coachProductFile param : bakDir
     * 
     */
    public boolean parseCoachProductData(File coachProductFile, String bakDir) {
        if (!coachProductFile.exists()) {
            log.error("coachProductFile file is not exist");
            throw new BusinessException(0, "coachProductFile file is not exist");
        }
        boolean flag = false;
        try {
            flag = FileUtil.copyFile(coachProductFile.getAbsolutePath(), bakDir);
        } catch (Exception e) {
            log.error("coachProductFile file copy error");
        }
        if (flag) {
            String character = parseDataManager.getFileCharacterEnding(coachProductFile);
            BufferedReader br = null;
            String fileName = coachProductFile.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf(File.separator) == -1 ? 0 : fileName.lastIndexOf(File.separator) + 1, fileName.length());
            bakFileName = bakDir + File.separator + bakFileName;
            File bakFile = new File(bakFileName);
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
            } catch (UnsupportedEncodingException e1) {
                if (log.isErrorEnabled()) {
                    log.error("parseCoachProductData UnsupportedEncodingException:" + bakDir, e1);
                }
            } catch (FileNotFoundException e1) {
                if (log.isErrorEnabled()) {
                    log.error("parseCoachProductData FileNotFoundException:" + bakDir, e1);
                }
            }
            String line = null;
            CoachProductData coachProductData = null;
            int lineNum = 0;
            while (true) {
                if (line == null || "".equals(line)) {
                    break;
                }
                try {
                    line = br.readLine();
                } catch (IOException e1) {
                    if (log.isErrorEnabled()) {
                        log.error("Coach parseCoachProductData IOException:" + bakDir, e1);
                    }
                }
                lineNum++;
                if (lineNum == 1) continue;
                String items[] = line.split(",", -1);
                if (items.length != 53) {
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
                coachProductData = new CoachProductData();
                setCoachProductData(coachProductData, items);
                coachProductDataDao.save(coachProductData);
            }
            // 处理完的文件转移到另一个文件夹
            try {
                br.close();
                removeFile(coachProductFile);// 解析文件成功，把原文件删掉
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return true;
    }

    private void setCoachProductData(CoachProductData coachProductData, String[] items) {
        coachProductData.setDefaultAlias(items[0].trim());
        coachProductData.setStyle(items[1].trim());
        coachProductData.setStyleDesc(items[2].trim());
        coachProductData.setColor(items[3].trim());
        coachProductData.setColorDesc(items[4].trim());
        coachProductData.setStyleColor(items[5].trim());
        coachProductData.setDepartment(items[6].trim());
        coachProductData.setDepartmentDesc(items[7].trim());
        coachProductData.setClasss(items[8].trim());
        coachProductData.setDeptClass(items[9].trim());
        coachProductData.setClassDesc(items[10].trim());
        coachProductData.setSubClass(items[11].trim());
        coachProductData.setDeptClassSub(items[12].trim());
        coachProductData.setSubClassDesc(items[13].trim());
        coachProductData.setStyleIntroDate(items[14].trim());
        coachProductData.setStyleDeleteDate(items[15].trim());
        coachProductData.setIntroDate(items[16].trim());
        coachProductData.setDeleteDate(items[17].trim());
        coachProductData.setRtlPrice(Integer.valueOf(items[18] == null ? "0" : items[18].trim()));
        coachProductData.setRtlCurrencyCode(items[19].trim());
        coachProductData.setColorGroupDesc(items[20].trim());
        coachProductData.setMasterCollection(items[21].trim());
        coachProductData.setCollectionDesc(items[22].trim());
        coachProductData.setSubCollection(items[23].trim());
        coachProductData.setSubcollectionDesc(items[24].trim());
        coachProductData.setHardwareColor(items[25].trim());
        coachProductData.setHardwareColorDesc(items[26].trim());
        coachProductData.setMaterial(items[27].trim());
        coachProductData.setMaterialDesc(items[28].trim());
        coachProductData.setSilhouette(items[29].trim());
        coachProductData.setSilhouetteDesc(items[30].trim());
        coachProductData.setSignatureType(items[31].trim());
        coachProductData.setSignatureTypeDesc(items[32].trim());
        coachProductData.setGender(items[33].trim());
        coachProductData.setGenderDesc(items[34].trim());
        coachProductData.setClosureDesc(items[35].trim());
        coachProductData.setPlanExclusion(items[36].trim());
        coachProductData.setStatus(items[37].trim());
        coachProductData.setSpecialProduct(items[38].trim());
        coachProductData.setSpecialProductDesc(items[39].trim());
        coachProductData.setHandbagSize(items[40].trim());
        coachProductData.setDimLengthCM(items[41].trim());
        coachProductData.setDimHeightCM(items[42].trim());
        coachProductData.setDimWidthCM(items[43].trim());
        coachProductData.setParentStyle(items[44].trim());
        coachProductData.setStyleGroup(items[45].trim());
        coachProductData.setEssChannelExclusive(items[46].trim());
        coachProductData.setStyleFsintroDate(items[47].trim());
        coachProductData.setFactoryType(items[48].trim());
        coachProductData.setCountryCode(items[49].trim());
        coachProductData.setSourceCode(items[50].trim());
        coachProductData.setBrand(items[51].trim());
        coachProductData.setUPCCode(items[52].trim().equalsIgnoreCase("null") ? "" : this.getValue(items[52].trim()));
        Date date = new Date();
        coachProductData.setCreateTime(date);
        coachProductData.setMasterStatus(0);
    }

    /**
     * 转换科学计数法数字
     * 
     * @return
     */
    public String getValue(String num) {
        NumberFormat fomatter = new DecimalFormat();
        String[] dataList = fomatter.format(Double.parseDouble(String.valueOf(num))).split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < dataList.length; i++) {
            sb.append(dataList[i]);
        }
        return sb.toString();
    }

    private void setCoachProductData(CoachProductData coachProductData, CoachProduct coachProduct) {
        coachProductData.setDefaultAlias(coachProduct.getDefaultAlias() == null ? "" : coachProduct.getDefaultAlias().trim());
        coachProductData.setStyle(coachProduct.getStyle() == null ? "" : coachProduct.getStyle().trim());
        coachProductData.setStyleDesc(coachProduct.getStyleDesc() == null ? "" : coachProduct.getStyleDesc().trim());
        coachProductData.setColor(coachProduct.getColor() == null ? "" : coachProduct.getColor().trim());
        coachProductData.setColorDesc(coachProduct.getColorDesc() == null ? "" : coachProduct.getColorDesc().trim());
        coachProductData.setStyleColor(coachProduct.getStyleColor() == null ? "" : coachProduct.getStyleColor().trim());
        coachProductData.setDepartment(coachProduct.getDepartment() == null ? "" : coachProduct.getDepartment().trim());
        coachProductData.setDepartmentDesc(coachProduct.getDepartmentDesc() == null ? "" : coachProduct.getDepartmentDesc().trim());
        coachProductData.setClasss(coachProduct.getClazz() == null ? "" : coachProduct.getClazz().trim());
        coachProductData.setDeptClass(coachProduct.getDeptClass() == null ? "" : coachProduct.getDeptClass().trim());
        coachProductData.setClassDesc(coachProduct.getClassDesc() == null ? "" : coachProduct.getClassDesc().trim());
        coachProductData.setSubClass(coachProduct.getSubClass() == null ? "" : coachProduct.getSubClass().trim());
        coachProductData.setDeptClassSub(coachProduct.getDeptClassSub() == null ? "" : coachProduct.getDeptClassSub().trim());
        coachProductData.setSubClassDesc(coachProduct.getSubClassDesc() == null ? "" : coachProduct.getSubClassDesc().trim());
        coachProductData.setStyleIntroDate(coachProduct.getStyleIntroDate() == null ? "" : coachProduct.getStyleIntroDate().trim());
        coachProductData.setStyleDeleteDate(coachProduct.getStyleDeleteDate() == null ? "" : coachProduct.getStyleDeleteDate().trim());
        coachProductData.setIntroDate(coachProduct.getIntroDate() == null ? "" : coachProduct.getIntroDate().trim());
        coachProductData.setDeleteDate(coachProduct.getDeleteDate() == null ? "" : coachProduct.getDeleteDate().trim());
        coachProductData.setRtlPrice(coachProduct.getRtlPrice() == null ? 0 : coachProduct.getRtlPrice().intValue());
        coachProductData.setRtlCurrencyCode(coachProduct.getRtlCurrencyCode() == null ? "" : coachProduct.getRtlCurrencyCode().trim());
        coachProductData.setColorGroupDesc(coachProduct.getColorGroupDesc() == null ? "" : coachProduct.getColorGroupDesc().trim());
        coachProductData.setMasterCollection(coachProduct.getCollection() == null ? "" : coachProduct.getCollection().trim());
        coachProductData.setCollectionDesc(coachProduct.getCollectionDesc() == null ? "" : coachProduct.getCollectionDesc().trim());
        coachProductData.setSubCollection(coachProduct.getSubcollection() == null ? "" : coachProduct.getSubcollection().trim());
        coachProductData.setSubcollectionDesc(coachProduct.getSubcollectionDesc() == null ? "" : coachProduct.getSubcollectionDesc().trim());
        coachProductData.setHardwareColor(coachProduct.getHardwareColor() == null ? "" : coachProduct.getHardwareColor().trim());
        coachProductData.setHardwareColorDesc(coachProduct.getHardwareColorDesc() == null ? "" : coachProduct.getHardwareColorDesc().trim());
        coachProductData.setMaterial(coachProduct.getMaterial() == null ? "" : coachProduct.getMaterial().trim());
        coachProductData.setMaterialDesc(coachProduct.getMaterialDesc() == null ? "" : coachProduct.getMaterialDesc().trim());
        coachProductData.setSilhouette(coachProduct.getSilhouette() == null ? "" : coachProduct.getSilhouette().trim());
        coachProductData.setSilhouetteDesc(coachProduct.getSilhouetteDesc() == null ? "" : coachProduct.getSilhouetteDesc().trim());
        coachProductData.setSignatureType(coachProduct.getSignatureType() == null ? "" : coachProduct.getSignatureType().trim());
        coachProductData.setSignatureTypeDesc(coachProduct.getSignatureTypeDesc() == null ? "" : coachProduct.getSignatureTypeDesc().trim());
        coachProductData.setGender(coachProduct.getGender() == null ? "" : coachProduct.getGender().trim());
        coachProductData.setGenderDesc(coachProduct.getGenderDesc() == null ? "" : coachProduct.getGenderDesc().trim());
        coachProductData.setClosureDesc(coachProduct.getClosureDesc() == null ? "" : coachProduct.getClosureDesc().trim());
        coachProductData.setPlanExclusion(coachProduct.getPlanExclusion() == null ? "" : coachProduct.getPlanExclusion().trim());
        coachProductData.setStatus(coachProduct.getStatus() == null ? "" : coachProduct.getStatus().trim());
        coachProductData.setSpecialProduct(coachProduct.getSpecialProduct() == null ? "" : coachProduct.getSpecialProduct().trim());
        coachProductData.setSpecialProductDesc(coachProduct.getSpecialProductDesc() == null ? "" : coachProduct.getSpecialProductDesc().trim());
        coachProductData.setHandbagSize(coachProduct.getHandbagSize() == null ? "" : coachProduct.getHandbagSize().trim());
        coachProductData.setDimLengthCM(coachProduct.getDimLengthCm() == null ? "" : coachProduct.getDimLengthCm().trim());
        coachProductData.setDimHeightCM(coachProduct.getDimHeightCm() == null ? "" : coachProduct.getDimHeightCm().trim());
        coachProductData.setDimWidthCM(coachProduct.getDimWidthCm() == null ? "" : coachProduct.getDimWidthCm().trim());
        coachProductData.setParentStyle(coachProduct.getParentStyle() == null ? "" : coachProduct.getParentStyle().trim());
        coachProductData.setStyleGroup(coachProduct.getStyleGroup() == null ? "" : coachProduct.getStyleGroup().trim());
        coachProductData.setEssChannelExclusive(coachProduct.getEssChannelExclusive() == null ? "" : coachProduct.getEssChannelExclusive().trim());
        coachProductData.setStyleFsintroDate(coachProduct.getStyleFsintroDate() == null ? "" : coachProduct.getStyleFsintroDate().trim());
        coachProductData.setFactoryType(coachProduct.getFactoryType() == null ? "" : coachProduct.getFactoryType().trim());
        coachProductData.setCountryCode(coachProduct.getCountryCode() == null ? "" : coachProduct.getCountryCode().trim());
        coachProductData.setSourceCode(coachProduct.getSourceCode() == null ? "" : coachProduct.getSourceCode().trim());
        coachProductData.setSize(coachProduct.getSize() == null ? "" : coachProduct.getSize().trim());
        coachProductData.setBrand(coachProduct.getBrand() == null ? "" : coachProduct.getBrand().trim());
        coachProductData.setUPCCode(coachProduct.getUpccode() == null ? "" : coachProduct.getUpccode().trim());
        coachProductData.setCreateTime(new Date());
        coachProductData.setMasterStatus(0);
    }

    /**
     * 接收MQ Product数据并插入数据库 param : message
     */
    public void receiveCoachProductByMq(String message) {
        log.debug("coachproduct=========================== : " + message);
        CoachProductData coachProductData = null;
        log.debug("************************** coachproduct message start *********************" + message);
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            CoachProduct coachProduct = (CoachProduct) MarshallerUtil.jsonStrToObject(messageContent, CoachProduct.class);// json字符串转java对象通用方法
            coachProductData = new CoachProductData();
            setCoachProductData(coachProductData, coachProduct);
            coachProductDataDao.deleteProductStatusByBarCode(coachProductData.getUPCCode());// 先删除再保存
            coachProductDataDao.save(coachProductData);
            log.debug("************************** coachproduct message end *********************");
        } catch (TokenStreamException e) {
            log.error("", e);
        } catch (RecognitionException e) {
            log.error("", e);
        } catch (MapperException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * 接收MQ Price数据并插入数据库 param : message
     */
    public void receiveCoachPriceByMq(String message) {
        log.debug("****************************************************************");

        CoachPriceData coachPriceData = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            CoachPrice coachPrice = (CoachPrice) MarshallerUtil.jsonStrToObject(messageContent, CoachPrice.class);// json字符串转java对象通用方法
            if (coachPrice != null) {
                coachPriceData = new CoachPriceData();
                coachPriceData.setItemCode(coachPrice.getItemCode() == null ? "" : coachPrice.getItemCode().trim());
                coachPriceData.setCurrency(coachPrice.getCurrency() == null ? "" : coachPrice.getCurrency().trim());
                coachPriceData.setRetailPrice(coachPrice.getRetailPrice() == null ? 0.00 : coachPrice.getRetailPrice().doubleValue());
                coachPriceData.setCreateTime(new Date());
                coachPriceData.setStatus(0);
                coachPriceDataDao.save(coachPriceData);
            } else {
                log.debug("*********************" + message + "*********************");
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * coach price 发送mq消息插入消息中间表
     */
    public void coachPriceToPriceLog() {
        log.debug("===========coachPriceToPriceLog start============");
        int statusNum = coachPriceDataDao.updatePriceStatus(1, 0);// 将price表status值为0改为1
        int insertNum = 0;
        if (statusNum > 0) {
            BiChannel cShop = shopDao.getByVmiCode(Constants.COACH_VMI_CODE);
            insertNum = coachPriceDataDao.insertPriceLogFromCoachPrice(cShop.getId(), 1);
        }
        if (insertNum > 0) {
            coachPriceDataDao.updatePriceStatus(5, 1);
            coachPriceDataDao.updateNotRmbPriceStatus("RMB", -1);// 非RMB记录状态修改为-1
        }
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

    public Locale getLocale() {
        ActionContext ctx = ActionContext.getContext();
        if (ctx != null) {
            return ctx.getLocale();
        } else {
            // LOG.debug("Action context not initialized");
            return null;
        }
    }

    /**
     * 删除文件
     * 
     * @return
     */
    public boolean removeFile(File file) {
        return FileUtil.deleteFile(file.getAbsolutePath());
    }

    public ExcelReader getCoachParseProductImportReader() {
        return coachParseProductImportReader;
    }

    public void setCoachParseProductImportReader(ExcelReader coachParseProductImportReader) {
        this.coachParseProductImportReader = coachParseProductImportReader;
    }

    public ExcelReader getCoachParsePriceImportReader() {
        return coachParsePriceImportReader;
    }

    public void setCoachParsePriceImportReader(ExcelReader coachParsePriceImportReader) {
        this.coachParsePriceImportReader = coachParsePriceImportReader;
    }

    public Map<String, Object> transferTOmap(CoachProductData coach) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("defaultAlias", coach.getDefaultAlias());
        map.put("department", coach.getDepartment());
        map.put("departmentDesc", coach.getDepartmentDesc());
        map.put("clazz", coach.getClasss());
        map.put("deptClass", coach.getDeptClass());
        map.put("classDesc", coach.getClassDesc());
        map.put("subClass", coach.getSubClass());
        map.put("deptClassSub", coach.getDeptClassSub());
        map.put("subClassDesc", coach.getSubClassDesc());
        map.put("colorGroupDesc", coach.getColorGroupDesc());
        map.put("collection", coach.getMasterCollection());
        map.put("collectionDesc", coach.getCollectionDesc());
        map.put("subCollection", coach.getSubCollection());
        map.put("subCollectionDesc", coach.getSubcollectionDesc());
        map.put("silhouette", coach.getSilhouette());
        map.put("silhouetteDesc", coach.getSilhouetteDesc());
        map.put("handbagSize", coach.getHandbagSize());
        map.put("dimLengthCm", coach.getDimLengthCM());
        map.put("dimHeightCm", coach.getDimHeightCM());
        map.put("dimWidthCm", coach.getDimWidthCM());
        map.put("styleGroup", coach.getStyleGroup());
        map.put("factoryType", coach.getFactoryType());
        map.put("gender", coach.getGender());
        map.put("genderDesc", coach.getGenderDesc());
        return map;
    }
}
