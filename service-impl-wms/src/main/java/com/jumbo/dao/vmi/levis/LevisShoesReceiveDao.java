package com.jumbo.dao.vmi.levis;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.levisData.LevisShoesReceive;

@Transactional
public interface LevisShoesReceiveDao extends GenericEntityDao<LevisShoesReceive, Long> {
    
    @NativeQuery
    List<LevisShoesReceive> findAllToWriteStatusData(BeanPropertyRowMapper<LevisShoesReceive> beanPropertyRowMapper);
    
    @NativeUpdate
    void updateToFinishStatus();
    
    @NativeUpdate
    void updateDataToWriteStatus();
}
