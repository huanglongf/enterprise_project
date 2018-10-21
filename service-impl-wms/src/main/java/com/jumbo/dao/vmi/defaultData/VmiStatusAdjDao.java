package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiStatusAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiStatusAdjDefault;

@Transactional
public interface VmiStatusAdjDao extends GenericEntityDao<VmiStatusAdjDefault, Long> {
    @NativeQuery
    List<VmiStatusAdjCommand> findVmiStatusAdjAll(RowMapper<VmiStatusAdjCommand> rowMapper);

    @NativeQuery
    List<VmiStatusAdjCommand> findVmiStatusAdjAllExt(@QueryParam("vmicode") String vmicode, RowMapper<VmiStatusAdjCommand> rowMapper);

    @NativeQuery
    List<VmiStatusAdjCommand> findVmiStatusAdjErrorAll(RowMapper<VmiStatusAdjCommand> rowMapper);
}
