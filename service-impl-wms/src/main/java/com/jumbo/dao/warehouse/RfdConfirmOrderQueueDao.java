package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.RfdConfirmOrderQueue;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
@Transactional
public interface RfdConfirmOrderQueueDao extends GenericEntityDao<RfdConfirmOrderQueue, Long> {
	
	@NamedQuery
    List<RfdConfirmOrderQueue> findExtOrder(@QueryParam("count") Long count);
	
	@NativeQuery
	List<RfdConfirmOrderQueue> findExtOrderIdSeo(@QueryParam("count") Long count, BeanPropertyRowMapper<RfdConfirmOrderQueue> s);
	
	@NativeQuery
	RfdConfirmOrderQueue findRfdQueueByStaCodeAndTransNo(@QueryParam("staCode") String staCode, @QueryParam("transNo") String transNo, BeanPropertyRowMapper<RfdConfirmOrderQueue> r);

	@NativeUpdate
	void updateExeCountByError(@QueryParam("qList") List<RfdConfirmOrderQueue> qList);
}
