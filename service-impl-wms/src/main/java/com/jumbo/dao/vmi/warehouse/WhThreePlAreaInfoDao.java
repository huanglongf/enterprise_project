package com.jumbo.dao.vmi.warehouse;



import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.WhThreePlAreaInfo;


@Transactional
public interface WhThreePlAreaInfoDao extends GenericEntityDao<WhThreePlAreaInfo, Long> {


    @NamedQuery
    WhThreePlAreaInfo findAreaIdByName(@QueryParam(value = "city") String city, @QueryParam(value = "areaName") String areaName);

    /**
     * 查询地区
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WhThreePlAreaInfo> findAreaByPaream(@QueryParam(value = "type") Long type, @QueryParam(value = "parentId") Long parentId, BeanPropertyRowMapper<WhThreePlAreaInfo> beanPropertyRowMapper);

}
