package com.jumbo.dao.baseinfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.LicensePlate;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

@Transactional
public interface LicensePlateDao extends GenericEntityDao<LicensePlate, Long> {
    @NativeQuery(pagable = true)
    Pagination<LicensePlate> findLicensePlateList(int start, int pageSize, @QueryParam("vehicleCode") String vehicleCode, @QueryParam("licensePlateNumber") String licensePlateNumber, @QueryParam("vehicleStandard") String vehicleStandard,
            @QueryParam("vehicleVolume1") BigDecimal vehicleVolume1, @QueryParam("vehicleVolume2") BigDecimal vehicleVolume2, @QueryParam("useTime") Date useTime, RowMapper<LicensePlate> rowMapper);

    @NativeQuery
    Integer maxSort(@QueryParam("useTime") Date useTime, SingleColumnRowMapper<Integer> rowMapper);

    /**
     * 获取当天可使用的车辆
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<LicensePlate> findLicensePlateByDay(RowMapper<LicensePlate> rowMapper);

}
