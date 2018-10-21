package com.jumbo.dao.vmi.cch;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cch.CacheStockTransferInData;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface CacheStockTransferInDataDao extends GenericEntityDao<CacheStockTransferInData, Long> {

    @NativeQuery
    List<String> getParcelCodeWithNoSta(@QueryParam("vmiCode") String vmiCode,RowMapper<String> rowMapper);

    @NamedQuery
    List<CacheStockTransferInData> getStockInListByParcelCode(@QueryParam("parcelCode") String parcelCode);
    
    @NativeQuery
    List<CacheStockTransferInData> getStockInListByParcelCodeSql(@QueryParam("parcelCode") String parcelCode,RowMapper<CacheStockTransferInData> rowMapper);
}
