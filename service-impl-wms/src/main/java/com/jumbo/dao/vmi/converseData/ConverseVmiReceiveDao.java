package com.jumbo.dao.vmi.converseData;


import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseVmiReceive;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ConverseVmiReceiveDao extends GenericEntityDao<ConverseVmiReceive, Long> {
    @NativeQuery
    List<ConverseVmiReceive> findReceiveInfosByStatus(@QueryParam("status") int status, @QueryParam("type") int type, RowMapper<ConverseVmiReceive> rowMapper);

    @NativeUpdate
    void updateReceiveInfoStatus(@QueryParam("fromStatus") int fromStatus, @QueryParam("toStatus") int toStatus, @QueryParam("type") int type);
    
    @NativeUpdate
    void createToshopForPos(@QueryParam("startDate") Date startDate,@QueryParam("endDate") Date endDate);
}
