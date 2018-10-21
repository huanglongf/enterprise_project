package com.jumbo.dao.wmsInterface;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.wmsInterface.cfm.IntfcLineCfm;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
public interface IntfcLineCfmDao extends GenericEntityDao<IntfcLineCfm, Long> {
    @NamedQuery
    List<IntfcLineCfm> getIntfcLineCfmByIcId(@QueryParam("icId") Long icId);


    @NativeQuery
    List<IntfcLineCfm> findIntfcLineCfmByIcId(@QueryParam("icId") Long icId, BeanPropertyRowMapperExt<IntfcLineCfm> r);
}
