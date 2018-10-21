package com.jumbo.dao.vmi.ckData;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.ckData.CKInboundFeedBack;

@Transactional
public interface CKInboundFeedBackDao extends GenericEntityDao<CKInboundFeedBack, Long> {

    @NativeUpdate
    void updateInFeedbackStatus(@QueryParam(value = "fromStatus") Integer fromStatus, @QueryParam(value = "toStatus") Integer toStatus);

    @NativeUpdate
    void saveRtnInBoundFromTemplate(@QueryParam(value = "source") String source, @QueryParam(value = "rtnStatus") Integer rtnStatus, @QueryParam(value = "fbStatus") Integer fbStatus);
}
