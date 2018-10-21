package com.jumbo.dao.warehouse;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhStaPickingListLog;


@Transactional
public interface WhStaPickingListLogDao extends GenericEntityDao<WhStaPickingListLog, Long> {

    // @NativeUpdate
    // void deletePickingListLog(@QueryParam(value = "plId") Long plId);

    @NativeUpdate
    void updatePickingListLog(@QueryParam(value = "plId") Long plId, @QueryParam(value = "memo") String memo);
}
