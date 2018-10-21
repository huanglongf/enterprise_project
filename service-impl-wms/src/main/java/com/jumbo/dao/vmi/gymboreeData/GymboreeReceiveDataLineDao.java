package com.jumbo.dao.vmi.gymboreeData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveDataLine;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface GymboreeReceiveDataLineDao extends GenericEntityDao<GymboreeReceiveDataLine, Long> {
    /**
     * 根据头id查询明细信息
     * 
     * @param id
     * @return
     */
    @NamedQuery
    List<GymboreeReceiveDataLine> findLineListByDataId(@QueryParam("id") Long id);

    @NativeQuery
    List<GymboreeReceiveDataLine> getByStaId(@QueryParam("staId") Long id, BeanPropertyRowMapper<GymboreeReceiveDataLine> beanPropertyRowMapper);

}
