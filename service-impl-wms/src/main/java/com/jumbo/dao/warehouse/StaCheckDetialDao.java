package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.StaCheckDetial;
import com.jumbo.wms.model.warehouse.StaCheckDetialCommand;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface StaCheckDetialDao extends GenericEntityDao<StaCheckDetial, Long> {

    @NativeQuery
    public List<StaCheckDetialCommand> findSnAllByStaId(@QueryParam("staId") Long staId, RowMapper<StaCheckDetialCommand> rowMapper);


    @NativeQuery
    public List<StaCheckDetialCommand> findSnAllBySkuIdStaId(@QueryParam("staId") Long staId, @QueryParam("skuId") Long skuId, RowMapper<StaCheckDetialCommand> rowMapper);

    @NativeQuery
    public List<StaCheckDetialCommand> findExpDateAllByStaId(@QueryParam("staId") Long staId, RowMapper<StaCheckDetialCommand> rowMapper);

    @NativeQuery
    public List<StaCheckDetialCommand> findExpDateAllBySkuIdStaId(@QueryParam("staId") Long staId, @QueryParam("skuId") Long skuId, RowMapper<StaCheckDetialCommand> rowMapper);


    @NativeQuery
    public List<String> findRfidAllBySkuIdStaId(@QueryParam("staId") Long staId, @QueryParam("barCode") String barCode, SingleColumnRowMapper<String> rowMapper);
}
