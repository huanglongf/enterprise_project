package com.jumbo.dao.vmi.ckData;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.ckData.CKOutBoundFeedBack;

@Transactional
public interface CKOutBoundFeedBackDao extends GenericEntityDao<CKOutBoundFeedBack, Long> {

    @NativeUpdate
    void updateOutFeedbackStatus(@QueryParam(value = "fromStatus") Integer fromStatus, @QueryParam(value = "toStatus") Integer toStatus);
}
