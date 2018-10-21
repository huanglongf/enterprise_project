package com.jumbo.dao.warehouse;

import java.util.Date;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WmsCancelOrder;

@Transactional
public interface WmsCancelOrderDao extends GenericEntityDao<WmsCancelOrder, Long> {

    /**
     * 查询配货失败的单据
     * 
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WmsCancelOrder> findDeliveryFailureSta(int start, int size, @QueryParam("owner") String owner, @QueryParam("code") String code, @QueryParam("slipCode") String slipCode, @QueryParam("ouId") Long ouId,
            @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("status") int status, RowMapper<WmsCancelOrder> rowMapper, Sort[] sorts);
    /**
     * 根据作业单查询取消单据
     * @param staCode
     * @return
     */
    @NamedQuery
    WmsCancelOrder findWmsCancelOrderByStaCode(@QueryParam("staCode")String staCode); 
    
}
