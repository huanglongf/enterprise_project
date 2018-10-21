package com.jumbo.dao.vmi.cch;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cch.CchStockTransConfirmLine;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface CchStockTransConfirmLineDao extends GenericEntityDao<CchStockTransConfirmLine, Long> {

    @NamedQuery
    List<CchStockTransConfirmLine> findLineByConfirm(@QueryParam("confirmID") Long confirmID);

    @NativeQuery
    List<CchStockTransConfirmLine> findGroupLineByConfirm(@QueryParam("confirmID") Long confirmID, RowMapper<CchStockTransConfirmLine> rowMapper);
}
