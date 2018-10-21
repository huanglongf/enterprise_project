package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueStaLineStockout;

@Transactional
public interface QueueStaLineStockoutDao extends GenericEntityDao<QueueStaLineStockout, Long> {
    @NativeQuery
    List<QueueStaLineStockout> queryStaLineId(@QueryParam(value = "qstaLineId") Long qstaLineId, RowMapper<QueueStaLineStockout> rowMapper);

    @NativeUpdate
    public void lineStockoutDelete(@QueryParam(value = "id") Long id);

}
