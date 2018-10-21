package com.jumbo.dao.lmis.warehouse;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.WarehouseCharges;

@Transactional
public interface WarehouseChargesDao extends GenericEntityDao<WarehouseCharges, Long> {

    @NativeQuery(pagable = true)
    Pagination<WarehouseCharges> findWarehouseChargesByTime(int start, int pageSize, @QueryParam("historyDate") Date historyDate, @QueryParam("nextDate") Date nextDate, @QueryParam("warehouseType") Integer warehouse_type,
            @QueryParam("storeCode") String store_code, @QueryParam("warehouseCode") String warehouse_code, @QueryParam("areaCode") String area_code, BeanPropertyRowMapper<WarehouseCharges> r, Sort[] sorts);

    @NativeQuery(pagable = true)
    Pagination<WarehouseCharges> findWarehouseChargesByRealtimeInv(int i, int pageSize, BeanPropertyRowMapper<WarehouseCharges> beanPropertyRowMapper, Sort[] sorts);
}
