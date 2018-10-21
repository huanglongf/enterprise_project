package com.jumbo.dao.vmi.levis;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.levisData.LevisYxTransferOut;

@Transactional
public interface LevisYxTransferOutDao extends GenericEntityDao<LevisYxTransferOut, Long>{
    
    @NativeUpdate
    void saveRtnResult(@QueryParam("staId") Long id);

    @NativeUpdate
    void updateDataToWriteStatus();

    @NativeUpdate
    void updateToFinishStatus();

    @NativeQuery
    List<LevisYxTransferOut> findAllToWriteStatusData(BeanPropertyRowMapper<LevisYxTransferOut> beanPropertyRowMapper);

}
