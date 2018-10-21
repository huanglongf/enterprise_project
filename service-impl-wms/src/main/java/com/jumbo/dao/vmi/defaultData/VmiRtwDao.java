package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiRtwCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtwDefault;

@Transactional
public interface VmiRtwDao extends GenericEntityDao<VmiRtwDefault, Long> {

    @NativeQuery
    List<VmiRtwCommand> findVmiRtwAll(RowMapper<VmiRtwCommand> rowMapper);
    
    @NativeQuery
    List<VmiRtwCommand> findVmiRtwAllExt(@QueryParam("vmicode") String vmicode, RowMapper<VmiRtwCommand> rowMapper);
    
    @NativeQuery
    List<VmiRtwCommand> findVmiRtwErrorAll(RowMapper<VmiRtwCommand> rowMapper);
    
    @NativeQuery
    List<VmiRtwCommand> findPumaVmiRtwAllExt(@QueryParam("vmicode") String vmicode, RowMapper<VmiRtwCommand> rowMapper);
    
    @NativeQuery
    List<VmiRtwCommand> findPumaNotHasLineNoVmiRtwAllExt(@QueryParam("vmicode") String vmicode, RowMapper<VmiRtwCommand> rowMapper);

}
