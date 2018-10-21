package com.jumbo.dao.vmi.cch;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cch.CchStockReturnLine;
import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface CchStockReturnLineDao extends GenericEntityDao<CchStockReturnLine, Long> {

    @NamedQuery
    List<CchStockReturnLine> findLineByStockReturn(@QueryParam("srId") Long srId);
}
