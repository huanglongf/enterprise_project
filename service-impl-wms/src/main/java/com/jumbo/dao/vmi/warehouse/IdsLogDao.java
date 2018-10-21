package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.warehouse.IdsLog;

@Transactional
public interface IdsLogDao extends GenericEntityDao<IdsLog, Long> {

    @NamedQuery
    public List<IdsLog> findIdslogBystatus(@QueryParam(value = "status") Long status);

    @NativeUpdate
    void updateIdsLogStatusByPayLoad(@QueryParam(value = "status") Long status, @QueryParam(value = "payload") String payload);
}
