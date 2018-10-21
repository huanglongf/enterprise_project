package com.jumbo.dao.vmi.gymboreeData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveRtnData;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface GymboreeReceiveRtnDataDao extends GenericEntityDao<GymboreeReceiveRtnData, Long> {
    /**
     * 更新接收数据中的SKUID,库存状态ID
     * 
     * @param vmiWhSourceGymboree
     */
    @NativeUpdate
    void updateBasicalInfo(@QueryParam("vmiSourceWh") String vmiWhSourceGymboree);
    /**
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<GymboreeReceiveRtnData> getNeedExeReceiveData(BeanPropertyRowMapper<GymboreeReceiveRtnData> beanPropertyRowMapper);

}
