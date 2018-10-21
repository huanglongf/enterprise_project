package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PackageSkuCounter;
import com.jumbo.wms.model.warehouse.PackageSkuCounterCommand;

/**
 * 套装组合商品计数器数据库操作
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface PackageSkuCounterDao extends GenericEntityDao<PackageSkuCounter, Long> {
    /**
     * 根据条件查询秒杀计数器 KJL
     * 
     * @param sku1Id
     * @param l
     * @param m
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    PackageSkuCounter getPackageSkuCounter(@QueryParam("skuId")Long sku1Id, @QueryParam("staTotalSkuQty")Long l, @QueryParam("skuQty")Long m, @QueryParam("ouId")Long id, BeanPropertyRowMapper<PackageSkuCounter> beanPropertyRowMapper);

    /**
     * 添加新的套装组合商品计数器 KJL
     * 
     * @param id
     * @param sku1Id
     */
    @NativeUpdate
    void addNewCounter(@QueryParam("ouId") Long id, @QueryParam("skuId") Long sku1Id);

    /**
     * 特定套装组合商品计数器+1 KJL
     * 
     * @param id
     */
    @NativeUpdate
    void addCounterById(@QueryParam("pscId") Long id);

    /**
     * 删除所有作业单数小于等于0的计数器记录
     */
    @NativeUpdate
    void deleteExpirePackageSkuCounter();

    /**
     * 查询满足套装组合商品阀值的套装组合商品计数器
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PackageSkuCounterCommand> findEnoughCountCounter(BeanPropertyRowMapper<PackageSkuCounterCommand> beanPropertyRowMapper);

    /**
     * 
     * @param skuIdList
     * @param ouId
     * @param staTotalSkuQty
     * @param skuQty
     * @param qty
     */
    @NativeUpdate
    void discountPackageSkuCounterWhenSecKill(@QueryParam("skuIdList") List<Long> skuIdList, @QueryParam("ouId") Long ouId, @QueryParam("staTotalSkuQty") Long staTotalSkuQty, @QueryParam("skuQty") Long skuQty, @QueryParam("qty") Long qty);

    /**
     * 删除套装组合商品计数器
     * 
     * @param ouId
     * @param skuId
     * @param skuQty
     * @param staTotalSkuQty
     */
    @NativeUpdate
    void deletePackageSkuCounterByPackageSku(@QueryParam("ouId") Long ouId, @QueryParam("skuId") Long skuId, @QueryParam("skuQty") Long skuQty, @QueryParam("staTotalSkuQty") Long staTotalSkuQty);
    /**
     * 扣减组合套装计数器当一个加入套装组合商品时
     * @param ouId
     * @param key
     * @param value
     */
    @NativeUpdate
    void updateCounterWhenPackage(@QueryParam("ouId")Long ouId, @QueryParam("skuId")Long key, @QueryParam("qty")Long value);
    /**
     * 
     * @param sku1Id
     * @param sku2Id
     * @param id
     */
    @NativeUpdate
    void discountCounter(@QueryParam("sku1Id")Long sku1Id,@QueryParam("sku2Id")Long sku2Id, @QueryParam("ouId")Long id);

}
