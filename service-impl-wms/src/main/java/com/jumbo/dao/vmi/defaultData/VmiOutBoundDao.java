package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiOutBoundCommand;
import com.jumbo.wms.model.vmi.Default.VmiOutBoundDefault;

@Transactional
public interface VmiOutBoundDao extends GenericEntityDao<VmiOutBoundDefault, Long> {


    @NativeQuery
    List<VmiOutBoundCommand> findVmiOutBoundByVmiCode(@QueryParam("vmicode") List<String> vmicode, RowMapper<VmiOutBoundCommand> rowMapper);

    @NativeQuery
    List<VmiOutBoundDefault> findVmiOutBoundByStaId(@QueryParam("staId") Long staId, RowMapper<VmiOutBoundDefault> r);
}
