package com.jumbo.dao.boc;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.boc.MasterData;
import com.jumbo.wms.model.command.SkuCommand;

@Transactional
public interface MasterDataDao extends GenericEntityDao<MasterData, Long> {
    @NativeQuery
    SkuCommand findMasterDataListByStats(@QueryParam("upc") String upc,RowMapper<SkuCommand> rowMapper);
    @NativeUpdate
    void updateMasterDataStatusById(@QueryParam("mid") Long mid, @QueryParam("status") int status);
    @NamedQuery
    MasterData findMasterData(@QueryParam("upc") String upc);
}
