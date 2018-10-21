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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.itData.ITVMIInstructionDao;
import com.jumbo.dao.vmi.itData.ITVMIReceiveInfoDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.itData.ITVMIInstruction;
import com.jumbo.wms.model.vmi.itData.ITVMIReceiveInfo;
import com.jumbo.wms.model.vmi.itData.VMIITReceiveCommand;
import com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

@Transactional
@Service("iTVMIReceiveInfoManager")
public class ITVMIReceiveInfoManagerImpl extends BaseManagerImpl implements ITVMIReceiveInfoManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2052553232292408581L;

    public static final Map<String, String> IT_VMI_WH_STATION;

    public static final String IT_VMI_CODE_TB = "EITCN";
    public static final String IT_VMI_CODE_BS = "EIXCN";
    static {
        IT_VMI_WH_STATION = new HashMap<String, String>();
        IT_VMI_WH_STATION.put(IT_VMI_CODE_TB, "01");
        IT_VMI_WH_STATION.put(IT_VMI_CODE_BS, "02");
    }

    @Autowired
    ITVMIReceiveInfoDao receiveInfoDao;
    @Autowired
    StaLineDao staLineDao;
    @Autowired
    StockTransApplicationDao staDao;
    @Autowired
    ITVMIInstructionDao instructionDao;
    @Autowired
    InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    ITVMIReceiveInfoDao receiveDao;
    @Autowired
    SkuDao skuDao;

    public List<Long> findNotFinishITDNStaIDs() {
        return receiveInfoDao.findStaIdListByStatus(VMIReceiveInfoStatus.NEW.getValue(), new SingleColumnRowMapper<Long>(Long.class));
    }

    public List<ITVMIReceiveInfo> findReceiveInfosByStaId(Long staId) {
        return receiveInfoDao.findReceiveInfosByStaId(staId, VMIReceiveInfoStatus.NEW.getValue());
    }

    public List<String> findTolocationByVender(String vender) {
        return receiveInfoDao.findNewTolocationByVender(vender, new SingleColumnRowMapper<String>(String.class));
    }

    public List<VMIITReceiveCommand> findITReceiveInfos(String vender, String toLocation) {
        return receiveInfoDao.findReceiveInfoGroupByTranId(VMIReceiveInfoStatus.NEW.getValue(), vender, toLocation, new BeanPropertyRowMapperExt<VMIITReceiveCommand>(VMIITReceiveCommand.class));
    }

    public void updateReceiveInfoStatusByVender(int status, String vender, String toLocation, int toStatus) {
        receiveInfoDao.updateReceiveInfoStatusByVender(status, vender, toLocation, toStatus);
    }

    public List<String> findInnerShopCodeFromReceiving() {
        return receiveInfoDao.findInnerShopCodeFromReceiving(new SingleColumnRowMapper<String>());
    }

    /**
     * 当入库指令执行完成后，创建VMIReceiveInfo
     * 
     * @param sta
     */
    public void generateVMIReceiveInfoBySta(StockTransApplication sta) {
        if (!sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT)) {
            return;
        }
        List<StaLine> lines = staLineDao.findByStaId(sta.getId());
        Date date = new Date();
        ITVMIReceiveInfo info = null;
        for (StaLine line : lines) {
            ITVMIInstruction instruction = instructionDao.findInstructionByStaLineId(line.getId());
            info = new ITVMIReceiveInfo();
            // /新增判断逻辑
            if (instruction == null) {
                // 是拆单数据1：找到主单子
                StockTransApplication s = sta.getGroupSta();
                List<StaLine> lines2 = staLineDao.findByStaId(s.getId());
                ITVMIInstruction instruction2 = instructionDao.findInstructionByStaLineId(lines2.get(0).getId());
                Sku ss = skuDao.getByPrimaryKey(line.getSku().getId());
                info.setFileName(instruction2.getFileName());
                info.setQuantity(new BigDecimal(line.getQuantity()));
                info.setSta(sta);
                info.setStaLine(line);
                info.setFromLocation(instruction2.getFromLocation());
                info.setInnerShopCode(instruction2.getInnerShopCode());
                info.setStdno(instruction2.getStdno());
                info.setStockstatus(instruction2.getStockstatus());
                info.setToLocation(instruction2.getToLocation());
                info.setTranid(instruction2.getTranid());
                info.setUPC(ss.getBarCode());
                info.setVender(instruction2.getVender());
                info.setVmiType(instruction2.getVmiType());
                info.setTxDate(date);
                info.setStatus(VMIReceiveInfoStatus.NEW);
                receiveInfoDao.save(info);
            } else {
                info.setFileName(instruction.getFileName());
                info.setQuantity(new BigDecimal(line.getQuantity()));
                info.setSta(sta);
                info.setStaLine(line);
                info.setFromLocation(instruction.getFromLocation());
                info.setInnerShopCode(instruction.getInnerShopCode());
                info.setStdno(instruction.getStdno());
                info.setStockstatus(instruction.getStockstatus());
                info.setToLocation(instruction.getToLocation());
                info.setTranid(instruction.getTranid());
                info.setUPC(instruction.getSkuCode());
                info.setVender(instruction.getVender());
                info.setVmiType(instruction.getVmiType());
                info.setTxDate(date);
                info.setStatus(VMIReceiveInfoStatus.NEW);
                receiveInfoDao.save(info);
            }
        }
        sta.setVmiRCStatus(Boolean.TRUE);
        // staDao.save(sta);
    }

    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        if (!inv.getType().equals(InventoryCheckType.VMI)) {
            return;
        }
        List<InventoryCheckDifTotalLine> lines = icLineDao.findByInventoryCheck(inv.getId());
        Date date = new Date();
        ITVMIReceiveInfo info = null;
        for (InventoryCheckDifTotalLine line : lines) {
            ITVMIInstruction instruction = instructionDao.findInstructionByInvLineId(line.getId());
            if (instruction == null) {
                throw new BusinessException(ErrorCode.VMI_ADJUSTME_INSTRUCTION_NULL);
            }
            info = new ITVMIReceiveInfo();
            info.setFileName(instruction.getFileName());
            info.setQuantity(new BigDecimal(line.getQuantity()));
            info.setInvCK(inv);
            info.setInvLine(line);
            info.setFromLocation(instruction.getFromLocation());
            info.setInnerShopCode(instruction.getInnerShopCode());
            info.setStdno(instruction.getStdno());
            info.setStockstatus(instruction.getStockstatus());
            info.setToLocation(instruction.getToLocation());
            info.setTranid(instruction.getTranid());
            info.setUPC(instruction.getSkuCode());
            info.setVender(instruction.getVender());
            info.setVmiType(instruction.getVmiType());
            info.setTxDate(date);
            info.setStatus(VMIReceiveInfoStatus.NEW);
            receiveDao.save(info);
        }
    }

    public void writeUploadFile(String fileDir, String context) {
        String fileName = fileDir + "/Upload.Ctl";
        File file = new File(fileName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            // if(file.exists()){
            fw.append(context).append("\r\n");
            // }else{
            // fw.write(context + "\r\n");
            // }
            log.debug("it upload DN Confirm File 2");

        } catch (IOException e1) {
            if (log.isErrorEnabled()) {
                log.error("IT writeUploadFile IOException:", e1);
            }
        } finally {
            try {
                if (fw != null) fw.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    public void generateReceiveConfirmationFile(String fileDir, String date) {

        // 查询当天此品牌有几个仓库的入库信息
        List<String> toLocations = findTolocationByVender(Constants.IT_VENDER);
        // 新建生成文件的文件夹
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 循环入库的仓库信息，根据仓库生成确认信息
        FileWriter fw = null;
        for (String toLocat : toLocations) {

            try {
                List<VMIITReceiveCommand> infos = findITReceiveInfos(Constants.IT_VENDER, toLocat);
                if (infos.size() > 0) {
                    String station = IT_VMI_WH_STATION.get(toLocat);
                    String fName = date + station + "_Dnrec.del";
                    String fileName = fileDir + "/" + fName;
                    File file = new File(fileName);

                    fw = new FileWriter(file);

                    StringBuilder context = new StringBuilder();
                    int num = 0;
                    for (VMIITReceiveCommand command : infos) {
                        num++;
                        context.append("\"").append(command.getToLocation()).append("\",").append(sdf2.format(command.getTxDate())).append(",\"").append(command.getStdNO()).append("\",\"").append(command.getUserKo()).append("\",\"")
                                .append(command.getTranid()).append("\",").append(command.getQuantity()).append("\r\n");
                    }
                    fw.write(context.toString());
                    String upload = fName + "," + num;
                    log.debug("it upload DN Confirm 1");
                    writeUploadFile(fileDir, upload);
                    updateReceiveInfoStatusByVender(VMIReceiveInfoStatus.NEW.getValue(), Constants.IT_VENDER, toLocat, VMIReceiveInfoStatus.FINISH.getValue());
                }

            } catch (IOException e1) {
                if (log.isErrorEnabled()) {
                    log.error("IT generateReceiveConfirmationFile IOException:", e1);
                }
            } finally {
                try {
                    if (fw != null) fw.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    public void generateRtnWh(StockTransApplication sta, String fileDir, String date, String locNo) {
        List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
        if (staLines.size() == 0) {
            return;
        }
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        FileWriter headFW = null;
        FileWriter detailFW = null;
        String headName = date + "01_Sthdr.del";
        String detailName = date + "01_Stdtl.del";
        File headFile = new File(fileDir + "/" + headName);
        File detailFile = new File(fileDir + "/" + detailName);
        try {
            headFW = new FileWriter(headFile);
            detailFW = new FileWriter(detailFile);
            StringBuilder header = new StringBuilder();
            StringBuilder detail = null;
            header.append("\"EITCN\",").append(sdf2.format(sta.getFinishTime())).append(",\"01\",") // “LOCKO”,TXDATE,”STDNO”
                    .append("\"" + sta.getId() + "\",").append("\"SR\",").append("\"00001\",") // ,”MEMONO”,”MEMTYPE”,”LOGINUSERKO”
                    .append("\"00001\",").append("\"00001\",").append("\"\",") // ”USERKO,”UPDUSERKO”,”REFLOCKO”,
                    .append("\"\",").append("\"\",").append("\"U\",") // ”REFSTDNO”,”REFMEMONO”,”POSTFLAG”,
                    .append(1).append(",\"\",").append("\"\",") // PRTCNT,”COUNTERID”,”DELVTYPE”,
                    .append("\"\",").append(1).append(",").append(",\"\""); // ”REASON”,BATCHCNT,”REFTXDATE”,”PACKAGEKO”
            headFW.write(header.toString());
            for (StaLine line : staLines) {
                detail = new StringBuilder();
                Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                detail.append("\"EITCN\",").append(sdf2.format(sta.getFinishTime())).append(",\"01\",") // “LOCKO”,TXDATE,”STDNO”,
                        .append("\"" + sta.getId() + "\",").append("\"SR\",").append(",") // ,”MEMONO”,”MEMTYPE”,SEQNO
                        .append("\"" + sku.getBarCode() + "\",").append(0).append("," + line.getCompleteQuantity()) // ”SKUKO”,IPLU,QTY,
                        .append(",\"\",").append("\"1\",").append(0); // ”COUNTERID”,”BATCHNO”,MANUAL]
                detail.append("\r\n");
                detailFW.write(detail.toString());
            }

        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (headFW != null) headFW.close();
                if (detailFW != null) detailFW.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }



    }


}
