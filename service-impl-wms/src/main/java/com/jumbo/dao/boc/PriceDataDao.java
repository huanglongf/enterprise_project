package com.jumbo.dao.boc;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.boc.PriceData;

@Transactional
public interface PriceDataDao extends GenericEntityDao<PriceData, Long> {

    @NativeQuery
    List<PriceData> findPriceDatabystartDate(@QueryParam("startDate") String startDate, RowMapper<PriceData> rowMapper);

    @NativeUpdate
    void updateStatePriceDatabyId(@QueryParam(value = "status") int status,@QueryParam("pid") Long pid);

}
