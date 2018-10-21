package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.RecieverInfo;

@Transactional
public interface OrderRecieverInfoDao extends GenericEntityDao<RecieverInfo, Long> {
    @NativeQuery
    public List<RecieverInfo> findByTransInfo(RowMapper<RecieverInfo> rowMapper);

    @NativeQuery
    public List<RecieverInfo> findByTrackingNumber(RowMapper<RecieverInfo> rowMapper);

}
