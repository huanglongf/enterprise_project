package com.jumbo.dao.hub2wms;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsConfirmOrder;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsConfirmOrderQueueDao extends GenericEntityDao<WmsConfirmOrderQueue, Long> {
    @NativeQuery(pagable = true)
    Pagination<WmsConfirmOrder> getListByQueryCondition(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, Sort[] sorts,
            BeanPropertyRowMapper<WmsConfirmOrder> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<WmsConfirmOrder> getListByQueryTypeCondition(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("type") Integer type, Sort[] sorts,
            BeanPropertyRowMapper<WmsConfirmOrder> beanPropertyRowMapper);


    /**
     * 查询直连PAC数据
     * 
     * @param systemKey
     * @return
     */
    @NativeQuery
    List<WmsConfirmOrderQueue> getListByQueryPac(@QueryParam("systemKey") String systemKey, BeanPropertyRowMapper<WmsConfirmOrderQueue> beanPropertyRowMapper);

    @NativeQuery
    List<WmsConfirmOrderQueue> getListByQueryPac2(@QueryParam("systemKey") String systemKey, BeanPropertyRowMapper<WmsConfirmOrderQueue> beanPropertyRowMapper);



    @NativeQuery(pagable = true)
    Pagination<WmsConfirmOrder> getListByTypeCondition(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("type") List<Integer> typeList,
            Sort[] sorts, BeanPropertyRowMapper<WmsConfirmOrder> beanPropertyRowMapper);

    /**
     * 
     * @param orderCode
     * @return
     */
    @NativeQuery
    WmsConfirmOrderQueue getListByOrderCode(@QueryParam("orderCode") String orderCode, @QueryParam("type") Integer type, BeanPropertyRowMapper<WmsConfirmOrderQueue> beanPropertyRowMapper);

    @NativeQuery
    List<WmsConfirmOrderQueue> getlistWmsConfirmOrderQueue(@QueryParam("day") int day, BeanPropertyRowMapper<WmsConfirmOrderQueue> beanPropertyRowMapper);

    /**
     * 获取 直连 创单反馈 排除（adidas）
     */
    @NativeQuery
    List<WmsConfirmOrderQueue> findWmsOrderFinishList(RowMapper<WmsConfirmOrderQueue> row);

    @NativeQuery
    WmsConfirmOrder getWmsConfirmOrderById(@QueryParam("id") Long id, BeanPropertyRowMapper<WmsConfirmOrder> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<WmsConfirmOrderQueue> queryWmsConfirmOrderQueue(int start, int size, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "codeList") String[] codeList, Sort[] sorts,
            RowMapper<WmsConfirmOrderQueue> rowMapper);

    /**
     * 查询创单未推送数量
     */
    @NativeQuery
    Long getUnpushedCreateOrderCount(SingleColumnRowMapper<Long> r);

    /**
     * 更新创单未推送is_mq标识
     */
    @NativeUpdate
    void updateCreateOrderFlag();
}
