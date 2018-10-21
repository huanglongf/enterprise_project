package com.jumbo.dao.baseinfo;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.MsgOmsSkuLog;

@Transactional
public interface MsgOmsSkuLogDao extends GenericEntityDao<MsgOmsSkuLog, Long> {

    @NativeUpdate
    void deleteByExtCode2(@QueryParam("extcode2") String extcode2);
    
    @NamedQuery
    MsgOmsSkuLog findMsgOmsSkuLogByCode(@QueryParam("extcode2") String extcode2);
    
    @NativeQuery
    List<MsgOmsSkuLog> findMsgOmsSkuLogByTime(RowMapper<MsgOmsSkuLog> row);
    
    @NativeUpdate
    void updateMsgOmsSkuLogById(@QueryParam("id") Long id);
}
