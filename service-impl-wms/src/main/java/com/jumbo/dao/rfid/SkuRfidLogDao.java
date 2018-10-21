package com.jumbo.dao.rfid;

import java.util.List;

import com.jumbo.wms.model.baseinfo.SkuRfidLog;

import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

public interface SkuRfidLogDao extends GenericEntityDao<SkuRfidLog, Long> {


    public List<SkuRfidLog> findSkuRFIDByStaId(@QueryParam("staId") Long staId);
}
