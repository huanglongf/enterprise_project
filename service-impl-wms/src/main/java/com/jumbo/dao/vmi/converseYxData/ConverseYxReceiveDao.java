package com.jumbo.dao.vmi.converseYxData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseYxReceive;

/**
 * Converse永兴收获反馈数据访问层
 * 
 * @author jinlong.ke
 * 
 */

@Transactional
public interface ConverseYxReceiveDao extends GenericEntityDao<ConverseYxReceive, Long> {
    @NativeUpdate
    void updateDataToWriteStatus();

    @NativeQuery
    List<ConverseYxReceive> findAllToWriteStatusData(BeanPropertyRowMapper<ConverseYxReceive> beanPropertyRowMapper);

    @NativeUpdate
    void updateToFinishStatus();


}
