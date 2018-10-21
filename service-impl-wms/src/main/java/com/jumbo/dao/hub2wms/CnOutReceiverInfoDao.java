package com.jumbo.dao.hub2wms;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnOutReceiverInfo;

@Transactional
public interface CnOutReceiverInfoDao extends GenericEntityDao<CnOutReceiverInfo, Long> {
    @NamedQuery
    CnOutReceiverInfo getCnOutReceiverInfoByNotifyId(@QueryParam(value = "notifyId") Long notifyId);
}
