/**
 * 
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
package com.jumbo.wms.manager.vmi.itData;

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

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.itData.ITVMIInstructionDao;
import com.jumbo.dao.vmi.itData.ITVMIReceiveInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.UnicodeReader;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.itData.ITVMIInstruction;
import com.jumbo.wms.model.vmi.itData.VMIInstructionType;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("iTVMIInstructionManager")
public class ITVMIInstructionManagerImpl extends BaseManagerImpl implements ITVMIInstructionManager {
    /**
	 * 
	 */
    private static final long serialVersionUID = 5728571344258808994L;


    @Autowired
    ITVMIInstructionDao instructionDao;
    @Autowired
    SequenceManager sequenceManager;
    @Autowired
    SkuDao skuDao;
    @Autowired
    BaseinfoManager baseinfoManager;
    @Autowired
    OperationUnitDao ouDao;
    @Autowired
    ITVMIReceiveInfoDao receiveDao;
    @Autowired
    InventoryCheckDao invDao;
    @Autowired
    BiChannelDao shopDao;
    @Autowired
    InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    WareHouseManager wareHouseManager;
    @Autowired
    WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    InventoryStatusDao inventoryStatusDao;
    @Autowired
    ITVMIParseDataManager parseDataManager;
    @Autowired
    WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    StockTransApplicationDao staDao;
    @Autowired
    StaLineDao staLineDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;

    /**
     * 导入指令文件到数据库
     */
    public void importITStockFile(String Dirpath, String bakDir) {
        if (Dirpath == null || Dirpath.equals("")) {
            log.debug("Import Stock File Dir is not exist!");
            return;
        }
        File dir = new File(Dirpath);
        File dirs[] = dir.listFiles();
        if (dirs == null || dirs.length == 0) {
            log.debug("Import Stock File Dir is not exist!");
            return;
        }
        for (File childDir : dirs) {
            if (childDir.isDirectory()) {
                List<File> fileList = FileUtil.traveFile(childDir.getAbsolutePath());
                for (File file : fileList) {
                    try {
                        importITFile(file, Constants.IT_VENDER, bakDir);
                    } catch (IOException e) {
                        log.error("", e);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                    List<File> test = FileUtil.traveFile(childDir.getAbsolutePath());
                    if (test.size() == 0) {
                        com.jumbo.util.FileUtil.deleteFile(childDir.getAbsolutePath());
                    }
                }
            }

        }
        File dirs2[] = dir.listFiles();
        if (dirs2.length == 0) {
            com.jumbo.util.FileUtil.deleteFile(dir.getAbsolutePath());
        }

    }

    /**
     * 解析I.T D/N指令
     * 
     * @param file
     * @param vender
     * @param innerShopCode
     * @param type
     * @return
     */
    public int parseITDNFile(File file, String vender, VMIInstructionType type, String bakDir) {
        int lenth = 0;
        if (!file.exists()) {
            throw new BusinessException("该文件不存在");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + file.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(file.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("brand file copy error");
        }
        if (flag) {
            String fileName = file.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            boolean isbom = parseDataManager.isBom(file);
            String character = parseDataManager.getFileCharacterEnding(file);
            BufferedReader br = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (isbom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                }

                String line = null;
                ITVMIInstruction instruction = null;
                Date importDate = new Date();
                BiChannel shop = null;
                while ((line = br.readLine()) != null) {
                    String items[] = line.split(",");
                    if (items.length != 8) {
                        throw new BusinessException("The file has error line");
                    }
                    instruction = new ITVMIInstruction();
                    instruction.setFromLocation(items[0].replace("\"", "").trim());
                    instruction.setSkuCode(items[1].replace("\"", "").trim());
                    instruction.setMemono(items[2].replace("\"", "").trim());
                    String vmiCode = items[3].replace("\"", "").trim();
                    if (vmiCode == null || vmiCode.equals("")) {
                        throw new BusinessException("The file has error TOLOCATION!!");
                    }
                    if (shop == null) {
                        shop = shopDao.getByVmiCode(vmiCode);
                        if (shop == null) {
                            throw new BusinessException("The vmiCode: " + vmiCode + " has no shop");
                        }
                    }

                    instruction.setInnerShopCode(shop.getCode());
                    instruction.setToLocation(vmiCode);
                    instruction.setTranid(items[4].replace("\"", "").trim());
                    instruction.setSkuCode(items[5].replace("\"", "").trim());
                    String quantity = items[6].replace("\"", "").trim();
                    if (quantity != null && !quantity.equals("")) {
                        instruction.setQuantity(new BigDecimal(quantity));
                    }
                    String txDate = items[7].replace("\"", "").trim();
                    if (txDate != null && !txDate.equals("")) {
                        Date date = null;
                        try {
                            date = sdf.parse(txDate);
                        } catch (ParseException e) {
                            throw new BusinessException("The file has error line");
                        }
                        if (date != null) {
                            instruction.setTxDate(date);
                        }
                    }
                    instruction.setFileName(file.getAbsolutePath());
                    instruction.setImportTime(importDate);
                    instruction.setStockstatus("良品");

                    instruction.setVender(vender);
                    instruction.setVmiType(type);

                    instructionDao.save(instruction);
                    lenth++;
                }
                // 处理完的文件转移到另一个文件夹
                try {
                    br.close();
                    parseDataManager.removeFile(file);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT parseITDNFile Exception:", e2);
                    }
                }
                parseDataManager.removeFile(bakFile);// 解析文件失败，把备份文件删掉
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

        return lenth;
    }


    /**
     * 解析库存调整文档
     * 
     * @param file
     * @return
     */
    public int parseITAdjustmentFile(File file, String vender, VMIInstructionType type, String bakDir) {
        int lenth = 0;
        if (!file.exists()) {
            throw new BusinessException("该文件不存在");
        }
        boolean flag = false;
        String bakFileDir = bakDir + "/" + file.getParentFile().getName();
        try {
            flag = FileUtil.copyFile(file.getAbsolutePath(), bakFileDir);
        } catch (Exception e) {
            log.error("brand file copy error");
        }
        if (flag) {
            String fileName = file.getName();
            String bakFileName = fileName.substring(fileName.lastIndexOf("/") == -1 ? 0 : fileName.lastIndexOf("/") + 1, fileName.length());
            bakFileName = bakFileDir + "/" + bakFileName;
            File bakFile = new File(bakFileName);
            boolean isbom = parseDataManager.isBom(file);
            String character = parseDataManager.getFileCharacterEnding(file);
            BufferedReader br = null;

            try {
                if (isbom) {
                    br = new BufferedReader(new UnicodeReader(new FileInputStream(bakFile), character));
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile), character));
                }

                String line = null;
                ITVMIInstruction instruction = null;
                Date importDate = new Date();
                String dirName = file.getParentFile().getName();
                String vmiCode = dirName.split("\\.")[0].substring(14);
                if (vmiCode == null || vmiCode.equals("")) {
                    throw new BusinessException("The file has error TOLOCATION!!");
                }

                BiChannel shop = shopDao.getByVmiCode(vmiCode);
                if (shop == null) {
                    throw new BusinessException("The vmiCode: '" + vmiCode + "' has no shop");
                }
                while ((line = br.readLine()) != null) {
                    String items[] = line.split(",");
                    if (items.length != 6) {
                        throw new BusinessException("The file has error line");
                    }
                    String skuCode = items[0].replace("\"", "").trim();
                    instruction = new ITVMIInstruction();
                    instruction.setSkuCode(skuCode);

                    String quantity = items[1].replace("\"", "").trim();
                    if (quantity != null && !quantity.equals("")) {
                        instruction.setQuantity(new BigDecimal(quantity));
                    }
                    instruction.setTranid(items[2].replace("\"", "").trim());
                    instruction.setRefNo(items[3].replace("\"", "").trim());

                    instruction.setRefInputby(items[4].replace("\"", "").trim());
                    instruction.setRefPostby(items[5].replace("\"", "").trim());
                    instruction.setFileName(file.getAbsolutePath());
                    instruction.setImportTime(importDate);

                    instruction.setInnerShopCode(shop.getCode());
                    instruction.setToLocation(vmiCode);
                    instruction.setVender(vender);
                    instruction.setVmiType(type);
                    instruction.setStockstatus("良品");
                    instructionDao.save(instruction);
                    lenth++;
                }
                try {
                    br.close();
                    parseDataManager.removeFile(file);// 解析文件成功，把原文件删掉
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (Exception e) {
                log.error("", e);
                try {
                    if (br != null) {
                        br.close();
                    }
                    parseDataManager.removeFile(bakFile);// 解析文件失败，把备份件删掉
                } catch (Exception e2) {
                    if (log.isErrorEnabled()) {
                        log.error("IT parseITAdjustmentFile Exception:", e2);
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

        return lenth;
    }



    public boolean importITFile(File file, String vender, String bakDir) throws Exception {
        if (file.getName().equals("nwwxferpool.del")) {
            // 解析文件存入数据库
            parseITDNFile(file, vender, VMIInstructionType.VMI_INBOUND, bakDir);
            return true;
        }
        if (file.getName().equals("nwstkadj.del")) {
            parseITAdjustmentFile(file, vender, VMIInstructionType.VMI_ADJUSTMENT, bakDir);
            return true;
        }
        return false;
    }

    public List<String> getNotOperaterITDNFileName(VMIInstructionType type) {
        return instructionDao.findNotOperateInstructionFileName(type.getValue(), new SingleColumnRowMapper<String>(String.class));
    }

    /**
     * 通过文件名，查找还没处理的指令
     */
    public List<ITVMIInstruction> getInstructionsByFileName(String fileName) {
        return instructionDao.findInstructionByFileName(fileName);
    }

    /**
     * 检查instruction中是否存在不存在的SKUCODE
     * 
     * @param fileName
     * @return
     */
    public boolean hasSkuNeedCreate(String fileName) {
        List<String> barCodeList = instructionDao.findFailedSkuBarCode(fileName, new SingleColumnRowMapper<String>(String.class));
        if (barCodeList != null && barCodeList.size() > 0) return true;
        return false;
    }

    public void generateSTAFromDNInstruction(String fileName) {
        List<ITVMIInstruction> instructionList = instructionDao.findGroupInstructionByFileName(fileName, new BeanPropertyRowMapperExt<ITVMIInstruction>(ITVMIInstruction.class));

        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        staDao.save(sta);
        staDao.flush();
        String innerShopCode = null;
        BiChannel shop = null;
        InventoryStatus is = null;
        Long skuQty = 0l;
        OperationUnit ou = null;
        boolean isNoSkuError = false;
        for (ITVMIInstruction ins : instructionList) {
            if (ins.getSta() != null || ins.getStaLine() != null) {
                log.debug("===============this instruction has create STA================");
                throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
            }
            if (ins.getVmiType() == null || ins.getVmiType() != VMIInstructionType.VMI_INBOUND) {
                log.debug("===============This VMI Instruction is not DN instruction================");
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
            wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
            ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
            if (ou == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {vmiCode});
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
            String skuCode = ins.getSkuCode();
            Sku sku = skuDao.getByBarcode(skuCode, customerId);
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(skuCode, shop.getVmiCode());
                isNoSkuError = true;
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setQuantity(ins.getQuantity().longValue());
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            innerShopCode = ins.getInnerShopCode();

            staLine.setOwner(innerShopCode);
            staLine.setInvStatus(is);
            staLineDao.save(staLine);
            log.debug("===============staLine sku:{}({}) ================", new Object[] {sku.getBarCode(), ins.getQuantity().longValue()});
            skuQty += staLine.getQuantity();
            instructionDao.flush();
            instructionDao.updateDNSta(sta.getId(), staLine.getId(), fileName, skuCode);
            // ins.setStaLine(staLine);
            // ins.setSta(sta);
            // instructionDao.save(ins);
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
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());

    }

    public void generateInventoryCheckFromITSA(String fileName) {
        List<ITVMIInstruction> instructionList = getInstructionsByFileName(fileName);
        OperationUnit ou = null;
        InventoryCheck ic = new InventoryCheck();
        ic.setCreateTime(new Date());
        ic.setStatus(InventoryCheckStatus.CREATED);
        ic.setType(InventoryCheckType.VMI);
        ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));
        ic.setRemork("i.t adjustment");

        invDao.save(ic);
        InventoryCheckDifTotalLine line = null;
        BiChannel shop = null;
        boolean isNoSkuError = false;
        for (ITVMIInstruction ins : instructionList) {
            if (ins.getVmiType() == null || ins.getVmiType() != VMIInstructionType.VMI_ADJUSTMENT) {
                log.debug("===============This VMI Instruction is not Store adjustment instruction================");
                throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
            }
            String toLocation = ins.getToLocation();
            if (toLocation == null || toLocation.equals("")) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {toLocation});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            shop = shopDao.getByVmiCode(ins.getToLocation());
            if (shop == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {toLocation});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
            ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
            if (ou == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {toLocation});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
            Sku sku = skuDao.getByBarcode(ins.getSkuCode(), customerId);
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(ins.getSkuCode(), shop.getVmiCode());
                isNoSkuError = true;
                continue;
            }
            line = new InventoryCheckDifTotalLine();
            line.setInventoryCheck(ic);
            line.setSku(sku);
            line.setQuantity(ins.getQuantity().longValue());
            icLineDao.save(line);
            ins.setInventoryCheck(ic);
            ins.setIcLine(line);
            instructionDao.save(ins);
        }
        if (isNoSkuError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        ic.setShop(shop);
        ic.setOu(ou);
        invDao.save(ic);
    }

    @Override
    public void generateITSkuInstruction(String fileName) {
        List<ITVMIInstruction> instructionList = instructionDao.findITSkuNotInInstruction(fileName, new BeanPropertyRowMapper<ITVMIInstruction>(ITVMIInstruction.class));
        BiChannel shop = null;
        for (ITVMIInstruction ins : instructionList) {
            shop = shopDao.getByVmiCode(ins.getToLocation());
            if (shop == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {ins.getToLocation()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            baseinfoManager.sendMsgToOmsCreateSku(ins.getSkuCode(), shop.getVmiCode());
        }
    }
}
