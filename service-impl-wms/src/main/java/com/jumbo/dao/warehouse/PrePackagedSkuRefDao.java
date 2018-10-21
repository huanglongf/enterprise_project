package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.PrePackagedSkuRef;

@Transactional
public interface PrePackagedSkuRefDao extends GenericEntityDao<PrePackagedSkuRef, Long> {
    /**
     * 分页查询当前仓库所有的预包装商品列表
     * 
     * @param start
     * @param pageSize
     * @param barCode
     * @param ouId
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<Sku> getAllPrePackagedSkuByOu(int start, int pageSize, @QueryParam("barCode") String barCode, @QueryParam("ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    /**
     * 根据主商品ID和当前组织id查询待核对商品列表
     * 
     * @param start
     * @param pageSize
     * @param skuId
     * @param ouId
     * @param sorts
     * @param beanPropertyRowMapper 
     * @return
     */
    @NativeQuery
    List<SkuCommand> finSubSkuByIdAndOu(int start, int pageSize, @QueryParam("skuId") Long skuId, @QueryParam("ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapper<SkuCommand> beanPropertyRowMapper);
    /**
     * 
     * @param barCode
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    Sku getSkuByBarCodeAndOu(@QueryParam("barCode")String barCode, @QueryParam("ouId")Long ouId, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    /**
     * 根据商品条码和组织ID添加预包装商品列表
     * @param barCode
     * @param ouId
     */
    @NativeUpdate
    void addPrepackagedSkuByBarCode(@QueryParam("barCode")String barCode, @QueryParam("ouId")Long ouId);
    
    /**
     * 根据主商品条码和组织ID删除预包装商品列表
     * @param barCode
     * @param ouId
     */
    @NativeUpdate
    void deletePrepackagedSkuByMainSkuId(@QueryParam("mainSkuId")Long mainSkuId, @QueryParam("ouId")Long ouId);
    
    /**
     * 根据主商品SkuId和子商品条形码插入预包装商品
     * @param mainSkuId
     * @param barCode
     * @param qty
     * @param ouId
     */
    @NativeUpdate
    void insertPrepackagedSkuBySkuIdAndSubBarCode(@QueryParam("mainSkuId")Long mainSkuId, @QueryParam("barCode")String barCode,@QueryParam("qty")Long qty, @QueryParam("ouId")Long ouId);
    
    /**
     * 根据主商品SkuId和子商品SkuId删除预包装商品
     * @param mainSkuId
     * @param subSkuId
     * @param ouId
     */
    @NativeUpdate
    void deletePrepackagedSkuByMainSkuIdAndSubSkuId(@QueryParam("mainSkuId")Long mainSkuId, @QueryParam("subSkuId")Long subSkuId, @QueryParam("ouId")Long ouId);
   
    /**
     * 通过BarCode获得Sku是否存在
     * @param barCode
     */
    @NativeQuery
    Sku findSkuByBarCode(@QueryParam("barCode")String barCode,  BeanPropertyRowMapper<Sku> beanPropertyRowMapper);
    
    /**
     * 根据MainSkuId删除空的预包装商品
     * @param mainSkuId
     */
    @NativeUpdate
    void deleteEmptyPrepackagedSkuByMainSkuId(@QueryParam("mainSkuId")Long mainSkuId);
}
