package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.BiWarehouseAddStatus;
import com.jumbo.wms.model.warehouse.BiWarehouseAddStatusCommand;

@Transactional
public interface BiWarehouseAddStatusDao extends GenericEntityDao<BiWarehouseAddStatus, Long> {


    @NativeUpdate
    int deleteBiWarehouseAddStatus(@QueryParam("ouId") Long ouId);

    @NamedQuery
    List<BiWarehouseAddStatus> getBiWarehouseStatus(@QueryParam("ouId") Long ouId);

    @NativeQuery
    BiWarehouseAddStatusCommand getBiWHTop(@QueryParam("ouId") Long ouId, RowMapper<BiWarehouseAddStatusCommand> r);

    @NativeQuery
    BiWarehouseAddStatusCommand getBiWHAboveTop(@QueryParam("ouId") Long ouId, @QueryParam("status") int status, RowMapper<BiWarehouseAddStatusCommand> r);

    @NativeQuery
    BiWarehouseAddStatusCommand getBiWHPickStatusAboveTop(@QueryParam("ouId") Long ouId, @QueryParam("status") int status, RowMapper<BiWarehouseAddStatusCommand> r);
}
