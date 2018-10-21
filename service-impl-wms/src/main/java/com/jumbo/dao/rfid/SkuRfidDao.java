package com.jumbo.dao.rfid;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.SkuRfid;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface SkuRfidDao extends GenericEntityDao<SkuRfid, Long> {

    @NamedQuery
    public List<SkuRfid> findSkuRFIDByStaId(@QueryParam("staId") Long staId);
}
