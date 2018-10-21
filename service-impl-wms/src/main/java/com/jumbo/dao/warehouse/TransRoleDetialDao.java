package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.TransRoleDetial;
import com.jumbo.wms.model.trans.TransRoleDetialCommand;

/**
 * 物流规则明细
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface TransRoleDetialDao extends GenericEntityDao<TransRoleDetial, Long> {
    @NamedQuery
    TransRoleDetial findRoleDetialByRoleId(@QueryParam(value = "roleId") Long roleId);

    @NativeUpdate
    void insertSkuRef(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "detialId") Long detialId);

    @NativeUpdate
    void insertSkuCateRef(@QueryParam(value = "skuCateId") Long skuCateId, @QueryParam(value = "detialId") Long detialId);

    @NativeUpdate
    void insertSkuTagRef(@QueryParam(value = "skuTagId") Long skuTagId, @QueryParam(value = "detialId") Long detialId);

    @NativeUpdate
    void insertWhRef(@QueryParam(value = "whId") Long wh_id, @QueryParam(value = "detialId") Long detialId);

    /**
     * 根据物流推荐规则获取推荐明细
     * 
     * @param trId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<TransRoleDetialCommand> findDetialsByTr(@QueryParam(value = "trId") Long trId, RowMapper<TransRoleDetialCommand> rowMapper);

    @NativeUpdate
    void deleteSkuRef(@QueryParam(value = "detialId") Long detialId);

    @NativeUpdate
    void deleteSkuCateRef(@QueryParam(value = "detialId") Long detialId);

    @NativeUpdate
    void deleteSkuTagRef(@QueryParam(value = "detialId") Long detialId);

    @NativeUpdate
    void deleteWhRef(@QueryParam(value = "detialId") Long detialId);

    /**
     * 获取关联仓库
     * 
     * @param tId
     * @param r
     * @return
     */
    @NativeQuery
    List<Long> findRefWh(@QueryParam(value = "trdId") Long trdId, RowMapper<Long> r);
}
