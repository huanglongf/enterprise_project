package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.StaCancel;

@Transactional
public interface StaCancelDao extends GenericEntityDao<StaCancel, Long> {

    @NativeQuery
    List<StaCancel> findByStaCancelId(@QueryParam("orderCancelId") Long staCancelId, BeanPropertyRowMapperExt<StaCancel> beanPropertyRowMapperExt);
}
