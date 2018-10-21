package com.jumbo.dao.wmsInterface;

import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.wmsInterface.cfm.IntfcCfm;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
public interface IntfcCfmDao extends GenericEntityDao<IntfcCfm, Long> {

    @NativeQuery
    IntfcCfm findIntfcCfmByStaCode(@QueryParam("staCode") String staCode, @QueryParam("stvId") Long stvId, BeanPropertyRowMapperExt<IntfcCfm> r);

    @NamedQuery
    List<IntfcCfm> getIntfcCfmByStatus(@QueryParam("status") Integer status);

    @NativeQuery
    List<IntfcCfm> getIntfcCfmByNeedSend(@QueryParam("dataSource") String dataSource, BeanPropertyRowMapperExt<IntfcCfm> r);

    @NativeQuery
    List<IntfcCfm> findIntfcCfmByNeedSend(@QueryParam("dataSource") String dataSource, @QueryParam("type") int type, BeanPropertyRowMapperExt<IntfcCfm> r);

    @NativeQuery
    List<String> findIntfcCfmDataSource(SingleColumnRowMapper<String> scm);
}
