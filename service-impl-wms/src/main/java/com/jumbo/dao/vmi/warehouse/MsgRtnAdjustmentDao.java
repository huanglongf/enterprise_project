package com.jumbo.dao.vmi.warehouse;


import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;

@Transactional
public interface MsgRtnAdjustmentDao extends GenericEntityDao<MsgRtnAdjustment, Long> {


    @NativeUpdate
    void saveByIDSADJ(@QueryParam("recStatus") Integer recStatus, @QueryParam("status") Integer status, @QueryParam("source") String source);

    @NamedQuery
    List<MsgRtnAdjustment> queryByCanceledAndCreated(@QueryParam("source") String source);

    @NativeQuery
    List<MsgRtnAdjustment> getBySourceAndType(@QueryParam(value = "sourceList") List<String> sourceList, @QueryParam("type") Long type, RowMapper<MsgRtnAdjustment> rowMapper);

    @NativeUpdate
    void updateStatusForGymboree();

    @NativeUpdate
    void updateStatusById(@QueryParam("id") Long id, @QueryParam("status") Integer status);
}
