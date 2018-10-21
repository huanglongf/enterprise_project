package com.jumbo.dao.warehouse;

import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SkuImperfect;
import com.jumbo.wms.model.warehouse.SkuImperfectCommand;

@Transactional
public interface SkuImperfectDao extends GenericEntityDao<SkuImperfect, Long> {

    @NativeQuery(pagable = true)
    Pagination<SkuImperfectCommand> getSkuImperfectl(int start, int pagesize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "receiver") String receiver,
            @QueryParam(value = "barCode") String barCode, @QueryParam(value = "supplierCode") String supplierCode, @QueryParam(value = "defectType") String defectType, @QueryParam(value = "defectWhy") String defectWhy,
            @QueryParam(value = "owner") String owner, @QueryParam(value = "defectCode") String defectCode, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "type") Integer type,
            @QueryParam(value = "staCode") String code, @QueryParam(value = "cartonCode") String cartonCode, RowMapper<SkuImperfectCommand> r, Sort[] sorts);

    /**
     * 查询残次信息表
     * 
     * @param id
     * @return
     */
    @NativeQuery
    SkuImperfectCommand findSkuImperfect(@QueryParam(value = "id") Long id, RowMapper<SkuImperfectCommand> r);

    /**
     * 查询残次信息表
     * 
     * @param id
     * @return
     */
    @NativeQuery
    List<SkuImperfect> findSkuImperfectStaId(@QueryParam(value = "staId") Long staId, RowMapper<SkuImperfect> r);

    @NativeQuery
    SkuImperfectCommand findSkuImperfectBarCode(@QueryParam(value = "defectCode") String defectCode, @QueryParam(value = "barCode") String barCode, RowMapper<SkuImperfectCommand> r);

    /**
     * 查询残次商品明细
     * 
     * @return
     */
    @NamedQuery
    SkuImperfect getSkuImperfectSkuId(@QueryParam(value = "defectCode") String defectCode, @QueryParam(value = "skuId") Long skuId);



}
