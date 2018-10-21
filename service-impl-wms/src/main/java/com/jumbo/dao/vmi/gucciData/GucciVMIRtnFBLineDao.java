package com.jumbo.dao.vmi.gucciData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFBLine;

@Transactional
public interface GucciVMIRtnFBLineDao extends GenericEntityDao<GucciVMIRtnFBLine, Long> {

    /**
     * 根据退大仓反馈头id查询入库反馈明细行
     * 
     * @param vmiInFbId
     * @return
     */
    @NamedQuery
    List<GucciVMIRtnFBLine> findLinesByOutFeedbackId(@QueryParam("vmiRtnFBId") Long vmiRtnFBId);

    /**
     * 生成PckNumber递增序列
     * 
     * @return
     */
    @NativeQuery
    Long generatePckNumberSequence(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 获取pickingListNumber
     * 
     * @return
     */
    @NativeQuery
    String getPickingListNumber(@QueryParam("staId") Long staId, SingleColumnRowMapper<String> singleColumnRowMapper);

}
