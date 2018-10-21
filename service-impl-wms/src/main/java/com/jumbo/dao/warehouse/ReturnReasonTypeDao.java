package com.jumbo.dao.warehouse;


import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;


@Transactional
public interface ReturnReasonTypeDao extends GenericEntityDao<StockTransApplication, Long>{

    /**StockTransApplication
     * 退货原因查询
     * 
     */
    @NativeQuery(pagable = true)
    Pagination<StockTransApplicationCommand> findAllReturnGoods(int start, int pageSize,@QueryParam("ouId") Long ouId, @QueryParam(value = "createTime") Date createTime,
            @QueryParam(value = "endCreateTime") Date endCreateTime, @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "code") String code,@QueryParam(value = "returnReasonType") Integer returnReasonType,
            @QueryParam(value = "refSlipCode") String refSlipCode, @QueryParam(value = "slipCode1") String slipCode1,
            @QueryParam(value = "slipCode2") String slipCode2, Sort[] sorts, RowMapper<StockTransApplicationCommand> rowMapper);
}
