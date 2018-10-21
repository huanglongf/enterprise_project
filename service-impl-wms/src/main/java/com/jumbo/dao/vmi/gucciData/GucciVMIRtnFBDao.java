package com.jumbo.dao.vmi.gucciData;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFB;

@Transactional
public interface GucciVMIRtnFBDao extends GenericEntityDao<GucciVMIRtnFB, Long> {

    /**
     * 根据时间段查询gucci退大仓反馈指令
     * 
     * @param startTime
     * @return endTime
     */
    @NativeQuery(pagable = true)
    Pagination<GucciVMIRtnFB> findVMIRtnFBListByTime(int start, int pageSize, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, BeanPropertyRowMapperExt<GucciVMIRtnFB> r, Sort[] sorts);

}
