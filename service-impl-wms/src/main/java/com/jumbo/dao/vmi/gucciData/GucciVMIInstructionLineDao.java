package com.jumbo.dao.vmi.gucciData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.GucciData.GucciVMIInstructionLine;

@Transactional
public interface GucciVMIInstructionLineDao extends GenericEntityDao<GucciVMIInstructionLine, Long> {

    /**
     * 根据入库指令id查询入库明细行
     * 
     * @param status
     * @return
     */
    @NamedQuery
    List<GucciVMIInstructionLine> findLinesByInstructionId(@QueryParam("vmiInId") Long vmiInId);
}
