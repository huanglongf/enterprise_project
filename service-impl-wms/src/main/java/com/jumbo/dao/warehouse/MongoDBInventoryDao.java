package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.MongoDBInventory;
import com.jumbo.wms.model.MongoDBInventoryOcp;
import com.jumbo.wms.model.MongoDBInventoryPac;

@Transactional
public interface MongoDBInventoryDao extends GenericEntityDao<MongoDBInventory, Long> {


    /**
     * 直连过仓，查询中间表的商品 byOwn
     */
    @NativeQuery
    List<Long> findAllSkuByOwner(@QueryParam("owner") String owner, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 直连过仓，查询总库存 byOwnandSkuid
     */
    @NativeQuery
    List<MongoDBInventory> findSkuInventoryByOwnerSkuId(@QueryParam("owner") String owner, @QueryParam("skuId") Long skuId, BeanPropertyRowMapper<MongoDBInventory> beanPropertyRowMapper);


    /**
     * 直连过仓，销售明细 byOwnandSkuid
     */
    @NativeQuery
    List<MongoDBInventory> findStaInventoryByOwnerSkuId(@QueryParam("owner") String owner, @QueryParam("skuId") Long skuId, BeanPropertyRowMapper<MongoDBInventory> beanPropertyRowMapper);

    /**
     * 直连过仓 中间表明细 byOwnandSkuid
     */
    @NativeQuery
    List<MongoDBInventory> findSoOrRoInventoryByOwnerSkuId(@QueryParam("owner") String owner, @QueryParam("skuId") Long skuId, BeanPropertyRowMapper<MongoDBInventory> beanPropertyRowMapper);



    /**
     * @param owner
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<MongoDBInventory> findInventoryForOnceUseByOwner(@QueryParam("owner") String owner, BeanPropertyRowMapper<MongoDBInventory> beanPropertyRowMapper);

    /**
     * @param owner
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<MongoDBInventoryPac> findInventoryForOnceUseBywhCode(@QueryParam("ouId") Long ouId, BeanPropertyRowMapper<MongoDBInventoryPac> beanPropertyRowMapper);

    /**
     * @param owner
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<MongoDBInventoryPac> findInventoryForOnceUseBywhCodeAF(@QueryParam("whCode") String whCode, BeanPropertyRowMapper<MongoDBInventoryPac> beanPropertyRowMapper);

    /**
     * 区域占用库存
     * 
     * @param owner
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<MongoDBInventoryOcp> findInventoryForOcpAreaByAreaCode(@QueryParam("areaCode") String areaCode, @QueryParam("ouId") Long ouId, BeanPropertyRowMapper<MongoDBInventoryOcp> beanPropertyRowMapper);

    @NativeQuery
    List<MongoDBInventoryPac> findAllSkuByOwnerPacs(@QueryParam("owner") String owner, @QueryParam("whCode") String whCode, BeanPropertyRowMapper<MongoDBInventoryPac> beanPropertyRowMapper);

    @NativeQuery
    List<MongoDBInventoryPac> findSkuInventoryByOwnerSkuIdPacs(@QueryParam("whCode") String whCode, @QueryParam("skuCode") String skuCode, BeanPropertyRowMapper<MongoDBInventoryPac> beanPropertyRowMapper);

    @NativeQuery
    List<MongoDBInventoryPac> findStaInventoryByOwnerSkuIdPacs(@QueryParam("whCode") String whCode, @QueryParam("skuCode") String skuCode, BeanPropertyRowMapper<MongoDBInventoryPac> beanPropertyRowMapper);
}
