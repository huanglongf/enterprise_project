package com.jumbo.dao.warehouse;

import java.math.BigDecimal;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SkuSizeConfig;

/**
 * 商品大小件分类
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface SkuSizeConfigDao extends GenericEntityDao<SkuSizeConfig, Long> {
    /**
     * 得到所有商品大小件分类
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<SkuSizeConfig> selectAllConfig(BeanPropertyRowMapper<SkuSizeConfig> beanPropertyRowMapper);

    @NativeQuery
    String findBypicklistId(@QueryParam("pid") Long ouId, SingleColumnRowMapper<String> pname);

    /**
     * 根据特定的skuMaxLength查询对应的团购标志 KJL
     * 
     * @param skuMaxLength
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findConfigBySkuQty(@QueryParam("skuMaxLength") BigDecimal skuMaxLength, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据特定的skuMaxLength查询对应的大中小
     * 
     * @param skuMaxLength
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String findSizeBySkuQty(@QueryParam("skuMaxLength") Long skuMaxLength, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据明细查找大小件分类ID
     * 
     * @param maxSize
     * @param minSize
     * @param groupSkuQtyLimit
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long getSkuSizeConfigIdByDeatil(@QueryParam("maxSize") BigDecimal maxSize, @QueryParam("minSize") BigDecimal minSize, @QueryParam("limitQty") Long limitQty, SingleColumnRowMapper<Long> singleColumnRowMapper);
}
