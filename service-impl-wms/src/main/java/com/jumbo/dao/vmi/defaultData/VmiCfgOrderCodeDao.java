package com.jumbo.dao.vmi.defaultData;


import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiCfgOrderCode;
import com.jumbo.wms.model.vmi.Default.VmiCfgOrderCodeCommand;

@Transactional
public interface VmiCfgOrderCodeDao extends GenericEntityDao<VmiCfgOrderCode, Long> {

    @NativeQuery
    VmiCfgOrderCodeCommand findCfgOrderCodeByVmiSourceType(@QueryParam("vmisource") String vmisource, @QueryParam("type") Integer type, RowMapper<VmiCfgOrderCodeCommand> rowMapper);
}
