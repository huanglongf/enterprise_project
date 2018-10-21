package com.jumbo.dao.invflow;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.invflow.WmsSkuInventoryFlow;

@Transactional
public interface WmsSkuInventoryFlowDao extends GenericEntityDao<WmsSkuInventoryFlow, Long> {
    /**
     * 根据时间段查询库存流水记录
     * 
     * @param systemKey
     * @param startTime
     * @return endTime
     */
    @NativeQuery(pagable = true)
    Pagination<WmsSkuInventoryFlow> getWmsSkuInventoryFlow(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("customerCode") String customerCode, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime,
            BeanPropertyRowMapper<WmsSkuInventoryFlow> beanPropertyRowMapper);

    /**
     * 删除过期数据
     * 
     * @param date
     */
    @NativeUpdate
    void removeExpireData(@QueryParam("date") String date);

    /**
     * 查询未推送数量
     * 
     * @param status
     */
    @NativeQuery
    Long getUnpushedSkuInvFlowCount(@QueryParam("status") Integer status, SingleColumnRowMapper<Long> r);

    /**
     * 根据推送状态才查询流水数据数量
     * 
     * @param start
     * @param pagesize
     * @param status
     */
    @NativeQuery(pagable = true)
    Pagination<WmsSkuInventoryFlow> getWmsSkuInventoryFlowByStatus(int start, int pagesize, @QueryParam("status") Integer status, BeanPropertyRowMapper<WmsSkuInventoryFlow> beanPropertyRowMapper);

    /**
     * 更新推送数据状态
     * 
     * @param date
     */
    @NativeUpdate
    void updateWmsSkuInvFlowStatus(@QueryParam("stLogIds") List<Long> stLogIds);

    /**
     * 更新推送错误次数
     * 
     * @param date
     */
    @NativeUpdate
    void updateSkuInventoryFlowErrorCount(@QueryParam("ids") List<Long> ids);

    /**
     * 查询未推送失败数量
     * 
     * @param status
     */
    @NativeQuery
    Long getUnpushedSkuInvFlowCountError(SingleColumnRowMapper<Long> singleColumnRowMapper);

}
