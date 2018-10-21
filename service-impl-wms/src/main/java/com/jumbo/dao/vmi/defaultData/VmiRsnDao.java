package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiRsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;

@Transactional
public interface VmiRsnDao extends GenericEntityDao<VmiRsnDefault, Long> {

    @NativeUpdate
    void updateVmiRsnStatusByVmiAsn(@QueryParam("ordercode") String ordercode, @QueryParam("vmicode") String vmicode, @QueryParam("status") Integer status);

    @NativeQuery
    List<VmiRsnCommand> findVmiRsnAll(RowMapper<VmiRsnCommand> rowMapper);
    
    @NativeQuery
    List<VmiRsnCommand> findVmiRsnAllExt(@QueryParam("vmicode") String vmicode, RowMapper<VmiRsnCommand> rowMapper);

    @NativeQuery
    List<VmiRsnCommand> findVmiRsnErrorAll(RowMapper<VmiRsnCommand> rowMapper);

}
