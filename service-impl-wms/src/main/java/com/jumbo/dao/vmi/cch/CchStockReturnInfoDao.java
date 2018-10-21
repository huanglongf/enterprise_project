package com.jumbo.dao.vmi.cch;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cch.CchStockReturnInfo;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface CchStockReturnInfoDao extends GenericEntityDao<CchStockReturnInfo, Long> {

    @NativeQuery
    BigDecimal findCCHSEQ(SingleColumnRowMapper<BigDecimal> rowMap);

    @NamedQuery
    List<CchStockReturnInfo> findReturnInfoByStatus(@QueryParam("status") Integer status, @QueryParam("vmiCode") String vmiCode);
    
    @NativeQuery
    List<Long> findStaIdByStatus(@QueryParam("status") Integer status, @QueryParam("vmiCode") String vmiCode,SingleColumnRowMapper<Long> singleColumnRowMapper);
    
    @NamedQuery
    List<CchStockReturnInfo> findReturnInfoByStatusAndStaId(@QueryParam("status") Integer status, @QueryParam("vmiCode") String vmiCode, @QueryParam("staId") Long staId);
    
    @NativeUpdate
    void updateCchStockReturnInfoByStaId(@QueryParam("msgBatchId") Long msgBatchId,@QueryParam("status") Integer status,@QueryParam("staId") Long staId);
}
