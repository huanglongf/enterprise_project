package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PdaHandOver;
import com.jumbo.wms.model.warehouse.PdaHandOverCommand;

@Transactional
public interface PdaHandOverDao extends GenericEntityDao<PdaHandOver, Long> {

    @NativeQuery
    public List<PdaHandOver> findPdaHandOverByUserId(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId, RowMapper<PdaHandOver> rowMapper);

    @NativeQuery
    public List<Long> findPdaHandOverByLpcode(@QueryParam("userId") Long userId, @QueryParam("lpCode") String lpCode, @QueryParam("ouId") Long ouId, SingleColumnRowMapper<Long> rowMapper);

    @NativeQuery
    public PdaHandOverCommand findPdaHandOverByTransNo(@QueryParam("transNo") String transNo, RowMapper<PdaHandOverCommand> rowMapper);

    @NativeUpdate
    void updatePdaHandOverByUserId(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId);

    @NativeUpdate
    void updatePdaHandByUserId(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId);

    @NativeQuery
    public List<PdaHandOver> findPdaHandOverByUserIdAndOuId(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId, RowMapper<PdaHandOver> rowMapper);

    @NativeQuery
    public Long pdaOutBoundHandNum(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId, SingleColumnRowMapper<Long> rowMapper);


    @NativeQuery
    public List<PdaHandOver> findPdaHandOverByStaId(@QueryParam("staId") Long staId, @QueryParam("ouId") Long ouId, RowMapper<PdaHandOver> rowMapper);
}
