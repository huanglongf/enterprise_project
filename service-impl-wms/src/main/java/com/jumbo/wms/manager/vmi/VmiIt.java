package com.jumbo.wms.manager.vmi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.vmi.itData.ITVMIInstructionDao;
import com.jumbo.wms.manager.vmi.itData.ITVMIInstructionManager;
import com.jumbo.wms.manager.vmi.itData.ITVMIReceiveInfoManager;
import com.jumbo.wms.model.vmi.itData.ITVMIInstruction;
import com.jumbo.wms.model.vmi.itData.VMIInstructionType;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;


public class VmiIt extends VmiBaseBrand {

    /**
     * 
     */
    private static final long serialVersionUID = -5774570921752038621L;

    protected static final Logger log = LoggerFactory.getLogger(VmiIt.class);

    @Autowired
    private ITVMIInstructionManager itManager;
    @Autowired
    private ITVMIReceiveInfoManager receivingManager;
    @Autowired
    private ITVMIInstructionDao instructionDao;

    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        receivingManager.generateVMIReceiveInfoBySta(sta);
    }

    /**
     * 根据中间表创建入库单据
     */
    @Override
    public void generateInboundSta() {
        // 执行入库指令
        generateITSTAByInstruction(VMIInstructionType.VMI_INBOUND);
        // 执行调整指令
        generateITInvetoryCheckByInstruction(VMIInstructionType.VMI_ADJUSTMENT);
    }

    // 检查商品是否存在商品表中,不存在就反馈PAC生成商品
    // public void generateITSkuInstruction() {
    // List<String> fileNameListInbound =
    // itManager.getNotOperaterITDNFileName(VMIInstructionType.VMI_INBOUND);
    // List<String> fileNameListAdjustment =
    // itManager.getNotOperaterITDNFileName(VMIInstructionType.VMI_ADJUSTMENT);
    // fileNameListInbound.addAll(fileNameListAdjustment);
    // for (String fileName : fileNameListInbound) {
    // try {
    // log.debug("===============generate IT Sku Instruction=================== : " + fileName);
    // itManager.generateITSkuInstruction(fileName);
    // } catch (Exception e) {
    // log.error("generateITSkuInstruction error-->", e);
    // }
    // }
    // }

    /**
     * 创建入库单
     * 
     * @param type
     */
    public void generateITSTAByInstruction(VMIInstructionType type) {
        List<String> fileNameList = itManager.getNotOperaterITDNFileName(type);
        for (String fileName : fileNameList) {
            if (type.equals(VMIInstructionType.VMI_INBOUND)) {
                try {
                    log.debug("====================fileName========================== : " + fileName);
                    itManager.generateSTAFromDNInstruction(fileName);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 创建调整单据
     * 
     * @param type
     */
    public void generateITInvetoryCheckByInstruction(VMIInstructionType type) {
        List<String> fileNameList = itManager.getNotOperaterITDNFileName(type);
        for (String fileName : fileNameList) {
            if (type.equals(VMIInstructionType.VMI_ADJUSTMENT)) {
                try {
                    log.debug("==============Create IT ADJUSTMENT START================");
                    itManager.generateInventoryCheckFromITSA(fileName);
                    log.debug("==============Create IT ADJUSTMENT END================");
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        receivingManager.generateVMIReceiveInfoByInvCk(inv);
    }

    /**
     * 入库作业当上架时反馈
     * 
     * @param sta
     * @param stv
     */
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        // DO NOTHING
    }

    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {
        // DO NOTHING
    }

    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {
        for (Entry<String, Long> ent : lineDetial.entrySet()) {
            String skuCode = ent.getKey();
            Long stalineId = ent.getValue();
            instructionDao.updateDNStaBySlipCode(staId, stalineId, slipCode, skuCode);
        }
    }

    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {}


    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        List<ITVMIInstruction> instructionList = instructionDao.findGroupInstructionBySlipCode(slipCode, new BeanPropertyRowMapperExt<ITVMIInstruction>(ITVMIInstruction.class));
        Map<String, Long> detials = new HashMap<String, Long>();
        for (ITVMIInstruction itv : instructionList) {
            Long val = detials.get(itv.getSkuCode());
            if (val == null) {
                detials.put(itv.getSkuCode(), itv.getQuantity().longValue());
            } else {
                detials.put(itv.getSkuCode(), val + itv.getQuantity().longValue());
            }
        }
        return detials;
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        return instructionDao.findUnDoInboundOrder(VMIInstructionType.VMI_INBOUND.getValue(), new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    public void generateRtnWh(StockTransApplication stockTransApplication) {

    }

    @Override
    public void generateReceivingTransfer(StockTransApplication sta) {}
}
