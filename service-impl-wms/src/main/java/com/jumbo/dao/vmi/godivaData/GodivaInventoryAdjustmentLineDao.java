package com.jumbo.dao.vmi.godivaData;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.godivaData.GodivaInventoryAdjustmentLine;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface GodivaInventoryAdjustmentLineDao extends GenericEntityDao<GodivaInventoryAdjustmentLine, Long> {

    @NamedQuery
    List<GodivaInventoryAdjustmentLine> findGodivaInventoryAdjustmentLineById(@QueryParam(value = "godId") Long godId);
}
