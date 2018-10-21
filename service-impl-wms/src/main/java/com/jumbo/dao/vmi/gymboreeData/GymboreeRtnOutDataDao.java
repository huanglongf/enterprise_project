package com.jumbo.dao.vmi.gymboreeData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.gymboreeData.GymboreeRtnOutData;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface GymboreeRtnOutDataDao extends GenericEntityDao<GymboreeRtnOutData, Long> {
    @NativeQuery
    List<GymboreeRtnOutData> getNeedSendData(@QueryParam("type") Integer type, BeanPropertyRowMapper<GymboreeRtnOutData> beanPropertyRowMapper);

    @NativeUpdate
    void insertRtnResultDataToDB(@QueryParam("staId") Long id);

}
