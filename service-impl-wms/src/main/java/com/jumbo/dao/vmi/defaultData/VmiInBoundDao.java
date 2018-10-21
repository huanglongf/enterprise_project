package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiInBoundCommand;
import com.jumbo.wms.model.vmi.Default.VmiInBoundDefault;

@Transactional
public interface VmiInBoundDao extends GenericEntityDao<VmiInBoundDefault, Long> {


    @NativeQuery
    List<VmiInBoundCommand> findVmiInBoundByVmiCode(@QueryParam("vmicode") List<String> vmicode, RowMapper<VmiInBoundCommand> rowMapper);
}
