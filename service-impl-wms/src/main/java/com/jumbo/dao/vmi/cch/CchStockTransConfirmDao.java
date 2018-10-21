package com.jumbo.dao.vmi.cch;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cch.CchStockTransConfirm;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface CchStockTransConfirmDao extends GenericEntityDao<CchStockTransConfirm, Long> {

    @NamedQuery
    List<CchStockTransConfirm> findConfirmByStatus(@QueryParam("status") Integer status, @QueryParam("vmiCode") String vmiCode);

    @NativeUpdate
    int updateCchConfirStatusByBarCode(@QueryParam("toStatus") Integer toStatus, @QueryParam("fromStatus") Integer fromStatus);
}
