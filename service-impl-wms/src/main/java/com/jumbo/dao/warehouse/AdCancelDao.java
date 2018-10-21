

package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.AdCancel;

@Transactional
public interface AdCancelDao extends GenericEntityDao<AdCancel, Long> {

    /**
     * 查询出所有的AdCancel 根据slipCode 整單
     */
    @NativeQuery
    List<AdCancel> getAdCancelBySlipCode(@QueryParam("slipCode") String slipCode, RowMapper<AdCancel> r);
    
    /**
     * 查询出所有的AdCancel 根据slipCode  部分
     */
    @NativeQuery
    List<AdCancel> getAdCancelBySlipCode2(@QueryParam("slipCode") String slipCode, RowMapper<AdCancel> r);
    
    @NativeUpdate
    int updateAdCancelIsCancel(@QueryParam("slipCode") String slipCode);

    
    @NativeUpdate
    int updateAdCancelIsCancel2(@QueryParam("slipCode") String slipCode,@QueryParam("lineNo") String lineNo);

    
    @NativeUpdate
    int updateAdCancelById(@QueryParam("id") Long id);

    
}
