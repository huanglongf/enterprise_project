package com.jumbo.dao.hub2wms;

import java.math.BigDecimal;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;

@Transactional
public interface WmsSalesOrderQueueDao extends GenericEntityDao<WmsSalesOrderQueue, Long> {
    /**
     * 查询过仓队列表rowlimit条数据，设置"区分单据是否可创建定时任务"开始标志
     * 
     * @param rowlimit
     */
    @NativeUpdate
    Integer setBeginFlagForOrder(@QueryParam("rowlimit") int rowlimit);

    @NativeUpdate
    Integer setBeginFlagForOrderNotin(@QueryParam("rowlimit") int rowlimit);

    @NativeQuery
    List<Long> getNeedExecuteWarehouse();

    /**
     * 查询所有设置了开始标识位的单据
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WmsSalesOrderQueue> findAllOrderHaveFlag(BeanPropertyRowMapper<WmsSalesOrderQueue> beanPropertyRowMapper);

    @NativeUpdate
    Integer updateBeginFlagByPriorityOrder();

    @NativeUpdate
    Integer updateBeginFlagByOwner(@QueryParam("limit") int limit, @QueryParam("owner") String owner);


    @NativeUpdate
    Integer updateBeginFlagBycommonOwner(@QueryParam("limit") int limit, @QueryParam("owner") List<String> owner);

    @NativeUpdate
    void updateBeginFlagByAnalyzeOwner(@QueryParam("limit") int limit);

    /**
     * 查询当前默认发货仓为当前仓的单据
     * 
     * @param id
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WmsSalesOrderQueue> getOrderToSetFlagByOuId(@QueryParam("ouId") Long id, BeanPropertyRowMapper<WmsSalesOrderQueue> beanPropertyRowMapper);

    /**
     * 查询是否所有的单子都打上了能否创单标识
     * 
     * @param singleColumnRowMapper
     */
    @NativeQuery
    Boolean getIsAllHaveFlag(SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    /**
     * 查询所有可创的单子
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllOrderToCreate(SingleColumnRowMapper<Long> singleColumnRowMapper);


    @NativeQuery
    List<Long> getAllOrderInvCheckCreate(@QueryParam("rownum") Long rownum, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据单据号
     * 
     * @param id
     * 
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NamedQuery
    WmsSalesOrderQueue getOrderToSetFlagByOrderCode(@QueryParam("code") String orderCode);

    @NamedQuery
    List<WmsSalesOrderQueue> findWmsSalesOrderQueueSendMq();

    @NamedQuery
    List<WmsSalesOrderQueue> queryErrorCount();

    @NativeUpdate
    int cleanDataByOrderId(@QueryParam("id") Long id);

    @NativeQuery
    List<WmsSalesOrderQueue> getWmsSalesOrderQueueByBeginFlag(@QueryParam("beginFlag") Integer beginFlag, BeanPropertyRowMapper<WmsSalesOrderQueue> beanPropertyRowMapper);

    @NativeQuery
    List<WmsSalesOrderQueue> sendMqTomsByMqLogTime(@QueryParam("num") BigDecimal num, BeanPropertyRowMapper<WmsSalesOrderQueue> beanPropertyRowMapper);



}
