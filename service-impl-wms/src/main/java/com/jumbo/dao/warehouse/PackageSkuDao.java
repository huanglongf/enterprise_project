package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PackageSku;
import com.jumbo.wms.model.warehouse.PackageSkuCommand;

/**
 * 套装组合商品DAO
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface PackageSkuDao extends GenericEntityDao<PackageSku, Long> {
    /**
     * 查询套装组合商品是否已经存在
     * 
     * @param id
     * @param staTotalSkuQty
     * @param skuQty
     * @param skus1
     * @param skus2
     * @param skus3
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    PackageSku findHaveSkuByContent(@QueryParam("ouId") Long id, @QueryParam("staTotalSkuQty") Long staTotalSkuQty, @QueryParam("skuQty") Long skuQty, @QueryParam("skus1") String skus1, @QueryParam("skus2") String skus2,
            @QueryParam("skus3") String skus3, BeanPropertyRowMapper<PackageSku> beanPropertyRowMapper);

    /**
     * 更新系统套装组合商品为手工维护
     * 
     * @param id
     */
    @NativeUpdate
    void updateIsSystemById(@QueryParam("id") Long id);

    /**
     * 根据仓库查询所有的packageSku
     * 
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PackageSkuCommand> selectAllPackageSkuByOu(@QueryParam("ouId") Long id, Sort[] sorts, BeanPropertyRowMapper<PackageSkuCommand> beanPropertyRowMapper);
    /**
     * 根据条件查询是否有符合条件的套装组合商品
     * @param ouId
     * @param skus
     * @param skuQty
     * @param staTotalSkuQty
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findPackageSkuListBySkus1System(@QueryParam("ouId")Long ouId, @QueryParam("skus")String skus,@QueryParam("skuQty")Long skuQty,@QueryParam("staTotalSkuQty")Long staTotalSkuQty, SingleColumnRowMapper<Long> singleColumnRowMapper);
    
    @NativeQuery
    String findSkus1ByPackingListId(@QueryParam("pid")Long pid,SingleColumnRowMapper<String> singleColumnRowMapper);
    
    @NativeQuery
    List<PackageSku> findExirePackageSku(BeanPropertyRowMapper<PackageSku> beanPropertyRowMapper);
}
