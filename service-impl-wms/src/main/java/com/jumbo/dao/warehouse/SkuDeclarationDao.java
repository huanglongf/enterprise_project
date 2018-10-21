package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.baseinfo.SkuDeclaration;
import com.jumbo.wms.model.baseinfo.SkuDeclarationCommand;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

/**
 * 报税仓商品表
 * @author 
 *
 */
public interface SkuDeclarationDao extends GenericEntityDao<SkuDeclaration, Long> {
    @NativeQuery
    SkuDeclarationCommand pushGoods(@QueryParam("id") Long id, RowMapper<SkuDeclarationCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<SkuDeclarationCommand> findGoodsList(int start, int pageSize, @QueryParam("owner") String owner, @QueryParam("upc") String upc, @QueryParam("hsCode") String hsCode, @QueryParam("skuCode") String skuCode, @QueryParam("style") String style,
            @QueryParam("color") String color, @QueryParam("skuSize") String skuSize, @QueryParam("isDiscount") int isDiscount, @QueryParam("status") int status, @QueryParam("skuName") String skuName, RowMapper<SkuDeclarationCommand> rowMapper);

    @NativeQuery
    List<SkuDeclarationCommand> findSkuOriginByPlId(@QueryParam("plId") Long plId, BeanPropertyRowMapper<SkuDeclarationCommand> bprm);
    
    /**
     * 获取多产地sku
     * @param skuCode
     * @param r
     * @return
     */
    @NativeQuery
    List<SkuDeclaration> findSkuDeclarationMoreLocation(@QueryParam("skuCode")String skuCode,RowMapper<SkuDeclaration> r);

    /**
     * 根据HSCODE查找
     * 
     * @param hsCode
     * @param r
     * @return
     */
    @NativeQuery
    List<SkuDeclaration> findSkuDeclarationByHsCode(@QueryParam("hsCode") String hsCode, RowMapper<SkuDeclaration> r);

    /**
     * 查询需要报关的商品
     * 
     * @param bprm
     * @return
     */
    @NativeQuery
    List<SkuDeclarationCommand> findNeedPushSkuInfo(BeanPropertyRowMapper<SkuDeclarationCommand> bprm);
}
