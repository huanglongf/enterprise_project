package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsIssueLine;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsGoodsIssueLineDao extends GenericEntityDao<PhilipsGoodsIssueLine, Long> {

    @NativeQuery
    List<PhilipsGoodsIssueLine> getPhilipsGoodsIssueLinesByIssueId(@QueryParam("issueId") Long issueId, RowMapper<PhilipsGoodsIssueLine> rowMapper);
}
