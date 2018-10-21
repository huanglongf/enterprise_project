package com.jumbo.dao.vmi.converseData;


import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseVmiInvStatusChange;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiInvStatusChangeCommand;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ConverseVmiInvStatusChangeDao extends GenericEntityDao<ConverseVmiInvStatusChange, Long> {

    @NativeQuery
    List<ConverseVmiInvStatusChangeCommand> findInvStatusChangeInfosByStatus(@QueryParam("status") int status, RowMapper<ConverseVmiInvStatusChangeCommand> rowMapper);

    @NativeUpdate
    void updateInvStatusChangeInfoStatus(@QueryParam("fromStatus") int fromStatus, @QueryParam("toStatus") int toStatus);
}
