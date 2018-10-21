package com.jumbo.dao.vmi.adidas;

import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.vmi.adidasData.InventoryBatch;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

public interface InventoryBatchDao extends GenericEntityDao<InventoryBatch, Long> {
    @NativeQuery
    InventoryBatch getWmsSalesInventoryBatch(@QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, RowMapper<InventoryBatch> rowMapper);
}
