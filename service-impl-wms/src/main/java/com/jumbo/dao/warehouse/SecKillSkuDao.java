package com.jumbo.dao.warehouse;


import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SecKillSku;

/**
 * 秒杀商品Dao
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface SecKillSkuDao extends GenericEntityDao<SecKillSku, Long> {

    /**
     * 根据仓库和Skus查询秒杀商品
     * 
     * @param id
     * @param skus
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    SecKillSku getSecKillSkuByOuAndName(@QueryParam("ouId") Long id, @QueryParam("skus") String skus, BeanPropertyRowMapper<SecKillSku> beanPropertyRowMapper);

    /**
     * 更新系统秒杀为手工秒杀
     * 
     * @param id
     */
    @NativeUpdate
    void updateIsSystemById(@QueryParam("id") Long id);

    /**
     * 删除系统生成的秒杀商品 根据sta判断
     * 
     * @param id
     * @param collection
     */
    @NativeUpdate
    void deleteSecSkillSkuIsSystemByOu(@QueryParam("ouId") Long id, @QueryParam("skus") String skus);

    /**
     * 根据订单查询秒杀商品
     * 
     * @param id
     * @param rowMapper
     * @return
     */
    @NativeQuery
    SecKillSku getSecKillSkuBySta(@QueryParam("staId") Long id, RowMapper<SecKillSku> rowMapper);


    /**
     * 添加秒杀商品
     * 
     * @param ouId
     * @param skus
     * @param isSystem
     */
    @NativeUpdate
    void addSecKillSku(@QueryParam("ouId") Long ouId, @QueryParam("skus") String skus, @QueryParam("isSystem") boolean isSystem);

    /**
     * 删除过期秒杀商品
     * 
     * @param ouId
     * @param skus
     * @param isSystem
     */
    @NativeUpdate
    void deleteExpireRecordSecKill();
}
