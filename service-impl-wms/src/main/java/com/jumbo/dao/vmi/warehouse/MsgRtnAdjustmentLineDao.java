package com.jumbo.dao.vmi.warehouse;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustmentLine;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface MsgRtnAdjustmentLineDao extends GenericEntityDao<MsgRtnAdjustmentLine, Long> {


    @NativeUpdate
    void saveByIDSADJLine(@QueryParam(value = "status") Integer status);

    @NamedQuery
    List<MsgRtnAdjustmentLine> queryLineByADJId(@QueryParam(value = "adjId") Long adjId);

    @NativeUpdate
    void updateSkuIdForGymboree();

}
