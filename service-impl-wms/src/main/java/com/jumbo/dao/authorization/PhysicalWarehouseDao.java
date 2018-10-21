package com.jumbo.dao.authorization;

import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.PhysicalWarehouseRowMapper;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;

@Transactional
public interface PhysicalWarehouseDao extends GenericEntityDao<PhysicalWarehouse, Long> {
    @NativeQuery
    List<PhysicalWarehouse> selectAllPhyWarehouse(BeanPropertyRowMapper<PhysicalWarehouse> beanPropertyRowMapper);

    @NativeQuery
    Map<String, PhysicalWarehouse> selectAllPhyAndVirtualWarehouse(PhysicalWarehouseRowMapper physicalWarehouseRowMapper);

    @NativeQuery
    PhysicalWarehouse findPhysicalWarehouseByWhOuId(@QueryParam(value = "ouId") Long ouId, RowMapper<PhysicalWarehouse> rowMapper);

    @NamedQuery
    PhysicalWarehouse getPhyWarehouseByName(@QueryParam(value = "name") String name);
}
