package com.jumbo.dao.vmi.converseYxData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseYxTransferOut;

/**
 * Converse永兴退大仓数据访问层
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface ConverseYxTransferOutDao extends GenericEntityDao<ConverseYxTransferOut, Long> {

    @NativeUpdate
    void saveRtnResult(@QueryParam("staId") Long id);

    @NativeUpdate
    void updateDataToWriteStatus();

    @NativeUpdate
    void updateToFinishStatus();

    @NativeQuery
    List<ConverseYxTransferOut> findAllToWriteStatusData(BeanPropertyRowMapper<ConverseYxTransferOut> beanPropertyRowMapper);

}
