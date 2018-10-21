package com.jumbo.dao.warehouse;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.RecieverInfo;

@Transactional
public interface RecieverInfoDao extends GenericEntityDao<RecieverInfo, Long> {

    @NativeQuery
    public RecieverInfo findRecieverInfo(@QueryParam("infoId") Long infoId, RowMapper<RecieverInfo> rowMapper);

    @NativeQuery
    public RecieverInfo findRecieverInfoByTransNo(@QueryParam("transNo") String transNo, RowMapper<RecieverInfo> rowMapper);



}
