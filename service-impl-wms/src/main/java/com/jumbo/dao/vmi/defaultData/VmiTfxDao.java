package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiTfxCommand;
import com.jumbo.wms.model.vmi.Default.VmiTfxDefault;

@Transactional
public interface VmiTfxDao extends GenericEntityDao<VmiTfxDefault, Long> {

    @NativeQuery
    List<VmiTfxCommand> findVmiTfxAll(RowMapper<VmiTfxCommand> rowMapper);

    @NativeQuery
    List<VmiTfxCommand> findVmiTfxErrorAll(RowMapper<VmiTfxCommand> rowMapper);
}
