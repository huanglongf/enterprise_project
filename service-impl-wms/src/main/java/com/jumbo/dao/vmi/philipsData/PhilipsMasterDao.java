package com.jumbo.dao.vmi.philipsData;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsMaster;

@Transactional
public interface PhilipsMasterDao extends GenericEntityDao<PhilipsMaster, Long> {

    @NativeQuery
    public PhilipsMaster getPhilipsMasterByConfirmId(@QueryParam("confirmId") String confirmId, BeanPropertyRowMapper<PhilipsMaster> beanPropertyRowMapper);
}
