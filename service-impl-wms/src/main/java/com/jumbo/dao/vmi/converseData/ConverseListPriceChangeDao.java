package com.jumbo.dao.vmi.converseData;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseListPriceChange;
import com.jumbo.wms.model.vmi.itData.ConversePriceChangeStatus;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ConverseListPriceChangeDao extends GenericEntityDao<ConverseListPriceChange, Long> {

    @NativeUpdate
    void updateRetailPriceByListPriceChange(@QueryParam("status") int status);

    @NativeUpdate
    void updateCLPCStatus(@QueryParam("fromStatus") int fromStatus, @QueryParam("toStatus") int toStatus);

    @NamedQuery
    List<ConverseListPriceChange> getDataByStatus(@QueryParam(value = "status") ConversePriceChangeStatus status);
}
