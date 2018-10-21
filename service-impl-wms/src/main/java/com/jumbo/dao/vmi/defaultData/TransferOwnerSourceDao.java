package com.jumbo.dao.vmi.defaultData;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.TransferOwnerSource;
import com.jumbo.wms.model.vmi.Default.TransferOwnerSourceCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

@Transactional
public interface TransferOwnerSourceDao extends GenericEntityDao<TransferOwnerSource, Long> {

    @NativeQuery(pagable = true)
    Pagination<TransferOwnerSourceCommand> findTransferOwnerSource(int start, int pageSize, @QueryParam("ouId") Long ouId, RowMapper<TransferOwnerSourceCommand> rowMapper);

    @NamedQuery
    TransferOwnerSource getByOwnerSourceAndTargetOwner(@QueryParam("ownerSource") String ownerSource, @QueryParam("targetOwner") String targetOwner, @QueryParam("ouId") Long ouId);
}
