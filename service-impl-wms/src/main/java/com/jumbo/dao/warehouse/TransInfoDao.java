package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.trans.TransInfo;
@Transactional
public interface TransInfoDao extends GenericEntityDao<TransInfo, Long> {
	
	@NamedQuery
	List<TransInfo> getAvailableTransInfo();
	
	@NativeQuery
	List<String> getAllLpCode(SingleColumnRowMapper<String> r);
	
	@NativeQuery
	List<Long> findStaByTransInfoLpCode(SingleColumnRowMapper<Long> r);
	
	@NativeQuery
	TransInfo getTransInfoByLpCodeAndRegionCode(@QueryParam("lpCode") String lpCode, @QueryParam("regionCode") String regionCode, BeanPropertyRowMapper<TransInfo> r);
}
