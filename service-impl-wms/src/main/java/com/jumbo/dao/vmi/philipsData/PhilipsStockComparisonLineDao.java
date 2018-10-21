package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsStockComparisonLine;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsStockComparisonLineDao extends GenericEntityDao<PhilipsStockComparisonLine, Long> {

    @NativeUpdate
    public void insertStockDataByShop(@QueryParam("shopId") Long shopId, @QueryParam("shopId") Long pscId);

    @NativeQuery
    List<PhilipsStockComparisonLine> getComparisonLinesByStockId(@QueryParam("stockId") Long stockId, RowMapper<PhilipsStockComparisonLine> rowMapper);
}
