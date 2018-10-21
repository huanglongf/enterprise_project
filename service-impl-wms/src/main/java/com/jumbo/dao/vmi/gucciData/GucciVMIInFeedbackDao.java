package com.jumbo.dao.vmi.gucciData;

import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFeedback;

@Transactional
public interface GucciVMIInFeedbackDao extends GenericEntityDao<GucciVMIInFeedback, Long> {

    /**
     * 根据状态查询gucci入库反馈指令
     * 
     * @param status
     * @return
     */
    @NamedQuery
    List<GucciVMIInFeedback> findInFeedbackByStatus(@QueryParam("status") Integer status);

    /**
     * 根据时间段查询gucci入库反馈指令
     * 
     * @param startTime
     * @return endTime
     */
    @NativeQuery(pagable = true)
    Pagination<GucciVMIInFeedback> findVMIInFBListByJDADocumentNo(int start, int pageSize, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("type") Integer type, BeanPropertyRowMapperExt<GucciVMIInFeedback> r,
            Sort[] sorts);

}
