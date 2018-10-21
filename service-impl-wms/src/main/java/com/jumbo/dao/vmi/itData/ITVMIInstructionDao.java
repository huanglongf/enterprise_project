package com.jumbo.dao.vmi.itData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.itData.ITVMIInstruction;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ITVMIInstructionDao extends GenericEntityDao<ITVMIInstruction, Long> {

    @NamedQuery
    List<ITVMIInstruction> findInstructionByFileName(@QueryParam("fileName") String fileName);

    @NativeQuery
    List<String> findNotOperateInstructionFileName(@QueryParam("insType") int insType, RowMapper<String> rowMapper);

    @NativeQuery
    List<String> findUnDoInboundOrder(@QueryParam("insType") int insType, RowMapper<String> rowMapper);
    
    @NamedQuery
    List<ITVMIInstruction> findInstructionsByStaId(@QueryParam("staId") Long staId);

    @NamedQuery
    ITVMIInstruction findInstructionByStaLineId(@QueryParam("lineId") Long lineId);

    @NamedQuery
    ITVMIInstruction findInstructionByInvLineId(@QueryParam("lineId") Long lineId);

    @NativeQuery
    List<String> findFailedSkuBarCode(@QueryParam("fileName") String fileName, RowMapper<String> rowMapper);

    @NativeQuery
    List<ITVMIInstruction> findGroupInstructionByFileName(@QueryParam("fileName") String fileName, RowMapper<ITVMIInstruction> rowMapper);

    @NativeQuery
    List<ITVMIInstruction> findGroupInstructionBySlipCode(@QueryParam("slipCode") String slipCode, RowMapper<ITVMIInstruction> rowMapper);

    
    @NativeUpdate
    void updateDNSta(@QueryParam("staId") Long staId, @QueryParam("staLineId") Long staLineId, @QueryParam("fileName") String fileName, @QueryParam("upc") String upc);

    @NativeUpdate
    void updateDNStaBySlipCode(@QueryParam("staId") Long staId, @QueryParam("staLineId") Long staLineId, @QueryParam("slipCode") String slipCode, @QueryParam("upc") String upc);
    
    @NativeQuery
    List<ITVMIInstruction> findITSkuNotInInstruction(@QueryParam("fileName") String fileName, RowMapper<ITVMIInstruction> rowMapper);

    
}
