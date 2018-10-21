package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PdaHandOverCurrency;

@Transactional
public interface PdaHandOverCurrencyDao extends GenericEntityDao<PdaHandOverCurrency, Long> {

    @NativeQuery
    public List<PdaHandOverCurrency> findPdaHandOverCurrencyByUserId(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId, RowMapper<PdaHandOverCurrency> rowMapper);
    
    @NativeQuery
    public List<PdaHandOverCurrency> findAllPdaHandOverCurrencyByUserId(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId, RowMapper<PdaHandOverCurrency> rowMapper);

    @NativeQuery
    public List<Long> findPdaHandOverCurrencyByLpcode(@QueryParam("userId") Long userId, @QueryParam("lpCode") String lpCode, SingleColumnRowMapper<Long> rowMapper);

    @NativeQuery
    public PdaHandOverCurrency findPdaHandOverCurrencyByTransNo(@QueryParam("transNo") String transNo, RowMapper<PdaHandOverCurrency> rowMapper);

    @NativeUpdate
    void updatePdaHandOverCurrencyByUserId(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId);

    @NativeQuery
    public Long pdaOutBoundHandCurrencyNum(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId, SingleColumnRowMapper<Long> rowMapper);
    
    @NativeUpdate
    void deletePdaHandOverCurrency(@QueryParam("userId") Long userId, @QueryParam("ouId") Long ouId);
}
