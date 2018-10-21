package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.InvetoryChange;
import com.jumbo.wms.model.warehouse.InvetoryChangeCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface InvetoryChangeDao extends GenericEntityDao<InvetoryChange, Long> {

    @NamedQuery
    public List<InvetoryChange> queryInvetoryChange();

    @NativeUpdate
    void updateInvetoryChangeStatus(@QueryParam("id") Long id);

    @NativeQuery
    public List<InvetoryChangeCommand> findInvAllByErrorCount(RowMapper<InvetoryChangeCommand> rowMapper);
}
