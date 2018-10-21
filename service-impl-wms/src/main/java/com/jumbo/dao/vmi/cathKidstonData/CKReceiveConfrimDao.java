package com.jumbo.dao.vmi.cathKidstonData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cathKidstonData.CKReceiveConfrim;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceiveConfrimCommand;

@Transactional
public interface CKReceiveConfrimDao extends GenericEntityDao<CKReceiveConfrim, Long> {

    @NativeQuery
    List<CKReceiveConfrimCommand> findCKReceiveConfrim(RowMapper<CKReceiveConfrimCommand> rowMapper);


}
