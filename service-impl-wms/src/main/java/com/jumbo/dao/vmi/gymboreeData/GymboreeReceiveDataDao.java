package com.jumbo.dao.vmi.gymboreeData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveData;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface GymboreeReceiveDataDao extends GenericEntityDao<GymboreeReceiveData, Long> {
    @NamedQuery
    List<GymboreeReceiveData> findReceiveDataByStatus();

    @NativeQuery
    GymboreeReceiveData getDataByCode(@QueryParam("fchrCode") String fchrCode, BeanPropertyRowMapper<GymboreeReceiveData> beanPropertyRowMapper);
}
