package com.jumbo.dao.vmi.ckData;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.ckData.CKInventoryData;

@Transactional
public interface CKInventoryDataDao extends GenericEntityDao<CKInventoryData, Long> {

    @NativeUpdate
    void updateInventoryDataStatus(@QueryParam(value = "fromStatus") Integer fromStatus, @QueryParam(value = "toStatus") Integer toStatus, @QueryParam(value = "batchNum") String batchNum);

    @NamedQuery
    List<CKInventoryData> findCKInvDataByStatus(@QueryParam(value = "status") Integer status, @QueryParam(value = "batchNum") String batchNum);

    @NamedQuery
    List<CKInventoryData> findLocCodeNotExistInLocation(@QueryParam(value = "status") Integer status, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "batchNum") String batchNum);
}
