package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.JdConfirmOrderQueue;

public interface JdConfirmOrderQueueDao extends GenericEntityDao<JdConfirmOrderQueue, Long> {
		@NamedQuery
	    List<JdConfirmOrderQueue> findExtOrder(@QueryParam("count") Long count);
		@NativeQuery
	    JdConfirmOrderQueue findTransNoOrder(@QueryParam("transno") String transno,RowMapper<JdConfirmOrderQueue> rowMapper);

}
