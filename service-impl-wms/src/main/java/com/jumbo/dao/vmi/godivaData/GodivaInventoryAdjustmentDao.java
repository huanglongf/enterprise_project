package com.jumbo.dao.vmi.godivaData;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.godivaData.GodivaInventoryAdjustment;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface GodivaInventoryAdjustmentDao extends GenericEntityDao<GodivaInventoryAdjustment, Long> {

    @NativeUpdate
    void updateStatusByInvId(@QueryParam(value = "status") int sta, @QueryParam(value = "msgId") Long msgId);

    @NativeUpdate
    void updateGodAdjustInvCode(@QueryParam(value = "invCode") String invCode, @QueryParam(value = "adjustId") Long adjustId);

    @NamedQuery
    List<GodivaInventoryAdjustment> findAdjustments(@QueryParam(value = "shopId") Long shopId, @QueryParam(value = "type") String type, @QueryParam(value = "adjustKey") String adjustKey);

}
