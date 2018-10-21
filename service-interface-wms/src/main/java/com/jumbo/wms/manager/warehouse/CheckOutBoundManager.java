package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

/**
 * 秒杀订单--核对--出库--交接业务逻辑层
 * 
 * @author jinlong.ke
 * 
 */
public interface CheckOutBoundManager extends BaseManager {
    /**
     * 根据查询条件查询所有的秒杀配货清单
     * 
     * @param pageSize
     * @param start
     * 
     * @param date
     * @param date2
     * @param pickingList
     * @param long1
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> getAllSecKillPickingListByStatus(int start, int pageSize, Date date, Date date2, PickingList pickingList, Long long1, Sort[] sorts);

    /**
     * 根据查询条件查询所有的秒杀配货清单
     * 
     * @param pageSize
     * @param start
     * 
     * @param date
     * @param date2
     * @param pickingList
     * @param long1
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> getAllSecKillPickingListByStatusopc(int start, int pageSize, Date date, Date date2, PickingList pickingList, Long long1, List<Long> lists, Sort[] sorts);

    /**
     * 根据配货清单核对秒杀配货批
     * 
     * @param id
     * @param sorts
     * @return
     */
    List<StaLineCommand> getStaPickingListPgIndex(Long id, Sort[] sorts);

    /**
     * 秒杀配货单核对出库
     * 
     * @param weight
     * @param pId
     * @param whId
     * @param userId
     * @param saddlines
     */
    void generSecKillHandOverList(BigDecimal weight, Long pId, List<StaAdditionalLine> saddlines, Long userId, Long whId);

    /**
     * 创建交接清单
     * 
     * @param id
     * @return
     */
    Long generHandOverList(Long id, Long whId,Long ouId, Long userId);

    /**
     * 查询取消作业单列表 KJL
     * 
     * @param id
     * @param sorts
     * @return
     */
    List<StockTransApplicationCommand> getCancelStaListPgIndex(Long id, Sort[] sorts);

    /**
     * 根据staId 删除取消作业单 并判断pickinglist可否删除同时执行操作
     * 
     * @param id
     * @param staId
     */
    void deleteStaById(Long id, Long staId);

}
