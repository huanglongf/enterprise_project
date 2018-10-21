package com.jumbo.dao.vmi.gucciData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFeedbackLine;

@Transactional
public interface GucciVMIInFeedbackLineDao extends GenericEntityDao<GucciVMIInFeedbackLine, Long> {

    /**
     * 根据入库反馈头id查询入库反馈明细行
     * 
     * @param vmiInFbId
     * @return
     */
    @NamedQuery
    List<GucciVMIInFeedbackLine> findLinesByInFeedbackId(@QueryParam("vmiInFbId") Long vmiInFbId);
}
