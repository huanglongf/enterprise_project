package com.jumbo.dao.vmi.order;


import java.util.Date;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.order.VmiOrderOpLog;
import com.jumbo.wms.model.vmi.order.VmiOrderOpLogCommand;

@Transactional
public interface VmiOrderOpLogDao extends GenericEntityDao<VmiOrderOpLog, Long> {

    @NamedQuery
    VmiOrderOpLog getOrderLogByOrderCode(@QueryParam("orderCode") String orderCode);

    @NativeQuery(pagable = true, withGroupby = true, model = VmiOrderOpLogCommand.class)
    Pagination<VmiOrderOpLogCommand> queryVmiLogByCondition(int start, int pageSize, @QueryParam("shopId") Long shopId, @QueryParam("orderCode") String code, @QueryParam("createTimeBegin") Date createTimeBegin,
            @QueryParam("createTimeEnd") Date createTimeEnd, Sort sorts[]);
}
