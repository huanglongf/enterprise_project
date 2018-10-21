package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;

@Transactional
public interface VmiRsnLineDao extends GenericEntityDao<VmiRsnLineDefault, Long> {

    @NativeQuery
    List<VmiRsnLineDefault> findVmiRsnLineByRsnId(@QueryParam("rsnid") Long rsnid, RowMapper<VmiRsnLineDefault> r);

}
