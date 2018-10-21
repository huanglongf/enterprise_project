package com.jumbo.dao.vmi.ids;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.ids.AeoSkuMasterInfo;

public interface AeoSkuMasterInfoDao extends GenericEntityDao<AeoSkuMasterInfo, Long>{
    @NativeQuery
    SkuCommand findMasterDataListByBrand(@QueryParam("upc") String upc,RowMapper<SkuCommand> rowMapper);
}
