package com.jumbo.dao.vmi.converseData;


import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseVmiAdjustment;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ConverseVmiAdjustmentDao extends GenericEntityDao<ConverseVmiAdjustment, Long> {

    @NativeQuery
    List<ConverseVmiAdjustment> findAdjustmentInfosByStatus(@QueryParam("status") int status, RowMapper<ConverseVmiAdjustment> rowMapper);

    @NativeUpdate
    void updateAdjustmentInfoStatus(@QueryParam("fromStatus") int fromStatus, @QueryParam("toStatus") int toStatus);

}
