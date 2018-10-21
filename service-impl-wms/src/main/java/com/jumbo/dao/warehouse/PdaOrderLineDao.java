package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PdaOrderLine;


@Transactional
public interface PdaOrderLineDao extends GenericEntityDao<PdaOrderLine, Long> {
    @NamedQuery
    List<PdaOrderLine> findLineByPdaOrderId(@QueryParam(value = "pdaOrderId") Long pdaOrderId);



    @NamedQuery
    List<PdaOrderLine> findLineByCreate(@QueryParam(value = "pdaOrderId") Long pdaOrderId);


    /**
     * 查询Pda操作日志明细行 KJL
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PdaOrderLine> findPdaLogLine(int start, int pageSize, @QueryParam("id") Long id, Sort[] sorts, BeanPropertyRowMapper<PdaOrderLine> beanPropertyRowMapper);

    /**
     * 根据PDAOrderId 汇总PDAOrderLine明细 退仓明细
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PdaOrderLine> findReturnOrderDetail(@QueryParam("orderId") Long id, BeanPropertyRowMapper<PdaOrderLine> beanPropertyRowMapper);

    /**
     * 根据PDA单据ID 汇总上传的盘点单数据
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PdaOrderLine> findInventoryCheckDetail(@QueryParam("poId") Long id, BeanPropertyRowMapper<PdaOrderLine> beanPropertyRowMapper);


    /**
     * 更新状态
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeUpdate
    void updatePdaOrderLineStatus(@QueryParam(value = "lineId") Long pdaOrderId, @QueryParam(value = "status") Integer status);

    /**
     * 根据PDA单据ID 汇总上传的盘点单数据
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PdaOrderLine> findByOrderCodeAndStatus(@QueryParam("orderCode") String orderCode, @QueryParam("pdaType") Integer pdaType, @QueryParam("pdaStatus") List<Integer> pdaStatus, BeanPropertyRowMapperExt<PdaOrderLine> beanPropertyRowMapper);

    /**
     * 查询上架数据
     * 
     * @param orderCode
     * @param pdaType
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PdaOrderLine> findShelevesPdaLine(@QueryParam("staCode") String orderCode, @QueryParam("stvId") Long pdaType, BeanPropertyRowMapperExt<PdaOrderLine> beanPropertyRowMapper);

    /**
     * 根据PDA错误查询明细
     * 
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PdaOrderLine> getErrorPdaLogDetailList(@QueryParam("id") Long id, Sort[] sorts, BeanPropertyRowMapper<PdaOrderLine> beanPropertyRowMapper);

    /**
     * 
     * @param id
     * @param orderCode
     */
    @NativeUpdate
    void updateErrorPdaLineLocation(@QueryParam("id") Long id, @QueryParam("orderCode") String orderCode);
}
