package com.jumbo.dao.vmi.philipsData;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsIssue;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsGoodsIssueDao extends GenericEntityDao<PhilipsGoodsIssue, Long> {

    @NativeQuery
    List<PhilipsGoodsIssue> getPhilipsGoodsIssuesNoBatchId(RowMapper<PhilipsGoodsIssue> rowMapper);
}
