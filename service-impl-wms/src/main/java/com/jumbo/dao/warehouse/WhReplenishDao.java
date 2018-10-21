package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhReplenish;
import com.jumbo.wms.model.warehouse.WhReplenishCommand;
import com.jumbo.wms.model.warehouse.WhReplenishStatus;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WhReplenishDao extends GenericEntityDao<WhReplenish, Long> {
    /**
     * 分页查询
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WhReplenishCommand> findAllReplenishOrder(int start, int pageSize, @QueryParam("ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapper<WhReplenishCommand> beanPropertyRowMapper);

    /**
     * 查询所有需要生成明细的
     * 
     * @return
     */
    @NamedQuery
    List<WhReplenish> findAllNeedDetailOrder();

    /**
     * 根据id取消补货申请
     * 
     * @param wrId
     * @param status
     */
    @NativeUpdate
    void updateStatusById(@QueryParam("wrId") Long wrId, @QueryParam("status") int status);
    @NamedQuery
    WhReplenish findOldWhReplenish(@QueryParam("ouId")Long id, @QueryParam("status")WhReplenishStatus created, @QueryParam("status1")WhReplenishStatus doing);

}
