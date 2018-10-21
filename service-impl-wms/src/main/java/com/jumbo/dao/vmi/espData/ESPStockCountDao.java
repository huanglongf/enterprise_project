package com.jumbo.dao.vmi.espData;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPStockCount;

@Transactional
public interface ESPStockCountDao extends GenericEntityDao<ESPStockCount, Long> {

    @NativeUpdate
    void updateStockCountForStatu(@QueryParam("newStatus") Integer newStatus, @QueryParam("oldStatus") Integer oldStatus, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate);

    @NativeUpdate
    void updateStockCountForStatuSeq(@QueryParam("newStatus") Integer newStatus, @QueryParam("oldStatus") Integer oldStatus, @QueryParam("seqNum") String seqNum);

    @NativeQuery(model = ESPStockCount.class)
    List<ESPStockCount> findStockCountList(@QueryParam("status") Integer newStatus);

}
