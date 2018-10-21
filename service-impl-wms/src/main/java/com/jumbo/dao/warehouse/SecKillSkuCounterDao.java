package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SecKillSkuCounter;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface SecKillSkuCounterDao extends GenericEntityDao<SecKillSkuCounter, Long> {

    /**
     * 根据订单 增加 秒杀商品 计数
     * 
     * @param skus
     * @param ouId
     * @param likeParam
     */
    @NativeUpdate
    void addSecKillSkuCounterBySta(@QueryParam("staId") Long ouId, @QueryParam("likeParam") String likeParam);

    /**
     * 查询达到秒杀订单商品数量的SKUS
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<SecKillSkuCounter> findSecKillSku(RowMapper<SecKillSkuCounter> rowMapper);

    /**
     * 删除 秒杀商品统计 数据
     * 
     * @param skus
     * @param ouId
     */
    @NativeUpdate
    void deleteSecKillSkuCounter(@QueryParam("ouId") Long ouid, @QueryParam("skus") String skus);

    /**
     * 删除 所有过期秒杀订单统计数据
     */
    @NativeUpdate
    void deleteExpireRecordSecKillSku();

    /**
     * 重新将删除的秒杀商品对应的秒杀单据加入秒杀计数器
     * 
     * @param skus
     * @param id
     */
    @NativeUpdate
    void rebackSecKillCounterBySkus(@QueryParam("skus") String skus, @QueryParam("ouId") Long id);

    /**
     * 根据sta删除秒杀计数器
     * 
     * @param id
     */
    @NativeUpdate
    void deleteSecKillCounterByStaId(@QueryParam("staId") Long id);
}
