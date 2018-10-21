package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueStaLineOwner;

@Transactional
public interface QueueStaLineOwnerDao extends GenericEntityDao<QueueStaLineOwner, Long> {
    @NativeQuery
    List<QueueStaLineOwner> queryStaLineId(@QueryParam(value = "qstaLineId") Long qstaLineId, RowMapper<QueueStaLineOwner> rowMapper);

    @NativeUpdate
    public void lineOwnerDelete(@QueryParam(value = "id") Long id);

}
