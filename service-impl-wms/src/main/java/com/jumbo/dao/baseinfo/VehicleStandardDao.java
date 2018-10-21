package com.jumbo.dao.baseinfo;

import java.math.BigDecimal;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.VehicleStandard;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

@Transactional
public interface VehicleStandardDao extends GenericEntityDao<VehicleStandard, Long> {

    @NativeQuery(pagable = true)
    Pagination<VehicleStandard> findVehicleStandardList(int start, int pageSize, @QueryParam("standardCode") String standardCode, @QueryParam("vehicleVolume1") BigDecimal vehicleVolume1, @QueryParam("vehicleVolume2") BigDecimal vehicleVolume2,
            RowMapper<VehicleStandard> rowMapper);

    @NativeQuery
    BigDecimal getVehicleVolume(@QueryParam("standardCode") String standardCode, SingleColumnRowMapper<BigDecimal> rowMapper);
}
