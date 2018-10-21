package com.jumbo.dao.vmi.espData;


import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPPoType;
import com.jumbo.wms.model.vmi.espData.ESPPoTypeCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

@Transactional
public interface ESPPoTypeDao extends GenericEntityDao<ESPPoType, Long> {

    @NamedQuery
    ESPPoType findByPo(@QueryParam("po") String po);

    @NamedQuery
    ESPPoType findByNum(@QueryParam("po") String po);

    @NativeQuery
    ESPPoType findByPo1(@QueryParam("po") String po, RowMapper<ESPPoType> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<ESPPoTypeCommand> findPoTypeList(int start, int pageSize, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("po") String po, @QueryParam("typeName") String typeName, Sort[] sorts,
            RowMapper<ESPPoTypeCommand> rowMapper);


}
