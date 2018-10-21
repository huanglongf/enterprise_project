package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsStockComparison;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsStockComparisonDao extends GenericEntityDao<PhilipsStockComparison, Long> {

    @NativeQuery
    List<PhilipsStockComparison> getStockComparisonsNoBatchId(RowMapper<PhilipsStockComparison> rowMapper);
}
