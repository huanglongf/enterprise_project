package com.jumbo.dao.pda;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PdaSkuLocTypeCap;
import com.jumbo.wms.model.pda.PdaSkuLocTypeCapCommand;

@Transactional
public interface PdaSkuLocTypeCapDao extends GenericEntityDao<PdaSkuLocTypeCap, Long> {


    /**
     * 分页查询（库位类型容量配置）
     */
    @NativeQuery(pagable = true)
    Pagination<PdaSkuLocTypeCapCommand> getPdaSkuLocTypeCapBindingByPage(int start, int pagesize, @QueryParam("ouId") long ouId, @QueryParam("supplierCode") String supplierCode, @QueryParam("skuCode") String skuCode,
            @QueryParam("skuName") String skuName, @QueryParam("typeName") String typeName, @QueryParam("typeCode") String typeCode, RowMapper<PdaSkuLocTypeCapCommand> r, Sort[] sorts);

    @NativeQuery
    PdaSkuLocTypeCap getBySkuCode(@QueryParam("skuCode") String skuCode, @QueryParam("id") Long id, @QueryParam("typeCode") String typeCode, @QueryParam("supplierCode") String supplierCode, RowMapper<PdaSkuLocTypeCap> r);

}
