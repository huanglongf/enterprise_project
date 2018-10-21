/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.dao.baseinfo;

import java.math.BigDecimal;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.SkuCategoriesRowMapper;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.command.SkuCategoriesCommand;

/**
 * 商品分类表Dao
 * 
 * @author fanht
 * 
 */
@Transactional
public interface SkuCategoriesDao extends GenericEntityDao<SkuCategories, Long> {

    /**
     * staId查询商品分类信息
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<SkuCategories> findSkuCategoriesByStaId(@QueryParam(value = "staId") Long staId, RowMapper<SkuCategories> rowMapper);

    @NativeQuery
    BigDecimal fingSkuCategoriesIdByName(@QueryParam(value = "name") String name, SingleColumnRowMapper<BigDecimal> r);

    /**
     * 树状列表查询
     * 
     * @param skuCategoriesRowMapper
     * @return
     */
    @NativeQuery
    List<SkuCategories> findSkuCategoryList(SkuCategoriesRowMapper skuCategoriesRowMapper);

    /**
     * 树状列表查询
     * 
     * @param skuCategoriesRowMapper
     * @return
     */
    @NativeQuery
    List<SkuCategoriesCommand> findSkuCategoryBySta(@QueryParam(value = "staId") Long staId, RowMapper<SkuCategoriesCommand> rowMapper);

    /**
     * 更具节点名称查询
     * 
     * @param skuCategoriesRowMapper
     * @return
     */
    @NamedQuery
    SkuCategories getBySkuCategoriesName(@QueryParam(value = "skuCategoriesName") String skuCategoriesName);

    /**
     * 更新分类节点信息 KJL
     * 
     * @param id
     * @param skuCategoriesName
     * @param sedPickingskuQty
     * @param isPickingCategories
     */
    @NativeUpdate
    void updateSkuCategory(@QueryParam("id") Long id, @QueryParam("name") String skuCategoriesName, @QueryParam("qty") Long sedPickingskuQty, @QueryParam("isPick") Boolean isPickingCategories, @QueryParam("skuMaxLimit") Integer skuMaxLimit);

    /**
     * KJL 如果父节点为配货下拉显示，则子节点更新为不显示
     * 
     * @param id
     */
    @NativeUpdate
    void updateSkuCategoriesById(@QueryParam("id") Long id);

    /**
     * KJL 根据id删除商品分类信息
     * 
     * @param id
     */
    @NativeUpdate
    void removeCategoryById(@QueryParam("id") Long id);


    @NativeQuery
    List<SkuCategories> findAllCategories(BeanPropertyRowMapper<SkuCategories> beanPropertyRowMapper);

    /**
     * 获取物流推荐规则明细所关联商品分类
     * 
     * @param trId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<Long> findSkuCategoriesBytrDId(@QueryParam(value = "trDetialId") Long trDetialId, RowMapper<Long> rowMapper);

    /**
     * 查询树的第三级目录
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<Long> findSkuCategoriesSonTree(RowMapper<Long> rowMapper);

    /**
     * 查询树的第二级目录
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<Long> findSkuCategoriesSonTree2(RowMapper<Long> rowMapper);

}
