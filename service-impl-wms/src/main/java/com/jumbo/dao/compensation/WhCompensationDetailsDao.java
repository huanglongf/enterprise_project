package com.jumbo.dao.compensation;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.compensation.WhCompensationDetails;
import com.jumbo.wms.model.compensation.WhCompensationDetailsCommand;

@Transactional
public interface WhCompensationDetailsDao extends GenericEntityDao<WhCompensationDetails, Long> {


    @NativeQuery(pagable = true)
    Pagination<WhCompensationDetailsCommand> findCompensationDetailsByParams(int start, int pageSize, Sort[] sorts, @QueryParam("compensationId") Long compensationId, RowMapper<WhCompensationDetailsCommand> rowMapper);

    @NativeQuery
    List<WhCompensationDetailsCommand> findCompensationDetailsByParamsNoPage(@QueryParam("compensationId") Long compensationId, RowMapper<WhCompensationDetailsCommand> rowMapper);


}
