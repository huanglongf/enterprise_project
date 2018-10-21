package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.SkuSnCheckCfg;
import com.jumbo.wms.model.command.SkuSnCheckCfgCommand;

@Transactional
public interface SkuSnCheckCfgDao extends GenericEntityDao<SkuSnCheckCfg, Long> {

    @NativeQuery
    List<SkuSnCheckCfg> findSnCheckCfgBySnCheckMode(@QueryParam(value = "snCheckMode") Long snCheckMode, RowMapper<SkuSnCheckCfg> rowMapper);

    @NativeQuery
    List<SkuSnCheckCfgCommand> getSkuSnCheckCfgBySnCheckMode(@QueryParam(value = "sncheckmode") Integer sncheckmode, RowMapper<SkuSnCheckCfgCommand> r);

}
