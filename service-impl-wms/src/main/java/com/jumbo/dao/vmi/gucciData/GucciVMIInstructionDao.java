package com.jumbo.dao.vmi.gucciData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.GucciData.GucciVMIInstruction;

@Transactional
public interface GucciVMIInstructionDao extends GenericEntityDao<GucciVMIInstruction, Long> {

    /**
     * 根据状态查询gucci入库指令
     * 
     * @param status
     * @return
     */
    @NamedQuery
    List<GucciVMIInstruction> findInstructionsByStatus(@QueryParam("status") Integer status);

    /**
     * 根据JDADocumentNo查询入库指令
     * 
     * @param JDADocumentNo
     * @return
     */
    @NamedQuery
    GucciVMIInstruction findByJDADocumentNo(@QueryParam("JDADocumentNo") String JDADocumentNo);

    /**
     * 根据staId查询gucci入库指令
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    GucciVMIInstruction findInstructionsByStaId(@QueryParam("staId") Long staId);
}
