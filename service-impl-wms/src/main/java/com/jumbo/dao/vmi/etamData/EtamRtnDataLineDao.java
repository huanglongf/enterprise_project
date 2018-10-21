package com.jumbo.dao.vmi.etamData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.etamData.EtamRtnDataLine;


@Transactional
public interface EtamRtnDataLineDao extends GenericEntityDao<EtamRtnDataLine, Long> {

    // T_WH_ETAM_RTN_LINE
    @NativeUpdate
    void createEtamDataRtnLineSql(@QueryParam(value = "billCode") String billCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "quantity") Long quantity, @QueryParam(value = "invStatus") String invStatus,
            @QueryParam(value = "userDef1") String userDef1, @QueryParam(value = "userDef2") String userDef2, @QueryParam(value = "userDef3") String userDef3, @QueryParam(value = "todoStatus") Integer todoStatus);

    @NativeUpdate
    void updateEtamRtnId(@QueryParam(value = "id") Long id, @QueryParam(value = "createStatus") Integer createStatus, @QueryParam(value = "billCode") String billCode);

    @NativeQuery
    List<EtamRtnDataLine> findByEtamRtnId(@QueryParam(value = "etamRtnId") Long etamRtnId, @QueryParam(value = "createStatus") Integer createStatus, BeanPropertyRowMapperExt<EtamRtnDataLine> beanPropertyRowMapperExt);

    @NativeUpdate
    void updateFinishByEtamRtnId(@QueryParam(value = "id") Long id);

    @NativeUpdate
    void updateSkuIdByEtamRtnId(@QueryParam(value = "etamRtnId") Long etamRtnId);
}
