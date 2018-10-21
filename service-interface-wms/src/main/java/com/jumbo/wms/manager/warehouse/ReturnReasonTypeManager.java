package com.jumbo.wms.manager.warehouse;


import java.util.Date;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

public interface ReturnReasonTypeManager extends BaseManager{
    
    /**
     * 退货原因查询
     * 
     */
    Pagination<StockTransApplicationCommand> findAllReturnGoods(int start, int pageSize,Long ouId, Date createTime, Date endCreateTime,Integer returnReasonType, StockTransApplicationCommand sta, Sort[] sorts);


}
