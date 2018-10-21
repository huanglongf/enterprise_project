package com.jumbo.dao.vmi.converseData;


import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseVmiStockIn;

@Transactional
public interface ConverseVmiStockInDao extends GenericEntityDao<ConverseVmiStockIn, Long> {
    @NativeQuery
    List<String> findCartonNoList(RowMapper<String> rowMapper);

    @NamedQuery
    List<ConverseVmiStockIn> findConverseInstructListByCartonNo(@QueryParam("cartonNo") String cartonNo);

    @NamedQuery
    ConverseVmiStockIn findCVSByStaLineId(@QueryParam("staLineId") Long staLineId);

    @NativeQuery
    List<ConverseVmiStockIn> findConverseByCartonNo(@QueryParam("cartonNo") String cartonNo);

    @NativeUpdate
    void updateConverseVmiStockIn(@QueryParam("cartonNo") String cartonNo, @QueryParam("staId") Long staId, @QueryParam("staLineId") Long stalineId);
}
