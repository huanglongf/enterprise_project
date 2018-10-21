package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.SkuTag;
import com.jumbo.wms.model.trans.SkuTagCommand;

/**
 * 商品标签
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface SkuTagDao extends GenericEntityDao<SkuTag, Long> {
    @NativeQuery(pagable = true)
    Pagination<SkuTagCommand> findSkuTag(int starts, int pageSize, @QueryParam(value = "code") String code, @QueryParam(value = "name") String name, Sort[] storts, BeanPropertyRowMapper<SkuTagCommand> tag);

    /**
     * 获取物流推荐规则明细所关联商品标签
     * 
     * @param trId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<SkuTagCommand> findSkuTagRefSkuBytrDId(@QueryParam(value = "trDetialId") Long trDetialId, RowMapper<SkuTagCommand> rowMapper);

    /**
     * 商品标签维护分页列表
     * 
     * @param start
     *@param size
     *@param code
     *@param name
     *@param status
     *@param sorts
     *@param tag
     *@return Pagination<SkuTagCommand>
     *@throws
     */
    @NativeQuery(pagable = true)
    Pagination<SkuTagCommand> findSkuTagByPagination(int start, int size, @QueryParam(value = "code") String code, @QueryParam(value = "name") String name, @QueryParam(value = "status") List<Integer> status, Sort[] sorts,
            BeanPropertyRowMapper<SkuTagCommand> tag);
    
    /**
     * 根据编码查询商品标签数量
     *@param tagCode
     *@param r
     *@return Long 
     *@throws
     */
    @NativeQuery
    Long findSkuTagCountByCode(@QueryParam("tagCode") String tagCode, SingleColumnRowMapper<Long> r);
    /**
     * 关联Sku
     *@param skuTagId
     *@param skuId void 
     *@throws
     */
    @NativeUpdate
    void insertSkuRef(@QueryParam(value = "skuTagId") Long skuTagId, @QueryParam(value = "skuId") Long skuId);
    /**
       * 删除关联的Sku
     *@param skuTagId void 
     *@throws
     */
    @NativeUpdate
    void deleteSkuRef(@QueryParam(value = "skuTagId") Long skuTagId);
    @NamedQuery()
    SkuTag findSkuTagByCode(@QueryParam("tagCode") String tagCode);
}
