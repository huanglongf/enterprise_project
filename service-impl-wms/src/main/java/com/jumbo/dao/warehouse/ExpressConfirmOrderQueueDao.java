package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ExpressConfirmOrderQueue;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
@Transactional
public interface ExpressConfirmOrderQueueDao extends GenericEntityDao<ExpressConfirmOrderQueue, Long> {
	
	@NativeQuery
	List<Long> findExtOrderIdSeo(@QueryParam("count") Long count, SingleColumnRowMapper<Long> singleColumnRowMapper);
	
	@NativeQuery
	ExpressConfirmOrderQueue findExOrderByStaCodeAndTransNo(@QueryParam("lpCode") String lpCode, @QueryParam("staCode") String staCode, @QueryParam("transNo") String transNo, BeanPropertyRowMapper<ExpressConfirmOrderQueue> r);
}
