package com.jumbo.dao.vmi.itochuData;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.warehouse.ItochuCheckInventory;

@Transactional
public interface ItochuCheckInventoryDao extends GenericEntityDao<ItochuCheckInventory, Long> {
    @NativeQuery
    ItochuCheckInventory findItochuCheckInventoryByBillNoAndSku(@QueryParam("billNo") String billNo, @QueryParam("sku") String sku, RowMapper<ItochuCheckInventory> rowMapper);



}
