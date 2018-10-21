package com.jumbo.dao.warehouse;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ConvenienceStoreOrderInfo;

@Transactional
public interface ConvenienceStoreOrderInfoDao extends GenericEntityDao<ConvenienceStoreOrderInfo, Long> {
    @NativeQuery
    List<ConvenienceStoreOrderInfo> findConvenienceStoreOrderInfo(@QueryParam("fromDate") Date fromDate, @QueryParam("endDate") Date endDate, @QueryParam("ouId") Long ouId, BeanPropertyRowMapper<ConvenienceStoreOrderInfo> beanPropertyRowMapper);
}
