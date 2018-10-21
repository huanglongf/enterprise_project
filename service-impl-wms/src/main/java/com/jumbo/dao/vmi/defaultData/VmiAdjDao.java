package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;

@Transactional
public interface VmiAdjDao extends GenericEntityDao<VmiAdjDefault, Long> {

    @NativeQuery
    List<VmiAdjCommand> findVmiAdjAll(RowMapper<VmiAdjCommand> rowMapper);
    
    @NativeQuery
    List<VmiAdjCommand> findVmiAdjAllExt(@QueryParam("vmicode") String vmicode, RowMapper<VmiAdjCommand> rowMapper);

    @NativeQuery
    List<VmiAdjCommand> findVmiAdjErrorAll(RowMapper<VmiAdjCommand> rowMapper);
}
